package com.cmrise.ejb.services.exams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenes;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.jpa.dao.exams.MrqsExamenesDao;
import com.cmrise.jpa.dao.exams.MrqsGrupoHdrDao;
import com.cmrise.jpa.dao.exams.MrqsGrupoLinesDao;
import com.cmrise.jpa.dao.mrqs.MrqsOpcionMultipleDao;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasHdrDao;
import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesDao;
import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesGrpDao;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;
import com.cmrise.jpa.dto.exams.MrqsExamenesV1Dto;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesDto;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesGrpDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsExamenesLocalImpl implements MrqsExamenesLocal {
	
	@Inject 
	MrqsExamenesDao mrqsExamenesDao; 
	
	@Inject 
	MrqsGrupoHdrDao mrqsGrupoHdrDao; 
	
	@Inject 
	MrqsGrupoLinesDao mrqsGrupoLinesDao; 
	
	@Inject 
	MrqsPreguntasHdrDao mrqsPreguntasHdrDao; 
	
	@Inject 
	MrqsPreguntasFtaDao mrqsPreguntasFtaDao; 
	
	@Inject 
	MrqsOpcionMultipleDao mrqsOpcionMultipleDao; 
	
	@Inject 
	MrqsImagenesGrpDao mrqsImagenesGrpDao;  
	
	@Inject 
	MrqsImagenesDao mrqsImagenesDao;  
	
	@Override
	public long insert(MrqsExamenesDto pMrqsExamenesDto) {
		return mrqsExamenesDao.insert(pMrqsExamenesDto);
	}

	@Override
	public List<MrqsExamenesDto> findAll() {
		return mrqsExamenesDao.findAll();
	}
	
	@Override
	public List<MrqsExamenesV1Dto> findAllWD() {
		return mrqsExamenesDao.findAllWD();
	}

	@Override
	public void delete(long pNumero) {
		mrqsExamenesDao.delete(pNumero);
	}

	@Override
	public MrqsExamenesDto findById(long pNumero) {
		return mrqsExamenesDao.findById(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsExamenesDto pMrqsExamenesDto) {
		mrqsExamenesDao.update(pNumero, pMrqsExamenesDto);
	}

	@Override
	public MrqsExamenes findByIdWD(long pNumero) {
		MrqsExamenesDto mrqsExamenesDto = mrqsExamenesDao.findById(pNumero);
		MrqsExamenes retval = new MrqsExamenes(); 
		retval.setNumero(mrqsExamenesDto.getNumero());
		return retval;
	}

	@Override
	public MrqsExamenes findObjMod(long pNumero) {
		MrqsExamenes retval = new MrqsExamenes(); 
		MrqsExamenesDto mrqsExamenesDto = mrqsExamenesDao.findById(pNumero);
		retval.setNumero(mrqsExamenesDto.getNumero());
		
		List<MrqsGrupoHdrDto> listMrqsGrupoHdrDto = mrqsGrupoHdrDao.findByNumeroExamen(pNumero); 
		if(null!=listMrqsGrupoHdrDto) {
			List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
			for(MrqsGrupoHdrDto i:listMrqsGrupoHdrDto) {
				MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr(); 
				mrqsGrupoHdr.setNumero(i.getNumero());
				mrqsGrupoHdr.setAdmonMateria(i.getAdmonMateria());
				
				List<MrqsGrupoLinesDto> listMrqsGrupoLinesDto = mrqsGrupoLinesDao.findByNumeroHdr(i.getNumero());
				if(null!=listMrqsGrupoLinesDto) {
					List<MrqsGrupoLines> listMrqsGrupoLines = new ArrayList<MrqsGrupoLines>(); 
					for(MrqsGrupoLinesDto j:listMrqsGrupoLinesDto) {
						MrqsGrupoLines mrqsGrupoLines = new MrqsGrupoLines();
						mrqsGrupoLines.setNumero(j.getNumero());
						mrqsGrupoLines.setNumeroHdr(j.getNumeroHdr());
						mrqsGrupoLines.setNumeroPregunta(j.getNumeroPregunta());
						
						MrqsPreguntasHdrV1Dto mrqsPreguntasHdrV1Dto = mrqsPreguntasHdrDao.findByNumero(j.getNumeroPregunta()); 
						if(null!=mrqsPreguntasHdrV1Dto) {
							MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = new MrqsPreguntasHdrV1(); 
							mrqsPreguntasHdrV1.setNumero(mrqsPreguntasHdrV1Dto.getNumero());
							mrqsPreguntasHdrV1.setTipoPregunta(mrqsPreguntasHdrV1Dto.getTipoPregunta());
							
							MrqsPreguntasFtaDto mrqsPreguntasFtaDto = mrqsPreguntasFtaDao.findDtoByNumeroHdr(mrqsPreguntasHdrV1Dto.getNumero()); 
							if(null!=mrqsPreguntasFtaDto) {
								MrqsPreguntasFtaV1 mrqsPreguntasFtaV1 = new MrqsPreguntasFtaV1(); 
								mrqsPreguntasFtaV1.setNumero(mrqsPreguntasFtaDto.getNumero());
								mrqsPreguntasFtaV1.setTextoPregunta(mrqsPreguntasFtaDto.getTextoPregunta());
								mrqsPreguntasFtaV1.setSingleAnswerMode(mrqsPreguntasFtaDto.isSingleAnswerMode());
								
								List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto = mrqsOpcionMultipleDao.findByNumeroFta(mrqsPreguntasFtaDto.getNumero()); 
								if(null!=listMrqsOpcionMultipleDto) {
									List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
									for(MrqsOpcionMultipleDto k:listMrqsOpcionMultipleDto) {
										MrqsOpcionMultiple mrqsOpcionMultiple = new MrqsOpcionMultiple();
										mrqsOpcionMultiple.setNumero(k.getNumero());
										mrqsOpcionMultiple.setTextoRespuesta(k.getTextoRespuesta());
										listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
									}
									mrqsPreguntasFtaV1.setListMrqsOpcionMultiple(listMrqsOpcionMultiple);
								}
								
								List<MrqsImagenesGrpDto> listMrqsImagenesGrpDto =  mrqsImagenesGrpDao.findByFta(mrqsPreguntasFtaDto.getNumero(), Utilitarios.INTRODUCCION); 
								if(null!=listMrqsImagenesGrpDto) {
									List<MrqsImagenesGrp> listMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
									for(MrqsImagenesGrpDto k:listMrqsImagenesGrpDto) {
										MrqsImagenesGrp mrqsImagenesGrp = new MrqsImagenesGrp(); 
										mrqsImagenesGrp.setNumero(k.getNumero());
										
										List<MrqsImagenesDto>  listMrqsImagenesDto = mrqsImagenesDao.findByGrp(k.getNumero()); 
										if(null!=listMrqsImagenesDto) {
											List<MrqsImagenes> listMrqsImagenes = new ArrayList<MrqsImagenes>(); 
											for(MrqsImagenesDto l:listMrqsImagenesDto) {
												MrqsImagenes mrqsImagenes = new MrqsImagenes(); 
												mrqsImagenes.setNumero(l.getNumero());
												mrqsImagenes.setNumeroGrp(l.getNumeroGrp());
												mrqsImagenes.setNombreImagen(l.getNombreImagen());
												mrqsImagenes.setRutaImagen(Utilitarios.FS_ROOT+l.getRutaImagen());
												try {
													byte[] bytesArray = Files.readAllBytes(Paths.get(Utilitarios.FS_ROOT+l.getRutaImagen()+"\\"+l.getNombreImagen()));
													mrqsImagenes.setImagenContent(bytesArray);
													mrqsImagenes.setImagenBase64(new String(Base64.getEncoder().encode(bytesArray)));
												} catch (IOException ie) {
												   System.out.println("IOException :"+ie.getMessage());
												}
												listMrqsImagenes.add(mrqsImagenes); 
											}
											mrqsImagenesGrp.setListMrqsImagenes(listMrqsImagenes);
										}
										listMrqsImagenesGrp.add(mrqsImagenesGrp); 
									}
									mrqsPreguntasFtaV1.setListMrqsImagenesGrp(listMrqsImagenesGrp);
								}
								
								mrqsPreguntasHdrV1.setMrqsPreguntasFtaV1(mrqsPreguntasFtaV1);
							}
							
							mrqsGrupoLines.setMrqsPreguntasHdrV1(mrqsPreguntasHdrV1);
						}
						listMrqsGrupoLines.add(mrqsGrupoLines); 
					}	
					mrqsGrupoHdr.setListMrqsGrupoLines(listMrqsGrupoLines);
				}
				
				listMrqsGrupoHdr.add(mrqsGrupoHdr); 
			}
			retval.setListMrqsGrupoHdr(listMrqsGrupoHdr);
		}
		
		
		return retval;
	}

	@Override
	public long insert(MrqsExamenes pMrqsExamenes) {
		
       MrqsExamenesDto mrqsExamenesDto = new MrqsExamenesDto();
		
       mrqsExamenesDto.setCreadoPor(pMrqsExamenes.getCreadoPor());
       mrqsExamenesDto.setActualizadoPor(pMrqsExamenes.getActualizadoPor());
       mrqsExamenesDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pMrqsExamenes.getFechaCreacion()));
       mrqsExamenesDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pMrqsExamenes.getFechaActualizacion()));
       
		mrqsExamenesDto.setDescripcion(pMrqsExamenes.getDescripcion());
		mrqsExamenesDto.setVisibilidad(pMrqsExamenes.getVisibilidad());
		mrqsExamenesDto.setFechaEfectivaDesde(Utilitarios.utilDateToTimestamp(pMrqsExamenes.getFechaEfectivaDesde()));
		if(null!=pMrqsExamenes.getFechaEfectivaHasta()) {
			mrqsExamenesDto.setFechaEfectivaHasta(Utilitarios.utilDateToTimestamp(pMrqsExamenes.getFechaEfectivaHasta()));
		}else {
			mrqsExamenesDto.setFechaEfectivaHasta(Utilitarios.endOfTimeTimestamp);
		}
		mrqsExamenesDto.setTiempoLimite(pMrqsExamenes.getTiempoLimite());
		mrqsExamenesDto.setSaltarPreguntas(pMrqsExamenes.isSaltarPreguntas());
		mrqsExamenesDto.setSaltarCasos(pMrqsExamenes.isSaltarCasos());
		mrqsExamenesDto.setMostrarRespuestas(pMrqsExamenes.isMostrarRespuestas());
		mrqsExamenesDto.setAleatorioGrupo(pMrqsExamenes.isAleatorioGrupo());
		mrqsExamenesDto.setAleatorioPreguntas(pMrqsExamenes.isAleatorioPreguntas());
		mrqsExamenesDto.setSeleccionCasosAleatorios(pMrqsExamenes.isSeleccionCasosAleatorios());
		mrqsExamenesDto.setMensajeFinalizacion(pMrqsExamenes.getMensajeFinalizacion());
		mrqsExamenesDto.setEstatus(Utilitarios.INITIAL_STATUS_CC_EXAM);
		mrqsExamenesDto.setFechaElaboracion(Utilitarios.utilDateToSqlDate(pMrqsExamenes.getFechaElaboracion()));
		mrqsExamenesDto.setAdmonExamen(pMrqsExamenes.getAdmonExamen());
		
		
		long numeroMrqsExamen = mrqsExamenesDao.insert(mrqsExamenesDto);
		pMrqsExamenes.setNumero(mrqsExamenesDto.getNumero()); 
		return numeroMrqsExamen;
	}

	@Override
	public MrqsExamenes findByNumeroWD(long pNumero) {
		MrqsExamenes retval = new MrqsExamenes(); 
		MrqsExamenesV1Dto mrqsExamenesV1Dto = mrqsExamenesDao.findByNumeroWD(pNumero); 
		
		retval.setNumero(mrqsExamenesV1Dto.getNumero());
		retval.setElaborador(mrqsExamenesV1Dto.getElaborador());
		retval.setFechaElaboracion(Utilitarios.sqlDateToUtilDate(mrqsExamenesV1Dto.getFechaElaboracion()));
		retval.setAdmonExamen(mrqsExamenesV1Dto.getAdmonExamen());
		retval.setEstatus(mrqsExamenesV1Dto.getEstatus());
		retval.setDescripcion(mrqsExamenesV1Dto.getDescripcion());
		retval.setVisibilidad(mrqsExamenesV1Dto.getVisibilidad());
		retval.setFechaEfectivaDesde(Utilitarios.timestampDateToUtilDate(mrqsExamenesV1Dto.getFechaEfectivaDesde()));
		if(Utilitarios.endOfTimeTimestamp.equals(mrqsExamenesV1Dto.getFechaEfectivaHasta())) {
			retval.setFechaEfectivaHasta(null);
		}else {
			retval.setFechaEfectivaHasta(Utilitarios.timestampDateToUtilDate(mrqsExamenesV1Dto.getFechaEfectivaHasta()));
		}
		retval.setTiempoLimite(mrqsExamenesV1Dto.getTiempoLimite());
		retval.setSaltarPreguntas(mrqsExamenesV1Dto.getSaltarPreguntas());
		retval.setSaltarCasos(mrqsExamenesV1Dto.getSaltarCasos());
		retval.setMostrarRespuestas(mrqsExamenesV1Dto.getMostrarRespuestas());
		retval.setAleatorioGrupo(mrqsExamenesV1Dto.getAleatorioGrupo());
		retval.setAleatorioPreguntas(mrqsExamenesV1Dto.getAleatorioPreguntas());
		retval.setSeleccionCasosAleatorios(mrqsExamenesV1Dto.getSeleccionCasosAleatorios());
		
		
		return retval;
	}

	@Override
	public void update(MrqsExamenes pMrqsExamenesForUpdate) {
		MrqsExamenesDto mrqsExamenesDto = new MrqsExamenesDto();
		mrqsExamenesDto.setNumero(pMrqsExamenesForUpdate.getNumero());
		mrqsExamenesDto.setEstatus(pMrqsExamenesForUpdate.getEstatus());
		
		mrqsExamenesDto.setAdmonExamen(pMrqsExamenesForUpdate.getAdmonExamen());
		
		mrqsExamenesDto.setDescripcion(pMrqsExamenesForUpdate.getDescripcion());
		mrqsExamenesDto.setVisibilidad(pMrqsExamenesForUpdate.getVisibilidad());
		mrqsExamenesDto.setFechaEfectivaDesde(Utilitarios.utilDateToTimestamp(pMrqsExamenesForUpdate.getFechaEfectivaDesde()));
		if(null!=pMrqsExamenesForUpdate.getFechaEfectivaHasta()) {
			mrqsExamenesDto.setFechaEfectivaHasta(Utilitarios.utilDateToTimestamp(pMrqsExamenesForUpdate.getFechaEfectivaHasta()));
		}else {
			mrqsExamenesDto.setFechaEfectivaHasta(Utilitarios.endOfTimeTimestamp);
		}
		mrqsExamenesDto.setTiempoLimite(pMrqsExamenesForUpdate.getTiempoLimite());
		mrqsExamenesDto.setSaltarPreguntas(pMrqsExamenesForUpdate.isSaltarPreguntas());
		mrqsExamenesDto.setSaltarCasos(pMrqsExamenesForUpdate.isSaltarCasos());
		mrqsExamenesDto.setMostrarRespuestas(pMrqsExamenesForUpdate.isMostrarRespuestas());
		mrqsExamenesDto.setAleatorioGrupo(pMrqsExamenesForUpdate.isAleatorioGrupo());
		mrqsExamenesDto.setAleatorioPreguntas(pMrqsExamenesForUpdate.isAleatorioPreguntas());
		mrqsExamenesDto.setSeleccionCasosAleatorios(pMrqsExamenesForUpdate.isSeleccionCasosAleatorios());
		mrqsExamenesDto.setMensajeFinalizacion(pMrqsExamenesForUpdate.getMensajeFinalizacion());
		mrqsExamenesDao.update(pMrqsExamenesForUpdate.getNumero(), mrqsExamenesDto);
		
	}

	
}
