package com.cmrise.jpa.dao.corecases;

import java.util.List;

import com.cmrise.jpa.dto.corecases.CcPreguntasFtaSinonimos;


public interface CcPreguntasFtaSinonimosDao {
	public long insert(List<CcPreguntasFtaSinonimos> sinonimos,long lNumeroFta,String texto) throws Exception;
    public List<CcPreguntasFtaSinonimos> findByFta(long pNumeroFta); 			 
	public void delete(CcPreguntasFtaSinonimos sinonimo) throws Exception;;
}
