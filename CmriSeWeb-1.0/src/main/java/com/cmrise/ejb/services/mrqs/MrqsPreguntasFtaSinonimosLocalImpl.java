package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaSinonimosDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;
@Stateless 
public class MrqsPreguntasFtaSinonimosLocalImpl implements MrqsPreguntasFtaSinonimosLocal {
	@Inject
	private MrqsPreguntasFtaSinonimosDao mrqsPreguntasFtaSinonimosDao;
	@Override
	public long insert(List<MrqsPreguntasFtaSinonimos> sinonimos,long lNumeroFta,String texto) throws Exception {
		// TODO Auto-generated method stub
		return mrqsPreguntasFtaSinonimosDao.insert(sinonimos, lNumeroFta,texto);
	}

	@Override
	public List<MrqsPreguntasFtaSinonimos> findByFta(long pNumeroFta) {
		// TODO Auto-generated method stub
		return mrqsPreguntasFtaSinonimosDao.findByFta(pNumeroFta);
	}

	@Override
	public void deleteSinonimo(MrqsPreguntasFtaSinonimos item) throws Exception {
		// TODO Auto-generated method stub
		 mrqsPreguntasFtaSinonimosDao.delete(item);;
	}

}
