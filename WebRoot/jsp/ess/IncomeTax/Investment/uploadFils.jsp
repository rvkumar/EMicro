<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<html xmlns="http://www.w3.org/1999/xhtml">
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
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
<script language="javascript">
function deleteDocumentsSelected()
{
var rows=document.getElementsByName("documentCheck");

var checkvalues='';
var uncheckvalues='';
for(var i=0;i<rows.length;i++)
{
if (rows[i].checked)
{
checkvalues+=rows[i].value+',';
}else{
uncheckvalues+=rows[i].value+',';
}
}

if(checkvalues=='')
{
alert('please select atleast one value to delete');
}
else
{
var agree = confirm('Are You Sure To Delete Selected file');
if(agree)
{
	document.forms[0].action="incomeTax.do?method=deleteDocuments&cValues="+checkvalues;
document.forms[0].submit();
}
}
}
function uploadDocument(){
if(document.forms[0].documentFile.value==""){
alert("Please Select Atleast One File")
return false;
}


    var x = document.getElementById("filedesc").value;

				if(x=="")
					{
					alert("Please Enter File Description");
					document.getElementById("filedesc").focus();
					return false;
					}
				
				
    

document.forms[0].action="incomeTax.do?method=uploadDocuments&filedesc="+x;
	document.forms[0].submit();

}







function totalAmount1(){
var levels =  document.getElementById("levelNo").value;
var total=0;
for(var i = 1; i <= levels; i++){
var no=document.getElementById('amount'+i).value;
no=parseInt(no);

total=total+no;
}
document.getElementById("totalAmount").value=total;
}


function statusMessage(message){
alert(message);
}
</script>
<body>
<html:form action="incomeTax" enctype="multipart/form-data" method="post">
<div align="center" id="messageID" style="visibility: true;">
				<logic:present name="incomeTaxForm" property="message2">
			
				<script language="javascript">
					statusMessage('<bean:write name="incomeTaxForm" property="message2" />');
					</script>
				</logic:present>
				<logic:present name="incomeTaxForm" property="message">
						<script language="javascript">
					statusMessage('<bean:write name="incomeTaxForm" property="message" />');
					</script>
					
				</logic:present>
			</div>
<table class="bordered"  style="margin-top: 0px;margin-bottom: 0px">
<tr><td colspan="10"><font color="red">Note:All  Investments must be supported by attaching the scanned PDF Documents. Without Supporting Investment Documents request will not be considered for IT submission </font></td></tr>
        <tr >
               <th   align="center" colspan="8">Upload Files : 
                  	<html:file property="documentFile" />
                  	File Description : <input  type="text" name="filedesc" id="filedesc" style="width: 300px; " onkeyup="this.value = this.value.replace(/'/g,'`')"/>
                   <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadDocument();" style="align:right;width:100px;"/>
                 
               </th>
		</tr>
		<logic:notEmpty name="documentDetails">
	
		<tr>
						<th colspan="10">Uploaded Documents </th>
						</tr>
						

						<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
				<td>${abc.invRemarks }</td>
					
						</tr>
						</logic:iterate>
							<td align="center" colspan="6">
								<input type="button"  class="content" value="Delete" onclick="deleteDocumentsSelected()"/>
							</td>
							
						</logic:notEmpty>
<html:hidden property="requestNumber"/>						
</table></html:form>
</body>
</html>