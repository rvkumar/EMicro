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

String getHolidays="select * from holidays where Location ='"+user.getPlantId()+"'";
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
<title>Untitled Document</title>
<link href="/EMicro/style/leftmenu.css" rel="stylesheet" type="text/css" />

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
</style>

</head>

<body>
<form name="frm" method="post">
<div>

<table width="233" cellspacing="0" cellpadding="0" class="cal_wrapper">

  <tr>
    <td align="left" valign="top">
    
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="calender">
      <tr>
        <td width="81" align="left">
          <select name="iMonth" title="Month" onChange="goTo()">
          
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
        <td width="63"> <font color="black"><a href="/EMicro/jsp/main/calender_table.jsp" > Today</a></font></td>
        <td width="59" align="right">
        <span class="calender_month">
        
        <select name="iYear" title="Year" onChange="goTo()">
        
        <%
        // start year and end year in combo box to change year in calendar
        for(int iy=iTYear-25;iy<=iTYear+50;iy++)
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
        
        </span></td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
    <td align="left" valign="top">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="calender_number">
      <tbody>
      
        <tr>
          <th class="calender_holiday">Su</th>
          <th>Mo</th>
          <th>Tu</th>
          <th>We</th>
          <th>Th</th>
          <th>Fr</th>
          <th>Sa</th>
        </tr>
        <tr></tr>
        
        
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
                <td align="center" >&nbsp;</td>
                <% 
                }
                else
                {
                	String currentDate=(cnt-weekStartDay+1)+"/"+currentMonth+"/"+iYear;
                  	
                	
                	if(map.containsKey(currentDate))
                	{ 
                	
               
                	%>
                         <td align="center" id="day_<%=(cnt-weekStartDay+1)%>" style=" background-color: yellow;"><span>
                         <b>
                         <font color="green"><a href="#" onclick="mainJ()" title="<%=map.get(currentDate) %>">
                         <%=(cnt-weekStartDay+1)%></a></font></b></span></td>
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
	                <td align="center" height="20" id="day_<%=(cnt-weekStartDay+1)%>"
	                 style="font-family:'Trebuchet MS', Arial, Helvetica, sans-serif; font-weight:bold;text-align:center;  font-size:12px; background-color:#fff; border:#eeeeee 1px solid; color:#green; " >
	                 <span><a href="#" ><font color="orange"><%=(cnt-weekStartDay+1)%></font></a></span></td>
	                 <% }else{ %>
	                 
	                 <td align="center" id="day_<%=(cnt-weekStartDay+1)%>" style="" >
	                 
	                 <%
	                 if(j==1){
	                 
	                 %>
	                 
	                 <span><a href="#"><font color="red"><%=(cnt-weekStartDay+1)%></font></a></span>
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
    </table></td>
  </tr>
  
  
</table>
</div>

</form>
</body>
</html>
