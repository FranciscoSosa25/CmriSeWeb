package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;

@Local
public interface CcOpcionMultipleLocal {
	public long insert(CcOpcionMultipleDto pCcOpcionMultipleDto); 
	public List<CcOpcionMultipleDto> findByNumeroFta(long pNumeroFta); 
	public void update(long pNumero, CcOpcionMultipleDto pCcOpcionMultipleDto);
	public void delete(long pNumero); 
	public int correctOrWrongAnswer(long pNumero
            ,long pNumetoFta
            );	
	public int totalCorrectAnswers(long pNumeroFta);
	public List<CcOpcionMultipleDto> findByNumeroFtaShuffleOrder(long pNumeroFta
            ,boolean pShuffleOrder
            );
	public List<CcOpcionMultiple> findByNumeroFtaShuffleOrderOM(long pNumeroFta, boolean pShuffleOrder);
}
