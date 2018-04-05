package com.microlabs.main.db;

import java.sql.ResultSet;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.microlabs.ess.form.JoiningFormalityForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMicroUtils;

public class ApprovalsDao {

	public ActionForward getExtraEmpAddressHistoryRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,String empID) 
	{
		JoiningFormalityForm	joiningFormApp = new JoiningFormalityForm();
		NewsandMediaDao ad=new NewsandMediaDao();
		LinkedList addrlist = new LinkedList();
		LinkedList list1=new LinkedList();
		/*String sql3="select * from emp_address_history as his where his.user_id='"+empID+"' and id not in " +
				"(select id from emp_address temp where temp.user_id='"+empID+"')";*/
		String sql3="select * from emp_address_history where user_id='"+empID+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		try {
			while(rs11.next()) {
				joiningFormApp = new JoiningFormalityForm();
				joiningFormApp.setId(rs11.getString("id"));
				joiningFormApp.setAddressType(rs11.getString("address_type"));
				
				
				joiningFormApp.setCareofcontactname(rs11.getString("care_of_contact_name"));
				joiningFormApp.setHouseNumber(rs11.getString("house_no"));
				joiningFormApp.setAddressLine1(rs11.getString("address_line1"));
				joiningFormApp.setAddressLine2(rs11.getString("address_line2"));
				joiningFormApp.setAddressLine3(rs11.getString("address_line3"));
				joiningFormApp.setPostalCode(rs11.getString("postal_code"));
				joiningFormApp.setCity(rs11.getString("a_city"));
			
					String cou="select cou.LANDX,st.BEZEI from State as st,Country as cou where st.BLAND='"+rs11.getString("a_state")+"' " +
							"and st.LAND1='"+rs11.getString("a_country")+"' and cou.LAND1='"+rs11.getString("a_country")+"'";
					ResultSet rs22 = ad.selectQuery(cou);
					while (rs22.next()) {
						joiningFormApp.setCountry(rs22.getString("LANDX"));
						joiningFormApp.setState(rs22.getString("BEZEI"));
					}
					
					joiningFormApp.setOwnAccomodation(rs11.getString("own_accomodation"));
					joiningFormApp.setCompanyHousing(rs11.getString("company_housing"));
					joiningFormApp.setAddStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
					joiningFormApp.setAddEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
					
					
						
					
					addrlist.add(joiningFormApp);
					
					
			}
			request.setAttribute("addrHistory", addrlist);
			
			String sql="select * from emp_address where user_id='"+empID+"' ";
			ResultSet rs1 = ad.selectQuery(sql);
					String pass="";
					JoiningFormalityForm	joiningForm = new JoiningFormalityForm();
					if (false) {
						
						joiningForm.setId(rs1.getString("id"));
							if(!(joiningFormApp.getAddressType().equalsIgnoreCase(rs1.getString("address_type"))))
							{
							joiningForm.setAddressType(rs1.getString("address_type"));
							}
							
							
							if(!(joiningFormApp.getCareofcontactname().equalsIgnoreCase(rs1.getString("care_of_contact_name"))))
							{
								joiningForm.setCareofcontactname(rs1.getString("care_of_contact_name"));
							}
							
							if(!(joiningFormApp.getHouseNumber().equalsIgnoreCase(rs1.getString("house_no"))))
							{
								joiningForm.setHouseNumber(rs1.getString("house_no"));

							}
							if(!(joiningFormApp.getAddressLine1().equalsIgnoreCase(rs1.getString("address_line1"))))
							{
								joiningForm.setAddressLine1(rs1.getString("address_line1"));

							}
							if(!(joiningFormApp.getAddressLine2().equalsIgnoreCase(rs1.getString("address_line2"))))
							{
								joiningForm.setAddressLine2(rs1.getString("address_line2"));

							}
							
							if(!(joiningFormApp.getAddressLine3().equalsIgnoreCase(rs1.getString("address_line3"))))
							{
								joiningForm.setAddressLine3(rs1.getString("address_line3"));

							}
							
							if(!(joiningFormApp.getPostalCode().equalsIgnoreCase(rs1.getString("postal_code"))))
							{
								joiningForm.setPostalCode(rs1.getString("postal_code"));

							}
							if(!(joiningFormApp.getCity().equalsIgnoreCase(rs1.getString("a_city"))))
							{
								joiningForm.setCity(rs1.getString("a_city"));

							}
					
							String cou="select cou.LANDX,st.BEZEI from State as st,Country as cou where st.BLAND='"+rs1.getString("a_state")+"' " +
							"and st.LAND1='"+rs1.getString("a_country")+"' and cou.LAND1='"+rs1.getString("a_country")+"'";
							 ResultSet	 rs22 = ad.selectQuery(cou);
					while (rs22.next()) {
						
						if(!(joiningFormApp.getCountry().equalsIgnoreCase(rs22.getString("LANDX"))))
						{
							joiningForm.setCountry(rs22.getString("LANDX"));

						}
						
						if(!(joiningFormApp.getState().equalsIgnoreCase(rs22.getString("BEZEI"))))
						{
							joiningForm.setState(rs22.getString("BEZEI"));

						}
					
						
					}
					
						if(!(joiningFormApp.getOwnAccomodation().equalsIgnoreCase(rs1.getString("own_accomodation"))))
						{
							joiningForm.setOwnAccomodation(rs1.getString("own_accomodation"));

						}
						if(!(joiningFormApp.getCompanyHousing().equalsIgnoreCase(rs1.getString("company_housing"))))
						{
							joiningForm.setCompanyHousing(rs1.getString("company_housing"));

						}
						
					list1.add(joiningForm);
					request.setAttribute("addrlist", list1);
				}else{
						joiningForm = new JoiningFormalityForm();
					joiningForm.setId(rs11.getString("id"));
					joiningForm.setAddressType("");
					joiningForm.setCareofcontactname("");
					joiningForm.setHouseNumber("");
					joiningForm.setAddressLine1("");
					joiningForm.setAddressLine2("");
					joiningForm.setAddressLine3("");
					joiningForm.setPostalCode("");
					joiningForm.setCity("");
					joiningForm.setState("");
					joiningForm.setCountry("");
					joiningForm.setOwnAccomodation("");
					joiningForm.setCompanyHousing("");
					joiningForm.setAddStartDate("");
					joiningForm.setAddEndDate("");
					list1.add(joiningForm);
					request.setAttribute("addrlist", list1);
				}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("addressinfo");
	}
}
