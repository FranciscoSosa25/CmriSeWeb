package com.cmrise.jpa.dao.mrqs;

import java.util.List;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;

public interface MrqsOpcionMultipleDao {
	public long insert(MrqsOpcionMultipleDto pMrqsOpcionMultipleDto); 
	public List<MrqsOpcionMultipleDto> findByNumeroFta(long pNumeroFta);
	public void update(long pNumero,MrqsOpcionMultipleDto pMrqsOpcionMultipleDto);
}
