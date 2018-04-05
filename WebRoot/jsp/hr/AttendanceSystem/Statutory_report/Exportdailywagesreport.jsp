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
	
	<div><center><big><b>FORM 15<bean:write name="hrApprovalForm" property="locationId"/><br/>REGISTER OF LEAVE WITH WAGES ( rule 87 of T N Factories Rules,1950)</center>
	
	
	</div>
	<div>Name of Factory: MICRO LABS LTD </div>
	<div>Emp No:</div>
	<br/>
		
		<table border="1">
		<tr><td><b>Factory Reg No.:</td><td><b>&nbsp;</td><td><b>Sl no. in the Register of Adult worker:</td><td><b>&nbsp;</td><td><b>Name</td><td><b>&nbsp;</td><td><b>Date of discharge:</td><td><b>&nbsp;</td></tr>
		<tr><td><b>Sl No.:</td><td><b>&nbsp;</td><td><b>Date of entry into service:</td><td><b>&nbsp;</td><td><b>Father / Husband name:</td><td><b>&nbsp;</td><td><b>Date & amount of payment made in lieu of leave due:</td><td><b>&nbsp;</td></tr>
		<tr><td><b>Department:</td><td><b>&nbsp;</td></tr>
		</table>
		
		<br/>
		
		<table border="1">
		<tr><td><b>Calender Year	</b></td><td><b>Wage period </b></td>	<td><b>Wage earned during the period	</b></td><td><b>No. of days work performed</b></td>	<td><b>No. of days lay off</b></td>	<td><b>No. of days maternity leave	</b></td><td><b>No.of days leave enjoyed	</b></td><td><b>Total no.of columns ( 4 to 7 )</b></td>	<td><b>Balance of leave from preceding year	</b></td><td><b>Leave earned during the current year mentioned in col.(1)	</b></td><td><b>Total of 9& 10	</b></td><td><b>Whether leave in accordance with scheme	</b></td><td><b>Leave enjoyed days		</b></td><td><b>Balance of leave to credit	</b></td><td><b>Normal rate of wages	</b></td><td><b>Cash equivalent of food grains	</b></td><td><b>Rate of wages for leave period 	</b></td>		</tr>
		<tr><td><b><center>1</center></b></td><td><b><center>2</center></b></td><td><b><center>3</center></b></td><td><b><center>4</center></b></td><td><b><center>5</center></b></td><td><b><center>6</center></b></td><td><b><center>7</center></b></td><td><b><center>8</center></b></td><td><b><center>9</center></b></td><td><b><center>10</center></b></td><td><b><center>11</center></b></td><td><b><center>12</center></b></td><td><b><center>13</center></b></td><td><b><center>14</center></b></td><td><b><center>15</center></b></td><td><b><center>16</center></b></td><td><b><center>17</center></b></td></tr>
		
		</table>
		
		
		


</html:form>
</body>
</html>
