

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
    th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
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
	if(document.forms[0].employeeName.value==""){

  		alert("Please Enter Full Name");
  		document.forms[0].employeeName.focus();
  		return false;

	}
	if(document.forms[0].gender.value==""){

  		alert("Please Select Gender ");
  		document.forms[0].gender.focus();
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
if(document.forms[0].department.value==""){
	
	  alert("Please Enter the Department");
	  document.forms[0].department.focus();
	  return false;
	
	}

if(document.forms[0].designation.value==""){
	
	  alert("Please Enter the Designation");
	  document.forms[0].designation.focus();
	  return false;
	
	}
	
/*	
if(document.forms[0].approvalManger.value==""){
	
		alert("please Enter Approval  Manager");
		document.forms[0].approvalManger.focus();
		return false;
	}
	
	*/
	
	if(document.forms[0].location.value==""){
	
	alert("Please Select the Location");
	  document.forms[0].location.focus();
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
	
	if(document.forms[0].dateofJoining.value==""){
	
	alert("Please Enter Date of Joining*");
	  document.forms[0].dateofJoining.focus();
	  return false;
	
	}
	
	if(document.forms[0].eligibleforESIDeduction.value==""){
	
		alert("Please Select  ESI Deduction");
		 document.forms[0].eligibleforESIDeduction.focus();
		 return false;
	}
	if(document.forms[0].eligibleforESIDeduction.value=="Y" &&document.forms[0].esiNumber.value=="" ){
      alert("Please Enter ESI Number");
       document.forms[0].esiNumber.focus();
      return false;
   	}   
	if(document.forms[0].eligibleforPFDeduction.value==""){

         alert("Please Select  PF  Deduction");
         document.forms[0].eligibleforPFDeduction.focus();
         return false;
    }
    if(document.forms[0].eligibleforPFDeduction.value=="Y"&&document.forms[0].pfNumber.value=="" ){
         alert("Please Enter PF Number");
         document.forms[0].pfNumber.focus();
          return false;
    }    
	    
	if(document.forms[0].eligibleforPTDeduction.value==""){

             alert("Please Select  PT  Deduction");
             document.forms[0].eligibleforPTDeduction.focus();
             return false;
    }
	    
	   
         
    if(document.forms[0].eligibleforITDeduction.value==""){

     alert("Please Select the Y/N in IT");
     document.forms[0].eligibleforITDeduction.focus();
     return false;
     }
	
	if(document.forms[0].panNo.value==""){

  		alert("Please Enter PAN No.");
  		document.forms[0].panNo.focus();
  		return false;

	}
	if(document.forms[0].dob.value==""){

  		alert("Please Enter Date Of Birth.");
  		document.forms[0].dob.focus();
  		return false;

	}
	
	 if(document.forms[0].bonus.value==""){

          alert("Please Select the Y/N in Bonus");
          document.forms[0].bonus.focus();
          return false;
     }
	if(document.forms[0].leaves.value==""){

       alert("Please Select the Y/N in Leaves");
       document.forms[0].leaves.focus();
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
	
	if(document.forms[0].gradeID.value==""){
	
		alert("please Enter Grade ID Catogery");
		document.forms[0].gradeID.focus();
		return false;
	}

	if(document.forms[0].paymentMethod.value==""){

         alert("Please Select Payment Method");
         document.forms[0].paymentMethod.focus();
         return false;
    }
         
         
   	if(document.forms[0].paymentMethod.value=="e"||document.forms[0].paymentMethod.value=="d"||document.forms[0].paymentMethod.value=="e"){
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
			
     
    var url = "hrNewEmp.do?method=saveEmpOfficialInformation";
   document.forms[0].action=url;
    document.forms[0].submit();
	    
	    
}

function searchApprovalManager(){
	var toadd = document.getElementById("approvalManger").value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	document.getElementById("approvalManger").focus();
	if(toadd == ""){
		document.getElementById("approvalManger").focus();
		document.getElementById("sU").style.display ="none";
		return false;
	}
	var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        }
    }
     xmlhttp.open("POST","hrNewEmp.do?method=searchForApprovers&sId=New&searchText="+toadd,true);
    xmlhttp.send();
}

function selectUser(input){
var res = input.split("-");
	document.getElementById("approvalManger").value=res[1];
	disableSearch();
}
function disableSearch(){
	if(document.getElementById("sU") != null){
	document.getElementById("sU").style.display="none";
	}
}
	
	
	function goBack(){
	 var url = "hrNewEmp.do?method=displayEmpOfficalInfo";
    document.forms[0].action=url;
    document.forms[0].submit();
	}
	
	function modifyDetails(){
		if(document.forms[0].employeeNumber.value==""){

	  		alert("Please Enter Employee No");
	  		document.forms[0].employeeNumber.focus();
	  		return false;

		}
		if(document.forms[0].employeeName.value==""){

	  		alert("Please Enter Full Name");
	  		document.forms[0].employeeName.focus();
	  		return false;

		}
		if(document.forms[0].gender.value==""){

	  		alert("Please Select Gender ");
	  		document.forms[0].gender.focus();
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
	if(document.forms[0].department.value==""){
		
		  alert("Please Enter the Department");
		  document.forms[0].department.focus();
		  return false;
		
		}

	if(document.forms[0].designation.value==""){
		
		  alert("Please Enter the Designation");
		  document.forms[0].designation.focus();
		  return false;
		
		}
		
	/*	
	if(document.forms[0].approvalManger.value==""){
		
			alert("please Enter Approval  Manager");
			document.forms[0].approvalManger.focus();
			return false;
		}
		
		*/
		
		if(document.forms[0].location.value==""){
		
		alert("Please Select the Location");
		  document.forms[0].location.focus();
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
		
		if(document.forms[0].dateofJoining.value==""){
		
		alert("Please Enter Date of Joining*");
		  document.forms[0].dateofJoining.focus();
		  return false;
		
		}
		
		if(document.forms[0].eligibleforESIDeduction.value==""){
		
			alert("Please Select  ESI Deduction");
			 document.forms[0].eligibleforESIDeduction.focus();
			 return false;
		}
		if(document.forms[0].eligibleforESIDeduction.value=="Y" &&document.forms[0].esiNumber.value=="" ){
	      alert("Please Enter ESI Number");
	       document.forms[0].esiNumber.focus();
	      return false;
	   	}   
		if(document.forms[0].eligibleforPFDeduction.value==""){

	         alert("Please Select  PF  Deduction");
	         document.forms[0].eligibleforPFDeduction.focus();
	         return false;
	    }
	    if(document.forms[0].eligibleforPFDeduction.value=="Y"&&document.forms[0].pfNumber.value=="" ){
	         alert("Please Enter PF Number");
	         document.forms[0].pfNumber.focus();
	          return false;
	    }    
		    
		if(document.forms[0].eligibleforPTDeduction.value==""){

	             alert("Please Select  PT  Deduction");
	             document.forms[0].eligibleforPTDeduction.focus();
	             return false;
	    }
		    
		   
	         
	    if(document.forms[0].eligibleforITDeduction.value==""){

	     alert("Please Select the Y/N in IT");
	     document.forms[0].eligibleforITDeduction.focus();
	     return false;
	     }
		
		if(document.forms[0].panNo.value==""){

	  		alert("Please Enter PAN No.");
	  		document.forms[0].panNo.focus();
	  		return false;

		}
		if(document.forms[0].dob.value==""){

	  		alert("Please Enter Date Of Birth.");
	  		document.forms[0].dob.focus();
	  		return false;

		}
		
		
		
		 if(document.forms[0].bonus.value==""){

	          alert("Please Select the Y/N in Bonus");
	          document.forms[0].bonus.focus();
	          return false;
	     }
		if(document.forms[0].leaves.value==""){

	       alert("Please Select the Y/N in Leaves");
	       document.forms[0].leaves.focus();
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
		
		if(document.forms[0].gradeID.value==""){
		
			alert("please Enter Grade ID Catogery");
			document.forms[0].gradeID.focus();
			return false;
		}

		if(document.forms[0].paymentMethod.value==""){

	         alert("Please Select Payment Method");
	         document.forms[0].paymentMethod.focus();
	         return false;
	    }
	         
	         
	   	if(document.forms[0].paymentMethod.value=="e"||document.forms[0].paymentMethod.value=="d"||document.forms[0].paymentMethod.value=="e"){
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
		 var url = "hrNewEmp.do?method=modifyEmployeeDetails";
    document.forms[0].action=url;
    document.forms[0].submit();
	
	
	}
	
	function searchEmployeeId()
	{
		var uId=document.forms[0].reportingManger.value;
		var x=window.open("hrNewEmp.do?method=displayListUsers&uId="+uId,"SearchSID","width=500,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
	}
	
</script>

  </head>
  
  <body>
        <div align="center" >
        <logic:present name="empOfficInfoForm" property="message">
       <font color="Green" size="3"><b> <bean:write name="empOfficInfoForm" property="message" /></b>
        </font>
        </logic:present>
        
        </div>
  
  <html:form action="/hrNewEmp.do" enctype="multipart/form-data" >
						<table  class="bordered" >
                                 <tr>
                                 <th colspan="5"><big><b><center>EMPLOYEE INFORMATION</center></b></big></th></tr>
                                 	<tr><td align="left" ><b>Employee No:</b><font color="red">*</font></td>
                                 	<td colspan="3"><html:text property="employeeNumber"  style="width:200px;" maxlength="8" styleClass="rounded" ></html:text>
                                 	   <html:hidden property="reqEmpNo"/>
                                 	</td>
                       <tr>
                     
                        <td align="left"><b>Full Name:<font color="red">*</font></td>
                                 	<td><html:text property="employeeName"  style="width:200px;"  styleClass="rounded" ></html:text></td>
                      
                       <td align="left"><b>Gender:<font color="red">*</font></td>
                       <td><html:select property="gender">
                       <html:option value="">Select</html:option>
                       <html:option value="M">Male</html:option>
                       <html:option value="F">Female</html:option>
                       </html:select>
                       </td>
                           </tr>
						<tr>
						<td align="left"><b>Company Name:<font color="red">*</font></td>
						<td>  
			            	
			            	<html:select property="companyName" style="width:200px;" styleClass="selectinput">
			            	     <html:options property="companyIDList" labelProperty="companyNameList"></html:options>  
	                       </html:select>
			            	<html:hidden property="saveType"/>
	                     </td>
	 					      
						
						
						<td align="left"><b>Plant <font color="red">*</font></td>
						<td><html:select property="plant" style="width:200px;" styleClass="selectinput">
	                       <html:option value="">Plant Name</html:option>
	                       <html:options property="locIDList" labelProperty="locNameList"></html:options>  
	                       </html:select></td>
					   </tr>
						<tr>
					    	
					    	<td align="left"><b>Department<font color="red" >*</font></td>
						     <td>
	                             <html:select property="department" style="width:200px;" styleClass="selectinput"> 
	                               <html:option value="">-----Select-----</html:option>
	                        
	                            <html:options property="departIDList" labelProperty="departTXTList"  styleClass="text_field" />
	                             </html:select>
	                          </td>
	                          
					    	  <td align="left"><b>Designation<font color="red" >*</font></td>
					             <td>
	                               <html:select property="designation" style="width:200px;" styleClass="selectinput">
	                                   <html:option value="">-----Select-----</html:option>
	                                      <html:options property="desgnIDList"   labelProperty="desgnTXTList"        styleClass="text_field" />
	                         
	                                </html:select>
	                            </td>
						</tr>
						 
						<html:hidden property="approvalManger" />
						<html:hidden property="reportingManger" />
					<%-- 	<tr>
                <td align="left">Approval Manager:<font color="red">*</font></td>
<td style="width:400px;">&nbsp;&nbsp;<html:text property="approvalManger" styleClass="rounded" style="width:95%;" maxlength="180"
 styleId="approvalManger" onkeyup="searchApprovalManager()"><bean:write property="approvalManger" name="empOfficInfoForm" /></html:text></td>
      
               	<td align="left">Reporting Manager:</td>
               	<td><html:text property="reportingManger"  style="width:200px;" maxlength="8" styleClass="rounded" ></html:text>
               	<a href="#"><img  src="images/add-items.gif" onclick="searchEmployeeId()"/></a>
               	</td>
                                     
                </tr> 
                                 	         
            <tr id="sU" style="display:none;">
				<th width="100" class="specalt" scope="row">&nbsp;&nbsp;</th>
				<td id="sUTD" style="width:400px;">&nbsp;&nbsp;<iframe src="jsp/hr/empOfficialInfo/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe></td>
				<th></th>
				<th></th>
			</tr> 	     
                     --%>            	         
                         
                             <tr>
                           <th colspan="5"><big><b>WORKING LOCATION INFORMATION</b></big></th></tr>
                         
                          <tr>
					     
					      <td align="left"><b>Location<font color="red" >*</font></td>
					      <td colspan="3"><html:text property="location" style="width:200px;" styleClass="selectinput"></html:text>
                          
                       </td>
					     </tr>
                             
                             
					         	<tr>
					         	   <td align="left"><b>Country of working<font color="red" >*</font></td>
					              <td>
	                                   <html:select property="counID" onchange="getStates()" style="width:200px;" styleClass="selectinput">
	                                   <html:option value="">-----Select-----</html:option>
	                                   <html:options property="countryID" labelProperty="country" styleClass="text_field" />
	                         
	                                   </html:select>
	                             </td>
	 				   
						<logic:empty  name="diplayStates">
						
						<td align="left" ><b>State</td>
                          <td><html:select property="state" style="width:200px;" styleClass="selectinput"> 
                             <html:option value="">-----Select-----</html:option>
                               </html:select>
                           </td>
						
						
						</logic:empty>
						
						<logic:notEmpty name="diplayStates">
                        <td align="left"><b>State<font color="red" >*</font></td>
                           <td><html:select property="state" style="width:200px;" styleClass="selectinput"> 
                           <html:option value="">-----Select----</html:option>
                       <html:options property="stateID" labelProperty="stateName" styleClass="text_field"/>
                       </html:select></td>
	                         </logic:notEmpty>
					
					     </tr>
                       
                         
                        <tr>
                         
                          <th colspan="5"><big><b>     CONTACT INFORMATION</b></big></th></tr>
                         <tr>
                              <td align="left"><b>IP Phone No</td>
					       	<td><html:text property="ipPhoneNo" name ="empOfficInfoForm" maxlength="30" styleClass="rounded" style="width:200px;" ></html:text></td>
                         
                              <td align="left"><b>Tel No</td>
					       	<td><html:text property="telNo" name ="empOfficInfoForm" maxlength="30" styleClass="rounded" style="width:200px;" ></html:text></td>
    </tr>
                             
                             <tr>
                              <td align="left"><b>Extn No</th>
					       	<td><html:text property="extnNo" name ="empOfficInfoForm" maxlength="10" styleClass="rounded" style="width:200px;" ></html:text></td>
    
                             <td align="left"><b>Email ID</td>
					       	<td><html:text property="emailid" name ="empOfficInfoForm" maxlength="140" styleClass="rounded" style="width:200px;" ></html:text></td>
                           </tr>
                           
                             <tr> 
                            <td align="left"><b>Block No</td>
					       	<td><html:text property="room" name ="empOfficInfoForm" maxlength="20" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                            <td align="left"><b>Floor No</td>
					       	<td><html:text property="floor" name ="empOfficInfoForm" maxlength="20" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                           
                           </tr>
                      <tr>     
                             <td align="left"><b>Buliding</td>
					       	<td colspan="3"><html:text property="building" name ="empOfficInfoForm" maxlength="40" styleClass="rounded" style="width:200px;" ></html:text></td><!--
   
                            <td align="left">Address Type</td>
					       	<td><html:text property="addressTypeID" name ="empOfficInfoForm" maxlength="5" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                           
                           --></tr>
                           
                <tr>
                         
         <th colspan="5"><big><b>HR INFORMATION </b></big></th></tr>
                         
                             
                          <tr>
					     	<td align="left"><b>Date of Joining<font color="red" >*</font></td>
						   <td>
	                           <html:text property="dateofJoining" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="rounded" style="width:200px;"
										readonly="true"/>
	                        </td>
					   
					    
                             <td align="left"><b>Date of Conformation</td>
						   <td>
	                           <html:text property="dateofConformation" styleId="dateofConformation" onfocus="popupCalender('dateofConformation')" styleClass="rounded" style="width:200px;"
										readonly="true"/>
	                        </td>
	                          </tr>
					   
                             <tr>
	                        <td align="left"><b>Date of Leaving</td>
						   <td>
	                           <html:text property="dateofLeaving" styleId="dateofLeaving" onfocus="popupCalender('dateofLeaving')" styleClass="rounded" style="width:200px;"
										/>
	                        </td>
                           
                        
                             <td align="left"><b>Eligible for ESI Deduction <font color="red" >*</font></td>
					          <td>
	                             <html:select property="eligibleforESIDeduction"  styleClass="selectinput"  onchange="isEmpStatus()" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
                               </tr>
                     <tr>
                             
                             <td align="left"><b>ESI Number<div id="isESI" style="visibility: hidden"><font color="red" >*</font></div></td>
					       	<td><html:text property="esiNumber" name ="empOfficInfoForm" maxlength="40" styleClass="rounded" style="width:200px;" ></html:text></td>
                             
                             <td align="left"><b>Eligible for PF Deduction <font color="red" >*</font></td>
					          <td>
	                             <html:select property="eligibleforPFDeduction"  styleClass="selectinput" onchange="isEmpStatus()" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
                                </tr>
                             
                               <tr>
                             
                             <td align="left"><b>PF Number<div id="isPF" style="visibility: hidden"><font color="red" >*</font></div></td>
					       	<td><html:text property="pfNumber" name ="empOfficInfoForm" maxlength="20" styleClass="rounded" style="width:200px;"></html:text></td>
                           
                                    <td align="left"><b>Eligible for PT Deduction <font color="red" >*</font></td>
					          <td>
	                             <html:select property="eligibleforPTDeduction"  styleClass="selectinput" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
	                          <tr>
	                           </tr>
	                               <td align="left"><b>Eligible for IT Deduction <font color="red" >*</font></td>
					          <td>
	                             <html:select property="eligibleforITDeduction"  styleClass="selectinput" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
                               
                                 <td align="left"><b>PAN No </b><font color="red" >*</font></td>
                                 <td>
                                 
                                   <html:text property="panNo" maxlength="40" styleClass="rounded" style="width:200px;" ></html:text>
                                 </td>                              
                                 </tr>
  
                         <tr>
                         <td><b>Date Of Birth</b> <font color="red" >*</font></td>
                         <td >
                         <html:text property="dob" styleClass="rounded" styleId="dob" onfocus="popupCalender('dob')"/>
                         </td>
                         <td><b>UAN No</b></td>
                         <td >
                         <html:text property="uanno" styleClass="rounded" style="width:200px;"/>
                         </td>
                         
                         </tr>
                         <tr>
                          <th colspan="5"><big><b> LEAVE DETAILS</b></big></th></tr>
                         <tr>
                           <td align="left"><b>Eligible for Bonus <font color="red" >*</font></td>
                                 
                                 <td>
                                 <html:select property="bonus" styleClass="selectinput">
                                 <html:option value="">-----Select-----</html:option>
                                    <html:option value="Y">YES</html:option>
                                       <html:option value="N">NO</html:option>
                                 </html:select>
                                 </td>
                              <td align="left"><b>Eligible for Leaves <font color="red" >*</font></td>
                                 <td>
                               <html:select property="leaves" styleClass="selectinput">
                               <html:option value="">-----Select-----</html:option>
                                    <html:option value="Y">YES</html:option>
                                       <html:option value="N">NO</html:option>
                                 </html:select>
                                 </td>
                                 </tr>
                         <tr>
                         <td><b>Year</b></td><td colspan="3"><html:text property="year"></html:text></td>
                         </tr>
                         <tr>
                         <th >Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th>
                         </tr>
                         <tr>
	                         <td><b>Casual</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="openingBalence1" style="width:50px;" /></td>
	                         <td><html:text property="availedBal1" style="width:50px;"/></td>
	                         <td><html:text property="closingBalence1" style="width:50px;"/></td>
	                         <td><html:text property="awaitingBal1" style="width:50px;"/></td>
                         </tr>
                          <tr>
	                         <td><b>Sick</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="openingBalence2" style="width:50px;"/></td>
	                         <td><html:text property="availedBal2" style="width:50px;"/></td>
	                         <td><html:text property="closingBalence2" style="width:50px;"/></td>
	                         <td><html:text property="awaitingBal2" style="width:50px;"/></td>
                         </tr>
                          <tr>
	                         <td><b>Privilege</b>&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="openingBalence3" style="width:50px;"/></td>
	                         <td><html:text property="availedBal3" style="width:50px;"/></td>
	                         <td><html:text property="closingBalence3" style="width:50px;"/></td>
	                         <td><html:text property="awaitingBal3" style="width:50px;"/></td>
                         </tr>
                          <tr>
	                         <td><b>Maternity</b>&nbsp;&nbsp;&nbsp;<html:text property="openingBalence4" style="width:50px;"/></td>
	                         <td><html:text property="availedBal4" style="width:50px;"/></td>
	                         <td><html:text property="closingBalence4" style="width:50px;"/></td>
	                         <td><html:text property="awaitingBal4" style="width:50px;"/></td>
                         </tr>
                         <tr>
                          <th colspan="5"><big><b> SALARY DETAILS</b></big></th></tr>
                         <tr>
                         
						<td align="left" ><b>Pay Group<font color="red" >*</font></td>
						<td><html:select property="payGroup" style="width:200px;" styleClass="selectinput">
						   <html:option value="">-----Select-----</html:option>
						   <html:options property="paygroupList"  labelProperty="paystextList"></html:options>
						
						</html:select>
						<td align="left"><b>Employee Category<font color="red" >*</font></td>
						<td>
						  <html:select property="employeeCategory" style="width:200px;" styleClass="selectinput">
						  <html:option value="">-----Select-----</html:option>
						   <html:options property="empCatogeryList" labelProperty="empCatogerytextList"/>
						  
						  </html:select>
						
						</td>
						</tr>
						<tr>
						
						<td align="left"><b>Grade ID<font color="red" >*</font></td>
						<td colspan="3">
						  <html:select property="gradeID" style="width:200px;" styleClass="selectinput">
						  <html:option value="">-----Select-----</html:option>
						  <html:options  property="gradeidList" labelProperty="gradetxtList"/>
						  
						  </html:select>
						
						</td>
					    </tr>
					   <!--   
                          
                                     <th align="left">Head Quarters<font color="red" >*</font></th>
					        <td><html:text property="headQuarters" styleClass="rounded"></html:text>
					        
	                          
	                        </td>
					     	-->
					     	 
                   
                                                         
                                 
                                 <tr>
                                 <td align="left"><b>Payment Method <font color="red" >*</font></td>
                                  <td>
	                             <html:select property="paymentMethod" styleClass="selectinput" onchange="isEmpStatus()" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                 <html:options property="payIDList" labelProperty="payNameList"/>
	                        
	                             </html:select>
	                              </td>
					        	 <td align="left"><b>Salary Currency</td>
					        	 <td>
					        	      <html:select property="salaryCurrency"  style="width:200px;" styleClass="selectinput">
						           <html:option value="">-----Select-----</html:option>
						           <html:options property="currencyIDList" labelProperty="currencyNameList"/>
						           </html:select>
					        	    </td>
                                   </tr>
                                 <tr>
                          
                         
                         
                         
                         <tr>
                          <th colspan="5"><big><b>BANK DETAILS</b></big></th></tr>
                         <tr>
                                 <tr>
                                 <td align="left"><b>Account Type<div id="isAT" style="visibility: hidden"><font color="red" >*</font></div></td>
						     
                                 <td>
                                  <html:select property="accountType" name="empOfficInfoForm" style="width:200px;" styleClass="selectinput">
                                  <html:option value="">-----Select-----</html:option>
                                  <html:option value="S">Savings Account</html:option>
                                   <html:option value="C">Current Account</html:option>
                                  
                                  
                                  </html:select>
                                 
                                 </td>
                                  <td align="left"><b>Account Number<div id="isAN" style="visibility: hidden"><font color="red" >*</font></div></td>
						         <td><html:text property="accountNumber" maxlength="30" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 </tr>
                                <!--  
                                 
                                 <tr>
                                  <th align="left">Bank ID<font color="red" >*</font></th>
                                 <td>
                                 <html:select property="bankId">
                                  <html:option value="">-------Select-------</html:option>
                                  <html:options property="bankIDList" />
                                 </html:select>
                                 </td>
                                 
                                 
                         
                                 </tr>
						      --><tr>
                                 <td align="left"><b>Bank Name<div id="isBN" style="visibility: hidden"><font color="red" >*</font></div></td>
						         <td>
						           <html:select property="bankName"  style="width:200px;"  onchange="getcodes()" styleClass="selectinput">
						           <html:option value="">-------Select-------</html:option>
						           <html:options property="bankIDList" labelProperty="banknameList"/>
						           
						           </html:select>
						         
						         </td>
                                 
                                  <td align="left"><b>Branch Name<div id="isBrN" style="visibility: hidden"><font color="red" >*</font></div></td>
						         <td><html:text property="branchName" maxlength="40" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 
                                 
                                 </tr>
                                 
                                 <tr>
                                 
                                 <td align="left"><b>IFSC Code</td>
						         <td><html:text property="ifsCCode" maxlength="20" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 
                                  <td align="left"><b>MICR Code</td>
						         <td><html:text property="micrCode" maxlength="20" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 </tr>
                                      <tr style="height:20px;">
	                              <td colspan="5"> 
	                               </td>
                                 </tr><!-- 
                                 <tr>
                                       <th align="left">Creation Date<font color="red" >*</font></th>
						           <td>
						              <html:text property="createdDate" styleId="createdDate" onfocus="popupCalender('createdDate')" styleClass="text_field" style="width:200px;"
										readonly="true"/>
										
										</td>
                                 
                                      <th align="left">Time Of Entry<font color="red" >*</font></th>
						           <td>
						              <html:text property="timeofEntry" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;" />
										
										</td>
                                 
                                 </tr>
                                 
                                   <tr style="height:20px;">
	                              <td> 
	                               </td>
                                 </tr> 
                                 
                                 <tr>
                                   <th align="left">User Name<font color="red" >*</font></th>
						           <td>
						              <html:text property="userName" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;" />
										
										</td>
                                 
                                  <th align="left">Changed On<font color="red" >*</font></th>
						           <td>
						              <html:text property="changedOn" styleId="changedOn" onfocus="popupCalender('changedOn')" styleClass="text_field" style="width:200px;"
										readonly="true"/>
										
										</td>
                                 
                                 </tr>
                                 
                                    <tr style="height:20px;">
	                              <td> 
	                               </td>
                                 </tr>
                                 
                                 <tr>
                                 <th align="left">Last Changed By<font color="red" >*</font></th>
						           <td>
						              <html:text property="lastChangedBy" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;" />
										
										</td>
                                 
                                 
                                 
                                 </tr> 
                                 
                                      <tr style="height:20px;">
	                              <td> 
	                               </td>
                                 </tr>
                                 
                                 <tr>
                                 
                                  <th align="left">Deleted On<font color="red" >*</font></th>
						           <td>
						              <html:text property="deletedOn" styleId="deletedOn" onfocus="popupCalender('deletedOn')" styleClass="text_field" style="width:200px;"
										readonly="true"/>
										
										</td>
                                 
                                 
                                   <th align="left">Deleted By<font color="red" >*</font></th>
						           <td>
						              <html:text property="deletedBy" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;" />
										
										</td>
                                 
                                 
                                 </tr>
                                    
                                 --> 
						
					   
						<tr>
						
						<td colspan="6"  align="center" style="background:#F2F0F1;">
						<center>
						<logic:notEmpty name="SaveButton">
				<%-- 	   <html:button property="method" onclick="saveOIEmployee()" value="save" styleClass="rounded" style="width:80px"></html:button>
					   <html:reset styleClass="rounded" style="width:80px">Clear</html:reset> --%>
						 <html:reset styleClass="rounded" style="width:80px" onclick="goBack()">Back</html:reset>
						</logic:notEmpty>
						<logic:notEmpty name="ModifyButton">
						<%--   <html:button property="method" onclick="modifyDetails()" value="Modify" styleClass="rounded" style="width:80px"></html:button> --%>
						 <html:reset styleClass="rounded" style="width:80px" onclick="goBack()">Back</html:reset>
						</logic:notEmpty>
						</td>
						</center>				
						</tr>
						
						
						
						
						  
                                   <tr style="height:10px;">
	                              <td colspan="5"> 
	                               </td>
                                 </tr> 
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						</table>				
<html:hidden property="leaveType1" />
<html:hidden property="leaveType2" />
<html:hidden property="leaveType3" />
<html:hidden property="leaveType4" />					
 
  </html:form>
    
  </body>
</html>
