package com.theStudyBuddy.Ignite.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;

public class HolidayData extends SQLiteOpenHelper
{
  public static final String DATABASE_NAME = "HolidayDatabase";
  public static final String HOLIDAY_TABLE = "HolidayTable";
  public static final int DATABASE_VERSION = 1;
  
  public static final String HOLIDAY_ID = BaseColumns._ID;
  public static final String HOLIDAY_TITLE = "HolidayTitle";
  public static final String HOLIDAY_DAYID = "HolidayDayId";
  
  public HolidayData(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public void onCreate(SQLiteDatabase db)
  {
    String sqlSetData = "CREATE TABLE " + HOLIDAY_TABLE + " (" + HOLIDAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
         + HOLIDAY_TITLE + " TEXT NOT NULL, " + HOLIDAY_DAYID + " INTEGER);";
    db.execSQL(sqlSetData);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    // no updates yet
  }

  public int addHoliday(int dayId){
    SQLiteDatabase db = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(HOLIDAY_TITLE, "N/A");
    values.put(HOLIDAY_DAYID, dayId);
    long rowId = db.insert(HOLIDAY_TABLE, null, values);
    db.close();
    return (int) rowId;
  }
  

  
  public ArrayList<HolidayObject> getHolidayIds(int todayId){
    SQLiteDatabase db = getReadableDatabase();
    Cursor dayIds = db.query(HOLIDAY_TABLE, null,null,null,null, null, null);
    ArrayList<HolidayObject> holidays = new ArrayList<HolidayObject>();
    
    for(int i = 0; i < dayIds.getCount(); i++){
      dayIds.moveToPosition(i);
      String title = dayIds.getString(dayIds.getColumnIndex(HOLIDAY_TITLE));

      int dayId = dayIds.getInt(dayIds.getColumnIndex(HOLIDAY_DAYID));
      int dbID = dayIds.getInt(dayIds.getColumnIndex(HOLIDAY_ID));
      
      if((dayId/100) < (todayId/100)){
        deleteHoliday(dbID);
      }
      
      else{
        
      HolidayObject holiday = new HolidayObject(dbID, dayId, title);
      holidays.add(holiday);
      
      }
      
    }
    
    Collections.sort(holidays, new sortThem());
    
    return holidays;
  }
  
  private class sortThem implements Comparator<HolidayObject>{

    public int compare(HolidayObject lhs, HolidayObject rhs)
    {
      return lhs.getDayId() - rhs.getDayId();
    }
    
  }
  
  
  public void deleteHoliday(int rowNumber){
      new deleteByDBID(rowNumber).execute();
  }
  
  private class deleteByDBID extends AsyncTask<Void, Void, Void>{

    private int rowNumber;
    
    public deleteByDBID(int rowNumber_){
      rowNumber = rowNumber_;
    }
    
    protected Void doInBackground(Void... arg0)
    {
      
      SQLiteDatabase db = getWritableDatabase();
      db.delete(HOLIDAY_TABLE, HOLIDAY_ID + " =? ", new String[] {rowNumber + ""});
      db.close();
      return null;
    }
    
  }
  
}
