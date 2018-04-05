<%@ page language="java" import="java.util.*,java.text.*"%>
<jsp:directive.page import="java.sql.ResultSet" />
<%!public int nullIntconv(String inv) {
		int conv = 0;
		
		try {
			conv = Integer.parseInt(inv);
		} catch (Exception e) {
		e.printStackTrace();
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
	
	cal = new GregorianCalendar(iYear, iMonth, days);
	int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
	int ryear = iYear;
	int rmonth = iMonth;
	System.out.println("YEAR=" + ryear);
	System.out.println("MONTH=" + rmonth);
	
	System.out.println("weekStartDay=" + weekStartDay);
	System.out.println("days=" + days);
	System.out.println("iTotalweeks=" + iTotalweeks);
%>
<html>
	<head>
		<title>How to create Calendar in JSP</title>
		
		<link rel='stylesheet' href='js/popbox.css' type='text/css'
			media='screen' charset='utf-8'>
		<script type='text/javascript' charset='utf-8'
			src='js/jquery.release.js'></script>
		<script type='text/javascript' charset='utf-8' src='js/popbox.js'></script>
		<style type='text/css' media='screen'>
body {
	text-align: center;
	background: #f7f7f7;
	font-family: georgia;
	font-size: 22px;
	text-shadow: 1px 1px 1px #FFF;
	margin: 200px;
}

a {
	color: #999;
	text-decoration: none;
}

a:hover {
	color: #ff2400;
}

a.active {
	color: #ff2400;
}

label {
	display: block;
}

form {
	margin: 25px;
	text-align: left
}

form input[type=text] {
	padding: 5px;
	width: 90%;
	border: solid 1px #CCC;
}

form textarea {
	padding: 5px;
	width: 90%;
	border: solid 1px #CCC;
	height: 100px;
}

.box {
	width: 250px;
}

footer {
	font-size: 12px;
}

table#calendarcontainer tr td.calendarcell {
	font: normal 11px/ 16px Verdana, Helvetica, sans-serif !important;
	padding-right: 2px;
	background: #fff;
	color: #f6f6f6 !important;
	border-right: 1px solid #cfcfcf;
	border-left: none;
	border-bottom: none;
}

form a,footer a {
	color: #40738d;
}

.floatRight {
	float: right;
}

.hide {
	display: none;
}
</style>
  <script type='text/javascript' charset='utf-8'>
    $(document).ready(function(){
      $('.popbox').popbox();
    });
  </script>



		<script>
function nextMonth(){
var cYear=document.frm.iYear.value;
var cMonth=document.frm.iMonth.value;
var cMonth="December";
document.getElementById('iMonth').value=cMonth;
alert("cYear="+cYear);

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
var url="calender.do?method=nextMonth1";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousMonth(){

var url="calender.do?method=prviousMonth";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function goTo()
{

  document.frm.submit()
}
</script>
		<style>
body {
	font-family: Verdana, Arial, Helvetica, sans-serif
}

.dsb {
	background-color: #EEEEEE
}
</style>
	</head>

	<body>


		<table width=100%>
			<tr>
				<td colspan="2" style="width: 20%; vertical-align: top;"><jsp:include
						page="../template/header.jsp" /></td>
			</tr>
			<tr>
				<td style="width: 20%; vertical-align: top;">
					<jsp:include page="../template/mainMenu.jsp">
						<jsp:param value="Master" name="module" />
						<jsp:param value="AdmissionType" name="subModule" />
					</jsp:include>
				</td>


				<td style="vertical-align: top;" align="center">
					<div align="center">

					</div>
					<form name="frm" method="post">



						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="25%">
									&nbsp;
								</td>
								<td width="45%">
									&nbsp;
								</td>
								<td width="30%">
									&nbsp;
								</td>
							</tr>


							<tr>
								<td width="6%">
									Year
								</td>
								<td width="7%">
									<select name="iYear" onChange="goTo()">
										<%
											// start year and end year in combo box to change year in calendar
											int iy = 0;
											for (iy = iTYear - 70; iy <= iTYear + 70; iy++) {
												if (iy == iYear) {
										%>
										<option value="<%=iy%>" selected="selected"><%=iy%></option>
										<%
										} else {
										%>
										<option value="<%=iy%>"><%=iy%></option>
										<%
											}
											}
										%>
									</select>
								</td>

								<td width="73%" align="center" style="color: #4E96DE">
									<h3><%=new SimpleDateFormat("MMMM").format(new Date(2008,
							iMonth, 01))%>
										<%=iYear%></h3>
								</td>
								<td width="6%">
									Month
								</td>
								<td width="8%">
									<select name="iMonth" onChange="goTo()">
										<%
											// print month in combo box to change month in calendar
											int im = 0;
											for (im = 0; im <= 11; im++) {
												if (im == iMonth) {
										%>
										<option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(
									2008, im, 01))%></option>
										<%
										} else {
										%>
										<option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(
									2008, im, 01))%></option>
										<%
											}
											}
										%>
									</select>
								</td>
								<td width="73%" align="center" style="color: #4E96DE">
									<h3>
										<input type="hidden" name="hYear" value="<%=ryear%>">
										<input type="hidden" name="hMonth" value="<%=rmonth%>">


										<input type="submit" value="Previous"
											onclick="previousMonth()" />


										&nbsp&nbsp
										<input type="submit" value="Next" onclick="nextMonth()" />

									</h3>
								</td>
							</tr>
						</table>


						<table align="center" border="1" cellpadding="3" cellspacing="0"
							width="100%" >
							<tbody>
								<tr>
									<td bgcolor="4F79B8">
										<font color="white">Sun</font>
									</td>
									<td bgcolor="4F79B8">
										<font color="white">Mon</font>
									</td>
									<td bgcolor="4F79B8">
										<font color="white">Tue</font>
									</td>
									<td bgcolor="4F79B8">
										<font color="white">Wed</font>
									</td>
									<td bgcolor="4F79B8">
										<font color="white">Thu</font>
									</td>
									<td bgcolor="4F79B8">
										<font color="white">Fri</font>
									</td>
									<td bgcolor="4F79B8">
										<font color="white">Sat</font>
									</td>
								</tr>
								<%
									int cnt = 1;
									for (int i = 1; i <= iTotalweeks; i++) {
								%>
								<tr>
									<%
												for (int j = 1; j <= 7; j++) {
												if (cnt < weekStartDay || (cnt - weekStartDay + 1) > days) {
									%>
									<td align="center" height="100">
										&nbsp;
									</td>
									<%
											} else {
											com.microlabs.newsandmedia.dao.NewsandMediaDao ad = new com.microlabs.newsandmedia.dao.NewsandMediaDao();
											String rDate = ryear + "-" + (rmonth+1) + "-"
													+ (cnt - weekStartDay + 1);
											
											String holiday_type = "Government Holidays";
											
											String sql = "SELECT * FROM holidays where Holiday_Date='"+rDate+"' ";
											java.sql.ResultSet rs = ad.selectQuery(sql);
											
											if (rs.next()) {
											
												String holid_type = rs.getString("Holiday_Type");
											
												if (holid_type.equalsIgnoreCase("Government Holidays")) {
											
									%>
									<td valign="top" height="100" class="calendarcell currentDate"
										day="4" id="M10D7" bgcolor="blue"
										onmouseover="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='show';this.bgColor='lightblue';"
										onmouseout="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='hide';this.bgColor='white';">
										
										<font color="white"><b><%=(cnt - weekStartDay + 1)%></b></font>
										<span>
											<table border="0" cellpadding="0"  class="floatRight"
												cellspacing="0">
												<tbody>
									<tr>
										<td class="hide" id="A<%=(cnt - weekStartDay + 1)%>">
											<span>
												<div class='popbox'>
													<a class='open' href='#' onMouseOver="bigImg(this)"
														onmouseout="normalImg(this)"
														id="A<%=(cnt - weekStartDay + 1)%>"> <img
															src='images/new leave.JPG'
															style='width: 14px; position: relative;'> </a>

													<div class='collapse'>
														<div class='box'>
															<div class='arrow'></div>
															<div class='arrow-border'></div>

												<form
													action="http://gristmill.createsend.com/t/j/s/nklki/"
													method="post" id="subForm">
													<b>Technician</b> Demo
													<br>
													<b>Leave Type</b>

													<select name="">
														<option value="Casual Leave">
															Casual Leave
														</option>
														<option value="Sick Leave">
															Sick Leave
														</option>
														<option value="Comp off">
															Comp off
														</option>
														<option value="On Duty">
															On Duty
														</option>
													</select>
													</br>
													From
													<input type="text" value="">
													<br>
													To
													<input type="text" value="">
													<br>


													<input type="submit" value="SAVE" />
													<a href="#" class="close">Cancel</a>
												</form>

														</div>
													</div>
												</div> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/ReminderDisplay.do?from=Calendar&selDate=1352091600256", "position=relative,width=550,height=250")'
												title='Add Reminder'><img
														src='images/Add remainder.JPG' vspace='2' border='0'>
											</a> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/tasks/CUDTask.jsp?MODE=New&FROM=Calendar&selDate=1352091600256", "position=relative,width=850,height=400")'
												title='Add Task'><img src='images/new task.JPG'
														vspace='2' border='0'> </a>
										</td>
									</tr>
								</tbody>
							</table> <br>
							<span>
										
									</td>
									<%
												}
												if (holid_type.equalsIgnoreCase("Regional Holidays")) {
											
												
									%>
										<td valign="top" height="100" class="calendarcell currentDate"
										day="4" id="M10D7" bgcolor="green"
										onmouseover="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='show';this.bgColor='lightblue';"
										onmouseout="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='hide';this.bgColor='white';">
										
										<font color="white"><b><%=(cnt - weekStartDay + 1)%></b>
										</font>
										
										<span>
											<table border="0" cellpadding="0"  class="floatRight"
												cellspacing="0">
												<tbody>
									<tr>
										<td class="hide" id="A<%=(cnt - weekStartDay + 1)%>">
											<span>
												<div class='popbox'>
													<a class='open' href='#' onMouseOver="bigImg(this)"
														onmouseout="normalImg(this)"
														id="A<%=(cnt - weekStartDay + 1)%>"> <img
															src='images/new leave.JPG'
															style='width: 14px; position: relative;'> </a>

													<div class='collapse'>
														<div class='box'>
															<div class='arrow'></div>
															<div class='arrow-border'></div>

												<form
													action="http://gristmill.createsend.com/t/j/s/nklki/"
													method="post" id="subForm">
													<b>Technician</b> Demo
													<br>
													<b>Leave Type</b>

													<select name="">
														<option value="Casual Leave">
															Casual Leave
														</option>
														<option value="Sick Leave">
															Sick Leave
														</option>
														<option value="Comp off">
															Comp off
														</option>
														<option value="On Duty">
															On Duty
														</option>
													</select>
													</br>
													From
													<input type="text" value="">
													<br>
													To
													<input type="text" value="">
													<br>


													<input type="submit" value="SAVE" />
													<a href="#" class="close">Cancel</a>
												</form>

														</div>
													</div>
												</div> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/ReminderDisplay.do?from=Calendar&selDate=1352091600256", "position=relative,width=550,height=250")'
												title='Add Reminder'><img
														src='images/Add remainder.JPG' vspace='2' border='0'>
											</a> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/tasks/CUDTask.jsp?MODE=New&FROM=Calendar&selDate=1352091600256", "position=relative,width=850,height=400")'
												title='Add Task'><img src='images/new task.JPG'
														vspace='2' border='0'> </a>
										</td>
									</tr>
								</tbody>
							</table> <br>
							<span>
									
									</td>
									<%
											}
											if (holid_type.equalsIgnoreCase("PlantWise Holidays")) {
											
												
									%>
										<td valign="top" height="100" class="calendarcell currentDate"
										day="4" id="M10D7" bgcolor="green"
										onmouseover="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='show';this.bgColor='lightblue';"
										onmouseout="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='hide';this.bgColor='white';">
										
										<font color="white"><b><%=(cnt - weekStartDay + 1)%></b>
										</font>
										
										<span>
											<table border="0" cellpadding="0"  class="floatRight"
												cellspacing="0">
												<tbody>
									<tr>
										<td class="hide" id="A<%=(cnt - weekStartDay + 1)%>">
											<span>
												<div class='popbox'>
													<a class='open' href='#' onMouseOver="bigImg(this)"
														onmouseout="normalImg(this)"
														id="A<%=(cnt - weekStartDay + 1)%>"> <img
															src='images/new leave.JPG'
															style='width: 14px; position: relative;'> </a>

													<div class='collapse'>
														<div class='box'>
															<div class='arrow'></div>
															<div class='arrow-border'></div>

												<form
													action="http://gristmill.createsend.com/t/j/s/nklki/"
													method="post" id="subForm">
													<b>Technician</b> Demo
													<br>
													<b>Leave Type</b>

													<select name="">
														<option value="Casual Leave">
															Casual Leave
														</option>
														<option value="Sick Leave">
															Sick Leave
														</option>
														<option value="Comp off">
															Comp off
														</option>
														<option value="On Duty">
															On Duty
														</option>
													</select>
													</br>
													From
													<input type="text" value="">
													<br>
													To
													<input type="text" value="">
													<br>


													<input type="submit" value="SAVE" />
													<a href="#" class="close">Cancel</a>
												</form>

														</div>
													</div>
												</div> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/ReminderDisplay.do?from=Calendar&selDate=1352091600256", "position=relative,width=550,height=250")'
												title='Add Reminder'><img
														src='images/Add remainder.JPG' vspace='2' border='0'>
											</a> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/tasks/CUDTask.jsp?MODE=New&FROM=Calendar&selDate=1352091600256", "position=relative,width=850,height=400")'
												title='Add Task'><img src='images/new task.JPG'
														vspace='2' border='0'> </a>
										</td>
									</tr>
								</tbody>
							</table> <br>
							<span>
									
									</td>
									<%
											}
											} else {
									%>
									<td valign="top" height="100" class="calendarcell currentDate"
										day="4" id="M10D7"
										onmouseover="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='show';this.bgColor='lightblue';"
										onmouseout="document.getElementById('A<%=(cnt - weekStartDay + 1)%>').className='hide';this.bgColor='white';">
										<span>
											<table border="0" cellpadding="0"  class="floatRight"
												cellspacing="0">
												<tbody>
									<tr>
										<td class="hide" id="A<%=(cnt - weekStartDay + 1)%>">
											<span>
												<div class='popbox'>
													<a class='open' href='#' onMouseOver="bigImg(this)"
														onmouseout="normalImg(this)"
														id="A<%=(cnt - weekStartDay + 1)%>"> <img
															src='images/new leave.JPG'
															style='width: 14px; position: relative;'> </a>

													<div class='collapse'>
														<div class='box'>
															<div class='arrow'></div>
															<div class='arrow-border'></div>

												<form
													action="http://gristmill.createsend.com/t/j/s/nklki/"
													method="post" id="subForm">
													<b>Technician</b> Demo
													<br>
													<b>Leave Type</b>

													<select name="">
														<option value="Casual Leave">
															Casual Leave
														</option>
														<option value="Sick Leave">
															Sick Leave
														</option>
														<option value="Comp off">
															Comp off
														</option>
														<option value="On Duty">
															On Duty
														</option>
													</select>
													</br>
													From
													<input type="text" value="">
													<br>
													To
													<input type="text" value="">
													<br>


													<input type="submit" value="SAVE" />
													<a href="#" class="close">Cancel</a>
												</form>

														</div>
													</div>
												</div> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/ReminderDisplay.do?from=Calendar&selDate=1352091600256", "position=relative,width=550,height=250")'
												title='Add Reminder'><img
														src='images/Add remainder.JPG' vspace='2' border='0'>
											</a> <br> <a href='javascript:void(0)'
												onClick='showURLInDialog("/tasks/CUDTask.jsp?MODE=New&FROM=Calendar&selDate=1352091600256", "position=relative,width=850,height=400")'
												title='Add Task'><img src='images/new task.JPG'
														vspace='2' border='0'> </a>
										</td>
									</tr>
								</tbody>
							</table> <br>
							<span> <%=(cnt - weekStartDay + 1)%></span>
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
							</tbody>
						</table>
						<table align="center">
							<tr>
								<td>
									<img src="images/blue box.JPG">
									&nbsp&nbspGovernmanent Holidays
								</td>
								<td>
									<img src="images/green box.JPG">
									&nbsp&nbspRegional Holidays
								</td>
							</tr>

						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>