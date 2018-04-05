<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
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

<script type="text/javascript">
	function popupCalender(param)
	{
		var toD = new Date();
		if(param == "endDate"){
			var sD = document.forms[0].startDate.value;
			var sDate = sD.split("/");
			toD = new Date(parseInt(sDate[2]),parseInt(sDate[1]),parseInt(sDate[0]));
		}
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
		});
	}



function firstRecord()
{

var url="security.do?method=pendingList1";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastRecord()
{

var url="security.do?method=lastRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}		


function nextRecord()
{

var url="security.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousRecord()
{

var url="security.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function searchRecord()
{

var url="security.do?method=pendingList1";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function editDetails(requestNo,type,requsterID)
{

var x=document.forms[0].selectedFilter.value;

//window.showModalDialog("security.do?method=editDetails&requestNo="+requestNo+"&requsterID="+requsterID+"&type="+type+"",null,"dialogWidth=500;dialogHeight=520;dialogTop=300;dialogLeft=350;scroll:no;");

window.showModalDialog("security.do?method=editDetails&requestNo="+requestNo+"&requsterID="+requsterID+"&type="+type+"&status="+x , "","dialogWidth:550px; dialogHeight:500px; dialogLeft:500px;" );
}




function timedRefresh(timeoutPeriod) {
	setTimeout("location.reload(true);",timeoutPeriod);
}

 
//  

</script>
</script>
<body >
<html:form action="security" enctype="multipart/form-data" method="post">
</br>
<div style="width:50%;">
<table class="bordered" align="center">

<tr><th ><center><big><b>Date</b></big> <font color="red" >*</font></center></th>
					
							<td align="left" >
								<html:text property="securityDate" styleId="startDate" onfocus="popupCalender('startDate')" 
										readonly="true" styleClass="rounded"/>
							
							</td>
				
				<th><b>Filter by</b> <font color="red">*</font></th>
						<td>
						<html:select property="selectedFilter" styleClass="content" styleId="filterId" >
						
						    <html:option value="Approved">Approved</html:option>
							<html:option value="In Process">Pending</html:option>
							
						
							
							</html:select>
					&nbsp;	&nbsp;
					
<a href="#"><img src="images/search.png" title="Search..." onclick="searchRecord()" align="absmiddle"/></a></td>
					
						</td>
							
	</tr>
	</table>
	</div>
	</br>

         			
						<center>
    <logic:notEmpty name="displayRecordNo">
	 							

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="securityForm"/>-
	
	<bean:write property="endRecord"  name="securityForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>
								
								</logic:notEmpty>
								</td>
							</tr>
						</table>
						</center>
						
					
<div align="center">
				<logic:present name="securityForm" property="message1">
					<font color="red" size="3"><b><bean:write name="securityForm" property="message1" /></b></font>
				</logic:present>
				<logic:present name="securityForm" property="message">
					<font color="Green" size="3"><b><bean:write name="securityForm" property="message" /></b></font>
				</logic:present>
			</div>
			
			
			
			
		
		<logic:notEmpty name="allrecords"  >	
		<div style="width: 85%;">
		<table class="bordered " >
		
		

		
		<tr><th colspan="8"> <center>Onduty And Permission List</center></th></tr>
		
		
<tr ><th>Type</th><th>EmpName</th><th>Department</th><th>Designation</th><th>Approver Name</th><th>Status</th><th>Out time</th><th>In Time </th></tr>
<logic:iterate name="allrecords" id="securityForm">
<tr><td><bean:write  name="securityForm" property="reqType"/></td><td><a href="#" onclick="editDetails('${securityForm.requestNo }','${securityForm.reqType }','${securityForm.requsterID }')"><bean:write name="securityForm" property="userName"/></a></td><td>
<bean:write name="securityForm" property="department"/></td><td><bean:write  name="securityForm" property="designation"/></td>
<td><bean:write  name="securityForm" property="approverName"/></td><td><bean:write  name="securityForm" property="approveStatus"/></td>
<td><bean:write  name="securityForm" property="outTime"/></td><td><bean:write name="securityForm" property="inTime"/></td> </tr>
</logic:iterate>

<%-- 
 <display:column  title="Emp Name"><a href="#" onclick="editDetails('${data.requestNo }','${data.reqType }','${data.requsterID }')"><bean:write name="data" property="userName"/></a></display:column>
         
            <display:column property="department" title="Department"  />
            <display:column property="designation" title="Designation"  />
            <display:column property="fromDate" title="FromDate"  />
            <display:column property="toDate" title="ToDate"  />
            <display:column property="fromTime" title="FromTime"  />
            <display:column property="toTime" title="ToTime"  />     
            <display:column property="approverName" title="Approver Name"  />   
               
            <display:column  title="Emp Name"><a href="#" onclick="editDetails('${data.requestNo }','${data.reqType }','${data.requsterID }')"><bean:write name="data" property="userName1"/></a></display:column>
         
           <display:column property="department1" title="Department"  />
            <display:column property="designation1" title="Designation"  />
            <display:column property="fromDate1" title="Date"  />
       
            <display:column property="fromTime1" title="FromTime"  />
            <display:column property="toTime1" title="ToTime"  />     
            <display:column property="approverName1" title="Approver Name"  />   
                    
      --%></br>         

</table></div>
</logic:notEmpty>
<logic:notEmpty name="No records"  >
<div style="width: 85%;">
	<table class="bordered ">
<tr><th colspan="8"> <center>Onduty And Permission List</center></th></tr>
		
		
<tr ><th>Type</th><th>EmpName</th><th>Department</th><th>Designation</th><th>Approver Name</th><th>Status</th><th>Out time</th><th>In Time </th></tr>
<tr><td colspan="8" style="color: red"><center>Search Details Not Found..</td></tr></center>

</table></div>
</logic:notEmpty>


 <html:hidden  property="totalRecords"/>
 <html:hidden  property="startRecord"/>
 <html:hidden  property="endRecord"/>	

  <html:hidden  property="next"/>
   <html:hidden  property="total"/>
    <html:hidden  property="prev"/>
   
  

</html:form>
</body>
</html>