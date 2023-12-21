package com.example.salesforce.Database;

import android.content.Context;
import android.database.Cursor;

import com.example.salesforce.Models.AddAttandancecls;
import com.example.salesforce.Models.AddVisitcls;
import com.example.salesforce.Models.Add_TA_Cls;
import com.example.salesforce.Models.Logincls;
import com.example.salesforce.Web.Webservicerequest;
import com.example.salesforce.adaptor.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GetData {

    Databaseutill db;

    Webservicerequest encrypt;
    Context cnt;

    public GetData(Databaseutill data, Context cv) {

        db = data;

        encrypt = new Webservicerequest();

        cnt = cv;
    }

    // Loginclass
    public String Logincls1(String loginid, String password, String lat, String lng, String btlvl) {
        String retval = "0";
//9753083863 //EvSNUlJbFToEsjPmN09UYQ== //Lenovo/Kraft-A6000/Kraft-A6000:5.0.2/LRX22G/Kraft-A6000_S058_151030:user/release-keys
        try {
            Logincls obj = new Logincls(loginid, password, lat, lng, btlvl);

            retval = encrypt.MobileWebservice(cnt, "Login", obj);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retval;
    }




    public String Visit_Add(String store_id, String store_name, String name, String mobile_number, String state,String district,String  resionofvisit,
                            String crteatedate,String activitydatedate,String remarks,String Createby,String updatestatus) {
        String retval = "0";
        ArrayList<AddVisitcls> cls=new ArrayList<AddVisitcls>();
        try {
            AddVisitcls obj = new AddVisitcls(store_id, store_name, name, mobile_number, state,district,resionofvisit,crteatedate,activitydatedate,
                    remarks,Createby,updatestatus );
            cls.add(obj);

            retval = encrypt.Add_retailer_Visit(cnt, "Createretailers", cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }



    public String Attandance_Add(String createdate, String punchdate, String remark, String createdby, String status) {
        String retval = "0";
        ArrayList<AddAttandancecls> cls=new ArrayList<AddAttandancecls>();
        try {
            AddAttandancecls obj = new AddAttandancecls(createdate, punchdate, remark, createdby, status);
            cls.add(obj);

            retval = encrypt.Add_Attandance(cnt, "CreateAttendance", cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }



    public String Add_TA(String TA_EMP_ID, String TA_DATE, String POST_DATE, String PLACE_FROM, String PLACE_TO, String OPEN_MTR,
                         String CLOSE_MTR, String TAX_RS, String FARE_RS, String MISC_RS, String LOCAL_CONVEYANCE,
                         String POSTAGE_RS, String PRINTING_RS, String REMARKS, String PLACE_TO1, String PLACE_TO2,
                         String PLACE_TO3, String PLACE_TO4, String DAILY_DA,String Daily_TA,String UPLOAD_1,String UPLOAD_2,
                         String UPLOAD_3,String UPLOAD_4,String UPLOAD_5,String UPLOAD_6,String UPLOAD_7,String UPLOAD_8,String UPLOAD_9) {
        String retval = "0";
        ArrayList<Add_TA_Cls> cls=new ArrayList<Add_TA_Cls>();
        try {
            Add_TA_Cls obj = new Add_TA_Cls(TA_EMP_ID, TA_DATE, POST_DATE,PLACE_FROM, PLACE_TO, OPEN_MTR,CLOSE_MTR,
                    TAX_RS,FARE_RS,MISC_RS,LOCAL_CONVEYANCE,POSTAGE_RS,PRINTING_RS,REMARKS,PLACE_TO1,PLACE_TO2,PLACE_TO3,
                    PLACE_TO4,DAILY_DA, Daily_TA,UPLOAD_1,UPLOAD_2,UPLOAD_3,UPLOAD_4,UPLOAD_5,UPLOAD_6,UPLOAD_7,UPLOAD_8,UPLOAD_9);
            
            cls.add(obj);

            retval = encrypt.Add_TA(cnt, "CreateDailyTA", cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }


//    public String Get_Ta_Price(String TA_EMP_ID) {
//        String retval = "0";
//        ArrayList<Ta_Price_cls> cls=new ArrayList<Ta_Price_cls>();
//        try {
//            Ta_Price_cls obj = new Ta_Price_cls(TA_EMP_ID);
//
//            cls.add(obj);
//
//            retval = encrypt.Get_ta_price(cnt, "GetTAPrice", cls);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return retval;
//    }



    public ArrayList<String> getEmpid() {
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            db.openDataBase();
            // read from local tables
            String query = "select * from mas_emp";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    //	int i = cur.getColumnCount();
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {

                        returnval.add((cur.getString(colCount)));

                    }
                    cur.moveToNext();
                }
                cur.close();

            }
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return returnval;
    }


    /*public void setLogin(String EMP_ID, String LOGIN_ID, String PASSWORD,
                         String districtId) {
        try {

            db.createDatabase();
            //Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            // read from local tables

            String query1 = "create table login (EMP_ID varchar(50), LOGIN_ID varchar(50), PASSWORD varchar(50), districtId varchar(50))";

            db.execQuery(query1);
            String query = "insert into login (EMP_ID,LOGIN_ID,PASSWORD,districtId) values ('"
                    + EMP_ID
                    + "','"
                    + LOGIN_ID
                    + "','"
                    + PASSWORD
                    + "','"
                    + districtId
                    + "')";

            db.execQuery(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/


    public String insertLocalRetailer(String id,String store_id, String store_name, String name,
                         String mobile_number,String state,String district,String resionofvisit,
                                   String crteatedate,String activitydatedate,String remarks,String Createby,String updatestatus) {
        try {

            db.createDatabase();
            //Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            String query = "insert into retailervisit (ID,store_id,store_name,name,mobile_number,state,district," +
                    "resionofvisit,crteatedate,activitydatedate,remarks,Createby,updatestatus) values ('"
                    +id
                    +"','"
                    + store_id
                    + "','"
                    + store_name
                    + "','"
                    + name
                    + "','"
                    + mobile_number
                    + "','"
                    + state
                    + "','"
                    + district
                    + "','"
                    + resionofvisit
                    + "','"
                    + crteatedate
                    + "','"
                    + activitydatedate
                    + "','"
                    + remarks
                    + "','"
                    + Createby
                    + "','"
                    + updatestatus
                    + "')";

            db.execQuery(query);
            db.close();
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }



    public void insRetailer(String store_id, String store_name, String name,
                         String mobile_number,String state,String district,String userid) {
        try {

            db.createDatabase();
            //Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            // read from local tables

            String query = "insert into mas_retailer (store_id,store_name,name,mobile_number,state,district,user_id) values ('"
                    + store_id
                    + "','"
                    + store_name
                    + "','"
                    + name
                    + "','"
                    + mobile_number
                    + "','"
                    + state
                    + "','"
                    + district
                    + "','"
                    + userid
                    + "')";

            db.execQuery(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addAttendance(String ID, String punchdate, String status) {
        try {

            db.createDatabase();
            //Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            // read from local tables

            String query = "insert into mas_attendance (ID,punchdate,status) values ('"
                    + ID
                    + "','"
                    + punchdate
                    + "','"
                    + status
                    + "')";

            db.execQuery(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<Search> getNumber(String number) {
        List<Search> data = new ArrayList<Search>();


        try {
            ArrayList<String> result = new ArrayList<String>();
            db.openDataBase();
//            String query = "select store_name,mobile_number from mas_retailer where mobile_number LIKE '%"+number+"%'";
            String query = "select store_name,mobile_number from mas_retailer";
            Cursor cur = null;
            cur   = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount += 1) {
                        result.add(cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();
            for (int icount = 0; icount < result.size(); icount += 2) {
//                data.add(result.get(icount+1)+", "+result.get(icount));
                data.add(new Search(result.get(icount+1), result.get(icount)));
            }


        } catch (Exception er) {
            er.getMessage();
        }
        return data;
    }

    public ArrayList<String> getDetails(String number) {
        ArrayList<String> data = new ArrayList<String>();


        try {
            ArrayList<String> result = new ArrayList<String>();
            db.openDataBase();
            String query = "select store_id,store_name,name,mobile_number,state,district from mas_retailer where mobile_number LIKE '%"+number+"%'";
            Cursor cur = null;
            cur   = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount += 1) {
                        result.add(cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();
            for (int icount = 0; icount < result.size(); icount += 6) {
                data.add(result.get(icount));
                data.add(result.get(icount+1));
                data.add(result.get(icount+2));
                data.add(result.get(icount+3));
                data.add(result.get(icount+4));
                data.add(result.get(icount+5));
            }

        } catch (Exception er) {
            er.getMessage();
        }
        return data;
    }






    public ArrayList<String> getLoginid() {
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            //Databaseutill db=        Databaseutill.getDBAdapterInstance(context);
            db.openDataBase();
            //read from local tables
            String query = "select LOGIN_ID from mas_emp";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval.add(cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return returnval;
    }


    public ArrayList<String> getPassword() {
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            db.openDataBase();
            // read from local tables
            String query = "select PASSWORD from mas_emp";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval.add(cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return returnval;
    }


    public ArrayList<HashMap<String, String>> get_visit_reason() {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            //Databaseutill db=        Databaseutill.getDBAdapterInstance(context);
            db.openDataBase();
            //read from local tables
            String query = "select sid,reason from mas_visitreason";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval.add(cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();


            for (int icount = 0; icount < returnval.size(); icount += 2) {
                HashMap<String, String> val = new HashMap<String, String>();
                val.put("1", (returnval.get(icount)));
                val.put("2", (returnval.get(icount + 1)));

                data.add(val);

            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return data;
    }




    public ArrayList<String> getLastUser_id() {
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            db.openDataBase();
            // read from local tables
            String query = "SELECT user_id FROM mas_retailer order by cast(user_id as int) desc LIMIT 1";
            Cursor cur = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    //	int i = cur.getColumnCount();
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval.add((cur.getString(colCount)));
                    }
                    cur.moveToNext();
                }
                cur.close();

            }
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return returnval;
    }


    public String getLastAttendanceId() {
        String returnval= "0";
        try {
            db.openDataBase();
            // read from local tables
            String query = "select ID from mas_attendance order by cast(ID as int) desc LIMIT 1";
            Cursor cur = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    //	int i = cur.getColumnCount();
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval= (cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();

            }
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return returnval;
    }





    public ArrayList<String> lastSixVisit() {
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            db.openDataBase();
            // read from local tables
            String query = "select store_name,district,state,crteatedate from retailervisit order by cast(ID as int) desc LIMIT 6";
            Cursor cur = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    //	int i = cur.getColumnCount();
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval.add((cur.getString(colCount)));
                    }
                    cur.moveToNext();
                }
                cur.close();

            }
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return returnval;
    }


    public String Lastid() {
        String returnval= "0";
        try {
            db.openDataBase();
            // read from local tables
            String query = "select ID from retailervisit order by cast(ID as int) desc LIMIT 1";
            Cursor cur = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    //	int i = cur.getColumnCount();
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval= (cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();

            }
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return returnval;
    }



    public String Visit(String month) {
        String returnval= "0";
        try {
            db.openDataBase();
            // read from local tables
            String query = "SELECT  Count(*) FROM  retailervisit WHERE  crteatedate like "+"\"%"+month+"%\"";
            Cursor cur = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    //	int i = cur.getColumnCount();
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval= (cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();

            }
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return returnval;
    }


    public String attandence_count(String month,String status) {
        String returnval= "0";
        try {
            db.openDataBase();
            // read from local tables
            String query = "SELECT  Count(*) FROM  mas_attendance WHERE status = "+status+" AND punchdate like "+"\"%"+month+"%\"";
            Cursor cur = db.selectData(query);

            if (cur != null) {
                cur.moveToFirst();
                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    //	int i = cur.getColumnCount();
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        returnval= (cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();

            }
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return returnval;
    }


    public ArrayList<HashMap<String, String>> getAttendanceField() {
        String query = "";
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        try {

            ArrayList<String> result = new ArrayList<String>();

            db.openDataBase();
            query="select ID,punchdate,status from mas_attendance ORDER by cast(ID as int) desc LIMIT 90";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount += 1) {
                        result.add(cur.getString(colCount));
                    }
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();

            for (int icount = 0; icount < result.size(); icount += 3) {
                HashMap<String, String> val = new HashMap<String, String>();
                val.put("1", (((result.get(icount)))));
                val.put("2", (((result.get(icount + 1)))));
                val.put("3", (((result.get(icount + 2)))));
                data.add(val);

            }

        } catch (Exception er) {
            er.getMessage();
        }
        return data;
    }



    /*public void alter_Table() {

        try {
            db.openDataBase();
            // read from local tables
            String query = "DROP TABLE mas_retailer";
            String query1 = "CREATE TABLE mas_retailer\n" +
                    "( ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  store_id text,store_name text,name text\n" +
                    ",mobile_number text,state text,district text,user_id text,statusl text\n" +
                    ")";
            db.selectData(query);
            db.selectData(query1);
            db.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }*/
}