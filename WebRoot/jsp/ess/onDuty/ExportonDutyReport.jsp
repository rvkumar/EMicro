
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 
</head>

<body >



		<html:form action="onDuty" enctype="multipart/form-data">
		

	<logic:notEmpty name="ondutyList">
			<div align="left" class="bordered">
			<table class="sortable" border="1">
			
				<tr>
				<th colspan="11"><center> OnDuty Requests</center></th>
				</tr>
				<tr><th style="text-align:left;"><b>Sl No.</b></th>
							<th style="text-align:left;"><b>Req No.</b></th>
								<th style="text-align:left;"><b>Requester</b></th>
							<th style="text-align:left;"><b>Type</b></th>
										<th style="text-align:left;"><b>Req Date</b></th>	
							<th style="text-align:left;"><b>Plant</b></th>	
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
								<th class="specalt" align="center"><b>Reason</b></th>
							
						</tr>
						<%int h=0; %>
				<logic:iterate id="mytable1" name="ondutyList">
				<%h++; %>
									<tr >
									<td><%=h %></td>
										<td>
				<bean:write name="mytable1" property="requestNumber"/>
				
				</td>
			
										<td>
				<bean:write name="mytable1" property="employeeName"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="onDutyType"/>
				</td>
				<td>
				<bean:write name="mytable1" property="submitDate"/>
				</td>
				<td>
				<bean:write name="mytable1" property="locationId"/>
				</td>
				
				<td>
				<bean:write name="mytable1" property="startDate"/>
				</td>
					<td>
				<bean:write name="mytable1" property="startTime"/>
				</td>
				<td>
				<bean:write name="mytable1" property="endDate"/>
				</td>
					<td>
				<bean:write name="mytable1" property="endTime"/>
				</td>
				<td>
				<bean:write name="mytable1" property="reason"/>
				</td>
				
				
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		
		 <logic:notEmpty name="noRecords">
 	
 <table class="sortable bordered">
			<tr>
				<th colspan="10"><center>My OnDuty Requests</center></th>
				</tr>
			<tr>
			<th style="text-align:left;"><b>Req No.</b></th>
							<th style="text-align:left;"><b>Type.</b></th>	
							<th style="text-align:left;"><b>Req Date</b></th>	
							<th style="text-align:left;"><b>Plant.</b></th>	
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
						
					</tr>
					<tr><td colspan="10"><center>No Records to display </center></td></tr>
						</table>
					
		</logic:notEmpty>
		</html:form>
		</body>
		</html>