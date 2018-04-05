
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.ArrayList"/>


 <table>
	<tr bgcolor="#485150">
	
	
	</tr>
 	 	
	 <tr bgcolor="4F79B8"><td align="center"><font color="white">Main</font></td></tr>
	 <%
	 
	    LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
	 	Set set=hm.entrySet();
	 	
	 	Iterator itr=set.iterator();
	 	
	 	while(itr.hasNext()){
	 	Map.Entry me=(Map.Entry)itr.next();
	 	
		 %>
<%

System.out.println("Getting a Key is ************************"+me.getKey());
if(!me.getKey().equals("Arr")){ %>
<tr><td><font color="white"><a href="<%=me.getKey()%>" ><%=me.getValue()%></a></font></td></tr>
<%}%>

<%if(me.getKey().equals("Arr")){

 ArrayList h=(ArrayList)me.getValue();
 
 Iterator itr1=h.iterator();
		
  while(itr1.hasNext()){
 
  String[] link=itr1.next().toString().split(",");
 
 %>
<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--<font color="white"><a href="<%=link[1]%>" ><%=link[0]%></a></font></td></tr>
 <%} %>
 
 <%} %>
 <%} %>


 </table>

