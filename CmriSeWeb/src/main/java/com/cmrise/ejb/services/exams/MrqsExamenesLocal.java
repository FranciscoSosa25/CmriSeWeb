package com.cmrise.ejb.services.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.exams.MrqsExamenesV1Dto;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;

@Local
public interface MrqsExamenesLocal {

	public long insert(MrqsExamenesDto pMrqsExamenesDto);
	public List<MrqsExamenesDto> findAll();
	public List<MrqsExamenesV1Dto> findAllWD();
	public void delete(long pNumero);
	public MrqsExamenesDto findById(long pNumero); 
	public void update(long pNumero,MrqsExamenesDto pMrqsExamenesDto);
	public MrqsExamenes findByIdWD(long pNumero);
	public MrqsExamenes findObjMod(long pNumero);
	public long insert(MrqsExamenes pMrqsExamenes);
	public MrqsExamenes findByNumeroWD(long pNumero);
	public void update(MrqsExamenes mrqsExamenesForUpdate); 
	
}
