package com.cmrise.ejb.services.mrqs;

import javax.ejb.Local;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;

@Local
public interface MrqsPreguntasFtaLocal  {
	public long insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
	public long findNumeroFtaByNumeroHdr(long pNumeroHdr); 
	public MrqsPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta); 
	public long copyPaste(long pNumero,MrqsPreguntasHdrDto pMrqsPreguntasHdrDto); 
}
