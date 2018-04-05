
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


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>Modify Feedback</title>
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

//-->
</script>
</head>

<body >
		<!--------WRAPER STARTS -------------------->
<div id="wraper">

           
                <div style="padding-left: 10px;width: 70%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
                    <div>
					
					
				<div align="center">
					<logic:present name="feedbackForm" property="message">
						<font color="red">
							<bean:write name="feedbackForm" property="message" />
						</font>
					</logic:present>
				</div>
				
				
					
			<html:form action="/feedBack.do" enctype="multipart/form-data">
			<logic:notEmpty name="empfeedback">
			<table border="1" id='mytable1'>
			<th  class="specalt" scope="row">
			S.No</th><td><html:text property="sno"/></td>

			<tr>
			<th  class="specalt" scope="row">Employee Id</th><td><html:text property="userId"/></td>
			</tr>
			<tr>
			<th  class="specalt" scope="row">Employee Name</th><td><html:text property="userName"/></td>
			</tr>
			<tr>
			<th  class="specalt" scope="row">Subject</th><td><html:text property="subject"/></td>
			</tr><tr>
			<th  class="specalt" scope="row">Comments</th><td><html:textarea property="comment" cols="45" rows="4"/></td>
			</tr><tr>
			<th  class="specalt" scope="row">Approve Status</th>
			<td><html:select property="approveStatus">
			<html:option value="Pending"/>
			<html:option value="Approved"/>
			<html:option value="Reject"/>
			</html:select>
			</td></tr>
			<tr>
			<td colspan="2" align="center">
			<html:button property="method" value="Save" onclick="sendReply()"></html:button>
			</td>
			</tr>
			</table>
			</logic:notEmpty>
			
			
			<table border="0" width="100%" id="newTableView">
			<logic:notEmpty name="feedBackList">
			
											<tr>
							<th style="text-align:left;"><b>Sl. No</b></th>
							<th style="text-align:left;"><b>Emp Id</b></th>
							<th style="text-align:left;"><b>Employee Name</b></th>	
							<th style="text-align:left;"><b>Subject</b></th>
							<th style="text-align:left;"><b>Comments</b></th>
							<th style="text-align:left;"><b>Status</b></th>
						</tr>
						
<tr style="height:5px;"><td></td></tr>
						<tr><td colspan="5" class="underline"></td></tr>
						<%
							int count = 1;
										
						%>
		
			<logic:iterate id="feedBackID" name="feedBackList">
			<%if(count == 1) {%>
	<tr class="tableOddTR">
		<td><a href="feedBack.do?method=getEmpFeedBack&sno=<bean:write name="feedBackID"  property="sno"/>"><bean:write name="feedBackID"  property="sno"/></a></td>
		<td><bean:write name="feedBackID"  property="userId"/></td>
			<td><bean:write name="feedBackID"  property="userName"/></td>
			<td><bean:write name="feedBackID"  property="subject"/></td>
			<td><bean:write name="feedBackID"  property="comment"/></td>
			<td><bean:write name="feedBackID"  property="approveStatus"/></td>
										
									</tr>
			<% count++;
							} else {
								int oddoreven=0;
								oddoreven  = count%2;
								if(oddoreven == 0)
								{
									%>
	<tr class="tableEvenTR">
	<td><a href="feedBack.do?method=getEmpFeedBack&sno=<bean:write name="feedBackID"  property="sno"/>"><bean:write name="feedBackID"  property="sno"/></a></td>
		<td><bean:write name="feedBackID"  property="userId"/></td>
			<td><bean:write name="feedBackID"  property="userName"/></td>
			<td><bean:write name="feedBackID"  property="subject"/></td>
			<td><bean:write name="feedBackID"  property="comment"/></td>
			<td><bean:write name="feedBackID"  property="approveStatus"/></td>
			
									</tr>
											
									<% }else{%>
<tr class="tableOddTR">
<td><a href="feedBack.do?method=getEmpFeedBack&sno=<bean:write name="feedBackID"  property="sno"/>"><bean:write name="feedBackID"  property="sno"/></a></td>
	<td><bean:write name="feedBackID"  property="userId"/></td>
			<td><bean:write name="feedBackID"  property="userName"/></td>
			<td><bean:write name="feedBackID"  property="subject"/></td>
			<td><bean:write name="feedBackID"  property="comment"/></td>
			<td><bean:write name="feedBackID"  property="approveStatus"/></td>
			
									</tr>
									<% }count++;}%>

									
			</logic:iterate>
			</table>
			</logic:notEmpty>
			
			
			
			
			
		</html:form>
				</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
 



                

</body>
</html>
