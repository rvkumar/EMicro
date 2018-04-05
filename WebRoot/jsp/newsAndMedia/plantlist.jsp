<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Semi Finished Creation</title>

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

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript">

function changeStatus(elem){
  
	var elemValue = elem.value;
	if(elem=="Reject")
	{
	if(document.forms[0].comments.value!=""){
	  alert("Please Add Some Comments");
	  }
	
	}
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
	var matGroup=document.forms[0].reqMaterialGroup.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back(-2);
  }
  
 function getCurrentRecord(){
	var url="materialCode.do?method=curentRecord";
	document.forms[0].action=url;
	document.forms[0].submit();


} 	  
</script>


</head>
  
  <body>
  <font face="Arial" >
<table class="bordered" >
<tr>
<th colspan="9"><center>Plant Information</center></th></tr>
<tr><th><b>Sl.No.</b></th><th><b>Plant&nbsp;Code</b></th><th><b>Unit Head</b></th><th><b>Address</b></th><th><b>City</b></th><th><b>State</b></th><th><b>Postal</b></th><th><b>Telephone</b></th><th><b>E-mail</b></th><tr>
<tr><td>1</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML01 HOSUR" style="text-decoration: none;color: black;"><b><u>ML0l & ML02</u></b></a></td><td>Mr.M.V. Ramna Murthy </br>(VP Operations)&nbsp;</td><td>No.92, Sipcot Industrial Area</td><td>Hosur </td><td>Tamilnadu</td><td>635126</td><td> 04344-276618 / 04344-277937 / 400518</td><td>ramnamurthy@microlabs.in</td></tr>
<tr><td>2</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML03 HOSUR" style="text-decoration: none;color: black;" ><b><u>ML03</u></b></a></td><td>Mr.Premkumar P.U (AVP Technical & Operations)</td><td>No.92, Sipcot Industrial Area</td><td>Hosur </td><td>Tamilnadu</td><td>635126</td><td> 04344-276618 / 04344-277937 / 400518</td><td>premkumar@microlabs.in</td></tr>
<tr><td>3</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML05 PONDICHERRY" style="text-decoration: none;color: black;"><b><u>ML05</u></b> </a> </td><td>Mr.M.V.KRISHNA RAO (GM works)</td><td>R.S.No. 63/3 & 4,Thiruvandar Koil,Kothapurinathan Road</td><td>Pondicherry</td><td>Pondicherry</td><td> 605107 </td><td>0413-2641024,2641924,2640015</td><td>mvkraopdy@microlabs.in</td></tr>
<tr><td>4</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML06 GOA" style="text-decoration: none;color: black;"> <b><u>ML06</u></b></a></td><td>Mr.Raghaba Bhaskar Patro (VP Technical & Operations)</td><td>Plot No.S 155-159,Phase 3,Verna Industrial Area ,Verna</td><td>GOA </td><td>GOA</td><td>403722</td><td>0832-2887142, 2887143 </td><td>raghabapatro@microlabs.in</td></tr>
<tr><td>5</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML07 DRY POWDER" style="text-decoration: none;color: black;"><b><u>ML07</u></b> </a> </td><td>Mr.Sharad O Tekade (AVP Operations)</td><td>No.121-124, 4th Phase,KIADB Bommasandra Industrial Area</td><td>Bangalore </td><td>Karnataka</td><td>560099</td><td> 080-27839039,27839037</td><td>sharadtekade@microlabs.in</td></tr>
<tr><td>6</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML08 KUMBALGODU" style="text-decoration: none;color: black;"><b><u>ML08</u></b></a></td><td>Mr.N.Gowritharan (General Manager Production)</td><td>No.15/A, 2nd Phase,Kumbalgodu Industrial Area</td><td>Bangalore </td><td>Karnataka</td><td>560074</td><td>080-2843351</td><td>gowritharann@microlabs.in</td></tr>
<tr><td>7</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML11 VEERSANDRA" style="text-decoration: none;color: black;"><b><u>ML11</u></b> </a></td><td>Mr.Sanjay Gopinath (VP Technical & Operations)</td><td>Plot No.16,Veerasandra Industrial Area,Anekal Taluk,Electronics city</td><td>Bangalore </td><td>Karnataka</td><td>560100</td><td>080-27848094/95/96</td><td>sanjaygopinath@microlabs.in</td></tr>
<tr><td>8</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML12 PEENYA_2" style="text-decoration: none;color: black;"><b><u>ML12</u></b> </a></td><td>Mr.Uppin Gangadhar Chandrashekar (AVP Operations)</td><td>No.67/68A, 3rd Phase,Peenya Industrial Area</td><td>Bangalore </td><td>Karnataka</td><td>560058</td><td>080-28371984</td><td>gangadharuppin@microlabs.in</td></tr>
<tr><td>9</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML14 EYE DROPS" style="text-decoration: none;color: black;"><b><u>ML14</u></b></a> </td><td>Mr.Raju Gujare (AVP Technical & Operations)</td><td>Plot No.113-116, 4th Phase,KIADB Bommasandra Industrial</td><td>Bangalore  </td><td>Karnataka</td><td>560099</td><td>080-27839033/34/38</td><td>rajugujare@microlabs.in</td></tr>
<tr><td>10</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML15 API" style="text-decoration: none;color: black;"> <b><u>ML15</u></b></a> </td><td>Mr.Shenoy NB (VP Technical & Operations)<td>Plot No.41/42/43, 4th Phase,KIADB Bommasandra Industrial</td><td>Bangalore  </td><td>Karnataka</td><td>560099</td><td>080-27833070</td><td>shenoynb@microlabs.in</td></tr>
<tr><td>11</td><td><b><u>ML18</u></b></a> </td><td>Mr.Rajesh Kshirsagar (Chief Technical Officer)<td>No.58/3, Kudulu Village, Annekal Taluk, Singasandra Post</td><td>Bangalore  </td><td>Karnataka</td><td>560068</td><td>080-25731936/37</td><td>rajeshk@microlabs.in</td></tr>
<tr><td>12</td><td><b><u>ML21</u></b></a> </td><td>Mr.Pankaj Sharad Mandpe (VP R&D Formulations)<td>CTS No.73, Saki Estate, Off.Chandivali Road, Chandivali, </td><td>Mumbai  </td><td>Maharashtra</td><td>400072</td><td>02228478593</td><td>pankajs@microlabs.in</td></tr>
<tr><td>13</td><td><b><u>ML22</u></b></a> </td><td>Mr.Arunprasath G (Manager Operations)</td><td>PLOT NO.37(CA), 147-151, 179/281 KIADB Bommasandra-Jigni Link Road, Anekal Taluk</td><td>Bangalore</td><td>Karnataka</td><td>562106</td><td></td><td>arunprasath@microlabs.in</td></tr>
<tr><td>14</td><td><b><u>ML24</u></b> </td><td>Mr.Ramesh P (VP Technical & Operations)</td><td>Mamring-Namthang Road, Mamring via  VIA  RANGPO</td><td>South Sikkim  </td><td>SIKKIM</td><td>737132</td><td>09832095795,09443378160</td><td>ramesh@microlabs.in</td></tr>
<tr><td>15</td><td><a href="newsAndMedia.do?method=displayPlantInfo&sId=ML25 VEERSANDRA" style="text-decoration: none;color: black;"><b><u>ML25</u></b></a></td><td>Mr.Sanjay Gopinath (VP Technical & Operations)</td><td>Plot No.7B,Veerasandra Industrial Area,Anekal Taluk,Electronics city</td><td>Bangalore </td><td>Karnataka</td><td>560100</td><td>080-27848242/43/46/47/48</td><td>sanjaygopinath@microlabs.in</td></tr>
<tr><td>16</td><td><b><u>ML27</u></b></a> </td><td>Dr. Promod Kumar Rastogi (Sr.VP R&D)<td>Plot No.41/42/43, 4th Phase,KIADB Bommasandra Industrial</td><td>Bangalore  </td><td>Karnataka</td><td>560099</td><td>080-27833070</td><td>pramodkumar@microlabs.in</td></tr>

</body>
</html>