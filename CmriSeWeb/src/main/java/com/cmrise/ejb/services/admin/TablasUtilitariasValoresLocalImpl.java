package com.cmrise.ejb.services.admin; 

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import com.cmrise.jpa.dao.admin.TablasUtilitariasValoresDao;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;

@Stateless 
public class TablasUtilitariasValoresLocalImpl implements TablasUtilitariasValoresLocal{

	@Inject 
	TablasUtilitariasValoresDao  tablasUtilitariasValoresDao; 
	
	public void insert(TablasUtilitariasValoresDto pTablasUtilitariasValoresDto) {
		tablasUtilitariasValoresDao.insert(pTablasUtilitariasValoresDto);
	}

	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla) {
		return tablasUtilitariasValoresDao.findByTipoTabla(pTipoTabla);
	}

	public void update(long pNumero, TablasUtilitariasValoresDto pTablasUtilitariasValoresDto) {
		tablasUtilitariasValoresDao.update(pNumero, pTablasUtilitariasValoresDto);
	}

	@Override
	public void delete(long pNumero) {
		tablasUtilitariasValoresDao.delete(pNumero);
	}

}
