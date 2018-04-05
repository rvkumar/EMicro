
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Plant Spares</title>

<%--<link href="style1/style.css" rel="stylesheet" type="text/css" />--%>
<%--<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />--%>

<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<%--<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>--%>

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


<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>
<script type="text/javascript" src="calender/js/calendar-en.js"></script>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript">

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


	 function popupCalender(param)
	  {
	      var cal = new Zapatec.Calendar.setup({
	      inputField     :     param,     // id of the input field
	      singleClick    :     true,     // require two clicks to submit
	      ifFormat       :    "%d/%m/%Y ",     // format of the input field
	      showsTime      :     false,     // show time as well as date
	      button         :    "button2"  // trigger button 
	      });
	  }
	  

function getMatrialList(materialList)
{
var materilaType=materialList;


if(materilaType=='1')
{
var url="rawMaterial.do?method=displayNewMaterialCodeMaster";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='2')
{
var url="packageMaterial.do?method=displayNewPackageMaterial";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='3')
{
var url="semifinished.do?method=displayNewSemiFinished";
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='4'||materilaType=='5')
{
var url="finishedProduct.do?method=displayNewFinishedProduct&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}



if(materilaType=='12')
{
var url="promotional.do?method=displayNewPromotional";
			document.forms[0].action=url;
			document.forms[0].submit();	
}

if(materilaType=='13')
{
var url="zpsr.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='10')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='7')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='8')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='14')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='9')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}
if(materilaType=='11')
{
var url="generalMaterial.do?method=displayNewGeneralMaterial&materialType="+materilaType;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

}
function moveMaterialCode(){


var saveType=document.forms[0].typeDetails.value;
if(saveType=="Save")
{
if(document.forms[0].storageLocationId.value!=""||document.forms[0].materialShortName.value!=""||document.forms[0].materialLongName.value!=""||
document.forms[0].materialGroupId.value!=""||document.forms[0].puchaseGroupId.value!=""||document.forms[0].unitOfMeasId.value!=""||document.forms[0].materialUsedIn.value!=""||
document.forms[0].isEquipment.value!=""||document.forms[0].isSpare.value!=""||document.forms[0].equipmentName.value!=""||document.forms[0].prNumber.value!=""||
document.forms[0].poNumber.value!=""||document.forms[0].utilizingDept.value!=""||document.forms[0].approximateValue.value!=""||document.forms[0].valuationClass.value!=""||
document.forms[0].detailedJustification.value!=""||document.forms[0].detailedSpecification.value!=""||document.forms[0].detailedSpecification.value!=""||document.forms[0].moc.value!=""||document.forms[0].rating.value!=""||document.forms[0].range.value!=""
)
{
var agree = confirm('Material code not saved.Are you sure open new material code form');
if(agree)
{



document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";

}
else
{

}
}else{
document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";
}


	 }
	 if(saveType=="Update")
{

document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
document.getElementById("materialTable").style.visibility="hidden";
document.forms[0].materialCodeLists.value="";

}
}


function saveData(param){	

if(document.forms[0].hsnCode.value=="")
	    {
	      alert("Please Enter HSN code");
	      document.forms[0].hsnCode.focus();
	      return false;
	    }
	     var hsnCode = document.forms[0].hsnCode.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(hsnCode)) {
             alert("HSNcode its should be Integer ");
                document.forms[0].hsnCode.focus();
            return false;
        }
       
if(document.forms[0].reqEmail.value=="")
	    {
	      alert("Please Enter Email ID ");
	      document.forms[0].reqEmail.focus();
	      return false;
	    }
	    
	    var str=document.forms[0].reqEmail.value;
	    if(document.forms[0].reqEmail.value!="")
	    {
	    var afterACT = str.substr(str.indexOf("@") + 1);
	    if(afterACT!="microlabs.in")
	    {
	     alert("Please Enter Valid Email ID");
	      document.forms[0].reqEmail.focus();
	      return false;
	    
	    }
	   
	    }



   
if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    }
	    
	     if(document.forms[0].materialTypeId.value=="")
	    {
	      alert("Please Select Material Type");
	      document.forms[0].materialTypeId.focus();
	      return false;
	    }
	    
	      if(document.forms[0].materialShortName.value=="")
	    {
	      alert("Please Enter Material Short Name");
	      document.forms[0].materialShortName.focus();
	      return false;
	    }
	    var st = document.forms[0].materialShortName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialShortName.value=st;
	
	
	      if(document.forms[0].materialLongName.value=="")
	    {
	      alert("Please Enter Material Long Name");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	    
	    if(document.forms[0].materialLongName.value!="")
	    {
	     var a=document.forms[0].materialLongName.value;
	     a=a.length;
	     if(a>80){
	      alert("Material Long Name Should be less than 80 characters");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	        var st = document.forms[0].materialLongName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialLongName.value=st;
	}
	
if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	     
	      if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select Unit of Measurement");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	     if(document.forms[0].puchaseGroupId.value=="")
	    {
	      alert("Please Select Purchase Group");
	      document.forms[0].puchaseGroupId.focus();
	      return false;
	    }
	     
	     
	     
	     
	      
	     /*  if(document.forms[0].isEquipment.value=="")
	    {
	      alert("Please Enter Is it a New Equipment / Machine");
	      document.forms[0].isEquipment.focus();
	      return false;
	    } */
	     var st = document.forms[0].equipmentName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentName.value=st;
	
	   var st = document.forms[0].equipmentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentMake.value=st;
	
	
	    var a=document.forms[0].materialGroupId.value;

if(a=="126"||	a=="135"||	a=="210"||	a=="211"||	a=="219"||	a=="220"||	a=="221"||	a=="222"||	a=="227"||	a=="232"||	a=="233"||	a=="236"||	a=="237"||	a=="238"||	a=="239"||	a=="240"||	a=="244"||	a=="248"||	a=="251"||	a=="253"||	a=="401"||	a=="402"||	a=="403"||	a=="404"||	a=="405"||	a=="406"||	a=="407"||	a=="408")
{
     if(document.forms[0].isEquipment.value=="")
	    {
	      alert("Please Enter Is it a New Equipment / Machine");
	      document.forms[0].isEquipment.focus();
	      return false;
	    }

	     if(document.forms[0].isEquipment.value=="1")
	    {
	
		      if(document.forms[0].equipmentName.value=="")
		    {
		      alert("Please Enter Equipment Name");
		      document.forms[0].equipmentName.focus();
		      return false;
		    }
    var st = document.forms[0].equipmentName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentName.value=st;
	
	
		    if(document.forms[0].equipmentMake.value=="")
		    {
		      alert("Please Enter Equipment Make");
		      document.forms[0].equipmentMake.focus();
		      return false;
		    }
   var st = document.forms[0].equipmentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].equipmentMake.value=st;
	    }
	    
	    
	    
	      if(document.forms[0].isSpare.value=="")
	    {
	      alert("Please Enter Is Spare");
	      document.forms[0].isSpare.focus();
	      return false;
	    } 
	    
	     var st = document.forms[0].componentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].componentMake.value=st;
	
	var st = document.forms[0].oemPartNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].oemPartNo.value=st;
	
	

	    if(document.forms[0].isSpare.value=="1")
	    {
	
		      if(document.forms[0].componentMake.value=="")
		    {
		      alert("Please Enter Component Make");
		      document.forms[0].componentMake.focus();
		      return false;
		    }
		      var st = document.forms[0].componentMake.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].componentMake.value=st;
	
	
		    if(document.forms[0].oemPartNo.value=="")
		    {
		      alert("Please Enter OEM Part No");
		      document.forms[0].oemPartNo.focus();
		      return false;
		    }
		    
	 var st = document.forms[0].oemPartNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].oemPartNo.value=st;
	     } 
	     
	     if(document.forms[0].isItNewFurniture.value=="")
	    {
	      alert("Please Enter Is it New Furniture /Doors/Window");
	      document.forms[0].isItNewFurniture.focus();
	      return false;
	    }
	     if(document.forms[0].isItFacility.value=="")
	    {
	      alert("Please Enter Is it for New Facility / Expanstion Area");
	      document.forms[0].isItFacility.focus();
	      return false;
	    }
	     if(document.forms[0].isSpareNewEquipment.value=="")
	    {
	      alert("Please Enter Is Spare required for New Equipment");
	      document.forms[0].isSpareNewEquipment.focus();
	      return false;
	    }
	     
	     
	     
	     }
	    
	    if(document.forms[0].dimensions.value!="")
	    {
		    var st = document.forms[0].dimensions.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].dimensions.value=st;
	    }
	    
	    if(document.forms[0].packSize.value!="")
	    {
		    var st = document.forms[0].packSize.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].packSize.value=st;
	    }
	    
	     
	          if(document.forms[0].moc.value!="")
	    {
	    var st = document.forms[0].moc.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].moc.value=st;
	    }
	        if(document.forms[0].rating.value!="")
	    {
	    var st = document.forms[0].rating.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].rating.value=st;
	    
	    }
	        if(document.forms[0].range.value!="")
	    {
	    var st = document.forms[0].range.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].range.value=st;
	    
	    }
	     
	     
	   /*   if(document.forms[0].isItNewFurniture.value=="")
	    {
	      alert("Please Enter Is it New Furniture /Doors/Window");
	      document.forms[0].isItNewFurniture.focus();
	      return false;
	    }
	     if(document.forms[0].isItFacility.value=="")
	    {
	      alert("Please Enter Is it for New Facility / Expanstion Area");
	      document.forms[0].isItFacility.focus();
	      return false;
	    }
	     if(document.forms[0].isSpareNewEquipment.value=="")
	    {
	      alert("Please Enter Is Spare required for New Equipment");
	      document.forms[0].isSpareNewEquipment.focus();
	      return false;
	    } */
	    
	    
	     
	    
	    if(document.forms[0].utilizingDept.value=="")
	    {
	      alert("Please Enter Utilizing Dept");
	      document.forms[0].utilizingDept.focus();
	      return false;
	    }
	   
	     if(document.forms[0].approximateValue.value=="")
	    {
	      alert("Please Enter Approximate Value");
	      document.forms[0].approximateValue.focus();
	      return false;
	    }
	   
 		var st = document.forms[0].approximateValue.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].approximateValue.value=st
	
	
	      if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Enter Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
	    if(document.forms[0].detailedJustification.value=="")
	    {
	      alert("Please Enter Detailed Justification");
	      document.forms[0].detailedJustification.focus();
	      return false;
	    }
	      var st = document.forms[0].detailedJustification.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].detailedJustification.value=st
	
	    if(document.forms[0].detailedSpecification.value=="")
	    {
	      alert("Please Enter Detailed Specification");
	      document.forms[0].detailedSpecification.focus();
	      return false;
	    }
	   var st = document.forms[0].detailedSpecification.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].detailedSpecification.value=st
	
	
	    if(document.forms[0].prNumber.value!=""){
	     var prNumber = document.forms[0].prNumber.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(prNumber)) {
             alert("PR Number  Should be Integer or Float!");
                 	      document.forms[0].prNumber.focus();
             
             
            return false;
        }
        }
         if(document.forms[0].poNumber.value!=""){
	     var poNumber = document.forms[0].poNumber.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(poNumber)) {
             alert("PO Number  Should be Integer or Float!");
              document.forms[0].poNumber.focus();
             
            return false;
        }
        }
         
         var st = document.forms[0].equipIntendedFor.value;
 	 	var Re = new RegExp("\\'","g");
 	 	st = st.replace(Re,"`");
 	 	document.forms[0].equipIntendedFor.value=st;
 	 	
 	 	var a=document.forms[0].reqEmail.value;
	    var EMAIL = a.replace("&", "*"); 
	    
	    var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
 	 	
          if(param=="Save")
	    {
        var url="zpsr.do?method=saveGeneralMaterial&EMAIL="+EMAIL;
			document.forms[0].action=url;
			document.forms[0].submit();	
			 }

else{
var url="zpsr.do?method=saveAndSubmitMaterial&EMAIL="+EMAIL;
			document.forms[0].action=url;
			document.forms[0].submit();		
}

}





function onDeleteFile(){
	
	var rows=document.getElementsByName("checkedfiles");

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
alert('please select atleast one value to delete');
}
else
{
var agree = confirm('Are You Sure To Delete Selected file');
if(agree)
{
	document.forms[0].action="finishedProduct.do?method=deleteFileListModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}

function closeData(){
var url="materialCode.do?method=displayMaterialList";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function checkEquipment()
{

var a=document.forms[0].materialGroupId.value;
var b=document.forms[0].isEquipment.value;

if(a=="126"||	a=="135"||	a=="210"||	a=="211"||	a=="219"||	a=="220"||	a=="221"||	a=="222"||	a=="227"||	a=="232"||	a=="233"||	a=="236"||	a=="237"||	a=="238"||	a=="239"||	a=="240"||	a=="244"||	a=="248"||	a=="251"||	a=="253"||	a=="401"||	a=="402"||	a=="403"||	a=="404"||	a=="405"||	a=="406"||	a=="407"||	a=="408")
{



if(b=="1"){

document.getElementById("equipment1").style.visibility="visible";
document.getElementById("equipment2").style.visibility="visible";

}	
if(b=="0"){

document.getElementById("equipment1").style.visibility="hidden";
document.getElementById("equipment2").style.visibility="hidden";

}	
if(b==""){

document.getElementById("equipment1").style.visibility="hidden";
document.getElementById("equipment2").style.visibility="hidden";

}

}
}


function checkSpare()  
{
var a=document.forms[0].materialGroupId.value;
var b=document.forms[0].isSpare.value;


if(a=="126"||	a=="135"||	a=="210"||	a=="211"||	a=="219"||	a=="220"||	a=="221"||	a=="222"||	a=="227"||	a=="232"||	a=="233"||	a=="236"||	a=="237"||	a=="238"||	a=="239"||	a=="240"||	a=="244"||	a=="248"||	a=="251"||	a=="253"||	a=="401"||	a=="402"||	a=="403"||	a=="404"||	a=="405"||	a=="406"||	a=="407"||	a=="408")
{



if(b=="1"){

document.getElementById("spare1").style.visibility="visible";
document.getElementById("spare2").style.visibility="visible";

}	
if(b=="0"){

document.getElementById("spare1").style.visibility="hidden";
document.getElementById("spare2").style.visibility="hidden";

}	
if(b==""){

document.getElementById("spare1").style.visibility="hidden";
document.getElementById("spare2").style.visibility="hidden";

}

}
}

function file(x)
{
alert(x);
}


function searchEqipname(fieldName)
{

var reqFieldName=fieldName;

var location=document.forms[0].locationId.value;

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
        if(reqFieldName=="equipmentName"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}

}
}

xmlhttp.open("POST","zpsr.do?method=searchEquipment&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName+"&loc="+location,true);
xmlhttp.send();
}


function selectUser(input,reqFieldName){


	document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName)
{

  if(reqFieldName=="equipmentName"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
}

function displayApprovers(){
	var locationId=document.forms[0].locationId.value;
	var materialGroupId=document.forms[0].materialGroupId.value;
	var materialTypeId=document.forms[0].materialTypeId.value;
	if(locationId!="" && materialGroupId!=""){
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("apprListID").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("POST","zpsr.do?method=displayApprovers&locationId="+locationId+"&materialGroupId="+materialGroupId+"&materialTypeId="+materialTypeId,true);
		xmlhttp.send();
	}
}
function oseval(){

var a=document.forms[0].materialGroupId.value;

if(a=="126"||	a=="135"||	a=="210"||	a=="211"||	a=="219"||	a=="220"||	a=="221"||	a=="222"||	a=="227"||	a=="232"||	a=="233"||	a=="236"||	a=="237"||	a=="238"||	a=="239"||	a=="240"||	a=="244"||	a=="248"||	a=="251"||	a=="253"||	a=="401"||	a=="402"||	a=="403"||	a=="404"||	a=="405"||	a=="406"||	a=="407"||	a=="408")
{
document.getElementById("newequip").style.visibility="visible";
document.getElementById("newspare").style.visibility="visible";
document.getElementById("newspareequip").style.visibility="visible";
document.getElementById("newfacility").style.visibility="visible";
document.getElementById("newfurniture").style.visibility="visible";



}	
else
{
document.getElementById("newequip").style.visibility="hidden";
document.getElementById("newspare").style.visibility="hidden";
document.getElementById("newspareequip").style.visibility="hidden";
document.getElementById("newfacility").style.visibility="hidden";
document.getElementById("newfurniture").style.visibility="hidden";
document.getElementById("equipment1").style.visibility="hidden";
document.getElementById("equipment2").style.visibility="hidden";
document.getElementById("spare1").style.visibility="hidden";
document.getElementById("spare2").style.visibility="hidden";
}
}

</script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
</head>

<body onload="checkSpare(),checkEquipment()">
   <%
		String menuIcon=(String)request.getAttribute("MenuIcon");
			if(menuIcon==null){
			menuIcon="";
			}
	%>
			
	<% 
		UserInfo user=(UserInfo)session.getAttribute("user");
	%>



	<html:form action="/zpsr.do" enctype="multipart/form-data">
	<div id="masterdiv" class="">
		<html:button property="method" value="New" onclick="moveMaterialCode()" styleClass="rounded"  style="width: 100px"></html:button>
		<br/><p/>
	
		<div style="width: 90%">
		<table class="bordered" width="90%">
			<tr>
				<th colspan="4"><center><big>Material Code Request Form</big></center></th>
			</tr>
			<tr>
				<td>Material Type</td>
				<td><div id="t1" style="visibility: visible;height: 20px;">
					<html:select property="materialCodeLists" name="zpsrForm" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="zpsrForm" property="materTypeIDList" labelProperty="materialTypeIdValueList" styleClass="content"/>
					</html:select>
					</div>
					
					<div id="t2" style="visibility: hidden; height: 0;">
						<html:select property="materialCodeLists1" name="zpsrForm" onchange="getMatrialList(this.value)" >
							<html:option value="">--Select--</html:option>
							<html:options name="zpsrForm" property="materTypeIDList" labelProperty="materialTypeIdValueList" styleClass="content"/>
						</html:select>
					</div>
				</td>
			</tr>
		</table>
		</div>
		
		<div style="visibility: visible; width: 90%">
					<table class="bordered"><tr><th><big>EMAIL ID (For which the confirmation will be Sent)</big><font color="red"><big>*</big></font> </th>
					<logic:notEmpty name="MAILP"> 
					<td><html:text property="reqEmail" name="zpsrForm" style="width: 212px; background-color:#d3d3d3;color:black;"  disabled="true"></html:text> </td>
					</logic:notEmpty>
					<logic:notEmpty name="MAILA"> 
					<td><html:text property="reqEmail" name="zpsrForm" style="width: 212px;"></html:text></td>
					</logic:notEmpty>
					</tr></table>
					
					
					</div>
	
		<div id="materialTable" style="visibility: visible; width: 90%">
			<table class="bordered" width="90%">
   				<tr>
	 				<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>

				<tr>
	 				<td>Request No <font color="red">*</font></td>
					<td><html:text property="requestNo" styleClass="content" readonly="true" style="background-color:#d3d3d3;"/>
						<html:hidden property="typeDetails"/>
					</td>
					<td>Request&nbsp;Date <font color="red">*</font></td>
					<td>
						<html:text property="requestDate" styleId="requestDate" styleClass="content" readonly="true" style="background-color:#d3d3d3;"/>
					</td>
				</tr>

				<tr>
					<td>Location <font color="red">*</font></td>
					<td colspan="3"><html:select name="zpsrForm" property="locationId" styleClass="content" onchange="displayApprovers()">
							<html:option value="">--Select--</html:option>
							<html:options name="zpsrForm" property="locationIdList" labelProperty="locationLabelList"/>
						</html:select>
					</td>
				</tr>

					<html:hidden property="materialTypeId"/>

				<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3">
						<html:text property="materialShortName" styleClass="content" style="width:380px;text-transform:uppercase;" size="55" maxlength="40" ></html:text>
					</td>
				</tr>

				<tr>
					<td>Long Name <font color="red">*</font></td>
					<td colspan="3">
						<html:text property="materialLongName" styleClass="content" style="width:700px;;text-transform:uppercase;" size="90" maxlength="80"></html:text>
					</td>
				</tr>

				<tr>
					<td>Material Group <font color="red">*</font></td>
					<td><html:select name="zpsrForm" property="materialGroupId" styleClass="content" onchange="displayApprovers();oseval();">
							<html:option value="">--Select--</html:option>
							<html:options name="zpsrForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/></html:select>
					</td>
					<td>Storage&nbsp;Location <font color="red">*</font></td>
					<td><html:select  property="storageLocationId" styleClass="content">
							<html:option value="">--Select--</html:option>
							<html:options name="zpsrForm" property="storageID" labelProperty="storageIDName"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>U O M <font color="red">*</font></td>
					<td><html:select property="unitOfMeasId" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:options name="zpsrForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
						</html:select>
					</td>
					<td>Purchase&nbsp;Group <font color="red">*</font></td>
					<td><html:select property="puchaseGroupId" styleClass="text_field" >
							<html:option value="">--Select--</html:option>
							<html:options name="zpsrForm" property="puchaseGroupIdList"	labelProperty="puchaseGroupIdValues"/>
						</html:select>
					</td>
				</tr>

				<tr>
					<th colspan="4"><big>Other Details</big></th>
   				</tr>
			
			
				<!--<tr>
				<th width="274" class="specalt" scope="row">Material Used In<img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="materialUsedIn" styleClass="text_field" >
				<html:option value="">-----Select-----</html:option>
				<html:option value="1">1</html:option>
				<html:option value="2">2</html:option>
				</html:select>
				</td>
				</tr>
				-->
				<html:hidden property="materialUsedIn"/>
				
				<tr> 
				
				
				
					<td>Is&nbsp;it&nbsp;a&nbsp;New&nbsp;Equipment&nbsp;/&nbsp; Machine <div id="newequip" style="visibility: hidden" ><font color="red">*</font></div></td>
					<td colspan="3"><html:select property="isEquipment" styleClass="content"  style="text-transform:uppercase;" onchange="checkEquipment()">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					&nbsp;&nbsp;&nbsp;&nbsp;	
					Equip.intended for
			       <html:text property="equipIntendedFor" styleClass="content" style="width:380px;text-transform:uppercase;" size="55" maxlength="40" ></html:text></td>
				</tr>

				<tr>
					<td>Equipment&nbsp;Name <div id="equipment1" style="visibility: hidden" ><font color="red">*</font></div></td>
					<td colspan="3">
						<html:text property="equipmentName" styleClass="content" styleId="equipmentName" style="width:580px;text-transform:uppercase;" size="80" maxlength="80" onkeyup="searchEqipname('equipmentName')">
						<bean:write property="equipmentName" name="zpsrForm" /></html:text>
					
		 <div id="sU" style="display:none;">
		 <div id="sUTD" style="width:400px;">
		<iframe src="jsp/ess/sapMasterRequest/GeneralMaterial/SearchEquipment.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	  </div>
					
					
					</td>
				</tr>

				<tr>
					<td>Equipment&nbsp;Make <div id="equipment2" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
						<html:text property="equipmentMake" styleClass="content" style="width:200px;text-transform:uppercase;" size="80" maxlength="20"></html:text>
					</td>
					<td>Is Spare <div id="newspare" style="visibility: hidden" ><font color="red">*</font></div></td>
					<td><html:select property="isSpare" styleClass="content" onchange="checkSpare()">
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Component&nbsp;Make <div id="spare1" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
						<html:text property="componentMake" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20" onchange=""></html:text>
					</td>
					<td>OEM Part No.<div id="spare2" styleClass="content" style="visibility: hidden"><font color="red">*</font></div></td>
					<td>
						<html:text property="oemPartNo" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20"></html:text>
					</td>
				</tr>
				<tr>
				<td>Add Size/Dimensions(Dia,Length x width)</td>
				<td><html:text property="dimensions"  size="35" style="text-transform:uppercase;width:280px;"/></td>
				<td>Pack Size</td><td ><html:text property="packSize" styleClass="content" style="width:230px;text-transform:uppercase;" size="80" maxlength="25"></html:text></td>
				</tr>
				<tr>
					<td>MOC </td>
					<td >
						<html:text property="moc" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20"></html:text>
					</td>
					<td>Rating </td>
					<td >
						<html:text property="rating" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20"></html:text>
					</td>
				</tr>
				

				<tr>
				<td>Range </td>
					<td >
						<html:text property="range" styleClass="content" style="width:200px;text-transform:uppercase;" size="20" maxlength="20"></html:text>
					</td>
					<html:hidden property="isNewEquipment"/>
					<!--<td>Is it a New Equipment / Machine <font color="red">*</font></td>
					<td><html:select property="isNewEquipment" styleClass="content" onchange="checkNewEquipment()">
							<html:option value="">--Select--</html:option>
							<html:option value="True">Yes</html:option>
							<html:option value="False">No</html:option>
							<html:option value="N/A">N/A</html:option>
						</html:select>
					</td>
					--><td>Is it New&nbsp;Furniture /Doors&nbsp;/Windows <div id="newfurniture" style="visibility: hidden" ><font color="red">*</font></div></td>
					<td><html:select property="isItNewFurniture" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
							<html:option value="2">N/A</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>Is it for New Facility / Expansion Area <div id="newfacility" style="visibility: hidden" ><font color="red">*</font></div></td>
					<td><html:select property="isItFacility" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
							<html:option value="2">N/A</html:option>
					</html:select></td>
					<td>Is Spare required for New Equipment<div id="newspareequip" style="visibility: hidden" ><font color="red">*</font></div></td>
					<td><html:select property="isSpareNewEquipment" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
							<html:option value="2">N/A</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>PR Number</td>
					<td><html:text property="prNumber" styleClass="content" maxlength="12" style="text-transform:uppercase;"></html:text></td>
					<td>PO Number</td>
					<td><html:text property="poNumber" styleClass="content" maxlength="12" style="text-transform:uppercase;"></html:text></td>
				</tr>

				<tr>
					<td>Utilizing Dept. <font color="red">*</font></td>
					<td><html:select  property="utilizingDept" styleClass="content">
							<html:option value="">Select</html:option>
							<html:options name="zpsrForm" property="deptId" labelProperty="deptName"/>
						</html:select>
					</td>

			<!--<tr>
				<th width="274" class="specalt" scope="row">Purpose<img src="images/star.gif" width="8" height="8" /></th>
				<td>
				<html:select property="purposeID" styleClass="text_field" >
				<html:option value="">-----Select-----</html:option>
				<html:options name="zpsrForm" property="valuationClassID" 
									labelProperty="valuationClassName"/>
				</html:select>
				</td>
			</tr>
			-->
				
				<html:hidden property="purposeID"/>

				<td>Approximate Value <font color="red">*</font></td>
				<td><html:text property="approximateValue" styleClass="content" style="text-transform:uppercase;"></html:text></td>
			</tr>

			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td><html:select property="valuationClass" styleClass="content">
						<html:option value="">--Select--</html:option>
						<html:options name="zpsrForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
					<td>HSN Code<font color="red">*</font></td>
					<td>
						<html:text property="hsnCode" maxlength="8" size="8"> </html:text>
					</td>
				
			</tr>
			<tr>
				<td>Justification <font color="red">*</font></td>
				<td>
					<html:textarea property="detailedJustification" cols="40" rows="5" styleClass="content" style="background-color:#f6f6f6;border:#38abff 1px solid;text-transform:uppercase;" ></html:textarea>
				</td>
				<td>Specification <font color="red">*</font></td>
				<td>
					<html:textarea property="detailedSpecification" cols="40" rows="5" styleClass="content" style="background-color:#f6f6f6;border:#38abff 1px solid;text-transform:uppercase;"></html:textarea>
				</td>
			</tr>

			<logic:notEmpty name="approved">
				<tr>			
					<th colspan="4">Approver Details</th>
	   			</tr>
				<tr>
					<td>Approve Type</td>
					<td><html:select name="zpsrForm" property="approveType" styleClass="content">
							<html:option value="">--Select--</html:option>
							<html:option value="Pending">Pending</html:option>
							<html:option value="Approved">Approved</html:option>
							<html:option value="Cancel">Cancel</html:option>
						</html:select>
					</td>
				</tr>
			</logic:notEmpty>
	
			<logic:notEmpty name="sapApprover">
				<tr>			
					<th colspan="4">Material Code Details</th>
	   			</tr>
				<tr>
					<td>SAP Code No <font color="red">*</font></td>
					<td>
						<html:text property="sapCodeNo" styleClass="content" style="width:200px;text-transform:uppercase;" maxlength="18"></html:text>
					</td>
					<td>SAP Code Exists <font color="red">*</font></td>
					<td><html:select property="sapCodeExists" styleClass="content" >
							<html:option value="">--Select--</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
				</tr>

				<tr>	
					<td>SAP Creation Date <font color="red">*</font></td>
					<td colspan="3">
						<html:text property="sapCreationDate" styleId="sapCreationDate" onfocus="popupCalender('sapCreationDate')" readonly="true" styleClass="content"/>
					</td>
				</tr>
				<tr>
					<td>SAP Created By <font color="red">*</font></td>
					<td><html:text property="sapCreatedBy" styleClass="content"></html:text></td>
					<td>Requested By <font color="red">*</font></td>
					<td><html:text property="requestedBy"  styleClass="content"></html:text></td>
				</tr>
			</logic:notEmpty>		
			
			<tr>
				<td colspan="4" style="text-align: center;">
					<html:button property="method" value="Save" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px" ></html:button>&nbsp;&nbsp;
					<html:button property="method"  value="Save & Submit" onclick="saveData(this.value)" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
					<html:reset value="Reset" styleClass="rounded" style="width: 100px"></html:reset>&nbsp;&nbsp;
					<html:button property="method" value="Close" onclick="closeData()" styleClass="rounded" style="width: 100px"></html:button>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
		displayApprovers();
		</script>
		<div id="apprListID">
		</div>
		</br>
			<div align="center">
		<logic:present name="zpsrForm" property="message">
			<font color="red" size="3"><b><bean:write name="zpsrForm" property="message" /></b></font>
			<script>	
				 	file("<bean:write name="zpsrForm" property="message" /> ") 
				 	</script>
		</logic:present>
		<logic:present name="zpsrForm" property="message2">
			<font color="Green" size="3"><b><bean:write name="zpsrForm" property="message2" /></b></font>
		</logic:present>
		<logic:notEmpty name="zpsrForm" property="appStatusMessage" >
				<br/>
				<font color="red" size="3"><b><bean:write name="zpsrForm" property="appStatusMessage" /></b></font>
		</logic:notEmpty>
	</div>
		</div></div>
</html:form>

</body>
</html>
