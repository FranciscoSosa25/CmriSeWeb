package com.cmrise.ejb.services.corecases.img;

import javax.ejb.Local;

import com.cmrise.jpa.dto.corecases.img.CcImagenesDto;


@Local
public interface CcImagenesLocal {

	public long insert(CcImagenesDto pCcImagenesDto); 
	
}
