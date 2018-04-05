<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 
 <script type='text/javascript' src="calender/js/zapatec.js"></script>
 
 <script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
 <!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
 <script src="js/sumo1.js"></script>
<script type="text/javascript">

function popupCalender(param)
	{
		var toD = new Date();
		if(param == "endDate"){
			var sD = document.forms[0].startDate.value;
			var sDate = sD.split("/");
			toD = new Date(parseInt(sDate[2]),parseInt(sDate[1]),parseInt(sDate[0]));
		}
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
		});
	}


function nextRecord(){
var url="materials.do?method=nextRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function previousRecord(){
var url="materials.do?method=previousRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="materials.do?method=lastRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function firstRecord(){


var url="materials.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
}
function showSelectedFilter()
{


 if(document.forms[0].materialCodeLists.value=="")
{

alert("Please Select Material Type");
document.forms[0].materialCodeLists.focus();
return false;
}


if(document.forms[0].fromDate.value=="")
{

alert("Please Select From date");
document.forms[0].fromDate.focus();
return false;
}


if(document.forms[0].toDate.value=="")
{

alert("Please Select To date");
document.forms[0].toDate.focus();
return false;
}

if(document.forms[0].locationId.value=="")
{

alert("Please Select  Location");
document.forms[0].locationId.focus();
return false;
}



if(document.forms[0].materialCodeLists.value!="" && document.forms[0].selectedFilter.value!=""){
var url="materials.do?method=pendingRecords&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
	
}
function completed()
{

var rows=document.getElementsByName("selectedRequestNo");

var j=0;
for(var i=0;i<rows.length;i++)
{

if (document.getElementById('code'+i).checked==true)
{


if(document.getElementById('itemCode'+i).value=="")
{
alert("Please enter Itemcode");
document.getElementById('itemCode'+i).focus();
return false;
}


j=j+1;

}

}


if(j==0)
{
alert('please select atleast one record');
}


var url="materials.do?method=approveMaterialRequest";
		document.forms[0].action=url;
		document.forms[0].submit();


}



	
		

function createInSAP()
{

var rows=document.getElementsByName("selectedRequestNo");

var j=0;
var sapnos = new Array();
for(var i=0;i<rows.length;i++)
{

if (document.getElementById('code'+i).checked==true)
{
sapnos.push(document.getElementById('itemCode'+i).value);



j=j+1;

}

}


if(j==0)
{
alert('please select atleast one record');
}


var url="materials.do?method=sapMaterialCode&sapnos="+sapnos;
		document.forms[0].action=url;
		document.forms[0].submit();
		loading();

}

 function loading() {

        // add the overlay with loading image to the page
        var over = '<div id="overlay">' +
            '<img id="loading" src="images/loadinggif.gif">' +
            '</div>';
        $(over).appendTo('body');

        // click on the overlay to remove it
        //$('#overlay').click(function() {
        //    $(this).remove();
        //});

        // hit escape to close the overlay
     /*    $(document).keyup(function(e) {
            if (e.which === 27) {
                $('#overlay').remove();
            }
        }); */
    };

function checkAll_main()
	{
		for(i=0; i < document.forms[0].selectedRequestNo.length; i++){
			if(document.forms[0].checkProp.checked==true)
				document.forms[0].selectedRequestNo[i].checked = true ;
			else
				document.forms[0].selectedRequestNo[i].checked = false ;
		}
	}
function checkAll_main1()
	{
		for(i=0; i < document.forms[0].itemExist.length; i++){
			if(document.forms[0].checkProp1.checked==true)
				document.forms[0].itemExist[i].checked = true ;
			else
				document.forms[0].itemExist[i].checked = false ;
		}
	}	

function viewMaterial(reqNo,type)
{

var url="materials.do?method=ViewMaterialrecord&reqId="+reqNo+"&type="+type;
			document.forms[0].action=url;
			document.forms[0].submit();	
}

function genarateNO()
{
var reqNo=document.forms[0].autoNo.value;
var actualSapSize=reqNo.length;
var actualSap=reqNo.substring(0,(reqNo.length-3));

var reqNo = reqNo.slice(-3);
reqNo=parseInt(reqNo);
var rows=document.getElementsByName("selectedRequestNo");

for(var i=0;i<rows.length;i++)
{
if(document.getElementById('code'+i).checked==true)
{

document.getElementById("itemCode"+i).value=(actualSap+reqNo)

/* document.forms[0].itemCode+i.value=(actualSap+reqNo); */
reqNo=reqNo+1;
}
}


}

-->


function statusMessage(message){
	$('#overlay').remove();

	}
</script>
  <style>
  #overlay {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: #000;
    opacity: 0.6;
    filter: alpha(opacity=80);
    
    
}
#loading {
    width: 250px;
    height: 257px;
    position: absolute;
    top: 20%;
    left: 50%;
    margin: -18px 0 0 -15px;
    
    
}

  </style>
</head>
<body onload="test()">
<html:form action="/materials.do" enctype="multipart/form-data">

<div align="center">
		<logic:notEmpty name="materialsForm" property="message">
		<font color="green" size="3">
			<script language="javascript">
					statusMessage('<bean:write name="materialsForm" property="message" />');
					</script>
		</font>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="materialsForm" property="message2">
		<font color="red" size="3">
			<b><bean:write name="materialsForm" property="message2" /></b>
		</font>
	</logic:present>
           
</div>

<table class="bordered" width="90%">
<tr>
	<th colspan="6"><big>My Approvals</big></th>
</tr> 
<tr>

<td><b>Material Type : <font color="red">*</font></b>
</td><td>	<html:select property="materialCodeLists" name="materialsForm" styleClass="text_field" >
					<html:option value="">--Select--</html:option>
					<html:options name="materialsForm" property="materTypeIDList" labelProperty="materialTypeIdValueList"/>
			</html:select>
	</td>
<td><b>Filter by</b> <font color="red">*</font></td>
						<td>
						<html:select property="selectedFilter" styleClass="content" styleId="filterId" >
							<html:option value="10">10</html:option>
							<html:option value="20">20</html:option>
							<html:option value="30">30</html:option>
							<html:option value="40">40</html:option>
							<html:option value="50">50</html:option>
							<html:option value="60">60</html:option>
							</html:select>
						</td>
</tr>		
<tr>
<td><b>From Date</b> <font color="red">*</font></td>
<td align="left" width="34%">
								<html:text property="fromDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field"
										readonly="true" />
							</td>
							<td><b>To Date</b> <font color="red">*</font></td>
							<td align="left" width="34%">
								<html:text property="toDate" styleId="endDate" onfocus="popupCalender('endDate')" styleClass="text_field" 
										readonly="true"/>
							</td>
</tr>			
<tr>
<td>
<b>
Location <font color="red">*</font>
</b>
</td>

<td>
	 			<html:select property="locationId" styleClass="text_field">
	 				<html:option value="">--Select--</html:option>
	  				<html:options property="locationIdList" labelProperty="locationLabelList" ></html:options>   
	 			</html:select>
			</td>
			
			<td>
			<b>Material Group  </b>
			
			
			</td>
			<td>
			<html:select property="materialGrup" name="materialsForm">
							<html:option value="">--Select--</html:option>
							<html:options name="materialsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
						</html:select>
				
	 			
			<a href="#"><img src="images/search.png" align="absmiddle" title="Search..." onclick="showSelectedFilter()"/></a>
			<a href="#"><img src="images/clearsearch.jpg"  onclick="onClear()" align="absmiddle" title="Clear Search."/></a>
			</td>
			
			
			</tr>

</table>
<br/>
<%-- <logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="materialsForm"/>-
	
	<bean:write property="endRecord"  name="materialsForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>

	</table>
	</logic:notEmpty>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/> --%>
<br/>
<logic:notEmpty name="materialList">
<%-- <sub>User Name </sub>
<html:text property="username"  />
<sub>Password </sub>
<html:password property="password"   /> --%>
<html:button property="method" value="Create In SAP" styleClass="rounded" onclick="createInSAP();"/>

&nbsp;<html:button property="method" value="Completed" styleClass="rounded" onclick="completed();"/>
<%-- &nbsp;<html:text property="autoNo"   />
&nbsp;<html:button property="method" value="Item Code Auto Genarate" styleClass="rounded" onclick="genarateNO();"/> --%>
</logic:notEmpty>
<br/>
<logic:notEmpty name="Material List">
          		   
					<table class=" bordered">
						<tr>
							<th><input class="checkbox" type="checkbox" name="checkProp" onclick="checkAll_main()"/>Select</th>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Plant</th>
							
							<th>Material&nbsp;Group</th>
							<th>Material&nbsp;Name</th>
							<th>U.O.M</th>
						    <th>Requester Name</th>
						  <th>Status</th>
						  		<th>SAP Approved Date</th>
							<th>SAP Message</th>
							<th>Item Code</th>
							<th>View</th>
							<th>Edit</th>
						</tr>
						
						<%int i=0; %>
						<logic:iterate name="materialList" id="abc">
						<tr class="tableOddTR">
							<td><html:checkbox property="selectedRequestNo" name="materialsForm" value="${abc.requestNumber}" styleId='<%="code"+i%>' /></td>
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestDate"/></td>
							<td><bean:write name="abc" property="locationCode" /></td>
							<td><bean:write name="abc" property="materialGroupName" /></td>
						  	<td> <font style="text-transform:uppercase;"><bean:write name="abc" property="materialName" /></font></td>
						  	<td><bean:write name="abc" property="uom" /></td>
						    <td><bean:write name="abc" property="employeeName"/></td>
      						<td><bean:write name="abc" property="approveStatus"/></td>
      						<td><bean:write name="abc" property="createdOn"/></td>
      						<td><bean:write name="abc" property="message"/></td>
      						<td>
      						<html:text  property="itemCode"  styleId='<%="itemCode"+i%>' name="materialsForm" value=""/>
      						 </td>
      						<td id="${abc.requestNumber}">
      						<a href="#">
      							<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${abc.requestNumber}',0)"/></a>
      						</td>
      						<td id="${abc.requestNumber}">
      						<a href="#">
      							<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${abc.requestNumber}',1)"/></a>
      						</td>
						</tr>
						<%
						i++;
						%>
						</logic:iterate>
					</logic:notEmpty>	
					<br/>
					
<table>
<tr>

						<td colspan="14" align="center">
						<logic:empty name="matlist">
						<logic:notEmpty name="no Material Master records">
<center>	<table class=" bordered">
			<tr>
							<th><input class="checkbox" type="checkbox" name="checkProp" onclick="checkAll_main()"/>Select</th>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Plant</th>
							
							<th>Material&nbsp;Group</th>
							<th>Material&nbsp;Name</th>
							<th>U.O.M</th>
						    <th>Requester Name</th>
						  <th>Status</th>
						  		<th>SAP Approved Date</th>
							<th>SAP Message</th>
							<th>Item Code</th>
							<th>View</th>
						</tr>
		<tr>
<td colspan="13">
<div align="center">
<font color="red" size="3">Searched details could not be found.</font>
</div>
</td>
</tr>
</table></font></center>
</logic:notEmpty>	
</logic:empty>
</td>
</tr>

</table>				

<logic:notEmpty name="matlist">
<table class=" bordered">
<tr>

<th colspan="5">
<center>
Sap Status
</center>
</th>
</tr>
			<tr>
<th>Request No</th>
<th>Sap Code</th>
<th>Sap Created Date</th>
<th>Sap Message</th>
            </tr>
		
		<logic:iterate id="mat" name="matlist">
		<tr>
		<td>${mat.requestNo}</td>
		<td>${mat.sapCodeNo}</td>
		<td>${mat.sapCreationDate}</td>		
		<td>${mat.message2}</td>
		</tr>
</logic:iterate>
</table>

</logic:notEmpty>	
</html:form>
</body>
</html>