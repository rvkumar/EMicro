<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="com.microlabs.newsandmedia.dao.NewsandMediaDao,com.microlabs.hr.form.HRForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>

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
		
.bordered {
    border: solid #ccc 1px;
    -moz-border-radius: 6px;
    -webkit-border-radius: 6px;
    border-radius: 6px;
    -webkit-box-shadow: 0 1px 1px #ccc; 
    -moz-box-shadow: 0 1px 1px #ccc; 
    box-shadow: 0 1px 1px #ccc;         
}



.bordered th {
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
    border-top: none;
    text-shadow: 0 1px 0 rgba(255,255,255,.5); 
}		
		
</style>



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





  
  
  	<html:form action="userGroup.do">
					
					<bean:write name="userGroupForm" property="contentDescription" filter="false" />
					<br/>
					<logic:notEmpty name="userGroupForm" property="fileFullPath">
					<table class="bordered" style="width:40%;">
					
					<tr>
									<th>Documents</th></tr>
									<tr>
						<td>
									<bean:define id="file" name="userGroupForm"
										property="fileFullPath" />
								<%
										String s = file.toString();
										s = s.substring(0, s.length());
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) {
										int x=v[i].lastIndexOf("/");
											String u=v[i].substring(x+1);
									%>
									<a href="<%=v[i]%>" target="_blank"><%=u%></a>

									<br />
									<%
									}
									%>
									
</td></tr>
					</table>
									</logic:notEmpty>
						
			<logic:notEmpty name="userGroupForm" property="videoFullPath">			
					
						<table class="bordered" style="float: right;" >
							<tr>
							<th>Videos</th></tr>
							<tr>
							<td>
									<bean:define id="video" name="userGroupForm" property="videoFullPath" />
									
									<%
										String s1 = video.toString();
										s1 = s1.substring(0, s1.length());
										String v1[] = s1.split(",");
										int l1 = v1.length;
										for (int j = 0; j < l1; j++) {
										int x=v1[j].lastIndexOf("/");
											String u=v1[j].substring(x+1);
											s1=v1[j].replace(",", "");
									%>
									
				<video controls >
  <source src="<%=s1 %>" type="video/ogg">
  <source src="<%=s1 %>" type="video/mp4">
  Your browser does not support the <code>video</code> element.
</video>
							
									<%
									}
									%>
							
							
							
							</td></tr></table>
							</logic:notEmpty>
					
							 <table align="left" >


    <tr>
  <td align="left">



<%
NewsandMediaDao ad=new NewsandMediaDao();

String linkName=(String)request.getAttribute("linkName");

String getImages="select * from archieves where link_name='"+linkName+"' and module='Main' and status='null'";	
ResultSet rsImages=ad.selectQuery(getImages);
String imagepath="";
while(rsImages.next()) {

	imagepath=rsImages.getString("image_name");
	
	
}
String a[]=imagepath.split(",");
String test="";
String[] fileName1=new String[a.length];
for(int i=0;i<a.length;i++)
{
	fileName1[i]=a[i];
	System.out.println("out put image in jsp="+fileName1[i]);
}
System.out.println("out put image in jsp="+a.length);
%>


  </td>
  </tr>
  <%
  
  if(imagepath.equalsIgnoreCase(""))
		  {
	  
		  }else{
  %>
  


    <div>&nbsp;</div>

        <div id="gallery_wrapper">
<table width="25%"  cellpadding="0" class="bordered" >
  <tr><th colspan="3">Images</th></tr>
         <%
         int count=0;
         
           for(int i=0;i<fileName1.length;i++){
        	   if(count==0){
        		   out.println("<tr>");
        		   System.out.println(" i am in "+count);
        	   }
        	   count++;
       %>
    
       
            <td align="left"><a class="fancybox" href="<%="../"+fileName1[i] %>"  rel="gallery" data-fancybox-group="gallery">
            <img src="<%="../"+fileName1[i] %>" alt=""  width="100" height="90" /></a></td>
             <%
            
             if(count==3){
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
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</div>

  
  
  
 
   </table>
   
   <%
		  }
   %>
  		
			</html:form></div>
    		</td>
   		</tr>
   		<tr>
   		<td>

</td></tr><!--------FOOTER ENDS -------------------->   		
   	</table>
   	
   	
</body>
</html>