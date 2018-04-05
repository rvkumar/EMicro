
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
<jsp:directive.page import="com.microlabs.ess.form.JoiningFormalityForm"/>



<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>Microlab</title>
<script type="text/javascript">
<!--
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

function openDivs(param)
{
	var url="ess.do?method=displayTables&param="+param;
	document.forms[0].action=url;
	document.forms[0].submit();
}

//-->
</script>
</head>

<body >
		<!--------WRAPER STARTS -------------------->
<div id="wraper">

       	   
                
                <div style="padding-left: 10px;width: 100%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
                    <div>
					
					
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



<html:form action="/ess.do" enctype="multipart/form-data">

		<table width="40%" border="0">
		<tr><td>
							<input type="button" class="button" value="Personal Information" onclick="openDivs('personalDetails')" style="width:210";/></td>
							<td><input type="button" class="button" value="Addresses" onclick="openDivs('addressDetails')" style="width:120"/></td>
							<td><input type="button" class="button" value="Family Details" onclick="openDivs('familyDetails')" style="width:150"/></td>
							<td><input type="button" class="button" value="Education Details" onclick="openDivs('educationDetails')" style="width:170"/></td>
							<td><input type="button" class="button" value="Experience" onclick="openDivs('experienceDetails')" style="width:120"/></td>
							<td><input type="button" class="button" value="Language" onclick="openDivs('languageDetails')" style="width:120"/>
							</td></tr>
							</table>
							
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

							<html:hidden property="userType"/>
							<html:hidden property="userName"/>
							<html:hidden property="password"/>
							<%--PERSONAL INFORMATION DETAILS--%>
							
							
							
							
							
							<logic:notEmpty name="personalDetails" >
							
								
								
							<div id="personalInformation">
							<table width="75%" border="1" id="mytable1">
							<tr class="tablerowdark1">
							<th colspan="5" ><center> Personal&nbsp;Information</center></th></tr>							
							<tr>
							<th  class="specalt" scope="row">Title<img src="images/star.gif" width="8" height="8" /></th>
						
									<td>
										<html:select property="title" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Mr."></html:option>
											<html:option value="Mrs."></html:option>
										</html:select>
										
									</td>
									
								<th  class="specalt" scope="row">First&nbsp;Name<img src="images/star.gif" width="8" height="8" /></th>
									
									<td>
										<html:text property="firstName" readonly="true" styleClass="text_field"></html:text>
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
								<th  class="specalt" scope="row">Initials</th>
									
									<td>
										<html:text property="initials" styleClass="text_field"></html:text>
									</td>
								<th  class="specalt" scope="row">Nick&nbsp;name</th>
								
									<td>
										<html:text property="nickName" styleClass="text_field"></html:text>
									</td>
								</tr>
							
							
								<tr>
								<th  class="specalt" scope="row">Gender<img src="images/star.gif" width="8" height="8" /></th>
									<td>
										<html:select property="gender" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Male"></html:option>
											<html:option value="Female"></html:option>
										</html:select>
									</td>
								
									<th  class="specalt" scope="row">Marital&nbsp;Status<img src="images/star.gif" width="8" height="8" /></th>
								
									
									<td>
										<html:select property="maritalStatus" styleClass="text_field">
											<html:option value="">--SELECT--</html:option>
											<html:option value="Single"></html:option>
											<html:option value="Marrried"></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">Date&nbsp;of&nbsp;Birth<img src="images/star.gif" width="8" height="8" /></th>
								
									<td>
										<html:text property="dateofBirth"   styleId="date1" onfocus="popupCalender('date1')"></html:text>
									</td>
								<th  class="specalt" scope="row">Birth&nbsp;Place</th>
								
									<td>
										<html:text property="birthplace" styleClass="text_field"></html:text>
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
										<html:text property="dateofexpiryofPassport"  styleClass="text_field" 
										 styleId="date3" onfocus="popupCalender('date3')"
										  ></html:text>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">Personal&nbsp;Identification&nbsp;Marks</th>
								
									<td>
										<html:text property="personalIdentificationMarks" styleClass="text_field"></html:text>
									</td>
								
								<th  class="specalt" scope="row">Date&nbsp;of&nbsp;issue&nbsp;of&nbsp;Passport</th>
								
										<td>
										<html:text property="dateofissueofPassport"  styleClass="text_field" 
										 styleId="date2" onfocus="popupCalender('date2')"
										  ></html:text>
									</td>
								</tr>
									<tr>
									<th  class="specalt" scope="row">Physically&nbsp;Challenged</th>
								
									<td>
									<html:select property="physicallyChallenged" styleClass="text_field" >
											<html:option value="">--SELECT--</html:option>
											<html:option value="YES"></html:option>
											<html:option value="NO"></html:option>
										</html:select>
									
									</td>
								
								<th  class="specalt" scope="row">Physically&nbsp;Challenged&nbsp;Details&nbsp;</th>
								
										<td>
										<html:textarea property="physicallyChallengeddetails" cols="20" rows="3" styleClass="text_field" ></html:textarea>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row">Emergency&nbsp;Contact&nbsp;Person&nbsp;Name&nbsp;<img src="images/star.gif" width="8" height="8" /></th>
									<td>
										<html:text property="emergencyContactPersonName" styleClass="text_field" ></html:text>
									</td>
								
									<th  class="specalt" scope="row">Emergency&nbsp;Contact&nbsp;Address&nbsp;Line&nbsp;1&nbsp;
									<img src="images/star.gif" width="8" height="8" /></th>
								
										<td>
										<html:textarea property="emergencyContactAddressLine1" cols="20" rows="3" styleClass="text_field" ></html:textarea>
									</td>
								</tr>
								<tr>
									<th  class="specalt" scope="row">Emergency&nbsp;Contact&nbsp;Address&nbsp;Line&nbsp;2</th>
								
									<td>
										<html:textarea  property="emergencyContactAddressLine2" cols="20" rows="3" styleClass="text_field" ></html:textarea>
									</td>
								
									<th  class="specalt" scope="row"> Emergency&nbsp;Telephone&nbsp;Number&nbsp;</th>
								
										<td>
										<html:text property="emergencyTelephoneNumber" styleClass="text_field" ></html:text>
									</td>
								</tr>
								<tr>
								<th  class="specalt" scope="row"> Emergency&nbsp;Mobile&nbsp;Number&nbsp;</th>
								
									<td>
										<html:text property="emergencyMobileNumber" styleClass="text_field" ></html:text>
									</td>
									<th  class="specalt" scope="row"> No.of Childrens</th>
								
									<td>
										<html:text property="noOfChildrens" styleClass="text_field" ></html:text>
									</td>
									</tr>
									
							</table>
							</div>
							
							
							
							</logic:notEmpty>
							
							<%--ADDRESS DETAILS--%>
							
							<logic:notEmpty name="addressDetails">
							
							<div id="address" style="overflow-y:hidden;overflow-x:scroll;width: 100%;">
							
								
										<logic:notEmpty name="listName">
										<table border="2" align="center" width="100%">
								<thead >
					
					
						<tr class="tablerowdark1">
							
							<td  align="center" bgcolor="#51B0F8">
							
								Serial No
								
							</td>
							<td  align="center" bgcolor="#51B0F8">
							
								Address Type
								
							</td>


							<td  align="center" bgcolor="#51B0F8">
							Care Of contact Name
							</td>
							<td  align="center" bgcolor="#51B0F8">
								House Number
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Address Line1
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Address Line2
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Address Line3
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Postal Code
							</td>
							<td  align="center" bgcolor="#51B0F8">
								City
							</td>
							<td  align="center" bgcolor="#51B0F8">
								State
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Country
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Own Accomodation
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Company Housing
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Start Date
							</td>
							<td  align="center" bgcolor="#51B0F8">
								End Date
							</td>
							
							
							
						</tr>
						
					</thead>
					
								<logic:iterate name="listName" id="listid">
								<%
					        JoiningFormalityForm empForm=(JoiningFormalityForm)listid;
					        %>
                                  <tr bgcolor="white">
                                   <td>
                                   	<html:text name="listid" property="id1" value='<%=empForm.getId()%>' readonly="true"/>
 
                                  </td>
                                  <td>
                                   	<html:text name="listid" property="addressType1" value='<%=empForm.getAddressType()%>' readonly="true"/>
                                 
									</td>
                                   <td>
                           
                                   <html:text name="listid" property="careofcontactname1" value='<%=empForm.getCareofcontactname()%>' readonly="true"></html:text>
                                 
                                  </td>
                                   <td>
                                   <html:text name="listid" property="houseNumber1" value='<%=empForm.getHouseNumber()%>' readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                   <html:text name="listid" property="addressLine11" value='<%=empForm.getAddressLine1()%>' readonly="true"></html:text>
                                 
                                  </td>
                                   <td>
                                   <html:text name="listid" property="addressLine21" value='<%=empForm.getAddressLine2() %>' readonly="true"></html:text>
                                 
                                  </td>
                                   <td>
                                   <html:text name="listid" property="addressLine31" value='<%=empForm.getAddressLine3() %>' readonly="true"></html:text>
                               
                                  </td>
                                   <td>
                                   <html:text name="listid" property="postalCode1" value='<%=empForm.getPostalCode() %>' readonly="true"></html:text>
                                 
                                  </td>
                                   <td>
                                   <html:text name="listid" property="city1" value='<%=empForm.getCity() %>' readonly="true"></html:text>
                               
                                  </td>
                                   <td>
                                    <html:text name="listid" property="state1" value='<%=empForm.getState() %>' readonly="true"></html:text>
										
									</td>
                                  <td>
                                  <html:text name="listid" property="country1" value='<%=empForm.getCountry()%>' readonly="true"></html:text>
										
									</td>
                                   <td>
                                    <html:text name="listid" property="ownAccomodation1" value='<%=empForm.getOwnAccomodation() %>' readonly="true"></html:text>
										
											
									</td>
                                  <td>
                                   <html:text name="listid" property="companyHousing1" value='<%=empForm.getCompanyHousing() %>' readonly="true"></html:text>
										
									</td>
                                   <td>
										<html:text name="listid" property="addStartDate1" value='<%=empForm.getAddStartDate() %>' readonly="true"/>
										 
									</td>
                                   <td>
										<html:text name="listid" property="addEndDate1" value='<%=empForm.getAddEndDate() %>' readonly="true"/>
									</td>
                                  
										
											
									</tr>
	
									</logic:iterate>
			
								</table>
								</logic:notEmpty>
								</div>
							
							</logic:notEmpty>
							
							<%--FAMILY DETAILS--%>
							
							<logic:notEmpty name="familyDetails">
							<div id="familyDetails" style="overflow-y:hidden;overflow-x:scroll;">
							
							
							<logic:notEmpty name="listName">
										<table border="2" align="center" >
								<thead >
					
					
						<tr align="center" bgcolor="light blue">
						<td  align="center" bgcolor="#51B0F8">
							
								Serial No
								
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Relationship
							</td>


							<td  align="center" bgcolor="#51B0F8">
							Title
							</td>
							<td  align="center" bgcolor="#51B0F8">
								First&nbsp;Name
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Middle&nbsp;Name
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Last&nbsp;Name
							</td>
							<td  align="center" bgcolor="#51B0F8">
									Initials
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Gender&nbsp;
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Date&nbsp;of&nbsp;Birth
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Birth&nbsp;Place
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Telephone&nbsp;Number&nbsp;
							</td>
							<td  align="center" bgcolor="#51B0F8">
							Mobile&nbsp;Number&nbsp;
							</td>
							<td  align="center" bgcolor="#51B0F8">
								E-Mail&nbsp;Address&nbsp;
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Blood&nbsp;Group&nbsp;
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Dependent&nbsp;
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Employee&nbsp;of&nbsp;Company&nbsp;
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Employee&nbsp;Number&nbsp;
							</td>
							
							
							
						</tr>
						
					</thead>
					
								<logic:iterate name="listName" id="listid">
								<%
					        JoiningFormalityForm empForm=(JoiningFormalityForm)listid;
					        %>
                                  <tr bgcolor="white">
                                   <td>
                                   <html:text name="listid"  property="id1" value='<%=empForm.getId() %>' readonly="true"></html:text>
                                 
                                  </td>
                                  <td>
                                  <html:text name="listid"  property="familyDependentTypeID1" value='<%=empForm.getFamilyDependentTypeID() %>' readonly="true"></html:text>
										
									</td>
                                  <td>
                                   <html:text name="listid"  property="ftitle1" value='<%=empForm.getFtitle()%>' readonly="true"></html:text>
										
									</td>
                                   <td>
                                   <html:text name="listid" property="ffirstName1" value='<%=empForm.getFfirstName()%>'  readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                   <html:text name="listid" property="fmiddleName1" value='<%=empForm.getFmiddleName()%>'  readonly="true"></html:text>
                                 
                                  </td>
                                   <td>
                                   <html:text name="listid" property="flastName1" value='<%=empForm.getFlastName()%>'  readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                   <html:text name="listid" property="finitials1" value='<%=empForm.getFinitials() %>'  readonly="true"></html:text>
                                  
                                  </td>
                                 <td>
                                  <html:text name="listid" property="fgender1" value='<%=empForm.getFgender() %>'  readonly="true"></html:text>
									
									</td>
                                   <td>
                                   <html:text name="listid" property="fdateofBirth1" value='<%=empForm.getFdateofBirth() %>' readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                   <html:text name="listid" property="fbirthplace1" value='<%=empForm.getFbirthplace() %>'  readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                   <html:text name="listid" property="ftelephoneNumber1" value='<%=empForm.getFtelephoneNumber() %>'  readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                   <html:text name="listid" property="fmobileNumber1" value='<%=empForm.getFmobileNumber()%>'  readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                    <html:text name="listid" property="femailAddress1" value='<%=empForm.getFemailAddress() %>'  readonly="true"></html:text>
                                  
                                  </td>
                                   <td>
                                    <html:text name="listid" property="fbloodGroup1" value='<%=empForm.getFbloodGroup() %>'  readonly="true"></html:text>
										
											
									</td>
                                  <td>
                                   <html:text name="listid" property="fdependent1" value='<%=empForm.getFdependent() %>'  readonly="true"></html:text>
										
									</td>
                                 <td>
                                  <html:text name="listid" property="femployeeofCompany1" value='<%=empForm.getFemployeeofCompany() %>'  readonly="true"></html:text>
										
									</td>
                                   <td>
                                   <html:text name="listid" property="femployeeNumber1" value='<%=empForm.getFemployeeNumber()%>'  readonly="true"></html:text>
                                 
                                  </td>
                                  
											
									</tr>
	
									</logic:iterate>
						
								</table>
								</logic:notEmpty>
							</div>
							
							</logic:notEmpty>
							<%--EDUCATIONAL DETAILS--%>
							
							
							
							<logic:notEmpty name="educationDetails">
							<div id="educationDetails" style="overflow-y:hidden;overflow-x:scroll;">
							
									
										<logic:notEmpty name="listName">
										<table border="2" align="center" >
								<thead >
						<tr align="center" bgcolor="light blue">
						<td  align="center" bgcolor="#51B0F8">
							
								Serial No
								
							</td>
							<td  align="center" bgcolor="#51B0F8">
								EducationalLevel
							</td>


							<td  align="center" bgcolor="#51B0F8">
							Qualification
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Specialization
							</td>
							<td  align="center" bgcolor="#51B0F8">
								university Name
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Univerysity Location
							</td>
							<td  align="center" bgcolor="#51B0F8">
								State
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Country
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Duration of Course
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Times
							</td>
							<td  align="center" bgcolor="#51B0F8">
								From Date
							</td>
							<td  align="center" bgcolor="#51B0F8">
								To Date
							</td>
							<td  align="center" bgcolor="#51B0F8">
								FullTime/PartTime
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Percentage
							</td>
							<td  align="center" bgcolor="#51B0F8">
								<input type="checkbox" name="checkEducation" onclick="checkAllEducation()"/>
							</td>
							
						</tr>
						
					</thead>
	
								<logic:iterate name="listName" id="listid">
								<%
					        JoiningFormalityForm empForm=(JoiningFormalityForm)listid;
					        %>
                                  <tr bgcolor="white">
                                   <td>
                                   <html:text name="listid" property="id1" value='<%=empForm.getId() %>' readonly="true"></html:text>
                                  
                                  </td>
                                  <td>
                                   <html:text name="listid" property="educationalLevel1" value='<%=empForm.getEducationalLevel() %>' readonly="true"></html:text>
										
											
									</td>                
									                  <td>
									                   <html:text name="listid" property="qualification1" value='<%=empForm.getQualification() %>' readonly="true"></html:text>
										
									</td>
                                   
									<td>
										<html:text name="listid" property="specialization1" value='<%=empForm.getSpecialization() %>' readonly="true"></html:text>
									</td>
								
									<td>
										<html:text name="listid" property="universityName1" value='<%=empForm.getUniversityName() %>' readonly="true"></html:text>
									</td>
								
									<td>
										<html:text name="listid" property="univerysityLocation1" value='<%=empForm.getUniverysityLocation() %>' readonly="true"></html:text>
									</td>
									
									<td>
									 <html:text name="listid" property="edstate1" value='<%=empForm.getEdstate() %>' readonly="true"></html:text>
										
									</td>
								
								<td>
								 <html:text name="listid" property="edcountry1" value='<%=empForm.getEdcountry() %>' readonly="true"></html:text>
										
									</td>
									
									<td>
										<html:text name="listid" property="durationofCourse1" value='<%=empForm.getDurationofCourse() %>' readonly="true"></html:text>
									</td>
								
								<td>
								 <html:text name="listid" property="times1" value='<%=empForm.getTimes() %>' readonly="true"></html:text>
										
									</td>
									
									<td>
										<html:text name="listid" property="fromDate1" value='<%=empForm.getFromDate() %>' readonly="true"/>
										
									</td>
								
								
									<td>
									<html:text name="listid" property="toDate1" value='<%=empForm.getToDate() %>' readonly="true" />
										
									</td>
									
									<td>
									 <html:text name="listid" property="id1" value='<%=empForm.getId() %>' readonly="true"></html:text>
										<html:select name="listid" property="fullTimePartTime1" value='<%=empForm.getFullTimePartTime() %>' >
											<html:option value="">--SELECT--</html:option>
											<html:option value="FullTime"></html:option>
											<html:option value="PartTime"></html:option>
											
										</html:select>
									</td>
								
									<td>
										<html:text name="listid" property="percentage1" value='<%=empForm.getPercentage() %>' readonly="true"></html:text>
									</td>
									
										
									</tr>
	
									</logic:iterate>
								</table>
								</logic:notEmpty>
									</div>
									</logic:notEmpty>
									<%--EXPERIENCE DETAILS--%>
							
							<logic:notEmpty name="experienceDetails">
							<div id="experience" style="overflow-y:hidden;overflow-x:scroll;">
							
							<logic:notEmpty name="listName">
										<table border="2" align="center" >
								<thead >
						<tr align="center" bgcolor="light blue">
						<td  align="center" bgcolor="#51B0F8">
							
								Serial No
								
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Name Of Employer
							</td>

							<td  align="center" bgcolor="#51B0F8">
							Industry
							</td>
							<td  align="center" bgcolor="#51B0F8">
								City
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Country
							</td>
							<td  align="center" bgcolor="#51B0F8">
								PositionHeld
							</td>
							<td  align="center" bgcolor="#51B0F8">
								JobRole
							</td>
						<td  align="center" bgcolor="#51B0F8">
								Start Date
							</td>
							<td  align="center" bgcolor="#51B0F8">
								End date
							</td>
							<td  align="center" bgcolor="#51B0F8">
								<input type="checkbox" name="checkExperience" onclick="checkAllExperience()"/>
							</td>
							
						</tr>
						
					</thead>
	
								<logic:iterate name="listName" id="listid">
								<%
					        JoiningFormalityForm empForm=(JoiningFormalityForm)listid;
					        %>
                                  <tr bgcolor="white">
                                  <td>
                                   <html:text name="listid" property="id1" value='<%=empForm.getId() %>' readonly="true"></html:text>
                                  
                                  </td>
                                  
									<td>
										<html:text name="listid" property="nameOfEmployer1" value='<%=empForm.getNameOfEmployer() %>' readonly="true"></html:text>
									</td>
									
									<td>
										<html:select  name="listid" property="industry1" value='<%=empForm.getIndustry() %>' >
											<html:option value="">--SELECT--</html:option>
											<html:option value="IT"></html:option>
											<html:option value="Management"></html:option>
											
										</html:select>
									</td>
								
									<td>
										<html:text name="listid" property="exCity1" value='<%=empForm.getExCity() %>' readonly="true"></html:text>
									</td>
									
									<td>
										<html:select name="listid" property="excountry1" value='<%=empForm.getExcountry() %>' >
											<html:option value="">--SELECT--</html:option>
											<html:option value="India"></html:option>
											<html:option value="Spain"></html:option>
											
										</html:select>
									</td>
								
									<td>
										<html:text property="positionHeld1" name="listid" value='<%=empForm.getPositionHeld() %>' readonly="true"></html:text>
									</td>
									
									<td>
									<html:text property="jobRole1" name="listid" value='<%=empForm.getJobRole() %>' readonly="true"></html:text>
									</td>
								
									<td>
										<html:text property="startDate1" name="listid" value='<%=empForm.getStartDate() %>' readonly="true"/>
									</td>
									
									<td>
									<html:text property="endDate1" name="listid" value='<%=empForm.getEndDate() %>' readonly="true"/>
									</td>
							
                                   
										
											
									</tr>
	
									</logic:iterate>
								</table>
								</logic:notEmpty>
							</div>
							
							</logic:notEmpty>
						
						<logic:notEmpty name="languageDetails">
							<div id="language" style="overflow-y:hidden;overflow-x:scroll;">
							
							<logic:notEmpty name="listName">
										<table border="2" align="center" >
								<thead >
						<tr align="center" bgcolor="light blue">
				
				<td  align="center" bgcolor="#51B0F8">
							
								Serial No
								
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Language
							</td>


							<td  align="center" bgcolor="#51B0F8">
							Mother Tongue
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Speaking
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Reading
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Writing
							</td>
							<td  align="center" bgcolor="#51B0F8">
								Start Date
							</td>
							<td  align="center" bgcolor="#51B0F8">
								End Date
							</td>
							<td  align="center" bgcolor="#51B0F8">
								<input type="checkbox" name="checkLanguage" onclick="checkAllLangauge()"/>
							</td>
							
						</tr>
						
					</thead>
	
								<logic:iterate name="listName" id="listid">
								<%
					        JoiningFormalityForm empForm=(JoiningFormalityForm)listid;
					        %>
                                  <tr bgcolor="white">
                                  <td>
                                  <html:text name="listid" property="id1" value='<%=empForm.getId() %>' readonly="true"></html:text>
                                 
                                  </td>
                                 
									<td>
										<html:select property="language1" name="listid" value='<%=empForm.getLanguage() %>' >
											<html:option value=" Kannada">--SELECT--</html:option>
											<html:option value="English"></html:option>
											<html:option value="Hindi"></html:option>
											
										</html:select>
									</td>
								
							
									<td>
										<html:select property="motherTongue1" name="listid" value='<%=empForm.getMotherTongue() %>' >
											<html:option value="">--SELECT--</html:option>
											<html:option value="YES"></html:option>
											<html:option value="NO"></html:option>
											
										</html:select>
									</td>
								
							
									<td>
										<html:select property="langSpeaking1" name="listid" value='<%=empForm.getLangSpeaking() %>' >
											<html:option value="">--SELECT--</html:option>
											<html:option value="Poor"></html:option>
											<html:option value="Good"></html:option>
											
										</html:select>
									</td>
									
									<td>
										<html:select property="langRead1" name="listid" value='<%=empForm.getLangRead() %>' >
											<html:option value="">--SELECT--</html:option>
											<html:option value="Poor"></html:option>
											<html:option value="Good"></html:option>
											
										</html:select>
									</td>
									
									<td>
										<html:select property="langWrite1" name="listid" value='<%=empForm.getLangWrite() %>' >
											<html:option value="">--SELECT--</html:option>
											<html:option value="Poor"></html:option>
											<html:option value="Good"></html:option>
											
										</html:select>
									</td>
								
							
									
										<td>
										<html:text property="langstartDate1" name="listid" value='<%=empForm.getLangstartDate() %>' readonly="true"/>
									</td>
									
								
									
										<td>
										<html:text property="langendDate1" name="listid" value='<%=empForm.getLangendDate() %>' readonly="true"/>
									</td>
								
                                   
											
									</tr>
	
									</logic:iterate>
									
								</table>
								</logic:notEmpty>
							</div>
							
							</logic:notEmpty>
	
            </html:form>
						</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
  
                

</body>
</html>
