<jsp:directive.page import="java.util.Calendar"/>
<jsp:directive.page import="java.util.GregorianCalendar"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="com.microlabs.toDoTask.dao.ToDoTaskDao"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.*"/>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>To Do</title>

  <style type="text/css" >
  table {
   
   font-family: Georgia, Times, serif;
    border-top-left-radius:0.8em;
    border-top-right-radius:0.8em;
     border-bottom-left-radius:0.8em;
    border-bottom-right-radius:0.8em;
   }
   th {
   border: 1px solid #D0D0D0; height: 2em;
   
   font-size: 90%;
   text-transform: uppercase;
   }
   td {
   border: 1px solid #D0D0D0;
  
   height: 4em;
   width:4em;
   padding: 5px;
   vertical-align: top;
   }
   caption {
   font-size: 300%;
   font-style: italic;
   }
   .day {
   text-align: right;
   }
   .notes {
   font-family: Arial, Helvetica, sans-serif;
   font-size: 80%;
   text-align: right;
   padding-left: 20px;
   }
   .holiday {
   background-color:#FFBB8E;
   }
   .weekend {
   background-color: #F3F3F3;
   color: #F3F3F3;
   }
  </style>
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
.cal-check a {
  color: #f79901;
  overflow: hidden;
}
</style>
<style type = "text/css">
        #bubble_tooltip{
		        width:200px;
		        height:100px;
                position:absolute;
                display: none;
        }
        #bubble_tooltip .bubble_top{
                position: relative;
                background-image: url(images/bubble_top.gif);
                background-repeat:no-repeat;
              
                }
        #bubble_tooltip .bubble_middle{
                position:relative;
                   background-image: url(images/finaltooltip.png);
                background-repeat: no-repeat;
               
        }
        #bubble_tooltip .bubble_middle div{
		        padding-left: 12px;
                padding-right: 20px;
               
                position:relative;
                font-size: 11px;
                font-family: arial, verdana, san-serif;
                text-decoration: none;
                color: green;	
		        text-align:justify;	
        }
        #bubble_tooltip .bubble_bottom{
                background-image: url(images/bubble_bottom.gif);
                background-repeat:no-repeat;
             
                position:relative;
                top: 0px;
        }
</style>
<!-- Script by hscripts.com -->
<style type = "text/css">
        #bubble_tooltip{
		        width:200px;
		        height:100px;
                position:absolute;
                display: none;
        }
        #bubble_tooltip .bubble_top{
                position: relative;
                background-image: url(images/bubble_top.gif);
                background-repeat:no-repeat;
              
                }
        #bubble_tooltip .bubble_middle{
                position:relative;
                   background-image: url(images/finaltooltip.png);
                background-repeat: no-repeat;
               
        }
        #bubble_tooltip .bubble_middle div{
		        padding-left: 12px;
                padding-right: 20px;
               
                position:relative;
                font-size: 11px;
                font-family: arial, verdana, san-serif;
                text-decoration: none;
                color: green;	
		        text-align:justify;	
        }
        #bubble_tooltip .bubble_bottom{
                background-image: url(images/bubble_bottom.gif);
                background-repeat:no-repeat;
             
                position:relative;
                top: 0px;
        }
        #pl{
             position: relative;
             top: 2%;
             left: 2%;
             background-color: aqua;
             border-radius: 10px;
             height: 30%;
             width: 20%;
        
        
        }
        #ds_data{
         position: relative;
             top: 0%;
             left: 0%;
        }
        #absentList{
        position: absolute;
        top:0%;
        left: 0%;
    	 /* fallback */
	   background-image: url(images/holibgnd.jpg);
	   background-repeat: no-repeat;
	   color:white;
	-webkit-border-radius: 5px ;
	border-top-right-radius:1em;
	border-top-left-radius:1em; 
	border-bottom-right-radius:1em;
	border-bottom-left-radius:1em;
	   
         overflow:auto;
         height: 10%;
         width: auto;
          }
          #hide
          {
          display: none;
          }
</style>

<!-- Script by hscripts.com -->
<script type="text/javascript">
function showModalWindow(requeredDate)	
{
window.showModalDialog("todoTask.do?method=getToDoRecords&reqdate="+requeredDate ,null, "dialogWidth=850px;dialogHeight=620px; center:yes");

}	

function subMenuClicked(id){

	var disp=document.getElementById(id);
	
	if(disp.style.display==''){
		disp.style.display='none';
		document.forms[0].divStatus.value='none';
		}
	else{
		disp.style.display=''; 
		document.forms[0].divStatus.value='';
		}
}

function ClearPlaceHolder (input) {

            if (input.value == input.defaultValue) {
            document.getElementById('conPer').style.cssText = 'color: black;border:none;';
                input.value = "";
            }
        }
        function SetPlaceHolder (input) {
            if (input.value == "") {
            
            document.getElementById('conPer').style.cssText = 'color: grey;border:none;';
                input.value = input.defaultValue;
            }
        }


function bigImg(x)
{
x.style.height="64px";
x.style.width="64px";
}

function normalImg(x)
{
x.style.height="0px";
x.style.width="0px";
}
function nextMonth(){
var s=document.forms[0].hYear.value;

var url="todoTask.do?method=nextMonth1";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousMonth(){

var url="todoTask.do?method=prviousMonth";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function openPopWindow(currentDate) {

window.showModalDialog("todoTask.do?method=entertask&reqdate="+currentDate ,null, "dialogWidth=650px;dialogHeight=520px; center:yes");

//$( "#dialog-form" ).dialog( "open" );
}
function goTo()
{

  document.frm.submit()
}

function saveToDoTask(){
//var s=document.forms[0].subject.value;
//alert("s="+s);
		var subject = document.getElementById("subject").value;
	var description= document.getElementById("description").value;
	var fromdate= document.getElementById("taskdate").value;
	var fromtime= document.getElementById("timeFrom").value;
	var todate= document.getElementById("taskdate2").value;
	var totime= document.getElementById("timeTo").value;
	
	
	var url="todoTask.do?method=saveToDoTask2&subject="+subject+"&description="+description+"&fromdate="+fromdate+"&fromtime="+fromtime+"&todate="+todate+"&totime="+totime;
			document.forms[0].action=url;
			document.forms[0].submit();
}
function closeToDoTask(){


		var url="";
			
			document.forms[0].action=url;
			document.forms[0].submit();
}
var text1="The tooltip is an easy way of interaction for the visitors in a web page ";
var text2="For webhosting, please contact us at support@hiox.com";                    

//This is the text to be displayed on the tooltip.
var date;
var dd;



function loadXMLDoc(date)
{
var xmlhttp;
var dt;
dt=date;
if (window.XMLHttpRequest)
{// code for IE7+, Firefox, Chrome, Opera, Safari
xmlhttp=new XMLHttpRequest();
}
else
{// code for IE6, IE5
xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
xmlhttp.onreadystatechange=function()
{
if (xmlhttp.readyState==4 && xmlhttp.status==200)
{
document.getElementById("bubble_tooltip_content").innerHTML=xmlhttp.responseText;
}
}
xmlhttp.open("POST","todoTask.do?method=ajaxTest&date="+dt,true);
xmlhttp.send();
}


function showToolTip(e,text,date){
	var xmlhttp;
		var dt;
		dt=date;
		var txt;
		txt=text;
	
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("absentList").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("POST","todoTask.do?method=holidList&date="+dt+"&text="+txt,true);
		xmlhttp.send();
		
		
		 if(document.all)e = event;
   var obj = document.getElementById('absentList');
    var obj2 = document.getElementById('absentList');

    obj.style.display = 'block';
    var st = Math.max(document.body.scrollTop,document.documentElement.scrollTop);
    if(navigator.userAgent.toLowerCase().indexOf('safari')>=0)st=0; 
    var leftPos = e.clientX-2;
    if(leftPos<0)leftPos = 0;
    obj.style.left = leftPos + 'px';
    obj.style.top = e.clientY-obj.offsetHeight+2+st+ 'px';
		        
}       
function hideToolTip()
{
    document.getElementById('bubble_tooltip').style.display = 'none';
}
function showList1(e,date){
// window.open("todoTask.do?method=appList&date="+date+"","appList.jsp","width=250,height=250,top=300,left=450","true");

//window.showModalDialog("todoTask.do?method=entertask&reqdate="+currentDate ,null, "dialogWidth=650px;dialogHeight=520px; center:yes");

window.showModalDialog("todoTask.do?method=appList&date="+date, null, "dialogWidth=500;dialogHeight=600px;scroll:Yes; " );
}

function showList(e,date){
var xmlhttp;
		var dt;
		dt=date;
	
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("absentList").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("POST","todoTask.do?method=appList&date="+dt,true);
		xmlhttp.send();
		
		
		 if(document.all)e = event;
   var obj = document.getElementById('absentList');
    var obj2 = document.getElementById('absentList');

    obj.style.display = 'block';
    var st = Math.max(document.body.scrollTop,document.documentElement.scrollTop);
    if(navigator.userAgent.toLowerCase().indexOf('safari')>=0)st=0; 
    var leftPos = e.clientX-2;
    if(leftPos<0)leftPos = 0;
    obj.style.left = leftPos + 'px';
    obj.style.top = e.clientY-obj.offsetHeight+2+st+ 'px';
		        




}
function hideShowList()
{
    document.getElementById('absentList').style.display = 'none';
}
function test()
{



}

</script>


</head>
<%!public int nullIntconv(String inv) {
		int conv = 0;

		try {
			conv = Integer.parseInt(inv);
		} catch (Exception e) {
		}
		return conv;
	}%>
		<%
	String iYears = (String) session.getAttribute("iYear");


	int iYear = Integer.parseInt(iYears);
	String iMonths = (String) session.getAttribute("iMonth");
	int iMonth = Integer.parseInt(iMonths);

	Calendar ca = new GregorianCalendar();
	int iTDay = ca.get(Calendar.DATE);
	int iTYear = ca.get(Calendar.YEAR);
	int iTMonth = ca.get(Calendar.MONTH);

	if (iYear == 0) {
		iYear = iTYear;
		iMonth = iTMonth;
	}

	GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);

	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);
	 int currentMonth=(iMonth)+1;
	cal = new GregorianCalendar(iYear, iMonth, days);
	int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
	int ryear = iYear;
	int rmonth = iMonth;
    
	//get employee remainders
	UserInfo user=(UserInfo)session.getAttribute("user");
	int userid=user.getId();
	HashMap map=new HashMap();
	HashMap mlist=new HashMap();
	HashMap leaves=new HashMap();
	HashMap all_emp_list=new HashMap();
    all_emp_list=(HashMap)request.getAttribute("emp_leaves");
	System.out.println("all_emp_list size-------------------"+all_emp_list.size());
	HashMap all_emp_Ondutyslist=new HashMap();
    all_emp_Ondutyslist=(HashMap)request.getAttribute("emp_Ondutys");
	System.out.println("all_emp_Ondutyslist size-------------------"+all_emp_Ondutyslist.size());
	String start_date=null;
	String end_date=null;
	Date sdate =new Date();
    Date edate=new Date();
    Date current=new Date();
	
	try{
		
		
		try{
			
			
			String query="select * from leave_details where user_id='"+user.getEmployeeNo()+"' and Approvel_Status='Approved'";
			ToDoTaskDao ad=new ToDoTaskDao();
			ResultSet rs=ad.selectQuery(query);
			while(rs.next()){
			
			System.out.println("leave data");
		    start_date=rs.getString("start_date");
					String a[]=start_date.split(" ");
					start_date=a[0];
					String b[]=start_date.split("-");
					start_date=b[2]+"/"+b[1]+"/"+b[0];
			
			end_date=rs.getString("end_date");
					String a1[]=end_date.split(" ");
					end_date=a1[0];
					String b1[]=end_date.split("-");
					end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
			
			System.out.println(end_date);
			leaves.put(start_date,end_date);
			
			}
			}
			catch(Exception e){
			e.printStackTrace();
			}

		String getEmpRemainders="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"'";
		ToDoTaskDao ad=new ToDoTaskDao();
		ResultSet rsEmpRemainders=ad.selectQuery(getEmpRemainders);
		
		while(rsEmpRemainders.next())	
		{
		    ArrayList dates = new ArrayList();
			String from_task_date=rsEmpRemainders.getString("from_task_date");
			
			String to_task_date=rsEmpRemainders.getString("to_task_date");
				
			
			
			
			//System.out.println("Subject="+rsEmpRemainders.getString("subject"));
			if(from_task_date.equalsIgnoreCase(to_task_date))
			{
			map.put(from_task_date,rsEmpRemainders.getString("subject"));
			}
			
			else
			{
			String b[]=from_task_date.split("/");
				from_task_date=b[2]+b[1]+b[0];
				
				if(Integer.parseInt(b[0])<10)
				{
			from_task_date=b[2]+b[1]+"0"+b[0];
			}
			if(Integer.parseInt(b[1])<10)
			{
			from_task_date=b[2]+"0"+b[1]+b[0];
			}
			if(Integer.parseInt(b[0])<10 && Integer.parseInt(b[1])<10)
			{
			from_task_date=b[2]+"0"+b[1]+"0"+b[0];
			}
			 to_task_date=rsEmpRemainders.getString("to_task_date");
			String c[]=to_task_date.split("/");
				to_task_date=c[2]+c[1]+c[0];
				if(Integer.parseInt(c[0])<10)
				{
			to_task_date=c[2]+c[1]+"0"+c[0];
			}
			if(Integer.parseInt(c[1])<10)
				{
			to_task_date=c[2]+"0"+c[1]+c[0];
			}	
			if(Integer.parseInt(c[0])<10 && Integer.parseInt(c[1])<10)
			{
		to_task_date=c[2]+"0"+c[1]+"0"+c[0];
			}
			SimpleDateFormat formatter ; 
	     	formatter = new SimpleDateFormat("yyyyMMdd");
			   if(from_task_date!=null){
			String str_date = from_task_date;
			
			String end = to_task_date;
			String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as from_task_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
			ResultSet rsDates=ad.selectQuery(getBetweenDates);
			while (rsDates.next()) {
			
			String reqRemDt=rsDates.getString("from_task_date");
			    String[] test=reqRemDt.split("/");
			    String ex=test[1];
			    String fx=test[0];
			    if(ex.charAt(0)=='0')
			      ex=ex.replace('0',' ');
			      ex=ex.trim();
			      if(fx.charAt(0)=='0')
			      fx=fx.replace('0',' ');
			      fx=fx.trim();
			      reqRemDt=fx+"/"+ex+"/"+test[2];
				map.put(reqRemDt,rsDates.getString("from_task_date"));
				
			}
			}
			
		
		}
		int size=map.size();
		
		}
	
	try{

		String query="select h.holiday_date,h.holiday_name from holidays as h,users as u,Location as loc  where h.location=loc.LOCID and u.employeenumber='"+user.getEmployeeNo()+"' and h.location='"+user.getPlantId()+"'";
		ResultSet holiday_list=ad.selectQuery(query);
		while(holiday_list.next()){
		String holiday_date=holiday_list.getString("holiday_date");
		String h_type=holiday_list.getString("holiday_name");
		mlist.put(holiday_date,h_type);
		
		}
		}
		catch(Exception e){
		e.printStackTrace();
		}	
	}
	catch(Exception e){
	e.printStackTrace();
	}
%>
<body background="">
<html:form action="/todoTask.do">


<table align="left"  style="width: 70%;top: 0px;" ><div align="left" >
 <caption><a href="todoTask.do?method=prviousMonth&hYear=<%=ryear%>&hMonth=<%=rmonth%>" title="Previous Month"><img src="images/Previous.png" height="25" width="25" align="absmiddle"/></a>
&nbsp;<b><%=new SimpleDateFormat("MMMM").format(new Date(2008,iMonth, 01))%>&nbsp;<%=iYear%></b>&nbsp;
<a href="todoTask.do?method=nextMonth1&hYear=<%=ryear%>&hMonth=<%=rmonth%>"><img src="images/Next.png"  align="absmiddle" title="Next Month" height="25" width="25"/></a></caption>
</div>

<input type="hidden" name="hYear" value="<%=ryear%>" />
<input type="hidden" name="hMonth" value="<%=rmonth%>" />
<br/>

</table>

<table align="left"  style="width: 70%;box-shadow: 8px 8px 5px #888888;" >
 <col class="weekend" >
  <col class="weekday" span="5">
  <col class="weekday">
<tr>
  <th id="sunday" style="color: red;border-top-left-radius:0.5em;" ><b><big>Sun</big></b></th>
  <th id="days" ><b><big>Mon</big></b></th>
  <th id="days" ><b><big>Tue</big></b></th>
  <th id="days" ><b><big>Wed</big></b></th>
  <th id="days" ><b><big>Thu</big></b></th>
  <th id="days" ><b><big>Fri</big></b></th>
  <th id="days" style="border-top-right-radius:0.5em;"><b><big>Sat</big></b></th>
</tr>
  <%
							LoginDao ad=new LoginDao();
								int cnt = 1;
								 int ss=0;
								 
							            
								
											

								
								for (int i = 1; i <= iTotalweeks; i++) {
							%>
							<tr>
								<%
											for (int j = 1; j <= 7; j++) {
											if (cnt < weekStartDay || (cnt - weekStartDay + 1) > days) {
								%>
								<td align="center" height="20" >
									&nbsp;
								</td>
								<%
										} else {
											
											
										String currentDate = (cnt - weekStartDay + 1)+"/"+currentMonth+"/"+iYear;
										
                                            ArrayList<Date> dates = new ArrayList<Date>();
                                            	SimpleDateFormat formatter ; 
                                            	formatter = new SimpleDateFormat("dd/MM/yyyy");
									        Set s=leaves.entrySet();
                                            	Iterator it=s.iterator();
                                            	while(it.hasNext()){
                                            	  Map.Entry m =(Map.Entry)it.next();
                                            	  start_date=(String)m.getKey();
                                            	  end_date=(String)m.getValue();
									        if(start_date!=null){
											String str_date =start_date;
											String end =end_date;
											
											String ds;
											
										
											
											
											Date  startDate = (Date)formatter.parse(str_date); 
											Date  endDate = (Date)formatter.parse(end);
											long interval = 24*1000 * 60 * 60; // 1 hour in millis
											long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
											long curTime = startDate.getTime();
											while (curTime <= endTime) {
											    dates.add(new Date(curTime));
											    curTime += interval;
											}
										
											}
											}
											
                                            
											
										if(map.containsKey(currentDate)||!leaves.isEmpty()||mlist.containsKey(currentDate)||!all_emp_list.isEmpty()||!all_emp_Ondutyslist.isEmpty())
					                	{
					                	    
											
							             String cdate=new String();
					                	 int c=0; 
					                	for(int x=0;x<dates.size();x++){
                                              Date lDate =(Date)dates.get(x);

											     cdate = formatter.format(lDate); 
											     String[] test=cdate.split("/");
											    String ex=test[1];
											   
											    String fx=test[0];
											    if(ex.charAt(0)=='0')
											      ex=ex.replace('0',' ');
											      ex=ex.trim();
											      if(fx.charAt(0)=='0')
											      fx=fx.replace('0',' ');
											      fx=fx.trim();
											      cdate=fx+"/"+ex+"/"+test[2];
											     
					                	if(cdate.equalsIgnoreCase(currentDate))
					                	c=1;
							             }
							             int l=0;
							             if(mlist.containsKey(currentDate)){
							             l=1;
							             }
							             /////////////////////////// all emp leave list/////////////////////////
							             int count=0;
							            
							             if(!all_emp_list.isEmpty()){
							            	 //leave check
							            		 for (Object value : all_emp_list.values()) {
									            	 cdate = (String)value;
												     String[] test=cdate.split("/");
												    String ex=test[1];
												   
												    String fx=test[0];
												    if(ex.charAt(0)=='0')
												      ex=ex.replace('0',' ');
												      ex=ex.trim();
												      if(fx.charAt(0)=='0')
												      fx=fx.replace('0',' ');
												      fx=fx.trim();
												      cdate=fx+"/"+ex+"/"+test[2];
									            	    if(cdate.equalsIgnoreCase(currentDate)){
									            	    count=1;
									            	}
							            	 
							            		 }
							             }
							             if(!all_emp_Ondutyslist.isEmpty()){
							            	 
							            		//onduty check
							            		 for (Object value : all_emp_Ondutyslist.values()) {
							            			 
									            	 cdate = (String)value;
												     String[] test=cdate.split("/");
												    String ex=test[1];
												   
												    String fx=test[0];
												    if(ex.charAt(0)=='0')
												      ex=ex.replace('0',' ');
												      ex=ex.trim();
												      if(fx.charAt(0)=='0')
												      fx=fx.replace('0',' ');
												      fx=fx.trim();
												      cdate=test[2]+"/"+ex+"/"+fx;
												     
									            	    if(cdate.equalsIgnoreCase(currentDate)){
									            	    	
									            	    count=1;
									            	}
							            	 
							            		 }
							        
							             }
								%>
								<td <%if(l==1) {%> class="holiday" <%} %>  >
								<%
								
									   if(l==1){
									   String holidayname=(String)mlist.get(currentDate);
									   %>
										
										 <%
									%>
								<font color="green"  style="text-align: left;"><%=(cnt - weekStartDay + 1)%></font>
									 <div>	<font color="green" size="1" ><%= holidayname%></font></div>
									<% }
								if(j==1){%>
								   
								   <font color="red"  > <%=(cnt - weekStartDay + 1)%></font>
								  <% }
								%>
								
								<%
								if(j!=1 && l!=1){
								%>
								<span id="ds_data">
									<a href="#" onclick="openPopWindow('<%=currentDate %>')" ><font color="" ><%=(cnt - weekStartDay + 1)%></font></a></span>
								<%
								}
								
								
								if(count==1){
								 %><div class="notes"  >
								 
							 <img src="images/gp_leave.png" onclick="showList1(event,'<%=currentDate %>')" title="Subordinates "  height="16" width="16"  /></div>
								
								 <%}if(c==1){%>
							<div class="notes"  > <img src="images/onLeave.gif" title="My Leave"></img></div>
								 <%} %>
									
									
									<% 
									
									if(l==1){
										
									}
									else{
									
									  
									 
									 }%>
								
									<div class="reminder_icon"><a href="javascript: showModalWindow('<%=currentDate %>');">
									<% if(map.containsKey(currentDate)){ %><div class="notes"  >
									<img src="images/cal_images/reminder_icon.png" width="16" height="16" border="0" /></div>
									<%} %></a></div>
									
									</font>
								</td>
								<%
					                	}
											}
											cnt++;
										}
								%>
							</tr>
							<%
							}
							%>
</table>
</td>
<td>
<div >


	<logic:notEmpty name="userTaskList">
	<logic:iterate id="details" name="userTaskList">
	
	<div style="height: 40px;">
<div style="background-image:url(images/DisplayG1.png);width:220px;height:80px;color:black;font-size:13px;float: right;font-family: cursive;background-repeat: no-repeat; ">
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sub:&nbsp;<bean:write name="details" property="subject"/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date:&nbsp;<bean:write name="details" property="from_date"/>&nbsp;

</div>
</td>

</div>
<br/></br>
<br/>
	</logic:iterate>
	
	</logic:notEmpty>
	
	</div>
</br>

</tr></table>

<div style="width: 80%;"  >

<div class="text" style="width: 80%;  " align="center" >

&nbsp;



</div>
<div class="text" style="width: 80%; " align="center" >
<img src="images/suday_image.png"/>&nbsp;Sunday&nbsp;&nbsp;
<img src="images/Company_Holiday_Image.png"/>&nbsp;Company Holiday&nbsp;&nbsp;
<img src="images/onLeave.gif" align="bottom" title="My Leave"/>My Self&nbsp;&nbsp;
<img src="images/cal_images/reminder_icon.png" width="16" height="16"/> Task </div>
</div>
</div>
</html:form>




<!-- Script by hscripts.com -->
<!-- Script by hscripts.com -->
<table id="bubble_tooltip">
       
        <tr class="bubble_middle">
       <td>
        <div id="bubble_tooltip_content"></div>
       </td>
        </tr>
       
</table>

    

<!-- Script by hscripts.com -->


<div id="absentList"></div>
  <div id="hide"></div>  






<!-- Script by hscripts.com -->

 
</body></html>


