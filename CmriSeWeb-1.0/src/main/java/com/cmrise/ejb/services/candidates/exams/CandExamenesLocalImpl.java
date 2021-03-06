package com.cmrise.ejb.services.candidates.exams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
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
	public List<CandExamenesV2> findByExamen(long pNumeroExamen, String pTipoExamen) {
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByExamen(pNumeroExamen, pTipoExamen); 
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = new CandExamenesV2(); 
			candExamenesV2.setNumero(candExamenesV2Dto.getNumero());
			candExamenesV2.setNumeroExamen(candExamenesV2Dto.getNumeroExamen());
			candExamenesV2.setNumeroUsuario(candExamenesV2Dto.getNumeroUsuario());
			candExamenesV2.setTipo(candExamenesV2Dto.getTipo());
			candExamenesV2.setMatricula(candExamenesV2Dto.getMatricula());
			candExamenesV2.setNombreUsuario(candExamenesV2Dto.getNombreUsuario());
			candExamenesV2.setApellidoPaterno(candExamenesV2Dto.getApellidoPaterno());
			candExamenesV2.setApellidoMaterno(candExamenesV2Dto.getApellidoMaterno());
			candExamenesV2.setDescripcionRol(candExamenesV2Dto.getDescripcionRol());
			candExamenesV2.setNombreCompletoUsuario(candExamenesV2Dto.getNombreCompletoUsuario());
			candExamenesV2.setCurp(candExamenesV2Dto.getCurp());
			candExamenesV2.setTotalPuntuacion(candExamenesV2Dto.getTotalPuntuacion());
			candExamenesV2.setEstatus(candExamenesV2Dto.getEstatus());
			candExamenesV2.setActualizadoPor(candExamenesV2Dto.getActualizadoPor());
			
			if(candExamenesV2Dto.getNombreActualizadoPor() != null)
				candExamenesV2.setNombreActualizadoPor(candExamenesV2Dto.getNombreActualizadoPor());
			else
				candExamenesV2.setNombreActualizadoPor("No disponible");
			
			candExamenesV2.setFechaActualizado(candExamenesV2Dto.getFechaActualizacion());
			candExamenesV2.setStringFechaAct();
			retval.add(candExamenesV2); 
		}
		return retval;
	}

	@Override
	public void delete(long pNumero) {
		candExamenesDao.delete(pNumero);
	}

	@Override
	public List<CandExamenesV2> findByUsuario(long pNumeroUsuario) {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByUsuario(pNumeroUsuario); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
		}
		return retval;
	}
	
	@Override
	public List<CandExamenesV2> findByUsuarioOnlyEfectiveDates(long pNumeroUsuario) {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByUsuarioOnlyEfectiveDates(pNumeroUsuario); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
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
		retval.setTitulo(pCandExamenesV2Dto.getTitulo());
		retval.setTiempoLimite(pCandExamenesV2Dto.getTiempoLimite());
		retval.setEstatus(pCandExamenesV2Dto.getEstatus());
		retval.setCurp(pCandExamenesV2Dto.getCurp());
		retval.setFechaEfectivaDesdeExamen(new java.util.Date(pCandExamenesV2Dto.getFechaEfectivaDesdeExamen().getTime()));
		retval.setFechaEfectivaHastaExamen(new java.util.Date(pCandExamenesV2Dto.getFechaEfectivaHastaExamen().getTime()));
		return retval; 
	}

	@Override
	public CandExamenesV1 findByNumero(long pNumero) {
		CandExamenesV1 retval = new CandExamenesV1(); 
		CandExamenesV1Dto candExamenesV1Dto = candExamenesDao.findByNumero(pNumero); 
		retval.setNumero(candExamenesV1Dto.getNumero());
		retval.setNumeroExamen(candExamenesV1Dto.getNumeroExamen());
		retval.setTipo(candExamenesV1Dto.getTipo());
		return retval;
	}

	@Override
	public List<CandExamenesV2> findByCURP(String pCurp,String pNombreUsuario, String pApellidoPaterno,String pApellidoMaterno) {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByCURP(pCurp,pNombreUsuario,pApellidoPaterno,pApellidoMaterno); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
		}
		return retval;
	}
	
	@Override
	public void updateEstatus(long pNumero, CandExamenesDto pCandExamenesDto) {
		 candExamenesDao.updateEstatus(pNumero, pCandExamenesDto);
	}
	
	@Override
	public List<CandExamenesV2> findCandidateByExam(String cCurp, String cNombre, String c_aPaterno, String c_aMaterno, String actPor, String fechaActu, long pNumeroExamen, String pTipoExamen) {
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findCandidateByExam(cCurp, cNombre, c_aPaterno, c_aMaterno, actPor, fechaActu, pNumeroExamen, pTipoExamen); 
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = new CandExamenesV2(); 
			candExamenesV2.setNumero(candExamenesV2Dto.getNumero());
			candExamenesV2.setNumeroExamen(candExamenesV2Dto.getNumeroExamen());
			candExamenesV2.setNumeroUsuario(candExamenesV2Dto.getNumeroUsuario());
			candExamenesV2.setTipo(candExamenesV2Dto.getTipo());
			candExamenesV2.setMatricula(candExamenesV2Dto.getMatricula());
			candExamenesV2.setNombreUsuario(candExamenesV2Dto.getNombreUsuario());
			candExamenesV2.setApellidoPaterno(candExamenesV2Dto.getApellidoPaterno());
			candExamenesV2.setApellidoMaterno(candExamenesV2Dto.getApellidoMaterno());
			candExamenesV2.setDescripcionRol(candExamenesV2Dto.getDescripcionRol());
			candExamenesV2.setNombreCompletoUsuario(candExamenesV2Dto.getNombreCompletoUsuario());
			candExamenesV2.setCurp(candExamenesV2Dto.getCurp());
			candExamenesV2.setEstatus(candExamenesV2Dto.getEstatus());
			candExamenesV2.setTotalPuntuacion(candExamenesV2Dto.getTotalPuntuacion());
			candExamenesV2.setActualizadoPor(candExamenesV2Dto.getActualizadoPor());
			
			if(candExamenesV2Dto.getNombreActualizadoPor() != null)
				candExamenesV2.setNombreActualizadoPor(candExamenesV2Dto.getNombreActualizadoPor());
			else
				candExamenesV2.setNombreActualizadoPor("No disponible");
			
			candExamenesV2.setFechaActualizado(candExamenesV2Dto.getFechaActualizacion());
			candExamenesV2.setStringFechaAct();
			
			retval.add(candExamenesV2); 
		}
		return retval;
	}
	
}
