package com.cmrise.jpa.dao.mrqs;

import java.util.List;


import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;


public interface MrqsPreguntasFtaSinonimosDao {
	public long insert(List<MrqsPreguntasFtaSinonimos> sinonimos,long lNumeroFta,String texto) throws Exception;
    public List<MrqsPreguntasFtaSinonimos> findByFta(long pNumeroFta); 			 
	public void delete(MrqsPreguntasFtaSinonimos sinonimo) throws Exception;;
			 
}
