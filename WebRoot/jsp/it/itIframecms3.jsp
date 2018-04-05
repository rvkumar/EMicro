
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html lang="en"> <!--<![endif]-->
<jsp:directive.page import="com.microlabs.newsandmedia.dao.NewsandMediaDao,com.microlabs.hr.form.HRForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<link href="jsp/newsAndMedia/PlanInfo/TableCSS.css" rel="stylesheet" type="text/css" />
<font face="Cambria">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../style/gallery.css" rel="stylesheet" type="text/css" />

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
}

		.fancybox-custom .fancybox-skin {
			box-shadow: 0 0 50px #222;
		}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

	<!-- Add jQuery library -->
	<script type="text/javascript" src="jsp/hr/lib/jquery-1.8.2.min.js"></script>

	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="jsp/hr/lib/jquery.mousewheel-3.0.6.pack.js"></script>

	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="jsp/hr/source/jquery.fancybox.js?v=2.1.3"></script>
	<link rel="stylesheet" type="text/css" href="jsp/hr/source/jquery.fancybox.css?v=2.1.2" media="screen" />

	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="jsp/hr/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="jsp/hr/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="jsp/hr/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="jsp/hr/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="jsp/hr/source/helpers/jquery.fancybox-media.js?v=1.0.5"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			/*
			Simple image gallery. Uses default settings
			*/

			$('.fancybox').fancybox();

			/*
			 *  Different effects
			 */

			// Change title type, overlay closing speed
			$(".fancybox-effects-a").fancybox({
				helpers: {
					title : {
						type : 'outside'
					},
					overlay : {
						speedOut : 0
					}
				}
			});

			// Disable opening and closing animations, change title type
			$(".fancybox-effects-b").fancybox({
				openEffect  : 'none',
				closeEffect	: 'none',

				helpers : {
					title : {
						type : 'over'
					}
				}
			});

			// Set custom style, close if clicked, change title type and overlay color
			$(".fancybox-effects-c").fancybox({
				wrapCSS    : 'fancybox-custom',
				closeClick : true,

				openEffect : 'none',

				helpers : {
					title : {
						type : 'inside'
					},
					overlay : {
						css : {
							'background' : 'rgba(238,238,238,0.85)'
						}
					}
				}
			});

			// Remove padding, set opening and closing animations, close if clicked and disable overlay
			$(".fancybox-effects-d").fancybox({
				padding: 0,

				openEffect : 'elastic',
				openSpeed  : 150,

				closeEffect : 'elastic',
				closeSpeed  : 150,

				closeClick : true,

				helpers : {
					overlay : null
				}
			});

			/*
			 *  Button helper. Disable animations, hide close button, change title type and content
			 */

			$('.fancybox-buttons').fancybox({
				openEffect  : 'none',
				closeEffect : 'none',

				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,

				helpers : {
					title : {
						type : 'inside'
					},
					buttons	: {}
				},

				afterLoad : function() {
					this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
				}
			});


			/*
			 *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
			 */

			$('.fancybox-thumbs').fancybox({
				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,
				arrows    : false,
				nextClick : true,

				helpers : {
					thumbs : {
						width  : 50,
						height : 50
					}
				}
			});

			/*
			 *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
			*/
			$('.fancybox-media')
				.attr('rel', 'media-gallery')
				.fancybox({
					openEffect : 'none',
					closeEffect : 'none',
					prevEffect : 'none',
					nextEffect : 'none',

					arrows : false,
					helpers : {
						media : {},
						buttons : {}
					}
				});

			/*
			 *  Open manually
			 */

			$("#fancybox-manual-a").click(function() {
				$.fancybox.open('images/gallery_img/1_b.jpg');
			});

			$("#fancybox-manual-b").click(function() {
				$.fancybox.open({
					href : 'iframe.html',
					type : 'iframe',
					padding : 5
				});
			});

			$("#fancybox-manual-c").click(function() {
				$.fancybox.open([
					{
						href : 'images/gallery_img/1_b.jpg',
						title : 'My title'
					}, {
						href : 'images/gallery_img/2_b.jpg',
						title : '2nd title'
					}, {
						href : 'images/gallery_img/3_b.jpg'
					}
				], {
					helpers : {
						thumbs : {
							width: 75,
							height: 50
						}
					}
				});
			});


		});
	</script>
	
	
	<!-- this cssfile can be found in the jScrollPane package -->
	<link rel="stylesheet" type="text/css" href="../jquery.jscrollpane.css" />	
			<script type="text/javascript" src="../jquery.jscrollpane.min.js"></script>
	<!-- latest jQuery direct from google's CDN -->
    
	<!-- the jScrollPane script -->

	
	<!--instantiate after some browser sniffing to rule out webkit browsers-->
	<script type="text/javascript">
	
	  $(document).ready(function () {
	      if (!$.browser.webkit) {
	          $('.container').jScrollPane();
	      }
	  });
	
	</script>
	
	
</head>



<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>


<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>sapMasterIframecms</title>



<script type="text/javascript">


function dispContactPer(){
	var conPer=document.getElementById('conPer');
	conPer.value='';
}


function onSubmit(){
	var url="fckEditor.do?method=submit";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function onUpdate(){
	var url="fckEditor.do?method=updateContent";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function subMenuClicked1(id,status){
	
	var disp=document.getElementById(id);
	
	disp.style.display=status;
}


function subMenuClicked(id){
	
	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		document.forms[0].divStatus.value='none';
	}
	else
	{
		disp.style.display=''; 
		document.forms[0].divStatus.value='';
	}
}
//-->
</script>

<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>



</head>

<body  onload="subMenuClicked('<bean:write name='itForm' property='linkName'/>')">

   	   	   	   	   	   	<table class="bordered">
<tr><th><strong><big>Web development</big></strong></th></tr>

	<tr>
		<td>
	<big>		
ITS provides web design, development and support services for web-based applications.  ITS works with departments to develop web-based solutions that can improve departmental workflows, communication, organization and help streamline work processes across organization.
		</big></td></tr>
   	
<tr><th><strong><big>Technology</big></strong></th></tr>

	<tr>
		<td>
		<big>a)	HTML & CSS<br/>
			b)	MySQL, MS-SQL<br/>
			c)	JAVA<br/>
			d)	Dot Net<br/>
			e)	jQuery, JavaScript / VBScript / Ajax<br/>
			Service & support : Take advantage of many of these services as mentioned below :<br/>
			
			a)	Computer account<br/>
			b)	Printing<br/>
			c)	Smart Device, Android & IOS support<br/>
			d)	Scanning<br/>
			e)	Online and Offline training support<br/>
			f)	Technology purchasing<br/>
			g)	Video Conferencing<br/>
			h)	Website creation<br/>
			i)	Remote connections<br/>
		
		</td></tr>
   	</table>
   	<br/>
   	 	   	   	   	   	<table class="bordered">
<tr><th><strong><big>Our Mission</big></strong></th></tr>

	<tr>
		<td>
	<big>		
"The mission of IT department is to empower Micro Labs in the use of technology through the delivery of an efficient, adaptable & well managed service. Provide strategic IT vision, leadership, and enterprise solutions to the staff so they can meet their goals, deliver results, and enhance the company's position"		</td>
</big>
</tr>
<br/>
<tr><td><ul><big><b>Supported by the following values:</b></big></ul>
		<big>
		A)Approachable<br/>
		B)Supportive<br/>
		C)Professional<br/>
		D)Pro Active<br/>
		E)Innovative<br/>
		F)Knowledgeable<br/>
		G)Informative<br/>
		</big>
		</td></tr>
   	</table>
   	<br/>
   	   	 	   	   	   	   	<table class="bordered">
<tr><th><strong><big>OUR STRATEGIC GOALS</big></strong></th></tr>


<tr>
<td>
			<b>
			<big>
1)	Create and maintain an information technology infrastructure that is reliable, accessible, and secure<br/>
2)	Maintain and improve user support<br/>
3)  Provide efficient and effective access to systems to various users through the utilization and deployment of web-based interfaces and productive tools<br/>
</big></ui></b>
		</td></tr>
	<tr><th>Team</th></tr>
	<tr>
	<td><big>
			a)IT Administration<br/>
                     Name:<br/>
                     Contact:<br/>		       
			b)	Enterprises applications & systems<br/>
			c)	Learning technologies<br/>
			d)	Telecommunications & Networking<br/>
			e)	User Services<br/>
			f)	Web Development<br/>
			</big>
			<br/>
			One point of contact : 	
	</td></tr>
	<tr><th><i><big>INFORMATION TECHNOLOGY ORGANIZATIONAL CHART</big> </i></th></tr>
	<tr><td>
	<big>
	1. Internet Usage Policy<br/>
	2. Email Policy<br/>
	3. Desktop Usage Policy<br/>
	4. Windows Login Policy<br/>
	5. Backup Policy<br/>
	6. Password Policy<br/>
	7. File access & sharing policy<br/>
	8. Equipment replacement policy<br/>
	9. Copyright and fair use policy<br/>
	Preventive Maintenance
	Performance Monitoring<br/>
	Application Compliance<br/>
Licensing Compliance<br/>
	Vulnerability scanning<br/>
	Policy enforcement<br/>
	Disaster Recovery & Data Protection Plan<br/>
	Scheduled maintenance on Desktops, Servers, UPS, Applications
	</big>
	</td></tr>
	
	<tr><th><i><big>Report an Issue</big></i> </th></tr>
	<tr><td>
	Service is available three ways. Select the way that works best for you.<br/><br/>
	<b>a)Online ticket: (Under Construction)<br/><br/>
	b)Phone: Call 222/303 to contact a Helpdesk 10 hours/day, 7 days/week<br/> <br/>
	c)Walk-in: Corporate Building, 3rd Floor, 9am - 8pm, Monday - Saturday<br/></b><br/>
	</td></tr>
   	</table>
   	<br/>
   	 <div align="right"><a href="it.do?method=displaySecondCMS"><img src="images/Pre-2.png" height="20px" width="80px" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;&nbsp;
   	 </div>
</body>
</html>