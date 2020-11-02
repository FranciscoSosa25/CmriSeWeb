package com.cmrise.jpa.dao.mrqs;

import java.util.List;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;

public interface MrqsOpcionMultipleDao {
	public long insert(MrqsOpcionMultipleDto pMrqsOpcionMultipleDto); 
	public List<MrqsOpcionMultipleDto> findByNumeroFta(long pNumeroFta);
	public void update(long pNumero,MrqsOpcionMultipleDto pMrqsOpcionMultipleDto);
	public void delete(long pNumero);
	public void deleteByNumeroFta(long pNumeroFta); 
	public void copyPaste(long pNumeroFtaOld,long longpNumeroFtaCopy); 
	public List<Object> findByNumeroFtaShuffleOrder(long pNumeroFta
                                                   ,boolean pShuffleOrder
                                                   );
	public int correctOrWrongAnswer(long pNumero
			                       ,long pNumetoFta
			                       );
	public int totalCorrectAnswers(long pNumeroFta);
}
