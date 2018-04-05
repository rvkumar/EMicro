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
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript">

function showSelectedFilter(){
if(document.forms[0].reqRequstType.value!="" ){

	
	var url="hrApprove.do?method=pendingRecords";
	document.forms[0].action=url;
	document.forms[0].submit();

	
	
	}
}
</script>

  </head>
  
  <body>
  <html:form action="/hrApprove.do" enctype="multipart/form-data">
   <table class="bordered" width="90%">
 
<tr>
<th><b>Request Type : <font color="red">*</font></b>
</th><td>	<html:select property="reqRequstType" styleClass="Content"  styleId="requestSelectId" onchange="showSelectedFilter()">
	<html:option value="">Select</html:option>

		<html:option value="Leave">Leave</html:option>	
		<html:option value="On Duty">On Duty</html:option>
		<html:option value="Permission">Permission</html:option>

	</html:select>
	</td></tr></table><br>
	</br>
	</html:form>
  </body>
</html>
