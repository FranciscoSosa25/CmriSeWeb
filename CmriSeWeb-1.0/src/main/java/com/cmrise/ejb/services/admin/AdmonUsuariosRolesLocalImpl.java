package com.cmrise.ejb.services.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
	public void insert (AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.insert(pAdmonUsuariosRolesDto);
	}

	@Override
	public void delete(long pNumero) {
		admonUsuariosRolesDao.delete(pNumero);
	}
	
	@Override
	public void deleteLogico(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.deleteLogico(pNumero, pAdmonUsuariosRolesDto);
	}

	@Override
	public void update(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.update(pNumero, pAdmonUsuariosRolesDto);
	}
	
	@Override
	public void activaRolUsuario(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.activaRolUsuario(pNumero, pAdmonUsuariosRolesDto);
	}

	@Override
	public List<AdmonUsuariosRolesV1Dto> findAll() {
		return admonUsuariosRolesDao.findAll();
	}
	
	@Override
	public List<AdmonUsuariosRolesV1Dto> findNotCand() {
		return admonUsuariosRolesDao.findNotCand();
	}
	
	@Override
	public List<AdmonUsuariosRolesV1Dto> findCand() {
		return admonUsuariosRolesDao.findCand();
	}

	@Override
	public int validaUsuarioRol(long pNumeroUsuario, long pNumeroRol) {
		return admonUsuariosRolesDao.validaUsuarioRol(pNumeroUsuario
				                                     ,pNumeroRol
				                                     );
	}
	
	@Override
	public int validaUsuarioRolID(long pNumeroUsuario,long pNumeroRol) {
		return admonUsuariosRolesDao.validaUsuarioRolID(pNumeroUsuario, pNumeroRol);
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
	public List<AdmonUsuariosRolesV1> findWithFilterExam(long pNumeroExamen, String pTipoExamen) {
		
		List<AdmonUsuariosRolesV1> retval = new ArrayList<AdmonUsuariosRolesV1>(); 
		List<Object> listObjects = admonUsuariosRolesDao.findWithFilterExam(pNumeroExamen, pTipoExamen); 
		
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
				if(row[13] instanceof BigInteger) { /** [ACTUALIZADO_POR] **/
					admonUsuariosRolesV1.setActualizadoPor(((BigInteger)row[13]).longValue());
					admonUsuariosRolesV1.setActualizadoPorString();
				}
				if(row[14] instanceof Date) { /** [FECHA_ACTUALIZACION] **/
					admonUsuariosRolesV1.setFechaActualizacion((Date)row[14]);
					admonUsuariosRolesV1.setFechaActString();
				}
				if(row[15] instanceof String) { /** [NOMBRE_ACT_POR] **/
					admonUsuariosRolesV1.setNombreActualizo((String)row[15]);
				}else {
					admonUsuariosRolesV1.setNombreActualizo("No disponible");
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

	@Override
	public List<AdmonUsuariosRolesV1> findCandidateNotExam(String cCurp, String cNombre, String c_aPaterno, 
			String c_aMaterno, String actPor, String fechaActu, long   pNumeroExamen, String pTipoExamen){
		
		List<AdmonUsuariosRolesV1> retval = new ArrayList<AdmonUsuariosRolesV1>(); 
		List<Object> listObjects = admonUsuariosRolesDao.findCandidateNotExam(cCurp, cNombre, c_aPaterno, c_aMaterno, actPor, fechaActu, pNumeroExamen, pTipoExamen); 
		
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
				if(row[13] instanceof BigInteger) { /** [ACTUALIZADO_POR] **/
					admonUsuariosRolesV1.setActualizadoPor(((BigInteger)row[13]).longValue());
					admonUsuariosRolesV1.setActualizadoPorString();
				}
				if(row[14] instanceof Date) { /** [FECHA_ACTUALIZACION] NOMBRE_ACT_POR**/
					admonUsuariosRolesV1.setFechaActualizacion((Date)row[14]);
					admonUsuariosRolesV1.setFechaActString();
				}
				if(row[15] instanceof String) { /** [NOMBRE_ACT_POR] **/
					admonUsuariosRolesV1.setNombreActualizo((String)row[15]);
				}else {
					admonUsuariosRolesV1.setNombreActualizo("No disponible");
				}
				
				retval.add(admonUsuariosRolesV1); 
			}
		}
		return retval;
	}
	
	@Override
	public List<AdmonUsuariosRolesV1> findUser(long numberUser){
		List<AdmonUsuariosRolesV1> retval = new ArrayList<AdmonUsuariosRolesV1>(); 
		List<Object> listObjects = admonUsuariosRolesDao.findUser(numberUser); 
		
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
				if(row[13] instanceof BigInteger) { /** [ACTUALIZADO_POR] **/
					admonUsuariosRolesV1.setActualizadoPor(((BigInteger)row[13]).longValue());
					admonUsuariosRolesV1.setActualizadoPorString();
				}
				if(row[14] instanceof Date) { /** [FECHA_ACTUALIZACION] NOMBRE_ACT_POR**/
					admonUsuariosRolesV1.setFechaActualizacion((Date)row[14]);
					admonUsuariosRolesV1.setFechaActString();
				}
				if(row[15] instanceof Date) { /** [FECHA_EFECTIVA_DESDE] **/
					admonUsuariosRolesV1.setFedAu((Date)row[15]);
				}
				if(row[16] instanceof Date) { /** [FECHA_EFECTIVA_HASTA] **/
					admonUsuariosRolesV1.setFehAu((Date)row[16]);
				}
				if(row[17] instanceof String) { /** [NOMBRE_ACT_POR] **/
					admonUsuariosRolesV1.setNombreActualizo((String)row[17]);
				}else {
					admonUsuariosRolesV1.setNombreActualizo("No disponible");
				}
				
				retval.add(admonUsuariosRolesV1); 
			}
		}
		return retval;
	}
	
	@Override
	public List<AdmonUsuariosRolesV1Dto> findUsers(){
		List<AdmonUsuariosRolesV1Dto> retval = new ArrayList<AdmonUsuariosRolesV1Dto>(); 
		List<Object> listObjects = admonUsuariosRolesDao.findUsers(); 
		
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				AdmonUsuariosRolesV1Dto admonUsuariosRolesV1dto = new AdmonUsuariosRolesV1Dto(); 
				String namerol;
				String rolesuser;
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** [NUMERO_USUARIO] **/
					admonUsuariosRolesV1dto.setNumeroUsuario(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof String) { /** [NOMBRE_COMPLETO_USUAURIO] **/
					admonUsuariosRolesV1dto.setNombreCompletoUsuario((String)row[1]);
				}				
				if(row[2] instanceof String) { /** [CURP] **/
					admonUsuariosRolesV1dto.setCurp((String)row[2]);
				}
				if(row[3] instanceof String) { /** [CORREO_ELECTRONICO] **/
					admonUsuariosRolesV1dto.setCorreoElectronico((String)row[3]);
				}
				if(row[4] instanceof String) { /** [NOMBRE_ROL] **/
					namerol = (String)row[4];
					String [] roles = namerol.split(",");
					if(roles.length>2)
						rolesuser = roles[0] + ", "+roles[1]+"...";						
					else if(roles.length== 2)
						rolesuser = roles[0] + ", "+roles[1];	
					else
						rolesuser = namerol;
					
					admonUsuariosRolesV1dto.setNombreRol(rolesuser);
				}				
				if(row[5] instanceof Date) { /** [FECHA_EFECTIVA_DESDE] **/
					Date fd = (Date)row[5];
					admonUsuariosRolesV1dto.setFedAu((java.sql.Date)fd);
				}
				if(row[6] instanceof Date) { /** [FECHA_EFECTIVA_HASTA] **/
					Date fh = (Date)row[6];
					admonUsuariosRolesV1dto.setFehAu((java.sql.Date)fh);
				}				
				
				retval.add(admonUsuariosRolesV1dto); 
			}
		}
		return retval;
	}
}
