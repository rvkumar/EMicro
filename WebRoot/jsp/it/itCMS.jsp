<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
  
  
  <html:form action="it.do">
					
					<bean:write name="itForm" property="contentDescription" filter="false" />
					
									<!--<b>Documents:</b>
									<bean:define id="file" name="itForm"
										property="fileFullPath" />
								
									
						<div style="float: right;">
									
									<b>Videos</b>
									<bean:define id="video" name="itForm" property="videoFullPath" />
									
									
									
							<video width="320" height="200" controls>
	 MP4 must be first for iPad! 
	<source src="" type="video/mp4" /> Safari / iOS video    
	<source src="" type="video/ogg" /> Firefox / Opera / Chrome10 
	 fallback to Flash: 
	<object width="100" height="100" type="application/x-shockwave-flash" data="__FLASH__.SWF">
		 Firefox uses the `data` attribute above, IE/Safari uses the param below 
		<param name="movie" value="__FLASH__.SWF" />
		<param name="flashvars" value="controlbar=over&amp;image=__POSTER__.JPG&amp;file=__VIDEO__.MP4" />
		 fallback image. note the title field below, put the title of the video there 
		<img src="__VIDEO__.JPG" width="640" height="360" alt="__TITLE__"
		     title="No video playback capabilities, please download the video below" />
	</object>
</video>
							<br/><br/><br/><br/>	
								
							
							</div>
							
							
							--><logic:notEmpty name="subLinkDetails">
							<br/>
							<br/>
							
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
										<a href="/EMicro/demo/index1.jsp?id=<bean:write name="abc" property="linkId" />" ><bean:write name="abc" property="linkTitle" /></a>
										</td>
									</tr>

							</logic:iterate>
								
								</table>
							
							</logic:notEmpty>
							
							
							
							
							
							
							
							</html:form>