package io.spring.batch.components;

import java.util.List;

import org.springframework.batch.item.ItemWriter;


public class SkipItemWriter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		for (String item : items) {
			System.out.println("-- Writing item: " + item);
			if(item.equalsIgnoreCase("w[84]")) {
				throw new CustomSkipException("Write failed.");
			}
			else {
				System.out.println("- Item " + item + " is written.");
			}
		}
	}
}
