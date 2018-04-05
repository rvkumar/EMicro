<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>



 <select name="travel_desk_type" id="travel_desk_type"  >
		<option value="">--Select--</option>
		<logic:notEmpty name="travelist">
		<logic:iterate id="a" name="travelist">
		<option value='<bean:write name="a" property="travel_desk_type"/>' >
		<bean:write name="a" property="traveldeskname"/></option>
		</logic:iterate>
		</logic:notEmpty>
</select> 
