package com.example.salesforce.Models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Logincls implements KvmSerializable {

	private String loginid;
	private String password;
	String lat;
	String lng;
	String verdtl;

	public Logincls(String loginid, String password, String lat, String lng, String btlvl) {

		this.loginid = loginid;
		this.password = password;
		this.lat=lat;
		this.lng=lng;
		this.verdtl=btlvl;
	}

	
	
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "loginid";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "password";
			break;

		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "lat";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "lng";
			break;		

		case 4:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "verdtl";
			break;				
				default:
			break;
		}
	}

	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			loginid = value.toString();
			break;
		case 1:
			password = value.toString();
			break;
		case 2:
			lat = value.toString();
			break;
		case 3:
			lng = value.toString();
			break;
		case 4:
			verdtl = value.toString();
			break;		
				default:
			break;
		}
	}

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
		case 0:
			return loginid;
		case 1:
			return password;
			
		case 2:
			return lat;
		case 3:
			return lng;
		case 4:
			return verdtl;
			
			default:
			break;
		}

		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	public String getInnerText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInnerText(String arg0) {
		// TODO Auto-generated method stub
		
	}

}