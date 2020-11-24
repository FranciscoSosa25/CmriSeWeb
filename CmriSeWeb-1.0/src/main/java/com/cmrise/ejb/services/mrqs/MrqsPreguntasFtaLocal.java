package com.cmrise.ejb.services.mrqs;

import javax.ejb.Local;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
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
	public long insert(MrqsPreguntasFtaV1 pMrqsPreguntasFtaV1);
	public MrqsPreguntasFtaV1 findObjModByNumeroFta(long lNumeroFta);
	public MrqsPreguntasFtaV1 findObjModByNumeroFta(long pNumeroFta
			                                      , String pTipoPregunta
			                                      );
	public void update(long pNumeroFta 
			          , MrqsPreguntasFtaV1 pMrqsPreguntasFtaV1
			         ); 
	
}
