<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Headlines & Announcements Display</title>
<link href="style/content.css" rel="stylesheet" type="text/css" />

<link href="css/microlabs.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-1.3.1.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {		
	
	//Execute the slideShow
	slideShow();

});

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


function testtimeout(){
 setTimeout("changeimg()",60000); 

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
}


</style>
<style>
.table{border: solid #ccc 1px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 6px;
    border-radius: 5px;
    -webkit-box-shadow: 0 1px 1px #ccc; 
    -moz-box-shadow: 0 1px 1px #ccc; 
    box-shadow: 0 1px 1px #ccc;*border-collapse: collapse; /* IE7 and lower */
    border-spacing: 0;
    width: 95%; 


}

.th
{
background-color: #dce9f9;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:         linear-gradient(top, #ebf3fc, #dce9f9);
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        

    text-shadow: 0 1px 0 rgba(255,255,255,.5); border-left: 1px solid #ccc;
    border-top: 1px solid #ccc;
    padding: 6px;
    text-align: left;    -moz-border-radius: 6px 6px 0 0;
    -webkit-border-radius: 6px 6px 0 0;
    border-radius: 6px 6px 0 0;

}
</style>
</head>

<body>

        <div class="clear"></div>         
           <logic:notEmpty name="onlyThoughtmsg">
         <table style="height: 190px;width: 200px;">
         <tr>
        <td >
        
		    <table class="table">
		    <tr>
		    <td class="th"><center><b>Daily Quote</b></center></td> 
		    </tr>
		    <tr>
		    <td align="center" height="137">
		    <font style="font-family: Comic Sans MS;" >
		 &nbsp;  <bean:write name="mainForm" property="thoughtMsg"/>
		
		     </font>
		    </td>
		    </tr>
		    </table>
		</td></tr></table>
           
           
           </logic:notEmpty>
		    
		    <logic:notEmpty name="birthdayImg" >
		    	<div id="t1" style="visibility: visible;height: 20px;">
		    <center><img src="images/Birthday.png" height="147" width="166" /></center>
		    </div>
		    
		    
	<div id="t2" style="visibility: hidden; height: 0;">
	  <table style="height: 190px;width: 200px;">
         <tr>
        <td >
        
		    <table class="table">
		    <tr>
		    <th  class="th"><center><b>Thought For The Day</b></center></th> 
		    </tr>
		    <tr>
		    <td align="center" height="137" style="background-color:#F8ECE0;">
		    <font style="font-family: Comic Sans MS;" >
		    &nbsp;<bean:write name="mainForm" property="thoughtMsg"/>
		     </font>
		    </td>
		    </tr>
		    </table>
		    </td></tr></table>
		    </div>
		  
		  <script type="text/javascript">
		  setTimeout("changeBirhDayImg()",6000);
		  </script>
		    </logic:notEmpty>
	       		<!--<img src="/EMicro Files/images/${mainForm.gifFile }" alt="" title="" width="203" height="147"/>
	       		<a href="/test/${mainForm.gifFile }">test</a>
	       	-->
	       
	



</body>
</html>