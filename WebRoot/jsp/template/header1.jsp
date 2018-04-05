<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/> 
 
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>--%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" 
      href="images/favicon.ico">
</head>
 <script type="text/javascript">
 document.onkeydown = function(e)
{
  var keyCode = document.all ? e.keyCode : e.which;
  if(keyCode == 13) getDetails(); 
}
function getSDetails(){
	var data;
	var reqType=document.forms[0].reqType.value;
 	var keyword=document.forms[0].keyword.value;
	data = new FormData();
	data.append('reqType',reqType);
	data.append('keyword',keyword);
    var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else {
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
             document.getElementById("annouPage").innerHTML=xmlhttp.responseText;
        }
    }

    xmlhttp.open("POST","contacts.do?method=getSearchDetails",true);
    xmlhttp.send(data);
}

 function getDetails()
 {
 	var reqType=document.forms[0].reqType.value;
 	var keyword=document.forms[0].keyword.value;
 	var aref = document.getElementById("searchId").href;
 	var lochref="contacts.do?method=getSearchDetails";
 	lochref = lochref + "&reqType="+reqType+"&keyword="+keyword;
 	document.getElementById("searchId").href = lochref;
 	window.frames["contentPage"].location.replace(document.getElementById("searchId").href);
 	  	
 }
 function createLink(elem){

 	var link = document.getElementById("searchId").href;
 	link = link.substring(link.lastIndexOf("/")+1, link.length);
 	if(elem == 'key')
 	{
 	var test = document.forms[0].reqType.value;
 
 		link ="contacts.do?method=getSearchDetails&reqType="+test+"&keyword="+document.forms[0].keyword.value;
 		document.getElementById("searchId").href = link
 	}
 	else{
 		var test = document.forms[0].reqType.value;
 
 		link = link+"&reqType="+document.forms[0].reqType.value;
 		document.getElementById("searchId").href = link
 		document.forms[0].keyword.value="Enter Keyword";
 	}
 	
 }
 </script>
 
<!--  <script type = "text/javascript" >
    history.pushState(null, null, 'Emicro');
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, 'Emicro');
    });
    
    
    
    </script>   -->
 

    <body onkeydown="return (event.keyCode != 116)">
 <tr>
    <td align="center" valign="top" class="header">
    <div class="logo">&nbsp;</div>
    <div class="emicro_logo">&nbsp;</div>
    <div class="header_middel">
    
    
    <%
			
			String menuIcon=(String)request.getAttribute("MenuIcon");
			
			if(menuIcon==null){
			menuIcon="";
			}
			
			%>
 			<% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
  			<table>
  			<tr><td>
  			<table style="float: right;"><tr><td>
    <div  style="float: right;" >	<img style="border-radius:15px; " " src="<%=user.getEmpPhoto()%>"  alt=""  width="50px" height="50px" align="right" />  &nbsp; &nbsp;</div>
      </td></tr>
     </table>
     </td><td>
			<table width="120%" border="0" align="center" cellpadding="0" cellspacing="4" style="font-family:Arial; font-size:12px; color:#FFFFFF">
        <tr>
          <td width="46%" align="right">Welcome&nbsp; : </td>
          <td width="53%" align="left">&nbsp;&nbsp;<%=user.getFullName() %></td>
        </tr>
        
        <tr>
          <td align="right">Last Login&nbsp; : </td>
          <td align="left">&nbsp;&nbsp;<%=user.getLastLoginDate() %></td>
        </tr>
        
        <tr>
          <td  width="46%"align="right">Password Expires on&nbsp; : </td>
          <td align="left">&nbsp;&nbsp;<%=user.getPasswordExpiryDate() %></td>
        </tr>
        
      </table>
      </td></tr>
      </table>
    </div>
    <div class="menu_wrapper">
<div class="menucor_L"></div>
<div class="menubg_px">


<%
	    
	    System.out.println("Getting SQL is ***************"+user);
	 	LoginDao ad=new LoginDao();
	 	
	 	String sql="select * from links where id in("+user.getIncludeLinks1()+") order by priority";
	 	
	    System.out.println("Getting SQL is ***************"+sql);
	 	
	    ResultSet rs=ad.selectQuery(sql);
	 	try{
	 	while(rs.next()){
	 	
	 	String id=rs.getString("id");
	 	
	 	String sql1="select * from links where id='"+id+"'";
	 	ResultSet rs1=ad.selectQuery(sql1);
	 	
	 	if(rs1.next()){
	 	
	 	if(rs1.getString("link_name").equalsIgnoreCase("Home")){
	
		 if(menuIcon.equalsIgnoreCase("Home")){ %>

		<div class="menu_bghover" title="HOME"><a href="main.do?method=display1&id=Home">Home</a></div>
		<% }else{ %>
			<div class="menu_bg" title="HOME"><span style="align:left"><a href="main.do?method=display1&id=Home">Home</a></span></div>
		<% } 
		} if(rs1.getString("link_name").equalsIgnoreCase("About Company")){
	
		 if(menuIcon.equalsIgnoreCase("About Company")){ %>

		<div class="menu_bgbighover" title="About Company"><a href="newsAndMedia.do?method=display&id=About Company">About Company</a></div>
		<% }else{ %>
		<div class="menu_bg_big" title="About Company"><a href="newsAndMedia.do?method=display&id=About Company">About Company</a></div>
		<% } 
		}if(rs1.getString("link_name").equalsIgnoreCase("ESS")){
	
		 if(menuIcon.equalsIgnoreCase("ESS")){ %>

		<div class="menu_bghover" title="Employee Self Service"><a href="leave.do?method=displayCMS&id=ESS">ESS</a></div>
		<% }else{ %>
		<div class="menu_bg" title="Employee Self Service"><a href="leave.do?method=displayCMS&id=ESS">ESS</a></div>
		<% } 
		}if(rs1.getString("link_name").equalsIgnoreCase("HR")){
	
		 if(menuIcon.equalsIgnoreCase("HR")){ %>

		<div class="menu_bghover" title="HR"><a href="hr.do?method=display&id=HR">HR</a></div>
		<% }else{ %>
		<div class="menu_bg" title="HR"><a href="hr.do?method=display&id=HR">HR</a></div>
		<% } 
		}if(rs1.getString("link_name").equalsIgnoreCase("IT")){
	
		 if(menuIcon.equalsIgnoreCase("IT")){ %>

		<div class="menu_bghover" title="IT"><a href="it.do?method=display&id=IT">IT</a></div>
		<% }else{ %>
		<div class="menu_bg" title="IT"><a href="it.do?method=display&id=IT">IT</a></div>
		<% } 
		}if(rs1.getString("link_name").equalsIgnoreCase("FORUM")){
	
		 if(menuIcon.equalsIgnoreCase("FORUM")){ %>

		<div class="menu_bghover" title="FORUM"><a href="forum.do?method=display&id=Forum">FORUM</a></div>
		<% }else{ %>
		<div class="menu_bg" title="FORUM"><a href="forum.do?method=display&id=Forum">FORUM</a></div>
		<% } 
		}if(rs1.getString("link_name").equalsIgnoreCase("ADMIN")){
	
		 if(menuIcon.equalsIgnoreCase("ADMIN")){ %>

		<div class="menu_bghover" title="ADMIN"><a href="userGroup.do?method=displayCMS&id=Admin">ADMIN</a></div>
		<% }else{ %>
		<div class="menu_bg" title="ADMIN"><a href="userGroup.do?method=displayCMS&id=Admin">ADMIN</a></div>
		<% } 
		}%>
		
		<%}
		}
		
		}catch(SQLException se)
		{
	out.println("You Dont Have Any Permissions");
		}
		catch(NullPointerException se)
		{
			out.println("You Dont Have Any Permissions");
		}%>

<div class="menucor_R"></div>

</div>
</div>
    </td>
  </tr>
  
  <html:form  action="/contacts.do"  method="post" onsubmit="getDetails(); return false;">
   <td align="center" valign="top" class="search_bg">
   <div class="feedback_wrapper">
    <a href="feedBack.do?method=displayFeedBack" class="link" title="feedback" target="contentPage">feedback </a>&nbsp;&nbsp;|&nbsp;&nbsp;
  <%if(!user.getStaffcat().equalsIgnoreCase("2")){ %>
    <a href="personalize.do?method=displayPersonalize" class="link" title="personalize" target="contentPage">personalize</a>&nbsp; |&nbsp; 
    <%} %> 
    <a href="login.do?method=logout" class="link" title="logout">logout</a></div>
<div align="right">
    <html:select property="reqType" onchange="createLink('type')" styleClass="content" >
      <html:option value="Person">Person</html:option>
      <html:option value="Content">Content</html:option>
    </html:select>
   <html:text  property="keyword" value="Search..." onmousedown="this.value='';" style="text-align:left" title="Enter Search keyword..." 
   		onblur="createLink('key')"></html:text>
  
    <a href="contacts.do?method=getSearchDetails" class="link" id="searchId" title="Search..." target="contentPage" >
   <img src="images/search.png" align="absmiddle" width="26" height="24" border="0"/> </a>
   <div>
       </td>
  <!--<tr>
    <td align="center" valign="top" class="search_bg">
    <div class="feedback_wrapper">
    <a href="feedBack.do?method=displayFeedBack" class="link" title="feedback" target="contentPage">feedback </a>&nbsp;&nbsp;|&nbsp;&nbsp;
    <a href="personalize.do?method=displayPersonalize" class="link" title="personalize" target="contentPage">personalize</a>&nbsp; |&nbsp; 
    <a href="login.do?method=logout" class="link" title="logout">logout</a></div>
   
    <div class="search1_icon"><a href="contacts.do?method=getSearchDetails" class="link" id="searchId" title="feedback" target="contentPage" ><img src="images/search.png" align="absmiddle" width="26" height="24" /> </a>&nbsp;</div>
    <div class="search1"> <html:text  property="keyword" value="Search..." onmousedown="this.value='';" onblur="createLink('key')"></html:text></div>
    <div class="contact"><html:select property="reqType" onchange="createLink('type')">
      <html:option value="Person">Person</html:option>
      <html:option value="Content">Content</html:option>
    </html:select></div>
    </td>
  </tr>
--></html:form>
  
  
  </html>
  </body>