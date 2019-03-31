package com.kingtous.wifilocate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class SQLiteClient {
    //数据库配置信息
    public static String databaseName="signMessage";
    public static int version=1;
    public static String table="signMessage";
    String createbook;
    String insertData;
    //数据库工具类
    mySQLiteHelper openHelper;
    SQLiteDatabase database;
    Context context;

    SQLiteClient(Context context){
        this.context=context;
        initOpenHelper();
    }

    void initOpenHelper(){
        openHelper=new mySQLiteHelper(context,databaseName,null,version);
    }

    ArrayList<WiFiRecordModel> onQuery(){
        String query="select * from "+table;
        database=openHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        ArrayList<WiFiRecordModel> wifiArray=new ArrayList<>();
        while (cursor.moveToNext()){
            WiFiRecordModel model=new WiFiRecordModel();
            model.setWifiName(cursor.getString(cursor.getColumnIndex("WiFiName")));
            model.setBssid(cursor.getString(cursor.getColumnIndex("BSSID")));
            model.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            model.setPosition(cursor.getString(cursor.getColumnIndex("Position")));
            wifiArray.add(model);
        }
        cursor.close();
        return wifiArray;
    }

    boolean isCorrectWiFi(String wifiName,String bssid){
        return context.getString(R.string.wifi).equals(wifiName) && context.getString(R.string.bssid).equals(bssid);
    }

    boolean Sign(String wifiName,String bssid,String Date,String position){
        if (isCorrectWiFi(wifiName,bssid)){
            insertData="insert into "+table+"(WiFiName,BSSID,Date,Position) values ('" +
                    wifiName +
                    "','" +
                    bssid +
                    "','" +
                    Date +
                    "','" +
                    position+
                    "')";
            database=openHelper.getWritableDatabase();
            if (database!=null){
                database.execSQL(insertData);
                return true;
            }
            return false;
        }
        else return false;
    }








}
