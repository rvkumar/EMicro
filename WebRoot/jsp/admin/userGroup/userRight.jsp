<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
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
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
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


function addRights(){
var form = document.getElementById('userRightsFrm');
var checkBox=form.selectedLinksArr;
var checked=false;
		for(var i=0;i<checkBox.length;i++)
			{
				if(checkBox[i].checked==true)
				checked=true;
			}
				 
		if(checked==true){
			form.action="userGroup.do?method=addUserRight";
			form.submit();
		}
		else
		alert("Assigne atleast one link to the module..");
		
		return false;
}


function reFresh(){
var form = document.getElementById('userRightsFrm');
	if(form.userName.value=='')
	{
	alert("please enter a user name");
	return false;
	}
		form.action="userGroup.do?method=userRight";
		form.submit();
}

function checkAll()
	{
		var form = document.getElementById('userRightsFrm');
		for(i=0; i < form.selectedLinksArr.length; i++){
			if(form.checkProp.checked==true)
				form.selectedLinksArr[i].checked = true ;
			else
				form.selectedLinksArr[i].checked = false ;
		}
	}
	
function searchStaffId()
{
var form = document.getElementById('userRightsFrm');
		var ser=form.userName.value;
			if(ser=='')
			{
			alert("please enter a charecter to search");
			return false;
			}
		var x = window.open("userGroup.do?method=getUserName&ser="+ser+"","GetUserName","width=500,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
	
	//var x = window.open("userGroup.do?method=getUserName","GetUserName","width=500,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
}


//-->
</script>
</head>

<body onload="MM_preloadImages('images2/home_hover.jpg','images2/news_hover.jpg','images2/ess_hover.jpg','images2/hr_hover.jpg','images2/it_hover.jpg','images2/timeout_hover.jpg','images2/admin_hover.jpg')">
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
        <!--------HEADER STARTS -------------------->
        	<div class="header">
            	<div class="logo"></div>
                <div style="width:658px; height:175px; float:left;">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family:Arial; font-size:14px; color:#FFFFFF">
  <tr>
    <td width="19%" style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td width="23%" height="60">&nbsp;</td>
    <td width="58%">&nbsp;</td>
  </tr>
  <tr>
    <td style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td align="right">Welcome&nbsp; : </td>
    <td> &nbsp;&nbsp;Mr:Kumar</td>
  </tr>
  <tr>
    <td style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td align="right">Last Login&nbsp; : </td>
    <td>&nbsp;&nbsp;15th Oct 2012 | 10:00am</td>
  </tr>
  <tr>
    <td style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td align="right">Password Expires on&nbsp; : </td>
    <td>&nbsp;&nbsp;14/10/2013</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

              </div>
                <div style="width:230px; height:175px; float:right;">
                	<div class="feedbackmenu"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="4%">&nbsp;</td>
    <td width="96%">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="link"><a href="#">feedback</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">personalize</a>&nbsp; |&nbsp;   <a href="login.do?method=submit">logout</a></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
                    <div class="logo2"></div>
                </div>
            </div><!--------HEADER ENDS -------------------->
           <!--------MENU STARTS -------------------->
  <!--  <div class="left_curve"></div>-->
                    <div class="menu"><!--------MAIN MENU STARTS -------------------->
                      
                      <div class="home"><a href="main.do?method=display&id=Home" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image5','','images2/home_hover.jpg',1)"><img src="images2/home.jpg" name="Image5" width="86" height="46" border="0" id="Image5" /></a></div>
                      <div class="news"><a href="newsAndMedia.do?method=display&id=News And Media" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image6','','images2/news_hover.jpg',1)"><img src="images2/news.jpg" name="Image6" width="165" height="46" border="0" id="Image6" /></a></div>
                      <div class="ess"><a href="leave.do?method=display&id=ESS" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','images2/ess_hover.jpg',1)"><img src="images2/ess.jpg" name="Image7" width="80" height="46" border="0" id="Image7" /></a></div>
                      <div class="hr"><a href="links.do?method=display&id=HR" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','images2/hr_hover.jpg',1)"><img src="images2/hr.jpg" name="Image8" width="81" height="46" border="0" id="Image8" /></a></div>
                      <div class="it"><a href="it.do?method=display&id=IT" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','images2/it_hover.jpg',1)"><img src="images2/it.jpg" name="Image9" width="67" height="46" border="0" id="Image9" /></a></div>
                      <div class="timeout"><a href="main.do?method=display&id=Home" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image10','','images2/timeout_hover.jpg',1)"><img src="images2/timeout.jpg" name="Image10" width="125" height="46" border="0" id="Image10" /></a></div>
                      <div class="admin"><a href="userGroup.do?method=displaySubLinks&id=Admin" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image11','','images2/admin_hover.jpg',1)"><img src="images2/admin.jpg" name="Image11" width="105" height="46" border="0" id="Image11" /></a></div>
                      
                      <div class="contactperson"><input type="text" value="Contact Person" size="15" />            
           			  <div class="arrow"><img src="images2/downarrow.png" /></div>
					  	
                      </div>
                      <div class="search"><input type="text" size="15"/>
                      </div>
                      <div class="icon4search"><img src="images2/search.png" /></div>
                    
  </div><!--------MAIN MENU ENDS -------------------->
                 <!-- <div class="right_curve"></div> -->   
            
           <!--------MENU ENDS -------------------->
           
  <div class="blank"></div>
           
            
            <!--------CONTENT STARTS -------------------->
        	<div class="content">
            	<div class="content_left"><!--------CONTENT LEFT -------------------->
               	  <div class="mail_main"><!--------MAIL MAIN STARTS -------------------->
               	  
               	  
               	  <%
	 
	    LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
	 	Set set=hm.entrySet();
	 	
	 	Iterator itr=set.iterator();
	 	
	 	while(itr.hasNext()){
	 	Map.Entry me=(Map.Entry)itr.next();
	 	
		 %>
		 
		
<%

if(!me.getKey().equals("Arr")){ %>


 <div class="mail"><a href="<%=me.getKey()%>"><img src="images2/menus.jpg" width="37" height="35" border="0" style="float:left; margin-right:5px;" /><%=me.getValue()%><img src="images2/up_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" /></a></div>
 
<%}%>

<%if(me.getKey().equals("Arr")){

 ArrayList h=(ArrayList)me.getValue();
 Iterator itr1=h.iterator();
		%>
		
		<% 
  while(itr1.hasNext()){
  String[] link=itr1.next().toString().split(",");
 %>
 <div class="mail_content"><img src="images2/lefarrow.png" border="0" />  <a href="<%=link[1]%>"><%=link[0]%></a></div>
 
 <%} %>

 
 <%} %>
 
 <%} %>
                    </div><!--------MAIL MAIN ENDS -------------------->
                    
                    
                    <div style="height:4px;"></div>
                    <!--------CALENDER STARTS -------------------->
                    
                    <!--------CALENDER ENDS -------------------->
                </div><!--------CONTENT LEFT END -------------------->
                
                <div style="padding-left: 10px;width: 70%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
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
					
					<html:form action="userGroup.do">
					
		<table border="0" align="center" width="30%" id="mytable" >
						<tr><th colspan="3" ><div align=center>User Rights</div></th></tr>
						<logic:notEmpty property="centersList" name="groupForm">
							<tr>
								<th class="spec">Center</th>
								<th class="spec" colspan="2">
								  <bean:define id="cntrList" property="centersList" name="groupForm"/>
									<html:select property="serCenterId" >
										<html:options collection="cntrList" property="id" labelProperty="value"/>
									</html:select>
								</th>
							</tr>
						</logic:notEmpty>
						<tr>
							<th class="spec">User&nbsp;Name</th>
							<td class="spec"><html:text property="userName" ></html:text></td>
							<td><html:button property="method" styleClass="rudraButtonCSS" value="Search Users" onclick="searchStaffId();"></html:button></td>
						</tr>
						<tr>
							<th colspan="3"><div align="center"><html:button property="method" styleClass="rudraButtonCSS" value="Get Links" onclick="reFresh();" ></html:button></div></th>
						</tr>
						<logic:notEmpty name="groupForm" property="modules">
						<tr><th colspan="3"><div align="center">Select Modules</div></th></tr>
						<tr>
							<td colspan="3" align="center" class="lft">
								<bean:define id="abc" property="modules" name="groupForm"/>
									<html:select name="groupForm" property="moduleName" onchange="reFresh('module');">
										<html:option value="">--Select Group--</html:option>
										<html:option value="Main">Main</html:option>
								 		<html:options collection="abc" property="value" labelProperty="value" />
									</html:select>
							</td>
						</tr>
					</logic:notEmpty>
				
						<logic:notEmpty name="groupForm" property="links">
						  <bean:define id="gid" property="allGroupIds" name="groupForm"/>
							<tr><th colspan="2"><div align="center">Select Links</div></th><th><input type="checkbox" name="checkProp" onclick="checkAll()"/></th></tr>
								<logic:iterate id="vvv" name="groupForm" property="links">
									<tr>
										<th class="spec" colspan="2"><bean:write name="vvv" property="value"/></th>
										<td><input type="checkbox" name="selectedLinksArr" value="${vvv.id}" <%  if(((ArrayList<String>) gid).contains(""+((IdValuePair)vvv).getId())) out.println("checked='checked'");%> ></input></td>
									</tr>
								</logic:iterate>
							<tr><th colspan="3"><div align=center><html:button property="method" styleClass="rudraButtonCSS" value="Modify Rights" onclick="addRights();"></html:button> </div></th></tr>
						</logic:notEmpty>
					
					<logic:empty name="groupForm" property="links">
						<tr><th colspan="3" class="specalt"><div align=center>No module selected</div></th></tr>
					</logic:empty>
				</table>



							</html:form>
						</div>
					<div class="headline">
                       
                    </div>
                    <div class="announcements_content">
                    	

                    </div>
                    
                </div><!--------CONTENT MIDDLE END -------------------->
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    
  </tr>
</table>
<!-------------- FOOTER STARTS ------------------------->
		<div class="footer_main">
		  <div class="footer">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10%" height="50">&nbsp;</td>
    <td width="56%">&nbsp;</td>
    <td width="34%">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="normaltext2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;© 2012 | Micro Labs Limited | All rights reserved.</td>
    <td style="font-family:Arial; font-size:18px; font-weight:bold; color:#FFFFFF">...Because health is in small details</td>
  </tr>
</table>

                </div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">

</table>

		</div><!-------------- FOOTER ENDS ------------------------->
</body>
</html>
