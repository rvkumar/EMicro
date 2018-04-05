
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.hr.form.HolidayForm"/>
<jsp:directive.page import="com.microlabs.hr.form.HRManPowerMatrixForm"/>



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

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">

<!--

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0

	document.getElementById('conPer').style.cssText = 'border: none;color: grey';
    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
  d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}



function onSave(){
	var url="adminHoliday.do?method=saveHolidays";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function onModify(){
	var url="adminHoliday.do?method=update";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function onDelete(){
	var url="adminHoliday.do?method=delete";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function showHolidays(){
	var lName = document.getElementById("selectedId").value;
	var url="holidays.do?method=displayHolidays&sId=Holiday List&id="+lName;
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

//-->
</script>
</head>

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg','images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg')">
	<!--------WRAPER STARTS -------------------->
		
	<div id="wraper">   
                
    	<div style="padding-left: 10px;width: 100%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
        	<div>
					
				<html:form action="/holidays.do" enctype="multipart/form-data">
				<%
					String header = (String)request.getAttribute("header");
					System.out.println(header);

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
		 		<div class="heading widgetTitle"><%=header %></div>
		 		<br/>
				<center>
					<div>Location Name :&nbsp;<html:select property="location" style="background-color:#f6f6f6; width:150px; height:20px;" styleId="selectedId" onchange="showHolidays()">
								          <html:options property="locationId" labelProperty="locationName"/>
							              </html:select>
					</div>
					<br/>
					<br/>

					<div align="left" class="bordered">
					<table border="1"  style="aligh:right;width:100%;font-size:12px;" class="sortable">
						<logic:empty name="listDetails">					
							<tr><th class="head" align="center" colspan="5" bgcolor="#51B0F8"><%=header %></th></tr>
							<tr><td colspan="5" style="text-align:center;">No records for selected location!</td></tr>
						</logic:empty>
						<logic:notEmpty name="listDetails">
						
						<tr height="20">
							<% if(header.equalsIgnoreCase("Holiday List")) {%>
							<th class="specalt" align=center><b>Sl. No</b></th>
							<th class="specalt" align=center><b>Holiday Name</b></th>
							<th class="specalt" align=center><b>Holiday Date</b></th>
							<th class="specalt" align=center><b>Day</b></th>
							<%} else{%>
							<th class="specalt" align=center><b>Sl. No</b></th>
							<th class="specalt" align=center><b>Recruitment Id</b></th>
							<th class="specalt" align=center><b>Department</b></th>
							<th class="specalt" align=center><b>Job Title</b></th>
							<th class="specalt" align=center><b>Required ManPower</b></th>
							<%}%>		
						</tr>
						<%
							int count = 1;
										
						%>
						<logic:iterate name="listDetails" id="abc">
							
						<tr>
							<td><%=count++ %></td>
							<% if(header.equalsIgnoreCase("Holiday List")) {%>
							<td><bean:write name="abc" property="holidayName" /></td>
							<td><bean:write name="abc" property="holidayDate"/></td>
							<td><bean:write name="abc" property="dayName" /></td>	
							
							<%} else {%>
							<td><bean:write name="abc" property="recuritmentID" /></td>
							<td><bean:write name="abc" property="department" /></td>
							<td><bean:write name="abc" property="jobTitle" /></td>
							<td><bean:write name="abc" property="totalEmp" /></td>
							<%} %>
										
						</tr>


						</logic:iterate>

						</logic:notEmpty>						
					</table>
					</div>
				
				</center>
				</html:form>
			</div>
		</div> <!--------CONTENT ENDS -------------------->
	</div><!--------WRAPER ENDS -------------------->

 
</body>
</html>
