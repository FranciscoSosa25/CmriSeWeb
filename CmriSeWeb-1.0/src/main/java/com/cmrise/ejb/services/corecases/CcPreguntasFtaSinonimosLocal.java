package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.corecases.CcPreguntasFtaSinonimos;


@Local
public interface CcPreguntasFtaSinonimosLocal {
	public long insert(List<CcPreguntasFtaSinonimos> sinonimos,long lNumeroFta,String texto)  throws Exception; 
	public List<CcPreguntasFtaSinonimos> findByFta(long pNumeroFta); 
	public void deleteSinonimo(CcPreguntasFtaSinonimos item)  throws Exception; 
	 
}
