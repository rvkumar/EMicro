<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Headlines & Announcements Display</title>
<link href="style/content.css" rel="stylesheet" type="text/css" />

<link href="css/microlabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" >
var x=true;
var interval;
function  changeBirhDayImg() 
{

if(x==true)
{
document.getElementById("birthdayImg").style.visibility="visible"; 
document.getElementById("birthdayImg").style.height="0px";
document.getElementById("thoughtTable").style.visibility="hidden";
document.getElementById("thoughtTable").style.height="20px";
x=false;
 clearInterval(interval);
 interval=setInterval(changeBirhDayImg,6000);
}
else{
document.getElementById("birthdayImg").style.visibility="hidden";
document.getElementById("birthdayImg").style.height="20px";
document.getElementById("thoughtTable").style.visibility="visible";
document.getElementById("thoughtTable").style.height="0px";
x=true;
clearInterval(interval);
 interval=setInterval(changeBirhDayImg,6000);
}

}

</script>



<style>
.table{border: solid #ccc 1px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 6px;
    border-radius: 5px;
    -webkit-box-shadow: 0 1px 1px #ccc; 
    -moz-box-shadow: 0 1px 1px #ccc; 
    box-shadow: 0 1px 1px #ccc;*border-collapse: collapse; /* IE7 and lower */
    border-spacing: 0;
    width: 95%; 


}

.th
{
background-color: #dce9f9;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:         linear-gradient(top, #ebf3fc, #dce9f9);
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        

    text-shadow: 0 1px 0 rgba(255,255,255,.5); border-left: 1px solid #ccc;
    border-top: 1px solid #ccc;
    padding: 6px;
    text-align: left;    -moz-border-radius: 6px 6px 0 0;
    -webkit-border-radius: 6px 6px 0 0;
    border-radius: 6px 6px 0 0;

}
</style>
</head>

<body onload="changeBirhDayImg()">

            
          
		    
		   
		    	<div id="birthdayImg" style="visibility: visible;height: 20px;" >
		  <img src="images/Birthday.png" height="147" width="166"  style="padding-left: 0px;align:left; border-top-right-radius:1em;border-top-left-radius:1em; border-bottom-right-radius:1em;border-bottom-left-radius:1em; "/>
		    </div>
		    
		    
	<div id="thoughtTable" style="visibility: hidden; height: 0;"> 
	  <table style="height: auto;width: 166px;" class="table"  >
         <tr>
		    <th  class="th"><center><b>Thought For The Day</b></center></th> 
		    </tr>
		    <tr>
		    <td align="center"  >
		    <font style="font-family: Comic Sans MS;" >
		    &nbsp;<bean:write name="mainForm" property="thoughtMsg"/>
		     </font>
		    </td>
		    </tr>
		   </table>
		    </div>
	       		
</body>
</html>