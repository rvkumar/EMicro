<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:directive.page import="com.microlabs.newsandmedia.dao.NewsandMediaDao,com.microlabs.hr.form.HRForm"/>
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
	
	
	
	<link rel="stylesheet" type="text/css" href="../jquery.jscrollpane.css" />	
			<script type="text/javascript" src="../jquery.jscrollpane.min.js"></script>

	<script type="text/javascript">
	
	  $(document).ready(function () {
	      if (!$.browser.webkit) {
	          $('.container').jScrollPane();
	      }
	  });
	
	</script>
	
	
</head>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<jsp:directive.page import="com.microlabs.newsandmedia.dao.NewsandMediaDao,com.microlabs.hr.form.HRForm"/>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
  <html:form action="newsAndMedia.do">
					<bean:write name="newsAndMediaForm" property="contentDescription" filter="false" />
									
									<logic:notEmpty name="newsAndMediaForm" property="fileFullPath">
									
									<bean:define id="file" name="newsAndMediaForm"
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
									
									</logic:notEmpty>
									<logic:notEmpty name="newsAndMediaForm" property="videoFullPath">	
						<div style="float: right;">
								
									<bean:define id="video" name="newsAndMediaForm" property="videoFullPath" />
									<%
										String s1 = video.toString();
										
										System.out.println("Getting A Video Name is **********************"+s1);
										
										s1 = s1.substring(0, s1.length());
										String v1[] = s1.split(",");
										int l1 = v1.length;
										for (int j = 0; j < l1; j++) {
										int x=v1[j].lastIndexOf("/");
											String u=v1[j].substring(x+1);
									%>
									
									
						<DIV ALIGN="CENTER">
		<table border="1" cellpadding="0" cellspacing="0">
			<tr><td style="text-align: center">
				<EMBED SRC="<%=s1 %>" AUTOPLAY="FALSE" width="400" height="320"></EMBED> 
			</td><tr>
		</table>
		</DIV>			
									
									
							
							<br/><br/><br/><br/>	
									<%
									}
									%>
							</div>
							</logic:notEmpty>
<%
NewsandMediaDao ad=new NewsandMediaDao();

String linkName=(String)request.getAttribute("linkname");

String getImages="select * from archieves where link_name='"+linkName+"' and module='Main' and status='null'";
System.out.println("getImages="+getImages);
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
	fileName1[i]="/EMicro/"+a[i];
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
  
<div>

        <div id="gallery_wrapper">
        <table width="50%" border="0" cellspacing="10" cellpadding="0">
         <%
         int count=0;
         
           for(int i=0;i<fileName1.length;i++){
        	   if(count==0){
        		   out.println("<tr>");
        		   System.out.println(" i am in "+count);
        	   }
        	   count++;
       %>
       <tr>
            <td align="left"><a class="fancybox" href="<%="../"+fileName1[i] %>" data-fancybox-group="gallery" >
            <img src="<%="../"+fileName1[i] %>" alt=""  width="100" height="90" /></a></td>
             <%
             if(count==4)
             {
            	 out.println("<br/>");
            	 count=0;
             }
             if(count==5){
        		   out.println("</tr>");
        		   count=0;
        	   } %>
        
       <%
         }
       %>
           
          
          </table>
          </div>
         
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
  		
			</div>
    		</td>
   		</tr>
   	</table>
</body>
</html>							
							</html:form>