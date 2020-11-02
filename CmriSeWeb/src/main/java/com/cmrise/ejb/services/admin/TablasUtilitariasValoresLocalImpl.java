package com.cmrise.ejb.services.admin; 

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.TablasUtilitariasValores;
import com.cmrise.ejb.model.admin.TablasUtilitariasValoresV1;
import com.cmrise.jpa.dao.admin.TablasUtilitariasValoresDao;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;
import com.cmrise.utils.Utilitarios;

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

	@Override
	public List<TablasUtilitariasValoresV1> findAllByGroup() {
		List<TablasUtilitariasValoresV1> retval = new ArrayList<TablasUtilitariasValoresV1>(); 
		List<Object> listObjects = tablasUtilitariasValoresDao.findAllByGroup(); 
		for(Object i:listObjects) {
			if(i instanceof Object[]) {
				TablasUtilitariasValoresV1 tablasUtilitariasValoresV1 = new TablasUtilitariasValoresV1(); 
				Object[] row = (Object[])i;
				if(row[0] instanceof String) {
					tablasUtilitariasValoresV1.setTipoTabla((String)row[0]);
				}
				if(row[1] instanceof Integer) {
					tablasUtilitariasValoresV1.setNumeroRegistros(((Integer)row[1]).intValue());
				}
				retval.add(tablasUtilitariasValoresV1); 
			}
		}
		return retval;
	}

	@Override
	public List<TablasUtilitariasValores> findObjModByTipoTabla(String pTipoTabla) {
		List<TablasUtilitariasValores> retval = new ArrayList<TablasUtilitariasValores>(); 
		List<TablasUtilitariasValoresDto> listTablasUtilitariasValoresDto = tablasUtilitariasValoresDao.findByTipoTabla(pTipoTabla);
		for(TablasUtilitariasValoresDto i:listTablasUtilitariasValoresDto) {
            TablasUtilitariasValores tablasUtilitariasValores = new TablasUtilitariasValores(); 
			
			tablasUtilitariasValores.setNumero(i.getNumero());
			tablasUtilitariasValores.setTipoTabla(i.getTipoTabla());
			tablasUtilitariasValores.setCodigoTabla(i.getCodigoTabla());
			tablasUtilitariasValores.setSignificado(i.getSignificado());
			tablasUtilitariasValores.setDescripcion(i.getDescripcion());
			tablasUtilitariasValores.setEstado(i.getEstado());
			tablasUtilitariasValores.setFechaEfectivaDesde(i.getFechaEfectivaDesde());
			java.util.Date utilFechaHasta = new java.util.Date(i.getFechaEfectivaHasta().getTime());
			if(null!=utilFechaHasta) {
				if(0==utilFechaHasta.compareTo(Utilitarios.endOfTime)) {
					tablasUtilitariasValores.setFechaEfectivaHasta(null);
				}else {
					tablasUtilitariasValores.setFechaEfectivaHasta(i.getFechaEfectivaHasta());
				}
			}
			tablasUtilitariasValores.setTipoTabla(i.getTipoTabla());
			retval.add(tablasUtilitariasValores);
		}
		return retval;
	}

	@Override
	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla, String pTipoPregunta) {
		return tablasUtilitariasValoresDao.findByTipoTabla(pTipoTabla,pTipoPregunta);
	}

}
