package com.cmrise.ejb.services.mrqs;

import javax.ejb.Local;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;

@Local
public interface MrqsPreguntasFtaLocal  {
	public void insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
}
