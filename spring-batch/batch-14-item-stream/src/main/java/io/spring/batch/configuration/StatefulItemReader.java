package io.spring.batch.configuration;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;


public class StatefulItemReader implements ItemStreamReader<String> {

	private final List<String> items;

	private int curIndex = -1;

	private boolean restart = false;

	private static final String CURR_INDEX = "curIndex";

	public StatefulItemReader(List<String> items) {
		this.items = items;
		this.curIndex = 0;
	}

	@Override
	public String read() throws Exception {
		String item = null;

		if(this.curIndex < this.items.size()) {
			item = this.items.get(this.curIndex);
			this.curIndex++;
		}

		if(this.curIndex == 42 && !restart) {
			throw new RuntimeException("An error has occurred when reading item " + curIndex);
		}

		return item;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if(executionContext.containsKey(CURR_INDEX)) {
			this.curIndex = executionContext.getInt(CURR_INDEX);
			this.restart = true;
		}
		else {
			this.curIndex = 0;
			executionContext.put(CURR_INDEX, this.curIndex);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.put(CURR_INDEX, this.curIndex);
	}

	@Override
	public void close() throws ItemStreamException {
		System.out.println("We don't need to close resources...");
	}
}
