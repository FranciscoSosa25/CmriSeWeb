package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;
import com.cmrise.utils.CorrelacionColumnasInsertException;

@Local
public interface MrqsPreguntasFtaSinonimosLocal {

	public long insert(List<MrqsPreguntasFtaSinonimos> sinonimos,long lNumeroFta,String texto)  throws Exception; 
	public List<MrqsPreguntasFtaSinonimos> findByFta(long pNumeroFta); 
	public void deleteSinonimo(MrqsPreguntasFtaSinonimos item)  throws Exception; 
	 
}
