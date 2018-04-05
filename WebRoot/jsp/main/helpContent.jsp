<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<link rel="stylesheet" type="text/css" href="css/style.css"/>
		<title>Emicro-Login Help</title>
		
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
		
		if(document.forms[0].questionId.value=="")
	    {
	      alert("Please Select Question");
	      document.forms[0].questionId.focus();
	      return false;
	    }
	    
	    
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
		
		document.getElementById('mainsub-block12').style.cssText = 'width:950px;margin:0px auto;height:auto;';
		
		</script>
</head>

<body onload="onGoUser()" style="background-color: #8ACEFF;">
	<div id="wrapper">
		<div class="backwrapper">
			<div class="log1-block">
				<div class="logos"><img src="images/logo1.jpg" /></div>
			</div>
			<div class="log2-block">
				<div class="help"><a href="login.do?method=logout" target="_self">Back to Log In</a></div>
			</div>
			<div class="mainsub-block">
				<div class="loginhelp" style="overflow-y:scroll">
				<table width="100%"><tr><td width="90%"><h3>Login Help Content</h3></td><td width="10%"><img src="images/helpContent.jpg" height="50px"/></td></tr>
				<tr><td style="padding-left: 10px;"><bean:write name="loginForm" property="contentDescription" filter="false" /></td></tr>
				</table>
				</div>
			</div>
		</div>
		<div class="footers">
			<div class="footer1">
				<div class="bottom">
					<div class="left-blocks"><p>&copy;|2014|Micro Labs Limited|All rights reserved</p></div>
					<div class="right-blocks"><p>...Because health is in small details</p></div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>
