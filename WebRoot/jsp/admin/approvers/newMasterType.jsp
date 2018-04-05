<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">
function goBack()
  {
  
  var URL="materialApprover.do?method=displayApprovers";
		document.forms[0].action=URL;
 		document.forms[0].submit();
  }
function searchEmployeeId(filed)
	{
	  
		var reqFiled=filed;
		var x=window.open("materialApprover.do?method=displayListUsers&reqFiled="+filed,"SearchSID","width=1100,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
	}

function onSave()
{

if(document.forms[0].materialType.value!="Customer Master" || document.forms[0].materialType.value!="Vendor Master")
{ 

}else{

if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
}
	    
if(document.forms[0].materialType.value=="")
	    {
	      alert("Please Select Material Type");
	      document.forms[0].materialType.focus();
	      return false;

	    }
	    
	    
	    //1
	    
	     if(document.forms[0].role1.value=="")
	     {
	     alert("Please Select Role1");
	      document.forms[0].role1.focus();
	      return false;
         }
         
	    if(document.forms[0].approver1.value=="")
	    {
	      alert("Please Select  Priority 1 Approver");
	      document.forms[0].approver1.focus();
	      return false;
	      }
	      
     
	    
	     if(document.forms[0].parllelAppr12.value!="" && document.forms[0].parllelAppr11.value=="") 	  
	      {
	     alert("Please Select Parallel Priority 1 Approver First!");
	      document.forms[0].parllelAppr11.focus();
	      return false;
	     
	     }
	    //2
	  if(document.forms[0].approver2.value!="" && document.forms[0].approver1.value=="")	
	  {
	    alert("Please Select  Priority 1 Approver First!");
	      document.forms[0].approver1.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver2.value!="")
	    {
	     if(document.forms[0].role2.value=="")
	     {
	     alert("Please Select Role2");
	      document.forms[0].role2.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr22.value!=""&& document.forms[0].parllelAppr21.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr21.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr22.value!=""||document.forms[0].parllelAppr21.value!="")	  
	      {
	      if(document.forms[0].approver2.value==""){
	     alert("Please Select  Priority 2 Approver");
	      document.forms[0].approver2.focus();
	      return false;
	     }
	     }
	     //3
	    if(document.forms[0].approver3.value!=""&&document.forms[0].approver2.value=="")	
	  {
	    alert("Please Select Priority 2 Approver First!");
	      document.forms[0].approver2.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver3.value!="")
	    {
	     if(document.forms[0].role3.value=="")
	     {
	     alert("Please Select Role3");
	      document.forms[0].role3.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr32.value!="" && document.forms[0].parllelAppr31.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr31.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr32.value!=""||document.forms[0].parllelAppr31.value!="")	  
	      {
	    if(document.forms[0].approver3.value==""){
	     alert("Please Select Priority 3 Approver!");
	      document.forms[0].approver3.focus();
	      return false;
	     
	     }}
	    //4
	    if(document.forms[0].approver4.value!=""&&document.forms[0].approver3.value=="")	
	  {
	    alert("Please Select Priority 3 Approver First!");
	      document.forms[0].approver3.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver4.value!="")
	    {
	     if(document.forms[0].role4.value=="")
	     {
	     alert("Please Select Role4");
	      document.forms[0].role4.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr42.value!="" && document.forms[0].parllelAppr41.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr41.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr42.value!=""||document.forms[0].parllelAppr41.value!="" )	  
	      {
	      if(document.forms[0].approver4.value==""){
	     alert("Please Select  Priority 4 Approver");
	      document.forms[0].approver4.focus();
	      return false;
	     }
	     }
	    //5
	 
	    if(document.forms[0].approver5.value!=""&&document.forms[0].approver4.value=="")	
	  {
	    alert("Please Select Priority 4 Approver First!");
	      document.forms[0].approver4.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver5.value!="")
	    {
	     if(document.forms[0].role5.value=="")
	     {
	     alert("Please Select Role5");
	      document.forms[0].role5.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr52.value!="" && document.forms[0].parllelAppr51.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr51.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr52.value!=""||document.forms[0].parllelAppr51.value!="" )	  
	      {
	      if(document.forms[0].approver5.value==""){
	     alert("Please Select  Priority 5 Approver");
	      document.forms[0].approver5.focus();
	      return false;
	     }
	     }
	    
	    //6
	    if(document.forms[0].approver6.value!=""&&document.forms[0].approver5.value=="")	
	  {
	    alert("Please Select Priority 5 Approver First!");
	      document.forms[0].approver5.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver6.value!="")
	    {
	     if(document.forms[0].role6.value=="")
	     {
	     alert("Please Select Role6");
	      document.forms[0].role6.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr62.value!=""&& document.forms[0].parllelAppr61.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr61.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr62.value!=""||document.forms[0].parllelAppr61.value!="" )	  
	      {
	      if(document.forms[0].approver6.value==""){
	     alert("Please Select  Priority 6 Approver");
	      document.forms[0].approver6.focus();
	      return false;
	     }
	     }
	    
	if(document.forms[0].approver1.value==document.forms[0].parllelAppr11.value  || document.forms[0].approver1.value==document.forms[0].parllelAppr12.value 
	|| document.forms[0].approver1.value==document.forms[0].approver2.value      || document.forms[0].approver1.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr22.value || document.forms[0].approver1.value==document.forms[0].approver3.value
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr31.value || document.forms[0].approver1.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].approver1.value==document.forms[0].approver4.value     || document.forms[0].approver1.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr42.value || document.forms[0].approver1.value==document.forms[0].approver5.value
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr51.value || document.forms[0].approver1.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].approver1.value==document.forms[0].approver6.value     || document.forms[0].approver1.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 1  Approver  Duplicate Employee Found.");
	document.forms[0].approver1.focus();
	return false;
	}
	if(document.forms[0].approver2.value!=""){ 
		if(document.forms[0].approver2.value==document.forms[0].parllelAppr21.value || document.forms[0].approver2.value==document.forms[0].parllelAppr22.value 
		|| document.forms[0].approver2.value==document.forms[0].approver1.value      || document.forms[0].approver2.value==document.forms[0].parllelAppr11.value 
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr12.value || document.forms[0].approver2.value==document.forms[0].approver3.value
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr31.value || document.forms[0].approver2.value==document.forms[0].parllelAppr32.value
		|| document.forms[0].approver2.value==document.forms[0].approver4.value     || document.forms[0].approver2.value==document.forms[0].parllelAppr41.value 
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr42.value  || document.forms[0].approver2.value==document.forms[0].approver5.value
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr51.value || document.forms[0].approver2.value==document.forms[0].parllelAppr52.value
		|| document.forms[0].approver2.value==document.forms[0].approver6.value     || document.forms[0].approver2.value==document.forms[0].parllelAppr61.value 
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr62.value)
		{
		alert("In Priority 2  Approver  Duplicate Employee Found.");
		document.forms[0].approver2.focus();
		return false;
		
		} 
	
	}
	 if(document.forms[0].approver3.value!=""){ 
	 if(document.forms[0].approver3.value==document.forms[0].parllelAppr31.value || document.forms[0].approver3.value==document.forms[0].parllelAppr32.value 
	|| document.forms[0].approver3.value==document.forms[0].approver1.value       || document.forms[0].approver3.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr12.value  || document.forms[0].approver3.value==document.forms[0].approver2.value
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr21.value || document.forms[0].approver3.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver3.value==document.forms[0].approver4.value     || document.forms[0].approver3.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr42.value || document.forms[0].approver3.value==document.forms[0].approver5.value
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr51.value || document.forms[0].approver3.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].approver3.value==document.forms[0].approver6.value      || document.forms[0].approver3.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 3  Approver  Duplicate Employee Found.");
	document.forms[0].approver3.focus();
	return false;
	
	} }
	    
	    
	    if(document.forms[0].approver4.value!=""){ 
	 if(document.forms[0].approver4.value==document.forms[0].parllelAppr41.value || document.forms[0].approver4.value==document.forms[0].parllelAppr42.value 
	|| document.forms[0].approver4.value==document.forms[0].approver1.value      || document.forms[0].approver4.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr12.value  || document.forms[0].approver4.value==document.forms[0].approver2.value
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr21.value  || document.forms[0].approver4.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver4.value==document.forms[0].approver3.value      || document.forms[0].approver4.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr32.value  || document.forms[0].approver4.value==document.forms[0].approver5.value
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr51.value  || document.forms[0].approver4.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].approver4.value==document.forms[0].approver6.value      || document.forms[0].approver4.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 4  Approver  Duplicate Employee Found.");
	document.forms[0].approver4.focus();
	return false;
	
	} }
	
	
	if(document.forms[0].approver5.value!=""){   
	 if(document.forms[0].approver5.value==document.forms[0].parllelAppr51.value || document.forms[0].approver5.value==document.forms[0].parllelAppr52.value 
	|| document.forms[0].approver5.value==document.forms[0].approver1.value      || document.forms[0].approver5.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr12.value || document.forms[0].approver5.value==document.forms[0].approver2.value
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr21.value || document.forms[0].approver5.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver5.value==document.forms[0].approver3.value    || document.forms[0].approver5.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr32.value  || document.forms[0].approver5.value==document.forms[0].approver4.value
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr41.value || document.forms[0].approver5.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].approver5.value==document.forms[0].approver6.value      || document.forms[0].approver5.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 5  Approver  Duplicate Employee Found.");
	document.forms[0].approver5.focus();
	return false;
	
	}   }
	
	if(document.forms[0].approver6.value!=""){ 
	
	 if(document.forms[0].approver6.value==document.forms[0].parllelAppr61.value || document.forms[0].approver6.value==document.forms[0].parllelAppr62.value 
	|| document.forms[0].approver6.value==document.forms[0].approver1.value      || document.forms[0].approver6.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr12.value  || document.forms[0].approver6.value==document.forms[0].approver2.value
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr21.value || document.forms[0].approver6.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver6.value==document.forms[0].approver3.value     || document.forms[0].approver6.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr32.value  || document.forms[0].approver6.value==document.forms[0].approver4.value
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr41.value || document.forms[0].approver6.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].approver6.value==document.forms[0].approver5.value   || document.forms[0].approver6.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr52.value)
	{
	alert("In Priority 6  Approver  Duplicate Employee Found.");
	document.forms[0].approver6.focus();
	return false;
	}
	}   
	
	
	//parallel app 1
	
	if(document.forms[0].parllelAppr11.value!=""){ 
	if(document.forms[0].parllelAppr11.value==document.forms[0].approver1.value   || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr12.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].approver2.value    || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr22.value || document.forms[0].parllelAppr11.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].approver4.value     || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr42.value || document.forms[0].parllelAppr11.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].approver6.value      || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 1  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr11.focus();
	return false;
	
	}   }
	
	if(document.forms[0].parllelAppr21.value!=""){ 
	if(document.forms[0].parllelAppr21.value==document.forms[0].approver2.value    || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr22.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].approver1.value     || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr21.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].approver4.value      || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr42.value  || document.forms[0].parllelAppr21.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].approver6.value     || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 2  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr21.focus();
	return false;
	
	}   }
	
if(document.forms[0].parllelAppr31.value!=""){ 
	if(document.forms[0].parllelAppr31.value==document.forms[0].approver3.value     || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr32.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].approver1.value      || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr31.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].approver4.value      || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr42.value  || document.forms[0].parllelAppr31.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr51.value   || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].approver6.value    || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 3  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr31.focus();
	return false;
	
	}   
	}
	
	if(document.forms[0].parllelAppr41.value!=""){ 
	if(document.forms[0].parllelAppr41.value==document.forms[0].approver4.value   || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr42.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].approver1.value    || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr41.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].approver3.value     || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr32.value    || document.forms[0].parllelAppr41.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr51.value  || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].approver6.value  || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 4  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr41.focus();
	return false;
	
	}  } 
	
	if(document.forms[0].parllelAppr51.value!=""){ 
	
	if(document.forms[0].parllelAppr51.value==document.forms[0].approver5.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr52.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].approver1.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr51.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr51.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 5  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr51.focus();
	return false;
	
	}  } 
	
	
	if(document.forms[0].parllelAppr61.value!=""){ 
	if(document.forms[0].parllelAppr61.value==document.forms[0].approver6.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr62.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].approver1.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr61.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr61.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].approver5.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr52.value)
	{
	alert("In Priority 6  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr61.focus();
	return false;
	}
	}  
	
	 
	 //parallel app 2
	 if(document.forms[0].parllelAppr12.value!=""){ 
	if(document.forms[0].parllelAppr12.value==document.forms[0].approver1.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].approver2.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr22.value || document.forms[0].parllelAppr12.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].approver4.value|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr42.value|| document.forms[0].parllelAppr12.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 1  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr12.focus();
	return false;
	}
	}   
	
	
	if(document.forms[0].parllelAppr22.value!="")
	{ 
	if(document.forms[0].parllelAppr22.value==document.forms[0].approver2.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].approver1.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr22.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].approver4.value|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr42.value|| document.forms[0].parllelAppr22.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 2  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr22.focus();
	return false;
	
	}   }
	
if(document.forms[0].parllelAppr32.value!=""){ 
	if(document.forms[0].parllelAppr32.value==document.forms[0].approver3.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].approver1.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr32.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].approver4.value|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr42.value|| document.forms[0].parllelAppr32.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 3  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr32.focus();
	return false;
	
	}  } 
	
	if(document.forms[0].parllelAppr42.value!=""){ 
	if(document.forms[0].parllelAppr42.value==document.forms[0].approver4.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].approver1.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr42.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr42.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 4  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr42.focus();
	return false;
	
	}   }
	
	if(document.forms[0].parllelAppr52.value!=""){ 
	if(document.forms[0].parllelAppr52.value==document.forms[0].approver5.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].approver1.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr52.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr52.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 5  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr52.focus();
	return false;
	
	}   }
	
	if(document.forms[0].parllelAppr62.value!=""){ 
	if(document.forms[0].parllelAppr62.value==document.forms[0].approver6.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].approver1.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr61.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr61.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].approver5.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr52.value)
	{
	alert("In Priority 6  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr62.focus();
	return false;
	}
	}

var URL="materialApprover.do?method=saveApprovers";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}
function onModify()
{

if(document.forms[0].materialType.value!="Customer Master" || document.forms[0].materialType.value!="Vendor Master")
{ 

}else{

if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
}
	    
if(document.forms[0].materialType.value=="")
	    {
	      alert("Please Select Material Type");
	      document.forms[0].materialType.focus();
	      return false;

	    }
	    
	    
	    //1
	    
	     if(document.forms[0].role1.value=="")
	     {
	     alert("Please Select Role1");
	      document.forms[0].role1.focus();
	      return false;
         }
         
	    if(document.forms[0].approver1.value=="")
	    {
	      alert("Please Select  Priority 1 Approver");
	      document.forms[0].approver1.focus();
	      return false;
	      }
	      
     
	    
	     if(document.forms[0].parllelAppr12.value!="" && document.forms[0].parllelAppr11.value=="") 	  
	      {
	     alert("Please Select Parallel Priority 1 Approver First!");
	      document.forms[0].parllelAppr11.focus();
	      return false;
	     
	     }
	    //2
	  if(document.forms[0].approver2.value!="" && document.forms[0].approver1.value=="")	
	  {
	    alert("Please Select  Priority 1 Approver First!");
	      document.forms[0].approver1.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver2.value!="")
	    {
	     if(document.forms[0].role2.value=="")
	     {
	     alert("Please Select Role2");
	      document.forms[0].role2.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr22.value!=""&& document.forms[0].parllelAppr21.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr21.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr22.value!=""||document.forms[0].parllelAppr21.value!="")	  
	      {
	      if(document.forms[0].approver2.value==""){
	     alert("Please Select  Priority 2 Approver");
	      document.forms[0].approver2.focus();
	      return false;
	     }
	     }
	     //3
	    if(document.forms[0].approver3.value!=""&&document.forms[0].approver2.value=="")	
	  {
	    alert("Please Select Priority 2 Approver First!");
	      document.forms[0].approver2.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver3.value!="")
	    {
	     if(document.forms[0].role3.value=="")
	     {
	     alert("Please Select Role3");
	      document.forms[0].role3.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr32.value!="" && document.forms[0].parllelAppr31.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr31.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr32.value!=""||document.forms[0].parllelAppr31.value!="")	  
	      {
	    if(document.forms[0].approver3.value==""){
	     alert("Please Select Priority 3 Approver!");
	      document.forms[0].approver3.focus();
	      return false;
	     
	     }}
	    //4
	    if(document.forms[0].approver4.value!=""&&document.forms[0].approver3.value=="")	
	  {
	    alert("Please Select Priority 3 Approver First!");
	      document.forms[0].approver3.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver4.value!="")
	    {
	     if(document.forms[0].role4.value=="")
	     {
	     alert("Please Select Role4");
	      document.forms[0].role4.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr42.value!="" && document.forms[0].parllelAppr41.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr41.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr42.value!=""||document.forms[0].parllelAppr41.value!="" )	  
	      {
	      if(document.forms[0].approver4.value==""){
	     alert("Please Select  Priority 4 Approver");
	      document.forms[0].approver4.focus();
	      return false;
	     }
	     }
	    //5
	 
	    if(document.forms[0].approver5.value!=""&&document.forms[0].approver4.value=="")	
	  {
	    alert("Please Select Priority 4 Approver First!");
	      document.forms[0].approver4.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver5.value!="")
	    {
	     if(document.forms[0].role5.value=="")
	     {
	     alert("Please Select Role5");
	      document.forms[0].role5.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr52.value!="" && document.forms[0].parllelAppr51.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr51.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr52.value!=""||document.forms[0].parllelAppr51.value!="" )	  
	      {
	      if(document.forms[0].approver5.value==""){
	     alert("Please Select  Priority 5 Approver");
	      document.forms[0].approver5.focus();
	      return false;
	     }
	     }
	    
	    //6
	    if(document.forms[0].approver6.value!=""&&document.forms[0].approver5.value=="")	
	  {
	    alert("Please Select Priority 5 Approver First!");
	      document.forms[0].approver5.focus();
	      return false;
	  
	  }  
	   if(document.forms[0].approver6.value!="")
	    {
	     if(document.forms[0].role6.value=="")
	     {
	     alert("Please Select Role6");
	      document.forms[0].role6.focus();
	      return false;
         }
	    }
	    
	     if(document.forms[0].parllelAppr62.value!=""&& document.forms[0].parllelAppr61.value=="")	  
	      {
	     alert("Please Select Parallel Approver1 First!");
	      document.forms[0].parllelAppr61.focus();
	      return false;
	     
	     }
	     if(document.forms[0].parllelAppr62.value!=""||document.forms[0].parllelAppr61.value!="" )	  
	      {
	      if(document.forms[0].approver6.value==""){
	     alert("Please Select  Priority 6 Approver");
	      document.forms[0].approver6.focus();
	      return false;
	     }
	     }
	    
	if(document.forms[0].approver1.value==document.forms[0].parllelAppr11.value  || document.forms[0].approver1.value==document.forms[0].parllelAppr12.value 
	|| document.forms[0].approver1.value==document.forms[0].approver2.value      || document.forms[0].approver1.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr22.value || document.forms[0].approver1.value==document.forms[0].approver3.value
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr31.value || document.forms[0].approver1.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].approver1.value==document.forms[0].approver4.value     || document.forms[0].approver1.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr42.value || document.forms[0].approver1.value==document.forms[0].approver5.value
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr51.value || document.forms[0].approver1.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].approver1.value==document.forms[0].approver6.value     || document.forms[0].approver1.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver1.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 1  Approver  Duplicate Employee Found.");
	document.forms[0].approver1.focus();
	return false;
	}
	if(document.forms[0].approver2.value!=""){ 
		if(document.forms[0].approver2.value==document.forms[0].parllelAppr21.value || document.forms[0].approver2.value==document.forms[0].parllelAppr22.value 
		|| document.forms[0].approver2.value==document.forms[0].approver1.value      || document.forms[0].approver2.value==document.forms[0].parllelAppr11.value 
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr12.value || document.forms[0].approver2.value==document.forms[0].approver3.value
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr31.value || document.forms[0].approver2.value==document.forms[0].parllelAppr32.value
		|| document.forms[0].approver2.value==document.forms[0].approver4.value     || document.forms[0].approver2.value==document.forms[0].parllelAppr41.value 
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr42.value  || document.forms[0].approver2.value==document.forms[0].approver5.value
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr51.value || document.forms[0].approver2.value==document.forms[0].parllelAppr52.value
		|| document.forms[0].approver2.value==document.forms[0].approver6.value     || document.forms[0].approver2.value==document.forms[0].parllelAppr61.value 
		|| document.forms[0].approver2.value==document.forms[0].parllelAppr62.value)
		{
		alert("In Priority 2  Approver  Duplicate Employee Found.");
		document.forms[0].approver2.focus();
		return false;
		
		} 
	
	}
	 if(document.forms[0].approver3.value!=""){ 
	 if(document.forms[0].approver3.value==document.forms[0].parllelAppr31.value || document.forms[0].approver3.value==document.forms[0].parllelAppr32.value 
	|| document.forms[0].approver3.value==document.forms[0].approver1.value       || document.forms[0].approver3.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr12.value  || document.forms[0].approver3.value==document.forms[0].approver2.value
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr21.value || document.forms[0].approver3.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver3.value==document.forms[0].approver4.value     || document.forms[0].approver3.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr42.value || document.forms[0].approver3.value==document.forms[0].approver5.value
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr51.value || document.forms[0].approver3.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].approver3.value==document.forms[0].approver6.value      || document.forms[0].approver3.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver3.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 3  Approver  Duplicate Employee Found.");
	document.forms[0].approver3.focus();
	return false;
	
	} }
	    
	    
	    if(document.forms[0].approver4.value!=""){ 
	 if(document.forms[0].approver4.value==document.forms[0].parllelAppr41.value || document.forms[0].approver4.value==document.forms[0].parllelAppr42.value 
	|| document.forms[0].approver4.value==document.forms[0].approver1.value      || document.forms[0].approver4.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr12.value  || document.forms[0].approver4.value==document.forms[0].approver2.value
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr21.value  || document.forms[0].approver4.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver4.value==document.forms[0].approver3.value      || document.forms[0].approver4.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr32.value  || document.forms[0].approver4.value==document.forms[0].approver5.value
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr51.value  || document.forms[0].approver4.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].approver4.value==document.forms[0].approver6.value      || document.forms[0].approver4.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver4.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 4  Approver  Duplicate Employee Found.");
	document.forms[0].approver4.focus();
	return false;
	
	} }
	
	
	if(document.forms[0].approver5.value!=""){   
	 if(document.forms[0].approver5.value==document.forms[0].parllelAppr51.value || document.forms[0].approver5.value==document.forms[0].parllelAppr52.value 
	|| document.forms[0].approver5.value==document.forms[0].approver1.value      || document.forms[0].approver5.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr12.value || document.forms[0].approver5.value==document.forms[0].approver2.value
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr21.value || document.forms[0].approver5.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver5.value==document.forms[0].approver3.value    || document.forms[0].approver5.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr32.value  || document.forms[0].approver5.value==document.forms[0].approver4.value
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr41.value || document.forms[0].approver5.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].approver5.value==document.forms[0].approver6.value      || document.forms[0].approver5.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].approver5.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 5  Approver  Duplicate Employee Found.");
	document.forms[0].approver5.focus();
	return false;
	
	}   }
	
	if(document.forms[0].approver6.value!=""){ 
	
	 if(document.forms[0].approver6.value==document.forms[0].parllelAppr61.value || document.forms[0].approver6.value==document.forms[0].parllelAppr62.value 
	|| document.forms[0].approver6.value==document.forms[0].approver1.value      || document.forms[0].approver6.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr12.value  || document.forms[0].approver6.value==document.forms[0].approver2.value
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr21.value || document.forms[0].approver6.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].approver6.value==document.forms[0].approver3.value     || document.forms[0].approver6.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr32.value  || document.forms[0].approver6.value==document.forms[0].approver4.value
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr41.value || document.forms[0].approver6.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].approver6.value==document.forms[0].approver5.value   || document.forms[0].approver6.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].approver6.value==document.forms[0].parllelAppr52.value)
	{
	alert("In Priority 6  Approver  Duplicate Employee Found.");
	document.forms[0].approver6.focus();
	return false;
	}
	}   
	
	
	//parallel app 1
	
	if(document.forms[0].parllelAppr11.value!=""){ 
	if(document.forms[0].parllelAppr11.value==document.forms[0].approver1.value   || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr12.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].approver2.value    || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr22.value || document.forms[0].parllelAppr11.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].approver4.value     || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr42.value || document.forms[0].parllelAppr11.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr11.value==document.forms[0].approver6.value      || document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr11.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 1  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr11.focus();
	return false;
	
	}   }
	
	if(document.forms[0].parllelAppr21.value!=""){ 
	if(document.forms[0].parllelAppr21.value==document.forms[0].approver2.value    || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr22.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].approver1.value     || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr21.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].approver4.value      || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr42.value  || document.forms[0].parllelAppr21.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr21.value==document.forms[0].approver6.value     || document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr21.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 2  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr21.focus();
	return false;
	
	}   }
	
if(document.forms[0].parllelAppr31.value!=""){ 
	if(document.forms[0].parllelAppr31.value==document.forms[0].approver3.value     || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr32.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].approver1.value      || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr31.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].approver4.value      || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr42.value  || document.forms[0].parllelAppr31.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr51.value   || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr31.value==document.forms[0].approver6.value    || document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr31.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 3  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr31.focus();
	return false;
	
	}   
	}
	
	if(document.forms[0].parllelAppr41.value!=""){ 
	if(document.forms[0].parllelAppr41.value==document.forms[0].approver4.value   || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr42.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].approver1.value    || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr41.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].approver3.value     || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr32.value    || document.forms[0].parllelAppr41.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr51.value  || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr41.value==document.forms[0].approver6.value  || document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr41.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 4  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr41.focus();
	return false;
	
	}  } 
	
	if(document.forms[0].parllelAppr51.value!=""){ 
	
	if(document.forms[0].parllelAppr51.value==document.forms[0].approver5.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr52.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].approver1.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr51.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr51.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr51.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr51.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 5  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr51.focus();
	return false;
	
	}  } 
	
	
	if(document.forms[0].parllelAppr61.value!=""){ 
	if(document.forms[0].parllelAppr61.value==document.forms[0].approver6.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr62.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].approver1.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr61.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr61.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr61.value==document.forms[0].approver5.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr52.value)
	{
	alert("In Priority 6  Parallel Approver 1  Duplicate Employee Found.");
	document.forms[0].parllelAppr61.focus();
	return false;
	}
	}  
	
	 
	 //parallel app 2
	 if(document.forms[0].parllelAppr12.value!=""){ 
	if(document.forms[0].parllelAppr12.value==document.forms[0].approver1.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].approver2.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr22.value || document.forms[0].parllelAppr12.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].approver4.value|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr42.value|| document.forms[0].parllelAppr12.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr12.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr12.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 1  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr12.focus();
	return false;
	}
	}   
	
	
	if(document.forms[0].parllelAppr22.value!="")
	{ 
	if(document.forms[0].parllelAppr22.value==document.forms[0].approver2.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr21.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].approver1.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr22.value==document.forms[0].approver3.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr31.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr32.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].approver4.value|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr42.value|| document.forms[0].parllelAppr22.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr22.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr22.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 2  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr22.focus();
	return false;
	
	}   }
	
if(document.forms[0].parllelAppr32.value!=""){ 
	if(document.forms[0].parllelAppr32.value==document.forms[0].approver3.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].approver1.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr32.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].approver4.value|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr42.value|| document.forms[0].parllelAppr32.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr32.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr32.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 3  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr32.focus();
	return false;
	
	}  } 
	
	if(document.forms[0].parllelAppr42.value!=""){ 
	if(document.forms[0].parllelAppr42.value==document.forms[0].approver4.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr41.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].approver1.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr42.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr42.value==document.forms[0].approver5.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr51.value || document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr52.value
	|| document.forms[0].parllelAppr42.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr42.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 4  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr42.focus();
	return false;
	
	}   }
	
	if(document.forms[0].parllelAppr52.value!=""){ 
	if(document.forms[0].parllelAppr52.value==document.forms[0].approver5.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].approver1.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr52.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr52.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr52.value==document.forms[0].approver6.value|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr52.value==document.forms[0].parllelAppr62.value)
	{
	alert("In Priority 5  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr52.focus();
	return false;
	
	}   }
	
	if(document.forms[0].parllelAppr62.value!=""){ 
	if(document.forms[0].parllelAppr62.value==document.forms[0].approver6.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr61.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].approver1.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr11.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr12.value || document.forms[0].parllelAppr61.value==document.forms[0].approver2.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr21.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr22.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].approver3.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr31.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr32.value|| document.forms[0].parllelAppr61.value==document.forms[0].approver4.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr41.value || document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr42.value
	|| document.forms[0].parllelAppr62.value==document.forms[0].approver5.value|| document.forms[0].parllelAppr61.value==document.forms[0].parllelAppr51.value 
	|| document.forms[0].parllelAppr62.value==document.forms[0].parllelAppr52.value)
	{
	alert("In Priority 6  Parallel Approver 2  Duplicate Employee Found.");
	document.forms[0].parllelAppr62.focus();
	return false;
	}
	}
var URL="materialApprover.do?method=modifyMatType";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function clearApprover1(priority)
{

	if(priority==1)
	{
		document.forms[0].role1.value="";
		document.forms[0].approver1.value="";
		document.forms[0].parllelAppr11.value="";
		document.forms[0].parllelAppr12.value="";
 }else if(priority==2)
 {
		document.forms[0].role2.value="";
		document.forms[0].approver2.value="";
		document.forms[0].parllelAppr21.value="";
		document.forms[0].parllelAppr22.value="";
 }else if(priority==3)
 {
		document.forms[0].role3.value="";
		document.forms[0].approver3.value="";
		document.forms[0].parllelAppr31.value="";
		document.forms[0].parllelAppr32.value="";
 }else if(priority==4)
 {
		document.forms[0].role4.value="";
		document.forms[0].approver4.value="";
		document.forms[0].parllelAppr41.value="";
		document.forms[0].parllelAppr42.value="";
 }else if(priority==5)
 {
		document.forms[0].role5.value="";
		document.forms[0].approver5.value="";
		document.forms[0].parllelAppr51.value="";
		document.forms[0].parllelAppr52.value="";
 }else if(priority==6)
 {
		document.forms[0].role6.value="";
		document.forms[0].approver6.value="";
		document.forms[0].parllelAppr61.value="";
		document.forms[0].parllelAppr62.value="";
 }
}	
</script>	
</head>
<body>
<html:form action="/materialApprover.do" enctype="multipart/form-data">
			<div align="center">
						<logic:present name="materialApproverForm" property="message">
						<font color="red">
							<bean:write name="materialApproverForm" property="message" />
						</font>
					</logic:present>
					</div>
<table class="bordered" >    	
<tr>
<th colspan="8" align="center"><center>Material Master Approvers</center>
</th>
</tr>
<tr>
<th>Location <font color="red">*</font></th>
<td align="left">
	<html:select name="materialApproverForm" property="locationId">
		<html:option value="">--Select--</html:option>
		<html:options name="materialApproverForm" property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
</td>

<th>Material Type <font color="red">*</font></th>
<td>
<html:select property="materialType" name="materialApproverForm"  >
	<html:option value="">Select</html:option>
	<html:options name="materialApproverForm" property="materTypeIDList" 
			labelProperty="materialTypeIdValueList"/>
	<html:option value="Service Master">Service Master</html:option>
	<html:option value="Code Extention">Code Extention</html:option>
	<html:option value="Customer Master">Customer Master</html:option>
	<html:option value="Vendor Master">Vendor Master</html:option>
	<html:options name="materialApproverForm"  property="categortShortlist" labelProperty="categorylist"/>
</html:select>
</td>
<th>Mat.Group </th>
<td align="left">
	<html:select name="materialApproverForm" property="materialGroupId">
		<html:option value="">--Select--</html:option>
		<html:options name="materialApproverForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
	</html:select>
</td>
<th>Group </th>
<td align="left">
	<html:select name="materialApproverForm" property="customerGroupId">
		<html:option value="">--Select--</html:option>
		<html:option value="Export">Export</html:option>
	<html:option value="Domestic">Domestic</html:option>
	<html:option value="Local">Local</html:option>
	<html:option value="Import">Import</html:option>
	<html:option value="V">Validation</html:option>
	</html:select>
</td>
</tr>
&nbsp;
&nbsp;
&nbsp;
</br>
<tr>
<th>Sub Category </th>
<td align="left">
	<html:select name="materialApproverForm" property="subCategoryId">
	    <html:option value="">--Select--</html:option>
		<html:options name="materialApproverForm"  property="subcatList" />
		</html:select>
</td>
</tr>
</table>
<br/>
<br/>
<br/>

<table class="sortable bordered" > 
<tr>
<th>Priority</th><th>Role</th><th>Approver</th><th>Parallel Approver 1</th><th>Parallel Approver 2</th>
</tr>
<tr>
	<td><html:text property="priority1" value="1" readonly="true"></html:text></td>
	<td><html:select property="role1"  >
	<html:option value="">Select</html:option>
	<html:option value="user">User</html:option>
	<html:option value="Accounts">Accounts</html:option>
	<html:option value="Creator">Creator</html:option>
	</html:select></td>
	<td><html:text property="approver1" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('approver1')"/></a></td>
	<td><html:text property="parllelAppr11" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr11')"/></a>
	</td>
	<td><html:text property="parllelAppr12" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr12')"/></a>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('1')"/></a>
	</td>
</tr>
<tr>
	<td><html:text property="priority2" value="2" readonly="true"></html:text></td>
	<td><html:select property="role2"  >
	<html:option value="">Select</html:option>
	<html:option value="user">User</html:option>
	<html:option value="Accounts">Accounts</html:option>
	<html:option value="Creator">Creator</html:option>
	</html:select></td>
	<td><html:text property="approver2"  ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('approver2')"/></a>
	</td>
	<td><html:text property="parllelAppr21" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr21')"/></a>
	</td>
	<td><html:text property="parllelAppr22" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr21')"/></a>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('2')"/></a>
	</td>
</tr>
<tr>
	<td><html:text property="priority3" value="3" readonly="true"></html:text></td>
	<td><html:select property="role3"  >
	<html:option value="">Select</html:option>
	<html:option value="user">User</html:option>
	<html:option value="Accounts">Accounts</html:option>
	<html:option value="Creator">Creator</html:option>
	</html:select></td>
	<td><html:text property="approver3"  ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('approver3')"/></a>
	</td>
	<td><html:text property="parllelAppr31" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr31')"/></a>
	</td>
	<td><html:text property="parllelAppr32" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr31')"/></a>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('3')"/></a>
	</td>
</tr>
<tr>
	<td><html:text property="priority4" value="4" readonly="true"></html:text></td>
	<td><html:select property="role4"  >
	<html:option value="">Select</html:option>
	<html:option value="user">User</html:option>
	<html:option value="Accounts">Accounts</html:option>
	<html:option value="Creator">Creator</html:option>
	</html:select></td>
	<td><html:text property="approver4"  ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('approver4')"/></a>
	</td>
	<td><html:text property="parllelAppr41" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr41')"/></a>
	</td>
	<td><html:text property="parllelAppr42" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr42')"/></a>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('4')"/></a>
	</td>
</tr>
<tr>
	<td><html:text property="priority5" value="5" readonly="true"></html:text></td>
	<td><html:select property="role5"  >
	<html:option value="">Select</html:option>
	<html:option value="user">User</html:option>
	<html:option value="Accounts">Accounts</html:option>
	<html:option value="Creator">Creator</html:option>
	</html:select></td>
	<td><html:text property="approver5"  readonly="true"></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('approver5')"/></a>
	</td>
	<td><html:text property="parllelAppr51" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr51')"/></a>
	</td>
	<td><html:text property="parllelAppr52" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr52')"/></a>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('5')"/></a>
	</td>
</tr>
<tr>
	<td><html:text property="priority6" value="6" readonly="true"></html:text></td>
	<td><html:select property="role6"  >
	<html:option value="">Select</html:option>
	<html:option value="user">User</html:option>
	<html:option value="Accounts">Accounts</html:option>
	<html:option value="Creator">Creator</html:option>
	</html:select></td>
	<td><html:text property="approver6"  ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle"  onclick="searchEmployeeId('approver6')"/></a>
	</td>
	<td><html:text property="parllelAppr61" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr61')"/></a>
	</td>
	<td><html:text property="parllelAppr62" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr62')"/></a>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('6')"/></a>
	</td>
</tr>
<tr>
<td colspan="5" align="center">
<logic:notEmpty name="saveButton">
<div align="center">
<html:button property="method" value="Save" onclick="onSave()" styleClass="rounded" style="width:100px;"/>
<html:reset value="Clear" styleClass="rounded" style="width:100px;"/>
<html:button property="method" value="Close" onclick="goBack()" styleClass="rounded" style="width:100px;"/>
</div>
</logic:notEmpty>
<logic:notEmpty name="modifyButton" >
<div align="center">
<html:button property="method" value="Modify" onclick="onModify()" styleClass="rounded" style="width:100px;"/>
<html:button property="method" value="Close" onclick="goBack()" styleClass="rounded" style="width:100px;"/>

</logic:notEmpty>
</td>
</tr>
</table>
</html:form>	
</body></html>				