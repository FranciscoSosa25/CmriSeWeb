
package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Local
public interface AdmonMaestroMateriaLocal {

	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMateriaDto);
	
	public List<KeysDto> findKeysMaterias();
}