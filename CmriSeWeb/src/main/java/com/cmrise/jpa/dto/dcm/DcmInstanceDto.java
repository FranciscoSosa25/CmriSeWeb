package com.cmrise.jpa.dto.dcm;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the DCM_INSTANCE database table.
 * 
 */
@Entity
@Table(name="DCM_INSTANCE")
@NamedQuery(name="DcmInstanceDto.findAll", query="SELECT d FROM DcmInstanceDto d")
public class DcmInstanceDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private long id;

	@Column(name="ACQUISITION_DATETIME")
	private Timestamp acquisitionDatetime;

	@Column(name="CONTENT_DATETIME")
	private Timestamp contentDatetime;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="EXPOSURE_TIME")
	private int exposureTime;

	@Column(name="IMAGE_ORIENTATION")
	private String imageOrientation;

	@Column(name="IMAGE_POSITION")
	private String imagePosition;

	@Column(name="IMAGE_TYPE")
	private String imageType;

	@Column(name="INSTANCE_NUMBER")
	private int instanceNumber;

	@Column(name="KVP")
	private String kvp;

	@Column(name="MEDIA_STORAGE_SOP_INSTANCE_UID")
	private String mediaStorageSopInstanceUid;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="PATIENT_ORIENTATION")
	private String patientOrientation;

	@Column(name="PIXEL_SPACING")
	private double pixelSpacing;

	@Column(name="SLICE_LOCATION")
	private double sliceLocation;

	@Column(name="SLICE_THICKNESS")
	private double sliceThickness;

	@Column(name="SOP_CLASS_UID")
	private String sopClassUid;

	@Column(name="SOP_INSTANCE_UID")
	private String sopInstanceUid;

	@Column(name="TRANSFER_SYNTAX_UID")
	private String transferSyntaxUid;

	@Column(name="WINDOW_CENTER")
	private String windowCenter;

	@Column(name="WINDOW_WIDTH")
	private String windowWidth;

	@Column(name="XRAY_TUBE_CURRENT")
	private int xrayTubeCurrent;

	//bi-directional many-to-one association to DcmSeriesDto
	@ManyToOne
	@JoinColumn(name="SERIES_ID")
	private DcmSeriesDto dcmSery;

	public DcmInstanceDto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getAcquisitionDatetime() {
		return this.acquisitionDatetime;
	}

	public void setAcquisitionDatetime(Timestamp acquisitionDatetime) {
		this.acquisitionDatetime = acquisitionDatetime;
	}

	public Timestamp getContentDatetime() {
		return this.contentDatetime;
	}

	public void setContentDatetime(Timestamp contentDatetime) {
		this.contentDatetime = contentDatetime;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public int getExposureTime() {
		return this.exposureTime;
	}

	public void setExposureTime(int exposureTime) {
		this.exposureTime = exposureTime;
	}

	public String getImageOrientation() {
		return this.imageOrientation;
	}

	public void setImageOrientation(String imageOrientation) {
		this.imageOrientation = imageOrientation;
	}

	public String getImagePosition() {
		return this.imagePosition;
	}

	public void setImagePosition(String imagePosition) {
		this.imagePosition = imagePosition;
	}

	public String getImageType() {
		return this.imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public int getInstanceNumber() {
		return this.instanceNumber;
	}

	public void setInstanceNumber(int instanceNumber) {
		this.instanceNumber = instanceNumber;
	}

	public String getKvp() {
		return this.kvp;
	}

	public void setKvp(String kvp) {
		this.kvp = kvp;
	}

	public String getMediaStorageSopInstanceUid() {
		return this.mediaStorageSopInstanceUid;
	}

	public void setMediaStorageSopInstanceUid(String mediaStorageSopInstanceUid) {
		this.mediaStorageSopInstanceUid = mediaStorageSopInstanceUid;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPatientOrientation() {
		return this.patientOrientation;
	}

	public void setPatientOrientation(String patientOrientation) {
		this.patientOrientation = patientOrientation;
	}

	public double getPixelSpacing() {
		return this.pixelSpacing;
	}

	public void setPixelSpacing(double pixelSpacing) {
		this.pixelSpacing = pixelSpacing;
	}

	public double getSliceLocation() {
		return this.sliceLocation;
	}

	public void setSliceLocation(double sliceLocation) {
		this.sliceLocation = sliceLocation;
	}

	public double getSliceThickness() {
		return this.sliceThickness;
	}

	public void setSliceThickness(double sliceThickness) {
		this.sliceThickness = sliceThickness;
	}

	public String getSopClassUid() {
		return this.sopClassUid;
	}

	public void setSopClassUid(String sopClassUid) {
		this.sopClassUid = sopClassUid;
	}

	public String getSopInstanceUid() {
		return this.sopInstanceUid;
	}

	public void setSopInstanceUid(String sopInstanceUid) {
		this.sopInstanceUid = sopInstanceUid;
	}

	public String getTransferSyntaxUid() {
		return this.transferSyntaxUid;
	}

	public void setTransferSyntaxUid(String transferSyntaxUid) {
		this.transferSyntaxUid = transferSyntaxUid;
	}

	public String getWindowCenter() {
		return this.windowCenter;
	}

	public void setWindowCenter(String windowCenter) {
		this.windowCenter = windowCenter;
	}

	public String getWindowWidth() {
		return this.windowWidth;
	}

	public void setWindowWidth(String windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getXrayTubeCurrent() {
		return this.xrayTubeCurrent;
	}

	public void setXrayTubeCurrent(int xrayTubeCurrent) {
		this.xrayTubeCurrent = xrayTubeCurrent;
	}

	public DcmSeriesDto getDcmSery() {
		return this.dcmSery;
	}

	public void setDcmSery(DcmSeriesDto dcmSery) {
		this.dcmSery = dcmSery;
	}

}