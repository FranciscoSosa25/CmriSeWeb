package com.cmrise.ejb.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonExamenLine;
import com.cmrise.jpa.dao.admin.AdmonExamenHdrDao;
import com.cmrise.jpa.dao.admin.AdmonExamenLineDao;
import com.cmrise.jpa.dto.admin.AdmonExamenHdrDto;
import com.cmrise.jpa.dto.admin.AdmonExamenLineDto;
import com.cmrise.jpa.dto.admin.AdmonMateriaDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class AdmonExamenHdrLocalImpl implements AdmonExamenHdrLocal {

	@Inject 
	AdmonExamenHdrDao  admonExamenHdrDao; 
	
	@Inject 
	AdmonExamenLineDao  admonExamenLineDao; 
	
	@Override
	public long insert(AdmonExamenHdrDto pAdmonExamenHdrDto) {
		return admonExamenHdrDao.insert(pAdmonExamenHdrDto);
	}

	@Override
	public long insert(AdmonExamenHdr pAdmonExamenHdr) {
		AdmonExamenHdrDto admonExamenHdrDto = new AdmonExamenHdrDto(); 
		
		admonExamenHdrDto.setNombre(pAdmonExamenHdr.getNombre());
		admonExamenHdrDto.setTipoExamen(pAdmonExamenHdr.getTipoExamen());
		admonExamenHdrDto.setFechaEfectivaDesde(new java.sql.Date(pAdmonExamenHdr.getFechaEfectivaDesde().getTime()));
		if(null==admonExamenHdrDto.getFechaEfectivaHasta()) {
			admonExamenHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonExamenHdrDto.setFechaEfectivaHasta(new java.sql.Date(pAdmonExamenHdr.getFechaEfectivaHasta().getTime()));
		}
		admonExamenHdrDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pAdmonExamenHdr.getFechaCreacion()));
		admonExamenHdrDto.setActualizadoPor(pAdmonExamenHdr.getActualizadoPor());
		admonExamenHdrDto.setCreadoPor(pAdmonExamenHdr.getCreadoPor());
		admonExamenHdrDto.setActualizadoPor(pAdmonExamenHdr.getActualizadoPor());
		admonExamenHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonExamenHdr.getFechaActualizacion()));
		
		long numeroAdmonExamenHdr =  admonExamenHdrDao.insert(admonExamenHdrDto); 
		pAdmonExamenHdr.setNumero(admonExamenHdrDto.getNumero());
		return numeroAdmonExamenHdr; 
	}

	@Override
	public List<AdmonExamenHdr> findAll() {
		List<AdmonExamenHdr> retval = new ArrayList<AdmonExamenHdr>();
		List<AdmonExamenHdrDto> listAdmonExamenHdrDto = admonExamenHdrDao.findAll(); 
		for(AdmonExamenHdrDto i:listAdmonExamenHdrDto) {
			AdmonExamenHdr admonExamenHdr = new AdmonExamenHdr(); 
			admonExamenHdr.setNumero(i.getNumero());
			admonExamenHdr.setNombre(i.getNombre());
			admonExamenHdr.setTipoExamen(i.getTipoExamen());
			admonExamenHdr.setFechaEfectivaDesde(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaDesde()));
			if(Utilitarios.endOfTime.compareTo(i.getFechaEfectivaHasta())==0) {
				admonExamenHdr.setFechaEfectivaHasta(null);
			}else {
				admonExamenHdr.setFechaEfectivaHasta(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaHasta()));
			}
			
			List<AdmonExamenLineDto> listAdmonExamenLineDto =  admonExamenLineDao.findByNumeroHdr(i.getNumero()); 
			if(null!=listAdmonExamenLineDto) {
				List<AdmonExamenLine> listAdmonExamenLine = new ArrayList<AdmonExamenLine>(); 
				for(AdmonExamenLineDto j:listAdmonExamenLineDto) {
					AdmonExamenLine admonExamenLine = new AdmonExamenLine(); 
					admonExamenLine.setNumero(j.getNumero());
					admonExamenLine.setNumeroExamen(j.getNumeroExamen());
					admonExamenLine.setNumeroMateria(j.getNumeroMateria());
					listAdmonExamenLine.add(admonExamenLine); 
				}
				admonExamenHdr.setExamenLines(listAdmonExamenLine);
			}
			
			retval.add(admonExamenHdr); 
		}
		return retval;
	}

	@Override
	public void update(AdmonExamenHdr pAdmonExamenHdr) {
		AdmonExamenHdrDto  admonExamenHdrDto = admonExamenHdrDao.findByNumero(pAdmonExamenHdr.getNumero()); 
		admonExamenHdrDto.setNombre(pAdmonExamenHdr.getNombre());
		admonExamenHdrDto.setTipoExamen(pAdmonExamenHdr.getTipoExamen());
		admonExamenHdrDto.setActualizadoPor(pAdmonExamenHdr.getActualizadoPor());
		admonExamenHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonExamenHdr.getFechaActualizacion()));
	}

	
}
