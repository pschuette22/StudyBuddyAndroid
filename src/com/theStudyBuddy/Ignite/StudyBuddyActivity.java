package com.theStudyBuddy.Ignite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.theStudyBuddy.Ignite.Classes.EditScheduleActivity;
import com.theStudyBuddy.Ignite.Classes.ScheduleViewFragment;
import com.theStudyBuddy.Ignite.Entries.AssignmentViewFragment;

public class StudyBuddyActivity extends FragmentActivity
{

  final String adUnitId = "a14f89f84e10faa";

  StudyBuddyApplication StudyBuddy;
  ScheduleViewFragment ScheduleView;
  ViewPager mViewPager;
  MyFragmentPagerAdapter pagerAdapter;
  int setPage = 1;
  

  public static final String TAG = "TAG";

  
  
  @Override
  public void onBackPressed()
  {
    
      super.onBackPressed();

  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    StudyBuddy = (StudyBuddyApplication) getApplication();

    mViewPager = (ViewPager) findViewById(R.id.mainActivityViewPager);
    
    pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(pagerAdapter);
    
    Bundle extras = getIntent().getExtras();
    if(extras != null){
     setPage = extras.getInt("FragNumber");
     mViewPager.setCurrentItem(setPage); 
    }
    

    if(StudyBuddy.launchTutorial){
      tutorial();
    }

    //    LaunchAds(this);

  }

  public void tutorial()
  {
    StudyBuddyApplication StudyBuddy = (StudyBuddyApplication) getApplication();
    if (StudyBuddy.getAllClasses().size() <= 1)
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
            .setPositiveButton("Launch Tutorial",
                new android.content.DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface dialog, int which)
                  {
                    String video_path = "http://www.youtube.com/watch?v=s3go02QC0w8&feature=youtu.be";
                    Uri uri = Uri.parse(video_path);

                    uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                  }
                }).setNegativeButton("Build Schedule",
                new android.content.DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface dialog, int which)
                  {
                    startActivity(new Intent(getBaseContext(),
                        EditScheduleActivity.class));
                  }
                });
        dialog.setTitle("Getting started");
        dialog
            .setMessage("Thanks for checking out the College Study Buddy. It's pretty sweet, I hope you find it useful. Would you like to watch the tutorial or get started?");
        dialog.show();

    }
  }

    
   
  private void LaunchAds(FragmentActivity mActivity)
  {
      final AdView adView = new AdView(mActivity, AdSize.BANNER, adUnitId);
      final LinearLayout layout = (LinearLayout) findViewById(R.id.ad_Layout);
      layout.addView(adView);

      AdRequest request = new AdRequest();
      request.addKeyword("Education");
      request.addKeyword("College");
      request.addTestDevice("015DB8CE0902B02C");

      adView.loadAd(request);
      final RelativeLayout parent = (RelativeLayout) findViewById(R.id.adView);

      adView.setAdListener(new AdListener()
      {

        public void onDismissScreen(Ad arg0)
        {
          parent.setVisibility(View.GONE);

          if (adView != null)
          {
            adView.destroy();
          }

        }

        public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1)
        {

          Log.d("TAG", "Failed to receive");
        }

        public void onLeaveApplication(Ad arg0)
        {
          parent.setVisibility(View.GONE);
          if (adView != null)
          {
            adView.destroy();
          }
        }

        public void onPresentScreen(Ad arg0)
        {

        }

        public void onReceiveAd(Ad arg0)
        {


          final float height = AdSize.BANNER.getHeight() * -1;
          TranslateAnimation animation = new TranslateAnimation(0, 0, height, 0);
          animation.setDuration(750);

          parent.setVisibility(View.VISIBLE);
          parent.startAnimation(animation);

          ImageView cancelButton = (ImageView) findViewById(R.id.cancelbutton);
          cancelButton.setOnClickListener(new OnClickListener()
          {
            public void onClick(View arg0)
            {
              parent.setVisibility(View.GONE);
              adView.destroy();
            }

          });
        }

      });

    

  }
  
  private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public CharSequence getPageTitle(int position)
    {
      if(position == 0) return "Planner";
      else return "Schedule";
    }

    public MyFragmentPagerAdapter(FragmentManager fm)
    {
      super(fm);

    }

    public Fragment getItem(int arg0)
    {
      if(arg0 == 0){
        return new AssignmentViewFragment();
      }
      else if(arg0 == 1){
        return new ScheduleViewFragment();
      }
      return null;
    }

    public int getCount()
    {
      return 2;
    }
  
  }
  
}