package com.theStudyBuddy.Ignite.Settings;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Classes.ClassDeleteActivity;
import com.theStudyBuddy.Ignite.Classes.ClassObject;
import com.theStudyBuddy.Ignite.Classes.ClassSpinnerData;
import com.theStudyBuddy.Ignite.Classes.ScheduleData;
import com.theStudyBuddy.Ignite.Classes.ScheduleViewActivity;
import com.theStudyBuddy.Ignite.Entries.AssignmentViewActivity;
import com.theStudyBuddy.Ignite.Entries.PlannerData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


public class SettingsViewActivity extends Fragment implements OnClickListener
{

  ScheduleData scheduleData;
  PlannerData plannerData;
  ClassSpinnerData CSPData;
  
  StudyBuddyApplication StudyBuddy;
  Button settingsToAssignments;
  Button settingsToSchedule;
  Button ClearEverything;
  Button ClearPlanner;
  Button ClearSchedule;
  Button DeleteSelect;
  Button LaunchTutorial;
  Button SetHolidays;
  
  CheckBox Notifications;
  CheckBox PlannerAssistant;
  CheckBox Morning;
  CheckBox Nightly;
  Context context;
  Activity activity;
  
//  String adUnitId = "a14f89f84e10faa";
  
  View mView;

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    super.onCreateView(inflater, container, savedInstanceState);
    activity = this.getActivity();
    context = activity.getBaseContext();
    
    StudyBuddyActivity.currentFragment = "Settings";

    StudyBuddy = (StudyBuddyApplication) activity.getApplication();

    mView = inflater.inflate(R.layout.settingsview, container, false);
    
    settingsToAssignments = (Button) mView.findViewById(R.id.buttonSettings_ToAssignments);
    settingsToAssignments.setOnClickListener(this);

    settingsToSchedule = (Button) mView.findViewById(R.id.buttonSettings_ToSchedule);
    settingsToSchedule.setOnClickListener(this);
    
    LaunchTutorial = (Button) mView.findViewById(R.id.launchTutorial);
    LaunchTutorial.setOnClickListener(this);

    SetHolidays = (Button) mView.findViewById(R.id.setHolidays);
    SetHolidays.setOnClickListener(this);
    
    ClearEverything = (Button) mView.findViewById(R.id.buttonSettings_ClearAll);
    ClearEverything.setOnClickListener(this);

    ClearPlanner = (Button) mView.findViewById(R.id.buttonSettings_ClearPlanner);
    ClearPlanner.setOnClickListener(this);

    ClearSchedule = (Button) mView.findViewById(R.id.buttonSettings_ClearSchedule);
    ClearSchedule.setOnClickListener(this);

    DeleteSelect = (Button) mView.findViewById(R.id.buttonSettings_DeleteIndividual);
    DeleteSelect.setOnClickListener(this);

    Notifications = (CheckBox) mView.findViewById(R.id.checkBox_NotificationsOn);
    Notifications.setChecked(StudyBuddy.notificationsOn());
    Notifications.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        if (Notifications.isChecked())
        {
          PlannerAssistant.setClickable(true);
          PlannerAssistant.setChecked(StudyBuddy.planAssistCheck());

          Morning.setClickable(true);
          Morning.setChecked(StudyBuddy.checkMorning());

          Nightly.setClickable(true);
          Nightly.setChecked(StudyBuddy.checkNightly());
          
          StudyBuddy.setNotifications(true);
          
        }
        else
        {
          
          PlannerAssistant.setClickable(false);
          PlannerAssistant.setChecked(false);

          Morning.setClickable(false);
          Morning.setChecked(false);

          Nightly.setClickable(false);
          Nightly.setChecked(false);


          StudyBuddy.setNotifications(false);
        }

      }
    });

    PlannerAssistant = (CheckBox) mView.findViewById(R.id.checkBox_PlannerAssistantOn);
    PlannerAssistant.setChecked(StudyBuddy.planAssistCheck() && StudyBuddy.notificationsOn());
    PlannerAssistant.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        StudyBuddy.setPlannerAssistant(PlannerAssistant.isChecked(), true);
      }
    });
    PlannerAssistant
        .setClickable(StudyBuddy.notificationsOn());


    Morning = (CheckBox) mView.findViewById(R.id.checkBox_NotificationsMorningOn);
    Morning.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        StudyBuddy.setMorning(Morning.isChecked(), true);
      }
    });
    Morning.setClickable(StudyBuddy.notificationsOn());
    Morning.setChecked(StudyBuddy.notificationsOn() && StudyBuddy.checkMorning());


    Nightly = (CheckBox) mView.findViewById(R.id.checkBox_NightlyOn);
    Nightly.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        StudyBuddy.setNightly(Nightly.isChecked(), true);
      }
    });
    
    Nightly.setClickable(StudyBuddy.notificationsOn());
    Nightly.setChecked(StudyBuddy.notificationsOn() && StudyBuddy.checkNightly());


    return mView;
  }


  public void onClick(View v)
  {

    switch (v.getId())
    {
    case R.id.buttonSettings_ToAssignments:
      FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
      ft.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right);
      Fragment newFrag = new AssignmentViewActivity();
      ft.replace(R.id.fragmentContainerUnique, newFrag).commit();

      break;

    case R.id.buttonSettings_ToSchedule:
      FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
      ft2.setCustomAnimations(R.anim.out_to_left, R.anim.in_from_right);
      Fragment newFrag2 = new ScheduleViewActivity();
      ft2.replace(R.id.fragmentContainerUnique, newFrag2).commit();

      break;

    case R.id.launchTutorial:
      launchTutorial();
      break;
      
    case R.id.buttonSettings_ClearAll:
      scheduleData = new ScheduleData(activity);
      plannerData = new PlannerData(activity);
      CSPData = new ClassSpinnerData(activity);

      AlertDialog.Builder Confirm = new AlertDialog.Builder(activity);
      Confirm.setPositiveButton("Yes",
          new android.content.DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {

              TurnPAoff();

              scheduleData.clearSchedule();
              plannerData.clearPlanner();
              CSPData.clearSpinner();
              
              StudyBuddy.getAllEntries().clear();
              StudyBuddy.getAllMeetings().clear();
              StudyBuddy.getAllClasses().clear();
              ClassObject general = new ClassObject("General", -1, false);
              StudyBuddy.getAllClasses().add(general);

            }
          });
      Confirm.setNegativeButton("Cancel",
          new android.content.DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
              dialog.dismiss();
            }
          });

      Confirm.setTitle("Clear Everything Confirmation");
      Confirm.show();
      break;

    case R.id.buttonSettings_ClearPlanner:
      plannerData = new PlannerData(activity);

      AlertDialog.Builder ConfirmPC = new AlertDialog.Builder(activity);
      ConfirmPC.setPositiveButton("Yes",
          new android.content.DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
              TurnPAoff();
              plannerData.clearPlanner();
              StudyBuddy.getAllEntries().clear();
            }
          });
      ConfirmPC.setNegativeButton("Cancel",
          new android.content.DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
              dialog.dismiss();
            }
          });

      ConfirmPC.setTitle("Clear Planner Confirmation");
      ConfirmPC.show();

      break;

    case R.id.buttonSettings_ClearSchedule:
      scheduleData = new ScheduleData(activity);
      CSPData = new ClassSpinnerData(activity);

      AlertDialog.Builder ConfirmCS = new AlertDialog.Builder(activity);
      ConfirmCS.setPositiveButton("Yes",
          new android.content.DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
              TurnPAoff();

              scheduleData.clearSchedule();
              CSPData.clearSpinner();
              
              StudyBuddy.getAllMeetings().clear();
              StudyBuddy.getAllClasses().clear();
              ClassObject general = new ClassObject("General", -1, false);
              StudyBuddy.getAllClasses().add(general);
              
            }
          });
      ConfirmCS.setNegativeButton("Cancel",
          new android.content.DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
              dialog.dismiss();
            }
          });

      ConfirmCS.setTitle("Clear Schedule Confirmation");
      ConfirmCS.show();

      break;

    case R.id.buttonSettings_DeleteIndividual:
      Intent toCD = new Intent(context, ClassDeleteActivity.class);
      startActivityForResult(toCD, 500);
      getActivity().overridePendingTransition(R.anim.in_from_bottom,
          R.anim.hold);
      break;

    case R.id.setHolidays:
      Intent setHolidays = new Intent(context, HolidayActivity.class);
      startActivityForResult(setHolidays, 500);
      getActivity().overridePendingTransition(R.anim.in_from_bottom,
          R.anim.hold);
      break;
      
      
    }
  }

  public void TurnPAoff()
  {
    StudyBuddy.setPlannerAssistant(false, false);
  }

  public void TurnPAon()
  {
    StudyBuddy.setPlannerAssistant(true, false);

  }
  
  public void launchTutorial(){
    String video_path = "http://www.youtube.com/watch?v=s3go02QC0w8&feature=youtu.be";
    Uri uri = Uri.parse(video_path);
    
    uri = Uri.parse("vnd.youtube:"  + uri.getQueryParameter("v"));

    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
    
  }

}
