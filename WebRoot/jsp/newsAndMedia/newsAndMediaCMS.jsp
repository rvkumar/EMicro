<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
  <script type="text/javascript" src="js/sorttable.js"></script>
 
  <script type="text/javascript">
  function nextRecord()
{
     
	document.forms[0].action="newsAndMedia.do?method=next";
	document.forms[0].submit();

}

  function previousRecord()
{
     
	document.forms[0].action="newsAndMedia.do?method=previous";
	document.forms[0].submit();

}

</script>  
  <html:form action="newsAndMedia.do">
					
		<logic:notEmpty property="message" name="newsAndMediaForm">
		<font color="red"><bean:write name="newsAndMediaForm" property="message" /></font>
		
		</logic:notEmpty>			
					<bean:write name="newsAndMediaForm" property="contentDescription" filter="false" />
					<logic:notEmpty name="newsAndMediaForm" property="fileFullPath">
					<table class="bordered1">
						<tr><td>
									<bean:define id="file" name="newsAndMediaForm" property="fileFullPath" />
									
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
									</td></tr>
									<tr><td>
									<%
									}
									%>
						</td></tr>
					</table>
									</logic:notEmpty>
						<logic:notEmpty name="newsAndMediaForm" property="videoFullPath">			
						<div style="float: right;">
						<table class="bordered">
							<tr><td>
									<bean:define id="video" name="newsAndMediaForm" property="videoFullPath" />
									
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
									
							<video width="320" height="240" controls="controls">
								<source src="<%=s1 %>" type="video/mp4">
								 Your browser does not support the video tag
								</source>
							</video>
							</td></tr>
							<tr><td>	
									<%
									}
									%>
							</td></tr></table>
							</div>
							
							
							
							<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	
	
	<center>
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="newsAndMediaForm"/>-
	
	<bean:write property="endRecord"  name="newsAndMediaForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		</center>
	</td>

	</table>
	</logic:notEmpty>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
							
							
							</logic:notEmpty>
							<logic:notEmpty name="subLinkDetails">
							<br/>
							
						<center>	
							<table class="bordered" style="width:75%;" >
							<tr>
								<th colspan="3">
									    <logic:equal name="newsAndMediaForm" property="linkName" value="Awards and Achievements">
										Awards & Achievements
										</logic:equal>
										<logic:notEqual name="newsAndMediaForm" property="linkName" value="Awards and Achievements">
										<center>Galleries</center>
										</logic:notEqual>
								</th>
							</tr>
							</table>
							<table class="sortable bordered" style="width:75%;">
							<tr>
							<th>&nbsp;</th>
								<th><center>Year</center></th>
							<th><center>Album Name</center></th>
						
							
							<logic:iterate name="subLinkDetails" id="abc">
							
									<tr>
										<td style="width:18%;">
										
											<img src="<bean:write name="abc" property="imageName" />" alt="" width="80" height="50"/>
										</td>
										
										<td style="width:10%;">
										<bean:write name="abc" property="contentYear" />
										</td>
										<td>
										<a href="/EMicro/demo/index1.jsp?id=<bean:write name="abc" property="linkId" />" style=" color: black; " ><i><bean:write name="abc" property="linkTitle" /></i></a>
										</td>
												</center>
									</tr>
                          
							</logic:iterate>
						
								</table>
							
							</logic:notEmpty>
							
						
 		
 				<html:hidden name="newsAndMediaForm" property="linkName"/>
 				<html:hidden name="newsAndMediaForm" property="module"/>
 	
 				<input style="visibility:hidden;" id="scnt" value="<bean:write property="startRecord"  name="newsAndMediaForm"/>"/>
				<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endRecord"  name="newsAndMediaForm"/>"/>
			
				
          		
							</html:form>