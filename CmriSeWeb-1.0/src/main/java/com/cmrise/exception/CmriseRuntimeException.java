package com.cmrise.exception;

public class CmriseRuntimeException extends RuntimeException{
		
	private static final long serialVersionUID = -872581757363489064L;

	public CmriseRuntimeException(String msg) {
		super(msg);
	}
	
	public CmriseRuntimeException(String msg, Throwable t) {
		super(msg, t);
	}
	
	

}
