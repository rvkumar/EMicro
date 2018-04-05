package com.microlabs.utilities;



public class Leave1{
	
	private String email;
	private String date;
	private String money;
	
	
	public Leave1(String email, String date,String money) {
		super();
		this.email = email;
		this.date = date;
		this.money = money;
		
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	
	
}