package com.theStudyBuddy.Ignite.Classes;

import java.util.ArrayList;

import com.theStudyBuddy.Ignite.StudyBuddyApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class ScheduleData extends SQLiteOpenHelper

{
  private static final String TAG = "TAG";
  StudyBuddyApplication sba;

  private static final String DATABASE_NAME = "scheduledb";

  private static final int DATABASE_VERSION = 1;

  public static final String TABLE_NAME_SCHEDULE = "Schedule";

  public static final String SCHEDULE_ID = BaseColumns._ID;
  public static final String CLASS_NAME = "ClassNameSD";
  public static final String CLASS_DAY = "ClassDaySD";
  public static final String CLASS_START = "ClassStartsSD";
  public static final String CLASS_END = "ClassEndsSD";
  public static final String PLANNER_ASSISTANT = "PlannerAssistantSD";
  public static final String ALARMID = "AlarmIdSD";
  public static final String STARTID = "StartIdSD";
  public static final String SELECTED = "SelectedSD";

  StudyBuddyApplication studyBuddy;

  public ScheduleData(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase dbSD)
  {

    String sqlSD = "CREATE TABLE " + TABLE_NAME_SCHEDULE + " (" + SCHEDULE_ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLASS_NAME
        + " TEXT NOT NULL, " + CLASS_DAY + " TEXT NOT NULL, " + CLASS_START
        + " LONG, " + CLASS_END + " LONG, " + PLANNER_ASSISTANT + " INTEGER, "
        + ALARMID + " INTEGER, " + STARTID + " INTEGER, " + SELECTED
        + " INTEGER" + ");";

    dbSD.execSQL(sqlSD);
  }

  @Override
  public void onUpgrade(SQLiteDatabase dbSD, int oldVersion, int newVersion)

  {
    /*
     * String newColumn = "mColumn"; String upgradeQuery =
     * "ALTER TABLE Schedule ADD COLUMN " + newColumn + " TEXT;"; if (oldVersion
     * < newVersion)dbSD.execSQL(upgradeQuery);
     */
  }

  public int insert(String ClassNameText, String ClassDayText,
      String ClassStartText, String ClassEndText, Integer PlannerAssistantStat,
      Integer AlarmId, Integer StartId)
  {

    SQLiteDatabase dbSD = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put(CLASS_NAME, ClassNameText);
    values.put(CLASS_DAY, ClassDayText);
    values.put(CLASS_START, ClassStartText);
    values.put(CLASS_END, ClassEndText);
    values.put(PLANNER_ASSISTANT, PlannerAssistantStat);
    values.put(ALARMID, AlarmId);
    values.put(STARTID, StartId);
    values.put(SELECTED, 0);

    return (int) dbSD.insert(TABLE_NAME_SCHEDULE, null, values);
  }

  public void updatePlannerAssistant(int Id, int PAStat)
  {

    SQLiteDatabase dbSD = this.getWritableDatabase();

    ContentValues value = new ContentValues();
    value.put(PLANNER_ASSISTANT, PAStat);
    dbSD.update(TABLE_NAME_SCHEDULE, value, SCHEDULE_ID + "=" + Id, null);
    dbSD.close();
  }

  public void Selected(int Id, int value)
  {
    SQLiteDatabase dbSD = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(SELECTED, value);
    dbSD.update(TABLE_NAME_SCHEDULE, values, SCHEDULE_ID + "=" + Id, null);
    dbSD.close();

  }

  public Cursor DeleteCursor()
  {
    String Columns[] = {SCHEDULE_ID, ALARMID, CLASS_NAME, CLASS_DAY,
        PLANNER_ASSISTANT, SELECTED};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor cursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, null, null, null,
        null, CLASS_NAME);
    return cursor;

  }

  public Cursor ClassExistanceCheck(String ClassName)
  {
    String[] Args = {ClassName};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor cursor = dbSD.query(TABLE_NAME_SCHEDULE, null, "ClassNameSD =?",
        Args, null, null, null);

    return cursor;
  }

  public void delete_byID(int Id)
  {
    SQLiteDatabase dbSD = this.getWritableDatabase();
    dbSD.delete(TABLE_NAME_SCHEDULE, SCHEDULE_ID + "=" + Id, null);
    dbSD.close();
  }

  public void clearSchedule()
  {

    SQLiteDatabase dbSD = this.getWritableDatabase();
    dbSD.delete(TABLE_NAME_SCHEDULE, null, null);
    dbSD.close();

  }

  public Cursor ExistsCheck()
  {
    String[] Columns = {PLANNER_ASSISTANT, ALARMID, CLASS_NAME};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor cursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, null, null, null,
        null, null);

    return cursor;
  }

  public ArrayList<MeetingObject> getClassTimes(ClassObject parent)
  {
    ArrayList<MeetingObject> meetings = new ArrayList<MeetingObject>();
    String[] args = {(parent.getTitle())};
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME_SCHEDULE, null, "ClassNameSD=?", args,
        null, null, ALARMID);
    if (cursor != null)
    {
      for (int i = 0; i < cursor.getCount(); i++)
      {
        cursor.moveToPosition(i);
        int scheduleID = cursor.getInt(cursor.getColumnIndex(SCHEDULE_ID));
        String day = cursor.getString(cursor.getColumnIndex(CLASS_DAY));
        String start = cursor.getString(cursor.getColumnIndex(CLASS_START));
        String end = cursor.getString(cursor.getColumnIndex(CLASS_END));
        int PAOn = cursor.getInt(cursor.getColumnIndex(PLANNER_ASSISTANT));
        int alarmId = cursor.getInt(cursor.getColumnIndex(ALARMID));
        int startId = cursor.getInt(cursor.getColumnIndex(STARTID));

        MeetingObject newMeeting = new MeetingObject(parent, scheduleID, day,
            start, end, PAOn, alarmId, startId);
        meetings.add(newMeeting);
      }
    }
    else Log.d("TAG", "Cursor null");
    
    cursor.close();
    return meetings;
  }

  public Cursor MondayData()
  {
    String[] MonArgs = {"Monday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, CLASS_END,
        PLANNER_ASSISTANT, ALARMID, SCHEDULE_ID};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor MondayCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySD=?", MonArgs, null, null, CLASS_START);

    return MondayCursor;

  }

  public Cursor MondayStartCursor()
  {
    String[] Args = {"Monday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, STARTID,
        SCHEDULE_ID};
    Log.d(TAG, "Got into the Cursor ");
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Log.d("TAG", "Got readable");
    Cursor MondayStartCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySd=?", Args, null, null, CLASS_START);
    return MondayStartCursor;
  }

  public Cursor TuesdayData()
  {

    String[] TueArgs = {"Tuesday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, CLASS_END,
        PLANNER_ASSISTANT, ALARMID, SCHEDULE_ID};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor TuesdayCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySD=?", TueArgs, null, null, CLASS_START);
    return TuesdayCursor;

  }

  public Cursor TuesdayStartCursor()
  {
    String[] Args = {"Tuesday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, STARTID,
        SCHEDULE_ID};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor theCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, "ClassDaySd=?",
        Args, null, null, CLASS_START);
    return theCursor;
  }

  public Cursor WednesdayData()
  {

    String[] WedArgs = {"Wednesday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, CLASS_END,
        PLANNER_ASSISTANT, ALARMID, SCHEDULE_ID};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor WednesdayCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySD=?", WedArgs, null, null, CLASS_START);
    return WednesdayCursor;
  }

  public Cursor WednesdayStartCursor()
  {
    String[] Args = {"Wednesday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, STARTID,
        SCHEDULE_ID};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor theCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, "ClassDaySd=?",
        Args, null, null, CLASS_START);
    return theCursor;
  }

  public Cursor ThursdayData()
  {
    String[] ThuArgs = {"Thursday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, CLASS_END,
        PLANNER_ASSISTANT, ALARMID, SCHEDULE_ID};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor ThursdayCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySD=?", ThuArgs, null, null, CLASS_START);

    return ThursdayCursor;
  }

  public Cursor ThursdayStartCursor()
  {
    String[] Args = {"Thursday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, STARTID,
        SCHEDULE_ID};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor theCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, "ClassDaySd=?",
        Args, null, null, CLASS_START);
    return theCursor;
  }

  public Cursor FridayData()
  {
    String[] FriArgs = {"Friday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, CLASS_END,
        PLANNER_ASSISTANT, ALARMID, SCHEDULE_ID};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor FridayCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySD=?", FriArgs, null, null, CLASS_START);

    return FridayCursor;
  }

  public Cursor FridayStartCursor()
  {
    String[] Args = {"Friday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, STARTID,
        SCHEDULE_ID};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor theCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, "ClassDaySd=?",
        Args, null, null, CLASS_START);
    return theCursor;
  }

  public Cursor SaturdayData()
  {
    String[] SatArgs = {"Saturday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, CLASS_END,
        PLANNER_ASSISTANT, ALARMID, SCHEDULE_ID};

    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor SaturdayCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySD=?", SatArgs, null, null, CLASS_START);

    return SaturdayCursor;
  }

  public Cursor SaturdayStartCursor()
  {
    String[] Args = {"Saturday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, STARTID,
        SCHEDULE_ID};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor theCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, "ClassDaySd=?",
        Args, null, null, CLASS_START);
    return theCursor;
  }

  public Cursor SundayData()
  {
    String[] SunArgs = {"Sunday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, CLASS_END,
        PLANNER_ASSISTANT, ALARMID, SCHEDULE_ID};
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Cursor SundayCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns,
        "ClassDaySD=?", SunArgs, null, null, CLASS_START);

    return SundayCursor;
  }

  public Cursor SundayStartCursor()
  {
    Log.d("TAG", "In Cursor");
    String[] Args = {"Sunday"};
    String[] Columns = {CLASS_NAME, CLASS_DAY, CLASS_START, STARTID,
        SCHEDULE_ID};
    Log.d("TAG", "Columns is not the issue");
    SQLiteDatabase dbSD = this.getReadableDatabase();
    Log.d("TAG", "Got readable database");
    Cursor theCursor = dbSD.query(TABLE_NAME_SCHEDULE, Columns, "ClassDaySd=?",
        Args, null, null, CLASS_START);
    Log.d("TAG", "Returned cursor");
    return theCursor;
  }
}
