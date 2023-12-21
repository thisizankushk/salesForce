package com.example.salesforce.Models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Add_TA_Cls implements KvmSerializable {

	String TA_EMP_ID;
	String TA_DATE;
	String POST_DATE;
	String PLACE_FROM;
	String PLACE_TO;
	String OPEN_MTR;
	String CLOSE_MTR;
	String TAX_RS;
	String FARE_RS;
	String MISC_RS;
	String LOCAL_CONVEYANCE;
	String POSTAGE_RS;
	String PRINTING_RS;
	String REMARKS;
	String PLACE_TO1;
	String PLACE_TO2;
	String PLACE_TO3;
	String PLACE_TO4;
	String DAILY_DA;
	String DAILY_TA;
	String UPLOAD_1;
	String UPLOAD_2;
	String UPLOAD_3;
	String UPLOAD_4;
	String UPLOAD_5;
	String UPLOAD_6;
	String UPLOAD_7;
	String UPLOAD_8;
	String UPLOAD_9;

	public Add_TA_Cls(String TA_EMP_ID, String TA_DATE, String POST_DATE, String PLACE_FROM, String PLACE_TO, String OPEN_MTR,
					  String CLOSE_MTR, String TAX_RS, String FARE_RS, String MISC_RS, String LOCAL_CONVEYANCE,
					  String POSTAGE_RS, String PRINTING_RS, String REMARKS, String PLACE_TO1, String PLACE_TO2,
					  String PLACE_TO3, String PLACE_TO4, String DAILY_DA,String DAILY_TA,String UPLOAD_1,String UPLOAD_2,
					  String UPLOAD_3,String UPLOAD_4,String UPLOAD_5,String UPLOAD_6,String UPLOAD_7,String UPLOAD_8,String UPLOAD_9) {

		this.TA_EMP_ID = TA_EMP_ID;
		this.TA_DATE = TA_DATE;
		this.POST_DATE =POST_DATE;
		this.PLACE_FROM=PLACE_FROM;
		this.PLACE_TO=PLACE_TO;
		this.OPEN_MTR=OPEN_MTR;
		this.CLOSE_MTR=CLOSE_MTR;
		this.TAX_RS=TAX_RS;
		this.FARE_RS=FARE_RS;
		this.MISC_RS=MISC_RS;
		this.LOCAL_CONVEYANCE=LOCAL_CONVEYANCE;
		this.POSTAGE_RS=POSTAGE_RS;
		this.PRINTING_RS=PRINTING_RS;
		this.REMARKS=REMARKS;
		this.PLACE_TO1=PLACE_TO1;
		this.PLACE_TO2=PLACE_TO2;
		this.PLACE_TO3=PLACE_TO3;
		this.PLACE_TO4=PLACE_TO4;
		this.DAILY_DA=DAILY_DA;
		this.DAILY_TA=DAILY_TA;
		this.UPLOAD_1=UPLOAD_1;
		this.UPLOAD_2=UPLOAD_2;
		this.UPLOAD_3=UPLOAD_3;
		this.UPLOAD_4=UPLOAD_4;
		this.UPLOAD_5=UPLOAD_5;
		this.UPLOAD_6=UPLOAD_6;
		this.UPLOAD_7=UPLOAD_7;
		this.UPLOAD_8=UPLOAD_8;
		this.UPLOAD_9=UPLOAD_9;
	}



	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "TA_EMP_ID";
				break;
			case 1:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "TA_DATE";
				break;

			case 2:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "POST_DATE";
				break;
			case 3:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "PLACE_FROM";
				break;

			case 4:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "PLACE_TO";
				break;

			case 5:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "OPEN_MTR";
				break;
			case 6:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "CLOSE_MTR";
				break;

			case 7:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "TAX_RS";
				break;
			case 8:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "FARE_RS";
				break;

			case 9:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "MISC_RS";
				break;

			case 10:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "LOCAL_CONVEYANCE";
				break;
			case 11:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "POSTAGE_RS";
				break;

			case 12:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "PRINTING_RS";
				break;
			case 13:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "REMARKS";
				break;

			case 14:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "PLACE_TO1";
				break;

			case 15:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "PLACE_TO2";
				break;

			case 16:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "PLACE_TO3";
				break;
			case 17:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "PLACE_TO4";
				break;

			case 18:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "DAILY_DA";
				break;
			case 19:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "DAILY_TA";
				break;
			case 20:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_1";
				break;
			case 21:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_2";
				break;
			case 22:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_3";
				break;
			case 23:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_4";
				break;
			case 24:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_5";
				break;
			case 25:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_6";
				break;
			case 26:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_7";
				break;
			case 27:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_8";
				break;
			case 28:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "UPLOAD_9";
				break;
			default:
				break;
		}
	}

	public void setProperty(int index, Object value) {
		switch (index) {
			case 0:
				TA_EMP_ID = value.toString();
				break;
			case 1:
				TA_DATE = value.toString();
				break;
			case 2:
				POST_DATE = value.toString();
				break;
			case 3:
				PLACE_FROM = value.toString();
				break;
			case 4:
				PLACE_TO = value.toString();
				break;
			case 5:
				OPEN_MTR = value.toString();
				break;
			case 6:
				CLOSE_MTR = value.toString();
				break;
			case 7:
				TAX_RS = value.toString();
				break;
			case 8:
				FARE_RS = value.toString();
				break;
			case 9:
				MISC_RS = value.toString();
				break;
			case 10:
				LOCAL_CONVEYANCE = value.toString();
				break;
			case 11:
				POSTAGE_RS = value.toString();
				break;
			case 12:
				PRINTING_RS = value.toString();
				break;
			case 13:
				REMARKS = value.toString();
				break;
			case 14:
				PLACE_TO1 = value.toString();
				break;
			case 15:
				PLACE_TO2 = value.toString();
				break;
			case 16:
				PLACE_TO3 = value.toString();
				break;
			case 17:
				PLACE_TO4 = value.toString();
				break;
			case 18:
				DAILY_DA = value.toString();
				break;
			case 19:
				DAILY_TA = value.toString();
				break;
			case 20:
				UPLOAD_1 = value.toString();
				break;
			case 21:
				UPLOAD_2 = value.toString();
				break;
			case 22:
				UPLOAD_3 = value.toString();
				break;
			case 23:
				UPLOAD_4 = value.toString();
				break;
			case 24:
				UPLOAD_5 = value.toString();
				break;
			case 25:
				UPLOAD_6 = value.toString();
				break;
			case 26:
				UPLOAD_7 = value.toString();
				break;
			case 27:
				UPLOAD_8 = value.toString();
				break;
			case 28:
				UPLOAD_9 = value.toString();
				break;
			default:
				break;
		}
	}

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
			case 0:
				return TA_EMP_ID;
			case 1:
				return TA_DATE;
			case 2:
				return POST_DATE;
			case 3:
				return PLACE_FROM;
			case 4:
				return PLACE_TO;
			case 5:
				return OPEN_MTR;
			case 6:
				return CLOSE_MTR;
			case 7:
				return TAX_RS;
			case 8:
				return FARE_RS;
			case 9:
				return MISC_RS;
			case 10:
				return LOCAL_CONVEYANCE;
			case 11:
				return POSTAGE_RS;
			case 12:
				return PRINTING_RS;
			case 13:
				return REMARKS;
			case 14:
				return PLACE_TO1;
			case 15:
				return PLACE_TO2;
			case 16:
				return PLACE_TO3;
			case 17:
				return PLACE_TO4;
			case 18:
				return DAILY_DA;
			case 19:
				return DAILY_TA;
			case 20:
				return UPLOAD_1;
			case 21:
				return UPLOAD_2;
			case 22:
				return UPLOAD_3;
			case 23:
				return UPLOAD_4;
			case 24:
				return UPLOAD_5;
			case 25:
				return UPLOAD_6;
			case 26:
				return UPLOAD_7;
			case 27:
				return UPLOAD_8;
			case 28:
				return UPLOAD_9;
			default:
				break;
		}

		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 29;
	}

	public String getInnerText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInnerText(String arg0) {
		// TODO Auto-generated method stub

	}

}