package com.theStudyBuddy.Ignite;

import java.util.Calendar;

import com.theStudyBuddy.Ignite.Classes.ScheduleData;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopBarActivity extends Fragment
{
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  ScheduleData scheduleData;

  Context context;
  Activity activity;
  View mView;
  
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    super.onCreateView(inflater, container, savedInstanceState);
    mView = inflater.inflate(R.layout.header_layout, container, false);
    activity = this.getActivity();
    ClassCountDown();
    return mView;
  }


  // /// ======== Class Countdown in header ========== /////

  public void ClassCountDown()
  {
    scheduleData = new ScheduleData(getActivity());
    final Calendar RightNow = Calendar.getInstance();

    int NowDay = RightNow.get(Calendar.DAY_OF_WEEK);

    int NowHour = RightNow.get(Calendar.HOUR_OF_DAY);
    String NOWHOUR;
    if (NowHour < 10)
      NOWHOUR = "0" + NowHour;
    else
      NOWHOUR = "" + NowHour;
    int NowMinute = RightNow.get(Calendar.MINUTE);
    String NOWMINUTE;
    if (NowMinute < 10)
      NOWMINUTE = "0" + NowMinute;
    else
      NOWMINUTE = "" + NowMinute;
    int NowSecond = RightNow.get(Calendar.SECOND);

    String NOWID = "" + NOWHOUR + NOWMINUTE;
    int NowId = Integer.parseInt(NOWID);
    Cursor TodaysCursor;
    if (NowDay == Calendar.MONDAY)
    {
      TodaysCursor = scheduleData.MondayStartCursor();
    }
    else if (NowDay == Calendar.TUESDAY)
    {
      TodaysCursor = scheduleData.TuesdayStartCursor();
    }
    else if (NowDay == Calendar.WEDNESDAY)
    {
      TodaysCursor = scheduleData.WednesdayStartCursor();
    }
    else if (NowDay == Calendar.THURSDAY)
    {
      TodaysCursor = scheduleData.ThursdayStartCursor();
    }
    else if (NowDay == Calendar.FRIDAY)
    {

      TodaysCursor = scheduleData.FridayStartCursor();
    }
    else if (NowDay == Calendar.SATURDAY)
    {
      TodaysCursor = scheduleData.SaturdayStartCursor();
    }
    else
    {
      TodaysCursor = scheduleData.SundayStartCursor();
    }

    int ClassCount = TodaysCursor.getCount();

    TextView Line1 = (TextView) mView.findViewById(R.id.textHeadline_Line1);
    TextView Line2 = (TextView) mView.findViewById(R.id.textHeadline_Line2);

    // ----- if there aren't any classes----
    if (ClassCount == 0)
    { 
      Line1.setText("No Classes");
      Line2.setText("Scheduled"); 
      return; 
    } 
    
    // ------------------------------------
    
    else {
    int Position = 0;
    TodaysCursor.moveToFirst();
    int startId = ((TodaysCursor.getInt(TodaysCursor
        .getColumnIndex(ScheduleData.STARTID))));

    // // move to the correct position or say no more classes
    for (Position = 0; NowId > startId; Position++)
    {
      if (Position == ClassCount)
      {
        Line1.setText("No Classes Are Left!");
        Line2.setText("");
        return;
      }
      TodaysCursor.moveToPosition(Position);
      startId = ((TodaysCursor.getInt(TodaysCursor
          .getColumnIndex(ScheduleData.STARTID))));
    }

    String ClassName = ((TodaysCursor.getString(TodaysCursor
        .getColumnIndex(ScheduleData.CLASS_NAME))));
    if (ClassName.length() > 12)
    {
      ClassName = ClassName.substring(0, 10) + "..";
    }
    Line1.setText(ClassName);

    String ClassStart = ((TodaysCursor.getString(TodaysCursor
        .getColumnIndex(ScheduleData.CLASS_START))));
    String StartTime = SetTime(ClassStart);
    Line1.setText(ClassName + " At: " + StartTime);

    startTimer(ClassStart, NowHour, NowMinute, NowSecond);
    return;
    }
  }

  public void startTimer(String ClassStart, int NowHour, int NowMinute,
      int NowSecond)
  {

    // / Get class Start hour
    String HOURS = ClassStart.substring(0, 2);
    int Hour = Integer.parseInt(HOURS);

    // / Get class Start Minutes
    String MINUTES = ClassStart.substring(3, 5);
    int Minute = Integer.parseInt(MINUTES);

    int StartMillis = (Hour * 60 * 60 * 1000) + (Minute * 60 * 1000);
    int nowMillis = (NowHour * 60 * 60 * 1000) + (NowMinute * 60 * 1000)
        + ((NowSecond - 1) * 1000);

    int MilisTill = StartMillis - nowMillis;

    new CountDownTimer(MilisTill, 1000)
    {
      @Override
      public void onTick(long millisUntilFinished)
      {
        int millisTill = (int) millisUntilFinished;
        String TIMERHOUR;
        int TimerHour = millisTill / (60 * 60 * 1000);
        if (TimerHour < 10)
          TIMERHOUR = "0" + TimerHour;
        else
          TIMERHOUR = "" + TimerHour;
        String TIMERMIN;
        int TimerMinute = (millisTill - (TimerHour * 60 * 60 * 1000))
            / (60 * 1000);
        if (TimerMinute < 10)
          TIMERMIN = "0" + TimerMinute;
        else
          TIMERMIN = "" + TimerMinute;
        String TIMERSEC;
        int TimerSeconds = (millisTill - ((TimerHour * 60 * 60 * 1000) + (TimerMinute * 60 * 1000))) / 1000;
        if (TimerSeconds < 10)
          TIMERSEC = "0" + TimerSeconds;
        else
          TIMERSEC = "" + TimerSeconds;
        TextView Line2 = (TextView) activity
            .findViewById(R.id.textHeadline_Line2);
        Line2.setText("In " + TIMERHOUR + ":" + TIMERMIN + ":" + TIMERSEC);

      }

      @Override
      public void onFinish()
      {
        TextView Line1 = (TextView) mView
            .findViewById(R.id.textHeadline_Line1);
        TextView Line2 = (TextView) mView
            .findViewById(R.id.textHeadline_Line2);


        Line1.setText("Class Started");
        Line2.setText("Pay Attention");
      }

    }.start();

  }

  public String SetTime(String Time)
  {
    String Hour;
    String TIME;
    Hour = Time.substring(0, 2);
    String Rest = Time.substring(2, 5);
    int HOUR = Integer.parseInt(Hour);
    if (HOUR >= 12)
    {
      if (HOUR == 12)
        Hour = "" + HOUR;
      else
        Hour = "" + (HOUR - 12);
      TIME = Hour + Rest + " PM";
    }
    else
    {
      if (HOUR == 00)
        Hour = "12";
      else
        Hour = "" + (HOUR);
      TIME = Hour + Rest + " AM";
    }

    return TIME;
  }

}