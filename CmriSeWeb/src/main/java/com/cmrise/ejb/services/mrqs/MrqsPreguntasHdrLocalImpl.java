package com.cmrise.ejb.services.mrqs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasHdrDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class MrqsPreguntasHdrLocalImpl implements MrqsPreguntasHdrLocal {

	@Inject
	MrqsPreguntasHdrDao mrqsPreguntasHdrDao; 
	
	@Override
	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		mrqsPreguntasHdrDao.insert(pMrqsPreguntasHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsPreguntasHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		mrqsPreguntasHdrDao.update(pNumero, pMrqsPreguntasHdrDto);
	}

	@Override
	public List<MrqsPreguntasHdrV1Dto> findAll() {
		return mrqsPreguntasHdrDao.findAll();
	}

	@Override
	public MrqsPreguntasHdrV1Dto findByNumero(long pNumero) {
		return mrqsPreguntasHdrDao.findByNumero(pNumero);
	}

	@Override
	public MrqsPreguntasHdrDto copyPaste(long pNumero) {
		return mrqsPreguntasHdrDao.copyPaste(pNumero);
	}
	
	@Override
	public List<MrqsPreguntasHdrV1Dto> findByTituloPregunta(String pTituloPregunta) {
		return mrqsPreguntasHdrDao.findByTituloPregunta(pTituloPregunta);
	}
	@Override
	public MrqsPreguntasHdrV2Dto findV2ByNumeroHdr(long pNumeroHdr) {
		return mrqsPreguntasHdrDao.findV2ByNumeroHdr(pNumeroHdr);
	}

	@Override
	public List<MrqsPreguntasHdrV1> findWithFilterExam(long pNumeroExamen) {
		List<MrqsPreguntasHdrV1> retval = new ArrayList<MrqsPreguntasHdrV1>();
		List<Object> listObjects = mrqsPreguntasHdrDao.findWithFilterExam(pNumeroExamen); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = new MrqsPreguntasHdrV1(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** [NUMERO] **/
					mrqsPreguntasHdrV1.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof String) { /** [NOMBRE] **/
					mrqsPreguntasHdrV1.setNombre((String)row[1]);
				}
				if(row[2] instanceof String) { /** [TITULO] **/
					mrqsPreguntasHdrV1.setTitulo((String)row[2]);
				}
				if(row[3] instanceof String) { /**[TIPO_PREGUNTA]**/
					mrqsPreguntasHdrV1.setTipoPregunta((String)row[3]);
				}
				if(row[4] instanceof String) { /**[TIPO_PREGUNTA_DESC]**/
					mrqsPreguntasHdrV1.setTipoPreguntaDesc((String)row[4]);
				}
				if(row[5] instanceof String) { /**[TEMA_PREGUNTA]**/
					mrqsPreguntasHdrV1.setTemaPregunta((String)row[5]);
				}
				if(row[6] instanceof String) { /**[TEMA_PREGUNTA_DESC]**/
					mrqsPreguntasHdrV1.setTemaPreguntaDesc((String)row[6]);
				}
				if(row[7] instanceof String) { /**[ETIQUETAS]**/
					mrqsPreguntasHdrV1.setEtiquetas((String)row[7]);
				}
				if(row[8] instanceof String) { /**[COMENTARIOS]**/
					mrqsPreguntasHdrV1.setComentarios((String)row[8]);
				}
				if(row[9] instanceof String) { /**[ESTATUS]**/
					mrqsPreguntasHdrV1.setEstatus((String)row[9]);
				}
				if(row[10] instanceof String) { /**[ESTATUS_DESC]**/
					mrqsPreguntasHdrV1.setEstatusDesc((String)row[10]);
				}
				retval.add(mrqsPreguntasHdrV1);
			}
		}	
		return retval;
	}

	@Override
	public long insert(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1) {
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		mrqsPreguntasHdrDto.setAdmonExamen(pMrqsPreguntasHdrV1.getAdmonExamen());
		mrqsPreguntasHdrDto.setAdmonMateria(pMrqsPreguntasHdrV1.getAdmonMateria());
		mrqsPreguntasHdrDto.setAdmonSubmateria(pMrqsPreguntasHdrV1.getAdmonSubmateria());
		mrqsPreguntasHdrDto.setTipoPregunta(pMrqsPreguntasHdrV1.getTipoPregunta());
		mrqsPreguntasHdrDto.setEtiquetas(pMrqsPreguntasHdrV1.getEtiquetas());
		mrqsPreguntasHdrDto.setComentarios(pMrqsPreguntasHdrV1.getComentarios());
		mrqsPreguntasHdrDto.setFechaEfectivaDesde(Utilitarios.utilDateToSqlDate(pMrqsPreguntasHdrV1.getFechaEfectivaDesde()));
		mrqsPreguntasHdrDto.setFechaEfectivaHasta(Utilitarios.utilDateToSqlDate(pMrqsPreguntasHdrV1.getFechaEfectivaHasta()));
		mrqsPreguntasHdrDto.setEstatus(pMrqsPreguntasHdrV1.getEstatus());
		mrqsPreguntasHdrDto.setSociedad(pMrqsPreguntasHdrV1.getSociedad());
		long numeroPreguntaHdr = mrqsPreguntasHdrDao.insert(mrqsPreguntasHdrDto); 
		pMrqsPreguntasHdrV1.setNumero(mrqsPreguntasHdrDto.getNumero());
		return mrqsPreguntasHdrDto.getNumero();
	}

}
