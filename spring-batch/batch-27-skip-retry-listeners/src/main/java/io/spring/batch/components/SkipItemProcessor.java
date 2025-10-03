package io.spring.batch.components;

import org.springframework.batch.item.ItemProcessor;


public class SkipItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		System.out.println("-- Processing item: " + item);
		if(item.equalsIgnoreCase("42")) {
			throw new CustomSkipException("Process failed.");
		}
		else {
			System.out.println("- Item " + item + " is processed.");
			return "w[" + item + "]";
		}
	}
}
