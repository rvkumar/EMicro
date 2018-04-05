package com.microlabs.utilities;




public class Leave {
	
	private String startDate;
	private String generatedBy;
	private String status;
	private String endDate;
	private String noOfDays;
	private String requestType;
	private String submitDate;
    private String holidayType;
	private String leaveType;
	private String id;
	private String reason;
	private String fileNames;

	
	
	
	
	public String getFileNames() {
		return fileNames;
	}





	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}





	public String getReason() {
		return reason;
	}





	public void setReason(String reason) {
		this.reason = reason;
	}





	public String getRequestType() {
		return requestType;
	}

	



	public String getHolidayType() {
		return holidayType;
	}





	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}





	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}




	




	public String getSubmitDate() {
		return submitDate;
	}





	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}





	public Leave(String startDate, String endDate, String noOfDays,String leaveType
			,String generatedBy,String status,String id,String submitDate,String requestType,String holidayType,String reason,String fileNames) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.noOfDays = noOfDays;
		this.leaveType = leaveType;
		this.generatedBy = generatedBy;
		this.submitDate=submitDate;
		this.requestType=requestType;
		this.status = status;
		this.id = id;
		this.holidayType=holidayType;
		this.reason=reason;
		this.fileNames=fileNames;
	}
	
	
	
	
	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}


	public String getNoOfDays() {
		return noOfDays;
	}



	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getLeaveType() {
		return leaveType;
	}
	
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}





	public String getEndDate() {
		return endDate;
	}





	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	/*public int compareTo(Leave leave) {
		
		//int compareQuantity = ((Leave) leave).getStartDate(); 
		 
		//ascending order
		return  (int) (this.startDate.getTime() - leave.getStartDate().getTime()) / (24 * 60 * 60 * 1000);
		
	}
*/
}
