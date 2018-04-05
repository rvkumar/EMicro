


<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>sapMasterIframecms</title>

 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

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
<font face="Arial">
   	<table class="bordered" style="font-size: 12">
   	
   	
<tr><th><strong>Our Infrastructure</strong></th></tr>
  <tr><th><strong>Hardware</strong></th></tr>
	<tr>
		<td>
			a)We are using the servers of best brand <font color="#9D1CB1">(IBM)</font>  at Corporate Office & at all our plants. These servers are known for their stability, reliability and ruggedness.<br/>
			b)We have recently built a Disaster Recovery site at Ahmedabad location to ensure business continuity without any loss of data & which also meets the regulatory compliance.<br/>
			c)All the data on servers are backed by daily Tape Drive backups.<br/>
			d)We have multifunction devices across the company to cater the services like printing, scanning & photo copying. Benefits of this are faster printing & cost effective.<br/>
		
		</td>
	</tr>
	 <tr><th><strong>Software</strong></th></tr>
	<tr>
		<td>
			
			1.ITS supports software applications both vendor specific  and in-house.<br/>
			2.Applications such as MICROSOFT OFFICE, ADOBE, COREL DRAW, EMPOWER and others.<br/>
		
		</td>
	</tr>
<tr><th><strong>Virtual Desktop</strong></th></tr>

	<tr>
		<td>
			
			a)We have started implementing Citrix based VDI <font color="#9D1CB1"> (Virtual Desktop Infrastructure)</font> in our company from Feb'2014.<br/>
			b)This is being rolled out in a phased manner across the company.<br/>
			c)Major benefit of this is Greater security of data in a centralized controlled location, reduction in IT maintenance cost & longer PC duration.<br/>
		
		</td>
	</tr>
	
	
   	
<tr><th><strong>Network</strong></th></tr>

	<tr>
		<td>
			
			a)	All our plants & corporate office are connected by dedicated MPLS network which will ensure seamless connectivity between these locations & with maximum uptime. Primary link is provided by SIFY.<br/>
			b)	We are building secondary backup for the MPLS link at all locations with alternate service provider by AIRTEL. This is to ensure the redundancy of the connectivity.<br/>
			c)We have provision to extend the IT data access services outside our premises through various tools like VPN <font color="#9D1CB1">(Virtual Private Network)</font> etc.<br/>
			d)We have setup VOIP <font color="#9D1CB1">(Voice Over Internet Protocol)</font> between locations to have free audio communication channel. Now this is being extended for staff working from outside to reach company peoples.<br/>
		    c)We have setup WiFi at Corporate Office for the convenience of visitors
		</td></tr><tr>
	
	</tr>
   	
<tr><th><strong>Business Applications</strong></th></tr>

	<tr>
		<td>
			
A)We are using SAP the best ERP software for running our business.<BR/>
B)We have made learning easier through e-learning system.  This tool is capable of narrating usage of a system in steps, all users to practice & do self-assessment.<BR/>
C)We have provided software to all our super stockiest connected to company which gives us online secondary sales data. This project is called as <b><font color="#9D1CB1">"DISHA"</font></b>.<BR/>
D)We have legacy software called <b><font color="#9D1CB1">"ML@NET"</font></b> for sales force automation for capturing all day-to-day activities of the field person. Now we are migrating to new software called "NAVDISHA" which is an enhanced version of current legacy software.<BR/>
E)We have <b><font color="#9D1CB1">"Pharmaready"</font></b> software which caters the services like eCTD submission & DMS.<BR/>
F)We have <b><font color="#9D1CB1">"ARISg"</font></b> world's leading pharmacovigilance software for maintaining critical drug safety data, provides all the functionality required to manage adverse event reporting and adverse reaction requirements of different authorities around the world<BR/>
G)	We are using <b><font color="#9D1CB1">"Tally"</font></b> software as supporting to Finance Department.<BR/>
		</td></tr>
   
<tr><th><strong>Video Conference</strong></th></tr>

	<tr>
		<td>
			
1)We have setup Video Conference at various locations like Corporate Office, Hosur Plant, Goa Plant, Kudulu R&D, Baddi, Mumbai R&D, Ahemdabad, Kolkatta.<br/>
2)Video Conference can be done with external & internal participants.<br/>
3)Within India we have tie up with Reliance Web World to conduct VC from various available locations to company VC.<br/>

		</td></tr>

<tr><th><strong>Emailing Solution</strong></th></tr>

	<tr>
		<td>
			
1.We use <font color="#9D1CB1">"IBM Notes"</font> e-mailing solution from IBM for business communications within & outside.<br/>
2.	We have <font color="#9D1CB1">"Notes Traveller"</font> for enabling the access of mails on smart phones & tablets.<br/>
3.We have <font color="#9D1CB1">"Same Time Chat"</font> from IBM for having simple text based communication among employees based on computer login (Windows Login).

		</td></tr>
	
	<tr><th><strong>Security Solutions</strong></th></tr>

	<tr>
		<td>
			
	1.We use <font color="#9D1CB1">"Trend Micro"</font> Anti-Virus solution to protect desktop, laptop  internet security.</br>
	2.We use <font color="#9D1CB1">"Websense"</font>for URL & PROXY control of internet usage.	</br>
	3.We use <font color="#9D1CB1">"IMSVA"</font> as Gateway security.	</br>
	4.We use <font color="#9D1CB1">FORTIGATE</font> based firewall.
		</td></tr>	
		
		
		
   	</table>

   	<br/>
   	 <div align="right"><a href="it.do?method=displayCMS1"><img src="images/Pre-2.png" height="20px" width="80px" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;&nbsp;
   	 </div>
   	   	   	   	     	</font> 	   	
</body>
</html>