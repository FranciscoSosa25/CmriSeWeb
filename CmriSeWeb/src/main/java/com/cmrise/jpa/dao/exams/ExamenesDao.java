package com.cmrise.jpa.dao.exams;

import java.util.List;

public interface ExamenesDao {
	
	public List<Object> findAll(); 
	public List<Object> findByTituloExamen(String pTituloExamen); 

}
