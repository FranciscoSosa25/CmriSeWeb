package com.cmrise.jpa.dao.mrqs;

import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;

public interface MrqsPreguntasFtaDao {
	
	public void insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
	/** Metodo para validar si ya existe informacion en FTA por Header **/
	public long findNumeroFtaByNumeroHdr(long pNumeroHdr); 
	public MrqsPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta);
	
}
