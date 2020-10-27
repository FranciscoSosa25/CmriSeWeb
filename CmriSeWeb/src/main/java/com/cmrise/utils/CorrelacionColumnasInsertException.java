package com.cmrise.utils;

import javax.persistence.PersistenceException;

public class CorrelacionColumnasInsertException extends Exception {
	public static final String 	ERROR_INSERTAR="Error en la inserccion de datos";
	public static final String 	ERROR_ELIMINAR="Error en la eliminacion de datos";
	public static final String 	ERROR_TRANSACCION="Error";
	public CorrelacionColumnasInsertException(String message,Exception ob) {
		super(message);
		handleExceptionDTO(ob);
	}
	public CorrelacionColumnasInsertException(String message,PersistenceException ob) {
		super(message);
		handleException(ob);
	}
	private void handleExceptionDTO(Exception ob) {		
		this.setStackTrace(ob.getStackTrace());		
	}
	private void handleException(PersistenceException ob) {		
		this.setStackTrace(ob.getStackTrace());		
	}
	
	
	
}
