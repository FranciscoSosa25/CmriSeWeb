package com.cmrise.jpa.dao.exams;

import java.util.Date;
import java.util.List;

import com.cmrise.ejb.model.candidates.exams.CandHExamenes;

public interface ExamenesDao {
	
	public List<Object> findAll();
	public List<Object> findByTitulo(int idExamen, String nombreExamen, Date fechaDesde, Date fechaHasta, int tiempo, String tipoExamen);
	public List<Object> findCandidatesForthisExam(int idExamen); 
	public List<Object> findCandidatesExamDetails(int idExamen, long numeroCandExamen);

}
