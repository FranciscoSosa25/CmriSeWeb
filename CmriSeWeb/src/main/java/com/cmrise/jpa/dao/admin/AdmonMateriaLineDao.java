package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonMateriaLineDto;

public interface AdmonMateriaLineDao {
	public long insert(AdmonMateriaLineDto pAdmonMateriaLineDto);
	public List<AdmonMateriaLineDto> findByNumeroMateria(long numeroAdmonMateria);
}
