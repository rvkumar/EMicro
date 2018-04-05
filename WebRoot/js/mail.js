function onUpload(){
	var ifH = document.getElementById("attvalueTR").height;
	ifH = parseInt(ifH) + 30;
	document.getElementById("attvalueTR").height = ifH; 
	resizeAttachIframe("add");
	var url="/EMicro/mail.do?method=uploadFiles";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function onDeleteFile(attName){
	var ifH = document.getElementById("attvalueTR").height;
	ifH = parseInt(ifH) - 30;
	document.getElementById("attvalueTR").height = ifH;
	resizeAttachIframe("delete");
	document.forms[0].action="/EMicro/mail.do?method=deleteUploadedFiles&cValues="+attName;
	document.forms[0].submit();
}