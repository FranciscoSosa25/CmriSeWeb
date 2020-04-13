package com.cmrise.ejb.services.mrqs.img;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesGrpDto;

@Local
public interface MrqsImagenesGrpLocal {

	public long insert(MrqsImagenesGrpDto pMrqsImagenesGrpDto);

	public void insert(long pNumetoFta
			          ,MrqsImagenesGrp pMrqsImagenesGrp
			          );

	public List<MrqsImagenesGrp> findByFta(long   pNumeroFta
			                              ,String pSeccion
			                              ); 
	
}
