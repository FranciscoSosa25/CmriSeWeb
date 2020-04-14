package com.cmrise.dicom.exception;

public class InvalidParameterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String attibute;

    public InvalidParameterException(String attribute, String message) {
        super(message);
        this.attibute = attribute;
    }
}
