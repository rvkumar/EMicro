<jsp:directive.page import="java.util.Calendar"/>
<jsp:directive.page import="java.util.GregorianCalendar"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="com.microlabs.toDoTask.dao.ToDoTaskDao"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.*"/>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>To Do</title>

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
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
    
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">

$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});
</script>

<style type="text/css" >
  table {
   font-family: Georgia, Times, serif;
   border-top-left-radius:0.8em;
   border-top-right-radius:0.8em;
   border-bottom-left-radius:0.8em;
    border-bottom-right-radius:0.8em;
   }
   th {
   border: 1px solid #D0D0D0; height: 2em;
   
   font-size: 90%;
   text-transform: uppercase;
   }
   td {
   border: 1px solid #D0D0D0;
  
   height: 4em;
   width:4em;
   padding: 5px;
   vertical-align: top;
   }
   caption {
   font-size: 300%;
   font-style: italic;
   }
   .day {
   text-align: right;
   }
   .notes {
   font-family: Arial, Helvetica, sans-serif;
   font-size: 80%;
   text-align: right;
   padding-left: 20px;
   }
   .holiday {
   background-color:#FFBB8E;
   }
   .weekend {
   background-color:#E6E6E6;
   color: #F3F3F3;
    
   }
 
  </style>
<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
.cal-check a {
  color: #f79901;
  overflow: hidden;
}
</style>
<style type = "text/css">
        #bubble_tooltip{
		        width:200px;
		        height:100px;
                position:absolute;
                display: none;
        }
        #bubble_tooltip .bubble_top{
                position: relative;
                background-image: url(images/bubble_top.gif);
                background-repeat:no-repeat;
              
                }
        #bubble_tooltip .bubble_middle{
                position:relative;
                   background-image: url(images/finaltooltip.png);
                background-repeat: no-repeat;
               
        }
        #bubble_tooltip .bubble_middle div{
		        padding-left: 12px;
                padding-right: 20px;
               
                position:relative;
                font-size: 11px;
                font-family: arial, verdana, san-serif;
                text-decoration: none;
                color: green;	
		        text-align:justify;	
        }
        #bubble_tooltip .bubble_bottom{
                background-image: url(images/bubble_bottom.gif);
                background-repeat:no-repeat;
             
                position:relative;
                top: 0px;
        }
</style>
<!-- Script by hscripts.com -->
<style type = "text/css">
        #bubble_tooltip{
		        width:200px;
		        height:100px;
                position:absolute;
                display: none;
        }
        #bubble_tooltip .bubble_top{
                position: relative;
                background-image: url(images/bubble_top.gif);
                background-repeat:no-repeat;
              
                }
        #bubble_tooltip .bubble_middle{
                position:relative;
                   background-image: url(images/finaltooltip.png);
                background-repeat: no-repeat;
               
        }
        #bubble_tooltip .bubble_middle div{
		        padding-left: 12px;
                padding-right: 20px;
               
                position:relative;
                font-size: 11px;
                font-family: arial, verdana, san-serif;
                text-decoration: none;
                color: green;	
		        text-align:justify;	
        }
        #bubble_tooltip .bubble_bottom{
                background-image: url(images/bubble_bottom.gif);
                background-repeat:no-repeat;
             
                position:relative;
                top: 0px;
        }
        #pl{
             position: relative;
             top: 2%;
             left: 2%;
             background-color: aqua;
             border-radius: 10px;
             height: 30%;
             width: 20%;
        
        
        }
        #ds_data{
         position: relative;
             top: 0%;
             left: 0%;
        }
        #absentList{
        position: absolute;
        top:0%;
        left: 0%;
    	 /* fallback */
	   background-image: url(images/holibgnd.jpg);
	   background-repeat: no-repeat;
	   color:white;
	-webkit-border-radius: 5px ;
	border-top-right-radius:1em;
	border-top-left-radius:1em; 
	border-bottom-right-radius:1em;
	border-bottom-left-radius:1em;
	   
         overflow:auto;
         height: 10%;
         width: auto;
          }
          #hide
          {
          display: none;
          }
</style>
  <style>
	input:focus { 
	    outline:none;
	    border-color:#2ECCFA;
	    box-shadow:0 0 10px #2ECCFA;
	}
	select:focus { 
	    outline:none;
	    border-color:#2ECCFA;
	    box-shadow:0 0 10px #2ECCFA;
	}
	</style>
<script type="text/javascript">
function previousMonth(){

	var url="cmpCalender.do?method=prviousMonth";
				document.forms[0].action=url;
				document.forms[0].submit();
	}
	function viewshift(currentDate) {
		
		
		var emplist =document.forms[0].employeeNo.value;
		
	
		if(emplist=="")
		{
		alert("Selection Result is Empty");
		return false;
		}
		var c=currentDate;
		var plant=document.forms[0].locationId.value;
		
		

		//var x=window.open("cmpCalender.do?method=UpdateCmpdetails&currentDate="+c+"&plant="+plant,"width=100,height=100,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
		var x=window.showModalDialog("hrApprove.do?method=viewemployeeshift&currentDate="+c+"&plant="+plant+"&emplist="+emplist,null, "dialogWidth=500px;dialogHeight=220px; center:yes");

	}

	function goTo()
	{

	  document.frm.submit()
	}

	
		
	function massUpdate(){
		
		
		if(document.forms[0].locationId.value==""){
			alert("Please Select Location");
			document.forms[0].locationId.focus();
			return false;
		}
		if(document.forms[0].frequency.value==""){
			alert("Please Select Frequency");
			document.forms[0].frequency.focus();
			return false;
		}
		if(document.forms[0].day.value==""){
			alert("Please Select Day");
			document.forms[0].day.focus();
			return false;
		}
		if(document.forms[0].month.value==""){
			alert("Please Select Month");
			document.forms[0].month.focus();
			return false;
		}
		if(document.forms[0].holidayType.value==""){
			alert("Please Select Holiday Type");
			document.forms[0].holidayType.focus();
			return false;
		}
		var r = confirm("Are you sure you want to update !");
		if (r == true) {
			var url="cmpCalender.do?method=massUpdate";
			document.forms[0].action=url;
			document.forms[0].submit();
		} 
		
	}	
	
	function search(){
	var hYear=document.forms[0].calYear.value;
	var hMonth=document.forms[0].monthFrom.value;
	if(hYear=="")
	{
	alert("Please enter year");
			document.forms[0].calYear.focus();
			return false;
	}
	
	var url="hrApprove.do?method=shiftManualAssignSearch&hYear="+hYear+"&hMonth="+hMonth;
		document.forms[0].action=url;
		document.forms[0].submit();
	
	}
	function changeReqLoc(){
		var url="hrApprove.do?method=changeReqLoc";
		document.forms[0].action=url;
		document.forms[0].submit();
	}	
		
	function nextMonth(url){
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	function previousMonth(url){
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function statusMessage(message){
alert(message);
}
	
function closeWindow(){
	
	var url="hrApprove.do?method=empshift";
		document.forms[0].action=url;
		document.forms[0].submit();

}
	
	
</script>


</head>
<%!public int nullIntconv(String inv) {
		int conv = 0;

		try {
			conv = Integer.parseInt(inv);
		} catch (Exception e) {
		}
		return conv;
	}%>
		<%
	System.out.println("calender.jsp");
	String iYears = (String) session.getAttribute("iYear");

	System.out.println("Getting a years is " + iYears);

	int iYear = Integer.parseInt(iYears);
	String iMonths = (String) session.getAttribute("iMonth");
	int iMonth = Integer.parseInt(iMonths);

	System.out.println("iMonth=" + iMonth);
	System.out.println("------");
	Calendar ca = new GregorianCalendar();
	int iTDay = ca.get(Calendar.DATE);
	int iTYear = ca.get(Calendar.YEAR);
	int iTMonth = ca.get(Calendar.MONTH);

	if (iYear == 0) {
		iYear = iTYear;
		iMonth = iTMonth;
	}

	GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);

	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);
	 int currentMonth=(iMonth)+1;
	cal = new GregorianCalendar(iYear, iMonth, days);
	int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
	int ryear = iYear;
	int rmonth = iMonth;
	System.out.println("YEAR=" + ryear);
	System.out.println("MONTH=" + rmonth);
    
	System.out.println("weekStartDay=" + weekStartDay);
	System.out.println("days=" + days);
	System.out.println("iTotalweeks=" + iTotalweeks);
	//get employee remainders
	UserInfo user=(UserInfo)session.getAttribute("user");
	int userid=user.getId();
	HashMap map=new HashMap();
	HashMap mlist=new HashMap();
	HashMap leaves=new HashMap();
	String start_date=null;
	String end_date=null;
	Date sdate =new Date();
    Date edate=new Date();
    Date current=new Date();
    
   
    HashMap holidaysList=(HashMap)request.getAttribute("holidaysList");
    System.out.println("holidaysList size="+holidaysList.size());
   
    
   
	
%>
<body >
<html:form action="hrApprove" enctype="multipart/form-data">

<div align="center">
<logic:notEmpty name="hrApprovalForm" property="message">

<script language="javascript">
statusMessage('<bean:write name="hrApprovalForm" property="message" />');
</script>
</logic:notEmpty>
<logic:notEmpty name="hrApprovalForm" property="message2">

<script language="javascript">
statusMessage('<bean:write name="hrApprovalForm" property="message" />');
</script>
</logic:notEmpty>
</div>

<html:hidden property="emplist"  name="hrApprovalForm" />
<div style="width: 90%" >
<table class="bordered" >
		<tr>
		<th colspan="8"><center>Day Wise Shift View</center></th>
	</tr>
			<tr>
			<th colspan="1">Location</th>
		 	<td colspan="2" >
		  		  	<center><bean:write name="hrApprovalForm" property="locationId"/></center>
		  		  	 <html:hidden property="locationId" name="hrApprovalForm" />
			</td>
			
			<th colspan="1">Employee Number</th>
		 	<td colspan="2">
		  	<center>	<bean:write name="hrApprovalForm" property="employeeNo"/></center>
		   <html:hidden property="employeeNo" name="hrApprovalForm" />
			</td>
			<th colspan="1">Department</th>
		 	<td colspan="2">
		  	<center><bean:write name="hrApprovalForm" property="department"/></center>
			</td>
		</tr>
			

</br>
<table>
<logic:notEmpty name="cal">

	
<div>&nbsp;</div>		
<table align="left"  style="width: 70%;top: 0px;" >
 <div align="left" >
 <caption><%-- <a href="#" onclick="previousMonth('hrApprove.do?method=prviousMonth&hYear=<%=ryear%>&hMonth=<%=rmonth%>')"><img src="images/Previous.png" height="25" width="25" align="absmiddle"/></a> --%>
&nbsp;<b><%=new SimpleDateFormat("MMMM").format(new Date(2008,iMonth, 01))%>&nbsp;<%=iYear%></b>&nbsp;
<%-- <a href="#" onclick="nextMonth('hrApprove.do?method=nextMonth1&hYear=<%=ryear%>&hMonth=<%=rmonth%>')"><img src="images/Next.png"  align="absmiddle" title="Next Month" height="25" width="25"/></a></caption> --%>
</div> 

<input type="hidden" name="hYear" value="<%=ryear%>" />
<input type="hidden" name="hMonth" value="<%=rmonth%>" />

</table>

<table align="left"  style="width: 70%;box-shadow: 8px 8px 5px #888888;" >
 <col class="weekend" >
  <col class="weekday" span="5">
  <col class="weekday">
  
  <tr>
    <th id="days"" ><b><big>Sun</big></b></th>
  <th id="days" ><b><big>Mon</big></b></th>
  <th id="days" ><b><big>Tue</big></b></th>
  <th id="days" ><b><big>Wed</big></b></th>
  <th id="days" ><b><big>Thu</big></b></th>
  <th id="days" ><b><big>Fri</big></b></th>
  <th id="days" style="border-top-right-radius:0.5em;"><b><big>Sat</big></b></th>
    </tr>
  
  <%
							LoginDao ad=new LoginDao();
								int cnt = 1;								
								for (int i = 1; i <= iTotalweeks; i++) {
							%>
							<tr>
								<%
											for (int j = 1; j <= 7; j++) {
											if (cnt < weekStartDay || (cnt - weekStartDay + 1) > days) {
								%>
								<td align="center" height="10" >
									&nbsp;
								</td>
								<%
										} else {
											
											
										String currentDate = (cnt - weekStartDay + 1)+"/"+currentMonth+"/"+iYear;
										  
										
										if(holidaysList.containsKey(currentDate))
										{
										  
										  String holidayType=(String)holidaysList.get(currentDate);
										  if((holidayType.equals("SS")))
										  {
											 %>
										<td align="center" height="70" width="70" 
												style="font-family: serif; font-size: 14px; text-decoration: none;">
												<span><b><a href="#" onclick="javascript:viewshift('<%=currentDate %>')">
												<font><%=(cnt - weekStartDay + 1)%></font></a>
												</b><span>
											</td>
											<%
										  } if((holidayType.equals("WO")))
										  {
											 %>
										<td align="center" height="70" width="70" 
												style="font-family: serif; font-size: 14px; text-decoration: none;">
												<span><b><a href="#" onclick="javascript:viewshift('<%=currentDate %>')">
												<font ><%=(cnt - weekStartDay + 1)%></font></a>
												</b><span>
											</td>
											<%
										  }
										  if(holidayType.equalsIgnoreCase("PH")||holidayType.equalsIgnoreCase("SH"))
										  {
										   %>
										<td align="center" height="70" width="70" 
												style="font-family: serif; font-size: 14px; text-decoration: none;">
												<span><b><a href="#" onclick="javascript:viewshift('<%=currentDate %>')">
												<font ><%=(cnt - weekStartDay + 1)%></font></a>
												</b><span>
											</td>
											<%
											  //RED
										  }
										  if(holidayType.equalsIgnoreCase("W"))
										  {
										   %>
												<td align="center" height="70" width="70" 
												style="font-family: serif; font-size: 14px; text-decoration: none;">
												<span><b><a href="#" onclick="javascript:viewshift('<%=currentDate %>')">
												<%=(cnt - weekStartDay + 1)%></font>
												</b><span>
											</td>
											<%
											  //RED
										  }
										
										 
										 }
											}
											cnt++;
										}
								%>
							</tr>
							<%
							}
							%>
							
					<tr><td colspan="8" style="text-align: center;">
	   							<html:button property="method"  value="Close" onclick="closeWindow()" styleClass="rounded" style="width: 100px"></html:button>
				</td></tr>		
							
							
     </table>
     
     </logic:notEmpty>
      
   
<div>&nbsp;</div>

<div style="width: 80%;"  >

<div class="text" style="width: 80%;  " align="center" >

&nbsp;



<!-- </div>
<div class="text" style="width: 80%; " align="center" >
<img src="images/suday_image.png"/>&nbsp;Sunday&nbsp;&nbsp;
<img src="images/secon_sat.png"/>&nbsp;Weekly Off&nbsp;&nbsp;
<img src="images/Company_Holiday_Image.png"/>&nbsp;Company Holiday&nbsp;&nbsp;
</div>
</div>

<div>&nbsp;</div> -->

<%-- <table style="width: 80%;">
<tr><th style="background-color: #D8D8D8;">Mass Updation</th></tr>
<tr>
<td>
Frequency&nbsp;<font color="red">*</font><html:select property="frequency"> 
<html:option value="">Select</html:option>
<html:option value="0">Every</html:option>
<html:option value="1">First Week</html:option>
<html:option value="2">Second Week</html:option>
<html:option value="3">Third Week</html:option>
<html:option value="4">Fourth Week</html:option>
<html:option value="5">Fifth Week</html:option>
</html:select>&nbsp;&nbsp;Day&nbsp;<font color="red">*</font><html:select property="day">
<html:option value="">Select</html:option>
<html:option value="1">SUNDAY</html:option>
<html:option value="2">MONDAY</html:option>
<html:option value="3">TUESDAY</html:option>
<html:option value="4">WEDNESDAY</html:option>
<html:option value="5">THURSDAY</html:option>
<html:option value="6">FRIDAY</html:option>
<html:option value="7">SATURDAY</html:option>
</html:select>
&nbsp;&nbsp;
Months&nbsp;<font color="red">*</font><html:select property="month">
<html:option value="">Select</html:option>
<html:option value="12">All Months</html:option>
	<html:option value="0">JAN</html:option>
	<html:option value="1">FEB</html:option>
	<html:option value="2">MAR</html:option>
	<html:option value="3">APR</html:option>
	<html:option value="4">MAY</html:option>
	<html:option value="5">JUN</html:option>
	<html:option value="6">JUL</html:option>
	<html:option value="7">AUG</html:option>
	<html:option value="8">SEP</html:option>
	<html:option value="9">OCT</html:option>
	<html:option value="10">NOV</html:option>
	<html:option value="11">DEC</html:option>
</html:select>&nbsp;&nbsp;Shift Type&nbsp;<font color="red">*</font>
<html:select property="shift">
<html:option value="">--Select--</html:option>
			<html:options name="hrApprovalForm"  property="shiftList" />
			</html:select>
&nbsp;&nbsp;

</td>
</tr>  
<tr>
<td><center><html:button property="method" value="Apply" onclick="massUpdate()"/></center></td>
</tr>
</table> --%>

<div align="center">
 <logic:notEmpty name="hrApprovalForm" property="message">
 	<font color="green" size="3"><bean:write name="hrApprovalForm" property="message" /></font>
 </logic:notEmpty>
 <logic:notEmpty name="hrApprovalForm" property="message2">
 	<font color="red" size="3"><bean:write name="hrApprovalForm" property="message2" /></font>
 </logic:notEmpty>
</div>
</html:form>
</table>


</body></html>

