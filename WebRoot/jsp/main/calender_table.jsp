<%@ page  language="java" import="java.util.*,java.text.*"%>
<jsp:directive.page import="java.sql.ResultSet" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%!
public int nullIntconv(String inv)
{   
    int conv=0;
        
    try{
        conv=Integer.parseInt(inv);
    }
    catch(Exception e)
    {}
    return conv;
 }
%>
<%
int iYear=nullIntconv(request.getParameter("iYear"));

int iMonth=nullIntconv(request.getParameter("iMonth"));

Calendar ca = new GregorianCalendar();

int iTDay=ca.get(Calendar.DATE);
int iTYear=ca.get(Calendar.YEAR);
int iTMonth=ca.get(Calendar.MONTH);

if(iYear==0)
{
     iYear=iTYear;
     iMonth=iTMonth;
}

GregorianCalendar cal = new GregorianCalendar (iYear, iMonth, 1); 

int days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
int weekStartDay=cal.get(Calendar.DAY_OF_WEEK);

cal = new GregorianCalendar (iYear, iMonth, days); 
int iTotalweeks=cal.get(Calendar.WEEK_OF_MONTH);
int iMaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
System.out.println("iMaxDay***="+iMaxDay);
com.microlabs.login.dao.LoginDao ad=new com.microlabs.login.dao.LoginDao();
int currentMonth=(iMonth)+1;
String fromYear=iTYear+"-"+currentMonth+"-"+1;
String toYear=iTYear+"-"+currentMonth+"-"+iMaxDay;
com.microlabs.utilities.UserInfo user = (com.microlabs.utilities.UserInfo)session.getAttribute("user");
System.out.println(user.getPlantId());

String getHolidays="select convert(nvarchar(10),Holiday_Date,103) as Holiday_Date,* from holidays where Location ='"+user.getPlantId()+"'";
ResultSet rsGetHolidays = ad.selectQuery(getHolidays);
HashMap map=new HashMap();
while(rsGetHolidays.next())
{
	map.put(rsGetHolidays.getString("Holiday_Date"),rsGetHolidays.getString("Holiday_Name"));

}

int day, month, year;

GregorianCalendar date = new GregorianCalendar();

day = date.get(Calendar.DAY_OF_MONTH);
month = date.get(Calendar.MONTH);
year = date.get(Calendar.YEAR);

String today=day+"/"+(month+1)+"/"+year;

 
%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Calender</title>
<!-- link href="/EMicro/style/leftmenu.css" rel="stylesheet" type="text/css" /-->

<script>

function mainJ(currentDate){

  var reqDate=currentDate;

  var retValue = showModalDialog ("/EMicro/main.do?method=getAboutDay", "", "dialogWidth:380px; dialogHeight:350px; dialogLeft:300px;scroll:yes;status=no;");
}

function goTo()
{
  document.frm.submit();
  //var url="bodyPart.jsp";
	//		document.forms[0].action=url;
	//		document.forms[0].submit();

}


function nextMonth(){
var url="calender.do?method=nextMonth1";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousMonth(){

var url="calender.do?method=prviousMonth";
			document.forms[0].action=url;
			document.forms[0].submit();
}
</script>

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

html, body
{
  margin: 1px;
  padding: 0px;
  border: 0;
  font-size: 100%;
  font: inherit;
  vertical-align: baseline;
}


body {
  line-height: 1;
}

ol, ul {
  list-style: none;
}

blockquote, q {
  quotes: none;
}

blockquote:before, blockquote:after,
q:before, q:after {
  content: '';
  content: none;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}

html, body {
  min-height: 100%;
}

body {
  font: 13px/20px 'Lucida Grande', Tahoma, Verdana, sans-serif;
  color: #404040;  
  background-image: -webkit-radial-gradient(center, circle cover, #dbdede, #bfbdbe);
  background-image: -moz-radial-gradient(center, circle cover, #dbdede, #bfbdbe);
  background-image: -o-radial-gradient(center, circle cover, #dbdede, #bfbdbe);
  background-image: radial-gradient(center, circle cover, #dbdede, #bfbdbe);
}

.cal {
  position: relative;
  padding: 4px;
  font-weight: bold;
  background: #bebfc0;
  background: rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  display: inline-block;
  vertical-align: baseline;
  zoom: 1;
  *display: inline;
  *vertical-align: auto;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.2), 0 1px rgba(255, 255, 255, 0.4);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.2), 0 1px rgba(255, 255, 255, 0.4);
}
.cal:before {
  content: '';
  position: absolute;
  bottom: 3px;
  left: 4px;
  right: 4px;
  height: 6px;
  background: #d9d9d9;
  border: 1px solid #909090;
  border-radius: 4px;
  -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}
.cal a {
  text-decoration: none;
}
.cal tr:first-child td {
  border-top: 0;
}
.cal td:first-child {
  border-left: 0;
}
.cal tr:first-child a {
  border-top: 0;
  margin-top: 0;
}
.cal tr:last-child a {
  border-bottom: 0;
  margin-bottom: 0;
}
.cal td:first-child a {
  border-left: 0;
  margin-left: 0;
}
.cal td:last-child a {
  border-right: 0;
  margin-right: 0;
}
.cal tr:last-child td:first-child a {
  border-radius: 0 0 0 3px;
}
.cal tr:last-child td:last-child a {
  border-radius: 0 0 3px 0;
}

.cal-table {
  position: relative;
  margin: 0 0 1px;
  border-collapse: separate;
  border-left: 1px solid #979797;
  border-right: 1px solid #979797;
  border-bottom: 1px solid #bbb;
  border-radius: 0 0 3px 3px;
  -webkit-box-shadow: 1px 0 rgba(0, 0, 0, 0.1), -1px 0 rgba(0, 0, 0, 0.1);
  box-shadow: 1px 0 rgba(0, 0, 0, 0.1), -1px 0 rgba(0, 0, 0, 0.1);
}

.cal-caption {
  width: 100%;
  padding-bottom: 1px;
  line-height: 32px;
  color: white;
  text-align: center;
  text-shadow: 0 -1px rgba(0, 0, 0, 0.3);
  background: #003c78;
  border-radius: 3px 3px 0 0;
	background-image: linear-gradient(bottom, rgb(65,107,145) 15%, rgb(0,60,120) 85%);
	background-image: -o-linear-gradient(bottom, rgb(65,107,145) 15%, rgb(0,60,120) 85%);
	background-image: -moz-linear-gradient(bottom, rgb(65,107,145) 15%, rgb(0,60,120) 85%);
	background-image: -webkit-linear-gradient(bottom, rgb(65,107,145) 15%, rgb(0,60,120) 85%);
	background-image: -ms-linear-gradient(bottom, rgb(65,107,145) 15%, rgb(0,60,120) 85%);
}

.cal-caption a {
  line-height: 30px;
  padding: 0 10px;
  font-size: 20px;
  font-weight: normal;
  color: white;
}
.cal-caption .prev {
  float: left;
}
.cal-caption .next {
  float: right;
}

.cal-body td {
  width: 30px;
  font-size: 11px;
  border-top: 1px solid #eaeaea;
  border-left: 1px solid #eaeaea;
}
.cal-body a {
  display: block;
  position: relative;
  line-height: 28px;
  color: #555;
  text-align: center;
  background: white;
}
.cal-body a:hover {
  background: #fafafa;
}

.cal-off a {
  color: #ccc;
  font-weight: normal;
}

.cal-today a {
  color: black;
  background: #f5f5f5;
  background-image: -webkit-linear-gradient(top, whitesmoke, white 70%);
  background-image: -moz-linear-gradient(top, whitesmoke, white 70%);
  background-image: -o-linear-gradient(top, whitesmoke, white 70%);
  background-image: linear-gradient(to bottom, whitesmoke, white 70%);
}

.cal-selected a, .cal-body a:active {
  margin: -1px;
  color: #b2494d;
  background: #fff5f6;
  border: 1px solid #e7d4d4;
}

.cal-check a {
  color: #f79901;
  overflow: hidden;
}
.cal-check a:before {
  content: '';
  position: absolute;
  top: -6px;
  right: -6px;
  width: 12px;
  height: 12px;
  background: #ffb83b;
  background-image: -webkit-linear-gradient(top, #ffb83b, #ff6c00);
  background-image: -moz-linear-gradient(top, #ffb83b, #ff6c00);
  background-image: -o-linear-gradient(top, #ffb83b, #ff6c00);
  background-image: linear-gradient(to bottom, #ffb83b, #ff6c00);
  -webkit-transform: rotate(-45deg);
  -moz-transform: rotate(-45deg);
  -ms-transform: rotate(-45deg);
  -o-transform: rotate(-45deg);
  transform: rotate(-45deg);
}

.lt-ie8 .cal-table {
  *border-collapse: collapse;
}
.lt-ie8 .cal-body a {
  zoom: 1;
}
</style>

</head>

<body>
<form name="frm" method="post">

<div class="cal">
<table width="225" class="cal-table" cellpadding="0" cellspacing="0">
<thead  class="cal-caption">
	<tr>
        <td>
          <select name="iMonth" title="Month" onChange="goTo()" class="cal-caption" style="border: 1px">
          
        <%
        // print month in combo box to change month in calendar
        for(int im=0;im<=11;im++)
        {
          if(im==iMonth)
          {
         %>
          <option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(2008,im,01))%></option>
         <%
          }
          else
          {
         %>
          <option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(2008,im,01))%></option>
          <%
          }
        } %>
          
          </select>		
        	
		</td>
		
        <td>
	        <select name="iYear" title="Year" onChange="goTo()" class="cal-caption" style="border: 1px">
		        <%
		        // start year and end year in combo box to change year in calendar
		        for(int iy=iTYear-1;iy<=iTYear+1;iy++)
		        {
		          if(iy==iYear)
		          {
		          %>
		          <option value="<%=iy%>" selected="selected"><%=iy%></option>
		          <%
		          }
		          else
		          {
		          %>
		          <option value="<%=iy%>"><%=iy%></option>
		          <%
		          }
		        }
		       %>
			</select>
		</td>
		<td class="cal-off"><a href="http://portal.microlabs.co.in/jsp/main/calender_table.jsp">Today</a></td>
	</tr>
	</thead>
</table> 
	<table width="225" class="cal-table" cellpadding="0" cellspacing="0">
    <tbody class="cal-body">
        <tr class="cal-caption">
          <th style="color: red;">Su</th>
          <th>Mo</th>
          <th>Tu</th>
          <th>We</th>
          <th>Th</th>
          <th>Fr</th>
          <th>Sa</th>
        </tr>
        
        <%
        int cnt =1;
        for(int i=1;i<=iTotalweeks;i++)
        {
        %>
        <tr>
          <% 
            for(int j=1;j<=7;j++)
            {
                if(cnt<weekStartDay || (cnt-weekStartDay+1)>days)
                {
                %>
                <td bgcolor="white"></td>
                <% 
                }
                else
                {
                	String currentDate=(cnt-weekStartDay+1)+"/"+("0"+currentMonth).substring (("0"+currentMonth).length()-2)+"/"+iYear;
                	
                	if(map.containsKey(currentDate))
                	{ 
                	
               
                	%>
                         <td align="center" id="day_<%=(cnt-weekStartDay+1)%>" class="cal-check"><span>
                         <a href="#"  title="<%=map.get(currentDate) %>">
                         <%=(cnt-weekStartDay+1)%></a></span></td>
                        <% 
	                }else{
	                
	                int currentMonth1=0;
	                if(currentMonth==1){
	                currentMonth1=0;
	                }else{
	                currentMonth1=currentMonth-1;
	                }
	                
	                if((cnt-weekStartDay+1)==iTDay & currentMonth1==iTMonth && iYear==iTYear){
	                %>
	                <td align="center" style="" id="day_<%=(cnt-weekStartDay+1)%>" class="cal-selected">
	                 <span><a href="#" title="Today">
	                 <font color="brown"><%=(cnt-weekStartDay+1)%></font></a></span></td>
	                 <% }else{ %>
	                 
	                 <td align="center" id="day_<%=(cnt-weekStartDay+1)%>" >
	                 
	                 <%
	                 if(j==1){
	                 
	                 %>
	                 
	                 <span><a href="#" ><font color="red"><%=(cnt-weekStartDay+1)%></font></a></span>
	                 <% 
	                 }else{
	                	 %>
	                	 
	                	  <span><a href="#"><%=(cnt-weekStartDay+1)%></a></span>
	                 <%
	                 }
	                 %>
	                 </td>
	               <% 
	                }
	                }
                }
                cnt++;
              }
            %>
        </tr>
        <% 
        }
        %>
  </tbody>    
  </table>
</div>

</form>
</body>
</html>
