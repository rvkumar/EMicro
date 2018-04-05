<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
    <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
        <title>Employee Attendance Details</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="Expand, contract, animate forms with jQuery wihtout leaving the page" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <meta name="keywords" content="expand, form, css3, jquery, animate, width, height, adapt, unobtrusive javascript"/>
		<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>
		
		<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" href="css/microlabs.css" />
        <link rel="stylesheet" type="text/css" href="css/TableCSS.css" />
		
		<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="style1/inner_tbl.css" />

		
		
		
		
		
		<script type="text/javascript" src="js/sorttable.js"></script>
		
<script type="text/javascript">
    function onSubmit(){
				
	    if(document.forms[0].empcode.value=="")
	    {
	      alert("Employee Code Should not be Empty");
	      document.forms[0].empcode.focus();
	      return false;
	    }
	    
	    if(document.forms[0].dat.value=="Select")
	    {
	      alert("Month/Year field Should not be Empty");
	      document.forms[0].dat.focus();
	      return false;
	    }
			
			var url1="attendence.do?method=submit";
				
			document.forms[0].action=url1;
 			document.forms[0].submit();

	}
	
	function display()
    {
    
    
    
    if(document.forms[0].mesage.value!="")
    {
    alert(document.forms[0].mesage.value);
    document.forms[0].empcode.value="";
    //document.forms[0].dat.value="May13";
    document.forms[0].empcode.focus();
    }
    //document.forms[0].dat.value="May13";
    document.forms[0].mesage.value=="";
    
    
    
    return false;
    
    }
	
</script>
	
<%--<script type="text/javascript">
var months = new Array("Jan", "Feb", "Mar",
"Apr", "May", "Jun", "Jul", "Aug", "Sep",
"Oct", "Nov", "Dec");

function writeMonthOptions() {

   var today = new Date();
   var date = new Date(today);	
   date.setFullYear(date.getFullYear() - 3)
   var dropDown = document.getElementById("dat");
   var i = 0;
   var optionValues;

   while (today.getTime() >= date.getTime()) {

	var myyear = today.getFullYear().toString(10).substring(2, 4);
      optionValues = months[today.getMonth()] + myyear;
      dropDown.options[i++] = new Option(optionValues, optionValues)
      today.setMonth(today.getMonth() - 1);
   }
}

//window.onload = writeMonthOptions;

</script>
    --%>
    
    
</head>
<%--<body onload="display(),writeMonthOptions()" style="background-color: #8181F7">--%>
	
<%--<body onload="display()" style="background-color: #58ACFA">--%>
<body onload="display()">
<div id="wraper" align="center">
    <html:form action="/attendence.do" enctype="multipart/form-data">
    
    <% 
		                 String status=(String)session.getAttribute("status");		
		             if(status==""||status==null)
		                 {
		
		                    }
		                 else{
		
		                 %>
		                <b><center><font color="red" size="4" ><%=status %></font></center></b>
		                   <%
		                   session.setAttribute("status"," ");
	                       	}
                         %>
    <html:hidden property="mesage"/>
    <html:hidden property="empcode"/>
    
    <%--<div align="center">
	    <center><img id="Img1" alt="" src="images/MLLogo.png"/></center>
	    <center><h2>Attendance Details</h2></center>
    </div>
    
    --%><div align="center">
		<center><h3><%--
		Employee Code: <html:text property="empcode" style=" border: 1px solid #ccc;
    -moz-border-radius: 10px;
    -webkit-border-radius: 10px;

    border-radius: 10px;
    -moz-box-shadow: 2px 2px 3px #666;
    -webkit-box-shadow: 2px 2px 3px #666;

    box-shadow: 2px 2px 3px #666;
    font-size: 11px;
    padding: 4px 7px;
    outline: 0;
    -webkit-appearance: none; background: #f8f8f8;" size="5" maxlength="5" readonly="true"/> &nbsp;&nbsp; 

--%><%--		Month/Year:	<html:select property="dat" style="width:150px;border-color: #339933;border: 1px solid #ccc;-moz-border-radius: 10px;-webkit-border-radius: 10px;border-radius: 10px;-moz-box-shadow: 2px 2px 3px #666;-webkit-box-shadow: 2px 2px 3px #666;box-shadow: 2px 2px 3px #666;font-size: 11px;padding: 4px 7px;outline: 0;-webkit-appearance: none;">--%>
			Month/Year:	<html:select property="dat" style="padding:3px;
    margin: 0;
    -webkit-border-radius:4px;
    -moz-border-radius:4px;
    border-radius:4px;
    -webkit-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    -moz-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    background: #f8f8f8;
    color:#0000;
    border:none;
    outline:none;
    display: inline-block;
    -webkit-appearance:none;
    -moz-appearance:none;
    appearance:none;
    cursor:pointer;">
			<%--<html:option value="Select">-- Select --</html:option>
			<html:option value="May13">May 2013</html:option>
			<html:option value="Apr13">April 2013</html:option>
			<html:option value="Mar13">March 2013</html:option>
			<html:option value="Feb13">February 2013</html:option>
			<html:option value="Jan13">January 2013</html:option>
		--%>
		<html:option value="Select">-- Select --</html:option>
		<html:options property="ar_id" labelProperty="ar_name" name="attendenceForm"/>
		
		</html:select>
	&nbsp;&nbsp;
	
	<input type="text" style="display: none;"/>
	<html:button property="method" value="Submit" onclick="onSubmit()" styleClass="rounded"></html:button></h3></center>
    
    </div>

	<hr/>
    
  	<div id="personalInformation" align="center">
    
    <logic:notEmpty name="detailed_att">
	    <br/>
	    
	    
	    <%--<table class="bordered">
	    <tr>
	    <th>Employee Code</th><th>Name</th><th>Department</th><th>Designation</th>
	    </tr>
	    
	    <tr class="trrem">
	    <td align="center"><bean:write property="empcode1" name="attendenceForm"/></td><td align="center"><bean:write property="name" name="attendenceForm"/></td><td align="center"><bean:write property="department" name="attendenceForm"/></td><td align="center"><bean:write property="designation" name="attendenceForm"/></td>
	    </tr>
	    </table>
	    <br/>
	    --%><br/>
	    <div class="bordered" id="personalInformation"  align="center" style="width: 80%">
	    
	    	<center>&nbsp;&nbsp;&nbsp; WO: Weekly Off &nbsp;&nbsp;&nbsp; SL : Sick Leave &nbsp;&nbsp;&nbsp; HL: Holiday &nbsp;&nbsp;&nbsp; CL : Casual Leave
	    	<br/>Click on any Column Header to sort</small></center>
	    
	    
	    <table class="sortable" width="80%" align="center">
	    <tr>    
	    <th>Date</th><th>Day</th><th>Entry Time</th><th>Exit Time</th><th>Morning</th><th>Evening</th></tr>

    	<%--<logic:iterate id="records" name="arrayl">
		    <tr class="trrem">
		    <td align="center"><bean:write property="day" name="records"/></td>
		    <td align="center"><bean:write property="getday" name="records"/></td>
		    <td align="center"><bean:write property="arr" name="records"/></td>
		    <td align="center"><bean:write property="dep" name="records"/></td>
		    <td align="center"><bean:write property="arrdesc" name="records"/></td>
		   	<td align="center"><bean:write property="depdesc" name="records"/></td>
		    </tr>
	    </logic:iterate>
	    
	    --%>
	    
	    <logic:iterate id="records" name="arrayl">
		    
		    <c:choose>
  				
  				<c:when test="${records.getday=='Sunday'}">
		    
		    <tr class="trrem" style="background-color: #BDBDBD">
		    
		    <td align="center" style="font-weight: bold"><bean:write property="day" name="records"/></td>
  			<td align="center" style="font-weight: bold"><bean:write property="getday" name="records"/></td>
		    <td align="center" style="font-weight: bold"><bean:write property="arr" name="records"/></td>
		    <td align="center" style="font-weight: bold"><bean:write property="dep" name="records"/>&nbsp;</td>
		    <html:hidden property="arrdesc"/>
    		<html:hidden property="depdesc"/>
		   	
		   	<c:choose>
  				<c:when test="${records.arrdesc=='P '}">
    			<td align="center">&nbsp;</td>
  				</c:when>
  				
  				<c:when test="${records.arrdesc=='A '}">
    			<td align="center">&nbsp;</td>
  				</c:when>

  				<c:otherwise>
    			<td align="center" style="font-weight: bold"><bean:write property="arrdesc" name="records"/></td>
  				</c:otherwise>
				</c:choose>
		   	
		   	
		   	<c:choose>
  				<c:when test="${records.depdesc=='P '}">
    			<td align="center">&nbsp;</td>
  				</c:when>
  				
  				<c:when test="${records.depdesc=='A '}">
    			<td align="center">&nbsp;</td>
  				</c:when>

  				<c:otherwise>
    			<td align="center" style="font-weight: bold"><bean:write property="depdesc" name="records"/>&nbsp;</td>
  				</c:otherwise>
				</c:choose>
		   	
		   
		    </tr>
		    
		    </c:when>
		    
		    <c:otherwise>
		    
		    <tr class="trrem">
		    
		    
		   
		    <td align="center"><bean:write property="day" name="records"/></td>
  			<td align="center"><bean:write property="getday" name="records"/></td>
		    <td align="center"><bean:write property="arr" name="records"/></td>
		    <td align="center"><bean:write property="dep" name="records"/>&nbsp;</td>
		    <html:hidden property="arrdesc"/>
    		<html:hidden property="depdesc"/>
		   	
		   	<c:choose>
  				<c:when test="${records.arrdesc=='P '}">
    			<td align="center">&nbsp;</td>
  				</c:when>
  				
  				<c:when test="${records.arrdesc=='A '}">
    			<td align="center">&nbsp;</td>
  				</c:when>

  				<c:otherwise>
    			<td align="center"><bean:write property="arrdesc" name="records"/></td>
  				</c:otherwise>
				</c:choose>
		   	
		   	
		   	<c:choose>
  				<c:when test="${records.depdesc=='P '}">
    			<td align="center">&nbsp;</td>
  				</c:when>
  				
  				<c:when test="${records.depdesc=='A '}">
    			<td align="center">&nbsp;</td>
  				</c:when>

  				<c:otherwise>
    			<td align="center"><bean:write property="depdesc" name="records"/>&nbsp;</td>
  				</c:otherwise>
				</c:choose>
		   	
		    </tr>
		    
		    
		    </c:otherwise>
		    
		    
		    </c:choose>
	    </logic:iterate>

	    <br/>
	    
	    </table>
	    </div>
	    
    </logic:notEmpty>
   
    </div>
    
	</html:form>
	</div>
    </body>
</html>