<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

 

 </head>

	<body>

		<html:form action="/hrApprove.do" enctype="multipart/form-data">

			
			<br/>
		<logic:notEmpty name="loginlist">
		<input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Login')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>LoginStatus</th></tr>
		  <logic:iterate id="a" name="loginlist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          <logic:notEmpty name="leavlist">
          <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Leave')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>LeaveStatus</th></tr>
		  <logic:iterate id="a" name="leavlist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          <logic:notEmpty name="alllist">
            
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('ALL')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>PersonalInfo Status</th><th>Address Status</th><th>Family Status</th><th>Education Status</th><th>Experience Status</th><th>Language Status</th></tr>
		  <logic:iterate id="a" name="alllist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.perstatus}</td><td>${a.addstatus}</td><td>${a.famstatus}</td><td>${a.edustatus}</td><td>${a.expstatus}</td><td>${a.lanstatus}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
            <logic:notEmpty name="perlist">
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Personal Information')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>PersonalInfo Status</th></tr>
		  <logic:iterate id="a" name="perlist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          
           <logic:notEmpty name="addrlist">
              <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Address')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Address Status</th></tr>
		  <logic:iterate id="a" name="addrlist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          <logic:notEmpty name="familist">
             <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Family')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Family Status</th></tr>
		  <logic:iterate id="a" name="familist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
            <logic:notEmpty name="edulist">
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Education')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Education Status</th></tr>
		  <logic:iterate id="a" name="edulist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
            <logic:notEmpty name="explist">
               <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Experience')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Experience Status</th></tr>
		  <logic:iterate id="a" name="explist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
          
          <logic:notEmpty name="langlist">
             <input type="button" class="rounded" value="Export To Excel"
							onclick="exportto('Language')" s style="width: 124px; "/>
		  <table class="bordered" border="1">
		  <tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Designation</th><th>Language Status</th></tr>
		  <logic:iterate id="a" name="langlist">
		  <tr><td>${a.employeeNo}</td><td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td><td>${a.status}</td></tr>
		  </logic:iterate>
		  </table>
          </logic:notEmpty>
		</html:form>
	
	</body>
</html>
