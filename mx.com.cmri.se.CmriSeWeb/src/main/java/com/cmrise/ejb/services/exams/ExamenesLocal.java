package com.cmrise.ejb.services.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.exams.Examenes;

@Local
public interface ExamenesLocal {

	public List<Examenes> findAllObjMod();

}
