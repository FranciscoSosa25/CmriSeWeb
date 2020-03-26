package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;

@Local
public interface MrqsOpcionMultipleLocal {

	public long insert(MrqsOpcionMultipleDto pMrqsOpcionMultipleDto); 
	public List<MrqsOpcionMultipleDto> findByNumeroFta(long pNumeroFta);
	public void update(long pNumero,MrqsOpcionMultipleDto pMrqsOpcionMultipleDto);
	public void delete(long pNumero); 
	public void deleteByNumeroFta(long pNumeroFta); 
	public void copyPaste(long pNumeroFtaOld
			             ,long longpNumeroFtaCopy
			             ); 
	public List<MrqsOpcionMultipleDto> findByNumeroFtaShuffleOrder(long pNumeroFta
										            ,boolean pShuffleOrder
										            );
	public int correctOrWrongAnswer(long pNumero
						            ,long pNumetoFta
						            );	
}
