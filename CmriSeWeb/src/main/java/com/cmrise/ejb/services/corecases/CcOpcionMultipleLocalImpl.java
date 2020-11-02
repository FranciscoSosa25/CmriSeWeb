package com.cmrise.ejb.services.corecases;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class CcOpcionMultipleLocalImpl implements CcOpcionMultipleLocal {

	@Inject 
	CcOpcionMultipleDao ccOpcionMultipleDao;
	
	@Override
	public long insert(CcOpcionMultipleDto pCcOpcionMultipleDto) {
		return ccOpcionMultipleDao.insert(pCcOpcionMultipleDto);
	}
    
	@Override
	public List<CcOpcionMultipleDto> findByNumeroFtaShuffleOrder(long pNumeroFta
			                                                     , boolean pShuffleOrder) {
		List<Object> listObjects = ccOpcionMultipleDao.findByNumeroFtaShuffleOrder(pNumeroFta, pShuffleOrder); 
		return objectsToDtos(listObjects);
	}
	public List<CcOpcionMultipleDto> objectsToDtos(List<Object> pListObjects){
		List<CcOpcionMultipleDto> retval = new ArrayList<CcOpcionMultipleDto>(); 
		for(Object object:pListObjects) {
			if(object instanceof Object[]) {
				CcOpcionMultipleDto ccOpcionMultipleDto = new CcOpcionMultipleDto(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** NUMERO **/
					ccOpcionMultipleDto.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof BigInteger) { /** NUMERO_FTA **/
					ccOpcionMultipleDto.setNumeroFta(((BigInteger)row[1]).longValue());
				}
				if(row[2] instanceof Boolean) { /** ESTATUS **/
					ccOpcionMultipleDto.setEstatus(((Boolean)row[2]));
				}
				if(row[3] instanceof String) { /** TEXTO_RESPUESTA **/
					ccOpcionMultipleDto.setTextoRespuesta(((String)row[3]));
				}
				if(row[4] instanceof String) { /** TEXTO_EXPLICACION **/
					ccOpcionMultipleDto.setTextoExplicacion(((String)row[4]));
				}
				if(row[5] instanceof java.sql.Date) { /** FEcHA_EFECTIVA_DESDE **/
					ccOpcionMultipleDto.setFechaEfectivaDesde(((java.sql.Date)row[5]));
				}
				if(row[6] instanceof java.sql.Date) { /** FECHA_EFECTIVA_HASTA **/
					ccOpcionMultipleDto.setFechaEfectivaDesde(((java.sql.Date)row[6]));
				}
				if(row[7] instanceof BigInteger) { /** CREADO_POR **/
					ccOpcionMultipleDto.setCreadoPor(((BigInteger)row[7]).longValue());
				}
				if(row[8] instanceof java.sql.Timestamp) { /** FECHA_CREACION **/
					ccOpcionMultipleDto.setFechaCreacion(((java.sql.Timestamp)row[8]));
				}
				if(row[9] instanceof BigInteger) { /** ACTUALIZADO_POR **/
					ccOpcionMultipleDto.setActualizadoPor(((BigInteger)row[9]).longValue());
				}
				if(row[10] instanceof java.sql.Timestamp) { /** FECHA_ACTUALIZACION **/
					ccOpcionMultipleDto.setFechaActualizacion(((java.sql.Timestamp)row[10]));
				}
				if(row[11] instanceof Integer) { /** NUMERO_LINEA **/
					ccOpcionMultipleDto.setNumeroLinea(((Integer)row[11]).intValue());
				}
				retval.add(ccOpcionMultipleDto);
			}
		}
		return retval; 
	}
	
	
	

	@Override
	public int totalCorrectAnswers(long pNumeroFta) {
		return ccOpcionMultipleDao.totalCorrectAnswers(pNumeroFta);
	}
	@Override
	public List<CcOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		return ccOpcionMultipleDao.findByNumeroFta(pNumeroFta);
	}

	@Override
	public void update(long pNumero, CcOpcionMultipleDto pCcOpcionMultipleDto) {
		ccOpcionMultipleDao.update(pNumero, pCcOpcionMultipleDto);
	}

	@Override
	public void delete(long pNumero) {
		ccOpcionMultipleDao.delete(pNumero); 
	}
	
	@Override
	public int correctOrWrongAnswer(long pNumero, long pNumeroFta) {
		return ccOpcionMultipleDao.correctOrWrongAnswer(pNumero, pNumeroFta);
	}
}
