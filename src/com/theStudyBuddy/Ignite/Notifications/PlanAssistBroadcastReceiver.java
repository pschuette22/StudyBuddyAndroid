package com.theStudyBuddy.Ignite.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.Entries.AssignmentAddActivity;
import com.theStudyBuddy.Ignite.Settings.SettingsData;

public class PlanAssistBroadcastReceiver extends BroadcastReceiver
{
  public NotificationManager PlannerAssistant;
  String TAG = "TAG";
  private static int NOTIFICATION_ID = 1;

  CharSequence NotificationTitle = "Planner Assistant";
  CharSequence NotificationMessage = "What Was Assigned?";

  @Override
  public void onReceive(Context context, Intent intent)
  {

    SettingsData settingsData = new SettingsData(context);

    if (settingsData.notHolidayToday(context) && settingsData.notificationsCheck()
        && settingsData.planAssistCheck())
    {
      String className = intent.getExtras().getString("ClassName");

      PlannerAssistant = (NotificationManager) context
          .getSystemService(Context.NOTIFICATION_SERVICE);
      Notification PANotification = new Notification(R.drawable.ic_pencil,
          NotificationTitle, 0);
      Intent PlannerAssistantIntent = new Intent(context,
          AssignmentAddActivity.class);
      PlannerAssistantIntent.putExtra("ClassName", className);
      PlannerAssistantIntent.putExtra(android.content.Intent.EXTRA_TEXT,
          className);

      PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
          PlannerAssistantIntent, 0);
      PANotification.setLatestEventInfo(context, NotificationTitle,
          NotificationMessage, contentIntent);

      PANotification.flags = Notification.FLAG_AUTO_CANCEL;
      // PANotification.icon |= R.drawable.notif_icon_pncl2;
      PANotification.defaults |= Notification.DEFAULT_SOUND;
      PANotification.defaults |= Notification.DEFAULT_VIBRATE;
      PANotification.defaults |= Notification.DEFAULT_LIGHTS;

      PlannerAssistant.notify(NOTIFICATION_ID, PANotification);
    }
  }

}
