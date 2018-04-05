
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

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
  
  <body>

 
<html:form action="hr">

	

	<table class="bordered content" width="80%" border="1">
		 <tr><th  colspan="7" align="center">
					<center> HR Asset List</center></th>
  						</tr>  		
  						<tr><th>Sl No</th><th>Asset type</th><th>Asset Description</th><th>Emp Name</th>
  						<th>Model</th><th>Make</th><th>Vehicle No</th><th>Insurance No</th><th>Insurance Company</th>
  						<th>Insurance Issue date</th><th>Insurance Expiry date</th><th>Previous User</th><th>Service Provider</th><th>Data Card No</th>
  						<th>Default Password</th><th>Data Plan</th><th>Deleted</th><th>Created By</th><th>Created date</th><th>Modified By</th><th>Modified date</th></tr>
  						<logic:notEmpty name="hrassets">
  						<%int f=0; %>
  						<logic:iterate id="hr" name="hrassets">
  						<%f++; %>
  						<tr><td><%=f %></td>
  						<td>${hr.assetType }</td><td>${hr.assetDesc }</td><td>${hr.empname }</td>
  						<td>${hr.make }</td><td>${hr.model }</td><td>${hr.vehicle_no }</td><td>${hr.insurance_no }</td><td>${hr.insurance_Compny }</td>
  						<td>${hr.insurance_Issue_date }</td><td>${hr.insurance_Exp_date }</td><td>${hr.prev_user }</td><td>${hr.service_provider }</td><td>${hr.data_card_no }</td>
  						<td>${hr.default_pwd }</td><td>${hr.service_plan }</td><td>${hr.delete_flag }</td><td>${hr.created_by }</td><td>${hr.created_Date }</td>
  						<td>${hr.modified_by }</td><td>${hr.modified_date }</td>
  						</tr>
  					
  						</logic:iterate>
  						</logic:notEmpty>
  						<logic:empty name="hrassets">
  						<tr><td colspan="10">No records to display</td></tr>
  						</logic:empty>
						
	
	</table>








</html:form>
  </body>
</html>
