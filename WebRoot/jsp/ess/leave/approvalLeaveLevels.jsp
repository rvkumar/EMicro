
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>Home Page</title>

<script language="javascript">

	function openPage(param)
		{
		var x = window.open("approvalLevels.do?method=displayEmpDetails&param="+param,"SearchSID","width=500,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
		}
		
		function onSubmit(){
			var url="approvalLevels.do?method=submit";
			document.forms[0].action=url;
			document.forms[0].submit();
		}
		var no;
		var type;
		function call(no,type){
		var url="approvalLevels.do?method=edit&no="+no+"&type="+type;
			document.forms[0].action=url;
			document.forms[0].submit();
		
		
		}
		
</script>
</head>

<body >
		

				<div align="center">
					<logic:present name="approvalLevelsForm" property="message">
						<font color="red">
							<bean:write name="approvalLevelsForm" property="message" />
						</font>
					</logic:present>
				</div>
				



<html:form action="approvalLevels" enctype="multipart/form-data">
  

	<table border="0" align="center" width="30%" id="mytable1">
						
						<tr><td bgcolor="#51B0F8" colspan="2"><font color="white">
						<center>Define Approval Levels</center>
						</font>
						</td></tr>
						
						
						<html:hidden property="employeeNumber"/>
						
						<html:hidden property="designation"/>
						
						<tr>
							
							<td>Plant<img src="images/smallindex.jpg"/></td>
								
								<td>Type<img src="images/smallindex.jpg"/></td>
								</tr>
								<tr>
											
						
						
						
							
							
							<td>
							<html:select property="plant">
								<html:option value="">--Select--</html:option>
								<html:option value="ML01">Hosur Plant</html:option>
							</html:select>
						</td>
						
						
						
						
					
						
						<td>
							<html:select property="type" >
								<html:option value="">--Select--</html:option>
								<html:option value="leave">Leave</html:option>
								<html:option value="ess">ESS</html:option>
									<html:option value="it">IT</html:option>
									
							</html:select>
						</td>
						
						
						</tr>
						
						
						<tr>
							<td align="center" class="lft style1">
								Approver1
							</td>
							
							<td align="center" class="lft style1">
							<html:text property="approver1" styleId="app1"/>
							
							<html:hidden property="approverNumber1" styleId="approverNumber1"/>
							<html:hidden property="designationNumber1" styleId="designationNumber1"/>
							<a href="javascript:openPage('1')" style="color: #FFC">
		 						<img src="images/add-items.gif" border="0" />
							</a>
							</td>
							
							
							
							
						</tr>
						
						
						<tr>
							<td align="center" class="lft style1">
								Approver2
							</td>
							
							<td align="center" class="lft style1">
							<html:text property="approver2"  styleId="app2"/>
							
							<html:hidden property="approverNumber2" styleId="approverNumber2"/>
							<html:hidden property="designationNumber2" styleId="designationNumber2"/>
							<a href="javascript:openPage('2')" style="color: #FFC">
		 						<img src="images/add-items.gif" border="0" />
							</a>
							</td>
							
							<td align="center" width="10%">
							
							
							
							</td>
						</tr>
						
						
						<tr>
							<td align="center" class="lft style1">
								Approver3
							</td>
							
							<td align="center" class="lft style1">
							<html:text property="approver3"  styleId="app3"/>
							
							<html:hidden property="approverNumber3" styleId="approverNumber3"/>
							<html:hidden property="designationNumber3" styleId="designationNumber3"/>
							
							<a href="javascript:openPage('3')" style="color: #FFC">
		 						<img src="images/add-items.gif" border="0" />
							</a>
							</td>
							
							
						</tr>
					
						<tr>
							<td align="center" class="lft style1">
								Approver4
							</td>
							
							<td align="center" class="lft style1">
							<html:text property="approver4"  styleId="app4"/>
							
							<html:hidden property="approverNumber4" styleId="approverNumber4"/>
							<html:hidden property="designationNumber4" styleId="designationNumber4"/>
							<a href="javascript:openPage('4')" style="color: #FFC">
		 						<img src="images/add-items.gif" border="0" />
							</a>
							</td>
							
							
						</tr>
						
						<tr>
							<td align="center" class="lft style1">
								Approver5
							</td>
							
							<td align="center" class="lft style1">
							<html:text property="approver5"  styleId="app5"/>
							
							<html:hidden property="approverNumber5" styleId="approverNumber5"/>
							<html:hidden property="designationNumber5" styleId="designationNumber5"/>
							<a href="javascript:openPage('5')" style="color: #FFC">
		 						<img src="images/add-items.gif" border="0" />
							</a>
							</td>
							
							</td>
						</tr>
						
						
				<tr>
                    <td colspan="2">
                    <div align="center">
                     <html:button property="method" styleClass="button" value="Submit" onclick="onSubmit();"/></div>
                    </td>
                </tr>
						
						
					</table>
					 <logic:notEmpty name="applist">
					
					 <display:table id="mytable1" name="applist" requestURI="/approvalLevels.do" pagesize="10" export="false">
     	
				       <display:column title="Approver Id"><a href="#" onclick="call('${mytable1.approverNumber},${mytable1.type}' }" }>${mytable1.approverNumber}</a></display:column>
				        <display:column property="designation" title="Designation"></display:column> 
				        <display:column property="type" title="Request Type"></display:column> 
				        <display:column property="plant" title="Plant Code"></display:column>
				       
				           
				           
				            
       
      
         
        </display:table>
				
					
					
					
					</logic:notEmpty>
					
            
            </html:form>
            <div style="clear:both"></div>
           <br/>
         


</body>
</html>
