package com.cmrise.jpa.dto.dcm;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the DCM_SERIES database table.
 * 
 */
@Entity
@Table(name="DCM_SERIES")
@NamedQuery(name="DcmSeriesDto.findAll", query="SELECT d FROM DcmSeriesDto d")
public class DcmSeriesDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private long id;

	@Column(name="BODY_PART_EXAMINED")
	private String bodyPartExamined;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="LATERALITY")
	private String laterality;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="OPERATORS_NAME")
	private String operatorsName;

	@Column(name="PATIENT_POSITION")
	private String patientPosition;

	@Column(name="PROTOCOL_NAME")
	private String protocolName;

	@Column(name="SERIES_DATETIME")
	private Timestamp seriesDatetime;

	@Column(name="SERIES_DESCRIPTION")
	private String seriesDescription;

	@Column(name="SERIES_INSTANCE_UID")
	private String seriesInstanceUid;

	@Column(name="SERIES_NUMBER")
	private int seriesNumber;

	//bi-directional many-to-one association to DcmEquipmentDto
	@OneToMany(mappedBy="dcmSery")
	private List<DcmEquipmentDto> dcmEquipments;

	//bi-directional many-to-one association to DcmInstanceDto
	@OneToMany(mappedBy="dcmSery")
	private List<DcmInstanceDto> dcmInstances;

	//bi-directional many-to-one association to DcmStudyDto
	@ManyToOne
	@JoinColumn(name="STUDY_ID")
	private DcmStudyDto dcmStudy;

	public DcmSeriesDto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBodyPartExamined() {
		return this.bodyPartExamined;
	}

	public void setBodyPartExamined(String bodyPartExamined) {
		this.bodyPartExamined = bodyPartExamined;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getLaterality() {
		return this.laterality;
	}

	public void setLaterality(String laterality) {
		this.laterality = laterality;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getOperatorsName() {
		return this.operatorsName;
	}

	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	public String getPatientPosition() {
		return this.patientPosition;
	}

	public void setPatientPosition(String patientPosition) {
		this.patientPosition = patientPosition;
	}

	public String getProtocolName() {
		return this.protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public Timestamp getSeriesDatetime() {
		return this.seriesDatetime;
	}

	public void setSeriesDatetime(Timestamp seriesDatetime) {
		this.seriesDatetime = seriesDatetime;
	}

	public String getSeriesDescription() {
		return this.seriesDescription;
	}

	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}

	public String getSeriesInstanceUid() {
		return this.seriesInstanceUid;
	}

	public void setSeriesInstanceUid(String seriesInstanceUid) {
		this.seriesInstanceUid = seriesInstanceUid;
	}

	public int getSeriesNumber() {
		return this.seriesNumber;
	}

	public void setSeriesNumber(int seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

	public List<DcmEquipmentDto> getDcmEquipments() {
		return this.dcmEquipments;
	}

	public void setDcmEquipments(List<DcmEquipmentDto> dcmEquipments) {
		this.dcmEquipments = dcmEquipments;
	}

	public DcmEquipmentDto addDcmEquipment(DcmEquipmentDto dcmEquipment) {
		getDcmEquipments().add(dcmEquipment);
		dcmEquipment.setDcmSery(this);

		return dcmEquipment;
	}

	public DcmEquipmentDto removeDcmEquipment(DcmEquipmentDto dcmEquipment) {
		getDcmEquipments().remove(dcmEquipment);
		dcmEquipment.setDcmSery(null);

		return dcmEquipment;
	}

	public List<DcmInstanceDto> getDcmInstances() {
		return this.dcmInstances;
	}

	public void setDcmInstances(List<DcmInstanceDto> dcmInstances) {
		this.dcmInstances = dcmInstances;
	}

	public DcmInstanceDto addDcmInstance(DcmInstanceDto dcmInstance) {
		getDcmInstances().add(dcmInstance);
		dcmInstance.setDcmSery(this);

		return dcmInstance;
	}

	public DcmInstanceDto removeDcmInstance(DcmInstanceDto dcmInstance) {
		getDcmInstances().remove(dcmInstance);
		dcmInstance.setDcmSery(null);

		return dcmInstance;
	}

	public DcmStudyDto getDcmStudy() {
		return this.dcmStudy;
	}

	public void setDcmStudy(DcmStudyDto dcmStudy) {
		this.dcmStudy = dcmStudy;
	}

}