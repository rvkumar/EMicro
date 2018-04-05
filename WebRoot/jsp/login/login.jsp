<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%> 
<% pageContext.setAttribute("currentYear", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="com.microlabs.main.action.MainAction"%>
<%@page import="java.util.Date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" type="image/x-icon" 
      href="images/favicon.ico"/>

<link rel="stylesheet" type="text/css" href="css/style.css"/>
<title>eMicro :: Login</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	<style>
			.placeholder{
    			position:relative;
			}
			.placeholder>#textInput{
			    position:absolute;
			    top:0;
			    left:0;
			}
			.placeholder>input{
			    width:240px;
			}
			
		input.button {
		background-image: url(images/loginbutton4.png);
		background-repeat: no-repeat;
		background-position: <left|right>;
		padding-<left|right>: <width of image>px;
		}
		
		</style>



<script type="text/javascript">
		
		
		document.onkeydown = function(e)
{
  var keyCode = document.all ? event.keyCode : e.which;
  if(keyCode == 13) onSubmit(); 
}
		
function onGo(){
	
	var text=document.getElementById('textInput');
    var pswd=document.getElementById('pswdInput');
	text.onfocus=pswd.onfocus=function(){
    text.style.display='none';
    pswd.focus();
	}
	pswd.onblur=function(){
    if(pswd.value==''){
        text.style.display='';
    }
	}
	

}


function onGoUser(){
	
	document.getElementById('myEl').style.cssText = 'color: grey';
	var text=document.getElementById('textInput');
    var pswd=document.getElementById('pswdInput');
	text.onfocus=pswd.onfocus=function(){
    text.style.display='none';
    pswd.focus();
	}
	pswd.onblur=function(){
    if(pswd.value==''){
        text.style.display='';
    }
	}
	
	
	
	
	
}


function displayFields(){
	
	var textUser=document.getElementById('textUserInput');
    var pswdUser=document.getElementById('pswdUserInput');
	
	
    textUser.style.display='none';
	
	
	var text=document.getElementById('textInput');
    var pswd=document.getElementById('pswdInput');
	
    text.style.display='none';
   
}

function onSubmit(){
	var pwd = document.forms[0].password.value; 
	var pwdlen = pwd.length;
	if(document.forms[0].userName.value=="User Name")
   	{
    	alert("Please Enter User Name");
     	document.forms[0].userName.focus();
     	return false;
   	}
	else if(pwd=="")
   	{
    	alert("Please Enter Password");
     	docment.forms[0].password.focus();
     	return false;
   	}

		var url="main.do?method=display";
		document.forms[0].action=url;
		document.forms[0].submit();
	
}

	
	
	function ClearPlaceHolder (input) {

            if (input.value == input.defaultValue) {
            document.getElementById('myEl').style.cssText = 'color: black';
                input.value = "";
            }
        }
        function SetPlaceHolder (input) {
            if (input.value == "") {
            
            document.getElementById('myEl').style.cssText = 'color: grey';
                input.value = input.defaultValue;
            }
        }
        
        function onLogout(){
        
        var url="login.do?method=logout";
			document.forms[0].action=url;
			document.forms[0].submit();
        
        }
        
        function myFunction() {
            myVar = setTimeout(captchareload, 3000);
        }
                
                 function captchareload(){
           
           

            		var xmlhttp;
            	    if (window.XMLHttpRequest){
            	        // code for IE7+, Firefox, Chrome, Opera, Safari
            	        xmlhttp=new XMLHttpRequest();
            	    }
            	    else{
            	        // code for IE6, IE5
            	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            	    }

            	    xmlhttp.onreadystatechange=function(){
            	        if (xmlhttp.readyState==4 && xmlhttp.status==200){
            	      
            	
            	       document.getElementById("captcha_image").contentWindow.location.reload(true);
            	        	       			
            	        }
            	    }
            	    xmlhttp.open("POST","login.do?method=captchareload",true);
            	    xmlhttp.send();
                
                }
                
        
        </script>

</head>

<body onload="onGoUser()">
<%
 String result = "Invalid Entry.... :(";
 String color = "red";
    if(request.getParameter("captcha") != null && session.getAttribute("captchaStr") != null){
  if(session.getAttribute("captchaStr").equals(request.getParameter("captcha"))){
   result = "Valid Entry.... :)";
   color = "green";
  }
 }
 
 	MainAction obj = new MainAction();
 BufferedImage ima = obj.getCaptchaImage();
/*  File outputfile = new File("C://Tomcat 8.0/webapps/EMicro Files/images/EmpPhotos/image2.jpg");
 ImageIO.write(ima, "jpg", outputfile); */
 String captchaStr = obj.getCaptchaString();
 
 session.setAttribute("captchaStr", captchaStr);

%>
<div id="wrapper">
	<div class="backwrapper">
		<div class="log1-block">
			<div class="logos"><img src="images/logo1.jpg" /></div>
		</div>

		<div class="log2-block">
	        <div class="help">
    		    <a href="login.do?method=displayHelpContent" target="_self">help ?</a>
        	</div>
        </div>

		<div class="mainsub-block">
			<div class="welcome-block">
				<%-- <bean:write property="contentDescription" name="loginForm" filter="false" /> --%>
			<p style="text-align: justify;">&nbsp;</p>
<p style="text-align: justify;">&nbsp;</p>
<p style="text-align: justify;"><span style="color: rgb(255, 255, 255);"><strong><span style="font-family: Tahoma;"><span style="font-size: small;">Welcome to eMicro Portal :</span></span></strong></span></p>
<p style="text-align: justify;"><span style="color: rgb(255, 255, 255);"><span style="font-family: Tahoma;"><span style="font-size: small;"> </span></span></span></p>
<p style="text-align: justify;"><span style="color: rgb(255, 255, 255);"><span style="font-family: Tahoma;"><span style="font-size: small;">Micro Labs is committed to use Information Technology tools to improve and streamline communication process with employees.  The eMicro Portal is one of the many ways which we use to collaborate with employees.  The tools available from this Portal are supporting many different business processes and are providing employee specific information.</span></span></span>&nbsp;</p>
			</div>


<div class="login-block">

<div class="up-block">

<div class="emerco"><img src="images/emicro2.png" /><span style="font-family:arial;"><small>Ver.1.0</small></span></div>

<div class="loginco"></div>

</div>
<div class="down-block">

  
  	<html:form action="/login.do" method="post" onsubmit="onSubmit(); return false;">
								
								<center>
											<logic:notEmpty name="loginForm" property="message">
												<font color="#0000FF">
													<bean:write name="loginForm" property="message" />
												</font>
											</logic:notEmpty>
										</center>
								
								
								<div class="field1">
								
							
								<html:text name="loginForm" property="userName" styleId="myEl" value="User Name" onfocus="ClearPlaceHolder (this)" onblur="SetPlaceHolder (this)"/>

								  
							
								</div>
								
								
								<div class="field2">
								
								<div class="placeholder">
									
							      <html:password name="loginForm" property="password"  styleId="pswdInput" />
							      <input type="text" maxlength="12" value="Password" id="textInput" onfocus="onGoUser()" style="color: grey"/>
							      </div>
								</div>
			<!-- 					<table style="margin-left: 32px;height: 20px;"><tr>
							<td>
					
<iframe src="jsp/login/Captchaimage.jsp" width="125px" height="30px" scrolling="no"   style="border:none;" id="captcha_image"  ></iframe></td><td> <input type="text"  value="" id="" name="captcha" style="width: 50%;height: 20px;margin-bottom: 1px" placeholder="Enter Captcha"/>
   <img src="images/refrez.jpg" width="13px" height="20px" align="absmiddle" onclick="myFunction()" title="Refresh Captcha"/></td></tr>
</table> -->
								<div class="" >
								
								<img title="" src="images/loginbutton5.png"
										onmouseover="this.src='images/loginbutton4.png'"
										onmouseout="this.src='images/loginbutton5.png'" border="0" onclick="onSubmit()" height="30px" style="margin-left: 40px"/>
										
								</div>
								
							</html:form>
	
    <div class="bottom-block">
    	<div class="forgot">
			<a href="login.do?method=displayForgotPasswordPage">forgot password</a>
		</div>
	</div>

</div>

</div>

		</div>
        
        </div>   
    <div class="footers">
    	<div class="footer1">
        	<div class="bottom">
	        	<div class="left-blocks">
		            <p>&nbsp;&nbsp; &copy; ${currentYear}. Micro Labs Limited, All rights reserved</p>
	            </div>
            
	            <div class="right-blocks">
		            <p>...Because health is in small details</p>
				</div>
			</div>
		</div>
	</div>

</div>

</body>
</html>
