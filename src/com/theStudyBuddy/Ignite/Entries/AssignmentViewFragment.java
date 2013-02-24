package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Classes.ClassObject;

public class AssignmentViewFragment extends Fragment implements OnClickListener
{

  StudyBuddyApplication StudyBuddy;

  Context context;
  Activity activity;

  // buttons
  Button assignmentsToSchedule;
  Button assignmentsToSettings;
  Button assignmentsToAssignmentsAdd;
  Button deleteEntries;
  Button setView;
  String lastChecked = "All";

  Spinner classes;

  final String ALL = "All";
  View mView;
  boolean lastUpcoming = true;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {

    activity = getActivity();
    context = activity.getBaseContext();

    StudyBuddy = (StudyBuddyApplication) getActivity().getApplication();
    lastChecked = "All";

    // / =================== Planner Part =======================
    mView = inflater.inflate(R.layout.assignmentsview, container, false);

    assignmentsToAssignmentsAdd = (Button) mView
        .findViewById(R.id.buttonAssignments_Add);
    assignmentsToAssignmentsAdd.setOnClickListener(this);

    deleteEntries = (Button) mView
        .findViewById(R.id.buttonAssignments_ViewSettings);
    deleteEntries.setOnClickListener(this);

    setPlanner(ALL, true);

    return mView;
  }

  public void setPlannerDefault()
  {
    Log.d("TAG", "Set Planner Default");
    setPlanner(lastChecked, lastUpcoming);
    return;
  }

  public void PlanSettings()
  {

    final AlertDialog.Builder PlannerView = new AlertDialog.Builder(activity);
    final View view = LayoutInflater.from(context).inflate(
        R.layout.plannersettings, null);

    PlannerView.setNegativeButton("Dismiss",
        new android.content.DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int which)
          {
            dialog.dismiss();
          }
        });

    PlannerView.setPositiveButton("Set Planner",
        new android.content.DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int which)
          {
            classes = (Spinner) view
                .findViewById(R.id.spinnerPlanSettings_Classes);

            CheckBox assignments = (CheckBox) view
                .findViewById(R.id.checkBoxPlanSettings_Assignments);
            CheckBox events = (CheckBox) view
                .findViewById(R.id.checkBoxPlanSettings_Events);
            CheckBox reminders = (CheckBox) view
                .findViewById(R.id.checkBoxPlanSettings_Reminders);
            RadioButton upcoming = (RadioButton) view
                .findViewById(R.id.radioPlanSettings_Upcoming);

            String Class = classes.getSelectedItem().toString();
            Log.d("TAG", "Got spinner class: " + Class);
            lastChecked = Class;
            StudyBuddy.setPlannerViewTypes(assignments.isChecked(),
                events.isChecked(), reminders.isChecked());

            setPlanner(Class, upcoming.isChecked());
            lastUpcoming = upcoming.isChecked();

            dialog.dismiss();
          }

        });

    PlannerView.setTitle("Planner View Settings");

    PlannerView.setView(view);

    AlertDialog settingsDialog = PlannerView.create();
    settingsDialog.show();

    classes = (Spinner) settingsDialog
        .findViewById(R.id.spinnerPlanSettings_Classes);
    ArrayList<String> classTitles = StudyBuddy.spinnerArray(true);

    final ArrayAdapter<String> PSAdapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_item, classTitles);
    PSAdapter
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    classes.setAdapter(PSAdapter);
    classes.setSelection(classTitles.indexOf(lastChecked));

    CheckBox assignments = (CheckBox) settingsDialog
        .findViewById(R.id.checkBoxPlanSettings_Assignments);
    CheckBox events = (CheckBox) settingsDialog
        .findViewById(R.id.checkBoxPlanSettings_Events);
    CheckBox reminders = (CheckBox) settingsDialog
        .findViewById(R.id.checkBoxPlanSettings_Reminders);
    RadioButton upcoming = (RadioButton) settingsDialog
        .findViewById(R.id.radioPlanSettings_Upcoming);

    upcoming.setChecked(lastUpcoming);

    RadioButton past = (RadioButton) settingsDialog
        .findViewById(R.id.radioPlanSettings_Past);
    past.setChecked(!lastUpcoming);

    assignments.setChecked(StudyBuddy.Assignments());
    events.setChecked(StudyBuddy.Events());
    reminders.setChecked(StudyBuddy.Reminders());

  }

  protected void setPlanner(String CLASS, boolean present)
  {

    LinearLayout assignPlanner = (LinearLayout) mView
        .findViewById(R.id.assignments_List);
    assignPlanner.removeAllViews();

    LinearLayout eventsPlanner = (LinearLayout) mView
        .findViewById(R.id.events_List);
    eventsPlanner.removeAllViews();

    LinearLayout reminderPlanner = (LinearLayout) mView
        .findViewById(R.id.reminders_List);
    reminderPlanner.removeAllViews();

    ClassObject parent;
    if (CLASS.contentEquals("All"))
      parent = null;
    else
      parent = StudyBuddy.findByName(CLASS);

    // / Sets the Assignments
    TextView assignText = (TextView) mView
        .findViewById(R.id.textAssignments_Title);
    if (StudyBuddy.Assignments())
    {

      ArrayList<EntryObject> assignmentList = StudyBuddy.getEntries(-1,
          "Assignment", parent, present, false);

      assignText.setText("Assignments ("
          + StudyBuddy.getEntries(StudyBuddy.todayId(0), "Assignment", parent,
              true, true).size() + " for today)");

      PlannerAdapter AssignmentAdapter = new PlannerAdapter(getActivity(),
          R.layout.plannerlistitem, assignmentList, this);
      assignText.setVisibility(View.VISIBLE);

      if (assignmentList.size() > 0)
      {

        for (int i = 0; i < (assignmentList.size()); i++)
        {
          View assignItem = AssignmentAdapter.getView(i, null, assignPlanner);
          assignPlanner.addView(assignItem);
          if (i == (assignmentList.size() - 1))
          {
            LinearLayout div = (LinearLayout) assignItem
                .findViewById(R.id.mDivider);
            div.setVisibility(View.GONE);
          }
        }
      }
    }
    else
    {
      assignText.setVisibility(View.GONE);
    }

    // Events


    TextView eventText = (TextView) mView.findViewById(R.id.textEvents_Title);

    if (StudyBuddy.Events())
    {

      ArrayList<EntryObject> eventList = StudyBuddy.getEntries(-1, "Event",
          parent, present, false);

      eventText.setText("Events ("
          + StudyBuddy.getEntries(StudyBuddy.todayId(0), "Event", parent, true,
              true).size() + " for today)");
      eventText.setVisibility(View.VISIBLE);

      PlannerAdapter EventAdapter = new PlannerAdapter(getActivity(),
          R.layout.plannerlistitem, eventList, this);
      if (eventList.size() > 0)
      {
        for (int i = 0; i < (eventList.size()); i++)
        {
          View eventItem = EventAdapter.getView(i, null, eventsPlanner);
          eventsPlanner.addView(eventItem);
          if (i == (eventList.size() - 1))
          {
            LinearLayout div = (LinearLayout) eventItem
                .findViewById(R.id.mDivider);
            div.setVisibility(View.GONE);
          }
        }
      }
    }
    else
    {
      eventText.setVisibility(View.GONE);
    }

    // / reminders

    TextView remindText = (TextView) mView
        .findViewById(R.id.textReminders_Title);

    if (StudyBuddy.Reminders())
    {


      ArrayList<EntryObject> reminderList = StudyBuddy.getEntries(-1,
          "Reminder", parent, present, false);

      remindText.setText("Reminders ("
          + StudyBuddy.getEntries(StudyBuddy.todayId(0), "Reminder", parent,
              true, true).size() + " for today)");

      PlannerAdapter ReminderAdapter = new PlannerAdapter(getActivity(),
          R.layout.plannerlistitem, reminderList, this);
      remindText.setVisibility(View.VISIBLE);
      if (reminderList.size() > 0)
      {

        for (int i = 0; i < reminderList.size(); i++)
        {
          View remindItem = ReminderAdapter.getView(i, null, reminderPlanner);
          reminderPlanner.addView(remindItem);

          if (i == (reminderList.size() - 1))
          {
            LinearLayout div = (LinearLayout) remindItem
                .findViewById(R.id.mDivider);
            div.setVisibility(View.GONE);
          }
        }
      }
    }
    else
    {
      remindText.setVisibility(View.GONE);
    }

  }

  public void onClick(View v)
  {
    switch (v.getId())
    {

    case R.id.buttonAssignments_Add:
      Intent toAdd = new Intent(context, EditPlannerActivity.class);
      toAdd.putExtra("ClassName", "General");
      startActivityForResult(toAdd, 500);
      getActivity().overridePendingTransition(R.anim.in_from_bottom,
          R.anim.hold);

      break;

    case R.id.buttonAssignments_ViewSettings:
      PlanSettings();
      break;

    }

  }

}
