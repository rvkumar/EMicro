<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Microlab</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

function MM_preloadImages() { //v3.0
    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
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


function subMenuClicked(id){
	
	var disp=document.getElementById(id);
		
		if(disp.style.display==''){
			disp.style.display='none';
			document.getElementById("mailTe").src = "images/left_menu/up_arrow.png";
			document.getElementById("mail12").className = "mail";
		}
		else{
			disp.style.display=''; 
			document.getElementById("mailTe").src = "images/left_menu/down_arrow.png";
			document.getElementById("mail12").className = "mailhover";
		}
}

	
  function resizeIframe(obj){
  
  if((obj.contentWindow.document.body.scrollHeight)<378){
  obj.style.height ='378px';
  }else{
  obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }
  
  }
	

</script>


</head>

<body>


<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  
  
   <jsp:include page="/jsp/template/header1.jsp"/>
  
  
  <tr>
    <td align="center" valign="top" style="padding-top:2px; background-color:#FFF"><table width="95%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="16%" align="left" valign="top"><!--------LEFT MENU STARTS -------------------->
          
          
          <jsp:include page="/jsp/template/subMenu.jsp"/>
          
          
        <!--------CALENDER ENDS --------------------></td>
        <!--------LEFT PART ENDS -------------------->
        
        <!--------CONTENT STARTS -------------------->
        <td align="left" valign="top" id="annouPage">
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
							
					</html:form>
          </iframe></td>
        <!--------CONTENT STARTS -------------------->
      </tr>
    </table></td></tr><!--------MIDDEL PART ENDS -------------------->
      <!-------- FOOTER STARTS -------------------->
    <tr><td>   
     <div class="footers">
    	<div class="footer1">
        
        	<div class="bottom">
        	<div class="copyright">
            
            
            <p>&copy;|2012|Micro Labs Limited|All rights reserved</p>
            
            
            </div>
            
            <div class="right-blocks">
            
            
            <p>...Because health is in small details</p>
            </div>
           </div>
        </div>
           </div>
</div></td></tr><!--------FOOTER ENDS -------------------->
</table>
</body>
</html>
