package com.theStudyBuddy.Ignite.Classes;

import java.util.ArrayList;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.R.anim;
import com.theStudyBuddy.Ignite.R.id;
import com.theStudyBuddy.Ignite.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassDeleteActivity extends Activity implements OnClickListener
{
  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    Intent intent = new Intent(getBaseContext(), StudyBuddyActivity.class);
    intent.putExtra(android.content.Intent.EXTRA_TEXT, "Schedule");
    startActivityForResult(intent, 500);
    overridePendingTransition(0, R.anim.out_to_bottom);
  }

  ArrayList<String> deleteItems = new ArrayList<String>();
  StudyBuddyApplication StudyBuddy;

  LinearLayout DeleteList;
  ArrayList<ClassObject> deleteList;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.deleteclasslist);
    StudyBuddy = (StudyBuddyApplication) getApplication();

    DeleteList = (LinearLayout) findViewById(R.id.classDeleteList);
    Button Delete = (Button) findViewById(R.id.buttonClassDelete_Delete);
    Delete.setOnClickListener(this);

    Button Cancel = (Button) findViewById(R.id.buttonClassDelete_Cancel);
    Cancel.setOnClickListener(this);

    deleteList = StudyBuddy.getAllClasses();
    if (deleteList.size() > 0)
    {
      ClassDeleteAdapter deleteAdapter = new ClassDeleteAdapter(this,
          R.layout.deleteclassadapterblk, deleteList);
      for (int i = 0; i < deleteList.size(); i++)
      {
        View item = deleteAdapter.getView(i, null, DeleteList);
        DeleteList.addView(item);
      }
    }

  }

  public void onClick(View v)
  {

    switch (v.getId())
    {

    case R.id.buttonClassDelete_Delete:
      deleteChecked();
      break;

    case R.id.buttonClassDelete_Cancel:
      onBackPressed();
      break;

    }
  }

  public void deleteChecked()
  {
    
    if (deleteList.size() == 0)
    {
      onBackPressed();
    }
    
    for(int i = 0; i < DeleteList.getChildCount(); i++){
      View mView = DeleteList.getChildAt(i);
      CheckBox cb = (CheckBox) mView.findViewById(R.id.checkBoxDelete);
      ClassObject parent = (ClassObject) mView.getTag();
      if(cb.isChecked()){
        // delete everything about the class
        StudyBuddy.deleteClass(parent);
      }
      
      // db check
      else if(!parent.isOnline()){
        LinearLayout meetingList = (LinearLayout) mView.findViewById(R.id.deleteMeetingsList);
        ArrayList<MeetingObject> meetings = parent.getMeetings();
        if( meetings.size() > 0){
          for(int j = 0; j < meetingList.getChildCount() ; j++){
            View lView = meetingList.getChildAt(j);
            CheckBox cb2 = (CheckBox) lView.findViewById(R.id.checkBoxDelete);
            if(cb2.isChecked()){
              MeetingObject meeting = (MeetingObject) lView.getTag();
              StudyBuddy.deleteMeeting(meeting);
            }
          }
        }
      }
      
    }
    onBackPressed();
  }

}
