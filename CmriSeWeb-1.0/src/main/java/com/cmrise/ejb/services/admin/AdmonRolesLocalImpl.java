package com.cmrise.ejb.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.math.BigInteger;


import com.cmrise.jpa.dao.admin.AdmonRolesDao;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.admin.KeysRolesDto;

@Stateless 
public class AdmonRolesLocalImpl implements AdmonRolesLocal {
	
	@Inject 
	AdmonRolesDao admonRolesDao; 
	
	@Override
	public void insert(AdmonRolesDto pAdmonRolesDto) {
		admonRolesDao.insert(pAdmonRolesDto);
	}

	@Override
	public List<AdmonRolesDto> findAll() {
		return admonRolesDao.findAll();
	}
	
	@Override
	public List<AdmonRolesDto> findCand() {
		return admonRolesDao.findCand();
	}
	
	@Override
	public List<AdmonRolesDto> findNotCand() {
		return admonRolesDao.findNotCand();
	}
	
	@Override
	public List<KeysDto> findKeysCand() {
		return admonRolesDao.findKeysCand();
	}
	
	@Override
	public List<KeysDto> findKeysNotCand() {
		return admonRolesDao.findKeysNotCand(); 
	}

	@Override
	public void delete(long pNumero) {
		admonRolesDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, AdmonRolesDto pAdmonRolesDto) {
		admonRolesDao.update(pNumero, pAdmonRolesDto);	
	}

	@Override
	public List<KeysDto> findKeys() {
		return admonRolesDao.findKeys();
	}
	
	@Override
	public AdmonRolesDto findRole(long idRole) {
		return admonRolesDao.findRole(idRole);
	}
	
	@Override
	public String[] findKeysRolesUser(long idUser){
		String[] roles = new String[10];
		List<Object> listObjects = admonRolesDao.findKeysRolesUser(idUser);
		int index = 0;
		long nRol = 0;	 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				String rol = "";
				
				Object[] row = (Object[]) object;
				if(row[2] instanceof BigInteger) { /** [NUMERO] **/
					nRol = ((BigInteger)row[2]).longValue();
					rol = String.valueOf(nRol);
					roles[index] = rol;
					index++;
				}				
			}
		}
		return roles;
	}

	@Override
	public List<KeysRolesDto> findKeysRolesUsuario(long idUser){
		
		List<KeysRolesDto> roles = new ArrayList<KeysRolesDto>();
		List<Object> listObjects = admonRolesDao.findKeysRolesUser(idUser);
		
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				String namerol;
				KeysRolesDto rol = new KeysRolesDto();
				Object[] row = (Object[]) object;
				if(row[1] instanceof String) { /** [NOMBRE_ROL] **/
					namerol = (String)row[1];
					rol.setNombre(namerol.trim());
				}	
				if(row[2] instanceof BigInteger) { /** [NUMERO_ROL] **/
					rol.setNumero(((BigInteger)row[2]).longValue());
				}	
				roles.add(rol);
			}
		}
		return roles;
	}
}
