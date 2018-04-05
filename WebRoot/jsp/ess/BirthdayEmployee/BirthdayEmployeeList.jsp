<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/sorttable.js"></script>
 <script type="text/javascript">
 
 
 </script>
 <body>
 <html:form action="birthdayEmp">
 <center>
 <table style="width: 30%;" ><tr><td><img src="images/birthday_balloons.gif" align="absmiddle" style="height: 50px;"/></td>
 <td><table class="bordered"><tr><th><center><b><big>Happy Birthday</big></b></center></th></tr></table></td><td>&nbsp;<img src="images/birthday_balloons.gif" align="absmiddle" style="height: 50px;"/></td></tr></table>
</center>

 <table class="bordered sortable">
 
<tr>
  <th>S.No</th><th></th><th>Location</th><th>Employee Name</th><th>Department</th><th>EmailID</th><th>Ext.No</th><th>IP Phone</th>
 </tr>
 <logic:notEmpty name="empBirthDayList">
 <% int i=1; %>
 <logic:iterate id="empList" name="empBirthDayList">
 <tr>
  <td><%=i++ %></td><td> <img src="/EMicro Files/images/EmpPhotos/<bean:write name="empList" property="empPhoto"/>" alt=""  width="40px" height="40px" /></td>
  <td><bean:write name="empList" property="location"/></td>
  <td><bean:write name="empList" property="emplyeeName"/></td>
  <td><bean:write name="empList" property="dept"/></td>
  <td><bean:write name="empList" property="emailID"/></td>
  <td><bean:write name="empList" property="extention"/></td>
 <td><bean:write name="empList" property="ipphone"/></td>
 </tr>
 </logic:iterate>
 
 </logic:notEmpty>
 
 <logic:notEmpty name="noRecords">
 <tr>
 <td colspan="8"><font color="green"><b><center>No Employee Birthday List</center></b></font></td>
 </tr>
 </logic:notEmpty>
 </table>
 </html:form>
 </body>
 