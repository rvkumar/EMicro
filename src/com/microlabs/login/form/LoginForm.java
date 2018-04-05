package com.microlabs.login.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm{
	
	
	private String loginType;
	private String userName;
	private String password;
	private String message;
	private String message2;
	
	private String contentDescription;
	
	private String questionAnswer;
	private String questionId;
	
	private ArrayList questionIdList = new ArrayList();
	private ArrayList questionValueList = new ArrayList();
	
	private String securityQuestion;
	private String dateOfJoin;
	
	private String newPassword;
	private String oldPassword;
	private String reqUserName;
	private String conformPwd;
	private String favoritQues;
	private String favAns;
	
	public String getFavoritQues() {
		return favoritQues;
	}
	public void setFavoritQues(String favoritQues) {
		this.favoritQues = favoritQues;
	}
	public String getFavAns() {
		return favAns;
	}
	public void setFavAns(String favAns) {
		this.favAns = favAns;
	}
	public String getConformPwd() {
		return conformPwd;
	}
	public void setConformPwd(String conformPwd) {
		this.conformPwd = conformPwd;
	}
	public String getReqUserName() {
		return reqUserName;
	}
	public void setReqUserName(String reqUserName) {
		this.reqUserName = reqUserName;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	public String getDateOfJoin() {
		return dateOfJoin;
	}
	public void setDateOfJoin(String dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getContentDescription() {
		return contentDescription;
	}
	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
	public String getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public ArrayList getQuestionIdList() {
		return questionIdList;
	}
	public void setQuestionIdList(ArrayList questionIdList) {
		this.questionIdList = questionIdList;
	}
	public ArrayList getQuestionValueList() {
		return questionValueList;
	}
	public void setQuestionValueList(ArrayList questionValueList) {
		this.questionValueList = questionValueList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
