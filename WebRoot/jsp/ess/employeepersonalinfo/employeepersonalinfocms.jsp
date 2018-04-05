
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<link rel="stylesheet" type="text/css" href="css/styles.css" />

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="style1/inner_tbl.css" />
<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<title>Microlab</title>
<script type="text/javascript">

 function openPersonalnfo()
{
document.getElementById('address').style.display='none';
document.getElementById('familyDetails').style.display='none';
document.getElementById('educationDetails').style.display='none';
document.getElementById('experience').style.display='none';
document.getElementById('language').style.display='none';
document.getElementById('personalInformation').style.display='';
}

function openAddress()
{

document.getElementById('personalInformation').style.display='none';
document.getElementById('familyDetails').style.display='none';
document.getElementById('educationDetails').style.display='none';
document.getElementById('experience').style.display='none';
document.getElementById('language').style.display='none';
document.getElementById('address').style.display='';
}
function openFamily()
{
document.getElementById('personalInformation').style.display='none';
document.getElementById('address').style.display='none';
document.getElementById('educationDetails').style.display='none';
document.getElementById('experience').style.display='none';
document.getElementById('language').style.display='none';
document.getElementById('familyDetails').style.display='';
}
function openEducation()
{
document.getElementById('personalInformation').style.display='none';
document.getElementById('address').style.display='none';
document.getElementById('familyDetails').style.display='none';
document.getElementById('experience').style.display='none';
document.getElementById('language').style.display='none';
document.getElementById('educationDetails').style.display='';

}

function openExperience()
{
document.getElementById('personalInformation').style.display='none';
document.getElementById('address').style.display='none';
document.getElementById('familyDetails').style.display='none';
document.getElementById('educationDetails').style.display='none';
document.getElementById('language').style.display='none';
document.getElementById('experience').style.display='';

}


function openLanguage()
{
document.getElementById('personalInformation').style.display='none';
document.getElementById('address').style.display='none';
document.getElementById('familyDetails').style.display='none';
document.getElementById('educationDetails').style.display='none';
document.getElementById('experience').style.display='none';
document.getElementById('language').style.display='';

}



function onModify(){
	                        		
	var url="employeepersonalinfo.do?method=modify";
	document.forms[0].action=url;
	document.forms[0].submit();
}

   function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }

//-->
</script>
</head>

<body onload="openPersonalnfo()">
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
                
					
				<div align="center">
					<logic:present name="userGroupForm" property="message">
						<font color="red">
							<bean:write name="userGroupForm" property="message" />
						</font>
					</logic:present>
				</div>
				
				<div align="center">
					<logic:present name="userGroupForm" property="statusMessage">
						<font color="red">
							<bean:write name="userGroupForm" property="statusMessage" />
						</font>
					</logic:present>
				</div>


<html:form action="/employeepersonalinfo.do" enctype="multipart/form-data">


							
												<% 
		                 String status=(String)session.getAttribute("status");		
		             if(status==""||status==null)
		                 {
		
		                    }
		                 else{
		
		                 %>
		                <b><center><font color="red" size="4" ><%=status %></font></center></b>
		                   <%
		                   session.setAttribute("status"," ");
	                       	}
                         %>



						
							<%--PERSONAL INFORMATION DETAILS--%>
							
							<html:hidden property="id" />
				
							<div id="personalInformation">
							<table width="100%" border="1" id="mytable1">
							<tr class="tablerowdark1">
							<td colspan="5" align="center" bgcolor="#51B0F8"><b><font color="white">Personal&nbsp;Information</font></b></td></tr>							
							<tr>
							<th  class="specalt" scope="row">Title<img src="images/star.gif" width="8" height="8" />Title</th>
								
							
									<td>
										<html:select property="title">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Mr."></html:option>
											<html:option value="Mrs."></html:option>
										</html:select>
										
									
										
									</td>
									
									<th  class="specalt" scope="row">First&nbsp;Name<img src="images/star.gif" width="8" height="8" /></th>
									
									<td>
										<html:text property="firstName"></html:text>
									</td>
								</tr>
									<tr>
									<th  class="specalt" scope="row">Middle&nbsp;Name</th>
									
									<td>
										<html:text property="middleName" readonly="true" styleClass="text_field"></html:text>
									</td>
								<th  class="specalt" scope="row">Last&nbsp;Name<img src="images/star.gif" width="8" height="8" /></th>
								
									<td>
										<html:text property="lastName" readonly="true" styleClass="text_field"></html:text>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">
										Initials
							</th>
									<td>
										<html:text property="initials"></html:text>
									</td>
								
						<th  class="specalt" scope="row">
										Nick&nbsp;name
					</th>
									<td>
										<html:text property="nickName"></html:text>
									</td>
								</tr>
								<tr>
							
								<tr>
								<th  class="specalt" scope="row">
										Gender

								</th>
									<td>
										<html:select property="gender">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Male"></html:option>
											<html:option value="Female"></html:option>
										</html:select>
									</td>
								
								
								<th  class="specalt" scope="row">
										Marital&nbsp;Status


								</th>
									<td>
										<html:select property="maritalStatus">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Single"></html:option>
											<html:option value="Marrried"></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">
										Date&nbsp;of&nbsp;Birth

									</th>
									<td>
										<html:text property="dateOfBirth_mod"  styleClass="textfield" 
										 styleId="date1" onfocus="popupCalender('date1')"
										  readonly="true"></html:text>
									</td>
								
								<th  class="specalt" scope="row">Birth&nbsp;Place</th>
									<td>
										<html:text property="birthplace"></html:text>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">Country&nbsp;of&nbsp;Birth</th>
								
									<td>
										<html:select property="countryofBirth" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
											<html:option value="India"></html:option>
											<html:option value="England"></html:option>
											<html:option value="USA"></html:option>
											<html:option value="Australia"></html:option>
										</html:select>
									</td>
									<th  class="specalt" scope="row">Caste</th>
								
									<td>
										<html:text property="caste" styleClass="text_field"></html:text>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">Religious&nbsp;Denomination&nbsp;</th>
								
									<td>
										<html:select property="religiousDenomination" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Hindu"></html:option>
											<html:option value="Muslim"></html:option>
											<html:option value="Christian"></html:option>
										</html:select>
									</td>
									<th  class="specalt" scope="row">Nationality<img src="images/star.gif" width="8" height="8" /></th>
								
									<td>
										<html:select property="nationality" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Indian"></html:option>
											<html:option value="Foreigner"></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">Telephone&nbsp;Number&nbsp;<img src="images/star.gif" width="8" height="8" /></th>
								
									<td>
										<html:text property="telephoneNumber" styleClass="text_field"></html:text>
									</td>
								<th  class="specalt" scope="row">Mobile&nbsp;Number&nbsp;</th>
								
									<td>
										<html:text property="mobileNumber" readonly="true" styleClass="text_field"></html:text>
									</td>
								</tr>
								<tr>
								
								<tr>
						       <th  class="specalt" scope="row">Fax&nbsp;Number&nbsp;</th>
									<td>
										<html:text property="faxNumber" styleClass="text_field"></html:text>
									</td>
								
									<th  class="specalt" scope="row">E-Mail&nbsp;Address&nbsp;<img src="images/star.gif" width="8" height="8" /></th>
									<td>
										<html:text property="emailAddress" readonly="true" styleClass="text_field"></html:text>
									</td>
								</tr>
										<tr>
									<th  class="specalt" scope="row">Website</th>
								
									<td>
										<html:text property="website" styleClass="text_field"></html:text>
									</td>
								<th  class="specalt" scope="row">Blood&nbsp;Group&nbsp;<img src="images/star.gif" width="8" height="8" /></th>
								
										<td>
										<html:select property="bloodGroup" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
											<html:option value="A+"></html:option>
											<html:option value="AB+"></html:option>
											<html:option value="B-"></html:option>
											<html:option value="AB-"></html:option>
											<html:option value="B+"></html:option>
											<html:option value="O+"></html:option>
											<html:option value="A-"></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">PAN &nbsp;Card&nbsp;Number</th>
								
									<td>
										<html:text property="permanentAccountNumber" styleClass="text_field"></html:text>
									</td>
								<th  class="specalt" scope="row">Passport&nbsp;Number</th>
								
										<td>
										<html:text property="passportNumber" styleClass="text_field"></html:text>
									</td>
								</tr>
								<tr>
									<th  class="specalt" scope="row">Place&nbsp;of&nbsp;Issue&nbsp;of&nbsp;Passport</th>
								
									<td>
										<html:text property="placeofIssueofPassport" styleClass="text_field"></html:text>
									</td>
								
								<th  class="specalt" scope="row">Date&nbsp;of&nbsp;expiry&nbsp;of&nbsp;Passport</th>
								
										<td>
										<html:text property="dateofexpiryofPassport_mod"  styleClass="text_field" 
										 styleId="date3" onfocus="popupCalender('date3')"
										  ></html:text>
									</td>
								</tr>
								<tr>
									<th  class="specalt" scope="row">Personal&nbsp;Identification&nbsp;Marks</th>
									<td>
										<html:text property="personalIdentificationMarks"></html:text>
									</td>
								
								<th  class="specalt" scope="row">Date&nbsp;of&nbsp;issue&nbsp;of&nbsp;Passport</th>
										<td>
										<html:text property="dateofissueofPassport_mod"  styleClass="textfield" 
										 styleId="date2" onfocus="popupCalender('date2')"
										  readonly="true"></html:text>
									</td>
								</tr>
									<tr>
								<th  class="specalt" scope="row">Physically&nbsp;Challenged</th>
									<td>
									<html:select property="physicallyChallenged">
											<html:option value="">--SELECT--</html:option>
											<html:option value="YES"></html:option>
											<html:option value="NO"></html:option>
										</html:select>
									
									</td>
								
										<th  class="specalt" scope="row">Physically&nbsp;Challenged&nbsp;Details&nbsp;</th>
										<td>
										<html:textarea property="physicallyChallengeddetails" cols="40" rows="3"></html:textarea>
									</td>
								</tr>
								<tr>
									<th  class="specalt" scope="row">Emergency&nbsp;Contact&nbsp;Person&nbsp;Name&nbsp;<img src="images/star.gif" width="8" height="8" /></th>
									<td>
										<html:text property="emergencyContactPersonName"></html:text>
									</td>
								
									
									<th  class="specalt" scope="row">Emergency&nbsp;Contact&nbsp;Address&nbsp;Line&nbsp;1&nbsp;
									<img src="images/star.gif" width="8" height="8" /></th>
										<td>
										<html:textarea property="emergencyContactAddressLine1" cols="40" rows="3"></html:textarea>
									</td>
								</tr>
								<tr>
									
									<th  class="specalt" scope="row">Emergency&nbsp;Contact&nbsp;Address&nbsp;Line&nbsp;2&nbsp;
									<img src="images/star.gif" width="8" height="8" /></th>
									<td>
										<html:textarea  property="emergencyContactAddressLine2" cols="40" rows="3"></html:textarea>
									</td>
								
															<th  class="specalt" scope="row"> Emergency&nbsp;Telephone&nbsp;Number&nbsp;</th>
										<td>
										<html:text property="emergencyTelephoneNumber"></html:text>
									</td>
								</tr>
								<tr>
						         	<th  class="specalt" scope="row"> Emergency&nbsp;Mobile&nbsp;Number&nbsp;</th>
									<td>
										<html:text property="emergencyMobileNumber"></html:text>
									</td>
										<th  class="specalt" scope="row"> No.of Childrens</th>
								
									<td>
										<html:text property="noOfChildrens" styleClass="text_field" ></html:text>
									</td>
									</tr>
									
									
									</tr>
							</table>
							</div>
							
							<%--ADDRESS DETAILS--%>
							
							
							<div id="address">
							<table width="100%" border="1">
							<tr class="tablerowdark1">
							<td colspan="5" align="center" bgcolor="#51B0F8"><b><font color="white">
							Addresses</font></b></td></tr>
							<tr>
									<td>
										Care&nbsp;of&nbsp;contact&nbsp;name
									</td>
									<td>
										<html:text property="careofcontactname"></html:text>
									</td>
								
									<td>
								   House&nbsp;Number
									</td>
										<td>
										<html:text property="houseNumber"></html:text>
									</td>
								</tr>
								<tr>
									<td>
										Address&nbsp;Line&nbsp;1
									</td>
									<td>
										<html:textarea property="addressLine1" cols="40" rows="3"></html:textarea>
									</td>
									<td>
										Address&nbsp;Line&nbsp;2
									</td>
									<td>
										<html:textarea property="addressLine2" cols="40" rows="3"></html:textarea>
									</td>
									
									</tr>
									<tr>
									<td>
										Address&nbsp;Line&nbsp;3
									</td>
									<td>
										<html:textarea property="addressLine3" cols="40" rows="3"></html:textarea>
									</td>
									<td>
										Postal&nbsp;Code
									</td>
									<td>
										<html:text property="postalCode"></html:text>
									</td>
									
									</tr>
									
									<tr>
									<td>
										City
									</td>
									<td>
										<html:text property="city"></html:text>
									</td>
									<td>
										State
									</td>
									<td>
										<html:select property="state">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Karnataka"></html:option>
											<html:option value="Tamilnadu"></html:option>
											<html:option value="Goa"></html:option>
										</html:select>
									</td>
									
									</tr>
									<tr>
									<td>
										Country
									</td>
									<td>
										<html:select property="country">
											<html:option value="">--SELECT--</html:option>
											<html:option value="India"></html:option>
											<html:option value="USA"></html:option>
											<html:option value="Italy"></html:option>
										</html:select>
									</td>
									<td>
										Own&nbsp;Accomodation
									</td>
									<td>
										<html:select property="ownAccomodation">
											<html:option value="">--SELECT--</html:option>
											<html:option value="YES"></html:option>
											<html:option value="NO"></html:option>
											
										</html:select>
									</td>
									
									</tr>
							</table>
							</div>
							
							
							<%--FAMILY DETAILS--%>
							
							
							<div id="familyDetails">
							<table width="100%" border="1">
							<tr class="tablerowdark1">
							<td colspan="5" align="center" bgcolor="#51B0F8"><b><font color="white">
							Family&nbsp;Details</font></b></td></tr>
							<tr>
									<td>
										Family/Dependent&nbsp;Type&nbsp;ID
									</td>
									<td>
										<html:select property="familyDependentTypeID">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Dependent"></html:option>
											<html:option value="Independent"></html:option>
										
										</html:select>
									</td>
									<td>
										Title
									</td>
									<td>
										<html:select property="ftitle">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Mr."></html:option>
											<html:option value="Mrs."></html:option>
											
										</html:select>
									</td>
									
									</tr>
									<tr>
									<td>
										First&nbsp;Name
									</td>
									<td>
										<html:text property="ffirstName"></html:text>
									</td>
							
								
									<td>
										Middle&nbsp;Name
									</td>
									<td>
										<html:text property="fmiddleName"></html:text>
									</td>
								</tr>
								<tr>
									<td>
										Last&nbsp;Name
									</td>
									<td>
										<html:text property="flastName"></html:text>
									</td>
								
								
									<td>
										Initials
									</td>
									<td>
										<html:text property="finitials"></html:text>
									</td>
								
									
								</tr>
								<tr>
									<td>
										Gender

									</td>
									<td>
										<html:select property="fgender">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Male"></html:option>
											<html:option value="Female"></html:option>
										</html:select>
									</td>
									<td>
										Date&nbsp;of&nbsp;Birth

									</td>
									<td>
										<html:text property="fdateofBirth" styleClass="textfield" 
										 styleId="date5" onfocus="popupCalender('date5')"
										  readonly="true"></html:text>
									</td>
								</tr>
								<tr>
								<td>
										Birth&nbsp;Place

									</td>
									<td>
										<html:text property="fbirthplace"></html:text>
									</td>
									<td>
										Telephone&nbsp;Number

									</td>
									<td>
										<html:text property="ftelephoneNumber"></html:text>
									</td>
								</tr>
								<tr>
								<td>
										Mobile&nbsp;Number

									</td>
									<td>
										<html:text property="fmobileNumber"></html:text>
									</td>
									<td>
									E-Mail&nbsp;Address

									</td>
									<td>
										<html:text property="femailAddress"></html:text>
									</td>
								</tr>
								<tr>
								<td>
									Blood&nbsp;Group
									</td>
										<td>
										<html:select property="fbloodGroup">
											<html:option value="">--SELECT--</html:option>
											<html:option value="B+"></html:option>
											<html:option value="O+"></html:option>
											<html:option value="O-"></html:option>
										</html:select>
									</td>
									<td>
									Dependent
									</td>
									<td>
										<html:select property="fdependent">
											<html:option value="">--SELECT--</html:option>
											<html:option value="YES"></html:option>
											<html:option value="NO"></html:option>
										
										</html:select>
									</td>
								</tr>
								<tr>
								<td>
									Employee&nbsp;of&nbsp;Company
									</td>
										<td>
										<html:select property="femployeeofCompany">
											<html:option value="">--SELECT--</html:option>
											<html:option value="YES"></html:option>
											<html:option value="NO"></html:option>
										</html:select>
									</td>
									<td>
									Employee&nbsp;Number
									</td>
									
										<td>
										<html:text property="femployeeNumber"></html:text>
									</td>
									
								</tr>
							</table>
							</div>
							<%--EDUCATIONAL DETAILS--%>
							
							
							<div id="educationDetails">
							<table width="100%" border="1">
							<tr class="tablerowdark1">
							<td colspan="5" align="center" bgcolor="#51B0F8"><b><font color="white">
							Educational&nbsp;Details</font></b></td></tr>
							<tr>
									<td>
										Educational&nbsp;Level
									</td>
									<td>
										<html:select property="educationalLevel">
											<html:option value="">--SELECT--</html:option>
											<html:option value="PG"></html:option>
											<html:option value="UG"></html:option>
										
										</html:select>
									</td>
									<td>
										Qualification
									</td>
									<td>
										<html:select property="qualification">
											<html:option value="">--SELECT--</html:option>
											<html:option value="MCA"></html:option>
											<html:option value="B.Tech"></html:option>
											
										</html:select>
									</td>
									
									</tr>
									<tr>
								<td>
										Specialization

									</td>
									<td>
										<html:text property="specialization"></html:text>
									</td>
									<td>
										Univerysity&nbsp;Name 

									</td>
									<td>
										<html:text property="universityName"></html:text>
									</td>
								</tr>
								<tr>
								<td>
										Univerysity&nbsp;Location

									</td>
									<td>
										<html:text property="univerysityLocation"></html:text>
									</td>
									<td>
										State

									</td>
									<td>
										<html:select property="edstate">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Delhi"></html:option>
											<html:option value="Bihar"></html:option>
											
										</html:select>
									</td>
								</tr>
								<tr>
								<td>
										Country

									</td>
								<td>
										<html:select property="edcountry">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Australia"></html:option>
											<html:option value="India"></html:option>
											
										</html:select>
									</td>
									<td>
									  Duration&nbsp;of&nbsp;Course

									</td>
									<td>
										<html:text property="durationofCourse"></html:text>
									</td>
								</tr>
								<tr>
								<td>
										Time

									</td>
								<td>
										<html:select property="times">
											<html:option value="">--SELECT--</html:option>
											<html:option value="1"></html:option>
											<html:option value="2"></html:option>
											
										</html:select>
									</td>
									<td>
									  From

									</td>
									<td>
										<html:text property="fromDate" styleClass="textfield" 
										 styleId="date6" onfocus="popupCalender('date6')"
										  readonly="true"></html:text>
									</td>
								</tr>
								<tr>
								<td>
										To
									</td>
									<td>
										<html:text property="toDate" styleClass="textfield" 
										 styleId="date7" onfocus="popupCalender('date7')"
										  readonly="true"></html:text>
									</td>
									<td>
										Full&nbsp;Time&nbsp;/&nbsp;Part&nbsp;Time

									</td>
									<td>
										<html:select property="fullTimePartTime">
											<html:option value="">--SELECT--</html:option>
											<html:option value="FullTime"></html:option>
											<html:option value="PartTime"></html:option>
											
										</html:select>
									</td>
								</tr>
								<tr>
								<td>
									Percentage

									</td>
									<td>
										<html:text property="percentage"></html:text>
									</td>
									</tr>
									</table>
									</div>
									<%--EXPERIENCE DETAILS--%>
							
							
							<div id="experience">
							<table width="100%" border="1">
							<tr class="tablerowdark1">
							<td colspan="5" align="center" bgcolor="#51B0F8"><b><font color="white">
							Experience</font></b></td></tr>
							<tr>
								<td>
									Name&nbsp;of&nbsp;employer

									</td>
									<td>
										<html:text property="nameOfEmployer"></html:text>
									</td>
									<td>
										Industry

									</td>
									<td>
										<html:select property="industry">
											<html:option value="">--SELECT--</html:option>
											<html:option value="IT"></html:option>
											<html:option value="Management"></html:option>
											
										</html:select>
									</td>
								</tr>
								
								
								<tr>
								<td>
									City

									</td>
									<td>
										<html:text property="exCity"></html:text>
									</td>
									<td>
										Country 
									</td>
									<td>
										<html:select property="excountry">
											<html:option value="">--SELECT--</html:option>
											<html:option value="India"></html:option>
											<html:option value="Spain"></html:option>
										</html:select>
									</td>
								</tr>
								
								<tr>
								<td>
									Position&nbsp;Held
									</td>
									<td>
										<html:text property="positionHeld"></html:text>
									</td>
									<td>
										Job&nbsp;Role
									</td>
									<td>
									<html:text property="jobRole"></html:text>
									</td>
								</tr>
								
								<tr>
								<td>
									Start&nbsp;Date
									
									</td>
									<td>
										<html:text property="startDate" styleClass="textfield" 
										 styleId="date8" onfocus="popupCalender('date8')"
										  readonly="true"></html:text>
									</td>
									<td>
										End&nbsp;Date

									</td>
									<td>
									<html:text property="endDate" styleClass="textfield" 
										 styleId="date9" onfocus="popupCalender('date9')"
										  readonly="true"></html:text>
									</td>
								</tr>
							</table>
							</div>
						
							<div id="language">
							<table width="100%" border="1">
							<tr class="tablerowdark1">
							<td colspan="5" align="center" bgcolor="#51B0F8"><b><font color="white">
							Language</font></b></td></tr>
							<tr>
							<td>
										Language 

									</td>
									<td>
										<html:select property="language">
											<html:option value="">--SELECT--</html:option>
											<html:option value="English"></html:option>
											<html:option value="Hindi"></html:option>
											
										</html:select>
									</td>
								
							        <td>
									Mother&nbsp;Tongue
									</td>
									
									<td>
										<html:select property="motherTongue">
											<html:option value="">--SELECT--</html:option>
											<html:option value="YES"></html:option>
											<html:option value="NO"></html:option>
											
										</html:select>
									</td>
								</tr>
								
								<tr>
							<td>
									Language&nbsp;Fluency
									</td>
									<td>
										<html:select property="languageFluency">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Poor"></html:option>
											<html:option value="Good"></html:option>
											
										</html:select>
									</td>
								
							<td>
									start&nbsp;Date

									</td>
									
										<td>
										<html:text property="langstartDate" styleClass="textfield" 
										 styleId="date10" onfocus="popupCalender('date10')"
										  readonly="true"></html:text>
									</td>
									
								</tr>
								
							</table>
							</div>
							
							<logic:empty name="modifyButton">
							
							<table align="center">
								<tr>
								<td>
								<html:submit styleClass="button" value="modify" property="method" onclick="onModify()"/>
								</td>
								</tr>
								</table>
							
							</logic:empty>
            
            </html:form>

						</div>
            
            </div> <!--------CONTENT ENDS -------------------->
            
</div><!--------WRAPER ENDS -------------------->

</body>
</html>
