<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: HR_CMS</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript" src="js/scrolltopcontrol.js"></script>
<script type="text/javascript">

function MM_preloadImages() { //v3.0
    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}


function onSubmit(){
	var url="fckEditor.do?method=submit";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function onUpdate(){
	var url="fckEditor.do?method=updateContent";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function subMenuClicked(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		document.getElementById(id+"ss").className = "mail";
		document.getElementById(id+"im").src = "images/left_menu/up_arrow.png";
	}
	else{
		disp.style.display=''; 
		document.getElementById(id+"ss").className = "mailhover";
		document.getElementById(id+"im").src = "images/left_menu/down_arrow.png";
	}
	
	var uu=document.getElementsByName("mailMenu");
	
	for(var i=0;i<uu.length;i++){
		
	if((id)!=(document.getElementById("gg"+i).value)){
 	
	document.getElementById((document.getElementById("gg"+i).value)+"ss").className = "mail";
	document.getElementById((document.getElementById("gg"+i).value)).style.display='none';
	document.getElementById((document.getElementById("gg"+i).value)+"im").src = "images/left_menu/up_arrow.png";
	}
	}
}

	
  function resizeIframe(obj){
  
  if((obj.contentWindow.document.body.scrollHeight)<378){
  obj.style.height ='378px';
  }else{
  obj.style.height = (100+obj.contentWindow.document.body.scrollHeight) + 'px';
  }
  
  }
	
	
	function generate(){
  
  alert('sagdjgsdd asgdjasgdh');
  
  }


function showOverFlow(){
	var hdiv = document.getElementById("subMenuDiv");
	if((hdiv.scrollHeight - hdiv.scrollTop) + "px" >= hdiv.style.height){
		document.getElementById("downDiv").style.display = "";
	}
}

 function disableCtrlKeyCombination(e)
{

//list all CTRL + key combinations you want to disable
var forbiddenKeys = new Array('a', 'n', 'c', 'x', 'v', 'j' , 'w','p');
var key;
var isCtrl;
if(window.event)
{
key = window.event.keyCode;     //IE
if(window.event.ctrlKey)
isCtrl = true;
else
isCtrl = false;
}
else
{
key = e.which;     //firefox
if(e.ctrlKey)
isCtrl = true;
else
isCtrl = false;
}
//if ctrl is pressed check if other key is in forbidenKeys array
if(isCtrl)
{
for(i=0; i<forbiddenKeys.length; i++)
{
//case-insensitive comparation
if(forbiddenKeys[i].toLowerCase() == String.fromCharCode(key).toLowerCase())
{

return false;
}
}
}
return true;
}

</script>


</head>
<%--<style type="text/css">--%>
<%--body{--%>
<%--overflow:hidden;--%>
<%--}--%>
<%--</style>--%>
<body onkeypress="return disableCtrlKeyCombination(event);" onkeydown="return disableCtrlKeyCombination(event);">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<jsp:include page="/jsp/template/header1.jsp"/>
		<tr>
			<td align="center" valign="top" style="padding-top:2px; background-color:#FFF;">
				<table style="width:100%;height:100%;" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="16%" style="padding-left:10px;" align="left" valign="top">
						<!--------LEFT MENU STARTS -------------------->
				          <jsp:include page="/jsp/template/subMenu.jsp"/>
        				</td>
        				<!--------LEFT PART ENDS -------------------->
						<!--------CONTENT STARTS -------------------->
						<td align="left" valign="top" id="annouPage">
							<iframe src="hr.do?method=displayCMS1"" name="contentPage" width="100%" frameborder="0"
								scrolling="auto" style="overflow-y:auto;height:1000px;" onload="showOverFlow()">
							</iframe>
						</td>
						<!--------CONTENT ENDS -------------------->
      				</tr>
    			</table>
    			<br/><p/>
    			<!--------MIDDEL PART ENDS -------------------->
		<!--------FOOTER STARTS -------------------->
		<tr><td>
			<div id="footer1"><jsp:include page="/jsp/template/footer1.jsp"/></div>
		</td></tr>
		<!--------FOOTER ENDS -------------------->
	</table>
</body>
</html>
