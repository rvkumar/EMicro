
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

function addGroup(){

var checkBox=document.forms[0].selectedMainModulesArr;
var checked=false;
if(document.forms[0].groupName.value=='')
{
alert('please enter a group name');
document.forms[0].groupName.focus();
return false;
}


if(checkBox.checked==true)
	checked=true;
		for(var i=0;i<checkBox.length;i++)
			{
				if(checkBox[i].checked==true)
				checked=true;
				//alert(checkBox[i].value);
			}
				 
		if(checked==true){
			document.forms[0].action="userGroup.do?method=addGroup";
			document.forms[0].submit();
		}
		else
		alert("Assigne atleast one module to the group..");
		
		return false;
}


function checkAll()
	{
		for(i=0; i < document.forms[0].selectedLinksArr.length; i++){
			if(document.forms[0].checkProp.checked==true)
				document.forms[0].selectedLinksArr[i].checked = true ;
			else
				document.forms[0].selectedLinksArr[i].checked = false ;
		}
	}
	
	function reFresh(str){
	
	if(str=='group' && document.forms[0].moduleName!=undefined)
	document.forms[0].moduleName.value="";
			document.forms[0].action="userGroup.do?method=reFresh&str="+str;
			document.forms[0].submit();
	}
	
	
	function reFreshSubModule(str){
	if(str=='group' && document.forms[0].moduleName!=undefined)
	document.forms[0].moduleName.value="";
			document.forms[0].action="userGroup.do?method=reFreshSubmodule";
			document.forms[0].submit();
	
	}
	
	
	
	
	
	function modifyGroup(){
	var checkBox=document.forms[0].selectedLinksArr;
	var checked=false;

	if(checkBox.checked==true)
				checked=true;

		for(var i=0;i<checkBox.length;i++)
			{
				if(checkBox[i].checked==true)
				checked=true;
				
			}
				 
		if(checked==true){
			document.forms[0].action="userGroup.do?method=modify";
			document.forms[0].submit();
		}
		else
		alert("Assigne atleast one link to the module..");
		
		return false;
}
	
	
	function addSubSubGroup(){
	    var checkBox=document.forms[0].selectedMainModulesArr;
		var checked=false;
		if(document.forms[0].groupName.value=='')
		{
		alert('please enter a group name');
		document.forms[0].groupName.focus();
		return false;
		}
		
		
		if(checkBox.checked==true)
			checked=true;
				for(var i=0;i<checkBox.length;i++)
					{
						if(checkBox[i].checked==true)
						checked=true;
						//alert(checkBox[i].value);
					}
						 
				if(checked==true){
					document.forms[0].action="userGroup.do?method=addSubSubGroup";
					document.forms[0].submit();
				}
				else
				alert("Assigne atleast one module to the group..");				
				return false;
			 
	
	}
		function modifySubGroup(){
	        document.forms[0].action="userGroup.do?method=modifySubSubGroup";
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
					
		<table border="1" align="center" width="30%" id="mytable" >
					
						<!-- <tr><td colspan=2 bgcolor="4372B7"><div align=center>
						<font color="white"> User Groups</font></div></td></tr>
						<tr>
							<th class="spec" colspan="2">Group Name : &nbsp;
							<html:text property="groupName"/></th>
						</tr>
						 -->
						
						
						<logic:notEmpty name="userGroupForm" property="groups">
						<tr><td colspan=2 bgcolor="#51B0F8"><div align=center>
						<b><font color="white">Select&nbsp;Group&nbsp;:</font></b></div></td></tr>
						
						<tr>
							<td>
							
							<div align="center">
								<html:select name="userGroupForm" property="groupName" onchange="reFresh('group');">
									<html:option value="">--Select Group--</html:option>
							 		<html:options property="groups" labelProperty="groups" name="userGroupForm"/>
								</html:select></div>
							</td>
							<td>&nbsp;</td>
						</tr>
						
						
						<logic:notEmpty name="userGroupForm" property="modules">
						<tr><td colspan=2 bgcolor="#51B0F8"><div align=center>
						<b><font color="white"><center>Select Modules</center></font></b></div></td></tr>
						<bean:define id="abc" name="userGroupForm" property="modules"/>
						<tr>
							<td colspan="2" align="center" class="lft style1">
								<html:select name="userGroupForm" property="moduleName" onchange="reFresh('module');">
									<html:option value="main">Main</html:option>
									<html:options collection="abc" property="value" labelProperty="value" />
								</html:select>
							</td>
						</tr>
						
						
						<logic:notEmpty name="userGroupForm" property="subModules">
						<tr><td colspan=2 bgcolor="#51B0F8"><div align=center>
						<b><font color="white"><center>Select Sub Modules</center></font></b></div></td></tr>
						<bean:define id="abc" name="userGroupForm" property="subModules"/>
						<tr>
							<td colspan="2" align="center" class="lft style1">
								<html:select name="userGroupForm" property="subModuleName" onchange="reFreshSubModule('subModule');">
									<html:options collection="abc" property="value" labelProperty="value" />
								</html:select>
							</td>
						</tr>
						
						</logic:notEmpty>
						
						</logic:notEmpty>
						
						
						
						<logic:notEmpty name="userGroupForm" property="subLinks">
						<bean:define id="gid" property="allGroupIds" name="userGroupForm"/>
						
						<%  int count1=0;%>
						
						<tr>
						<td  bgcolor="#51B0F8"><div align=center>
						<b><font color="white"><center>Select Links</center></font></b></div></td>
						<td  bgcolor="#51B0F8">
						<input class="checkbox" type="checkbox" name="checkProp" onclick="checkAll()"/></td>
						</tr>
						<input type="hidden" name="selectedMainModulesArr" value="Main"/>
						<logic:iterate id="vvv" name="userGroupForm" property="subLinks">
							<tr>
								<th class="spec"><bean:write name="vvv" property="value"/></th>
								<td><input class="checkbox" type="checkbox" 
								 name="selectedMainModulesArr" 
								 value="<bean:write name="vvv" property="value"/>" 
								  <%  
								  
								  if(((ArrayList<String>) gid).contains(""+((IdValuePair)vvv).getId())){
								   count1++;
								   out.println("checked='checked'");
								  }
								   %>/></td>
							</tr>
						</logic:iterate>
						
						<%if(count1>0){ %>
						<tr><td colspan=2  bgcolor="white"><div align=center>
					
						<html:button property="method" value="Modify Sub Sub Group" styleClass="rudraButtonCSS" onclick="modifySubGroup();">
						</html:button> 
						</div></td></tr>
						
						<%}else{ %>
						<tr><td colspan=2 bgcolor="white"><div align=center>
						<html:button property="method" value="Add Sub Sub Group" styleClass="rudraButtonCSS" onclick="addSubSubGroup();">
						</html:button> 
						</div></td></tr>
						
						<%} %>
						
						</logic:notEmpty>
						
						</logic:notEmpty>
						
						
					</table>


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
