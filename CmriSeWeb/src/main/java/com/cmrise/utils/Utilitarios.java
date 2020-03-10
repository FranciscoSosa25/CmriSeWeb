package com.cmrise.utils;


public  class Utilitarios {

	public static final String PCUNITNAME="CMRISEPU";
	public static final java.sql.Date endOfTime = java.sql.Date.valueOf("9999-12-31");
	public static final java.sql.Date startOfTime = java.sql.Date.valueOf("0001-01-01");
	public static final String SOCIEDAD ="CMRI - Consejo Mexicano de Radiolog\u00eda e Imagen";
	public static final String INITIAL_STATUS_MRQ ="PARA_REVISAR";
	public static final String INITIAL_STATUS_CC_EXAM="EN_EL_TRABAJO";
	public static final String RESP_TEXTO_LIBRE = "RESP_TEXTO_LIBRE"; 
	public static final String OPCION_MULTIPLE = "OPCION_MULTIPLE";
	public static final String WRONG_CORRECT = "WRONG_CORRECT"; 
	public  java.sql.Date toSqlDate(java.util.Date pUtilDate){
		 java.sql.Date retval = null; 
		 if(null!=pUtilDate) {
			 retval = new java.sql.Date(pUtilDate.getTime());
		 }
		 return retval; 		 
	}
	public  java.sql.Timestamp toSqlTimestamp(java.util.Date pUtilDate){
		 java.sql.Timestamp retval = null; 
		 if(null!=pUtilDate) {
			 retval = new java.sql.Timestamp(pUtilDate.getTime());
		 }
		 return retval; 		 
	}
	public java.util.Date toUtilDate(java.sql.Date pSqlDate){
		java.util.Date retval = null; 
		if(null!=pSqlDate) {
			retval = new java.util.Date(pSqlDate.getTime());
		}
		return retval; 
	} 
	
	public java.util.Date toUtilDate(java.sql.Timestamp pSqlTimestamp){
		java.util.Date retval = null; 
		if(null!=pSqlTimestamp) {
			retval = new java.util.Date(pSqlTimestamp.getTime());
		}
		return retval; 
	} 
	
	public static long objToLong(Object pObject) {
		long retval = 0;
		 if(null!=pObject) {
	    	 if(pObject instanceof Long) {
	    		 long numeroCcHdr = (Long)pObject;
	    		 retval = numeroCcHdr; 
	    	 }else {
	    		 System.out.println("pObject instanceof Long:false");
	    		 return 0;
	    	 }
	     }else {
	    	 System.out.println("null!=pObject");
	    	 return 0;
	     }	
		return retval;
	} 
	
}
