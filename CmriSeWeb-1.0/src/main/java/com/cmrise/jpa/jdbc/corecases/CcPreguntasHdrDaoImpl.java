package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcPreguntasHdrDaoImpl implements CcPreguntasHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcPreguntasHdrDto pCcPreguntasHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_PREGUNTAS_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pCcPreguntasHdrDto.setNumero(lNumeroS.longValue());
		em.persist(pCcPreguntasHdrDto);
		return longNumeroS; 
	}

	@Override
	public void delete(long pNumero) {
		CcPreguntasHdrDto ccPreguntasHdrDto = em.find(CcPreguntasHdrDto.class, pNumero);
		em.remove(ccPreguntasHdrDto);
	}

	@Override
	public void update(long pNumero, CcPreguntasHdrDto pCcPreguntasHdrDto) {
		CcPreguntasHdrDto ccPreguntasHdrDto = em.find(CcPreguntasHdrDto.class, pNumero);
		//ccPreguntasHdrDto.setNombre(pCcPreguntasHdrDto.getNombre());
		//ccPreguntasHdrDto.setTitulo(pCcPreguntasHdrDto.getTitulo());
		ccPreguntasHdrDto.setEstatus(pCcPreguntasHdrDto.getEstatus());
		ccPreguntasHdrDto.setTipoPregunta(pCcPreguntasHdrDto.getTipoPregunta());
		//ccPreguntasHdrDto.setTemaPregunta(pCcPreguntasHdrDto.getTemaPregunta());
		ccPreguntasHdrDto.setMaxPuntuacion(pCcPreguntasHdrDto.getMaxPuntuacion());
		ccPreguntasHdrDto.setEtiquetas(pCcPreguntasHdrDto.getEtiquetas());
		ccPreguntasHdrDto.setComentarios(pCcPreguntasHdrDto.getComentarios());
	}

	@Override
	public CcPreguntasHdrV1Dto findByNumero(long pNumero) {
		return em.find(CcPreguntasHdrV1Dto.class, pNumero);
	}

	@Override
	public List<CcPreguntasHdrV1Dto> findListByNumeroCcHdr(long pNumeroCcHdr) {
		String strQuery = "SELECT c FROM CcPreguntasHdrV1Dto c WHERE c.numeroCcHdr="+pNumeroCcHdr;
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}

	@Override
	public List<CcPreguntasHdrV1> findListByNumeroCcHdrWD(long pNumeroCcHdr) {
		List<CcPreguntasHdrV1> listCcPreguntas = new ArrayList<CcPreguntasHdrV1>();
		String strQuery = "SELECT CCPH.NUMERO, \r\n" + 
				"	CCPH.NUMERO_CC_HDR,\r\n" + 
				"	CCPF.NUMERO AS NUMERO_CCPF,\r\n" + 
				"	CCPH.TIPO_PREGUNTA, \r\n" + 
				"	TUV1.SIGNIFICADO AS TIPO_PREGUNTA_DESC, \r\n" + 
				"	CCPH.ETIQUETAS, \r\n" + 
				"	CCPH.COMENTARIOS, \r\n" + 
				"	CCPF.TEXTO_PREGUNTA, \r\n" + 
				"	CCPF.TEXTO_SUGERENCIAS, \r\n" + 
				"	CCPF.RESPUESTA_CORRECTA, \r\n" + 
				"	CCPF.SINGLE_ANSWER_MODE, \r\n" + 
				"	CCPF.SUFFLE_ANSWER_ORDER, \r\n" + 
				"	CCPF.TITULO_PREGUNTA, \r\n" + 
				"	CCPF.LIMITE_CARACTERES, \r\n" + 
				"   CCPF.METODO_PUNTUACION, \r\n" + 
				"	CCPF.VALOR_PUNTUACION \r\n"+
				"FROM    dbo.CC_PREGUNTAS_HDR AS CCPH INNER JOIN\r\n" + 
				"        dbo.CC_PREGUNTAS_FTA AS CCPF ON CCPH.NUMERO = CCPF.NUMERO_HDR INNER JOIN\r\n" + 
				"        dbo.TABLAS_UTILITARIAS_VALORES AS TUV1 ON CCPH.TIPO_PREGUNTA = TUV1.CODIGO_TABLA INNER JOIN\r\n" + 
				"        dbo.TABLAS_UTILITARIAS_VALORES AS TUV3 ON CCPH.ESTATUS = TUV3.CODIGO_TABLA INNER JOIN\r\n" + 
				"        dbo.ADMON_EXAMEN_HDR AS AEH ON CCPH.ADMON_EXAMEN = AEH.NUMERO INNER JOIN\r\n" + 
				"        dbo.ADMON_MATERIA_HDR AS AMH ON CCPH.ADMON_MATERIA = AMH.NUMERO INNER JOIN\r\n" + 
				"        dbo.ADMON_SUBMATERIA AS ASM ON CCPH.ADMON_SUBMATERIA = ASM.NUMERO INNER JOIN\r\n" + 
				"        dbo.ADMON_USUARIOS AS AU ON CCPH.CREADO_POR = AU.NUMERO\r\n" + 
				"WHERE  CCPH.NUMERO_CC_HDR =" + pNumeroCcHdr ;
		Query query = em.createNativeQuery(strQuery);
		List<Object> listObject=query.getResultList();
	    Iterator<Object> iterObject = listObject.iterator();
	    while(iterObject.hasNext()) {
	    	Object result = iterObject.next();
	    	CcPreguntasHdrV1 pregunta = new CcPreguntasHdrV1();
	    	CcPreguntasFtaV1 ccPreguntasFta = new CcPreguntasFtaV1();
	    	if(result instanceof Object[]) {
	    		Object[] row = (Object[]) result;
	    		if(row[0] instanceof BigInteger){
	    			pregunta.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof BigInteger) {					
					pregunta.setNumeroCcHdr(((BigInteger)row[1]).longValue());
				}
				if(row[2] instanceof BigInteger) {					
					ccPreguntasFta.setNumero(((BigInteger)row[2]).longValue());
				}
				if(row[3] instanceof String) {					
					pregunta.setTipoPregunta((String)row[3]);
				}
				if(row[4] instanceof String) {					
					pregunta.setTipoPreguntaDesc((String)row[4]);
				}
				if(row[5] instanceof String) {					
					pregunta.setEtiquetas((String)row[5]);
				}
				if(row[6] instanceof String) {					
					pregunta.setComentarios((String)row[6]);
				}
				if(row[7] instanceof String) {					
					ccPreguntasFta.setTextoPregunta((String)row[7]);
				}
				if(row[8] instanceof BigInteger) {					
					ccPreguntasFta.setTextoSugerencias((String)row[8]);
				}
				if(row[9] instanceof String) {					
					ccPreguntasFta.setRespuestaCorrecta((String)row[9]);
				}
				if(row[10] instanceof Boolean) {					
					ccPreguntasFta.setSingleAnswerMode((boolean)row[10]);
				}
				if(row[11] instanceof Boolean) {					
					ccPreguntasFta.setSuffleAnswerOrder((boolean)row[11]);
				}
				if(row[12] instanceof String) {					
					ccPreguntasFta.setTituloPregunta((String)row[12]);
				}
				if(row[13] instanceof Integer) {					
					ccPreguntasFta.setLimiteCaracteres((Integer)row[13]);
				}
				if(row[14] instanceof String) {					
					ccPreguntasFta.setMetodoPuntuacion((String)row[14]);
				}
				if(row[15] instanceof String) {					
					ccPreguntasFta.setValorPuntuacion((String)row[15]);
				}
	    	}
	    	pregunta.setCcPreguntasFtaV1(ccPreguntasFta);
	    	listCcPreguntas.add(pregunta);
	    }
		
		return listCcPreguntas;
	}
}
