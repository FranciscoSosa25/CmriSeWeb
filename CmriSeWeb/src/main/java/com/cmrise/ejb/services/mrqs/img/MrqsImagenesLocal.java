package com.cmrise.ejb.services.mrqs.img;

import javax.ejb.Local;

import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesDto;

@Local
public interface MrqsImagenesLocal {

	public long insert(MrqsImagenesDto pMrqsImagenesDto); 
	
}
