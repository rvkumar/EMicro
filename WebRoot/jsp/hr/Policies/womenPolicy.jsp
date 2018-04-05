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
<table class="bordered"><tr><th>Women Policy</th></tr>
<tr>
<td><a href="pdf/PSHWW POLICY.PDF" target="_blank">Prevention of Sexual Harassment of Women at Work Place</a></td></tr></table>
</body>
</html>