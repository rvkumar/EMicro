<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>service Master</title>


<style>
	 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>


<script type="text/javascript">
<!--

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
   var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y ",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }


function onUploadDocuments(){
		
		if(document.forms[0].serviceAttachment.value=="")
	    {
	      alert("Please Select File To Upload");
	      document.forms[0].serviceAttachment.focus();
	      return false;
	    }
	
	
	var url="serviceMasterRequest.do?method=uploadFile";
	document.forms[0].action=url;
	document.forms[0].submit();
}



function savedata(){
		var splChars = "'";
       if(document.forms[0].r_no.value=="")
	    {
	      alert("Please Enter Request No");
	      document.forms[0].requestNo.focus();
	      return false;
	    }
	    
	    if(document.forms[0].plantCode.value=="")
	    {
	      alert("Please Enter PlantCode ");
	      document.forms[0].PlantCode.focus();
	      return false;
	    }
	     if(document.forms[0].serviceDescription.value=="")
	    {
	      alert("Please Enter Service Description");
	      document.forms[0].serviceDescription.focus();
	      return false;
	    }
	    var serviceDescription=document.forms[0].serviceDescription.value;
	    for (var i = 0; i < serviceDescription.length; i++) {
    if (splChars.indexOf(serviceDescription.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in Service Description!"); 
 return false;
}
}
	     
	    if(document.forms[0].detailedServiceDescription.value=="")
	    {
	      alert("Please Enter Detailed Service Description");
	      document.forms[0].detailedServiceDescription.focus();
	      return false;
	    }
	    var detailedServiceDescription=document.forms[0].detailedServiceDescription.value;
	    for (var i = 0; i < detailedServiceDescription.length; i++) {
    if (splChars.indexOf(detailedServiceDescription.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in Detailed Service Description!"); 
 return false;
}
}
	     if(document.forms[0].uom.value=="")
	    {
	      alert("Please Enter uom");
	      document.forms[0].uom.focus();
	      return false;
	    }
	     if(document.forms[0].purchaseGroup.value=="")
	    {
	      alert("Please Enter Purchase Group");
	      document.forms[0].purchaseGroup.focus();
	      return false;
	    }
	  
	    
	    if(document.forms[0].serviceCatagory.value=="")
	    {
	      alert("Please Enter Service Catagory");
	      document.forms[0].serviceCatagory.focus();
	      return false;
	    }
	     if(document.forms[0].serviceGroup.value=="")
	    {
	    
	    alert("Please Enter Service Group");
	      document.forms[0].serviceGroup.focus();
	      return false;
}
	     /*if(document.forms[0].serviceGroup.value=="")
	    {
	      alert("Please Enter service Group");
	      document.forms[0].serviceGroup.focus();
	      return false;
	    }
	    
	    if(document.forms[0].app_amount.value=="")
	    {
	      alert("Please Enter app_amount");
	      document.forms[0].app_amount.focus();
	      return false;
	    }
	     if(document.forms[0].whereUsed.value=="")
	    {
	      alert("Please Enter whereUsed");
	      document.forms[0].whereUsed.focus();
	      return false;
	    }
	    */
	    


	     if(document.forms[0].justification.value=="")
	    {
	      alert("Please Enter justification");
	      document.forms[0].justification.focus();
	      return false;
	    }
	     if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Enter valuationClass");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	     
	  
	    
	      if(document.forms[0].e_m_name.value!="")
	    {
	      var e_m_name=document.forms[0].e_m_name.value;
	    for (var i = 0; i < e_m_name.length; i++) {
    if (splChars.indexOf(e_m_name.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in Equipment/Machine Name!"); 
     document.forms[0].e_m_name.focus();
 return false;
}
}
	    }
	     if(document.forms[0].app_amount.value!=""){
	     var app_amount = document.forms[0].app_amount.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(app_amount)) {
             alert("Approximate Value  Should be Integer or Float!");
            return false;
        }
        }
	    
	  
	    
	    
	    if(document.forms[0].whereUsed.value!="")
	    {
	      var whereUsed=document.forms[0].whereUsed.value;
	    for (var i = 0; i < whereUsed.length; i++) {
    if (splChars.indexOf(whereUsed.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in Where Used!"); 
     document.forms[0].whereUsed.focus();
 return false;
}
}
	    }
	   
	  
	    

   var url="serviceMasterRequest.do?method=saveServiceMaster";
	document.forms[0].action=url;
	document.forms[0].submit();



}



function deleteDocumentsSelected()
		{
			var documentChecked=0;
			var documentLength=document.forms[0].documentCheck.length;
			var documentLength1=document.forms[0].documentCheck.checked;
			
			if(documentLength1==true && documentLength==undefined)
			{
			   var agree = confirm('Are You Sure To  Delete Selected Documents');
    		    if(agree)
      		    {
					var URL="serviceMasterRequest.do?method=deleteFile";
					document.forms[0].action=URL;
	 				document.forms[0].submit();
 			    }
 			    else
 			    {
 				  return false;
 			    }
		    }
		    else
		    {
			for(i=0;i<documentLength;i++)
			{
				if(document.forms[0].documentCheck[i].checked==true)
				{
					documentChecked=documentChecked+1;
				}
			}
			
			if(documentChecked==0)
			{
				alert('Select Atleast One Record To Delete Selected Documents');
				return false;
			}
			else
			{
			}
			
			var agree = confirm('Are You Sure To  Delete Selected Documents');
        		if(agree)
        		{
				var URL="serviceMasterRequest.do?method=deleteFile";
				document.forms[0].action=URL;
 				document.forms[0].submit();
 			    }
 			    else
 			    {
 				  return false;
 			    }
		}
  }
  
  function subMenuClicked(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		document.forms[0].divStatus.value='none';
		}
	else{
		disp.style.display=''; 
		document.forms[0].divStatus.value='';
		}
  }



function displayStates(){
		
		var URL="vendorMasterRequest.do?method=displayState";
		document.forms[0].action=URL;
 		document.forms[0].submit();
		
}
function close1()
   {
               var URL="serviceMasterRequest.do?method=displayList";
				document.forms[0].action=URL;
 				document.forms[0].submit();


    }
function getValuationClass(){

  var URL="serviceMasterRequest.do?method=getValuationClass";
				document.forms[0].action=URL;
 				document.forms[0].submit();

}

//-->
</script>

</head>

<body style="text-transform:uppercase;">
	<%
		String menuIcon=(String)request.getAttribute("MenuIcon");
			if(menuIcon==null){
			menuIcon="";
			}
	%>
	<% 
		UserInfo user=(UserInfo)session.getAttribute("user");
	%>

<div class="middel-blocks">

	<html:form action="serviceMasterRequest.do" enctype="multipart/form-data">
			
	   <logic:iterate id="serviceMasterRequestForm" name="serdetails">
	<div id="materialTable" style="visibility: visible;">
			<table class="bordered">
			<tr>
					<th colspan="8" style="text-align: center;"><big>Service Request Form</big></th>
				</tr>
				<tr>
					<th colspan="8"><big>Basic Details Of Material</big></th>
   				</tr>
			<tr>
		<tr>
			<td>Request Number <font color="red">*</font></td>
			<td colspan="3">
			<bean:write name="serviceMasterRequestForm" property="r_no"/>

			</td>
			
		</tr>

		<tr>
			<td>Location <font color="red">*</font></td>
			<td >	
			<bean:write name="serviceMasterRequestForm" property="plantCode"/>
			</td>
			<td>Request Date <font color="red">*</font></td>
					<td align="left">
			<bean:write name="serviceMasterRequestForm" property="requestDate"/>
					</td>
		</tr>
								
		<tr>
			<td>Service Description <font color="red">*</font></td>
			<td colspan="3">
			<bean:write name="serviceMasterRequestForm" property="serviceDescription"/>
				
			</td>
			</tr>
			<tr>
			
			<td>Detailed Service description <font color="red">*</font></td>
			<td colspan="3">
			<bean:write name="serviceMasterRequestForm" property="detailedServiceDescription"/>
			</td>
		</tr>

		<tr>
			<td>U O M <font color="red">*</font></td>
			<td>
					<bean:write name="serviceMasterRequestForm" property="uom"/>

			</td>
			<td>Purchase Group <font color="red">*</font></td>
			<td>				
				<bean:write name="serviceMasterRequestForm" property="purchaseGroup"/>

			</td>
		</tr>

		<tr>
			<td>Service Category <font color="red">*</font></td>
			<td>				
			<bean:write name="serviceMasterRequestForm" property="serviceCatagory"/>

			</td>
			<td>Service Group<font color="red">*</font></td>
			<td>
						<bean:write name="serviceMasterRequestForm" property="serviceGroup"/>

			</td>
		</tr>

		<tr>
			<td>Equipment/Machine Name</td>
			<td>
						<bean:write name="serviceMasterRequestForm" property="e_m_name"/>
			</td>
			<td>Approximate Value </td>
			<td>
						<bean:write name="serviceMasterRequestForm" property="app_amount"/>
			</td>
		</tr>

		<!--<tr>
			<td>Where Used</td>
			<td>
				<html:text name="serviceMasterRequestForm" property="whereUsed" styleClass="content" maxlength="40" size="45"></html:text>
			</td>
		
			<td>Purpose <font color="red">*</font></td>
			<td>
				<html:text name="serviceMasterRequestForm" property="purpose"  styleClass="content"></html:text>
			</td>
			
		</tr>
			--><html:hidden property="whereUsed"/>
			<html:hidden property="purpose"/>

		<tr>
			<td>Justification <font color="red">*</font></td>
			<td colspan="3">
						<bean:write name="serviceMasterRequestForm" property="justification"/>
			</td>
		</tr>
								
		<tr>
			<td>Valuation Class </td>
			<td colspan="3">
			<bean:write name="serviceMasterRequestForm" property="valuationClass"/>

			</td>
		</tr>

		<logic:notEmpty name="documentDetails">
			<tr>
				<th colspan="4"><big>Uploaded Documents</big></th>
			</tr>
				
			<logic:iterate id="abc" name="documentDetails">
				<tr>				
					
					<td colspan="3">
						<a href="/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/${abc.sfile }" > <bean:write name="abc" property="sfile"/></a>
					</td>
				</tr>
			</logic:iterate>
						
			
		</logic:notEmpty>
	
		  <tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="serviceMasterRequestForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="serviceMasterRequestForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="serviceMasterRequestForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="serviceMasterRequestForm" property="sapCreatedBy"/>
			
				</td>
			</tr>
						
			
					<tr>
						<td colspan="6">
							<html:button property="method" value="Close" onclick="history.back(-1)" styleClass="rounded" style="width: 100px" ></html:button>
						</td>
					</tr>
				</table> 
		</div>
		</div>
		</logic:iterate>

<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td></tr>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;		
		
</html:form>

</div>
</body>
</html>
