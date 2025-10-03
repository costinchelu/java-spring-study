package io.spring.batch.components;

import java.util.List;

import org.springframework.batch.item.ItemWriter;


public class RetryItemWriter implements ItemWriter<String> {

	private boolean retry = false;

	private int attemptCount = 0;

	@Override
	public void write(List<? extends String> items) throws Exception {
		for (String item : items) {
			System.out.println("Writing item: " + item);
			if(retry && item.equalsIgnoreCase("w[84]")) {
				attemptCount++;

				if(attemptCount >= 5) {
					System.out.println("Success! Item 84 is written.");
					retry = false;
				}
				else {
					System.out.println("Writing of item " + item + " failed. (" + attemptCount + ")");
					throw new CustomRetryableException("Write failed. Attempt:" + attemptCount);
				}
			}
			else {
				System.out.println("- Item " + item + " is written.");
			}
		}
	}

	public void setRetry(boolean retry) {
		this.retry = retry;
	}
}
