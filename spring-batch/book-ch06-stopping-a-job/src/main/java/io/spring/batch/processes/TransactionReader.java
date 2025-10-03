package io.spring.batch.processes;

import io.spring.batch.domain.Transaction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * Import a simple transaction file (transaction.csv). <br>
 * Each transaction consists of an account number, a timestamp, and an amount
 * (positive is a credit, negative is a debit). The file ends with a single summary record containing the number of
 * records in the file.
 */
@RequiredArgsConstructor
public class TransactionReader implements ItemStreamReader<Transaction> {

    @NonNull
    @Setter
    private ItemStreamReader<FieldSet> fieldSetReader;

    private int recordCount = 0;

    private int expectedRecordCount = 0;

    // added so that the check is moved into the reader
    private StepExecution stepExecution;

    // delegates to the reader we inject. In our case, it will be a FlatFileItemReader
    public Transaction read() throws Exception {
        return process(fieldSetReader.read());
        // We let the delegate return a FieldSet because we actually have two record formats.
        // One for the data we’re importing and one for the footer that
        //contains the number of records in the file. We pass the FieldSet returned from the delegate ItemReader to
        //a method called process.
    }

    private Transaction process(FieldSet fieldSet) {
        Transaction result = null;

        if (fieldSet != null) {
            // If there is more than one value in the record, it’s a data record.
            // If there is only one field in the record, it’s the footer record.
            if (fieldSet.getFieldCount() > 1) {
                result = new Transaction();
                result.setAccountNumber(fieldSet.readString(0));
                result.setTimestamp(fieldSet.readDate(1, "yyyy-MM-DD HH:mm:ss"));
                result.setAmount(fieldSet.readDouble(2));
                recordCount++;
            } else {
                expectedRecordCount = fieldSet.readInt(0);

                if (expectedRecordCount != this.recordCount) {
                    this.stepExecution.setTerminateOnly();
                }
            }
        }

        return result;
    }

    /**
     * This method will be called once our step is complete
     * giving us the opportunity to return a specific ExitStatus. In our case, this method will look at the
     * number of records read and compare that with the value saved in the footer of the file. If they match,
     * it will return the ExitStatus set by the framework. Otherwise, it will return ExitStatus.STOPPED. This
     * will allow us to stop the Job from continuing if the file is invalid.
     */
//    @AfterStep
//    public ExitStatus afterStep(StepExecution execution) {
//        if(recordCount == expectedRecordCount) {
//            return execution.getExitStatus();
//        } else {
//            return ExitStatus.STOPPED;
//        }
//    }

    // by doing the check before the Step we can remove the "after step" annotated method
    @BeforeStep
    public void beforeStep(StepExecution execution) {
        this.stepExecution = execution;
    }

    /*
    The rest of the methods in our TransactionItemReader are the implementation of the ItemStream
interface. Spring Batch will automatically look at the ItemReader, ItemProcessor, and ItemWriter to
see if it’s an ItemStream and register it automatically to have the callbacks executed at the appropriate
time. However, we have a delegate that implements ItemStream. This delegate ItemReader won’t be
explicitly registered with Spring Batch so the framework won’t look to see if it implements ItemStream.
This leaves us with two options. Either we remember to explicitly register the delegate as an ItemStream
on our job (an error-prone approach since it requires us to remember to do the registration) or implement
ItemStream in our TransactionItemReader and have it call the appropriate lifecycle methods on the
delegate which is what this does.
     */

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.fieldSetReader.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        this.fieldSetReader.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        this.fieldSetReader.close();
    }
}

