
package com.cmrise.ejb.services.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.admin.AdmonMaestroMateriaDao;
import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Stateless 
public class AdmonMaestroMateriaLocalImpl implements AdmonMaestroMateriaLocal {
	
	@Inject 
	AdmonMaestroMateriaDao admonMaestroMateriaDao; 
	
	@Override
	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMateriaDto) {
		admonMaestroMateriaDao.insert(pAdmonMaestroMateriaDto);
	}
	
	@Override
	public void deleteLogico(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto) {
		admonMaestroMateriaDao.deleteLogico(pNumero, pAdmonMaestroMateriaDto);
	}
	
	@Override
	public void activaRolUsuario(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto) {
		admonMaestroMateriaDao.activaRolUsuario(pNumero, pAdmonMaestroMateriaDto);
	}
	
	@Override
	public int validaMaestroMateria(long pNumeroUsuario, long pNumeroMateria) {
		return admonMaestroMateriaDao.validaMaestroMateria(pNumeroUsuario, pNumeroMateria);
	}
	
	@Override
	public int validaMaestroMateriaID(long pNumeroUsuario, long pNumeroMateria) {
		return admonMaestroMateriaDao.validaMaestroMateriaID(pNumeroUsuario, pNumeroMateria);
	}
	
	@Override
	public List<KeysDto> findKeysMaterias() {
		return admonMaestroMateriaDao.findKeysMaterias();
	}
	
	@Override
	public String[] findKeysMateriasOfMaestros(long idUser){
		String[] materias = new String[10];
		List<Object> listObjects = admonMaestroMateriaDao.findKeysMateriasOfMaestros(idUser);
		int index = 0;
		long nMat = 0;	 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				String materia = "";
				
				Object[] row = (Object[]) object;
				if(row[2] instanceof BigInteger) { /** [NUMERO] **/
					nMat = ((BigInteger)row[2]).longValue();
					materia = String.valueOf(nMat);
					materias[index] = materia;
					index++;
				}				
			}
		}
		return materias;
	}
	
	public void delete(long usuario) {
		admonMaestroMateriaDao.delete(usuario);
	}
}
