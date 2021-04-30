package com.cmrise.ejb.services.exams;

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

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dao.corecases.img.CcImagenesDao;
import com.cmrise.jpa.dao.corecases.img.CcImagenesGrpDao;
import com.cmrise.jpa.dao.exams.CcExamAsignacionesDao;
import com.cmrise.jpa.dao.exams.CcExamenesDao;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.corecases.img.CcImagenesDto;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcExamenesLocalImpl implements CcExamenesLocal {

	@Inject 
	CcExamenesDao ccExamenesDao; 

	@Inject 
	CcExamAsignacionesDao ccExamAsignacionesDao; 
	
	@Inject 
	CcHdrDao ccHdrDao; 
	
	@Inject 
	CcPreguntasHdrDao  ccPreguntasHdrDao;
	
	@Inject 
	CcPreguntasFtaDao  ccPreguntasFtaDao;
	
	@Inject 
	CcOpcionMultipleDao  ccOpcionMultipleDao; 
	
	@Inject 
	CcImagenesGrpDao ccImagenesGrpDao; 
	
	@Inject 
	CcImagenesDao ccImagenesDao; 
	
	@Override
	public long insert(CcExamenesDto pCcExamenesDto) {
		return ccExamenesDao.insert(pCcExamenesDto);
	}

	@Override
	public List<CcExamenesDto> findAll() {
		return ccExamenesDao.findAll();
	}

	@Override
	public List<CcExamenesV1Dto> findAllWD() {
		return ccExamenesDao.findAllWD();
	}

	@Override
	public void delete(long pNumero) {
		ccExamenesDao.delete(pNumero);
	}

	@Override
	public CcExamenesDto findById(long pNumero) {
		return ccExamenesDao.findById(pNumero);
	}

	@Override
	public void update(long pNumero, CcExamenesDto pCcExamenesDto) {
		ccExamenesDao.update(pNumero, pCcExamenesDto);
	}

	@Override
	public CcExamenes findByNumeroObjMod(long pNumeroCcExamen) {
		CcExamenes retval = new CcExamenes(); 
		CcExamenesDto ccExamenesDto = ccExamenesDao.findById(pNumeroCcExamen); 
		retval.setDescripcion(ccExamenesDto.getDescripcion());
		retval.setTiempoLimite(ccExamenesDto.getTiempoLimite());
		retval.setSaltarCasos(ccExamenesDto.getSaltarCasos());
		retval.setMostrarRespuestas(ccExamenesDto.getMostrarRespuestas());
		retval.setMensajeFinalizacion(ccExamenesDto.getMensajeFinalizacion());
		retval.setIdTipoExamen(ccExamenesDto.getIdTipoExamen());
		retval.setVisibilidad(ccExamenesDto.getVisibilidad());
		retval.setEstatus(ccExamenesDto.getEstatus());
		retval.setSociedad(ccExamenesDto.getSociedad());
		retval.setFechaEfectivaDesde(new java.util.Date(ccExamenesDto.getFechaEfectivaDesde().getTime()));
		if(Utilitarios.endOfTime.equals(ccExamenesDto.getFechaEfectivaHasta())) {
			retval.setFechaEfectivaHasta(null);
		}else {
			retval.setFechaEfectivaHasta(new java.util.Date(ccExamenesDto.getFechaEfectivaHasta().getTime()));
		}
		
		List<CcExamAsignacionesDto> listCcExamAsignacionesDto  = ccExamAsignacionesDao.findByNumeroExamenDtos(pNumeroCcExamen); 
		if(null!=listCcExamAsignacionesDto) {
			List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
			for(CcExamAsignacionesDto i:listCcExamAsignacionesDto) {
				CcExamAsignaciones ccExamAsignaciones = new CcExamAsignaciones(); 
				ccExamAsignaciones.setNumero(i.getNumero());
				ccExamAsignaciones.setNumeroCcExamen(i.getNumeroCcExamen());
				ccExamAsignaciones.setNumeroCoreCase(i.getNumeroCoreCase());
				ccExamAsignaciones.setFechaEfectivaDesde(new java.util.Date(i.getFechaEfectivaDesde().getTime()));
				ccExamAsignaciones.setFechaEfectivaHasta(new java.util.Date(i.getFechaEfectivaHasta().getTime()));
				
				CcHdrV1 ccHdrV1 = new CcHdrV1(); 
				CcHdrV1Dto ccHdrV1Dto = ccHdrDao.findByNumero(i.getNumeroCoreCase()); 
				ccHdrV1.setNumero(ccHdrV1Dto.getNumero());
				//ccHdrV1.setNombre(ccHdrV1Dto.getNombre());
				//ccHdrV1.setTema(ccHdrV1Dto.getTema());
				//ccHdrV1.setTemaDesc(ccHdrV1Dto.getTemaDesc());
				ccHdrV1.setHistorialClinico(ccHdrV1Dto.getHistorialClinico());
				ccHdrV1.setDescripcionTecnica(ccHdrV1Dto.getDescripcionTecnica());
				ccHdrV1.setOpcionInsegura(ccHdrV1Dto.getOpcionInsegura());
				ccHdrV1.setEtiquetas(ccHdrV1Dto.getEtiquetas());
				ccHdrV1.setNota(ccHdrV1Dto.getNota());
				ccHdrV1.setEstatus(ccHdrV1Dto.getEstatus());
				ccHdrV1.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
				//ccHdrV1.setSociedad(ccHdrV1Dto.getSociedad());
				ccHdrV1.setFechaEfectivaDesde(new java.util.Date(ccHdrV1Dto.getFechaEfectivaDesde().getTime()));
				ccHdrV1.setFechaEfectivaHasta(new java.util.Date(ccHdrV1Dto.getFechaEfectivaHasta().getTime()));
				
				List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrDao.findListByNumeroCcHdr(i.getNumeroCoreCase()); 
				if(null!=listCcPreguntasHdrV1Dto) {
					List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
				 	for(CcPreguntasHdrV1Dto j :listCcPreguntasHdrV1Dto) {
				 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
				 		ccPreguntasHdrV1.setNumero(j.getNumero());
				     	ccPreguntasHdrV1.setNumeroCcHdr(j.getNumeroCcHdr());
				     //	ccPreguntasHdrV1.setTitulo(j.getTitulo());
				     	ccPreguntasHdrV1.setTipoPregunta(j.getTipoPregunta());
				     	ccPreguntasHdrV1.setTipoPreguntaDesc(j.getTipoPreguntaDesc());
				    // 	ccPreguntasHdrV1.setTemaPregunta(j.getTemaPregunta());
				    // 	ccPreguntasHdrV1.setTemaPreguntaDesc(j.getTemaPreguntaDesc());
				     	ccPreguntasHdrV1.setEstatus(j.getEstatus());
				     	ccPreguntasHdrV1.setEstatusDesc(j.getEstatusDesc());
				     	ccPreguntasHdrV1.setMaxPuntuacion(j.getMaxPuntuacion());
				     	ccPreguntasHdrV1.setEtiquetas(j.getEtiquetas());
				     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);  	
				  	}	
				 	ccHdrV1.setListCcPreguntasHdrV1(listCcPreguntasHdrV1);
				}
				
				ccExamAsignaciones.setCcHdrV1(ccHdrV1);
				listCcExamAsignaciones.add(ccExamAsignaciones); 
				
			}
			retval.setListCcExamAsignaciones(listCcExamAsignaciones);
		}
		
		return retval;
	}

	@Override
	public CcExamenes findByNumeroObjModCand(long pNumeroCcExamen) {
		CcExamenes retval = new CcExamenes(); 
		CcExamenesDto ccExamenesDto = ccExamenesDao.findById(pNumeroCcExamen); 
		retval.setDescripcion(ccExamenesDto.getDescripcion());
		retval.setTiempoLimite(ccExamenesDto.getTiempoLimite());
		retval.setSaltarCasos(ccExamenesDto.getSaltarCasos());
		retval.setMostrarRespuestas(ccExamenesDto.getMostrarRespuestas());
		retval.setMensajeFinalizacion(ccExamenesDto.getMensajeFinalizacion());
		//retval.setTipoExamen(ccExamenesDto.getTipoExamen());
		retval.setVisibilidad(ccExamenesDto.getVisibilidad());
		retval.setEstatus(ccExamenesDto.getEstatus());
		retval.setSociedad(ccExamenesDto.getSociedad());
		retval.setFechaEfectivaDesde(new java.util.Date(ccExamenesDto.getFechaEfectivaDesde().getTime()));
		if(Utilitarios.endOfTime.equals(ccExamenesDto.getFechaEfectivaHasta())) {
			retval.setFechaEfectivaHasta(null);
		}else {
			retval.setFechaEfectivaHasta(new java.util.Date(ccExamenesDto.getFechaEfectivaHasta().getTime()));
		}
		
		List<CcExamAsignacionesDto> listCcExamAsignacionesDto  = ccExamAsignacionesDao.findByNumeroExamenDtos(pNumeroCcExamen); 
		if(null!=listCcExamAsignacionesDto) {
			List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
			for(CcExamAsignacionesDto i:listCcExamAsignacionesDto) {
				CcExamAsignaciones ccExamAsignaciones = new CcExamAsignaciones(); 
				ccExamAsignaciones.setNumero(i.getNumero());
				ccExamAsignaciones.setNumeroCcExamen(i.getNumeroCcExamen());
				ccExamAsignaciones.setNumeroCoreCase(i.getNumeroCoreCase());
				ccExamAsignaciones.setFechaEfectivaDesde(new java.util.Date(i.getFechaEfectivaDesde().getTime()));
				ccExamAsignaciones.setFechaEfectivaHasta(new java.util.Date(i.getFechaEfectivaHasta().getTime()));
				
				CcHdrV1 ccHdrV1 = new CcHdrV1(); 
				CcHdrV1Dto ccHdrV1Dto = ccHdrDao.findByNumero(i.getNumeroCoreCase()); 
				ccHdrV1.setNumero(ccHdrV1Dto.getNumero());
			//	ccHdrV1.setNombre(ccHdrV1Dto.getNombre());
			//	ccHdrV1.setTema(ccHdrV1Dto.getTema());
			//	ccHdrV1.setTemaDesc(ccHdrV1Dto.getTemaDesc());
				ccHdrV1.setHistorialClinico(ccHdrV1Dto.getHistorialClinico());
				ccHdrV1.setDescripcionTecnica(ccHdrV1Dto.getDescripcionTecnica());
				ccHdrV1.setOpcionInsegura(ccHdrV1Dto.getOpcionInsegura());
				ccHdrV1.setEtiquetas(ccHdrV1Dto.getEtiquetas());
				ccHdrV1.setNota(ccHdrV1Dto.getNota());
				ccHdrV1.setEstatus(ccHdrV1Dto.getEstatus());
				ccHdrV1.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
			//	ccHdrV1.setSociedad(ccHdrV1Dto.getSociedad());
				ccHdrV1.setFechaEfectivaDesde(new java.util.Date(ccHdrV1Dto.getFechaEfectivaDesde().getTime()));
				ccHdrV1.setFechaEfectivaHasta(new java.util.Date(ccHdrV1Dto.getFechaEfectivaHasta().getTime()));
				
				List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrDao.findListByNumeroCcHdr(i.getNumeroCoreCase()); 
				if(null!=listCcPreguntasHdrV1Dto) {
					List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
				 	for(CcPreguntasHdrV1Dto j :listCcPreguntasHdrV1Dto) {
				 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
				 		ccPreguntasHdrV1.setNumero(j.getNumero());
				     	ccPreguntasHdrV1.setNumeroCcHdr(j.getNumeroCcHdr());
				   //  	ccPreguntasHdrV1.setTitulo(j.getTitulo());
				     	ccPreguntasHdrV1.setTipoPregunta(j.getTipoPregunta());
				     	ccPreguntasHdrV1.setTipoPreguntaDesc(j.getTipoPreguntaDesc());
				  //   	ccPreguntasHdrV1.setTemaPregunta(j.getTemaPregunta());
				  //   	ccPreguntasHdrV1.setTemaPreguntaDesc(j.getTemaPreguntaDesc());
				     	ccPreguntasHdrV1.setEstatus(j.getEstatus());
				     	ccPreguntasHdrV1.setEstatusDesc(j.getEstatusDesc());
				     	ccPreguntasHdrV1.setMaxPuntuacion(j.getMaxPuntuacion());
				     	ccPreguntasHdrV1.setEtiquetas(j.getEtiquetas());
				     	
				     	CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto = ccPreguntasFtaDao.findDtoByNumeroHdr(j.getNumero()); 
				     	CcPreguntasFtaV1 ccPreguntasFtaV1 = new CcPreguntasFtaV1(); 
				     	ccPreguntasFtaV1.setNumero(ccPreguntasFtaV1Dto.getNumero());
				     	ccPreguntasFtaV1.setNumeroHdr(ccPreguntasFtaV1Dto.getNumeroHdr());
				     	ccPreguntasFtaV1.setTituloPregunta(ccPreguntasFtaV1Dto.getTituloPregunta());
				     	ccPreguntasFtaV1.setTextoPregunta(ccPreguntasFtaV1Dto.getTextoPregunta());
				     	ccPreguntasFtaV1.setTextoSugerencias(ccPreguntasFtaV1Dto.getTextoSugerencias());
				     	if(Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(j.getTipoPregunta())) {
				     		ccPreguntasHdrV1.setCcPreguntasFtaV1(ccPreguntasFtaV1);
					     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);  	
				     	}else if(Utilitarios.OPCION_MULTIPLE.equals(j.getTipoPregunta())) {
				     		List<CcOpcionMultipleDto> listCcOpcionMultipleDto = ccOpcionMultipleDao.findByNumeroFta(ccPreguntasFtaV1Dto.getNumero()); 
					     	if(null!=listCcOpcionMultipleDto) {
					     		List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>();
					     		for(CcOpcionMultipleDto k:listCcOpcionMultipleDto) {
					     			CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
					     			ccOpcionMultiple.setNumero(k.getNumero());
					     			ccOpcionMultiple.setNumeroFta(k.getNumeroFta());
					     			ccOpcionMultiple.setNumeroLinea(k.getNumeroLinea());
					     			ccOpcionMultiple.setEstatus(k.isEstatus());
					     			ccOpcionMultiple.setTextoRespuesta(k.getTextoRespuesta());
					     			ccOpcionMultiple.setTextoExplicacion(k.getTextoExplicacion());
					     			listCcOpcionMultiple.add(ccOpcionMultiple);
						     	}	
					     		if(null!=listCcOpcionMultiple&&listCcOpcionMultiple.size()>0) { /** Valida Preguntas con Opciones Multiple a los candidatos **/
					     			ccPreguntasFtaV1.setListCcOpcionMultiple(listCcOpcionMultiple);
						     		ccPreguntasFtaV1.setSingleAnswerMode(ccPreguntasFtaV1Dto.isSingleAnswerMode());
							     	ccPreguntasFtaV1.setSuffleAnswerOrder(ccPreguntasFtaV1Dto.isSuffleAnswerOrder());
						     		ccPreguntasHdrV1.setCcPreguntasFtaV1(ccPreguntasFtaV1);
							     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);  
						     	}
					     	}
					    } /** END else if(Utilitarios.OPCION_MULTIPLE.equals(j.getTipoPregunta())) { **/
				     	
				     	
				    	List<CcImagenesGrp> localListCcImagenesGrp = new ArrayList<CcImagenesGrp>(); 
						
						List<CcImagenesGrpDto> listCcImagenesGrpDto = ccImagenesGrpDao.findByFta(ccPreguntasFtaV1Dto.getNumero()
								                                                                ,Utilitarios.INTRODUCCION
								                                                                ); 
						if(null!=listCcImagenesGrpDto) {
						  
							for(CcImagenesGrpDto k:listCcImagenesGrpDto) {
								System.out.println("*Paso1*");
								CcImagenesGrp ccImagenesGrp = new CcImagenesGrp();
								ccImagenesGrp.setNumero(k.getNumero());
								ccImagenesGrp.setSeccion(k.getSeccion());
								ccImagenesGrp.setTipo(k.getTipo());
								ccImagenesGrp.setTituloSuperior(k.getTituloSuperior());
								ccImagenesGrp.setTituloInferior(k.getTituloInferior());
								ccImagenesGrp.setTexto(k.getTexto());
								
								List<CcImagenes> listCcImagenes = new ArrayList<CcImagenes>(); 
								List<CcImagenesDto> listCcImagenesDto = ccImagenesDao.findByGrp(k.getNumero()); 
								for(CcImagenesDto l:listCcImagenesDto) {
									System.out.println("*Paso2*"); 
									CcImagenes ccImagenes = new CcImagenes(); 
									ccImagenes.setNumero(l.getNumero());
									ccImagenes.setNumeroGrp(l.getNumeroGrp());
									ccImagenes.setNombreImagen(l.getNombreImagen());
									ccImagenes.setRutaImagen(Utilitarios.FS_ROOT+l.getRutaImagen());
									String strJpgRuta  = Utilitarios.FS_ROOT+l.getRutaImagen()+ File.separator+l.getNombreImagen().replace(".dcm", Utilitarios.JPG_SUFFIX);
									String strThumbailRuta  = Utilitarios.FS_ROOT+l.getRutaImagen()+File.separator+l.getNombreImagen().replace(".dcm", Utilitarios.THUMBNAIL_SUFFIX);
									
									try {
										/** byte[] bytesArray = Files.readAllBytes(Paths.get(j.getRutaImagen()+"\\"+j.getNombreImagen())); **/
										/** ccImagenes.setImagenContent(bytesArray); **/
										byte[] bytesArray = Files.readAllBytes(Paths.get(strJpgRuta));
										ccImagenes.setJpgContent(bytesArray);
										ccImagenes.setJpgBase64(new String(Base64.getEncoder().encode(bytesArray)));
										bytesArray = Files.readAllBytes(Paths.get(strThumbailRuta));
										ccImagenes.setThumbailContent(bytesArray);
										ccImagenes.setThumbailBase64(new String(Base64.getEncoder().encode(bytesArray)));
										/*******************************************************************************
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
										********************************************************************************/
										
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
				     	
				     	
				     } /** END for(CcPreguntasHdrV1Dto j :listCcPreguntasHdrV1Dto) { **/
				 	ccHdrV1.setListCcPreguntasHdrV1(listCcPreguntasHdrV1);
				}
				
				ccExamAsignaciones.setCcHdrV1(ccHdrV1);
				if(null!=ccExamAsignaciones.getCcHdrV1().getListCcPreguntasHdrV1()) {
					if(ccExamAsignaciones.getCcHdrV1().getListCcPreguntasHdrV1().size()>0) { /** Valida Casos con preguntas a los candidatos **/
						listCcExamAsignaciones.add(ccExamAsignaciones); 
					}
				}
			}
			retval.setListCcExamAsignaciones(listCcExamAsignaciones);
		}
		
		return retval;
	}

}
