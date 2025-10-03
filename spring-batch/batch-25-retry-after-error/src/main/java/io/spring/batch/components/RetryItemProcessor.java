package io.spring.batch.components;

import org.springframework.batch.item.ItemProcessor;


public class RetryItemProcessor implements ItemProcessor<String, String> {

    private boolean retry = false;

    private int attemptCount = 0;

    @Override
    public String process(String item) throws Exception {
        System.out.println("Processing item: " + item);
        if (retry && item.equalsIgnoreCase("46")) {
            attemptCount++;

            if (attemptCount >= 5) {
                System.out.println("Success! Item 46 is processed.");
                retry = false;
                return "w[" + item + "]";
            } else {
                System.out.println("Processing of item " + item + " failed. (" + attemptCount + ")");
                throw new CustomRetryableException("Process failed. Attempt:" + attemptCount);
            }
        } else {
            System.out.println("- Item " + item + " is processed.");
            return "w[" + item + "]";
        }
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}
