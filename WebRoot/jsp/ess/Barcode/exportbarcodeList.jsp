<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 
</head>

<body >
 <html:form action="/barcode.do">

	
				
				<logic:notEmpty name="barcodelist">
			<div align="left" class="bordered">
			<table class="sortable" border="1">
			
				<tr>
				<th colspan="11"><center>Postal Received Requests</center></th>
				</tr>
			<tr>
		<th style="text-align:left;"><b>Location </b></th>
			<th style="text-align:left;"><b>Grn No</b></th>
			<th style="text-align:left;"><b>Grn Date</b></th>
			<th style="text-align:left;"><b>Material Type</b></th>
			<th style="text-align:left;"><b>PG Group</b></th>
			<th style="text-align:left;"><b>Vendor Name</b></th>
			<th style="text-align:left;"><b>City</b></th>
			<th style="text-align:left;"><b>Enter By</b></th>
			<th style="text-align:left;"><b>Enter Date</b></th>		
			<th style="text-align:left;"><b>Received By</b></th>	
			<th style="text-align:left;"><b>Received Date</b></th>	
			<th style="text-align:left;"><b>Received Sign</b></th>							
					</tr>
				<logic:iterate id="mytable1" name="barcodelist">
									<tr >
											<td>
				<bean:write name="mytable1" property="wERKS"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="mBLNR"/>
				</td>
				<td>
				<bean:write name="mytable1" property="bLDAT"/>
				</td>
				<td>
				<bean:write name="mytable1" property="mTART"/>
				</td>
				<td>
				<bean:write name="mytable1" property="eKGRP"/>
				</td>
				<td>
				<bean:write name="mytable1" property="name1"/>
				</td>
				<td>
				<bean:write name="mytable1" property="oORT01"/>
				</td>
		
				<td>
				<bean:write name="mytable1" property="empName"/>
				</td>
				
				<td>
				<bean:write name="mytable1" property="accdate"/>
				</td>
				
				<td></td>
				<td></td>
				<td></td>
				
				
				
				
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		
		
		</html:form>

	</body>
</html>
					
			