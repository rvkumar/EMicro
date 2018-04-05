
function displayRequest(elem){
	var reqId = elem.id;
	var reqType = document.getElementById("reqType").value;
	var searchValue="";
	var tab = document.getElementById("heading").innerHTML;
   	if(tab.indexOf("Search") != -1){
   		searchValue = document.getElementById("sValue").value;
   	}
	var url="approvals.do?method=getSelectedRequestToApprove&reqId="+reqId+"&reqType="+reqType+"&searchTxt="+searchValue;
	document.forms[0].action=url;
	document.forms[0].submit();

}

function uploadRequest(url){
	var url=url;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function createCode(elem){
	var elemValue = elem.value;
	if(document.forms[0].sapCodeNo.value=="")
	{
		alert("Please Select Sap CodeNo");
	    document.forms[0].sapCodeNo.focus();
	    return false;
	}
	if(document.forms[0].sapCodeExists.value=="")
	{
	    alert("Please Enter SAP Code Exists");
	    document.forms[0].sapCodeExists.focus();
	    return false;
	}
    if(document.forms[0].sapCreationDate.value=="")
    {
      alert("Please Enter SAP Creation Date");
      document.forms[0].sapCreationDate.focus();
      return false;
    }
    if(document.forms[0].sapCreatedBy.value=="")
    {
      alert("Please Enter SAP Created By");
      document.forms[0].sapCreatedBy.focus();
      return false;
    }
	var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var url="approvals.do?method=saveCreatedCode&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function changeStatus(elem){
	var elemValue = elem.value;
	var reqId = document.getElementById("reqId").value;;
	var reqType = document.getElementById("reqType").value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function closeOpenDiv(elem){
	var sCount = document.getElementById("scnt").value;
   	var eCount = document.getElementById("ecnt").value;
   	var srcTxt="";
   	var url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=";
	if(elem == "Pending"){
	   	var url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=";
	}
	else if(elem == "Approved"){
		var url="approvals.do?method=displayApproved&sCount="+sCount+"&eCount="+eCount+"&fnpl=";
	}
	else{
		var url="approvals.do?method=displayRejected&sCount="+sCount+"&eCount="+eCount+"&fnpl=";
	}
	var tab = document.getElementById("heading").innerHTML;
   	if(tab.indexOf("Search") != -1){
   		srcTxt = document.getElementById("sValue").value;
   		var url="approvals.do?method=searchInApprovals&filter="+elem+"&sCount=0&eCount=0";
   	}
   	url = url+"&searchTxt="+srcTxt;
   	document.forms[0].action=url;
	document.forms[0].submit();
}

function showSelectedFilter(){
	var filter = document.getElementById("filterId").value;
	if(filter == null || filter == ""){
		filter = "Pending";
	}
	else{
		var filter = document.getElementById("filterId").value;
	}
	var url="approvals.do?method=displayPending&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();
	if(filter == "Approved"){
	var url="approvals.do?method=displayApproved&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	else if(filter == "Rejected")
	{
		var url="approvals.do?method=displayRejected&sCount=0&eCount=0&searchTxt=";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
}

function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}



function sentNavigation(naviType){
	var filter = document.getElementById("filterId").value;
	var sCount = document.getElementById("scnt").value;
   	var eCount = document.getElementById("ecnt").value;
   	var url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
	if(naviType=="onload"){
		document.getElementById("successful").style.display="";
		setTimeout(function(){document.getElementById("successful").style.display="none";},5000);
	}
	else if(naviType == "next"){
   		if(filter == "Pending"){
			url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
		}
		else if(filter == "Approved"){
			url="approvals.do?method=displayApproved&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
		}
		else if(filter == "Rejected")
		{
			url="approvals.do?method=displayRejected&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
		}
		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		url=url+"&searchTxt="+srcTxt;
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "prev"){
   		if(filter == "Pending"){
			url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
		}
		else if(filter == "Approved"){
			url="approvals.do?method=displayApproved&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
		}
		else if(filter == "Rejected")
		{
			url="approvals.do?method=displayRejected&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
		}
		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		url=url+"&searchTxt="+srcTxt;
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "atLast"){
   		if(filter == "Pending"){
			url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
		}
		else if(filter == "Approved"){
			url="approvals.do?method=displayApproved&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
		}
		else if(filter == "Rejected")
		{
			url="approvals.do?method=displayRejected&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
		}
		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		url=url+"&searchTxt="+srcTxt;
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "veryFirst"){
   		if(filter == "Pending"){
			url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
		}
		else if(filter == "Approved"){
			url="approvals.do?method=displayApproved&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
		}
		else if(filter == "Rejected")
		{
			url="approvals.do?method=displayRejected&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
		}
		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		url=url+"&searchTxt="+srcTxt;
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}	
}

function searchInApprovals(type){
	var filter = document.getElementById("filterId").value;
	var requestId = document.getElementById("requestSelectId").value;
	if(type == "clear"){
		var url="approvals.do?method=display"+filter+"&sCount=0&eCount=0&searchTxt=";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else{
		var sCount = document.getElementById("scnt").value;
   		var eCount = document.getElementById("ecnt").value;
		var srcTxt = document.getElementById("searchText").value;
		document.getElementById("sValue").value = srcTxt;
		var url="approvals.do?method=searchInApprovals&filter="+filter+"&searchTxt="+srcTxt+"&sCount=0&eCount=0";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
}
