package com.microlabs.admin.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.ProductForm;

public class ProductAction extends DispatchAction {
	
	public ActionForward displayPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
        UserDao ad = new UserDao();
        ProductForm pf =(ProductForm)form;
		try{
			ArrayList list = new ArrayList();
			String sql3="select * from product_list  ";
			ResultSet rs12 = ad.selectQuery(sql3);
			ProductForm linksForm;
			while (rs12.next())
			{
				linksForm = new ProductForm();
				linksForm.setGifFile(rs12.getString("products_path"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			
			pf.setFileCount(list.size());
			
			}catch (Exception e) {
			e.printStackTrace();
			}
	                     
	                     
	                     
	                     
	                     
				return mapping.findForward("displayProducts");
		
		
		
		
	}
	
	public ActionForward upLoadProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
	    	 
            UserDao ad = new UserDao();
            ProductForm pf =(ProductForm)form;
      
          
          	int fileCount=pf.getFileCount();
          	 
          	if(fileCount==0){
             FormFile myProduct = pf.getProductFileName();
             String contentType =myProduct.getContentType();
             String fileName   =  myProduct.getFileName();
             System.out.println(fileName);
			 byte[] fileData  =   myProduct.getFileData();
			 String filePath = getServlet().getServletContext().getRealPath("images");
			 System.out.println(filePath);
			 File destinationDir = new File(filePath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			File fileToCreate = new File(filePath, fileName);
			System.out.println(fileToCreate);
			FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
			fileOutStream.write(myProduct.getFileData());
			fileOutStream.flush();
			fileOutStream.close();
			
			 //storing another drive
			 String otherFilePath="E:/EMicro Files/Home Page Annimation";
			 File destDir = new File(otherFilePath);
				if(!destDir.exists())
				{
					destDir.mkdirs();
				}
				otherFilePath="E:/EMicro Files/Home Page Annimation/"+myProduct.getFileName();
			 
				 File imageFile1=new File(otherFilePath);
				 FileOutputStream outputStream1=new FileOutputStream(imageFile1);
				 outputStream1.write(myProduct.getFileData());
				 outputStream1.flush();
				 outputStream1.close();
			
			filePath = filePath.replace("\\", "\\");
			System.out.println("2nd"+filePath);
			String sql="insert into product_list(products_path) values('"+fileName+"')";
			int a = ad.SqlExecuteUpdate(sql);
			if (a > 0) {
			  pf.setMessage("Gif File Uploaded Successfully");
			}else{
				pf.setMessage("Error While Uploading Gif Files ... Please check Entered Values");
			}
          	}else{
          		pf.setMessage("Only One Gif File is allowed To Upload");
          	}

			try{
			ArrayList list = new ArrayList();
			String sql3="select * from product_list  ";
			ResultSet rs12 = ad.selectQuery(sql3);
			ProductForm linksForm;
			while (rs12.next())
			{
				linksForm = new ProductForm();
				linksForm.setGifFile(rs12.getString("products_path"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			}catch (Exception e) {
			e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
         
		
		
		return mapping.findForward("displayProducts");
	}
	
	public ActionForward deleteProductgif(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductForm pf = (ProductForm)form;
		UserDao ud = new UserDao();
		String filelist[]=request.getParameterValues("checkedfiles");
		String files="";
		if(filelist.length==0)
		{
			files="";
		}else{
		
			for(int i=0;i<filelist.length;i++)
			{
				
				String deleteFiels="delete from product_list where products_path='"+filelist[i]+"'";
				int a=0;
				a=ud.SqlExecuteUpdate(deleteFiels);
				if (a > 0) {
					pf.setMessage("Documents Deleted Successfully");
				}else{
					pf.setMessage("Error While Deleted Files ... Please check Entered Values");
				}
			}
		}
		
		try{
			ArrayList list = new ArrayList();
			String sql3="select * from product_list  ";
			ResultSet rs12 = ud.selectQuery(sql3);
			ProductForm linksForm;
			while (rs12.next())
			{
				linksForm = new ProductForm();
				linksForm.setGifFile(rs12.getString("products_path"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			
			pf.setFileCount(list.size());
			}catch (Exception e) {
			e.printStackTrace();
			}
		
		
				return mapping.findForward("displayProducts");
				}

}
