package com.theStudyBuddy.Ignite;

import java.util.Calendar;

import com.theStudyBuddy.Ignite.Classes.ScheduleData;
import com.theStudyBuddy.Ignite.Notifications.MorningBroadcastReceiver;
import com.theStudyBuddy.Ignite.Notifications.NightBroadcastReceiver;
import com.theStudyBuddy.Ignite.Notifications.PlanAssistBroadcastReceiver;
import com.theStudyBuddy.Ignite.Settings.SettingsData;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class OnBootClass
extends BroadcastReceiver {

  SettingsData settingsData;
  ScheduleData scheduleData;
  String TAG = "TAG";
  
  @Override
  public void onReceive(Context context, Intent intent)
  {

  
    settingsData = new SettingsData(context);
    if (settingsData.notificationsCheck())
    {

      if (settingsData.MorningCheck())
      {
        int MrnAlarmHr = 6;
        int TempMin = 00;
        
        Calendar ALARM_TIME = Calendar.getInstance();
        int MinNow = ALARM_TIME.get(Calendar.MINUTE);
        int HrNow = ALARM_TIME.get(Calendar.HOUR_OF_DAY);
        int SecNow = ALARM_TIME.get(Calendar.SECOND);

        if (HrNow >= MrnAlarmHr)
        {
          ALARM_TIME.add(Calendar.DATE, 1);
        }

        ALARM_TIME.add(Calendar.SECOND, -SecNow);
        ALARM_TIME.add(Calendar.MINUTE, (TempMin-MinNow));
        ALARM_TIME.add(Calendar.HOUR_OF_DAY, (MrnAlarmHr - HrNow));

        Intent MorningIntent = new Intent(context, MorningBroadcastReceiver.class);

        PendingIntent MorningPendingIntent = PendingIntent.getBroadcast(context,
            1889, MorningIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager MorningAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        MorningAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
            ALARM_TIME.getTimeInMillis(), (24 * 60 * 60 * 1000),
            MorningPendingIntent);
      }

      if (settingsData.NightlyCheck())
      {
        int NgtAlarmHr = 20;
        int tempMin = 00;
        Calendar ALARM_TIME = Calendar.getInstance();
        int MinNow = ALARM_TIME.get(Calendar.MINUTE);
        int HrNow = ALARM_TIME.get(Calendar.HOUR_OF_DAY);
        int SecNow = ALARM_TIME.get(Calendar.SECOND);

        if (HrNow >= NgtAlarmHr)
        {
          ALARM_TIME.add(Calendar.DATE, 1);
        }

        ALARM_TIME.add(Calendar.SECOND, -SecNow);
        ALARM_TIME.add(Calendar.MINUTE, (tempMin-MinNow));
        ALARM_TIME.add(Calendar.HOUR_OF_DAY, (NgtAlarmHr - HrNow));

        Intent NightIntent = new Intent(context, NightBroadcastReceiver.class);

        PendingIntent NightPendingIntent = PendingIntent.getBroadcast(context,
            1992, NightIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager NightAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        NightAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
            ALARM_TIME.getTimeInMillis(), (24 * 60 * 60 * 1000),
            NightPendingIntent);


      }

      if (settingsData.planAssistCheck())
      {
        scheduleData = new ScheduleData(context);

        Cursor CheckCursor = scheduleData.ExistsCheck();
        int check = CheckCursor.getCount();
        if(check >= 0){
        for (int x = 0; check > x; x++)
        {
          

          CheckCursor.moveToPosition(x);
          int PlanAssisStat = ((CheckCursor.getInt(CheckCursor
              .getColumnIndex(ScheduleData.PLANNER_ASSISTANT))));

          if (PlanAssisStat == 1)
          {

            int ALARMID = ((CheckCursor.getInt(CheckCursor
                .getColumnIndex(ScheduleData.ALARMID))));

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

            String RIGHTNOWID = "" + ALARM_TIME.get(Calendar.DAY_OF_WEEK)
                + HRNOW + MINNOW;
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

            Intent PAintent = new Intent(context,
                PlanAssistBroadcastReceiver.class);

            PendingIntent PApendingIntent = PendingIntent.getBroadcast(context,
                ALARMID, PAintent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager PlannerAssistantAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            PlannerAssistantAlarm.setRepeating(AlarmManager.RTC_WAKEUP,
                ALARM_TIME.getTimeInMillis(), (7 * 24 * 60 * 60 * 1000),
                PApendingIntent);

          }
        }}
      }

    
  }


 }}
