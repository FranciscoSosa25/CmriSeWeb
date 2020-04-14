package com.cmrise.dicom.attribute;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Project RadixDesktop
 * Created by ernesto on 14/09/16.
 */
public class DicomProperties {

    private String patientName;
    private String studyName;
    private String modality;
    private String institutionName;
    private String mediaStorageSOPClassUID;
    private String mediaStorageSOPInstanceUID;
    private String transferSyntaxUID;
    private String sopClassUID;
    private String sopInstanceUID;
    private String manufacturer;
    private String patientID;
    private String patientAge;
    private String bodyPartExamined;
    private String scanOptions;
    private String deviceSerialNumber;
    private String studyInstanceUID;
    private String seriesInstanceUID;
    private String patientSex;
    private String protocolName;
    private String photometricInterpretation;
    private String manufacturerModelName;
    private String institutionalDepartmentName;
    private String studyId;
    private String seriesNumber;
    private String studyDescription;
    private String seriesDescription;
    private String referringPhysician;
    private String performingPhysician;

    private LocalDate patientBirthDate;
    private LocalDate studyDate;

    private Timestamp studyDateTime;

    public DicomProperties() {
        super();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getMediaStorageSOPClassUID() {
        return mediaStorageSOPClassUID;
    }

    public void setMediaStorageSOPClassUID(String mediaStorageSOPClassUID) {
        this.mediaStorageSOPClassUID = mediaStorageSOPClassUID;
    }

    public String getMediaStorageSOPInstanceUID() {
        return mediaStorageSOPInstanceUID;
    }

    public void setMediaStorageSOPInstanceUID(String mediaStorageSOPInstanceUID) {
        this.mediaStorageSOPInstanceUID = mediaStorageSOPInstanceUID;
    }

    public String getTransferSyntaxUID() {
        return transferSyntaxUID;
    }

    public void setTransferSyntaxUID(String transferSyntaxUID) {
        this.transferSyntaxUID = transferSyntaxUID;
    }

    public String getSopClassUID() {
        return sopClassUID;
    }

    public void setSopClassUID(String sopClassUID) {
        this.sopClassUID = sopClassUID;
    }

    public String getSopInstanceUID() {
        return sopInstanceUID;
    }

    public void setSopInstanceUID(String sopInstanceUID) {
        this.sopInstanceUID = sopInstanceUID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getBodyPartExamined() {
        return bodyPartExamined;
    }

    public void setBodyPartExamined(String bodyPartExamined) {
        this.bodyPartExamined = bodyPartExamined;
    }

    public String getScanOptions() {
        return scanOptions;
    }

    public void setScanOptions(String scanOptions) {
        this.scanOptions = scanOptions;
    }

    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public String getStudyInstanceUID() {
        return studyInstanceUID;
    }

    public void setStudyInstanceUID(String studyInstanceUID) {
        this.studyInstanceUID = studyInstanceUID;
    }

    public String getSeriesInstanceUID() {
        return seriesInstanceUID;
    }

    public void setSeriesInstanceUID(String seriesInstanceUID) {
        this.seriesInstanceUID = seriesInstanceUID;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getPhotometricInterpretation() {
        return photometricInterpretation;
    }

    public void setPhotometricInterpretation(String photometricInterpretation) {
        this.photometricInterpretation = photometricInterpretation;
    }

    public String getManufacturerModelName() {
        return manufacturerModelName;
    }

    public void setManufacturerModelName(String manufacturerModelName) {
        this.manufacturerModelName = manufacturerModelName;
    }

    public String getInstitutionalDepartmentName() {
        return institutionalDepartmentName;
    }

    public void setInstitutionalDepartmentName(String institutionalDepartmentName) {
        this.institutionalDepartmentName = institutionalDepartmentName;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public String getReferringPhysician() {
        return referringPhysician;
    }

    public void setReferringPhysician(String referringPhysician) {
        this.referringPhysician = referringPhysician;
    }

    public String getPerformingPhysician() {
        return performingPhysician;
    }

    public void setPerformingPhysician(String performingPhysician) {
        this.performingPhysician = performingPhysician;
    }

    public LocalDate getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(LocalDate patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public LocalDate getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(LocalDate studyDate) {
        this.studyDate = studyDate;
    }

    public Timestamp getStudyDateTime() {
        return studyDateTime;
    }

    public void setStudyDateTime(Timestamp studyDateTime) {
        this.studyDateTime = studyDateTime;
    }

    @Override
    public String toString() {
        return "DicomProperties [patientName=" + patientName + ", studyName=" + studyName + ", studyDate=" + studyDate
            + ", modality=" + modality + ", institutionName=" + institutionName + ", mediaStorageSOPClassUID="
            + mediaStorageSOPClassUID + ", mediaStorageSOPInstanceUID=" + mediaStorageSOPInstanceUID
            + ", transferSyntaxUID=" + transferSyntaxUID + ", sopClassUID=" + sopClassUID + ", sopInstanceUID="
            + sopInstanceUID + ", manufacturer=" + manufacturer + ", patientID=" + patientID + ", patientAge="
            + patientAge + ", bodyPartExamined=" + bodyPartExamined + ", scanOptions=" + scanOptions
            + ", deviceSerialNumber=" + deviceSerialNumber + ", studyInstanceUID=" + studyInstanceUID
            + ", seriesInstanceUID=" + seriesInstanceUID + ", patientSex=" + patientSex + ", protocolName="
            + protocolName + ", photometricInterpretation=" + photometricInterpretation + ", manufacturerModelName="
            + manufacturerModelName + ", institutionalDepartmentName=" + institutionalDepartmentName + ", studyId="
            + studyId + ", seriesNumber=" + seriesNumber + "]";
    }

}