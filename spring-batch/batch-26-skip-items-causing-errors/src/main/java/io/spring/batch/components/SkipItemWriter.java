package io.spring.batch.components;

import java.util.List;

import org.springframework.batch.item.ItemWriter;


public class SkipItemWriter implements ItemWriter<String> {

	private boolean skip = false;

	private int attemptCount = 0;

	@Override
	public void write(List<? extends String> items) throws Exception {
		for (String item : items) {
			System.out.println("-- Writing item: " + item);
			if(skip && item.equalsIgnoreCase("w[84]")) {
				attemptCount++;

				System.out.println("Writing of item " + item + " failed. (skipping)");
				throw new CustomSkipableException("Write failed. Attempt:" + attemptCount);
			}
			else {
				System.out.println("- Item " + item + " is written.");
			}
		}
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
