<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<html>

	<head>
		<title>ESS</title>
		

		
		
		
<script language="javascript">
 

</script>
	</head>
	<body>


		<table width=100%>
			<tr>
				<td colspan="2" style="width: 20%; vertical-align: top;"><jsp:include
						page="/jsp/template/header.jsp" /></td>
			</tr>
			<tr>
				<td style="width: 20%; vertical-align: top;">
					<jsp:include page="/jsp/template/mainMenu.jsp">
						<jsp:param value="Master" name="module" />
						<jsp:param value="AdmissionType" name="subModule" />
					</jsp:include>
				</td>
				<td>
				

					
					
					<html:form action="/empofficial.do" enctype="multipart/form-data">
							
							</html:form>
										
								</td>
								</tr>
								</table>
				
	</body>
</html>