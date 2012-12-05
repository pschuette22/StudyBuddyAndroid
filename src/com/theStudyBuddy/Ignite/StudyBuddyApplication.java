package com.theStudyBuddy.Ignite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import com.theStudyBuddy.Ignite.Classes.ClassObject;
import com.theStudyBuddy.Ignite.Classes.ClassSpinnerData;
import com.theStudyBuddy.Ignite.Classes.MeetingObject;
import com.theStudyBuddy.Ignite.Classes.ScheduleData;
import com.theStudyBuddy.Ignite.Entries.EntryObject;
import com.theStudyBuddy.Ignite.Entries.PlannerData;
import com.theStudyBuddy.Ignite.Notifications.MorningBroadcastReceiver;
import com.theStudyBuddy.Ignite.Notifications.NightBroadcastReceiver;
import com.theStudyBuddy.Ignite.Notifications.PlanAssistBroadcastReceiver;
import com.theStudyBuddy.Ignite.Settings.HolidayData;
import com.theStudyBuddy.Ignite.Settings.HolidayObject;
import com.theStudyBuddy.Ignite.Settings.SettingsData;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class StudyBuddyApplication extends Application
{

  String TAG = "TAG";

  ScheduleData scheduleData;
  PlannerData plannerData;
  ClassSpinnerData spinnerData;
  SettingsData settingsData;
  HolidayData holidayData;

  private ArrayList<EntryObject> allEntries;
  private ArrayList<ClassObject> allClasses;
  private ArrayList<MeetingObject> allMeetings;
  public ArrayList<HolidayObject> allHolidays;

  // / Settings
  private boolean notifications;
  private boolean plannerAssistant;
  private boolean morning;
  private boolean nightly;

  private boolean sendMorningOnHolidays;
  private boolean sendNightlyOnHolidays;

  // only used in entry view
  private boolean Assignments;
  private boolean Events;
  private boolean Reminders;

  boolean finishedLoading;

  /*
   * public boolean todayIsntAHoliday(){ int todayId = todayId(0); for(int i =0;
   * i < Holidays.size(); i++){
   * 
   * if (checker < todayId){
   * 
   * }
   * 
   * else if(checker == todayId) return false; } return true; }
   */

  @Override
  public void onCreate()
  {
    super.onCreate();
    finishedLoading = false;

    spinnerData = new ClassSpinnerData(this);
    scheduleData = new ScheduleData(this);
    plannerData = new PlannerData(this);

    allClasses = new ArrayList<ClassObject>();
    allMeetings = new ArrayList<MeetingObject>();
    allEntries = new ArrayList<EntryObject>();
    allHolidays = new ArrayList<HolidayObject>();

    Cursor classes = spinnerData.ClassSpinner();

    if (classes.getCount() > 0)
    {
      while (classes.moveToNext())
      {
        String className = ((classes.getString(classes
            .getColumnIndex(ClassSpinnerData.SPINNER_ITEM))));
        if (className.contentEquals("All"))
        {
          // dont do anything
        }
        else
        {
          ClassObject newClass = new ClassObject(className,
              classes.getPosition(), false);
          ArrayList<MeetingObject> meetings = scheduleData
              .getClassTimes(newClass);
          if (meetings.size() == 0)
            newClass.setOnline(true);
          newClass.setMeetingArray(meetings);
          allMeetings.addAll(meetings);

          ArrayList<EntryObject> entriesByClass = plannerData
              .getEntries(newClass);
          allEntries.addAll(entriesByClass);
          newClass.setEntryArray(entriesByClass);

          allClasses.add(newClass);
        }
      }
    }
    classes.close();

    settingsData = new SettingsData(this);
    Assignments = settingsData.Assignments();
    Events = settingsData.Events();
    Reminders = settingsData.Reminders();
    notifications = settingsData.notificationsCheck();
    plannerAssistant = settingsData.planAssistCheck();
    morning = settingsData.MorningCheck();
    nightly = settingsData.NightlyCheck();

    sendNightlyOnHolidays = settingsData.NightlyOnHolidayCheck();
    sendMorningOnHolidays = settingsData.MorningOnHolidayCheck();

    holidayData = new HolidayData(this);
    allHolidays = holidayData.getHolidayIds(todayId(0));

    new InitializeAlarms().execute();

    finishedLoading = true;
  }

  public ArrayList<ClassObject> getAllClasses()
  {
    return allClasses;
  }

  public ArrayList<MeetingObject> getAllMeetings()
  {
    return allMeetings;
  }

  public ArrayList<EntryObject> getAllEntries()
  {
    return allEntries;
  }

  public void deleteItems(ArrayList<EntryObject> deleteThese)
  {
    for (int i = 0; deleteThese.size() > i; i++)
    {
      new DeleteEntryFromDB(allEntries.get(i)).execute();
      allEntries.remove(deleteThese.get(i));
    }
  }

  public ArrayList<HolidayObject> getHolidaysFor(int year, int month)
  {
    ArrayList<HolidayObject> selectedHolidays = new ArrayList<HolidayObject>();

    Collections.sort(allHolidays, new sortThem());

    for (int i = 0; i < allHolidays.size(); i++)
    {
      HolidayObject temp = allHolidays.get(i);
      int tYear = temp.getDayId() / 10000;
      int tMonth = (temp.getDayId() % 10000) / 100;
      if (tYear == year && tMonth == month)
      {
        selectedHolidays.add(temp);
      }

      else if (tYear >= year)
      {
        if (year > tYear)
          break;
        else if (year == tYear && tMonth > month)
          break;
      }

    }

    return selectedHolidays;
  }

  public void deleteHoliday(HolidayObject holiday)
  {
    allHolidays.remove(holiday);
    holidayData.deleteHoliday(holiday.getDatabaseId());
  }

  public HolidayObject addHoliday(int dayId)
  {
    HolidayObject newHoliday = new HolidayObject(holidayData.addHoliday(dayId),
        dayId, "N/A");
    allHolidays.add(newHoliday);
    return newHoliday;
  }

  private class sortThem implements Comparator<HolidayObject>
  {

    public int compare(HolidayObject lhs, HolidayObject rhs)
    {
      return lhs.getDayId() - rhs.getDayId();
    }

  }

  private class DeleteEntryFromDB extends AsyncTask<Void, Void, Void>
  {

    private EntryObject entry;

    DeleteEntryFromDB(EntryObject entry_)
    {
      entry = entry_;
    }

    protected Void doInBackground(Void... params)
    {
      plannerData.delete_byID(entry.getDbId());
      return null;
    }

  }

  public ClassObject getOrCreate(String className, boolean online)
  {
    ClassObject thisClass = null;

    for (int i = 0; i < allClasses.size(); i++)
    {
      if (allClasses.get(i).getTitle().contentEquals(className))
      {
        thisClass = allClasses.get(i);
        break;
      }
    }
    if (thisClass == null)
    {
      thisClass = new ClassObject(className, -1, online);
      new SaveClass(thisClass).execute();
      allClasses.add(thisClass);
    }

    return thisClass;
  }

  private class SaveClass extends AsyncTask<Void, Void, Void>
  {
    ClassObject passed;

    public SaveClass(ClassObject passed_)
    {
      passed = passed_;
    }

    protected Void doInBackground(Void... arg0)
    {
      passed.setPosition(spinnerData.insertSpinnerClass(passed.getTitle()));
      return null;
    }
  }

  public ArrayList<MeetingObject> getClassTimes(String day)
  {
    ArrayList<MeetingObject> classTimes = new ArrayList<MeetingObject>();
    for (int i = 0; i < allMeetings.size(); i++)
    {
      if (allMeetings.get(i).getDayOfWeek().contentEquals(day))
        classTimes.add(allMeetings.get(i));
    }
    Collections.sort(classTimes, new CompareMeetingTimes());

    return classTimes;
  }

  public ArrayList<String> spinnerArray(boolean includeAll)
  {
    ArrayList<String> strings = new ArrayList<String>();
    if (includeAll)
      strings.add("All");
    Collections.sort(allClasses, new CompareClassByName());
    for (int i = 0; i < allClasses.size(); i++)
    {
      strings.add(allClasses.get(i).getTitle());
    }

    return strings;
  }

  public boolean todayEntries()
  {
    if (allEntries == null)
      return false;
    else
    {
      int todayID = todayId(0);
      for (int i = 0; i < allEntries.size(); i++)
      {
        if (allEntries.get(i).getDateId() == todayID)
          return true;
      }
      return false;
    }
  }

  public boolean upcomingEntries()
  {
    if (allEntries.size() == 0)
      return false;
    for (int i = 0; i < allEntries.size(); i++)
    {
      int entryDay = allEntries.get(i).getDateId();
      if (entryDay == todayId(1) || entryDay == todayId(2)
          || entryDay == todayId(3))
        return true;
    }
    return false;
  }

  // / this ones heavy...

  public ArrayList<EntryObject> getEntries(int dayId, String type,
      ClassObject parent, boolean present, boolean sortByClass)
  {
    ArrayList<EntryObject> entries = new ArrayList<EntryObject>();

    if (allEntries.size() == 0)
      return entries;

    else
    {
      int todayId = todayId(0);

      if (type == null && parent == null)
      {
        for (int i = 0; i < allEntries.size(); i++)
        {
          EntryObject entry = allEntries.get(i);
          if (entry.getDateId() == dayId)
            entries.add(entry);
        }
        return entries;
      }

      // -1 represents no specific day
      if (dayId == -1)
      {
        if (present)
        {
          for (int i = 0; i < allEntries.size(); i++)
          {
            EntryObject entry = allEntries.get(i);
            if (parent == null)
            {
              if (entry.getDateId() >= todayId
                  && entry.getType().contentEquals(type))
                entries.add(entry);
            }
            else
            {
              if (entry.getDateId() >= todayId
                  && entry.getType().contentEquals(type)
                  && entry.getParent() == parent)
                entries.add(entry);
            }
          }
        }
        else
        {
          for (int i = 0; i < allEntries.size(); i++)
          {
            EntryObject entry = allEntries.get(i);
            if (parent == null)
            {
              if (entry.getDateId() <= todayId
                  && entry.getType().contentEquals(type))
                entries.add(entry);
            }
            else
            {
              if (entry.getDateId() <= todayId
                  && entry.getType().contentEquals(type)
                  && entry.getParent() == parent)
                entries.add(entry);
            }
          }
        }
      }

      else
      {
        if (parent == null)
        {
          for (int i = 0; i < allEntries.size(); i++)
          {
            EntryObject entry = allEntries.get(i);
            if (entry.getDateId() == dayId
                && entry.getType().contentEquals(type))
              entries.add(entry);
          }
        }
        else
        {
          for (int i = 0; i < allEntries.size(); i++)
          {
            EntryObject entry = allEntries.get(i);
            if (entry.getDateId() == dayId
                && entry.getType().contentEquals(type)
                && entry.getParent() == parent)
              entries.add(entry);
          }
        }
      }

      if (sortByClass)
      {
        Collections.sort(entries, new CompareEntriesByClass(present));
      }

      else
      {
        Collections.sort(entries, new CompareEntriesByDate(present));
      }

      return entries;
    }
  }

  public void addEntry(String entryType, String entryClass, String entryText,
      String entryDate, int edayIdNum)
  {
    ClassObject parent = findByName(entryClass);

    EntryObject newEntry = new EntryObject(entryText, edayIdNum, parent, 0,
        entryType, -1, entryDate);
    allEntries.add(newEntry);
    parent.addEntry(newEntry);
    int pos = allEntries.indexOf(newEntry);
    new AddEntryToDB(entryType, entryClass, entryText, entryDate, edayIdNum,
        pos).execute();
  }

  private class AddEntryToDB extends AsyncTask<Void, Void, Void>
  {
    private String entryType, entryClass, entryText, entryDate;
    private int edayIdNum, arrayPos;

    public AddEntryToDB(String entryType_, String entryClass_,
        String entryText_, String entryDate_, int edayIdNum_, int arrayPos_)
    {
      entryType = entryType_;
      entryClass = entryClass_;
      entryText = entryText_;
      entryDate = entryDate_;
      edayIdNum = edayIdNum_;
      arrayPos = arrayPos_;
    }

    protected Void doInBackground(Void... params)
    {
      int id = plannerData.EntryAdd(entryType, entryClass, entryText,
          entryDate, edayIdNum);
      allEntries.get(arrayPos).setDBId(id);
      return null;
    }
  }

  // / All Notifications
  // ---------------------------------------------
  public void setNotifications(boolean stat)
  {
    notifications = stat;

    if (notifications)
    {
      new NotificationsOn().execute();
    }
    else
    {
      new NotificationsOff().execute();
    }

  }

  public boolean notificationsOn()
  {
    return notifications;
  }

  private class NotificationsOn extends AsyncTask<Object, Object, Object>
  {

    protected Object doInBackground(Object... params)
    {
      settingsData.updateSetting("Notifications", 1);
      if (plannerAssistant)
        new AllPlannerAssistantOn(false).execute(false);
      if (morning)
        new MorningAlarmOn(false).execute(false);
      if (nightly)
        new NightAlarmOn(false).execute(false);
      return null;
    }

  }

  private class NotificationsOff extends AsyncTask<Object, Object, Object>
  {

    protected Object doInBackground(Object... params)
    {
      settingsData.updateSetting("Notifications", 0);
      if (plannerAssistant)
        new AllPlannerAssistantOff(false).execute(false);
      if (morning)
        new MorningAlarmOff(false).execute(false);
      if (nightly)
        new NightAlarmOff(false).execute(false);
      return null;
    }

  }

  // // planner assistant

  Intent PAintent;
  PendingIntent PApendingIntent;
  AlarmManager PlannerAssistantAlarm;

  public void setPlannerAssistant(boolean stat, boolean updateDB)
  {
    plannerAssistant = stat;

    if (plannerAssistant)
    {
      new AllPlannerAssistantOn(updateDB).execute(updateDB);
    }
    else
    {
      new AllPlannerAssistantOff(updateDB).execute(updateDB);
    }

  }

  public boolean planAssistCheck()
  {
    return plannerAssistant;
  }

  private class AllPlannerAssistantOn extends AsyncTask<Object, Object, Object>
  {

    private boolean changeIt;

    public AllPlannerAssistantOn(boolean updateDB)
    {
      changeIt = updateDB;
    }

    protected Object doInBackground(Object... params)
    {
      if (changeIt)
        settingsData.updateSetting("Planner Assistant", 1);

      for (int i = 0; i < allMeetings.size(); i++)
      {
        MeetingObject meeting = allMeetings.get(i);
        if (meeting.paIsOn())
          new PlannerAssistantOn(meeting.getAlarmId(), meeting.getParent()
              .getTitle()).execute();

      }

      return null;
    }

  }

  private class AllPlannerAssistantOff extends
      AsyncTask<Object, Object, Object>
  {
    private boolean changeIt;

    public AllPlannerAssistantOff(boolean updateDB)
    {
      changeIt = updateDB;
    }

    protected Object doInBackground(Object... params)
    {
      if (changeIt)
        settingsData.updateSetting("Planner Assistant", 0);

      for (int i = 0; i < allMeetings.size(); i++)
      {
        MeetingObject meeting = allMeetings.get(i);
        if (meeting.paIsOn())
          new PlannerAssistantOff(meeting.getAlarmId()).execute();
      }

      return null;
    }

  }

  private class PlannerAssistantOn extends AsyncTask<Void, Void, Void>
  {
    private int alarmId;
    private String className;

    public PlannerAssistantOn(int _alarmId, String className_)
    {
      alarmId = _alarmId;
      className = className_;
    }

    protected Void doInBackground(Void... params)
    {

      final int ALARMID = alarmId;
      int AlarmDay = ALARMID / 10000;

      int AlarmHour = (ALARMID - ((AlarmDay * 10000))) / 100;
      int AlarmMin = (ALARMID - ((AlarmDay * 10000) + (AlarmHour * 100)));

      Calendar ALARM_TIME = Calendar.getInstance();
      int NOWMIN = ALARM_TIME.get(Calendar.MINUTE);
      String MINNOW;
      if (NOWMIN <= 9)
      {
        MINNOW = "0" + NOWMIN;
      }
      else
      {
        MINNOW = "" + NOWMIN;
      }

      int NOWHR = ALARM_TIME.get(Calendar.HOUR_OF_DAY);
      String HRNOW;
      if (NOWHR <= 9)
      {
        HRNOW = "0" + NOWHR;
      }
      else
      {
        HRNOW = "" + NOWHR;
      }

      String RIGHTNOWID = "" + ALARM_TIME.get(Calendar.DAY_OF_WEEK) + HRNOW
          + MINNOW;
      int RightNowId = Integer.parseInt(RIGHTNOWID);

      if (RightNowId < ALARMID)
      {
        switch (ALARM_TIME.get(Calendar.DAY_OF_WEEK))
        {
        case (Calendar.SUNDAY):
          ALARM_TIME.add(Calendar.DATE, -1);

          break;
        case (Calendar.MONDAY):
          ALARM_TIME.add(Calendar.DATE, -2);

          break;
        case (Calendar.TUESDAY):
          ALARM_TIME.add(Calendar.DATE, -3);

          break;
        case (Calendar.WEDNESDAY):
          ALARM_TIME.add(Calendar.DATE, -4);

          break;
        case (Calendar.THURSDAY):
          ALARM_TIME.add(Calendar.DATE, -5);

          break;
        case (Calendar.FRIDAY):
          ALARM_TIME.add(Calendar.DATE, -6);

          break;
        case (Calendar.SATURDAY):
          ALARM_TIME.add(Calendar.DATE, -7);

          break;

        }
      }

      if (RightNowId > ALARMID)
      {

        switch (ALARM_TIME.get(Calendar.DAY_OF_WEEK))
        {
        case (Calendar.SUNDAY):
          ALARM_TIME.add(Calendar.DATE, 6);
          break;
        case (Calendar.MONDAY):
          ALARM_TIME.add(Calendar.DATE, 5);

          break;
        case (Calendar.TUESDAY):
          ALARM_TIME.add(Calendar.DATE, 4);

          break;
        case (Calendar.WEDNESDAY):
          ALARM_TIME.add(Calendar.DATE, 3);

          break;
        case (Calendar.THURSDAY):
          ALARM_TIME.add(Calendar.DATE, 2);

          break;
        case (Calendar.FRIDAY):
          ALARM_TIME.add(Calendar.DATE, 1);

          break;
        case (Calendar.SATURDAY):

          break;

        }
      }
      ALARM_TIME.add(Calendar.DATE, AlarmDay);
      int hourNow = ALARM_TIME.get(Calendar.HOUR_OF_DAY);
      ALARM_TIME.add(Calendar.HOUR_OF_DAY, (AlarmHour - hourNow));
      int minNow = ALARM_TIME.get(Calendar.MINUTE);
      ALARM_TIME.add(Calendar.MINUTE, (AlarmMin - minNow));
      int seconds = ALARM_TIME.get(Calendar.SECOND);
      ALARM_TIME.add(Calendar.SECOND, (seconds * -1));

      PAintent = new Intent(getApplicationContext(),
          PlanAssistBroadcastReceiver.class);
      PAintent.putExtra("ClassName", className);

      PApendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
          ALARMID, PAintent, PendingIntent.FLAG_UPDATE_CURRENT);

      PlannerAssistantAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);

      PlannerAssistantAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
          ALARM_TIME.getTimeInMillis(), (7 * 24 * 60 * 60 * 1000),
          PApendingIntent);

      return null;
    }
  }

  private class PlannerAssistantOff extends AsyncTask<Void, Void, Void>
  {
    private int alarmId;

    public PlannerAssistantOff(int _alarmId)
    {
      alarmId = _alarmId;
    }

    protected Void doInBackground(Void... params)
    {
      int ALARMID = alarmId;
      Log.d("TAG", "AlarmId = " + alarmId);
      PAintent = new Intent(getApplicationContext(),
          PlanAssistBroadcastReceiver.class);
      PApendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
          ALARMID, PAintent, PendingIntent.FLAG_UPDATE_CURRENT);
      PlannerAssistantAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
      PlannerAssistantAlarm.cancel(PApendingIntent);
      return null;
    }

  }

  public void setAPlannerAssistant(boolean stat, int alarmID, String className)
  {

    if (stat)
      new PlannerAssistantOn(alarmID, className).execute();
    else
      new PlannerAssistantOff(alarmID).execute();
  }

  // Morning Alarm Stuff..
  // -----------------------------------------------------------
  Intent MorningIntent;
  PendingIntent MorningPendingIntent;
  AlarmManager MorningAlarm;

  public void setMorning(boolean stat, boolean updateDB)
  {
    morning = stat;

    if (morning)
    {
      new MorningAlarmOn(updateDB).execute(updateDB);
    }
    else
    {
      new MorningAlarmOff(updateDB).execute(updateDB);
    }

  }

  public boolean checkMorning()
  {
    return morning;
  }

  private class MorningAlarmOn extends AsyncTask<Object, Object, Object>
  {
    private boolean updateDB;

    public MorningAlarmOn(boolean changeIt)
    {
      super();
      updateDB = changeIt;
    }

    protected Object doInBackground(Object... params)
    {
      // pass in whether or not to update the setting(only for when
      // notifications are all turned off)
      if (updateDB)
      {
        settingsData.updateSetting("Morning Notifications", 1);
      }

      int MrnAlarmHr = 6;
      int TempMin = 00;

      Calendar ALARM_TIME = Calendar.getInstance();
      int MinNow = ALARM_TIME.get(Calendar.MINUTE);
      int HrNow = ALARM_TIME.get(Calendar.HOUR_OF_DAY);
      int SecNow = ALARM_TIME.get(Calendar.SECOND);

      if (HrNow > MrnAlarmHr)
      {
        ALARM_TIME.add(Calendar.DATE, 1);
      }

      ALARM_TIME.add(Calendar.SECOND, -SecNow);
      ALARM_TIME.add(Calendar.MINUTE, (TempMin - MinNow));
      ALARM_TIME.add(Calendar.HOUR_OF_DAY, (MrnAlarmHr - HrNow));

      MorningIntent = new Intent(getApplicationContext(),
          MorningBroadcastReceiver.class);

      MorningPendingIntent = PendingIntent.getBroadcast(
          getApplicationContext(), 1889, MorningIntent,
          PendingIntent.FLAG_UPDATE_CURRENT);

      MorningAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
      MorningAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
          ALARM_TIME.getTimeInMillis(), (24 * 60 * 60 * 1000),
          MorningPendingIntent);

      return null;
    }
  }

  private class MorningAlarmOff extends AsyncTask<Object, Object, Object>
  {
    private boolean changeIt;

    public MorningAlarmOff(boolean updateDB)
    {
      changeIt = updateDB;
    }

    protected Object doInBackground(Object... params)
    {
      if (changeIt)
        settingsData.updateSetting("Morning Notifications", 0);
      MorningIntent = new Intent(getApplicationContext(),
          MorningBroadcastReceiver.class);

      MorningPendingIntent = PendingIntent.getBroadcast(
          getApplicationContext(), 1889, MorningIntent,
          PendingIntent.FLAG_UPDATE_CURRENT);
      AlarmManager MorningAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
      MorningAlarm.cancel(MorningPendingIntent);
      return null;
    }
  }

  //   nightly alarms
  // ------------------------------------------------

  Intent NightIntent;
  PendingIntent NightPendingIntent;
  AlarmManager NightAlarm;

  public void setNightly(boolean stat, boolean updateDB)
  {
    nightly = stat;

    if (nightly)
    {
      new NightAlarmOn(updateDB).execute(updateDB);
    }
    else
    {
      new NightAlarmOff(updateDB).execute(updateDB);
    }

  }

  public boolean checkNightly()
  {
    return nightly;
  }

  private class NightAlarmOn extends AsyncTask<Object, Object, Object>
  {
    private boolean changeIt;

    public NightAlarmOn(boolean updateDB)
    {
      changeIt = updateDB;
    }

    protected Object doInBackground(Object... params)
    {
      if (changeIt)
        settingsData.updateSetting("Nightly Notifications", 1);

      // 8PM for now ( probably give users time option in future)
      int NgtAlarmHr = 12 + 8;
      int tempMin = 00;
      Calendar ALARM_TIME = Calendar.getInstance();
      int MinNow = ALARM_TIME.get(Calendar.MINUTE);
      int HrNow = ALARM_TIME.get(Calendar.HOUR_OF_DAY);
      int SecNow = ALARM_TIME.get(Calendar.SECOND);

      if (HrNow > NgtAlarmHr)
      {
        ALARM_TIME.add(Calendar.DATE, 1);
      }

      ALARM_TIME.add(Calendar.SECOND, -SecNow);
      ALARM_TIME.add(Calendar.MINUTE, (tempMin - MinNow));
      ALARM_TIME.add(Calendar.HOUR_OF_DAY, (NgtAlarmHr - HrNow));

      NightIntent = new Intent(getApplicationContext(),
          NightBroadcastReceiver.class);

      NightPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
          1992, NightIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      NightAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);

      NightAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
          ALARM_TIME.getTimeInMillis(), (24 * 60 * 60 * 1000),
          NightPendingIntent);
      return null;
    }

  }

  private class NightAlarmOff extends AsyncTask<Object, Object, Object>
  {
    private boolean changeIt;

    public NightAlarmOff(boolean updateDB)
    {
      changeIt = updateDB;
    }

    protected Object doInBackground(Object... params)
    {
      if (changeIt)
        settingsData.updateSetting("Nightly Notifications", 0);

      NightIntent = new Intent(getBaseContext(), NightBroadcastReceiver.class);
      NightPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1992,
          NightIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      AlarmManager NightAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
      NightAlarm.cancel(NightPendingIntent);
      return null;
    }

  }

  // / Planner Assistant

  public void initializeAll()
  {
    new InitializeAlarms().execute();
  }

  private class InitializeAlarms extends AsyncTask<Object, Object, Object>
  {

    protected Object doInBackground(Object... arg0)
    {
      if (notifications)
      {
        if (morning)
        {
          new MorningAlarmOn(false).execute();
        }
        if (nightly)
        {
          new NightAlarmOn(false).execute();
        }
        if (plannerAssistant)
        {
          new AllPlannerAssistantOn(false).execute();
        }
      }
      return null;
    }

  }

  // // assignment stuff

  public void updateStatus(boolean set, int dbID)
  {
    new UpdateEntryStatus(set, dbID).execute();
  }

  private class UpdateEntryStatus extends AsyncTask<Object, Object, Object>
  {
    private int dbID, status;

    public UpdateEntryStatus(boolean set, int _dbID)
    {
      dbID = _dbID;
      if (set)
        status = 1;
      else
        status = 0;
    }

    protected Object doInBackground(Object... params)
    {
      plannerData.StatusUpdate(dbID, status);
      return null;
    }

  }

  public void editEntry(int _ID, String _EntryType, String _EntryClass,
      String _EntryText, String _EntryDate, int _DayId)
  {

    new EditExistingEntry(_ID, _EntryType, _EntryClass, _EntryText, _EntryDate,
        _DayId).execute();

  }

  public ClassObject findByName(String name)
  {
    for (int i = 0; i < allClasses.size(); i++)
    {
      if (allClasses.get(i).getTitle().contentEquals(name))
        return allClasses.get(i);
    }
    return null;
  }

  private class EditExistingEntry extends AsyncTask<Object, Object, Object>
  {
    private int ID, DayId;
    private String EntryType, EntryClass, EntryText, EntryDate;

    public EditExistingEntry(int _ID, String _EntryType, String _EntryClass,
        String _EntryText, String _EntryDate, int _DayId)
    {
      ID = _ID;
      EntryText = _EntryText;
      EntryType = _EntryType;
      EntryClass = _EntryClass;
      EntryDate = _EntryDate;
      DayId = _DayId;
    }

    protected Object doInBackground(Object... arg0)
    {
      plannerData.EntryEdit(ID, EntryType, EntryClass, EntryText, EntryDate,
          DayId);

      return null;
    }

  }

  public void deleteById(EntryObject entry)
  {
    new DeleteEntryItem(entry.getDbId()).execute();
    allEntries.remove(entry);
  }

  private class DeleteEntryItem extends AsyncTask<Object, Object, Object>
  {
    private int dbID;

    public DeleteEntryItem(int _dbID)
    {
      dbID = _dbID;
    }

    protected Object doInBackground(Object... params)
    {
      plannerData.delete_byID(dbID);

      return null;
    }

  }

  public boolean Assignments()
  {
    return Assignments;
  }

  public boolean Events()
  {
    return Events;
  }

  public boolean Reminders()
  {
    return Reminders;
  }

  public void setPlannerViewTypes(boolean A, boolean E, boolean R)
  {
    Assignments = A;
    Events = E;
    Reminders = R;
    new SetViewTypes().execute();
  }

  private class SetViewTypes extends AsyncTask<Object, Object, Object>
  {

    protected Object doInBackground(Object... params)
    {
      if (Assignments)
        settingsData.updateSetting("Assignments", 1);
      else
        settingsData.updateSetting("Assignments", 0);

      if (Events)
        settingsData.updateSetting("Events", 1);
      else
        settingsData.updateSetting("Events", 0);

      if (Reminders)
        settingsData.updateSetting("Reminders", 1);
      else
        settingsData.updateSetting("Reminders", 0);
      return null;
    }

  }

  public void addClass(ClassObject parent, String classDay, String classStart,
      String classEnd, int alarmId, int startId)
  {

    MeetingObject newMeeting = new MeetingObject(parent, -1, classDay,
        classStart, classEnd, 1, alarmId, startId);
    parent.addMeeting(newMeeting);
    allMeetings.add(newMeeting);
    new AddClass(newMeeting, parent.getTitle(), classDay, classStart, classEnd,
        alarmId, startId).execute();
    Collections.sort(allMeetings, new CompareMeetingTimes());

  }

  private class AddClass extends AsyncTask<Void, Void, Void>
  {

    private MeetingObject newMeeting;
    private String className, classDay, classStart, classEnd;
    private int alarmId, startId;

    //
    public AddClass(MeetingObject newMeeting_, String className_,
        String classDay_, String classStart_, String classEnd_, int alarmId_,
        int startId_)
    {
      newMeeting = newMeeting_;
      className = className_;
      classDay = classDay_;
      classStart = classStart_;
      classEnd = classEnd_;
      alarmId = alarmId_;
      startId = startId_;
    }

    protected Void doInBackground(Void... arg0)
    {
      newMeeting.setScheduleId(scheduleData.insert(className, classDay,
          classStart, classEnd, 1, alarmId, startId));
      return null;
    }

  }

  // / Delete Class Stuff

  public void deleteClass(ClassObject parent)
  {

    allMeetings.removeAll(parent.getMeetings());
    allClasses.remove(parent);
    ArrayList<MeetingObject> meetings = parent.getMeetings();
    for (int i = 0; i < meetings.size(); i++)
    {
      MeetingObject meeting = meetings.get(i);
      new DeleteMeeting(meeting).execute();
    }
    new DeleteClass(parent).execute();
  }

  public void deleteMeeting(MeetingObject meeting)
  {
    allMeetings.remove(meeting);
    meeting.getParent().getMeetings().remove(meeting);
    new DeleteMeeting(meeting).execute();
  }

  private class DeleteMeeting extends AsyncTask<Void, Void, Void>
  {
    private MeetingObject meeting;

    DeleteMeeting(MeetingObject meeting_)
    {
      meeting = meeting_;
    }

    protected Void doInBackground(Void... params)
    {
      if (notifications && plannerAssistant && meeting.paIsOn())
      {
        setAPlannerAssistant(false, meeting.getAlarmId(), meeting.getParent()
            .getTitle());
      }
      scheduleData.delete_byID(meeting.getScheduleId());

      return null;
    }

  }

  private class DeleteClass extends AsyncTask<Void, Void, Void>
  {

    ClassObject parent;

    public DeleteClass(ClassObject parent_)
    {
      parent = parent_;
    }

    protected Void doInBackground(Void... params)
    {
      allEntries.removeAll(parent.getAllEntries());
      plannerData.delete_byClass(parent.getTitle());
      spinnerData.deleteItem(parent.getTitle());
      return null;
    }

  }

  // Comparators

  class CompareClassByName implements Comparator<ClassObject>
  {
    public int compare(ClassObject c1, ClassObject c2)
    {
      if (c1.getTitle().contentEquals("General"))
        return -1;
      else if (c2.getTitle().contentEquals("General"))
        return 1;
      else
        return c1.getTitle().compareToIgnoreCase(c2.getTitle());
    }
  }

  class CompareMeetingTimes implements Comparator<MeetingObject>
  {
    public int compare(MeetingObject m1, MeetingObject m2)
    {
      int meeting1ID = m1.getStartId();
      int meeting2ID = m2.getStartId();
      if (meeting1ID < 10000)
        ;
      meeting1ID += 70000;
      if (meeting2ID < 10000)
        ;
      meeting2ID += 70000;
      return meeting1ID - meeting2ID;
    }
  }

  class CompareEntriesByClass implements Comparator<EntryObject>
  {
    private boolean upcoming;

    CompareEntriesByClass(boolean upcoming_)
    {
      upcoming = upcoming_;
    }

    public int compare(EntryObject lhs, EntryObject rhs)
    {
      int comparison = lhs.getParent().getTitle()
          .compareToIgnoreCase(rhs.getParent().getTitle());
      if (comparison == 0)
      {
        comparison = lhs.getDateId() - rhs.getDateId();

      }

      if (!upcoming)
        comparison *= -1;
      if (comparison == 0)
      {
        comparison = lhs.getText().compareToIgnoreCase(rhs.getText());
      }

      return comparison;
    }
  }

  class CompareEntriesByDate implements Comparator<EntryObject>
  {
    private boolean upcoming;

    CompareEntriesByDate(boolean upcoming_)
    {
      upcoming = upcoming_;
    }

    public int compare(EntryObject lhs, EntryObject rhs)
    {
      int comparison = lhs.getDateId() - rhs.getDateId();

      if (comparison == 0)
      {
        comparison = lhs.getParent().getTitle()
            .compareToIgnoreCase(rhs.getParent().getTitle());
        if (comparison != 0)
          return comparison;
      }

      if (!upcoming)
        comparison *= -1;

      if (comparison == 0)
      {
        comparison = lhs.getText().compareToIgnoreCase(rhs.getText());
      }

      return comparison;

    }
  }

  public boolean launchMorning()
  {
    if (!morning || !notifications)
      return false;

    Calendar today = Calendar.getInstance();
    int todayId = (today.get(Calendar.YEAR) * 10000)
        + (today.get(Calendar.MONTH) * 100) + today.get(Calendar.DAY_OF_MONTH);
    // no entries
    if (getEntries(todayId, null, null, true, true).size() <= 0)
      return false;

    else
    {
      ArrayList<HolidayObject> holidays = getHolidaysFor(
          today.get(Calendar.YEAR), today.get(Calendar.MONTH));
      // no holidays

      if (holidays.size() <= 0)
        return true;
      else
      {
        for (int i = 0; i < holidays.size(); i++)
        {
          int ballSacks = holidays.get(i).getDayId();
          if (todayId == ballSacks)
          {
            return sendMorningOnHolidays;
          }
          else if (todayId > ballSacks)
            break;
        }
      }
    }
    return true;
  }

  public boolean launchNightly()
  {
    if (!nightly || !notifications)
      return false;

    Calendar today = Calendar.getInstance();
    int todayId = (today.get(Calendar.YEAR) * 10000)
        + (today.get(Calendar.MONTH) * 100) + today.get(Calendar.DAY_OF_MONTH);

    ArrayList<EntryObject> upcoming = new ArrayList<EntryObject>();
    for (int i = 1; i <= 3; i++)
    {
      Calendar temp = Calendar.getInstance();
      temp.add(Calendar.DATE, i);
      int tempDayId = (temp.get(Calendar.YEAR) * 10000)
          + (temp.get(Calendar.MONTH) * 100) + temp.get(Calendar.DAY_OF_MONTH);
      upcoming.addAll(getEntries(tempDayId, null, null, true, true));
    }

    // no entries
    if (upcoming.size() <= 0)
      return false;

    else
    {
      ArrayList<HolidayObject> holidays = getHolidaysFor(
          today.get(Calendar.YEAR), today.get(Calendar.MONTH));

      // no holidays already established no entries
      if (holidays.size() <= 0)
        return true;
      else
      {
        for (int i = 0; i < holidays.size(); i++)
        {
          int ballSacks = holidays.get(i).getDayId();
          if (todayId == ballSacks)
          {
            return sendNightlyOnHolidays;
          }
          else if (todayId > ballSacks)
            break;
        }
      }
    }
    return true;
  }

  public boolean checkHolidayNightly()
  {
    return sendNightlyOnHolidays;
  }

  public boolean checkHolidayMorning()
  {
    return sendMorningOnHolidays;
  }

  public void setHolidayNotifications(boolean nightly, boolean isOn)
  {
    if (nightly)
    {
      sendNightlyOnHolidays = isOn;
    }
    else
      sendMorningOnHolidays = isOn;
    new SetHolidayNotifications(nightly, isOn).execute();
  }

  private class SetHolidayNotifications extends AsyncTask<Void, Void, Void>
  {

    private boolean nightly, isOn;

    SetHolidayNotifications(boolean nightly_, boolean isOn_)
    {
      nightly = nightly_;
      isOn = isOn_;
    }

    protected Void doInBackground(Void... arg0)
    {
      int isOnValue = (isOn == true ? 1 : 0);

      if (nightly)
      {
        settingsData.updateSetting("SendNightlyOnHolidays", isOnValue);
      }
      else
      {
        settingsData.updateSetting("SendMorningOnHolidays", isOnValue);
      }

      return null;
    }

  }

  public boolean notHolidayToday()
  {
    int todayId = todayId(0);
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

  public int todayId(int add)
  {
    Calendar today = Calendar.getInstance();
    today.add(Calendar.DATE, add);
    int year = today.get(Calendar.YEAR) * 10000;
    int month = today.get(Calendar.MONTH) * 100;
    int day = today.get(Calendar.DAY_OF_MONTH);

    return year + month + day;
  }
}
