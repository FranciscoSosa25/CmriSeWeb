package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.admin.AdmonCcCandidatosDao;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosDto;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosV1Dto;

@Stateless 
public class AdmonCcCandidatosLocalImpl implements AdmonCcCandidatosLocal {

	@Inject
	AdmonCcCandidatosDao  admonCcCandidatosDao;
	
	@Override
	public long insert(AdmonCcCandidatosDto pAdmonCcCandidatosDto) {
		return admonCcCandidatosDao.insert(pAdmonCcCandidatosDto);
	}

	@Override
	public List<AdmonCcCandidatosV1Dto> findByNumeroCcExamenWD(long pNumeroExamen) {
		return admonCcCandidatosDao.findByNumeroCcExamenWD(pNumeroExamen);
	}

	@Override
	public void delete(long pNumero) {
		admonCcCandidatosDao.delete(pNumero);
	}

	@Override
	public void deleteAll(long pNumeroCcExamen) {
		admonCcCandidatosDao.deleteAll(pNumeroCcExamen);
	}

}
