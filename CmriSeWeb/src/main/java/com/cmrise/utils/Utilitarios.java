package com.cmrise.utils;

import javax.ejb.Singleton;
import javax.ejb.Startup;


public  class Utilitarios {

	public static final String PCUNITNAME="CMRISEPU";
	public static java.sql.Date endOfTime = java.sql.Date.valueOf("9999-12-31");
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
	
}
