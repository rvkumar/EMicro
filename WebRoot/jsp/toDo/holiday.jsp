<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<img  src="images/close.jpg" onclick="hideShowList()" style="height: 20px;width: 20px;"></img>
<div id="tt">
<logic:notEmpty name="apsentList">
   <logic:iterate id="mylist" name="apsentList">
  
         <font size="3">&nbsp;<bean:write name="mylist" property="hol_name"/>&nbsp;</font> 
          
        </logic:iterate>
       
       
        

</logic:notEmpty>
</div>
</body>
</html>