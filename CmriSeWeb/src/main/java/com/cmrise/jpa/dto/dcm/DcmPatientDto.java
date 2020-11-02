package com.cmrise.jpa.dto.dcm;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the DCM_PATIENT database table.
 * 
 */
@Entity
@Table(name="DCM_PATIENT")
@NamedQuery(name="DcmPatientDto.findAll", query="SELECT d FROM DcmPatientDto d")
public class DcmPatientDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private long id;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="PATIENT_AGE")
	private String patientAge;

	@Column(name="PATIENT_BIRTHDAY")
	private Date patientBirthday;

	@Column(name="PATIENT_ID")
	private String patientId;

	@Column(name="PATIENT_NAME")
	private String patientName;

	@Column(name="PATIENT_SEX")
	private String patientSex;

	//bi-directional many-to-one association to DcmStudyDto
	@OneToMany(mappedBy="dcmPatient")
	private List<DcmStudyDto> dcmStudies;

	public DcmPatientDto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPatientAge() {
		return this.patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	public Date getPatientBirthday() {
		return this.patientBirthday;
	}

	public void setPatientBirthday(Date patientBirthday) {
		this.patientBirthday = patientBirthday;
	}

	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientSex() {
		return this.patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

	public List<DcmStudyDto> getDcmStudies() {
		return this.dcmStudies;
	}

	public void setDcmStudies(List<DcmStudyDto> dcmStudies) {
		this.dcmStudies = dcmStudies;
	}

	public DcmStudyDto addDcmStudy(DcmStudyDto dcmStudy) {
		getDcmStudies().add(dcmStudy);
		dcmStudy.setDcmPatient(this);

		return dcmStudy;
	}

	public DcmStudyDto removeDcmStudy(DcmStudyDto dcmStudy) {
		getDcmStudies().remove(dcmStudy);
		dcmStudy.setDcmPatient(null);

		return dcmStudy;
	}

}