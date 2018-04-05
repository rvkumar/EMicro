

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
    


	

	
	<script type="text/javascript">
	function  getcodes(){
	
	var url="hrNewEmp.do?method=getcodes";
			document.forms[0].action=url;
			document.forms[0].submit();	
	
	
	}

 
 function getStates(){	   
	
			var url="hrNewEmp.do?method=getStatesModifyEmp";
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
    

function  modifyOIEmployee(){
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

	if(document.forms[0].states.value==""){
	
	alert("Please Select the State");
	  document.forms[0].states.focus();
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
			
     if(document.forms[0].salaryCurrency.value==""){

             alert("Please Enter Salary Currency");
             document.forms[0].salaryCurrency.focus();
             return false;
    }
	    
	    var url = "hrNewEmp.do?method=modifyEmployeeDetails";
	    document.forms[0].action=url;
	    document.forms[0].submit();
	    
	    
}
	
</script>





  </head>
  
  <body>
        <div align="center" >
        <logic:present name="empOfficInfoForm" property="message">
        <font color="red">
        <bean:write name="empOfficInfoForm" property="message" />
        </font>
        </logic:present>
        
        </div>
  
  <html:form action="/hrNewEmp.do" enctype="multipart/form-data" >
  <table class="bordered">
			<tr><th>EMPLOYEE INFORMATION </th></tr>
                                 
                                 <tr>
                                 	<th align="left">Employee No: <font color="red">*</font></th>
                                 	<td><html:text property="employeeNumber"  style="width:200px;" maxlength="8" styleClass="rounded" ></html:text></td>
                                 
                                 
                                 
                                 </tr>
                                       <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr> 
						
						<tr>
						<th align="left">Company Name:<font color="red">*</font></th>
						<td>  
			            	
			            	<html:select property="companyName" style="width:200px;">
			            	 <html:option value="">Company  Name</html:option>
			            	     <html:options property="companyIDList" labelProperty="companyNameList"></html:options>  
	                       </html:select>
			            	
	                     </td>
	 					
						
						<th align="left">Plant <font color="red">*</font></th>
						<td><html:select property="plant" styleClass="rounded" style="width:200px;">
	                       <html:option value="">Plant Name</html:option>
	                       <html:options property="plantIDList" labelProperty="plantNameList"></html:options>  
	                       </html:select></td>
						</tr>
						
						 <tr style="height:10px;">
	                      <td> 
	                      </td>
                         </tr>
						<tr>
						
						<th align="left" >Pay Group<font color="red" >*</font></th>
						<td><html:select property="payGroup" style="width:200px;">
						   <html:option value="">-----Select-----</html:option>
						   <html:options property="paygroupList"  labelProperty="paystextList"></html:options>
						
						</html:select>
					        	
						
						
						

						
						
						<th align="left">Employee Category<font color="red" >*</font></th>
						<td>
						  <html:select property="employeeCategory" style="width:200px;">
						  <html:option value="">-----Select-----</html:option>
						   <html:options property="empCatogeryList" labelProperty="empCatogerytextList"/>
						  
						  </html:select>
						
						</td>
						</tr>
							 <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
						<tr>
						
						    <tr>
                                 	<th align="left">Reporting Manager :<font color="red">*</font></th>
                                 	<td><html:text property="reportingManger"  style="width:200px;" maxlength="8" styleClass="rounded" ></html:text></td>
                                 
                                 	<th align="left">Approval Manager :<font color="red">*</font></th>
                                 	<td><html:text property="approvalManger"  style="width:200px;" maxlength="8" styleClass="rounded" ></html:text></td>
                                 
                                 </tr>
                                 
                                  <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
						
						<tr>
						
						<th align="left">Grade ID<font color="red" >*</font></th>
						<td>
						  <html:select property="gradeID" style="width:200px;">
						  <html:option value="">-----Select-----</html:option>
						  <html:options  property="gradeidList" labelProperty="gradetxtList"/>
						  
						  </html:select>
						
						
						</td>
					   
					      
					          
	 				   
					   
					   
					    </tr>
					    
					
							 <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
					  <tr>
					    	<tr>
					    	
					    	  <th align="left">Designation<font color="red" >*</font></th>
					             <td>
	                               <html:select property="designation"  styleClass="rounded"  style="width:200px;">
	                                   <html:option value="">-----Select-----</html:option>
	                                      <html:options property="desgnIDList"   labelProperty="desgnTXTList"        styleClass="text_field" />
	                         
	                                </html:select>
	                            </td>
						
					      	<th align="left">Department<font color="red" >*</font></th>
						     <td>
	                             <html:select property="department"  styleClass="rounded" style="width:200px;"> 
	                               <html:option value="">-----Select-----</html:option>
	                        
	                            <html:options property="departIDList" labelProperty="departTXTList"  styleClass="text_field" />
	                             </html:select>
	                          </td>
					   
					      
					         
					   
					   
					     </tr>
					         <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                             
                             
                                <tr>     
                             <th align="left">Email ID</th>
					       	<td><html:text property="emailid" name ="empOfficInfoForm" maxlength="140" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                          
   
                           
                           </tr>
                           
                             <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                             
                             <tr>
                              <th align="left">Tel No</th>
					       	<td><html:text property="telNo" name ="empOfficInfoForm" maxlength="30" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                              <th align="left">Extn No</th>
					       	<td><html:text property="extnNo" name ="empOfficInfoForm" maxlength="10" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                             
                             
                             </tr>
                              <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                             
                             
                             
					         	<tr>
					         	   <th align="left">Country of working<font color="red" >*</font></th>
					              <td>
	                                   <html:select property="counID"  styleClass="rounded" onchange="getStates()" style="width:200px;">
	                                   <html:option value="">-----Select-----</html:option>
	                                   <html:options property="countryID" labelProperty="country" styleClass="text_field" />
	                         
	                                   </html:select>
	                             </td>
	 				   
						<logic:empty  name="diplayStates">
						
						<th align="left" >State</th>
                          <td><html:select property="state" styleClass="text_field" style="width:200px;"> 
                             <html:option value="">-----Select-----</html:option>
                               </html:select>
                           </td>
						
						
						</logic:empty>
						
						<logic:notEmpty name="diplayStates">
                        <th align="left">State<font color="red" >*</font></th>
                           <td><html:select property="states" styleClass="text_field" style="width:200px;"> 
                           <html:option value="">-----Select----</html:option>
                       <html:options property="stateID" labelProperty="stateName" styleClass="text_field"/>
                       </html:select></td>
	                         </logic:notEmpty>
					
					     </tr>
					         
					     <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
					     <tr>
					     
					      <th align="left">Location<font color="red" >*</font></th>
					      <td><html:text property="location" styleClass="text_field" style="width:200px;"></html:text>
                           </td>
					     </tr>
					     
					     <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                             
                          <tr><!--   
                          
                                     <th align="left">Head Quarters<font color="red" >*</font></th>
					        <td><html:text property="headQuarters" styleClass="rounded"></html:text>
					        
	                          
	                        </td>
					     	--><th align="left">Date of Joining<font color="red" >*</font></th>
						   <td>
	                           <html:text property="dateofJoining" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field" style="width:200px;"
										readonly="true"/>
	                        </td>
					   
					      </tr>
					   
   		          <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                             
                             <tr>
                             <th align="left">Date of Conformation<font color="red" >*</font></th>
						   <td>
	                           <html:text property="dateofConformation" styleId="dateofConformation" onfocus="popupCalender('dateofConformation')" styleClass="text_field" style="width:200px;"
										readonly="true"/>
	                        </td>
	                        
	                        <th align="left">Date of Leaving</th>
						   <td>
	                           <html:text property="dateofLeaving" styleId="dateofLeaving" onfocus="popupCalender('dateofLeaving')" styleClass="text_field" style="width:200px;"
										/>
	                        </td>
                             </tr>
                   
                   
                   
                   
                    <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                           <tr>
                           
                            <th align="left">Block No</th>
					       	<td><html:text property="room" name ="empOfficInfoForm" maxlength="20" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                            <th align="left">Floor No</th>
					       	<td><html:text property="floor" name ="empOfficInfoForm" maxlength="20" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                           
                           </tr>
                           
                           
                    <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                      <tr>     
                             <th align="left">Buliding</th>
					       	<td><html:text property="building" name ="empOfficInfoForm" maxlength="40" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                            <th align="left">Address Type</th>
					       	<td><html:text property="addressTypeID" name ="empOfficInfoForm" maxlength="5" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                           
                           </tr>
                           
                   
                    <tr style="height:10px;">
	                           <td> 
	                           </td>
                             </tr>
                   
                             <tr>
                        
                             <th align="left">Eligible for ESI Deduction <font color="red" >*</font></th>
					          <td>
	                             <html:select property="eligibleforESIDeduction"  styleClass="rounded"  onchange="isEmpStatus()" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
                             
                             
                             <th align="left">ESI Number<div id="isESI" style="visibility: hidden"><font color="red" >*</font></div></th>
					       	<td><html:text property="esiNumber" name ="empOfficInfoForm" maxlength="40" styleClass="rounded" style="width:200px;" ></html:text></td>
   
                             </tr>
                             
                                <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>
                             
                               <tr>
                             
                             <th align="left">Eligible for PF Deduction <font color="red" >*</font></th>
					          <td>
	                             <html:select property="eligibleforPFDeduction"  styleClass="rounded" onchange="isEmpStatus()" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
                             
                             
                             <th align="left">PF Number<div id="isPF" style="visibility: hidden"><font color="red" >*</font></div></th>
					       	<td><html:text property="pfNumber" name ="empOfficInfoForm" maxlength="20" styleClass="rounded" style="width:200px;"></html:text></td>
                           
                             </tr>
					   
					           <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>
                                 
                                 <tr>
                                    <th align="left">Eligible for PT Deduction <font color="red" >*</font></th>
					          <td>
	                             <html:select property="eligibleforPTDeduction"  styleClass="rounded" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
	                               <th align="left">Eligible for IT Deduction <font color="red" >*</font></th>
					          <td>
	                             <html:select property="eligibleforITDeduction"  styleClass="rounded" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                <html:option value="Y">Yes</html:option>
	                                 <html:option value="N">No</html:option>
	                         
	                             </html:select>
	                          </td>
                                 </tr>
                                 
                                 
                                   <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>
                                 
                                 <tr>
                                 <th align="left">PAN No <font color="red" >*</font></th>
                                 <td>
                                 
                                   <html:text property="panNo" maxlength="40" styleClass="rounded" style="width:200px;" ></html:text>
                                 </td>                              
                                 
                                 </tr>
                                 
                                   
                                   <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>
                                 
                                 <tr>
                              <th align="left">Eligible for Leaves <font color="red" >*</font></th>
                                 
                                 <td>
                                 
        
        
                               <html:select property="leaves">
                               <html:option value="">-----Select-----</html:option>
                                    <html:option value="Y">YES</html:option>
                                       <html:option value="N">NO</html:option>
                                 </html:select>
                                 </td>
                                 
                                      <th align="left">Eligible for Bonus <font color="red" >*</font></th>
                                 
                                 <td>
                                 <html:select property="bonus">
                                 <html:option value="">-----Select-----</html:option>
                                    <html:option value="Y">YES</html:option>
                                       <html:option value="N">NO</html:option>
                                 </html:select>
                                 </td>
                                 
                                 
                                 
                                 </tr>
                                 
                                 
                                <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>   
                                 
                                 
                                 
                                 
                                 <tr>
                                 
                                 
                                 <th align="left">Payment Method <font color="red" >*</font></th>
                                  <td>
	                             <html:select property="paymentMethod"  styleClass="rounded" onchange="isEmpStatus()" style="width:200px;">
	                               <html:option value="">-----Select-----</html:option>
	                                 <html:options property="payIDList" labelProperty="payNameList"/>
	                        
	                             </html:select>
	                              </td>
	                              
	                              
	                              	
					        	
					        	 <th align="left">Salary Currency</th>
					        	 <td>
					        	      <html:select property="salaryCurrency"  style="width:200px;">
						           <html:option value="">-----Select-----</html:option>
						           <html:options property="currencyIDList" labelProperty="currencyNameList"/>
						           
						           </html:select>
					        	
					        	    </td>
                              
	                              
	                              
                                 
                                   </tr>
                                   
                                   
                                   
                                   <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>
                                 <tr>
                                 
                                 <tr>
                                 <th align="left">Account Type<div id="isAT" style="visibility: hidden"><font color="red" >*</font></div></th>
						     
                                 <td>
                                  <html:select property="accountType" name="empOfficInfoForm" style="width:200px;">
                                  <html:option value="">-----Select-----</html:option>
                                  <html:option value="S">Savings Account</html:option>
                                   <html:option value="C">Current Account</html:option>
                                  
                                  
                                  </html:select>
                                 
                                 </td>
                                  <th align="left">Account Number<div id="isAN" style="visibility: hidden"><font color="red" >*</font></div></th>
						         <td><html:text property="accountNumber" maxlength="30" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 
                                 
                                
                                 </tr>
                                   <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr><!--  
                                 
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
                                 <th align="left">Bank Name<div id="isBN" style="visibility: hidden"><font color="red" >*</font></div></th>
						         <td>
						           <html:select property="bankName"  style="width:200px;"  onchange="getcodes()">
						           <html:option value="">-------Select-------</html:option>
						           <html:options property="bankIDList" labelProperty="banknameList"/>
						           
						           </html:select>
						         
						         </td>
                                 
                                  <th align="left">Branch Name<div id="isBrN" style="visibility: hidden"><font color="red" >*</font></div></th>
						         <td><html:text property="branchName" maxlength="40" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 
                                 
                                 </tr>
                                 
                                 
                                   <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>
                                 
                                 <tr>
                                 
                                 <th align="left">IFSC Code<font color="red" >*</font></th>
						         <td><html:text property="ifsCCode" maxlength="20" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 
                                  <th align="left">MICR Code<font color="red" >*</font></th>
						         <td><html:text property="micrCode" maxlength="20" name ="empOfficInfoForm" styleClass="rounded" style="width:200px;"></html:text></td>
                                 
                            
                                 </tr>
                                      <tr style="height:20px;">
	                              <td> 
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
                                    
                                 --><tr>
                                       <tr style="height:10px;">
	                              <td> 
	                               </td>
                                 </tr>  
						
					   
						<tr>
						<td colspan="6"  align="center" style="background:#F2F0F1;">
						
					   <html:button property="method" onclick="modifyOIEmployee()" value="Modify" styleClass="rounded" style="width:80px"></html:button>
					   <html:reset styleClass="rounded" style="width:80px">Clear</html:reset>
						
						</td>				
						</tr>
						
						
						
						
						  
                                   <tr style="height:10px;">
	                              <td>bor 
	                               </td>
                                 </tr> 
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						</table>				
 
  </html:form>
    
  </body>
</html>
