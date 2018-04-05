
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Main_Content</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/popModal.css" />
	<link rel="stylesheet" type="text/css" href="css/spopModal.min.css" />
  
<!--  <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
    <link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" /> --> 
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/scrolltopcontrol.js"></script>

<script type="text/javascript" src="js/popModal.js"></script>
<script type="text/javascript" src="js/popModal.min.js"></script>
<script type="text/javascript">
/* 
function getPending1()
	{
	 alert(type);
	closealert(); 
	 alert(type);
	var req_id=id;
	var type1= type;
	
	if(type1=="")
		{
		var url="approvals.do?method=HRqueryRequestToApprove&reqId="+req_id;
		document.forms[0].action=url;
		document.forms[0].submit();
		}
	if(type1=="Comp-Off/OT")
	{
	var url="approvals.do?method=OTSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="Comp-Off/OT")
	{
	var url="approvals.do?method=compSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="VC Booking")
	{
	var url="approvals.do?method=vcSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="VC Booking")
	{
	var url="approvals.do?method=confSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="IT Sap Request")
	{
	var url="approvals.do?method=SapITSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="IT Request")
	{
	var url="approvals.do?method=ITSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="Leave"||type1=="On Duty"||type1=="Permission")
	{
	var url="approvals.do?method=getSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="Travel")
	{
	var url="approvals.do?method=TravelRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
	} */
	function goBack(){
		var url="main.do?method=displayAnnouncement";
		document.forms[0].action=url;
		document.forms[0].submit();

}
	function getPending1(id,type)
	{
	/* var req_id=id;
	var type1= type; */
	
	var url="approvals.do?method=getSelectedRequestToApprove";
document.forms[0].action=url;

document.forms[0].submit();
	/* /* alert(type);
	closealert(); 
	 alert(type); */
	/* var req_id=id;
	var type1= type;
	
	if(type1=="")
		{
		var url="approvals.do?method=HRqueryRequestToApprove&reqId="+req_id;
		document.forms[0].action=url;
		document.forms[0].submit();
		}
	if(type1=="Comp-Off/OT")
	{
	var url="approvals.do?method=OTSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="Comp-Off/OT")
	{
	var url="approvals.do?method=compSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="VC Booking")
	{
	var url="approvals.do?method=vcSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="VC Booking")
	{
	var url="approvals.do?method=confSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="IT Sap Request")
	{
	var url="approvals.do?method=SapITSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="IT Request")
	{
	var url="approvals.do?method=ITSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
	if(type1=="Leave"||type1=="On Duty"||type1=="Permission")
	{
	 alert(type);
	var url1="approvals.do?method=getSelectedRequestToApprove&reqId="+req_id;
	document.forms[0].action=url1;
	document.forms[0].submit();
	}
	if(type1=="Travel")
	{
	var url="approvals.do?method=TravelRequestToApprove&reqId="+req_id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	if(type1=="")
	{
	var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit();
	} */
/* 	var url1="main.do?method=getSelectedRequestToApprove&reqId="+req_id;
	alert(1);
	document.forms[0].action=url1;
	document.forms[0].submit();
		alert(2); */
		/* alert(1);
		var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit(); */
	}


function showcontactusform() {
    /*  //set the width and height of the 
     //pop up window in pixels
     var width = 500;
     var height = 500;
 
     //Get the TOP coordinate by
     //getting the 50% of the screen height minus
     //the 50% of the pop up window height
     var top = parseInt((screen.availHeight/2) - (height/2));
 
     //Get the LEFT coordinate by
     //getting the 50% of the screen width minus
     //the 50% of the pop up window width
     var left = parseInt((screen.availWidth/2) - (width/2));
 
     //Open the window with the 
     //file to show on the pop up window
     //title of the pop up
     //and other parameter where we will use the
     //values of the variables above
     
     var x=window.showModalDialog("https://jsfiddle.net", "dialogHeight:100px; dialogWidth:100px;center:yes; scrollbars=yes,dialogLeft="+left+ ",dialogTop="+top+",screenX="+left+",screenY="+top);
   */ 
    var width = 1100;
     var height = 0;
   var x = parseInt((screen.availHeight/2) - (height/2));
var y = parseInt((screen.availWidth/2) - (width/2));;
window.showModalDialog("main.do?method=displayAnnouncement", "","dialogWidth:700px; dialogHeight:300px; dialogTop:"+y+"px; dialogLeft:"+x+"px; scrollbars=yes;center:yes; status:no");
   
    /*  window.open('https://jsfiddle.net/', 
           "Contact The Code Ninja", 
           "menubar=no,resizable=no,width=500,height=500,scrollbars=yes,left="  
           + left + ",top=" + top + ",screenX=" + left + ",screenY=" + top);     */
     }
/* 

function MM_preloadImages() { 
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function removeSelect(){
	document.getElementById("mnid").className="";
	document.getElementById("miid").className="";
	document.getElementById("mdid").className="";
	document.getElementById("msid").className="";
	document.getElementById("mtid").className="";
}

function selectSubMenu(elem){
	removeSelect();
	 if(elem.id == "mnid"){
	 	document.getElementById("mnid").className="selectedMenu";
	 }
	 else if(elem.id == "miid"){
	 	document.getElementById("miid").className="selectedMenu";
	 }
	 else if(elem.id == "mdid"){
	 	document.getElementById("mdid").className="selectedMenu";
	 }
	 else if(elem.id == "msid"){
	 	document.getElementById("msid").className="selectedMenu";
	 }
	 else if(elem.id == "mtid"){
	 	document.getElementById("mtid").className="selectedMenu";
	 }
}



function menuClicked(menu) {
document.getElementById("empBrithdayID").style.visibility="hidden";
	collapseSubMenu();
	removeSelect();
	if(menu.id == "mail"){
		subMenuExtn = "_submenu";
		subMenu = menu.id + subMenuExtn;
		var eSubMenu = document.getElementById(subMenu);
		if(eSubMenu.style.display == 'block') {
			eSubMenu.style.display = 'none';
		}
		else {
			eSubMenu.style.display = 'block';
		}
	}
	document.getElementById(menu.id).className="mailhover";
}

function menuClicked1(menu) {
	
	subMenuExtn = "_submenu";
	subMenu = menu.id + subMenuExtn;
	collapseSubMenu();
	removeSelect();
	var eSubMenu = document.getElementById(subMenu);
	
	if(eSubMenu.style.display == 'block') {
		eSubMenu.style.display = 'none';
		menu.className = "mail";
		//menu.getElementByTagName('IMG')[0].src = "images/left_menu/up_arrow.png";
	}
	else {
		eSubMenu.style.display = 'block';
		menu.className = "mailhover";
		//menu.getElementByTagName('IMG')[0].src = "images/left_menu/down_arrow.png";
	}
}

function collapseSubMenu(){
	var menuIds = "mail, approvals, myrequest, todolist, contacts";
	var sMenuIds = "mail_submenu";
	var menuIdsArray = menuIds.split(", ");
	//var sMenuIdsArray = sMenuIds.split(", ");
	
	for (var i = 0; i < menuIdsArray.length; i++) {
  	if (menuIdsArray[i].length > 0) {
  		document.getElementById(menuIdsArray[i]).className="mail";
    	document.getElementById(sMenuIds).style.display = 'none';
  	}
	}
}

function subMenuClicked(id){
		
		var disp=document.getElementById(id);
		
		if(disp.style.display==''){
			disp.style.display='none';
			document.getElementById("mailTe").src = "images/left_menu/up_arrow.png";
			document.getElementById("mail12").className = "mail";
		}
		else{
			disp.style.display=''; 
			document.getElementById("mailTe").src = "images/left_menu/down_arrow.png";
			document.getElementById("mail12").className = "mailhover";
		}
	}
	
  function resizeIframe(obj) {
  if((obj.contentWindow.document.body.scrollHeight)<378){
  obj.style.height ='378px';
  }else{
  obj.style.height = (50+obj.contentWindow.document.body.scrollHeight) + 'px';
  }
  
  }
  
function showOverFlow(){
	var hdiv = document.getElementById("subMenuDiv");
	document.getElementById("downDiv").style.display = "none";
	if((hdiv.scrollHeight - hdiv.scrollTop) + "px" > hdiv.style.height){
		document.getElementById("downDiv").style.display = "";
	}
}

function showDownLinks(){
	var hdiv = document.getElementById("subMenuDiv");
	var uplink = document.getElementById("upDiv").style.display;
	if(uplink == "none"){
		document.getElementById("upDiv").style.display = "";
	}
	document.getElementById("subMenuDiv").scrollTop = (100 + hdiv.scrollTop)
	if((hdiv.scrollHeight - hdiv.scrollTop) + "px" == hdiv.style.height){
		document.getElementById("downDiv").style.display = "none";
	}
}
function showUpLinks(){
	var hdiv = document.getElementById("subMenuDiv");
	var downlink = document.getElementById("downDiv").style.display;
	if(downlink == "none"){
		document.getElementById("downDiv").style.display = "";
	}
	hdiv.scrollTop = (hdiv.scrollTop-100);
	if(hdiv.scrollTop == 0){
		document.getElementById("upDiv").style.display = "none";
	}
}
	
</script>

<script type="text/javascript" src="js/jquery-1.3.1.min.js"></script>
<script type="text/javascript">

$(".toggle").on("click", function () {
    $(".marquee").toggleClass("microlabs");
});
/*
$(document).ready(function() {		
	
	//Execute the slideShow
	slideShow();

});
*/
function slideShow() {

	//Set the opacity of all images to 0
	$('#gallery a').css({opacity: 0.0});
	
	//Get the first image and display it (set it to full opacity)
	$('#gallery a:first').css({opacity: 1.0});
	
	//Set the caption background to semi-transparent
	$('#gallery .caption').css({opacity: 0.7});

	//Resize the width of the caption according to the image width
	$('#gallery .caption').css({width: $('#gallery a').find('img').css('width')});
	
	//Get the caption of the first image from REL attribute and display it
	$('#gallery .content').html($('#gallery a:first').find('img').attr('rel'))
	.animate({opacity: 0.7}, 400);
	
	//Call the gallery function to run the slideshow, 6000 = change to next image after 6 seconds
	setInterval('gallery()',6000);
	
}

function gallery() {
	
	//if no IMGs have the show class, grab the first image
	var current = ($('#gallery a.show')?  $('#gallery a.show') : $('#gallery a:first'));

	//Get next image, if it reached the end of the slideshow, rotate it back to the first image
	var next = ((current.next().length) ? ((current.next().hasClass('caption'))? $('#gallery a:first') :current.next()) : $('#gallery a:first'));	
	
	//Get next image caption
	var caption = next.find('img').attr('rel');	
	
	//Set the fade in effect for the next image, show class has higher z-index
	next.css({opacity: 0.0})
	.addClass('show')
	.animate({opacity: 1.0}, 1000);

	//Hide the current image
	current.animate({opacity: 0.0}, 1000)
	.removeClass('show');
	
	//Set the opacity to 0 and height to 1px
	$('#gallery .caption').animate({opacity: 0.0}, { queue:false, duration:0 }).animate({height: '1px'}, { queue:true, duration:300 });	
	
	//Animate the caption, opacity to 0.7 and heigth to 100px, a slide up effect
	$('#gallery .caption').animate({opacity: 0.7},30 ).animate({height: '30px'},500 );
	
	//Display the content
	$('#gallery .content').html(caption);
	
	
}
</script>

<script type="text/javascript" >

function goBack(){
		var url="main.do?method=displayAnnouncement";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function showAlertbox() {

	document.getElementById('d').click();

}



function showpendingAlertbox() {

	document.getElementById('d1').click();

}


function testtimeout(){
 setTimeout("changeimg()",2000); 

}


intImage = 2;
function changeimg() {
switch (intImage) {
 case 1:
  
   change_img1.src = "/EMicro/images/mgmt_img1.jpg"
  
case 2:
   change_img1.src = "/EMicro/images/mgmt_img.jpg"
  
 }
}

function  changeBirhDayImg() 
{

document.getElementById("t1").style.visibility="hidden";
document.getElementById("t1").style.height="0px";
document.getElementById("t2").style.visibility="visible";
document.getElementById("t2").style.height="20px";
}
function checkPwdDays(noofDays)
{

noofDays=parseInt(noofDays);
if(noofDays!=0)
{
	if(noofDays<=7) 
	{
		alert("Your password will expire with in "+noofDays+" days.\n Go to personalize to reset your password.");
	
	}
}
}
</script>
<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}

.modalDialog {
	position: fixed;
	font-family: Arial, Helvetica, sans-serif;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: rgba(0,0,0,0.6);
	z-index: 99999;
	opacity:0;
	-webkit-transition: opacity 400ms ease-in;
	-moz-transition: opacity 400ms ease-in;
	transition: opacity 400ms ease-in;
	pointer-events: none;

	
}

.modalDialog:target {
	opacity:1;
	pointer-events: auto;
	overflow: auto;
}

.modalDialog > div {
	width: 400px;
	position: relative;
	margin: 10% auto;
	padding: 5px 20px 13px 20px;
	border-radius: 10px;
	background:#00ccff;
	background: -moz-linear-gradient(#fff, #00ccff);
	background: -webkit-linear-gradient(#fff, #00ccff);
	background: -o-linear-gradient(#fff, #00ccff);
background-image: -ms-radial-gradient(top, ellipse farthest-corner, #FFFFFF 0%, #00ccff 100%);
    
	
}
.modalDialog1 {
	position: fixed;
	font-family: Arial, Helvetica, sans-serif;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: rgba(0,0,0,0.6);
	z-index: 99999;
	opacity:0;
	-webkit-transition: opacity 400ms ease-in;
	-moz-transition: opacity 400ms ease-in;
	transition: opacity 400ms ease-in;
	pointer-events: none;

	
}

/* .modalDialog1-body {
    max-height: calc(100vh - 210px);
    overflow-y: auto;
}
 */
.modalDialog1:target {
	opacity:1;
	pointer-events: auto;
	overflow: auto;
}

.modalDialog1 > div {
max-height: calc(100vh - 410px);
    overflow-y: auto;
	width: 400px;
	
	position: relative;
	margin: 10% auto;
	padding: 5px 20px 13px 20px;
	border-radius: 10px;
	background:#e6e9ed;
	background: -moz-linear-gradient(#fff, #e6e9ed);
	background: -webkit-linear-gradient(#fff, #e6e9ed);
	background: -o-linear-gradient(#fff, #e6e9ed);
background-image: -ms-radial-gradient(top, ellipse farthest-corner, #FFFFFF 0%, #e6e9ed 100%);
    
	
}

.close {
	background: #606061;
	color: #FFFFFF;
	line-height: 25px;
	position: absolute;
	right: -12px;
	text-align: center;
	top: -10px;
	width: 24px;
	text-decoration: none;
	font-weight: bold;
	-webkit-border-radius: 12px;
	-moz-border-radius: 12px;
	border-radius: 12px;
	-moz-box-shadow: 1px 1px 3px #000;
	-webkit-box-shadow: 1px 1px 3px #000;
	box-shadow: 1px 1px 3px #000;
}
.close1 {
	background: #ef0404;
	color: #FFFFFF;
	line-height: 25px;
	position: absolute;
	right: 510px;
	text-align: center;
	top: 140px;
	width: 24px;
	text-decoration: none;
	font-weight: bold;
	-webkit-border-radius: 12px;
	-moz-border-radius: 12px;
	border-radius: 12px;
	-moz-box-shadow: 1px 1px 3px #000;
	-webkit-box-shadow: 1px 1px 3px #000;
	box-shadow: 1px 1px 3px #000;
}

.close:hover { background: #00d9ff; }

.close1:hover { background: #00d9ff; }

</style>

<style>
.shadow {
  -moz-box-shadow:    0px 0px 4px 5px #ccc;
  -webkit-box-shadow: 0px 0px 4px 5px #ccc;
  box-shadow:         0px 0px 4px 5px #ccc;

	-moz-border-radius: 10px;
 	border-radius: 10px;

/* IE10 Consumer Preview */ 
background-image: -ms-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #00A3EF 100%);

/* Mozilla Firefox */ 
background-image: -moz-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #00A3EF 100%);

/* Opera */ 
background-image: -o-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #00A3EF 100%);

/* Webkit (Safari/Chrome 10) */ 
background-image: -webkit-gradient(radial, center center, 0, center center, 506, color-stop(0, #FFFFFF), color-stop(1, #00A3EF));

/* Webkit (Chrome 11+) */ 
background-image: -webkit-radial-gradient(center, ellipse farthest-corner, #FFFFFF 0%, #00A3EF 100%);

/* W3C Markup, IE10 Release Preview */ 
background-image: radial-gradient(ellipse farthest-corner at center, #FFFFFF 0%, #00A3EF 100%); 
width: 100%;
}


</style>


<!--  <style type="text/css">
body{
overflow:hidden;
}
</style>  -->

<link rel="stylesheet" href="res/colorbox.css" />
<script type="text/javascript" src="res/jquery.min.js"></script>
<script type="text/javascript" src="res/jquery.colorbox-min.js"></script>
<script type="text/javascript">  
function showBirthdayImage(){

$(document).ready(function(){
	setTimeout(function() {
		$.fn.colorbox({href:"/EMicro/images/birthdayPopup1.jpg", open:true});  
	}, 700);
});  
}
function showwonanImage(){

	$(document).ready(function(){
		setTimeout(function() {
			$.fn.colorbox({href:"/EMicro/images/Wde.jpg", open:true});  
		}, 700);
	});  
	}


function resizeFrame(f) {
//	alert(f.contentWindow.document.body.scrollHeight + "px");
	f.style.height = "1820px";

	}
	
	
function showmodalAlert()
{
	window.showModalDialog("main.do?method=alertmessage" ,null, "dialogWidth=650px;dialogHeight=520px;dialogLeft:400px;dialogTop:150px; center:yes;status:no;");
 	
   
}

function closealert()
{
	
	document.getElementById("openModal").style.visibility="collapse";
	
	
}
	
	
</script> 

<style>
/* Make it a marquee */
.marquee {
    width: 1250px;
    margin: 0 auto;
    overflow: hidden;
    white-space: nowrap;
    box-sizing: border-box;
    animation: marquee 35s linear infinite;
}

.marquee:hover {
    animation-play-state: paused
}

/* Make it move */
@keyframes marquee {
    0%   { text-indent: 187.5em }
    100% { text-indent: -105em }
}

/* Make it pretty */


/* Style the links */
.vanity {
    color: #333;
    text-align: center;
    font: .75em 'Segoe UI', Tahoma, Helvetica, Sans-Serif;
}

.vanity a, .microlabs a {
    color: #1570A6;
    transition: color .5s;
    text-decoration: none;
}

.vanity a:hover, .microlabs a:hover {
    color: #F65314;
}

/* Style toggle button */
.toggle {
	display: block;
    margin: 2em auto;
}


</style>

</head>

<body onload="resizeFrame(document.getElementById('mainFR'))" >
<logic:notEmpty name="Alert">
<a href="#openModal" id="d" ></a>
		<div id="openModal" class="modalDialog">
	<div class="dialog">
		<a href="JavaScript:closealert()" id="cl" title="Close" class="close">X</a>

	<table  class="bordered">
	
	<tr><th><big>${mainForm.headLines}</big></th></tr>
	<tr><th>${mainForm.alertheader}</th></tr>

		
			<tr>
				<td align="left" valign="top"><i><font color="gray" size="2px">
					<%
						String val=(String)request.getAttribute("ContentData");
						out.println(val);
	  				%>
		</font></i></td></tr>
				
        
    </table>
	</div>
</div> </logic:notEmpty>


<%-- <logic:notEmpty name="getlist">
<a href="#openModal" id="d1" ></a>
		<div id="openModal" class="modalDialog1">
	
		<a href="JavaScript:closealert()" id="cl" title="Close" class="close1"><big>X</big></a>
	<div class="dialog1">
		
	
<!-- <div class="scrollingDown"    style="width: 100%"> -->
	<table  class="bordered" border="1">
	<tr><th colspan="6"><center>My Pending Workflow</center></th></tr>
	<tr><th>Req No</th><th>Req Type</th><th>Req Date</th><th>Req Name</th>	<th style="width: 80px;">Last Approver</th>
		<th>View</th></tr>
<logic:iterate id="a1" name="getlist">
	<tr>
	<td><a href="JavaScript:closealert()"><big>${a1.req_id}</big></a></td>
	<td>${a1.req_type}</td>
	<td>${a1.req_date}</td>
	<td>${a1.request_name}</td>
	<td>${a1.last_approver}</td>
	<td><a><img onclick="getPending1()" src="images/view.gif" height="28" width="28"/></a></td>
	
	</tr>
      </logic:iterate>  
    </table>
    <!-- </div> -->
	</div>
</div>
</logic:notEmpty> --%>

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
		<jsp:include page="/jsp/template/header1.jsp"/>
		
		<tr><td align="center" id="mailContentTD" valign="top" style="padding-top:5px; _padding:2px 0px 0px 0px; background-color:#FFF;height:400px;">
		<table style="width:100%;height:100%;" border="0" cellspacing="0" cellpadding="0">
      		<tr>
      		
        	<td width="16%" align="left" valign="top" >
        		<!--------LEFT PART STARTS -------------------->
        		<div onclick="showUpLinks()" id="upDiv" style="cursor:pointer;text-align:center;display:none;">
            		<img src="images/up_arr.gif" width="10" height="9"/>
  				</div>
           		<div class="wrapper_left">
			
					<!--------LEFT MENU STARTS -------------------->
					
					<!-- Mal - Start -->
					
					<!--<div class="mail" title="MAIL" onclick="menuClicked(this);" id="mail">
						<a href="mail.do?method=displayMailHome&openType=home" id="mMenuList" target="contentPage"><img src="images/left_menu/mail_icon.png" width="17" height="12" border="0" align="top" style="float:left; margin:5px;_margin:5px;" />MAIL
						<img src="images/left_menu/up_arrow.png" width="18" height="16" border="0" style="float:right; margin:5px;_margin:-20px 2px 0px 0px;" />
						</a>
					</div>
					             
					<div class="submenubg"  style="display: none;" id="mail_submenu">
						<ul><a href="mail.do?method=displayComposeMail&sId=New" id="ul_content" target="contentPage"><img src="images/left_menu/lefarrow.png" border="0" /><span id="mnid">&nbsp;New</span></a></ul>
					    <ul><a href="mail.do?method=displayInboxMail&sCount=0&eCount=0" id="ul_content" target="contentPage"><img src="images/left_menu/lefarrow.png" border="0" /><span id="miid">&nbsp;Inbox</span></a></ul>
					    <ul><a href="mail.do?method=displayDraftMail&sCount=0&eCount=0" id="ul_content" target="contentPage"><img src="images/left_menu/lefarrow.png" border="0" /><span id="mdid">&nbsp;Draft</span></a></ul>
					    <ul><a href="mail.do?method=displaySentMail&sCount=0&eCount=0" id="ul_content" target="contentPage"><img src="images/left_menu/lefarrow.png" border="0" /><span id="msid">&nbsp;Sent</span></a></ul>
					    <ul><a href="mail.do?method=displayDeletedMail&sCount=0&eCount=0" id="ul_content" target="contentPage"><img src="images/left_menu/lefarrow.png" border="0" /><span id="mtid">&nbsp;Trash</span></a></ul>
					</div>
					        
					--><!-- Mail - End --> 
         
					<!-- START Approvals - added by madhavan  -->     
					<div class="mail"  title="APPROVALS" onclick="menuClicked(this);" id="approvals">
						<a href="approvals.do?method=displayApprovePage&sCount=0&eCount=0" id="aMenuLit" target="contentPage"><img src="images/left_menu/appl_icon.png" width="17" height="13" border="0" style="float:left; margin:5px;_margin:5px;" />APPROVALS
						<img src="images/left_menu/up_arrow.png" width="18" height="16" border="0" style="float:right; margin:5px;_margin:-20px 2px 0px 0px;" id="mailTe"/>
						</a>
					</div>
					
					<!-- END Approvals - added by madhavan  -->
               

					<!-- My Request Start -->
					<div class="mail"  title="MY REQUEST" onclick="menuClicked(this);" id="myrequest">
						<a href="myRequest.do?method=displayMyRequest" target="contentPage"><img src="images/left_menu/request_icon.png" width="20" height="17" border="0" style="float:left; margin:5px;_margin:5px;" />MY REQUEST 
						<img src="images/left_menu/up_arrow.png" width="18" height="16" border="0" style="float:right; margin:5px;_margin:-20px 2px 0px 0px;" />
						</a>
					</div>
				
					<!--  My Request End -->

					<!-- To DO Start -->
					<div class="mail"  title="TO DO" onclick="menuClicked(this);" id="todolist">
						<a href="todoTask.do?method=displaycalender" target="contentPage"><img src="images/left_menu/todo_icon.png" width="17" height="17" border="0" style="float:left; margin:5px;_margin_5px;" /> To do
						<img src="images/left_menu/up_arrow.png" width="18" height="16" border="0" style="float:right; margin:5px;_margin:-20px 2px 0px 0px;" />
						</a>
					</div>
					      
					<!-- To DO End -->

					<!-- Contact Start -->        
					              
					<div class="mail" title="CONTACTS" onclick="menuClicked(this);" id="contacts">
						<a href="contacts.do?method=displayContacts" target="contentPage"><img src="images/left_menu/contact_icon.png" width="17" height="18" border="0" style="float:left; margin:5px;_margin:5px;" />CONTACTS
						<img src="images/left_menu/up_arrow.png" width="18" height="16" border="0" style="float:right; margin:5px;_margin:-20px 2px 0px 0px;" />
						</a>
					</div>
					
					<!-- Contact End --> 

					<!--------LEFT MENU ENDS -------------------->            
            
					<!--------CALENDER STARTS -------------------->
						  	<% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
    		   <%if(!user.getStaffcat().equalsIgnoreCase("2")){ %>
					<div class="left-blocks">
						<iframe src="jsp/main/calender_table.jsp" width="237px" height="330px" scrolling="no"  frameborder="0" style="border:none;"></iframe>
					</div>
					
					<%} %>
					
					
					       
					<!--------CALENDER ENDS --------------------> 
				</div>
				
			
				<!--------LEFT PART ENDS -------------------->
			</td>
       		<!--------CONTENT STARTS -------------------->
       		<td align="left" valign="top" id="annouPage">

       		<iframe src="main.do?method=displayAnnouncement" name="contentPage" scrolling="no" width="100%"  id="mainFR" frameborder="0" style="overflow-y:auto;height:1830px;" onload="showOverFlow()"></iframe></td>
       		
       		
			<!--------CONTENT END -------------------->
			</tr>
    	</table>
    	</td></tr>
    		 <%if(!user.getStaffcat().equalsIgnoreCase("2")){ %>
    	<tr>
    	<td>
    	<div id="empBrithdayID" style="visibility: visible;">
    	<logic:notEmpty name="empBirthDayList" >
<!--<center><img  src="images/hb01.jpg"   style="height:20mm ;width: 40%; "/></center>
-->
<div style="position: fixed; bottom: 40px; width: 100%; height: 40px;">
<table width="100%">
<tr>
<!-- <td>
<img src="images/birthday_balloons.gif" align="absmiddle" style="height: 50px;"/>
</td> -->
<td style="width: 100%;background-color: #81BEF7;-webkit-border-radius: 5px ;border-top-right-radius:1em;border-top-left-radius:1em; border-bottom-right-radius:1em;border-bottom-left-radius:1em;" height="1px">
<p class="marquee">
<font size="+2">Birthday Wishes : </font><font face="Comic Sans MS" size="+2"><bean:write name="mainForm" property="empName"/></font>
</p>
</td>
<!-- 
<td>
<img src="images/birthday_balloons.gif" align="absmiddle" style="height: 50px;"/>
</td> -->
</tr></table> 
</div>    
</logic:notEmpty>
</div>
<a href="#openModal">Open Modal</a>



    	</td>
    	</tr>
    	<%} %>
    			
    	<!--------MIDDEL PART ENDS -------------------->

    	<!-------- FOOTER STARTS -------------------->
<%--    	<tr><td top="5px">--%>   
<%--     	<div class="footers">--%>
<%--    		<div class="footer1">--%>
<%--        		<div class="bottom">--%>
<%--        			<div class="copyright"><p>&copy;|2012|Micro Labs Limited|All rights reserved</p></div>--%>
<%--            --%>
<%--            		<div class="right-blocks"><p>...Because health is in small details</p></div>--%>
<%--          		</div>--%>
<%--        	</div>--%>
<%--    	</div>--%>
<%--		</td></tr>--%>


			
		
			<tr><td>
			<div id="footer1"><jsp:include page="/jsp/template/footer1.jsp"/></div>
			</td></tr>
			
		<!--------FOOTER ENDS -------------------->
		
	</table>
	<%
HttpSession session2=request.getSession();
 user=(UserInfo)session2.getAttribute("user");

int pwdDays=user.getPwdExpDays();
if(pwdDays<=5) 
{
%>
<script type="text/javascript">
checkPwdDays('<%=pwdDays%>');
	</script>
<%
}
%>
<logic:notEmpty name="birthdayImage">
  <script type="text/javascript">
showBirthdayImage();
 </script>
</logic:notEmpty>


 <logic:notEmpty name="getlist">

  <script type="">
showpendingAlertbox();
 </script>

 </logic:notEmpty>
 <logic:notEmpty name="Alert">
  <script type="">
showAlertbox();

 </script>

 </logic:notEmpty> 


</body>
</html>
