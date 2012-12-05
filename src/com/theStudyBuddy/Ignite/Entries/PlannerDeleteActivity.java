package com.theStudyBuddy.Ignite.Entries;

import java.util.ArrayList;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;
import com.theStudyBuddy.Ignite.StudyBuddyApplication;
import com.theStudyBuddy.Ignite.R.anim;
import com.theStudyBuddy.Ignite.R.id;
import com.theStudyBuddy.Ignite.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class PlannerDeleteActivity extends Activity implements OnClickListener
{

  LinearLayout DeleteList;
  StudyBuddyApplication StudyBuddy;
  ArrayList<EntryObject> allEntries;
//
  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    Intent intent = new Intent(getBaseContext(), StudyBuddyActivity.class);
    intent.putExtra(android.content.Intent.EXTRA_TEXT, "ButtPirate");
    startActivityForResult(intent, 500);
    overridePendingTransition(0, R.anim.out_to_bottom);
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    Log.d("TAG", "Created this shiiiit");
    setContentView(R.layout.deleteentrylist);
    StudyBuddy = (StudyBuddyApplication) getApplication();

    DeleteList = (LinearLayout) findViewById(R.id.entryDeleteList);

    Button Delete = (Button) findViewById(R.id.buttonEntryDelete_Delete);
    Delete.setOnClickListener(this);

    Button Cancel = (Button) findViewById(R.id.buttonEntryDelete_Cancel);
    Cancel.setOnClickListener(this);

    allEntries = StudyBuddy.getAllEntries();

    if (allEntries.size() > 0)
    {
      PlannerDeleteAdapter deleteAdapter = new PlannerDeleteAdapter(this,
          R.layout.deleteclassadapterblk, StudyBuddy.getAllEntries());

      for (int i = 0; i < allEntries.size(); i++)
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

    case R.id.buttonEntryDelete_Delete:
      deleteChecked();
      onBackPressed();
      break;

    case R.id.buttonEntryDelete_Cancel:
      onBackPressed();
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
      /// changed
      StudyBuddy.deleteItems(deleteThese);
    }
    
    return;
  }
  
}
