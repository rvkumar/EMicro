
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
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />

<title>Home Page</title>

<script language="javascript">

function displayRequest(){

		var url="it.do?method=displayReqIssues";
		document.forms[0].action=url;
		document.forms[0].submit();
	}	

		
</script>
</head>

<body >

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
					<logic:present name="userGroupForm" property="message">
						<font color="red">
							<bean:write name="userGroupForm" property="message" />
						</font>
					</logic:present>
				</div>
				
				<div align="center">
					<logic:present name="userGroupForm" property="statusMessage">
						<font color="red">
							<bean:write name="userGroupForm" property="statusMessage" />
						</font>
					</logic:present>
				</div>



<html:form action="/it.do" enctype="multipart/form-data">

	<table width=100%>
			
			<tr>
			
			<td valign="top" align="left">
		
	<html:select property="status" name="itForm" style="margin-left:100" onchange="displayRequest()">
		    <html:option value="Open">All Open Request</html:option> 
			<html:option value="Closed">Closed</html:option> 
		    <html:option value="On Hold">On Hold</html:option> 
		
		    <html:option value="4">Resolved</html:option>
	</html:select>
			
		
     <display:table id="data" name="sessionScope.itRequestList" requestURI="/it.do" pagesize="10" export="true" >
			     
            <display:column property="requesterId" title="Request ID" sortable="true"   />
            <display:column property="subject" title="subject" sortable="true"  />
            <display:column property="name" title="Requester Name" sortable="true"  />
            
             <display:column property="assignedTo" title="Assigned To" sortable="true"   />
            <display:column property="category" title="Category" sortable="true"  />
            <display:column property="dueBy" title="Due By" sortable="true"  />
            
             <display:column property="status" title="Status" sortable="true"   />
            <display:column property="createdDate" title="Created Date" sortable="true"  />
            <display:column property="priority" title="Priority" sortable="true"  />
            
            
             <display:column property="createdBy" title="Created By" sortable="true"   />
            <display:column property="completedDate" title="Completed Date" sortable="true"  />
            <display:column property="department" title="Department" sortable="true"  />
            
             <display:column property="plant" title="Plant" sortable="true"   />
        </display:table>
	
			</td></tr>
			<tr><td colspan="2"><jsp:include page="/jsp/template/footer.jsp"/></td></tr>
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
