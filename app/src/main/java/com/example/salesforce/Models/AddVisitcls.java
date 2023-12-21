package com.example.salesforce.Models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class AddVisitcls implements KvmSerializable {

	String store_id;
	String store_name;
	String name;
	String mobile_number;
	String state;
	String district;
	String  resionofvisit;
	String crteatedate;
	String activitydatedate;
	String remarks;
	String Createby;
	String updatestatus;

	public AddVisitcls(){

	}

	public AddVisitcls(String store_id, String store_name, String name, String mobile_number, String state,String district,String  resionofvisit,
					   String crteatedate,String activitydatedate,String remarks,String Createby,String updatestatus) {

		this.store_id = store_id;
		this.store_name = store_name;
		this.name=name;
		this.mobile_number=mobile_number;
		this.state=state;
		this.district=district;
		this.resionofvisit=resionofvisit;
		this.crteatedate=crteatedate;
		this.activitydatedate=activitydatedate;
		this.remarks=remarks;
		this.Createby=Createby;
		this.updatestatus=updatestatus;
	}

	
	
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "store_id";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "store_name";
			break;

		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "name";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "mobile_number";
			break;		

		case 4:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "state";
			break;

		case 5:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "district";
			break;

		case 6:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "resionofvisit";
			break;

		case 7:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "crteatedate";
			break;

		case 8:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "activitydatedate";
			break;

		case 9:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "remarks";
			break;

		case 10:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "Createby";
			break;

		case 11:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "updatestatus";
			break;
				default:
			break;
		}
	}

	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			store_id = value.toString();
			break;
		case 1:
			store_name = value.toString();
			break;
		case 2:
			name = value.toString();
			break;
		case 3:
			mobile_number = value.toString();
			break;
		case 4:
			state = value.toString();
			break;
		case 5:
			district = value.toString();
			break;
		case 6:
			resionofvisit = value.toString();
			break;
		case 7:
			crteatedate = value.toString();
			break;
		case 8:
			activitydatedate = value.toString();
			break;
		case 9:
			remarks = value.toString();
			break;
		case 10:
			Createby = value.toString();
			break;
		case 11:
			updatestatus = value.toString();
			break;
				default:
			break;
		}
	}

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
		case 0:
			return store_id;
		case 1:
			return store_name;
			
		case 2:
			return name;
		case 3:
			return mobile_number;
		case 4:
			return state;
		case 5:
			return district;
		case 6:
			return resionofvisit;
		case 7:
			return crteatedate;
		case 8:
			return activitydatedate;
		case 9:
			return remarks;
		case 10:
			return Createby;
		case 11:
			return updatestatus;
			
			default:
			break;
		}

		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 12;
	}

	public String getInnerText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInnerText(String arg0) {
		// TODO Auto-generated method stub
		
	}

}