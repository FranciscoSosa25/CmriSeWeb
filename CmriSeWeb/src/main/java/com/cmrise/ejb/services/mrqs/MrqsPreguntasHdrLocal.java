package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasHdrDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;

@Local
public interface MrqsPreguntasHdrLocal {
	
	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public List<MrqsPreguntasHdrV1Dto> findAll();

}
