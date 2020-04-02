package com.cmrise.ejb.services.candidates.exams;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.model.candidates.exams.Examinations;
import com.cmrise.jpa.dao.candidates.exams.CandExamenesDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;

@Stateless 
public class CandExamenesLocalImpl implements CandExamenesLocal {

	@Inject 
	CandExamenesDao  candExamenesDao; 
	
	@Override
	public long insert(CandExamenesDto pCandExamenesDto) {
		return candExamenesDao.insert(pCandExamenesDto);
	}

	@Override
	public List<CandExamenesV1> findByExamen(long pNumeroExamen, String pTipoExamen) {
		List<CandExamenesV1Dto> listCandExamenesV1Dto = candExamenesDao.findByExamen(pNumeroExamen, pTipoExamen); 
		List<CandExamenesV1> retval = new ArrayList<CandExamenesV1>(); 
		for(CandExamenesV1Dto CandExamenesV1Dto:listCandExamenesV1Dto) {
			CandExamenesV1 candExamenesV1 = new CandExamenesV1(); 
			candExamenesV1.setNumero(CandExamenesV1Dto.getNumero());
			candExamenesV1.setNumeroExamen(CandExamenesV1Dto.getNumeroExamen());
			candExamenesV1.setNumeroUsuario(CandExamenesV1Dto.getNumeroUsuario());
			candExamenesV1.setTipo(CandExamenesV1Dto.getTipo());
			candExamenesV1.setMatricula(CandExamenesV1Dto.getMatricula());
			candExamenesV1.setNombreUsuario(CandExamenesV1Dto.getNombreUsuario());
			candExamenesV1.setApellidoPaterno(CandExamenesV1Dto.getApellidoPaterno());
			candExamenesV1.setApellidoMaterno(CandExamenesV1Dto.getApellidoMaterno());
			candExamenesV1.setDescripcionRol(CandExamenesV1Dto.getDescripcionRol());
			candExamenesV1.setNombreCompletoUsuario(CandExamenesV1Dto.getNombreCompletoUsuario());
			retval.add(candExamenesV1); 
		}
		return retval;
	}

	@Override
	public void delete(long pNumero) {
		candExamenesDao.delete(pNumero);
	}

	@Override
	public List<Examinations> findByUsuario(long pNumeroUsuario) {
		List<Examinations> retval = new ArrayList<Examinations>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByUsuario(pNumeroUsuario); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			Examinations examinatios = new Examinations(); 
			examinatios.setNumeroMrqsExamen(candExamenesV2Dto.getNumeroExamen());
			examinatios.setTiempoLimite(candExamenesV2Dto.getTiempoLimite());
			examinatios.setTituloExamen(candExamenesV2Dto.getTitulo());
			retval.add(examinatios); 
		}
		return retval;
	}

	@Override
	public List<CandExamenesV2> findAll() {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findAll(); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
		}
		return retval;
	}

	public CandExamenesV2 dtoV2ToObjMod(CandExamenesV2Dto pCandExamenesV2Dto) {
		CandExamenesV2 retval = new CandExamenesV2(); 
		retval.setNumero(pCandExamenesV2Dto.getNumero());
		retval.setNumeroExamen(pCandExamenesV2Dto.getNumeroExamen());
		retval.setNumeroUsuario(pCandExamenesV2Dto.getNumeroUsuario());
		retval.setMatricula(pCandExamenesV2Dto.getMatricula());
		retval.setNombreCompletoUsuario(pCandExamenesV2Dto.getNombreCompletoUsuario());
		retval.setDescripcionRol(pCandExamenesV2Dto.getDescripcionRol());
		retval.setTipo(pCandExamenesV2Dto.getTipo());
		return retval; 
	}
	
}
