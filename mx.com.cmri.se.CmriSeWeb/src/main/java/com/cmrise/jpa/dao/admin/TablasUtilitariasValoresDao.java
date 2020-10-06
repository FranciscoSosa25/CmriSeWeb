package com.cmrise.jpa.dao.admin; 

import java.util.List;

import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;

public interface TablasUtilitariasValoresDao {

	public void insert(TablasUtilitariasValoresDto pTablasUtilitariasValoresDto);  
	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla); 
	public void update(long pNumero,TablasUtilitariasValoresDto pTablasUtilitariasValoresDto);
	public void delete(long pNumero);
	public List<Object> findAllByGroup();
	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla, String pTipoPregunta); 
	
}
