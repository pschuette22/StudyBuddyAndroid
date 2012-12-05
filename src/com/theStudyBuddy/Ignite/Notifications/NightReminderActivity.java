package com.theStudyBuddy.Ignite.Notifications;

import java.util.ArrayList;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Entries.EntryObject;
import com.theStudyBuddy.Ignite.Entries.PlannerData;
import com.theStudyBuddy.Ignite.R.anim;
import com.theStudyBuddy.Ignite.R.id;
import com.theStudyBuddy.Ignite.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class NightReminderActivity extends Activity implements OnClickListener
{
  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    Intent intent = new Intent(getBaseContext(), StudyBuddyActivity.class);
    startActivityForResult(intent, 500);
    overridePendingTransition(0, R.anim.out_to_bottom);
  }

  Button ToPlanner;
  StudyBuddyApplication StudyBuddy;
  PlannerData plannerData;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {

    StudyBuddy = (StudyBuddyApplication) getApplication();

    super.onCreate(savedInstanceState);
    setContentView(R.layout.nightlyreminder);

    ToPlanner = (Button) findViewById(R.id.buttonNightReminder_ToSchedule);
    ToPlanner.setText("Planner");
    ToPlanner.setOnClickListener(this);

    // ///-----Assignments---------
    LinearLayout Tomorrow = (LinearLayout) findViewById(R.id.listNightReminder_Tomorrow);
    ArrayList<EntryObject> TomorrowCursor = StudyBuddy.getEntries(
        StudyBuddy.todayId(1), null, null, true, true);

    ReminderAdapter TomorrowAdapter = new ReminderAdapter(this,
        R.layout.reminderlistitem, TomorrowCursor);

    int TomorrowCount = TomorrowCursor.size();
    if (TomorrowCount > 0)
    {
      int i;
      for (i = 0; i < TomorrowCount; i++)
      {
        View listItem = TomorrowAdapter.getView(i, null, Tomorrow);
        Tomorrow.addView(listItem);
      }
    }

    // ///---------Events-----------
    LinearLayout DayAfter = (LinearLayout) this
        .findViewById(R.id.listNightReminder_DayAfter);
    ArrayList<EntryObject> DayAfterCursor = StudyBuddy.getEntries(
        StudyBuddy.todayId(2), null, null, true, true);
    ReminderAdapter DayAfterAdapter = new ReminderAdapter(this,
        R.layout.reminderlistitem, DayAfterCursor);

    int DayAfterCount = DayAfterCursor.size();
    if (DayAfterCount > 0)
    {
      int i;
      for (i = 0; i < DayAfterCount; i++)
      {
        View listItem = DayAfterAdapter.getView(i, null, DayAfter);
        DayAfter.addView(listItem);
      }
    }

    // //---------Reminders---------
    LinearLayout AfterThat = (LinearLayout) this
        .findViewById(R.id.listNightReminder_AfterThat);
    ArrayList<EntryObject> AfterThatCursor = StudyBuddy.getEntries(
        StudyBuddy.todayId(3), null, null, true, true);

    ReminderAdapter AfterThatAdapter = new ReminderAdapter(this,
        R.layout.reminderlistitem, AfterThatCursor);

    int AfterThatCount = AfterThatCursor.size();
    if (AfterThatCount > 0)
    {
      for (int i = 0; i < AfterThatCount; i++)
      {
        View listItem = AfterThatAdapter.getView(i, null, AfterThat);
        AfterThat.addView(listItem);
      }
    }
  }

  public void onClick(View v)
  {
    switch (v.getId())
    {
    case R.id.buttonNightReminder_ToSchedule:
      onBackPressed();
      break;

    }

  }

}
