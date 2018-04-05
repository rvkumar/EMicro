


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>


<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>sapMasterIframecms</title>

<!--<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>-->

<script type="text/javascript">


function dispContactPer(){
	var conPer=document.getElementById('conPer');
	conPer.value='';
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

function subMenuClicked1(id,status){
	
	var disp=document.getElementById(id);
	
	disp.style.display=status;
}


function subMenuClicked(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		document.forms[0].divStatus.value='none';
	}
	else
	{
		disp.style.display=''; 
		document.forms[0].divStatus.value='';
	}
}
//-->
</script>

<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>



</head>

<body onload="subMenuClicked('<bean:write name='vendorMasterRequestForm' property='linkName'/>')">
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
  
  
    <td align="center" valign="top" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	<html:form action="vendorMasterRequest.do">
					
					<bean:write name="vendorMasterRequestForm" property="contentDescription" filter="false" />
									
									<b>Documents</b>
									<bean:define id="file" name="vendorMasterRequestForm"
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
									
									<br />
									<%
									}
									%>

						<div style="float: right;">
									
									<b>Videos</b>
									<bean:define id="video" name="vendorMasterRequestForm" property="videoFullPath" />
									
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
							</html:form>
							
							</table></td></tr></table></body></html>