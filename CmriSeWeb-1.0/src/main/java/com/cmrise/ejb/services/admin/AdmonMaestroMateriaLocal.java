
package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Local
public interface AdmonMaestroMateriaLocal {

	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMateriaDto);
	

	public void deleteLogico(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto);
	
	public void activaRolUsuario(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto);
	
	public int validaMaestroMateria(long pNumeroUsuario, long pNumeroMateria);
	
	public int validaMaestroMateriaID(long pNumeroUsuario, long pNumeroMateria);
	
	public List<KeysDto> findKeysMaterias();
	
	public String[] findKeysMateriasOfMaestros(long usuario);
	
	public void delete(long usuario);

}