package com.cmrise.ejb.services.corecases;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dao.corecases.img.CcImagenesDao;
import com.cmrise.jpa.dao.corecases.img.CcImagenesGrpDao;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV2Dto;
import com.cmrise.jpa.dto.corecases.img.CcImagenesDto;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class CcPreguntasFtaLocalImpl implements CcPreguntasFtaLocal {

	@Inject 
	CcPreguntasFtaDao ccPreguntasFtaDao; 
	
	@Inject 
	CcOpcionMultipleDao ccOpcionMultipleDao; 
	
	@Inject 
	CcImagenesGrpDao ccImagenesGrpDao; 
	
	@Inject 
	CcImagenesDao ccImagenesDao; 
	
	@Override
	public long insert(CcPreguntasFtaDto pCcPreguntasFtaDto) {
		return ccPreguntasFtaDao.insert(pCcPreguntasFtaDto);
	}

	@Override
	public void delete(long pNumero) {
		ccPreguntasFtaDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, CcPreguntasFtaDto pCcPreguntasFtaDto) {
		ccPreguntasFtaDao.update(pNumero, pCcPreguntasFtaDto);
	}

	@Override
	public long finNumeroByHdr(long pNumeroHdr) {
		return ccPreguntasFtaDao.finNumeroByHdr(pNumeroHdr);
	}

	@Override
	public CcPreguntasFtaV1Dto findDtoByNumeroHdr(long pNumeroHdr) {
		return ccPreguntasFtaDao.findDtoByNumeroHdr(pNumeroHdr);
	}

	@Override
	public CcPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta) {
		return ccPreguntasFtaDao.findDtoByNumeroFta(pNumeroFta);
	}

	@Override
	public CcPreguntasFtaV2Dto findV2DtoByNumeroFta(long pNumeroFta) {
		return ccPreguntasFtaDao.findV2DtoByNumeroFta(pNumeroFta);
	}

	@Override
	public CcPreguntasFtaV1 findObjByNumeroHdr(long pNumeroHdr) {
		CcPreguntasFtaV1 retval = new CcPreguntasFtaV1(); 
		CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto = ccPreguntasFtaDao.findDtoByNumeroHdr(pNumeroHdr);
		
		retval.setNumero(ccPreguntasFtaV1Dto.getNumero());
		retval.setNumeroHdr(ccPreguntasFtaV1Dto.getNumeroHdr());
		retval.setTituloPregunta(ccPreguntasFtaV1Dto.getTituloPregunta()); 
		retval.setTextoPregunta(ccPreguntasFtaV1Dto.getTextoPregunta());
		retval.setTextoSugerencias(ccPreguntasFtaV1Dto.getTextoSugerencias());
		retval.setRespuestaCorrecta(ccPreguntasFtaV1Dto.getRespuestaCorrecta());
     	retval.setSingleAnswerMode(ccPreguntasFtaV1Dto.isSingleAnswerMode());
     	retval.setSuffleAnswerOrder(ccPreguntasFtaV1Dto.isSuffleAnswerOrder());
     	
    	List<CcOpcionMultipleDto> listCcOpcionMultipleDto =  ccOpcionMultipleDao.findByNumeroFta(ccPreguntasFtaV1Dto.getNumero());
		if(null!=listCcOpcionMultipleDto) {
			List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
			 for(CcOpcionMultipleDto j:listCcOpcionMultipleDto) {
				 CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
	        	 ccOpcionMultiple.setNumeroLinea(j.getNumeroLinea());
	        	 ccOpcionMultiple.setEstatus(j.isEstatus());
	        	 ccOpcionMultiple.setNumero(j.getNumero());
	        	 ccOpcionMultiple.setNumeroFta(j.getNumeroFta());
	        	 ccOpcionMultiple.setTextoExplicacion(j.getTextoExplicacion());
	        	 ccOpcionMultiple.setTextoRespuesta(j.getTextoRespuesta());
	        	 listCcOpcionMultiple.add(ccOpcionMultiple); 
	         }
			 retval.setListCcOpcionMultiple(listCcOpcionMultiple);
		}else {
			retval.setListCcOpcionMultiple(new ArrayList<CcOpcionMultiple>()); /** Prevent null pointer exception **/
		}
     	
		List<CcImagenesGrp> localListCcImagenesGrp = new ArrayList<CcImagenesGrp>(); 
		
		List<CcImagenesGrpDto> listCcImagenesGrpDto = ccImagenesGrpDao.findByFta(ccPreguntasFtaV1Dto.getNumero()
				                                                                ,Utilitarios.INTRODUCCION
				                                                                ); 
		if(null!=listCcImagenesGrpDto) {
			  
			for(CcImagenesGrpDto j:listCcImagenesGrpDto) {
				CcImagenesGrp ccImagenesGrp = new CcImagenesGrp();
				ccImagenesGrp.setNumero(j.getNumero());
				ccImagenesGrp.setSeccion(j.getSeccion());
				ccImagenesGrp.setTipo(j.getTipo());
				ccImagenesGrp.setTituloSuperior(j.getTituloSuperior());
				ccImagenesGrp.setTituloInferior(j.getTituloInferior());
				ccImagenesGrp.setTexto(j.getTexto());
				
				List<CcImagenes> listCcImagenes = new ArrayList<CcImagenes>(); 
				List<CcImagenesDto> listCcImagenesDto = ccImagenesDao.findByGrp(j.getNumero()); 
				for(CcImagenesDto k:listCcImagenesDto) {
					CcImagenes ccImagenes = new CcImagenes(); 
					ccImagenes.setNumero(k.getNumero());
					ccImagenes.setNumeroGrp(k.getNumeroGrp());
					ccImagenes.setNombreImagen(k.getNombreImagen());
					ccImagenes.setRutaImagen(Utilitarios.FS_ROOT+k.getRutaImagen());
					String strJpgRuta  = Utilitarios.FS_ROOT+k.getRutaImagen()+ File .separator+k.getNombreImagen().replace(".dcm", Utilitarios.JPG_SUFFIX);
					String strThumbailRuta  = Utilitarios.FS_ROOT+k.getRutaImagen()+File.separator+k.getNombreImagen().replace(".dcm", Utilitarios.THUMBNAIL_SUFFIX);
					System.out.println("strJpgRuta:"+strJpgRuta);
					System.out.println("strThumbailRuta:"+strThumbailRuta);
					
					try {
						/** byte[] bytesArray = Files.readAllBytes(Paths.get(j.getRutaImagen()+"\\"+j.getNombreImagen())); **/
						/** ccImagenes.setImagenContent(bytesArray); **/
						byte[] bytesArray = Files.readAllBytes(Paths.get(strJpgRuta));
						ccImagenes.setJpgContent(bytesArray);
						ccImagenes.setJpgBase64(new String(Base64.getEncoder().encode(bytesArray)));
						bytesArray = Files.readAllBytes(Paths.get(strThumbailRuta));
						ccImagenes.setThumbailContent(bytesArray);
						ccImagenes.setThumbailBase64(new String(Base64.getEncoder().encode(bytesArray)));
						
						StreamedContent streamedContent = DefaultStreamedContent.builder()
                                .contentType("image/png")
                                .stream(() -> {
                                    	try {
											byte[] bytesArray2 = Files.readAllBytes(Paths.get(strJpgRuta));
											InputStream is = new ByteArrayInputStream(bytesArray2);
                              				return is;	
										} catch (IOException e) {
											e.printStackTrace();
										}
                                    	    return null; 
                              	     })
                                 .build()
                                 ;
						ccImagenes.setJpgStreamedContent(streamedContent);
						
					} catch (IOException ie) {
					   System.out.println("IOException :"+ie.getMessage());
					}
					listCcImagenes.add(ccImagenes);
				}
				ccImagenesGrp.setListCcImagenes(listCcImagenes);
				localListCcImagenesGrp.add(ccImagenesGrp); 
			}
			retval.setListCcImagenesGrp(localListCcImagenesGrp);
		} /*** END if(null!=listCcImagenesGrpDto) { **/
		
		
		return retval;
	}

}
