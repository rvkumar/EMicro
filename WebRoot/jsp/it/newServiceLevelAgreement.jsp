
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
var url="serviceLevelAgreement.do?method=saveServiceLevelAgreement";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function check1(){

  if(document.getElementById("enableEscLevel1").style.display == "none")
    document.getElementById("enableEscLevel1").style.display = "block";
  else
    document.getElementById("enableEscLevel1").style.display = "none";
  

}

function check2(){

  if(document.getElementById("enableEscLevel2").style.display == "none")
    document.getElementById("enableEscLevel2").style.display = "block";
  else
    document.getElementById("enableEscLevel2").style.display = "none";
  

}
function check3(){

  if(document.getElementById("enableEscLevel3").style.display == "none")
    document.getElementById("enableEscLevel3").style.display = "block";
  else
    document.getElementById("enableEscLevel3").style.display = "none";
  

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
				
				
<html:form action="/serviceLevelAgreement.do" enctype="multipart/form-data">


				
						<table width=100% border="1">
				

				<tr>
					
					<td valign="top" align="left">
						<table width="100%" align="right" border="1">
							<tr>
							
								<td align=center colspan=5 bgcolor="#51B0F8"><font color="white">
									<b>Helpdesk-Service Level Agreements</b></font>
								</td>
							</tr>
							<tr>
								<br/>
								<td class="lightheadbg2" >
									SLA Details
								</td>
							</tr>
							
							<tr>
								<td>
									SLA Name
									<span class="mandatory">*</span>
									<html:text property="slaName" style="margin-left:100"></html:text>
								</td>
							</tr>
							<tr>
								<td>
									Description
									<html:textarea property="description" style="margin-left:107"></html:textarea>
								</td>
							</tr>
							
							<tr>
								<td class="lightheadbg2">
									SLA Rules
								</td>
							</tr>
							<tr>
							<td>
							Criteria<html:text property="criteria" style="margin-left:140"></html:text>
							</td>
							</tr>
							<tr>
							<td>Criteria Type
						<html:text property="criteriaType" style="margin-left:100"></html:text>
						</td>
						
						<tr>
						<td>
						<b>Any request matching the above rules should be responded  <br>within:</b>
						<html:text property="respondedDays"></html:text>Days
						<html:text property="respondedHours"></html:text>Hours
						<html:text property="respondedMins"></html:text>Mins
						
						</tr>
						<tr>
						<td>
						<b>Any request matching the above rules should be resolved   <br>within:</b>
						<html:text property="resolvedDays"></html:text>Days
						<html:text property="resolvedHours"></html:text>Hours
						<html:text property="resolvedMins"></html:text>Mins
						
						</tr>
							<tr>
								<td>
									<html:checkbox property="enableLevel1Esc1" value="Level 1" onchange="check1()">Enable Level 1 Escalation
		        </html:checkbox>
			              <div id="enableEscLevel1" style="display:none">
						
					<table border="1" background="#ECECEC">
				
						<tr>
							<td>
								Escalate to
								<html:text property="escalateTo1"></html:text>
							</td>
						</tr>
						<tr>
						<td>
						<html:radio property="escalateTime1" value="Escalate Before">Escalate Before</html:radio>
						<html:radio property="escalateTime1" value="Escalate After">Escalate After</html:radio>
                             Day: <html:text property="escDay1" value="0"></html:text>
                              |
                              Time :<html:text property="escTime1" value="0"></html:text>
                              Minutes :<html:text property="escMin1" value="0"></html:text>
                              </td>
                              </tr>
					</table>

					</div>

								</td>
								</tr>
						
							<tr>
								<td>
									<html:checkbox property="enableLevel1Esc2" value="Level 2" onchange="check2()">Enable Level 2 Escalation
		</html:checkbox>
			<div id="enableEscLevel2" style="display:none">
						
					<table border="1" background="#ECECEC">
				
						<tr>
							<td>
								Escalate to
								<html:text property="escalateTo2"></html:text>
							</td>
						</tr>
						<tr>
						<td>
						<html:radio property="escalateTime2" value="Escalate Before">Escalate Before</html:radio>
						<html:radio property="escalateTime2" value="Escalate After">Escalate After</html:radio>
                             Day: <html:text property="escDay2" value="0"></html:text>
                              |
                              Time :<html:text property="escTime2" value="0"></html:text>
                              Minutes :<html:text property="escMin2" value="0"></html:text>
                              </td>
                              </tr>
					</table>

					</div>

								</td>
								</tr>
							<tr>
							<tr>
								<td>
									<html:checkbox property="enableLevel1Esc3" value="Level 3" onchange="check3()">Enable Level 3 Escalation
		</html:checkbox>
			<div id="enableEscLevel3" style="display:none">
						
					<table border="1" background="#ECECEC">
				
						<tr>
							<td>
								Escalate to
								<html:text property="escalateTo3"></html:text>
							</td>
						</tr>
						<tr>
						<td>
						<html:radio property="escalateTime3" value="Escalate Before">Escalate Before</html:radio>
						<html:radio property="escalateTime3" value="Escalate After">Escalate After</html:radio>
                              Day:<html:text property="escDay3" value="0"></html:text>
                              |
                              Time :<html:text property="escTime3" value="0"></html:text>
                              Minutes :<html:text property="escMin3" value="0"></html:text>
                              </td>
                              </tr>
                              
                              
					</table>
					
					</div>
					
					<tr>
						<td align="center">
					<html:button property="method" styleClass="button" value="Submit" 	onclick="onSubmit()" />
					</td></tr>
                              </table>
                              <table>
							
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
