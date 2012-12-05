package com.theStudyBuddy.Ignite.Classes;

import java.util.Calendar;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.R.anim;
import com.theStudyBuddy.Ignite.R.id;
import com.theStudyBuddy.Ignite.R.layout;
import com.theStudyBuddy.Ignite.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditScheduleActivity extends Activity implements OnClickListener

{

  EditText ClassNameSE;
  Spinner classNames;
  Button Return;
  Button AddClass;
  boolean isOnlineClass = false;

  public static final String SPINNER_ITEM = "SpinnerItem";

  private int sHour;
  private int sMinute;

  private int eHour;
  private int eMinute;

  CheckBox allSame;

  // / monday
  CheckBox CBMonday;
  Button TimeStartMon;
  Button TimeEndMon;
  static final int TIME_DIALOG_MONS = 11;
  static final int TIME_DIALOG_MONE = 12;

  // //tuesday
  CheckBox CBTuesday;
  Button TimeStartTue;
  Button TimeEndTue;
  static final int TIME_DIALOG_TUES = 21;
  static final int TIME_DIALOG_TUEE = 22;

  // // wednesday
  CheckBox CBWednesday;
  Button TimeStartWed;
  Button TimeEndWed;
  static final int TIME_DIALOG_WEDS = 31;
  static final int TIME_DIALOG_WEDE = 32;

  // // thursday
  CheckBox CBThursday;
  Button TimeStartThu;
  Button TimeEndThu;
  static final int TIME_DIALOG_THUS = 41;
  static final int TIME_DIALOG_THUE = 42;

  // // friday
  CheckBox CBFriday;
  Button TimeStartFri;
  Button TimeEndFri;
  static final int TIME_DIALOG_FRIS = 51;
  static final int TIME_DIALOG_FRIE = 52;

  // /saturday
  CheckBox CBSaturday;
  Button TimeStartSat;
  Button TimeEndSat;
  static final int TIME_DIALOG_SATS = 61;
  static final int TIME_DIALOG_SATE = 62;

  // / sunday
  CheckBox CBSunday;
  Button TimeStartSun;
  Button TimeEndSun;
  static final int TIME_DIALOG_SUNS = 71;
  static final int TIME_DIALOG_SUNE = 72;

  StudyBuddyApplication StudyBuddy;

  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    Intent intent = new Intent(getBaseContext(), StudyBuddyActivity.class);
    intent.putExtra(android.content.Intent.EXTRA_TEXT, "Schedule");
    startActivityForResult(intent, 500);
    overridePendingTransition(0, R.anim.out_to_bottom);
  }

  @Override
  public void onCreate(Bundle bundle)
  {

    super.onCreate(bundle);

    setContentView(R.layout.scheduleedit);
    StudyBuddy = (StudyBuddyApplication) getApplication();
    
    Return = (Button) findViewById(R.id.buttonScheduleEdit_Return);
    Return.setOnClickListener(this);

    AddClass = (Button) findViewById(R.id.buttonScheduleEdit_AddClass);
    AddClass.setOnClickListener(this);

    ClassNameSE = (EditText) findViewById(R.id.editTextScheduleEdit_ClassName);
    allSame = (CheckBox) findViewById(R.id.checkBoxEditSchedule_SameTime);

    CBMonday = (CheckBox) findViewById(R.id.checkBoxScheduleEdit_Mon);
    CBMonday.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {

        if (CBMonday.isChecked())
        {
          updateDisplayMS();
          updateDisplayME();
        }
        else
        {
          TimeStartMon.setText("--:--");
          TimeEndMon.setText("--:--");
        }

      }
    });
    CBTuesday = (CheckBox) findViewById(R.id.checkBoxScheduleEdit_Tues);
    CBTuesday.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {

        if (CBTuesday.isChecked())
        {
          updateDisplayTuS();
          updateDisplayTuE();
        }
        else
        {
          TimeStartTue.setText("--:--");
          TimeEndTue.setText("--:--");
        }

      }
    });
    CBWednesday = (CheckBox) findViewById(R.id.checkBoxScheduleEdit_Wed);
    CBWednesday.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {

        if (CBWednesday.isChecked())
        {
          updateDisplayWS();
          updateDisplayWE();
        }
        else
        {
          TimeStartWed.setText("--:--");
          TimeEndWed.setText("--:--");
        }

      }
    });
    CBThursday = (CheckBox) findViewById(R.id.checkBoxScheduleEdit_Thur);
    CBThursday.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {
        if (CBThursday.isChecked())
        {
          updateDisplayThS();
          updateDisplayThE();
        }
        else
        {
          TimeStartThu.setText("--:--");
          TimeEndThu.setText("--:--");
        }

      }
    });

    CBFriday = (CheckBox) findViewById(R.id.checkBoxScheduleEdit_Fri);
    CBFriday.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {

        if (CBFriday.isChecked())
        {
          updateDisplayFS();
          updateDisplayFE();
        }
        else
        {
          TimeStartFri.setText("--:--");
          TimeEndFri.setText("--:--");
        }

      }
    });
    CBSaturday = (CheckBox) findViewById(R.id.checkBoxScheduleEdit_Sat);
    CBSaturday.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {

        if (CBSaturday.isChecked())
        {
          updateDisplaySaS();
          updateDisplaySaE();
        }
        else
        {
          TimeStartSat.setText("--:--");
          TimeEndSat.setText("--:--");
        }

      }
    });
    CBSunday = (CheckBox) findViewById(R.id.checkBoxScheduleEdit_Sun);
    CBSunday.setOnClickListener(new OnClickListener()
    {
      public void onClick(View arg0)
      {

        if (CBSunday.isChecked())
        {
          updateDisplaySuS();
          updateDisplaySuE();
        }
        else
        {
          TimeStartSun.setText("--:--");
          TimeEndSun.setText("--:--");
        }

      }
    });

    // ///////// time selectors, dont fuck with this
    TimeStartMon = (Button) findViewById(R.id.buttonScheduleEdit_MonStart);
    TimeStartMon.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_MONS);
      }
    });
    TimeStartMon.setText("--:--");

    TimeEndMon = (Button) findViewById(R.id.buttonScheduleEdit_MonEnds);
    TimeEndMon.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_MONE);
      }
    });
    TimeEndMon.setText("--:--");

    TimeStartTue = (Button) findViewById(R.id.buttonScheduleEdit_TuesStart);
    TimeStartTue.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_TUES);
      }
    });
    TimeStartTue.setText("--:--");

    TimeEndTue = (Button) findViewById(R.id.buttonScheduleEdit_TuesEnds);
    TimeEndTue.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_TUEE);
      }
    });
    TimeEndTue.setText("--:--");

    TimeStartWed = (Button) findViewById(R.id.buttonScheduleEdit_WedsStart);
    TimeStartWed.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_WEDS);
      }
    });
    TimeStartWed.setText("--:--");

    TimeEndWed = (Button) findViewById(R.id.buttonScheduleEdit_WedsEnds);
    TimeEndWed.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_WEDE);
      }
    });
    TimeEndWed.setText("--:--");

    TimeStartThu = (Button) findViewById(R.id.buttonScheduleEdit_ThurStart);
    TimeStartThu.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_THUS);
      }
    });
    TimeStartThu.setText("--:--");

    TimeEndThu = (Button) findViewById(R.id.buttonScheduleEdit_ThurEnds);
    TimeEndThu.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_THUE);
      }
    });
    TimeEndThu.setText("--:--");

    TimeStartFri = (Button) findViewById(R.id.buttonScheduleEdit_FriStart);
    TimeStartFri.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_FRIS);
      }
    });
    TimeStartFri.setText("--:--");

    TimeEndFri = (Button) findViewById(R.id.buttonScheduleEdit_FriEnds);
    TimeEndFri.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_FRIE);
      }
    });
    TimeEndFri.setText("--:--");

    TimeStartSat = (Button) findViewById(R.id.buttonScheduleEdit_SatStart);
    TimeStartSat.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_SATS);
      }
    });
    TimeStartSat.setText("--:--");

    TimeEndSat = (Button) findViewById(R.id.buttonScheduleEdit_SatEnds);
    TimeEndSat.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_SATE);
      }
    });
    TimeEndSat.setText("--:--");

    TimeStartSun = (Button) findViewById(R.id.buttonScheduleEdit_SunStart);
    TimeStartSun.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_SUNS);
      }
    });
    TimeStartSun.setText("--:--");

    TimeEndSun = (Button) findViewById(R.id.buttonScheduleEdit_SunEnds);
    TimeEndSun.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        showDialog(TIME_DIALOG_SUNE);
      }
    });
    TimeEndSun.setText("--:--");

    sHour = 12;
    sMinute = 00;

    eHour = 12;
    eMinute = 00;

  }

  private static String pad(int c)
  {
    if (c >= 10)
      return String.valueOf(c);
    else
      return "0" + String.valueOf(c);
  }

  // ///////////////////// Monday Start Time
  private void updateDisplayMS()
  {
    TimeStartMon.setText(new StringBuilder().append(pad(sHour)).append(":")
        .append(pad(sMinute)));

  }

  private TimePickerDialog.OnTimeSetListener METimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      eHour = hourOfDay;
      eMinute = minute;
      if (allSame.isChecked())
        updateAllE();
      else
        updateDisplayME();
    }
  };

  // /////////////////// Monday End Time

  private void updateDisplayME()
  {
    TimeEndMon.setText(new StringBuilder().append(pad(eHour)).append(":")
        .append(pad(eMinute)));
  }

  private TimePickerDialog.OnTimeSetListener MSTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      sHour = hourOfDay;
      sMinute = minute;
      if (allSame.isChecked())
        updateAllS();
      else
        updateDisplayMS();

    }
  };

  // ///////// Tuesday Start Time
  private void updateDisplayTuS()
  {
    TimeStartTue.setText(new StringBuilder().append(pad(sHour)).append(":")
        .append(pad(sMinute)));

  }

  private TimePickerDialog.OnTimeSetListener TuSTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      sHour = hourOfDay;
      sMinute = minute;
      if (allSame.isChecked())
        updateAllS();
      else
        updateDisplayTuS();

    }
  };

  // /////// Tuesday End Time

  private void updateDisplayTuE()
  {
    TimeEndTue.setText(new StringBuilder().append(pad(eHour)).append(":")
        .append(pad(eMinute)));
  }

  private TimePickerDialog.OnTimeSetListener TuETimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      eHour = hourOfDay;
      eMinute = minute;
      if (allSame.isChecked())
        updateAllE();
      else
        updateDisplayTuE();
    }
  };

  // /////// Wednesday Start Time

  private void updateDisplayWS()
  {
    TimeStartWed.setText(new StringBuilder().append(pad(sHour)).append(":")
        .append(pad(sMinute)));
  }

  private TimePickerDialog.OnTimeSetListener WSTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      sHour = hourOfDay;
      sMinute = minute;
      if (allSame.isChecked())
        updateAllS();
      else
        updateDisplayWS();

    }
  };

  // //////// Wednesday End Time

  private void updateDisplayWE()
  {
    TimeEndWed.setText(new StringBuilder().append(pad(eHour)).append(":")
        .append(pad(eMinute)));
  }

  private TimePickerDialog.OnTimeSetListener WETimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      eHour = hourOfDay;
      eMinute = minute;

      if (allSame.isChecked())
        updateAllE();
      else
        updateDisplayWE();
    }
  };

  // ///////////// Thursday Start
  private void updateDisplayThS()
  {
    TimeStartThu.setText(new StringBuilder().append(pad(sHour)).append(":")
        .append(pad(sMinute)));
  }

  private TimePickerDialog.OnTimeSetListener ThSTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      sHour = hourOfDay;
      sMinute = minute;
      if (allSame.isChecked())
        updateAllS();
      else
        updateDisplayThS();
    }
  };

  // //////////////////Thursday End
  private void updateDisplayThE()
  {
    TimeEndThu.setText(new StringBuilder().append(pad(eHour)).append(":")
        .append(pad(eMinute)));
  }

  private TimePickerDialog.OnTimeSetListener ThETimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      eHour = hourOfDay;
      eMinute = minute;
      if (allSame.isChecked())
        updateAllE();
      else
        updateDisplayThE();
    }
  };

  // ///////////// Friday Start
  private void updateDisplayFS()
  {
    TimeStartFri.setText(new StringBuilder().append(pad(sHour)).append(":")
        .append(pad(sMinute)));
  }

  private TimePickerDialog.OnTimeSetListener FSTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      sHour = hourOfDay;
      sMinute = minute;
      if (allSame.isChecked())
        updateAllS();
      else
        updateDisplayFS();
    }
  };

  // //////////////////Friday End
  private void updateDisplayFE()
  {
    TimeEndFri.setText(new StringBuilder().append(pad(eHour)).append(":")
        .append(pad(eMinute)));
  }

  private TimePickerDialog.OnTimeSetListener FETimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      eHour = hourOfDay;
      eMinute = minute;
      if (allSame.isChecked())
        updateAllE();
      else
        updateDisplayFE();
    }
  };

  // ///////////// Saturday Start
  private void updateDisplaySaS()
  {
    TimeStartSat.setText(new StringBuilder().append(pad(sHour)).append(":")
        .append(pad(sMinute)));
  }

  private TimePickerDialog.OnTimeSetListener SaSTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      sHour = hourOfDay;
      sMinute = minute;
      if (allSame.isChecked())
        updateAllS();
      else
        updateDisplaySaS();
    }
  };

  // //////////////////Saturday End
  private void updateDisplaySaE()
  {
    TimeEndSat.setText(new StringBuilder().append(pad(eHour)).append(":")
        .append(pad(eMinute)));
  }

  private TimePickerDialog.OnTimeSetListener SaETimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      eHour = hourOfDay;
      eMinute = minute;
      if (allSame.isChecked())
        updateAllE();
      else
        updateDisplaySaE();
    }
  };

  // ///////////// Sunday Start
  private void updateDisplaySuS()
  {
    TimeStartSun.setText(new StringBuilder().append(pad(sHour)).append(":")
        .append(pad(sMinute)));
  }

  private TimePickerDialog.OnTimeSetListener SuSTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      sHour = hourOfDay;
      sMinute = minute;
      if (allSame.isChecked())
        updateAllS();
      else
        updateDisplaySuS();
    }
  };

  // //////////////////Sunday End
  private void updateDisplaySuE()
  {
    TimeEndSun.setText(new StringBuilder().append(pad(eHour)).append(":")
        .append(pad(eMinute)));
  }

  private TimePickerDialog.OnTimeSetListener SuETimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      eHour = hourOfDay;
      eMinute = minute;
      if (allSame.isChecked())
        updateAllE();
      else
        updateDisplaySuE();
    }
  };

  @Override
  protected Dialog onCreateDialog(int id)
  {
    switch (id)
    {
    case TIME_DIALOG_MONS:
      return new TimePickerDialog(this, MSTimeSetListener, sHour, sMinute,
          false);

    case TIME_DIALOG_MONE:
      return new TimePickerDialog(this, METimeSetListener, eHour, eMinute,
          false);

    case TIME_DIALOG_TUES:
      return new TimePickerDialog(this, TuSTimeSetListener, sHour, sMinute,
          false);

    case TIME_DIALOG_TUEE:
      return new TimePickerDialog(this, TuETimeSetListener, eHour, eMinute,
          false);

    case TIME_DIALOG_WEDS:
      return new TimePickerDialog(this, WSTimeSetListener, sHour, sMinute,
          false);

    case TIME_DIALOG_WEDE:
      return new TimePickerDialog(this, WETimeSetListener, eHour, eMinute,
          false);

    case TIME_DIALOG_THUS:
      return new TimePickerDialog(this, ThSTimeSetListener, sHour, sMinute,
          false);

    case TIME_DIALOG_THUE:
      return new TimePickerDialog(this, ThETimeSetListener, eHour, eMinute,
          false);

    case TIME_DIALOG_FRIS:
      return new TimePickerDialog(this, FSTimeSetListener, sHour, sMinute,
          false);

    case TIME_DIALOG_FRIE:
      return new TimePickerDialog(this, FETimeSetListener, eHour, eMinute,
          false);

    case TIME_DIALOG_SATS:
      return new TimePickerDialog(this, SaSTimeSetListener, sHour, sMinute,
          false);

    case TIME_DIALOG_SATE:
      return new TimePickerDialog(this, SaETimeSetListener, eHour, eMinute,
          false);

    case TIME_DIALOG_SUNS:
      return new TimePickerDialog(this, SuSTimeSetListener, sHour, sMinute,
          false);

    case TIME_DIALOG_SUNE:
      return new TimePickerDialog(this, SuETimeSetListener, eHour, eMinute,
          false);

    }

    return null;

  }

  public void updateAllS()
  {
    if (CBMonday.isChecked())
      updateDisplayMS();
    if (CBTuesday.isChecked())
      updateDisplayTuS();
    if (CBWednesday.isChecked())
      updateDisplayWS();
    if (CBThursday.isChecked())
      updateDisplayThS();
    if (CBFriday.isChecked())
      updateDisplayFS();
    if (CBSaturday.isChecked())
      updateDisplaySaS();
    if (CBSunday.isChecked())
      updateDisplaySuS();

  }

  public void updateAllE()
  {
    if (CBMonday.isChecked())
      updateDisplayME();
    if (CBTuesday.isChecked())
      updateDisplayTuE();
    if (CBWednesday.isChecked())
      updateDisplayWE();
    if (CBThursday.isChecked())
      updateDisplayThE();
    if (CBFriday.isChecked())
      updateDisplayFE();
    if (CBSaturday.isChecked())
      updateDisplaySaE();
    if (CBSunday.isChecked())
      updateDisplaySuE();

  }

  public void onClick(View v)
  {
    switch (v.getId())
    {

    case R.id.buttonScheduleEdit_AddClass:
      if (saveClass())
      {
        Toast.makeText(this, getString(R.string.toastclass_save_success),
            Toast.LENGTH_SHORT).show();
        ClassNameSE.setText("");
      }
      else if (isOnlineClass)
      {
        isOnlineClass = false;
        // run this if nothing is checked
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Is this an Online Class?");
        dialog
            .setMessage("If this isn't an online class, days and times must be inserted");
        dialog.setPositiveButton("Yes",
            new android.content.DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface arg0, int arg1)
              {
                String ClassNameText = ClassNameSE.getText().toString();
                ClassSpinnerData ClassSpinnerdb = new ClassSpinnerData(
                    getBaseContext());
                ClassSpinnerdb.insertSpinnerClass(ClassNameText);
                
                StudyBuddy.getOrCreate(ClassNameText, true);

                arg0.dismiss();

                Toast.makeText(getBaseContext(),
                    getString(R.string.toastclass_save_success),
                    Toast.LENGTH_SHORT).show();
                ClassNameSE.setText("");
              }
            });
        dialog.setNegativeButton("No ",
            new android.content.DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface arg0, int arg1)
              {
                arg0.dismiss();
              }
            });
        dialog.show();

      }
      else
      {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Class not saved");
        dialog
            .setMessage("Class name too short (3 characters or more) or the start time was entered as later than the end time");
        dialog.setNeutralButton("ok",
            new android.content.DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface arg0, int arg1)
              {
                arg0.dismiss();
              }
            });
        dialog.show();
      }

      break;

    case R.id.buttonScheduleEdit_Return:
      onBackPressed();
      break;

    }

  }

  // / public boolean onKeyMultiple()
  public boolean saveClass()
  {
    String ClassNameText = ClassNameSE.getText().toString();

    if (ClassNameText.length() < 2)
    {
      return false;
    }
    if ((!CBMonday.isChecked()) && (!CBTuesday.isChecked())
        && (!CBWednesday.isChecked()) && (!CBThursday.isChecked())
        && (!CBFriday.isChecked()) && (!CBSaturday.isChecked())
        && (!CBSunday.isChecked()))
    {
      isOnlineClass = true;
      return false;
    }

    else
    {
      StudyBuddyApplication sba = (StudyBuddyApplication) this.getApplication();
      boolean paOn = true;
      if (!StudyBuddy.notificationsOn() || !StudyBuddy.planAssistCheck())
        paOn = false;
      
      ClassObject parent = StudyBuddy.getOrCreate(ClassNameText, false);
      
      if (CBMonday.isChecked())
      {

        String ClassDayText = "Monday".toString();
        String ClassStartText = TimeStartMon.getText().toString();
        String ClassEndText = TimeEndMon.getText().toString();

        if (ClassStartText.contentEquals("--:--")
            || ClassEndText.contentEquals("--:--"))
          return false;

        String IDHR = ClassEndText.substring(0, 2);
        String IDMN = ClassEndText.substring(3, 5);
        String ALRMID = Calendar.MONDAY + IDHR + IDMN;
        int AlarmId = Integer.parseInt(ALRMID);

        String STHR = ClassStartText.substring(0, 2);
        String STMN = ClassStartText.substring(3, 5);
        String STRTID = "" + STHR + STMN;
        int StartId = Integer.parseInt(STRTID);
        int EndId = Integer.parseInt(ALRMID);

        if (invalidTimes(StartId, EndId)) return false;


        if (paOn)
          sba.setAPlannerAssistant(true, AlarmId, ClassNameText);

        StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
            ClassEndText, AlarmId, StartId);


      }

      if (CBTuesday.isChecked())
      {
        String ClassDayText = "Tuesday".toString();
        String ClassStartText = TimeStartTue.getText().toString();
        String ClassEndText = TimeEndTue.getText().toString();
        if (ClassStartText.contentEquals("--:--")
            || ClassEndText.contentEquals("--:--"))
          return false;

        String IDHR = ClassEndText.substring(0, 2);
        String IDMN = ClassEndText.substring(3, 5);
        String ALRMID = Calendar.TUESDAY + IDHR + IDMN;
        int AlarmId = Integer.parseInt(ALRMID);

        String STHR = ClassStartText.substring(0, 2);
        String STMN = ClassStartText.substring(3, 5);
        String STRTID = "" + STHR + STMN;
        int StartId = Integer.parseInt(STRTID);
        int EndId = Integer.parseInt(ALRMID);

        if (invalidTimes(StartId, EndId)) return false;

        if (paOn)
          sba.setAPlannerAssistant(true, AlarmId, ClassNameText);

        StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
            ClassEndText, AlarmId, StartId);

      }

      if (CBWednesday.isChecked())
      {
        String ClassDayText = "Wednesday".toString();
        String ClassStartText = TimeStartWed.getText().toString();
        String ClassEndText = TimeEndWed.getText().toString();
        if (ClassStartText.contentEquals("--:--")
            || ClassEndText.contentEquals("--:--"))
          return false;

        String IDHR = ClassEndText.substring(0, 2);
        String IDMN = ClassEndText.substring(3, 5);
        String ALRMID = Calendar.WEDNESDAY + IDHR + IDMN;
        int AlarmId = Integer.parseInt(ALRMID);

        String STHR = ClassStartText.substring(0, 2);
        String STMN = ClassStartText.substring(3, 5);
        String STRTID = "" + STHR + STMN;
        int StartId = Integer.parseInt(STRTID);
        int EndId = Integer.parseInt(ALRMID);

        if (invalidTimes(StartId, EndId)) return false;

        if (paOn)
          sba.setAPlannerAssistant(true, AlarmId, ClassNameText);

        StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
            ClassEndText, AlarmId, StartId);

      }

      if (CBThursday.isChecked() && ClassNameText.length() >= 2)
      {
        String ClassDayText = "Thursday".toString();
        String ClassStartText = TimeStartThu.getText().toString();
        String ClassEndText = TimeEndThu.getText().toString();
        if (ClassStartText.contentEquals("--:--")
            || ClassEndText.contentEquals("--:--"))
          return false;

        String IDHR = ClassEndText.substring(0, 2);
        String IDMN = ClassEndText.substring(3, 5);
        String ALRMID = Calendar.THURSDAY + IDHR + IDMN;
        int AlarmId = Integer.parseInt(ALRMID);

        String STHR = ClassStartText.substring(0, 2);
        String STMN = ClassStartText.substring(3, 5);
        String STRTID = "" + STHR + STMN;
        int StartId = Integer.parseInt(STRTID);
        int EndId = Integer.parseInt(ALRMID);

        if (invalidTimes(StartId, EndId)) return false;

        if (paOn)
          sba.setAPlannerAssistant(true, AlarmId, ClassNameText);

        StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
            ClassEndText, AlarmId, StartId);

      }

      if (CBFriday.isChecked())
      {

        String ClassDayText = "Friday".toString();
        String ClassStartText = TimeStartFri.getText().toString();
        String ClassEndText = TimeEndFri.getText().toString();
        if (ClassStartText.contentEquals("--:--")
            || ClassEndText.contentEquals("--:--"))
          return false;

        String IDHR = ClassEndText.substring(0, 2);
        String IDMN = ClassEndText.substring(3, 5);
        String ALRMID = Calendar.FRIDAY + IDHR + IDMN;
        int AlarmId = Integer.parseInt(ALRMID);

        String STHR = ClassStartText.substring(0, 2);
        String STMN = ClassStartText.substring(3, 5);
        String STRTID = "" + STHR + STMN;
        int StartId = Integer.parseInt(STRTID);
        int EndId = Integer.parseInt(ALRMID);

        if (invalidTimes(StartId, EndId)) return false;

        if (paOn)
          sba.setAPlannerAssistant(true, AlarmId, ClassNameText);

        StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
            ClassEndText, AlarmId, StartId);

      }

      if (CBSaturday.isChecked())
      {

        String ClassDayText = "Saturday".toString();
        String ClassStartText = TimeStartSat.getText().toString();
        String ClassEndText = TimeEndSat.getText().toString();
        if (ClassStartText.contentEquals("--:--")
            || ClassEndText.contentEquals("--:--"))
          return false;

        String IDHR = ClassEndText.substring(0, 2);
        String IDMN = ClassEndText.substring(3, 5);
        String ALRMID = Calendar.SATURDAY + IDHR + IDMN;
        int AlarmId = Integer.parseInt(ALRMID);

        String STHR = ClassStartText.substring(0, 2);
        String STMN = ClassStartText.substring(3, 5);
        String STRTID = "" + STHR + STMN;
        int StartId = Integer.parseInt(STRTID);
        int EndId = Integer.parseInt(ALRMID);

        if (invalidTimes(StartId, EndId)) return false;

        if (paOn)
          sba.setAPlannerAssistant(true, AlarmId, ClassNameText);

        StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
            ClassEndText, AlarmId, StartId);

      }

      if (CBSunday.isChecked())
      {

        String ClassDayText = "Sunday".toString();
        String ClassStartText = TimeStartSun.getText().toString();
        String ClassEndText = TimeEndSun.getText().toString();
        if (ClassStartText.contentEquals("--:--")
            || ClassEndText.contentEquals("--:--"))
          return false;

        String IDHR = ClassEndText.substring(0, 2);
        String IDMN = ClassEndText.substring(3, 5);
        String ALRMID = Calendar.SUNDAY + IDHR + IDMN;
        int AlarmId = Integer.parseInt(ALRMID);

        String STHR = ClassStartText.substring(0, 2);
        String STMN = ClassStartText.substring(3, 5);
        String STRTID = "" + STHR + STMN;
        int StartId = Integer.parseInt(STRTID);
        int EndId = Integer.parseInt(ALRMID);

        if (invalidTimes(StartId, EndId)) return false;

        if (paOn)
          sba.setAPlannerAssistant(true, AlarmId, ClassNameText);

        StudyBuddy.addClass(parent, ClassDayText, ClassStartText,
            ClassEndText, AlarmId, StartId);

      }


      TimeStartMon.setText("--:--");
      TimeEndMon.setText("--:--");
      CBMonday.setChecked(false);

      TimeStartTue.setText("--:--");
      TimeEndTue.setText("--:--");
      CBTuesday.setChecked(false);

      TimeStartWed.setText("--:--");
      TimeEndWed.setText("--:--");
      CBWednesday.setChecked(false);

      TimeStartThu.setText("--:--");
      TimeEndThu.setText("--:--");
      CBThursday.setChecked(false);

      TimeStartFri.setText("--:--");
      TimeEndFri.setText("--:--");
      CBFriday.setChecked(false);

      TimeStartSat.setText("--:--");
      TimeEndSat.setText("--:--");
      CBSaturday.setChecked(false);

      TimeStartSun.setText("--:--");
      TimeEndSun.setText("--:--");
      CBSunday.setChecked(false);

      sHour = 12;
      sMinute = 00;
      eHour = 12;
      eMinute = 00;

      return true;
    }

  };

  public boolean invalidTimes(int start, int end)
  {
    if (start >= end)
      return true;
    else
      return false;
  }

}
