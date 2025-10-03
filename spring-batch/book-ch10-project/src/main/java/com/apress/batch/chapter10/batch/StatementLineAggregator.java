package com.apress.batch.chapter10.batch;

import com.apress.batch.chapter10.domain.Account;
import com.apress.batch.chapter10.domain.Customer;
import com.apress.batch.chapter10.domain.Statement;
import com.apress.batch.chapter10.domain.Transaction;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;

public class StatementLineAggregator implements LineAggregator<Statement> {

	private static final String ADDRESS_LINE_ONE = String.format("%121s%n", "Apress Banking");

	private static final String ADDRESS_LINE_TWO = String.format("%120s%n", "1060 West Addison St.");

	private static final String ADDRESS_LINE_THREE = String.format("%120s%n%n", "Chicago, IL 60613");

	private static final String ACCOUNT_NUMBER_LINE = "Account no %d";

	private static final String STATEMENT_DATE_LINE = String.format(" summary %78s ", "Statement Period") + "%tD to %tD\n\n";

	public String aggregate(Statement statement) {
		StringBuilder output = new StringBuilder();

		formatHeader(statement, output);
		formatAccount(statement, output);

		return output.toString();
	}

	private void formatAccount(Statement statement, StringBuilder output) {
		if(!CollectionUtils.isEmpty(statement.getAccounts())) {

			for (Account account : statement.getAccounts()) {

				output.append(String.format(ACCOUNT_NUMBER_LINE, account.getId()));

				output.append(
						String.format(STATEMENT_DATE_LINE,
								account.getLastStatementDate(),
								new Date()));

				BigDecimal creditAmount = new BigDecimal(0);
				BigDecimal debitAmount = new BigDecimal(0);
				for (Transaction transaction : account.getTransactions()) {
					if(transaction.getCredit() != null) {
						creditAmount = creditAmount.add(transaction.getCredit());
					}

					if(transaction.getDebit() != null) {
						debitAmount = debitAmount.add(transaction.getDebit());
					}

					output.append(String.format("               %tD          %-50s    %8.2f%n",
							transaction.getTimestamp(),
							transaction.getDescription(),
							transaction.getTransactionAmount()));
				}

				output
						.append(String.format("%80s %14.2f%n", "Total Debit:" , debitAmount))
						.append(String.format("%81s %13.2f%n", "Total Credit:", creditAmount))
						.append(String.format("%76s %18.2f%n%n", "Balance:", account.getBalance()));
			}
		}
	}

	private void formatHeader(Statement statement, StringBuilder output) {
		Customer customer = statement.getCustomer();
		String customerName = String.format("%n%s %s", customer.getFirstName(), customer.getLastName());
		String addressString = String.format("%s, %s %s", customer.getCity(), customer.getState(), customer.getPostalCode());

		output
				.append(customerName)
				.append(ADDRESS_LINE_ONE.substring(customerName.length()))
				.append(customer.getAddress1())
				.append(ADDRESS_LINE_TWO.substring(customer.getAddress1().length()))
				.append(addressString)
				.append(ADDRESS_LINE_THREE.substring(addressString.length()));
	}
}