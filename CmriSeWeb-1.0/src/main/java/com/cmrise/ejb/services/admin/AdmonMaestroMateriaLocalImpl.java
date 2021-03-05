
package com.cmrise.ejb.services.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonMaestroMateriaDao;
import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class AdmonMaestroMateriaLocalImpl implements AdmonMaestroMateriaLocal {
	
	@Inject 
	AdmonMaestroMateriaDao admonMaestroMateriaDao; 
	
	@Override
	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMateriaDto) {
		admonMaestroMateriaDao.insert(pAdmonMaestroMateriaDto);
	}
	
	@Override
	public List<KeysDto> findKeysMaterias() {
		return admonMaestroMateriaDao.findKeysMaterias();
	}

}
