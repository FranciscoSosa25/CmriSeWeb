package com.cmrise.jpa.jdbc.mrqs.img;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesGrpDao;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesGrpDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsImagenesGrpDaoImpl implements MrqsImagenesGrpDao {


	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(MrqsImagenesGrpDto pMrqsImagenesGrpDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_IMAGENES_GRP_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsImagenesGrpDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsImagenesGrpDto.setCreadoPor((long)-1);
		pMrqsImagenesGrpDto.setActualizadoPor((long)-1);
		pMrqsImagenesGrpDto.setFechaCreacion(sqlsysdate);
		pMrqsImagenesGrpDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsImagenesGrpDto);
		return lNumeroS.longValue();
	}

	@Override
	public List<MrqsImagenesGrpDto> findByFta(long pNumeroFta
			                                 ,String pSeccion
			                                 ) {
		String strQuery = "SELECT m FROM MrqsImagenesGrpDto m WHERE m.numeroFta = "+pNumeroFta+" AND m.seccion='"+pSeccion+"'"; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

}
