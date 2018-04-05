<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="style/content.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />


<script type="text/javascript" >

function nextMaterialRecord()
{

var url="main.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function previousMaterialRecord()
{

var url="main.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstMaterialRecord()
{

var url="main.do?method=firstRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastMaterialRecord()
{

var url="main.do?method=lastRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function goBack(){
		var url="main.do?method=displayAnnouncement";
		document.forms[0].action=url;
		document.forms[0].submit();

}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Read_More</title>

</head>

<body>
	<html:form action="main.do">
			<logic:notEmpty name="HeadLinesData">
					<div class="headline">
                        <div class="head_leftcurve"></div>
                        <div class="head_bg">
<br/>
<logic:notEmpty name="displayRecordNo">

	<table >
		<tr>	
	  		<td align="center">
	  			<img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/>
			<logic:notEmpty name="disablePreviousButton">
				<img src="images/disableLeft.jpg" align="absmiddle"/>
			</logic:notEmpty>

			<logic:notEmpty name="previousButton">
				<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>
			</logic:notEmpty>

			<bean:write property="startRecord"  name="mainForm"/>-
			<bean:write property="endRecord"  name="mainForm"/>

			<logic:notEmpty name="nextButton">
				<img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/>
			</logic:notEmpty>

			<logic:notEmpty name="disableNextButton">
				<img src="images/disableRight.jpg" align="absmiddle" />
			</logic:notEmpty>
			<img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/>
		</td>
		</tr>
	</table>
	</div>
	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
		<html:hidden property="requiredType"/>
	
	</logic:notEmpty>

<div >
	<big ><strong><img src="images/lines.png" align="" /><font color="#9F81F7" size="4px"> HEADLINES</br></font></strong></big>
	<table><tr><td class="underline"></td></tr></table>
	</br>
            
			<logic:iterate name="HeadLinesData" id="headLines">
			<table >
			<tr>
			<td style="width: 5%;">
             <table ><tr>
             <th style="background-color: #FF4500;size: 10px;-webkit-border-radius: 1px ;border-top-right-radius:0.5em;border-top-left-radius:0.5em; "><font color="white" > <big><bean:write name="headLines" property="announcementMonth" /></big></br></font>  
             
             </th>
             </tr>
             <tr>
             <td style="background-color: #F5DEB3;size: 10px;-webkit-border-radius: 1px ;border-bottom-right-radius:0.5em;border-bottom-left-radius:0.5em; "><center><b><bean:write name="headLines" property="announcementDay" /></b></center></td>
             </tr>
             </table>
             </td>
             <td>&nbsp;</td>
             <td>
				 		<a href="main.do?method=getContentDescription&Type=Head Lines&ContentId=${headLines.id}" style="text-decoration: none" title="Read More..">
				 			<ul><strong><big><i><font color="gray" size="2px">&nbsp;<bean:write name="headLines" property="linkDescription" filter="false"/></font></i><big></strong></ul>
				 		</a>
				</td></tr></table> 		
				 		</br>
				<table><tr><td class="underline"></td></tr></table>
				</br>
				</logic:iterate>
	<tr>
    	<td><html:button property="method" value="Back" onclick="goBack()" styleClass="rounded" style="width: 100px"></html:button></td>
	</tr>
</div>
</logic:notEmpty>
					
					
					<logic:notEmpty name="OrganisationData">
						<div class="headline">
                        <div class="head_leftcurve"></div>
                        <div class="head_bg">
                   <div >
<div>
<big ><strong><img src="images/speaker.png" align="absmiddle" /><font color="#9F81F7">&nbsp;ORGANIZATION ANNOUNCEMENTS</br></font></strong></big> 
	 		</br><table><tr><td class="underline"></td></tr></table></br>
					<logic:iterate name="OrganisationData" id="OrganisationLines">
					<table >
			<tr>
			<td style="width: 5%;">
             <table ><tr>
             <th style="background-color: #FF4500;size: 10px;-webkit-border-radius: 1px ;border-top-right-radius:0.5em;border-top-left-radius:0.5em; "><font color="white" > <big><bean:write name="OrganisationLines" property="announcementMonth" /></big></br></font>  
             
             </th>
             </tr>
             <tr>
             <td style="background-color: #F5DEB3;size: 10px;-webkit-border-radius: 1px ;border-bottom-right-radius:0.5em;border-bottom-left-radius:0.5em; "><center><b><bean:write name="OrganisationLines" property="announcementDay" /></b></center></td>
             </tr>
             </table>

</td>
             <td>&nbsp;</td>
             <td>
 	<a href="main.do?method=getContentDescription&Type=ORGANIZATION ANNOUNCEMENTS&ContentId=${OrganisationLines.id}" >
 <ul><strong><big><i><font color="gray" size="2px"><bean:write name="OrganisationLines" property="linkDescription" filter="false"/></font></i></big></strong></ul></a></font>

</td></tr></table> 		
				 		</br>
				<table><tr><td class="underline"></td></tr></table>
				</br>
</div>
		
</logic:iterate>

		</div>			
					
</tr>
                    	
					 <tr>
        <td><html:button property="method" value="Back" onclick="goBack()" styleClass="rounded" style="width: 100px"></html:button></td>
        </tr>
        </table>
						
					</logic:notEmpty>
						
							</html:form>
</body>
</html>
