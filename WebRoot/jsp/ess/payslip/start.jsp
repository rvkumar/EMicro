<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>  
<head>  

    <script type="text/javascript" language="javascript">
        
        
        function Load() {
        
		var url1="payslip.do?method=monthyear";
				
			document.forms[0].action=url1;
 			document.forms[0].submit();
 				}
 				
 		
    
    </script>
</head>  
   
<body onload="Load()">  
<html:form action="payslip.do"> 

</html:form>  
</body>  
</html>  