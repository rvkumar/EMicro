
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.hr.form.HRRecruitmentForm"/>



<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
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

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">


function showOpenings(){
	//var lName = document.getElementById("selectedId").value;
	var url="hrRecruitment.do?method=displayCurrentOpenings&sId=Current Openings&id=HR";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}

</script>
</head>

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg','images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg')">
	<!--------WRAPER STARTS -------------------->
		
	<div id="wraper">   
                
    	<div style="padding-left: 10px;width: 100%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
        	<div>
					
				<html:form action="/hrRecruitment.do" enctype="multipart/form-data">
				<%
					String header = (String)request.getAttribute("header");
					String recreq = (String)request.getAttribute("recreq");
					System.out.println(header +"  <- header-recreq ->"+recreq);

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
				<center>
				<logic:empty name="openingMoreDetails">
				<table id="mytable1"> 
					
					<tr>
						<th width="200" class="specalt" scope="row">Location Name</th>
						<td style="border:0px;"><html:select property="location" style="background-color:#f6f6f6; width:150px; height:20px;" styleId="selectedlocId" onchange="showOpenings()">
									            <html:options property="locationId" labelProperty="locationName"/>
								               </html:select>	
						</td>
						
                   	</tr>
                   	<tr></tr>
				</table>
				<br/>
				
				<table align="center" id="mytable1" style="text-transform:capitalize;" width="100%" border="0" align="left" cellpadding="0" cellspacing="0"> 
					<logic:empty name="listDetails">					
						<tr><th class="head" align="center" colspan="6" bgcolor="#51B0F8"><%=header %></th></tr>
						<tr><td colspan="6" style="text-align:center;">No records for selected location!</td></tr>
					</logic:empty>
					<logic:notEmpty name="listDetails">
						<tr><th colspan="6" class="head" align="center" bgcolor="#51B0F8"><%=header %></th></tr>
						<logic:iterate name="listDetails" id="abc">
							
						<tr style="display: list-item; list-style: none;">
							<td style="color:#127BCA; border:0px;width:20%;"><bean:write name="abc" property="department" /></td>
							<td style="border:0px; width:10%;"><bean:write name="abc" property="primaryLocation" /></td>
							<td style="border:0px; wid	th:10%;"><bean:write name="abc" property="jobTitle" /></td>
							<td style="border:0px; width:20%;">Contact: <bean:write name="abc" property="requesterName" /></td>
							<td style="width:20%; border:0px;"><a href="<bean:write name="abc" property="moreUrl" />"><input id="moredetail" class="sudmit_btn" style="height:20px;" value="More Details" onmousedown="" /></a></td>
							<td style="width:20%; border:0px;"><a href="<bean:write name="abc" property="applyUrl" />"><input id="apply" class="sudmit_btn" style="height:20px;" value="Apply" onmousedown="" /></a></td>
						</tr>
						<tr style="display: list-item; list-style: none; width:100%;" class="underline"></tr>

						</logic:iterate>

					</logic:notEmpty>					
				</table>
				</logic:empty>
				<br/>
				<logic:notEmpty name="openingMoreDetails">
				<logic:iterate name="openingMoreDetails" id="abc">
				<table id="mytable1" style="border:2px solid; border-radius: 5px;" >
					<tr><td style="border:0px;"><h2 style="color:#51B0F8;"><bean:write name="abc" property="department" /></h2><p><bean:write name="abc" property="jobTitle" /><br/><bean:write name="abc" property="primaryLocation" /></p></td></tr>
					<tr><td style="border:0px;"><h3 style="color:#51B0F8;">Job Profile:</h3></td></tr>
					<tr><td style="border:0px;">Experience : <bean:write name="abc" property="experience" />&#09;Year</td></tr>
					<tr><td style="border:0px;">Shift Type : <bean:write name="abc" property="shiftType" /></td></tr>
					<tr><td style="border:0px;">Role : <bean:write name="abc" property="jobTitle" /></td></tr>
					<tr><td style="border:0px;">Qualification and Skills : <bean:write name="abc" property="qualifications" /></td></tr>
					<tr><td style="border:0px;"><h3 style="color:#51B0F8;">Job Description:</h3></td></tr>
					<tr><td style="border:0px;"><bean:write name="abc" property="jobDescription" /></td></tr>
					<tr><td style="width:20%; border:0px; align:center;"><a href="<bean:write name="abc" property="applyUrl" />"><input id="apply" class="sudmit_btn" style="height:20px;" value="Apply" onmousedown="" /></a></td></tr>
				</table>
				</logic:iterate>
				</logic:notEmpty>
				
				</center>
				</html:form>
			</div>
		</div> <!--------CONTENT ENDS -------------------->
	</div><!--------WRAPER ENDS -------------------->

 
</body>
</html>
