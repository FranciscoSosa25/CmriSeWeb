package com.cmrise.ejb.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonMateriaLine;
import com.cmrise.jpa.dao.admin.AdmonMateriaLineDao;
import com.cmrise.jpa.dto.admin.AdmonMateriaLineDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class AdmonMateriaLineLocalImpl implements AdmonMateriaLineLocal {

	@Inject 
	AdmonMateriaLineDao admonMateriaLineDao; 
	
	@Override
	public long insert(AdmonMateriaLineDto pAdmonMateriaLineDto) {
		return admonMateriaLineDao.insert(pAdmonMateriaLineDto);
	}

	@Override
	public List<AdmonMateriaLine> findByNumeroMateria(long numeroAdmonMateria) {
		List<AdmonMateriaLine> revtal = new ArrayList<AdmonMateriaLine>(); 
		List<AdmonMateriaLineDto> listAdmonMateriaLineDto = admonMateriaLineDao.findByNumeroMateria(numeroAdmonMateria); 
		for(AdmonMateriaLineDto i:listAdmonMateriaLineDto) {
			AdmonMateriaLine admonMateriaLine = new AdmonMateriaLine();
			admonMateriaLine.setNumero(i.getNumero());
			admonMateriaLine.setNumeroMateriaHdr(i.getNumeroMateriaHdr());
			admonMateriaLine.setNumeroSubmateria(i.getNumeroSubmateria());
			admonMateriaLine.setFechaEfectivaDesde(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaDesde()));
			if(Utilitarios.endOfTime.compareTo(i.getFechaEfectivaHasta())==0) {
				admonMateriaLine.setFechaEfectivaHasta(null);
			}else {
				admonMateriaLine.setFechaEfectivaHasta(Utilitarios.sqlDateToUtilDate(i.getFechaEfectivaHasta()));			
			}
			revtal.add(admonMateriaLine); 
		}
		return revtal;
	}

	@Override
	public long insert(AdmonMateriaLine pAdmonMateriaLine) {
		AdmonMateriaLineDto admonMateriaLineDto = new AdmonMateriaLineDto(); 
		System.out.println("pAdmonMateriaLine.getNumeroExamen():"+pAdmonMateriaLine.getNumeroMateriaHdr());
		admonMateriaLineDto.setNumeroMateriaHdr(pAdmonMateriaLine.getNumeroMateriaHdr());
		admonMateriaLineDto.setNumeroSubmateria(pAdmonMateriaLine.getNumeroSubmateria());
		admonMateriaLineDto.setFechaEfectivaDesde(Utilitarios.utilDateToSqlDate(pAdmonMateriaLine.getFechaEfectivaDesde()));
		if(null==pAdmonMateriaLine.getFechaEfectivaHasta()) {
			admonMateriaLineDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}else {
			admonMateriaLineDto.setFechaEfectivaHasta(Utilitarios.utilDateToSqlDate(pAdmonMateriaLine.getFechaEfectivaHasta()));
		}
		admonMateriaLineDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pAdmonMateriaLine.getFechaCreacion()));
		admonMateriaLineDto.setActualizadoPor(pAdmonMateriaLine.getActualizadoPor());
		admonMateriaLineDto.setCreadoPor(pAdmonMateriaLine.getCreadoPor());
		admonMateriaLineDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pAdmonMateriaLine.getFechaActualizacion()));
		long numeroAdmonExamenLine =  admonMateriaLineDao.insert(admonMateriaLineDto); 
		pAdmonMateriaLine.setNumero(admonMateriaLineDto.getNumero());
		
		return admonMateriaLineDto.getNumero();
	}
	

}
