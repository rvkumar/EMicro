
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="com.microlabs.admin.form.LinksForm"/>
<jsp:directive.page import="java.util.LinkedList"/> 

  
  
			
			 <%
				 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= 
				 		(LinkedHashMap<LinksForm,LinkedList<LinksForm>>) session.getAttribute("SUBLINKS");%>
				 
				<% 
				int count=0;
				for(Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
					
			 	    LinksForm mainMenu=entry.getKey();
			 	    
			 	  
			 	    %>
			
			<div class="mail" title="MAIL" onclick="subMenuClicked('<%=mainMenu.getLinkName()%>');" id="<%=mainMenu.getLinkName()+"ss"%>">
			
			<input type="hidden" name="mailMenu" id="gg<%=count%>" value="<%=mainMenu.getLinkName()%>"/>
			
			<a href="<%=mainMenu.getLinkPath()%>" target="contentPage">
			
			<img src="/EMicro Files/Icons/<%=mainMenu.getIconName()%>" width="17" height="17" border="0" align="top" style="float:left; margin:5px;"  />
              <%=mainMenu.getLinkName()%><img src="images/left_menu/up_arrow.png" width="18" height="16" 
              border="0"  style="float:right; margin:5px;"  id="<%=mainMenu.getLinkName()+"im"%>"/></a></div>
			<div class="submenubg" id="<%=mainMenu.getLinkName()%>" style="display: none;">
			
			<%
			  	    for (LinksForm lin : entry.getValue()) {
			  %>
                <ul>
                  <a href="<%=lin.getLinkPath()+"&subLink="+mainMenu.getLinkName()%>" id="ul_content" target="contentPage">
                  <img src="images/left_menu/lefarrow.png" border="0" />  <%=lin.getLinkName()%> </a>
      			</ul>
      			 <%
			   }%>
                
              </div>
              
			 <%
               count++;
			 }
			%>
            
            
			
          
          