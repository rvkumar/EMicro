<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/> 
 
 
<style type="text/css">

a:link {
	text-decoration: none;
}

a, u {
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
    
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="8" style="font-family:Arial; font-size:14px; color:#FFFFFF">
      
        <tr>
          <td width="46%" align="right">Welcome&nbsp; : </td>
          <td width="53%" align="left">&nbsp;&nbsp;<%=user.getFullName() %></td>
        </tr>
        
        <tr>
          <td align="right">Last Login&nbsp; : </td>
          <td align="left">&nbsp;&nbsp;<%=user.getLastLoginDate() %></td>
        </tr>
        
        <tr>
          <td align="right">Password Expires on&nbsp; : </td>
          <td align="left">&nbsp;&nbsp;<%=user.getPasswordExpiryDate() %></td>
        </tr>
        
      </table>
    </div>
    <div class="menu_wrapper">
<div class="menucor_L"></div>
<div class="menubg_px">


<%
	    
	    System.out.println("Getting SQL is ***************"+user);
	 	LoginDao ad=new LoginDao();
	 	
	 	String sql="select * from links where id in("+user.getIncludeLinks1()+") order by id";
	 	
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
		<div class="menu_bg" title="HOME"><a href="main.do?method=display1&id=Home">Home</a></div>
		<% } 
		}
		
		}
		}
		
		}catch(SQLException se){
		
		se.printStackTrace();
		}
		
		
		 if(menuIcon.equalsIgnoreCase("News and Media")){ %>

		<div class="menu_bg_bighover" title="News and Media"><a href="newsAndMedia.do?method=display&id=News And Media">News and Media</a></div>
		<% }else{ %>
		<div class="menu_bg_big" title="News and Media"><a href="newsAndMedia.do?method=display&id=News And Media">News and Media</a></div>
		<% }
		 
		 
		  if(menuIcon.equalsIgnoreCase("ESS")){ %>

		<div class="menu_bghover" title="ESS"><a href="leave.do?method=displayCMS&id=ESS">ESS</a></div>
		<% }else{ %>
		<div class="menu_bg" title="ESS"><a href="leave.do?method=displayCMS&id=ESS">ESS</a></div>
		<% }  
		
		if(menuIcon.equalsIgnoreCase("HR")){ %>

		<div class="menu_bghover" title="HR"><a href="hr.do?method=display&id=HR">HR</a></div>
		<% }else{ %>
		<div class="menu_bg" title="HR"><a href="hr.do?method=display&id=HR">HR</a></div>
		<% } 
		
		if(menuIcon.equalsIgnoreCase("IT")){ %>

		<div class="menu_bghover" title="IT"><a href="it.do?method=display&id=IT">IT</a></div>
		<% }else{ %>
		<div class="menu_bg" title="IT"><a href="it.do?method=display&id=IT">IT</a></div>
		<% } 
		
		if(menuIcon.equalsIgnoreCase("FORUM")){ %>

		<div class="menu_bghover" title="FORUM"><a href="#">FORUM</a></div>
		<% }else{ %>
		<div class="menu_bg" title="FORUM"><a href="#">FORUM</a></div>
		
		<% } if(menuIcon.equalsIgnoreCase("ADMIN")){ %>

		<div class="menu_bghover" title="ADMIN"><a href="userGroup.do?method=displayCMS&id=Admin">ADMIN</a></div>
		<% }else{ %>
		<div class="menu_bg" title="ADMIN"><a href="userGroup.do?method=displayCMS&id=Admin">ADMIN</a></div>
		<% } %>


<div class="menucor_R"></div>

</div>
</div>
    </td>
  </tr>
  
  <tr>
    <td align="center" valign="top" class="search_bg">
    <div class="feedback_wrapper"><a href="#" class="link" title="feedback">feedback </a>&nbsp;&nbsp;|&nbsp;&nbsp;
    <a href="#" class="link" title="personalize">personalize</a>&nbsp; |&nbsp; <a href="login.do?method=logout" class="link" title="logout">logout</a></div>
    <div class="search1_icon"><img src="images/search.png" width="26" height="24" /></div>
    <div class="search1"><input type="text" value="Search..." title="Enter keyword to search "/></div>
    <div class="contact">
    <select name="">
      <option>Content</option>
      <option>Name</option>
      </select></div>
    </td>
  </tr>