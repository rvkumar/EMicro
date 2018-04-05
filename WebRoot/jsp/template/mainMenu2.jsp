
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.ArrayList"/><div class="main-block">


<div id="leftmenu">
 <div id="main_left_menu">
 <%
	 
	    LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
	 	Set set=hm.entrySet();
	 	
	 	Iterator itr=set.iterator();
	 	
	 	while(itr.hasNext()){
	 	Map.Entry me=(Map.Entry)itr.next();
	 	
		 %>
		 
		
<%

if(!me.getKey().equals("Arr")){ %>


 <div id="main_left_menu1">
 <a href="<%=me.getKey()%>"><%=me.getValue()%></a></div>
 
<%}%>

<%if(me.getKey().equals("Arr")){

 ArrayList h=(ArrayList)me.getValue();
 
 Iterator itr1=h.iterator();
		%>
		
		
		<% 
  while(itr1.hasNext()){
  String[] link=itr1.next().toString().split(",");
 %>
  <div id="submenu1">
  <img src="images/lefarrow.png" width="7" height="12" alt="image" border="0" /> 
  <a href="<%=link[1]%>"><%=link[0]%></a></div>
 
 <%} %>
        
  </div>
 
 <%} %>
 
 <%} %>
  
  </div>

<br/>

