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


<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<!-- Theme css -->




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
		 if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Select Sap CodeNo");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    }
	    if(document.forms[0].sapCodeExists.value=="")
	    {
	      alert("Please Enter SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    }
	    if(document.forms[0].sapCreationDate.value=="")
	    {
	      alert("Please Enter SAP Creation Date");
	      document.forms[0].sapCreationDate.focus();
	      return false;
	    }
	    if(document.forms[0].sapCreatedBy.value=="")
	    {
	      alert("Please Enter SAP Created By");
	      document.forms[0].sapCreatedBy.focus();
	      return false;
	    }
      
	  
	    

   var url="serviceMasterRequest.do?method=saveSAPCODEData";
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
  
  
					<logic:present name="serviceMasterRequestForm" property="message">
						<font color="red">
							<bean:write name="serviceMasterRequestForm" property="message"/>
						</font>
					</logic:present>
				<html:form action="serviceMasterRequest.do" enctype="multipart/form-data">
					
				<bean:define id="myOptions" name="serviceMasterRequestForm" 
        property="uomLabel" type="java.util.Collection"/>
				
				<bean:define id="pgroup" name="serviceMasterRequestForm" 
        property="plabel" type="java.util.Collection"/>
        
        <bean:define id="plcode" name="serviceMasterRequestForm" 
        property="pcode" type="java.util.Collection"/>
					
					
						<table align="left" border="0" cellpadding="4" cellspacing="0" id="mytable1">
				        	
				           <tr>
       							 <th colspan="2" ><center>Service Master</center></th>
      					   </tr>
				        
				     		
							  
							  <th width="274" class="specalt" scope="row">Request Number <img src="images/star.gif" width="8" height="8" /><br /></th>
							  	
								<td align="left">
									<label><html:text name="serviceMasterRequestForm" property="r_no" styleClass="text_field" readonly="true" maxlength="50"/></label>
								<br /></td>
									</tr>		
							<tr>
							  
							  <th width="274" class="specalt" scope="row">Plant Code<img src="images/star.gif" width="8" height="8" /><br /></th>
							  	
								<td align="left">&nbsp;<html:select name="serviceMasterRequestForm" property="plantCode" styleClass="text_field" disabled="true">
								
								<html:option value="">-----Select-----</html:option>
				                 <html:options collection="plcode"  property="pl_id" labelProperty="pl_all"></html:options>
								
								
								
								</html:select>
								<br /></td>
									</tr>
									
								<tr> <th width="274" class="specalt" scope="row">Service Description<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:textarea name="serviceMasterRequestForm" property="serviceDescription" styleClass="text_field" style="width:400px;" disabled="true"></html:textarea>
								<br /></td></tr>		
								
							
							
							
							<tr>
							
								 <th width="274" class="specalt" scope="row">Detailed Service description<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								<html:textarea name="serviceMasterRequestForm" property="detailedServiceDescription"  styleClass="text_field" style="width:400px;" disabled="true"></html:textarea>
								<br /></td>
							</tr>
							
							
								
								<tr> <th width="274" class="specalt">U.O.M<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:select  name="serviceMasterRequestForm" property="uom" styleClass="text_field" disabled="true">
								<html:option value="">-----Select-----</html:option>
				                <html:options collection="myOptions" property="u_id" labelProperty="u_label" />
								</html:select>
								<br /></td>
								</tr>
								
								
								<tr> <th width="274" class="specalt" scope="row">Purcahse Group<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left"><html:select name="serviceMasterRequestForm" property="purchaseGroup" styleClass="text_field" disabled="true">
								<html:option value="">-----Select-----</html:option>
				                 <html:options collection="pgroup"  property="p_id" labelProperty="p_all"></html:options>
								
								</html:select>
								<br /></td></tr>
								
								
								
																
								<tr> <th width="274" class="specalt" scope="row">Service Catagory<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								<html:select name="serviceMasterRequestForm" property="serviceCatagory" styleClass="text_field" disabled="true">
									<html:option value="">--Select--</html:option>
										<html:option value="ZAMC- Annual maintainence">ZAMC- Annual maintainence</html:option>
										<html:option value="ZCIV- Civil works">ZCIV- Civil works</html:option>
										<html:option value="ZCLB- Calibration">ZCLB- Calibration</html:option>
										<html:option value="ZMKT- Marketing">ZMKT- Marketing</html:option>
										<html:option value="ZMNT- Maintainence">ZMNT- Maintainence</html:option>
										<html:option value="ZTRC- Training&Recruitment">ZTRC- Training&Recruitment</html:option>
										<html:option value="ZTST- Testing&Analysis">ZTST- Testing&Analysis</html:option>
								</html:select>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Service Group</th>
								
								
				
								<td align="left">
								
									<html:text  name="serviceMasterRequestForm" property="serviceGroup" styleClass="text_field" readonly="true"></html:text>
								
								<br /></td></tr>
								
								
								
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Equipment/Machine Name</th>
								<td align="left"><html:text name="serviceMasterRequestForm" property="e_m_name" styleClass="text_field" maxlength="16" readonly="true"></html:text>
								<br /></td></tr>
								
							
								<tr> <th width="274" class="specalt" scope="row">Approximate Value</th>
								<td align="left"><html:text name="serviceMasterRequestForm" property="app_amount" styleClass="text_field"  maxlength="16" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr>
								 <th width="274" class="specalt" scope="row">Where Used</th>
								<td align="left"><html:text name="serviceMasterRequestForm" property="whereUsed" styleClass="text_field" style="width:300px;"  maxlength="40" readonly="true"></html:text>
								<br /></td></tr>
								
								<tr> <th width="274" class="specalt" scope="row">Purpose<img src="images/star.gif" width="8" height="8" /></th>
								<td align="left">
								
								<html:text name="serviceMasterRequestForm" property="purpose"  styleClass="text_field" readonly="true"></html:text>
								
								<br />
								</td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Justification<img src="images/star.gif" width="8" height="8" /></th>
								 								<td align="left">
								
								<html:text  name="serviceMasterRequestForm"  property="justification" styleClass="text_field" readonly="true"></html:text>
								<br /></td></tr>
								
								
								<tr>
								 <th width="274" class="specalt" scope="row">Valuation Class</th>
								<td align="left">
								<html:select  name="serviceMasterRequestForm"  property="valuationClass" styleClass="text_field" disabled="true">
								
								<html:option value="">-----Select-----</html:option>
				                 <html:options name="serviceMasterRequestForm"  property="vlabel"></html:options>
								
								</html:select>
								
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
									<a href="${abc.filepath}" >  <bean:write name="abc" property="sfile"/>
									</a>
								<br /></td>
							</tr>
	
						</logic:iterate>
						
	
				</logic:notEmpty>
				
								<tr>			
	<th colspan="2">Approver Details:</th>
	   </tr>
	<tr> 
		<th width="274" class="specalt" scope="row">Approve Type
			<td align="left">
			
			<html:select name="serviceMasterRequestForm" property="approveType" styleClass="text_field" disabled="true">
				<html:option value="">--Select--</html:option>
					<html:option value="Pending">Pending</html:option>
					<html:option value="Approved">Approved</html:option>
					<html:option value="Cancel">Cancel</html:option>
			</html:select>
			
			<br /></td></tr>
			
		<tr>			
	<th colspan="2">Material Code Details:</th>
	   </tr>
				<th width="274" class="specalt" scope="row">SAP Code No<img src="images/star.gif" width="8" height="8" /></th>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;" maxlength="18"></html:text></td>
			
			</tr>
			<tr>
				<th width="274" class="specalt" scope="row">SAP Code Exists<img src="images/star.gif" width="8" height="8" /></th>
				<td><html:select property="sapCodeExists" styleClass="text_field" >
					<html:option value="">-----Select-----</html:option>
					<html:option value="True">Yes</html:option>
					<html:option value="False">No</html:option>
				</html:select></td>
			</tr>
			<tr>	
			<th width="274" class="specalt" scope="row">SAP Creation Date<img src="images/star.gif" width="8" height="8" /></th>
		
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" readonly="true" styleClass="text_field"/></td>
			
			</tr>
			<tr><th width="274" class="specalt" scope="row">SAP Created By<img src="images/star.gif" width="8" height="8" /></th><td><html:text property="sapCreatedBy" styleClass="text_field" maxlength="12"></html:text>
				</td>
			</tr>
			<tr><th width="274" class="specalt" scope="row">Requested By<img src="images/star.gif" width="8" height="8" /></th><td><html:text property="requestedBy"  styleClass="text_field" style="width:500px;" maxlength="50"></html:text>
				</td>
			</tr>
										
								
								
								
								
								<tr>
									<td  colspan="4" align="center"><html:button property="method"  value="Submit" onclick="savedata()"></html:button>
									<html:reset value="Reset"></html:reset>
									<html:button property="method"  value="close" onclick="close1()" />
									</td>
								</tr>

						</table>
				
							</html:form>
</td>
      </tr>
      </table></td></tr>
   
</table>
</body>
</html>
