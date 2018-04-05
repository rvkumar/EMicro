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
<style type="text/css">


.floating-box {
    display: inline-block;
    width: 120px;
    height: 30px;
    margin: 10px;
    border: 3px solid #9F81F7;  
    
}
div.floating-box:hover {
    background-color: #9F81F7;

}

.after-box {
    border: 3px solid white; 
    color: white;
}



table.get {
    border-collapse: collapse;
    width: 100%;
}

th.get, td.get {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}
</style>

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


 function getPending1(id,type)
	{
	var req_id=id;
	var type1= type;
	
 	var url1="main.do?method=getSelectedRequestToApprove&reqId="+req_id;
	/* alert(1); */
	document.forms[0].action=url1;
	document.forms[0].submit();
/*  alert(type); */
	/* closealert(); 
	 alert(type);
	var req_id=id;
	var type1= type;
	 */
/* 	if(type1=="")
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
	} 
 	var url1="main.do?method=getSelectedRequestToApprove&reqId="+req_id;
	/* alert(1); */
	/*document.forms[0].action=url1;
	document.forms[0].submit(); */
		/* alert(2); 
		alert(1);
		var url="main.do?method=displayAnnouncement";
	document.forms[0].action=url;
	document.forms[0].submit(); */
	} 

function goBack(){
		var url="main.do?method=displayAnnouncement";
		document.forms[0].action=url;
		document.forms[0].submit();

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

</head>

<body>

<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-left:15px">
  <tr>
    <td width="100%" align="center" valign="top"><div class="middel-blocks-iframe">
      <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left">
          
                 		
      <logic:notEmpty name="HeadLinesData">

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
           
           <tr>
               <td class="heading"><img src="images/lines.png" align="absmiddle" /><font color="#9F81F7"> Headlines</font></td>
           </tr>
           <tr>
              <td class="underline"></td>
           </tr>
          </table>
          </td>
          </tr>
        
        <tr>
          <td align="left" valign="top" width="80%">
   <div class="box effect2">
          <ul>
          <logic:iterate name="HeadLinesData" id="headLines">
           
          <li><a href="main.do?method=getContentDescription&ContentId=<bean:write name="headLines" property="id" />" class="content">
         <i><font color="gray" size="2px">&nbsp; <bean:write name="headLines" property="linkDescription"   filter="false"/></font></i>
      
          </a></li>
          
         </logic:iterate>
          </ul>
                    <div style="float: right;">
					<bean:write name="mainForm" property="linkDescription"  />
					<a href="main.do?method=getMoreDetails&Type=HeadLines"  style="text-align: right" ><img src="images/readmore.jpg"/></a>
					</div>
		   </div>
          </td>
        </tr>


        </logic:notEmpty>
        
        
         <tr>
          <td height="5" >&nbsp;</td>
            </tr>

        <tr>
          <td>
          
          <logic:notEmpty name="OrganisationData">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="heading"><img src="images/speaker.png" onclick="goback()" align="absmiddle" /><font color="#9F81F7"> ORGANIZATION ANNOUNCEMENTS</font></td>
             </tr>
            <tr>
              <td class="underline"></td>
            </tr>
          </table></td>
          
        </tr>
        

        <tr>
          <td align="left" valign="top">
<div class="box effect2"> 
          <ul> 
          <logic:iterate name="OrganisationData" id="OrganisationLines">
          <li> <a href="main.do?method=getContentDescription&ContentId=<bean:write name="OrganisationLines" property="id" />" class="content">
       <i><font color="gray" size="2px">&nbsp;    <bean:write name="OrganisationLines" property="linkDescription"  filter="false"/></font></i></a></li>
          
          </logic:iterate>
          </ul>
      
			<div style="float: right;">
				<bean:write name="mainForm" property="linkDescription" filter="false" />
				<a href="main.do?method=getMoreDetails&Type=ORGANIZATION ANNOUNCEMENTS"><img src="images/readmore.jpg"/></a>
			</div>
			</div>  
             </td>
            </tr>
        </logic:notEmpty>
        
    </table>
    </div></td>
    
  
    
     <td width="21%" align="left" valign="top">
     	<div class="right-img_blocks-iframe">
    		<div id="slidshow_wrapper">
        		<div id="gallery">
					<a href="#" class="show">
						<img src="images/mgmt_imgCMD2.jpg" style=" border-top-right-radius:1em;border-top-left-radius:1em;border-bottom-right-radius:1em;border-bottom-left-radius:1em;"  alt="Flowing Rock" width="166" height="160" title="" rel="Message from Management"/>
					</a>	
					<a href="#">
						<img src="images/mgmt_img-DIR2.jpg" style=" border-top-right-radius:1em;border-top-left-radius:1em;border-bottom-right-radius:1em;border-bottom-left-radius:1em;" alt="Grass Blades" width="166" height="160" title="" rel="Message from Management"/>
					</a>  
				</div>
	        
	       
	        </div>
	        <div>&nbsp;</div>
	        
    <div class="right-img_blocks-iframe">
	<logic:empty name="getlist">
	<iframe src="main.do?method=displayThoughts"  width="210" height="180" scrolling="no" frameborder="0" style="overflow-y:"  ></iframe>
</logic:empty>  <logic:notEmpty name="getlist">

     <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr><td colspan="6" class="get"><font color="#9F81F7">&nbsp;<b>My Pending Approvals</b></font></td></tr> 
	
          </table>
	 

	<table  class="get" width="100%" >
	
	
	<%-- <tr><th>${a1.req_type}</th></tr> --%>
<logic:iterate id="a1" name="getlist">
<tr class="get">

<td class="get">${a1.req_type}</td>
<td class="get"><a href="approvals.do?method=pendingRecords&req_type=<bean:write name="a1" property="req_type" />">${a1.id}</a></td></tr>
	<%-- <tr><th style="background-color: #9F81F7;" width="475px;">${a1.req_type}</th>
	<td width="475px;">${a1.id}</td>
	</tr> --%>
	
	<%-- <tr>
	<td><a ><big>${a1.req_id}</big></a></td>
	<td>${a1.req_type}</td>
	<td>${a1.req_date}</td>
	<td>${a1.request_name}</td>
	<td>${a1.last_approver}</td>
	getPending1('${a1.req_id}','${a1.req_type}')
	<td><html:button property="method" value="Back" onclick="goBack()" styleClass="rounded" style="width: 100px"></html:button></td>
	
	</tr> --%>
      </logic:iterate>  
    </table>
    </logic:notEmpty>
    </div>
 

 
 
 

	
      
      </td>
      
      

  </tr>
   
</table>

<%-- <logic:notEmpty name="getlist">
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
    </logic:notEmpty> --%>

<br/><br/><br/><br/>

    	<tr>
    	<logic:notEmpty name="empBirthDayList">
<p>

<div class="shadow" style="width: 80%;">
<marquee behavior="scroll" scrolldelay="0" scrollamount="3" direction="left" onmouseover="this.stop();" onmouseout="this.start();">
<font size="+2">Birthday Wishes : </font><font face="Comic Sans MS" size="+2"><bean:write name="mainForm" property="empName"/></font>
</marquee >
</div>

    
</logic:notEmpty>
    	</tr>
    	
</body>
</html>
