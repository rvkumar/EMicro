package com.microlabs.newsandmedia.form;

import org.apache.struts.action.ActionForm;

public class ArchivesForm extends ActionForm {

	private int id;
	private int year;
	private String linkName;
	private String contentDescription;
	private String filePath;
	private String subLinkName;
	private String videoPath;
	private String mainLinkName;
	

	
	public String getMainLinkName() {
		return mainLinkName;
	}

	public void setMainLinkName(String mainLinkName) {
		this.mainLinkName = mainLinkName;
	}

	public String getSubLinkName() {
		return subLinkName;
	}

	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	
}
