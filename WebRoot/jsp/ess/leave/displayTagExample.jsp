<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<jsp:directive.page import="com.microlabs.utilities.Leave1"/>
<jsp:directive.page import="com.microlabs.utilities.Leave"/>

<link rel="stylesheet" type="text/css" href="css/styles.css" />

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml">
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <link href="css/micro_style.css" type="text/css" rel="stylesheet" />
 <title>Microlab</title>
 
 <script type='text/javascript' src="calender/js/zapatec.js"></script>
 <!-- Custom includes -->
 <!-- import the calendar script -->
 <script type="text/javascript" src="calender/js/calendar.js"></script>
 
 <!-- import the language module -->
 <script type="text/javascript" src="calender/js/calendar-en.js"></script>
 <!-- other languages might be available in the lang directory; please check your distribution archive. -->
 <!-- ALL demos need these css -->
 <link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
	 
 <!-- Theme css -->
 <link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
 
 <script type="text/javascript" src="js/jquery.min.js"></script>
 <script type="text/javascript" src="js/jqueryslidemenu.js"></script>
 <script type="text/javascript" src="js/validate.js"></script>
 
 
 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" /> 
 <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
 <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script> 
 <link rel="stylesheet" href="/resources/demos/style.css" /> 
 <style>   
 body { font-size: 62.5%; }   
 label, input { display:block; }   
 input.text { margin-bottom:12px; width:95%; padding: .4em; }  
 fieldset { padding:0; border:0; margin-top:25px; }    
 h1 { font-size: 1.2em; margin: .6em 0; }   
 div#users-contain { width: 350px; margin: 20px 0; }  
 div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }  
 div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }  
 .ui-dialog .ui-state-error { padding: .3em; }    
 .validateTips { border: 1px solid transparent; padding: 0.3em; } 
 </style> 


<script type="text/javascript">
<!--

	 function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y ",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }


function applyLeave()
{
	document.forms[0].action="leave.do?method=applyLeave";
	document.forms[0].submit();
}


function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}


function daydiff(first, second) {
    return (second-first)/(1000*60*60*24)
}


function uploadDocument()
{
	document.forms[0].action="leave.do?method=uploadDocuments";
	document.forms[0].submit();
}


function deleteDocumentsSelected()
{
	document.forms[0].action="leave.do?method=deleteDocuments";
	document.forms[0].submit();
}


function displayTabs(param)
{
	document.forms[0].action="leave.do?method=displayTabs&param="+param;
	document.forms[0].submit();
}

function applyLeave(param)
{
	document.forms[0].action="leave.do?method=submit&param="+param;
	document.forms[0].submit();
}

function displayRequests()
{
	document.forms[0].action="leave.do?method=displayRequests";
	document.forms[0].submit();
}


function calculateEndDate()
{
document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
}

function openPopWindow() {

$("#dialog-form").dialog("open");
}

function onSaveColumns()
{
	var fr=document.getElementById("fpr1");
	
	fr.action="leave.do?method=test1";
	fr.submit();
}


function subMenuClicked(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		
	}
	else{
		disp.style.display=''; 
		
		}
}
//-->
</script>


<style>
body {
	font-size: 62.5%;
}

label,input {
	display: block;
}

input.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
 }

h1 {
	font-size: 1.2em;
	margin: .6em 0;
}

div#users-contain {
	width: 350px;
	margin: 20px 0;
}

div#users-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#users-contain table td,div#users-contain table th {
	border: 1px solid #eee;
	padding: .6em 10px;
	text-align: left;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
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

</style>


</head>

<body>
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
           
                	
           <div>
					
					
				<div align="center">
					<logic:present name="leaveForm" property="message">
						<font color="red">
							<bean:write name="leaveForm" property="message" />
						</font>
					</logic:present>
				</div>
					
					
					<html:form action="leave" enctype="multipart/form-data" styleId="fpr1">
					
					
					<%String selectModule=""; %>
					<c:forEach var="c2" items="${collist1}">
					<bean:define id="title1" value="${c2.title}"/>
					<%selectModule+=title1+","; %>
					</c:forEach>
					
					<table>
					
					<tr>
					
					<%int count=0; %>
					<c:forEach var="cl" items="${collist}">
			     		
			     		<bean:define id="title" value="${cl.title}"/>
			     		
			     		<td>
			     		
			     		<%
			     		System.out.println("Getting a selectModule is &&&&&&&&&&&&&&&&&&&&&"+selectModule);
			     		System.out.println("Getting a title is &&&&&&&&&&&&&&&&&&&&&"+title);
			     		System.out.println("Getting a Count is *****************"+count);
			     		
			     		if(selectModule.contains(title)){ 
			     			System.out.println("Inside If is &&&&&&&&&&&&&&&&&&&&&"+selectModule);
			     			count=count++;
			     		%>
      					<input type="checkbox" value="${cl.property},${cl.title}" name="email2" checked="checked" />${cl.title}
      					<%}else if(selectModule.equals("")){
      					 %>
      					<input type="checkbox" value="${cl.property},${cl.title}"  checked="checked" name="email2"/>${cl.title}
      					<% }else{
      					%>
      					<input type="checkbox" value="${cl.property},${cl.title}" name="email2" />${cl.title}
      					<% }%>
      					
      					</td>
      					
   					 </c:forEach>
					</tr>
						
					<tr>
						<td colspan="3"><input type="button" value="Save" onclick="onSaveColumns()"/></td>
					</tr>
						
					</table>
					</html:form>
				
		<span><b><a href="#" onclick="openPopWindow()">Click Here</a>
		<a href="#" onclick="subMenuClicked('frat')">Hide</a>
				</b><span>
					
					
					<html:form action="leave" enctype="multipart/form-data">
			<logic:notEmpty name="testDetails">
					
					<input type="hidden" value="${testDetails}" name="testDetails1"/>
					
					<% 
					
					ArrayList ownerList=(ArrayList)request.getAttribute("testDetails");
					ArrayList a2=new ArrayList();
					
					if (ownerList != null)
       			 	{
              			Iterator itr= ownerList.iterator();
						
			            while(itr.hasNext()){
			            	  Leave1 l=(Leave1) itr.next();
			            	  
			            	  a2.add(l);
			            }
			              
			              session.setAttribute("Art",a2);
				    %>
				                
				    <%
				    }
				    %>
					
					
			<bean:define id="noOfRecor" name="leaveForm" property="noOfRecords"/>
					
			<html:select property="noOfRecords" onchange="onSaveColumns()">
			   <html:option value="2">25</html:option>
			   <html:option value="3">50</html:option>
			   <html:option value="75">75</html:option>
			   <html:option value="100">100</html:option>
			   <html:option value="125">125</html:option>
			   <html:option value="150">150</html:option>
			</html:select>
			<%int count1=0; %>
			<div id="frat" style="display:none;">
			<c:forEach var="cl" items="${collist1}">
			<input type='text'/>
    		</c:forEach>
			</div>
			<display:table name="testDetails" requestURI="/leave.do" pagesize="10" export="true">
			<display:setProperty name="export.pdf.filename"   value="LeaveDetails.pdf" />
			<display:setProperty name="export.rtf.filename"   value="LeaveDetails.rtf" />
			<display:setProperty name="export.excel.filename" value="LeaveDetails.xls" />
			<display:setProperty name="export.csv.filename"   value="LeaveDetails.csv" />
			     
			   
			    
			    
			<c:forEach var="cl" items="${collist1}">
			
     			 <display:column property="${cl.property}" title="${cl.title}" sortable="${cl.sortable}" />
    		</c:forEach>
			     
             </display:table>
          
           
          
           	
             
		   </logic:notEmpty>
            
            </html:form>
            
            
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    
  </tr>
</table>





</body>
</html>
