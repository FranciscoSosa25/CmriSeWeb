package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.KeysDto;

public interface AdmonMaestroMateriaDao {


	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMateriaDto);
	
	public void deleteLogico(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto);
	
	public void activaRolUsuario(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto);
	
	public int validaMaestroMateria(long pNumeroUsuario, long pNumeroMateria);
	
	public int validaMaestroMateriaID(long pNumeroUsuario, long pNumeroMateria);
	
	public List<KeysDto> findKeysMaterias();
	
	public List<Object> findKeysMateriasOfMaestros(long usuario);
	
	public void delete(long usuario);

}
