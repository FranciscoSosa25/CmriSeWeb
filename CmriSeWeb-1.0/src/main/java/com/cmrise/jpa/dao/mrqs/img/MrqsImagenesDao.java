package com.cmrise.jpa.dao.mrqs.img;

import java.util.List;

import com.cmrise.ejb.model.mrqs.img.MrqsImagenes;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesDto;

public interface MrqsImagenesDao {

	public long insert(MrqsImagenesDto pMrqsImagenesDto);

	public long insert(long numeroImagenesGrp
			         , MrqsImagenes mrqsImagenes);

	public List<MrqsImagenesDto> findByGrp(long pNumeroGrp); 
	
	public long eliminar(long numero,long pNumeroGrp) throws Exception;
	
}
