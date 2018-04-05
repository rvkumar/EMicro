<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>eMicro :: Main_Content</title>

	<link href="/EMicro/style/content.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

	<script type="text/javascript" >
		function goBack(){
			var url="main.do?method=displayAnnouncement";
			document.forms[0].action=url;
			document.forms[0].submit();
		}
	</script>
</head>

<body>

	<html:form action="main">
	<table class="bordered">
		<logic:notEmpty name="ContentData">
			<logic:equal name="mainForm" property="headLines" value="HEADLINES">
				<tr>
					<th><big><bean:write name="mainForm" property="headLines" /><small style="float: right;"><bean:write name="mainForm" property="annoucementDate" />
					<bean:write name="mainForm" property="announcementTime" /></small></big></th>
          		 </tr>
			</logic:equal>
               
			<logic:equal name="mainForm" property="headLines" value="ORGANIZATION ANNOUNCEMENTS">
				<tr >
					<th><big><bean:write name="mainForm" property="headLines" /><small style="float: right;"><bean:write name="mainForm" property="annoucementDate" />
					<bean:write name="mainForm" property="announcementTime" /></small></big></th>
					
			</logic:equal>
				</tr>
			
		
			<tr>
				<td align="left" valign="top"><i><font color="gray" size="2px">
					<%
						String val=(String)request.getAttribute("ContentData");
						out.println(val);
	  				%>
		</font></i></td>
		
		
			<logic:notEmpty name="managementVideo">
	
			
		
			<tr><td style="text-align: right;">
			 <center>
		<video controls >
  <source src="/EMicro Files/Announcments/Videos/${mainForm.managementVideo }" type="video/ogg">
  <source src="/EMicro Files/Announcments/Videos/${mainForm.managementVideo }" type="video/mp4">
  Your browser does not support the <code>video</code> element.
</video>
		</center>
		</td></tr>
	</logic:notEmpty>	 

		

			<tr>
				<td><html:button property="method" value="Back" onclick="history.back(-1)" styleClass="rounded" style="width: 100px"></html:button></td>
        	</tr>	
        </logic:notEmpty>
    </table>

</html:form>
</body>
</html>
