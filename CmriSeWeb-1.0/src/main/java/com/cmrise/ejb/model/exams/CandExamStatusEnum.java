package com.cmrise.ejb.model.exams;

public enum CandExamStatusEnum {
	
	ASIGNADO("ASIGNADO"),
	REVISADO("REVISADO"),
	PAUSAR("PAUSAR"),
	RESUME("RESUME"),
	SUSPENDER("SUSPENDER"),
	DEFAULT("")
	;
	
	private String status;
	CandExamStatusEnum(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public static CandExamStatusEnum getCandExamStatusEnum(String status){
		CandExamStatusEnum candExamStatusEnum;
		String val = status !=null ? status.toUpperCase() : "";
		
		switch (val) {
		case "ASIGNADO" :
			candExamStatusEnum = ASIGNADO;
			break;
		case "REVISADO" :
			candExamStatusEnum = REVISADO;
			break;
		case "PAUSAR" :
			candExamStatusEnum = PAUSAR;
			break;
		case "RESUME" :
			candExamStatusEnum = RESUME;
			break;
		case "SUSPENDER" :
			candExamStatusEnum = SUSPENDER;
			break;			
		default:
			candExamStatusEnum = DEFAULT; 
			break;
		}
		return candExamStatusEnum;
	}
	
	
	
	
	
	
	
	

}
