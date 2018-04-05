<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Microlab :: Login</title>

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
		
		
	
        function onLogout(){
      
        var url="login.do?method=logout";
			document.forms[0].action=url;
			document.forms[0].submit();
        
        }
        
        
        </script>

</head>

<body onload="onLogout()">
<div id="wrapper">


		<div class="backwrapper">

		<div class="log1-block">

		<div class="logos"><img src="images/logo1.jpg" /></div>
		</div>


		<div class="log2-block">
        
        
        <div class="help">
        <a href="login.do?method=displayHelpContent" target="_blank">help?</a>
        </div>
        
        
        </div>

<div class="mainsub-block">

<div class="welcome-block">

<div class="welcome"><h2>Welcome to <span style="color:#127bca;">e Micro</span> Portal</h2></div>

<div class="para"><p>We welcome you to our brand new Admin Panel. We welcome you to our brand new Admin Panel.  We welcome you to our brand new Admin Panel. </p>

<p>We welcome you to our brand new Admin Panel. We welcome you to our brand new Admin Panel. We welcome you to our brand new Admin Panel. We welcome you to our brand new Admin Panel. We welcome you to our brand new Admin Panel. </p></div>


<div class="log"><p>Happy login!</p></div>

</div>

<div class="login-block">

<div class="up-block">

<div class="emerco"><img src="images/emicro2.png" /></div>

<div class="loginco"> <p>Login</p></div>

</div>
<div class="down-block">

  
  	<html:form action="/login.do" method="post" onsubmit="onSubmit(); return false;">
								
								
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
            
            
            <p>&copy;|2012|Micro Labs Limited|All rights reserved</p>
            
            
            
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
