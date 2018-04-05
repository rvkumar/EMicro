

<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 
</head>

<body>

				
	<html:form action="hrApprove" enctype="multipart/form-data">
	

		
		 
		

                <logic:notEmpty name="list">
				 
				    <table class="bordered" border="1" >				     
              <tr><th>Shift Code</th><th>Shift Name</th><th>Shift Start Time</th><th>Shift End Time</th></tr>
               <logic:iterate id="abc1" name="shiftlist">
              <tr><th>${abc1.sh}</th><th>${abc1.shift}</th><th>${abc1.startTime}</th><th>${abc1.endTime}</th></tr>
              </logic:iterate>
              <tr></tr>
                  <tr>
                    
                    
                    <th>Employee No</th>
                    <th>Month</th>
                    <th>Year</th>
           			 <th>Day1</th>
					<th>Day2</th>
					<th>Day3</th>
					<th>Day4</th>
					<th>Day5</th>
					<th>Day6</th>
					<th>Day7</th>
					<th>Day8</th>
					<th>Day9</th>
					<th>Day10</th>
					<th>Day11</th>
					<th>Day12</th>
					<th>Day13</th>
					<th>Day14</th>
					<th>Day15</th>
					<th>Day16</th>
					<th>Day17</th>
					<th>Day18</th>
					<th>Day19</th>
					<th>Day20</th>
					<th>Day21</th>
					<th>Day22</th>
					<th>Day23</th>
					<th>Day24</th>
					<th>Day25</th>
					<th>Day26</th>
					<th>Day27</th>
					<th>Day28</th>
					<th>Day29</th>
					<th>Day30</th>
					<th>Day31</th>
            
                  </tr>
                  
                
                   
                  <logic:iterate id="abc1" name="list">
            
             
 
       
                <tr>               
                   
                     <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="month"/></td>
                    <td> <bean:write name="abc1" property="year"/></td>
                    <td> <bean:write name="abc1" property="day1"/></td>
					<td> <bean:write name="abc1" property="day2"/></td>
					<td> <bean:write name="abc1" property="day3"/></td>
					<td> <bean:write name="abc1" property="day4"/></td>
					<td> <bean:write name="abc1" property="day5"/></td>
					<td> <bean:write name="abc1" property="day6"/></td>
					<td> <bean:write name="abc1" property="day7"/></td>
					<td> <bean:write name="abc1" property="day8"/></td>
					<td> <bean:write name="abc1" property="day9"/></td>
					<td> <bean:write name="abc1" property="day10"/></td>
					<td> <bean:write name="abc1" property="day11"/></td>
					<td> <bean:write name="abc1" property="day12"/></td>
					<td> <bean:write name="abc1" property="day13"/></td>
					<td> <bean:write name="abc1" property="day14"/></td>
					<td> <bean:write name="abc1" property="day15"/></td>
					<td> <bean:write name="abc1" property="day16"/></td>
					<td> <bean:write name="abc1" property="day17"/></td>
					<td> <bean:write name="abc1" property="day18"/></td>
					<td> <bean:write name="abc1" property="day19"/></td>
					<td> <bean:write name="abc1" property="day20"/></td>
					<td> <bean:write name="abc1" property="day21"/></td>
					<td> <bean:write name="abc1" property="day22"/></td>
					<td> <bean:write name="abc1" property="day23"/></td>
					<td> <bean:write name="abc1" property="day24"/></td>
					<td> <bean:write name="abc1" property="day25"/></td>
					<td> <bean:write name="abc1" property="day26"/></td>
					<td> <bean:write name="abc1" property="day27"/></td>
					<td> <bean:write name="abc1" property="day28"/></td>
					<td> <bean:write name="abc1" property="day29"/></td>
					<td> <bean:write name="abc1" property="day30"/></td>
					<td> <bean:write name="abc1" property="day31"/></td>  
                  </tr>
            
				 </logic:iterate>
				 	
				 
				
				
		
				 </table>
			
				    </logic:notEmpty>
				  
				  
				  


</html:form>
</body>
</html>
