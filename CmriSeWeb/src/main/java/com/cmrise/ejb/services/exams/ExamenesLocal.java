package com.cmrise.ejb.services.exams;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.exams.Examenes;

@Local
public interface ExamenesLocal {

	public List<Examenes> findAllObjMod();
	public List<Examenes> findByTituloExamen(int idExamen, String nombreExamen, Date fechaDesde, Date fechaHasta, int tiempo, String tipoExamen);

}
