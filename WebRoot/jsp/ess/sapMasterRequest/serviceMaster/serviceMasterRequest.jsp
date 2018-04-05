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
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>service Master</title>


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



function saveData(param){
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
        
	    
	  
	    
	    
	    if(document.forms[0].whereUsed.value!="")
	    {
	      var st = document.forms[0].whereUsed.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].whereUsed.value=st;
	    }
	   
	   var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	    
 if(param=="Save"){
   var url="serviceMasterRequest.do?method=saveServiceMaster";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
var url="serviceMasterRequest.do?method=saveAndSubmitMaterial";
	document.forms[0].action=url;
	document.forms[0].submit();

}


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
function file(x)
{
alert(x);
}

function displayApprovers(){
	var locationId=document.forms[0].plantCode.value;
	
	if(locationId!="" ){
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("apprListID").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("POST","serviceMasterRequest.do?method=displayApprovers&locationId="+locationId,true);
		xmlhttp.send();
	}
}
//-->
</script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
</head>

<body>
	<%
		String menuIcon=(String)request.getAttribute("MenuIcon");
			if(menuIcon==null){
			menuIcon="";
			}
	%>
	<% 
		UserInfo user=(UserInfo)session.getAttribute("user");
	%>

	

	<html:form action="serviceMasterRequest.do" enctype="multipart/form-data">
			<div id="masterdiv" class="">
	<div style="width: 90%">		
	<table class="bordered" width="90%">
		<tr>
       		<th colspan="4" ><center><big>Service Master Form</big></center></th>
      	</tr>

		<tr>
			<td>Request Number <font color="red">*</font></td>
			<td><html:text name="serviceMasterRequestForm" property="r_no" styleClass="content" readonly="true" style="background-color:#d3d3d3;"/>
				<html:hidden property="typeDetails"/>
			</td>
			
		</tr>

		<tr>
			<td>Location <font color="red">*</font></td>
			<td ><html:select name="serviceMasterRequestForm" property="plantCode" styleClass="content" onchange="displayApprovers()">
				<html:option value="">--Select--</html:option>
				       <html:options name="serviceMasterRequestForm" property="locationIdList" labelProperty="locationLabelList" />
				</html:select>
			</td>
			<td>Request Date <font color="red">*</font></td>
					<td align="left">
						<html:text property="requestDate" styleId="requestDate" readonly="true" style="background-color:#d3d3d3;"/>
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
				<html:textarea name="serviceMasterRequestForm" property="detailedServiceDescription" style="width:400px;text-transform:uppercase"  styleClass="content" rows="3" cols="80" ></html:textarea>
			</td>
		</tr>

		<tr>
			<td>U O M <font color="red">*</font></td>
			<td><html:select  name="serviceMasterRequestForm" property="uom" styleClass="content" >
					<html:option value="">--Select--</html:option>
			        <html:options name="serviceMasterRequestForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
				</html:select>
			</td>
			<td>Purchase Group <font color="red">*</font></td>
			<td><html:select name="serviceMasterRequestForm" property="purchaseGroup" styleClass="content" >
					<html:option value="">--Select--</html:option>
			        <html:options property="puchaseGroupIdList" labelProperty="puchaseGroupIdValues"></html:options>
				</html:select>
			</td>
		</tr>

		<tr>
			<td>Service Category <font color="red">*</font></td>
			<td><html:select name="serviceMasterRequestForm" property="serviceCatagory" styleClass="content" >
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
			<html:select  name="serviceMasterRequestForm" property="serviceGroup" styleClass="content" >
					<html:option value="">--Select--</html:option>
			        <html:options name="serviceMasterRequestForm" property="serviceGroupID" labelProperty="serviceGroupValues"/>
				</html:select>
			</td>
		</tr>

		<tr>
			<td>Equipment/Machine Name</td>
			<td>
				<html:text name="serviceMasterRequestForm" property="e_m_name" styleClass="content" maxlength="16" style="width:300px;width:400px;text-transform:uppercase;"></html:text>
			</td>
			<td>Approximate Value </td>
			<td>
				<html:text name="serviceMasterRequestForm" property="app_amount" styleClass="content"  maxlength="16" style="width:400px;text-transform:uppercase"></html:text>
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
				<html:textarea  name="serviceMasterRequestForm"  property="justification" styleClass="content" rows="3" cols="80" style="width:400px;text-transform:uppercase"></html:textarea>
			</td>
		</tr>
								
		<tr>
			<td>Valuation Class <font color="red">*</font> </td>
			<td colspan="3"><html:select  name="serviceMasterRequestForm"  property="valuationClass" styleClass="text_field">
					<html:option value="">--Select--</html:option>
			        <html:options name="serviceMasterRequestForm" property="valuationClassID" labelProperty="valuationClassName"/>
				</html:select>
			</td>
		</tr>

		<logic:notEmpty name="documentDetails">
			<tr>
				<th colspan="4"><big>Uploaded Documents</big></th>
			</tr>
				
			<logic:iterate id="abc" name="documentDetails">
				<tr>				
					<td><html:checkbox property="documentCheck" name="abc"
							value="${abc.sfile}" styleId="${abc.sfile}" onclick="addInput(this.value)" style="width :10px;"/>
					</td>
					<td colspan="3">
						<a href="/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/${abc.sfile }" > <bean:write name="abc" property="sfile"/></a>
					</td>
				</tr>
			</logic:iterate>
						
			<tr>
				<td colspan="4">
					<input type="button" value="Delete"  onclick="deleteDocumentsSelected()" styleClass="rounded" style="width: 100px"/>
				</td>
			</tr>
		</logic:notEmpty>
	
		<tr>
			<th colspan="4"><big>Attachments </big></th> 
		</tr>

		<tr>
			<td colspan="4">
				<html:file  name="serviceMasterRequestForm" property="serviceAttachment" styleClass="content" style="width:220px;"></html:file>
				&nbsp;&nbsp;
				<html:button property="method" value="Upload" onclick="onUploadDocuments()" styleClass="rounded" style="width: 100px" />
			</td>
		</tr>
								
		<tr>
			<td colspan="4" style="text-align: center;">
				<html:button property="method" value="Save" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px" ></html:button>&nbsp;&nbsp;
				<html:button property="method"  value="Save & Submit" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
					<html:reset value="Reset" styleClass="rounded" style="width: 100px" ></html:reset>&nbsp;&nbsp;
					<html:button property="method" value="Close" onclick="close1()" styleClass="rounded" style="width: 100px;"/>
			</td>
		</tr>

	</table>
	<script type="text/javascript">
		displayApprovers();
		</script>
		<div id="apprListID">
		</div>
	</br>
	<div align="center">
				<logic:present name="serviceMasterRequestForm" property="message">
					<font color="Green" size="3"><b><bean:write name="serviceMasterRequestForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="serviceMasterRequestForm" property="message2">
					<font color="red" size="3"><b><bean:write name="serviceMasterRequestForm" property="message2" /></b></font>
				</logic:present>
				<logic:notEmpty name="serviceMasterRequestForm" property="appStatusMessage" >
				<br/>
				<font color="red" size="3"><b><bean:write name="serviceMasterRequestForm" property="appStatusMessage" /></b></font>
				</logic:notEmpty>
			</div>
	</div>		
	<logic:notEmpty name="serfile">
	 <script>	
	 	file("<bean:write name="serviceMasterRequestForm" property="message2" /> ") 
 	</script>
	</logic:notEmpty></div>
	</html:form>

</body>
</html>
