package com.cmrise.dicom;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.cmrise.dicom.attribute.DicomAttributeReaderService;
import com.cmrise.dicom.attribute.DicomProperties;
import com.cmrise.dicom.exception.InvalidParamException;
import com.cmrise.dicom.image.DicomImageReaderService;
import com.cmrise.dicom.image.SaveJpegFileService;

public class EjemploUsoDCM {

	private DicomAttributeReaderService dicomAttributeReaderService = new DicomAttributeReaderService();
	private DicomImageReaderService dicomImageReaderService = new DicomImageReaderService();
	private SaveJpegFileService saveJpegFileService = new SaveJpegFileService();
	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	public static final String RUTA_IMAGEN_DCM = "c:\\rutaDondeGuardarLaImagenDelArchivoDicom\\NombreDelArchivo.jpg";

	public void leerArchivoDicom() {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("c:\\rutaDelArchivoDCM\\archivo.dcm");
			setOutputStream(inputStream);
			//Validate the file
			String xml = dicomAttributeReaderService.readAttributes(getInputStream());
			
			//Get the dicom attributes
			DicomProperties properties = dicomAttributeReaderService.translateData(dicomAttributeReaderService.translateData(xml));
			
			//Read the image
			BufferedImage image = dicomImageReaderService.generateBufferedImage(getInputStream());
			
			File jpegFile = saveJpegFile(image, properties);
			
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
	
	private File saveJpegFile(BufferedImage image, DicomProperties properties) throws IOException, InvalidParamException {
        File jpegFile = new File(RUTA_IMAGEN_DCM);
        saveJpegFileService.saveJpegFile(properties, jpegFile, image, false);
        return jpegFile;
    }
}
