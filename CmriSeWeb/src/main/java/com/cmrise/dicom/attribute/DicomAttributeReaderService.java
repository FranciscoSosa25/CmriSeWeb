package com.cmrise.dicom.attribute;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.SAXWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cmrise.dicom.exception.InvalidDicomFileException;
import com.cmrise.dicom.exception.InvalidParameterException;
import com.cmrise.utils.DateUtil;

public class DicomAttributeReaderService {

    private final static Logger log = Logger.getLogger(DicomAttributeReaderService.class);

    private static final String KEYWORD = "keyword";
    private static final String VALUE = "Value";
    private static final String XML_1_1 = "1.1";
    private static final String FORMAT_DATE_ALTERNATIVE_1 = "yyyyMMdd";
    private static final String FORMAT_DATE_ALTERNATIVE_2 = "yyyy.MM.dd";
    private static final String FORMAT_DATE_ALTERNATIVE_3 = "yyyy-MM-dd";
    private static final String FORMAT_DATE_TIME = "yyyyMMdd HHmmss";
    private static final String TAG_FAMILY_NAME = "FamilyName";
    private static final String TAG_GIVEN_NAME = "GivenName";
    private static final String DICOM_ATTRIBUTE = "DicomAttribute";
    private static final String ATTRIBUTE_SCAN_OPTIONS = "ScanOptions";
    private static final String ATTRIBUTE_STUDY_DATE = "StudyDate";
    private static final String ATTRIBUTE_MODALITY = "Modality";
    private static final String ATTRIBUTE_MANUFACTURER = "Manufacturer";
    private static final String ATTRIBUTE_PATIENT_NAME = "PatientName";
    private static final String ATTRIBUTE_INSTITUTION_NAME = "InstitutionName";
    private static final String ATTRIBUTE_PIXEL_DATA = "PixelData";
    private static final String ATTRIBUTE_PRIVATE_CREATOR_ID = "PrivateCreatorID";
    private static final String ATTRIBUTE_MEDIA_STORAGE_SOP_CLASS_UID = "MediaStorageSOPClassUID";
    private static final String ATTRIBUTE_MEDIA_STORAGE_SOP_INSTANCE_UID = "MediaStorageSOPInstanceUID";
    private static final String ATTRIBUTE_TRANSFER_SYNTAX_UID = "TransferSyntaxUID";
    private static final String ATTRIBUTE_SOP_CLASS_UID = "SOPClassUID";
    private static final String ATTRIBUTE_SOP_INSTANCE_UID = "SOPInstanceUID";
    private static final String ATTRIBUTE_PATIENT_ID = "PatientID";
    private static final String ATTRIBUTE_PATIENT_AGE = "PatientAge";
    private static final String ATTRIBUTE_BODY_PART_EXAMINED = "BodyPartExamined";
    private static final String ATTRIBUTE_DEVICE_SERIAL_NUMBER = "DeviceSerialNumber";
    private static final String ATTRIBUTE_STUDY_INSTANCE_UID = "StudyInstanceUID";
    private static final String ATTRIBUTE_SERIE_INSTANCE_UID = "SeriesInstanceUID";
    private static final String ATTRIBUTE_PATIENT_SEX = "PatientSex";
    private static final String ATTRIBUTE_PROTOCOL_NAME = "ProtocolName";
    private static final String ATTRIBUTE_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    private static final String ATTRIBUTE_MANUFACTURER_MODEL_NAME = "ManufacturerModelName";
    private static final String ATTRIBUTE_INSTITUTIONAL_DEPT_NAME = "InstitutionalDepartmentName";
    private static final String ATTRIBUTE_STUDY_NAME = "StudyName";
    private static final String ATTRIBUTE_STUDY_ID = "StudyID";
    private static final String ATTRIBUTE_SERIES_NUMBER = "SeriesNumber";
    private static final String ATTRIBUTE_STUDY_TIME = "StudyTime";
    private static final String ATTRIBUTE_REFERRING_PHYSICIAN = "ReferringPhysicianName";
    private static final String ATTRIBUTE_PERFORMING_PHYSICIAN = "PerformingPhysicianName";
    private static final String ATTRIBUTE_STUDY_DESCRIPTION = "StudyDescription";
    private static final String ATTRIBUTE_SERIES_DESCRIPTION = "SeriesDescription";
    private static final String ATTRIBUTE_PATIENT_BIRTH_DATE = "PatientBirthDate";
    public static final String CHAR_ESPACIO = " ";

    private String xsltURL;
    private String xmlVersion = XML_1_1;

    private boolean indent = true;
    private boolean includeKeyword = true;
    private boolean includeNamespaceDeclaration = false;
    private boolean catBlkFiles = false;

    private DicomInputStream.IncludeBulkData includeBulkData = DicomInputStream.IncludeBulkData.YES;
    
    
    

    public DicomAttributeReaderService() {
		super();
	}

	/**
     * Transforma un archivo dicom a XML como String 
     *
     * @param InputStream Objeto que contiene el archivo DCM en disco duro
     * @return XML con los atributos en String.
     */
    public String readAttributes(InputStream inputStream) throws Exception  {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (inputStream == null) {

            throw new InvalidParameterException("inputStream", "inputStream is null");
        }

        try {
            DicomInputStream dicomInputStream = new DicomInputStream(inputStream);

            convert(dicomInputStream, outputStream);

            String attributes = outputStream.toString();

            dicomInputStream.close();

            outputStream.flush();
            outputStream.close();

            return attributes;

        } catch (IOException e) {

            log.error("Error reading file ", e);

            throw new InvalidDicomFileException(e);
        }
    }

    /**
     * Lee el contenido de un archivo DCM y genera su XML
     *
     * @param dis          Stream que contienen el archivo dicom
     * @param outputStream Stream que contendra el XML
     */
    private void convert(DicomInputStream dis, OutputStream outputStream) {

        log.info("Call to convertToShowInUI");

        try {
            dis.setIncludeBulkData(includeBulkData);
            dis.setConcatenateBulkDataFiles(catBlkFiles);

            TransformerHandler th = getTransformerHandler();

            Transformer t = th.getTransformer();

            t.setOutputProperty(OutputKeys.INDENT, indent ? "yes" : "no");
            t.setOutputProperty(OutputKeys.VERSION, xmlVersion);

            th.setResult(new StreamResult(outputStream));

            SAXWriter saxWriter = new SAXWriter(th);

            saxWriter.setIncludeKeyword(includeKeyword);
            saxWriter.setIncludeNamespaceDeclaration(includeNamespaceDeclaration);

            dis.setDicomInputHandler(saxWriter);
            dis.readDataset(-1, -1);

        } catch (TransformerConfigurationException | IOException e) {

            log.error("Error generating image", e);
        }
    }

    private TransformerHandler getTransformerHandler() throws TransformerConfigurationException, IOException {

        log.debug("Call to getTransformerHandler");

        SAXTransformerFactory tf = (SAXTransformerFactory) TransformerFactory.newInstance();

        if (xsltURL == null) {

            return tf.newTransformerHandler();
        }

        return tf.newTransformerHandler(new StreamSource(xsltURL));
    }


    /**
     * Convierte un XML proveniente de un archivo dicom en objeto de tipo DicomAttribute     
     *
     * @param xmlString un String con el XML provenienten de un archivo dicom
     * @return Lista de objetos DicomAttribute proveniente del XML
     */
    public List<DicomAttribute> translateData(String xmlString) {

        log.info("Call to translateData with xmlString " + (xmlString == null ? "NULL" : xmlString.length() + " characters "));

        if (null == xmlString) {

            log.warn("XML String is null");

            return null;
        }

        List<DicomAttribute> result = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            InputSource inputSource = new InputSource(new StringReader(xmlString));

            Document doc = dBuilder.parse(inputSource);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName(DICOM_ATTRIBUTE);

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node nNode = nodeList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nNode;

                    String keyWord = element.getAttribute(KEYWORD);

                    if (keyWordToInclude(keyWord)) {

                        DicomAttribute attribute = this.getDicomAttribute(element, keyWord);

                        result.add(attribute);
                    }
                }
            }

        } catch (SAXException | ParserConfigurationException | IOException e) {

            log.error("Error generating list of attributes", e);
        }

        return result;
    }

    /** 
     * Lee el contenido de un XML y los regresa en un objeto DicomAttribute
     *
     * @param element Elemento XML
     * @param keyWord Palabra clave a encontrar
     * @return Objeto DicomAttribute con los valores del nodo identificado por la palabra clave
     */
    private DicomAttribute getDicomAttribute(Element element, String keyWord) {

        List<String> values = new ArrayList<>();

        if (keyWord.equals("PatientName")) {
            //Getting patient name custom value
            String customValue = this.findPatientName(element);

            values.add(customValue);

            return new DicomAttribute(keyWord, values);

        } else if (StringUtils.containsIgnoreCase(keyWord, "physician")) {

            NodeList subNodeList = element.getElementsByTagName(TAG_FAMILY_NAME);

            for (int i = 0; i < subNodeList.getLength(); i++) {

                Node subElement = subNodeList.item(i);

                values.add(subElement.getTextContent());
            }

            return new DicomAttribute(keyWord, values);

        } else {
            //Getting standard values
            NodeList subNodeList = element.getElementsByTagName(VALUE);

            for (int j = 0; j < subNodeList.getLength(); j++) {

                Node subElement = subNodeList.item(j);

                values.add(subElement.getTextContent());
            }

            return new DicomAttribute(keyWord, values);
        }
    }

    /**
     * Encuentra el nombre de un paciente en un elemento XML
     *
     * @param element Elemento XML
     * @return Un string que contienen el nombr edel paciente
     */
    private String findPatientName(Element element) {

        NodeList familyNameNode = element.getElementsByTagName(TAG_FAMILY_NAME);
        NodeList givenNameNode = element.getElementsByTagName(TAG_GIVEN_NAME);

        if (familyNameNode != null && familyNameNode.getLength() > 0) {

            if (givenNameNode != null && givenNameNode.getLength() > 0) {

                return givenNameNode.item(0).getTextContent().concat(
                    CHAR_ESPACIO).concat(familyNameNode.item(0).getTextContent());
            }

            return familyNameNode.item(0).getTextContent();
        }

        return null;
    }

    /**
     * Revisa si algunas palabras clave van a ser omitidas al leer
     * Como los datos de la imagen
     *
     * @param keyWord Keyword
     * @return True if the keyword is going to be read
     */
    private boolean keyWordToInclude(String keyWord) {

        //This keyword contains the image itself.
        if (keyWord.equals(ATTRIBUTE_PIXEL_DATA)) {

            return false;
        }

        //This keyword contains non human readable raw binary data.
        if (keyWord.equals(ATTRIBUTE_PRIVATE_CREATOR_ID)) {

            return false;
        }

        return true;
    }

    /**
     * Transforma una lista de atributos en un objeto util al programar
     *
     * @param attributes Los atributos a ser transformados
     * @return Las propiedades de un archivo dicom
     */
    public DicomProperties translateData(List<DicomAttribute> attributes) {

        DicomProperties dicomProperties = new DicomProperties();

        dicomProperties.setStudyDate(convertDate(attributes, ATTRIBUTE_STUDY_DATE));
        dicomProperties.setModality(findByName(attributes, ATTRIBUTE_MODALITY));
        dicomProperties.setManufacturer(findByName(attributes, ATTRIBUTE_MANUFACTURER));
        dicomProperties.setInstitutionName(findByName(attributes, ATTRIBUTE_INSTITUTION_NAME));
        dicomProperties.setPatientName(findByName(attributes, ATTRIBUTE_PATIENT_NAME));
        dicomProperties.setMediaStorageSOPClassUID(findByName(attributes, ATTRIBUTE_MEDIA_STORAGE_SOP_CLASS_UID));
        dicomProperties.setMediaStorageSOPInstanceUID(findByName(attributes, ATTRIBUTE_MEDIA_STORAGE_SOP_INSTANCE_UID));
        dicomProperties.setTransferSyntaxUID(findByName(attributes, ATTRIBUTE_TRANSFER_SYNTAX_UID));
        dicomProperties.setSopClassUID(findByName(attributes, ATTRIBUTE_SOP_CLASS_UID));
        dicomProperties.setSopInstanceUID(findByName(attributes, ATTRIBUTE_SOP_INSTANCE_UID));
        dicomProperties.setPatientID(findByName(attributes, ATTRIBUTE_PATIENT_ID));
        dicomProperties.setPatientAge(findByName(attributes, ATTRIBUTE_PATIENT_AGE));
        dicomProperties.setBodyPartExamined(findByName(attributes, ATTRIBUTE_BODY_PART_EXAMINED));
        dicomProperties.setScanOptions(findByName(attributes, ATTRIBUTE_SCAN_OPTIONS));
        dicomProperties.setDeviceSerialNumber(findByName(attributes, ATTRIBUTE_DEVICE_SERIAL_NUMBER));
        dicomProperties.setStudyInstanceUID(findByName(attributes, ATTRIBUTE_STUDY_INSTANCE_UID));
        dicomProperties.setSeriesInstanceUID(findByName(attributes, ATTRIBUTE_SERIE_INSTANCE_UID));
        dicomProperties.setPatientSex(findByName(attributes, ATTRIBUTE_PATIENT_SEX));
        dicomProperties.setScanOptions(findByName(attributes, ATTRIBUTE_SCAN_OPTIONS));
        dicomProperties.setProtocolName(findByName(attributes, ATTRIBUTE_PROTOCOL_NAME));
        dicomProperties.setPhotometricInterpretation(findByName(attributes, ATTRIBUTE_PHOTOMETRIC_INTERPRETATION));
        dicomProperties.setStudyName(findByName(attributes, ATTRIBUTE_STUDY_NAME));
        dicomProperties.setManufacturerModelName(findByName(attributes, ATTRIBUTE_MANUFACTURER_MODEL_NAME));
        dicomProperties.setInstitutionalDepartmentName(findByName(attributes, ATTRIBUTE_INSTITUTIONAL_DEPT_NAME));
        dicomProperties.setStudyId(findByName(attributes, ATTRIBUTE_STUDY_ID));
        dicomProperties.setSeriesNumber(findByName(attributes, ATTRIBUTE_SERIES_NUMBER));
        dicomProperties.setStudyDescription(findByName(attributes, ATTRIBUTE_STUDY_DESCRIPTION));
        dicomProperties.setSeriesDescription(findByName(attributes, ATTRIBUTE_SERIES_DESCRIPTION));
        dicomProperties.setReferringPhysician(findByName(attributes, ATTRIBUTE_REFERRING_PHYSICIAN));
        dicomProperties.setPerformingPhysician(findByName(attributes, ATTRIBUTE_PERFORMING_PHYSICIAN));
        dicomProperties.setPatientBirthDate(this.convertDate(attributes, ATTRIBUTE_PATIENT_BIRTH_DATE));

        String fechaHoraEstudio = StringUtils.substring(findByName(attributes,
            ATTRIBUTE_STUDY_DATE), 0, 8).concat(CHAR_ESPACIO).concat(
            StringUtils.substring(findByName(attributes, ATTRIBUTE_STUDY_TIME), 0, 6));

        dicomProperties.setStudyDateTime(Timestamp.valueOf(DateUtil.convertWithTime(fechaHoraEstudio, FORMAT_DATE_TIME)));

        return dicomProperties;
    }

    private LocalDate convertDate(List<DicomAttribute> attributes, String attributeName) {

        String dateString = findByName(attributes, attributeName);

        LocalDate answer1 = DateUtil.convert(dateString, FORMAT_DATE_ALTERNATIVE_1);

        if (answer1 != null) {

            return answer1;

        } else {

            LocalDate answer2 = DateUtil.convert(dateString, FORMAT_DATE_ALTERNATIVE_2);

            if (answer2 != null) {

                return answer2;

            } else {

                return DateUtil.convert(dateString, FORMAT_DATE_ALTERNATIVE_3);
            }
        }
    }

    /**
     * Encuentra el valor dado a un atributo
     *
     * @param attributes    Los atributos a ser leidos.
     * @param attributeName El nombre del atributo a buscar.
     * @return El valor del atributo.
     */
    private String findByName(List<DicomAttribute> attributes, String attributeName) {

        for (DicomAttribute attribute : attributes) {

            if (StringUtils.equalsIgnoreCase(attribute.getName(), attributeName)) {

                return attribute.getValues().isEmpty() ? null : attribute.getValues().stream().findFirst().get();
            }
        }

        return null;
    }

}