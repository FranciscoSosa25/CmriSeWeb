package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Local;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;

@Local
public interface MrqsPreguntasHdrLocal {
	
	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public void delete(long pNumero);
	public void update(long pNumero,MrqsPreguntasHdrDto pMrqsPreguntasHdrDto);
	public List<MrqsPreguntasHdrV1Dto> findAll();
	public MrqsPreguntasHdrV1Dto findByNumero(long pNumero);
	public MrqsPreguntasHdrDto copyPaste(long pNumero);
	public MrqsPreguntasHdrV2Dto findV2ByNumeroHdr(long pNumeroHdr); 
	public List<MrqsPreguntasHdrV1> findWithFilterExam(long pNumeroExamen);
	public List<MrqsPreguntasHdrV1Dto> findByTituloPregunta(String ptituloPregunta);
	public long insert(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1);
	public String delete(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1);
	public List<MrqsPreguntasHdrV1> findWithFilterExam(long pNumeroMrqsExamen
			                                         , long pAdmonExamen
			                                         , long pAdmonMateria
			                                         );
}
