package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Classes.ClassObject;

public class PlannerAdapter extends ArrayAdapter<EntryObject>
{
  // MorningReminderActivity MorningReminder;
  TextView Class;
  TextView Body;
  TextView DueDate;
  TextView Type;

  Activity activity;
  AssignmentViewActivity AssignmentView;
  StudyBuddyApplication StudyBuddy;
  Context context;
  ArrayList<EntryObject> entries;

  public PlannerAdapter(Activity mActivity, int layout,
      ArrayList<EntryObject> _entries, AssignmentViewActivity AVA)
  {
    super(mActivity, layout, _entries);
    activity = mActivity;
    entries = _entries;
    context = activity.getBaseContext();
    StudyBuddy = (StudyBuddyApplication) activity.getApplication();
    AssignmentView = AVA;
  }

  public View getView(int position, View convertView, ViewGroup parent)
  {
    View PlannerItem = activity.getLayoutInflater().inflate(
        R.layout.plannerlistitem, parent, false);
    final EntryObject current = entries.get(position);

    final int ID = current.getDbId();

    final String CLASS = current.getParent().getTitle();

    final String BODY = current.getText();

    final String DUEDATE = current.getDate();

    final String TYPE = current.getType();

    final TextView ClassName = (TextView) PlannerItem
        .findViewById(R.id.textPlannerView_Class);
    ClassName.setText(CLASS + ": ");

    final TextView EntryBody = (TextView) PlannerItem
        .findViewById(R.id.textPlannerItem_Item);
    EntryBody.setText(BODY);

    final int DayId = current.getDateId();

    final CheckBox ItemStat = (CheckBox) PlannerItem
        .findViewById(R.id.checkBoxPlanner_Item);

    ItemStat.setChecked(current.isCompleted());
    ItemStat.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {
        current.setCompletion(ItemStat.isChecked());
        StudyBuddy.updateStatus(ItemStat.isChecked(), ID);
      }
    });

    final TextView DueDate = (TextView) PlannerItem
        .findViewById(R.id.textPlannerItem_Date);
    DueDate.setText(DUEDATE);

    PlannerItem.setOnLongClickListener(new OnLongClickListener()
    {

      public boolean onLongClick(View item)
      {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String[] types = {"Assignment", "Event", "Reminder"};
        builder.setTitle("Edit Planner Item");

        final View view = LayoutInflater.from(context).inflate(
            R.layout.editplanner, null);
        builder.setView(view);
        final Spinner mType = (Spinner) view.findViewById(R.id.spinnerType);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item, types);
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        int tpos = adapter.getPosition(TYPE);
        mType.setAdapter(adapter);
        mType.setSelection(tpos);

        ArrayList<String> classTitles = StudyBuddy.spinnerArray(true);

        final ArrayAdapter<String> scheduleCursorAdapter = new ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item, classTitles);
        scheduleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        final Spinner mClass = (Spinner) view.findViewById(R.id.spinnerClass);
        mClass.setAdapter(scheduleCursorAdapter);
        mClass.setSelection(classTitles.indexOf(current.getParent().getTitle()));

        final EditText editText = (EditText) view.findViewById(R.id.editText);
        editText.setText(BODY);

        final DatePicker dp = (DatePicker) view.findViewById(R.id.editDate);

        int day = DayId % 100;
        int month = (DayId % 10000) / 100;
        int year = DayId / 10000;

        dp.updateDate(year, month, day);

        builder.setPositiveButton("Apply",
            new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int arg1)
              {
                String EntryText = editText.getText().toString();
                if (EntryText != null)
                {
                  // Entry Type
                  String EntryType = mType.getSelectedItem().toString();
                  // Entry Class

                  String EntryClass = mClass.getSelectedItem().toString();
                  // Dates Stuff
                  int day = dp.getDayOfMonth();
                  int month = dp.getMonth();
                  int year = dp.getYear();
                  String EntryDate = (month + 1) + "/" + day + "/" + year;

                  int DayId = (year * 10000) + (month * 100) + day;
                  Log.d("TAG", "Day Id: " + DayId);
                  StudyBuddy.editEntry(ID, EntryType, EntryClass, EntryText,
                      EntryDate, DayId);
                  
                  ClassObject parent = StudyBuddy.findByName(EntryClass);
                  current.getParent().getAllEntries().remove(current);
                  current.editEntry(EntryText, DayId, parent, EntryType, ID, EntryDate);
                  parent.addEntry(current);
                }

                    AssignmentView.setPlannerDefault();
                dialog.dismiss();
              }
            });

        builder.setNeutralButton("Cancel",
            new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int arg1)
              {
                dialog.dismiss();
              }
            });
        builder.setNegativeButton("Delete",
            new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int arg1)
              {
                StudyBuddy.deleteById(current);
                AssignmentView.setPlannerDefault();
                dialog.dismiss();
              }
            });
        builder.create().show();

        return false;
      }
    });

    return PlannerItem;
  }

}
