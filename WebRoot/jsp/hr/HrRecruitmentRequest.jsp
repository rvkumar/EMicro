
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

function addNewDepart(){
	var url="hrRecruitment.do?method=newHrRecrutmentRequestForm&sId=Recruitment Request&id=HR";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function showManPower(){
	//var lName = document.getElementById("selectedId").value;
	var url="hrRecruitment.do?method=displayRecruitmentRequest&sId=Recruitment Request&id=HR";
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
							
				<table id="mytable1"> 
							 										
					<tr>
						<th width="200" class="specalt" scope="row">Location Name</th>
						<td style="border:0px;"><html:select property="location" style="background-color:#f6f6f6; width:150px; height:20px;" styleId="selectedlocId" onchange="showManPower()">
									            <html:options property="locationId" labelProperty="locationName"/>
								               </html:select>	
						</td>
						<td align="right" style="border:0px;"><input id="newDepart" class="sudmit_btn" style="height:20px;" value="New Request" onmousedown="addNewDepart();" /></td>
                   	</tr>
                   	<tr></tr>
                   	<tr>
						<th width="200" class="specalt" scope="row">Department Name</th>
						<td style="border:0px;"><html:select property="userDepart" style="background-color:#f6f6f6; width:150px; height:20px;" styleId="selecteddepartId" onchange="showManPower()">
									            <html:options property="departmentName" labelProperty="departmentName"/>
								               </html:select>	
						</td>
						<td style="border:0px;"></td>
                   	</tr>
				</table>
				<br/>
				<br/>

					<table border="1" class="forumline" align="center" id="mytable1">
						<logic:empty name="listDetails">					
							<tr><th class="head" align="center" colspan="5" bgcolor="#51B0F8"><%=header %></th></tr>
							<tr><td colspan="5" style="text-align:center;">No records for selected location!</td></tr>
						</logic:empty>
						<logic:notEmpty name="listDetails">
						<tr><th colspan="5" class="head" align="center" bgcolor="#51B0F8"><%=header %></th></tr>
						
						<tr>
							<th class="specalt" align=center><b>Recruitment Id</b></th>
							<th class="specalt" align=center><b>Department</b></th>
							<th class="specalt" align=center><b>Job Title</b></th>
							<th class="specalt" align=center><b>Required Employee</b></th>
							<th class="specalt" align=center><b>Request Status</b></th>	
						</tr> 
						<logic:iterate name="listDetails" id="abc">
							
						<tr>
							<td style="color:#127BCA;"><bean:write name="abc" property="recuritmentID" /></td>
							<td><bean:write name="abc" property="department" /></td>
							<td><bean:write name="abc" property="jobTitle" /></td>
							<td><bean:write name="abc" property="totalEmp" /></td>	
							<td><bean:write name="abc" property="approvalStatus" /></td>
						</tr>


						</logic:iterate>

						</logic:notEmpty>						
					</table>
				
				</center>
				</html:form>
			</div>
		</div> <!--------CONTENT ENDS -------------------->
	</div><!--------WRAPER ENDS -------------------->

 
</body>
</html>
