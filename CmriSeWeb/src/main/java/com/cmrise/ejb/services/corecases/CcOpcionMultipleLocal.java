package com.cmrise.ejb.services.corecases;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;

@Local
public interface CcOpcionMultipleLocal {
	public long insert(CcOpcionMultipleDto pCcOpcionMultipleDto); 
	public List<CcOpcionMultipleDto> findByNumeroFta(long pNumeroFta); 
	public void update(long pNumero, CcOpcionMultipleDto pCcOpcionMultipleDto);
	public void delete(long pNumero); 
}
