package com.theStudyBuddy.Ignite.Notifications;

import java.util.ArrayList;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.Entries.AssignmentViewActivity;
import com.theStudyBuddy.Ignite.Entries.EntryObject;
import com.theStudyBuddy.Ignite.Entries.PlannerData;
import com.theStudyBuddy.Ignite.R.id;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReminderAdapter extends ArrayAdapter<EntryObject>
{
  AssignmentViewActivity AssignmentView;
  MorningReminderActivity MorningReminder;
  TextView Class;
  TextView Body;
  int Layout;
  Activity mActivity;
  ArrayList<EntryObject> mPlannerList;

  PlannerData plannerData;
  StudyBuddyApplication StudyBuddy;

  public ReminderAdapter(Activity activity, int layout,
      ArrayList<EntryObject> PlannerList)
  {
    super( activity, layout);
    mActivity = activity;
    Layout = layout;
    mPlannerList = PlannerList;
  }

  public View getView(int position, View convertView, ViewGroup parent)
  {
    // TODO Auto-generated method stub
    EntryObject currentObject = mPlannerList.get(position);
    View mView = mActivity.getLayoutInflater().inflate(Layout, parent, false);
    
    TextView ClassName = (TextView) mView
        .findViewById(R.id.textPlannerView_Class);
    ClassName.setText(currentObject.getParent().getTitle() + ": ");
    
    TextView EntryBody = (TextView) mView
        .findViewById(R.id.textPlannerItem_Item);
    EntryBody.setText(currentObject.getText());
    
    TextView ClassDate = (TextView) mView
        .findViewById(R.id.textPlannerItem_Date);
    ClassDate.setText(currentObject.getDate());
    
    TextView ClassType = (TextView) mView
        .findViewById(R.id.textPlannerView_Type);
    ClassType.setText(currentObject.getType());
    
    LinearLayout divider = (LinearLayout) mView.findViewById(R.id.mDivider);
    if(position == mPlannerList.size() - 1){
      divider.setVisibility(View.GONE);
    }
    
    return mView;
  }

}
