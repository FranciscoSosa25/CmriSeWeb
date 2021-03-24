package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.KeysDto;

public interface AdmonRolesDao {
  public void insert(AdmonRolesDto pAdmonRolesDto);	
  public List<AdmonRolesDto> findAll();
  public List<AdmonRolesDto> findCand();
  public List<AdmonRolesDto> findNotCand();
  public void delete(long pNumero); 
  public void update(long pNumero,AdmonRolesDto pAdmonRolesDto);
  public List<KeysDto> findKeys();
  public List<KeysDto> findKeysCand();
  public List<KeysDto> findKeysNotCand();
  public AdmonRolesDto findRole(long idRole);
  public List<Object> findKeysRolesUser(long idUser);
}
