package com.cmrise.jpa.dto.dcm;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the DCM_STUDY database table.
 * 
 */
@Entity
@Table(name="DCM_STUDY")
@NamedQuery(name="DcmStudyDto.findAll", query="SELECT d FROM DcmStudyDto d")
public class DcmStudyDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private long id;

	@Column(name="ACCESSION_NUMBER")
	private String accessionNumber;

	@Column(name="ADDITIONAL_PATIENT_HISTORY")
	private String additionalPatientHistory;

	@Column(name="ADMITTING_DIAGNOSES_DESCRIPTION")
	private String admittingDiagnosesDescription;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="REFERRING_PHYSICIAN_NAME")
	private String referringPhysicianName;

	@Column(name="STUDY_DATE_TIME")
	private Timestamp studyDateTime;

	@Column(name="STUDY_DESCRIPTION")
	private String studyDescription;

	@Column(name="STUDY_ID")
	private String studyId;

	@Column(name="STUDY_INSTANCE_UID")
	private String studyInstanceUid;

	@Column(name="STUDY_PRIORITY_ID")
	private String studyPriorityId;

	@Column(name="STUDY_STATUS_ID")
	private String studyStatusId;

	//bi-directional many-to-one association to DcmSeriesDto
	@OneToMany(mappedBy="dcmStudy")
	private List<DcmSeriesDto> dcmSeries;

	//bi-directional many-to-one association to DcmPatientDto
	@ManyToOne
	@JoinColumn(name="PATIENT_ID")
	private DcmPatientDto dcmPatient;

	public DcmStudyDto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccessionNumber() {
		return this.accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getAdditionalPatientHistory() {
		return this.additionalPatientHistory;
	}

	public void setAdditionalPatientHistory(String additionalPatientHistory) {
		this.additionalPatientHistory = additionalPatientHistory;
	}

	public String getAdmittingDiagnosesDescription() {
		return this.admittingDiagnosesDescription;
	}

	public void setAdmittingDiagnosesDescription(String admittingDiagnosesDescription) {
		this.admittingDiagnosesDescription = admittingDiagnosesDescription;
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

	public String getReferringPhysicianName() {
		return this.referringPhysicianName;
	}

	public void setReferringPhysicianName(String referringPhysicianName) {
		this.referringPhysicianName = referringPhysicianName;
	}

	public Timestamp getStudyDateTime() {
		return this.studyDateTime;
	}

	public void setStudyDateTime(Timestamp studyDateTime) {
		this.studyDateTime = studyDateTime;
	}

	public String getStudyDescription() {
		return this.studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}

	public String getStudyId() {
		return this.studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getStudyInstanceUid() {
		return this.studyInstanceUid;
	}

	public void setStudyInstanceUid(String studyInstanceUid) {
		this.studyInstanceUid = studyInstanceUid;
	}

	public String getStudyPriorityId() {
		return this.studyPriorityId;
	}

	public void setStudyPriorityId(String studyPriorityId) {
		this.studyPriorityId = studyPriorityId;
	}

	public String getStudyStatusId() {
		return this.studyStatusId;
	}

	public void setStudyStatusId(String studyStatusId) {
		this.studyStatusId = studyStatusId;
	}

	public List<DcmSeriesDto> getDcmSeries() {
		return this.dcmSeries;
	}

	public void setDcmSeries(List<DcmSeriesDto> dcmSeries) {
		this.dcmSeries = dcmSeries;
	}

	public DcmSeriesDto addDcmSery(DcmSeriesDto dcmSery) {
		getDcmSeries().add(dcmSery);
		dcmSery.setDcmStudy(this);

		return dcmSery;
	}

	public DcmSeriesDto removeDcmSery(DcmSeriesDto dcmSery) {
		getDcmSeries().remove(dcmSery);
		dcmSery.setDcmStudy(null);

		return dcmSery;
	}

	public DcmPatientDto getDcmPatient() {
		return this.dcmPatient;
	}

	public void setDcmPatient(DcmPatientDto dcmPatient) {
		this.dcmPatient = dcmPatient;
	}

}