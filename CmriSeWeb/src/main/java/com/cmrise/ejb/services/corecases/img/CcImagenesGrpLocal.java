package com.cmrise.ejb.services.corecases.img;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;

@Local
public interface CcImagenesGrpLocal {
	public long insert(CcImagenesGrpDto pCcImagenesGrpDto);

	public void insert(long pNumetoFta
			          ,CcImagenesGrp pCcImagenesGrp
			          );

	public List<CcImagenesGrp> findByFta(long   pNumeroFta
			                              ,String pSeccion
			                              ); 
}
