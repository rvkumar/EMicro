function sentNavigation(naviType,from){
	if(from != "authentication"){
		var filter = document.getElementById("filterId").value;
	}
	var sCount = document.getElementById("scnt").value;
   	var eCount = document.getElementById("ecnt").value;
   	var url="approvals.do?method=displayPending&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
	if(naviType=="onload"){
		document.getElementById("successful").style.display="";
		setTimeout(function(){document.getElementById("successful").style.display="none";},5000);
	}
	else if(naviType == "next"){
		if(from == "authentication"){
			url="authentication.do?method=displaynewForm&sId=Authentication&id=Admin&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
		}
		else{
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
		}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "prev"){
		if(from == "authentication"){
			url="authentication.do?method=displaynewForm&sId=Authentication&id=Admin&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
		}
		else{
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
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "atLast"){
		if(from == "authentication"){
			url="authentication.do?method=displaynewForm&sId=Authentication&id=Admin&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
		}
		else{
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
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "veryFirst"){
		if(from == "authentication"){
			url="authentication.do?method=displaynewForm&sId=Authentication&id=Admin&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
		}
		else{
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
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}	
}