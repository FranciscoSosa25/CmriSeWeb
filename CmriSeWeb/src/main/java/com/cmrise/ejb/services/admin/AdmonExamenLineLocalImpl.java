package com.cmrise.ejb.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonExamenLine;
import com.cmrise.jpa.dao.admin.AdmonExamenLineDao;
import com.cmrise.jpa.dto.admin.AdmonExamenLineDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class AdmonExamenLineLocalImpl implements AdmonExamenLineLocal {

	@Inject 
	AdmonExamenLineDao  admonExamenLineDao; 
	
	@Override
	public long insert(AdmonExamenLineDto pAdmonExamenLineDto) {
		return admonExamenLineDao.insert(pAdmonExamenLineDto);
	}

	@Override
	public long insert(AdmonExamenLine pAdmonExamenLine) {
		AdmonExamenLineDto admonExamenLineDto = new AdmonExamenLineDto(); 
		System.out.println("pAdmonExamenLine.getNumeroExamen():"+pAdmonExamenLine.getNumeroExamen());
		admonExamenLineDto.setNumeroExamen(pAdmonExamenLine.getNumeroExamen());
		admonExamenLineDto.setNumeroMateria(pAdmonExamenLine.getNumeroMateria());
		admonExamenLineDto.setFechaEfectivaDesde(Utilitarios.utilDateToSqlDate(pAdmonExamenLine.getFechaEfectivaDesde()));
		if(null==pAdmonExamenLine.getFechaEfectivaHasta()) {
			admonExamenLineDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonExamenLineDto.setFechaEfectivaHasta(Utilitarios.utilDateToSqlDate(pAdmonExamenLine.getFechaEfectivaHasta()));
		}
		admonExamenLineDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pAdmonExamenLine.getFechaCreacion()));
		admonExamenLineDto.setActualizadoPor(pAdmonExamenLine.getActualizadoPor());
		admonExamenLineDto.setCreadoPor(pAdmonExamenLine.getCreadoPor());
		admonExamenLineDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonExamenLine.getFechaActualizacion()));
		long numeroAdmonExamenLine =  admonExamenLineDao.insert(admonExamenLineDto); 
		pAdmonExamenLine.setNumero(admonExamenLineDto.getNumero());
		
		return admonExamenLineDto.getNumero();
	}

	@Override
	public List<AdmonExamenLine> findByNumeroExamen(long pNumeroAdmonExamen) {
		List<AdmonExamenLine> retval = new ArrayList<AdmonExamenLine>(); 
		List<AdmonExamenLineDto> listAdmonExamenLineDto =  admonExamenLineDao.findByNumeroHdr(pNumeroAdmonExamen); 
		if(null!=listAdmonExamenLineDto) {
			for(AdmonExamenLineDto j:listAdmonExamenLineDto) {
				AdmonExamenLine admonExamenLine = new AdmonExamenLine(); 
				admonExamenLine.setNumero(j.getNumero());
				admonExamenLine.setNumeroExamen(j.getNumeroExamen());
				admonExamenLine.setNumeroMateria(j.getNumeroMateria());
				admonExamenLine.setFechaEfectivaDesde(Utilitarios.utilDateToSqlDate(j.getFechaEfectivaDesde()));
				if(Utilitarios.endOfTime.compareTo(j.getFechaEfectivaHasta())==0) {
					admonExamenLine.setFechaEfectivaHasta(null); 
				}else {
					admonExamenLine.setFechaEfectivaHasta(Utilitarios.utilDateToSqlDate(j.getFechaEfectivaHasta()));	
				}
				retval.add(admonExamenLine); 
			}
	  }
    return retval;
  }

	@Override
	public void delete(long pNumero) {
		admonExamenLineDao.delete(pNumero); 
	}

}