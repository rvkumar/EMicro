

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 <STYLE TYPE="text/css">
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}

</STYLE>
  <head><link rel="stylesheet" type="text/css" href="css/microlabs1.css" />

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
<!--
/////////////////////////////////////////////////
-->
     <script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
    <title>My JSP 'empOIForm.jsp' starting page</title>
    
<style type="text/css">
.selectinput{
border: 1px solid #ccc;
-moz-border-radius: 7px;
-webkit-border-radius: 7px;
border-radius: 7px;
-moz-box-shadow: 2px 2px 3px #666;
-webkit-box-shadow: 2px 2px 3px #666;
box-shadow: 2px 2px 3px
}
</style>

	

	
	<script type="text/javascript">
	function  getcodes(){
	
	var url="hrNewEmp.do?method=getcodes";
			document.forms[0].action=url;
			document.forms[0].submit();	
	
	
	}

 
 function getStates(){	   
	
			var url="hrNewEmp.do?method=getStates";
			document.forms[0].action=url;
			document.forms[0].submit();		 
     }
	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}
	///////// Stars////////
	function isEmpStatus()
{
var a=document.forms[0].eligibleforESIDeduction.value;
var b=document.forms[0].eligibleforPFDeduction.value;
var c=document.forms[0].paymentMethod.value;

if(a=="Y"){

document.getElementById("isESI").style.visibility="visible";



}	
if(a=="N"){

document.getElementById("isESI").style.visibility="hidden";

}	
if(a==""){

document.getElementById("isESI").style.visibility="hidden";


}
if(b=="Y"){


document.getElementById("isPF").style.visibility="visible";


}	
if(b=="N"){

document.getElementById("isPF").style.visibility="hidden";

}	
if(b==""){

document.getElementById("isPF").style.visibility="hidden";


}
if(c=="e"||c=="d"||c=="b"){


document.getElementById("isAT").style.visibility="visible";
document.getElementById("isAN").style.visibility="visible";
document.getElementById("isBN").style.visibility="visible";
document.getElementById("isBrN").style.visibility="visible";


}	
if(c=="c"){

document.getElementById("isAT").style.visibility="hidden";
document.getElementById("isAN").style.visibility="hidden";
document.getElementById("isBN").style.visibility="hidden";
document.getElementById("isBrN").style.visibility="hidden";


}	
if(c==""){


document.getElementById("isAT").style.visibility="hidden";
document.getElementById("isAN").style.visibility="hidden";
document.getElementById("isBN").style.visibility="hidden";
document.getElementById("isBrN").style.visibility="hidden";


}

}
//////////VALIDATIONS/////////
function  saveOIEmployee(){

	if(document.forms[0].employeeNumber.value==""){

  		alert("Please Enter Employee No");
  		document.forms[0].employeeNumber.focus();
  		return false;

	}
	if(document.forms[0].companyName.value==""){

  		alert("select Company Name");
  		document.forms[0].companyName.focus();
  		return false;

	}

	if(document.forms[0].plant.value==""){

  		alert("Enter the Plant Code");
  		document.forms[0].plant.focus();
  	return false;
	}


	if(document.forms[0].payGroup.value==""){


		alert("Please enter the Pay Group");
		document.forms[0].payGroup.focus();
		return false;
	}

	if(document.forms[0].employeeCategory.value==""){
	
		alert("please Enter Employee Catogery");
		document.forms[0].employeeCategory.focus();
		return false;
	}

	if(document.forms[0].reportingManger.value==""){
	
		alert("please Enter Reporting Manager");
		document.forms[0].reportingManger.focus();
		return false;
	}

	if(document.forms[0].approvalManger.value==""){
	
		alert("please Enter Approval  Manager");
		document.forms[0].approvalManger.focus();
		return false;
	}
	if(document.forms[0].gradeID.value==""){
	
		alert("please Enter Grade ID Catogery");
		document.forms[0].gradeID.focus();
		return false;
	}

	if(document.forms[0].designation.value==""){
	
	  alert("Please Enter the Designation");
	  document.forms[0].designation.focus();
	  return false;
	
	}
	if(document.forms[0].department.value==""){
	
	  alert("Please Enter the Department");
	  document.forms[0].department.focus();
	  return false;
	
	}
	
	if(document.forms[0].counID.value==""){
	
	  alert("Please Select the Country");
	  document.forms[0].counID.focus();
	  return false;
	}

	if(document.forms[0].state.value==""){
	
	alert("Please Select the State");
	  document.forms[0].state.focus();
	  return false;
	
	}
	
	if(document.forms[0].location.value==""){
	
	alert("Please Select the Location");
	  document.forms[0].location.focus();
	  return false;
	
	}
	
	if(document.forms[0].dateofJoining.value==""){
	
	alert("Please Enter Date of Joining*");
	  document.forms[0].dateofJoining.focus();
	  return false;
	
	}
	if(document.forms[0].dateofConformation.value==""){
	
	alert("Please Enter Date of Conformation");
	  document.forms[0].dateofConformation.focus();
	  return false;
	
	}
	if(document.forms[0].dateofLeaving.value==""){
	
	alert("Please Enter Date of Leaving");
	  document.forms[0].dateofLeaving.focus();
	  return false;
	
	}
	if(document.forms[0].eligibleforESIDeduction.value==""){
	
		alert("Please Select the Y/N in ESI");
		 document.forms[0].eligibleforESIDeduction.focus();
		 return false;
	}
	if(document.forms[0].eligibleforESIDeduction.value=="Y" &&document.forms[0].esiNumber.value=="" ){
      alert("Please Enter ESI Number");
       document.forms[0].esiNumber.focus();
      return false;
   	}   
	if(document.forms[0].eligibleforPFDeduction.value==""){

         alert("Please Select the Y/N in PF");
         document.forms[0].eligibleforPFDeduction.focus();
         return false;
    }
    if(document.forms[0].eligibleforPFDeduction.value=="Y"&&document.forms[0].pfNumber.value=="" ){
         alert("Please Enter PF Number");
         document.forms[0].pfNumber.focus();
          return false;
    }    
	    
	if(document.forms[0].eligibleforPTDeduction.value==""){

             alert("Please Select the Y/N in PT");
             document.forms[0].eligibleforPTDeduction.focus();
             return false;
    }
	    
	   
         
    if(document.forms[0].eligibleforITDeduction.value==""){

     alert("Please Select the Y/N in IT");
     document.forms[0].eligibleforPTDeduction.focus();
     return false;
     }

     if(document.forms[0].leaves.value==""){

       alert("Please Select the Y/N in Leaves");
       document.forms[0].leaves.focus();
       return false;
     }
     if(document.forms[0].bonus.value==""){

          alert("Please Select the Y/N in Bonus");
          document.forms[0].bonus.focus();
          return false;
     }
	if(document.forms[0].paymentMethod.value==""){

         alert("Please Select Payment Method");
         document.forms[0].paymentMethod.focus();
         return false;
    }
         
         
   	if(document.forms[0].paymentMethod.value=="c"||document.forms[0].paymentMethod.value=="e"||document.forms[0].paymentMethod.value=="d"){
    	if(document.forms[0].accountType.value==""){

        	alert("Please Select  Account Type ");
            document.forms[0].accountType.focus();
            return false;
        }
        if(document.forms[0].accountNumber.value==""){

            alert("Please Select Account Number ");
            document.forms[0].accountNumber.focus();
            return false;
        }
        if(document.forms[0].bankName.value==""){

            alert("Please Enter Bank Name ");
            document.forms[0].bankName.focus();
            return false;
        }
        if(document.forms[0].branchName.value==""){

            alert("Please Enter Branch Name ");
            document.forms[0].branchName.focus();
            return false;
        }
     }	   
			
     if(document.forms[0].salaryCurrency.value==""){

             alert("Please Enter Salary Currency");
             document.forms[0].salaryCurrency.focus();
             return false;
    }
    var url = "hrNewEmp.do?method=saveEmpOfficialInformation";
    document.forms[0].action=url;
    document.forms[0].submit();
	    
	    
}

function update(){

var url = "empofficial.do?method=updateOfficialInfo";
    document.forms[0].action=url;
    document.forms[0].submit();

}
	
	function closeemp()
	{
	
var url="contacts.do?method=curentRecordcontacts";
			document.forms[0].action=url;
			document.forms[0].submit();
		
}

</script>

</head>

<body>
	
   	<html:form action="/empofficial.do" enctype="multipart/form-data" >
   	   
   		<table     class="bordered">
			<tr>
				
				<th colspan="4" style="text-align:center;" >OFFICIAL INFORMATION </th>
				</tr>
					<logic:notEmpty name="details">
					<logic:iterate name="details" id="detailsView">
					

					<tr >
							<td colspan="2" rowspan="6" style="text-align:center;">
						<logic:notEmpty name="employeePhoto">
						<logic:iterate id="abc" name="empPhotoList">
						
							<img border="1" src="/EMicro Files/images/EmpPhotos/<bean:write name="abc" property="photoImage"/>" width="150px" height="150px"/>
							</logic:iterate>
						
							</logic:notEmpty>
						</td>
						
						
						<tr>
						<td colspan="2"><font size="5"> ${detailsView.employeeName}</font></td> 
						
						</tr>
						<!--<tr style="height:10px;">
						<th>Employee No</th><td>${detailsView.employeeNumber}</td>
						
						</tr>
                        
						--><tr>
						<th align="left">First Name</th><td>${detailsView.firstName}</td> 
						</tr>
						<tr>
						<th align="left">Middle Name</th><td>${detailsView.middleName}</td> 
						</tr>
						<tr>
						<th align="left">Last Name</th><td>${detailsView.lastName}</td> 
						</tr>
					
						<tr>
						<td colspan="4"></td>
						</tr>
					
					
						   <tr>
                          
                           	<th align="left">Plant </th><td >${detailsView.location}</td>
 							<th align="left" >Pay Group</th><td>${detailsView.payGroup}</td>
						</tr>
				<tr>
						<th align="left" >Staff Category</th><td>${detailsView.employeeCategory}</td>
						<th align="left">Grade ID</th><td>${detailsView.gradeID}</td>
					
						</tr>
						<tr style="height:10px;">
								<th align="left">Designation</th><td>${detailsView.designation}</td>
								<th align="left">Department</th><td>${detailsView.department}</td>
                           </tr>
                           <tr>
                           	<th align="left">Reporting Manager </th><td>${detailsView.reportingManger}</td> 
                               <th align="left">Approval Manager </th><td>${detailsView.approvalManger}</td>
						</tr>
						<tr>
						<td colspan="4"></td>
						</tr>
                         <tr>
                         <th align="left">Location</th><td>${detailsView.plant}</td>
                       
 				 			<th align="left" >State</th><td>${detailsView.state}</td>
							
				   		</tr>
				   		<tr>
				   		  <th align="left">Country of working</th><td colspan="3">${detailsView.counID}</td>
				   			  	
				       </tr>
                          
                        	<tr>
                		<td colspan="4">
                		<div align="center" >
		<logic:present name="employeeOfficialInfoForm" property="message">
			<font color="green"><bean:write name="employeeOfficialInfoForm" property="message" /></font>
		</logic:present>
		<logic:present name="employeeOfficialInfoForm" property="message">
			<font color="red"><bean:write name="employeeOfficialInfoForm" property="message1" /></font>
		</logic:present>
   	</div>
                		</td>
                		</tr>
				          <tr>
                        <tr>
                        <th colspan="4" style="text-align:center;">Contact Information </th>
                  
                        </tr>
                        <tr>
                        	<th align="left">Building</th><td>${detailsView.building}</td>
                        
  							<th align="left">Floor No</th><td>${detailsView.floor}</td>
  								
  						</tr>
  						<tr>
  						<th align="left">Room / Block No.</th><td>${detailsView.room}</td>
  						<th align="left">Tel No</th><td>${detailsView.telNo}</td>
  						</tr>
  						<tr>
                           	
  							<th align="left">Extn No</th><td>${detailsView.extnNo}</td>
  							 <th align="left">IP Phone No.</th><td>${detailsView.ipPhone}</td>
  						</tr>
                        <tr>
                        	  <th align="left">Email ID</th><td colspan="4">${detailsView.emailid}</td>
                        	  	   	 
                		</tr>
                		
                		<tr>
                		<td colspan="4" align="center">
                		<center><html:button property="method"  value="Close" onclick="closeemp()" styleClass="rounded" style="width: 100px"></html:button></center>
                		</td>
                		</tr>
					</table>
					</logic:iterate>
					</logic:notEmpty>				
		
                        <html:hidden name="contactsForm" property="totalRecords"/>
				<html:hidden name="contactsForm" property="startRecord"/>
				<html:hidden name="contactsForm" property="endRecord"/>
				<html:hidden name="contactsForm" property="locationId"/>
				<html:hidden name="contactsForm" property="department"/>
						<html:hidden name="contactsForm" property="firstName"/>
				
	</html:form>
	   
</body>
</html>
