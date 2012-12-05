package com.theStudyBuddy.Ignite.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Entries.PlannerData;
import com.theStudyBuddy.Ignite.Settings.SettingsData;

public class NightBroadcastReceiver extends BroadcastReceiver
{

  public NotificationManager NightManager;
  private static int NOTIFICATION_ID =1;
  
  CharSequence NotificationTitle = "Good Evening!";
  CharSequence NotificationMessage = "You have a few things coming up";


  StudyBuddyApplication StudyBuddy;
  
  @Override
  public void onReceive(Context context, Intent intent)
  {
    PlannerData plannerData = new PlannerData(context);
    if(plannerData.noEntriesUpcoming()){
      return;
    }
    
    SettingsData settingsData = new SettingsData(context);
    if(!settingsData.notHolidayToday(context) && !settingsData.NightlyOnHolidayCheck()){
      return;
    }
    
    if (settingsData.NightlyCheck() && settingsData.notificationsCheck()){
      NightManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      Notification NightReminder = new Notification(R.drawable.ic_pencil,NotificationTitle,0);
    Intent NightIntent = new Intent(context,NightReminderActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, NightIntent,0);
    NightReminder.setLatestEventInfo(context,NotificationTitle,NotificationMessage, contentIntent);
    
    NightReminder.flags = Notification.FLAG_AUTO_CANCEL;
    NightReminder.defaults |= Notification.DEFAULT_LIGHTS;
    NightReminder.defaults |= Notification.DEFAULT_SOUND;
    NightReminder.defaults |= Notification.DEFAULT_VIBRATE;
    
    NightManager.notify(NOTIFICATION_ID, NightReminder);
  }
  

    
    
  }
  
}
