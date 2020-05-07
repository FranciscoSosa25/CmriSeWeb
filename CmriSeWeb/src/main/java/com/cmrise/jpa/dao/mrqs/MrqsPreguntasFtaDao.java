package com.cmrise.jpa.dao.mrqs;

import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;

public interface MrqsPreguntasFtaDao {
	
	public long insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasFtaDto pMrqsPreguntasFtaDto);
	/** Metodo para validar si ya existe informacion en FTA por Header **/
	public long findNumeroFtaByNumeroHdr(long pNumeroHdr); 
	public MrqsPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta);
	public long copyPaste(long pNumero,MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	/** could not resolve property: numeroHdr of: com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto **/
	public MrqsPreguntasFtaDto findDtoByNumeroHdr(long pNumeroHdr); 
}
