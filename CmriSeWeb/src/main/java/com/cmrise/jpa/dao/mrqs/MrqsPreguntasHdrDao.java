package com.cmrise.jpa.dao.mrqs;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;

import java.util.List;

public interface MrqsPreguntasHdrDao {

	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public List<MrqsPreguntasHdrV1Dto> findAll();
	public MrqsPreguntasHdrV1Dto findByNumero(long pNumero);
	public MrqsPreguntasHdrDto copyPaste(long pNumero);
	public MrqsPreguntasHdrV2Dto findV2ByNumeroHdr(long pNumeroHdr); 
	public List<Object> findWithFilterExam(long pNumeroExamen); 
	 public List<MrqsPreguntasHdrV1Dto> findByTituloPregunta(String ptituloPregunta);
}
