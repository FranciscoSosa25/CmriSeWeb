package com.cmrise.jpa.dao.corecases.img;

import java.util.List;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;

public interface CcImagenesGrpDao {

	public long insert(CcImagenesGrpDto pCcImagenesGrpDto);
	
	public boolean delete(long pNumeroGrp);

	public List<CcImagenesGrpDto> findByFta(long pNumeroFta
			                                 ,String pSeccion
			                                 ); 
	
	public List<CcImagenesGrpDto> findByCcHDR(long pNumeroFta
            ,String pSeccion
            );

	public void update(CcImagenesGrpDto pCcImagenesGrpDto); 
	
}
