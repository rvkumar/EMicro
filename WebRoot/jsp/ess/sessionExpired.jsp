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
  obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }
  
  }
	
	
	function generate(){
  
  alert('sagdjgsdd asgdjasgdh');
  
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
          
          

        
          
        <!--------CALENDER ENDS --------------------></td>
        <!--------LEFT PART ENDS -------------------->
        
        <!--------CONTENT STARTS -------------------->
        <td align="left" valign="top" id="annouPage">
        <div class="middel-blocks">
     		
     			<%
     			
     			String message=(String)request.getAttribute("message");
     			%>
     			<center><img src="images/warning-Sign.png" height="20" width="20"/>  <font color="Green"><b><%= message%></b></font></center>
     			 <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
     			  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
				
			</div>
      </td>
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
