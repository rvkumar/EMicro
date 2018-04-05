
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
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



 function modifyQuestion()
    {
		document.forms[0].action="securityQuestions.do?method=modifyQuestion";
		document.forms[0].submit();
	}

function deleteQuestion()
    {
		document.forms[0].action="securityQuestions.do?method=deleteQuestion";
		document.forms[0].submit();
	}


function onSubmit()
 {
		document.forms[0].action="securityQuestions.do?method=submit";
		document.forms[0].submit();
	}
//-->
</script>
</head>

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg','images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg')">
		<!--------WRAPER STARTS -------------------->
<div id="wraper">

        <jsp:include page="/jsp/template/header1.jsp"/>
           <jsp:include page="/jsp/template/subMenu.jsp"/>
                
                <div style="padding-left: 10px;width: 70%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
                    <div>
					
					
						
									<div align="center">
					<logic:present name="securityQuestionsForm" property="message">
						<font color="red">
							<bean:write name="securityQuestionsForm" property="message" />
						</font>
					</logic:present>
				</div>
	
					<html:form action="securityQuestions.do">
					
		<table border="1" align="center" id="mytable" >
						<tr><td colspan=2 bgcolor="#51B0F8"><div align="center" class="head-block">
						<font color="#000000">Add Users</font></div></td></tr>
						<tr>
						    <td colspan="3" class="lft style1">
							    <div style="color: red">
									<font color="red"><img src="images/smallindex.jpg" /> Mark Indicates Mandatory fields</font>
								</div>
							</td>
					    </tr>
					    
					  
					    <html:hidden property="questionId"/>
					    
						<tr>
							<td>Question <img src="images/smallindex.jpg"/></td>
							<td><html:text property="questionName"/></td>
						</tr>
						
						<logic:empty name="displayModifyButton">
						<tr><td colspan=2 bgcolor="white">
						<div align=center>
							<html:button property="method" styleClass="rudraButtonCSS" value="Submit" onclick="onSubmit();">
							</html:button>
							
						</div>
						</td></tr>
						
						</logic:empty>
						
						
						<logic:notEmpty name="displayModifyButton">
						
						<tr><td colspan=2 bgcolor="white">
						<div align=center>
							<html:button property="method" styleClass="rudraButtonCSS" value="Modify" onclick="modifyQuestion();">
							</html:button>
							
							<html:button property="method" styleClass="rudraButtonCSS" value="Delete" onclick="deleteQuestion();">
							</html:button>
							
						</div>
						</td></tr>
						
						</logic:notEmpty>
					</table>


	<logic:notEmpty name="questionDetails">

							<table class=forumline align=center width='60%'>

								<tr>
									<td bgcolor="#51B0F8" colspan=5>
									<font color="white">
										Questions LIST</font>
									</td>
								</tr>

								<tr height='20'>
									<td align=center>
										<b>Sl. No</b>
									</td>
									<td align=center>
										<b>Question</b>
									</td>
									

								</tr>
								<%int count=1; %>
								<logic:iterate name="questionDetails" id="abc">

									<tr>
										<td>
											<a
												href="securityQuestions.do?method=edit&sId=<bean:write name="abc" property="questionId"/>"><%=count++ %>
										</td>
										<td>
											<bean:write name="abc" property="questionName"/>
										</td>
										
									</tr>

								</logic:iterate>

							</table>

						</logic:notEmpty>

							</html:form>
						</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    
  </tr>
</table>


<jsp:include page="/jsp/template/footer1.jsp"/>  
                

</body>
</html>
