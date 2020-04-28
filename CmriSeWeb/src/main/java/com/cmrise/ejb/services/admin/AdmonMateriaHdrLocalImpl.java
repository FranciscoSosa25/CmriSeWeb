package com.cmrise.ejb.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.jpa.dao.admin.AdmonMateriaHdrDao;
import com.cmrise.jpa.dto.admin.AdmonMateriaHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class AdmonMateriaHdrLocalImpl implements AdmonMateriaHdrLocal {
	
	@Inject 
	AdmonMateriaHdrDao admonMateriaHdrDao; 
	
	@Override
	public long insert(AdmonMateriaHdrDto pAdmonMateriaDto) {
		return admonMateriaHdrDao.insert(pAdmonMateriaDto);
	}

	@Override
	public void update(AdmonMateriaHdr pAdmonMateria) {
		AdmonMateriaHdrDto admonMateriaHdrDto = admonMateriaHdrDao.findByNumero(pAdmonMateria.getNumero()); 
		admonMateriaHdrDto.setNombre(pAdmonMateria.getNombre());
		admonMateriaHdrDto.setFechaEfectivaDesde(new java.sql.Date(pAdmonMateria.getFechaEfectivaDesde().getTime()));
		if(null==pAdmonMateria.getFechaEfectivaHasta()) {
			admonMateriaHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonMateriaHdrDto.setFechaEfectivaHasta(new java.sql.Date(pAdmonMateria.getFechaEfectivaHasta().getTime()));
		}
		admonMateriaHdrDto.setActualizadoPor(pAdmonMateria.getActualizadoPor());
		admonMateriaHdrDto.setFechaActualizacion(new java.sql.Timestamp(pAdmonMateria.getFechaActualizacion().getTime()));
	}

	@Override
	public void insert(AdmonMateriaHdr pAdmonMateria) {
		AdmonMateriaHdrDto admonMateriaHdrDto = new AdmonMateriaHdrDto(); 
		
		admonMateriaHdrDto.setNombre(pAdmonMateria.getNombre());
		admonMateriaHdrDto.setFechaEfectivaDesde(new java.sql.Date(pAdmonMateria.getFechaEfectivaDesde().getTime()));
		if(null==pAdmonMateria.getFechaEfectivaHasta()) {
			admonMateriaHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonMateriaHdrDto.setFechaEfectivaHasta(new java.sql.Date(pAdmonMateria.getFechaEfectivaHasta().getTime()));
		}
		admonMateriaHdrDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pAdmonMateria.getFechaCreacion()));
		admonMateriaHdrDto.setActualizadoPor(pAdmonMateria.getActualizadoPor());
		admonMateriaHdrDto.setCreadoPor(pAdmonMateria.getCreadoPor());
		admonMateriaHdrDto.setActualizadoPor(pAdmonMateria.getActualizadoPor());
		admonMateriaHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonMateria.getFechaActualizacion()));
		
		admonMateriaHdrDao.insert(admonMateriaHdrDto); 
		pAdmonMateria.setNumero(admonMateriaHdrDto.getNumero());
		
	}

	@Override
	public List<AdmonMateriaHdr> findAll() {
		List<AdmonMateriaHdr> retval = new ArrayList<AdmonMateriaHdr>(); 
		List<AdmonMateriaHdrDto> listAdmonMateriaDto = admonMateriaHdrDao.findAll(); 
		for(AdmonMateriaHdrDto i:listAdmonMateriaDto) {
			AdmonMateriaHdr admonMateriaHdr = new AdmonMateriaHdr(); 
			admonMateriaHdr.setNumero(i.getNumero());
			admonMateriaHdr.setNombre(i.getNombre());
			admonMateriaHdr.setFechaEfectivaDesde(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaDesde()));
			if(Utilitarios.endOfTime.compareTo(i.getFechaEfectivaHasta())==0) {
			 admonMateriaHdr.setFechaEfectivaHasta(null);	
			}else {
			 admonMateriaHdr.setFechaEfectivaHasta(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaHasta()));
			}
			retval.add(admonMateriaHdr); 
		}
		return retval;
	}

	@Override
	public void delete(long pNumero) {
		admonMateriaHdrDao.delete(pNumero);
	}
	

}
