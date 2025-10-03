package io.spring.batch.configuration;

import java.util.List;

import org.springframework.batch.item.ItemWriter;


public class SysOutItemWriter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) {
		System.out.println("Chunk was sent to the output. The size of this chunk was: " + items.size());

		for (String item : items) {
			System.out.println(">> " + item);
		}
	}
}
