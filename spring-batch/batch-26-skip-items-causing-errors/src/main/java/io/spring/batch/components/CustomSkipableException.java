package io.spring.batch.components;


public class CustomSkipableException extends Exception {

	public CustomSkipableException(String msg) {
		super(msg);
	}
}
