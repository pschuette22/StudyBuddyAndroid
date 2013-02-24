package com.theStudyBuddy.Ignite.Classes;

import java.util.ArrayList;

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
import android.widget.LinearLayout;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;

public class ClassDeleteFragment extends Fragment implements OnClickListener
{

  ArrayList<String> deleteItems = new ArrayList<String>();
  StudyBuddyApplication StudyBuddy;
  FragmentActivity activity;
  Context context;
  View mView;

  LinearLayout DeleteList;
  ArrayList<ClassObject> deleteList;
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    super.onCreateView(inflater, container, savedInstanceState);
    activity = getActivity();
    context = activity.getBaseContext();
    mView = inflater.inflate(R.layout.deleteclasslist, container, false);

    StudyBuddy = (StudyBuddyApplication) activity.getApplication();

    DeleteList = (LinearLayout) mView.findViewById(R.id.classDeleteList);
    Button Delete = (Button) mView.findViewById(R.id.buttonClassDelete_Delete);
    Delete.setOnClickListener(this);

    Button Cancel = (Button) mView.findViewById(R.id.buttonClassDelete_Cancel);
    Cancel.setOnClickListener(this);

    deleteList = StudyBuddy.getAllClasses();
    if (deleteList.size() > 0)
    {
      ClassDeleteAdapter deleteAdapter = new ClassDeleteAdapter(activity,
          R.layout.deleteclassadapterblk, deleteList);
      for (int i = 0; i < deleteList.size(); i++)
      {
        View item = deleteAdapter.getView(i, null, DeleteList);
        DeleteList.addView(item);
      }
    }
    
    return mView;
  } 
  
  public void onClick(View v)
  {

    switch (v.getId())
    {

    case R.id.buttonClassDelete_Delete:
      deleteChecked();
      break;

    case R.id.buttonClassDelete_Cancel:
      activity.onBackPressed();
      break;

    }
  }

  public void deleteChecked()
  {
    
    if (deleteList.size() == 0)
    {
      activity.onBackPressed();
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
    activity.onBackPressed();
  }

}
