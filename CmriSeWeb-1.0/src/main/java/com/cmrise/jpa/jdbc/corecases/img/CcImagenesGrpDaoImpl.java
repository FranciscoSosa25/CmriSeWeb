package com.cmrise.jpa.jdbc.corecases.img;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.jpa.dao.corecases.img.CcImagenesGrpDao;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcImagenesGrpDaoImpl implements CcImagenesGrpDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcImagenesGrpDto pCcImagenesGrpDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_IMAGENES_GRP_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcImagenesGrpDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcImagenesGrpDto.setCreadoPor((long)-1);
		pCcImagenesGrpDto.setActualizadoPor((long)-1);
		pCcImagenesGrpDto.setFechaCreacion(sqlsysdate);
		pCcImagenesGrpDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcImagenesGrpDto);
		return lNumeroS.longValue();
	}
	
	@Override
	public void update(CcImagenesGrpDto pCcImagenesGrpDto) {
		CcImagenesGrpDto ccImagenesGrpDto = em.find(CcImagenesGrpDto.class, pCcImagenesGrpDto.getNumero());
		ccImagenesGrpDto.setModality(pCcImagenesGrpDto.getModality());
		ccImagenesGrpDto.setTituloSuperior(pCcImagenesGrpDto.getTituloSuperior());
	}
	
	public long delete(CcImagenesGrpDto pCcImagenesGrpDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_IMAGENES_GRP_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcImagenesGrpDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcImagenesGrpDto.setCreadoPor((long)-1);
		pCcImagenesGrpDto.setActualizadoPor((long)-1);
		pCcImagenesGrpDto.setFechaCreacion(sqlsysdate);
		pCcImagenesGrpDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcImagenesGrpDto);
		return lNumeroS.longValue();
	}

	@Override
	public List<CcImagenesGrpDto> findByFta(long pNumeroFta
			                             , String pSeccion) {
		String strQuery = "SELECT c FROM CcImagenesGrpDto c WHERE c.numeroFta = "+pNumeroFta+" AND c.seccion='"+pSeccion+"'"; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public List<CcImagenesGrpDto> findByCcHDR(long pNumeroFta, String pSeccion) {
		String strQuery = "SELECT c FROM CcImagenesGrpDto c WHERE c.numeroCcHDR = "+pNumeroFta+" AND c.seccion='"+pSeccion+"'"; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}
	
	
	@Override
	public boolean delete(long pNumeroGrp) {
		CcImagenesGrp ob=new CcImagenesGrp();
		ob.setNumero(pNumeroGrp);
		try {
			CcImagenesGrpDto ccImagenesGrpDto = em.find(CcImagenesGrpDto.class, pNumeroGrp);
			em.remove(ccImagenesGrpDto);
			em.flush();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	



}
