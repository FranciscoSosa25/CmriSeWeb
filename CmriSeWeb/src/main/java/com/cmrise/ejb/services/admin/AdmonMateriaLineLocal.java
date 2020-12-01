package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonMateriaLine;
import com.cmrise.jpa.dto.admin.AdmonMateriaLineDto;

@Local
public interface AdmonMateriaLineLocal {
   public long insert(AdmonMateriaLineDto pAdmonMateriaLineDto);
   public List<AdmonMateriaLine> findByNumeroMateria(long numeroAdmonMateria);
   public long insert(AdmonMateriaLine i);
}
