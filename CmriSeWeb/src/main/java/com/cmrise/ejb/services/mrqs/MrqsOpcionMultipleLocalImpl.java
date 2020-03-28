package com.cmrise.ejb.services.mrqs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.mrqs.MrqsOpcionMultipleDao;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;

@Stateless 
public class MrqsOpcionMultipleLocalImpl implements MrqsOpcionMultipleLocal {

	@Inject 
	 MrqsOpcionMultipleDao  mrqsOpcionMultipleDao; 
	
	@Override
	public long insert(MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		return mrqsOpcionMultipleDao.insert(pMrqsOpcionMultipleDto);
	}

	@Override
	public List<MrqsOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		return mrqsOpcionMultipleDao.findByNumeroFta(pNumeroFta);
	}

	@Override
	public void update(long pNumero, MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		mrqsOpcionMultipleDao.update(pNumero, pMrqsOpcionMultipleDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsOpcionMultipleDao.delete(pNumero);
	}

	@Override
	public void deleteByNumeroFta(long pNumeroFta) {
		mrqsOpcionMultipleDao.deleteByNumeroFta(pNumeroFta);
	}

	@Override
	public void copyPaste(long pNumeroFtaOld, long longpNumeroFtaCopy) {
		mrqsOpcionMultipleDao.copyPaste(pNumeroFtaOld, longpNumeroFtaCopy);
	}

	@Override
	public List<MrqsOpcionMultipleDto> findByNumeroFtaShuffleOrder(long pNumeroFta
			                                                     , boolean pShuffleOrder) {
		List<Object> listObjects = mrqsOpcionMultipleDao.findByNumeroFtaShuffleOrder(pNumeroFta, pShuffleOrder); 
		return objectsToDtos(listObjects);
	}
	
	public List<MrqsOpcionMultipleDto> objectsToDtos(List<Object> pListObjects){
		List<MrqsOpcionMultipleDto> retval = new ArrayList<MrqsOpcionMultipleDto>(); 
		for(Object object:pListObjects) {
			if(object instanceof Object[]) {
				MrqsOpcionMultipleDto mrqsOpcionMultipleDto = new MrqsOpcionMultipleDto(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** NUMERO **/
					mrqsOpcionMultipleDto.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof BigInteger) { /** NUMERO_FTA **/
					mrqsOpcionMultipleDto.setNumeroFta(((BigInteger)row[1]).longValue());
				}
				if(row[2] instanceof Boolean) { /** ESTATUS **/
					mrqsOpcionMultipleDto.setEstatus(((Boolean)row[2]));
				}
				if(row[3] instanceof String) { /** TEXTO_RESPUESTA **/
					mrqsOpcionMultipleDto.setTextoRespuesta(((String)row[3]));
				}
				if(row[4] instanceof String) { /** TEXTO_EXPLICACION **/
					mrqsOpcionMultipleDto.setTextoExplicacion(((String)row[4]));
				}
				if(row[5] instanceof java.sql.Date) { /** FEcHA_EFECTIVA_DESDE **/
					mrqsOpcionMultipleDto.setFechaEfectivaDesde(((java.sql.Date)row[5]));
				}
				if(row[6] instanceof java.sql.Date) { /** FECHA_EFECTIVA_HASTA **/
					mrqsOpcionMultipleDto.setFechaEfectivaDesde(((java.sql.Date)row[6]));
				}
				if(row[7] instanceof BigInteger) { /** CREADO_POR **/
					mrqsOpcionMultipleDto.setCreadoPor(((BigInteger)row[7]).longValue());
				}
				if(row[8] instanceof java.sql.Timestamp) { /** FECHA_CREACION **/
					mrqsOpcionMultipleDto.setFechaCreacion(((java.sql.Timestamp)row[8]));
				}
				if(row[9] instanceof BigInteger) { /** ACTUALIZADO_POR **/
					mrqsOpcionMultipleDto.setActualizadoPor(((BigInteger)row[9]).longValue());
				}
				if(row[10] instanceof java.sql.Timestamp) { /** FECHA_ACTUALIZACION **/
					mrqsOpcionMultipleDto.setFechaActualizacion(((java.sql.Timestamp)row[10]));
				}
				if(row[11] instanceof Integer) { /** NUMERO_LINEA **/
					mrqsOpcionMultipleDto.setNumeroLinea(((Integer)row[11]).intValue());
				}
				retval.add(mrqsOpcionMultipleDto);
			}
		}
		return retval; 
	}

	@Override
	public int correctOrWrongAnswer(long pNumero, long pNumetoFta) {
		return mrqsOpcionMultipleDao.correctOrWrongAnswer(pNumero, pNumetoFta);
	}

	@Override
	public int totalCorrectAnswers(long pNumeroFta) {
		return mrqsOpcionMultipleDao.totalCorrectAnswers(pNumeroFta);
	}

}
