package com.microlabs.admin.form;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ProductForm extends ActionForm{
	
	

	private FormFile productFileName;
	
	private String message;
	
	private String gifFile;
	private int fileCount;

	

	

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public String getGifFile() {
		return gifFile;
	}

	public void setGifFile(String gifFile) {
		this.gifFile = gifFile;
	}

	public FormFile getProductFileName() {
		return productFileName;
	}

	public void setProductFileName(FormFile productFileName) {
		this.productFileName = productFileName;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
