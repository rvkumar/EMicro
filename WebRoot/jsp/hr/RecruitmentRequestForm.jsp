
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
<jsp:directive.page import="com.microlabs.hr.form.HRRecruitmentForm"/>



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
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
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

function doSubmit(){
	//var lName = document.getElementById("selectedId").value;
	var sData = $("#requestForm").serialize();
	$.ajax({
            type: "POST",
            url: "hrRecruitment.do?method=saveRecruitmentDetails&sId=Recruitment Request&id='Recruitment Request'",
            data: sData,
            success: function(successMsg) {
            		document.getElementById("successful").style.display="";
            		document.getElementById("successful").innerHTML = "Request uploaded successfully!";
					$("#successful").fadeOut(5000);
					document.getElementById("requestForm").reset();
            } 
        });
}

//-->
</script>
<style type="text/css">
.thstyle{
width: 250px;
background:#d1e8f4 url(../images/bullet1.gif) no-repeat;
}
.tdstyle{
width:250px;
}

.successStyle{
padding:2px 4px;
margin:0px;
border:solid 1px #FBD3C6;
border-radius: 5%;
background:#FDE4E1;
color:#CB4721;
font-family:Arial, Helvetica, sans-serif;
font-size:14px;
font-weight:bold;
text-align:center;
}

</style>
</head>

<body >
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
	<div style="padding-left: 10px;width: 100%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->

                <html:form action="/hrRecruitment.do" enctype="multipart/form-data" styleId="requestForm">
				
					<table id="NewTable" style="border:0px;">
						<tr class="tablerowdark1">
							<th colspan="2" class="thstyle"><center> Recruitment&nbsp;Request</center></th><td style="text-align:center; display:none;" id="successful" class="successStyle"><bean:write name="hrRecruitmentForm" property="message"/></td>
						</tr>
						<tr><td colspan="2" class="underline"></td></tr>
						<tr>
							<th class="thstyle" scope="row">Recruitment Id</th>
							<td>
								<html:text property="recuritmentID"  styleClass="text_field tdstyle"></html:text>
							</td>
							
						</tr>							
						<tr>
							<th class="thstyle" scope="row">Title</th>
							<td>
								<html:text property="jobTitle"  styleClass="text_field tdstyle"></html:text>
							</td>
							
						</tr>
						<tr>
							<th class="thstyle" scope="row">Department</th>
							<td>
								<html:text property="department" styleClass="text_field tdstyle"></html:text>
							</td>
						</tr>
						<tr>
							<th  class="thstyle" scope="row">Primary&nbsp;Location<img src="images/star.gif" width="8" height="8" /></th>	
							<td>
								<html:text property="primaryLocation" styleClass="text_field  tdstyle"></html:text>
							</td>
							
						</tr>
						<tr>
							<th  class="thstyle" scope="row">Other Locations</th>
									
							<td>
								<html:text property="otherLocation" styleClass="text_field  tdstyle"></html:text>
							</td>
						</tr>
						<tr>
							<th  class="thstyle" scope="row">Employee Status</th>
								
							<td><html:select property="empStatus" styleClass="text_field tdstyle">
									    <option value="Regular">Regular</option>
									    <option value="Contract">Contract</option>
									    <option value="Consultant">Consultant</option>
								 </html:select>
							</td>
						</tr>
							
							
						<tr>
							<th class="thstyle" scope="row">Number of Openings</th>
							<td><html:text property="totalEmp" styleClass="text_field tdstyle"></html:text></td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Shift (If Applicable)</th>
													
							<td><html:select property="shiftType" styleClass="text_field tdstyle">
									    <option value="No">No</option>
									    <option value="Yes">Yes</option>
								</html:select>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Job Description</th>
								
							<td>
								<html:text property="jobDescription" styleId="date1" styleClass="text_field tdstyle"></html:text>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Qualifications</th>
								
							<td>
								<html:text property="qualifications" styleClass="text_field tdstyle"></html:text>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Experience</th>
								
							<td>
								<html:text property="experience" styleClass="text_field tdstyle"></html:text> <span>&nbsp;+yrs</span>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Industry Type</th>
								
							<td>
								<html:text property="industryType" styleClass="text_field tdstyle"></html:text>
							</td>
						</tr>
						<tr>
							<th class="thstyle" scope="row">Salary Offered</th>
								
							<td>
								<html:text property="saleryOffered" styleClass="text_field tdstyle"></html:text>
								<em>CTC in Lakhs</em>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input id="submitForm" class="sudmit_btn" style="height:20px;" value="Upload Request" onmousedown="doSubmit();" /></td>
						</tr>
												
					</table>
						
            </html:form>

                
	</div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
  
                

</body>
</html>
