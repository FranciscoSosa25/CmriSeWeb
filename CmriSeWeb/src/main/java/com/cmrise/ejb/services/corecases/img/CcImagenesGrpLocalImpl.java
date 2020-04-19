package com.cmrise.ejb.services.corecases.img;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.dicom.attribute.DicomAttributeReaderService;
import com.cmrise.dicom.attribute.DicomProperties;
import com.cmrise.dicom.exception.InvalidParamException;
import com.cmrise.dicom.image.DicomImageReaderService;
import com.cmrise.dicom.image.SaveJpegFileService;
import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.jpa.dao.corecases.img.CcImagenesDao;
import com.cmrise.jpa.dao.corecases.img.CcImagenesGrpDao;
import com.cmrise.jpa.dto.corecases.img.CcImagenesDto;
import com.cmrise.jpa.dto.corecases.img.CcImagenesGrpDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcImagenesGrpLocalImpl implements CcImagenesGrpLocal {

	private DicomAttributeReaderService dicomAttributeReaderService = new DicomAttributeReaderService();
	private DicomImageReaderService dicomImageReaderService = new DicomImageReaderService();
	private SaveJpegFileService saveJpegFileService = new SaveJpegFileService();
	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	@Inject 
	CcImagenesGrpDao ccImagenesGrpDao; 
	
	@Inject 
	CcImagenesDao ccImagenesDao; 
	
	@Override
	public long insert(CcImagenesGrpDto pCcImagenesGrpDto) {
		return ccImagenesGrpDao.insert(pCcImagenesGrpDto);
	}

	@Override
	public void insert(long pNumetoFta, CcImagenesGrp pCcImagenesGrp) {
		CcImagenesGrpDto ccImagenesGrpDto = new CcImagenesGrpDto();
		ccImagenesGrpDto.setNumeroFta(pNumetoFta);
		ccImagenesGrpDto.setTipo(pCcImagenesGrp.getTipo());
		ccImagenesGrpDto.setSeccion(pCcImagenesGrp.getSeccion());
		ccImagenesGrpDto.setTituloSuperior(pCcImagenesGrp.getTituloSuperior());
		ccImagenesGrpDto.setTituloInferior(pCcImagenesGrp.getTituloInferior());
		long numeroImagenesGrp = insert(ccImagenesGrpDto);
		for(CcImagenes ccImagenes:pCcImagenesGrp.getListCcImagenes()) {
			/****************************************************************************************
			 **************************************************************************************** COMIENZA DCM
			 */
			ccImagenes.setRutaImagen(Utilitarios.FS_CORE_CASES+"\\"+pNumetoFta+"\\"+numeroImagenesGrp);
			System.out.println("V1 mrqsImagenes.getRutaImagen():"+ccImagenes.getRutaImagen());
			ccImagenesDao.insert(numeroImagenesGrp,ccImagenes); 
			System.out.println("V2 mrqsImagenes.getRutaImagen():"+ccImagenes.getRutaImagen());
			System.out.println("*");
			File directory =new File(ccImagenes.getRutaImagen()); 
			directory.mkdirs(); 
			String strRutaImgDcm = ccImagenes.getRutaImagen()+"\\"+ccImagenes.getNombreImagen(); 
			File destination = new File(strRutaImgDcm); 
		    System.out.println("**");
		    System.out.println("mrqsImagenes.getImagenContent():"+ccImagenes.getImagenContent());
		   
		    try {
				copy(ccImagenes.getImagenContent(),destination);
			} catch (IOException ie) {
			   System.out.println("IOException MrqsImagenesGrpLocalImpl.insert "+ie.getMessage());
			} 	
		    /****************************************************************************************
		     **************************************************************************************** FINALIZA DCM 
		     */
		    
		    String strRutaImgJpg =ccImagenes.getRutaImagen()+"\\"+ccImagenes.getNombreImagen().replace(".dcm", ".jpg"); 
		    
		    InputStream inputStream;
			try {
				inputStream = new FileInputStream(ccImagenes.getRutaImagen()+"\\"+ccImagenes.getNombreImagen());
				setOutputStream(inputStream);
				//Validate the file
				String xml = dicomAttributeReaderService.readAttributes(getInputStream());
				if(null!=xml) {
					System.out.println("xml.length():"+xml.length());
				}else {
					System.out.println("xml:"+xml);
				}
				//Get the dicom attributes
				DicomProperties properties = dicomAttributeReaderService.translateData(dicomAttributeReaderService.translateData(xml));
				
				//Read the image
				BufferedImage image = dicomImageReaderService.generateBufferedImage(getInputStream());
				System.out.println("image:"+image);
				File jpegFile = saveJpegFile(image, properties,strRutaImgJpg);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		
		}
	}

	@Override
	public List<CcImagenesGrp> findByFta(long pNumeroFta, String pSeccion) {
        List<CcImagenesGrp> retval = new ArrayList<CcImagenesGrp>(); 
		
		List<CcImagenesGrpDto> listCcImagenesGrpDto = ccImagenesGrpDao.findByFta(pNumeroFta
				                                                                ,pSeccion
				                                                                ); 
		for(CcImagenesGrpDto i:listCcImagenesGrpDto) {
			CcImagenesGrp ccImagenesGrp = new CcImagenesGrp();
			ccImagenesGrp.setNumero(i.getNumero());
			ccImagenesGrp.setSeccion(i.getSeccion());
			ccImagenesGrp.setTipo(i.getTipo());
			ccImagenesGrp.setTituloSuperior(i.getTituloSuperior());
			ccImagenesGrp.setTituloInferior(i.getTituloInferior());
			ccImagenesGrp.setTexto(i.getTexto());
			
			List<CcImagenes> listCcImagenes = new ArrayList<CcImagenes>(); 
			List<CcImagenesDto> listCcImagenesDto = ccImagenesDao.findByGrp(i.getNumero()); 
			for(CcImagenesDto j:listCcImagenesDto) {
				CcImagenes ccImagenes = new CcImagenes(); 
				ccImagenes.setNumero(j.getNumero());
				ccImagenes.setNumeroGrp(j.getNumeroGrp());
				ccImagenes.setNombreImagen(j.getNombreImagen());
				ccImagenes.setRutaImagen(j.getRutaImagen());
				String strJpgRuta  = j.getRutaImagen()+"\\"+j.getNombreImagen().replace(".dcm", Utilitarios.JPG_SUFFIX); 
				String strThumbailRuta  = j.getRutaImagen()+"\\"+j.getNombreImagen().replace(".dcm", Utilitarios.THUMBNAIL_SUFFIX); 
				try {
					/** byte[] bytesArray = Files.readAllBytes(Paths.get(j.getRutaImagen()+"\\"+j.getNombreImagen())); **/
					/** ccImagenes.setImagenContent(bytesArray); **/
					byte[] bytesArray = Files.readAllBytes(Paths.get(strJpgRuta));
					ccImagenes.setJpgContent(bytesArray);
					ccImagenes.setJpgBase64(new String(Base64.getEncoder().encode(bytesArray)));
					bytesArray = Files.readAllBytes(Paths.get(strThumbailRuta));
					ccImagenes.setThumbailContent(bytesArray);
					ccImagenes.setThumbailBase64(new String(Base64.getEncoder().encode(bytesArray)));
				} catch (IOException ie) {
				   System.out.println("IOException :"+ie.getMessage());
				}
				listCcImagenes.add(ccImagenes);
			}
			ccImagenesGrp.setListCcImagenes(listCcImagenes);
			retval.add(ccImagenesGrp); 
		}
		return retval;
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
	
	
	public static void setOutputStream(InputStream inputStream) throws IOException {
		outputStream.reset();
		int len;
		byte[] inputStreamBuffer = new byte[1024];

		while ((len = inputStream.read(inputStreamBuffer)) > -1) {
			outputStream.write(inputStreamBuffer, 0, len);
		}

		outputStream.flush();
	}
	
	private static InputStream getInputStream() {

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
	
	private File saveJpegFile(BufferedImage image
			               , DicomProperties properties
			               , String pRutaImgJpg) throws IOException, InvalidParamException {
        File jpegFile = new File(pRutaImgJpg);
        saveJpegFileService.saveJpegFile(properties, jpegFile, image, false);
        return jpegFile;
    }
	
	
}
