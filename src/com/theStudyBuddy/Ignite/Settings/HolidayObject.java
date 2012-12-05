package com.theStudyBuddy.Ignite.Settings;

public class HolidayObject
{
  private int databaseId;
  private String title;
  private int dayId;
  
  public HolidayObject(int databaseId_, int dayId_, String title_){
    databaseId = databaseId_;
    dayId = dayId_;
    title = title_;
  }
  
  public int getDatabaseId(){
    return databaseId;
  }
  public String getTitle(){
    return title;
  }
  public int getDayId(){
    return dayId;
  }
}
