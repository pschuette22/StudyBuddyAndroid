package com.theStudyBuddy.Ignite.Classes;

import java.util.ArrayList;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.R.id;
import com.theStudyBuddy.Ignite.R.layout;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassDeleteAdapter extends ArrayAdapter<MeetingObject>
{

  Activity activity;
  StudyBuddyApplication studyBuddy;
  ArrayList<ClassObject> mClasses;
    
  public ClassDeleteAdapter(Activity activity_in, int layout, ArrayList<ClassObject> mClasses_)
  {
    super(activity_in, layout);
    activity = activity_in;
    mClasses = mClasses_;

  }
  
  public View getView(int position, View convertView, ViewGroup parent)
  {
    
    View mView = activity.getLayoutInflater().inflate(R.layout.deleteclassadapterblk, parent, false);
    ClassObject mClass = mClasses.get(position);
    mView.setTag(mClass);
    
    // I think I need to do it this way/ this is easiest.. hacky though I will change in near future
    if(mClass.getTitle().contentEquals("General")){
      mView.setVisibility(View.GONE);
      return mView;
    }
    
    final ArrayList<MeetingObject> meetings = mClass.getMeetings();
    TextView title = (TextView) mView.findViewById(R.id.textViewDeleteClass);
    if(mClass.isOnline()){
      title.setText(mClass.getTitle() + " - Online");
    }
    else{
      title.setText(mClass.getTitle());
      final LinearLayout deleteList = (LinearLayout) mView.findViewById(R.id.deleteMeetingsList);
      for(int i = 0; i < meetings.size(); i++){
        View listItem = activity.getLayoutInflater().inflate(R.layout.deleteclassadapterblk , parent, false);
        MeetingObject meeting = meetings.get(i);
        listItem.setTag(meeting);
        TextView itemText = (TextView) listItem.findViewById(R.id.textViewDeleteClass);
        itemText.setText(meeting.getDayOfWeek() + ", " + meeting.getStart());

        LinearLayout divider = (LinearLayout) listItem.findViewById(R.id.mDivider);
        divider.setVisibility(View.GONE);
        
        deleteList.addView(listItem);
        }
      
      final CheckBox cb = (CheckBox) mView.findViewById(R.id.checkBoxDelete);
      cb.setOnClickListener(new OnClickListener(){

        public void onClick(View arg0)
        {
          for(int i = 0; i < meetings.size(); i++){
            View lowerView = deleteList.getChildAt(i);
            CheckBox cb2 = (CheckBox) lowerView.findViewById(R.id.checkBoxDelete);
            cb2.setChecked(cb.isChecked());
            cb2.setClickable(!cb.isChecked());
          }
        }
      });
      
    }
    
    return mView; 
  }
  

//  public void bindView(View ListItem, final Context context, Cursor ScheduleCursor) {
//  
//    final CheckBox mCB = (CheckBox) ListItem.findViewById(R.id.checkBoxDelete);
//    mCB.setChecked(Selected == 0? false:true);
//    mCB.setOnClickListener( new OnClickListener(){
//      public void onClick(View v)
//      {       
//       ScheduleData scheduledb = new ScheduleData(context);
//       
//       if (mCB.isChecked() == true)
//       {
//         scheduledb.Selected(Id, 1);
//         };
// 
//         
//         if (mCB.isChecked() == false)
//       { 
//           scheduledb.Selected(Id, 0);
//         };      
//         }});
//    
//    ItemName.setOnClickListener( new OnClickListener(){
//      public void onClick(View v)
//      {       
//       ScheduleData scheduledb = new ScheduleData(context);
//       
//       if (mCB.isChecked() == false)
//       {
//         scheduledb.Selected(Id, 1);
//         mCB.setChecked(true);
//         };
// 
//         
//         if (mCB.isChecked() == true)
//       { 
//           scheduledb.Selected(Id, 0);
//           mCB.setChecked(false);
//         };      
//         }});
//
//  }  
}
