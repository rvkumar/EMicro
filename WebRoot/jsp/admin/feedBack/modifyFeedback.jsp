
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

<%--<link rel="stylesheet" type="text/css" href="css/styles.css" />--%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	
	<script type="text/javascript" src="js/sorttable.js"></script>

<title>eMicro :: Modify Feedback</title>
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

function sendReply(){
var url="feedBack.do?method=sendReply";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function previousRecord()
{
var url="feedBack.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function nextRecord()
{
var url="feedBack.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function closefeedback()
{
var url="feedBack.do?method=modifyFeedback";
			document.forms[0].action=url;
			document.forms[0].submit();
}

//-->
</script>

</head>

<body>
<br/>
				<div align="center">
					<logic:present name="feedbackForm" property="message">
						Message : <font color="red" size="+1">
							<bean:write name="feedbackForm" property="message" />
							<br/>
						</font>
					</logic:present>
				</div>
				
	<html:form action="/feedBack.do" enctype="multipart/form-data">
		<logic:notEmpty name="empfeedback">
			<div style="width: 80%">
			<table class="bordered">
				<tr>
					<th colspan="2" style="text-align: center;">Feedback Review</th>
				</tr>
				<tr>
					<td>Sl.No</td><td><html:text property="sno"/></td>
				</tr>
				<tr>
					<td>No</td><td><html:text property="userId"/></td>
				</tr>
				<tr>
					<td>Name</td><td><html:text property="userName"/></td>
				</tr>
				<tr>
					<td>Subject</td><td><html:text property="subject"/></td>
				</tr>
				<tr>
					<td>Comments</td><td><html:textarea property="comment" style="height:200px;width:100%;" rows="4"/></td>
				</tr>
				<tr>
				<td>Approve Status</td>
					<td colspan="2"><html:select property="approveStatus">
							<html:option value="Pending"/>
							<html:option value="Approved"/>
							<html:option value="Reject"/>
						</html:select>
					</td>
				</tr>
				<tr>
				<td colspan="2" style="text-align: center;">
					<html:button property="method" styleClass="rounded" value="Reply" style="width: 100px" onclick="sendReply()"></html:button>
				<html:button property="method" styleClass="rounded" value="Close" style="width: 100px" onclick="closefeedback()"></html:button>
				</td>
				</tr>
			</table>
			</div>
		</logic:notEmpty>
	
		<logic:notEmpty name="feedBackList">
			<div align="center">  
					
							<logic:notEmpty name="displayRecordNo">
	 							<logic:notEmpty name="veryFirst">
	 								&nbsp;<a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>&nbsp;
	 							</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<a href="#"><img src="images/disableLeft.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="feedbackForm"/>&nbsp;-&nbsp;
									<bean:write property="endRecord"  name="feedbackForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
								
						
								</logic:notEmpty>
								</div>
								
	<html:hidden property="totalRecords" name="feedbackForm"/>
	<html:hidden property="startRecord" name="feedbackForm"/>
	<html:hidden property="endRecord" name="feedbackForm"/>
			
			<div class="bordered">
			<table class="sortable" width="100%">
				<tr>
					<th colspan="6"><center><big>Feedback Review</big></center></th>
				</tr>
					<tr>
					<th>Sl.No</th><th>No </th><th>Name </th><th>Subject</th><th>Comments</th><th>Status</th>
					</tr>
					<logic:iterate id="feedBackID" name="feedBackList">
						<tr>
							<td><a href="feedBack.do?method=getEmpFeedBack&sno=<bean:write name="feedBackID"  property="sno"/>"><bean:write name="feedBackID"  property="sno"/></a></td>
							<td><bean:write name="feedBackID"  property="userId"/></td>
							<td><bean:write name="feedBackID"  property="userName"/></td>
							<td><bean:write name="feedBackID"  property="subject"/></td>
							<td><bean:write name="feedBackID"  property="comment"/></td>
							<td><bean:write name="feedBackID"  property="approveStatus"/></td>
						</tr>
					</logic:iterate>
			</table>
			</div>
		</logic:notEmpty>
		
	
		<logic:notEmpty name="noRecords">
			<table class="sortable bordered" width="100%">
				<tr>
					<th colspan="6"><center><big>Feedback Review</big></center></th>
				</tr>
					<tr>
					<th>Sl.No</th><th>No </th><th>Name </th><th>Subject</th><th>Comments</th><th>Status</th>
					</tr>
					<tr><td colspan="6"><center><font color="red">search details are not found</font></center></td></tr>
					</table>
			</logic:notEmpty>
	</html:form>
</body>
</html>