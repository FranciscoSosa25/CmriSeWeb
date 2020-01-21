package com.cmrise.utils;

import javax.ejb.Local;

@Local
public interface UtilitariosLocal {

	public  java.sql.Date toSqlDate(java.util.Date pUtilDate); 
	public  java.sql.Timestamp toSqlTimestamp(java.util.Date pUtilDate); 
	public java.util.Date toUtilDate(java.sql.Date pSqlDate); 
	public java.util.Date toUtilDate(java.sql.Timestamp pSqlTimestamp); 
}
