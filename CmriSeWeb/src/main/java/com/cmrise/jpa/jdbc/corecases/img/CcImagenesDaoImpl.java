package com.cmrise.jpa.jdbc.corecases.img;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.jpa.dao.corecases.img.CcImagenesDao;
import com.cmrise.jpa.dto.corecases.img.CcImagenesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcImagenesDaoImpl implements CcImagenesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcImagenesDto pCcImagenesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_IMAGENES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcImagenesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcImagenesDto.setCreadoPor((long)-1);
		pCcImagenesDto.setActualizadoPor((long)-1);
		pCcImagenesDto.setFechaCreacion(sqlsysdate);
		pCcImagenesDto.setFechaActualizacion(sqlsysdate);
		pCcImagenesDto.setRutaImagen(pCcImagenesDto.getRutaImagen()+"\\"+lNumeroS.longValue());
		em.persist(pCcImagenesDto);
		return lNumeroS.longValue();
	}

	@Override
	public void insert(long pNumeroImagenesGrp
			         , CcImagenes pCcImagenes) {
		CcImagenesDto ccImagenesDto = new CcImagenesDto();
		ccImagenesDto.setNumeroGrp(pNumeroImagenesGrp);
		ccImagenesDto.setNombreImagen(pCcImagenes.getNombreImagen());
		ccImagenesDto.setRutaImagen(pCcImagenes.getRutaImagen());
		System.out.println("V1 mrqsImagenesDto.getRutaImagen():"+ccImagenesDto.getRutaImagen());
		insert(ccImagenesDto);
		System.out.println("V2 mrqsImagenesDto.getRutaImagen():"+ccImagenesDto.getRutaImagen());
		pCcImagenes.setRutaImagen(ccImagenesDto.getRutaImagen());
	}

	@Override
	public List<CcImagenesDto> findByGrp(long pNumeroGrp) {
		String strQuery = "SELECT c FROM CcImagenesDto c WHERE c.numeroGrp="+pNumeroGrp;
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

}
