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
	public List<MrqsCorrelacionColumnasDto> findByFta(long pNumeroFta) {
		
		return mrqsCorrelacionColumnas.findByFta(pNumeroFta);
	}
	@Override
	public List<MrqsCorrelacionColumnasRespuestasDto> findRespuestasCorrectasByFta(long pNumeroFta) {
		
		return mrqsCorrelacionColumnas.findRespuestasCorrectasByFta(pNumeroFta);
	}
	@Override
	public long insert(List<MrqsCorrelacionColumnasDto> item, List<MrqsCorrelacionColumnasRespuestasDto> respuestas,long lNumeroFta) throws CorrelacionColumnasInsertException{
			return mrqsCorrelacionColumnas.insert(item,respuestas,lNumeroFta);
	}
	@Override
	public void deleteColumna(MrqsCorrelacionColumnasDto item) throws CorrelacionColumnasInsertException {
		mrqsCorrelacionColumnas.delete(item);
	}
	@Override
	public void deleteColumna(MrqsCorrelacionColumnasRespuestasDto item) throws CorrelacionColumnasInsertException {
		mrqsCorrelacionColumnas.delete(item);
	}
	
	


}
