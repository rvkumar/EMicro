package com.microlabs.utilities;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class FilterLogin implements Filter{
	private FilterConfig config = null;
	private HashMap protectedResourcesURL=null;
	
	@Override
	public void destroy() {
		config=null;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			HttpServletRequest httpServletRequest=(HttpServletRequest)request;
			HttpServletResponse httpServletResponse=(HttpServletResponse)response;
			HttpSession session = httpServletRequest.getSession();
			
	
			String url=httpServletRequest.getServletPath();
			String id=httpServletRequest.getParameter("id");
			String method=httpServletRequest.getParameter("method");
			String completeUrl=url+"?method="+method;
	        System.out.println("Complete Url: " + completeUrl); 
	        
	        
	        boolean valid=false;
	        
	        
			//ArrayList<IdValuePair<String, String>> exceptionalLinks=new ArrayList<IdValuePair<String,String>>();
			UserInfo user=null;
			if(session!=null) 
				user=(UserInfo)session.getAttribute("user");
			
			System.out.println(url+"  "+method); 
			
			if(url.equalsIgnoreCase("/main.do") && method.equalsIgnoreCase("logout") && id!=null){
			
			httpServletResponse.sendRedirect("login.do?method=display");
			}
			else if(!url.equalsIgnoreCase("/EMicro") && method==null && user==null && id==null){
				
				if(method==null && id==null){
					httpServletResponse.sendRedirect("login.do?method=logout");
				}
				else if(!url.equalsIgnoreCase("/EMicro")){
					httpServletResponse.sendRedirect("QMS.do?method=Dashboard");
				}
				
			}else if(!url.equalsIgnoreCase("/EMicro") && !method.equalsIgnoreCase("logout") && user==null && id!=null){
				if(user==null)
				{
					httpServletResponse.sendRedirect("jsp/sessionExpired.jsp");
				}
				else{
				httpServletResponse.sendRedirect("login.do?method=logout");
				}
				//chain.doFilter(request, response);
			}
			
			else{
				chain.doFilter(request, response);
			}
			
			
			}
	
	
	@Override
	public void init(FilterConfig config) throws ServletException {
			this.config=config;
	}
	
	
	

}
