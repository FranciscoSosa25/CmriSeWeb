package com.cmrise.ejb.services.corecases;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.corecases.CcHdrForAction;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;

@Stateless 
public class CcHdrLocalImpl implements CcHdrLocal {

	@Inject 
	CcHdrDao ccHdrDao; 
	
	@Override
	public void insert(CcHdrDto pCcHdrDto) {
		ccHdrDao.insert(pCcHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		ccHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, CcHdrDto pCcHdrDto) {
		ccHdrDao.update(pNumero
				      , pCcHdrDto);
	}

	@Override
	public List<CcHdrV1Dto> findAll() {
		return ccHdrDao.findAll();
	}

	@Override
	public CcHdrV1Dto findByNumero(long pNumero) {
		return ccHdrDao.findByNumero(pNumero);
	}

	@Override
	public List<KeysDto> findKeys() {
		return ccHdrDao.findKeys();
	}

	@Override
	public List<CcHdrForAction> findCoreCasesForExam(long pNumeroExamen) {
		List<CcHdrForAction> retval = new ArrayList<CcHdrForAction>();
		List<Object> listObjects = ccHdrDao.findCoreCasesForExam(pNumeroExamen); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				CcHdrForAction ccHdrForAction = new CcHdrForAction(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** [NUMERO] **/
					ccHdrForAction.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof String) { /** [NOMBRE] **/
					ccHdrForAction.setNombre((String)row[1]);
				}
				if(row[2] instanceof String) { /** [ESTATUS] **/
					ccHdrForAction.setEstatus((String)row[2]);
				}
				if(row[3] instanceof String) { /** [ESTATUS_DESC] **/
					ccHdrForAction.setEstatusDesc((String)row[3]);
				}
				if(row[4] instanceof String) { /** [TEMA] **/
					ccHdrForAction.setTema((String)row[4]);
				}
				if(row[5] instanceof String) { /** [TEMA_DESC] **/
					ccHdrForAction.setTemaDesc((String)row[5]);
				}
				retval.add(ccHdrForAction); 
			}
		}	
		return retval;
	}

}
