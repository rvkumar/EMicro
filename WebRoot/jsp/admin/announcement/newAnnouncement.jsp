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


function onSave(){	   
if(document.forms[0].headLinesType.value=="")
	    {
	      alert("Please Select Type ");
	      document.forms[0].headLinesType.focus();
	      return false;
	    }
	    
	 var x= document.forms[0].subject.value;
		  if(x.length>200)
	    {
	      alert(" Subject should be less then 200 characters");
	      document.forms[0].subject.focus();
	      return false;
	    }
		
			var url="announcement.do?method=saveAnnouncement";
		    document.forms[0].action=url;
			document.forms[0].submit();	
}
function onModify(){	

 var x= document.forms[0].subject.value;
	  
		
		  if(x.length>200)
	    {
	      alert(" Subject should be less then 200 characters");
	      document.forms[0].subject.focus();
	      return false;
	    } 

 
			var url="announcement.do?method=updateAnnouncemetnts";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
function onDelete(){	   
		
			var url="announcement.do?method=deleteAnnouncemetnts";
			document.forms[0].action=url;
			document.forms[0].submit();	
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

//-->
</script>
</head>

<body>
	<div align="center"  style="text-align: center;">
		<logic:present name="userGroupForm" property="message">
				<font color="red"><bean:write name="userGroupForm" property="message" /></font>
		</logic:present>
	</div>
		<html:form action="/announcement.do" enctype="multipart/form-data">
		<% 
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
		
		<div style="width: 100%">

		<logic:notEmpty name="listOfannouncement">
			<table class="bordered">
				<tr>
					<th style="text-align: center;" colspan="5"><big>Headlines and Announcements</big></th>
				</tr>
				
				<tr>
					<th style="text-align:left;"><b>Sl.No.</b></th>
					<th style="text-align:left;"><b>Type</b></th>
					<th style="text-align:left;"><b>Subject</b></th>	
					<th style="text-align:left;"><b>Date</b></th>
					<th style="text-align:left;"><b>Time</b></th>
				</tr>

				<tr><td colspan="5" class="underline"></td></tr>
					<%
						int count = 1;
					%>
					<%
						int count1 = 1;
					%>			
								
					<logic:iterate name="listOfannouncement" id="abc">
						<%if(count == 1) {%>
							<tr class="tableOddTR">
								<td>
									<a href="announcement.do?method=getAnnouncemetnts&id=<bean:write name="abc" property="id"/>"><%=count1++ %>
								</td>
									
								<td>
									<bean:write name="abc" property="headLinesType" />
								</td>
								<td>
									<bean:write name="abc" property="content" />
								</td>
								<td>
									<bean:write name="abc" property="annoucementDate" />
								</td>
								<td>
									<bean:write name="abc" property="announcementTime" />
								</td>
								
							</tr>
						<% count++;
							} else {
								int oddoreven=0;
								oddoreven  = count%2;
								if(oddoreven == 0)
								{
									%>
									<tr class="tableEvenTR">
										<td>
											<a href="announcement.do?method=getAnnouncemetnts&id=<bean:write name="abc" property="id"/>"><%=count1++ %>
										</td>
										<td>
											<bean:write name="abc" property="headLinesType" />
										</td>
										<td>
											<bean:write name="abc" property="content" />
										</td>
										<td>
											<bean:write name="abc" property="annoucementDate" />
										</td>
										<td>
											<bean:write name="abc" property="announcementTime" />
										</td>
									</tr>
									<% }else{%>
									<tr class="tableOddTR">
										<td>
											<a href="announcement.do?method=getAnnouncemetnts&id=<bean:write name="abc" property="id"/>"><%=count1++ %>
										</td>
										<td>
											<bean:write name="abc" property="headLinesType" />
										</td>
										<td>
											<bean:write name="abc" property="content" />
										</td>
										<td>
											<bean:write name="abc" property="annoucementDate" />
										</td>
										<td>
											<bean:write name="abc" property="announcementTime" />
										</td>
									</tr>
									<% }count++;}%>								
				</logic:iterate>
			</table>
		</logic:notEmpty>	
		<br/>		
		
		<table class="bordered">
			<tr>
				<th style="text-align: center;" colspan="4"><big>Creation / Modification</big></th>
			</tr>

			<tr>
				<td>Type <font color="red">*</font></td>
				<td colspan="3"><html:hidden property="id"/>
					<html:select property="headLinesType" styleClass="content">
					<html:option value="">--Select--</html:option>
					<html:option value="HEAD LINES">Headlines</html:option>
					<html:option value="ORGANIZATION ANNOUNCEMENTS">Announcements</html:option>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td>Date</td>
				<td><html:text property="annoucementDate" styleClass="content" readonly="true" styleId="date1" onfocus="popupCalender('date1')" ></html:text></td>
				<td> Time</td>
				<td><html:text property="announcementTime" styleClass="content" readonly="true"></html:text>
				</td>
			</tr>
			<tr>
			<td>
			Subject</td>
			<td colspan="4">
			<html:textarea property="subject" rows="5" cols="89" title="Subject Should Be less than 200 characters"></html:textarea>
			</td></tr>

			<tr>
				<td colspan="4">
					<FCK:editor instanceName="EditorDefault" width="900">
					<jsp:attribute name="value">
						<logic:present name="announcementForm" property="content">
                        	<bean:define id="data" name="announcementForm" property="content" />
							<%out.println(data.toString());%>
                    	</logic:present>
					</jsp:attribute>
					</FCK:editor>
				</td>
			</tr>
			<tr>
						<td>Videos</td>
						<td><html:file name="announcementForm" property="videoFileNames" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;
							<html:button value="Upload" onclick="onUploadVideo()" property="method" styleClass="rounded" style="width: 100px"/>
						</td>
					</tr>
			<logic:notEmpty name="videosList">
							<tr>
								<th colspan="4">Uploaded Videos</th>
							</tr>
								
							<logic:iterate name="videosList" id="listid1">
								<tr>
									<td align="center"><a href="/EMicro Files/Announcments/Videos/<bean:write name="listid1" property="videoFilesList"/>"><bean:write name="listid1" property="videoFilesList"/></a></td>
									<td><input type="checkbox" name="checkedVideofiles" value="<bean:write name="listid1" property="videoFilesList"/>" />
									</td>
								</tr>
							</logic:iterate>

							<tr>
								<td colspan="2">
									<html:button value="Delete" onclick="deleteMainLinkUploadedVideos()" property="method" styleClass="rounded" style="width: 100px"/>
								</td>
							</tr>
						</logic:notEmpty>
					</table>
								
			<logic:notEmpty name="saveButton">			
				<tr>
					<td  colspan="4" style="text-align: center;">
						<html:button property="method" value="Save" onclick="onSave()" styleClass="rounded" style="width: 100px" />
					</td>
				</tr>	
			</logic:notEmpty>

			<logic:notEmpty name="modifyButton">			
				<tr>
					<td  colspan="4" style="text-align: center;">
						<html:button property="method" value="Modify" onclick="onModify()" styleClass="rounded" style="width: 100px"/>&nbsp;&nbsp;
						<html:button property="method" value="Delete" onclick="onDelete()" styleClass="rounded" style="width: 100px"/>
					</td>
				</tr>	
			</logic:notEmpty>
		</table>
		</div>
		<html:hidden property="saveType" />
	</html:form>

</body>
</html>
