package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;

import com.cmrise.jpa.dao.mrqs.MrqsCorrelacionColumnasDao;
import com.cmrise.jpa.dao.mrqs.MrqsListasPalabrasDao;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.utils.CorrelacionColumnasInsertException;
@Stateless 
public class MrqsCorrelacionColumnasLocalImpl implements  MrqsCorrelacionColumnasLocal{
	@Inject
	private MrqsCorrelacionColumnasDao mrqsCorrelacionColumnas;
	
	

	@Override
	public void update(long pNumero, MrqsCorrelacionColumnasDto item) {
		mrqsCorrelacionColumnas.update(pNumero, item);
		
	}

	@Override
	public List<MrqsCorrelacionColumnasDto> findByFta(long pNumeroFta) {
		
		return mrqsCorrelacionColumnas.findByFta(pNumeroFta);
	}
	@Override
	public List<MrqsCorrelacionColumnasRespuestasDto> findRespuestasCorrectasByFta(long pNumeroFta) {
		
		return mrqsCorrelacionColumnas.findRespuestasCorrectasByFta(pNumeroFta);
	}
	@Override
	public void delete(long pNumero) {
		mrqsCorrelacionColumnas.delete(pNumero);
	}

	


	@Override
	public long insert(List<MrqsCorrelacionColumnasDto> item, List<MrqsCorrelacionColumnasRespuestasDto> respuestas,long lNumeroFta) throws CorrelacionColumnasInsertException{
		
		
			return mrqsCorrelacionColumnas.insert(item,respuestas,lNumeroFta);
		
	}


}
