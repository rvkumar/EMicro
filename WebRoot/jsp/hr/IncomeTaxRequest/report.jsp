<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<!--
/////////////////////////////////////////////////
-->

<!-- 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
<script src="js/sumo1.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
    <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
       <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />

    <script type="text/javascript">
        $(document).ready(function () {
            window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
            window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
            window.testSelAll = $('.testSelAll').SumoSelect({okCancelInMulti:true, selectAll:true });
            window.testSelAll2 = $('.testSelAll2').SumoSelect({selectAll:true });

        });
        
         $(document).ready(function () {
           $('.testselect1').SumoSelect();

        });
    </script>
<script type="text/javascript">
function showSelectedFilter(){
	if(document.forms[0].reqRequstType.value!="" && document.forms[0].selectedFilter.value!=""){

		var filter = document.getElementById("filterId").value;
		var url="incomeTaxReq.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
	
		}
	}
function getDetails(url){

if(document.forms[0].locArray.value=="")
{

alert("Please Select Plant");
return false;
}

if(document.forms[0].fiscalYear.value=="")
{

alert("Please Select Fiscal year");
return false;
}

	document.forms[0].action=url;
	document.forms[0].submit();
	}
function approve(){
	if(document.forms[0].reqRequstType.value=="")
	{
	alert("Please Select Request Type");
	 document.forms[0].reqRequstType.focus();
	  return false;
	}
	if(document.forms[0].selectedFilter.value=="")
	{
	alert("Please Select Filter By");
	 document.forms[0].selectedFilter.focus();
	  return false;
	}
	if(document.forms[0].selectedFilter.value!="Pending")
	{
	alert("Please Choose Request Type As Pending");
	 document.forms[0].reqRequstType.focus();
	  return false;
	}

	var rows=document.getElementsByName("selectedRequestNo");
	var checkvalues='';
	var uncheckvalues='';
	for(var i=0;i<rows.length;i++)
	{
	if (rows[i].checked)
	{
	checkvalues+=rows[i].value+',';
	}else{
	uncheckvalues+=rows[i].value+',';
	}
	}
	if(checkvalues=='')
	{
	alert('please select atleast one record');
	}
	else
	{

	var url="incomeTaxReq.do?method=approveRequest";
			document.forms[0].action=url;
			document.forms[0].submit();
			}
	}
	
function nextRecord(){
	var url="incomeTaxReq.do?method=nextRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function previousRecord(){
	var url="incomeTaxReq.do?method=previousRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function firstRecord(){
	var url="incomeTaxReq.do?method=pendingRecords";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function lastRecord(){
	var url="incomeTaxReq.do?method=lastRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function searchEmployee(fieldName){

var reqFieldName=fieldName

	var toadd = document.getElementById(reqFieldName).value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	document.getElementById(reqFieldName).focus();
	if(toadd == ""){
		document.getElementById(reqFieldName).focus();
		document.getElementById("sU").style.display ="none";
		return false;
	}
	var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
        if(reqFieldName=="employeeno"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }
     xmlhttp.open("POST","itHelpdesk.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}

function selectUser(input,reqFieldName){
document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="employeeno"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}
	
	function displayemprecord()
	
	{
	
		if(document.forms[0].chooseKeyword.value!=""){

		var url="incomeTaxReq.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
		}
		
		else
		{
		alert("please enter employee number");
		return false;
		}
	}
	
</script>
 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
</head>
<body>
<html:form action="/incomeTaxReq.do" enctype="multipart/form-data">
<table class="bordered">

<tr>
<th>Plant<font color="red">*</font></th>
<td>

<html:select  property="locArray" name="incomeTaxReqForm" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll" multiple="true">

<html:options name="incomeTaxReqForm"  property="locationIdList" labelProperty="locationLabelList"/>
</html:select>

</td>

<th>Reporting Group</th>
<td colspan="3">
<html:select  property="repgrpArray" name="incomeTaxReqForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="incomeTaxReqForm"  property="repgrpList" labelProperty="repgrpLabelList"/>
</html:select>


</td>

</tr>

<tr><th>Fiscal Year <font color="red" size="2" >*</font></th>
<td colspan="4"><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="incomeTaxReqForm"  property="yearList"></html:options>
</html:select>

</td>
<td>
<html:button property="method" value="Go" styleClass="rounded" onclick="javascript:getDetails('incomeTaxReq.do?method=displayStatusReport')" style="width:100px;"/>

</td>

</tr>



<!-- <tr><td colspan="5"><a onclick="javascript:getDetails('incomeTaxReq.do?method=displayStatusReport')"><font color="black"><big><b>Status Report</b></big></font></a></td></tr></table>
 -->
 </html:form>
</html>				