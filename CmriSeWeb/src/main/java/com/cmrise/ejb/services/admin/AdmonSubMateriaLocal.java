package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.jpa.dto.admin.AdmonSubMateriaDto;

@Local
public interface AdmonSubMateriaLocal {
  public long insert(AdmonSubMateriaDto pAdmonSubMateriaDto);
  public List<AdmonSubMateria> findAll();
  public void insert(AdmonSubMateria i);
  public void update(AdmonSubMateria i);
  public void delete(long numero); 

}
