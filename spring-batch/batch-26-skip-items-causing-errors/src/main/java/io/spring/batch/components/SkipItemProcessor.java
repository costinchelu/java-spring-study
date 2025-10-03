package io.spring.batch.components;

import org.springframework.batch.item.ItemProcessor;


public class SkipItemProcessor implements ItemProcessor<String, String> {

	private boolean skip = false;

	private int attemptCount = 0;

	@Override
	public String process(String item) throws Exception {
		System.out.println("-- Processing item: " + item);
		if(skip && (item.equalsIgnoreCase("46") || item.equalsIgnoreCase("53"))) {
			attemptCount++;

			System.out.println("Processing of item " + item + " failed. (skipping)");
			throw new CustomSkipableException("Process failed. Attempt:" + attemptCount);
		}
		else {
			System.out.println("- Item " + item + " is processed.");
			return "w[" + item + "]";
		}
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
