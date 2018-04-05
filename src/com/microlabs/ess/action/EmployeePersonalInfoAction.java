package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EmployeePersonalInfoDao;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.EmppersonalForm;
import com.microlabs.utilities.UserInfo;




public class EmployeePersonalInfoAction extends DispatchAction{
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
		
		
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		EmployeePersonalInfoDao ad=new EmployeePersonalInfoDao();
		
		String sql="select * from links where module='"+id+"' and sub_linkname is null";
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			
			
			 while(rs.next()){
				 hm.put(rs.getString("link_path")+"?method="+rs.getString("method")+"&sId="+rs.getString("link_name")+"&id="+rs.getString("module"), rs.getString("link_name"));
				 
			}
			 
			 session.setAttribute("SUBLINKS", hm);
			
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		

		return mapping.findForward("display");
	}
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
	
		EmppersonalForm emppersonalForm = (EmppersonalForm) form;
	
		String linkName=request.getParameter("sId"); 	
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();		
		/*UserInfo user=(UserInfo)session.getAttribute("user_id");
		user.getId();		*/	
		EmployeePersonalInfoDao ad=new EmployeePersonalInfoDao();
		
		String sql="select * from links where link_name='"+linkName+"' and module='"+module+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			 
			 String sql1="select * from links where module='"+module+"' and sub_linkname is null";
				
				ResultSet rs1=ad.selectQuery(sql1);
					
					
					LinkedHashMap hm=new LinkedHashMap();	
					
					ArrayList a1=new ArrayList();
					 while(rs1.next()){
						 
						 
						 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), (rs1.getString("link_name")+','+rs1.getString("icon_name")));
						 
						 String linkName1=rs1.getString("link_name");
						 
						 if(linkName1.equalsIgnoreCase(linkName)){
						 
						 String sql2="select * from links where module='"+module+"' and sub_linkname='"+rs1.getString("link_name")+"' and sub_linkname is not null";
						 
						 ResultSet rs2=ad.selectQuery(sql2);
						 
						 while (rs2.next()) {
						 	 a1.add(rs2.getString("link_name")+","+rs2.getString("link_path")+"?method="+rs2.getString("method")+"&subLink="+rs2.getString("sub_linkname")+"&module="+rs2.getString("module")+"&linkName="+rs2.getString("link_name"));
						 }
						 
						 hm.put("Arr",a1);
						 }
						 
					}
					 session.setAttribute("SUBLINKS", hm);
					 UserInfo user=(UserInfo)session.getAttribute("user");
					 String Id=emppersonalForm.getId();
					 String sql3="select * from emp_personal_info where user_id='" + user.getId()+ "'";
					

					 ResultSet  rs4 = ad.selectQuery(sql3);
						while (rs4.next()) {
							emppersonalForm.setTitle(rs4.getString("title"));
							emppersonalForm.setFirstName(rs4.getString("first_name"));
							emppersonalForm.setMiddleName(rs4.getString("middle_name"));
							emppersonalForm.setLastName(rs4.getString("last_name"));
							emppersonalForm.setInitials(rs4.getString("initials"));
							emppersonalForm.setNickName(rs4.getString("nick_name"));
							emppersonalForm.setGender(rs4.getString("gender"));
							emppersonalForm.setMaritalStatus(rs4.getString("marital_status"));
							///////////////////////////////////////////////////////
							emppersonalForm.setDateofBirth(rs4.getString("date_of_birth"));
							String dateOfBirth=rs4.getString("date_of_birth");
							//1988-12-04 00:00:00.0
							String a[]=dateOfBirth.split(" ");
							dateOfBirth=a[0];
							String b[]=dateOfBirth.split("-");
							dateOfBirth=b.toString();
							StringBuffer result = new StringBuffer();
							   result.append( b[2]+"/"+b[1]+"/"+b[0]);
							String dateOfBirth_mod = result.toString();
						    emppersonalForm.setDateOfBirth_mod(dateOfBirth_mod);
							
						
							
							emppersonalForm.setBirthplace(rs4.getString("birth_place"));
							emppersonalForm.setCountryofBirth(rs4.getString("country_of_birth"));
							emppersonalForm.setCaste(rs4.getString("caste"));
							emppersonalForm.setReligiousDenomination(rs4.getString("religous_denomination"));;
							emppersonalForm.setNationality(rs4.getString("nationality"));
							emppersonalForm.setTelephoneNumber(rs4.getString("telephone_no"));
							emppersonalForm.setMobileNumber(rs4.getString("mobile_no"));
							emppersonalForm.setFaxNumber(rs4.getString("fax_no"));
							emppersonalForm.setEmailAddress(rs4.getString("email_address"));
							emppersonalForm.setWebsite(rs4.getString("website"));
							emppersonalForm.setBloodGroup(rs4.getString("blood_group"));
							emppersonalForm.setPermanentAccountNumber(rs4.getString("permanent_acno"));
							emppersonalForm.setPassportNumber(rs4.getString("passport_no"));
							emppersonalForm.setPlaceofIssueofPassport(rs4.getString("place_of_issue_of_passport"));
							////////////////////////////////////////////////////////////////////////
							emppersonalForm.setDateofexpiryofPassport(rs4.getString("date_of_expiry_of_passport"));
							String  dateofexpiryofPassport =emppersonalForm.getDateofexpiryofPassport();	
							String aep[]=dateofexpiryofPassport.split(" ");
							dateofexpiryofPassport=aep[0];
							String bep[]=dateofexpiryofPassport.split("-");
							
							StringBuffer result_ep = new StringBuffer();
							result_ep.append( bep[2]+"/"+bep[1]+"/"+bep[0]);
							String dateofexpiryofPassport_mod = result_ep.toString();
						    emppersonalForm.setDateofexpiryofPassport_mod(dateofexpiryofPassport_mod);
							
							
							emppersonalForm.setPersonalIdentificationMarks(rs4.getString("personal_identification_mark"));
							////////////////////////////////////////////////
							emppersonalForm.setDateofissueofPassport(rs4.getString("date_of_issue_of_passp"));
							String  dateofissueofPassport =emppersonalForm.getDateofissueofPassport();	
							String aip[]=dateofissueofPassport.split(" ");
							dateofissueofPassport=aip[0];
							String bip[]=dateofissueofPassport.split("-");
							
							StringBuffer result_ip = new StringBuffer();
							result_ip.append( bip[2]+"/"+bip[1]+"/"+bip[0]);
							String dateofissueofPassport_mod = result_ip.toString();
						    emppersonalForm.setDateofissueofPassport_mod(dateofissueofPassport_mod);
							
							
							
							
							emppersonalForm.setPhysicallyChallenged(rs4.getString("physiaclly_challenged"));
							emppersonalForm.setPhysicallyChallengeddetails(rs4.getString("physically_challenged_details"));
							emppersonalForm.setEmergencyContactPersonName(rs4.getString("emergency_contact_person_name"));
							emppersonalForm.setEmergencyContactAddressLine1(rs4.getString("emegency_contact_addressline1"));
							emppersonalForm.setEmergencyContactAddressLine2(rs4.getString("emegency_contact_addressline2"));
							emppersonalForm.setEmergencyTelephoneNumber(rs4.getString("emegency_telephone_number"));
							emppersonalForm.setEmergencyMobileNumber(rs4.getString("emegency_mobile_number"));
							emppersonalForm.setNoOfChildrens(rs4.getString("number_of_childrens"));
						}
					/* String sql4="select * from emp_address where id='" + Id+ "'";
					 ResultSet  rs5 = ad.selectQuery(sql4);
						while (rs5.next()) {
							emppersonalForm.setCareofcontactname(rs5.getString(2));
							emppersonalForm.setHouseNumber(rs5.getString(3));
							emppersonalForm.setAddressLine1(rs5.getString(4));
							emppersonalForm.setAddressLine2(rs5.getString(5));
							emppersonalForm.setAddressLine3(rs5.getString(6));
							emppersonalForm.setPostalCode(rs5.getString(7));
							emppersonalForm.setCity(rs5.getString(8));
							emppersonalForm.setState(rs5.getString(9));
							emppersonalForm.setCountry(rs5.getString(10));
							emppersonalForm.setOwnAccomodation(rs5.getString(11));
						}
						 String sql5="select * from emp_family_details where id='" + Id+ "'";
						 ResultSet  rs6 = ad.selectQuery(sql5);
							while (rs6.next()) {
								emppersonalForm.setFamilyDependentTypeID(rs6.getString(2));
								emppersonalForm.setFtitle(rs6.getString(3));
								emppersonalForm.setFfirstName(rs6.getString(4));
								emppersonalForm.setFmiddleName(rs6.getString(5));
								emppersonalForm.setFlastName(rs6.getString(6));
								emppersonalForm.setFinitials(rs6.getString(7));
								emppersonalForm.setFgender(rs6.getString(8));
								emppersonalForm.setFdateofBirth(rs6.getString(9));
								emppersonalForm.setFbirthplace(rs6.getString(10));
								emppersonalForm.setFtelephoneNumber(rs6.getString(11));
								emppersonalForm.setFmobileNumber(rs6.getString(12));
								emppersonalForm.setFemailAddress(rs6.getString(13));
								emppersonalForm.setFbloodGroup(rs6.getString(14));
								emppersonalForm.setFdependent(rs6.getString(15));
								emppersonalForm.setFemployeeofCompany(rs6.getString(16));
								emppersonalForm.setFemployeeNumber(rs6.getString(17));
								 
							}
							 String sql6="select * from emp_education_details where id='" + Id+ "'";
							 ResultSet  rs7 = ad.selectQuery(sql6);
								while (rs7.next()) {
									 emppersonalForm.setEducationalLevel(rs7.getString(2));
									 emppersonalForm.setQualification(rs7.getString(3));
									 emppersonalForm.setSpecialization(rs7.getString(4));
									 emppersonalForm.setUniversityName(rs7.getString(5));
									 emppersonalForm.setUniverysityLocation(rs7.getString(6));
									 emppersonalForm.setEdstate(rs7.getString(7));;
									 emppersonalForm.setEdcountry(rs7.getString(8));
									 emppersonalForm.setDurationofCourse(rs7.getString(9));
									 emppersonalForm.setTimes(rs7.getString(10));
									 emppersonalForm.setFromDate(rs7.getString(11));
									 emppersonalForm.setToDate(rs7.getString(12));
									 emppersonalForm.setFullTimePartTime(rs7.getString(13));
									 emppersonalForm.setPercentage(rs7.getString(14));
									
								}
								 String sql7="select * from emp_experience_details where id='" + Id+ "'";
								 ResultSet  rs8 = ad.selectQuery(sql7);
									while (rs8.next()) {
										emppersonalForm.setNameOfEmployer(rs8.getString(2));
										emppersonalForm.setIndustry(rs8.getString(3));
										emppersonalForm.setExCity(rs8.getString(4));
										emppersonalForm.setExcountry(rs8.getString(5));
										emppersonalForm.setPositionHeld(rs8.getString(6));
										emppersonalForm.setJobRole(rs8.getString(7));
										emppersonalForm.setStartDate(rs8.getString(8));
										emppersonalForm.setEndDate(rs8.getString(10));
												
									}
									 String sql8="select * from emp_language_details where id='" + Id+ "'";
									 ResultSet  rs9 = ad.selectQuery(sql8);
										while (rs9.next()) {
											emppersonalForm.setLanguage(rs9.getString(2));
											emppersonalForm.setMotherTongue(rs9.getString(3));
											emppersonalForm.setLanguageFluency(rs9.getString(4));
											emppersonalForm.setLangstartDate(rs9.getString(5));
										}
										*/
		}catch(SQLException se){
			se.printStackTrace();
		}
	
		ArrayList list =new ArrayList();

		request.setAttribute("listName", list);
		
		 
		 
		return mapping.findForward("display1");
	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmppersonalForm emppersonalForm = (EmppersonalForm) form;
		EmployeePersonalInfoDao ad=new EmployeePersonalInfoDao();
		HttpSession session1=request.getSession();
		EssDao ad1=new EssDao();
	
		try
		{
			HttpSession session=request.getSession();		
			UserInfo user=(UserInfo)session.getAttribute("user");
			user.getId();
		 String Id=emppersonalForm.getId();
		 String title=emppersonalForm.getTitle();
		 String firstName=emppersonalForm.getFirstName();
	     String middleName=emppersonalForm.getMiddleName();
		 String lastName=emppersonalForm.getLastName();
		 String initials=emppersonalForm.getInitials();
		 String nickName=emppersonalForm.getNickName();
		 String gender=emppersonalForm.getGender();
		 String maritalStatus=emppersonalForm.getMaritalStatus();
		 String dateofBirth=emppersonalForm.getDateofBirth();
		 String birthplace=emppersonalForm.getBirthplace();
		 String countryofBirth=emppersonalForm.getCountryofBirth();
		 String caste=emppersonalForm.getCaste();
		 String religiousDenomination=emppersonalForm.getReligiousDenomination();
		 String nationality=emppersonalForm.getNationality();
		 String telephoneNumber=emppersonalForm.getTelephoneNumber();
		 String mobileNumber=emppersonalForm.getMobileNumber();
		 String faxNumber=emppersonalForm.getFaxNumber();
		 String emailAddress=emppersonalForm.getEmailAddress();
		 String website=emppersonalForm.getWebsite();
		 String bloodGroup=emppersonalForm.getBloodGroup();
		 String permanentAccountNumber=emppersonalForm.getPermanentAccountNumber();
		 String passportNumber=emppersonalForm.getPassportNumber();
		 String placeofIssueofPassport=emppersonalForm.getPlaceofIssueofPassport();
		 String dateofexpiryofPassport=emppersonalForm.getDateofexpiryofPassport();
		 String personalIdentificationMarks=emppersonalForm.getPersonalIdentificationMarks();
		 String dateofissueofPassport=emppersonalForm.getDateofissueofPassport();
		 String physicallyChallenged=emppersonalForm.getPhysicallyChallenged();
		 String physicallyChallengeddetails=emppersonalForm.getPhysicallyChallengeddetails();
		 String emergencyContactPersonName=emppersonalForm.getEmergencyContactPersonName();
		 String emergencyContactAddressLine1=emppersonalForm.getEmergencyContactAddressLine1();
		 String emergencyContactAddressLine2=emppersonalForm.getEmergencyContactAddressLine2();
		 String emergencyTelephoneNumber=emppersonalForm.getEmergencyTelephoneNumber();
		 String emergencyMobileNumber=emppersonalForm.getEmergencyMobileNumber();
		
		
		 
		 String careofcontactname=emppersonalForm.getCareofcontactname();
		 String houseNumber=emppersonalForm.getHouseNumber();
		 String addressLine1=emppersonalForm.getAddressLine1();
		 String addressLine2=emppersonalForm.getAddressLine2();
		 String addressLine3=emppersonalForm.getAddressLine3();
		 String postalCode=emppersonalForm.getPostalCode();
		 String city=emppersonalForm.getCity();
		 String state=emppersonalForm.getState();
		 String country=emppersonalForm.getCountry();
		 String ownAccomodation=emppersonalForm.getOwnAccomodation();
			 
			 
	     String familyDependentTypeID=emppersonalForm.getFamilyDependentTypeID();
		 String ftitle=emppersonalForm.getFtitle();
		 String ffirstName=emppersonalForm.getFfirstName();
		 String fmiddleName=emppersonalForm.getFmiddleName();
		 String flastName=emppersonalForm.getFlastName();
		 String finitials=emppersonalForm.getFinitials();
		 String fgender=emppersonalForm.getFgender();
		 String fdateofBirth=emppersonalForm.getFdateofBirth();
		 String fbirthplace=emppersonalForm.getFbirthplace();
		 String ftelephoneNumber=emppersonalForm.getFtelephoneNumber();
		 String fmobileNumber=emppersonalForm.getFmobileNumber();
		 String femailAddress=emppersonalForm.getFemailAddress();
		 String fbloodGroup=emppersonalForm.getFbloodGroup();
		 String fdependent=emppersonalForm.getFdependent();
		 String femployeeofCompany=emppersonalForm.getFemployeeofCompany();
		 String femployeeNumber=emppersonalForm.getFemployeeNumber();
		 
		 
		 
		 String educationalLevel=emppersonalForm.getEducationalLevel();
		 String qualification=emppersonalForm.getQualification();
		 String specialization=emppersonalForm.getSpecialization();
		 String universityName=emppersonalForm.getUniversityName();
		 String univerysityLocation=emppersonalForm.getUniverysityLocation();
		 String edstate=emppersonalForm.getEdstate();
		 String edcountry=emppersonalForm.getEdcountry();
		 String durationofCourse=emppersonalForm.getDurationofCourse();
		 String time=emppersonalForm.getTimes();
		 String from=emppersonalForm.getFromDate();
		 String to=emppersonalForm.getToDate();
		 String fullTimePartTime=emppersonalForm.getFullTimePartTime();
		 String percentage=emppersonalForm.getPercentage();
		 
		 
		 
		 String  nameOfEmployer=emppersonalForm.getNameOfEmployer();
		 String  industry=emppersonalForm.getIndustry();
		 String  exCity=emppersonalForm.getExCity();
		 String  excountry=emppersonalForm.getExcountry();
		 String  positionHeld=emppersonalForm.getPositionHeld();
		 String  jobRole=emppersonalForm.getJobRole();
		 String  startDate=emppersonalForm.getStartDate();
		 String  endDate=emppersonalForm.getEndDate();
				
			
		 String  language=emppersonalForm.getLanguage();
		 String  motherTongue=emppersonalForm.getMotherTongue();
		 String  languageFluency=emppersonalForm.getLanguageFluency();
		 String  langstartDate=emppersonalForm.getLangstartDate();
		 
		 
			
		String updatePersonalInfo = "update emp_personal_info set " +
				"title='"+ title+ "', first_name='"+ firstName+ "', middle_name='"+ middleName+"'," +
				"last_name='"+ lastName+ "',initials='"+ initials+ "', nick_name='"+ nickName+ "'," +
				"gender='"+ gender+ "', marital_status='"+ maritalStatus+ "', date_of_birth='"+ dateofBirth+ "'," +
				"birth_place='"+ birthplace+ "', country_of_birth='"+ countryofBirth+ "',caste='"+ caste+ "'," +
				"religous_denomination='"+ religiousDenomination+ "', nationality='"+ nationality+ "', " +
				"telephone_no='"+ telephoneNumber+ "', mobile_no='"+ mobileNumber+ "',fax_no='"+ faxNumber+ "', " +
				"email_address='"+ emailAddress+ "',website='"+ website+ "',blood_group='"+ bloodGroup+ "'," +
				"permanent_acno='"+ permanentAccountNumber+ "',passport_no='"+ passportNumber+ "'," +
				"place_of_issue_of_passport='"+ placeofIssueofPassport+ "',date_of_issue_of_passp='"+ dateofissueofPassport+ "'," +
				"date_of_expiry_of_passport='"+ dateofexpiryofPassport+ "',personal_identification_mark='"+ personalIdentificationMarks+ "'," +
				"physiaclly_challenged='"+ physicallyChallenged+ "',physically_challenged_details='"+ physicallyChallengeddetails+ "'," +
				"emergency_contact_person_name='"+ emergencyContactPersonName+ "',emegency_contact_addressline1='"+ emergencyContactAddressLine1+ "'," +
				"emegency_contact_addressline2='"+ emergencyContactAddressLine2+ "',emegency_telephone_number='"+ emergencyTelephoneNumber+ "'," +
				"emegency_mobile_number='"+ emergencyMobileNumber+ "' " +
				"where user_id='" + user.getId() + "'";
				
		
		
		System.out.println("updatePersonalInfo="+updatePersonalInfo);
		int numPersonInfo=ad1.SqlExecuteUpdate(updatePersonalInfo);
		 
		
			
		if (numPersonInfo > 0) {
			
			
			request.setAttribute("result","Employee's Details Updated Successfully");
		}
			else
			{
				request.setAttribute("result","Error While Updating Employee's Details .. Please check Entered Values");
				
			}
		
		/*String updateAddress = "update emp_address set " +
				"care_of_contact_name='"+ careofcontactname+ "',house_no='"+ houseNumber+ "', " +
				"address_line1'"+ addressLine1+ "',address_line2'"+ addressLine2+ "',address_line3='"+ addressLine3+ "', " +
				"postal_code='"+ postalCode+ "', a_city='"+ city+ "',a_state='"+ state+ "',a_country='"+ country+ "'," +
				" own_accomodation'"+ ownAccomodation+ "',pers_info_id='"+ key+ "'" +
				"where id='" + Id + "'";
					
		int numAddInfo = statement1.executeUpdate(updateAddress);
			
		System.out.println("updateAddress"+updateAddress);
			if (numAddInfo > 0) {
				
				

				session1.setAttribute("result","Employee's Details Updated Successfully");
			}
				else
				{
					session1.setAttribute("result","Error While Updating Employee's Details .. Please check Entered Values");
					
				}
			String updateFamilyDetails = "update emp_family_details set " +
					"family_dependent_type_id='"+ familyDependentTypeID+ "',f_title='"+ ftitle+ "'," +
					"f_first_name='"+ ffirstName+ "',f_middle_name='"+ fmiddleName+ "', f_last_name='"+ flastName+ "'," +
					"f_gender='"+ fgender+ "', f_date_of_birth='"+ fdateofBirth+ "',f_birth_place='"+ fbirthplace+ "'," +
					"f_telephone_no='"+ ftelephoneNumber+ "', f_mobile_no='"+ fmobileNumber+ "', f_email='"+ femailAddress+ "'," +
					"f_blood_group='"+ fbloodGroup+ "', dependent='"+ fdependent+ "', employee_of_company='"+ femployeeofCompany+ "'," +
					"employee_no='"+ femployeeNumber+ "', f_initials='"+ finitials+ "',pers_info_id='"+ key+ "'" +
					"where id='" + Id + "'";
				
		
			int numFamilyInfo = statement1.executeUpdate(updateFamilyDetails);

			System.out.println("updateFamilyDetails"+updateFamilyDetails);
		
				if (numFamilyInfo > 0) {
					

					session1.setAttribute("result","Employee's Details Updated Successfully");
				}
					else
					{
						session1.setAttribute("result","Error While Updating Employee's Details .. Please check Entered Values");
						
					}
				
				String updateEducationDetails = "update emp_education_details set " +
						"education_level='"+ educationalLevel+ "', qualification='"+ qualification+ "', specialization='"+ specialization+ "', " +
						"univarsity_name='"+ universityName+ "',university_location='"+ univerysityLocation+ "'," +
						" e_state='"+ edstate+ "',e_country='"+ edcountry+ "', duration_of_course='"+ durationofCourse+ "'," +
						"time='"+ time+ "',from_date='"+ from+ "',to_date='"+ to+ "', fulltime_parttime='"+ fullTimePartTime+ "'," +
						"percentage='"+ percentage+ "',pers_info_id='"+ key+ "'" +
						"where id='" + Id + "'";
			
				int numEducationInfo = statement1.executeUpdate(updateEducationDetails);
					
				System.out.println("updateEducationDetails"+updateEducationDetails);
				
					if (numEducationInfo > 0) {
						

						session1.setAttribute("result","Employee's Details Updated Successfully");
					}
						else
						{
							session1.setAttribute("result","Error While Updating Employee's Details .. Please check Entered Values");
							
						}
				
					String updateExperience = "update emp_experience_details set" +
							" name_of_employer='"+ nameOfEmployer+ "',industry='"+ industry+ "', ex_city='"+ exCity+ "', " +
							"ex_country='"+ excountry+ "',position_held='"+ positionHeld+ "',job_role='"+ jobRole+ "'," +
							"start_date='"+ startDate+ "', end_date='"+ endDate+ "',pers_info_id='"+ key+ "'" +
							"where id='" + Id + "'";
			
						
					int numExperienceInfo = statement1.executeUpdate(updateExperience);
					
					System.out.println("updateExperience"+updateExperience);
						if (numExperienceInfo > 0) {
							

							session1.setAttribute("result","Employee's Details Updated Successfully");
						}
							else
							{
								session1.setAttribute("result","Error While Updating Employee's Details .. Please check Entered Values");
								
							}
						
						String updateLanguage = "update emp_language_details set " +
								"language='"+ language+ "',mother_tongue='"+ motherTongue+ "', language_fluency='"+ languageFluency+ "', " +
								"l_start_date='"+ langstartDate+ "',pers_info_id='"+ key+ "'" +
								"where id='" + Id + "'";
							
						
						int numLangInfo = statement1.executeUpdate(updateLanguage);
						System.out.println("updateLanguage"+updateLanguage);
						//insertPersonalInfo+="\n"+insertAddress+insertFamilyDetails+insertEducationDetails+insertExperience+insertLanguage;
					
							if (numLangInfo > 0) {
								

								session1.setAttribute("result","Employee's Details Updated Successfully");
							}
								else
								{
									session1.setAttribute("result","Error While Updating Employee's Details .. Please check Entered Values");
									
								}
					*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	request.setAttribute("modifyButton", "modifyButton");
	return mapping.findForward("display1");
	}
}
