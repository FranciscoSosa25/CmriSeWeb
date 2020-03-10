package com.cmrise.ejb.services.admin; 

import java.util.List;
import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;

@Local
public interface TablasUtilitariasValoresLocal {

	public void insert(TablasUtilitariasValoresDto pTablasUtilitariasValoresDto); 
	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla);
	public void update(long pNumero,TablasUtilitariasValoresDto pTablasUtilitariasValoresDto);
	public void delete(long pNumero);
}

