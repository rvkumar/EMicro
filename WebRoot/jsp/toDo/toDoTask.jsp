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
   background-color:#E6E6E6;
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
<script type="text/javascript">
function showModalWindow(requeredDate)	
{
	
window.showModalDialog("todoTask.do?method=getToDoRecords&reqdate="+requeredDate ,window.location, "dialogWidth=850px;dialogHeight=620px; center:yes");

}	
		
		
		
		
$(function() {
var name = $( "#name" ),
email = $( "#email" ),
password = $( "#password" ),
allFields = $( [] ).add( name ).add( email ).add( password ),
tips = $( ".validateTips" );
function updateTips( t ) {
tips
.text( t )
.addClass( "ui-state-highlight" );
setTimeout(function() {
tips.removeClass( "ui-state-highlight", 1500 );
}, 500 );
}
function checkLength( o, n, min, max ) {
if ( o.val().length > max || o.val().length < min ) {
o.addClass( "ui-state-error" );
updateTips( "Length of " + n + " must be between " +
min + " and " + max + "." );
return false;
} else {
return true;
}
}
function checkRegexp( o, regexp, n ) {
if ( !( regexp.test( o.val() ) ) ) {
o.addClass( "ui-state-error" );
updateTips( n );
return false;
} else {
return true;
}
}
$( "#dialog-form" ).dialog({
autoOpen: false,
height: 500,
width: 350,
modal: true,


});
$("#create-user")
.button().click(function() {$( "#dialog-form" ).dialog( "open" );
});
});
</script>




		<!-- PopUp Window -->

		<script type="text/javascript">
<!--


jQuery(document).ready(function () {
//add class active for the first image of slideshow
$("#slideshow img:first").addClass("active");
//call the slideshow for every 3 seconds (3000 mili-secs)
setInterval("slideshow()", 1500);
});
function slideshow() {
var $active = $("#slideshow .active");
var $next = ($("#slideshow .active").next().length > 0) ? $("#slideshow .active").next() : $("#slideshow img:first");
// 1500 is the fade in and out speed in mili-secs - change this to speed up or slow down
$next.fadeIn(1500,function(){
$next.addClass("active");
$active.fadeOut(1500).removeClass("active");
});
}

function popupCalender(param)
		{
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%Y/%m/%d ", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2" // trigger button
		});
		}



function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
 document.getElementById('conPer').style.cssText = 'color: grey;border:none;';
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
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

}

function goTo()
{

  document.frm.submit()
}

function saveToDoTask(){	
var subject = document.getElementById("subject").value;
	var description= document.getElementById("description").value;
	var fromdate= document.getElementById("taskdate").value;
	var fromtime= document.getElementById("timeFrom").value;
	var todate= document.getElementById("taskdate2").value;
	var totime= document.getElementById("timeTo").value;
	
	if(subject=="")
	{
	alert("Please enter Subject ")
	document.getElementById("subject").focus();
	return false;
	}
	
		if(fromdate=="")
	{
	alert("Please enter From Date ")
	document.getElementById("taskdate").focus();
	return false;
	}
	
	if(todate=="")
	{
	alert("Please enter To Date ")
	document.getElementById("taskdate2").focus();
	return false;
	}
	
			if(fromdate!=todate)
	    {
	   var str1 = fromdate;
var str2 = todate;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 

if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.getElementById("taskdate2").value="";
    document.getElementById("taskdate2").focus();
     return false;
}
	    
	    
	     }
	
	if(todate!="")
	{
	
	 var fa=ValidateForm();
      if(fa==false)
     {
      return false;
     }
     	}

var url="todoTask.do?method=saveToDoTask2&subject="+subject+"&description="+description+"&fromdate="+fromdate+"&fromtime="+fromtime+"&todate="+todate+"&totime="+totime;
	
			document.forms[0].action=url;
			document.forms[0].submit();
}
function validatedate(inputText)  
 {  

 var dateformat = /^(0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])[\/\-]\d{4}$/;  
 
 
 
  // Match the date format through regular expression  
  if(inputText.match(dateformat))  
 {  
  //Test which seperator is used '/' or '-'  
  var opera1 = inputText.split('/');  
  var opera2 = inputText.split('-');  
  lopera1 = opera1.length;  
  lopera2 = opera2.length;  
  // Extract the string into month, date and year  
  if (lopera1>1)  
  {  
 var pdate = inputText.split('/');  
  }  
  else if (lopera2>1)  
  {  
  var pdate = inputText.split('-');  
  }  
  var mm  = parseInt(pdate[0]);  
  var dd = parseInt(pdate[1]);  
  var yy = parseInt(pdate[2]);  
  // Create list of days of a month [assume there is no leap year by default]  
  var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];  
 if (mm==1 || mm>2)  
  {  
  if (dd>ListofDays[mm-1])  
  {  
  alert('Invalid date format!');  
  return false;  
  }  
  }  
  if (mm==2)  
  {  
  var lyear = false;  
  if ( (!(yy % 4) && yy % 100) || !(yy % 400))   
  {  
  lyear = true;  
  }  
  if ((lyear==false) && (dd>=29))  
  {  
  alert('Invalid date format!');  
  return false;  
 }  
  if ((lyear==true) && (dd>29))  
  {  
  alert('Invalid date format!');  
  return false;  
  }  
  }  
  }  
  else  
  {  
  alert("Invalid date format!");  
  return false;  
  }  
  }  
  
  
function closeToDoTask(){
			var url="";
			
			document.forms[0].action=url;
			document.forms[0].submit();
}

//-->
</script>

<script language = "Javascript">

var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strDay=dtStr.substring(0,pos1)
	var strMonth=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		alert("The date format should be : dd/mm/yyyy")
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day")
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false;
	}
return true
}

function ValidateForm(){
	var dt=document.getElementById("taskdate2");
	if (isDate(dt.value)==false){
		dt.focus();
		return false;
	}
    return true;
 }
 


</script>
<script type="text/javascript">
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
           function hideShowList()
        {
                document.getElementById('absentList').style.display = 'none';
        }
                 function  hide(e,text,date)
        {
                document.getElementById('absentList').style.display = 'none';
        }
       

</script>
<!-- Script by hscripts.com -->



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
	System.out.println("calender.jsp");
	String iYears = (String) session.getAttribute("iYear");

	System.out.println("Getting a years is " + iYears);

	int iYear = Integer.parseInt(iYears);
	String iMonths = (String) session.getAttribute("iMonth");
	int iMonth = Integer.parseInt(iMonths);

	System.out.println("iMonth=" + iMonth);
	System.out.println("------");
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
	System.out.println("YEAR=" + ryear);
	System.out.println("MONTH=" + rmonth);
    
	System.out.println("weekStartDay=" + weekStartDay);
	System.out.println("days=" + days);
	System.out.println("iTotalweeks=" + iTotalweeks);
	//get employee remainders
	UserInfo user=(UserInfo)session.getAttribute("user");
	int userid=user.getId();
	HashMap map=new HashMap();
	HashMap mlist=new HashMap();
	HashMap leaves=new HashMap();
	String start_date=null;
	String end_date=null;
	Date sdate =new Date();
    Date edate=new Date();
    Date current=new Date();
	try{
	
		String getEmpRemainders="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"'";
		System.out.println("getEmpRemainders="+getEmpRemainders);
		ToDoTaskDao ad=new ToDoTaskDao();
		ResultSet rsEmpRemainders=ad.selectQuery(getEmpRemainders);
		
	
	while(rsEmpRemainders.next())	
	{   
		ArrayList dates = new ArrayList();
	String from_task_date=rsEmpRemainders.getString("from_task_date");
	
	String to_task_date=rsEmpRemainders.getString("to_task_date");
		
     System.out.println(to_task_date);
	
	
	
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
System.out.println("map size="+map.size());

}
	
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	try{

		String query="select h.holiday_date,h.holiday_name from holidays as h,users as u,Location as loc  where h.location=loc.LOCID and u.employeenumber='"+user.getEmployeeNo()+"' and h.location='"+user.getPlantId()+"'";
	ToDoTaskDao ad=new ToDoTaskDao();
	ResultSet holiday_list=ad.selectQuery(query);
	while(holiday_list.next()){
	String holiday_date=holiday_list.getString("holiday_date");
	String h_type=holiday_list.getString("holiday_name");
	System.out.println(holiday_date);
	mlist.put(holiday_date,h_type);
	
	}
	}
	catch(Exception e){
	e.printStackTrace();
	}
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
								for (int i = 1; i <= iTotalweeks; i++) {
							%>
							<tr>
								<%
											for (int j = 1; j <= 7; j++) {
											if (cnt < weekStartDay || (cnt - weekStartDay + 1) > days) {
								%>
								<td align="center" height="10" >
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
											
											
											
										if(map.containsKey(currentDate)||!leaves.isEmpty()||mlist.containsKey(currentDate))
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
											      
											    System.out.println(cdate);
											      
											    
											     
					                	if(cdate.equalsIgnoreCase(currentDate))
					                	{
											c=1;
							             }
							             }
							             int l=0;
							             if(mlist.containsKey(currentDate)){
							             l=1;
							             }
												
								%>
								<td <%if(l==1) {%> class="holiday" <%} %> >
								
									<span>
									
									 
									<a href="#" onclick="openPopWindow('<%=currentDate %>')" >	
								
									
								
									<%
									   if(l==1){
										   
									%>
									<font color="green" size="3px;" ><%=(cnt - weekStartDay + 1)%></font>
								
									
									<% }
									if(c==1){
										 %>
										<div class="notes"> 	<img src="images/onLeave.gif" align="right" title="My Leave" ></img></div>
										<% 
										}
									
									 if(l==1){
										 String holidayname=(String)mlist.get(currentDate);
										 %>
										 <div>	<font color="green" size="1" ><%= holidayname%></font></div>
										 <% 
									 }
									else{
									 if(j==1){%>
									   
								  <font color="red"  ><%=(cnt - weekStartDay + 1)%></font>
									  <% }
									  else{
									 %><font color=""><%=(cnt - weekStartDay + 1)%></font>
									 <%} 
									 }%>
									 
									
								
								
									
									<div class="reminder_icon"><a href="javascript: showModalWindow('<%=currentDate %>');">
									<% if(map.containsKey(currentDate)){ %>
								<div class="notes"  ><img src="images/cal_images/reminder_icon.png" width="16" height="16" border="0" align="bottom" /></div>
									<%} %></a></div>
									
									</font><span>
								</td>
								<%
					                	}
					                		
					                
					                	else{
					                	
											%><% 
											if(j==1){%>
											   <td align="center" height="45" width="70"  
												style="font-family: serif; font-size: 14px; text-decoration: none;width: 100px;">
												<span><b> <a href="#" onclick="openPopWindow('<%=currentDate %>')"><font color="red" ><%=(cnt - weekStartDay + 1)%></font></a>
												</b><span>
											</td>
											<% }else{%><td align="center" height="45" width="70" 
												style="font-family: serif; font-size: 14px; text-decoration: none;width: 100px;">
												<span><b><a href="#" onclick="openPopWindow('<%=currentDate %>')"><%=(cnt - weekStartDay + 1)%></a>
												</b><span>
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
							<br></br>
</table>
</td>
<td>
<div >


	<logic:notEmpty name="userTaskList">
	<logic:iterate id="details" name="userTaskList" length="4">
	
<!-- Codes by HTML.am -->
<div style="height: 40px;">

<div style="background-image:url(images/DisplayG2.png);width:220px;height:80px;color:black;font-size:13px;float: right;font-family: cursive;background-repeat: no-repeat; ">
<br/>&nbsp;&nbsp;Sub:&nbsp;<bean:write name="details" property="subject"/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date:&nbsp;<bean:write name="details" property="from_date"/>&nbsp;

</div>

</div>
<br/></br>
<br/>
	</logic:iterate>
	
	</logic:notEmpty>
	
	</div>
</td>
</tr></table>
<br></br>

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

