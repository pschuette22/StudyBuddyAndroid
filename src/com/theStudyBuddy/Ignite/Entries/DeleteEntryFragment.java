package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;

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

public class DeleteEntryFragment extends Fragment implements OnClickListener
{

  LinearLayout DeleteList;
  StudyBuddyApplication StudyBuddy;
  ArrayList<EntryObject> allEntries;
  View mView;  
  FragmentActivity activity;
  
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    super.onCreateView(inflater, container, savedInstanceState);
    mView = inflater.inflate(R.layout.deleteentrylist, container, false);
    activity = getActivity();
    StudyBuddy = (StudyBuddyApplication) activity.getApplication();
    
    DeleteList = (LinearLayout) mView.findViewById(R.id.entryDeleteList);

    Button Delete = (Button) mView.findViewById(R.id.buttonEntryDelete_Delete);
    Delete.setOnClickListener(this);

    Button Cancel = (Button) mView.findViewById(R.id.buttonEntryDelete_Cancel);
    Cancel.setOnClickListener(this);

    allEntries = StudyBuddy.getAllEntries();

    if (allEntries.size() > 0)
    {
      PlannerDeleteAdapter deleteAdapter = new PlannerDeleteAdapter(activity,
          R.layout.deleteclassadapterblk, StudyBuddy.getAllEntries());

      for (int i = 0; i < allEntries.size(); i++)
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

    case R.id.buttonEntryDelete_Delete:
      deleteChecked();
      activity.onBackPressed();
      break;

    case R.id.buttonEntryDelete_Cancel:
      activity.onBackPressed();
      break;

    }
  }

  public void deleteChecked()
  {
    ArrayList<EntryObject> deleteThese = new ArrayList<EntryObject>();

    for (int i = 0; i < allEntries.size(); i++)
    {
      View listItem = DeleteList.getChildAt(i);
      CheckBox cb = (CheckBox) listItem.findViewById(R.id.checkBoxDelete);
      if (cb.isChecked())
      {
        deleteThese.add( (EntryObject) listItem.getTag() );
      }
    }
    
    if (deleteThese.size() > 0)
    {
      StudyBuddy.deleteItems(deleteThese);
    }
    
    return;
  }
  
}
