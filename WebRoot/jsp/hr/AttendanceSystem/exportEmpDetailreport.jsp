

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

<body>

				
	<html:form action="hrApprove" enctype="multipart/form-data">
	
			<div align="center">
				<logic:notEmpty name="hrApprovalForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="hrApprovalForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
			</div>

		 <div style="width: 90%" >
		 
		
<div>



</div>
</center>
</br>
</br>
 <logic:notEmpty name="emplist">
				  <% int i=0;%>
				    <table class="bordered" border="1">
                 
                     <tr>   <th colspan="30"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Employee Detail report</center></th></tr>
                  <tr>
                  
                    
                    <th>#</th>
             
<th>Plant</th>
<th>Plant Name</th>
<th>Pay Group</th>
<th>Emp No</th>
<th>Title</th>
<th>Emp Name</th>
<th>Marital Status</th>
<th>DOB</th>
<th>Tel No</th>
<th>Mob No</th>
<th>Blood Group</th>
<th>Official Email</th>
<th>PAN</th>
<th>No.Children</th>
<th>Emp Category</th>
<th>Joining Date</th>
<th>Confirm Date</th>
<th>State Name</th>
<th>Emp Grade</th>
<th>Designation</th>
<th>Department</th>
<th>ESI Number</th>
<th>PF Number</th>
<th>Payment Method</th>
<th>Bank Acc No</th>
<th>Bank Name</th>
<th>IFSC Code</th>
<th>MICR Code</th>
<th>Qualification</th>
<th>Creation Date</th>

                  </tr>
                  
                
                   
                  <logic:iterate id="emplist" name="emplist">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>                  
                <td>${emplist.location}</td>
<td>${emplist.location_name}</td>
<td>${emplist.paygrp}</td>
<td>${emplist.empId}</td>
<td>${emplist.title}</td>
<td>${emplist.empname}</td>
<td>${emplist.maritalStatus}</td>
<td>${emplist.dateofBirth}</td>
<td>${emplist.telephoneNumber}</td>
<td>${emplist.mobileNumber}</td>
<td>${emplist.bloodGroup}</td>
<td>${emplist.emailAddress}</td>
<td>${emplist.permanentAccountNumber}</td>
<td>${emplist.noOfChildrens}</td>
<td>${emplist.category}</td>
<td>${emplist.doc}</td>
<td>${emplist.doj}</td>
<td>${emplist.state}</td>
<td>${emplist.grade}</td>
<td>${emplist.desg}</td>
<td>${emplist.dept}</td>
<td>${emplist.esino}</td>
<td>${emplist.pfno}</td>
<td>${emplist.paymentmethod}</td>
<td>${emplist.bankacno}</td>
<td>${emplist.bankname}</td>
<td>${emplist.ifsc}</td>
<td>${emplist.micr}</td>
<td>${emplist.qualification}</td>
<td>${emplist.created_date}</td>
                
                 
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
				   
				    
				  

</div>
</html:form>
</body>
</html>
