package com.cmrise.dicom.image;

import org.apache.log4j.Logger;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;

import com.cmrise.dicom.exception.CantReadImageFromDicomFileException;
import com.cmrise.dicom.exception.InvalidParameterException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Iterator;

public class DicomImageReaderService {

    private final static Logger log = Logger.getLogger(DicomImageReaderService.class);
    
    public DicomImageReaderService() {
		super();
	}

	public BufferedImage generateBufferedImage(InputStream inputStream) throws  InvalidParameterException, CantReadImageFromDicomFileException {

        log.info("Call to generateBufferedImage with " );

        try {
        	 log.info("DicomImageReaderService generateBufferedImage Paso1");
        	Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("DICOM");
        	
        	System.out.println("iterator:"+iterator);
        	
            log.info("DicomImageReaderService generateBufferedImage Paso2");
	        if (null == inputStream) {

	            log.error("inputStream is null");

	            throw new InvalidParameterException("inputStream", "inputStream is null");
	        }

	        System.out.println("iterator.hasNext():"+iterator.hasNext());
	        
	        while (iterator.hasNext()) {
              
                ImageReader imageReader = iterator.next();
                DicomImageReadParam imageReadParam = (DicomImageReadParam)imageReader.getDefaultReadParam();
	            /**ImageReadParam imageReadParam = imageReader.getDefaultReadParam(); **/
                System.out.println("imageReadParam:"+imageReadParam);
	            try {
	            	 log.info("DicomImageReaderService generateBufferedImage Paso3");
	                ImageInputStream iss = ImageIO.createImageInputStream(inputStream);
	                log.info("DicomImageReaderService generateBufferedImage Paso4");
	                imageReader.setInput(iss, false);

	                log.info("DicomImageReaderService generateBufferedImage Paso5");
                    BufferedImage result = imageReader.read(0, imageReadParam);
                    log.info("DicomImageReaderService generateBufferedImage Paso6");

	                iss.close();

	                inputStream.close();

	                if (null == result) {

	                    log.error("Could not read image");
	                }

	                log.error("Sale DicomImageReaderService generateBufferedImage");
                    return result;

	            } catch (Exception e) {

	                log.error("Error getting image " , e);

	                throw new CantReadImageFromDicomFileException(e);
	            }
	        }

        } catch (Exception e) {

        	log.error("Error getting image by format DICOM " , e);

        	log.error("Message "+ e.getMessage() +"\n - "+e.getLocalizedMessage());

        	throw new CantReadImageFromDicomFileException(e);
		}

		return null;
    }

}