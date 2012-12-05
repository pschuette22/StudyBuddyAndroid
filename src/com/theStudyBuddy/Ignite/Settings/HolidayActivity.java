package com.theStudyBuddy.Ignite.Settings;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;

public class HolidayActivity extends Activity implements OnClickListener
{

  public void onBackPressed()
  {
    super.onBackPressed();

    Intent intent = new Intent(getBaseContext(), StudyBuddyActivity.class);
    intent.putExtra(android.content.Intent.EXTRA_TEXT, "Settings");
    startActivityForResult(intent, 500);
    overridePendingTransition(0, R.anim.out_to_bottom);

  }

  int year, month;
  StudyBuddyApplication studyBuddy;
  
  CheckBox sendNightlyOnHoliday, sendMorningOnHoliday;
  ImageView lastMonth, nextMonth;
  
  // all hard coded, this is the fastest way to do it
  int[] resources = {
      R.id.calItem0,
      R.id.calItem1,
      R.id.calItem2,
      R.id.calItem3,
      R.id.calItem4,
      R.id.calItem5,
      R.id.calItem6,
      R.id.calItem7,
      R.id.calItem8,
      R.id.calItem9,
      R.id.calItem10,
      R.id.calItem11,
      R.id.calItem12,
      R.id.calItem13,
      R.id.calItem14,
      R.id.calItem15,
      R.id.calItem16,
      R.id.calItem17,
      R.id.calItem18,
      R.id.calItem19,
      R.id.calItem20,
      R.id.calItem21,
      R.id.calItem22,
      R.id.calItem23,
      R.id.calItem24,
      R.id.calItem25,
      R.id.calItem26,
      R.id.calItem27,
      R.id.calItem28,
      R.id.calItem29,
      R.id.calItem30,
      R.id.calItem31,
      R.id.calItem32,
      R.id.calItem33,
      R.id.calItem34,
      R.id.calItem35,
      R.id.calItem36,
      R.id.calItem37,
      R.id.calItem38,
      R.id.calItem39,
      R.id.calItem40,
      R.id.calItem41
  };

  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.holiday_picker);
    studyBuddy = (StudyBuddyApplication) getApplication();

    Calendar today = Calendar.getInstance();
    year = today.get(Calendar.YEAR);
    month = today.get(Calendar.MONTH);

    Button getOut = (Button) findViewById(R.id.buttonHolidayPickerOut);
    getOut.setOnClickListener(this);

    sendNightlyOnHoliday = (CheckBox) findViewById(R.id.nightlyOnHolidays);
    sendNightlyOnHoliday.setChecked(studyBuddy.checkHolidayNightly());
    sendNightlyOnHoliday.setOnClickListener(new OnClickListener(){

      public void onClick(View arg0)
      {
        studyBuddy.setHolidayNotifications(true, sendNightlyOnHoliday.isChecked());
      }
      
    });
    sendMorningOnHoliday = (CheckBox) findViewById(R.id.morningOnHolidays);
    sendMorningOnHoliday.setChecked(studyBuddy.checkHolidayMorning());
    sendMorningOnHoliday.setOnClickListener(new OnClickListener(){

      public void onClick(View v)
      {
        studyBuddy.setHolidayNotifications(false, sendMorningOnHoliday.isChecked());
      }
      
    });
    
    lastMonth = (ImageView) findViewById(R.id.lastMonth);
    lastMonth.setOnClickListener(new OnClickListener(){

      public void onClick(View v)
      {
        if(month == 0){
          
          month = 11;
          year -= 1;
          setCalendar(year, month);
          
        }
        else{
          month -= 1;
          setCalendar(year, month);

        }
        
      }
      
    });
    
    nextMonth = (ImageView) findViewById(R.id.nextMonth);
    nextMonth.setOnClickListener(new OnClickListener(){

      public void onClick(View v)
      {
        if(month == 11){
          month = 0;
          year += 1;
          setCalendar(year, month);
          
        }
        else{
          month += 1;
          setCalendar(year, month);
        }
        
      }
      
    });
    
    setCalendar(year, month);

  }

  public void setCalendar(int year, int month)
  {
    Calendar today = Calendar.getInstance();
    lastMonth.setVisibility(View.VISIBLE);
    if(today.get(Calendar.YEAR) == year && today.get(Calendar.MONTH) == month){
      lastMonth.setVisibility(View.GONE);
    }
    
    TextView monthAndYear = (TextView) findViewById(R.id.textMonthName);

    String monthAndYearString = "Error Occured";

    switch (month)
    {
    case 0:
      monthAndYearString = "January, " + year;
      break;
    case 1:
      monthAndYearString = "Febuary, " + year;
      break;
    case 2:
      monthAndYearString = "March, " + year;
      break;
    case 3:
      monthAndYearString = "April, " + year;
      break;
    case 4:
      monthAndYearString = "May, " + year;
      break;
    case 5:
      monthAndYearString = "June, " + year;
      break;
    case 6:
      monthAndYearString = "July, " + year;
      break;
    case 7:
      monthAndYearString = "August, " + year;
      break;
    case 8:
      monthAndYearString = "September, " + year;
      break;
    case 9:
      monthAndYearString = "October, " + year;
      break;
    case 10:
      monthAndYearString = "November, " + year;
      break;
    case 11:
      monthAndYearString = "December, " + year;
      break;
    }

    monthAndYear.setText(monthAndYearString);
    MonthDisplayHelper mdh = new MonthDisplayHelper(year, month);
    int offset = mdh.getOffset();
    int numDays = mdh.getNumberOfDaysInMonth();

    ArrayList<HolidayObject> holidays = studyBuddy.getHolidaysFor(year, month);

    for (int i = 0; i < 42; i++)
    {      
      final View mView = findViewById(resources[i]);

      final TextView calNum = (TextView) mView.findViewById(R.id.calendarDate);
      calNum.setBackgroundColor(Color.WHITE);
      calNum.setTypeface(Typeface.DEFAULT);

      int calendarNum = i - offset + 2;
      if (calendarNum <= 0 || calendarNum > numDays)
      {
        calNum.setText("");
        mView.setClickable(false);
      }

      else
      {
        final int calDayId = (year * 10000) + (month * 100) + calendarNum;
        
        calNum.setText(calendarNum + "");
        
        if(calendarNum == today.get(Calendar.DAY_OF_MONTH) && 
            month == today.get(Calendar.MONTH) && 
            year == today.get(Calendar.YEAR)){
          
          calNum.setTypeface(null, Typeface.BOLD);
        }
        
        if (holidays.size() > 0)
        {
          if (holidays.get(0).getDayId() == calDayId)
          {
            calNum.setBackgroundColor(Color.RED);
            mView.setTag(holidays.get(0));
            holidays.remove(0);

          }
          else
            mView.setTag(null);
        }

        else
          mView.setTag(null);

        mView.setOnClickListener(new OnClickListener()
        {
          public void onClick(View arg0)
          {
            if (mView.getTag() != null)
            {
              studyBuddy.deleteHoliday((HolidayObject) mView.getTag());
              calNum.setBackgroundColor(Color.WHITE);
              mView.setTag(null);
            }
            else
            {
              calNum.setBackgroundColor(Color.RED);
              mView.setTag(studyBuddy.addHoliday(calDayId));
            }

          }
        });
      }
    }

  }

  public void onClick(View v)
  {
    switch (v.getId())
    {

    case R.id.buttonHolidayPickerOut:
      onBackPressed();
      break;

    }
  }

}
