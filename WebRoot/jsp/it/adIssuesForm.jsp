
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/><link rel="stylesheet" type="text/css" href="css/styles.css" />
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Home Page</title>

<script language="javascript">

function onSubmit(){
var url="activeDirectory.do?method=saveADRequestAction&savedType=Inbox";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function onDrafts(){
var url="activeDirectory.do?method=saveADRequestAction&savedType=Drafts";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function OnRequest(){

var url="activeDirectory.do?method=displayRequriedADForm";
	document.forms[0].action=url;
	document.forms[0].submit();
}

		
</script>
</head>

<body  onload="document.forms[0].groupName.focus()">

<div id="container">
<div class="header-background">



<div class="head">

<div class="logo-block">

<div class="logos"><img src="images/logo.png" /></div>
<div class="text">Micro Labs Ltd</div>

</div>

<div class="head-middle-block">
<p>Welcome: Mr. Kumar<br/>
Last Login: 15th Oct 2012 | 10:00am<br/>
Password Expires on: 14/10/2013
</p>

</div>


<div class="head-right-block">

<div class="topmenu-block">

		<ul>
        
        		<li><a href="#">feedback&nbsp;</a><span style="color:#ffffff;">|</span></li>
                <li><a href="#">personalize&nbsp;</a><span style="color:#ffffff;">|</span></li>
                <li><a href="login.do?method=submit">logout</a></li>
		</ul>



</div>

			<div class="topsecond-block">

				<img src="images/emicro.png" border="0" target="_blank" />

			</div>
			
			
				<div style="clear:both"></div>

				


</div>


</div>

	<div class="back">

		<div class="menu-bar">
        
        <ul>
                    
        <%
	 	
	    UserInfo user=(UserInfo)session.getAttribute("user");
	    System.out.println("Getting SQL is ***************"+user);
	 	LoginDao ad=new LoginDao();
	 	
	 	String sql="select * from links where id in("+user.getIncludeLinks1()+")";
	 	
	 	
	    System.out.println("Getting SQL is ***************"+sql);
	 
	    ResultSet rs=ad.selectQuery(sql);
	 	try{
	 	while(rs.next()){
	 	
	 	String id=rs.getString("id");
	 	
	 	String sql1="select * from links where id='"+id+"'";
	 	ResultSet rs1=ad.selectQuery(sql1);
	 	
	 	if(rs1.next()){
		 %>
		 <li><a href="<%=rs1.getString("link_path")+"?method="+rs1.getString("method")+"&id="+rs1.getString("link_name")%>"><%=rs1.getString("link_name")%></a></li>
     <%}
		
     }%>
     </ul>
   <%  }catch(SQLException se){
     se.printStackTrace();
     }
     
     %>
                            
          </ul>       
               
	  </div>
                
               
            <div class="back-left">
    		
    		<div class="contact">
            
            <input type="text" class="textbox" value="CONTACT&nbsp;/&nbsp;PERSON" name="CONTACT/PERSON" />
            <div class="arrow"><img src="images/downarrow.png" /></div>
            
            </div>
    		
    		<div class="contact1">
            
            <input type="text" class="textbox1" value="ENTER THE KEYWORD" name="name" />
               
           </div>     
             
            <div class="searchtool">
            
            <img src="images/searchsymbol.png" border="0" />
            
            </div>
             
      </div>
				
 
</div>


</div>

<div style="clear:both"></div>
<jsp:include page="../template/mainMenu2.jsp" />


</div>			

				<div align="center">
					<logic:present name="serviceLevelAgreementForm" property="message">
						<font color="red">
							<bean:write name="serviceLevelAgreementForm" property="message" />
						</font>
					</logic:present>
				</div>
					<% 
		String status=(String)session.getAttribute("status");		
		if(status==""||status==null)
		{
		
		}
		else{
		
		%>
		<b><font color="red"><%=status %></font></b>
		<%
		session.setAttribute("status", " ");
		}
		 %>
				
<html:form action="/it.do" enctype="multipart/form-data">
							
						
	<table width=100%>
			
		<tr>
			<td valign="top" align="left">
		
     <display:table id="data" name="sessionScope.adReqList" requestURI="/activeDirectory.do" pagesize="10" export="true" >
			     
            <a href=""><display:column property="reqADId" title="Request ID" href="activeDirectory.do?method=displaySavedADDraftForm" paramId="reqADId"   sortable="true"/></a>
            <display:column property="employeeNo" title="Employee No" sortable="true"  />
            <display:column property="adLoginName" title="AD Login Name" sortable="true"  />
            
             <display:column property="requiredFolderAccess" title="Folder Access" sortable="true"   />
             
        </display:table>
	
			</td></tr>
			
		</table>
						
            </html:form>
            <div style="clear:both"></div>
           <br/>
          <div class="footer">
					
					<div class="left-block">
                    
                    <div class="paragraph"><p>&copy;|2012|Micro Labs Limited|All rights reserved</p></div>
                    
                    </div>
            <div class="demo-block">
            
            
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...Because health is in small details</p>
            
            
            </div>
            
</div>


</body>
</html>
