package com.cmrise.ejb.services.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
import com.cmrise.jpa.dao.admin.AdmonUsuariosRolesDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

@Stateless
public class AdmonUsuariosRolesLocalImpl implements AdmonUsuariosRolesLocal {

	@Inject 
	AdmonUsuariosRolesDao admonUsuariosRolesDao;
	
	@Override
	public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.insert(pAdmonUsuariosRolesDto);
	}

	@Override
	public void delete(long pNumero) {
		admonUsuariosRolesDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.update(pNumero, pAdmonUsuariosRolesDto);
	}

	@Override
	public List<AdmonUsuariosRolesV1Dto> findAll() {
		return admonUsuariosRolesDao.findAll();
	}

	@Override
	public int validaUsuarioRol(long pNumeroUsuario, long pNumeroRol) {
		return admonUsuariosRolesDao.validaUsuarioRol(pNumeroUsuario
				                                     ,pNumeroRol
				                                     );
	}

	@Override
	public int loginUsuarioRol(String pCurp
			                 , String pRol
			                 , String pContrasenia) {
		return admonUsuariosRolesDao.loginUsuarioRol(pCurp
				                                   , pRol
				                                   , pContrasenia);
	}

	@Override
	public List<AdmonUsuariosRolesV1> findWithFilterExam(long pNumeroExamen
			                                           , String pTipoExamen) {
		List<AdmonUsuariosRolesV1> retval = new ArrayList<AdmonUsuariosRolesV1>(); 
		List<Object> listObjects = admonUsuariosRolesDao.findWithFilterExam(pNumeroExamen
				                                                          , pTipoExamen); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				AdmonUsuariosRolesV1 admonUsuariosRolesV1 = new AdmonUsuariosRolesV1(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** [NUMERO] **/
					admonUsuariosRolesV1.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof BigInteger) { /** [NUMERO_USUARIO] **/
					admonUsuariosRolesV1.setNumeroUsuario(((BigInteger)row[1]).longValue());
				}
				if(row[2] instanceof String) { /** [MATRICULA] **/
					admonUsuariosRolesV1.setMatricula((String)row[2]);
				}
				if(row[3] instanceof String) { /** [NOMBRE_USUAURIO] **/
					admonUsuariosRolesV1.setNombreUsuario((String)row[3]);
				}
				if(row[4] instanceof String) { /** [NOMBRE_COMPLETO_USUARIO] **/
					admonUsuariosRolesV1.setNombreCompletoUsuario((String)row[4]);
				}
				if(row[5] instanceof BigInteger) { /** [NUMERO_ROL] **/
					admonUsuariosRolesV1.setNumeroRol(((BigInteger)row[5]).longValue());
				}
				if(row[6] instanceof String) { /** [NOMBRE_ROL] **/
					admonUsuariosRolesV1.setNombreRol((String)row[6]);
				}
				if(row[7] instanceof String) { /** [DESCRIPCION_ROL] **/
					admonUsuariosRolesV1.setDescripcionRol((String)row[7]);
				}
				if(row[8] instanceof String) { /** [APELLIDO_PATERNO] **/
					admonUsuariosRolesV1.setApellidoPaterno((String)row[8]);
				}
				if(row[9] instanceof String) { /** [APELLIDO_MATERNO] **/
					admonUsuariosRolesV1.setApellidoMaterno((String)row[9]);
				}
				if(row[10] instanceof String) { /** [CORREO_ELECTRONICO] **/
					admonUsuariosRolesV1.setCorreoElectronico((String)row[10]);
				}
				if(row[11] instanceof String) { /** [CONTRASENIA] **/
					admonUsuariosRolesV1.setContrasenia((String)row[11]);
				}
				if(row[12] instanceof String) { /** [CURP] **/
					admonUsuariosRolesV1.setCurp((String)row[12]);
				}
				retval.add(admonUsuariosRolesV1); 
			}
		}
		return retval;
	}

	@Override
	public AdmonUsuariosRolesV1Dto findLoginUsusarioRol(String pCurp
			                                          , String pRol
			                                          , String pContrasenia
			                                          ) {
		return admonUsuariosRolesDao.findLoginUsusarioRol(pCurp
				                                        , pRol
				                                        , pContrasenia);
	}

}
