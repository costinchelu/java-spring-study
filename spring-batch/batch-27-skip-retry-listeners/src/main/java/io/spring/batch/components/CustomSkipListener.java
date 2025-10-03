package io.spring.batch.components;

import org.springframework.batch.core.SkipListener;


public class CustomSkipListener implements SkipListener<Object, Object> {
	@Override
	public void onSkipInRead(Throwable t) {
		System.out.println("Just if the reading has errors...");
	}

	@Override
	public void onSkipInWrite(Object itemThatCausedError, Throwable t) {
		System.out.println(">> Skipping " + itemThatCausedError + " because writing it caused the error: " + t.getMessage());
	}

	@Override
	public void onSkipInProcess(Object itemThatCausedError, Throwable t) {
		System.out.println(">> Skipping " + itemThatCausedError + " because processing it caused the error: " + t.getMessage());
	}
}
