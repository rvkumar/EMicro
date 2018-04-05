<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Raw Material </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	
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

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
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

function changeStatus(elem){
if(document.forms[0].sacCode.value=="")
	    {
	      alert("Please Enter SAC code");
	      document.forms[0].sacCode.focus();
	      return false;
	    }
	     var sacCode = document.forms[0].sacCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(sacCode)) {
             alert("SAC code its should be Integer ");
                document.forms[0].sacCode.focus();
            return false;
        }
       
	   

var elemValue = elem.value;
if(elemValue=="Reject")
	{

	if(document.forms[0].comments.value==""){
	  alert("Please Add Some Comments");
	       document.forms[0].comments.focus();
	         return false;
	  }
	
	}
  
	var elemValue = elem.value;
	if(elemValue=='Created')
	{
	elemValue='Approve';
	
	 
	    
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
	    var st = document.forms[0].serviceDescription.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].serviceDescription.value=st;
	     
	    if(document.forms[0].detailedServiceDescription.value=="")
	    {
	      alert("Please Enter Detailed Service Description");
	      document.forms[0].detailedServiceDescription.focus();
	      return false;
	    }
	    var st = document.forms[0].detailedServiceDescription.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].detailedServiceDescription.value=st;
	
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
	     var st = document.forms[0].justification.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].justification.value=st;
	
	     if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Enter valuationClass");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	     
	  
	    
	      if(document.forms[0].e_m_name.value!="")
	    {
	    var st = document.forms[0].e_m_name.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].e_m_name.value=st;
	    }
	    
	     if(document.forms[0].app_amount.value!=""){
	     var app_amount = document.forms[0].app_amount.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(app_amount)) {
             alert("Approximate Value  Should be Integer or Float!");
             document.forms[0].app_amount.focus();
            return false;
        }
        }
	    
	  
	    
	    
	    if(document.forms[0].whereUsed.value!="")
	    {
	      var st = document.forms[0].whereUsed.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].whereUsed.value=st;
	    }
	
		   if(document.forms[0].sapCodeExists.checked==false && document.forms[0].sapCodeExistsNo.checked==false )
	    {
	      alert("Please Select  SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    } 
	    
	       if(document.forms[0].sapCodeExists.checked==true && document.forms[0].sapCodeExistsNo.checked==true )
	    {
	      alert("Please Choose Only One Option In SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    }
	    
	     	   if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Enter SAP Code No.");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    } 
	  var st = document.forms[0].sapCodeNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].sapCodeNo.value=st; 
	
	
	}
	
	  var st = document.forms[0].e_m_name.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		document.forms[0].e_m_name.value=st;
		    
		    
		    
		     var app_amount = document.forms[0].app_amount.value;
	        var pattern = /^\d+(\.\d+)?$/
	        if (!pattern.test(app_amount)) {
	             alert("Approximate Value  Should be Integer or Float!");
	             document.forms[0].app_amount.focus();
	            return false;
	        }
	var reqId = document.forms[0].requestNo.value;
		var reqType = "";
	var matGroup="";
	var location="";
	var url="approvals.do?method=statusChangeForServiceRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
    document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back()
  }	  
   function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;
	
	document.forms[0].action=url;
	document.forms[0].submit();


} 
</script>
<body style="text-transform: uppercase;">
	<html:form action="/approvals.do" enctype="multipart/form-data">
	<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>
	<table class="bordered">
		<tr>
       		<th colspan="4" ><center><big>Service Master Form</big></center></th>
      	</tr>

		<tr>
			<td>Request Number <font color="red">*</font></td>
			<td><html:text name="approvalsForm" property="requestNo" styleClass="content" readonly="true" />
				<html:hidden property="typeDetails"/>
			</td>
			
		</tr>

		<tr>
			<td>Location <font color="red">*</font></td>
			<td ><html:select name="approvalsForm" property="plantCode" styleClass="content" disabled="true">
				<html:option value="">--Select--</html:option>
				       <html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList" />
				</html:select>
			</td>
			<td>Request Date <font color="red">*</font></td>
					<td align="left">
						<html:text property="requestDate" styleId="requestDate" readonly="true" />
					</td>
		</tr>
								
		<tr>
			<td>Service Description <font color="red">*</font></td>
			<td>
			<html:text property="serviceDescription" maxlength="40" style="width:400px;text-transform:uppercase" ></html:text>
				
			</td>
			<td>SAC Code<font color="red">*</font></td>
					<td>
						<html:text property="sacCode" maxlength="8" size="8"> </html:text>
					</td>
			</tr>
			<tr>
			
			<td>Detailed Service description <font color="red">*</font></td>
			<td colspan="3">
				<html:textarea name="approvalsForm" property="detailedServiceDescription" style="width:400px;text-transform:uppercase"  styleClass="content" rows="3" cols="80" ></html:textarea>
			</td>
		</tr>

		<tr>
			<td>U O M <font color="red">*</font></td>
			<td><html:select  name="approvalsForm" property="uom" styleClass="content" >
					<html:option value="">--Select--</html:option>
			        <html:options name="approvalsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
				</html:select>
			</td>
			<td>Purchase Group <font color="red">*</font></td>
			<td><html:select name="approvalsForm" property="purchaseGroup" styleClass="content" >
					<html:option value="">--Select--</html:option>
			        <html:options property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"></html:options>
				</html:select>
			</td>
		</tr>

		<tr>
			<td>Service Category <font color="red">*</font></td>
			<td><html:select name="approvalsForm" property="serviceCatagory" styleClass="content" onchange="getValuationClass()">
					<html:option value="">--Select--</html:option>
					<html:option value="ZITA">ZITA- AMC-IT</html:option>
					<html:option value="ZAMC">ZAMC- Annual maintainence</html:option>
					<html:option value="ZCLB">ZCLB- Calibration</html:option>
					<html:option value="ZCIV">ZCIV- Civil works</html:option>
					<html:option value="ZMNT">ZMNT- Maintainence</html:option>
					<html:option value="ZITM">ZITM-MAINAINENCE-IT</html:option>
					<html:option value="ZMKT">ZMKT- Marketing</html:option>
					<html:option value="ZTST">ZTST- Testing&Analysis</html:option>
					<html:option value="ZTRC">ZTRC- Training&Recruitment</html:option>
					
				</html:select>
			</td>
			<td>Service Group<font color="red">*</font></td>
			<td>
			<html:select  name="approvalsForm" property="serviceGroup" styleClass="content" >
					<html:option value="">--Select--</html:option>
			        <html:options name="approvalsForm" property="serviceGroupID" labelProperty="serviceGroupValues"/>
				</html:select>
			</td>
		</tr>

		<tr>
			<td>Equipment/Machine Name</td>
			<td>
				<html:text name="approvalsForm" property="e_m_name" styleClass="content" maxlength="16" style="width:300px;" ></html:text>
			</td>
			<td>Approximate Value </td>
			<td>
				<html:text name="approvalsForm" property="app_amount" styleClass="content"  maxlength="16" style="width:400px;text-transform:uppercase"></html:text>
			</td>
		</tr>

		<!--<tr>
			<td>Where Used</td>
			<td>
				<html:text name="approvalsForm" property="whereUsed" styleClass="content" maxlength="40" size="45"></html:text>
			</td>
		
			<td>Purpose <font color="red">*</font></td>
			<td>
				<html:text name="approvalsForm" property="purpose"  styleClass="content"></html:text>
			</td>
			
		</tr>
			--><html:hidden property="whereUsed"/>
			<html:hidden property="purpose"/>

		<tr>
			<td>Justification <font color="red">*</font></td>
			<td colspan="3">
				<html:textarea  name="approvalsForm"  property="justification" styleClass="content" rows="3" cols="80" style="width:400px;text-transform:uppercase"></html:textarea>
			</td>
		</tr>
								
		<tr>
			<td>Valuation Class </td>
			<td colspan="3"><html:select  name="approvalsForm"  property="valuationClass" styleClass="text_field">
					<html:option value="">--Select--</html:option>
			        <html:options name="approvalsForm" property="valuationClassID" labelProperty="valuationClassName"/>
				</html:select>
			</td>
		</tr>

		<logic:notEmpty name="documentDetails">
			<tr>
				<th colspan="4"><big>Uploaded Documents</big></th>
			</tr>
				
			<logic:iterate id="abc" name="documentDetails">
				<tr>				
					
					<td colspan="4">
						<a href="/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/${abc.sfile }" > <bean:write name="abc" property="sfile"/></a>
					</td>
				</tr>
			</logic:iterate>
						
			
		</logic:notEmpty>
		<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>	
			</logic:notEmpty>
	<tr>			
	<th colspan="4">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<font color="red">*</font></td>
				<td>
				
						<html:checkbox property="sapCodeExists"/>
						<span class="text">Yes</span>
						&nbsp;&nbsp;&nbsp;
				<html:checkbox property="sapCodeExistsNo"/>
						<span class="text">No</span>
				
				
			
			<td >SAP Code No<font color="red">*</font></td>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;" maxlength="18"></html:text></td>
			
			</tr>
			
			<tr>	
			<td >SAP Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate"  readonly="true" styleClass="text_field"/></td>
		
				<td >SAP Created By<font color="red">*</font></td>
				<td><html:text property="sapCreatedBy" styleClass="text_field"  maxlength="12" readonly="true"></html:text>
				</td>
			</tr>		
		<tr>
		<td>
		Comments</td>
		<td colspan="3">
<html:textarea property="comments" style="width:100%;"></html:textarea>		
		
		</td>
		</tr>
	
		<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Created" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<!--<input type="button" class="rounded" value="Close" onclick="goBack()"  />
			
			--><input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  />
			</td>
			
			</tr>	
		</table>
	
							
	<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="userRole"/>


	<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td>
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;
		</html:form>
</body>
