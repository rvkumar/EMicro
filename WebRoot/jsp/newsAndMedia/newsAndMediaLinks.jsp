
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.admin.form.LinksForm"/>

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
<script type="text/javascript">

<!--

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0

document.getElementById('conPer').style.cssText = 'border: none;color: grey';
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
</head>

<body onload="MM_preloadImages('images/home_hover.jpg','images/news_hover.jpg','images/ess_hover.jpg','images/hr_hover.jpg','images/it_hover.jpg','images/timeout_hover.jpg','images/admin_hover.jpg'),subMenuClicked('<bean:write name='newsAndMediaForm' property='linkName'/>')">
		<!--------WRAPER STARTS -------------------->
<div id="wraper">
		  	
          <jsp:include page="/jsp/template/header1.jsp"/>
           
          <div class="content">
            	<div class="content_left"><!--------CONTENT LEFT -------------------->
               	  <div class="mail_main"><!--------MAIL MAIN STARTS -------------------->
               	  
               	  
               	  <%
	 
				  //  LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
				 	
				 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= (LinkedHashMap<LinksForm,LinkedList<LinksForm>>) session.getAttribute("SUBLINKS");;
				
				for (Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
			 	    LinksForm mainMenu=entry.getKey();
			 	    %>
			 	     	<div  onclick="subMenuClicked('<%=mainMenu.getLinkName()%>');" class="mail" >
			 	     	<a href="<%=mainMenu.getLinkPath()%>">
			 	     	<img src="<%=mainMenu.getIconName()%>" width="37" height="35" border="0" style="float:left; margin-right:5px;" />
			 	     	<%=mainMenu.getLinkName()%>
			 	     	<img src="images2/up_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:10px;" />
			 	     	</a>
			 	     	</div>
			 	   <div id='<%=mainMenu.getLinkName()%>' style="display: none;">
			 	    <%
			 	    
			 	    for (LinksForm lin : entry.getValue()) {
						%>
							<div class="mail_content"  ><img src="images2/lefarrow.png" border="0" />  <a href="<%=lin.getLinkPath()+"&subLink="+mainMenu.getLinkName()%>"><%=lin.getLinkName()%></a></div>
 						<%
					}%>
					</div>
					<%
			 	}
				%>
 					
 					
                    </div><!--------MAIL MAIN ENDS -------------------->
                    
                    
                    <div style="height:4px;"></div>
                    <!--------CALENDER STARTS -------------------->
                    
                    <!--------CALENDER ENDS -------------------->
                </div><!--------CONTENT LEFT END -------------------->
                
                <div style="padding-left: 10px;width: 70%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
                	
                <div"C:/rudra/2-01-2013/galleria/themes/classic/classic-demo.html">
					
					
					<html:form action="newsAndMedia.do">
					
					
					<html:hidden property="divStatus" name="newsAndMediaForm"/>
					<bean:write name="newsAndMediaForm" property="linkName"/>
					
					<bean:write name="newsAndMediaForm" property="contentDescription" filter="false" />
							
							
							
							<logic:notEmpty name="subLinkDetails">
							
							<table border="0" align="center">
							
							
							        <tr>
										<th>
											Awards & Achievements
										</th>
									</tr>
							
							<logic:iterate name="subLinkDetails" id="abc">
								
									<tr>

										<td>
											<img src="<bean:write name="abc" property="imageName" />" alt="" width="80" height="50"/>
										</td>
										<td>
										<a href="newsAndMedia.do?method=displayLinkContent&id=<bean:write name="abc" property="linkId" />" target="blank"><bean:write name="abc" property="linkTitle" /></a>
										</td>
									</tr>

								</logic:iterate>
								
								</table>
							
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

 <jsp:include page="/jsp/template/footer1.jsp"/>  
 
</body>
</html>
