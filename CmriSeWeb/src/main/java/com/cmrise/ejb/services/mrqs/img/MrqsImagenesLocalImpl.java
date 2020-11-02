package com.cmrise.ejb.services.mrqs.img;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesDao;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesDto;

@Stateless
public class MrqsImagenesLocalImpl implements MrqsImagenesLocal {

	@Inject 
	MrqsImagenesDao mrqsImagenesDao; 
	
	@Override
	public long insert(MrqsImagenesDto pMrqsImagenesDto) {
		return mrqsImagenesDao.insert(pMrqsImagenesDto);
	}

}
