

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script src="js/sumo1.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
   <script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
    <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />

    <script type="text/javascript">
        $(document).ready(function () {
            window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
            window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
            window.testSelAll = $('.testSelAll').SumoSelect({okCancelInMulti:true, selectAll:true });
            window.testSelAll2 = $('.testSelAll2').SumoSelect({selectAll:true });

        });
        
         $(document).ready(function () {
           $('.testselect1').SumoSelect();

        });
    </script>


<script type="text/javascript">
	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}
</script>

<!-- captcha -->
<link rel="stylesheet" href="css/captcha/jquery.realperson.css"/>
<style>
label { display: inline-block; width: 20%; }
.realperson-challenge { display: inline-block }
</style>

<script src="js/captcha/jquery.plugin.js"></script>
<script src="js/captcha/jquery.realperson.js"></script>
<script>
$(function() {
	$('#defaultReal').realperson({dot: 'o', dots: 
    [['   *   ', '  ***  ', '  ***  ', ' **  * ', ' ***** ', '**    *', '**    *'], 
    ['****** ', '**    *', '**    *', '****** ', '**    *', '**    *', '****** '], 
    [' ***** ', '**    *', '**     ', '**     ', '**     ', '**    *', ' ***** '], 
    ['****** ', '**    *', '**    *', '**    *', '**    *', '**    *', '****** '], 
    ['*******', '**     ', '**     ', '****   ', '**     ', '**     ', '*******'], 
    ['*******', '**     ', '**     ', '****   ', '**     ', '**     ', '**     '], 
    [' ***** ', '**    *', '**     ', '**     ', '**  ***', '**    *', ' ***** '], 
    ['**    *', '**    *', '**    *', '*******', '**    *', '**    *', '**    *'], 
    ['*******', '  **   ', '  **   ', '  **   ', '  **   ', '  **   ', '*******'], 
    ['     **', '     **', '     **', '     **', '     **', '*    **', ' ***** '], 
    ['**    *', '**  ** ', '****   ', '**     ', '****   ', '**  ** ', '**    *'], 
    ['**     ', '**     ', '**     ', '**     ', '**     ', '**     ', '*******'], 
    ['*     *', '**   **', '*** * *', '** *  *', '**    *', '**    *', '**    *'], 
    ['*     *', '**    *', '***   *', '** *  *', '**  * *', '**   **', '**    *'], 
    [' ***** ', '**    *', '**    *', '**    *', '**    *', '**    *', ' ***** '], 
    ['****** ', '**    *', '**    *', '****** ', '**     ', '**     ', '**     '], 
    [' ***** ', '**    *', '**    *', '**    *', '**  * *', '**   * ', ' **** *'], 
    ['****** ', '**    *', '**    *', '****** ', '**  *  ', '**   * ', '**    *'], 
    [' ***** ', '**    *', '**     ', ' ***** ', '     **', '*    **', ' ***** '], 
    ['*******', '  **   ', '  **   ', '  **   ', '  **   ', '  **   ', '  **   '], 
    ['**    *', '**    *', '**    *', '**    *', '**    *', '**    *', ' ***** '], 
    ['**    *', '**    *', ' **  * ', ' **  * ', '  ***  ', '  ***  ', '   *   '], 
    ['**    *', '**    *', '**    *', '** *  *', '*** * *', '**   **', '*     *'], 
    ['**    *', ' **  * ', '  ***  ', '   *   ', '  ***  ', ' **  * ', '**    *'], 
    ['**    *', ' **  * ', '  ***  ', '  **   ', '  **   ', '  **   ', '  **   '], 
    ['*******', '    ** ', '   **  ', '  **   ', ' **    ', '**     ', '*******'], 
    ['  ***  ', ' *   * ', '*   * *', '*  *  *', '* *   *', ' *   * ', '  ***  '], 
    ['   *   ', '  **   ', ' * *   ', '   *   ', '   *   ', '   *   ', '*******'], 
    [' ***** ', '*     *', '      *', '     * ', '   **  ', ' **    ', '*******'], 
    [' ***** ', '*     *', '      *', '    ** ', '      *', '*     *', ' ***** '], 
    ['    *  ', '   **  ', '  * *  ', ' *  *  ', '*******', '    *  ', '    *  '], 
    ['*******', '*      ', '****** ', '      *', '      *', '*     *', ' ***** '], 
    ['  **** ', ' *     ', '*      ', '****** ', '*     *', '*     *', ' ***** '], 
    ['*******', '     * ', '    *  ', '   *   ', '  *    ', ' *     ', '*      '], 
    [' ***** ', '*     *', '*     *', ' ***** ', '*     *', '*     *', ' ***** '], 
    [' ***** ', '*     *', '*     *', ' ******', '      *', '     * ', ' ****  ']],chars: $.realperson.alphanumeric});
});
</script>

<!-- capthca -->
<title>Home Page</title>

<script language="javascript">
	function saveAsset()
	{
	 if(document.forms[0].HrID.value == ""){
		 alert("Please Select To HR");
		 document.forms[0].HrID.focus();
	  return false;	  
		    }
		    
		     if(document.forms[0].subject.value == ""){
		 alert("Please Enter Subject");
		document.forms[0].subject.focus();
	  return false;	  
		    }
		    
		    
		     if(document.forms[0].description.value == ""){
		 alert("Please Enter Description");
		document.forms[0].description.focus();
	  return false;	  
		    }
	
	var Hrid=document.forms[0].HrID.value


	document.forms[0].action="paysliprequest.do?method=submitquery&Hrid="+Hrid;
	document.forms[0].submit();
	}
	
	function statusMessage(message){

alert(message);
}


function closeasset()

{

document.forms[0].action="paysliprequest.do?method=displayquerieslist";
	document.forms[0].submit();
}


function saveAsset(){

  

	
	
		if(document.forms[0].comments.value==""){
		alert("Please Add Some Comments");
		 document.forms[0].comments.focus();
		 return false;
		}
	
	
	var url="paysliprequest.do?method=SubmitHRqueryUSERRESPONSE";

	document.forms[0].action=url;
	document.forms[0].submit();
}
</script>
</head>

<body >
<html:form action="paysliprequest" onsubmit="return processrequest()" enctype="multipart/form-data" method="post">
<div align="center">
				<logic:notEmpty name="paysliprequestForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="paysliprequestForm" property="message" />');
					</script>
				</logic:notEmpty>
				
			</div>

<html:hidden property="req_type" />
	
		<table class="bordered">
		<tr>
<th colspan="4"><center>HR Query Form</center></th>
</tr>
<tr><th colspan="4"><big>User Details</big></th></tr>
<tr><td><b>Name:</b></td><td> <bean:write name="paysliprequestForm" property="empname" /></td>

<td><b>Employee No:</b></td><td><bean:write name="paysliprequestForm" property="req_by" /></td></tr>
<tr>
<td><b>Department:</b></td><td><bean:write name="paysliprequestForm" property="empdep" /></td>
<td><b>Designation:</b></td><td><bean:write name="paysliprequestForm" property="empdesg" /></td></tr>
<tr>
<td><b>Location:</b></td><td><bean:write name="paysliprequestForm" property="emploc" /></td>
<td><b>Ext No:</b></td><td><bean:write name="paysliprequestForm" property="empext" /></td></tr>
<tr><th colspan="4"><big>Other Details</big></th></tr>
<tr><td>Request Number<font color="red">*</font></td><td colspan="3"><bean:write name="paysliprequestForm" property="req_id" /></td></tr>
<tr><td>Category<font color="red">*</font></td><td colspan="3"><bean:write name="paysliprequestForm" property="category" /></td></tr>
<tr><td>Subject<font color="red">*</font></td><td colspan="3"><bean:write name="paysliprequestForm" property="subject" /></td></tr>
<tr><td>Description<font color="red">*</font></td><td colspan="3"><bean:write name="paysliprequestForm" property="description" /></td></tr>
<tr><th colspan="4"><big>Comments</big></th></tr>
<tr>
			<td colspan="7"><html:textarea property="comments" style="width:100%;" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:textarea>	</td></tr>

<tr><td colspan="4"><center>
<logic:equal value="Closed" property="status" name="paysliprequestForm">
<html:button property="method" value="Send to HR" onclick="saveAsset()" styleClass="rounded"/>
</logic:equal>

&nbsp;<html:button property="method" value="Close" styleClass="rounded" onclick="closeasset()"/>
</center></td>

</tr>
	</table>
	
	
	<br/>
	<logic:notEmpty name="trnalist">
	<table class="bordered"><tr><th colspan="5">Transaction List</th>
	<tr><th>Name</th><th>Designation<th>Comments</th><th>Updated Time</th></tr>
	<logic:iterate id="t" name="trnalist">
	<tr><td>${t.empname}</td><td>${t.empdesg}</td><td>${t.comments}</td><td>${t.apprvddate}</td></tr>
	</logic:iterate>

	
	</tr></table>
	
</logic:notEmpty>
		<html:hidden property="req_id"/>
				<html:hidden property="category"/>
		</html:form>
</body>
</html>