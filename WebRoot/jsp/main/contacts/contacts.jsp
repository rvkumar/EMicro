<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
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
				
		function searchExt()
		
	 {

		if(document.getElementById("a").value!="" )
		{
	
	    document.getElementById("abc").setAttribute("href",document.getElementById("a").value);
		document.getElementById("abc").click();
		}
		

	}
	
	
		
		
	</script>


</head>

<body>

	<html:form action="contacts"  onsubmit="searchContacts(); return false;">

<a href="#" id="abc" target="_blank"></a>


	<table width="100%" class="bordered">
		<tr>
			<th colspan="4" style="text-align: center;"><big>PDF Documents </big><img src="images/pdf.png" align="absmiddle" height="28" width="28" ></th>
		</tr>
		<tr>
			<td>Select : 
			<select name="myjumpbox" id="a" >
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
					<option value="pdf/Veer2_ML25.pdf">ML25 Veersandra2</option>
					<option value="pdf/API_R&D_ML-27.pdf">ML27 API R&D</option>
				</select>
				&nbsp;<input type=button class="rounded" style="width: 80px" value="Open" onclick="searchExt()" />
				
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
		
		  		<html:select property="locationId" styleClass="rounded" >
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
	  		<td>
	  		<center>
	  		<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
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

				<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
					</center>
				</td>
	
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
					  
            <img src="/EMicro Files/images/EmpPhotos/<bean:write name="empList" property="empPhoto"/>" alt=""  width="40px" height="40px" />
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