<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Semi Finished Creation</title>

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

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript">

function changeStatus(elem){
  
	var elemValue = elem.value;
	if(elem=="Reject")
	{
	if(document.forms[0].comments.value!=""){
	  alert("Please Add Some Comments");
	  }
	
	}
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
	var matGroup=document.forms[0].reqMaterialGroup.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back(-2);
  }
  
 function getCurrentRecord(){
	var url="materialCode.do?method=curentRecord";
	document.forms[0].action=url;
	document.forms[0].submit();


} 	  
</script>


</head>
  
  <body>
  <font face="Arial" >
<table class="bordered" >
<tr>
<th colspan="9"><center>R&D Information</center></th></tr>
<tr><th><b>Sl.No.</b></th><th><b>R&D&nbsp;Code</b></th><th><b>Unit Head</b></th><th><b>Address</b></th><th><b>City</b></th><th><b>State</b></th><th><b>Postal</b></th><th><b>Telephone</b></th><th><b>E-mail</b></th><tr>
<tr><td>1</td><td><a href="newsAndMedia.do?method=displayabtcmpny&sId=Research And Development" style="text-decoration: none;color: black;"><b><u>ML18 </u></b></a></td><td>Mr.Kshirasagar Rajesh</td><td>No 58C/12, Singasandra Post Kudulu Village, Anekal Taluk Hosur Road</td><td>Bangalore </td><td>Karnataka</td><td>560068</td><td> 080-25731936-38</td><td>rajeshk@microlabs.in</td></tr>
<tr><td>2</td><td><a href="newsAndMedia.do?method=displayabtcmpny&sId=Research And Development2" style="text-decoration: none;color: black;" ><b><u>ML27</u></b></a></td><td>Dr. Pramod Kumar</td><td>PLOT NO. 43-45, K.l.A.D.B., JIGANl-BOMMASANDRA LINK ROAD, ANEKAL TALUK</td><td>Bangalore  </td><td>Karnataka</td><td>560105</td><td>08110-415647, 415648</td><td> pramodkumar@microlabs.in</td></tr>

</body>
</html>