
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
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<title>Microlab</title>
<script type="text/javascript">


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

<body >
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
							
					</html:form>


					</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
 

</body>
</html>
