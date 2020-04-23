package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.jpa.dto.admin.AdmonExamenHdrDto;

@Local
public interface AdmonExamenHdrLocal {

	public long insert(AdmonExamenHdrDto pAdmonExamenHdrDto);
	public long insert(AdmonExamenHdr i);
	public List<AdmonExamenHdr> findAll();
	public void update(AdmonExamenHdr i);
	
}
