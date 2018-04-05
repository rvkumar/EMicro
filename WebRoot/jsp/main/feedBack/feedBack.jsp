<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
 <STYLE TYPE="text/css">
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}

</STYLE>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>eMicro :: Feedback</title>

<style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript">

function MM_preloadImages() { 
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function subMenuClicked(id){
		
		var disp=document.getElementById(id);
		
		if(disp.style.display==''){
			disp.style.display='none';
			document.getElementById("mailTe").src = "images/left_menu/up_arrow.png";
			document.getElementById("mail12").className = "mail";
		}
		else{
			disp.style.display=''; 
			document.getElementById("mailTe").src = "images/left_menu/down_arrow.png";
			document.getElementById("mail12").className = "mailhover";
		}
	}
	
  function resizeIframe(obj) {
  
  if((obj.contentWindow.document.body.scrollHeight)<478){
  obj.style.height ='478px';
  }else{
  obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }
  
  }
	

</script>

<script type="text/javascript">
function saveFeedBack(){

if(document.forms[0].subject.value=="")
	    {
	      alert("Please Enter Subject");
	      document.forms[0].subject.focus();
	      return false;
	    }
		var st = document.forms[0].subject.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		document.forms[0].subject.value=st;  
	    if(document.forms[0].comment.value=="")
	    {
	      alert("Please Enter Some Comments");
	      document.forms[0].comment.focus();
	      return false;
	    }
	    var st = document.forms[0].comment.value;
	    var Re = new RegExp("\\'","g");
	    st = st.replace(Re,"`");
	    document.forms[0].comment.value=st;  
	  
		var url="feedBack.do?method=saveFeedBackDetails";
		document.forms[0].action=url;
		document.forms[0].submit();

}

</script>

</head>

<body>
	<logic:present name="feedbackForm" property="message">
		Message : <font color="green" size="+1"><bean:write name="feedbackForm" property="message" /></font>
	</logic:present>
	<html:form action="feedBack.do" enctype="multipart/form-data" styleId="feedForm">
	<div style="width: 70%">
		<table width="70%" class="bordered">
			<tr>
			<th colspan="2"><big>Feedback</big></th>
			</tr>

			<tr>
				<td width="15%">Subject <font color="red">*</font></td>
				<td><html:text property="subject"  style="width:80%;text-align:left;" maxlength="50" styleId="toSubject" styleClass="content">
						<bean:write property="subject" name="feedbackForm" />
					</html:text>
				</td>
			</tr>

			<tr>
				<td width="15%">Comments <font color="red">*</font></td>
				<td><html:textarea property="comment" styleId="bdyContent" style="height:150px;width:80%;" styleClass="content">
					</html:textarea>
				</td>
			</tr>

			<tr>
			    <td colspan="2" style="text-align: center;">
			    	<html:button onclick="saveFeedBack()" property="method" value="Submit" styleClass="rounded" style="width:100px"></html:button>
			    	&nbsp; 
					<html:reset value="Clear" styleClass="rounded" style="width:100px"/>
    	        </td>
    	    </tr>
    	    <tr>
    	    	<td colspan="2" style="text-align: right"><small>Thanks for helping us to improve eMicro</small></td>
		    </tr>
		</table>
<%--		<div class="content">--%>
<%--           <logic:notEmpty name="feedBackList">--%>
<%--            	<h3>FeedBack Details</h3>--%>
<%--            	<table align="left">--%>
<%--            	<logic:iterate id="feedBackId" name="feedBackList">--%>
<%--            	<tr><td>--%>
<%--            	User Name=<bean:write name="feedBackId" property="userName"/></td></tr>--%>
<%--            	<tr><td>--%>
<%--            	EmailId=<bean:write name="feedBackId" property="emailId"/></td></tr>--%>
<%--            	<tr><td>--%>
<%--            	Subject=<bean:write name="feedBackId" property="subject"/></td></tr>--%>
<%--            	<tr><td>--%>
<%--            	Comments=<bean:write name="feedBackId" property="comment"/></td></tr>--%>
<%--            	<tr><td>--%>
<%--            	Approve Status=<bean:write name="feedBackId" property="approveStatus"/></td></tr>--%>
<%--            	--%>
<%--            	<tr>--%>
<%--            	<td>--%>
<%--            	..........................................................................--%>
<%--            	</td>--%>
<%--            	</tr>--%>
<%--            	--%>
<%--            	</logic:iterate>--%>
<%--            	</table>--%>
<%--            </logic:notEmpty>--%>
<%--            	--%>
<%--            	--%>
<%--            --%>
<%--            	</div><!--------WRAPER ENDS -------------------->--%>
	</div>	
	</html:form>
</body>
</html>
