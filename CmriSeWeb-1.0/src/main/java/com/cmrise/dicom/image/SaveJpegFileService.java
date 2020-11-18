package com.cmrise.dicom.image;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.cmrise.dicom.attribute.DicomProperties;
import com.cmrise.dicom.exception.InvalidParamException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveJpegFileService {

    private final static Logger log = Logger.getLogger(SaveJpegFileService.class);

    private static final String THUMBNAIL_SUFFIX = "_thumbnail.jpg";
    
    public SaveJpegFileService() {
		super();
	}

	public void saveJpegFile(DicomProperties dicomProperties, File destination, BufferedImage bufferedImage, boolean overwrite) throws InvalidParamException, IOException {
        log.info("Call to saveAsJPEG with destination:" + (destination == null ? "NULL" : destination.getAbsolutePath()) +
            " overwrite:" + overwrite);
        if (destination == null) {
            throw new InvalidParamException("Null value at destination");
        }
        if (bufferedImage == null) {
            throw new InvalidParamException("Null value at bufferedImage");
        }
        if (dicomProperties == null) {
            throw new InvalidParamException("Null value at dicomProperties");
        }
        log.debug("Properties of the file" + dicomProperties.toString());
        if (destination.exists()) {
            if (overwrite) {
                log.debug("Deleting an existing file");
                destination.delete();
            } else {
                return;
            }
        }
        try {
            ImageIO.write(bufferedImage, "jpg", destination);

            // Se agrega el prefijo _thumbnail a la ruta original
            File destinationThumbnail = new File(StringUtils
                .substringBeforeLast(destination.getAbsolutePath(), ".").concat(THUMBNAIL_SUFFIX));

            // Escribe la imagen thumbnail.
            ImageIO.write(this.generateThumbnailImage(bufferedImage,
                96, 96, BufferedImage.TYPE_INT_RGB), "jpg", destinationThumbnail);

        } catch (IOException e) {
            log.error("Error saving file to " + destination.getAbsolutePath(), e);
            throw e;
        }


    }

    private BufferedImage generateThumbnailImage(
        BufferedImage originalImage, int width, int height, int type) {
        BufferedImage thumbnailImage = new BufferedImage(width, height, type);
        Graphics2D graphics2d = thumbnailImage.createGraphics();
        graphics2d.drawImage(originalImage, 0, 0, width, height, null);
        graphics2d.dispose();

        return thumbnailImage;
    }
}