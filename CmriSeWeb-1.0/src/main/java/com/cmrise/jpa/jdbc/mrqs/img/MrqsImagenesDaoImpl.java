package com.cmrise.jpa.jdbc.mrqs.img;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.mrqs.img.MrqsImagenes;
import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsImagenesDaoImpl implements MrqsImagenesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(MrqsImagenesDto pMrqsImagenesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_IMAGENES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsImagenesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsImagenesDto.setCreadoPor((long)-1);
		pMrqsImagenesDto.setActualizadoPor((long)-1);
		pMrqsImagenesDto.setFechaCreacion(sqlsysdate);
		pMrqsImagenesDto.setFechaActualizacion(sqlsysdate);
		pMrqsImagenesDto.setRutaImagen(pMrqsImagenesDto.getRutaImagen()+ File.separator+lNumeroS.longValue());
		pMrqsImagenesDto.setContentType(pMrqsImagenesDto.getContentType());
		em.persist(pMrqsImagenesDto);
		return lNumeroS.longValue();
	}

	@Override
	public long insert(long pNumeroImagenesGrp
			          , MrqsImagenes pMrqsImagenes) {
		MrqsImagenesDto mrqsImagenesDto = new MrqsImagenesDto();
		mrqsImagenesDto.setNumeroGrp(pNumeroImagenesGrp);
		mrqsImagenesDto.setNombreImagen(pMrqsImagenes.getNombreImagen());
		mrqsImagenesDto.setRutaImagen(pMrqsImagenes.getRutaImagen());
		mrqsImagenesDto.setContentType(pMrqsImagenes.getContentType());
		System.out.println("V1 mrqsImagenesDto.getRutaImagen():"+mrqsImagenesDto.getRutaImagen());
		insert(mrqsImagenesDto);
		System.out.println("V2 mrqsImagenesDto.getRutaImagen():"+mrqsImagenesDto.getRutaImagen());
		pMrqsImagenes.setRutaImagen(mrqsImagenesDto.getRutaImagen());
		
		return  mrqsImagenesDto.getNumero(); 
	}

	@Override
	public List<MrqsImagenesDto> findByGrp(long pNumeroGrp) {
		String strQuery = "SELECT m FROM MrqsImagenesDto m WHERE m.numeroGrp="+pNumeroGrp;
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public long eliminar(long numero, long pNumeroGrp) throws Exception {
		StringBuilder query =new StringBuilder();
		query.append("DELETE FROM MrqsImagenesDto m WHERE m.numeroGrp=");
		query.append(pNumeroGrp);
		query.append(" AND m.numero=");
		query.append(numero);
		MrqsImagenesDto ob=new MrqsImagenesDto();
		ob.setNumero(numero);
		ob.setNumeroGrp(pNumeroGrp);
		try {
			em.remove(em.contains(ob) ? ob : em.merge(ob));
			em.flush();
			return 1;
		}catch(Exception e) {
			return -1;
		}
		
	}

}
