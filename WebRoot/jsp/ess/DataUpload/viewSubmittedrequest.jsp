<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
 <style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
function reverta(param1,param2,id)
{	
 	var id1=id;
	 id1=id.replace("b","");
	id1="searchText"+id1;
	
	if(document.getElementById(id1).value=="")
	{
	alert("Enter the Remarks");
	return false;	
	}
	var comments = document.getElementById(id1).value;
	document.forms[0].action="vc.do?method=revertCompletedrequest&pernr="+param1+"&reqno="+param2+"&comments="+comments;
	document.forms[0].submit();
}

function verify(param1,param2,id)
{	
 	var id1=id;
	 id1=id.replace("b","");
	id1="searchText"+id1;
	
	if(document.getElementById(id1).value=="")
	{
	alert("Enter the Remarks");
	return false;	
	}
	var comments = document.getElementById(id1).value;
	document.forms[0].action="vc.do?method=verifyCompletedrequest&pernr="+param1+"&reqno="+param2+"&comments="+comments;
	document.forms[0].submit();
}


function verifybulk(param1,param2)
{

	var j=0;
	 var t =document.getElementsByName("selectedRequestNo").length;
		for(var i=0; i<t; i++){
			if(document.forms[0].checkProp.checked==true)
			{
			j=1;  
			}	
		}
	if(j==0)
	{
	alert("Please Select one Checkbox to verify");
	return false;
	}		
		
	var group_type=	document.forms[0].group_type.value;
	document.forms[0].action="vc.do?method=bulkverifyCompletedrequest&pernr="+param1+"&reqno="+param2+"&comments='Bulk Approve'&group_type="+group_type;
	document.forms[0].submit();

}

function checkAll()
	{
	
	 var t =document.getElementsByName("selectedRequestNo").length;
		for(var i=0; i<t; i++){
			if(document.forms[0].checkProp.checked==true)
			
			{
			
			   document.getElementsByName("selectedRequestNo")[i].checked = true ;
			}
			else
			{
				document.getElementsByName("selectedRequestNo")[i].checked = false ;
			}	
		}
		
		
		
		
		
	}

</script>



</head>
<body >

	<html:form action="vc" enctype="multipart/form-data">
	<div>&nbsp;</div>
	 &nbsp;&nbsp;&nbsp;
	<div>&nbsp;</div>
	
	<html:hidden property="reqNo"  name="vcForm" />
	<html:hidden property="group_type" name="vcForm" />
	
				<div style="height:300px;overflow:auto">
				<logic:notEmpty name="emplist">
			
			
			<html:button property="method" value="Verify" onclick="verifybulk('${mytable1.empId}','${vcForm.reqNo}')" styleClass="rounded"/>&nbsp;
			<html:button property="method" value="Close" onclick="window.close()" styleClass="rounded"/>
			<br/>
			<br/>
				
			<div align="left" class="bordered">
			<table class="sortable" >
			<thead>
			
				<tr>
				<th colspan="11"><center>User Details</center></th>
				</tr>
				
				
			<tr>
			<th style="text-align:left;"><b>
			#<input type="checkbox" name="checkProp" id="r4" onclick="checkAll()"/>
		 </b></th>
			<th style="text-align:left;"><b>Division</b></th>
			<th style="text-align:left;"><b>Emp.No</b></th>
			<th style="text-align:left;"><b>Full Name</b></th>
			<th style="text-align:left;"><b>Role</b></th>
			<th style="text-align:left;"><b>HQ</b></th>
			<th style="text-align:left;"><b>State</b></th>
			<th style="text-align:left;"><b>Submitted Date</b></th>
			<th style="text-align:left;"><b>File Name</b></th>
			<th style="text-align:left;"><b>Remarks</b></th>
			<th style="text-align:left;"><b>Action</b></th>
										
					</tr>
					</thead>
					<%int i=0; %>
				<logic:iterate id="mytable1" name="emplist">
				<tr>
				<%i++; %>
					<td>
					<%=i%>
					<html:checkbox property="selectedRequestNo" name="vcForm" value="${mytable1.empId}" styleId="${mytable1.empId}" />
					
					</td>				
					
					<td>
					
				<bean:write name="mytable1" property="divisionid"/>
					</td>
										<td>
				<bean:write name="mytable1" property="empId"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="empName"/>
				</td>
				<td>
				<bean:write name="mytable1" property="desg"/>
				</td>
				<td>
				<bean:write name="mytable1" property="headquater"/>
				</td>
				<td>
				<bean:write name="mytable1" property="state"/>
				</td>
				
				<td>
				<bean:write name="mytable1" property="submitDate"/>
				</td>
				
				
				<td>
				<a href="${mytable1.path}"  target="_blank">${mytable1.filename}</a>						
				</td>
				
				
				<td>
				<html:textarea property="searchText" name="vcForm"  styleId='<%="searchText"+i%>'>
				</html:textarea>
				</td>
				
				<td>
				<html:button property="method" value="Verify" onclick="verify('${mytable1.empId}','${vcForm.reqNo}',this.id)" styleClass="rounded"  styleId='<%="b"+i%>'/>
				<br/>
				<html:button property="method" value="Revert" onclick="reverta('${mytable1.empId}','${vcForm.reqNo}',this.id)" styleClass="rounded"  styleId='<%="b"+i%>'/>
				</td>
				
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		</div>
	
	</html:form>
	
</body>
	
</html>	