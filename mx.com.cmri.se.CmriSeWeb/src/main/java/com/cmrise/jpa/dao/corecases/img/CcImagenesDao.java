package com.cmrise.jpa.dao.corecases.img;

import java.util.List;

import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.jpa.dto.corecases.img.CcImagenesDto;

public interface CcImagenesDao {

	public long insert(CcImagenesDto pCcImagenesDto);

	public void insert(long pNumeroImagenesGrp
			         , CcImagenes pCcImagenes);

	public List<CcImagenesDto> findByGrp(long pNumeroGrp); 
	
}
