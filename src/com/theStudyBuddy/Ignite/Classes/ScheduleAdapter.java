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

//    ListItem.setOnLongClickListener(new OnLongClickListener()
//    {
//
//      public boolean onLongClick(View arg0)
//      {
//        String className = (current.getParent().getTitle());
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
//
//        boolean nextWeek = false;
//        Calendar asgnDay = Calendar.getInstance();
//        int DOW = Calendar.DAY_OF_WEEK;
//        int ClassDay = AlarmId % 100;
//
//        asgnDay.add(Calendar.DATE, (ClassDay - DOW));
//        if (ClassDay < DOW)
//        {
//          asgnDay.add(Calendar.DATE, 7);
//          nextWeek = true;
//        }
//
//        int aY = asgnDay.get(Calendar.YEAR) * 1000;
//        int aM = asgnDay.get(Calendar.MONTH) * 100;
//        int aD = asgnDay.get(Calendar.DAY_OF_MONTH);
//
//        int dayId = aY + aM + aD;
//        String nxtWk = "";
//        if (nextWeek)
//          nxtWk = " (Next Week)";
//        dialog.setTitle("This Days Entries for " + className + nxtWk);
//
//        ArrayList<EntryObject> classEntries = current.getParent()
//            .getAllEntries();
//        ArrayList<EntryObject> dayEntries = new ArrayList<EntryObject>();
//
//        for (int i = 0; i < classEntries.size(); i++)
//        {
//          if (classEntries.get(i).getDateId() == dayId)
//            dayEntries.add(classEntries.get(i));
//        }
//
//        if (dayEntries.size() == 0)
//        {
//          dialog.setMessage("None Entered");
//        }
//        else
//        {
//          dialog.setMessage("Stuff entered");
//          /*
//           * ListView mListView = new ListView(context);
//           * 
//           * // Put assignments in a string Cursor assignments =
//           * PD.dayNclass(dayId, (ScheduleCursor
//           * .getString(ScheduleCursor.getColumnIndex
//           * (ScheduleData.CLASS_NAME))), "Assingments"); if (assignments !=
//           * null){
//           * 
//           * // SimpleCursorAdapter aAdapter = new
//           * SimpleCursorAdapter(context,android.R.layout.simple_list_item_1,
//           * assignments);
//           * 
//           * }
//           * 
//           * Cursor events = PD.dayNclass(dayId, (ScheduleCursor
//           * .getString(ScheduleCursor
//           * .getColumnIndex(ScheduleData.CLASS_NAME))), "Events"); if (events
//           * != null){
//           * 
//           * }
//           * 
//           * Cursor reminders = PD.dayNclass(dayId, (ScheduleCursor
//           * .getString(ScheduleCursor
//           * .getColumnIndex(ScheduleData.CLASS_NAME))), "Reminders");
//           * 
//           * if (reminders != null){
//           * 
//           * }
//           * 
//           * dialog.setView(mListView);
//           */
//        }
//
//        dialog.setPositiveButton("Dismiss",
//            new android.content.DialogInterface.OnClickListener()
//            {
//              public void onClick(DialogInterface dialog, int which)
//              {
//                dialog.dismiss();
//              }
//            });
//
//        dialog.setNegativeButton("Delete",
//            new android.content.DialogInterface.OnClickListener()
//            {
//              public void onClick(DialogInterface dialog, int arg1)
//              {
//                sba.setAPlannerAssistant(false, AlarmId, null);
//                ScheduleData scheduleData = new ScheduleData(context);
//                scheduleData.delete_byID(Id);
//
//                if (scheduleData.ClassExistanceCheck(classTitle) == null)
//                {
//                  ClassSpinnerData csp = new ClassSpinnerData(context);
//                  csp.deleteItem(classTitle);
//                }
//                dialog.dismiss();
//              }
//            });
//
//        AlertDialog theseAssignments = dialog.create();
//
//        theseAssignments.show();
//
//        return false;
//      }
//
//    });

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
