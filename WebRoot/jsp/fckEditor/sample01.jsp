<%@ include file="/form/header.jsp"%>

<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="net.fckeditor.*" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<%@ taglib uri="../../WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="../../WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="../../WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<%--
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2004-2010 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 * @version: $Id: sample01.jsp 4785 2009-12-21 20:10:28Z mosipov $
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>FCKeditor - JSP Sample</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="robots" content="noindex, nofollow" />
		<link href="../sample.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="../fckeditor.gif"
				type="image/x-icon" />
		<script type="text/javascript">
			function FCKeditor_OnComplete(editorInstance) {
				window.status = editorInstance.Description;
			}
			
			
			function checkProperty(){
			
			var URL="manageAdds.do?method=storePagesSizeWise";
			document.forms[0].action=URL;
			document.forms[0].submit();		
			
			}
			
			
		</script>
	</head>
	<%
		FCKeditor fckEditor = new FCKeditor(request, "EditorDefault");
	%>
	<body>
		
		<hr/>
		
		 <html:form action="/manageAdds.do"  method="post" target="_blank"  >
		
		
		<!-- <form action="sampleposteddata.jsp" method="post" target="_blank"> -->
		<div id="main">

	<!-- Columns -->
	<div id="cols" class="box">
		<%@ include file="/form/addsSubmenu.jsp"%> 
		
		<div id="content" class="box" style="width:1500px">
		
			<!-- System Messages -->
			<h3 class="tit">Edit Epaper Pages</h3>
		
		<%
		
		String val=(String)request.getAttribute("tableData");
			fckEditor.setValue(val);
			out.println(fckEditor);
		%>
		
		
		<br />
		
	<html:text property="pageNumber" styleClass="textfield" />	
		
	<html:button property="method" value="Submit" onclick="checkProperty()"/>
		
		
		</div>
			<!-- /tab01 -->
			
			<div class="fix"></div>

		</div> 
		<!-- /content -->

	</div> <!-- /cols -->
		</html:form>
	</body>
</html>

