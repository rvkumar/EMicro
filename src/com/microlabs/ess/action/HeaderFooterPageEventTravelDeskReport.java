package com.microlabs.ess.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;





import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.microlabs.db.ConnectionFactory;
public class HeaderFooterPageEventTravelDeskReport extends PdfPageEventHelper {

	

	String pernr="";
	String hrno="";
	

	String location="";
	String reqdate="";
	String TravelMode="";
	int reqno=0;
	
	long count=0;
	String locationName="";
	
    
    
   
    
	public HeaderFooterPageEventTravelDeskReport(String empno,String hrno,String location, String reqdate ,int reqno,String locationName,String TravelMode) {
	
    	this.pernr= empno;
    	this.hrno= hrno;
    	this.reqno=reqno;
    	
    	this.location= location;
    	this.reqdate= reqdate;
    	this.locationName= locationName;
    	this.TravelMode=TravelMode;
	}




	public void onStartPage(PdfWriter writer,Document document) {
		
		
		Font smallfont = new Font(Font.FontFamily.COURIER,8);
	       smallfont.setColor(BaseColor.BLUE);
    	
   	 PdfPTable table1 = new PdfPTable(3); // 3 columns.
        table1.setWidthPercentage(100); //Width 100%
        table1.setSpacingBefore(10f); //Space before table
        table1.setSpacingAfter(10f); //Space after table
        
        float[] columnWidths1 = {1.0f, 5f,2f};
	      
			try {
				table1.setWidths(columnWidths1);
				 Image image1;
					try {
						
						InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
					 	 Properties props = new Properties();
					 	 try {
							props.load(in);	
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					 	 
					 	try {
							in.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String uploadFilePath=props.getProperty("file.uploadFilePath");
						
						String filePath=uploadFilePath+"/EMicro Files/ESS/Travel Request/";
						image1 = Image.getInstance(filePath.replace("/", "\\")+"logo.png");
					
			           //Fixed Positioning
			     
			           //Scale to new height and new width of image
			           image1.scaleAbsolute(40, 40);
			           image1.setAlignment(Element.ALIGN_CENTER);
			           //Add to document
			           PdfPCell t1 = new PdfPCell(image1);
			           t1.setHorizontalAlignment(Element.ALIGN_CENTER);
			           t1.setVerticalAlignment(Element.ALIGN_CENTER);
			           t1.setRowspan(2);
			   	
			           table1.addCell(t1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Font columnheader = new Font(Font.FontFamily.UNDEFINED, 10,
		                    Font.BOLD);
					Font largeBold = new Font(Font.FontFamily.UNDEFINED, 12,Font.BOLD);
					
					
					 Paragraph preface1 = new Paragraph("MICRO LABS LIMITED -"+" "+locationName+" " ,largeBold); 
		      	    preface1.setAlignment(Element.ALIGN_RIGHT);
					PdfPCell t2 = new PdfPCell(preface1);
				
					t2.setHorizontalAlignment(Element.ALIGN_CENTER);
					t2.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table1.addCell(t2);
					
					 PdfPCell perpernr6 = new PdfPCell(new Paragraph("No.:TR "+reqno+" ",columnheader));			  
					 table1.addCell(perpernr6);
					
					  perpernr6 = new PdfPCell(new Paragraph(""+TravelMode+" Travel Requisition",columnheader));	
					  perpernr6.setHorizontalAlignment(Element.ALIGN_CENTER);
					 table1.addCell(perpernr6);
					
					 
					 perpernr6 = new PdfPCell(new Paragraph(reqdate,smallfont));			  
					 table1.addCell(perpernr6);
			
					
				    
				    
				    
					 
			          
			         
			           
			         
			           
			       	   document.add(table1);
			       	 
			       	  
					
						
					
			       	
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   	
   	/*
   	Rectangle rect = writer.getBoxSize("art");
       ColumnText.showTextAligned(
       		writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Left"), rect.getLeft(), rect.getTop(), 0);
       ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);*/
   }
    public void onEndPage(PdfWriter writer,Document document)
    {
    	
   	 BaseFont base = null;
		try {
		
				try {
					base = BaseFont.createFont("\\Windows\\fonts\\wingding_0.ttf", BaseFont.IDENTITY_H, false);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	Font font = new Font(base, 16f, Font.BOLD);
	  char checked='\u00FE';
	  char unchecked='\u00A8';
    	 
    	
    	Font largeBold = new Font(Font.FontFamily.UNDEFINED, 10,
                Font.BOLD);
    	
     	Font columnheader = new Font(Font.FontFamily.UNDEFINED, 8);
    	
        Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("dd-MM-yyyy HH:mm");
		String dateNow1 = ft1.format(dNow1);
		
       
       Font smallfont = new Font(Font.FontFamily.COURIER,8);
       smallfont.setColor(BaseColor.BLUE);
       Font mediumfont = new Font(Font.FontFamily.UNDEFINED,8);
		
		 PdfPTable table2 = new PdfPTable(2); // 3 columns.
         table2.setWidthPercentage(100); //Width 100%
         table2.setSpacingBefore(10f); //Space before table
         table2.setSpacingAfter(10f); //Space after table
         
         table2.setTotalWidth(527);
         table2.setLockedWidth(true);
         table2.getDefaultCell().setFixedHeight(20);
         table2.getDefaultCell().setFixedHeight(20);
         table2.getDefaultCell().setBorder(Rectangle.OUT_BOTTOM);
         table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
         //Set Column widths
          float[] columnWidths2 = {3f,3f};
          
          
          /*PdfPCell perpernr1 = new PdfPCell(new Paragraph("Printed By:"+hrno+" On: "+dateNow1,columnheader));			  
		  table2.addCell(perpernr1);*/
		  PdfPCell perpernrv = new PdfPCell(new Paragraph("https://portal.microlabs.co.in",smallfont));			  
		  table2.addCell(perpernrv);
		  
		  PdfPCell perpernr3 = new PdfPCell(new Paragraph("Printed By:"+hrno+" On: "+dateNow1,columnheader));			  
		  table2.addCell(perpernr3);
		  
	
			
		
			
			 
			 table2.writeSelectedRows(0, -1, 36, 36, writer.getDirectContent());
		    

		       /*try {
				document.add(table2);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		
		       
	
        
    }
    
    
   
} 