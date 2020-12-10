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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

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
			mrqsImagenes.setRutaImagen(Utilitarios.FS_MRQS+File.separator+pNumetoFta+File.separator+numeroImagenesGrp);
			long numeroMrqImagen = mrqsImagenesDao.insert(numeroImagenesGrp,mrqsImagenes); 
			pMrqsImagenesGrp.setNumero(numeroMrqImagen);
			File directory =new File(mrqsImagenes.getRutaImagen()); 
			directory.mkdirs(); 
			File destination = new File(mrqsImagenes.getRutaImagen()+File.separator+mrqsImagenes.getNombreImagen());
		    
		    try {
				copy(mrqsImagenes.getImagenContent(),destination);
			} catch (IOException ie) {
			   System.out.println("IOException MrqsImagenesGrpLocalImpl.insert "+ie.getMessage());
			} 	
		
		}
	}
	
	public long eliminarImagen(long pNumeroFta, MrqsImagenesGrp pMrqsImagenesGrp,MrqsImagenes item) throws Exception {
		List<MrqsImagenesGrpDto> list=	mrqsImagenesGrpDao.findByFta(pNumeroFta,pMrqsImagenesGrp.getSeccion()); 
		long rs=-1;
		if(list!=null&&list.size()>0) {
			long grpNum=list.get(0).getNumero();
			rs=mrqsImagenesDao.eliminar(item.getNumero(),grpNum);
			String ruta=Utilitarios.FS_MRQS+File.separator+pNumeroFta+File.separator+grpNum+File.separator+item.getNumero();
			File destination = new File(ruta+File.separator+item.getNombreImagen());
			try {
		    boolean e=	destination.exists();
			boolean r=destination.delete();
			rs=1;
			}
			catch(Exception e) {
				throw new Exception();
			}
		}return rs;
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
				mrqsImagenes.setRutaImagen(Utilitarios.FS_ROOT+j.getRutaImagen());
				try {
					byte[] bytesArray = Files.readAllBytes(Paths.get(Utilitarios.FS_ROOT+j.getRutaImagen()+File.separator+j.getNombreImagen()));
					mrqsImagenes.setImagenContent(bytesArray);
					mrqsImagenes.setImagen(cargarImagen(bytesArray,j.getNombreImagen()));
					mrqsImagenes.setImagenBase64(new String(Base64.getEncoder().encode(bytesArray)));
				} catch (IOException ie) {
				   System.out.println("IOException :"+ie.getMessage());
				}
				mrqsImagenes.setContentType(j.getContentType());
				listMrqsImagenes.add(mrqsImagenes);
			}
			mrqsImagenesGrp.setListMrqsImagenes(listMrqsImagenes);
			retval.add(mrqsImagenesGrp); 
		}
		return retval;
	}
  private StreamedContent cargarImagen(byte contents[],String nombre ) {
		StreamedContent file=null;
		try {
			
			 file = DefaultStreamedContent.builder()
                    .name(nombre)
                    .contentType("application/octet-stream")
                    .stream(() -> new ByteArrayInputStream(contents)).build();

		} catch (Exception e) {
		
		}
		return file;
	}
	
}
