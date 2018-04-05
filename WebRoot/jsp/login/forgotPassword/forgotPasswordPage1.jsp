<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="shortcut icon" type="image/x-icon" 
      href="images/favicon.ico"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="css/style.css"/>
		<title>Emicro-Login Page</title>
		
		<style>
			
			
		input.button {
		background-repeat: no-repeat;
		background-position: <left|right>;
		padding-<left|right>: 78px;
		background-color: white;
		width:200px;
		height:24px;  
		}
		
		</style>
		
		<script type="text/javascript">
		
		
	function onChangePaasswordSubmit(){
			
	    if(document.forms[0].questionAnswer.value=="")
	    {
	      alert("Please Enter Answer");
	      document.forms[0].questionAnswer.focus();
	      return false;
	    }
	    
	var url="login.do?method=sendChangePassword";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
	
	
		</script>

	</head>

	<body onload="onGoUser()">
		<div id="wrapper">
			
			
			<div class="backwrapper">
				
				<div class="log1-block">
					
					<div class="logos">
						<img src="images/logo.png" />
					</div>
					<div class="text1">
						Micro Labs Ltd
					</div>
					
				</div>
				
				
				<div class="mainsub-block">
					
					<div class="login-block">
					
										<center>
											<logic:notEmpty name="loginForm" property="message">
												<font color="#0000FF">
													<bean:write name="loginForm" property="message" />
												</font>
											</logic:notEmpty>
										</center>
							
							
							<html:form action="/login.do" method="post">
								
								
							<center><h3>
							Answer Your Security Question to reset Your Password</h3></center>
							<html:hidden property="userName"/>
							
						<table border="1" align="center" >
						
						
						<tr>
								<td>
									UserName
								</td>
								<td>
										<bean:write name="loginForm" property="userName" />
								</td>
							</tr>
							
							
							<tr>
								
								<td colspan="2" style="width: 350px; height: 10px"> 
										<b><bean:write name="loginForm" property="questionId" />?</b>
								</td>
							</tr>
							
							
							<tr>
								<td>
									Enter&nbsp;Answer
								</td>
								<td>
								<html:text property="questionAnswer" style="width: 200px;height: 24px;"/>
								</td>
							</tr>
							
							
							<tr>
								<td colspan="2" align="center"> 
										<html:button property="method" styleClass="button" value="Submit" onclick="onChangePaasswordSubmit()" />
									</td>
							</tr>

						</table>

						<div align="left">
						<a href="login.do?method=displayForgotPasswordPage">Back</a>
						</div>
						
						</html:form>
						
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
