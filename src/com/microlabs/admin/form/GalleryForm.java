package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class GalleryForm extends ActionForm {

	private String menuName;
	private String linkName;
	private String subLink;
	private String galleryTitle;
	private String[] select1;
	private String message;
	private FormFile fileNames;
	private FormFile videoFileNames;
	private FormFile iconNames;
	private String contentDescription;
	private String contentDescriptionAdmin;
	private String iconName;
	private FormFile imageGalleryFile;
	private String message1;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private int rowno;
	private String serchType;
	private String contentYear;
	private String imageId;
	private String imageName;
	private String imageTitle;
	private String[] imageTitle1;
	private String cmsLinkArchive;
	private ArrayList years = new ArrayList();
	private String archiveStatus;
	private String cmsLinkId;

	public String getCmsLinkId() {
		return cmsLinkId;
	}

	public void setCmsLinkId(String cmsLinkId) {
		this.cmsLinkId = cmsLinkId;
	}

	public String getArchiveStatus() {
		return archiveStatus;
	}

	public void setArchiveStatus(String archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

	public ArrayList getYears() {
		return years;
	}

	public void setYears(ArrayList years) {
		this.years = years;
	}

	public String getCmsLinkArchive() {
		return cmsLinkArchive;
	}

	public void setCmsLinkArchive(String cmsLinkArchive) {
		this.cmsLinkArchive = cmsLinkArchive;
	}

	public String[] getImageTitle1() {
		return imageTitle1;
	}

	public void setImageTitle1(String[] imageTitle1) {
		this.imageTitle1 = imageTitle1;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getSubLink() {
		return subLink;
	}

	public void setSubLink(String subLink) {
		this.subLink = subLink;
	}

	public String getGalleryTitle() {
		return galleryTitle;
	}

	public void setGalleryTitle(String galleryTitle) {
		this.galleryTitle = galleryTitle;
	}

	public String[] getSelect1() {
		return select1;
	}

	public void setSelect1(String[] select1) {
		this.select1 = select1;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FormFile getFileNames() {
		return fileNames;
	}

	public void setFileNames(FormFile fileNames) {
		this.fileNames = fileNames;
	}

	public FormFile getVideoFileNames() {
		return videoFileNames;
	}

	public void setVideoFileNames(FormFile videoFileNames) {
		this.videoFileNames = videoFileNames;
	}

	public FormFile getIconNames() {
		return iconNames;
	}

	public void setIconNames(FormFile iconNames) {
		this.iconNames = iconNames;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}

	public String getContentDescriptionAdmin() {
		return contentDescriptionAdmin;
	}

	public void setContentDescriptionAdmin(String contentDescriptionAdmin) {
		this.contentDescriptionAdmin = contentDescriptionAdmin;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public FormFile getImageGalleryFile() {
		return imageGalleryFile;
	}

	public void setImageGalleryFile(FormFile imageGalleryFile) {
		this.imageGalleryFile = imageGalleryFile;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}

	public int getRowno() {
		return rowno;
	}

	public void setRowno(int rowno) {
		this.rowno = rowno;
	}

	public String getSerchType() {
		return serchType;
	}

	public void setSerchType(String serchType) {
		this.serchType = serchType;
	}

	public String getContentYear() {
		return contentYear;
	}

	public void setContentYear(String contentYear) {
		this.contentYear = contentYear;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

}
