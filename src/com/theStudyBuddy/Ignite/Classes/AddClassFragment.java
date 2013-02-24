package com.theStudyBuddy.Ignite.Classes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;

public class AddClassFragment extends Fragment
{

  View mView;
  FragmentActivity activity;
  Context context;
  StudyBuddyApplication StudyBuddy;

  EditText ClassNameSE;
  Spinner classNames;
  LinearLayout addClass;
  boolean isOnlineClass = false;
  LayoutInflater inflater;
  LinearLayout addClassList;
  LinearLayout currentClassList;

  public static final String SPINNER_ITEM = "SpinnerItem";

  private int sHour;
  private int sMinute;

  private int eHour;
  private int eMinute;

  CheckBox allSame;

  public View onCreateView(LayoutInflater passedInflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    super.onCreateView(passedInflater, container, savedInstanceState);
    inflater = passedInflater;
    activity = getActivity();
    context = activity.getBaseContext();
    StudyBuddy = (StudyBuddyApplication) activity.getApplication();

    mView = inflater.inflate(R.layout.scheduleadd, container, false);

    addClassList = (LinearLayout) mView
        .findViewById(R.id.scheduleBuilderLayout);
    addClass = (LinearLayout) mView.findViewById(R.id.addClass);
    addClass.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        includeAddClassLayout();
      }
    });

    sHour = 12;
    sMinute = 00;

    eHour = 12;
    eMinute = 00;

    return mView;
  }

  public void includeAddClassLayout()
  {
    View addClassLayout = inflater.inflate(R.layout.addclass_sublayout,
        null);
    addClassList.addView(addClassLayout, 0);

  }

  // public void onClick(View v)
  // {
  // switch (v.getId())
  // {
  //
  // case R.id.buttonScheduleEdit_AddClass:
  // if (saveClass())
  // {
  // Toast.makeText(context, getString(R.string.toastclass_save_success),
  // Toast.LENGTH_SHORT).show();
  // ClassNameSE.setText("");
  // }
  // else if (isOnlineClass)
  // {
  // isOnlineClass = false;
  // // run context if nothing is checked
  // AlertDialog.Builder dialog = new AlertDialog.Builder(context);
  // dialog.setTitle("Is context an Online Class?");
  // dialog
  // .setMessage("If context isn't an online class, days and times must be inserted");
  // dialog.setPositiveButton("Yes",
  // new android.content.DialogInterface.OnClickListener()
  // {
  // public void onClick(DialogInterface arg0, int arg1)
  // {
  // String ClassNameText = ClassNameSE.getText().toString();
  // ClassSpinnerData ClassSpinnerdb = new ClassSpinnerData(
  // context);
  // ClassSpinnerdb.insertSpinnerClass(ClassNameText);
  //
  // StudyBuddy.getOrCreate(ClassNameText, true);
  //
  // arg0.dismiss();
  //
  // Toast.makeText(context,
  // getString(R.string.toastclass_save_success),
  // Toast.LENGTH_SHORT).show();
  // ClassNameSE.setText("");
  // }
  // });
  // dialog.setNegativeButton("No ",
  // new android.content.DialogInterface.OnClickListener()
  // {
  // public void onClick(DialogInterface arg0, int arg1)
  // {
  // arg0.dismiss();
  // }
  // });
  // dialog.show();
  //
  // }
  // else
  // {
  // AlertDialog.Builder dialog = new AlertDialog.Builder(context);
  // dialog.setTitle("Class not saved");
  // dialog
  // .setMessage("Class name too short (3 characters or more) or the start time was entered as later than the end time");
  // dialog.setNeutralButton("ok",
  // new android.content.DialogInterface.OnClickListener()
  // {
  // public void onClick(DialogInterface arg0, int arg1)
  // {
  // arg0.dismiss();
  // }
  // });
  // dialog.show();
  // }
  //
  // break;
  //
  // case R.id.buttonScheduleEdit_Return:
  // activity.onBackPressed();
  // break;
  //
  // }
  //
  // }
  // }
  //
  //
  // private static String pad(int c)
  // {
  // if (c >= 10)
  // return String.valueOf(c);
  // else
  // return "0" + String.valueOf(c);
  // }

  // ///////////////////// Monday Start Time
  // private void updateDisplayMS()
  // {
  // TimeStartMon.setText(new StringBuilder().append(pad(sHour)).append(":")
  // .append(pad(sMinute)));
  //
  // }
  //
  // private TimePickerDialog.OnTimeSetListener METimeSetListener = new
  // TimePickerDialog.OnTimeSetListener()
  // {
  // public void onTimeSet(TimePicker view, int hourOfDay, int minute)
  // {
  // eHour = hourOfDay;
  // eMinute = minute;
  // if (allSame.isChecked())
  // updateAllE();
  // else
  // updateDisplayME();
  // }
  // };

  // protected Dialog showDialog(int id)
  // {
  // switch (id)
  // {
  // case TIME_DIALOG_MONS:
  // return new TimePickerDialog(context, MSTimeSetListener, sHour, sMinute,
  // false);

  // / public boolean onKeyMultiple()
  public boolean saveClass()
  {
    // String ClassNameText = ClassNameSE.getText().toString();
    //
    // if (ClassNameText.length() < 2)
    // {
    // return false;
    // }
    // if ((!CBMonday.isChecked()) && (!CBTuesday.isChecked())
    // && (!CBWednesday.isChecked()) && (!CBThursday.isChecked())
    // && (!CBFriday.isChecked()) && (!CBSaturday.isChecked())
    // && (!CBSunday.isChecked()))
    // {
    // isOnlineClass = true;
    // return false;
    // }
    //
    // else
    // {
    //
    // boolean paOn = false;
    // if (StudyBuddy.notificationsOn() || StudyBuddy.planAssistCheck()){
    // paOn = true;
    // }
    //
    // ClassObject parent = StudyBuddy.getOrCreate(ClassNameText, false);
    //
    // if (CBMonday.isChecked())
    // {

    // String IDHR = ClassEndText.substring(0, 2);
    // String IDMN = ClassEndText.substring(3, 5);
    // String ALRMID = Calendar.MONDAY + IDHR + IDMN;
    // int AlarmId = Integer.parseInt(ALRMID);
    //
    // String STHR = ClassStartText.substring(0, 2);
    // String STMN = ClassStartText.substring(3, 5);
    // String STRTID = "" + STHR + STMN;
    // int StartId = Integer.parseInt(STRTID);
    // int EndId = Integer.parseInt(ALRMID);

    // if (invalidTimes(StartId, EndId)) return false;
    // if (paOn){
    // StudyBuddy.setAPlannerAssistant(true, AlarmId, ClassNameText);
    // }
    //
    // StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
    // ClassEndText, AlarmId, StartId);
    //
    //
    // }
    //
    //
    //
    // sHour = 12;
    // sMinute = 00;
    // eHour = 12;
    // eMinute = 00;
    //
    return true;
  }

  public boolean invalidTimes(int start, int end)
  {
    if (start >= end)
      return true;
    else
      return false;
  }

}
