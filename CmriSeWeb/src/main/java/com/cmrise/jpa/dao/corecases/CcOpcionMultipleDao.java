package com.cmrise.jpa.dao.corecases;

import java.util.List;

import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;

public interface CcOpcionMultipleDao {

	public long insert(CcOpcionMultipleDto pCcOpcionMultipleDto); 
	public List<CcOpcionMultipleDto> findByNumeroFta(long pNumeroFta);
	public void update(long pNumero,CcOpcionMultipleDto pCcOpcionMultipleDto);
	
}
