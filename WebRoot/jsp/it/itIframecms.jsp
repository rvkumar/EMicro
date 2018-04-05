
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:directive.page import="com.microlabs.newsandmedia.dao.NewsandMediaDao,com.microlabs.hr.form.HRForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<font face="Arial">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>

<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
}

		.fancybox-custom .fancybox-skin {
			box-shadow: 0 0 50px #222;
		}
</style>


	
	
	
</head>



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
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />


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
function nextPage(){
alert("hi");
var url="it.do?method=displaySecondCMS";
	document.forms[0].action=url;
	document.forms[0].submit();

}
//-->
</script>

<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>



</head>

<body  >
<font face="Arial">
<table class="bordered" style="font-size: 10">

<html:form action="it.do">
<bean:write name="itForm" property="contentDescription" filter="false" />
									

  <tr><th>Welcome </th></tr><tr><td>

<p>

Welcome to the Information Technology Services <font color="#9D1CB1">(ITS)</font> Department page. We are responsible for Company-wide deployment of information technologies and information technology services for use by the staff.</p>
<p>Our Organization is sophisticated with world class IT infrastructure, maintained by trained professionals at Corporate Office. Our Data Center is located at Corporate office, Bangalore, INDIA.</p>
</td>
</tr>
   	</table>
   	<div>&nbsp;</div>
   	<table class="bordered">
<tr><th><strong>Our Mission</strong></th></tr>

	<tr>
		<td>
			
"The mission of IT department is to empower Micro Labs in the use of technology through the delivery of an efficient, adaptable & well managed service. Provide strategic IT vision, leadership, and enterprise solutions to the staff so they can meet their goals, deliver results, and enhance the company's position"	<br /><br />	

Supported by the following values:</br></br>
		A)Approachable<br/>
		B)Supportive<br/>
		C)Professional<br/>
		D)Pro Active<br/>
		E)Innovative<br/>
		F)Knowledgeable<br/>
		G)Informative<br/>
		</td></tr>
		
   	</table>
   	<br/>
   	
   	
   	
   	<br/>
   <div align="right"><a href="it.do?method=displaySecondCMS"><img src="images/Next-2.png" height="20px" width="80px" align="absmiddle"/></a></div>
  </html:form>
</body>
</html>