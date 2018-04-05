<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="/EMicro/style/content.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-left:15px">
  <tr>
    <td width="79%" align="center" valign="top"><div class="middel-blocks-iframe">
      <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left"><!--<div class="headline">
        <div class="head_leftcurve"></div>
        <div class="head_bg"><img src="images/headlines_icon1.jpg" width="27" height="42" border="0" style="float:left; margin-right:10px;" />HEAD LINES</div><div class="head_rightcurve"></div>        
      </div>-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <logic:notEmpty name="HeadLinesData">
            <tr>
               <td class="heading">Head Lines</td>
             </tr>
            <tr>
              <td class="underline"></td>
            </tr>
          </table>
      </td>
        </tr>
        
        <logic:iterate name="HeadLinesData" id="headLines">
        
        <tr>
          <td align="left" valign="top">
          <ul>
          <li><a href="main.do?method=getContentDescription&ContentId=<bean:write name="headLines" property="id" />">
          <bean:write name="headLines" property="linkDescription" /></a>
          </li>
          
          </ul>
          </td>
        </tr>
        
         </logic:iterate>
         
             <tr>
              <td>
            <div style="float: right;">
					<bean:write name="mainForm" property="linkDescription" filter="false" />
					<a href="main.do?method=getMoreDetails&Type=Head Lines">Read More</a>
							
					</div>  
             </td>
            </tr>
        
         <tr>
              <td width="4%" colspan="3" align="right" valign="top" class="normaltext">
            <div style="float: right;">
					<bean:write name="mainForm" property="linkDescription" filter="false" />
					<a href="main.do?method=getMoreDetails&Type=Head Lines">Read More</a>
							
					</div>  
             </td>
            </tr>
        </logic:notEmpty>
       
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="heading">ORGANIZATION ANNOUNCEMENTS</td>
             </tr>
            <tr>
              <td class="underline"></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td align="left" valign="top">
          <ul>
          <li>Micro Labs Limited is a multi-faceted healthcare organization with a proficient marketing team, state-of-the-art manufacturing facilities.Micro Labs Limited is a multi-faceted healthcare organization with a proficient marketing team, state-of-the-art manufacturing facilities. Micro Labs Limited is a multi-faceted healthcare organization with a proficient marketing team, state-of-the-art manufacturing facilities...</li>
          <li>Micro Labs Limited is a multi-faceted healthcare organization with a proficient marketing team, state-of-the-art manufacturing facilities.Micro Labs Limited is a multi-faceted healthcare organization with a proficient marketing team, state-of-the-art manufacturing facilities. Micro Labs Limited is a multi-faceted healthcare organization with a proficient marketing team, state-of-the-art manufacturing facilities...</li>
          </ul>
          </td>
        </tr>
    </table>
    </div></td>
    <td width="21%" align="right" valign="top"><div class="right-img_blocks-iframe">
    
        <div><img src="/EMicro/images/mgmt_img.jpg" alt="" title="" width="203" height="147"/>
          <div class="mgmtmsg">Message from Management</div>
        </div>
         <div class="mgmt_img"><img src="/EMicro/images/mgmt_img1.jpg" alt="" title="" width="203" height="147"/></div>
      </div></td>
  </tr>
</table>
</body>
</html>
