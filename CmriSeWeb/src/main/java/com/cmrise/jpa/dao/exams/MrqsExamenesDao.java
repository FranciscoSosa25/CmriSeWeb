package com.cmrise.jpa.dao.exams;

import java.util.List;

import com.cmrise.jpa.dto.exams.MrqsExamenesV1Dto;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;

public interface MrqsExamenesDao {

	public long insert(MrqsExamenesDto pMrqsExamenesDto);
	public List<MrqsExamenesDto> findAll();
	public List<MrqsExamenesV1Dto> findAllWD();
	public void delete(long pNumero);
	public MrqsExamenesDto findById(long pNumero); 
	public void update(long pNumero,MrqsExamenesDto pMrqsExamenesDto);
	
}
