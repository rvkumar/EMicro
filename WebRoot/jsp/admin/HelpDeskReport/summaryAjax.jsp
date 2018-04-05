<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css" />
	<script type="text/javascript">
$(document).ready(function() {
    $('#example').DataTable( {
        initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
} );
</script>
</head>
<body>
<br/>
<%int i = 0; %>
<table class="bordered" id="example" style="width: 65%">
<logic:notEmpty name="Techinician">
<tr>
<th>Tec Name</th><th >Pending</th><th>Closed</th><th>Total</th><th>Forwarded</th><th>SLA Violated</th>
</tr>
<logic:iterate id="h" name="Techinician">

<%i++; %>
<tr>

<td><bean:write name="h" property="empName"/></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno" />&requestedtype=Techinician&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=pending&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>"  /> ${h.pending} </a></td>
<td><bean:write name="h" property="closed"/></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Techinician&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=total&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>"  /> ${h.total} </a></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Techinician&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=forward&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>"  /> ${h.next} </a></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Techinician&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=SLA&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>"  /> ${h.prev} </a></td>
 </tr> 
 </logic:iterate>
 </logic:notEmpty>
 
 
<logic:notEmpty name="Category">
<th>Category</th><th >Pending</th><th>Closed</th><th>Total</th>
<logic:iterate id="h" name="Category">
<%i++; %>
<tr>
<td><bean:write name="h" property="empno"/></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/> &requestedtype=Category&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=pending&selType=Summary &fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>" /> ${h.pending} </a></td>
<td><bean:write name="h" property="closed"/></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Category&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=total&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>" /> ${h.total} </a></td>
 </tr> 
 </logic:iterate>
 </logic:notEmpty>


<logic:notEmpty name="Priority">
<th>Priority</th><th >Pending</th><th>Closed</th><th>Total</th>
<logic:iterate id="h" name="Priority">
<%i++; %>
<tr>
<td><bean:write name="h" property="empno"/></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Priority&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=pending&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>" /> ${h.pending} </a></td>
<td><bean:write name="h" property="closed"/></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Priority&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=total&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>" /> ${h.total} </a></td>
 </tr> 
 </logic:iterate>
 </logic:notEmpty>

<logic:notEmpty name="Mode">
<th>Mode</th><th >Pending</th><th>Closed</th><th>Total</th>
<logic:iterate id="h" name="Mode">
<%i++; %>
<tr>
<td><bean:write name="h" property="empno"/></td>
<td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Mode&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=pending&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>" /> ${h.pending} </a></td>
<td><bean:write name="h" property="closed"/></td>
 <td> <a href="helpDeskReport.do?method=getSummaryDetail&techname=<bean:write name="h" property="empno"/>&requestedtype=Mode&loc=<bean:write name="helpdeskReportForm" property="requesterdepartment"/>&status=total&selType=Summary&fromDate=<bean:write name="helpdeskReportForm" property="fromDate1"/>&toDate=<bean:write name="helpdeskReportForm" property="toDate1"/>" /> ${h.total} </a></td>
 </tr> 
 </logic:iterate>
 </logic:notEmpty>
 
 <% if(i==0) {%>
 <tr>
 <th>Type</th><th >Pending</th><th>Closed</th><th>Total</th>
 </tr>
 <tr>
 <td colspan="4">No Search Records Found</td>
 </tr>
 <%} %>

</table>

</body>
</html>									