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
<title>Report</title>

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />


<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>



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



function save(){
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
	     
	    if(document.forms[0].storageLocation.value=="")
	    {
	      alert("Please Enter storageLocation");
	      document.forms[0].storageLocation.focus();
	      return false;
	    }
	     if(document.forms[0].storageDescription.value=="")
	    {
	      alert("Please Enter storageDescription");
	      document.forms[0].storageDescription.focus();
	      return false;
	    }
	    if(document.forms[0].detailedServiceDescription.value=="")
	    {
	      alert("Please Enter detailedServiceDescription");
	      document.forms[0].detailedServiceDescription.focus();
	      return false;
	    }
	     if(document.forms[0].uom.value=="")
	    {
	      alert("Please Enter uom");
	      document.forms[0].uom.focus();
	      return false;
	    }
	     if(document.forms[0].purchaseGroup.value=="")
	    {
	      alert("Please Enter purchaseGroup");
	      document.forms[0].purchaseGroup.focus();
	      return false;
	    }
	  
	    
	    if(document.forms[0].serviceCatagory.value=="")
	    {
	      alert("Please Enter serviceCatagory");
	      document.forms[0].serviceCatagory.focus();
	      return false;
	    }
	     if(document.forms[0].serviceDescription.value=="")
	    {
	      alert("Please Enter serviceDescription");
	      document.forms[0].serviceDescription.focus();
	      return false;
	    }
	     if(document.forms[0].serviceCatagory.value=="")
	    {
	      alert("Please Enter serviceCatagory");
	      document.forms[0].serviceCatagory.focus();
	      return false;
	    }
	     if(document.forms[0].serviceGroup.value=="")
	    {
	      alert("Please Enter serviceGroup");
	      document.forms[0].serviceGroup.focus();
	      return false;
	    }
	     if(document.forms[0].e_m_name.value=="")
	    {
	      alert("Please Enter e_m_name");
	      document.forms[0].e_m_name.focus();
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
	     if(document.forms[0].purpose.value=="")
	    {
	      alert("Please Enter purpose");
	      document.forms[0].purpose.focus();
	      return false;
	    }
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


//-->
</script>
<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
.style2 {	color: #df1e1c; font: bold 11px "Arial", Verdana, Arial, Helvetica, sans-serif;	font-size: 12px;
}
</style>
<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>


<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
</style>

</head>

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg',
'images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg'),subMenuClicked('<bean:write name='vendorMasterRequestForm' property='linkName'/>')">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >

  <tr>
   			<%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			<% 
  			  UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  
  
    <td align="center" valign="top" style="background-image:url(images/bg1.jpg); background-repeat:repeat-x;  height:180px; width:100%"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
     
    <jsp:include page="/jsp/template/header1.jsp"/>
    
    
      <tr>
        <td colspan="3" align="center" valign="top" style="padding-top:3px">
        <div class="middelpart2">
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="24%" align="left" valign="top">
    <div class="left-blocks">
      <!--------CONTENT LEFT -------------------->
     
      <jsp:include page="/jsp/template/subMenu1.jsp"/>
     
      <!--------CALENDER ENDS -------------------->
    </div></td>
    <td colspan="3" align="left" valign="top">
    <div class="middel-blocks">
    <div align="center">
					<logic:present name="serviceMasterRequestForm" property="message">
						<font color="red">
							<bean:write name="serviceMasterRequestForm" property="message"/>
						</font>
					</logic:present>
					
					<br />
					<html:form action="serviceMasterRequest.do" enctype="multipart/form-data">
					
				
					
					
						<table align="center" border="0" cellpadding="4" cellspacing="0" id="mytable1">
				        	
				           <tr>
       							 <th colspan="2">Material</th>
      					   </tr>
				          <tr>
                  <td colspan="2" scope="row"><div align="left" class="style2"><img src="images/star.gif" width="8" height="8" />Star Marks are Mandatory fields<img src="images/star.gif" width="8" height="8" /></div>
            </td>
                </tr>
				     		<tr>
	  <th colspan="2"></th>
   </tr>
				     	  	<tr>
							  
							  <th width="274" class="specalt" scope="row">Request Date <img src="images/star.gif" width="8" height="8" /></th>
							  	
								<td align="left">
									<label><html:text name="reportRequestForm" property="m_rquestDate" styleClass="text_field" readonly="true" maxlength="50"/></label>
								</td>
									</tr>		
							<tr>
							  
							  <th width="274" class="specalt" scope="row">Material Type<img src="images/star.gif" width="8" height="8" /></th>
							  	
								<td align="left">
									<label><html:text name="reportRequestForm" property="m_materialType" styleClass="text_field" readonly="false" maxlength="50"/></label>
								</td>
									</tr>
									<tr>
									 <th width="274" class="specalt" scope="row">Material Description<img src="images/star.gif" width="8" height="8" /></th>
									<br /></th>
									<td align="left">
										<html:text name="reportRequestForm" property="m_materialDescription" styleClass="text_field"/>
									<br /></td>
									</tr>
								
							<tr>
							 <th width="274" class="specalt" scope="row">Material Group<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								
								<html:textarea name="reportRequestForm" property="m_materialGroup"></html:textarea>
								<br /></td>
							</tr>	
							
							
							<tr>
							
								 <th width="274" class="specalt" scope="row">Plant<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								<html:text name="reportRequestForm" property="m_plant"></html:text>
								<br /></td>
							</tr>
							
							
								
								<tr> <th width="274" class="specalt" scope="row">Status<img src="images/star.gif" width="8" height="8" /></th>
								<td ><html:text  name="reportRequestForm" property="m_status" styleClass="text_field" style="width:300px;" maxlength="40"></html:text>
								<br /></td>
								</tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">Requested By<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text name="reportRequestForm" property="m_requestedBy" styleClass="text_field" style="width:300px;" maxlength="40"/>
								<br /></td></tr>
								
								
								
								
								
								
								
								<tr> <th width="274" class="specalt" scope="row">Approve Date<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text name="reportRequestForm" property="m_approveDate" styleClass="text_field" style="width:300px;" maxlength="40"></html:text>
								<br /></td></tr>
								
							
								<th colspan="2">Service</th>
								<tr> <th width="274" class="specalt" scope="row">Request Date<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:text name="reportRequestForm" property="s_requestDate" styleClass="text_field" style="width:300px;" maxlength="40"></html:text>
								
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Service Catagory</th>
								
								
				
								<td align="left">
								
									<html:text  name="reportRequestForm" property="s_serviceCatagory"></html:text>
								
								<br /></td></tr>
								
								
								
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Service Description</th>
								<td align="left"><html:text name="reportRequestForm" property="s_serviceDescription" styleClass="text_field" maxlength="16"></html:text>
								<br /></td></tr>
								
							
								<tr> <th width="274" class="specalt" scope="row">Service Group</th>
								<td align="left"><html:text name="reportRequestForm" property="s_serviceGroup" styleClass="text_field"  maxlength="16"></html:text>
								<br /></td></tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Plant</th>
								<td align="left"><html:text name="reportRequestForm" property="s_plant" styleClass="text_field" style="width:300px;"  maxlength="40"></html:text>
								<br /></td></tr>
								
								<tr> <th width="274" class="specalt" scope="row">Status<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								
								<html:text name="reportRequestForm" property="s_status"  styleClass="text_field"></html:text>
								
								<br />
								</td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Requested By<img src="images/star.gif" width="8" height="8" /></th>
								 								<td align="left">
								
								<html:text  name="reportRequestForm"  property="s_requestedBy" styleClass="text_field"></html:text>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Approve Date</th>
								<td>
								<html:text  name="reportRequestForm"  property="s_approveDate"></html:text>
								
								<br /></td>
								</tr>
								 <th colspan="2">Customer</th>
								<tr>
								 <th width="274" class="specalt" scope="row">Request Date</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_requestDate"></html:text>
								
								<br /></td>
								</tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Name</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_name"></html:text>
								
								<br /></td>
								</tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">location</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_location"></html:text>
								
								<br /></td>
								</tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Country</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_country"></html:text>
								
								<br /></td>
								</tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Customer Group</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_customerGroup"></html:text>
								
								<br /></td>
								</tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Status</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_status"></html:text>
								
								<br /></td>
								</tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Requested By</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_requestedBy"></html:text>
								
								<br /></td>
								</tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Approve Date</th>
								<td>
								<html:text  name="reportRequestForm"  property="c_approveDate"></html:text>
								
								<br /></td>
								</tr>
							
								<th colspan="2">Vendor</th>
							
								<tr>
								 <th width="274" class="specalt" scope="row">Request Date</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_requestDate"></html:text>
								
								<br /></td>
								</tr>
							
								<tr>
								 <th width="274" class="specalt" scope="row">Name</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_name"></html:text>
								
								<br /></td>
								</tr>		
								
								<tr>
								 <th width="274" class="specalt" scope="row">Location</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_location"></html:text>
								
								<br /></td>
								</tr>		
								
								<tr>
								 <th width="274" class="specalt" scope="row">Country</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_country"></html:text>
								
								<br /></td>
								</tr>					
											
								<tr>
								 <th width="274" class="specalt" scope="row">Vendor Type</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_vendorType"></html:text>
								
								<br /></td>
								</tr>				
								
								<tr>
								 <th width="274" class="specalt" scope="row">Status</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_status"></html:text>
								
								<br /></td>
								</tr>		
								
								<tr>
								 <th width="274" class="specalt" scope="row">Requested By</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_requestedBy"></html:text>
								
								<br /></td>
								</tr>	
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Approve Date</th>
								<td>
								<html:text  name="reportRequestForm"  property="v_approveDate"></html:text>
								
								<br /></td>
								</tr>
													
							
								
								<logic:notEmpty name="documentDetails">
						
						<tr>
							<th colspan="2">
							<div align="center">
								<b><font color="white">Uploaded&nbsp;Documents&nbsp;</font></b></div>
						</th>
						</tr>
						
						<tr class="tablerowdark">
					<th colspan="1">
							<font color="white">Sel.</font>
						</th>
						<th colspan="1"><font color="white">Document</font></th>
						
						</tr>
						<logic:iterate id="abc" name="documentDetails">
							<TR>
								<td align="left">
									<html:checkbox property="documentCheck" name="abc"
										value="${abc.sfile}" styleId="${abc.sfile}" onclick="addInput(this.value)" style="width :10px;"/>
								<br /></TD>
								<td colspan="2">
									<a href="#" onclick="openResume('${abc.sfile},${abc.sfile}')">  <bean:write name="abc" property="sfile"/>
									</a>
								<br /></td>
							</tr>
	
						</logic:iterate>
						
						<TR>
							<td align="center" colspan="4">
								<input type="button" value="Delete"   onclick="deleteDocumentsSelected()"/>
							<br /></td>
						</TR>
	
				</logic:notEmpty>
								
						<!--  	
								<tr><td align="left"><span class="text">Attachments</span><span class="red_man"><img src="images/star.gif" width="8" height="8" /></span><br /></td>
								<td align="left">
								<html:file property="serviceAttachment" styleClass="text_field" style="width:400px;"/>
								<br />
								</td>
								</tr>
								<tr>
								<td  colspan="2" align="center"><html:button property="method" value="Upload Document"   onclick="onUploadDocuments()" />
									<br /></td>
								
								</tr>
								
								-->
								
								
								<tr>
									<td  colspan="4" align="center"><html:button property="method"  value="Submit" onclick="save()" />
									</td>
								</tr>

						</table>
				
							</html:form>
</div>
</td>
      </tr>
      </table></td></tr>
   
</table>
</body>
</html>
						