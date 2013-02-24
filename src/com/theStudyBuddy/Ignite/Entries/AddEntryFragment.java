package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;

public class AddEntryFragment extends Fragment
{
  
  String className;
  FragmentActivity activity;
  Context context;
  View mView;
  
  String AdayId;
  String AdayIdYear;
  String AdayIdMonth;
  String AdayIdDay;
  int AdayIdNum;

  String EdayId;
  String EdayIdYear;
  String EdayIdMonth;
  String EdayIdDay;
  int EdayIdNum;

  String RdayId;
  String RdayIdYear;
  String RdayIdMonth;
  String RdayIdDay;
  int RdayIdNum;

  Button assignmentReturn;
  protected Spinner classSpinnerA;
  protected Spinner classSpinnerE;
  protected Spinner classSpinnerR;

  // assignment Date Pick Button
  private Button datePickAssignment;
  private int AYear;
  private int AMonth;
  private int ADay;

  static final int DATE_DIALOG_IDA = 0;

  // //event
  private Button datePickEvent;
  private int EYear;
  private int EMonth;
  private int EDay;

  static final int DATE_DIALOG_IDE = 1;

  // // Reminder date pick Button
  private Button datePickReminder;
  private int RYear;
  private int RMonth;
  private int RDay;

  static final int DATE_DIALOG_IDR = 2;

  EditText AssignmentText;
  EditText ReminderText;
  EditText EventText;
  Button AssignmentAdd;
  Button EventAdd;
  Button ReminderAdd;

  int ADAYID;
  int AMONTHID;
  int AYEARID;

  int EDAYID;
  int EMONTHID;
  int EYEARID;

  int RDAYID;
  int RMONTHID;
  int RYEARID;

  StudyBuddyApplication StudyBuddy;
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle bundle)
  {
    super.onCreateView(inflater, container, bundle);
    activity = getActivity();
    context = activity.getApplicationContext();
    
    mView = inflater.inflate(R.layout.assignmentadd, container, false);
    
    StudyBuddy = (StudyBuddyApplication) getActivity().getApplication();

    AssignmentAdd = (Button) mView.findViewById(R.id.buttonAssignmentAdd_AddAssignment);

    AssignmentText = (EditText) mView.findViewById(R.id.editTextAssignmentAdd_Assignment);

    EventAdd = (Button) mView.findViewById(R.id.buttonAssignmentAdd_SaveEvent);

    EventText = (EditText) mView.findViewById(R.id.editTextAssignmentAdd_EventTitle);

    ReminderAdd = (Button) mView.findViewById(R.id.buttonAssignmentAdd_SaveReminder);

    ReminderText = (EditText) mView.findViewById(R.id.editTextAssignmentAdd_Reminder);

    // // for class pick spinner
    classSpinnerA = (Spinner) mView.findViewById(R.id.spinnerAssignmentAdd_ClassA);
    classSpinnerE = (Spinner) mView.findViewById(R.id.spinnerAssignmentAdd_ClassE);
    classSpinnerR = (Spinner) mView.findViewById(R.id.spinnerAssignmentAdd_ClassR);

    // fix adapter, make a custom one maybe?

    
    ArrayList<String> classTitles = StudyBuddy.spinnerArray(false);
    final ArrayAdapter<String> scheduleCursorAdapter = new ArrayAdapter<String>(
        context, android.R.layout.simple_spinner_item, classTitles);
    
    classSpinnerA.setAdapter(scheduleCursorAdapter);
    scheduleCursorAdapter
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
    classSpinnerE.setAdapter(scheduleCursorAdapter);
    scheduleCursorAdapter
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    classSpinnerR.setAdapter(scheduleCursorAdapter);
    scheduleCursorAdapter
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
    if(className != null){
      classSpinnerE.setSelection(scheduleCursorAdapter.getPosition(className));
      classSpinnerA.setSelection(scheduleCursorAdapter.getPosition(className));
      classSpinnerR.setSelection(scheduleCursorAdapter.getPosition(className));
    }

    // ///////////////////// Assignment Field
    datePickAssignment = (Button) mView.findViewById(R.id.button1AssignmentAdd_AssignmentDate);
    datePickAssignment.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(DATE_DIALOG_IDA);
      }
    });

    final Calendar A = Calendar.getInstance();
    AYear = A.get(Calendar.YEAR);
    AMonth = A.get(Calendar.MONTH);
    ADay = A.get(Calendar.DAY_OF_MONTH);

    updateDisplayA();

    // //////////////// Event

    datePickEvent = (Button) mView.findViewById(R.id.buttonAssignmentAdd_EventDate);
    datePickEvent.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(DATE_DIALOG_IDE);
      }
    });

    final Calendar E = Calendar.getInstance();
    EYear = E.get(Calendar.YEAR);
    EMonth = E.get(Calendar.MONTH);
    EDay = E.get(Calendar.DAY_OF_MONTH);
    updateDisplayE();

    // ///////////////// Reminder
    datePickReminder = (Button) mView.findViewById(R.id.buttonAssignmentAdd_RemindDate);
    datePickReminder.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(DATE_DIALOG_IDR);
      }
    });

    final Calendar R = Calendar.getInstance();
    RYear = R.get(Calendar.YEAR);
    RMonth = R.get(Calendar.MONTH);
    RDay = R.get(Calendar.DAY_OF_MONTH);

    updateDisplayR();

    
    return mView;
  }


  
  
  private void updateDisplayR()
  {

    RdayIdYear = "" + RYear;
    if (RMonth < 10)
    {
      RdayIdMonth = "0" + RMonth;
    }
    else
    {
      RdayIdMonth = "" + RMonth;
    }
    if (RDay < 10)
    {
      RdayIdDay = "0" + RDay;
    }
    else
    {
      RdayIdDay = "" + RDay;
    }

    datePickReminder.setText(new StringBuilder().append(RMonth + 1).append("/")
        .append(RDay).append("/").append(RYear).append(" "));
  }

  private DatePickerDialog.OnDateSetListener RDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth)
    {
      RYear = year;
      RMonth = monthOfYear;
      RDay = dayOfMonth;
      updateDisplayR();
    }
  };

  private void updateDisplayE()
  {

    EdayIdYear = "" + EYear;
    if (EMonth < 10)
    {
      EdayIdMonth = "0" + EMonth;
    }
    else
    {
      EdayIdMonth = "" + EMonth;
    }
    EDAYID = EDay;
    if (EDay < 10)
    {
      EdayIdDay = "0" + EDay;
    }
    else
    {
      EdayIdDay = "" + EDay;
    }

    datePickEvent.setText(new StringBuilder().append(EMonth + 1).append("/")
        .append(EDay).append("/").append(EYear).append(" "));
  }

  private DatePickerDialog.OnDateSetListener EDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth)
    {
      EYear = year;
      EMonth = monthOfYear;
      EDay = dayOfMonth;
      updateDisplayE();

    }
  };

  private void updateDisplayA()
  {
    AdayIdYear = "" + AYear;
    if (AMonth < 10)
    {
      AdayIdMonth = "0" + AMonth;
    }
    else
    {
      AdayIdMonth = "" + AMonth;
    }
    ADAYID = ADay;
    if (ADay < 10)
    {
      AdayIdDay = "0" + ADay;
    }
    else
    {
      AdayIdDay = "" + ADay;
    }

    datePickAssignment.setText(new StringBuilder().append(AMonth + 1)
        .append("/").append(ADay).append("/").append(AYear).append(" "));

  }

  private DatePickerDialog.OnDateSetListener ADateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth)
    {
      AYear = year;
      AMonth = monthOfYear;
      ADay = dayOfMonth;
      updateDisplayA();
    }
  };

  protected Dialog showDialog(int id)
  {
    switch (id)
    {
    case DATE_DIALOG_IDA:
      return new DatePickerDialog(context, ADateSetListener, AYear, AMonth, ADay);

    case DATE_DIALOG_IDR:
      return new DatePickerDialog(context, RDateSetListener, RYear, RMonth, RDay);

    case DATE_DIALOG_IDE:
      return new DatePickerDialog(context, EDateSetListener, EYear, EMonth, EDay);

    }
    return null;
  }

  public void onClick(View v)
  {
    switch (v.getId())
    {

    case R.id.buttonAssignmentAdd_AddAssignment:
      if (EntryAssignmentAdd())
      {
        AssignmentText.setText(null);
        final Calendar A = Calendar.getInstance();
        AYear = A.get(Calendar.YEAR);
        AMonth = A.get(Calendar.MONTH);
        ADay = A.get(Calendar.DAY_OF_MONTH);
        updateDisplayA();

        Toast.makeText(context,
            getString(R.string.toastAssignmentAdd_AssignmentAdd),
            Toast.LENGTH_SHORT).show();

      }
      break;

    case R.id.buttonAssignmentAdd_SaveEvent:
      if (EntryEventAdd())
      {

        EventText.setText(null);
        final Calendar A = Calendar.getInstance();
        EYear = A.get(Calendar.YEAR);
        EMonth = A.get(Calendar.MONTH);
        EDay = A.get(Calendar.DAY_OF_MONTH);
        updateDisplayE();

        Toast.makeText(context, getString(R.string.toastAssignmentAdd_EventAdd),
            Toast.LENGTH_SHORT).show();

      }
      break;

    case R.id.buttonAssignmentAdd_SaveReminder:
      if (EntryReminderAdd())
      {

        ReminderText.setText(null);
        final Calendar A = Calendar.getInstance();
        RYear = A.get(Calendar.YEAR);
        RMonth = A.get(Calendar.MONTH);
        RDay = A.get(Calendar.DAY_OF_MONTH);
        updateDisplayR();

        Toast.makeText(context,
            getString(R.string.toastAssignmentAdd_ReminderAdd),
            Toast.LENGTH_SHORT).show();
      }
      break;

    }

  }

  public boolean EntryAssignmentAdd()
  {
    String AEntryText = AssignmentText.getText().toString();
    String AEntryClass = classSpinnerA.getSelectedItem().toString();

    if (AEntryText.length() < 2)
    {
      AlertDialog.Builder dialog = new AlertDialog.Builder(context);
      dialog.setTitle(R.string.toastAssignmentAdd_FailEntryTitle);
      dialog.setMessage(R.string.toastAssignmentAdd_FailEntryBody);
      dialog.show();

      return false;
    }

    else
    {

      AdayId = AdayIdYear + AdayIdMonth + AdayIdDay;
      AdayIdNum = Integer.parseInt(AdayId);

      String AEntryType = "Assignment".toString();
      String AEntryDate = (AMonth + 1) + "/" + ADay + "/" + AYear;

      StudyBuddy.addEntry(AEntryType, AEntryClass, AEntryText, AEntryDate,
          AdayIdNum);

      return true;
    }
  }

  public boolean EntryEventAdd()
  {
    String EEntryText = EventText.getText().toString();
    String EEntryClass = classSpinnerE.getSelectedItem().toString();

    if ((EEntryText.length() < 2) || (EEntryClass == null))
    {

      AlertDialog.Builder dialog = new AlertDialog.Builder(context);
      dialog.setTitle(R.string.toastAssignmentAdd_FailEntryTitle);
      dialog.setMessage(R.string.toastAssignmentAdd_FailEntryBody);
      dialog.show();
      return false;
    }

    else
    {

      EdayId = EdayIdYear + EdayIdMonth + EdayIdDay;
      EdayIdNum = Integer.parseInt(EdayId);

      String EEntryType = "Event".toString();
      String EEntryDate = (EMonth + 1) + "/" + EDay + "/" + EYear;

      StudyBuddy.addEntry(EEntryType, EEntryClass, EEntryText, EEntryDate,
          EdayIdNum);

      return true;
    }
  }

  public boolean EntryReminderAdd()
  {
    String REntryText = ReminderText.getText().toString();

    String REntryClass = classSpinnerR.getSelectedItem().toString();
    // if (REntryClass == null){
    // //////--------change later-------------------
    // String noClassesTitle = "Error, No Classes!";
    // String noClassesBody = "Add Classes in Schedule to Continue";
    // /////----------create resource strings-------
    //
    //
    // AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    // dialog.setTitle(noClassesTitle);
    // dialog.setMessage(noClassesBody);
    // dialog.show();
    //
    // return false;
    // }
    //
    if ((REntryText.length() < 2) || (REntryClass == null))
    {

      AlertDialog.Builder dialog = new AlertDialog.Builder(context);
      dialog.setTitle(R.string.toastAssignmentAdd_FailEntryTitle);
      dialog.setMessage(R.string.toastAssignmentAdd_FailEntryBody);
      dialog.show();
      return false;
    }

    else
    {

      RdayId = RdayIdYear + RdayIdMonth + RdayIdDay;
      RdayIdNum = Integer.parseInt(RdayId);

      String REntryType = "Reminder".toString();
      String REntryDate = (RMonth + 1) + "/" + RDay + "/" + RYear;

      StudyBuddy.addEntry(REntryType, REntryClass, REntryText, REntryDate,
          RdayIdNum);

      return true;
    }
  }
  

}
