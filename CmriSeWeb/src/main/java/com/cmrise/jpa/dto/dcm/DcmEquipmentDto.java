package com.cmrise.jpa.dto.dcm;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the DCM_EQUIPMENT database table.
 * 
 */
@Entity
@Table(name="DCM_EQUIPMENT")
@NamedQuery(name="DcmEquipmentDto.findAll", query="SELECT d FROM DcmEquipmentDto d")
public class DcmEquipmentDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private long id;

	@Column(name="CONVERSION_TYPE")
	private String conversionType;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="DEVICE_SERIAL_NUMBER")
	private String deviceSerialNumber;

	@Column(name="INSTITUTION_ADDRESS")
	private String institutionAddress;

	@Column(name="INSTITUTION_ALDEPARTMENT_NAME")
	private String institutionAldepartmentName;

	@Column(name="INSTITUTION_NAME")
	private String institutionName;

	@Column(name="MANUFACTURER")
	private String manufacturer;

	@Column(name="MANUFACTURER_MODEL_NAME")
	private String manufacturerModelName;

	@Column(name="MODALITY")
	private String modality;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="SOFTWARE_VERSION")
	private String softwareVersion;

	@Column(name="STATION_NAME")
	private String stationName;

	//bi-directional many-to-one association to DcmSeriesDto
	@ManyToOne
	@JoinColumn(name="SERIES_ID")
	private DcmSeriesDto dcmSery;

	public DcmEquipmentDto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConversionType() {
		return this.conversionType;
	}

	public void setConversionType(String conversionType) {
		this.conversionType = conversionType;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeviceSerialNumber() {
		return this.deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getInstitutionAddress() {
		return this.institutionAddress;
	}

	public void setInstitutionAddress(String institutionAddress) {
		this.institutionAddress = institutionAddress;
	}

	public String getInstitutionAldepartmentName() {
		return this.institutionAldepartmentName;
	}

	public void setInstitutionAldepartmentName(String institutionAldepartmentName) {
		this.institutionAldepartmentName = institutionAldepartmentName;
	}

	public String getInstitutionName() {
		return this.institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturerModelName() {
		return this.manufacturerModelName;
	}

	public void setManufacturerModelName(String manufacturerModelName) {
		this.manufacturerModelName = manufacturerModelName;
	}

	public String getModality() {
		return this.modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getSoftwareVersion() {
		return this.softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public DcmSeriesDto getDcmSery() {
		return this.dcmSery;
	}

	public void setDcmSery(DcmSeriesDto dcmSery) {
		this.dcmSery = dcmSery;
	}

}