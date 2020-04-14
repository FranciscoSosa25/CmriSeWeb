package com.cmrise.dicom.image;

import org.apache.log4j.Logger;

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
            Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("DICOM");

	        if (null == inputStream) {

	            log.error("inputStream is null");

	            throw new InvalidParameterException("inputStream", "inputStream is null");
	        }

	        while (iterator.hasNext()) {

                ImageReader imageReader = iterator.next();

	            ImageReadParam imageReadParam = imageReader.getDefaultReadParam();

	            try {
	                ImageInputStream iss = ImageIO.createImageInputStream(inputStream);

	                imageReader.setInput(iss, false);

                    BufferedImage result = imageReader.read(0, imageReadParam);

	                iss.close();

	                inputStream.close();

	                if (null == result) {

	                    log.error("Could not read image");
	                }

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