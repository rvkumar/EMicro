<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
  
  
    <html:form action="newsAndMedia.do">
					
		
		<bean:write name="newsAndMediaForm" property="contentDescription" filter="false" />
					<logic:notEmpty name="newsAndMediaForm" property="fileFullPath">
									
									<bean:define id="file" name="newsAndMediaForm"
										property="fileFullPath" />
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

									<br />
									<%
									}
									%>
									</logic:notEmpty>
						<logic:notEmpty name="newsAndMediaForm" property="videoFullPath">				
						<div style="float: right;">
									
									
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
					
					    
					<logic:notEmpty name="linksDetails">
							
							
							<table border="0" align="center">
							<tr>
								<th>
								Link Name
								</th>
							</tr>
							
							<logic:iterate name="linksDetails" id="abc">
								
									<tr>
										<td>
										<a href="newsAndMedia.do?method=displayArchiveLinks&id=<bean:write name="abc" property="linkName" />" ><bean:write name="abc" property="linkName" /></a>
										</td>
									</tr>

								</logic:iterate>
								
								</table>
							
							</logic:notEmpty>
							
						
							<logic:notEmpty name="subLinksDetails">
							
							<table border="0" align="center">
							<tr>
								<th>
								Link Name
								</th>
							</tr>
							
							<logic:iterate name="subLinksDetails" id="abc">
								
									<tr>
										<td>
										<a href="newsAndMedia.do?method=displayArchiveYears&id=<bean:write name="abc" property="linkName" />" ><bean:write name="abc" property="linkName" /></a>
										</td>
									</tr>

								</logic:iterate>
								
								<tr>&nbsp;</tr>
								
								</table>
							
							<br/>
							<br/>
							<div align="center" style="padding-top: 250px;">
										<a href="newsAndMedia.do?method=display1&sId=Archives&id=News and Media">
										Back</a>
							</div>
							</logic:notEmpty>
							
							
							<logic:notEmpty name="subLinksYearDetails">
							
							<table border="0" align="center">
							<tr>
								<th>
								Content Year
								</th>
							</tr>
							
							<logic:iterate name="subLinksYearDetails" id="abc">
								
									<tr>
										<td>
										<a href="/EMicro/demo/index1.jsp?id=<bean:write name="abc" property="linkId" />" target="_blank">
										<bean:write name="abc" property="contentYear" /></a>
										</td>
									</tr>

								</logic:iterate>
								
								
								</table>
							
							<br/>
							<br/>
							 <div align="center" style="padding-top: 250px;">
									<a href="newsAndMedia.do?method=displayArchiveLinks&id=<bean:write name="newsAndMediaForm" property="linkName" />">
									Back</a>
							 </div>
									
									
							</logic:notEmpty>
							
						</html:form>