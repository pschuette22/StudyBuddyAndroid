package com.theStudyBuddy.Ignite.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.theStudyBuddy.Ignite.R;
import com.theStudyBuddy.Ignite.StudyBuddyActivity;

public class EditScheduleActivity extends FragmentActivity 
{
  
  ViewPager mViewPager;
  EditPagerAdapter pagerAdapter;
  FragmentActivity activity;
  
  RelativeLayout tab1;
  LinearLayout tab1Bar;
  
  RelativeLayout tab2;
  LinearLayout tab2Bar;
  Button backButton;

  AddClassFragment addClassFragment;
  ClassDeleteFragment deleteClassFragment;
  
  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    Intent intent = new Intent(getBaseContext(), StudyBuddyActivity.class);
    intent.putExtra("FragNumber", 1);
    startActivityForResult(intent, 500);
    overridePendingTransition(0, R.anim.out_to_bottom);
  }

  @Override
  public void onCreate(Bundle bundle)
  {

    super.onCreate(bundle);

    setContentView(R.layout.tabholder);
    
    addClassFragment = new AddClassFragment();
    deleteClassFragment = new ClassDeleteFragment();
    
    mViewPager = (ViewPager) findViewById(R.id.tabViewPager);
    pagerAdapter = new EditPagerAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(pagerAdapter);
    
    backButton = (Button) findViewById(R.id.backButton);
    backButton.setOnClickListener(new OnClickListener(){

      public void onClick(View arg0)
      {
        onBackPressed();
      }
      
    });
    
    tab1 = (RelativeLayout) findViewById(R.id.tabholderTab1);
    tab1Bar = (LinearLayout) findViewById(R.id.tab1bar);
    tab1.setOnClickListener(new OnClickListener(){

      public void onClick(View arg0)
      {
        pageChangedTabAction(0);
        mViewPager.setCurrentItem(0, true);
      }
      
    });
    
    tab2 = (RelativeLayout) findViewById(R.id.tabholderTab2);
    tab2Bar = (LinearLayout) findViewById(R.id.tab2bar);
    tab2.setOnClickListener(new OnClickListener(){

      public void onClick(View v)
      {
        pageChangedTabAction(1);
        mViewPager.setCurrentItem(1, true);
      }
      
    });
    
    
    mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

      public void onPageScrollStateChanged(int arg0)
      {
        
      }

      public void onPageScrolled(int arg0, float arg1, int arg2)
      {
        
      }

      public void onPageSelected(int arg0)
      {
        pageChangedTabAction(arg0);
      }
      
    });
    

  }
  
  
  public void pageChangedTabAction(int toPage){
    if(toPage == 0){
      tab1Bar.setVisibility(View.VISIBLE);
      tab2Bar.setVisibility(View.INVISIBLE);
    }
    
    else {
      tab1Bar.setVisibility(View.INVISIBLE);
      tab2Bar.setVisibility(View.VISIBLE);
    }
  }

  
  private class EditPagerAdapter extends FragmentPagerAdapter {

    public EditPagerAdapter(FragmentManager fm)
    {
      super(fm);

    }


    public Fragment getItem(int arg0)
    {
      if(arg0 == 0){
        return addClassFragment;
      }
      else {
        return deleteClassFragment;
      }

    }

    public int getCount()
    {
      return 2;
    }

  }

}
