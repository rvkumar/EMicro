<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<title>Salary Details</title>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">
</script>
<style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}

select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>
</head>
<body>
<div align="center" id="messageID" style="visibility: true;">
		<logic:present name="summaryForm" property="message">
			<font color="red" size="3"><b><bean:write name="summaryForm" property="message" /></b></font>
			<script type="text/javascript">
			setInterval(hideMessage,6000);
			</script>
		</logic:present>
		<logic:present name="summaryForm" property="message1">
			<font color="Green" size="3"><b><bean:write name="summaryForm" property="message1" /></b></font>
			<script type="text/javascript">
			setInterval(hideMessage,6000);
			</script>
		</logic:present>
	</div>
<html:form action="/summary.do" enctype="multipart/form-data" >

 
<logic:notEmpty name="salaryDetails">
<table class="bordered">
<tr><th>Component</th><th>Total</th><th>April</th><th>May</th><th>June</th><th>July</th><th>August</th><th>September</th><th>October</th><th>November</th>
<th>December</th><th>January</th><th>February</th><th>March</th></tr>

<tr>
<td>BASIC</td><td>${summaryForm.basicTotal }</td><td><%= (request.getAttribute("4basic"))%></td><td><%= (request.getAttribute("5basic"))%></td><td><%= (request.getAttribute("6basic"))%></td><td><%= (request.getAttribute("7basic"))%></td><td><%= (request.getAttribute("8basic"))%></td><td><%= (request.getAttribute("9basic"))%></td><td><%= (request.getAttribute("10basic"))%></td><td><%= (request.getAttribute("11basic"))%></td><td><%= (request.getAttribute("12basic"))%></td><td><%= (request.getAttribute("1basic"))%></td><td><%= (request.getAttribute("2basic"))%></td><td><%= (request.getAttribute("3basic"))%></td>
</tr>
<tr>
<td>HRA</td><td>${summaryForm.hraTotal }</td><td><%= (request.getAttribute("4hra"))%></td><td><%= (request.getAttribute("5hra"))%></td><td><%= (request.getAttribute("6hra"))%></td><td><%= (request.getAttribute("7hra"))%></td><td><%= (request.getAttribute("8hra"))%></td><td><%= (request.getAttribute("9hra"))%></td><td><%= (request.getAttribute("10hra"))%></td><td><%= (request.getAttribute("11hra"))%></td><td><%= (request.getAttribute("12hra"))%></td><td><%= (request.getAttribute("1hra"))%></td><td><%= (request.getAttribute("2hra"))%></td><td><%= (request.getAttribute("3hra"))%></td>
</tr>
<tr>
<td>CONVY</td><td>${summaryForm.convyTotal }</td><td><%= (request.getAttribute("4convy"))%></td><td><%= (request.getAttribute("5convy"))%></td><td><%= (request.getAttribute("6convy"))%></td><td><%= (request.getAttribute("7convy"))%></td><td><%= (request.getAttribute("8convy"))%></td><td><%= (request.getAttribute("9convy"))%></td><td><%= (request.getAttribute("10convy"))%></td><td><%= (request.getAttribute("11convy"))%></td><td><%= (request.getAttribute("12convy"))%></td><td><%= (request.getAttribute("1convy"))%></td><td><%= (request.getAttribute("2convy"))%></td><td><%= (request.getAttribute("3convy"))%></td>
</tr>
<tr>
<td>CEA</td><td>${summaryForm.ceaTotal }</td><td><%= (request.getAttribute("4cea"))%></td><td><%= (request.getAttribute("5cea"))%></td><td><%= (request.getAttribute("6cea"))%></td><td><%= (request.getAttribute("7cea"))%></td><td><%= (request.getAttribute("8cea"))%></td><td><%= (request.getAttribute("9cea"))%></td><td><%= (request.getAttribute("10cea"))%></td><td><%= (request.getAttribute("11cea"))%></td><td><%= (request.getAttribute("12cea"))%></td><td><%= (request.getAttribute("1cea"))%></td><td><%= (request.getAttribute("2cea"))%></td><td><%= (request.getAttribute("3cea"))%></td>
</tr>
<tr>
<td>INC</td><td>${summaryForm.incTotal }</td><td><%= (request.getAttribute("4inc"))%></td><td><%= (request.getAttribute("5inc"))%></td><td><%= (request.getAttribute("6inc"))%></td><td><%= (request.getAttribute("7inc"))%></td><td><%= (request.getAttribute("8inc"))%></td><td><%= (request.getAttribute("9inc"))%></td><td><%= (request.getAttribute("10inc"))%></td><td><%= (request.getAttribute("11inc"))%></td><td><%= (request.getAttribute("12inc"))%></td><td><%= (request.getAttribute("1inc"))%></td><td><%= (request.getAttribute("2inc"))%></td><td><%= (request.getAttribute("3inc"))%></td>
</tr>
<tr>
<td>LTAA</td><td>${summaryForm.ltaaTotal }</td><td><%= (request.getAttribute("4ltaa"))%></td><td><%= (request.getAttribute("5ltaa"))%></td><td><%= (request.getAttribute("6ltaa"))%></td><td><%= (request.getAttribute("7ltaa"))%></td><td><%= (request.getAttribute("8ltaa"))%></td><td><%= (request.getAttribute("9ltaa"))%></td><td><%= (request.getAttribute("10ltaa"))%></td><td><%= (request.getAttribute("11ltaa"))%></td><td><%= (request.getAttribute("12ltaa"))%></td><td><%= (request.getAttribute("1ltaa"))%></td><td><%= (request.getAttribute("2ltaa"))%></td><td><%= (request.getAttribute("3ltaa"))%></td>
</tr>
<tr>
<td>MEDA</td><td>${summaryForm.medaTotal }</td><td><%= (request.getAttribute("4meda"))%></td><td><%= (request.getAttribute("5meda"))%></td><td><%= (request.getAttribute("6meda"))%></td><td><%= (request.getAttribute("7meda"))%></td><td><%= (request.getAttribute("8meda"))%></td><td><%= (request.getAttribute("9meda"))%></td><td><%= (request.getAttribute("10meda"))%></td><td><%= (request.getAttribute("11meda"))%></td><td><%= (request.getAttribute("12meda"))%></td><td><%= (request.getAttribute("1meda"))%></td><td><%= (request.getAttribute("2meda"))%></td><td><%= (request.getAttribute("3meda"))%></td>
</tr>
<tr>
<td>BONUS</td><td>${summaryForm.bonusTotal }</td><td><%= (request.getAttribute("4bonus"))%></td><td><%= (request.getAttribute("5bonus"))%></td><td><%= (request.getAttribute("6bonus"))%></td><td><%= (request.getAttribute("7bonus"))%></td><td><%= (request.getAttribute("8bonus"))%></td><td><%= (request.getAttribute("9bonus"))%></td><td><%= (request.getAttribute("10bonus"))%></td><td><%= (request.getAttribute("11bonus"))%></td><td><%= (request.getAttribute("12bonus"))%></td><td><%= (request.getAttribute("1bonus"))%></td><td><%= (request.getAttribute("2bonus"))%></td><td><%= (request.getAttribute("3bonus"))%></td>
</tr>
<tr>
<td>OTHERS</td><td>${summaryForm.othersTotal }</td><td><%= (request.getAttribute("4others"))%></td><td><%= (request.getAttribute("5others"))%></td><td><%= (request.getAttribute("6others"))%></td><td><%= (request.getAttribute("7others"))%></td><td><%= (request.getAttribute("8others"))%></td><td><%= (request.getAttribute("9others"))%></td><td><%= (request.getAttribute("10others"))%></td><td><%= (request.getAttribute("11others"))%></td><td><%= (request.getAttribute("12others"))%></td><td><%= (request.getAttribute("1others"))%></td><td><%= (request.getAttribute("2others"))%></td><td><%= (request.getAttribute("3others"))%></td>
</tr>
<tr>
<td>GRSAL</td><td>${summaryForm.grsalTotal }</td><td><%= (request.getAttribute("4grsal"))%></td><td><%= (request.getAttribute("5grsal"))%></td><td><%= (request.getAttribute("6grsal"))%></td><td><%= (request.getAttribute("7grsal"))%></td><td><%= (request.getAttribute("8grsal"))%></td><td><%= (request.getAttribute("9grsal"))%></td><td><%= (request.getAttribute("10grsal"))%></td><td><%= (request.getAttribute("11grsal"))%></td><td><%= (request.getAttribute("12grsal"))%></td><td><%= (request.getAttribute("1grsal"))%></td><td><%= (request.getAttribute("2grsal"))%></td><td><%= (request.getAttribute("3grsal"))%></td>
</tr>
<tr>
<td>EA</td><td>${summaryForm.eaTotal }</td><td><%= (request.getAttribute("4ea"))%></td><td><%= (request.getAttribute("5ea"))%></td><td><%= (request.getAttribute("6ea"))%></td><td><%= (request.getAttribute("7ea"))%></td><td><%= (request.getAttribute("8ea"))%></td><td><%= (request.getAttribute("9ea"))%></td><td><%= (request.getAttribute("10ea"))%></td><td><%= (request.getAttribute("11ea"))%></td><td><%= (request.getAttribute("12ea"))%></td><td><%= (request.getAttribute("1ea"))%></td><td><%= (request.getAttribute("2ea"))%></td><td><%= (request.getAttribute("3ea"))%></td>
</tr>
<tr>
<td>PF</td><td>${summaryForm.pfTotal }</td><td><%= (request.getAttribute("4pf"))%></td><td><%= (request.getAttribute("5pf"))%></td><td><%= (request.getAttribute("6pf"))%></td><td><%= (request.getAttribute("7pf"))%></td><td><%= (request.getAttribute("8pf"))%></td><td><%= (request.getAttribute("9pf"))%></td><td><%= (request.getAttribute("10pf"))%></td><td><%= (request.getAttribute("11pf"))%></td><td><%= (request.getAttribute("12pf"))%></td><td><%= (request.getAttribute("1pf"))%></td><td><%= (request.getAttribute("2pf"))%></td><td><%= (request.getAttribute("3pf"))%></td>
</tr>
<tr>
<td>VOLPF</td><td>${summaryForm.volpfTotal }</td><td><%= (request.getAttribute("4volpf"))%></td><td><%= (request.getAttribute("5volpf"))%></td><td><%= (request.getAttribute("6volpf"))%></td><td><%= (request.getAttribute("7volpf"))%></td><td><%= (request.getAttribute("8volpf"))%></td><td><%= (request.getAttribute("9volpf"))%></td><td><%= (request.getAttribute("10volpf"))%></td><td><%= (request.getAttribute("11volpf"))%></td><td><%= (request.getAttribute("12volpf"))%></td><td><%= (request.getAttribute("1volpf"))%></td><td><%= (request.getAttribute("2volpf"))%></td><td><%= (request.getAttribute("3volpf"))%></td>
</tr>
<tr>
<td>LIP</td><td>${summaryForm.lipTotal }</td><td><%= (request.getAttribute("4lip"))%></td><td><%= (request.getAttribute("5lip"))%></td><td><%= (request.getAttribute("6lip"))%></td><td><%= (request.getAttribute("7lip"))%></td><td><%= (request.getAttribute("8lip"))%></td><td><%= (request.getAttribute("9lip"))%></td><td><%= (request.getAttribute("10lip"))%></td><td><%= (request.getAttribute("11lip"))%></td><td><%= (request.getAttribute("12lip"))%></td><td><%= (request.getAttribute("1lip"))%></td><td><%= (request.getAttribute("2lip"))%></td><td><%= (request.getAttribute("3lip"))%></td>
</tr>

<tr>
<td>PT</td><td>${summaryForm.ptTotal }</td><td><%= (request.getAttribute("4pt"))%></td><td><%= (request.getAttribute("5pt"))%></td><td><%= (request.getAttribute("6pt"))%></td><td><%= (request.getAttribute("7pt"))%></td><td><%= (request.getAttribute("8pt"))%></td><td><%= (request.getAttribute("9pt"))%></td><td><%= (request.getAttribute("10pt"))%></td><td><%= (request.getAttribute("11pt"))%></td><td><%= (request.getAttribute("12pt"))%></td><td><%= (request.getAttribute("1pt"))%></td><td><%= (request.getAttribute("2pt"))%></td><td><%= (request.getAttribute("3pt"))%></td>
</tr>
<tr>
<td>CONVYE</td><td>${summaryForm.convyeTotal }</td><td><%= (request.getAttribute("4convye"))%></td><td><%= (request.getAttribute("5convye"))%></td><td><%= (request.getAttribute("6convye"))%></td><td><%= (request.getAttribute("7convye"))%></td><td><%= (request.getAttribute("8convye"))%></td><td><%= (request.getAttribute("9convye"))%></td><td><%= (request.getAttribute("10convye"))%></td><td><%= (request.getAttribute("11convye"))%></td><td><%= (request.getAttribute("12convye"))%></td><td><%= (request.getAttribute("1convye"))%></td><td><%= (request.getAttribute("2convye"))%></td><td><%= (request.getAttribute("3convye"))%></td>
</tr>
<tr>
<td>CEAE</td><td>${summaryForm.ceaeTotal }</td><td><%= (request.getAttribute("4ceae"))%></td><td><%= (request.getAttribute("5ceae"))%></td><td><%= (request.getAttribute("6ceae"))%></td><td><%= (request.getAttribute("7ceae"))%></td><td><%= (request.getAttribute("8ceae"))%></td><td><%= (request.getAttribute("9ceae"))%></td><td><%= (request.getAttribute("10ceae"))%></td><td><%= (request.getAttribute("11ceae"))%></td><td><%= (request.getAttribute("12ceae"))%></td><td><%= (request.getAttribute("1ceae"))%></td><td><%= (request.getAttribute("2ceae"))%></td><td><%= (request.getAttribute("3ceae"))%></td>
</tr>
<tr>
<td>HRAE</td><td>${summaryForm.hraeTotal }</td><td><%= (request.getAttribute("4hrae"))%></td><td><%= (request.getAttribute("5hrae"))%></td><td><%= (request.getAttribute("6hrae"))%></td><td><%= (request.getAttribute("7hrae"))%></td><td><%= (request.getAttribute("8hrae"))%></td><td><%= (request.getAttribute("9hrae"))%></td><td><%= (request.getAttribute("10hrae"))%></td><td><%= (request.getAttribute("11hrae"))%></td><td><%= (request.getAttribute("12hrae"))%></td><td><%= (request.getAttribute("1hrae"))%></td><td><%= (request.getAttribute("2hrae"))%></td><td><%= (request.getAttribute("3hrae"))%></td>
</tr>



</table>
</logic:notEmpty>
</html:form></body></html>