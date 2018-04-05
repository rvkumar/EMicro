<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<logic:notEmpty name="noOfDays">
<logic:iterate id="a" name="noOfDays">
<bean:write name="a" property="totalLeaveDays" />
</logic:iterate>
</logic:notEmpty>
<html:text property="totalLeaveDays" name="ltaForm" readonly="true" onchange="checkTotalDays()" styleClass="text_field"  style="border:0;"/>

