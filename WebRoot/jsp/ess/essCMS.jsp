<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>--%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: ESS_CMS</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/scrolltopcontrol.js"></script>

<script type="text/javascript">

window.onscroll = function (oEvent) {
 	var bt = document.getElementById("back-top").style.display;
 	if(document.body.scrollTop > 100){
	 	if(bt == "none"){
	 		document.getElementById("back-top").style.display = "";
	 	}
 	}
 	else{
 		document.getElementById("back-top").style.display = "none";
 	}
}

function showOverFlow(){
	var hdiv = document.getElementById("subMenuDiv");
	if((hdiv.scrollHeight - hdiv.scrollTop) + "px" > hdiv.style.height){
		document.getElementById("downDiv").style.display = "";
	}
}


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
	
	
<%--	function generate(){--%>
<%--  --%>
<%--  alert('sagdjgsdd asgdjasgdh');--%>
<%--  --%>
<%--  }--%>

function goTop() {
	document.getElementById('back-top').style.display='none';
}

</script>
<style type="text/css">
#back-top{
	position: fixed;
	bottom: 100px;
	margin-left: 990px;
	cursor:pointer;
}
</style>
	<script>
	function calcHeight()
		{
		
        //find the height of the internal page
        var the_height=
        document.getElementById('the_iframe').contentWindow.
        document.body.scrollHeight;

        //change the height of the iframe
        document.getElementById('the_iframe').height=
        the_height;
      
       }
       
		function Reload () {
			var f = document.getElementById('the_iframe');
			f.src = f.src;
			f.contentWindow.location.reload(true);
		}
       
       
       function hideSubMenu(){
		
		document.getElementById("reqTD").style.display="none";
		document.getElementById("hide1").style.visibility="hidden";
		document.getElementById("show1").style.visibility="visible";
		document.getElementById("reqColumn").style.align="right";
		}
		
		function showSubMenu(){
		document.getElementById("reqTD").style.display="";
			document.getElementById("hide1").style.visibility="visible";
		document.getElementById("show1").style.visibility="hidden";
		document.getElementById("reqColumn").style.align="left";
		}
	</script>



</head>
<%--<style type="text/css">--%>
<%--#back-top{--%>
<%--	position: fixed;--%>
<%--	bottom: 100px;--%>
<%--	cursor:pointer;--%>
<%--}--%>
<%--</style>--%>

<body id="top">


<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  
  
   <jsp:include page="/jsp/template/header1.jsp"/>
  
  
  <tr>
    <td align="center" valign="top" style="padding-top:2px; background-color:#FFF;">
    <table style="width:100%;height:100%;"  cellspacing="0" cellpadding="0">
		
    	<tr>
        		<td  align="left" valign="top"  style="width:16%;display: show;" id="reqTD" colspan="0"><!--------LEFT MENU STARTS -------------------->
          
          
          <jsp:include page="/jsp/template/subMenu.jsp"/>
        
         </td> 
        <!--------CALENDER ENDS -------------------->
        <!--------LEFT PART ENDS -------------------->
        
        <!--------CONTENT STARTS -------------------->
        <td align="left" valign="top" id="annouPage"><!--
     		
       <iframe src="/EMicro/leave.do?method=displayCMS1" name="contentPage" width="100%" frameborder="0" style="overflow-y:auto;height:1000px;"></iframe></td>
         
         --><iframe src="leave.do?method=displayCMS1" name="contentPage" width="100%" 
        			frameborder="0" scrolling="no" id="the_iframe" style="overflow-y:auto;height:3000px;" onload="showOverFlow()" frameborder="0">
        </iframe>    
        
        </td>
      <!--------CONTENT STARTS -------------------->
      </tr>
    </table></td></tr><!--------MIDDEL PART ENDS -------------------->
      
<%--      <!-------- FOOTER STARTS -------------------->--%>
<%--    <tr><td>   --%>
<%--     <div class="footers">--%>
<%--    	<div class="footer1">--%>
<%--        --%>
<%--        	<div class="bottom">--%>
<%--        	<div class="copyright"><p>&copy;|2012|Micro Labs Limited|All rights reserved</p></div>--%>
<%--            --%>
<%--            <div class="right-blocks"><p>...Because health is in small details</p></div>--%>
<%--           </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--	</td></tr><!--------FOOTER ENDS -------------------->--%>
<tr>
      	<td  valign="top">
      	<td>
      		<p id="back-top" style="display: none;">
				<a href="#top">Back To Top</a>
					<br/>Back to Top
				</a>
			</p>
      	</td>
      </tr>
</table>
<br/><p><p>
<div id="footer1"><jsp:include page="/jsp/template/footer1.jsp"/></div>
</body>
</html>
