<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>jQuery UI Accordion - Default functionality</title>
<link rel="stylesheet" href="css/jquery-ui.css">

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script src="js/accordion-1.10.2.js"></script>
<script src="js/accordion-1.11.4.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
$(function() {
$( "#accordion" ).accordion({
heightStyle: "content"
});
});

function viewshow(text) 
{
var a=text;


    document.getElementById('note').scrollIntoView();    
    document.getElementById(a).src="images/tdown.png";
    if(a=="cl")
    {  
    document.getElementById('sl').src="images/tright.png";
    document.getElementById('el').src="images/tright.png";
    document.getElementById('ml').src="images/tright.png";
    document.getElementById('lwp').src="images/tright.png";
    }
     if(a=="sl")
    {  
    document.getElementById('cl').src="images/tright.png";
    document.getElementById('el').src="images/tright.png";
    document.getElementById('ml').src="images/tright.png";
    document.getElementById('lwp').src="images/tright.png";
    }
     if(a=="el")
    {  
    document.getElementById('sl').src="images/tright.png";
    document.getElementById('cl').src="images/tright.png";
    document.getElementById('ml').src="images/tright.png";
    document.getElementById('lwp').src="images/tright.png";
    }
     if(a=="ml")
    {  
    document.getElementById('sl').src="images/tright.png";
    document.getElementById('cl').src="images/tright.png";
    document.getElementById('el').src="images/tright.png";
    document.getElementById('lwp').src="images/tright.png";
    }
    if(a=="lwp")
    {  
    document.getElementById('sl').src="images/tright.png";
    document.getElementById('cl').src="images/tright.png";
    document.getElementById('el').src="images/tright.png";
    document.getElementById('ml').src="images/tright.png";
    }
}

</script>



    <script language="javascript">
    document.onmousedown=disableclick;
    status="Right Click Disabled";
    function disableclick(event)
    {
      if(event.button==2)
       {
         
         return false;    
       }
    }
    
    function disableCtrlKeyCombination(e)
{




//list all CTRL + key combinations you want to disable
var forbiddenKeys = new Array('a', 'n', 'c', 'x', 'v', 'j' , 'w','p');
var key;
var isCtrl;
if(window.event)
{
key = window.event.keyCode;     //IE
if(window.event.ctrlKey)
isCtrl = true;
else
isCtrl = false;
}
else
{
key = e.which;     //firefox
if(e.ctrlKey)
isCtrl = true;
else
isCtrl = false;
}
//if ctrl is pressed check if other key is in forbidenKeys array
if(isCtrl)
{
for(i=0; i<forbiddenKeys.length; i++)
{
//case-insensitive comparation
if(forbiddenKeys[i].toLowerCase() == String.fromCharCode(key).toLowerCase())
{

return false;
}
}
}
return true;
}



  function clearData(){
        window.clipboardData.setData('text','') ;
    }
    function cldata(){
        if(clipboardData){
            clipboardData.clearData();
        }
    }
    setInterval("cldata();", 1000);



    
    </script>


</head>


<body >
<font face="Arial">
<div id="note"><p>Note:Click the tab to open/close.</p></div>

<div style="width: 95%"><table class="bordered"><tr><th ><center><big><b>Leave  Policy</b></big></center></th></tr></table></div>

<div id="accordion" class="bordered" style="width: 95%">
<h3  onclick="viewshow('cl')" ><img src="images/tright.png" width="10" height="10" align="absmiddle" id="cl"/>&nbsp;<b>Casual Leave</b></h3>
<div>

<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Purpose</b></big></p>
<p>The purpose of the Casual Leave is to grant days off from work to an employee for any personal reason. The employee may avail of casual leave at a short notice and for a short term absence.</p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Eligibility</b></big></p>
<p>This policy is applicable to:<br>
         &nbsp;&nbsp;-All Micro Labs employees including Probationers.  </p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Provisions</b></big></p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>1.Entitlement</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b> An employee is entitled to 6 calendar days of paid Casual Leave (CL) every calendar year. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b> Annual entitlement of Casual Leave will be credited in the beginning of each calendar year. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.</b> Un-availed Casual Leave in a calendar  year cannot be carried forward to the next calendar year and will not be encashed (i.e. it will lapse at the end of calendar year)  </p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>2.Availing the Benefit</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b> Employees may apply for leave any number of times during the year, subject to availability of balance.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b> Leave may be applied for a minimum of half a day and for a maximum of 3 consecutive days in each request. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.</b> Employees can avail maximum of 3 days in the first half of the year i.e. January to June. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.</b> The leave cannot be prefixed and/or suffixed with other leave types. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>e.</b> Leave may be applied after availing the same; however it must be applied within 6 working days of reporting back from leave. If not applied within this period, the reporting authorities may use their discretion to convert this to Loss of Pay. </p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>3.New Joinee</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b>The employee may avail of Casual Leave if he/she has sufficient balance. <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b>Casual leaves for employees who join during the calendar year will be credited after completing 6 months and their entitlement will be pro-rated based from date of Joining.</p>
</div>


<h3  onclick="viewshow('sl')"><img src="images/tright.png" width="10" height="10" align="absmiddle" id="sl"/><b>&nbsp;Sick Leave</b></h3>
<div>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Purpose</b></big></p>
<p> The purpose of the Sick Leave is to grant days off from work to an employee in the event of his/her illness or injury for medical treatment and/or recuperation.</p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Eligibility</b></big></p>
<p>This policy is applicable to:<br>
         &nbsp;&nbsp;-All Micro Labs employees including Trainees and Probationers.</p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Provisions</b></big></p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>1.Entitlement</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b>An employee is entitled to 6 days of paid Sick Leave (SL) every calendar year. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b> Annual entitlement of Sick Leave will be credited in the beginning of each calendar year. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.</b>Unutilised leave may carry forward to the next calendar year and the employee may accumulate up to a maximum of 12 days, including the current year's eligibility.  Number of days in excess of 12 is treated as "surplus" and will lapse without any encashment.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.</b>Sick leave must be availed only for an employee's own medical reasons. </p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>2.Availing the Benefit</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b> Employees may apply for leave multiple times during the year, subject to availability of balance. However, such applications must not violate the principle and purpose for which sick leave is provided. Sick leave can be prefixed or suffixed to weekends / holidays with prior sanction, but not both. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b> There is no provision for an employee to apply for Advance Sick Leave. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.</b> Leave may be applied for a minimum of half a day. There is no limit on the maximum number of days, subject to availability of balance.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.</b>  The leave cannot be prefixed and/or suffixed with other leave types.  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>e.</b> Leave may be applied after availing the s  Leave may be applied after availing the same; however it must be applied within 6 working days of reporting back from leave. If not applied within this period, the reporting authorities may use their discretion to convert this to Loss of Pay. </p>
<p>In case the leave duration is more than 2 days, a Medical Certificate should be submitted. A Medical Certificate from an authorised medical practitioner should be submitted to the HR substantiating the reason for absence. </p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>3.New Joinee</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b>Sick Leave for employees who join during the calendar year will be credited after completion of one month service and their entitlement will be pro-rated based on date of Joining.<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b> The employee may avail of Sick Leave if he/she has sufficient balance.</p>

 
</div>
<h3  onclick="viewshow('el')"><img src="images/tright.png" width="10" height="10" align="absmiddle" id="sl"/><b>&nbsp;Privilege Leave</b></h3>
<div>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Purpose</b></big></p>
<p> The purpose of the Privilege Leave is to grant the employee with a fixed number of days off from work, as a benefit which he/she has earned on account of working for a specific number of days in a particular period. The Privilege Leave may be availed for any personal reason, but it should be planned sufficiently in advance.</p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Eligibility</b></big></p>
<p>This policy is applicable to:<br>
         &nbsp;&nbsp;-All Micro Labs confirmed employees.</p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Provisions</b></big></p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>1.Entitlement</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b>An employee is entitled to 15 working days of Privilege Leave (PL) every year.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b>   The year for the purpose of Leave Accounting is January to December.  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.</b>Annual entitlement of Privilege Leave will be credited in the beginning of each calendar year. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.</b> Unutilised leave may be carried forward to the next calendar year up to a maximum of 45 days.  Number of days in excess of 45, accumulated at the beginning of the calendar year is treated as "surplus" and will lapse without any encashment.   <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.</b> If the employee applies for Privilege Leave, but does not avail of the same due to any reason, he/she may choose to cancel the same. </p>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>2.Availing the Benefit</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b> Employees may apply for Leave for a minimum of 1 day and maximum of TEN occasions in a calendar year, subject to availability of leave balance.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b> The leave cannot be prefixed and/or suffixed with other leave types. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.</b> The leave days in a request cannot span across calendar year.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.</b>  Privilege Leave should be planned well in advance. It should be applied at least 10 days in advance.  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>e.</b>  While availing privilege leave, the intervening weekly offs and paid holidays will not be counted. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>f.</b>  During the resignation notice period, employees are not entitled to avail privilege leave.   <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>g.</b> In the event of Separation, the accumulated balance privilege leave will be encashed and paid to the employee along with the full and final settlement dues.  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>h.</b>  The employee may claim tax benefit under the Leave Travel Assistance (LTA) policy in case if he/she applies for Privilege Leave of 3 or more consecutive days. The employee will require submitting the supporting documents (air tickets, train tickets etc.) in order to avail of LTA  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>i.</b>   An employee proceeding on leave exceeding 3 days must hand over the responsibilities to his/her immediate senior / junior employee. </p>
 
</div>
<h3 onclick="viewshow('ml')"><img src="images/tright.png" width="10" height="10" align="absmiddle" id="ml"/>&nbsp;<b>Maternity Leave</b></h3>
<div>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Purpose</b></big></p>
<p> The purpose of the Maternity Leave policy is to grant days off from work to women employees for safe delivery and nurturing of their new born child. </p>

<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Availing the Benefit</b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.</b> Women employees shall be entitled to Maternity Leave for Twenty Six weeks as per the new ammendment of Maternity Benefit Act, 1961.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.</b> Women employee will submit leave application to her superior well in advance. The Leave application will state the expected date of confinement, the date from which  Maternity leave is required and the period of leave supported with Medical Certificate.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.</b> At the time of resumption of work the employee will submit a certificate from a Qualified medical practitioner/Nursing Home /Hospital, specifying the date on which she delivered a baby.       <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.</b>  Maternity Leave beyond the normal entitlement - Maternity leave will not be Granted for period in excess of what is prescribed under the Maternity Benefit.</p>
</div>
<h3 onclick="viewshow('lwp')"><img src="images/tright.png" width="10" height="10" align="absmiddle" id="lwp"/>&nbsp;<b>Leave Without Pay</b> </h3>
<div>
<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Purpose</b></big></p>
<p> The purpose of the LWP policy is as follow: <br>
  &nbsp;&nbsp;-To extend support to employees during personal emergencies. </p>

<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Eligibility </b></big></p>
<p>This policy is applicable to:<br>
        &nbsp;&nbsp;-  All Micro Labs employees including Probationers</p>
		<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b>Provisions </b></big></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.&nbsp; </b>An employee may avail LWP for the following reasons : <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. &nbsp;  Illness of Self<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. &nbsp;  Marriage of Self<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. &nbsp; Hospitalisation of self or dependants <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.&nbsp; </b>  An employee may apply for LWP on very few occasions during his or her tenure in Micro Labs unless specifically limited in this policy based on the reasons mentioned herein or situation beyond the control of employee<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>c.&nbsp; </b>All LWP requests are subject to approvals from the Reporting Authority and HR Head, jointly. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>d.&nbsp; </b> Micro Labs reserves the right to initiate disciplinary action in case the employee does not report back by the approved LWP end date.</p>
		<p style="font-size: 12pt; color: rgb(0, 112, 192);"><big><b> Impact on Compensation and Benefits during LWP  </b></big></p>
   
   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>a.&nbsp;</b>The employee is not eligible for any component of Compensation and Benefits for the duration of LWP.  <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>b.&nbsp;</b>  Employees will not be entitled towards retiral benefits such as Payment of Bonus, Provident Fund, Payment of Gratuity etc. during the period of LWP.<br>

   </p>

</div>


</div>

</font>
</body>
</html>