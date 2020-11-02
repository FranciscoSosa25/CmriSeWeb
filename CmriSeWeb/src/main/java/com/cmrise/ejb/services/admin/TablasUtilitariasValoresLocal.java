package com.cmrise.ejb.services.admin; 

import java.util.List;
import javax.ejb.Local;

import com.cmrise.ejb.model.admin.TablasUtilitariasValores;
import com.cmrise.ejb.model.admin.TablasUtilitariasValoresV1;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;

@Local
public interface TablasUtilitariasValoresLocal {

	public void insert(TablasUtilitariasValoresDto pTablasUtilitariasValoresDto); 
	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla);
	public void update(long pNumero,TablasUtilitariasValoresDto pTablasUtilitariasValoresDto);
	public void delete(long pNumero);
	public List<TablasUtilitariasValoresV1> findAllByGroup();
	public List<TablasUtilitariasValores> findObjModByTipoTabla(String pTipoTabla);
	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla, String pTipoPregunta);
}

