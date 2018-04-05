<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

	<title>eMicro :: Headlines_Announcement Creation</title>

<script type="text/javascript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
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



function onModify(){	

 var x= document.forms[0].subject.value;
	  
		
		  if(x.length>200)
	    {
	      alert(" Subject should be less then 200 characters");
	      document.forms[0].subject.focus();
	      return false;
	    } 
	     var st = document.forms[0].subject.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].subject.value=st;

 
			var url="announcement.do?method=updateAnnouncemetnts";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
function onDelete(){	   

var x=confirm("Are you sure You Want to Delete");

if(x==true){

		
			var url="announcement.do?method=deleteAnnouncemetnts";
			document.forms[0].action=url;
			document.forms[0].submit();	
		
			}
			
			else{
			
			return false;
			}
}
function onUpload(){	   
		
			var url="announcement.do?method=uploadFiles";
			document.forms[0].action=url;
			document.forms[0].submit();	
}

function onUploadVideo(){
	if(document.forms[0].headLinesType.value=="")
    {
      alert("Please Select Type ");
      document.forms[0].headLinesType.focus();
      return false;
    }
	if(document.forms[0].videoFileNames.value=="")
		{
		  alert("Please Choose Video File");
		  document.forms[0].videoFileNames.focus();
		  return false;
		}
	var url="announcement.do?method=uploadVideos";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
function deleteMainLinkUploadedVideos(){
	var rows=document.getElementsByName("checkedVideofiles");
	var checkvalues='';
	var uncheckvalues='';
	for(var i=0;i<rows.length;i++)
	{
	if (rows[i].checked)
	{
	checkvalues+=rows[i].value+',';
	}else{
	uncheckvalues+=rows[i].value+',';
	}
	}
	if(checkvalues=='')
	{
	alert('please select atleast one value to delete');
	}
	else
	{
	var agree = confirm('Are You Sure To Delete Selected file');
	if(agree)
	{

		document.forms[0].action="announcement.do?method=deleteVideo&checkvalues"+checkvalues;
		document.forms[0].submit();
	}
	}
	}	
	
	function back()
	{	
    window.close(); 
	}
	
		function addnew()
	{
	
	document.forms[0].action="announcement.do?method=displayAnnouncementForm";
    document.forms[0].submit();
	}
	
	
	
	
	
	function onSave()
{
 

document.forms[0].action="announcement.do?method=savrecipient";
document.forms[0].submit();

	
	}
	
	


</script>
</head>

<body>
	
		<html:form action="/announcement.do" enctype="multipart/form-data">
	<html:hidden property="headLinesType" />
	<html:hidden property="annoucementDate" />
	<html:hidden property="announcementTime" />
	<html:hidden property="subject" />
	
	<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="announcementForm" property="message">
				<font color="green" size="3"><b><bean:write name="announcementForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="announcementForm" property="message2">
				<font color="red" size="3"><b><bean:write name="announcementForm" property="message2" /></b></font>
				
			</logic:present>
		</div>
	
	
		<br/>		
		
	<table class="bordered" width="60%" style="width: 491px; ">
			<tr>
				<th style="text-align: center;" colspan="8"><big>ADD Recipients</big></th>
			</tr>

			<tr>
				<th>Location <font color="red">*</font></th>
				<td><html:select name="announcementForm" property="locationId" multiple="true">
							<html:option value="">--Select--</html:option>
							<html:options name="announcementForm" property="locationIdList" labelProperty="locationLabelList"/>
			    </html:select>
			    </td>
			    <th>Category<font color="red">*</font></th>
			    <td>
			    <html:select property="cat" name="announcementForm" multiple="true">
				<html:option value="">--Select--</html:option>
				<html:option value="1">Office Staff</html:option>
				<html:option value="2">Field Staff</html:option>
				<html:option value="3">Workers - Permanent</html:option>
				<html:option value="4">Workers - Temporary</html:option>
				<html:option value="5">Consultants</html:option>
				<html:option value="6">Workers - Union</html:option>
				<html:option value="7">Factory Staff</html:option>
				</html:select>
			 
			   
			   
			   </td> 
			   
			  <th>Department<font color="red">*</font></th>
			    <td><html:select name="announcementForm" property="department" multiple="true"  >
			   	<html:option value="">--Select--</html:option>
					<html:options name="announcementForm" property="deptIdList" labelProperty="deptLabelList"/>
				</html:select> 
			   		   
			   </td> 
			   
			   
			    
			 
				
			</tr>
			
			
			<tr>
					<td  colspan="8" style="text-align: center;">
						<html:button property="method" value="Save" onclick="onSave()" styleClass="rounded" style="width: 100px" />
					
					<html:button property="method" value="Back" onclick="back()" styleClass="rounded" style="width: 100px" />
					</td>
				</tr>
			
			
			
			
			</table>
	</html:form>

</body>
</html>
