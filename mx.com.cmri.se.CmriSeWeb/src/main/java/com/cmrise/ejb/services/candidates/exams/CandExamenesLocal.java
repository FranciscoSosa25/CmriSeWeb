package com.cmrise.ejb.services.candidates.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;

@Local
public interface CandExamenesLocal {

	public long insert(CandExamenesDto pCandExamenesDto); 
	public List<CandExamenesV1> findByExamen(long pNumeroExamen
								            ,String pTipoExamen
								            ); 
	public void delete(long pNumero); 
	public List<CandExamenesV2> findByUsuario(long pNumeroUsuario); 
	public List<CandExamenesV2> findAll(); 
    public CandExamenesV1 findByNumero(long pNumero); 
    public List<CandExamenesV2> findByCURP(String pCurp,
    		                               String pNombreUsuario, 
    		                               String pApellidoPaterno,
    		                                String pApellidoMaterno);
    public void updateEstatus(long pNumero
    		                 ,CandExamenesDto pCandExamenesDto);
}

