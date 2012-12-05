package com.theStudyBuddy.Ignite.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class ClassSpinnerData extends SQLiteOpenHelper
{

  public static final String SPINNER_DATABASE_NAME = "ClassSpinnerdb";
  public static final int SPINNER_DATABASE_VERSION = 2;
  public static final String TAG = "TAG";

  public static final String SPINNER_TABLE = "SpinnerTable";

  public static final String SPINNER_ID = BaseColumns._ID;
  public static final String SPINNER_ITEM = "SpinnerItem";
  static Context context;

  public ClassSpinnerData(Context context)
  {
    super(context, SPINNER_DATABASE_NAME, null, SPINNER_DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase dbCD)
  {
    String sqlCD = "CREATE TABLE " + SPINNER_TABLE + " (" + SPINNER_ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SPINNER_ITEM
        + " TEXT NOT NULL" + ");";
    dbCD.execSQL(sqlCD);
    
    ContentValues values = new ContentValues();
    values.put(SPINNER_ITEM, "General");
    dbCD.insertOrThrow(SPINNER_TABLE, null, values);
  }
  
  
  @Override
  public void onUpgrade(SQLiteDatabase dbSPINNER, int oldVersion, int newVersion)
  {

  }

  public Cursor ClassSpinner()
  {
    String[] from = {SPINNER_ID, SPINNER_ITEM};
    String order = SPINNER_ITEM;

    Log.d("TAG", "Calling read now");
    SQLiteDatabase dbCD = getReadableDatabase();
    Cursor classcursor = dbCD.query(SPINNER_TABLE, from, "SpinnerItem != ?",
        new String[] {"All"}, null, null, order);

    return classcursor;

  }

  public boolean notThere(String entry, SQLiteDatabase dbCD)
  {
    return (dbCD.query(SPINNER_TABLE, new String[] {entry}, null, null,
        null, null, null).getCount() == 0 ? true : false);
  }

  public Cursor PlanClassSettings()
  {
    String[] from = {SPINNER_ID, SPINNER_ITEM};
    String order = SPINNER_ITEM;
    SQLiteDatabase dbCD = this.getReadableDatabase();
    Cursor classcursor = dbCD.query(SPINNER_TABLE, from, null, null, null,
        null, order);

    return classcursor;
  }

  public void deleteItem(String ClassName)
  {
    SQLiteDatabase classData = this.getWritableDatabase();
    classData.delete(SPINNER_TABLE, "SpinnerItem=?", new String[] {ClassName});
    classData.close();

  }

  public void clearSpinner()
  {
    SQLiteDatabase classData = this.getWritableDatabase();
    classData.delete(SPINNER_TABLE, "SpinnerItem != ? AND SpinnerItem != ?",
        new String[] {"General", "All"});
    classData.close();

  }

  public int insertSpinnerClass(String SpinnerItem)
  {
    SQLiteDatabase checkData = getReadableDatabase();
    Log.d(TAG, "Class Name = " + SpinnerItem);
    Cursor CheckerCursor = checkData.query(SPINNER_TABLE,
        new String[] {SPINNER_ITEM}, "SpinnerItem == ?",
        new String[] {SpinnerItem}, null, null, null);
    int checkCount = CheckerCursor.getCount();
    CheckerCursor.close();
    checkData.close();

    if (checkCount == 0)
    {
      SQLiteDatabase dbCD = this.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(SPINNER_ITEM, SpinnerItem);
      int row = (int) dbCD.insertOrThrow(SPINNER_TABLE, null, values);
      dbCD.close();
      return row;
    }
    else return -1;
  }

  public void insertOnUpgrade(SQLiteDatabase db, String SpinnerItem)
  {
    if(notThere(SpinnerItem, db)){
    ContentValues values = new ContentValues();
    values.put(SPINNER_ITEM, SpinnerItem);
    db.insertOrThrow(SPINNER_TABLE, null, values);
    }
  }

}