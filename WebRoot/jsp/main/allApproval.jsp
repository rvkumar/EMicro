
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.main.form.ApprovalsForm"/>
<jsp:directive.page import="com.microlabs.ess.form.RawMaterialForm"/>



<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>Microlab</title>

 <script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/approvals.js"></script>

<style type="text/css">
.openMail{
display:inline-block;
border-radius: 5px;
position: fixed;
width: 99%; 
z-index: 10;
height:300px;
box-shadow: 1px 1px 3px 2px #63b8ff;
-moz-box-shadow: 1px 1px 3px 2px #63b8ff;
  -webkit-box-shadow: 1px 1px 3px 2px #63b8ff;
}
</style>
</head>

<body>
	<!--------WRAPER STARTS -------------------->
		
	<div id="wraper">   
                
    	<div style="padding-left:15px; width: 100%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
					
				<html:form action="/approvals.do" enctype="multipart/form-data">
				<%
					String header = (String)request.getAttribute("header");
					System.out.println("header -> "+header);
					String status=(String)session.getAttribute("result");		
					if(status==""||status==null)
					{
				
					}
					else{
			
				%>
				<b><font color="red"><%=status %></font></b>
				<%
						session.setAttribute("result", " ");
					}
		 		%>
		 		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		 			<tr>
      					<td align="left"><logic:notEmpty name="approvalsForm">
      						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr style="border-collapse:collapse;" class="heading widgetTitle"><td id="heading"><bean:write property="heading" name="approvalsForm"/></td></tr>
		          			</table>
		          			</logic:notEmpty>
          				</td>
          			</tr>
          			<tr style="height:5px;"><td></td></tr>
      				<tr>
          				<td align="left"><logic:notEmpty name="approvalsForm">
    						<table width="75%" border="0" style="padding-left: 10%;">
								<tr>
									<td><b>Request Type : </b>
										<html:select property="reqRequstType" styleClass="text_field tdstyle" style="border-radius: 5px;width:200px;" styleId="requestSelectId">
										<html:option value="">Select</html:option>
										<html:option value="ROH">ROH</html:option>
										<html:option value="VERP">VERP</html:option>
										<html:option value="FERT">FERT</html:option>
										<html:option value="HAWA">HAWA</html:option>
										<html:option value="SEMI FINISHED">HALB</html:option>
										<html:option value="PROMOTIONAL">PROMOTIONAL</html:option>
										<html:option value="PRINTED">PRINTED </html:option>
										<html:option value="COMPLIMENTS">COMPLIMENTS</html:option>
										<html:option value="ZPPC">ZPPC</html:option>
										<html:option value="ZPSR">ZPSR-GENERAL MATERIAL</html:option>
										<html:option value="ZLAB">ZLAB-GENERAL MATERIAL</html:option>
										<html:option value="ZCIV">ZCIV-GENERAL MATERIAL</html:option>
										<html:option value="ZCON">ZCON-GENERAL MATERIAL</html:option>
										<html:option value="Custemer Master">Custemer Master</html:option>
										<html:option value="Vendor Master">Vendor Master</html:option>
										<html:option value="Service Master">Service Master</html:option>
										<html:option value="Code Extention">Code Extention</html:option>
										<html:option value="Leave">Leave</html:option>	
										<html:option value="On Duty">On Duty</html:option>
										<html:option value="Recruitment Request">Recruitment Request</html:option>
										</html:select>

									</td>
									
									<td><b>Filter by : </b><html:select property="selectedFilter" styleClass="text_field tdstyle" style="border-radius: 5px; width: 150px;" styleId="filterId" onchange="showSelectedFilter()">
									    <html:options property="filterType" labelProperty="filterType"/>
								 	</html:select></td>
								</tr>
         						<!--  tr><td width="3px"></td><td class="underline"></td></tr-->
         					</table></logic:notEmpty>
          				</td>
          			</tr>
          			<%if(header.contains("Recruitment")){ %>
					<logic:notEmpty name="openRequest">
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;overflow-y:auto;">
          						<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%">
          							<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Job Title: </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="jobTitle"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department: </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location: </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details</th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/> From </td><td style="border:0px;"><bean:write name="approvalsForm" property="requestedBy"/><html:text property="requestedBy" styleClass="text_field" style="width:730px; display:none;" maxlength="80" styleId="requestBy" ><bean:write property="requestedBy" name="approvalsForm"/></html:text></td></tr>
									<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
									<tr><td style="border:0px;"><span>Job Description :</span><bean:write name="approvalsForm" property="jobDescription"/></td><td style="border:0px;"><span>Employee Status :</span><bean:write name="approvalsForm" property="empStatus"/></td></tr>
									<tr><td style="border:0px;"><span>Required Empolyee :</span><bean:write name="approvalsForm" property="totalEmp"/></td><td style="border:0px;"><span>Qualification :</span><bean:write name="approvalsForm" property="qualifications"/></td></tr>
									<tr><td style="border:0px;"><span>Experience :</span><bean:write name="approvalsForm" property="experience"/></td><td style="border:0px;"><span>Salary Offered :</span><bean:write name="approvalsForm" property="saleryOffered"/></td></tr>
									</table>
									</td></tr>
									<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"><bean:write name="approvalsForm" property="remark"/></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
									</logic:notEmpty>
          						</table>
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} else if(header.contains("Leave")) {%>
          			<logic:notEmpty name="openRequest" >
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;overflow-y:auto;">
          						<table class="bordered" width="90%">
          							<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Title: </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="jobTitle"/> For <bean:write name="approvalsForm" property="empStatus"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department: </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location: </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details</th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/> From <bean:write name="approvalsForm" property="requestedBy"/></td></tr>
									<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
										<logic:notEmpty name="details">
										<logic:iterate name="details" id="detailsView">
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">Leave Details submitted @ <bean:write name="approvalsForm" property="requestDate"/></td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Leave/Onduty Type : </span>${detailsView.leaveType}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Duration : </span>${detailsView.holidayType}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Name : </span>MR/MRS/MS. ${detailsView.employeeName}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Start Date : </span>${detailsView.startDate}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">No of Days : </span>${detailsView.noOfDays}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Reason : </span>${detailsView.reason}</td>
										</tr>
										<tr>
											<td style="border:0px;"><span>Documents : </span>
												<logic:notEmpty name="listName">
												<table>
													<tr><td style="border:0px;">
													<logic:iterate name="listName" id="listid">
													<bean:define id="file" name="listid" property="fileNameList" />
				
													<%
														String s = file.toString();
														String v[] = s.split(",");
														int l = v.length;
														for (int i = 0; i < l; i++) 
														{
															int x=v[i].lastIndexOf("/");
															String u=v[i].substring(x+1);
													%>
													<span><a target="_blank" href="jsp/EMicro Files/ESS/Leave/<%=u%>"><%=u%></a></span>
													<%  } %>
													</logic:iterate>
													</td></tr>
												</table>
												</logic:notEmpty>		
											</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
									</table>
									</td></tr>
									<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
									</logic:notEmpty>
          						</table>
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} else if(header.contains("Permission")) {%>
          			<logic:notEmpty name="openRequest" >
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;overflow-y:auto;">
          						<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%">
          							<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Title: </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="requestType"/> </td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department: </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location: </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details</th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/> From <bean:write name="approvalsForm" property="requestedBy"/></td></tr>
									<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
										<logic:notEmpty name="details">
										<logic:iterate name="details" id="detailsView">
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">Permission Details submitted @ <bean:write name="approvalsForm" property="requestDate"/></td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Permission Date : </span>${detailsView.date}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Time : </span>${detailsView.startTime}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Reason : </span>${detailsView.reason}</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
									</table>
									</td></tr>
									<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
									</logic:notEmpty>
          						</table>
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} else if(header.contains("Vendor Master")) {%>
          			<logic:notEmpty name="openRequest">
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;overflow-y:auto;">
          					<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%">
          						<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Title : </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="requestType"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
								<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department : </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
								<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location : </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
								<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details </th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/> From <bean:write name="approvalsForm" property="requestedBy"/></td></tr>
								<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
          								<logic:notEmpty name="details">
										<logic:iterate name="details" id="detailsView">
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">General Data:</td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Account Group : </span>${detailsView.accountGroupId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">View Type : </span>${detailsView.purchaseView}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Name : </span>${detailsView.title} . ${detailsView.name}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address1 : </span>${detailsView.address1}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address2 : </span>${detailsView.address2}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address3 : </span>${detailsView.address3}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address4 : </span>${detailsView.address4}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">City : </span>${detailsView.city}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">PinCode : </span>${detailsView.pinCode}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Country : </span>${detailsView.country}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">State : </span>${detailsView.state}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Land line No : </span>${detailsView.landLineNo}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Mobile No : </span>${detailsView.mobileNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Fax No : </span>${detailsView.faxNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Email ID : </span>${detailsView.emailId}</td>
										</tr>
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">Tax Details : </td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Currency : </span>${detailsView.currencyId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Reconciliation Account : </span>${detailsView.reConcillationActId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Is Eligible For Tds : </span>${detailsView.elgTds}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Tds Code : </span>${detailsView.tdsCode}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">LST No : </span>${detailsView.lstNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Tin No : </span>${detailsView.tinNo}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">CST No : </span>${detailsView.cstNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Payment Term : </span>${detailsView.paymentTermId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Account Clerk : </span>${detailsView.accountClerkId}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Is Approved Vendor : </span>${detailsView.isApprovedVendor}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">PAN No : </span>${detailsView.panNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Service Tax Registration No : </span>${detailsView.serviceTaxRegNo}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Is Registered Excise Vendor : </span>${detailsView.regExciseVendor}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">ECC No. : </span>${detailsView.eccNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Excise Reg No. : </span>${detailsView.exciseRegNo}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Excise Range : </span>${detailsView.exciseRange}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Excise Division : </span>${detailsView.exciseDivision}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Commissionerate : </span>${detailsView.commissionerate}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Type OF Vendor : </span>${detailsView.typeOfVendor}</td>
											
											<td colspan="2" style="border:0px;"><span>Documents : </span>
												<logic:notEmpty name="listName">
												<table>
													<tr><td style="border:0px;">
													<logic:iterate name="listName" id="listid">
													<bean:define id="file" name="listid" property="fileNameList" />
				
													<%
														String s = file.toString();
														String v[] = s.split(",");
														int l = v.length;
														for (int i = 0; i < l; i++) 
														{
															int x=v[i].lastIndexOf("/");
															String u=v[i].substring(x+1);
													%>
													<span><a target="_blank" href="<bean:write name="approvalsForm" property="url"/>/<%=u%>"><%=u%></a></span>
													<%  } %>
													</logic:iterate>
													</td></tr>
												</table>

												</logic:notEmpty>		
											</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
									</table>
          						</td></tr>
          						<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
								</logic:notEmpty>
          					</table>	
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} else if(header.contains("Customer Master")) {%>
          			<logic:notEmpty name="openRequest">
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;overflow-y:auto;">
          						<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%">
          						<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Title : </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="requestType"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
								<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department : </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
								<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location : </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
								<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details </th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/> From <bean:write name="approvalsForm" property="requestedBy"/></td></tr>
								<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
          								<logic:notEmpty name="details">
										<logic:iterate name="details" id="detailsView">
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">General Data:</td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Account Group : </span>${detailsView.accGroupId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">View Type : </span>${detailsView.viewType}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Customer Type : </span>${detailsView.custmerType}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Name : </span>MR/MRS/MS. ${detailsView.customerName}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address1 : </span>${detailsView.address1}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address2 : </span>${detailsView.address2}</td>
											
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address3 : </span>${detailsView.address3}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Address4 : </span>${detailsView.address4}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">City : </span>${detailsView.city}</td>
											
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">PinCode : </span>${detailsView.pincode}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Country : </span>${detailsView.countryId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">State : </span>${detailsView.state}</td>
											
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Land line No : </span>${detailsView.landlineNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Mobile No : </span>${detailsView.mobileNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Fax No : </span>${detailsView.faxNo}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Email ID : </span>${detailsView.emailId}</td>
										</tr>
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">Sales : </td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Customer Group : </span>${detailsView.customerGroup}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Price Group : </span>${detailsView.priceGroup}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Price List : </span>${detailsView.priceList}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Tax Type : </span>${detailsView.taxType}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Currency : </span>${detailsView.currencyId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Payment Term : </span>${detailsView.paymentTermID}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Account Clerk : </span>${detailsView.accountClerkId}</td>
										</tr>
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">Excise / Tax:</td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Is Registered Excise Customer : </span>${detailsView.tdsStatus}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">TDS CODE : </span>${detailsView.tdsCode}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">LST Number : </span>${detailsView.listNumber}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Tin Number : </span>${detailsView.tinNumber}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">CST Number : </span>${detailsView.cstNumber}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">PAN Number : </span>${detailsView.panNumber}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Service Tax Registration No : </span>${detailsView.serviceTaxNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">IS REGD EXCISE VENDOR : </span>${detailsView.isRegdExciseVender}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">ECC No : </span>${detailsView.eccNo}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Excise Reg Number : </span>${detailsView.exciseRegNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Excise Range : </span>${detailsView.exciseRange}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Excise Division : </span>${detailsView.exciseDivision}</td>
										</tr>
										<tr>
											
											<td colspan="3" style="border:0px;"><span>Documents : </span>
												<logic:notEmpty name="listName">
												<table>
													<tr><td style="border:0px;">
													<logic:iterate name="listName" id="listid">
													<bean:define id="file" name="listid" property="fileNameList" />
				
													<%
														String s = file.toString();
														String v[] = s.split(",");
														int l = v.length;
														for (int i = 0; i < l; i++) 
														{
															int x=v[i].lastIndexOf("/");
															String u=v[i].substring(x+1);
													%>
													<span><a target="_blank" href="<bean:write name="approvalsForm" property="url"/>/<%=u%>"><%=u%></a></span>
													<%  } %>
													</logic:iterate>
													</td></tr>
												</table>
												</logic:notEmpty>		
											</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
									</table>
          						</td></tr>
          						<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
								</logic:notEmpty>
          						</table>
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} else if(header.contains("Feedback")) {%>
          			<logic:notEmpty name="openRequest">
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;">
          						<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%">
          							<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Title : </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="requestType"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department : </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location : </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details </th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/></td></tr>
									<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
									<tr><td style="border:0px;"><span>Subject : </span><bean:write name="approvalsForm" property="empStatus"/></td><td style="border:0px;"><span>Date : </span><bean:write name="approvalsForm" property="requestDate"/></td></tr>
									<tr><td style="border:0px;"><span>Comments : </span><bean:write name="approvalsForm" property="saleryOffered"/></td></tr>
									</table>
									</td></tr>
									<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
									</logic:notEmpty>
          						</table>
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} else if(header.contains("Material Code Request")) {%>
          			<logic:notEmpty name="openRequest">
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;overflow-y:auto;">
          						<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%;">
          							<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Title : </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="requestType"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department : </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location : </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details </th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/> From <bean:write name="approvalsForm" property="requestedBy"/></td></tr>
									<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
										<logic:notEmpty name="details">
										<logic:iterate name="details" id="detailsView">
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Material Type : </span>${detailsView.materialTypeId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Material Name Short: </span>${detailsView.materialShortName}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Material Long Short: </span>${detailsView.materialLongName}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Material Group : </span>${detailsView.materialGroupId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Unit Of Measurement : </span>${detailsView.unitOfMeasId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Purchasing Group : </span>${detailsView.puchaseGroupId}</td>
										</tr>
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">Quality Specification:</td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Pharmacopoeial&nbspName : </span>${detailsView.pharmacopName}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Pharmacopoeial&nbspGrade : </span>${detailsView.pharmacopGrade}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Generic Name : </span>${detailsView.genericName}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Synonym : </span>${detailsView.synonym}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Pharmacopoeial&nbspSpecification : </span>${detailsView.pharmacopSpecification}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Duty Element : </span>${detailsView.dutyElement}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Is DMF Material : </span>${detailsView.isDMFMaterial}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">DMF Grade : </span>${detailsView.dmfGradeId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Material Grade : </span>${detailsView.materialGrade}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">COS Grade No : </span>${detailsView.cosGradeNo}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Additional Test : </span>${detailsView.additionalTest}</td>
										</tr>
										<tr><td style="border:0px;background-color:#fff;border-radius:5px;">Vendor Manufacture Information:</td></tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Is Vendor Specific Material : </span>${detailsView.isVendorSpecificMaterial}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Manufacture  Name : </span>${detailsView.mfgrName}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Site Of Manufacture : </span>${detailsView.siteOfManufacture}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Country : </span>${detailsView.countryId}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Customer Name : </span>${detailsView.customerName}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">To Be Used In Product (S) : </span>${detailsView.toBeUsedInProducts}</td>
										</tr>
										<tr>
											<td style="border:0px;background-color:#fff;border-radius:5px;">Other Details:</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Temp.Condition : </span>${detailsView.tempCondition}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Storage Condition : </span>${detailsView.storageCondition}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Shelf Life : </span>${detailsView.shelfLife}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Retest Days : </span>${detailsView.retestDays}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Valuation Class : </span>${detailsView.valuationClass}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Approximate Value : </span>${detailsView.approximateValue}</td>
										</tr>
										<tr>
											<td style="border:0px;"><span>Documents : </span>
												<logic:notEmpty name="listName">
												<table>
													<tr><td style="border:0px;">
													<logic:iterate name="listName" id="listid">
													<bean:define id="file" name="listid" property="fileNameList" />
				
													<%
														String s = file.toString();
														String v[] = s.split(",");
														int l = v.length;
														for (int i = 0; i < l; i++) 
														{
															int x=v[i].lastIndexOf("/");
															String u=v[i].substring(x+1);
													%>
													<span><a target="_blank" href="<bean:write name="approvalsForm" property="url"/>"><%=u%></a></span>
													<%  } %>
													</logic:iterate>
													</td></tr>
												</table>

												</logic:notEmpty>		
											</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
										<logic:notEmpty name="codeCreation">
										<tr>
											<td colspan="2" style="border:0px;"><span style="border:0px;padding-right: 12px;">SAP Code Exists <img src="images/star.gif" width="8" height="8" />: </span><html:select property="sapCodeExists" styleClass="text_field" >
												<html:option value="">-----Select-----</html:option>
												<html:option value="True">Yes</html:option>
												<html:option value="False">No</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border:0px;"><span style="border:0px;padding-left: 20px; padding-right: 12px;">SAP Code No <img src="images/star.gif" width="8" height="8" />: </span><html:text property="sapCodeNo" styleClass="text_field" style="width:200px;"></html:text></td>
										</tr>
			
										<tr>	
											<td colspan="2" style="border:0px;"><span style="border:0px;padding-right: 3px;">SAP Creation Date <img src="images/star.gif" width="8" height="8" />: </span><html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" readonly="true" styleClass="text_field" style="width:200px;"/></td>
										</tr>
										<tr>
											<td colspan="2" style="border:0px;"><span style="border:0px;padding-left: 5px;padding-right: 12px;">SAP Created By <img src="images/star.gif" width="8" height="8" />: </span><html:text property="sapCreatedBy" styleClass="text_field"  style="width:200px;"></html:text></td>
										</tr>
										<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Create Code" onclick="createCode(this)" /><input type="button" class="rounded" value="Cancel" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"/></td></tr>
										</logic:notEmpty>
									</table>
									</td></tr>
									<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
									</logic:notEmpty>
          						</table>
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} else if(header.contains("Service Master")) {%>
          			<logic:notEmpty name="openRequest">
          				<tr><td>
          					<div id="openMailDiv" class="openMail" style="top:5px;left:9px;overflow-y:auto;">
          						<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%">
          							<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Title : </th><td style="width:60%; border:0px;"><bean:write name="approvalsForm" property="requestType"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;cursor: pointer;" onclick="closeOpenDiv('<bean:write name="approvalsForm" property="reqStatus"/>')"><img id="closeMail" src="images/Close1.jpg" width="16" height="16"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Department : </th><td colspan="2" style="width:60%; border:0px;"><bean:write name="approvalsForm" property="department"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Location : </th><td colspan="2"style="width:60%; border:0px;"><bean:write name="approvalsForm" property="primaryLocation"/></td></tr>
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Details </th><td style="border:0px;"><bean:write name="approvalsForm" property="requestType"/></td></tr>
									<tr><td style="border:0px;"></td><td colspan="2" style="border:0px;">
									<table style="width:100%; background:#f1f1f1; border-radius:5px;">
										<logic:notEmpty name="details">
										<logic:iterate name="details" id="detailsView">
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Service Description : </span>${detailsView.serviceDescription}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Detailed Service description : </span>${detailsView.detailedServiceDescription}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">U.O.M : </span>${detailsView.uom}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Purcahse Group : </span>${detailsView.purchaseGroup}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Service Catagory : </span>${detailsView.serviceCatagory}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Service Group : </span>${detailsView.serviceGroup}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Equipment/Machine Name : </span>${detailsView.e_m_name}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Approximate Value : </span>${detailsView.app_amount}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Where Used : </span>${detailsView.whereUsed}</td>
										</tr>
										<tr>
											<td style="width:30%; border:0px;"><span style="border:0px;">Purpose : </span>${detailsView.purpose}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Justification : </span>${detailsView.justification}</td>
											<td style="width:30%; border:0px;"><span style="border:0px;">Valuation Class : </span>${detailsView.valuationClass}</td>
										</tr>
										<tr>
											<td style="border:0px;"><span>Documents : </span>
												<logic:notEmpty name="listName">
												<table>
													<tr><td style="border:0px;">
													<logic:iterate name="listName" id="listid">
													<bean:define id="file" name="listid" property="fileNameList" />
				
													<%
														String s = file.toString();
														String v[] = s.split(",");
														int l = v.length;
														for (int i = 0; i < l; i++) 
														{
															int x=v[i].lastIndexOf("/");
															String u=v[i].substring(x+1);
													%>
													<span><a target="_blank" href="<bean:write name="approvalsForm" property="url"/>/<%=u%>"><%=u%></a></span>
													<%  } %>
													</logic:iterate>
													</td></tr>
												</table>
												</logic:notEmpty>		
											</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
									</table>
									</td></tr>
									<logic:notEmpty name="approveRequest">
									<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Comments </th><td colspan="2" style="width:60%; border:0px;"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
									<tr><td colspan="3" style="border:0px; text-align: center;"><input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" /></td></tr>
									</logic:notEmpty>
          						</table>
          					</div>			
          				</td></tr>
          			</logic:notEmpty>
          			<%} %>
          			<tr style="height:10px;"><td></td></tr>
          			<logic:empty name="openRequest">
          			<tr><td align="center" valign="top">
          			<table align="center" style="height:30px;width:100%;">
						<logic:notEmpty name="displayRecordNo">
				 			<tr>
				 				<td style="width:35%;"></td>
				 				<logic:notEmpty name="veryFirst">
								<td style="width:2%;"><img src="images/First10.jpg" id="veryFirstItem" onclick="sentNavigation('veryFirst')"/></td>
								</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
								<td style="width:2%;"><img src="images/disableLeft.jpg" /></td>
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
								<td style="width:2%;"><img src="images/previous1.jpg" id="privSetItem" onclick="sentNavigation('prev')"/></td>
								</logic:notEmpty>

								<td id="secnt" style="width:8%;text-align:center;"><bean:write property="startAppCount"  name="approvalsForm"/>&nbsp;-&nbsp;<bean:write property="endAppCount"  name="approvalsForm"/></td>
								<logic:notEmpty name="nextButton">
								<td id="nextSet" style="width:2%;"><img src="images/Next1.jpg" id="nextSetItem" onclick="sentNavigation('next')"/></td>
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
								<td style="width:2%;"><img src="images/disableRight.jpg"/></td>
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
								<td style="width:2%;"><img src="images/Last10.jpg" id="atLastItem" onclick="sentNavigation('atLast')"/></td>
								</logic:notEmpty>
								<td style="align:right;text-align:center;">
									<img src="images/clear.jpg" style="vertical-align:middle;" onclick="searchInApprovals('clear');" width="25" height="25" />
									<input type="text" id="searchText" style="padding-top: 3px; width: 200px;" class="rounded" value="Search in Approvals" onmousedown="this.value='';"/>
									<img src="images/search-bg.jpg" style="vertical-align:middle;" onclick="searchInApprovals('search')" width="40" height="50" />
								</td>
							</tr>
						</logic:notEmpty>
					</table>
          			</td></tr>
          			<tr style="height:15px;"><td></td></tr>
          			<tr><td>
          			<div align="left" class="bordered">
					<table border="1"  style="aligh:right;width:100%;font-size:12px;" class="sortable">
						<tr>
							<th style="text-align:left;"><b>Request No</b></th>
							<th style="text-align:left;"><b>Requested By</b></th>
							<th style="text-align:left;"><b>Request Type</b></th>
							<th style="text-align:left;"><b>Requested Date</b></th>	
						</tr>
						<logic:empty name="listDetails">					
							<tr><th class="head" align="center" colspan="5" bgcolor="#51B0F8" style="display:none;"><%=header %></th></tr>
							<logic:notEmpty name="noRecords">
							<tr>
	        					<td  colspan="7" style="text-align:center;"><bean:write name="approvalsForm" property="appMessage"/></td>
	            			</tr>
	            			</logic:notEmpty>
	            			<logic:empty name="noRecords">
							<tr><td colspan="4" style="text-align:center;">No records Found!</td></tr>
							</logic:empty>
						</logic:empty>
						<logic:notEmpty name="listDetails">
						<tr><th colspan="4" class="head" align="center" bgcolor="#51B0F8" style="display:none;"><%=header %></th></tr>
						
						<logic:iterate name="listDetails" id="abc">
						<tr class="tableOddTR">
							<td id="${abc.requestNo}"><a href="${abc.url}">${abc.requestNo }</a></td>
							<td><bean:write name="abc" property="requestedBy" /></td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td><bean:write name="abc" property="requestDate" /></td>	
						</tr>
						</logic:iterate>

						</logic:notEmpty>						
					</table>
					</div>
					</td></tr>
					</logic:empty>
					</table>
					<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
				</html:form>
		</div> <!--------CONTENT ENDS -------------------->
	</div><!--------WRAPER ENDS -------------------->

 
</body>
</html>
