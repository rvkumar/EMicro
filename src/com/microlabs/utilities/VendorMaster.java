package com.microlabs.utilities;

public class VendorMaster {
	
	private String requestNo;
	private String requestDate;
	private String requestedBy;
	private String requestedType;
	
	public VendorMaster(String requestNo, String requestDate,
			String requestedBy,String requestedType) {
		super();
		this.requestNo = requestNo;
		this.requestDate = requestDate;
		this.requestedBy = requestedBy;
		this.requestedType = requestedType;
	}
	
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getRequestedType() {
		return requestedType;
	}
	public void setRequestedType(String requestedType) {
		this.requestedType = requestedType;
	}
	
	
	
	
	
}
