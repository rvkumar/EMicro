
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>


<html>
<head>

<style type="text/css">

#index #menu .index, #page1 #menu .page1 {
  font-weight: bold;
}

</style>

<script language="javascript">

var text=document.getElementById('textInput');
var pswd=document.getElementById('pswdInput');
text.onfocus=pswd.onfocus=function(){
    text.style.display='none';
    pswd.focus();
}
pswd.onblur=function(){
    if(pswd.value==''){
        text.style.display='block';
    }
}

}

</script>
<title></title>
</head>


<body id="page1">
<div id="menu">
 <ul>
  <li class="index"     ><a href="index.html">Index page</a></li>
  <li class="page1"     ><a href="page1.html">Page 1</a></li>
 </ul>
</div> <!-- menu -->
</body>


</html>