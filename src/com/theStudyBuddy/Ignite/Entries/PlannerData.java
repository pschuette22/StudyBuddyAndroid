package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;
import java.util.Calendar;

import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Classes.ClassObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class PlannerData extends SQLiteOpenHelper
{
  StudyBuddyApplication StudyBuddy;
  String TAG = "TAG";
  String ALL = "All";

  private static final String DATABASE_NAME = "plannerdb";
  private static final int DATABASE_VERSION = 1;

  public static final String ENTRY_ID = BaseColumns._ID;
  public static final String ENTRY_TYPE = "EntryTypePD";
  public static final String ENTRY_CLASS = "EntryClassPD";
  public static final String ENTRY_DATE = "EntryDatePD";
  public static final String ENTRY_TEXT = "EntryTextPD";
  public static final String ENTRY_DAYID = "EntryDayId";
  public static final String TABLE_NAME_PLANNER = "Planner";
  public static final String SELECTED = "SelectedPD";
  public static final String STATUS = "StatusPD";

  public PlannerData(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);

  }

  @Override
  public void onCreate(SQLiteDatabase dbPlanner)
  {

    String sqlPD = "CREATE TABLE " + TABLE_NAME_PLANNER + " (" + ENTRY_ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ENTRY_TYPE
        + " TEXT NOT NULL, " + ENTRY_CLASS + " TEXT NOT NULL, " + ENTRY_DATE
        + " TEXT NOT NULL, " + ENTRY_TEXT + " TEXT NOT NULL, " + ENTRY_DAYID
        + " TEXT, " + SELECTED + " INTEGER, " + STATUS + " INTEGER" + ");";
    dbPlanner.execSQL(sqlPD);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {

  }

  public int EntryAdd(String EntryType, String EntryClass, String EntryText,
      String EntryDate, int EntryDayId)
  {
    
    SQLiteDatabase dbPlanner = this.getWritableDatabase();

    ContentValues values = new ContentValues();

    values.put(ENTRY_TYPE, EntryType);

    values.put(ENTRY_CLASS, EntryClass);

    values.put(ENTRY_DATE, EntryDate);

    values.put(ENTRY_TEXT, EntryText);

    values.put(ENTRY_DAYID, EntryDayId);

    values.put(SELECTED, 0);
    values.put(STATUS, 0);

    long dbId = dbPlanner.insert(TABLE_NAME_PLANNER, null, values);
    dbPlanner.close();
    
    return (int) dbId;
  }

  public ArrayList<EntryObject> getEntries(ClassObject parent)
  {
    ArrayList<EntryObject> entries = new ArrayList<EntryObject>();
    String[] args = {(parent.getTitle())};
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME_PLANNER, null,
        "EntryClassPD=?", args, null, null, ENTRY_DAYID);
    if (cursor != null)
    {
      for (int i = 0; i < cursor.getCount(); i++)
      {
        cursor.moveToPosition(i);

        String enteredText = cursor
            .getString(cursor.getColumnIndex(ENTRY_TEXT));
        String Date = cursor.getString(cursor.getColumnIndex(ENTRY_DATE));
        Log.d("TAG", "Date = " + Date);
        int EntryDayID = cursor.getInt(cursor.getColumnIndex(ENTRY_DAYID));
        int completed = cursor.getInt(cursor.getColumnIndex(STATUS));
        String type = cursor.getString(cursor.getColumnIndex(ENTRY_TYPE));
        int databaseId = cursor.getInt(cursor.getColumnIndex(ENTRY_ID));

        EntryObject newEntry = new EntryObject(enteredText, EntryDayID, parent,
            completed, type, databaseId, Date);
        entries.add(newEntry);
      }
    }
    cursor.close();
    db.close();
    return entries;
  }
  
  public boolean TodayPlanner(){
    SQLiteDatabase dbPD = this.getReadableDatabase();
    Cursor cursor = dbPD.query(TABLE_NAME_PLANNER, null, null,null, null, null, null);
    boolean answer;
    if(cursor.getCount() == 0) answer = false;
    else answer = true;
    cursor.close();
    return answer;
  }
  
  public boolean noEntriesUpcoming(){
    
    StudyBuddyApplication studyBuddy = new StudyBuddyApplication();
    SQLiteDatabase dbPlanner = this.getReadableDatabase();

    for(int i = 1; i <= 3; i++){
      String arg = studyBuddy.todayId(i) + "";
      
      Cursor cursor = dbPlanner.query(TABLE_NAME_PLANNER, null, "EntryDayId = ?", new String[]{arg},
          null, null, null);
      if(cursor.getCount() > 0) return false;
    }
    return true;
  }
  
  public boolean noEntriesToday(){
    StudyBuddyApplication studyBuddy = new StudyBuddyApplication();
    String arg = studyBuddy.todayId(0) + "";
    SQLiteDatabase dbPlanner = this.getReadableDatabase();
    Cursor cursor = dbPlanner.query(TABLE_NAME_PLANNER, null, "EntryDayId = ?", new String[]{arg},
        null, null, null);
    return (cursor.getCount() > 0 ? false:true);
  }

  public void clearPlanner()
  {
    SQLiteDatabase dbPD = this.getWritableDatabase();
    dbPD.delete(TABLE_NAME_PLANNER, null, null);
    dbPD.close();
  }

  public Cursor DeleteCursor()
  {
    SQLiteDatabase dbPlanner = this.getReadableDatabase();
    String Order = ENTRY_CLASS;
    String[] columns = {ENTRY_ID, ENTRY_CLASS, ENTRY_TEXT, SELECTED};
    Cursor cursor = dbPlanner.query(TABLE_NAME_PLANNER, columns, null, null,
        null, null, Order);

    return cursor;
  }

  public Cursor ExistsCheck()
  {
    String[] Columns = {ENTRY_ID};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor Cursor = dbSD.query(TABLE_NAME_PLANNER, Columns, null, null, null,
        null, null);

    return Cursor;
  }

  public void delete_byID(int Id)
  {
    SQLiteDatabase dbPD = this.getWritableDatabase();
    dbPD.delete(TABLE_NAME_PLANNER, ENTRY_ID + "=" + Id, null);
    dbPD.close();
  }

  public void delete_byClass(String Class)
  {
    SQLiteDatabase dbPD = this.getWritableDatabase();
    dbPD.delete(TABLE_NAME_PLANNER, "EntryClassPD =? and EntryTypePD =? ",
        new String[] {Class});
    dbPD.close();
  }

  public void Selected(int Id, int value)
  {
    SQLiteDatabase dbSD = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(SELECTED, value);
    dbSD.update(TABLE_NAME_PLANNER, values, ENTRY_ID + "=" + Id, null);
    dbSD.close();

  }

  public void StatusUpdate(int Id, int Status)
  {
    SQLiteDatabase dbSD = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(STATUS, Status);
    dbSD.update(TABLE_NAME_PLANNER, values, ENTRY_ID + " = " + Id, null);
    dbSD.close();
  }

  public void EntryEdit(int Id, String EntryType, String EntryClass,
      String EntryText, String EntryDate, int DayId)
  {
    SQLiteDatabase dbSD = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(ENTRY_TYPE, EntryType);
    values.put(ENTRY_CLASS, EntryClass);
    values.put(ENTRY_TEXT, EntryText);
    values.put(ENTRY_DATE, EntryDate);
    values.put(ENTRY_DAYID, DayId);
    dbSD.update(TABLE_NAME_PLANNER, values, ENTRY_ID + " = " + Id, null);
    dbSD.close();

  }

  // / Cursor for planner view

  public int todayCount(String type, String Class)
  {
    String TodayId = todaysId(0) + "";
    SQLiteDatabase dbPlanner = this.getReadableDatabase();
    Cursor cursor;
    if (Class.contentEquals(ALL))
    {
      cursor = dbPlanner.query(TABLE_NAME_PLANNER, null,
          "EntryTypePd == ? and EntryDayId == ?", new String[] {type, TodayId},
          null, null, null);

    }
    else
    {
      cursor = dbPlanner.query(TABLE_NAME_PLANNER, null,
          "EntryTypePd == ? and  EntryClassPD == ? and EntryDayId == ?",
          new String[] {type, Class, TodayId}, null, null, null);
    }
    int count = cursor.getCount();
    dbPlanner.close();
    return count;
  }

  public int todaysId(int add)
  {
    int TodayYear;
    int TodayMonth;
    int TodayDay;

    final Calendar Today = Calendar.getInstance();
    Today.add(Calendar.DATE, add);

    TodayYear = Today.get(Calendar.YEAR)*10000;
    TodayMonth = Today.get(Calendar.MONTH)*100;
    TodayDay = Today.get(Calendar.DAY_OF_MONTH);
    
    return (TodayYear + TodayMonth + TodayDay);
  }

}
