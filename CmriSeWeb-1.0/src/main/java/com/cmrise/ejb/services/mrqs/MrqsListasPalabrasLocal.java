package com.cmrise.ejb.services.mrqs;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;

@Local
public interface MrqsListasPalabrasLocal {
	public long insert(MrqsListasPalabrasDto pMrqsListasPalabrasDto);
	public void update(long pNumero
	             ,MrqsListasPalabrasDto pMrqsListasPalabrasDto); 
	 public List<MrqsListasPalabrasDto> findByFta(long pNumeroFta
									             ,String pTipoRegistro
									             );    
	 public void delete(long pNumero); 
}
