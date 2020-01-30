package com.cmrise.utils;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class UtilitariosLocalImpl implements UtilitariosLocal {

	public static final String PCUNITNAME="CMRISEPU";
	public static java.sql.Date endOfTime = java.sql.Date.valueOf("9999-12-31");
	@Override
	public  java.sql.Date toSqlDate(java.util.Date pUtilDate){
		 java.sql.Date retval = null; 
		 if(null!=pUtilDate) {
			 retval = new java.sql.Date(pUtilDate.getTime());
		 }
		 return retval; 		 
	}
	@Override
	public  java.sql.Timestamp toSqlTimestamp(java.util.Date pUtilDate){
		 java.sql.Timestamp retval = null; 
		 if(null!=pUtilDate) {
			 retval = new java.sql.Timestamp(pUtilDate.getTime());
		 }
		 return retval; 		 
	}
	@Override
	public java.util.Date toUtilDate(java.sql.Date pSqlDate){
		java.util.Date retval = null; 
		if(null!=pSqlDate) {
			retval = new java.util.Date(pSqlDate.getTime());
		}
		return retval; 
	} 
	@Override
	public java.util.Date toUtilDate(java.sql.Timestamp pSqlTimestamp){
		java.util.Date retval = null; 
		if(null!=pSqlTimestamp) {
			retval = new java.util.Date(pSqlTimestamp.getTime());
		}
		return retval; 
	}
	@Override
	public long objToLong(Object pObject) {
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
