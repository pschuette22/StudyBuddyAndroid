package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Classes.ClassObject;
import com.theStudyBuddy.Ignite.Classes.ScheduleViewActivity;
import com.theStudyBuddy.Ignite.Settings.SettingsViewActivity;

public class AssignmentViewActivity extends Fragment implements OnClickListener
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
  RadioButton SortClass;
  RadioButton SortDue;
  
  SimpleCursorAdapter AssignmentClassAdapter;
  ListView AssignmentsClass;
  Dialog PlannerView;

  SimpleCursorAdapter Assignments_Class;
  LayoutInflater infl;

  Cursor TodayPlannerCursor;
  final String ALL = "All";
  View mView;
  boolean lastUpcoming = true;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    
    activity = getActivity();
    context = activity.getBaseContext();
    StudyBuddyActivity.currentFragment = "Assignments";
    infl = inflater;
    StudyBuddy = (StudyBuddyApplication) getActivity().getApplication();
    lastChecked = "All";
    
    // / =================== Planner Part =======================
    mView = inflater.inflate(R.layout.assignmentsview, container, false);

    assignmentsToSchedule = (Button) mView
        .findViewById(R.id.buttonAssignmentsView_ToScheduleView);
    assignmentsToSchedule.setOnClickListener(this);

    assignmentsToSettings = (Button) mView
        .findViewById(R.id.buttonAssignmentsView_ToSettingsView);
    assignmentsToSettings.setOnClickListener(this);

    assignmentsToAssignmentsAdd = (Button) mView
        .findViewById(R.id.buttonAssignments_Add);
    assignmentsToAssignmentsAdd.setOnClickListener(this);

    deleteEntries = (Button) mView.findViewById(R.id.buttonAssignments_Delete);
    deleteEntries.setOnClickListener(this);

    setView = (Button) mView.findViewById(R.id.buttonAssignmentsView_Settings);
    setView.setOnClickListener(this);

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
            StudyBuddy.setPlannerViewTypes(assignments.isChecked(), events.isChecked(), reminders.isChecked());

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
    
    
    final ArrayAdapter<String> PSAdapter = new ArrayAdapter<String>(
        context, android.R.layout.simple_spinner_item, classTitles);
    PSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
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
    
    RadioButton past = (RadioButton) settingsDialog.findViewById(R.id.radioPlanSettings_Past);
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
    if(CLASS.contentEquals("All")) parent = null;
    else parent = StudyBuddy.findByName(CLASS);
    
    // / Sets the Assignments
    LinearLayout assignTitleLine = (LinearLayout) mView
        .findViewById(R.id.assigntitleLine);
    if (StudyBuddy.Assignments())
    {
      Log.d("TAG", "In here");
      assignTitleLine.setVisibility(View.VISIBLE);

      ArrayList<EntryObject> assignmentList = StudyBuddy.getEntries(-1, "Assignment", parent, present, false);
      
      TextView assignText = (TextView) mView
          .findViewById(R.id.textAssignments_Title);
      assignText.setText("Assignments (" + StudyBuddy.getEntries(StudyBuddy.todayId(0), "Assignment", parent, true, true).size() + " for today)");

      PlannerAdapter AssignmentAdapter = new PlannerAdapter(getActivity(),
          R.layout.plannerlistitem, assignmentList, this);
      assignTitleLine.setVisibility(View.VISIBLE);

      if (assignmentList.size() > 0)
      {

        for (int i = 0; i < (assignmentList.size()); i++)
        {
          View assignItem = AssignmentAdapter.getView(i, null, assignPlanner);
          assignPlanner.addView(assignItem);
          if(i == (assignmentList.size()-1)){
            LinearLayout div = (LinearLayout) assignItem.findViewById(R.id.mDivider);
            div.setVisibility(View.GONE);
          }
        }
      }
    }
    else
    {
      assignTitleLine.setVisibility(View.GONE);
    }

    // Events

    LinearLayout eventTitleLine = (LinearLayout) mView
        .findViewById(R.id.eventtitleLine);

    if (StudyBuddy.Events())
    {
      Log.d("TAG", "Setting Events");
      LayoutParams ELP = new LayoutParams(LayoutParams.MATCH_PARENT,
          LayoutParams.WRAP_CONTENT);
      eventTitleLine.setLayoutParams(ELP);
      
      ArrayList<EntryObject> eventList = StudyBuddy.getEntries(-1, "Event", parent, present, false);
      
      TextView eventText = (TextView) mView.findViewById(R.id.textEvents_Title);
      eventText.setText("Events (" + StudyBuddy.getEntries(StudyBuddy.todayId(0), "Event", parent,true, true).size() + " for today)");
      eventTitleLine.setVisibility(View.VISIBLE);

      PlannerAdapter EventAdapter = new PlannerAdapter(getActivity(),
          R.layout.plannerlistitem, eventList,
          this);
      if (eventList.size() > 0)
      {
        for (int i = 0; i < (eventList.size()); i++)
        {
          View eventItem = EventAdapter.getView(i, null, eventsPlanner);
          eventsPlanner.addView(eventItem);
          if(i == (eventList.size()-1)){
            LinearLayout div = (LinearLayout) eventItem.findViewById(R.id.mDivider);
            div.setVisibility(View.GONE);
          }
        }
      }
    }
    else
    {
      eventTitleLine.setVisibility(View.GONE);
    }

    // / reminders

    LinearLayout remindTitleLine = (LinearLayout) mView
        .findViewById(R.id.remindtitleLine);

    if (StudyBuddy.Reminders())
    {

      LayoutParams RLP = new LayoutParams(LayoutParams.MATCH_PARENT,
          LayoutParams.WRAP_CONTENT);
      remindTitleLine.setLayoutParams(RLP);
      Log.d("TAG", "Setting Reminders");

      ArrayList<EntryObject> reminderList = StudyBuddy.getEntries(-1, "Reminder", parent, present, false);

      TextView remindText = (TextView) mView
          .findViewById(R.id.textReminders_Title);
      remindText.setText("Reminders (" + StudyBuddy.getEntries(StudyBuddy.todayId(0),"Reminder", parent, true, true).size() + " for today)");

      PlannerAdapter ReminderAdapter = new PlannerAdapter(getActivity(),
          R.layout.plannerlistitem, reminderList, this);
      remindTitleLine.setVisibility(View.VISIBLE);
      if (reminderList.size() > 0)
      {

        for (int i = 0; i < reminderList.size(); i++)
        {
          View remindItem = ReminderAdapter.getView(i, null, reminderPlanner);
          reminderPlanner.addView(remindItem);
          
          if(i == (reminderList.size()-1)){
            LinearLayout div = (LinearLayout) remindItem.findViewById(R.id.mDivider);
            div.setVisibility(View.GONE);
          }
        }
      }
    }
    else
    {
      remindTitleLine.setVisibility(View.GONE);
    }

  }

  public void onClick(View v)
  {
    switch (v.getId())
    {
    case R.id.buttonAssignmentsView_ToScheduleView:
      FragmentTransaction ft = getActivity().getSupportFragmentManager()
          .beginTransaction();
      ft.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right);
      Fragment newFrag = new ScheduleViewActivity();
      ft.replace(R.id.fragmentContainerUnique, newFrag).commit();

      break;

    case R.id.buttonAssignmentsView_ToSettingsView:
      FragmentTransaction ft2 = getActivity().getSupportFragmentManager()
          .beginTransaction();
      ft2.setCustomAnimations(R.anim.out_to_left, R.anim.in_from_right);
      Fragment newFrag2 = new SettingsViewActivity();
      ft2.replace(R.id.fragmentContainerUnique, newFrag2).commit();

      break;

    case R.id.buttonAssignments_Add:
      Intent toAdd = new Intent(context, AssignmentAddActivity.class);
      toAdd.putExtra("ClassName", "General");
      startActivityForResult(toAdd, 500);
      getActivity().overridePendingTransition(R.anim.in_from_bottom,
          R.anim.hold);

      break;

    case R.id.buttonAssignments_Delete:
      Intent toDelete = new Intent(context, PlannerDeleteActivity.class);
      startActivityForResult(toDelete, 500);
      getActivity().overridePendingTransition(R.anim.in_from_bottom,
          R.anim.hold);

      break;
      

    case R.id.buttonAssignmentsView_Settings:
      PlanSettings();
      break;

    }

  }

}
