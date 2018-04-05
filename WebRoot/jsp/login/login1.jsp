<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
		<link rel="stylesheet" type="text/css" href="css/style.css"/>

<title>eMicro :: Login</title>

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
if(document.forms[0].userName.value=="User Name")
   {
     alert("Please Enter User Name");
     document.forms[0].userName.focus();
     return false;
   }
else if(document.forms[0].password.value=="")
   {
     alert("Please Enter Password");
     document.forms[0].password.focus();
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
        
        function inValidLogin()
        {
        alert("The username or password you entered is incorrect");
        }
        function statusMessage2(message){
				alert(message);
			
				
		
				}
			 
function onSave()
{

var oldPwd=document.forms[0].oldPassword.value;
var newPwd=document.forms[0].newPassword.value;
var conformPwd=document.forms[0].conformPassword.value;


if(document.forms[0].oldPassword.value=="")
   {
     alert("Please Enter Date Of Join");
     document.forms[0].oldPassword.focus();
     return false;
   }
else if(document.forms[0].newPassword.value=="")
   {
     alert("Please Enter New Password");
     document.forms[0].newPassword.focus();
     return false;
   }   
else if(document.forms[0].conformPassword.value=="")
   {
     alert("Please Enter Conform Password");
     document.forms[0].conformPassword.focus();
     return false;
   }
else if(document.forms[0].favoritQues.value=="")
   {
     alert("Please Select  Favorit Question");
     document.forms[0].favoritQues.focus();
     return false;
   }
else if(document.forms[0].favAns.value=="")
   {
     alert("Please Enter Answer");
     document.forms[0].favAns.focus();
     return false;
   }
else if(newPwd==conformPwd){



	//var regularExpression  = /^[a-zA-Z0-9!@#$%^&*]{6,12}$/;
	var regularExpression  = /^.*(?=.{6,12})(?=.*\d)(?=.*[a-zA-Z]).*$/;

if(!regularExpression.test(newPwd)) {
        alert("Minimum Password length : 6 (Combination Of 1 number, 1 special character)");
         document.forms[0].newPassword.focus();
        return false;
    }
    var url="main.do?method=savePassword";
			document.forms[0].action=url;
			document.forms[0].submit();
    
    }
 else{
alert("New password and Confirm password not matches");
document.forms[0].newPassword.value="";
document.forms[0].conformPassword.value="";
}	   
      			
}

function myFunction() {
    myVar = setTimeout(onBack, 3000);
}

function onBack()
{


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

function gotoLoginPage()
{
alert("Your password has been changed successfully.");
var url="login.do?method=logout";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function passwordExpires()
{
alert("Your password has expired.\nReset your password and login");
			var url="login.do?method=changePasswordExp";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function displayMessage(message1){
alert(message1);
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
        <a href="login.do?method=displayHelpContent" >help ?</a>
        </div>
        
        
        </div>

<div class="mainsub-block">
	<logic:notEmpty name="loginArea">
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
			<div class="emerco"><img src="images/emicro2.png"/></div>

			
		</div>
		<div class="down-block">

				  		<html:form action="/main.do" method="post" onsubmit="onSubmit(); return false;">
<div align="center">
				 <logic:notEmpty name="mainForm" property="message">
					
					<script language="javascript">
					statusMessage2('<bean:write name="mainForm" property="message" />','');
					</script>
					
				</logic:notEmpty> 
		
				
		
			</div>
								<div class="field1">
									<html:text name="mainForm" property="userName" styleId="myEl" value="User Name" onfocus="ClearPlaceHolder (this)" onblur="SetPlaceHolder (this)"/>
								</div>
								
								<div class="field2">
									<div class="placeholder">
										<html:password name="mainForm" property="password"  styleId="pswdInput" />
										<input type="text" maxlength="8" value="Password" id="textInput" onfocus="onGoUser()" style="color: grey"/>
									</div>
								</div>
			<!-- 						<table style="margin-left: 32px;height: 20px;"><tr>
							<td>
					
<iframe src="jsp/login/Captchaimage.jsp" width="125px" height="30px" scrolling="no"   style="border:none;" id="captcha_image"  ></iframe></td><td> <input type="text"  value="" id="" name="captcha" style="width: 50%;height: 20px;margin-bottom: 1px" placeholder="Enter Captcha"/>
   <img src="images/refrez.jpg" width="13px" height="20px" align="absmiddle" onclick="myFunction()" title="Refresh Captcha"/></td></tr>
</table> -->
			
								<div class="logblock">
									<img title="Hello" src="images/loginbutton5.png"
										onmouseover="this.src='images/loginbutton4.png'"
										onmouseout="this.src='images/loginbutton5.png'" border="0" onclick="onSubmit()"/>
								</div>
						</html:form>

    	<div class="bottom-block">
    
		    <div class="forgot">
		      <a href="login.do?method=displayForgotPasswordPage">forgot password</a>
		    </div>
   
    	</div>

	</div>
	</div>
	</logic:notEmpty>
	
	<logic:notEmpty name="personalizeArea">
	<div class="welcome-block" align="center" >

		<html:form action="/main.do" method="post" >
		
		<table class="bordered" bgcolor="#E6E6E6" align="center">
		<tr>
							 				<th colspan="2"><big>Welcome - First Time Login User</big></th>
							 			</tr>
										
										<tr>
											<td>Date of Join (ddmmyyyy) <font color="red">*</font> </td> 
											<td>
												<html:password property="oldPassword"  name="mainForm"  title="Date Of Join(ddmmyyyy)"></html:password>
												<html:hidden property="empOldPwd" name="mainForm"/>
												<html:hidden property="userName" name="mainForm"/>
	  										</td>	
										</tr>

										<tr>
											<td>New Password <font color="red">*</font> </td>
											<td>
												<html:password property="newPassword" name="mainForm"  maxlength="12" size="12"  onblur="checkPassFormat(this)"></html:password>
												<br/>
												<small>Minimum password length should be of 6 characters, 1 number & 1 special character. (Ex:1@emicro)</small>
							  				</td>	
										</tr>

										<tr>
											<td>Confirm Password <font color="red">*</font> </td>
											<td>
												<html:password property="conformPassword" name="mainForm" maxlength="12" size="12" title="Minimum of 6 and Maximum of 12 characters" ></html:password>
											</td>	
										</tr>
										<tr>
										<td>Favorite question <font color="red">*</font> </td>
										<td>
										<html:select property="favoritQues" styleClass="content">
										<html:option value="">Select</html:option>
										 <html:option value="What was your childhood nickname?">What was your childhood nickname?</html:option>
										 <html:option value="What is your mothers maiden name?">What is your mother's maiden name?</html:option>
										 <html:option value="What is the name of the first school you attended?">What is the name of the first school you attended?</html:option>
										 <html:option value="In what city or town was your first job?">In what city or town was your first job?</html:option>
										 <html:option value="Which is your place of birth?">Which is your place of birth?</html:option>
										</html:select>
										</td>
										</tr>
										<tr>
											<td>Answer <font color="red">*</font> </td>
											<td>
												<html:text property="favAns" name="mainForm" maxlength="15" size="15"  styleClass="content"></html:text>
											</td>	
										</tr>	

										<tr>
											<td colspan="2" style="text-align: center;">
											<logic:notEmpty name="saveButton">
						      					<html:button property="method" value=" Save " styleClass="rounded" style="width:100px" onclick="onSave()"></html:button> &nbsp;
												<html:reset value=" Reset " styleClass="rounded" style="width:100px"></html:reset>  &nbsp;
												<html:button property="method" value="Back To Login" onclick="onBack()" styleClass="rounded" style="width:100px"></html:button>
											</logic:notEmpty>
											<logic:notEmpty name="backToLogin">
													<script type="text/javascript">
												gotoLoginPage();
												</script>
											</logic:notEmpty>
											</td>
										</tr>		
								</table>
		</table>
		</html:form>
		</div>
		</logic:notEmpty>
	
	
	

			

		</div>
        
        </div>   
    <div class="footers" style="width: 100%">
    	<div class="footer1">
        
        	<div class="bottom" >
        	<div class="left-blocks">
            
            
            <p>&nbsp;&nbsp; &copy; 2014, Micro Labs Limited. All rights reserved</p>
            </div>
            
            <div class="right-blocks">
            
            
            <p>...Because health is in small details</p>
            
            
            </div>
           </div>
           </div>
           </div>
</div>
<logic:notEmpty name="mainForm" property="message">
<logic:equal value="Username and Password are incorrect please check" property="message" name="mainForm">
	<script type="text/javascript">
	inValidLogin();
	</script>
	</logic:equal>
	<logic:equal value="Password expired" property="message" name="mainForm">
	<script type="text/javascript">
	passwordExpires();
	</script>
	</logic:equal>
</logic:notEmpty>
<logic:present name="mainForm" property="statusMessage">
						<script type="text/javascript">
						 displayMessage('<bean:write name="mainForm" property="statusMessage" />');
						</script>
							
						</logic:present>
</body>
</html>
