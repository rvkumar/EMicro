<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />


	<style>
#myProgress {
  position: relative;
  width: 100%;
  height: 30px;
  background-color: grey;
  
  
  background: repeating-linear-gradient(
  45deg,
  #606dbc,
  rgb(18,123,202) 10px,
  #465298 10px,
  #465298 20px
);
}

#myBar {
  position: absolute;
 
  height: 100%;
  background-color:rgb(18,123,202);
}

#label {
  text-align: center;
  line-height: 30px;
  color: #0e150b;
}

#squaresWaveG{
	position:relative;
	width:234px;
	height:28px;
	margin:auto;
}

.squaresWaveG{
	position:absolute;
	top:0;
	background-color:rgb(18,123,202);
	width:28px;
	height:28px;
	animation-name:bounce_squaresWaveG;
		-o-animation-name:bounce_squaresWaveG;
		-ms-animation-name:bounce_squaresWaveG;
		-webkit-animation-name:bounce_squaresWaveG;
		-moz-animation-name:bounce_squaresWaveG;
	animation-duration:1.5s;
		-o-animation-duration:1.5s;
		-ms-animation-duration:1.5s;
		-webkit-animation-duration:1.5s;
		-moz-animation-duration:1.5s;
	animation-iteration-count:infinite;
		-o-animation-iteration-count:infinite;
		-ms-animation-iteration-count:infinite;
		-webkit-animation-iteration-count:infinite;
		-moz-animation-iteration-count:infinite;
	animation-direction:normal;
		-o-animation-direction:normal;
		-ms-animation-direction:normal;
		-webkit-animation-direction:normal;
		-moz-animation-direction:normal;
}

#squaresWaveG_1{
	left:0;
	animation-delay:0.6s;
		-o-animation-delay:0.6s;
		-ms-animation-delay:0.6s;
		-webkit-animation-delay:0.6s;
		-moz-animation-delay:0.6s;
}

#squaresWaveG_2{
	left:29px;
	animation-delay:0.75s;
		-o-animation-delay:0.75s;
		-ms-animation-delay:0.75s;
		-webkit-animation-delay:0.75s;
		-moz-animation-delay:0.75s;
}

#squaresWaveG_3{
	left:58px;
	animation-delay:0.9s;
		-o-animation-delay:0.9s;
		-ms-animation-delay:0.9s;
		-webkit-animation-delay:0.9s;
		-moz-animation-delay:0.9s;
}

#squaresWaveG_4{
	left:88px;
	animation-delay:1.05s;
		-o-animation-delay:1.05s;
		-ms-animation-delay:1.05s;
		-webkit-animation-delay:1.05s;
		-moz-animation-delay:1.05s;
}

#squaresWaveG_5{
	left:117px;
	animation-delay:1.2s;
		-o-animation-delay:1.2s;
		-ms-animation-delay:1.2s;
		-webkit-animation-delay:1.2s;
		-moz-animation-delay:1.2s;
}

#squaresWaveG_6{
	left:146px;
	animation-delay:1.35s;
		-o-animation-delay:1.35s;
		-ms-animation-delay:1.35s;
		-webkit-animation-delay:1.35s;
		-moz-animation-delay:1.35s;
}

#squaresWaveG_7{
	left:175px;
	animation-delay:1.5s;
		-o-animation-delay:1.5s;
		-ms-animation-delay:1.5s;
		-webkit-animation-delay:1.5s;
		-moz-animation-delay:1.5s;
}

#squaresWaveG_8{
	left:205px;
	animation-delay:1.64s;
		-o-animation-delay:1.64s;
		-ms-animation-delay:1.64s;
		-webkit-animation-delay:1.64s;
		-moz-animation-delay:1.64s;
}



@keyframes bounce_squaresWaveG{
	0%{
		background-color:rgb(18,123,202);
	}

	100%{
		background-color:rgb(255,255,255);
	}
}

@-o-keyframes bounce_squaresWaveG{
	0%{
		background-color:rgb(18,123,202);
	}

	100%{
		background-color:rgb(255,255,255);
	}
}

@-ms-keyframes bounce_squaresWaveG{
	0%{
		background-color:rgb(18,123,202);
	}

	100%{
		background-color:rgb(255,255,255);
	}
}

@-webkit-keyframes bounce_squaresWaveG{
	0%{
		background-color:rgb(18,123,202);
	}

	100%{
		background-color:rgb(255,255,255);
	}
}

@-moz-keyframes bounce_squaresWaveG{
	0%{
		background-color:rgb(18,123,202);
	}

	100%{
		background-color:rgb(255,255,255);
	}
}

</style>
</head>
<body>
<BR/><BR/>
<html:form action="hrApprove" enctype="multipart/form-data">
<html:hidden property="lock_id" name="hrApprovalForm" /> 
<html:hidden property="progress" name="hrApprovalForm" />
<div style="width: 50%;margin-left: 260px"><center>
<table class="bordered"><tr><th colspan="6"><center>Attendance Processing</center></th></tr>
<tr><td style="background-color: white;">From </td><td style="background-color: white;">${hrApprovalForm.fromDate} </td><td style="background-color: white;">To </td><td style="background-color: white;">${hrApprovalForm.toDate}</td></tr>
<tr><td style="background-color: white;">Total Employees </td><td style="background-color: white;" colspan="3">${hrApprovalForm.emp_count} </td></tr>


<tr><th>Process Name </th><th>Start Time </th><th>End Time</th><th>Progress </th></tr>
<tr><td style="background-color: white;">Fetching Punch Records</td><td style="background-color: white;">${hrApprovalForm.fetch_st}</td><td style="background-color: white;">${hrApprovalForm.fetch_et}</td>

<logic:empty  name="hrApprovalForm" property="fetch_st">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:0%" >
    <div id="label">0% </div>
  </div>
</div></td>
</logic:empty>

<logic:notEmpty name="hrApprovalForm" property="fetch_st">
<logic:empty name="hrApprovalForm" property="fetch_et">
<td style="background-color: white;">  <div id="squaresWaveG">
	<div id="squaresWaveG_1" class="squaresWaveG"></div>
	<div id="squaresWaveG_2" class="squaresWaveG"></div>
	<div id="squaresWaveG_3" class="squaresWaveG"></div>
	<div id="squaresWaveG_4" class="squaresWaveG"></div>
	<div id="squaresWaveG_5" class="squaresWaveG"></div>
	<div id="squaresWaveG_6" class="squaresWaveG"></div>
	<div id="squaresWaveG_7" class="squaresWaveG"></div>
	<div id="squaresWaveG_8" class="squaresWaveG"></div>
</div></td>
</logic:empty>
</logic:notEmpty>

<logic:notEmpty name="hrApprovalForm" property="fetch_st">
<logic:notEmpty name="hrApprovalForm" property="fetch_et">
<td style="background-color: white;"> <div id="myProgress" >  <div id="myBar" style="width:100%" >
    <div id="label">100% </div>
  </div>
</div></td>
</logic:notEmpty>
</logic:notEmpty>

</tr>

<tr><td style="background-color: white;">Leave and Onduty Updation</td><td style="background-color: white;">${hrApprovalForm.leave_st }</td><td style="background-color: white;">${hrApprovalForm.leave_et}</td>
<logic:empty  name="hrApprovalForm" property="leave_st">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:0%" >
    <div id="label">0% </div>
  </div>
</div></td>
</logic:empty>

<logic:notEmpty name="hrApprovalForm" property="leave_st">
<logic:empty name="hrApprovalForm" property="leave_et">
<td style="background-color: white;"> <div id="squaresWaveG">
	<div id="squaresWaveG_1" class="squaresWaveG"></div>
	<div id="squaresWaveG_2" class="squaresWaveG"></div>
	<div id="squaresWaveG_3" class="squaresWaveG"></div>
	<div id="squaresWaveG_4" class="squaresWaveG"></div>
	<div id="squaresWaveG_5" class="squaresWaveG"></div>
	<div id="squaresWaveG_6" class="squaresWaveG"></div>
	<div id="squaresWaveG_7" class="squaresWaveG"></div>
	<div id="squaresWaveG_8" class="squaresWaveG"></div>
</div></td>
</logic:empty>
</logic:notEmpty>

<logic:notEmpty name="hrApprovalForm" property="leave_st">
<logic:notEmpty name="hrApprovalForm" property="leave_et">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:100%" >
    <div id="label">100% </div>
  </div>
</div></td>
</logic:notEmpty>
</logic:notEmpty>

</tr>
<tr><td style="background-color: white;">Punch Records Updation</td><td style="background-color: white;">${hrApprovalForm.punch_st }</td><td style="background-color: white;">${hrApprovalForm.punch_et}</td>
<logic:empty  name="hrApprovalForm" property="punch_st">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:0%" >
    <div id="label">0% </div>
  </div>
</div></td>
</logic:empty>

<logic:notEmpty name="hrApprovalForm" property="punch_st">
<logic:empty name="hrApprovalForm" property="punch_et">
<td style="background-color: white;"> <div id="squaresWaveG">
	<div id="squaresWaveG_1" class="squaresWaveG"></div>
	<div id="squaresWaveG_2" class="squaresWaveG"></div>
	<div id="squaresWaveG_3" class="squaresWaveG"></div>
	<div id="squaresWaveG_4" class="squaresWaveG"></div>
	<div id="squaresWaveG_5" class="squaresWaveG"></div>
	<div id="squaresWaveG_6" class="squaresWaveG"></div>
	<div id="squaresWaveG_7" class="squaresWaveG"></div>
	<div id="squaresWaveG_8" class="squaresWaveG"></div>
</div></td>
</logic:empty>
</logic:notEmpty>

<logic:notEmpty name="hrApprovalForm" property="punch_st">
<logic:notEmpty name="hrApprovalForm" property="punch_et">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:100%" >
    <div id="label">100% </div>
  </div>
</div></td>
</logic:notEmpty>
</logic:notEmpty>
</tr>
<tr><td style="background-color: white;">Manual entry,Comp Off,Week Off,Reports</td><td style="background-color: white;">${hrApprovalForm.manual_st}</td><td style="background-color: white;">${hrApprovalForm.rep_et}</td>
<logic:empty  name="hrApprovalForm" property="manual_st">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:0%" >
    <div id="label">0% </div>
  </div>
</div></td>
</logic:empty>

<logic:notEmpty name="hrApprovalForm" property="manual_st">
<logic:empty name="hrApprovalForm" property="rep_et">
<td style="background-color: white;"><div id="squaresWaveG">
	<div id="squaresWaveG_1" class="squaresWaveG"></div>
	<div id="squaresWaveG_2" class="squaresWaveG"></div>
	<div id="squaresWaveG_3" class="squaresWaveG"></div>
	<div id="squaresWaveG_4" class="squaresWaveG"></div>
	<div id="squaresWaveG_5" class="squaresWaveG"></div>
	<div id="squaresWaveG_6" class="squaresWaveG"></div>
	<div id="squaresWaveG_7" class="squaresWaveG"></div>
	<div id="squaresWaveG_8" class="squaresWaveG"></div>
</div></td>
</logic:empty>
</logic:notEmpty>

<logic:notEmpty name="hrApprovalForm" property="manual_st">
<logic:notEmpty name="hrApprovalForm" property="rep_et">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:100%" >
    <div id="label">100% </div>
  </div>
</div></td>
</logic:notEmpty>
</logic:notEmpty>
</tr>
<%-- <tr><td style="background-color: white;">Week Off </td><td style="background-color: white;">${hrApprovalForm.week_st }</td><td style="background-color: white;">${hrApprovalForm.week_et}</td>
<logic:empty  name="hrApprovalForm" property="week_st">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:0%" >
    <div id="label">0% </div>
  </div>
</div></td>
</logic:empty>

<logic:notEmpty name="hrApprovalForm" property="week_st">
<logic:empty name="hrApprovalForm" property="week_et">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:15%" >
    <div id="label">15% </div>
  </div>
</div></td>
</logic:empty>
</logic:notEmpty>

<logic:notEmpty name="hrApprovalForm" property="week_st">
<logic:notEmpty name="hrApprovalForm" property="week_et">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:100%" >
    <div id="label">100% </div>
  </div>
</div></td>
</logic:notEmpty>
</logic:notEmpty>
</tr>
<tr><td style="background-color: white;">Reports</td><td style="background-color: white;">${hrApprovalForm.rep_st}</td><td style="background-color: white;">${hrApprovalForm.rep_et}</td>
<logic:empty  name="hrApprovalForm" property="rep_st">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:0%" >
    <div id="label">0% </div>
  </div>
</div></td>
</logic:empty>

<logic:notEmpty name="hrApprovalForm" property="rep_st">
<logic:empty name="hrApprovalForm" property="rep_et">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:15%" >
    <div id="label">15% </div>
  </div>
</div></td>
</logic:empty>
</logic:notEmpty>

<logic:notEmpty name="hrApprovalForm" property="rep_st">
<logic:notEmpty name="hrApprovalForm" property="rep_et">
<td style="background-color: white;"><div id="myProgress" >  <div id="myBar" style="width:100%" >
    <div id="label">100% </div>
  </div>
</div></td>
</logic:notEmpty>
</logic:notEmpty>
</tr> --%>
<tr><td style="background-color: white;" >Total</td><td style="background-color: white;" colspan="3"><div id="myProgress" >  <div id="myBar" style="width:${hrApprovalForm.progress}%" >
    <div id="label">${hrApprovalForm.progress}% </div>
  </div>
</div></td></tr>
</table>
<br/>
</center>
</div>
</html:form>
</body>
</html>