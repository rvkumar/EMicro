package com.microlabs.toDoTask.action;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.toDoTask.dao.ToDoTaskDao;
import com.microlabs.toDoTask.form.ToDoTaskForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;


public class ToDoTaskAction extends DispatchAction {
	
	
	public ActionForward entertask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		String date=request.getParameter("reqdate");
		String e[]=date.split("/");
		date=Integer.parseInt(e[0])+"/"+Integer.parseInt(e[1])+"/"+e[2]; 
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		
		toDoForm.setFrom_date(date);
		
		return mapping.findForward("displaytodo");
	}
	
	
	public ActionForward saveToDoTask2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		
		toDoForm.setSubject(request.getParameter("subject"));
		toDoForm.setDescription(request.getParameter("description"));
		toDoForm.setFrom_date(request.getParameter("fromdate"));
		toDoForm.setFrom_time(request.getParameter("fromtime"));
		toDoForm.setTo_date(request.getParameter("todate"));
		toDoForm.setTo_time(request.getParameter("totime"));
		
		String taskDate=request.getParameter("fromdate");
		String c[]=taskDate.split("/");
 		String fromdate=c[2]+"-"+c[1]+"-"+c[0];
		
		
		String taskDate1=request.getParameter("todate");

		String e[]=taskDate1.split("/");
		taskDate1=Integer.parseInt(e[0])+"/"+Integer.parseInt(e[1])+"/"+e[2]; 
		
		String d[]=taskDate1.split("/");
		String todate=d[2]+"-"+d[1]+"-"+d[0];
		

		System.out.println("date size="+taskDate.length());
		
			
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		System.out.println("user id="+user.getId());
		
		String saveToDoDetails="insert into to_do_task_new(emp_id,subject,description,from_date,from_time,to_date,to_time,from_task_date,to_task_date)  " +
				"values('"+user.getEmployeeNo()+"','"+toDoForm.getSubject()+"','"+toDoForm.getDescription()+"','"+fromdate+"'," +
						"'"+toDoForm.getFrom_time()+"','"+todate+"','"+toDoForm.getTo_time()+"','"+taskDate+"','"+taskDate1+"')";
		
		System.out.println("saveTodo Query="+saveToDoDetails);
		
		int i=0;
		i=ad.SqlExecuteUpdate(saveToDoDetails);
		if(i==1)
		{
			toDoForm.setStatusMessage("Task Added Successfully");
			return mapping.findForward("displaytodo");
		}else{
			toDoForm.setStatusMessage("Error..While Adding Task.Please Check... ");
				
		}
		//UserInfo user=(UserInfo)session.getAttribute("user");
		String user_id=user.getEmployeeNo();
		int ch=0;
		//ToDoTaskDao ad=new ToDoTaskDao();
		HashMap<Integer,ArrayList> m1=new HashMap<Integer,ArrayList>();
		HashMap<Integer,ArrayList> m2=new HashMap<Integer,ArrayList>();
		String dept_id="";
		//CONVERT(varchar(11),ond.[start_date],101) as start_date
	    String query="select emp.PERNR,desg.DSGSTXT,dep.DPTSTXT,emp.DPTID  from emp_official_info as emp,DESIGNATION as desg," +
	    "DEPARTMENT as dep where PERNR='"+user_id+"' and desg.DSGID=emp.DSGID and dep.DPTID=emp.DPTID";
	    ResultSet rs=ad.selectQuery(query);
	  
	    try{
	    if(rs.next()){
	    	String get_leave_details="select user_id,start_date,end_date from leave_details where Approver_id='"+user.getEmployeeNo()+"'  and Approvel_Status='Approved'";
	    	//and month(start_date) = MONTH(GetDate()
	    	 ResultSet rs1=ad.selectQuery(get_leave_details);
	    	 while(rs1.next()){
	    		 String start_date=rs1.getString("start_date");
	    		 String a[]=start_date.split(" ");
	 			start_date=a[0];
	 			String b[]=start_date.split("-");
	 			start_date=b[2]+"/"+b[1]+"/"+b[0];
	    		 String end_date=rs1.getString("end_date");
	    		 String a1[]=end_date.split(" ");
	 			end_date=a1[0];
	 			String b1[]=end_date.split("-");
	 			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
	    		 Calendar ca = new GregorianCalendar();
	    		 int month=ca.get(Calendar.MONTH);
	    		 month=month+1;
	    		 ArrayList dates = new ArrayList();
	    		 if(start_date.equalsIgnoreCase(end_date))
	    		 {
	    			 dates.add(start_date);
	    			 if(!(rs.getString("PERNR").equalsIgnoreCase(rs1.getString("user_id"))))
	    	    		 m1.put(Integer.parseInt(rs1.getString("user_id")), dates);
	    		 }else{
             	
		        if(start_date!=null){
				String str_date =b[0]+b[1]+b[2];
				String end =b1[0]+b1[1]+b1[2];
				
				String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
				ResultSet rsDates=ad.selectQuery(getBetweenDates);
				while (rsDates.next()) {
					dates.add(rsDates.getString("start_date"));
				}
		        }
				//for(int z=0;z<dates.size();z++){
				  //  Date lDate =(Date)dates.get(z);
				 //    ds = formatter.format(lDate);    
				  // System.out.println(" Date is ..." + ds);
				//}
	    		
	    		 if(!(rs.getString("PERNR").equalsIgnoreCase(rs1.getString("user_id"))))
	    		 m1.put(Integer.parseInt(rs1.getString("user_id")), dates);
	    	 }
	    	
	    	ch=1;
	    	
	    }
	    
	    }
	    
	    //onduty employees
	    
	   String getOndutyList="select user_id,OnDuty_status,start_date,end_date from OnDuty_details where Approver_id='"+user.getEmployeeNo()+"' and OnDuty_status=1";
	   ResultSet rsOndutyList=ad.selectQuery(getOndutyList);
	   while(rsOndutyList.next())
	   {
  		 String start_date=rsOndutyList.getString("start_date");
			String b[]=start_date.split("-");
			start_date=b[2]+"/"+b[1]+"/"+b[0];
		 String end_date=rsOndutyList.getString("end_date");
			String b1[]=end_date.split("-");
			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
			Calendar ca = new GregorianCalendar();
		 int month=ca.get(Calendar.MONTH);
		 month=month+1;
		 ArrayList dates = new ArrayList();
		 if(start_date.equalsIgnoreCase(end_date))
		 {
			 dates.add(start_date);
			 if(!(user.getEmployeeNo().equalsIgnoreCase(rsOndutyList.getString("user_id"))))
	    		 m2.put(Integer.parseInt(rsOndutyList.getString("user_id")), dates);
		 }else{
     	SimpleDateFormat formatter ; 
     	formatter = new SimpleDateFormat("yyyyMMdd");
        if(start_date!=null){
		String str_date =start_date;
		String end =end_date;
		String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
		ResultSet rsDates=ad.selectQuery(getBetweenDates);
		while (rsDates.next()) {
			dates.add(rsDates.getString("start_date"));
		}
        }
		//for(int z=0;z<dates.size();z++){
		  //  Date lDate =(Date)dates.get(z);
		 //    ds = formatter.format(lDate);    
		  // System.out.println(" Date is ..." + ds);
		//}
		
		 if(!(user.getEmployeeNo().equalsIgnoreCase(rsOndutyList.getString("user_id"))))
		 m2.put(Integer.parseInt(rsOndutyList.getString("user_id")), dates);
	 }
	
	ch=1;
	

		   
	   }
    
		    
		    
		    
		    String firstDay=c[2]+"-"+c[1]+"-"+1;
			String lastDay=c[2]+"-"+c[1]+"-"+30;
			String firstDay1=d[2]+"-"+d[1]+"-"+1;
		    String lastDay1=d[2]+"-"+d[1]+"-"+30;
		    
			HashSet userTaskList=new HashSet();
			userTaskList.clear();
		    String getTaskList="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and((from_date between '"+firstDay+"' and  '"+lastDay+"') or" +
		    		" (to_date between '"+firstDay1+"' and '"+lastDay1+"'))";
		    
		    ResultSet rsTaskList=ad.selectQuery(getTaskList);
		    while(rsTaskList.next())
		    {
		    	ToDoTaskForm todoList=new ToDoTaskForm();
		    	todoList.setSubject(rsTaskList.getString("subject"));
		        todoList.setDescription(rsTaskList.getString("description"));
		        todoList.setFrom_date(EMicroUtils.display1(rsTaskList.getDate("from_date")));
		        todoList.setFrom_time(rsTaskList.getString("from_time"));
		        todoList.setTo_date(EMicroUtils.display1(rsTaskList.getDate("to_date")));
		        todoList.setTo_time(rsTaskList.getString("to_time"));
		    	userTaskList.add(todoList);
		    }
		    request.setAttribute("userTaskList", userTaskList);
		    }
		    catch(Exception r){
		    	r.printStackTrace();
		    }
		    if(ch==1){
		    	request.setAttribute("emp_leaves", m1);
				request.setAttribute("emp_Ondutys", m2);
		    	return mapping.findForward("displayHOD");
			}
			else
				return mapping.findForward("displaytodo");
	}
	public ActionForward modifyTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String requiredRemiderDate=toDoForm.getRequiredRemiderDate();
			toDoForm.setRequiredRemiderDate(requiredRemiderDate);
			String id=request.getParameter("id");
			String subject =request.getParameter("subject");
			String description =request.getParameter("description");
			String FromtaskDate =request.getParameter("from_date");
			String FromTime =request.getParameter("from_time");
			String Totaskdate =request.getParameter("to_date");
			String Totime=request.getParameter("to_time");
			
		
			SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
			String dateInString = request.getParameter("from_date");
		
			try {
		 
				Date date = formatter.parse(dateInString);
				System.out.println(date);
				System.out.println(formatter.format(date));
				FromtaskDate=formatter.format(date);
				FromtaskDate=FromtaskDate.toString();
			}catch (Exception e) {
				e.printStackTrace();
			}
			String fromdate1 = request.getParameter("from_date");
			String c[]=fromdate1.split("/");
			String fromdate=c[2]+"-"+c[1]+"-"+c[0];
			
			
			String dateInString1 = request.getParameter("to_date");
			
			try {
		 
				Date date = formatter.parse(dateInString1);
				System.out.println(date);
				System.out.println(formatter.format(date));
				Totaskdate=formatter.format(date);
				Totaskdate=Totaskdate.toString();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			String todate1=request.getParameter("to_date");
			String d[]=todate1.split("/");
			String todate=d[2]+"-"+d[1]+"-"+d[0];
			
			
			String deleteTaskQuery =" Update  to_do_task_new set subject='"+subject+"' , description='"+description+"', from_task_date = '"+FromtaskDate+"', " +
					"from_time='"+FromTime+"' ,to_task_date='"+Totaskdate+"' ,to_time='"+Totime+"',from_date='"+fromdate+"',to_date='"+todate+"' where id='"+id+"'";                          
			
			int i=0;
			i=ad.SqlExecuteUpdate(deleteTaskQuery);
			if(i>0){
				
				toDoForm.setStatusMessage("Task Modified successfully");
				
			}else {
				toDoForm.setStatusMessage("Error..When  modifing task.. ");	
			}
			
			try{
				LinkedList listOfRecords=new LinkedList();
				
				String sql="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and '"+fromdate+"' between  from_date and to_date  ";
				ResultSet rs=ad.selectQuery(sql);
				while(rs.next())
				{
					ToDoTaskForm toDoTaskForm=new ToDoTaskForm();
					toDoTaskForm.setSno(rs.getInt("id"));
					toDoTaskForm.setSubject(rs.getString("subject"));
					toDoTaskForm.setDescription(rs.getString("description"));
					toDoTaskForm.setFrom_date(EMicroUtils.display1(rs.getDate("from_date")));
					toDoTaskForm.setTo_date(EMicroUtils.display1(rs.getDate("to_date")));
					toDoTaskForm.setFrom_time(rs.getString("from_time"));
					toDoTaskForm.setTo_time(rs.getString("to_time"));
					toDoTaskForm.setTaskdate(fromdate);
					listOfRecords.add(toDoTaskForm);
				}
				
				request.setAttribute("listOfRecords", listOfRecords);
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayReminderDetails");
		
	
	
	
	}
	
	
	public ActionForward deleteTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String id=request.getParameter("id");
			String deleteTaskQuery="delete from to_do_task_new where id='"+id+"'";
			String getfrmdate="select * from to_do_task_new where id='"+id+"'";
			ResultSet rsfrmdate =ad.selectQuery(getfrmdate);
			String fromdate="";
			while(rsfrmdate.next())
			{
				fromdate=rsfrmdate.getString("from_date");
				
			}
			int i=0;
			i=ad.SqlExecuteUpdate(deleteTaskQuery);
			if(i>0)
			{
				toDoForm.setStatusMessage("Task deleted successfully");
				
				
			}else {
				toDoForm.setStatusMessage("Error..When deleting task.g ");	
			}
			
			try{
				LinkedList listOfRecords=new LinkedList();
				
				String sql="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and '"+fromdate+"' between  from_date and to_date  ";
				ResultSet rs=ad.selectQuery(sql);
				while(rs.next())
				{
					ToDoTaskForm toDoTaskForm=new ToDoTaskForm();
					toDoTaskForm.setSno(rs.getInt("id"));
					toDoTaskForm.setSubject(rs.getString("subject"));
					toDoTaskForm.setDescription(rs.getString("description"));
					toDoTaskForm.setFrom_date(EMicroUtils.display1(rs.getDate("from_date")));
					toDoTaskForm.setTo_date(EMicroUtils.display1(rs.getDate("to_date")));
					toDoTaskForm.setFrom_time(rs.getString("from_time"));
					toDoTaskForm.setTo_time(rs.getString("to_time"));
					toDoTaskForm.setTaskdate(fromdate);
					listOfRecords.add(toDoTaskForm);
				}
				
				request.setAttribute("listOfRecords", listOfRecords);
				
				if(listOfRecords.size()==0)
				{
					request.setAttribute("details", "details");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		displaycalender(mapping, form, request, response);
		return mapping.findForward("displayReminderDetails");
	}
	
	
	
	
	public ActionForward getReminderDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		System.out.println("getTODoRecords()---");
		String remainderDate=request.getParameter("reqdate");
		toDoForm.setRequiredRemiderDate(remainderDate);
		String c[]=remainderDate.split("/");
		String remainderDate1=c[2]+"-"+c[1]+"-"+c[0];
     
		int sno=Integer.parseInt(request.getParameter("sno"));
		LinkedList listOfRecords=new LinkedList();
		LinkedList remdetails=new LinkedList();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		System.out.println("user id="+user.getId());
		try{
			String sql="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and '"+remainderDate1+"' between  from_date and to_date  ";
			ResultSet rs=ad.selectQuery(sql);
			while(rs.next())
			{
				ToDoTaskForm toDoTaskForm=new ToDoTaskForm();
				toDoTaskForm.setSno(rs.getInt("id"));
				toDoTaskForm.setSubject(rs.getString("subject"));
				toDoTaskForm.setDescription(rs.getString("description"));
				toDoTaskForm.setFrom_date(EMicroUtils.display1(rs.getDate("from_date")));
				toDoTaskForm.setTo_date(EMicroUtils.display1(rs.getDate("to_date")));
				toDoTaskForm.setFrom_time(rs.getString("from_time"));
				toDoTaskForm.setTo_time(rs.getString("to_time"));
				listOfRecords.add(toDoTaskForm);
			}
			
			request.setAttribute("listOfRecords", listOfRecords);
			
			
			String sql1="select * from to_do_task_new where  emp_id='"+user.getEmployeeNo()+"'  and id='"+sno+"'";
			ResultSet rs1=ad.selectQuery(sql1);
			while(rs1.next())
			{
				ToDoTaskForm toDoTaskForm=new ToDoTaskForm();
				toDoTaskForm.setSno(rs1.getInt("id"));
				toDoTaskForm.setSubject(rs1.getString("subject"));
				toDoTaskForm.setDescription(rs1.getString("description"));
				toDoTaskForm.setFrom_date(EMicroUtils.display1(rs1.getDate("from_date")));
				toDoTaskForm.setTo_date(EMicroUtils.display1(rs1.getDate("to_date")));
				toDoTaskForm.setFrom_time(rs1.getString("from_time"));
				toDoTaskForm.setTo_time(rs1.getString("to_time"));
				remdetails.add(toDoTaskForm);
			}
			request.setAttribute("reminderDetails", remdetails);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	return mapping.findForward("displayReminderDetails");
	}

	public ActionForward getToDoRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		System.out.println("getTODoRecords()---");
	  
	    String remainderDate=request.getParameter("reqdate");
	    toDoForm.setRequiredRemiderDate(remainderDate);
		String c[]=remainderDate.split("/");
		String remainderDate1=c[2]+"-"+c[1]+"-"+c[0];
		
		LinkedList listOfRecords=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		System.out.println("user id="+user.getId());
		try{
			
			
			String sql="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and '"+remainderDate1+"' between  from_date and to_date  ";
			ResultSet rs=ad.selectQuery(sql);
			while(rs.next())
			{
				ToDoTaskForm toDoTaskForm=new ToDoTaskForm();
				toDoTaskForm.setSno(rs.getInt("id"));
				toDoTaskForm.setSubject(rs.getString("subject"));
				toDoTaskForm.setDescription(rs.getString("description"));
				toDoTaskForm.setFrom_date(EMicroUtils.display1(rs.getDate("from_date")));
				toDoTaskForm.setTo_date(EMicroUtils.display1(rs.getDate("to_date")));
				toDoTaskForm.setFrom_time(rs.getString("from_time"));
				toDoTaskForm.setTo_time(rs.getString("to_time"));
				toDoTaskForm.setTaskdate(remainderDate);
				listOfRecords.add(toDoTaskForm);
			}
			
			request.setAttribute("listOfRecords", listOfRecords);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayReminderDetails");
		
	}
	public ActionForward saveToDoTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		
		toDoForm.setSubject(request.getParameter("subject"));
		toDoForm.setDescription(request.getParameter("description"));
		toDoForm.setSheduleTime(request.getParameter("sheduleTime"));
		toDoForm.setRemarks(request.getParameter("remarks"));
		toDoForm.setStatus(request.getParameter("status"));
		toDoForm.setTaskdate(request.getParameter("taskdate"));
		String taskDate=request.getParameter("taskdate");
		String c[]=taskDate.split("/");
		String taskDate1=c[2]+"-"+c[1]+"-"+c[0];
		System.out.println("date size="+taskDate.length());
		
			
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		System.out.println("user id="+user.getId());
		String saveToDoDetails="insert into to_do_task(user_id,subject,description,shedule_Time,remarks,status,task_date,task_date2) " +
				"values('"+user.getId()+"','"+toDoForm.getSubject()+"','"+toDoForm.getDescription()+"','"+toDoForm.getSheduleTime()+"'," +
						"'"+toDoForm.getRemarks()+"','"+toDoForm.getStatus()+"','"+taskDate+"','"+taskDate1+"')";
		System.out.println("saveTodo Query="+saveToDoDetails);
		int i=0;
		i=ad.SqlExecuteUpdate(saveToDoDetails);
		if(i==1)
		{
			toDoForm.setStatusMessage("Task Added Successfully");

			
		}else{
			toDoForm.setStatusMessage("Error..While Adding Task.Please Check... ");
				
		}
		//UserInfo user=(UserInfo)session.getAttribute("user");
		int user_id=user.getId();
		int ch=0;
		//ToDoTaskDao ad=new ToDoTaskDao();
		HashMap<Integer,ArrayList> m1=new HashMap<Integer,ArrayList>();
		HashMap<Integer,ArrayList> m2=new HashMap<Integer,ArrayList>();
		 String query="select emp.PERNR,desg.DSGSTXT,dep.DPTSTXT,emp.DPTID  from emp_official_info as emp,DESIGNATION as desg," +
		    "DEPARTMENT as dep where PERNR='"+user.getEmployeeNo()+"' and desg.DSGID=emp.DSGID and dep.DPTID=emp.DPTID";
		    ResultSet rs=ad.selectQuery(query);
		  
		    try{
		    if(rs.next()){
		    	if(rs.getString("DSGSTXT").equalsIgnoreCase("GM INFORMATION TECH")){
		    	String get_leave_details="select user_id,start_date,end_date from leave_details where Approver_id='"+user.getEmployeeNo()+"'  and leave_status='1'";
		    	//and month(start_date) = MONTH(GetDate()
		    	 ResultSet rs1=ad.selectQuery(get_leave_details);
		    	 while(rs1.next()){
		    		 String start_date=rs1.getString("start_date");
		    		 String a[]=start_date.split(" ");
		 			start_date=a[0];
		 			String b[]=start_date.split("-");
		 			start_date=b[2]+"/"+b[1]+"/"+b[0];
		 			
		    		 String end_date=rs1.getString("end_date");
		    		 String a1[]=end_date.split(" ");
		 			end_date=a1[0];
		 			String b1[]=end_date.split("-");
		 			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
		 	
		    		 
		    		
		    		 ArrayList<Date> dates = new ArrayList<Date>();
	             	SimpleDateFormat formatter ; 
	             	formatter = new SimpleDateFormat("dd/MM/yyyy");
			        if(start_date!=null){
					String str_date =start_date;
					String end =end_date;
					
					String ds;
					
				
					
					
					Date  startDate = formatter.parse(str_date); 
					Date  endDate = formatter.parse(end);
					long interval = 24*1000 * 60 * 60; // 1 hour in millis
					long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
					long curTime = startDate.getTime();
					String ch_date;
					while (curTime <= endTime) {
					    dates.add(new Date(curTime));
					    curTime += interval;
					}
					//for(int z=0;z<dates.size();z++){
					  //  Date lDate =(Date)dates.get(z);
					 //    ds = formatter.format(lDate);    
					  // System.out.println(" Date is ..." + ds);
					//}
		    		
					 if(!(rs.getString("PERNR").equalsIgnoreCase(rs1.getString("user_id"))))
		    		 m1.put(Integer.parseInt(rs1.getString("user_id")), dates);
		    		 System.out.println("map size........"+m1.size());
		    	 }
		    	
		    	ch=1;
		    }
		    }
		    }
		    
		  //onduty employees
		    
			   String getOndutyList="select user_id,OnDuty_status,start_date,end_date from OnDuty_details where Approver_id='"+user.getEmployeeNo()+"' and OnDuty_status=1";
			   ResultSet rsOndutyList=ad.selectQuery(getOndutyList);
			   while(rsOndutyList.next())
			   {
		  		 String start_date=rsOndutyList.getString("start_date");
					String b[]=start_date.split("-");
					start_date=b[2]+"/"+b[1]+"/"+b[0];
				 String end_date=rsOndutyList.getString("end_date");
					String b1[]=end_date.split("-");
					end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
				 Calendar ca = new GregorianCalendar();
				 int month=ca.get(Calendar.MONTH);
				 month=month+1;
				 ArrayList dates = new ArrayList();
				 if(start_date.equalsIgnoreCase(end_date))
				 {
					 dates.add(start_date);
					 if(!(user.getEmployeeNo().equalsIgnoreCase(rsOndutyList.getString("user_id"))))
			    		 m2.put(Integer.parseInt(rsOndutyList.getString("user_id")), dates);
				 }else{
		     	SimpleDateFormat formatter ; 
		     	formatter = new SimpleDateFormat("yyyyMMdd");
		        if(start_date!=null){
				String str_date =start_date;
				String end =end_date;
				String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
				ResultSet rsDates=ad.selectQuery(getBetweenDates);
				while (rsDates.next()) {
					dates.add(rsDates.getString("start_date"));
				}
		        }
				//for(int z=0;z<dates.size();z++){
				  //  Date lDate =(Date)dates.get(z);
				 //    ds = formatter.format(lDate);    
				  // System.out.println(" Date is ..." + ds);
				//}
				
				 if(!(user.getEmployeeNo().equalsIgnoreCase(rsOndutyList.getString("user_id"))))
				 m2.put(Integer.parseInt(rsOndutyList.getString("user_id")), dates);
			 }
			
			ch=1;
			

				   
			   }

		    String firstDay=c[2]+"-"+c[1]+"-"+1;
			String lastDay=c[2]+"-"+c[1]+"-"+30;
			HashSet userTaskList=new HashSet();
			userTaskList.clear();
		    String getTaskList="select * from to_do_task where task_date2 between '"+firstDay+"' and '"+lastDay+"' and user_id='"+user.getId()+"'";
			ResultSet rsTaskList=ad.selectQuery(getTaskList);
		    while(rsTaskList.next())
		    {
		    	ToDoTaskForm todoList=new ToDoTaskForm();
		    	todoList.setSubject(rsTaskList.getString("subject"));
		    	todoList.setSheduleTime(rsTaskList.getString("shedule_Time"));
		    	todoList.setTaskdate(rsTaskList.getString("task_date"));
		    	userTaskList.add(todoList);
		    }
		    request.setAttribute("userTaskList", userTaskList);
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    if(ch==1){
		    	request.setAttribute("emp_leaves", m1);
				request.setAttribute("emp_Ondutys", m2);
				return mapping.findForward("displayHOD");
			}
			else
				return mapping.findForward("display");
	}
	
	public ActionForward displaycalender(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("displaycalender()---");

		ToDoTaskForm calenderForm=(ToDoTaskForm)form;
		HttpSession session = request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int iYear = 0;
		int iMonth = 0;
		session.setAttribute("iYear", String.valueOf(iYear));
		session.setAttribute("iMonth", String.valueOf(iMonth));
		String user_id=user.getEmployeeNo();
		int ch=0;
		HashMap<Integer,String> m1=new HashMap<Integer,String>();
		HashMap<Integer,String> m2=new HashMap<Integer,String>();
		ToDoTaskDao ad=new ToDoTaskDao();
		String dept_id="";
		//CONVERT(varchar(11),ond.[start_date],101) as start_date
	    String query="select emp.PERNR,desg.DSGSTXT,dep.DPTSTXT,emp.DPTID  from emp_official_info as emp,DESIGNATION as desg," +
	    "DEPARTMENT as dep where PERNR='"+user_id+"' and desg.DSGID=emp.DSGID and dep.DPTID=emp.DPTID";
	    ResultSet rs=ad.selectQuery(query);
	  
	    try{
	    if(rs.next()){
	    	ArrayList dates = new ArrayList();
	    	String get_leave_details="select user_id,start_date,end_date from leave_details where Approver_id='"+user.getEmployeeNo()+"' and user_id!='"+user.getEmployeeNo()+"'  and month(start_date) = MONTH(GetDate()) and Approvel_Status='Approved'";
	    	//and month(start_date) = MONTH(GetDate()
	    	 ResultSet rs1=ad.selectQuery(get_leave_details);
	    	 int leaveCout=0;
	    	 
	    	 while(rs1.next()){
	    		 String start_date=rs1.getString("start_date");
	    		 String a[]=start_date.split(" ");
	 			start_date=a[0];
	 			String b[]=start_date.split("-");
	 			start_date=b[2]+"/"+b[1]+"/"+b[0];
	    		 String end_date=rs1.getString("end_date");
	    		 String a1[]=end_date.split(" ");
	 			end_date=a1[0];
	 			String b1[]=end_date.split("-");
	 			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
	    		 Calendar ca = new GregorianCalendar();
	    		 int month=ca.get(Calendar.MONTH);
	    		 month=month+1;
	    		 
	    		 if(start_date.equalsIgnoreCase(end_date))
	    		 {
	    			 ++leaveCout;
	    			 dates.add(start_date);
	    			 m1.put(leaveCout, start_date);
	    			
	    			
	    		 }else{
             	
		        if(start_date!=null){
				String str_date =b[0]+b[1]+b[2];
				String end =b1[0]+b1[1]+b1[2];
				
				String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
				ResultSet rsDates=ad.selectQuery(getBetweenDates);
				while (rsDates.next()) {
					++leaveCout;
					dates.add(rsDates.getString("start_date"));
					m1.put(leaveCout, rsDates.getString("start_date"));
					
					
				}
		        }
	    		
	    		
	    	 }
	    		 if(!(rs.getString("PERNR").equalsIgnoreCase(rs1.getString("user_id"))))
    	    		// m1.put(Integer.parseInt(rs1.getString("user_id")), dates);
	    	ch=1;
	    	
	    }
	    
	    }
	    
	    //onduty employees
	    ArrayList dates = new ArrayList();
	   String getOndutyList="select user_id,OnDuty_status,start_date,end_date from OnDuty_details where Approver_id='"+user.getEmployeeNo()+"' and OnDuty_status=1";
	   ResultSet rsOndutyList=ad.selectQuery(getOndutyList);
	   int ondutyCout=0;
	   while(rsOndutyList.next())
	   {
  		 String start_date=rsOndutyList.getString("start_date");
			String b[]=start_date.split("-");
			start_date=b[0]+"/"+b[1]+"/"+b[2];
		 String end_date=rsOndutyList.getString("end_date");
			String b1[]=end_date.split("-");
			end_date=b1[0]+"/"+b1[1]+"/"+b1[2];
		 Calendar ca = new GregorianCalendar();
		 int month=ca.get(Calendar.MONTH);
		 month=month+1;
		
		 if(start_date.equalsIgnoreCase(end_date))
		 {
			 dates.add(start_date);
			 ++ondutyCout;
				m2.put(ondutyCout, start_date);
			
		 }else{
     	SimpleDateFormat formatter ; 
     	formatter = new SimpleDateFormat("yyyyMMdd");
        if(start_date!=null){
		String str_date = start_date;
		String end =end_date;
		String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
		ResultSet rsDates=ad.selectQuery(getBetweenDates);
		while (rsDates.next()) {
			++ondutyCout;
			dates.add(rsDates.getString("start_date"));
			m2.put(ondutyCout, rsDates.getString("start_date"));
		}
        }
		
	 }
		 if(!(user.getEmployeeNo().equalsIgnoreCase(rsOndutyList.getString("user_id"))))
    		// m2.put(Integer.parseInt(rsOndutyList.getString("user_id")), dates);
	ch=1;
	   }
	   
	   //
	    
	    //display current month task
	    
	    Calendar cal1 = Calendar.getInstance();  
	    int year = cal1.get(Calendar.YEAR);  
	    int month = cal1.get(Calendar.MONTH)+1; //zero-based  
	    System.out.println("year = "+year+"\nmonth = "+month);  
	    
	    
	    GregorianCalendar cal = new GregorianCalendar(year, month-1, 1);

		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);
		 int currentMonth=(iMonth)+1;
		cal = new GregorianCalendar(year, month-1, days);
		int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
		int ryear = year;
		int rmonth = month;
		
		System.out.println("days=" + days);
		
		String firstDay=year+"-"+month+"-"+1;
		String lastDay=year+"-"+month+"-"+days;
		 String firstDay1=year+"-"+month+"-"+1;
			String lastDay1=year+"-"+month+"-"+days;
			
		    
			HashSet userTaskList=new HashSet();
			userTaskList.clear();
		    String getTaskList="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and ((from_date between '"+firstDay+"' and  '"+lastDay+"') or" +
		    		" (to_date between '"+firstDay1+"' and '"+lastDay1+"'))";
		    
		    ResultSet rsTaskList=ad.selectQuery(getTaskList);
		    while(rsTaskList.next())
		    {
		    	
		    
				
		    	ToDoTaskForm todoList=new ToDoTaskForm();
		    	todoList.setSubject(rsTaskList.getString("subject"));
		        todoList.setDescription(rsTaskList.getString("description"));
		        todoList.setFrom_date(EMicroUtils.display1(rsTaskList.getDate("from_date")));
		        todoList.setFrom_time(rsTaskList.getString("from_time"));
		        todoList.setTo_date(EMicroUtils.display1(rsTaskList.getDate("to_date")));
		        todoList.setTo_time(rsTaskList.getString("to_time"));
		    	userTaskList.add(todoList);
		    }
		    request.setAttribute("userTaskList", userTaskList);
		    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
		
		
		if(ch==1){
			request.setAttribute("emp_leaves", m1);
			request.setAttribute("emp_Ondutys", m2);
			
			return mapping.findForward("displayHOD");
		}
		else
		
		
		return mapping.findForward("display");
	}

	
	
	public ActionForward nextMonth1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	System.out.println("nextMonth1()");

	ToDoTaskForm calenderForm=(ToDoTaskForm)form;
	HttpSession session = request.getSession();
	
		String iYears = request.getParameter("hYear");
		String iMonths = request.getParameter("hMonth");
		
		
		int iYear=Integer.parseInt(iYears);
		System.out.println("iYear="+iYear);
		int iMonth=Integer.parseInt(iMonths);
		iMonth=iMonth+1;
		
		if(iMonth==12)
		{
			iMonth=0;
			iYear=iYear+1;
		}
		
		System.out.println("iMonth="+iMonth);
		
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

		for (int iy = iTYear - 70; iy <= iTYear + 70; iy++) {
			if (iy == iYear) {
				iy = iy;
			} else {
				iy = iy;
			}
			int im = 0;
			for (im = 0; im <= 11; im++) {
				if (im == iMonth) {
					im = im;
				}
			}
		
			

			session.setAttribute("iYear", String.valueOf(iYear));
			session.setAttribute("iMonth", String.valueOf(iMonth));

		}
		UserInfo user=(UserInfo)session.getAttribute("user");
		String user_id=user.getEmployeeNo();
		int ch=0;
		ToDoTaskDao ad=new ToDoTaskDao();
		HashMap<Integer,String> m1=new HashMap<Integer,String>();
		HashMap<Integer,String> m2=new HashMap<Integer,String>();
		String dept_id="";
		//CONVERT(varchar(11),ond.[start_date],101) as start_date
	    String query="select emp.PERNR,desg.DSGSTXT,dep.DPTSTXT,emp.DPTID  from emp_official_info as emp,DESIGNATION as desg," +
	    "DEPARTMENT as dep where PERNR='"+user.getEmployeeNo()+"' and desg.DSGID=emp.DSGID and dep.DPTID=emp.DPTID";
	    ResultSet rs=ad.selectQuery(query);
	  
	    try{
	    if(rs.next()){
	    	 ArrayList dates = new ArrayList();
	    	String get_leave_details="select user_id,start_date,end_date from leave_details where Approver_id='"+user.getEmployeeNo()+"'  and Approvel_Status='Approved'";
	    	//and month(start_date) = MONTH(GetDate()
	    	 ResultSet rs1=ad.selectQuery(get_leave_details);
	    	 int leaveCout=0;
	    	 while(rs1.next()){
	    		 String start_date=rs1.getString("start_date");
	    		 String a[]=start_date.split(" ");
	 			start_date=a[0];
	 			String b[]=start_date.split("-");
	 			start_date=b[2]+"/"+b[1]+"/"+b[0];
	    		 String end_date=rs1.getString("end_date");
	    		 String a1[]=end_date.split(" ");
	 			end_date=a1[0];
	 			String b1[]=end_date.split("-");
	 			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
	    		  ca = new GregorianCalendar();
	    		 int month=ca.get(Calendar.MONTH);
	    		 month=month+1;
	    		
	    		 if(start_date.equalsIgnoreCase(end_date))
	    		 {
	    			 ++leaveCout;
	    			 dates.add(start_date);
	    			 m1.put(leaveCout, start_date);
	    		
	    		 }else{
             	
		        if(start_date!=null){
				String str_date =b[0]+b[1]+b[2];
				String end =b1[0]+b1[1]+b1[2];
				
				String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
				ResultSet rsDates=ad.selectQuery(getBetweenDates);
				while (rsDates.next()) {
					++leaveCout;
					dates.add(rsDates.getString("start_date"));
					m1.put(leaveCout, rsDates.getString("start_date"));
				}
		        }
				//for(int z=0;z<dates.size();z++){
				  //  Date lDate =(Date)dates.get(z);
				 //    ds = formatter.format(lDate);    
				  // System.out.println(" Date is ..." + ds);
				//}
	    		
	    		
	    	 }
	    		 if(!(rs.getString("PERNR").equalsIgnoreCase(rs1.getString("user_id"))))
    	    		// m1.put(Integer.parseInt(rs1.getString("user_id")), dates);
	    	ch=1;
	    	
	    }
	    
	    }
	    
	    //onduty employees
	    
	   String getOndutyList="select user_id,OnDuty_status,start_date,end_date from OnDuty_details where Approver_id='"+user.getEmployeeNo()+"' and OnDuty_status=1";
	   ResultSet rsOndutyList=ad.selectQuery(getOndutyList);
	   int ondutyCout=0;
	   while(rsOndutyList.next())
	   {
  		 String start_date=rsOndutyList.getString("start_date");
			String b[]=start_date.split("-");
			start_date=b[2]+"/"+b[1]+"/"+b[0];
		 String end_date=rsOndutyList.getString("end_date");
			String b1[]=end_date.split("-");
			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
		  ca = new GregorianCalendar();
		 int month=ca.get(Calendar.MONTH);
		 month=month+1;
		 ArrayList dates = new ArrayList();
		 if(start_date.equalsIgnoreCase(end_date))
		 {
			 dates.add(start_date);
			
			 if(!(user.getEmployeeNo().equalsIgnoreCase(rsOndutyList.getString("user_id")))){
				 ++ondutyCout;
				 m2.put(ondutyCout, start_date);
			 }
				
		 }else{
     	SimpleDateFormat formatter ; 
     	formatter = new SimpleDateFormat("yyyyMMdd");
        if(start_date!=null){
		String str_date =start_date;
		String end =end_date;
		String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
		ResultSet rsDates=ad.selectQuery(getBetweenDates);
		while (rsDates.next()) {
			dates.add(rsDates.getString("start_date"));
			++ondutyCout;
			 m2.put(ondutyCout, start_date);
		}
        }
		//for(int z=0;z<dates.size();z++){
		  //  Date lDate =(Date)dates.get(z);
		 //    ds = formatter.format(lDate);    
		  // System.out.println(" Date is ..." + ds);
		//}
		
		 //m2.put(Integer.parseInt(rsOndutyList.getString("user_id")), dates);
	 }
	
	ch=1;
	

		   
	   }
	   
		String firstDay=ryear+"-"+(rmonth+1)+"-"+1;
		String lastDay=ryear+"-"+(rmonth+1)+"-"+days;
		String firstDay1=ryear+"-"+(rmonth+1)+"-"+1;
		String lastDay1=ryear+"-"+(rmonth+1)+"-"+days;
		
	    
		HashSet userTaskList=new HashSet();
		userTaskList.clear();
	    String getTaskList="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and ((from_date between '"+firstDay+"' and  '"+lastDay+"') or" +
	    		" (to_date between '"+firstDay1+"' and '"+lastDay1+"'))";
	    
	    ResultSet rsTaskList=ad.selectQuery(getTaskList);
	    while(rsTaskList.next())
	    {
	    	ToDoTaskForm todoList=new ToDoTaskForm();
	    	todoList.setSubject(rsTaskList.getString("subject"));
	        todoList.setDescription(rsTaskList.getString("description"));
	        todoList.setFrom_date(EMicroUtils.display1(rsTaskList.getDate("from_date")));
	        todoList.setFrom_time(rsTaskList.getString("from_time"));
	        todoList.setTo_date(EMicroUtils.display1(rsTaskList.getDate("to_date")));
	        todoList.setTo_time(rsTaskList.getString("to_time"));
	    	userTaskList.add(todoList);
	    }
	    request.setAttribute("userTaskList", userTaskList);
	    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    if(ch==1){
		    	request.setAttribute("emp_leaves", m1);
				request.setAttribute("emp_Ondutys", m2);
				return mapping.findForward("displayHOD");
			}
			else
		return mapping.findForward("display");
	}
	public ActionForward prviousMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	System.out.println("nextMonth1()");

		String iYears = request.getParameter("hYear");
		String iMonths = request.getParameter("hMonth");
		HttpSession session = request.getSession();
		
		
		int iYear=Integer.parseInt(iYears);
		System.out.println("iYear="+iYear);
		int iMonth=Integer.parseInt(iMonths);
		iMonth=iMonth-1;
		
		if(iMonth==-1)
		{
			iMonth=11;
			iYear=iYear-1;
		}
		
		System.out.println("iMonth="+iMonth);
		
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

		for (int iy = iTYear - 70; iy <= iTYear + 70; iy++) {
			if (iy == iYear) {
				iy = iy;
			} else {
				iy = iy;
			}
			int im = 0;
			for (im = 0; im <= 11; im++) {
				if (im == iMonth) {
					im = im;
				}
			}
		
		

			session.setAttribute("iYear", String.valueOf(iYear));
			session.setAttribute("iMonth", String.valueOf(iMonth));

		}
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String user_id=user.getEmployeeNo();
		int ch=0;
		ToDoTaskDao ad=new ToDoTaskDao();
		HashMap<Integer,String> m1=new HashMap<Integer,String>();
		HashMap<Integer,String> m2=new HashMap<Integer,String>();
		String dept_id="";
		//CONVERT(varchar(11),ond.[start_date],101) as start_date
	    String query="select emp.PERNR,desg.DSGSTXT,dep.DPTSTXT,emp.DPTID  from emp_official_info as emp,DESIGNATION as desg," +
	    "DEPARTMENT as dep where PERNR='"+user_id+"' and desg.DSGID=emp.DSGID and dep.DPTID=emp.DPTID";
	    ResultSet rs=ad.selectQuery(query);
	    ArrayList dates = new ArrayList();
	    try{
	    if(rs.next()){
	    	String get_leave_details="select user_id,start_date,end_date from leave_details where Approver_id='"+user.getEmployeeNo()+"'  and Approvel_Status='Approved'";
	    	//and month(start_date) = MONTH(GetDate()
	    	 ResultSet rs1=ad.selectQuery(get_leave_details);
	    	 int leaveCout=0;
	    	 while(rs1.next()){
	    		 String start_date=rs1.getString("start_date");
	    		 String a[]=start_date.split(" ");
	 			start_date=a[0];
	 			String b[]=start_date.split("-");
	 			start_date=b[2]+"/"+b[1]+"/"+b[0];
	    		 String end_date=rs1.getString("end_date");
	    		 String a1[]=end_date.split(" ");
	 			end_date=a1[0];
	 			String b1[]=end_date.split("-");
	 			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
	    		  ca = new GregorianCalendar();
	    		 int month=ca.get(Calendar.MONTH);
	    		 month=month+1;
	    		
	    		 if(start_date.equalsIgnoreCase(end_date))
	    		 {
	    			 ++leaveCout;
	    			 dates.add(start_date);
	    			 m1.put(leaveCout, start_date);
	    			 
	    		 }else{
             	
		        if(start_date!=null){
				String str_date =b[0]+b[1]+b[2];
				String end =b1[0]+b1[1]+b1[2];
				
				String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
				ResultSet rsDates=ad.selectQuery(getBetweenDates);
				while (rsDates.next()) {
					++leaveCout;
					dates.add(rsDates.getString("start_date"));
					m1.put(leaveCout, rsDates.getString("start_date"));
				}
		        }
				//for(int z=0;z<dates.size();z++){
				  //  Date lDate =(Date)dates.get(z);
				 //    ds = formatter.format(lDate);    
				  // System.out.println(" Date is ..." + ds);
				//}
	    		
	    		
	    		
	    	 }
	    		 if(!(rs.getString("PERNR").equalsIgnoreCase(rs1.getString("user_id"))))
		    		// m1.put(Integer.parseInt(rs1.getString("user_id")), dates);
	    	ch=1;
	    	
	    }
	    
	    }
	    
	    //onduty employees
	    ArrayList dates1 = new ArrayList();
	   String getOndutyList="select user_id,OnDuty_status,start_date,end_date from OnDuty_details where Approver_id='"+user.getEmployeeNo()+"' and OnDuty_status=1";
	   ResultSet rsOndutyList=ad.selectQuery(getOndutyList);
	   int ondutyCout=0;
	   while(rsOndutyList.next())
	   {
  		 String start_date=rsOndutyList.getString("start_date");
			String b[]=start_date.split("-");
			start_date=b[2]+"/"+b[1]+"/"+b[0];
		 String end_date=rsOndutyList.getString("end_date");
			String b1[]=end_date.split("-");
			end_date=b1[2]+"/"+b1[1]+"/"+b1[0];
		  ca = new GregorianCalendar();
		 int month=ca.get(Calendar.MONTH);
		 month=month+1;
		
		 if(start_date.equalsIgnoreCase(end_date))
		 {
			 if(!(user.getEmployeeNo().equalsIgnoreCase(rsOndutyList.getString("user_id")))){
				 dates1.add(start_date);
				 ++ondutyCout;
					m2.put(ondutyCout, start_date);
			 }
	    		
		 }else{
     	SimpleDateFormat formatter ; 
     	formatter = new SimpleDateFormat("yyyyMMdd");
        if(start_date!=null){
		String str_date =start_date;
		String end =end_date;
		String getBetweenDates="SELECT CONVERT(varchar(11),thedate,103) as start_date FROM dbo.ExplodeDates('"+str_date+"','"+end+"') as a";
		ResultSet rsDates=ad.selectQuery(getBetweenDates);
		while (rsDates.next()) {
			dates1.add(rsDates.getString("start_date"));
			 ++ondutyCout;
				m2.put(ondutyCout, start_date);
		}
        }
		//for(int z=0;z<dates.size();z++){
		  //  Date lDate =(Date)dates.get(z);
		 //    ds = formatter.format(lDate);    
		  // System.out.println(" Date is ..." + ds);
		//}
		
		 
	 }
	
	ch=1;
	

		   
	   }
	   String firstDay=ryear+"-"+(rmonth+1)+"-"+1;
		String lastDay=ryear+"-"+(rmonth+1)+"-"+days;
		String firstDay1=ryear+"-"+(rmonth+1)+"-"+1;
		String lastDay1=ryear+"-"+(rmonth+1)+"-"+days;
		
	    
		HashSet userTaskList=new HashSet();
		userTaskList.clear();
	    String getTaskList="select * from to_do_task_new where emp_id='"+user.getEmployeeNo()+"' and ((from_date between '"+firstDay+"' and  '"+lastDay+"') or" +
	    		" (to_date between '"+firstDay1+"' and '"+lastDay1+"'))";
	    
	    ResultSet rsTaskList=ad.selectQuery(getTaskList);
	    while(rsTaskList.next())
	    {
	    	ToDoTaskForm todoList=new ToDoTaskForm();
	    	todoList.setSubject(rsTaskList.getString("subject"));
	        todoList.setDescription(rsTaskList.getString("description"));
	        todoList.setFrom_date(EMicroUtils.display1(rsTaskList.getDate("from_date")));
	        todoList.setFrom_time(rsTaskList.getString("from_time"));
	        todoList.setTo_date(EMicroUtils.display1(rsTaskList.getDate("to_date")));
	        todoList.setTo_time(rsTaskList.getString("to_time"));
	    	userTaskList.add(todoList);
	    }
	    request.setAttribute("userTaskList", userTaskList);
	    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    if(ch==1){
				request.setAttribute("emp_leaves", m1);
				request.setAttribute("emp_Ondutys", m2);
				return mapping.findForward("displayHOD");
			}
			else
		return mapping.findForward("display");
	}
	public ActionForward ajaxTest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String date=request.getParameter("date");
		System.out.println(date);
		ToDoTaskForm toDoForm=(ToDoTaskForm)form;
		ToDoTaskDao ad=new ToDoTaskDao();
		int i=0;
		String query="select holiday_name,day_name from holidays where holiday_date='"+date+"'";
		ResultSet rs=ad.selectQuery(query);
		try{
		while(rs.next()){
			toDoForm.setHoliday_name(rs.getString("holiday_name"));
			toDoForm.setDay_name(rs.getString("day_name"));
			System.out.println("i am in while");
            i=1;
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(i==1)
		request.setAttribute("foundHoliday", "foundHoliday");
		return mapping.findForward("ajaxTest");
	}
	public ActionForward appList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String date=request.getParameter("date");
		String a[]=date.split("/");
		date=a[1]+"/"+a[0]+"/"+a[2];
		System.out.println(date);
		ToDoTaskDao ad=new ToDoTaskDao();
		HttpSession session = request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ArrayList appList=new ArrayList();
		ArrayList ondutyList=new ArrayList();
		
		/*select e.PERNR,e.EMP_FULLNAME,Convert(varchar(10),CONVERT(date,d.start_date,106),103) lv_stdt,Convert(varchar(10),CONVERT(date,d.end_date,106),103) lv_endDt,no_of_days,start_date from emp_official_info e,leave_details d 
		where e.PERNR=d.user_id  and d.Approvel_Status='Approved' and '7/15/2014' between d.start_date and d.end_date */
		
		String getappList="select e.PERNR,e.EMP_FULLNAME,d.start_date,Convert(varchar(10),CONVERT(date,d.start_date,106),103) lv_stdt,Convert(varchar(10),CONVERT(date,d.end_date,106),103) lv_endDt,no_of_days from emp_official_info e,leave_details d " +
				          "where e.PERNR=d.user_id  and d.Approvel_Status='Approved' and '"+date+"' between d.start_date and d.end_date and d.Approver_id='"+user.getEmployeeNo()+"'";
		ResultSet rs=ad.selectQuery(getappList);
		
		try{
		while(rs.next()){
			ToDoTaskForm toform=new ToDoTaskForm();
			
			if(!(rs.getString("PERNR").equalsIgnoreCase(user.getEmployeeNo())))
			{
				toform.setEmp_id(rs.getString("PERNR"));
				toform.setEmp_name(rs.getString("EMP_FULLNAME"));
				toform.setFrom_date(rs.getString("lv_stdt"));
				toform.setTo_date(rs.getString("lv_endDt"));
				toform.setTotalDays(rs.getDouble("no_of_days"));
				appList.add(toform);
			}
		
			
		}
		String getempList="select e.PERNR,e.EMP_FULLNAME,Convert(varchar(10),CONVERT(date,d.start_date,106),103)  stDt,Convert(varchar(10),"
				+ "CONVERT(date,d.end_date,106),103) end_date,start_date from emp_official_info e,OnDuty_details d  " +
        "where e.PERNR=d.user_id  and d.Approver_Status='Approved' and '"+date+"' between d.start_date and d.end_date and d.Approver_id='"+user.getEmployeeNo()+"'";
	ResultSet rs1=ad.selectQuery(getempList);
	while(rs1.next())
	{
		ToDoTaskForm toform=new ToDoTaskForm();
		
		if(!(rs1.getString("PERNR").equalsIgnoreCase(user.getEmployeeNo())))
		{
			toform.setEmp_id(rs1.getString("PERNR"));
			toform.setEmp_name(rs1.getString("EMP_FULLNAME"));
			toform.setFrom_date(rs1.getString("stDt"));
			toform.setTo_date(rs1.getString("end_date"));
			ondutyList.add(toform);
		}
	
	}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(appList.size()>0)
		request.setAttribute("apsentList", appList);
		if(ondutyList.size()>0)
			request.setAttribute("ondutyList", ondutyList);
			
		return mapping.findForward("appList");
	}
	public ActionForward holidList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String date=request.getParameter("date");
		String a[]=date.split("/");
		date=a[0]+"/"+a[1]+"/"+a[2];
		System.out.println(date);
		ToDoTaskDao ad=new ToDoTaskDao();
		ArrayList appList=new ArrayList();
		String getappList="select h.holiday_date,h.holiday_name from holidays as h,users as u,Location as loc  where h.location=loc.LOCID and u.employeenumber='"+user.getEmployeeNo()+"' and h.location='"+user.getPlantId()+"' and Holiday_Date='"+date+"'";
		ResultSet rs=ad.selectQuery(getappList);
		try{
		while(rs.next()){
			ToDoTaskForm toform=new ToDoTaskForm();
			toform.setHol_name(rs.getString("holiday_name"));

			appList.add(toform);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("apsentList", appList);
		
		return mapping.findForward("holList");
	}
}
