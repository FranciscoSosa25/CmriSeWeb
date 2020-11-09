package com.cmrise.jpa.dao.mrqs;

import java.util.List;

import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;
import com.cmrise.utils.CorrelacionColumnasInsertException;

public interface MrqsCorrelacionColumnasDao {	 
	   public long insert(List<MrqsCorrelacionColumnasDto> respuestas,List<MrqsCorrelacionColumnasRespuestasDto> preguntas,long lNumeroFta)
	   throws CorrelacionColumnasInsertException; 
	   public List<MrqsCorrelacionColumnasDto> findByFta(long pNumeroFta); 
	   public List<MrqsCorrelacionColumnasRespuestasDto> findRespuestasCorrectasByFta(long pNumeroFta);
	   public void delete(MrqsCorrelacionColumnasDto respuestas)  throws CorrelacionColumnasInsertException;
	   public void delete(MrqsCorrelacionColumnasRespuestasDto preguntas) throws CorrelacionColumnasInsertException;
	  
}
