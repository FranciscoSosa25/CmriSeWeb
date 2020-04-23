package com.cmrise.ejb.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonMateria;
import com.cmrise.jpa.dao.admin.AdmonMateriaDao;
import com.cmrise.jpa.dto.admin.AdmonMateriaDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class AdmonMateriaLocalImpl implements AdmonMateriaLocal {
	
	@Inject 
	AdmonMateriaDao admonMateriaDao; 
	
	@Override
	public long insert(AdmonMateriaDto pAdmonMateriaDto) {
		return admonMateriaDao.insert(pAdmonMateriaDto);
	}

	@Override
	public void update(AdmonMateria pAdmonMateria) {
		AdmonMateriaDto admonMateriaDto = admonMateriaDao.findByNumero(pAdmonMateria.getNumero()); 
		admonMateriaDto.setNombre(pAdmonMateria.getNombre());
		admonMateriaDto.setFechaEfectivaDesde(new java.sql.Date(pAdmonMateria.getFechaEfectivaDesde().getTime()));
		if(null==pAdmonMateria.getFechaEfectivaHasta()) {
			admonMateriaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonMateriaDto.setFechaEfectivaHasta(new java.sql.Date(pAdmonMateria.getFechaEfectivaHasta().getTime()));
		}
		admonMateriaDto.setActualizadoPor(pAdmonMateria.getActualizadoPor());
		admonMateriaDto.setFechaActualizacion(new java.sql.Timestamp(pAdmonMateria.getFechaActualizacion().getTime()));
	}

	@Override
	public void insert(AdmonMateria pAdmonMateria) {
		AdmonMateriaDto admonMateriaDto = new AdmonMateriaDto(); 
		
		admonMateriaDto.setNombre(pAdmonMateria.getNombre());
		admonMateriaDto.setFechaEfectivaDesde(new java.sql.Date(pAdmonMateria.getFechaEfectivaDesde().getTime()));
		if(null==pAdmonMateria.getFechaEfectivaHasta()) {
			admonMateriaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonMateriaDto.setFechaEfectivaHasta(new java.sql.Date(pAdmonMateria.getFechaEfectivaHasta().getTime()));
		}
		admonMateriaDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pAdmonMateria.getFechaCreacion()));
		admonMateriaDto.setActualizadoPor(pAdmonMateria.getActualizadoPor());
		admonMateriaDto.setCreadoPor(pAdmonMateria.getCreadoPor());
		admonMateriaDto.setActualizadoPor(pAdmonMateria.getActualizadoPor());
		admonMateriaDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonMateria.getFechaActualizacion()));
		
		admonMateriaDao.insert(admonMateriaDto); 
		pAdmonMateria.setNumero(admonMateriaDto.getNumero());
		
	}

	@Override
	public List<AdmonMateria> findAll() {
		List<AdmonMateria> retval = new ArrayList<AdmonMateria>(); 
		List<AdmonMateriaDto> listAdmonMateriaDto = admonMateriaDao.findAll(); 
		for(AdmonMateriaDto i:listAdmonMateriaDto) {
			AdmonMateria admonMateria = new AdmonMateria(); 
			admonMateria.setNumero(i.getNumero());
			admonMateria.setNombre(i.getNombre());
			admonMateria.setFechaEfectivaDesde(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaDesde()));
			if(Utilitarios.endOfTime.compareTo(i.getFechaEfectivaHasta())==0) {
			 admonMateria.setFechaEfectivaHasta(null);	
			}else {
			 admonMateria.setFechaEfectivaHasta(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaHasta()));
			}
			retval.add(admonMateria); 
		}
		return retval;
	}

	@Override
	public void delete(long pNumero) {
		admonMateriaDao.delete(pNumero);
	}
	

}
