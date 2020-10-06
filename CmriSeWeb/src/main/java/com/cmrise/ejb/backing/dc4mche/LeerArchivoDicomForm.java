package com.cmrise.ejb.backing.dc4mche;

import java.io.File;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;

import com.cmrise.ejb.services.dcm.DcmEquipmentLocal;
import com.cmrise.ejb.services.dcm.DcmInstanceLocal;
import com.cmrise.ejb.services.dcm.DcmPatientLocal;
import com.cmrise.ejb.services.dcm.DcmSeriesLocal;
import com.cmrise.ejb.services.dcm.DcmStudyLocal;

@ManagedBean
@ViewScoped
public class LeerArchivoDicomForm {

	@Inject 
	DcmEquipmentLocal dcmEquipmentLocal; 
	
	@Inject 
	DcmInstanceLocal dcmInstanceLocal; 
	
	@Inject 
	DcmPatientLocal dcmPatientLocal;
	
	@Inject 
	DcmSeriesLocal dcmSeriesLocal; 
	
	@Inject 
	DcmStudyLocal dcmStudyLocal;
	
	public void leerSoloUnArchivoDicom() {
		System.out.println("Entra leerSoloUnArchivoDicom");

  	    DicomInputStream din = null;
  	    Attributes attr = null; //file dataset info
  	    Attributes fmi = null; //file metadata info
  	  
		try {
		    din = new DicomInputStream(new File("C:\\ProyectosOpenSource\\ConMexRadioImag\\dicom\\series-000001\\image-000001.dcm"));
            System.out.println(din);
            din.setIncludeBulkData(IncludeBulkData.URI);
            attr = din.readDataset(-1, -1);
            fmi = din.readFileMetaInformation();
            System.out.println(attr);
            System.out.println(fmi);
            
            System.out.println("*** Patient Info ***");
            System.out.println("PatientId:"+attr.getString(Tag.PatientID));
            System.out.println("PatientName:"+attr.getString(Tag.PatientName));
            System.out.println("PatientSex:"+attr.getString(Tag.PatientSex));
            System.out.println("PatientAge:"+attr.getString(Tag.PatientAge));
            System.out.println("PatientBirthDate:"+attr.getDate(Tag.PatientBirthDate));
            System.out.println("*** End of Patient Info ***");
            
            System.out.println("*** Study Info ***");
            System.out.println("AccessionNumber:"+attr.getString(Tag.AccessionNumber));
            System.out.println("AdditionalPatientHistory:"+attr.getString(Tag.AdditionalPatientHistory));
            System.out.println("AdmittingDiagnosesDescription:"+attr.getString(Tag.AdmittingDiagnosesDescription));
            System.out.println("ReferringPhysicianName:"+attr.getString(Tag.ReferringPhysicianName));
            System.out.println("StudyDateAndTime:"+attr.getDate(Tag.StudyDateAndTime));
            System.out.println("StudyID:"+attr.getString(Tag.StudyID));
            System.out.println("StudyDescription:"+attr.getString(Tag.StudyDescription));
            System.out.println("StudyInstanceUID:"+attr.getString(Tag.StudyInstanceUID));
            System.out.println("StudyPriorityID:"+attr.getString(Tag.StudyPriorityID));
            System.out.println("StudyStatusID:"+attr.getString(Tag.StudyStatusID));
            System.out.println("*** End of Study Info ***");
            
            System.out.println("*** Series Info ***");
            System.out.println("BodyPartExamined:"+attr.getString(Tag.BodyPartExamined));
            System.out.println("Laterality:"+attr.getString(Tag.Laterality));
            System.out.println("OperatorsName:"+attr.getString(Tag.OperatorsName));
            System.out.println("PatientPosition:"+attr.getString(Tag.PatientPosition));
            System.out.println("ProtocolName:"+attr.getString(Tag.ProtocolName));
            System.out.println("SeriesDateAndTime:"+attr.getDate(Tag.SeriesDateAndTime));
            System.out.println("SeriesDescription:"+attr.getString(Tag.SeriesDescription));
            System.out.println("SeriesInstanceUID:"+attr.getString(Tag.SeriesInstanceUID));
            System.out.println("SeriesNumber:"+attr.getInt(Tag.SeriesNumber,0));
            System.out.println("*** End of Series Info ***");
            
            System.out.println("*** Equipment Info ***");
            System.out.println("ConversionType:"+attr.getString(Tag.ConversionType));
            System.out.println("DeviceSerialNumber:"+attr.getString(Tag.DeviceSerialNumber));
            System.out.println("InstitutionAddress:"+attr.getString(Tag.InstitutionAddress));
            System.out.println("InstitutionName:"+attr.getString(Tag.InstitutionName));
            System.out.println("InstitutionalDepartmentName:"+attr.getString(Tag.InstitutionalDepartmentName));
            System.out.println("Manufacturer:"+attr.getString(Tag.Manufacturer));
            System.out.println("ManufacturerModelName:"+attr.getString(Tag.ManufacturerModelName));
            System.out.println("Modality:"+attr.getString(Tag.Modality));
            System.out.println("SoftwareVersions:"+attr.getString(Tag.SoftwareVersions));
            System.out.println("StationName:"+attr.getString(Tag.StationName));
            System.out.println("*** End of Equipment Info ***");
            
            System.out.println("*** Instance Info ***");
            System.out.println("AcquisitionDateAndTime:"+attr.getDate(Tag.AcquisitionDateAndTime));
            System.out.println("ContentDateAndTime:"+attr.getDate(Tag.ContentDateAndTime));
            System.out.println("ExposureTime:"+attr.getInt(Tag.ExposureTime,0));
            System.out.println("ImageOrientation:"+attr.getString(Tag.ImageOrientation));
            System.out.println("ImagePosition:"+attr.getString(Tag.ImagePosition));
            System.out.println("ImageType:"+attr.getString(Tag.ImageType));
            System.out.println("InstanceNumber:"+attr.getInt(Tag.InstanceNumber,0));
            System.out.println("KVP:"+attr.getString(Tag.KVP));
            System.out.println("MediaStorageSOPInstanceUID:"+attr.getString(Tag.MediaStorageSOPInstanceUID)); //InstanceUID -> it is also the file name itself
            System.out.println("TransferSyntaxUID:"+attr.getString(Tag.TransferSyntaxUID));
            System.out.println("PatientOrientation:"+attr.getString(Tag.PatientOrientation));
            System.out.println("PixelSpacing:"+attr.getFloat(Tag.PixelSpacing,0));
            System.out.println("SliceLocation:"+attr.getFloat(Tag.SliceLocation,0));
            System.out.println("SliceThickness:"+attr.getFloat(Tag.SliceThickness,0));
            System.out.println("SOPClassUID:"+attr.getString(Tag.SOPClassUID));
            System.out.println("SOPInstanceUID:"+attr.getString(Tag.SOPInstanceUID));
            System.out.println("WindowCenter:"+attr.getString(Tag.WindowCenter));
            System.out.println("WindowWidth:"+attr.getString(Tag.WindowWidth));
            System.out.println("XRayTubeCurrent:"+attr.getInt(Tag.XRayTubeCurrent,0));
            System.out.println("*** End of Instance Info ***");
            
		}
		catch (IOException e) {
		    e.printStackTrace();
		    return;
		}
		finally {
		    try {
		        din.close();
		    }
		    catch (IOException ignore) {
		    }
		}
		System.out.println("Sale leerSoloUnArchivoDicom");	
	}
	
}
