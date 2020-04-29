package com.cmrise.ejb.services.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.jpa.dao.admin.AdmonSubMateriaDao;
import com.cmrise.jpa.dto.admin.AdmonSubMateriaDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class AdmonSubMateriaLocalImpl implements AdmonSubMateriaLocal {

	@Inject 
	AdmonSubMateriaDao admonSubMateriaDao;
	
	@Override
	public long insert(AdmonSubMateriaDto pAdmonSubMateriaDto) {
		return admonSubMateriaDao.insert(pAdmonSubMateriaDto);
	}

	@Override
	public List<AdmonSubMateria> findAll() {
		List<AdmonSubMateria> retval = new ArrayList<AdmonSubMateria>(); 
		List<AdmonSubMateriaDto> listAdmonSubMateriaDto = admonSubMateriaDao.findAll(); 
		for(AdmonSubMateriaDto i:listAdmonSubMateriaDto) {
			AdmonSubMateria admonSubMateria = new AdmonSubMateria(); 
			admonSubMateria.setNumero(i.getNumero());
			admonSubMateria.setNombre(i.getNombre());
			admonSubMateria.setFechaEfectivaDesde(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaDesde()));
			if(Utilitarios.endOfTime.compareTo(i.getFechaEfectivaHasta())==0) {
				admonSubMateria.setFechaEfectivaHasta(null);	
			}else {
				admonSubMateria.setFechaEfectivaHasta(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaHasta()));
			}
			retval.add(admonSubMateria); 
		}
		return retval;
	}

	@Override
	public void insert(AdmonSubMateria pAdmonSubMateria) {
		AdmonSubMateriaDto admonSubMateriaDto = new AdmonSubMateriaDto(); 
		
		admonSubMateriaDto.setNombre(pAdmonSubMateria.getNombre());
		admonSubMateriaDto.setFechaEfectivaDesde(new java.sql.Date(pAdmonSubMateria.getFechaEfectivaDesde().getTime()));
		if(null==pAdmonSubMateria.getFechaEfectivaHasta()) {
			admonSubMateriaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonSubMateriaDto.setFechaEfectivaHasta(new java.sql.Date(pAdmonSubMateria.getFechaEfectivaHasta().getTime()));
		}
		admonSubMateriaDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pAdmonSubMateria.getFechaCreacion()));
		admonSubMateriaDto.setActualizadoPor(pAdmonSubMateria.getActualizadoPor());
		admonSubMateriaDto.setCreadoPor(pAdmonSubMateria.getCreadoPor());
		admonSubMateriaDto.setActualizadoPor(pAdmonSubMateria.getActualizadoPor());
		admonSubMateriaDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonSubMateria.getFechaActualizacion()));
		
		admonSubMateriaDao.insert(admonSubMateriaDto); 
		pAdmonSubMateria.setNumero(admonSubMateriaDto.getNumero());
		
	}

	@Override
	public void update(AdmonSubMateria pAdmonSubMateria) {
		AdmonSubMateriaDto admonSubMateriaDto = admonSubMateriaDao.findByNumero(pAdmonSubMateria.getNumero()); 
		admonSubMateriaDto.setNombre(pAdmonSubMateria.getNombre());
		admonSubMateriaDto.setFechaEfectivaDesde(Utilitarios.utilDateToSqlDate(pAdmonSubMateria.getFechaEfectivaDesde()));
		if(null==pAdmonSubMateria.getFechaEfectivaHasta()) {
			admonSubMateriaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonSubMateriaDto.setFechaEfectivaHasta(Utilitarios.utilDateToSqlDate(pAdmonSubMateria.getFechaEfectivaHasta()));
		}
		admonSubMateriaDto.setActualizadoPor(pAdmonSubMateria.getActualizadoPor());
		admonSubMateriaDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonSubMateria.getFechaActualizacion()));
	}

	@Override
	public void delete(long numero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AdmonSubMateria> findByNumeroMateria(long pAdmonMateria) {
		List<AdmonSubMateria> retval = new ArrayList<AdmonSubMateria>(); 
		List<Object> listObjects = admonSubMateriaDao.findByNumeroExamen(pAdmonMateria);
		for(Object i:listObjects) {
			if(i instanceof Object[]) {
				AdmonSubMateria admonSubMateria = new AdmonSubMateria();
				Object[] row = (Object[])i;
				if(row[0] instanceof BigInteger) { /** NUMERO **/
					admonSubMateria.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof String) { /** NOMBRE **/
					admonSubMateria.setNombre((String)row[1]);
				}
				retval.add(admonSubMateria);
			}
		}
		return retval;
	}
	

}
