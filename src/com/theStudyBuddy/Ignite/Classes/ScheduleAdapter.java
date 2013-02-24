package com.theStudyBuddy.Ignite.Classes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;

public class ScheduleAdapter extends ArrayAdapter<MeetingObject>
{

  CheckBox PlannerAssistant;

  Activity activity;
  Context context;
  ArrayList<MeetingObject> meetings;

  StudyBuddyApplication sba;

  public ScheduleAdapter(Activity mActivity, int layout,
      ArrayList<MeetingObject> ScheduleCursor)
  {
    super(mActivity, layout, ScheduleCursor);

    activity = mActivity;
    context = mActivity.getBaseContext();
    meetings = ScheduleCursor;
    sba = (StudyBuddyApplication) mActivity.getApplication();
  }

  public View getView(int position, View convertView, ViewGroup parent)
  {

    // View ListItem = convertView;
    final MeetingObject current = meetings.get(position);
    View ListItem = activity.getLayoutInflater().inflate(
        R.layout.schedulelistitem, parent, false);

//    final int Id = current.getScheduleId();

    final int AlarmId = current.getStartId();

    final String classTitle = current.getParent().getTitle();
    TextView ClassName = (TextView) ListItem
        .findViewById(R.id.textListItem_Day);
    ClassName.setText(classTitle);

    TextView ClassStart = (TextView) ListItem
        .findViewById(R.id.textListItem_Start);
    String CLASS_START = current.getStart();
    String TIMESTART = SetTime(CLASS_START);
    ClassStart.setText(TIMESTART);

    TextView ClassEnd = (TextView) ListItem.findViewById(R.id.textListItem_End);
    String CLASS_END = current.getEnd();
    String TIMEEND = SetTime(CLASS_END);
    ClassEnd.setText(TIMEEND);

    final CheckBox PlannerAssistant = (CheckBox) ListItem
        .findViewById(R.id.checkBoxListItem_Notify);
    PlannerAssistant.setChecked(current.paIsOn());
    PlannerAssistant.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        current.setPA(PlannerAssistant.isChecked());

        sba.setAPlannerAssistant(PlannerAssistant.isChecked(), AlarmId, current.getParent().getTitle());
      }
    });
    if (!sba.planAssistCheck() || !sba.notificationsOn())
    {
      PlannerAssistant.setClickable(false);
      PlannerAssistant.setChecked(false);
      PlannerAssistant.setText("Feature Disabled");
    }


    if (position == meetings.size() - 1)
    {
      LinearLayout divider = (LinearLayout) ListItem
          .findViewById(R.id.mDivider);
      divider.setVisibility(View.GONE);
    }

    return ListItem;
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
