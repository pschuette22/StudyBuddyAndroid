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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MorningReminderActivity extends Activity implements
    OnClickListener
{
  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    Intent intent = new Intent(getBaseContext(), StudyBuddyActivity.class);
    intent.putExtra(android.content.Intent.EXTRA_TEXT, "Schedule");
    startActivityForResult(intent, 500);
    overridePendingTransition(0, R.anim.out_to_bottom);
  }

  LinearLayout Assignments;
  ArrayList<EntryObject> AssignmentsCursor;
  LinearLayout Events;
  ArrayList<EntryObject> EventsCursor;
  LinearLayout Reminders;
  ArrayList<EntryObject> RemindersCursor;

  Button ToSchedule;

  StudyBuddyApplication StudyBuddy;
  PlannerData plannerData;

  String TAG = "TAG";

  @Override
  public void onCreate(Bundle savedInstanceState)
  {


    StudyBuddy = (StudyBuddyApplication) getApplication();

    super.onCreate(savedInstanceState);
    setContentView(R.layout.morningreminder);
    Log.d(TAG, "Set Content View");

    ToSchedule = (Button) findViewById(R.id.buttonMorningReminder_ToSchedule);
    ToSchedule.setOnClickListener(this);

    // ///-----Assignments---------
    Assignments = (LinearLayout) this
        .findViewById(R.id.listMorningReminder_Assignments);

    AssignmentsCursor = StudyBuddy.getEntries(StudyBuddy.todayId(0),
        "Assignment", null, true, true);

    int AssignmentCount = AssignmentsCursor.size();
    if (AssignmentCount > 0)
    {
      ReminderAdapter AssignmentsAdapter = new ReminderAdapter(this,
          R.layout.reminderlistitem, AssignmentsCursor);
      int i;
      for (i = 0; i < AssignmentCount; i++)
      {
        View listItem = AssignmentsAdapter.getView(i, null, Assignments);
        Assignments.addView(listItem);
      }

    }

    /*
     * Assignments.setOnItemClickListener(new OnItemClickListener(){ public void
     * onItemClick(AdapterView<?> AssignmentsAdapter, View view, int position,
     * long id) { Cursor cursor =
     * (Cursor)AssignmentsAdapter.getItemAtPosition(position); String
     * CursorClass =
     * ((cursor.getString(cursor.getColumnIndex(PlannerData.ENTRY_CLASS))));
     * String Body =
     * ((cursor.getString(cursor.getColumnIndex(PlannerData.ENTRY_TEXT))));
     * itemSelectDialog(CursorClass, Body); } });
     */

    // ///---------Events-----------
    Events = (LinearLayout) this.findViewById(R.id.listMorningReminder_Events);
    EventsCursor = StudyBuddy.getEntries(StudyBuddy.todayId(0), "Event", null,
        true, true);
    ReminderAdapter EventsAdapter = new ReminderAdapter(this,
        R.layout.reminderlistitem, EventsCursor);

    int EventCount = EventsCursor.size();
    if (EventCount > 0)
    {
      int i;
      for (i = 0; i < EventCount; i++)
      {
        View listItem = EventsAdapter.getView(i, null, Events);
        Events.addView(listItem);
      }
    }

    // //---------Reminders---------
    Reminders = (LinearLayout) this
        .findViewById(R.id.listMorningReminder_Reminders);
    RemindersCursor = StudyBuddy.getEntries(StudyBuddy.todayId(0), "Reminder",
        null, true, true);

    ReminderAdapter RemindersAdapter = new ReminderAdapter(this,
        R.layout.reminderlistitem, RemindersCursor);

    int ReminderCount = RemindersCursor.size();

    if (ReminderCount > 0)
    {
      int i;
      for (i = 0; i < ReminderCount; i++)
      {
        View listItem = RemindersAdapter.getView(i, null, Reminders);
        Reminders.addView(listItem);
      }
    }

  }

  public void onClick(View v)
  {
    switch (v.getId())
    {
    case R.id.buttonMorningReminder_ToSchedule:
      onBackPressed();

      break;

    }

  }

  /*
   * public void itemSelectDialog(String Class, String Body){
   * AlertDialog.Builder dialog = new AlertDialog.Builder(this)
   * .setPositiveButton("Close", new
   * android.content.DialogInterface.OnClickListener() { public void
   * onClick(DialogInterface dialog, int which) { dialog.dismiss(); } });
   * dialog.setTitle(Class); dialog.setMessage(Body); dialog.show(); }
   */

}
