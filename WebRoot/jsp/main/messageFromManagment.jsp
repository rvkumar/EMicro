<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="com.microlabs.admin.form.LinksForm"/>
<jsp:directive.page import="java.util.LinkedList"/>
<jsp:directive.page import="java.util.Map"/>

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>--%>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<link rel="stylesheet" type="text/css" href="style2/css/microlabs.css"/>

<script type="text/javascript">


	
  
  function goBack(){
		var url="main.do?method=displayAnnouncement";
		document.forms[0].action=url;
		document.forms[0].submit();

}

</script>
<%--<style>--%>
<%--<!----%>
<%--.navibutton,.nextnavi,.backnavi,.disableNavi{--%>
<%--font-family: 'Trebuchet MS';--%>
<%--font-style: italic;--%>
<%--font-size: 14px;--%>
<%--color: #127bca;--%>
<%--cursor:pointer;--%>
<%--}--%>
<%--.nextnavi{--%>
<%--float:right;--%>
<%--}--%>
<%--.backnavi{--%>
<%--padding-left: 20px;--%>
<%--}--%>
<%--.disableNavi{--%>
<%--color: #000000;--%>
<%--}--%>
<%---->--%>
<%--</style>--%>

<html:form action="main.do">
	<table id="HeadLineTable" class="content" style="width:100%; heigth:100%; border:0px;">
		<tr>
          	<td colspan="3" class="content" ><bean:write name="mainForm" property="contentDescription" filter="false" /></td>
         </tr>
<%--         <tr><td><span class="backnavi" onclick="goBack()">Back</span></td><td></td><td><span class="nextnavi disableNavi" onclick="goNext()">Next</span></td></tr>--%>
	</table>	
</html:form>