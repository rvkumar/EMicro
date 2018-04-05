<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
					<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>


	<html:select name="approvalsForm" property="state">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="stateList" labelProperty="stateLabelList"/>
					</html:select>
				
								
									</body>
									</html>