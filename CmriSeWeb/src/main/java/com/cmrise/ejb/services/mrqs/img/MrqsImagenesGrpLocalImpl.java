package com.cmrise.ejb.services.mrqs.img;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.io.FileOutputStream; 

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.mrqs.img.MrqsImagenes;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesDao;
import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesGrpDao;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesDto;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesGrpDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsImagenesGrpLocalImpl implements MrqsImagenesGrpLocal {

	@Inject 
	MrqsImagenesGrpDao mrqsImagenesGrpDao; 
	
	@Inject 
	MrqsImagenesDao mrqsImagenesDao; 
	
	@Override
	public long insert(MrqsImagenesGrpDto pMrqsImagenesGrpDto) {
		return mrqsImagenesGrpDao.insert(pMrqsImagenesGrpDto);
	}

	@Override
	public void insert(long pNumetoFta
			         , MrqsImagenesGrp pMrqsImagenesGrp) {
		MrqsImagenesGrpDto mrqsImagenesGrpDto = new MrqsImagenesGrpDto();
		mrqsImagenesGrpDto.setNumeroFta(pNumetoFta);
		mrqsImagenesGrpDto.setTipo(pMrqsImagenesGrp.getTipo());
		mrqsImagenesGrpDto.setSeccion(pMrqsImagenesGrp.getSeccion());
		mrqsImagenesGrpDto.setTituloSuperior(pMrqsImagenesGrp.getTituloSuperior());
		mrqsImagenesGrpDto.setTituloInferior(pMrqsImagenesGrp.getTituloInferior());
		long numeroImagenesGrp = insert(mrqsImagenesGrpDto);
		for(MrqsImagenes mrqsImagenes:pMrqsImagenesGrp.getListMrqsImagenes()) {
			mrqsImagenes.setRutaImagen(Utilitarios.FS_MRQS+"\\"+pNumetoFta+"\\"+numeroImagenesGrp);
			System.out.println("V1 mrqsImagenes.getRutaImagen():"+mrqsImagenes.getRutaImagen());
			mrqsImagenesDao.insert(numeroImagenesGrp,mrqsImagenes); 
			System.out.println("V2 mrqsImagenes.getRutaImagen():"+mrqsImagenes.getRutaImagen());
			System.out.println("*");
			File directory =new File(mrqsImagenes.getRutaImagen()); 
			directory.mkdirs(); 
			File destination = new File(mrqsImagenes.getRutaImagen()+"\\"+mrqsImagenes.getNombreImagen()); 
		    System.out.println("**");
		    System.out.println("mrqsImagenes.getImagenContent():"+mrqsImagenes.getImagenContent());
		   
		    try {
				copy(mrqsImagenes.getImagenContent(),destination);
			} catch (IOException ie) {
			   System.out.println("IOException MrqsImagenesGrpLocalImpl.insert "+ie.getMessage());
			} 	
		
		}
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
	public List<MrqsImagenesGrp> findByFta(long pNumeroFta
			                             , String pSeccion) {
		List<MrqsImagenesGrp> retval = new ArrayList<MrqsImagenesGrp>(); 
		
		List<MrqsImagenesGrpDto> listMrqsImagenesGrpDto = mrqsImagenesGrpDao.findByFta(pNumeroFta
				                                                                      ,pSeccion
				                                                                      ); 
		for(MrqsImagenesGrpDto i:listMrqsImagenesGrpDto) {
			MrqsImagenesGrp mrqsImagenesGrp = new MrqsImagenesGrp();
			mrqsImagenesGrp.setNumero(i.getNumero());
			mrqsImagenesGrp.setSeccion(i.getSeccion());
			mrqsImagenesGrp.setTipo(i.getTipo());
			mrqsImagenesGrp.setTituloSuperior(i.getTituloSuperior());
			mrqsImagenesGrp.setTituloInferior(i.getTituloInferior());
			mrqsImagenesGrp.setTexto(mrqsImagenesGrp.getTexto());
			
			List<MrqsImagenes> listMrqsImagenes = new ArrayList<MrqsImagenes>(); 
			List<MrqsImagenesDto> listMrqsImagenesDto = mrqsImagenesDao.findByGrp(i.getNumero()); 
			for(MrqsImagenesDto j:listMrqsImagenesDto) {
				MrqsImagenes mrqsImagenes = new MrqsImagenes(); 
				mrqsImagenes.setNumero(j.getNumero());
				mrqsImagenes.setNumeroGrp(j.getNumeroGrp());
				mrqsImagenes.setNombreImagen(j.getNombreImagen());
				mrqsImagenes.setRutaImagen(j.getRutaImagen());
				try {
					byte[] bytesArray = Files.readAllBytes(Paths.get(j.getRutaImagen()+"\\"+j.getNombreImagen()));
					mrqsImagenes.setImagenContent(bytesArray);
					mrqsImagenes.setImagenBase64(new String(Base64.getEncoder().encode(bytesArray)));
				} catch (IOException ie) {
				   System.out.println("IOException :"+ie.getMessage());
				}
				listMrqsImagenes.add(mrqsImagenes);
			}
			mrqsImagenesGrp.setListMrqsImagenes(listMrqsImagenes);
			retval.add(mrqsImagenesGrp); 
		}
		return retval;
	}

	
}
