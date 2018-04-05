<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html lang="en"> <!--<![endif]-->
<jsp:directive.page import="com.microlabs.newsandmedia.dao.NewsandMediaDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>


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
	text-decoration: none;
}

		.fancybox-custom .fancybox-skin {
			box-shadow: 0 0 50px #222;
		}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

	<!-- Add jQuery library -->
	<script type="text/javascript" src="../lib/jquery-1.8.2.min.js"></script>

	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="../lib/jquery.mousewheel-3.0.6.pack.js"></script>

	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="../source/jquery.fancybox.js?v=2.1.3"></script>
	<link rel="stylesheet" type="text/css" href="../source/jquery.fancybox.css?v=2.1.2" media="screen" />

	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="../source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="../source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="../source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="../source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="../source/helpers/jquery.fancybox-media.js?v=1.0.5"></script>

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

<body>


  <%
        
       
        NewsandMediaDao ad=new NewsandMediaDao();
       
       
       
       
      
       
        String mainLinkName="";
        String subLinkName="";
        String subLinkTitle="";
        String contentYear="";
      
       
       
      
       
       
       ///////////////////////////////////////////////////////////////////////
       
       String fileDescription="";
        String fileName="";
      
		fileName="EMicro/jsp/EMicro Files/News And Media/Archieves/News and Media/UploadFiles/2013/Chrysanthemum.jpg,";
		String[] fileName1=fileName.split(",");
		System.out.println("FileName Length is ************************"+fileName1.length);
       
       %>


<div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
 
  
 
  
 
  
  <tr>
    <td valign="top">
    <div class="container">
    <table width="95%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="56%" align="left" valign="top">
        <div id="gallery_wrapper">
        <table width="100%" border="0" cellspacing="10" cellpadding="0">
         <%
         int count=0;
         
           for(int i=0;i<fileName1.length;i++){
        	   if(count==0){
        		   out.println("<tr>");
        	   }
        	   count++;
       %>
            <th><a class="fancybox" href="<%=fileName1[i] %>" data-fancybox-group="gallery" title="">
            <img src="<%=fileName1[i] %>" alt=""  width="100" height="90"/></a></th>
             <%if(count==5){
        		   out.println("</tr>");
        		   count=0;
        	   } %>
        
       <%
         }
       %>
           
          
          </table>
          </div>
          </td>
      </tr>
    </table>
    </div>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</div>

		
</body>
</html>
