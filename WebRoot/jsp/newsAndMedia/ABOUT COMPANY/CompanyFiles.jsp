<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
</head>
<body>
<form action="newsAndMedia">


<logic:notEmpty name="CorporatePPT">
<table class="bordered">
<tr>
<th>Documents</th>
</tr>
<logic:notEmpty name="newsAndMediaForm" property="fileFullPath" > 
								
									
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
									<tr>
									<td>
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>
									</td>
									</tr>
									<%
									}
									%>
									
										
									
									</logic:notEmpty>
									</table>
</logic:notEmpty>
<logic:notEmpty name="magzinePdf">
<table class="bordered">
<tr>
<th>Documents</th>
</tr>
	<logic:notEmpty name="newsAndMediaForm" property="fileFullPath" > 
								
									
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
									<tr>
									<td>
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>
										</td>
									</tr>
									<%
									}
									%>
									
									
									
									</logic:notEmpty>
									</table>
</logic:notEmpty>

</form>
</body>
</html>