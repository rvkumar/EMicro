

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
	function newreq()
	{
	
	document.forms[0].action="paysliprequest.do?method=newquery";
	document.forms[0].submit();
	}
	
	function statusMessage(message){

alert(message);
}

function getDetails(url){
var url=url;

		document.forms[0].action=url;
		document.forms[0].submit();
}

function displayhelpdeskrecord()
	{
		var req=document.forms[0].chooseKeyword.value;
		if(req==""||req=="Req No:")
			{
			alert("Please Enter Request No.");
			 document.forms[0].chooseKeyword.focus();
			  return false;
			}
			
		
		var url="paysliprequest.do?method=HRqueryUSERRESPONSE&reqId="+req;
		document.forms[0].action=url;
			document.forms[0].submit();
	}

function showSelectedFilter(){
if(document.forms[0].selectedFilter.value!=""){


	var url="paysliprequest.do?method=displayquerieslist&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
}

    function isNumber(evt) {
    if(document.getElementById("reqno").value=="Req No:")
document.getElementById("reqno").value="";


    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
    }
</script>
</head>

<body >
<html:form action="paysliprequest" onsubmit="return displayhelpdeskrecord();return false;" enctype="multipart/form-data" method="post">
<div align="center">
				<logic:notEmpty name="paysliprequestForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="paysliprequestForm" property="message" />');
					</script>
				</logic:notEmpty>
				
			</div>

<html:hidden property="req_type" />
	&nbsp;&nbsp;<table class="bordered"><tr><td><center><html:button property="method" value="New" onclick="newreq()" styleClass="rounded" style="width: 81px;"/>	
	</center></td>
	<th><b>Filter by</b> <font color="red">*</font></th>
						<td>
						<html:select property="selectedFilter" styleClass="content" styleId="filterId" onchange="showSelectedFilter()">
						
							
							<html:option value="New">New</html:option>
							<html:option value="Open">Open</html:option>	
							<html:option value="Closed">Closed</html:option>		
						    <html:option value="ReOpen">ReOpen</html:option>	
							<html:option value="All">All</html:option>
							</html:select>
						</td>
						<td colspan="3">
						<html:text property="chooseKeyword" title="Enter Request Number" styleClass="rounded" onkeypress="return isNumber(event)" value="Req No:" styleId="reqno"/>&nbsp;&nbsp;&nbsp;
<html:button property="method" value="Go" styleClass="rounded" onclick="displayhelpdeskrecord()" style="width:100px;"/></td>
						</td></tr></table>
	<br/><br/>
	<logic:notEmpty name="query">
		<table class="bordered">
	<tr><th colspan="8"><center>My Request</center></th></tr>
	<tr><th>Sl no</th><th> Req no</th><th> Req Date</th><th> Subject</th><th>Category<th> Description</th><th>HR Status</th><th> View</th>
	</tr>
	<%int h=0; %>
	<logic:iterate id="a" name="query">
	<%h++; %>
	<tr><td><%=h %></td><td>${a.req_id}</td><td>${a.req_date}</td><td>${a.subject}</td><td>${a.category}</td><td> ${a.description}</td><td>${a.status}</td>
	<logic:equal value="Closed" name="a" property="status">
	<td><a onclick="javascript:getDetails('paysliprequest.do?method=HRqueryUSERRESPONSE&reqId=${a.req_id}')" ><img src="images/view.gif" height="28" width="28"/></a></td>
	</logic:equal>
<logic:notEqual value="Closed" name="a" property="status">
<td>&nbsp;</td>
</logic:notEqual>
	</tr>
	</logic:iterate>
	</table>	
	</logic:notEmpty>
	
	<logic:empty name="query">
	<table class="bordered">
	<tr><th colspan="10"><center>My Request</center></th></tr>
<tr><th>Sl no</th><th> Req no</th><th> Req Date</th><th> Req Status</th><th> Subject</th><th> Pending Approver</th><th> Last Approver</th><th>Approved Date</th></tr>
	<tr><td colspan="10"><center>No records...</center></td></tr>
	</table>
	</logic:empty>
		</html:form>
</body>
</html>