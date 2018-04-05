var approvelJS= new function() {
    this.displayRequest= function(elem){
		var reqId = elem.id;
		var reqType = '<bean:write name="approvalsForm" property="requestType"/>';
		var url="approvals.do?method=getSelectedRequestToApprove&reqId="+reqId+"&reqType="+reqType;
		document.forms[0].action=url;
		document.forms[0].submit();
	};
	
	this.uploadRequest=function(){
		this.alert("maha");
	};
	this.changeStatus=function(){
		var reqId = '<bean:write name="approvalsForm" property="requestNo"/>';
		var reqType = '<bean:write name="approvalsForm" property="requestType"/>';
		var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType;
		document.forms[0].action=url;
		document.forms[0].submit();	
	};
};
