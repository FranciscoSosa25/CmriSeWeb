package com.cmrise.ejb.services.corecases;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.cmrise.dicom.attribute.DicomAttributeReaderService;
import com.cmrise.dicom.image.DicomImageReaderService;
import com.cmrise.dicom.image.SaveJpegFileService;
import com.cmrise.ejb.model.corecases.CcHdrForAction;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dao.corecases.img.CcImagenesDao;
import com.cmrise.jpa.dao.corecases.img.CcImagenesGrpDao;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.corecases.img.CcImagenesDto;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class CcHdrLocalImpl implements CcHdrLocal {
	
	@Inject 
	CcHdrDao ccHdrDao; 
	
	@Inject 
	CcPreguntasHdrDao  ccPreguntasHdrDao;
	
	@Inject 
	CcPreguntasFtaDao  ccPreguntasFtaDao;
	
	@Inject 
	CcOpcionMultipleDao ccOpcionMultipleDao; 
	
	@Inject 
	CcImagenesGrpDao ccImagenesGrpDao; 
	
	@Inject 
	CcImagenesDao ccImagenesDao; 
	
	@Override
	public void insert(CcHdrDto pCcHdrDto) {
		ccHdrDao.insert(pCcHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		ccHdrDao.delete(pNumero);
	}
	@Override
	public void deletePregunta(long pNumero) {
		ccHdrDao.deletePregunta(pNumero);
	}

	@Override
	public void update(long pNumero, CcHdrDto pCcHdrDto) {
		ccHdrDao.update(pNumero, pCcHdrDto);
	}

	@Override
	public List<CcHdrV1> findAll() {
		List<CcHdrV1> retval = new ArrayList<CcHdrV1>(); 
		List<CcHdrV1Dto> listCcHdrV1Dto = ccHdrDao.findAll();
		for(CcHdrV1Dto i:listCcHdrV1Dto) {
			CcHdrV1 ccHdrV1 = new CcHdrV1(); 
			ccHdrV1.setNumero(i.getNumero());
			ccHdrV1.setAdmonExamen(i.getAdmonExamen());
			ccHdrV1.setAdmonMateria(i.getAdmonMateria());
			ccHdrV1.setAdmonSubMateria(i.getAdmonSubmateria());
			ccHdrV1.setFechaElaboracion(Utilitarios.sqlDateToUtilDate(i.getFechaElaboracion()));
			ccHdrV1.setAdmonExamenDesc(i.getAdmonExamenDesc());
			ccHdrV1.setAdmonMateriaDesc(i.getAdmonMateriaDesc());
			ccHdrV1.setAdmonSubMateriaDesc(i.getAdmonSubmateriaDesc());
			ccHdrV1.setEstatus(i.getEstatus());
			ccHdrV1.setEstatusDesc(i.getEstatusDesc());
			retval.add(ccHdrV1); 
		}
		
		return retval;
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

	@Override
	public CcHdrV1 findByNumeroObjMod(long pNumeroCcHdr) {
		System.out.println("Entra findByNumeroObjMod pNumeroCcHdr:"+pNumeroCcHdr);
		CcHdrV1 retval = new CcHdrV1(); 
		CcHdrV1Dto ccHdrV1Dto = ccHdrDao.findByNumero(pNumeroCcHdr); 
		retval.setNumero(ccHdrV1Dto.getNumero());
		retval.setHistorialClinico(ccHdrV1Dto.getHistorialClinico());
		retval.setDescripcionTecnica(ccHdrV1Dto.getDescripcionTecnica());
		retval.setOpcionInsegura(ccHdrV1Dto.getOpcionInsegura());
		retval.setEtiquetas(ccHdrV1Dto.getEtiquetas());
		retval.setNota(ccHdrV1Dto.getNota());
		retval.setEstatus(ccHdrV1Dto.getEstatus());
		retval.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
		retval.setFechaEfectivaDesde(new java.util.Date(ccHdrV1Dto.getFechaEfectivaDesde().getTime()));
		retval.setFechaEfectivaHasta(new java.util.Date(ccHdrV1Dto.getFechaEfectivaHasta().getTime()));
	    retval.setAdmonExamen(ccHdrV1Dto.getAdmonExamen());
	    retval.setAdmonMateria(ccHdrV1Dto.getAdmonMateria());
	    retval.setAdmonSubMateria(ccHdrV1Dto.getAdmonSubmateria());
	    retval.setAdmonExamenDesc(ccHdrV1Dto.getAdmonExamenDesc());
	    retval.setAdmonMateriaDesc(ccHdrV1Dto.getAdmonMateriaDesc());
	    retval.setAdmonSubMateriaDesc(ccHdrV1Dto.getAdmonSubmateriaDesc());
	    retval.setFechaElaboracion(ccHdrV1Dto.getFechaElaboracion());
		
		List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrDao.findListByNumeroCcHdr(pNumeroCcHdr); 
		
		List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
	 	for(CcPreguntasHdrV1Dto i :listCcPreguntasHdrV1Dto) {
	 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
	 		ccPreguntasHdrV1.setNumero(i.getNumero());
	     	ccPreguntasHdrV1.setNumeroCcHdr(i.getNumeroCcHdr());
	     	ccPreguntasHdrV1.setTipoPregunta(i.getTipoPregunta());
	     	ccPreguntasHdrV1.setTipoPreguntaDesc(i.getTipoPreguntaDesc());
	     	ccPreguntasHdrV1.setEstatus(i.getEstatus());
	     	ccPreguntasHdrV1.setEstatusDesc(i.getEstatusDesc());
	     	ccPreguntasHdrV1.setMaxPuntuacion(i.getMaxPuntuacion());
	     	ccPreguntasHdrV1.setEtiquetas(i.getEtiquetas());
	     	ccPreguntasHdrV1.setAdmonExamen(i.getAdmonExamen());
	     	ccPreguntasHdrV1.setAdmonMateria(i.getAdmonMateria());
	     	ccPreguntasHdrV1.setAdmonSubMateria(i.getAdmonSubmateria());
	     	ccPreguntasHdrV1.setAdmonExamenDesc(i.getAdmonExamenDesc());
	     	ccPreguntasHdrV1.setAdmonMateriaDesc(i.getAdmonMateriaDesc());
	     	ccPreguntasHdrV1.setAdmonSubMateriaDesc(i.getAdmonSubmateriaDesc());
	     	
	     	CcPreguntasFtaV1 ccPreguntasFtaV1 = new CcPreguntasFtaV1(); 
	     	CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto =  ccPreguntasFtaDao.findDtoByNumeroHdr(i.getNumero()); 
	     	ccPreguntasFtaV1.setNumero(ccPreguntasFtaV1Dto.getNumero());
	     	ccPreguntasFtaV1.setNumeroHdr(ccPreguntasFtaV1Dto.getNumeroHdr());
	     	ccPreguntasFtaV1.setTituloPregunta(ccPreguntasFtaV1Dto.getTituloPregunta()); 
	     	ccPreguntasFtaV1.setTextoPregunta(ccPreguntasFtaV1Dto.getTextoPregunta());
	     	ccPreguntasFtaV1.setTextoSugerencias(ccPreguntasFtaV1Dto.getTextoSugerencias());
	     	ccPreguntasFtaV1.setRespuestaCorrecta(ccPreguntasFtaV1Dto.getRespuestaCorrecta());
	     	ccPreguntasFtaV1.setSingleAnswerMode(ccPreguntasFtaV1Dto.isSingleAnswerMode());
	     	ccPreguntasFtaV1.setSuffleAnswerOrder(ccPreguntasFtaV1Dto.isSuffleAnswerOrder());
	     	ccPreguntasFtaV1.setMetodoPuntuacion(ccPreguntasFtaV1Dto.getMetodoPuntuacion());
	    	ccPreguntasFtaV1.setValorPuntuacion(ccPreguntasFtaV1Dto.getValorPuntuacion()); 
	    	if(ccPreguntasFtaV1Dto.getLimiteCaracteres()!=null)ccPreguntasFtaV1.setLimiteCaracteres(ccPreguntasFtaV1Dto.getLimiteCaracteres());
	     	List<CcOpcionMultipleDto> listCcOpcionMultipleDto =ccPreguntasFtaV1Dto.isSuffleAnswerOrder()==false?ccOpcionMultipleDao.findByNumeroFta(ccPreguntasFtaV1Dto.getNumero()):ccOpcionMultipleDao.findByNumeroFtaAleatorio(ccPreguntasFtaV1Dto.getNumero())  ;
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
				 ccPreguntasFtaV1.setListCcOpcionMultiple(listCcOpcionMultiple);
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
						String strJpgRuta  = Utilitarios.FS_ROOT+k.getRutaImagen()+ File.separator+k.getNombreImagen().replace(".dcm", Utilitarios.JPG_SUFFIX);
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
				ccPreguntasFtaV1.setListCcImagenesGrp(localListCcImagenesGrp);
			} /*** END if(null!=listCcImagenesGrpDto) { **/
			
	     	ccPreguntasHdrV1.setCcPreguntasFtaV1(ccPreguntasFtaV1);
	     	
	     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);
	 	}
	 	
	 	retval.setListCcPreguntasHdrV1(listCcPreguntasHdrV1);
	 	
	 	System.out.println("Sale findByNumeroObjMod pNumeroCcHdr:"+pNumeroCcHdr);
		return retval;
	}

	@Override
	public long insert(CcHdrV1 pCcHdrV1) {
		CcHdrDto ccHdrDto = new CcHdrDto(); 
		ccHdrDto.setAdmonExamen(pCcHdrV1.getAdmonExamen());
		ccHdrDto.setAdmonMateria(pCcHdrV1.getAdmonMateria());
		ccHdrDto.setAdmonSubmateria(pCcHdrV1.getAdmonSubMateria());
		ccHdrDto.setFechaElaboracion(Utilitarios.utilDateToSqlDate(pCcHdrV1.getFechaElaboracion()));
		ccHdrDto.setCreadoPor(pCcHdrV1.getCreadoPor());
		ccHdrDto.setActualizadoPor(pCcHdrV1.getActualizadoPor());
		ccHdrDto.setEstatus(pCcHdrV1.getEstatus());
		ccHdrDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pCcHdrV1.getFechaCreacion()));
		ccHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pCcHdrV1.getFechaActualizacion()));
		ccHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		ccHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		ccHdrDto.setHistorialClinico(pCcHdrV1.getHistorialClinico());
		ccHdrDto.setDescripcionTecnica(pCcHdrV1.getDescripcionTecnica());
		ccHdrDto.setEtiquetas(pCcHdrV1.getEtiquetas());
		ccHdrDto.setOpcionInsegura(pCcHdrV1.isOpcionInsegura());
		ccHdrDao.insert(ccHdrDto);
		pCcHdrV1.setNumero(ccHdrDto.getNumero());
		return ccHdrDto.getNumero();
	}

	
}
