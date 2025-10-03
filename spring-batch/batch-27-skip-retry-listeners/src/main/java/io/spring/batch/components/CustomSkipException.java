package io.spring.batch.components;


public class CustomSkipException extends Exception {

	public CustomSkipException(String msg) {
		super(msg);
	}
}
