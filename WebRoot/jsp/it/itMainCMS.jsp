
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>

<link rel="stylesheet" type="text/css" href="css/styles.css" />

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<title>Microlab</title>
<script type="text/javascript" src="http://api.html5media.info/1.1.5/html5media.min.js">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
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

function onSubmit(){
	var url="fckEditor.do?method=submit";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function onUpdate(){
	var url="fckEditor.do?method=updateContent";
	document.forms[0].action=url;
	document.forms[0].submit();
}

//-->
</script>
</head>

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg','images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg')">
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
        <!--------HEADER STARTS -------------------->
        	<div class="header">
            	<div class="logo"></div>
                <div style="width:658px; height:135px; float:left;">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family:Arial; font-size:14px; color:#FFFFFF">
  <tr>
    <td width="19%" style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td width="23%" height="38">&nbsp;</td>
    <td width="58%">&nbsp;</td>
  </tr>
  
  <tr>
    <td style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td align="right">Welcome&nbsp; : </td>
    <td> &nbsp;&nbsp;Mr:Kumar</td>
  </tr>
  
  <tr>
    <td style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td align="right">Last Login&nbsp; : </td>
    <td>&nbsp;&nbsp;15th Oct 2012 | 10:00am</td>
  </tr>
  
  <tr>
    <td style="padding-bottom:5px; padding-top:5px;">&nbsp;</td>
    <td align="right">Password Expires on&nbsp; : </td>
    <td>&nbsp;&nbsp;14/10/2013</td>
  </tr>
  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>

              </div>
                <div style="width:230px; height:135px; float:right;">
                	<div class="feedbackmenu"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="4%">&nbsp;</td>
    <td width="96%">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="link"><a href="#">feedback </a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">personalize</a>&nbsp; |&nbsp;   <a href="login.do?method=logout">logout</a></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
                    <div class="logo2"></div>
                </div>
            </div><!--------HEADER ENDS -------------------->
           <!--------MENU STARTS -------------------->
  <div class="left_curve"></div>
                    <div class="menu"><!--------MAIN MENU STARTS -------------------->
                    
                    
                    
                    
                    
                    
                    
                     <%
	 	
	    UserInfo user=(UserInfo)session.getAttribute("user");
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
		 %>
		 <div class="home"><a href="main.do?method=display&id=Home" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image5','','images2/home_hover.jpg',1)"><img src="images2/home.jpg" name="Image5" width="86" height="46" border="0" id="Image5" /></a></div>
     <%}if(rs1.getString("link_name").equalsIgnoreCase("News And Media")){
		 %>
		 <div class="news"><a href="newsAndMedia.do?method=display&id=News And Media" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image6','','images2/news_hover.jpg',1)"><img src="images2/news.jpg" name="Image6" width="165" height="46" border="0" id="Image6" /></a></div>
     <%}if(rs1.getString("link_name").equalsIgnoreCase("ESS")){ %>
     <div class="ess"><a href="leave.do?method=displayCMS&id=ESS" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','images2/ess_hover.jpg',1)"><img src="images2/ess.jpg" name="Image7" width="80" height="46" border="0" id="Image7" /></a></div>
    <% }if(rs1.getString("link_name").equalsIgnoreCase("HR")){ %>
     <div class="hr"><a href="hr.do?method=display&id=HR" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','images2/hr_hover.jpg',1)"><img src="images2/hr.jpg" name="Image8" width="81" height="46" border="0" id="Image8" /></a></div>
    <% }if(rs1.getString("link_name").equalsIgnoreCase("IT")){ %>
     <div class="it"><a href="it.do?method=displayCMS&id=IT" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','images2/it_hover.jpg',1)"><img src="images2/it.jpg" name="Image9" width="67" height="46" border="0" id="Image9" /></a></div>
    <% }if(rs1.getString("link_name").equalsIgnoreCase("Time Out")){ %>
     <div class="timeout"><a href="main.do?method=display&id=Home" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image10','','images2/timeout_hover.jpg',1)"><img src="images2/timeout.jpg" name="Image10" width="125" height="46" border="0" id="Image10" /></a></div>
    <% }if(rs1.getString("link_name").equalsIgnoreCase("Admin")){ %>
     <div class="admin"><a href="userGroup.do?method=displayCMS&id=Admin" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image11','','images2/admin_hover.jpg',1)"><img src="images2/admin.jpg" name="Image11" width="105" height="46" border="0" id="Image11" /></a></div>
    <% }
     
     
     }
     
     }
		
     %>
    
   <%  }catch(SQLException se){
     se.printStackTrace();
     }
     
     %>
                    
            <div class="contactperson"><input type="text" value="Contact Person" size="15" 
 />            
            <div class="arrow"><img src="images/downarrow.png" /></div>

                      </div>
                      <div class="search"><input type="text" size="15"/>
                      </div>
                      <div class="icon4search"><img src="images/search.png" /></div>
                    
  </div><!--------MAIN MENU ENDS -------------------->
                    <div class="right_curve"></div>
            
           <!--------MENU ENDS -------------------->
           
  <div class="blank"></div>
           
            
            <!--------CONTENT STARTS -------------------->
        	<div class="content">
            	<div class="content_left"><!--------CONTENT LEFT -------------------->
               	  <div class="mail_main"><!--------MAIL MAIN STARTS -------------------->
                  <%
	 
	    LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
	 	Set set=hm.entrySet();
	 	
	 	Iterator itr=set.iterator();
	 	
	 	while(itr.hasNext()){
	 	Map.Entry me=(Map.Entry)itr.next();
	 	
		 %>
		
		<%
		
		if(!me.getKey().equals("Arr")){ %>
		
		 <div class="mail"><a href="<%=me.getKey()%>"><img src="images2/menus.jpg" width="37" height="35" border="0" style="float:left; margin-right:5px;" /><%=me.getValue()%><img src="images2/up_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" /></a></div>
		 
		<%}%>
		
		<%if(me.getKey().equals("Arr")){
		
		 ArrayList h=(ArrayList)me.getValue();
		 Iterator itr1=h.iterator();
				%>
				
				<% 
		  while(itr1.hasNext()){
		  String[] link=itr1.next().toString().split(",");
		 %>
		 <div class="mail_content"><img src="images2/lefarrow.png" border="0" />  <a href="<%=link[1]%>"><%=link[0]%></a></div>
		 
		 <%} %>
		
		 
		 <%} %>
		 
		 <%} %>                    </div><!--------MAIL MAIN ENDS -------------------->
                     </div><!--------MAIL MAIN ENDS -------------------->
                 
                </div><!--------CONTENT LEFT END -------------------->
                
                <div style="padding-left: 10px;width: 70%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
                    <div>
					
					
					   <div align="center">		
				   	<logic:present name="itForm" property="message">
						<font color="red">
						<bean:write name="itForm" property="message" />
						</font>
					</logic:present>
				  </div>
	
	
		<html:form action="it.do">
		
		
					
					<bean:write name="itForm" property="contentDescription" filter="false" />
					<logic:notEmpty name="itForm" property="fileFullPath">
					<table border="1">
					<tr>
									<th><b>Documents</b></th>
					</tr>
					<tr><td>
									<bean:define id="file" name="itForm"
										property="fileFullPath" />
								<%
										String s = file.toString();
										s = s.substring(0, s.length());
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) {
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
									%>
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>

						</td>
					</tr>
									<%
									}
									%>
					</table>
</logic:notEmpty>
<logic:notEmpty name="itForm" property="videoFullPath">
						<div style="float: right;">
									
									<b>Videos</b>
									<bean:define id="video" name="itForm" property="videoFullPath" />
									
									<%
										String s1 = video.toString();
										
										
										System.out.println("Getting A Video Name is **********************"+s1);
										
										s1 = s1.substring(0, s1.length());
										String v1[] = s1.split(",");
										int l1 = v1.length;
										for (int j = 0; j < l1; j++) {
										int x=v1[j].lastIndexOf("/");
											String u=v1[j].substring(x+1);
									%>
									
							<video width="320" height="200" controls="controls">
								<source src="<%=s1 %>" type="video/mp4">
								
								 	Your browser does not support the video tag
								</source>
							</video>
							<br/><br/><br/><br/>	
									<%
									}
									%>
							
							
							
							</div>
							</logic:notEmpty>
							</html:form>
						</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    
  </tr>
</table>
<!-------------- FOOTER STARTS ------------------------->
    <div class="footers" >
    	<div class="footer1">
        
        	<div class="bottom">
        	<div class="left-blocks">
            
            
            <p>&copy;|2012|Micro Labs Limited|All rights reserved</p>
            
            
            </div>
            
            
            
            <div class="right-blocks">
            
            
            <p>...Because health is in small details</p>
            
            
            </div>
            
            </div>
        	
        
        </div>
        
        
        
        </div>
                

		</div><!-------------- FOOTER ENDS ------------------------->
</body>
</html>
