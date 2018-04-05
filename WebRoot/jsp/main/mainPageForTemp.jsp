
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/><link rel="stylesheet" type="text/css" href="css/styles.css" />
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Home Page</title>
</head>

<body>

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
	    System.out.println("user id="+user.getId());
	 	LoginDao ad=new LoginDao();
	 	
	 	String sql="select * from links where id in("+user.getIncludeLinks1()+")";
	 	
	 	
	 	
	    System.out.println("Getting SQL22 is ***************"+sql);
	 
	    ResultSet rs=ad.selectQuery(sql);
	 	try{
	 	while(rs.next()){
	 	 System.out.println("Getting SQL11 is ***************"+sql);
	 	String id=rs.getString("id");
	 	
	 	String sql1="select * from links where id='"+id+"'";
	  	System.out.print("sql1="+sql1);
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
            
            
            <img src="images/searchsymbol.png" borde="0" />
            
            </div>
             
             
      </div>
               
      
                
                
				

</div>
				



</div>

<div style="clear:both"></div>
<div class="main-block">


<div class="mainleft-block">

		

		
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
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

			</div>


  
            


</div>


</div>



</body>
</html>
