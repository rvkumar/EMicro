  <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
  
  
  
  
  
  
  
  
  <div class="middel-blocks">
     	<html:form action="newsAndMedia.do">
					
					
					<html:hidden property="divStatus" name="newsAndMediaForm"/>
					
					
					<bean:write name="newsAndMediaForm" property="contentDescription" filter="false" />
					
									<b>Documents</b>
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
									
									<br/>
									<%
									}
									%>

						<div style="float: right;">
									
									<b>Videos</b>
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
								
								<%out.println(s1); %>
								
								 	Your browser does not support the video tag
								</source>
							</video>
							<br/><br/><br/><br/>	
									<%
									}
									%>
							
							
							</div>
							
							
							<logic:notEmpty name="subLinkDetails">
							
							
							<table border="0" align="left">
							<tr>
								<th>
								
									<logic:equal name="newsAndMediaForm" property="linkName" value="Awards and Achievements">
										Awards & Achievements
										</logic:equal>
										<logic:notEqual name="newsAndMediaForm" property="linkName" value="Awards and Achievements">
										Galleries
										</logic:notEqual>
									
								</th>
							</tr>
							
							<logic:iterate name="subLinkDetails" id="abc">
								
									<tr>

										<td>
											<img src="<bean:write name="abc" property="imageName" />" alt="" width="80" height="50"/>
										</td>
										<td>
										<a href="/EMicro/demo/index1.jsp?id=<bean:write name="abc" property="linkId" />" target="_blank"><bean:write name="abc" property="linkTitle" /></a>
										</td>
									</tr>

								</logic:iterate>
								
								</table>
							
							</logic:notEmpty>
							
							</html:form>
</div>