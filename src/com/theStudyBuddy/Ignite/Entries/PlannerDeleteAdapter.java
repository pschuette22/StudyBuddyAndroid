package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.R.id;
import com.theStudyBuddy.Ignite.R.layout;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class PlannerDeleteAdapter extends ArrayAdapter<EntryObject>
{
  
  PlannerData plannerData;
  Context context;
  StudyBuddyApplication studyBuddy;
  
  StudyBuddyApplication sba;
  Activity activity;
  
  ArrayList<EntryObject> allEntries;
  
  public PlannerDeleteAdapter(Activity activity_, int layout, ArrayList<EntryObject> allEntries_)
  {
    super(activity_, layout);
    activity = activity_;
    allEntries = allEntries_;
    
  }
  
  public View getView(int position, View convertView, ViewGroup parent){
    View listItem = activity.getLayoutInflater().inflate(R.layout.deleteclassadapterblk , parent, false);
    
    EntryObject entry = allEntries.get(position);
    listItem.setTag(entry);
    
    final String ClassName = entry.getParent().getTitle();
    final String item = entry.getText();
    
    TextView ItemName = (TextView) listItem.findViewById(R.id.textViewDeleteClass);
    ItemName.setText(ClassName + " - " + item);
    
    return listItem;
  }
//final CheckBox mCB = (CheckBox) ListItem.findViewById(R.id.checkBoxDelete);
}
