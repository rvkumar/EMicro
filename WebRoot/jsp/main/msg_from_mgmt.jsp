<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<html>
<head>
<title>eMicro :: Message from Management</title>
<link href="jsp/newsAndMedia/PlanInfo/TableCSS.css" rel="stylesheet" type="text/css" /></head>
<body>
<font face="Cambria">
<p>

<table class="bordered">
	<tr><th colspan="2"><strong>Message from Management</strong></th></tr>
	<tr>
		<td align="center">
			<img src="images/DilipSurana.png" title="Mr.Dilip Surana (CMD)"><br/>
			<strong><font size="+2">Mr. Dilip Surana</font><br/>
			CMD</strong>
		</td>
		<td>
			<img src="images/AnandSurana.png" title="Mr.Anand Surana (Director)"><br/>
			<strong><font size="+2">Mr. Anand Surana</font><br/>
			Director</strong>
		</td>
	</tr>
 	<tr>
 	<td colspan="2">
		<p>Yet another year has passed with challenges and accomplishments. And along with its passing, what we have are the memories of many wonderful learning through which we can fine tune ourselves to face newer challenges and make greater strides in business refinements. I am pleased to congratulate each one of you involved in the continuing success of Micro Labs.</p> 

		<p><b>Some of the highlights of 2012 :</b><br/>
		<ul><li>Micro Labs awarded as <font color="blue"><b><i>India's Most Valuable Pharmaceutical Company</i></b></font> at 3rd Annual India Leadership conclave & Indian Affairs Business Leadership Awards 2012</li>
		<li>Micro Labs Baddi Unit bags <font color="blue"><b><i>Gold Award</i></b></font> at The Economic Times India Manufacturing Excellence Awards (IMEA) held in partnership with Frost & Sullivan</li> 
		<li>Micro Labs has been conferred with <font color="blue"><b><i>Pharma CSR Organisation of the Year 2012</i></b></font> awarded by Pharmaleaders</li>
		<li>Micro has been bestowed upon the <font color="blue"><b><i>Innovative R & D Company of the Year 2012</i></b></font> awarded by Pharmaleaders</li>
		<li>Mr. Ashok jain, Executive Director, Micro Labs Limited, awarded with <font color="blue"><b><i>Pharma Professional of the Decade 2012</i></b></font> award by the Pharma Leaders.</li> 
		<li>Micro Labs manufacturing units won a <font color="blue"><b><i>Gold Award</i></b></font> and <font color="blue"><b><i>Three Silver Awards</i></b></font> under the category of best formulation plants. </li>
		<li>Mr. Shivaji kapade, Sr. Vice President, HR awarded with <font color="blue"><b><i>HR Leadership Award</i></b></font> by Employer Branding Institute - India at 7th Employer Branding Awards</li></ul></p>
		
		<p>As we document the unfolding of the year, our primary focus should be to create new fountain heads of value creation both within India and abroad while continuing to expand the existing flanks of value creation.</p>  
		
		<p>The Indian Pharma industry is posed with tough competitions and fast moving changes. <br/>
		To keep our company at par with the existing market conditions, we have to be proactive to "<font color="blue">Accept the Change</font>" and then, "<font color="blue">Be the Change</font>" to face the challenges and hard competitions ahead.</p>
		<p>Like every drop that makes an ocean, each one of us has within ourselves, the capability to make a difference. It is important that we believe in ourselves, have faith in our capabilities and encompass the courage to carry out our convictions.</p> 
	</td></tr>
</table>
<p><br/>
</font>
<html:form action="main.do">
	
	<logic:notEmpty name="managementVideo">
	
			<DIV ALIGN="right">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr><td style="text-align: right;">
				<EMBED SRC="/EMicro Files/News And Media/Archieves/Message From Managment/UploadVideos/2013/${mainForm.managementVideo }" AUTOPLAY="FALSE" width="400" height="280" controller="true"></EMBED> 
			</td><tr>
		</table>
		</DIV>	
	</logic:notEmpty>	
	</html:form>	
</body>
</html>
