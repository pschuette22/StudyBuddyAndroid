package com.theStudyBuddy.Ignite.Classes;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Settings.HolidayActivity;

public class ScheduleViewFragment extends Fragment implements OnClickListener

{
  View mView;
  Context context;
  Activity activity;
  StudyBuddyApplication studyBuddy;
  
  Button scheduleToAssignments;
  Button scheduleToSettings;
  Button scheduleEdit;
  Button deleteClasses;

  ArrayList<MeetingObject> mondayCursor;
  ArrayList<MeetingObject> tuesdayCursor;
  ArrayList<MeetingObject> wednesdayCursor;
  ArrayList<MeetingObject> thursdayCursor;
  ArrayList<MeetingObject> fridayCursor;
  ArrayList<MeetingObject> saturdayCursor;
  ArrayList<MeetingObject> sundayCursor;
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {

    activity = getActivity();
    context = activity.getBaseContext();
    studyBuddy = (StudyBuddyApplication) activity.getApplication();

    mView = inflater.inflate(R.layout.scheduleview, container, false);

    scheduleEdit = (Button) mView
        .findViewById(R.id.buttonScheduleView_ToScheduleEdit);
    scheduleEdit.setOnClickListener(this);

    deleteClasses = (Button) mView
        .findViewById(R.id.buttonSchedule_DeleteClasses);
    deleteClasses.setOnClickListener(this);

    // /// Lists


    // /------------MONDAY----------------
    mondayCursor = studyBuddy.getClassTimes("Monday");
    if (mondayCursor.size() > 0)
    {
      LinearLayout MondaySchedule = (LinearLayout) mView
          .findViewById(R.id.textViewSchedule_MondaySchedule);

      ScheduleAdapter MondayAdapter = new ScheduleAdapter(activity, 0,
           mondayCursor);

      for (int i = 0; i < mondayCursor.size(); i++)
      {
        View listItem = MondayAdapter.getView(i, null, MondaySchedule);
        MondaySchedule.addView(listItem);
      }

    }


    // / Tuesday
    tuesdayCursor = studyBuddy.getClassTimes("Tuesday");
    if (tuesdayCursor.size() > 0)
    {
      LinearLayout TuesdaySchedule = (LinearLayout) mView
          .findViewById(R.id.textViewSchedule_TuesdaySchedule);

      ScheduleAdapter TuesdayAdapter = new ScheduleAdapter(activity,
          R.layout.schedulelistitem, tuesdayCursor);
      for (int i = 0; i < tuesdayCursor.size(); i++)
      {
        View listItem = TuesdayAdapter.getView(i, null, TuesdaySchedule);
        TuesdaySchedule.addView(listItem);
      }
    }

    // / Wednesday

    wednesdayCursor = studyBuddy.getClassTimes("Wednesday");
    if (wednesdayCursor.size() > 0)
    {
      LinearLayout WednesdaySchedule = (LinearLayout) mView
          .findViewById(R.id.textViewSchedule_WednesdaySchedule);

      ScheduleAdapter WednesdayAdapter = new ScheduleAdapter(activity,
          R.layout.schedulelistitem, wednesdayCursor);

      for (int i = 0; i < wednesdayCursor.size(); i++)
      {
        View listItem = WednesdayAdapter.getView(i, null, WednesdaySchedule);
        WednesdaySchedule.addView(listItem);
      }
    }

    // / Thursday

    thursdayCursor = studyBuddy.getClassTimes("Thursday");
    if (thursdayCursor.size() > 0)
    {
      LinearLayout ThursdaySchedule = (LinearLayout) mView
          .findViewById(R.id.textViewSchedule_ThursdaySchedule);

      ScheduleAdapter ThursdayAdapter = new ScheduleAdapter(activity,
          R.layout.schedulelistitem, thursdayCursor);
      for (int i = 0; i < thursdayCursor.size(); i++)
      {
        View listItem = ThursdayAdapter.getView(i, null, ThursdaySchedule);
        ThursdaySchedule.addView(listItem);
      }
    }

    // / Friday
    fridayCursor = studyBuddy.getClassTimes("Friday");
    if (fridayCursor.size() > 0)
    {
      LinearLayout FridaySchedule = (LinearLayout) mView
          .findViewById(R.id.textViewSchedule_FridaySchedule);

      ScheduleAdapter FridayAdapter = new ScheduleAdapter(activity,
          R.layout.schedulelistitem, fridayCursor);
      for (int i = 0; i < fridayCursor.size(); i++)
      {
        View listItem = FridayAdapter.getView(i, null, FridaySchedule);
        FridaySchedule.addView(listItem);
      }
    }

    // / Saturday

    saturdayCursor = studyBuddy.getClassTimes("Saturday");
    if (saturdayCursor.size() > 0)
    {
      LinearLayout SaturdaySchedule = (LinearLayout) mView
          .findViewById(R.id.textViewSchedule_SaturdaySchedule);

      ScheduleAdapter SaturdayAdapter = new ScheduleAdapter(activity,
          R.layout.schedulelistitem, saturdayCursor);

      for (int i = 0; i < saturdayCursor.size(); i++)
      {
        View listItem = SaturdayAdapter.getView(i, null, SaturdaySchedule);
        SaturdaySchedule.addView(listItem);
      }
    }

    // / Sunday

    sundayCursor = studyBuddy.getClassTimes("Sunday");

    if (sundayCursor.size() > 0)
    {
      LinearLayout SundaySchedule = (LinearLayout) mView
          .findViewById(R.id.textViewSchedule_SundaySchedule);

      ScheduleAdapter SundayAdapter = new ScheduleAdapter(activity,
          R.layout.schedulelistitem, sundayCursor);
      for (int i = 0; i < sundayCursor.size(); i++)
      {
        View listItem = SundayAdapter.getView(i, null, SundaySchedule);
        SundaySchedule.addView(listItem);
      }
    }

    return mView;
  }


  // listview shinanigans

  public void onClick(View v)
  {

    switch (v.getId())
    {

    case R.id.buttonScheduleView_ToScheduleEdit:
      Intent toES = new Intent(context, EditScheduleActivity.class);
      startActivityForResult(toES, 500);
      getActivity().overridePendingTransition(R.anim.in_from_bottom,
          R.anim.hold);
      break;

    case R.id.buttonSchedule_DeleteClasses:
      Intent toHA = new Intent(context, HolidayActivity.class);
      startActivityForResult(toHA, 500);
      getActivity().overridePendingTransition(R.anim.in_from_bottom,
          R.anim.hold);
      break;

    }

  }
  
  /// this is going to be called from the delete void in the adapter
  
  public void resetDay(int dayCode){
    switch(dayCode){
    
    case Calendar.MONDAY:
      
      break;
    case Calendar.TUESDAY:
      
      break;
      
    case Calendar.WEDNESDAY:
      
      break;
      
    case Calendar.THURSDAY:
  
      break;
    case Calendar.FRIDAY:
  
      break;
  
    case Calendar.SATURDAY:
  
      break;
    case Calendar.SUNDAY:
      
      break;
    }
    
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
