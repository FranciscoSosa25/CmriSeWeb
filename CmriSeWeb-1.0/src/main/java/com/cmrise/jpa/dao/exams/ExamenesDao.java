package com.cmrise.jpa.dao.exams;

import java.util.Date;
import java.util.List;

public interface ExamenesDao {
	
	public List<Object> findAll();
	public List<Object> findByTitulo(int idExamen, String nombreExamen, Date fechaDesde, Date fechaHasta, int tiempo, String tipoExamen);
	public List<Object> findCandidatesForthisExam(int idExamen); 
}
