
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<link rel="stylesheet" type="text/css" href="css/styles.css" />

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
<title>Archive</title>


<!--<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>-->

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>





<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>


<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
</style>
</head>

<body >
   <%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			 <% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  
  			%>
  
     
    
    
    <div class="middel-blocks">
     			<div align="center">
					<logic:present name="statusMessage">
						<font color="red">
							<bean:write name="awardsForm" property="message" />
						</font>
					</logic:present>
<html:form action="/archieves.do" enctype="multipart/form-data">
<br>
		
<logic:notEmpty name="listOfSubLinks">
					Archives - <bean:write name="archivesForm" property="linkName"/><br>
				<table border="2">
				<logic:iterate id="subLinkId" name="listOfSubLinks">
				
				<tr>
				<td bgcolor="skyblue">
				<b><a href="archieves.do?method=linksContent&requiredYear=<bean:write name="subLinkId" property="year"/>&LinkName=<bean:write name="archivesForm" property="linkName"/>"><bean:write name="subLinkId" property="year"/></a>
				</td>
				</tr>
				</logic:iterate>
				
				</table>
	
</logic:notEmpty>
					
<logic:notEmpty name="linksContent">
					
					Archives - <bean:write name="archivesForm" property="linkName"/><br>
					
			
				<%
		
		String val1=(String)request.getAttribute("contentDescription");
			
			out.println(val1);
		%>
				</br>
				
			<b>Documents  :</b>
			<br>
									<bean:define id="file" name="archivesForm"
										property="filePath" />
								<%
										String s = file.toString();
										s = s.substring(0, s.length());
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) {
									
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
									%>
									
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>
                                  <br>
									
									<%
									}
									%>		
			
					<br>
				<div style="float: right;">
									
									<b>Videos</b>
									<br>
						<!--<a href="<bean:write name="archivesForm" property="videoPath"/>"><bean:write name="archivesForm" property="videoPath"/></a>			
									--><bean:define id="video" name="archivesForm" property="videoPath" />
									
									<%
										String s1 = video.toString();
										
										System.out.println("Getting A Video Name is **********************"+s1);
										
										s1 = s1.substring(0, s1.length());
										String v1[] = s1.split(",");
										int l1 = v1.length;
										for (int j = 0; j < l1; j++) {
										int x=v1[j].lastIndexOf("/");
											String u=v1[j].substring(x+1);
									%>
									
							<video width="320" height="200" controls="controls">
								<source src="<%=s1 %>" type="video/mp4">
								 
								 Your browser does not support the video tag
								</source>
							</video>
							<br/><br/><br/><br/>	
									<%
									}
									%>
							
							</div>
</logic:notEmpty>
							<br>
<logic:notEmpty name="subLinksList">
			<table border="2" align="center">
				<logic:iterate id="subLinksId" name="subLinksList">
				
				<tr>
	<td bgcolor="skyblue">
				<a href="archieves.do?method=getsubLinksYears&LinkName=<bean:write name="archivesForm" property="linkName"/>&subLink=<bean:write name="subLinksId" property="subLinkName"/>"><bean:write name="subLinksId" property="subLinkName"/></a>
				</td>
				</tr>
				</logic:iterate>
				</table>
				
</logic:notEmpty>
					
		<logic:notEmpty name="subLinksYearList"> 
			Archives - <bean:write name="archivesForm" property="linkName"/><br>
			Link - <bean:write name="archivesForm" property="subLinkName"/>
			<br>
				<table border="2">
				<logic:iterate id="subLinkYear" name="subLinksYearList">
				
				<tr>
				<td bgcolor="skyblue">
				<b><a href="archieves.do?method=subLinkContent&requiredYear=<bean:write name="subLinkYear" property="year"/>&LinkName=<bean:write name="archivesForm" property="linkName"/>&SubLinkName=<bean:write name="archivesForm" property="subLinkName"/>"><bean:write name="subLinkYear" property="year"/></a>
				</td>
				</tr>
		</logic:iterate>
				
		</table>
		</logic:notEmpty>			
					
					
					
					
					
					
<logic:notEmpty name="SubLinkContent">
					
					
				<b><bean:write name="archivesForm" property="subLinkName"/> :</b><br>
				<%
		
		String val1=(String)request.getAttribute("contentDescription");
			
			out.println(val1);
		%>
				</br>
				
			<b>Documents  :</b>
			<br>
									<bean:define id="file" name="archivesForm"
										property="filePath" />
								<%
										String s = file.toString();
										s = s.substring(0, s.length());
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) {
									
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
									%>
									
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>
                                  <br>
									
									<%
									}
									%>		
			
					<br>
				<div style="float: right;">
									
									<b>Videos</b>
									<br>
									<bean:define id="video" name="archivesForm" property="videoPath" />
									
									<%
										String s1 = video.toString();
										
										System.out.println("Getting A Video Name is **********************"+s1);
										
										s1 = s1.substring(0, s1.length());
										String v1[] = s1.split(",");
										int l1 = v1.length;
										for (int j = 0; j < l1; j++) {
										int x=v1[j].lastIndexOf("/");
											String u=v1[j].substring(x+1);
									%>
									
							<video width="320" height="200" controls="controls">
								<source src="<%=s1 %>" type="video/mp4">
								 
								 Your browser does not support the video tag
								</source>
							</video>
							<br/><br/><br/><br/>	
									<%
									}
									%>
							
							
		
					
					
</logic:notEmpty>
<logic:notEmpty name="SubLinksYear">
					<br>
					<table border="2">
				<logic:iterate id="subLinkYear" name="SubLinksYear">
				
				<tr>
				<td bgcolor="skyblue">
				<b><a href="archieves.do?method=getSubLinksContent&requiredYear=<bean:write name="subLinkYear" property="year"/>&LinkName=<bean:write name="archivesForm" property="linkName"/>"><bean:write name="subLinkYear" property="year"/></a>
				</td>
				</tr>
				</logic:iterate>
				
				</table>
</logic:notEmpty>
				
				<logic:notEmpty name="subLinksData1">
					
					Archives - <bean:write name="archivesForm" property="mainLinkName"/><br>
					
			<bean:write name="archivesForm" property="linkName"/>--><bean:write name="archivesForm" property="year"/>
			
				<%
		
		String val1=(String)request.getAttribute("contentDescription");
			
			out.println(val1);
		%>
				</br>
				
			<b>Documents  :</b>
			<br>
									<bean:define id="file" name="archivesForm"
										property="filePath" />
								<%
										String s = file.toString();
										s = s.substring(0, s.length());
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) {
									
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
									%>
									
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>
                                  <br>
									
									<%
									}
									%>		
			
					<br>
				<div style="float: right;">
									
									<b>Videos</b>
									<br>
									<bean:define id="video" name="archivesForm" property="videoPath" />
									
									<%
										String s1 = video.toString();
										
										System.out.println("Getting A Video Name is **********************"+s1);
										
										s1 = s1.substring(0, s1.length());
										String v1[] = s1.split(",");
										int l1 = v1.length;
										for (int j = 0; j < l1; j++) {
										int x=v1[j].lastIndexOf("/");
											String u=v1[j].substring(x+1);
									%>
									
							<video width="320" height="200" controls="controls">
								<source src="<%=s1 %>" type="video/mp4">
								 
								 Your browser does not support the video tag
								</source>
							</video>
							<br/><br/><br/><br/>	
									<%
									}
									%>
							
							</div>
				
				<br>
				
				<logic:notEmpty name="subsubLinksList">
				
				<table border="2" align="center">
				<logic:iterate id="subsubLinksId" name="subsubLinksList">
				
				<tr>
	<td bgcolor="skyblue">
				<a href="archieves.do?method=getSubSubLinkYears&LinkName=<bean:write name="archivesForm" property="mainLinkName"/>&subLink=<bean:write name="archivesForm" property="linkName"/>&subsubLink=<bean:write name="subsubLinksId" property="subLinkName"/>"><bean:write name="subsubLinksId" property="subLinkName"/></a>
				</td>
				</tr>
				</logic:iterate>
				</table>
				
				</logic:notEmpty>
				
				
							
							
							
</logic:notEmpty>
							<br>
			
		<logic:notEmpty name="subsubLinksYearList">
		Archive-<bean:write name="archivesForm" property="mainLinkName"/>
		<br>
		<bean:write name="archivesForm" property="linkName"/>--><bean:write name="archivesForm" property="subLinkName"/>
					<br>
					<table border="2">
				<logic:iterate id="YearId" name="subsubLinksYearList">
				
				<tr>
				<td bgcolor="skyblue">
				<b><a href="archieves.do?method=getSubSubLinksData&requiredYear=<bean:write name="YearId" property="year"/>&LinkName=<bean:write name="archivesForm" property="mainLinkName"/>&SubLinkName=<bean:write name="archivesForm" property="linkName"/>&SubSubLinkName=<bean:write name="archivesForm" property="subLinkName"/>"><bean:write name="YearId" property="year"/></a>
				</td>
				</tr>
				</logic:iterate>
				
				</table>
</logic:notEmpty>		
				
<br>
		<logic:notEmpty name="subsublinkdata">
					
					Archives - <bean:write name="archivesForm" property="mainLinkName"/><br>
					
			<bean:write name="archivesForm" property="linkName"/>--><bean:write name="archivesForm" property="subLinkName"/>--><bean:write name="archivesForm" property="year"/>
			
				<%
		
		String val1=(String)request.getAttribute("contentDescription");
			
			out.println(val1);
		%>
				</br>
				
			<b>Documents  :</b>
			<br>
									<bean:define id="file" name="archivesForm"
										property="filePath" />
								<%
										String s = file.toString();
										s = s.substring(0, s.length());
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) {
									
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
									%>
									
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>
                                  <br>
									
									<%
									}
									%>		
			
					<br>
				<div style="float: right;">
									
									<b>Videos</b>
									<br>
										<br>
									<bean:define id="video" name="archivesForm" property="videoPath" />
									
									<%
										String s1 = video.toString();
										
										System.out.println("Getting A Video Name is **********************"+s1);
										
										s1 = s1.substring(0, s1.length());
										String v1[] = s1.split(",");
										int l1 = v1.length;
										for (int j = 0; j < l1; j++) {
										int x=v1[j].lastIndexOf("/");
											String u=v1[j].substring(x+1);
									%>
									
							<video width="320" height="200" controls="controls">
								<source src="<%=s1 %>" type="video/mp4">
								 
								 Your browser does not support the video tag
								</source>
							</video>
							<br/><br/><br/><br/>	
									<%
									}
									%>
							
							</div>
				
				<br>
				</br></logic:notEmpty>

</html:form>
</div>
</td>
      </tr>
      </table></td></tr>
</body>
</html>
