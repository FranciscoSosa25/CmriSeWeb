package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcHdrDaoImpl implements CcHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(CcHdrDto pCcHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcHdrDto.setNumero(lNumeroS.longValue());
		em.persist(pCcHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		CcHdrDto ccHdrDto = em.find(CcHdrDto.class, pNumero);
		em.remove(ccHdrDto);
	}
	@Override
	public void deletePregunta(long pNumero) {
		CcPreguntasHdrDto ccPreguntasHdrDto = em.find(CcPreguntasHdrDto.class, pNumero);
		em.remove(ccPreguntasHdrDto);
	}


	@Override
	public void  update(CcHdrV1Dto pCcHdrV1Dto , CcHdrDto pCcHdrDto) {
		CcHdrDto ccHdrDto = em.find(CcHdrDto.class, pCcHdrV1Dto  );
		
		//ccHdrDto.setNombre(pCcHdrDto.getNombre());
		ccHdrDto.setEstatus(pCcHdrDto.getEstatus());
		//ccHdrDto.setTema(pCcHdrDto.getTema());
		ccHdrDto.setEtiquetas(pCcHdrDto.getEtiquetas());
		ccHdrDto.setHistorialClinico(pCcHdrDto.getHistorialClinico());
		ccHdrDto.setDescripcionTecnica(pCcHdrDto.getDescripcionTecnica());
		ccHdrDto.setNota(pCcHdrDto.getNota());
	

	}

	@Override
	public List<CcHdrV1Dto> findAll() {
		String strQuery = "SELECT c FROM CcHdrV1Dto c";
		Query query = em.createQuery(strQuery);
		/*String strQuery = "SELECT * FROM CC_HDR_V1";
		Query query = em.createNativeQuery(strQuery);*/
		return query.getResultList();
	}

	@Override
	public CcHdrV1Dto findByNumero(long pNumero) {
		return em.find(CcHdrV1Dto.class, pNumero);
	}

	@Override
	public List<KeysDto> findKeys() {
		String strQuery = "SELECT new com.cmrise.jpa.dto.admin.KeysDto(c.numero,c.nombre) FROM CcHdrDto c";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}

	@Override
	public List<Object> findCoreCasesForExam(long pNumeroExamen) {
		String strQuery="SELECT [NUMERO]\r" + 
						"      ,[NOMBRE]\r" + 
						"      ,[ESTATUS]\r" + 
						"      ,[ESTATUS_DESC]\r" + 
						"	  ,[TEMA]\r" + 
						"	  ,[TEMA_DESC]\r" + 
						" FROM [dbo].[CC_HDR_V1]\r" + 
						"WHERE [NUMERO] NOT IN ( SELECT CEA.[NUMERO_CORE_CASE]\r" + 
						"                          FROM [dbo].[CC_EXAM_ASIGNACIONES] CEA\r" +
						"                         WHERE CEA.[NUMERO_CC_EXAMEN] ="+pNumeroExamen+"\r"+
						"                       )\r";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
	

}
