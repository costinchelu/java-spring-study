package io.spring.batch.reader;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;


public class StatelessItemReader implements ItemReader<String> {

	// the item can be as complex as we want. Here is a simple example with items as strings.
	private final Iterator<String> data;

	public StatelessItemReader(List<String> data) {
		this.data = data.iterator();
	}

	@Override
	public String read() throws Exception {
		if(this.data.hasNext()) {
			return this.data.next();
		}
		else {
			return null;
		}
	}
}
// when the reader returns null, the read phase is finished
// if we are not returning null, then the value we return will be passed to the writer in an infinite loop
