package com.example.salesforce.Models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class AddAttandancecls implements KvmSerializable {

	String createdate;
	String punchdate;
	String remark;
	String createdby;
	String status;

	public AddAttandancecls(String createdate, String punchdate, String remark, String createdby, String status) {

		this.createdate = createdate;
		this.punchdate = punchdate;
		this.remark=remark;
		this.createdby=createdby;
		this.status=status;
	}



	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "createdate";
				break;
			case 1:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "punchdate";
				break;

			case 2:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "remark";
				break;
			case 3:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "createdby";
				break;

			case 4:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "status";
				break;
			default:
				break;
		}
	}

	public void setProperty(int index, Object value) {
		switch (index) {
			case 0:
				createdate = value.toString();
				break;
			case 1:
				punchdate = value.toString();
				break;
			case 2:
				remark = value.toString();
				break;
			case 3:
				createdby = value.toString();
				break;
			case 4:
				status = value.toString();
				break;
			default:
				break;
		}
	}

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
			case 0:
				return createdate;
			case 1:
				return punchdate;

			case 2:
				return remark;
			case 3:
				return createdby;
			case 4:
				return status;

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