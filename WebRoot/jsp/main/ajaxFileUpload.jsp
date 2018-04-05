<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
function onUpload(){

alert("ht");

	var url="/EMicro/mail.do?method=uploadFiles";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function onDeleteFile(){
	
	var rows=document.getElementsByName("checkedfiles");

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
	document.forms[0].action="/EMicro/mail.do?method=deleteUploadedFiles&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<html:form action="mail.do" enctype="multipart/form-data">
		<tr>
			<td>
<html:file  property="documentFile" onchange="onUpload()"/></td>
</tr>
<logic:notEmpty name="listName">
		<tr>
			<th colspan="2">
				Uploaded Documents
			</th>
		</tr>
		
		<logic:iterate name="listName" id="listid">
			
			<bean:define id="file" name="listid"
				property="fileList1" />
				
		<%
				String s = file.toString();
				String v[] = s.split(",");
				int l = v.length;
				for (int i = 0; i < l; i++) 
				{
				int x=v[i].lastIndexOf("/");
					String u=v[i].substring(x+1);
					
			%>
			<tr>
			<th width="274" class="specalt" scope="row" ><a href="${listid.uploadFilePath }"   ><%=u%></a></th>
			
			<td width="15" colspan="2"><input type="checkbox" name="checkedfiles" 
						value="<%=u%>" /></td> 

			</tr>
			<%
			}
			%>		
			</logic:iterate>
			<tr>
		<td colspan="4" align="center">
			<html:button value="Delete Files" onclick="onDeleteFile()" property="method"  />
		</td>
		</tr>
			</logic:notEmpty>
			</html:form>
</body>
</html>