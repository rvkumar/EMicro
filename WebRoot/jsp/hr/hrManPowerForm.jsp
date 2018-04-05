<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<jsp:directive.page import="com.microlabs.hr.form.HRManPowerMatrixForm"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<title>Microlab</title>
<script type="text/javascript">

function popupCalender(param)
{
	var cal = new Zapatec.Calendar.setup({inputField : param, singleClick : true, ifFormat : "%d/%m/%Y ", showsTime : false, button : "button2" });
}

function doSubmit(){
	var sData = $("#manpowerForm").serialize();
	$.ajax({
            type: "POST",
            url: "hrManPower.do?method=sendManPowerRequest&sId=Man Power Matrix&id='Recruitment Request'",
            data: sData,
            success: function(successMsg) {
            		document.getElementById("successful").style.display="";
            		document.getElementById("successful").innerHTML = "Request uploaded successfully!";
					$("#successful").fadeOut(5000);
					document.getElementById("manpowerForm").reset();
            } 
        });
}

</script>
<style type="text/css">
.thstyle{
width: 250px;
	background:#d1e8f4 url(../images/bullet1.gif) no-repeat;
}
.tdstyle{
width:250px;
}

.successStyle{
padding:2px 4px;
margin:0px;
border:solid 1px #FBD3C6;
border-radius: 5%;
background:#FDE4E1;
color:#CB4721;
font-family:Arial, Helvetica, sans-serif;
font-size:14px;
font-weight:bold;
text-align:center;
}

</style>

</head>

<body >
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
	<div style="padding-left: 10px;width: 100%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->

                <html:form action="/hrManPower.do" enctype="multipart/form-data" styleId="manpowerForm">
					<% String manordepart = (String)request.getAttribute("manordepart"); %>
					<table id="NewTable" style="border:0px;">
						<tr class="tablerowdark1">
							<th colspan="2" class="thstyle"><center> Department&nbsp;ManPower</center></th><td style="text-align:center; display:none;" id="successful" class="successStyle"><bean:write name="hrManPowerMatrixForm" property="message"/></td>
						</tr>
						<tr><td colspan="2" class="underline"></td></tr>
						<tr>
							<th scope="row">Plant : </th>
							<td>
								<bean:write name="hrManPowerMatrixForm" property="location"/>
							</td>
							
						</tr>
						<tr>
							<th class="thstyle" scope="row">Recruitment Id</th>
							<td>
								<html:text property="recuritmentID"  styleClass="text_field tdstyle"><bean:write name="hrManPowerMatrixForm" property="recuritmentID"/></html:text>
								<em><b>Ex : </b>short form of post name _ Number</em>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Department</th>
							<td>
								<html:text property="department" styleClass="text_field tdstyle"><bean:write name="hrManPowerMatrixForm" property="department"/></html:text>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Required Man Power</th>
							<td>
								<html:text property="reqEmp" styleClass="text_field tdstyle"></html:text>
							</td>
						</tr>
						<%if(!manordepart.equalsIgnoreCase("man")) {%>
						<tr>
							<th class="thstyle" scope="row">Post Name</th>
							<td>
								<html:text property="jobTitle"  styleClass="text_field tdstyle"></html:text>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Budget Assigned</th>
							<td>
								<html:text property="departmentInvestment"  styleClass="text_field tdstyle"></html:text>
								<em>CTC in Lakhs</em>
							</td>
						</tr>
						
						<tr>
							<th class="thstyle" scope="row">Monthly Salary</th>
							<td>
								<html:text property="monthlySalary"  styleClass="text_field tdstyle"></html:text>
							</td>
						</tr>
						
						<tr>
							<th class="thstyle" scope="row">Start Date</th>
							<td>
								<html:text property="startDate"  styleClass="text_field tdstyle" readonly="true" onfocus="popupCalender('startDate')"></html:text>
							</td>
						</tr>
						
						<tr>
							<th class="thstyle" scope="row">End Date</th>
							<td>
								<html:text property="endDate"  styleClass="text_field tdstyle" readonly="true" onfocus="popupCalender('endDate')"></html:text>
							</td>
						</tr>
						<%} %>
						<tr>
							<td colspan="2" align="center"><input id="submitForm" class="sudmit_btn" style="height:20px;" value="Save" onclick="doSubmit()"/></td>
						</tr>										
					</table>
						
            </html:form>

                
	</div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
  
                

</body>
</html>
