package com.twodarray.helloworld.exception;

public class HelloWorldException extends RuntimeException
{
	private static final long serialVersionUID = 2827308907740826575L;
	
	private final String code;
	
	public HelloWorldException(String message)
	{
		super(message);
		this.code = "0";
	}
	
	/**
	 * Instantiates a new Base exception.
	 *
	 * @param message the message
	 * @param code    the code
	 */
	public HelloWorldException(String message, String code) {
		super(message);
		this.code = code;
	}
	
	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
}
