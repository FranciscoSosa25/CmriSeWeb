package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonRolesDto;

public interface AdmonRolesDao {
  public void insert(AdmonRolesDto pAdmonRolesDto);	
  public List<AdmonRolesDto> findAll();
  public void delete(long pNumero); 
  public void update(long pNumero,AdmonRolesDto pAdmonRolesDto);
}
