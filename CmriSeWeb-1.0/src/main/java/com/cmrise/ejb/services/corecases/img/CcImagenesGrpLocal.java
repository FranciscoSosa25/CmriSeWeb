package com.cmrise.ejb.services.corecases.img;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;

@Local
public interface CcImagenesGrpLocal {
	public long insert(CcImagenesGrpDto pCcImagenesGrpDto);

	public void insert(long pNumetoFta
			          ,CcImagenesGrp pCcImagenesGrp
			          );

	
	public List<CcImagenesGrp> findByFta(long   pNumeroFta
			                              ,String pSeccion
			                              );
	
	public List<CcImagenesGrp> findByCcHDR(long   pNumeroFta
            ,String pSeccion
            );
	
	public void deleteByCcHrd(long pNumetoCcHRD, String pSeccion);
	public boolean deleteGroup(CcImagenesGrp pCcImagenesGrp);
	public boolean deleteGroupImage(CcImagenesGrp pCcImagenesGrp, CcImagenes imagenes);

	public void update(long pNumetoFta, CcImagenesGrp pCcImagenesGrp);
	public boolean savePolygon(CcImagenes ccImagenes);

	void deleteByCcFTA(long pNumeroFta, String pSeccion);
}
