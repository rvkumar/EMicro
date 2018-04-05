<jsp:directive.page import="java.util.Calendar"/>
<jsp:directive.page import="java.util.GregorianCalendar"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="com.microlabs.toDoTask.dao.ToDoTaskDao"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="com.microlabs.utilities.VendorMaster"/>


<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Microlab</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
		
		<!--<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>-->
		<!-- PopUp Window -->
		
		<link rel="stylesheet" href="style/jquery-ui.css" />
		<script src="js/jquery-1.8.3.js"></script>
		<script src="js/jquery-ui.js"></script>
		
		
		<script type="text/javascript">
		
		function popupCalender(param)
		{
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%Y/%m/%d ", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2" // trigger button
		});
		}
		
		
		function MM_swapImgRestore() { //v3.0
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}
		
		function MM_preloadImages() { //v3.0
		 document.getElementById('conPer').style.cssText = 'color: grey;border:none;';
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		
		function MM_findObj(n, d) { //v4.01
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}
		
		function MM_swapImage() { //v3.0
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}
		
		function subMenuClicked(id){
		
			var disp=document.getElementById(id);
			
			if(disp.style.display==''){
				disp.style.display='none';
				document.forms[0].divStatus.value='none';
				}
			else{
				disp.style.display=''; 
				document.forms[0].divStatus.value='';
				}
		}
		
				function ClearPlaceHolder (input) {
		
		            if(input.value==input.defaultValue) {
		                document.getElementById('conPer').style.cssText = 'color: black;border:none;';
		                input.value = "";
		            }
		        }
		        
		        function SetPlaceHolder (input) {
		            if(input.value == "") {
		            
		            document.getElementById('conPer').style.cssText = 'color: grey;border:none;';
		            input.value = input.defaultValue;
		            }
		        }
		
		
		function bigImg(x)
		{
		x.style.height="64px";
		x.style.width="64px";
		}
		
		function normalImg(x)
		{
		x.style.height="0px";
		x.style.width="0px";
		}
		
		
		function nextMonth(){
		var s=document.forms[0].hYear.value;
		var url="todoTask.do?method=nextMonth1";
					document.forms[0].action=url;
					document.forms[0].submit();
		}
		
		function previousMonth(){
		
		var url="todoTask.do?method=prviousMonth";
					document.forms[0].action=url;
					document.forms[0].submit();
		}
		
		function openPopWindow(param) {
		
		var year1=document.forms[0].hYear.value;
		var month1=document.forms[0].hMonth.value;
		
		
		document.forms[0].currentDate.value=param+'/'+(parseInt(month1)+1)+'/'+year1;
		
		$( "#dialog-form" ).dialog( "open" );
		}
		
		function goTo()
		{
		  document.frm.submit()
		}
		
		function saveToDoTask(){
				
			var subject = $("#subject");
			var description= $("#description");
			var sheduleFromTime= $("#sheduleFromTime");
			var sheduleToTime= $("#sheduleToTime");
			var remarks= $("#remarks");
			var status= $("#status");
			var taskdate= document.forms[0].currentDate.value;
				
			var url="todoTask.do?method=saveToDoTask&subject="+subject.val()+"&description="+description.val()+
			"&sheduleFromTime="+sheduleFromTime.val()+"&remarks="+remarks.val()+"&status="+status.val()+"&taskdate="+taskdate+"&sheduleToTime="+sheduleToTime.val();
					
			document.forms[0].action=url;
			document.forms[0].submit();
		}
		
function onSaveColumns()
{
	var fr=document.getElementById("fpr1");
	
	fr.action="approvals.do?method=test1";
	fr.submit();
}
		
		//-->
		</script>


<style>

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
a:visited{
	text-decoration: none;
}
a:hover{
	text-decoration: none;
}
a:active{
	text-decoration: none;
}

</style>
</head>

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg',
'images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg')"  >


<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

  <tr>
  
  
  
            <%
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
			
			<% 
  			  UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  
  
    <td align="center" valign="top" style="background-image:url(images/bg1.jpg); background-repeat:repeat-x;  height:180px; width:100%"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
     
    <jsp:include page="/jsp/template/header1.jsp"/>
    
    
    <tr>
        <td colspan="3" align="center" valign="top" style="padding-top:3px">
        <div class="middelpart">
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="24%" align="left" valign="top"><div class="left-blocks">
      <!--------CONTENT LEFT -------------------->
      <div class="mail_main">
        <!--------MAIL MAIN STARTS -------------------->
        <div class="mail"><a href="#"><img src="images/letterimg.png" width="37" height="35" border="0" style="float:left; margin-right:5px;" />MAIL<img src="images/up_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" /></a></div>
       <!--  <div class="mail_content"><img src="images/lefarrow.png" border="0" /> New</div>
        <div class="mail_content"><img src="images/lefarrow.png" border="0" /> Inbox</div>
        <div class="mail_content"><img src="images/lefarrow.png" border="0" /> Sent</div> -->
        <div class="mail"><a href="approvals.do?method=display"><img src="images/tick.png" width="37" height="35" border="0" style="float:left; margin-right:5PX;" />APPROVALS<img src="images/down_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" /></a></div>
        <div class="mail"><a href="#"><img src="images/myreq.png" width="37" height="35" border="0" style="float:left; margin-right:5px;" />MY REQUEST<img src="images/down_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" /></a></div>
        <div class="mail"><a href="todoTask.do?method=displaycalender"><img src="images/dolist.png" width="37" height="35" border="0" style="float:left; margin-right:5px;" />TO DO<img src="images/down_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" /></a></div>
        <div class="mail"><a href="#"><img src="images/contactico.png" width="37" height="35" border="0" style="float:left; margin-right:5px;" />CONTACTS<img src="images/down_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" /></a></div>
      </div>
      
      <!-------- MAIL MAIN ENDS -------------------->
      
      <!--------CALENDER STARTS -------------------->
      
      <iframe src="/EMicro/jsp/main/calendar.jsp"  width="220px" height="210px" scrolling="no" frameborder="0" style="border:none; ">
	  				
	  </iframe>       
      
      <!--------CALENDER ENDS -------------------->
    </div></td>
    <td colspan="3" align="left" valign="top">
      
          
    <div class="middel-blocks">
          
          
          	<div id="dialog-form" title="Add Task">
				
					<html:form action="approvals" enctype="multipart/form-data" styleId="fpr1">
				
				
				
					<input type="hidden" value="${testDetails}" name="testDetails1"/>
					
				
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
				</div>
          	<% 
					
					ArrayList ownerList=(ArrayList)request.getAttribute("requestDetails");
					ArrayList a2=new ArrayList();
					
					if (ownerList != null)
       			 	{
              			Iterator itr= ownerList.iterator();
						
			            while(itr.hasNext()){
			            	  VendorMaster l=(VendorMaster) itr.next();
			            	  
			            	  a2.add(l);
			            }
			              
			              session.setAttribute("Art",a2);
				    %>
				                
				    <%
				    }
				    %>
          
           <display:table name="requestDetails" requestURI="/leave.do" pagesize="2" export="true">
			<display:setProperty name="export.pdf.filename"   value="LeaveDetails.pdf" />
			<display:setProperty name="export.rtf.filename"   value="LeaveDetails.rtf" />
			<display:setProperty name="export.excel.filename" value="LeaveDetails.xls" />
			<display:setProperty name="export.csv.filename"   value="LeaveDetails.csv" />
			     
			<c:forEach var="cl" items="${collist1}">
			<c:out value="${cl.title}"/>
     			 <display:column property="${cl.property}" title="${cl.title}" sortable="${cl.sortable}" />
    		</c:forEach>
			     
            </display:table>
          
        
        
    </div></td>
     
     
  </tr>
</table>
</div>
</td>
      </tr>
      </table></td></tr>
    <tr><td>   
     <div class="footers">
    	<div class="footer1">
        
        	<div class="bottom">
        	<div class="copyright">
            
            
            <p>&copy;|2012|Micro Labs Limited|All rights reserved</p>
            
            
            
            </div>
            
            <div class="right-blocks">
            
            
            <p>...Because health is in small details</p>
            
            
            </div>
           </div>
           </div>
           </div>
</td></tr>
</table>



</body>
</html>
