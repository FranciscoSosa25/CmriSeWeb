package com.cmrise.jpa.dao.corecases.img;

import java.util.List;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;

public interface CcImagenesGrpDao {

	public long insert(CcImagenesGrpDto pCcImagenesGrpDto);

	public List<CcImagenesGrpDto> findByFta(long pNumeroFta
			                                 ,String pSeccion
			                                 ); 
	
}
