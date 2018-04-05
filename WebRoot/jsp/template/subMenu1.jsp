
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="com.microlabs.admin.form.LinksForm"/>
<jsp:directive.page import="java.util.LinkedList"/> 

 <div class="wrapper_left">
            <div class="mail_main">
            
        <%
	 
				  //  LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
				 	
				 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= 
				 		(LinkedHashMap<LinksForm,LinkedList<LinksForm>>) session.getAttribute("SUBLINKS");%>
				
				<% for (Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
			 	    LinksForm mainMenu=entry.getKey();
			 	    %>
			 	    
			 	     <div class="mail" onclick="subMenuClicked('<%=mainMenu.getLinkName()%>');"><a href="<%=mainMenu.getLinkPath()%>" target="contentPage">
			 	     <img src="<%=mainMenu.getIconName()%>" width="37" height="35" border="0" style="float:left; margin-right:5PX;" />
                <div id="mail"><%=mainMenu.getLinkName()%></div>
                <img src="images/down_arrow.png" width="17" height="17" border="0" style="float:right; margin-top:10px; margin-right:8px;" /></a></div>
			 	  
			 	   <div id='<%=mainMenu.getLinkName()%>' class="ul_content" style="display: none;">
			 	    <%
			 	    for (LinksForm lin : entry.getValue()) {
					%>
                <ul>
                  <a href="<%=lin.getLinkPath()+"&subLink="+mainMenu.getLinkName()%>" target="contentPage"><img src="images/lefarrow.png" border="0" /> 
                  <%=lin.getLinkName()%> </a>
                </ul>
             
							
 			<%
			}%>
			</div>
			
			<%
			 	}
			%>
			</div>
			
         </div>