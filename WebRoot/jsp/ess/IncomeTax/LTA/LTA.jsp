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
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/sorttable.js"></script>
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
/* function popupCalender(param)
{
	var toD = new Date();

	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2",
	range :[2016,2017]// trigger button
	});
} */





function datedisplay()
{	

	var start=$("#startDate").val();


	 var str1=start;





		var dt1  = parseInt(str1.substring(0,2),10); 
		var mon1 = parseInt(str1.substring(3,5),10); 
		var yr1  = parseInt(str1.substring(6,10),10); 
		var date1 = new Date(yr1, mon1-1, dt1); 
	
		
		$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy',maxDate:date1});
		$('#inlineDatepicker').datepick({onSelect: showDate});



}


function datedisplay1()
{

	var end=$("#endDate").val();


	var str2=end;




	
		var dt2  = parseInt(str2.substring(0,2),10); 
		var mon2 = parseInt(str2.substring(3,5),10); 
		var yr2  = parseInt(str2.substring(6,10),10); 	
		var date2 = new Date(yr2, mon2-1, dt2);

		

		
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy',minDate:date2});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
	
}

function calculateDays()
{
	var startDate=document.forms[0].startDate.value;
	var startDateDuration=document.forms[0].startDurationType.value;
	var endDate=document.forms[0].endDate.value;
	var endDurationType=document.forms[0].endDurationType.value;
	var leaveType=document.forms[0].leaveType.value;
	var casualMaxDays=document.forms[0].clMaxDays.value;
	var sickMaxDays=document.forms[0].slMaxDays.value;

	var clmindur=document.forms[0].clmindur.value;
	var slmindur=document.forms[0].slmindur.value;
	var lossmindur=document.forms[0].lossmindur.value;
	var plmindur=document.forms[0].plmindur.value;

	 if(leaveType=="")
	   {
	     alert("Leave Type should not be left blank");
	     document.forms[0].leaveType.focus();
	     document.forms[0].endDurationType.value="";
	     
	     return false;
	   }
	var lvType="";

	if(leaveType==1)
	    lvType="Casual ";
	if(leaveType==2)
	    lvType="Sick ";
	if(leaveType==3)
	    lvType="Privilege";
	if(leaveType==5)
	    lvType="MATERNITY";        
	        
	    
	   
	if(startDate=="")
	   {
	     alert("Please Select Start Date");
	     document.forms[0].startDate.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }
	 if(startDateDuration=="")
	   {
	     alert("Please Select Start Date Duration");
	     document.forms[0].startDateDuration.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }
	   if(endDate=="")
	   {
	     alert("Please Select End Date");
	     document.forms[0].endDate.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }
	    if(startDate!=""&&endDate!=""){
	   
	     var str1 = document.forms[0].startDate.value;
	var str2 = document.forms[0].endDate.value;
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
	    document.forms[0].endDate.value="";
	     document.forms[0].endDate.focus();
	     return false;
	}
	   }
	   if(endDurationType=="")
	   {
	     alert("Please Select End Date Duration");
	     document.forms[0].endDurationType.focus();
	     return false;
	   }
	   
	   if((startDateDuration=="FD"&&endDurationType=="SH"))
	   {
	   
	   alert("Please Select Valid Duration");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].endDurationType.value="";
	      
	     return false;
	   }
	    if((startDateDuration=="FD"&&endDurationType=="SH"))
	   {
	   
	   alert("Please Select Valid Duration");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].endDurationType.value="";
	      
	     return false;
	   }
	   
	   if((startDateDuration=="FH"&&endDurationType=="FD"))
	   {
	   
	   alert("Please Select Valid Duration");
	   document.forms[0].startDurationType.focus();
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].startDurationType.value="";
	      
	     return false;
	   }
	   
	   if((startDateDuration=="FH"&&endDurationType=="SH"))
	   {
	   
	   alert("Please Select Valid Duration");
	   document.forms[0].startDurationType.focus();
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].endDurationType.value="";
	      
	     return false;
	   }
	  
	   if(startDate==endDate && startDateDuration!=endDurationType)
	   {
	   alert("Please Select Valid Duration ");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	        document.forms[0].endDurationType.value="";
	     return false;
	   }
	   if(document.forms[0].leaveType.value=="3")
	   {
	   
	   
	   
	   var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
	  
	   if((startDateDuration=="SH" && endDurationType=="FD") ||(startDateDuration=="FD" && endDurationType=="SH")||(startDateDuration=="FD" && endDurationType=="FH"))
	   {
	   totalLeaveDays=totalLeaveDays-1;
	   }

	 var d=document.forms[0].preleavmin.value;
	 
	 var casualBal=document.forms[0].casuallvcloseBal.value;
	 var sickBal=document.forms[0].sicklvcloseBal.value;
	 
	if(sickBal!=0.0 && casualBal!=0.0)
	{
	   if(totalLeaveDays<d){
	     alert("Minimum "+d+" days should be availed for Previlage Leave");
	    document.forms[0].totalLeaveDays.value="";
	    document.forms[0].endDate.value="";
	    
	         return false;
	    }
	   }
	   }
	   if((startDateDuration=="FH"&&endDurationType=="FH")||(startDateDuration=="SH"&&endDurationType=="SH"))
	   {
	    if(startDate!=endDate){
	   alert("Please Select Valid Duration");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	        document.forms[0].endDurationType.value="";
	     return false;
	    }
	   
	   }  
	 if(leaveType!="" && startDate!="" && startDateDuration!="" && endDate!="" && endDurationType!="" && leaveType!="5")
	 {
	var xmlhttp;



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
	    document.getElementById("noOfDaysDiv").innerHTML=xmlhttp.responseText;
	    var totalDays=document.forms[0].totalLeaveDays.value;
	    if(leaveType==1)
	    {
	   if(casualMaxDays<totalDays){
	    var mess= "Maximum "+casualMaxDays+" days allow to apply Casual leave";
	    alert(mess);
	    document.forms[0].endDate.value="";
	   document.forms[0].totalLeaveDays.value="";
	    }
	    }
	     if(leaveType==2)
	    {
		    if(sickMaxDays<totalDays){
		    var mess= "Maximum "+sickMaxDays+" days allow to apply Sick leave";
		    alert(mess);
		    document.forms[0].endDate.value="";
		   document.forms[0].totalLeaveDays.value="";
		    }
	    }
	    
	    if(totalDays==0.0){
	      alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
	    document.forms[0].totalLeaveDays.value="";
	     document.forms[0].endDate.value="";
	    }
	    if(totalDays==-1.0){
	    alert("You have applied Onduty");
	    document.forms[0].endDate.value="";
	    document.forms[0].totalLeaveDays.value="";
	    }
	    if(totalDays==-2.0){
	    alert("You have applied Permission");
	    document.forms[0].endDate.value="";
	    document.forms[0].totalLeaveDays.value="";
	    }
	    if(totalDays==-4.0){
	       // alert("You have applied Sick Leave.\nPlease Choose Another Leave Type");
	       alert("Already you have applied Leave On Selection Date.");
	        document.forms[0].endDate.value="";
	        document.forms[0].totalLeaveDays.value="";
	      }
	    /*  
	    if(totalDays==-5.0){
	        alert("You have applied Casual Leave.\nPlease Choose Another Leave Type");
	        document.forms[0].endDate.value="";
	        document.forms[0].totalLeaveDays.value="";
	      }
	      */
	      if(totalDays==-3.0){
	            var leavtype=document.forms[0].leaveType.value;
	        //casual leave condition   
	        if(false)
	        {
	        var advdays=document.forms[0].casleavadv.value;
	      
	        var mess= " Minimum "+advdays+" days before is required to apply casual leave";
	        alert(mess);
	        document.forms[0].endDate.value="";
	      document.forms[0].totalLeaveDays.value="";
	        }
	        
	         if(leavtype==3)
	        {
	        var advdays=document.forms[0].preleavadv.value;
	        var mess= "Minimum "+advdays+" days before is required to apply privilege leave";
	        alert(mess);
	        document.forms[0].endDate.value="";
	       document.forms[0].totalLeaveDays.value="";
	        }
	        
	    }
	    if(totalDays==-6.0){
	    var mess= " Selection date is holiday.Please Check the Dates ";
	        alert(mess);
	        document.forms[0].endDate.value="";
	      document.forms[0].totalLeaveDays.value="";
	    }
	     if(totalDays==-7.0){
	     if(leaveType=="1")
	    	var mess= "Between Two Casual Leaves "+clmindur+" days is required. \n Please Check last Applied Casual Leave.";
	    if(leaveType=="2")
	    	var mess= "Between Two Sick Leaves "+slmindur+" days is required. \n Please Check last Applied Sick Leave. ";
	    if(leaveType=="3")
	    	var mess= "Between Two Privilege Leaves "+plmindur+" days is required. \n Please Check last Applied Privilege Leave. ";
	    if(leaveType=="4")
	    	var mess= "Between Two Loss Of Pay Leaves "+lossmindur+" days is required. \n Please Check last Applied Loss Of Pay Leave. ";			
	        alert(mess);
	        document.forms[0].endDate.value="";
	      document.forms[0].totalLeaveDays.value="";			
	        
	    }
	    
	    
	    }
	  }

	xmlhttp.open("POST","lta.do?method=calculateDays&LeaveType="+leaveType+"&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType,true);
	xmlhttp.send();

	}
	if(leaveType=="5" )
	{
		var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
		
		 if(totalLeaveDays<60){
	     alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
	    document.forms[0].totalLeaveDays.value="";
	    }else if(totalLeaveDays>90){
	    alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
	    document.forms[0].totalLeaveDays.value="";
	    }
	    else{
		document.forms[0].totalLeaveDays.value=totalLeaveDays;
		}
	}
}

function daydiff(first, second) {

	//daydiff

	var totaldays=(second-first)/(1000*60*60*24);

	if(totaldays<0)
	{

	    return "";
	}
	else{

	    return ((second-first)/(1000*60*60*24)+1)
	    }
	}
function checkLeaveCal(){
	var leave=document.forms[0].leaveDetails.value;
	
	if(leave!="")
	{
	var str=leave.split(";");
	document.forms[0].startDate.value=str[0];
    document.forms[0].startDurationType.value=str[1];
	document.forms[0].endDate.value=str[2];
	document.forms[0].endDurationType.value=str[3];
	document.forms[0].totalLeaveDays.value=str[4];
	document.forms[0].leaveType.value=str[5];

		}
		
		else
		{
		document.forms[0].startDate.value="";
    document.forms[0].startDurationType.value="";
	document.forms[0].endDate.value="";
	document.forms[0].endDurationType.value="";
	document.forms[0].totalLeaveDays.value="";
	document.forms[0].leaveType.value="";
		}

	}
	
function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}
	
function calculateDays()
{
	var startDate=document.forms[0].startDate.value;
	var startDateDuration=document.forms[0].startDurationType.value;
	var endDate=document.forms[0].endDate.value;
	var endDurationType=document.forms[0].endDurationType.value;
	var leaveType=document.forms[0].leaveType.value;
	var casualMaxDays=document.forms[0].clMaxDays.value;
	var sickMaxDays=document.forms[0].slMaxDays.value;

	var clmindur=document.forms[0].clmindur.value;
	var slmindur=document.forms[0].slmindur.value;
	var lossmindur=document.forms[0].lossmindur.value;
	var plmindur=document.forms[0].plmindur.value;

	 if(leaveType=="")
	   {
	     alert("Leave Type should not be left blank");
	     document.forms[0].leaveType.focus();
	     document.forms[0].endDurationType.value="";
	     
	     return false;
	   }
	var lvType="";

	if(leaveType==1)
	    lvType="Casual ";
	if(leaveType==2)
	    lvType="Sick ";
	if(leaveType==3)
	    lvType="Privilege";
	if(leaveType==5)
	    lvType="MATERNITY";        
	        
	    
	   
	if(startDate=="")
	   {
	     alert("Please Select Start Date");
	     document.forms[0].startDate.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }
	 if(startDateDuration=="")
	   {
	     alert("Please Select Start Date Duration");
	     document.forms[0].startDateDuration.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }
	   if(endDate=="")
	   {
	     alert("Please Select End Date");
	     document.forms[0].endDate.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }
	    if(startDate!=""&&endDate!=""){
	   
	     var str1 = document.forms[0].startDate.value;
	var str2 = document.forms[0].endDate.value;
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
	    document.forms[0].endDate.value="";
	     document.forms[0].endDate.focus();
	     return false;
	}
	   }
	   if(endDurationType=="")
	   {
	     alert("Please Select End Date Duration");
	     document.forms[0].endDurationType.focus();
	     return false;
	   }
	   
	   if((startDateDuration=="FD"&&endDurationType=="SH"))
	   {
	   
	   alert("Please Select Valid Duration");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].endDurationType.value="";
	      
	     return false;
	   }
	    if((startDateDuration=="FD"&&endDurationType=="SH"))
	   {
	   
	   alert("Please Select Valid Duration");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].endDurationType.value="";
	      
	     return false;
	   }
	   
	   if((startDateDuration=="FH"&&endDurationType=="FD"))
	   {
	   
	   alert("Please Select Valid Duration");
	   document.forms[0].startDurationType.focus();
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].startDurationType.value="";
	      
	     return false;
	   }
	   
	   if((startDateDuration=="FH"&&endDurationType=="SH"))
	   {
	   
	   alert("Please Select Valid Duration");
	   document.forms[0].startDurationType.focus();
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	       document.forms[0].endDurationType.value="";
	      
	     return false;
	   }
	  
	   if(startDate==endDate && startDateDuration!=endDurationType)
	   {
	   alert("Please Select Valid Duration ");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	        document.forms[0].endDurationType.value="";
	     return false;
	   }
	   if(document.forms[0].leaveType.value=="3")
	   {
	   
	   
	   
	   var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
	  
	   if((startDateDuration=="SH" && endDurationType=="FD") ||(startDateDuration=="FD" && endDurationType=="SH")||(startDateDuration=="FD" && endDurationType=="FH"))
	   {
	   totalLeaveDays=totalLeaveDays-1;
	   }

	 var d=document.forms[0].preleavmin.value;
	 
	 var casualBal=document.forms[0].casuallvcloseBal.value;
	 var sickBal=document.forms[0].sicklvcloseBal.value;
	 
	if(sickBal!=0.0 && casualBal!=0.0)
	{
	   if(totalLeaveDays<d){
	     alert("Minimum "+d+" days should be availed for Previlage Leave");
	    document.forms[0].totalLeaveDays.value="";
	    document.forms[0].endDate.value="";
	         return false;
	    }
	   }
	   }
	   if((startDateDuration=="FH"&&endDurationType=="FH")||(startDateDuration=="SH"&&endDurationType=="SH"))
	   {
	    if(startDate!=endDate){
	   alert("Please Select Valid Duration");
	     document.forms[0].endDurationType.focus();
	      document.forms[0].totalLeaveDays.value="";
	        document.forms[0].endDurationType.value="";
	     return false;
	    }
	   
	   }  
	 if(leaveType!="" && startDate!="" && startDateDuration!="" && endDate!="" && endDurationType!="" && leaveType!="5")
	 {
	var xmlhttp;



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
	    document.getElementById("noOfDaysDiv").innerHTML=xmlhttp.responseText;
	    var totalDays=document.forms[0].totalLeaveDays.value;
	    if(leaveType==1)
	    {
	   if(casualMaxDays<totalDays){
	    var mess= "Maximum "+casualMaxDays+" days allow to apply Casual leave";
	    alert(mess);
	    document.forms[0].endDate.value="";
	   document.forms[0].totalLeaveDays.value="";
	    }
	    }
	     if(leaveType==2)
	    {
		    if(sickMaxDays<totalDays){
		    var mess= "Maximum "+sickMaxDays+" days allow to apply Sick leave";
		    alert(mess);
		    document.forms[0].endDate.value="";
		   document.forms[0].totalLeaveDays.value="";
		    }
	    }
	    
	    if(totalDays==0.0){
	      alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
	    document.forms[0].totalLeaveDays.value="";
	     document.forms[0].endDate.value="";
	    }
	    if(totalDays==-1.0){
	    alert("You have applied Onduty");
	    document.forms[0].endDate.value="";
	    document.forms[0].totalLeaveDays.value="";
	    }
	    if(totalDays==-2.0){
	    alert("You have applied Permission");
	    document.forms[0].endDate.value="";
	    document.forms[0].totalLeaveDays.value="";
	    }
	    if(totalDays==-4.0){
	       // alert("You have applied Sick Leave.\nPlease Choose Another Leave Type");
	       alert("Already you have applied Leave On Selection Date.");
	        document.forms[0].endDate.value="";
	        document.forms[0].totalLeaveDays.value="";
	      }
	    /*  
	    if(totalDays==-5.0){
	        alert("You have applied Casual Leave.\nPlease Choose Another Leave Type");
	        document.forms[0].endDate.value="";
	        document.forms[0].totalLeaveDays.value="";
	      }
	      */
	      if(totalDays==-3.0){
	            var leavtype=document.forms[0].leaveType.value;
	        //casual leave condition   
	        if(false)
	        {
	        var advdays=document.forms[0].casleavadv.value;
	      
	        var mess= " Minimum "+advdays+" days before is required to apply casual leave";
	        alert(mess);
	        document.forms[0].endDate.value="";
	      document.forms[0].totalLeaveDays.value="";
	        }
	        
	         if(leavtype==3)
	        {
	        var advdays=document.forms[0].preleavadv.value;
	        var mess= "Minimum "+advdays+" days before is required to apply privilege leave";
	        alert(mess);
	        document.forms[0].endDate.value="";
	       document.forms[0].totalLeaveDays.value="";
	        }
	        
	    }
	    if(totalDays==-6.0){
	    var mess= " Selection date is holiday.Please Check the Dates ";
	        alert(mess);
	        document.forms[0].endDate.value="";
	      document.forms[0].totalLeaveDays.value="";
	    }
	     if(totalDays==-7.0){
	     if(leaveType=="1")
	    	var mess= "Between Two Casual Leaves "+clmindur+" days is required. \n Please Check last Applied Casual Leave.";
	    if(leaveType=="2")
	    	var mess= "Between Two Sick Leaves "+slmindur+" days is required. \n Please Check last Applied Sick Leave. ";
	    if(leaveType=="3")
	    	var mess= "Between Two Privilege Leaves "+plmindur+" days is required. \n Please Check last Applied Privilege Leave. ";
	    if(leaveType=="4")
	    	var mess= "Between Two Loss Of Pay Leaves "+lossmindur+" days is required. \n Please Check last Applied Loss Of Pay Leave. ";			
	        alert(mess);
	        document.forms[0].endDate.value="";
	      document.forms[0].totalLeaveDays.value="";			
	        
	    }
	    
	    
	    }
	  }

	xmlhttp.open("POST","lta.do?method=calculateDays&LeaveType="+leaveType+"&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType,true);
	xmlhttp.send();

	}
	if(leaveType=="5" )
	{
		var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
		
		 if(totalLeaveDays<60){
	     alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
	    document.forms[0].totalLeaveDays.value="";
	    }else if(totalLeaveDays>90){
	    alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
	    document.forms[0].totalLeaveDays.value="";
	    }
	    else{
		document.forms[0].totalLeaveDays.value=totalLeaveDays;
		}
	}
}
function displayFiscalYear(){
	var fiscalYear=document.forms[0].fiscalYear.value;
	if(fiscalYear=="")
		document.forms[0].fiscalYearDesc.value="";
	if(fiscalYear!=""){
		var nextYear=parseInt(fiscalYear);
		nextYear=nextYear+1;
		document.forms[0].fiscalYearDesc.value="April-"+fiscalYear+" to March "+nextYear;
		
		 var url="lta.do?method=newLta&Fiscal="+fiscalYear;
		document.forms[0].action=url;
		document.forms[0].submit();	
	}
}

function addDetails(){
	
	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	/* if(document.forms[0].travelStartDate.value==""){
		alert("Please Select Travel Start Date");
		document.forms[0].travelStartDate.focus();
		return false;
	}
	if(document.forms[0].travelEndDate.value==""){
		alert("Please Select Travel End Date");
		document.forms[0].travelEndDate.focus();
		return false;
	} */
	var noofDays=document.forms[0].totalLeaveDays.value;
	if(noofDays=="0.0"){
		alert("No Of days should not be zero");
		return false;
	}
	
	if(document.forms[0].ltaAmtApplFor.value==""){
		alert("Please Enter LTA Amount Applied For");
		document.forms[0].ltaAmtApplFor.focus();
		return false;
	}

	
	//check same travle date ebtr id validation

	var reqlen=document.getElementsByName("std").length;

	for(var h=0;h<reqlen;h++)
		{
		if(document.getElementsByName("std")[h].value==document.forms[0].startDate.value)
			{
			
			alert("Details already Added for the select leave Date");
			return false;
			}
		}

document.getElementById("ltadetails").style.display="";
	document.getElementById("subbutton").style.display="";
	

   /*  var tstd=document.forms[0].travelStartDate.value;
	var tend=document.forms[0].travelEndDate.value; */
	var ltype=document.forms[0].leaveType.value;
	var std=document.forms[0].startDate.value;
	var stddu=document.forms[0].startDurationType.value;
	var end=document.forms[0].endDate.value;
	var enddu=document.forms[0].endDurationType.value;
	var tota=document.forms[0].totalLeaveDays.value;
	var amount=document.forms[0].ltaAmtApplFor.value;
	
	var table = document.getElementById('ltadetails');
	var row = table.insertRow(1);
   /*  var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1); */
    var cell3 = row.insertCell(0);
    var cell4 = row.insertCell(1);
    var cell5 = row.insertCell(2); 
    var cell6 = row.insertCell(3);
    var cell7 = row.insertCell(4);
    var cell8 = row.insertCell(5);
     var cell9 = row.insertCell(6);
    var cell10 = row.insertCell(7); 

	/* cell1.innerHTML=tstd; */
    
   /*  var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=tstd;
    element1.name="tstd";
    cell1.appendChild(element1);
    
    
    
    	cell2.innerHTML=tend;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=tend;
    element1.name="tend";
    cell2.appendChild(element1); */
    
    
    	cell3.innerHTML=ltype;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=ltype;
    element1.name="ltype";
    cell3.appendChild(element1);
    
    
    
    	cell4.innerHTML=std;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=std;
    element1.name="std";
    cell4.appendChild(element1);
    
    
    
	cell5.innerHTML=stddu;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=stddu;
    element1.name="stddu";
    cell5.appendChild(element1);
    
    
    
	cell6.innerHTML=end;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=end;
    element1.name="end";
    cell6.appendChild(element1);
    
    
    
	cell7.innerHTML=enddu;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=enddu;
    element1.name="enddu";
    cell7.appendChild(element1);
    
    
	cell8.innerHTML=tota;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=tota;
    element1.name="tota";
    cell8.appendChild(element1);
    
	cell9.innerHTML=amount;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=amount;
    element1.name="amount";
    cell9.appendChild(element1);
    
    
	
    
    cell10.innerHTML="<img src='images/delete.png'   onclick='deleteRow(this,1)' title='Remove Row'/>";
    
    


	
}
function hideMessage(){
	document.getElementById("messageID").style.visibility="hidden";	
}
function submitForApproval(){
	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	document.forms[0].action="lta.do?method=submitForApproval";
	document.forms[0].submit();
}
function CancelReq(){
	
	var agree = confirm('Are You Sure To Cancel Request');
	if(agree)
	{
		document.forms[0].action="lta.do?method=CancelReq";
		document.forms[0].submit();
	}
}
function deleteRent()
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
	document.forms[0].action="lta.do?method=deleteLTADetails&cValues="+checkvalues;
document.forms[0].submit();
}
}
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function deleteRow(element,value)
{
	 var table="";
	
	
		 table = document.getElementById('ltadetails');
		
	table.deleteRow(element.parentNode.parentNode.rowIndex);
	
	
    }
    
    
    function statusMessage(message){
alert(message);
}
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
<body onload="self.scrollTo(0,0)">
			<div align="center" id="messageID" style="visibility: true;">
				
				<logic:present name="ltaForm" property="message">
						<script language="javascript">
					statusMessage('<bean:write name="ltaForm" property="message" />');
					</script>
					
				</logic:present>
			</div>
<html:form action="/lta.do" enctype="multipart/form-data" >
<table class="bordered"   >

<tr><th colspan="8"><big>Requester Details</big></th></tr>
						<tr><td>Employee Number</td><td><bean:write name="ltaForm" property="employeeNo" /></td><td>Employee Name</td><td><bean:write name="ltaForm" property="employeeName" /></td>
						<td>Department</td><td><bean:write name="ltaForm" property="department" /></td><td>Designation</td><td><bean:write name="ltaForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan=""><bean:write name="ltaForm" property="doj" /></td><td>Location</td><td colspan=""><bean:write name="ltaForm" property="location" /></td>

<td>Staff Category</td><td><bean:write name="ltaForm" property="staffCategory" /></td><th>Fiscal Year <font color="red" size="2" >*</font></th>
<td><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="ltaForm"  property="yearList"></html:options>
</html:select>
<html:text property="fiscalYearDesc"  style="border:0;width:160px;" readonly="true" />
</td></tr>

</table>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6"><big>Approvers Details</big></th></tr>
	<tr><th><center>Priority</center></th><th><center>Approver Name</center></th><th><center>Department</center></th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.approverName }</td><td>${abc.apprDept }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
<br/>
<html:hidden property="requestNo"/>
<table class="bordered">
<tr><th colspan="4"><center><b><big>LTA Details</big></b></center></th></tr>


<tr>
		<td >Approved Leaves <font color="red" size="4">*</font></td>
			<td  colspan="4">
				<html:select name="ltaForm" property="leaveDetails" styleClass="content" onchange="checkLeaveCal()">
					<html:option value="">--Select--</html:option>
					<logic:notEmpty name="li"><logic:iterate name="li" id="l">
							<html:option value="${l.startDate};${l.startDurationType};${l.endDate};${l.endDurationType};${l.noOfDays};${l.leaveType}">Start Date:${l.startDate} - ${l.startDurationType}  ; End Date : ${l.endDate} - ${l.endDurationType} ; No of Days:${l.noOfDays}</html:option>
						</logic:iterate></logic:notEmpty>
				</html:select>
				
			</td>
		</tr>
		<%-- <tr><td>Travel Start Date <font color="red" size="4">*</font></td><td><html:text property="travelStartDate" onmouseover="datedisplay()" styleId="popupDatepicker" readonly="true" onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
<td>Travel End Date <font color="red" size="4">*</font></td><td><html:text property="travelEndDate" onmouseover="datedisplay1()" styleId="popupDatepicker2"  readonly="true" onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
</tr> --%>
		<tr><td>Leave Type </td><td colspan="3"><html:text property="leaveType" styleId="leaveType" readonly="true" style="background: rgb(179,175,174)"/></td></tr>
		<tr>
			<td >Start Date <font color="red" size="4">*</font></td>
			<td >
				<html:text property="startDate" styleId="startDate" readonly="true" style="background: rgb(179,175,174)"/>
			</td>
			<td >Duration <font color="red" size="4">*</font></td>
			<td >
			<html:text property="startDurationType" styleId="startDurationType"  readonly="true" style="background: rgb(179,175,174)"/>
				
			</td>
		</tr>
		<tr>
			<td >End Date <font color="red" size="4">*</font></td>
			<td ><html:text property="endDate" styleId="endDate" readonly="true" style="background: rgb(179,175,174)"/>
				
			</td>
			<td >Duration <font color="red" size="4">*</font></td>
			<td >
			<html:text property="endDurationType" styleId="endDurationType"  readonly="true" style="background: rgb(179,175,174)"/>
				
			</td>
		</tr>
		
		<tr>
			<td >No of Days <font color="red" size="4">*</font></td>
			
			<td colspan="3">
				<html:text property="totalLeaveDays" styleId="totalLeaveDays" readonly="true" style="background: rgb(179,175,174)"/>
		
			</td>
	 </tr>
	 <tr><td>LTA Amount Applied For <font color="red" size="4">*</font></td><td><html:text property="ltaAmtApplFor" onkeyup="this.value = this.value.replace(/'/g,'`')" onkeypress="return isNumber(event)"  style="text-align:right;"/></td>
	 <td>LTA Amount Approved </td><td><html:text property="ltaAmtAprvdFor" onkeyup="this.value = this.value.replace(/'/g,'`')" onkeypress="return isNumber(event)" disabled="true" style="background: rgb(179,175,174)"/></td>
	 </tr>
	<tr><td colspan="4"><center><html:button property="method" value="Add Details" styleClass="rounded" onclick="addDetails()"/></center></td></tr>		
</table>

 <br/>
 
        
	
	<table class="bordered" id="ltadetails" style="display: none;">
	<tr><th>Leave Type</th><th>Leave Start Date</th><th>Start Duration</th><th>Leave End Date</th><th> End Duration</th><th>No Of Days</th><th>LTA Amount Applied For</th><th>Delete</th>
	</tr>
	
	
	</table>
<br/>
  <iframe src="lta.do?method=showUploadFields" name="contentPage1" width="105%" 
        			frameborder="1" scrolling="yes" id="the_iframe">
        </iframe>
             
       

<br/>
 <table id="subbutton" style="display: none;"><tr><td colspan="6"><center><html:button property="method" value="Submit For Approval" onclick="submitForApproval()" styleClass="rounded"/> &nbsp; <%-- <html:button property="method" value="Cancel Request" onclick="CancelReq()" styleClass="rounded"/> --%> </center></td></tr></table>
 
 <div>&nbsp;</div>


	
	
	

</html:form>
</body>
</html>