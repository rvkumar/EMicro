
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

function changePassword(){
		var URL="changePassword.do?method=changePassword";
		document.forms[0].action=URL;
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
					<logic:present name="activateUserForm" property="message">
						<font color="red">
							<bean:write name="activateUserForm" property="message" />
						</font>
					</logic:present>
				</div>

					
					<html:form action="activateUser">
					
		
					<logic:notEmpty name="userDetails">
							
							<table class=forumline align=center width='60%'>
								
								<tr>
									<td bgcolor="4F79B8" colspan=5>
									<font color="white">
										Users Deactivated List
									</font>
									</td>
								</tr>
								
								<tr height='20'>
									<td align=center>
										<b>Sl. No</b>
									</td>
									<td align=center>
										<b>User Name</b>
									</td>
									<td align=center>
										<b>Actions</b>
									</td>
									
								</tr>
								<%int count=1; %>
								<logic:iterate name="userDetails" id="abc">
								
									<tr>
										<td>
											<a
												href="activateUser.do?method=activateUser&id=<bean:write name="abc" property="userId"/>">
												<%=count++ %></a>
										</td>
										<td>
											<bean:write name="abc" property="userName" />
										</td>
										
										
										<td>
											<a
												href="activateUser.do?method=activateUser&id=<bean:write name="abc" property="userId"/>">Activate</a>
										</td>
									</tr>
									
								</logic:iterate>

							</table>
							
						</logic:notEmpty>
										
					
					<logic:empty name="userDetails">
							<div align="center">
						       No Users To Display here
							</div>
					</logic:empty>
            


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
