package com.cmrise.jpa.dao.mrqs;

import java.util.List;

import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;

public interface MrqsListasPalabrasDao {
   public long insert(MrqsListasPalabrasDto pMrqsListasPalabrasDto);
   public void update(long pNumero
		             ,MrqsListasPalabrasDto pMrqsListasPalabrasDto); 
   public List<MrqsListasPalabrasDto> findByFta(long pNumeroFta
		                                       ,String pTipoRegistro
		                                       ); 
   public void delete(long pNumero);
   public void deleteByNumeroFta(long numeroFta); 
}
