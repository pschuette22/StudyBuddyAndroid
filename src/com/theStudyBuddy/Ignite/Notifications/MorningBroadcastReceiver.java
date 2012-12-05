package com.theStudyBuddy.Ignite.Notifications;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Classes.ScheduleData;
import com.theStudyBuddy.Ignite.Entries.PlannerData;
import com.theStudyBuddy.Ignite.R.drawable;
import com.theStudyBuddy.Ignite.Settings.SettingsData;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MorningBroadcastReceiver extends BroadcastReceiver
{

  public NotificationManager MorningManager;
  private static int NOTIFICATION_ID =1;
  
  CharSequence NotificationTitle = "Good Morning!";
  CharSequence NotificationMessage = "You have a few things for the day";
  
  
  StudyBuddyApplication StudyBuddy;
  PlannerData plannerData;
  ScheduleData scheduleData;
  SettingsData settingsData;
  boolean launchReminder;
  
  @Override
  public void onReceive(Context context, Intent intent)
  {
    PlannerData plannerData = new PlannerData(context);
    if(plannerData.noEntriesToday()){
      return;
    }
    
    SettingsData settingsData = new SettingsData(context);
    if(!settingsData.notHolidayToday(context) && !settingsData.MorningOnHolidayCheck()){
      return;
    }
    
    if (settingsData.MorningCheck() && settingsData.notificationsCheck()){
      MorningManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      Notification MorningReminder = new Notification(R.drawable.ic_pencil,NotificationTitle,0);
    Intent MorningIntent = new Intent(context,MorningReminderActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, MorningIntent,0);
    MorningReminder.setLatestEventInfo(context,NotificationTitle,NotificationMessage, contentIntent);

    MorningReminder.flags = Notification.FLAG_AUTO_CANCEL;
    MorningReminder.defaults |= Notification.DEFAULT_LIGHTS;
    
    MorningManager.notify(NOTIFICATION_ID, MorningReminder);
  }
  
    
  }
}

