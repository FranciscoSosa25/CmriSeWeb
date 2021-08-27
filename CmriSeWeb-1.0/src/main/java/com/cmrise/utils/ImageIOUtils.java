package com.cmrise.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.cmrise.dicom.exception.CantReadImageFromDicomFileException;
import com.cmrise.dicom.exception.InvalidParameterException;

public class ImageIOUtils {
	
    private final static Logger log = Logger.getLogger(ImageIOUtils.class);

	
	private ImageIOUtils() {
		throw new IllegalArgumentException("ImageIOUtils ");
	}
	
	public static BufferedImage  generateBufferedImage(File file) throws IOException{
		
		FileFormat fileFormat = FileFormat.getFileFormat(file.getAbsolutePath());
			return generateBufferedImage(new FileInputStream(file), fileFormat.toString());
		
	}
	
	private static enum FileFormat {
		JPEG, PNG, JPG, JPE;
		
		public static FileFormat getFileFormat(String path) {
			FileFormat fileFormat = null;
			try {
				if(path !=null && !"".equals(path)) {
					fileFormat = FileFormat.valueOf(FilenameUtils.getExtension(path));
				}
			}catch (RuntimeException e) {
				fileFormat = JPG;
			}
			
			return fileFormat;
		}
		
	}

	
	public static BufferedImage  generateBufferedImage(InputStream inputStream, String format) {
        log.debug("Call to generateBufferedImage with "+format );

        try {
        	log.debug("generateBufferedImage Paso1");
        	Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(format);
            if (null == inputStream) {
	            log.error("inputStream is null");
	            throw new InvalidParameterException("inputStream", "inputStream is null");
	        }

	        while (iterator.hasNext()) {
              
                ImageReader imageReader = iterator.next();
                ImageReadParam imageReadParam = imageReader.getDefaultReadParam();
	            try{
	            
	                ImageInputStream iss = ImageIO.createImageInputStream(inputStream);
	                log.info(" generateBufferedImage read streem");
	                imageReader.setInput(iss, false);

	                log.info(" generateBufferedImage set input");
                    BufferedImage result = imageReader.read(0, imageReadParam);
                    log.info("generateBufferedImage completed");
                    System.out.println(result.getHeight());
                    System.out.println(result.getWidth());
	                iss.close();
	                inputStream.close();
	                return result;

	            } catch (RuntimeException | IOException  e) {
	                log.error("Error getting image " , e);
	            }
	        }

        } catch (RuntimeException | InvalidParameterException e) {
        	log.error("Error getting image by format "+format , e);
        	log.error("Message "+ e.getMessage() +"\n - "+e.getLocalizedMessage());

		} 

		return null;
    }
}
