package com.cmrise.ejb.services.mrqs;



import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class MrqsPreguntasFtaLocalImpl implements MrqsPreguntasFtaLocal {

	@Inject
	MrqsPreguntasFtaDao mrqsPreguntasFtaDao;
	
	@Override
	public long insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		return mrqsPreguntasFtaDao.insert(pMrqsPreguntasFtaDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsPreguntasFtaDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		mrqsPreguntasFtaDao.update(pNumero, pMrqsPreguntasFtaDto);
	}

	@Override
	public long findNumeroFtaByNumeroHdr(long pNumeroHdr) {
		return mrqsPreguntasFtaDao.findNumeroFtaByNumeroHdr(pNumeroHdr);
	}

	@Override
	public MrqsPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta) {
		return mrqsPreguntasFtaDao.findDtoByNumeroFta(pNumeroFta);
	}

	@Override
	public long copyPaste(long pNumero, MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		return mrqsPreguntasFtaDao.copyPaste(pNumero, pMrqsPreguntasHdrDto);
	}

	@Override
	public long insert(MrqsPreguntasFtaV1 pMrqsPreguntasFtaV1) {
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = new MrqsPreguntasFtaDto();
		mrqsPreguntasFtaDto.setNumeroHdr(pMrqsPreguntasFtaV1.getNumeroHdr());
		mrqsPreguntasFtaDto.setTitulo(pMrqsPreguntasFtaV1.getTitulo());
		mrqsPreguntasFtaDto.setCreadoPor(pMrqsPreguntasFtaV1.getCreadoPor());
		mrqsPreguntasFtaDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pMrqsPreguntasFtaV1.getFechaCreacion()));
		mrqsPreguntasFtaDto.setActualizadoPor(pMrqsPreguntasFtaV1.getActualizadoPor());
		mrqsPreguntasFtaDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pMrqsPreguntasFtaV1.getFechaActualizacion()));
		mrqsPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		mrqsPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		mrqsPreguntasFtaDto.setMetodoPuntuacion(pMrqsPreguntasFtaV1.getMetodoPuntuacion());
		mrqsPreguntasFtaDto.setRespuestaCorrecta(pMrqsPreguntasFtaV1.getRespuestaCorrecta());
		mrqsPreguntasFtaDto.setNombreImagen(pMrqsPreguntasFtaV1.getNombreImagen());
	    mrqsPreguntasFtaDto.setContentType(pMrqsPreguntasFtaV1.getContentType());
	    mrqsPreguntasFtaDto.setTextoPregunta(pMrqsPreguntasFtaV1.getValorPuntuacion());
		mrqsPreguntasFtaDto.setValorPuntuacion(pMrqsPreguntasFtaV1.getValorPuntuacion());
		mrqsPreguntasFtaDto.setTextoSugerencias(pMrqsPreguntasFtaV1.getTextoSugerencias());
		mrqsPreguntasFtaDto.setSingleAnswerMode(pMrqsPreguntasFtaV1.isSingleAnswerMode());
		mrqsPreguntasFtaDto.setSuffleAnswerOrder(pMrqsPreguntasFtaV1.isSuffleAnswerOrder());
		mrqsPreguntasFtaDto.setValorPuntuacion(pMrqsPreguntasFtaV1.getValorPuntuacion());
		mrqsPreguntasFtaDto.setRutaImagen(Utilitarios.FS_MRQS+"\\"+pMrqsPreguntasFtaV1.getNumeroHdr());
		mrqsPreguntasFtaDto.setPoligonos(pMrqsPreguntasFtaV1.getPoligonos());
		mrqsPreguntasFtaDto.setWidth(pMrqsPreguntasFtaV1.getWidth());
		mrqsPreguntasFtaDto.setHeight(pMrqsPreguntasFtaV1.getHeight());
		mrqsPreguntasFtaDao.insert(mrqsPreguntasFtaDto); 
		pMrqsPreguntasFtaV1.setNumero(mrqsPreguntasFtaDto.getNumero());	
		
		File directory =new File(mrqsPreguntasFtaDto.getRutaImagen()); 
		directory.mkdirs(); 
		File destination = new File(mrqsPreguntasFtaDto.getRutaImagen()+"\\"+mrqsPreguntasFtaDto.getNombreImagen()); 
	    
	    try {
			copy(pMrqsPreguntasFtaV1.getImagenContent(),destination);
		} catch (IOException ie) {
		   System.out.println("IOException MrqsPreguntasFtaLocalImpl.insert "+ie.getMessage());
		} 	
		
		return mrqsPreguntasFtaDto.getNumero();
	}
	
	private void copy(byte[] pBytes, File destination)  throws IOException{
		try (   InputStream in =  new ByteArrayInputStream(pBytes);
				OutputStream out = new BufferedOutputStream(new FileOutputStream(destination)))
		        {
				 byte[] buffer = new byte[1024];
				 int lengthRead;
				 while ((lengthRead = in.read(buffer)) > 0) {
				    out.write(buffer,0,lengthRead);
				    out.flush();
				 }
		
     	}
	}

	@Override
	public MrqsPreguntasFtaV1 findObjModByNumeroFta(long pNumeroFta) {
		MrqsPreguntasFtaV1 revtal = new MrqsPreguntasFtaV1(); 
		
		MrqsPreguntasFtaV1Dto mrqsPreguntasFtaV1Dto = mrqsPreguntasFtaDao.findV1DtoByNumeroFta(pNumeroFta); 
		
		revtal.setTextoPregunta(mrqsPreguntasFtaV1Dto.getTextoPregunta());
		revtal.setTextoSugerencias(mrqsPreguntasFtaV1Dto.getTextoSugerencias());
		revtal.setRespuestaCorrecta(mrqsPreguntasFtaV1Dto.getRespuestaCorrecta());
		revtal.setSingleAnswerMode(mrqsPreguntasFtaV1Dto.isSingleAnswerMode());
		revtal.setSuffleAnswerOrder(mrqsPreguntasFtaV1Dto.isSuffleAnswerOrder());
		revtal.setMetodoPuntuacion(mrqsPreguntasFtaV1Dto.getMetodoPuntuacion());
		revtal.setValorPuntuacion(mrqsPreguntasFtaV1Dto.getValorPuntuacion());
		
		return revtal;
	}

	@Override
	public MrqsPreguntasFtaV1 findObjModByNumeroFta(long pNumeroFta, String pTipoPregunta) {
        MrqsPreguntasFtaV1 revtal = new MrqsPreguntasFtaV1(); 
		
        MrqsPreguntasFtaDto mrqsPreguntasFtaDto = mrqsPreguntasFtaDao.findDtoByNumeroFta(pNumeroFta); 
        
		revtal.setTextoPregunta(mrqsPreguntasFtaDto.getTextoPregunta());
		revtal.setTextoSugerencias(mrqsPreguntasFtaDto.getTextoSugerencias());
		revtal.setRespuestaCorrecta(mrqsPreguntasFtaDto.getRespuestaCorrecta());
		revtal.setSingleAnswerMode(mrqsPreguntasFtaDto.isSingleAnswerMode());
		revtal.setSuffleAnswerOrder(mrqsPreguntasFtaDto.isSuffleAnswerOrder());
		revtal.setMetodoPuntuacion(mrqsPreguntasFtaDto.getMetodoPuntuacion());
		revtal.setValorPuntuacion(mrqsPreguntasFtaDto.getValorPuntuacion());
		
		if(Utilitarios.IMAGEN_INDICADA.equals(pTipoPregunta)) {
			
			try {
				byte[] bytesArray = Files.readAllBytes(Paths.get(Utilitarios.FS_ROOT+mrqsPreguntasFtaDto.getRutaImagen()+"\\"+mrqsPreguntasFtaDto.getNombreImagen()));
				revtal.setImagenContent(bytesArray);
				revtal.setImagenBase64(new String(Base64.getEncoder().encode(bytesArray)));
				revtal.setNombreImagen(mrqsPreguntasFtaDto.getNombreImagen());
				revtal.setContentType(mrqsPreguntasFtaDto.getContentType());
				revtal.setPoligonos(mrqsPreguntasFtaDto.getPoligonos());
				revtal.setRutaImagen(mrqsPreguntasFtaDto.getRutaImagen());
			} catch (IOException ie) {
			   System.out.println("IOException MrqsPreguntasFtaLocalImpl findObjModByNumeroFta:"+ie.getMessage());
			}
		
			BufferedImage readImage = null;

			try {
			    readImage = ImageIO.read(new File(Utilitarios.FS_ROOT+mrqsPreguntasFtaDto.getRutaImagen()+"\\"+mrqsPreguntasFtaDto.getNombreImagen()));
			    int h = readImage.getHeight();
			    int w = readImage.getWidth();
			    revtal.setHeight(h);
			    revtal.setWidth(w);
			} catch (Exception e) {
				revtal.setHeight(mrqsPreguntasFtaDto.getHeight());
			    revtal.setWidth(mrqsPreguntasFtaDto.getWidth());
				System.out.println("A veces manda excepcion por eso se agregaron 2 campos extras"); 
				System.out.println("IOException MrqsPreguntasFtaLocalImpl findObjModByNumeroFta ImageIO.read:"+e.getMessage());
				readImage = null;
			}
			
		}
		
		return revtal;
	}

	@Override
	public void update(long pNumeroFta
			         , MrqsPreguntasFtaV1 pMrqsPreguntasFtaV1) {
        MrqsPreguntasFtaDto mrqsPreguntasFtaDto = new MrqsPreguntasFtaDto(); 	
  		mrqsPreguntasFtaDto.setNumeroHdr(pMrqsPreguntasFtaV1.getNumeroHdr());
		mrqsPreguntasFtaDto.setMetodoPuntuacion(pMrqsPreguntasFtaV1.getMetodoPuntuacion());
		mrqsPreguntasFtaDto.setRespuestaCorrecta(pMrqsPreguntasFtaV1.getRespuestaCorrecta());
		mrqsPreguntasFtaDto.setTextoPregunta(pMrqsPreguntasFtaV1.getTextoPregunta());
		mrqsPreguntasFtaDto.setValorPuntuacion(pMrqsPreguntasFtaV1.getValorPuntuacion());
		mrqsPreguntasFtaDto.setTextoSugerencias(pMrqsPreguntasFtaV1.getTextoSugerencias());
		mrqsPreguntasFtaDto.setSingleAnswerMode(pMrqsPreguntasFtaV1.isSingleAnswerMode());
		mrqsPreguntasFtaDto.setSuffleAnswerOrder(pMrqsPreguntasFtaV1.isSuffleAnswerOrder());
 	    
		mrqsPreguntasFtaDto.setNombreImagen(pMrqsPreguntasFtaV1.getNombreImagen());
		mrqsPreguntasFtaDto.setContentType(pMrqsPreguntasFtaV1.getContentType());
		mrqsPreguntasFtaDto.setPoligonos(pMrqsPreguntasFtaV1.getPoligonos());
		mrqsPreguntasFtaDto.setWidth(pMrqsPreguntasFtaV1.getWidth());
		mrqsPreguntasFtaDto.setHeight(pMrqsPreguntasFtaV1.getHeight());
        mrqsPreguntasFtaDao.update(pNumeroFta, mrqsPreguntasFtaDto);	
        
       File destination = new File(pMrqsPreguntasFtaV1.getRutaImagen()+"\\"+pMrqsPreguntasFtaV1.getNombreImagen()); 
	    
	    try {
			copy(pMrqsPreguntasFtaV1.getImagenContent(),destination);
		} catch (IOException ie) {
		   System.out.println("IOException MrqsPreguntasFtaLocalImpl.update "+ie.getMessage());
		} 	
        
	}

}
