<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <head>
    <base href="<%=basePath%>">

<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      <link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" type="text/css" href="css/styles.css" />
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">

</script>
<script type="text/javascript">

 
</script>


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


<style>
      .example-modal .modal {
        position: absolute;
        top: auto;
        bottom: auto;
        right: 90px;
        left: 90px;
        display: block;
        z-index: 1;
      }
      .example-modal .modal {
        background: transparent !important;
      }
      
       /* Style the list */
ul.tab {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: rgb(227,237,250);
    border-radius: 6px 6px 0 0;
    width: 100%;
}

/* Float the list items side by side */
ul.tab li {float: left;}

/* Style the links inside the list items */
ul.tab li a {
    display: inline-block;
    color: black;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    transition: 0.3s;
    font-size: 17px;
}

/* Change background color of links on hover */
ul.tab li a:hover {background-color: #ddd;}

/* Create an active/current tablink class */
ul.tab li a:focus, .active {background-color: #ccc;}

/* Style the tab content */
.tabcontent {
    display: none;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-top: none;
}

.tabcontent1 {
    display: none;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-top: none;
}
      
    </style>
    <script >


</script>
  </head>
  
	<body onload="change()">
		<div align="center">
			<logic:notEmpty name="travelRequestForm" property="message">
				<script language="javascript">
					statusMessage('<bean:write name="travelRequestForm" property="message" />');
				</script>
			</logic:notEmpty>
		</div>
  
		<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
		<html:hidden property="id"  name="travelRequestForm"/>
		<html:hidden property="travel_desk_type"  name="travelRequestForm"/>
		
		
		<div id="masterdiv" class="">
		

			<br/>
			
			
			<logic:notEmpty name="multicity">

			<table class="bordered" style="position: relative;left: 2%;width: 80%;" >
				<tr><th colspan="4">Multiple City Details </th></tr>
				<tr>
				<td>
				Traveller List
				</td>
				<td >
				${travelRequestForm.multiemployeeno}
				</td>
				</tr>
				<tr>
				<td>Travel Mode<font color="red" size="3">*</font></td>
				<td>
				${travelRequestForm.travelmode}
				</td>	
	
				<td colspan="1"> Travel Type<font color="red" size="3">*</font> </td>
				<td>
				${travelRequestForm.traveltype}
				</td>
 				</tr>
 
				 <tr style="display: none"  id="passportm1">
				<td>Passport No.<font color="red" size="3">*</font></td>
				<td>${travelRequestForm.passportno}</td>
				<td>Passport Expiry Date:<font color="red" size="3">*</font> </td>
				<td>${travelRequestForm.passportexpirydate}
				</td>
				 </tr>
 
				<tr style="display: none;" id="passportm2">		
						<td>Visa Number & Date:<font color="red" size="3">*</font> </td>
						<td colspan="3">
						${travelRequestForm.visano}						
						</td>
				</tr>
				
				<tr>
					<td>Location:<font color="red" size="3">*</font> </td>
					<td>${travelRequestForm.locationId}</td> 
					<td> Preference:<font color="red" size="3">*</font> </td>
					<td>${travelRequestForm.airline_Pref} </td>
					
				</tr>
				<tr> 
					<td>Origin:<font color="red" size="3">*</font> </td>
					<td>${travelRequestForm.origin} </td>
					
					<td>Destination: <font color="red" size="3">*</font></td>
					<td>${travelRequestForm.departure} </td>
				
				</tr>
				<tr>
					<td>Arrival date: <font color="red" size="3">*</font></td>
					<td>
					${travelRequestForm.departOn}
					</td>
					<td>Arrival Time:<font color="red" size="3">*</font> </td>
					<td>
					${travelRequestForm.departTime}
					</td>
				</tr>
				<tr>
					<td>Departure Date:<font color="red" size="3">*</font> </td>
					<td>
					${travelRequestForm.returnOn}
					</td>
					<td>Departure Time:<font color="red" size="3">*</font> </td>
					<td>
					${travelRequestForm.returnTime}
					</td>
				</tr>
				<tr >
					<td>Accomodation: <font color="red" size="3">*</font></td>
					<td>
					${travelRequestForm.hotel_Res }
					</td>
					
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
					${travelRequestForm.rent_Car}	
					</td>
					
				</tr>
				
				<tr>
				<td>Accomodation Type</td>
				<td>
				
				${travelRequestForm.accom_type}
			
				</td>
				<td>Guest House Name</td>
				<td>
				${travelRequestForm.accom_name}
				</td>
				
				</tr>
				
				
				
				<tr>
				<td>
				Hotel Name:
				</td>
				<td>
				${travelRequestForm.hotel_Name}
				</td>
				
				<td>
				Hotel City:
				</td>
				<td>
				${travelRequestForm.hotel_City }
				</td>
				</tr>
				
				<tr style="display: none" id="mcity3">
					<td>Pickup Details: </td>
					<td>
					${travelRequestForm.pickup_Details }
					</td>
					<td>Drop Details: </td>
					<td>
					${travelRequestForm.drop_Details}
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center"><center>
						&nbsp; <html:button property="method" styleClass="rounded" value="Close" onclick="window.close()" style="align:right;width:100px;"/> &nbsp;
						</center>
					</td>
				</tr>
				
			</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="travellist">
			<table class="bordered" style="position: relative;left: 2%;width: 60%;">
						<tr>
						<th colspan="16"><center>Traveller Details</center></th>
						</tr>
						<tr>
						<td>Travel Type<font color="red" size="3">*</font></td>
						<td colspan="2">
						${travelRequestForm.utravelmode}
							</td>	
							
							<td colspan="1"> Travel Mode<font color="red" size="3">*</font> </td>
							<td colspan="2">
							${travelRequestForm.traveltype}
							</td>
							</tr>						
						
						<logic:equal value="International" name="travelRequestForm" property="traveltype">
						
						<tr >
						<td>Passport No.<font color="red" size="3">*</font></td><td>
						${travelRequestForm.passportno}
						</td>
						<td>Passport Expiry Date:<font color="red" size="3">*</font> </td><td>
						${travelRequestForm.passportexpirydate}
						</td>
						<td>Visa Number & Date:<font color="red" size="3">*</font> </td><td>
						${travelRequestForm.guest_Visano}
						</td>
						</tr>
						</logic:equal>
						
						
						<tr><td>Traveller Type</td> 
						<td>
						${travelRequestForm.guest_Type}
						</td>
						<td>Employee No: </td><td>
						${travelRequestForm.guest_pernr}
						</td>
						<td>Title: </td><td>
						${travelRequestForm.guest_Title}
						</td>
						</tr>
						<tr>
						<td>Name: </td><td>
						${travelRequestForm.guestName}
						</td> 
						
						<td>Gender: </td>
						 <td>
						${travelRequestForm.gender}
						</td>
						<td>e-Mail:</td><td>
						${travelRequestForm.email_Guest}
						</td>
						</tr>
							<tr>
					<td>Origin: <font color="red" size="3">*</font></td>
					<td colspan="2">
					${travelRequestForm.fromPlace}
					</td>
					<td>Final Destination: <font color="red" size="3">*</font></td>
					<td colspan="2">
					${travelRequestForm.toPlace}
					</td>
					</tr>
						<tr>
						<td>Contact No: </td><td>
						${travelRequestForm.guestContactNo}
						</td>
						<td>Company Name: </td><td>
						${travelRequestForm.guest_Company}
						</td>
						<td>Age: </td><td>
						${travelRequestForm.guest_DOB}
						</td>
						</tr>
						<tr>
					<td>Trip Departure Date: <font color="red" size="3">*</font></td>
					<td colspan="1">
					${travelRequestForm.departOn}
					</td>&nbsp;&nbsp;
		            <td>Preferred Departure Time: <font color="red" size="3">*</font></td>
		            <td>
		            ${travelRequestForm.departTime}
					</td>
					
					
					<td>Trip Return On: <font color="red" size="3">*</font></td>
					<td>
					${travelRequestForm.returnOn}
					</td>
					
						</tr>
						
						<tr>
						<td>Preferred Return Time: <font color="red" size="3">*</font></td>
					<td>
					${travelRequestForm.returnTime}
					</td>
						<td>Meal Preference: </td><td>
						
						${travelRequestForm.guest_Meal}
						</td>
						<td>Attachment: </td>
						
						<td><a href="${travelRequestForm.fileFullPath}"  target="_blank">${travelRequestForm.fileName}</a></td>
			                     
						
						
						</tr>
						
						
						</table>
						
				<logic:notEqual value="Multi-City" name="travelRequestForm" property="travelRequestFor"></logic:notEqual>				
				<table class="bordered" style="position: relative;left: 2%;width: 80%;">
			<tr><th colspan="8">Other Details</th></tr>
			
			
			<tr>
					<td>Accomodation<font color="red" size="3">*</font></td>
					<td>
					${travelRequestForm.hotel_Res}
					
					</td>
					
					<td>Rental Car required: <font color="red" size="3">*</font></td>
					<td>
					${travelRequestForm.rent_Car}
					</td>
					
				</tr>
				
				<tr>
				<td>Accomodation Type</td>
				<td>
				${travelRequestForm.accom_type}
				
				
				</td>
				<td>Guest House Name</td>
				<td>
				${travelRequestForm.accom_name}
				</tr>
				
				<tr>
				<td>
				Hotel Name:
				</td>
				<td>
				${travelRequestForm.hotel_Name}
				</td>
				
				<td>
				Hotel City:
				</td>
				<td>
				${travelRequestForm.hotel_City}
				</td>
				</tr>
				
				<tr>
					<td>Pickup Details: </td>
					<td>
					${travelRequestForm.pickup_Details}
					<td>Drop Details: </td>
					<td>
					${travelRequestForm.drop_Details}
				</tr>
				
				</table>	
			
			
			<table class="bordered" style="position: relative;left: 2%;width: 60%;">
			<tr>
					<td colspan="4" align="center"><center>
						&nbsp; <html:button property="method" styleClass="rounded" value="Close" onclick="window.close()" style="align:right;width:100px;"/> &nbsp;
						</center>
					</td>
				</tr>
				</table>
</logic:notEmpty>
		<br/>
		<html:hidden property="requestNumber" />
		</div>
  		</html:form>			
	</body>
</html>
