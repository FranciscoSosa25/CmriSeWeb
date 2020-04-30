package com.cmrise.jpa.dao.mrqs.img;

import java.util.List;

import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesGrpDto;

public interface MrqsImagenesGrpDao {

	public long insert(MrqsImagenesGrpDto pMrqsImagenesGrpDto);

	public List<MrqsImagenesGrpDto> findByFta(long pNumeroFta
			                                 ,String pSeccion
			                                 );

	public void deleteByNumeroFta(long pNumeroFta); 
	
}
