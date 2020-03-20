package com.cmrise.jpa.dao.mrqs;

import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;

import java.util.List;

public interface MrqsPreguntasHdrDao {

	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public List<MrqsPreguntasHdrV1Dto> findAll();
	public MrqsPreguntasHdrV1Dto findByNumero(long pNumero);
	public MrqsPreguntasHdrDto copyPaste(long pNumero);
}
