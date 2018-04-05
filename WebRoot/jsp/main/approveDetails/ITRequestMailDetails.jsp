<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      </head>
<body>
<html:form action="/approvals.do" enctype="multipart/form-data">
<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>



<table class="sortable bordered" >
<tr >
<th>Req&nbsp;No</th><th >Type</th>
	<th >Req Date</th> <th >Status</th>
</tr>

<tr>

<td><bean:write name="approvalsForm" property="requestNo"/></td>
<td><bean:write name="approvalsForm" property="reqRequstType"/></td>

<td><bean:write name="approvalsForm" property="requestDate"/></td>
<td><bean:write name="approvalsForm" property="requestStatus"/></td>

</tr>

</table>





</html:form>
</body>
</html>