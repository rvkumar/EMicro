<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="com.microlabs.admin.form.LinksForm"/>
<jsp:directive.page import="java.util.LinkedList"/> 

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: About_Company_CMS</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript" src="js/scrolltopcontrol.js"></script>
<script type="text/javascript">

window.onscroll = function (oEvent) {
 	var bt = document.getElementById("back-top").style.display;
 	var scTop = document.body.scrollTop;
 	if(scTop == 0){
 		scTop = document.documentElement.scrollTop;
 	}
 	if(scTop > 100){
	 	if(bt == "none"){
	 		document.getElementById("back-top").style.display = "";
	 	}
 	}
 	else{
 		document.getElementById("back-top").style.display = "none";
 	}
}

function showOverFlow(){
	var hdiv = document.getElementById("subMenuDiv");
	if((hdiv.scrollHeight - hdiv.scrollTop) + "px" >= hdiv.style.height){
		document.getElementById("downDiv").style.display = "";
	}
}

function MM_preloadImages() { //v3.0
    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}


function subMenuClicked(id){
	
	var disp=document.getElementById(id);
	if(disp.style.display==''){
		disp.style.display='none';
		document.getElementById(id+"ss").className = "mail";
		document.getElementById(id+"im").src = "images/left_menu/up_arrow.png";
	}
	else{
		disp.style.display=''; 
		document.getElementById(id+"ss").className = "mailhover";
		document.getElementById(id+"im").src = "images/left_menu/down_arrow.png";
	}
	
	var uu=document.getElementsByName("mailMenu");
	
	for(var i=0;i<uu.length;i++){
		
		
	if((id)!=(document.getElementById("gg"+i).value)){
		
	
	document.getElementById((document.getElementById("gg"+i).value)+"ss").className = "mail";
	document.getElementById((document.getElementById("gg"+i).value)).style.display='none';
	document.getElementById((document.getElementById("gg"+i).value)+"im").src = "images/left_menu/up_arrow.png";
	}
	}
	
}


function subMenuClicked2(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		document.forms[0].divStatus.value='none';
	}
	else{
		disp.style.display=''; 
		document.forms[0].divStatus.value='';
	}
}

function subMenuClicked1(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		//document.forms[0].divStatus.value='none';
		
		document.getElementById('Submenu').style.display='none';
		document.getElementById('Submenu1').style.display='';
	}
	else{
		disp.style.display=''; 
		//document.forms[0].divStatus.value='';
		
		document.getElementById('Submenu1').style.display='';
		document.getElementById('Submenu').style.display='none';
		}
}

function resizeIframe(obj) {
	
	 if((obj.contentWindow.document.body.scrollHeight)<378){
		  obj.style.height ='378px';
	}else{
		  obj.style.height = (100+obj.contentWindow.document.body.scrollHeight) + 'px';
	}
	 
}

function goTop() {
	document.getElementById('back-top').style.display='none';
}
	

</script>
<style type="text/css">
#back-top{
	position: fixed;
	bottom: 100px;
	cursor:pointer;
}
<%--body{--%>
<%--overflow:hidden;--%>
<%--}--%>
</style>
</head>

<body>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<jsp:include page="/jsp/template/header1.jsp"/>
		<tr>
			<td align="center" valign="top" style="padding-top:2px; background-color:#FFF;height:400px;">
				<table style="width:100%;height:100%;" border="0" cellspacing="0" cellpadding="0">
					<tr>
        				<td width="16%" align="left" valign="top">
	        			<!--------LEFT MENU STARTS -------------------->
	        			<jsp:include page="/jsp/template/newsAndMediasubMenu.jsp"/>
        				</td>
						<!-------- LEFT PART ENDS -------------------->
						<!--------CONTENT STARTS -------------------->
        				<td align="left" valign="top" id="annouPage">
	        				<iframe src="newsAndMedia.do?method=displayCMS" name="contentPage" scrolling="auto"
	        					width="100%" frameborder="0" style="overflow-y:auto;height:1000px;" onload="showOverFlow()">
	        				</iframe>
        				</td>
					<!--------CONTENT STARTS -------------------->
					</tr>
				</table>
				<br/><p/>
    		</td></tr>    		
    	<!--------MIDDEL PART ENDS -------------------->
		<!-------- FOOTER STARTS -------------------->
		<tr><td>
			<div id="footer1"><jsp:include page="/jsp/template/footer1.jsp"/></div>
		</td></tr>
		<!--------FOOTER ENDS -------------------->
	</table>
</body>
</html>