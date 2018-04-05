<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <STYLE TYPE="text/css">
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}

</STYLE>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>eMicro :: Contacts </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

	<script type="text/javascript" src="js/sorttable.js"></script>
	<!-- Add jQuery library -->
	<script type="text/javascript" src="jsp/hr/lib/jquery-1.8.2.min.js"></script>

	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="jsp/hr/lib/jquery.mousewheel-3.0.6.pack.js"></script>

	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="jsp/hr/source/jquery.fancybox.js?v=2.1.3"></script>
	<link rel="stylesheet" type="text/css" href="jsp/hr/source/jquery.fancybox.css?v=2.1.2" media="screen" />

	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="jsp/hr/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="jsp/hr/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="jsp/hr/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="jsp/hr/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="jsp/hr/source/helpers/jquery.fancybox-media.js?v=1.0.5"></script>

	<script type="text/javascript">
		function searchContacts()
		{
		var url="contacts.do?method=firstRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		}
		
		function nextRecord()
		{
		var url="contacts.do?method=nextRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		}
		
		function previousRecord()
		{
		
		var url="contacts.do?method=previousRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		
		}
		function firstRecord()
		{
		
		var url="contacts.do?method=firstRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		
		}
		
		function lastRecord()
		{
		
		var url="contacts.do?method=lastRecord";
					document.forms[0].action=url;
					document.forms[0].submit();
		
		}
		function clearContacts()
		{
		document.forms[0].locationId.value="";
		document.forms[0].department.value="";
		document.forms[0].firstName.value="";
		}
		
			function viewMaterial(empNo)
		{
		
	 
		
			var url="contacts.do?method=viewEmployee&empNo="+empNo;
			document.forms[0].action=url;
			document.forms[0].submit();
		
		}
	
	
		
		
	</script>
<link href="../style/gallery.css" rel="stylesheet" type="text/css" />
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

		.fancybox-custom .fancybox-skin {
			box-shadow: 0 0 50px #222;
		}
</style>


	

	
	
	
	<!-- this cssfile can be found in the jScrollPane package -->
	
	<!-- latest jQuery direct from google's CDN -->
    
	<!-- the jScrollPane script -->

	
	<!--instantiate after some browser sniffing to rule out webkit browsers-->
	
</head>

<body>

	<html:form action="contacts"  onsubmit="searchContacts(); return false;">






	<table width="100%" class="bordered">
		<tr>
			<th colspan="4" style="text-align: center;"><big>PDF Documents </big><img src="images/pdf.png" align="absmiddle" height="28" width="28" ></th>
		</tr>
		<tr>
			<td>Select : 
				<form>
				<select name="myjumpbox">
					<option value="">Select Intercom List</option>
					<option value="pdf/Corporate_ML00.pdf">ML00 Corporate</option>
					<option value="pdf/Goa_ML06.pdf">ML06 Goa</option>
					<option value="pdf/DryPowder_ML07.pdf">ML07 Dry Powder</option>
					<option value="pdf/Kumbalgodu_ML08.pdf">ML08 Kumbalgodu</option>
					<option value="pdf/Veer_ML11.pdf">ML11 Veersandra</option>
					<option value="pdf/Peenya2_ML12.pdf">ML12 Eros</option>
					<option value="pdf/Baddi_ML13.pdf">ML13 Baddi</option>
					<option value="pdf/EyeDrops_ML14.pdf">ML14 Eye Drops</option>
					<option value="pdf/API_ML15.pdf">ML15 API</option>
					<option value="pdf/Kudulu_ML18.pdf">ML18 Kudulu</option>
					<option value="pdf/Mum_R&D_ML21.pdf">ML21 Mum_R&D</option>
					<option value="pdf/Mum_Mktg_ML21.pdf">ML21 Mum_Mktg</option>
				</select>
				</form>
				<input type=button styleClass="rounded" style="width: 80px" value=Open onclick="window.parent.location=form.myjumpbox.options[form.myjumpbox.selectedIndex].value" />
				
			</td>
			<td><a href="pdf/VoIPList.pdf" target="_blank">VoIP List</a></td>
			<td><a href="pdf/EmergencyNos.pdf" target="_blank">Emergency Nos.</a></td>
			<td><a href="pdf/First_Aid.pdf" target="_blank">First Aid</a></td>
		<tr/>
	</table>
	<br/>


	<table width="100%" class="bordered">
	<tr>
			<th colspan="4" style="text-align: center;"><big>Contact Search </big></th>
		</tr>
		<tr>
			<td>Plant</td>
		 	<td>
		  		<html:select property="locationId" styleClass="rounded">
			    	<html:option value="">--Select--</html:option>
			      	<html:options property="locationIdList" labelProperty="locationLabelList"></html:options>  
				</html:select>
			</td>
	  
	   		<td>Dept</td>
		 	<td>
				<html:select property="department"  styleClass="rounded">
					<html:option value="">--Select--</html:option>
         			<html:options property="departmentList"  ></html:options>  
      			</html:select>
  			<a href="#"><img src="images/clearsearch.jpg" align="absmiddle" title="Clear..."  onclick="clearContacts()"/></a>
    			<html:text property="firstName" styleId="prdsname" title="Name/Email/Desg/Ext No/IP Phone" maxlength="30" size="30" styleClass="rounded"/>
	      		<a href="#"><img src="images/search.png" align="absmiddle" title="Search..."  onclick="searchContacts()"/></a>
	      </td>
		</tr>
	</table>

	<br/><br/>

	<table align="center">
		<logic:notEmpty name="displayRecordNo">
 		<tr>
	  		<td><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
				<logic:notEmpty name="disablePreviousButton">
					<img src="images/disableLeft.jpg" align="absmiddle"/>
				</logic:notEmpty>
	  	
				<logic:notEmpty name="previousButton">
					<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
				</logic:notEmpty>
	
				<bean:write property="startRecord"  name="contactsForm"/> -
				<bean:write property="endRecord"  name="contactsForm"/>

				<logic:notEmpty name="nextButton">
					<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
				</logic:notEmpty>
	
				<logic:notEmpty name="disableNextButton">
					<img src="images/disableRight.jpg" align="absmiddle"/>
				</logic:notEmpty>

				<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></td>
	
				<html:hidden property="totalRecords"/>
				<html:hidden property="startRecord"/>
				<html:hidden property="endRecord"/>
			</tr>
			</logic:notEmpty>
	 </table>
	 
		<logic:notEmpty name="contactlist" >
			<div align="left" class="bordered">
			<table class="sortable">
				<tr>
					<th >Employee Photo</th><th >Employee Name</th><th >Designation</th><th >Department</th><th>Plant</th><th >Email</th><th>Board Line</th><th >Ext No</th><th >IP Phone No</th>
				<th >View</th>
				</tr>

				<logic:iterate id="empList" name="contactlist">
					<tr>
					<td colspan="1" align="center" valign="middle" style="text-align: center;">
					   <div id="gallery_wrapper">
					 	
					 	<a class="fancybox" href="/EMicro Files/images/EmpPhotos/<bean:write name="empList" property="empPhoto"/>" data-fancybox-group="gallery" title="${empList.firstName }">
            <img src="/EMicro Files/images/EmpPhotos/<bean:write name="empList" property="empPhoto"/>" alt=""  width="40px" height="40px" /></a>
					 	     
				
						</div>			
							</td>										
						<td><bean:write name="empList" property="firstName"/></td>
						<td><bean:write name="empList" property="designation"/></td>
						<td><bean:write name="empList" property="department"/></td>
						<td><bean:write name="empList" property="locationId"/></td>
						<td><bean:write name="empList" property="emailID"/></td>
							<td><bean:write name="empList" property="boardNo"/>&nbsp;</td>
						<td><bean:write name="empList" property="contactNo"/></td>
					
						<td><bean:write name="empList" property="ipPhone"/></td>
						<td >
      							<a href="#"><img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${empList.empid}')"/></a>
      						</td>
					</tr>
				</logic:iterate>
			</table> 
			</div>
		</logic:notEmpty>
	
		<logic:notEmpty name="noRecords">
			<table class="bordered">
				<tr>
					<!-- th>ID</th-->
					<th >Employee Photo</th><th>Employee Name</th><th >Designation</th><th >Department</th><th>Plant</th><th >Email</th><th >Contact No</th><th >View</th>
				</tr>
					<tr>
					<td colspan="8">
				<logic:present name="contactsForm" property="message">
					
						<font color="red">
						<center>Searched details could not be found.</center>
						</font>
					
				</logic:present>
				</td>
				</tr>
			</table>
		</logic:notEmpty>

	</html:form>

</body>
</html>