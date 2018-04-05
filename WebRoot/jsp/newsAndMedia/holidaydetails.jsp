<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<html>

	<head>
		<title>Holiday Details</title>
		<link href="/css/CalendarControl.css" rel="stylesheet" type="text/css">
		<script src="/js/CalendarControl.js" language="javascript"></script>
		<script language="javascript">
function onSave(){
	var url="holidays.do?method=saveHolidays";
	document.forms[0].action=url;
	document.forms[0].submit();
}



</script>
	</head>
	<body>


		<table width=100%>
			<tr>
				<td colspan="2" style="width: 20%; vertical-align: top;"><jsp:include
						page="../template/header.jsp" /></td>
			</tr>
			<tr>
				<td style="width: 20%; vertical-align: top;">
					<jsp:include page="../template/mainMenu.jsp">
						<jsp:param value="Master" name="module" />
						<jsp:param value="AdmissionType" name="subModule" />
					</jsp:include>
				</td>
				<td>
				<%
				HttpSession session2=request.getSession();
				String result=" ";
				
			result=(String)session2.getAttribute("result");

				%>
				<center><font color="red"><%=result %></font></center>
				 
			<%--<div align="center">
								<logic:present name="holidayForm" property="message">
									<font color="red"> <bean:write name="holidayForm"
											property="message" /> </font>
								</logic:present>
							</div>

					--%>
					<html:form action="/holidays.do" enctype="multipart/form-data">
						<center>
							
							<table border="1">

								<tr>
									<td>
										Holiday Name
									</td>
									<td>
										<html:text property="holidayName"></html:text>
									</td>
								</tr>
								<tr>
									<td>
										Holiday Date
									</td>
									<td>
										<html:text property="holidayDate" onfocus="showCalendarControl(this);"></html:text>
									</td>
								</tr>

								<tr>
									<td>
										Holiday Type
									</td>
									<td>
										<html:select property="holidayType">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Regional Holidays"></html:option>
											<html:option value="Government Holidays"></html:option>
											<html:option value="PlantWise Holidays"></html:option>

										</html:select>
									</td>
								</tr>

								<tr>
									<td>
										Country
									</td>
									<td>
										<html:select property="country">
											<html:option value="">--SELECT--</html:option>
											<html:option value="India"></html:option>
											<html:option value="US"></html:option>
											<html:option value="UK"></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td>
										State
									</td>
									<td>
										<html:select property="state">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Karnatka"></html:option>
											<html:option value="Maharashtra"></html:option>
											<html:option value="Tamilnadu"></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td>
										city
									</td>
									<td>
										<html:select property="city">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Bangalore"></html:option>
											<html:option value="Mumbai"></html:option>
											<html:option value="Chennai"></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td>
										Planet
									</td>
									<td>
										<html:select property="planet">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Basaveswar Nagar"></html:option>
											<html:option value="Jaya Nagar"></html:option>
											<html:option value="BTM Layout"></html:option>
										</html:select>
									</td>
								</tr>
									<logic:empty name="modifyButton">
								<tr>
									<td colspan="2" align="center">
										<html:button property="method" value="Save" onclick="onSave()" />

									</td>
								</tr>
								</logic:empty>
								<logic:notEmpty name="modifyButton">
								<tr>
									<td colspan="2" align="center">
										<html:button property="method" value="Modify" onclick="onModify()" />
									
										<html:button property="method" value="Delete" onclick="onDelete()" />
									</td>
								</tr>

							</logic:notEmpty>
						

										</teble>
										<logic:notEmpty name="listDetails">

									<table class=forumline align=center width='60%'>

										<tr>
											<th Class="head" align=center colspan=5>
												Holiday Details
											</th>
										</tr>

										<tr height='20'>
											<th class="specalt" align=center>
												<b>Sl. No</b>
											</th>
											<th class="specalt" align=center>
												<b>Holiday Name</b>
											</th>
											<th class="specalt" align=center>
												<b>Holiday Date</b>
											</th>
											<th class="specalt" align=center>
												<b>Holiday Type</b>
											</th>
											<th class="specalt" align=center>
												<b>Country</b>
											</th>
											<th class="specalt" align=center>
												<b>State</b>
											</th>
											<th class="specalt" align=center>
												<b>City</b>
											</th>
												<th class="specalt" align=center>
												<b>Planet</b>
											</th>
										</tr>
									
										<%
										int count = 1;
										
										%>
										<logic:iterate name="listDetails" id="abc">

									<tr>
										<td>
											<a
												href="links.do?method=edit&sId=<bean:write name="abc" property="holidayDate"/>"><%=count++ %>
										</td>
									
										<td>
											<bean:write name="abc" property="holidayName" />
										</td>
										<td>
											<bean:write name="abc" property="holidayDate" />
										</td>
										<td>
											<bean:write name="abc" property="holidayType" />
										</td>
										<td>
											<bean:write name="abc" property="country" />
										</td>
										<td>
											<bean:write name="abc" property="state" />
										</td>
										<td>
											<bean:write name="abc" property="city" />
										</td>
										<td>
											<bean:write name="abc" property="planet" />
										</td>
									</tr>


								</logic:iterate>

										
										</table>
											</logic:notEmpty>
											
										</center>
										</html:form>
										</td>
										</tr>
									</table>
	</body>
</html>

