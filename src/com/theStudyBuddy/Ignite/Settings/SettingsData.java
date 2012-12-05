package com.theStudyBuddy.Ignite.Settings;

import java.util.ArrayList;
import java.util.Calendar;

import com.theStudyBuddy.Ignite.StudyBuddyApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class SettingsData extends SQLiteOpenHelper
{
  StudyBuddyApplication StudyBuddy;

  private static final String DATABASE_NAME = "settingsdb";
  private static final int DATABASE_VERSION = 3;

  public static final String SETTINGS_TABLE_NAME = "StudyBuddySettings";
  public static final String SETTINGS_ID = BaseColumns._ID;
  public static final String SETTINGS_TYPE = "SettingsTypeSD";
  public static final String SETTINGS_DESCRIPTION = "SettingsDescSD";
  public static final String SETTINGS_VALUE = "SettingsValueSD";
  
  public SettingsData(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  
  }

  
  @Override
  public void onCreate(SQLiteDatabase dbSettings)
  {

    String sqlSetData = "CREATE TABLE " + SETTINGS_TABLE_NAME + " ("
        + SETTINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SETTINGS_TYPE
        + " TEXT NOT NULL, " + SETTINGS_DESCRIPTION + " TEXT NOT NULL, "
        + SETTINGS_VALUE + " INTEGER" + ");";
    dbSettings.execSQL(sqlSetData);
    
    createValues(dbSettings);
    
    InsertPlannerSettings2(dbSettings);
    
    InsertPlannerSettings3(dbSettings);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    if(oldVersion < 2){
      InsertPlannerSettings2(db);
    }
    if(oldVersion < 3){
      InsertPlannerSettings3(db);
    }
    
  }
  
  public void InsertPlannerSettings2(SQLiteDatabase db){
    
    insertSD(db,"PlannerTypes", "Assignments",1);
    insertSD(db,"PlannerTypes", "Events", 1);
    insertSD(db,"PlannerTypes", "Reminders",1);
    insertSD(db,"PlannerDates", "Dates", 1);
    insertSD(db,"General", "First Load", 0);      
  }
  
  public void InsertPlannerSettings3(SQLiteDatabase db){
    insertSD(db,"HolidayNotification", "SendNightlyOnHolidays", 1); 
    insertSD(db,"HolidayNotification", "SendMorningOnHolidays", 0); 
    insertSD(db,"General", "First Load", 0); 
    
  }
  
  public boolean existsCheck(){
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor mcursor = dbSettings.query(SETTINGS_TABLE_NAME,
        new String[] {SETTINGS_TYPE}, null, null, null, null, null);
    int count = mcursor.getCount();
    dbSettings.close();
    
    if(count > 0) return true;
    else return false;
  }
  
  
  /// wtf is this? like why is it named this way?
  public boolean alarmsOn(){
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"First Load"}, null, null, null,
        null);
    cursor.moveToFirst();
    return (cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE)) == 0? true:false);
  }

  
  public void createValues(SQLiteDatabase dbSettings)
  {
    
      insertSD(dbSettings, "Notification", "Notifications", 1);
      insertSD(dbSettings, "Notification", "Planner Assistant", 1);
      insertSD(dbSettings, "Notification", "Morning Notifications", 1);
      insertSD(dbSettings, "Notification", "Nightly Notifications", 1);

  }
  
  // =============== insert and update things =====================
  public void insertSD(SQLiteDatabase dbSettings, String TYPE, String DESCRIPTION, int VALUE)
  {

    ContentValues values = new ContentValues();
    values.put(SETTINGS_TYPE, TYPE);
    values.put(SETTINGS_DESCRIPTION, DESCRIPTION);
    values.put(SETTINGS_VALUE, VALUE);
    dbSettings.insert(SETTINGS_TABLE_NAME, null, values);

  }
  

  public void updateSetting(String Desc, int value)
  {
    SQLiteDatabase dbSD = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(SETTINGS_VALUE, value);
    dbSD.update(SETTINGS_TABLE_NAME, values, "SettingsDescSD=?",
        new String[] {Desc});
    dbSD.close();

  }
  // ===============================================================
  
  
  public boolean notificationsCheck()
  {
    int onCheck;

    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Notifications"}, null, null, null,
        null);
    cursor.moveToFirst();

    onCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    cursor.close();
    return (onCheck == 1? true:false);
  }
  
  public boolean firstTime(){
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"First Load"}, null, null, null,
        null);
    cursor.moveToFirst();
    return (cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE)) == 0? true:false);
  }

  public boolean planAssistCheck()
  {
    int onCheck;
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Planner Assistant"}, null, null,
        null, null);
    cursor.moveToFirst();
    
    onCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    cursor.close();
    return (onCheck == 1? true:false);
  }

  public boolean NightlyCheck()
  {
    int onCheck;
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Nightly Notifications"}, null,
        null, null, null);
    cursor.moveToFirst();

    onCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    cursor.close();

    return (onCheck == 1? true:false);
  }

  public boolean MorningCheck()
  {
    int onCheck;
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Morning Notifications"}, null,
        null, null, null);
    cursor.moveToFirst();

    onCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    cursor.close();
    return (onCheck == 1? true:false);
  }
  
  public boolean MorningOnHolidayCheck()
  {
    int onCheck;
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"SendMorningOnHolidays"}, null,
        null, null, null);
    cursor.moveToFirst();

    onCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    cursor.close();
    return (onCheck == 1? true:false);
  }
  
  public boolean NightlyOnHolidayCheck()
  {
    int onCheck;
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"SendNightlyOnHolidays"}, null,
        null, null, null);
    cursor.moveToFirst();

    onCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    cursor.close();
    return (onCheck == 1? true:false);
  }
  
  public int checker(){

      int onCheck;

      SQLiteDatabase dbSettings = this.getReadableDatabase();
      Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
          null, null, null,
          null, null, null);
      onCheck = cursor.getCount();
      cursor.close();
      return onCheck;
  }
  
  
  // ========== for determining what to display on planner view ===========
  public boolean Assignments(){
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor;
     cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Assignments"}, null,
        null, null, null);
    cursor.moveToFirst();
    int assignCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    return (assignCheck == 1? true:false);
  }
  
  public boolean Events(){
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Events"}, null,
        null, null, null);
    cursor.moveToFirst();
    int assignCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    return (assignCheck == 1? true:false);    
  }
  
  public boolean Reminders(){
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Reminders"}, null,
        null, null, null);
    cursor.moveToFirst();
    int assignCheck = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    return (assignCheck == 1? true:false);    
  }
  
  public boolean notHolidayToday(Context context)
  {
    Calendar today = Calendar.getInstance();
    HolidayData holidayData = new HolidayData(context);
    int year = today.get(Calendar.YEAR) * 10000;
    int month = today.get(Calendar.MONTH) * 100;
    int day = today.get(Calendar.DAY_OF_MONTH);
    
    int todayId = year + month + day;
    ArrayList<HolidayObject> allHolidays = holidayData.getHolidayIds(todayId);
    if (allHolidays.size() <= 0)
      return true;

    else
    {
      for (int i = 0; i < allHolidays.size(); i++)
      {
        int holidayId = allHolidays.get(i).getDayId();
        Log.d("TAG", "Holiday Id = " + holidayId);
        if (todayId == holidayId)
          return false;
        else if (todayId < holidayId)
          break;

      }
    }

    return true;
  }
  
  /// ==============================================================================
  public boolean timeCheck(){
    SQLiteDatabase dbSettings = this.getReadableDatabase();
    Cursor cursor = dbSettings.query(SETTINGS_TABLE_NAME, null,
        "SettingsDescSD =?", new String[] {"Dates"}, null,
        null, null, null);
    cursor.moveToFirst();
    int Check = cursor.getInt(cursor.getColumnIndex(SETTINGS_VALUE));
    if(Check == 1){
      return true;
    }
    else return false;
    
  }
  

}
