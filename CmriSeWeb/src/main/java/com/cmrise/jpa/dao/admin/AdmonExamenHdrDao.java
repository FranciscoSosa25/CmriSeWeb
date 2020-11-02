package com.cmrise.jpa.dao.admin;

import java.util.List;
import com.cmrise.jpa.dto.admin.AdmonExamenHdrDto;

public interface AdmonExamenHdrDao {
	public long insert(AdmonExamenHdrDto pAdmonExamenHdrDto);
	public List<AdmonExamenHdrDto> findAll();
	public AdmonExamenHdrDto findByNumero(long pNumero);
	public List<AdmonExamenHdrDto> findByTipo(String pTipo);
	
}
