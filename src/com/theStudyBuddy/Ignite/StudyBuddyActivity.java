package com.theStudyBuddy.Ignite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.theStudyBuddy.Ignite.Classes.ClassSpinnerData;
import com.theStudyBuddy.Ignite.Classes.EditScheduleActivity;
import com.theStudyBuddy.Ignite.Classes.ScheduleData;
import com.theStudyBuddy.Ignite.Classes.ScheduleViewActivity;
import com.theStudyBuddy.Ignite.Entries.AssignmentViewActivity;
import com.theStudyBuddy.Ignite.Settings.SettingsData;
import com.theStudyBuddy.Ignite.Settings.SettingsViewActivity;

public class StudyBuddyActivity extends FragmentActivity
{

  SettingsData settingsData;
  ScheduleData scheduleData;
  ClassSpinnerData classData;
  public static String currentFragment;
  final String adUnitId = "a14f89f84e10faa";

  StudyBuddyApplication StudyBuddy;
  ScheduleViewActivity ScheduleView;

  public static final String TAG = "TAG";

  
  
  @Override
  public void onBackPressed()
  {
    
    if (currentFragment.contentEquals("Settings"))
    {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right);
      Fragment newFrag = new AssignmentViewActivity();
      ft.replace(R.id.fragmentContainerUnique, newFrag).commit();
      return;
    }
    if (currentFragment.contentEquals("Schedule"))
    {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.setCustomAnimations(R.anim.out_to_left, R.anim.in_from_right);
      Fragment newFrag = new AssignmentViewActivity();
      ft.replace(R.id.fragmentContainerUnique, newFrag).commit();
      return;
    }
    else
    {
      // close out
      moveTaskToBack(true);

      super.onBackPressed();
    }

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
    Log.d("TAG", "Creating");
      
    LinearLayout mainLinLayout = (LinearLayout) findViewById(R.id.fragmentContainerUnique);
    mainLinLayout.removeAllViews();
    
    if(savedInstanceState == null){
      Bundle extras = getIntent().getExtras();
      if (extras != null)
      {
        if (extras.getString(Intent.EXTRA_TEXT).contentEquals("Schedule"))
        {
          FragmentManager fragmentManager = this.getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager
              .beginTransaction();
          Fragment AVfragment = new ScheduleViewActivity();
          fragmentTransaction.add(R.id.fragmentContainerUnique, AVfragment);
          fragmentTransaction.commit();
        }
        else if (extras.getString(Intent.EXTRA_TEXT).contentEquals("Settings"))
        {
          FragmentManager fragmentManager = this.getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager
              .beginTransaction();
          Fragment SVfragment = new SettingsViewActivity();
          fragmentTransaction.add(R.id.fragmentContainerUnique, SVfragment);
          fragmentTransaction.commit();
        }
        else
        {
          FragmentManager fragmentManager = this.getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager
              .beginTransaction();
          Fragment AVfragment = new AssignmentViewActivity();
          fragmentTransaction.add(R.id.fragmentContainerUnique, AVfragment);
          fragmentTransaction.commit();
        }
      }
      else
      {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
            .beginTransaction();
        Fragment AVfragment = new AssignmentViewActivity();
        fragmentTransaction.add(R.id.fragmentContainerUnique, AVfragment);
        fragmentTransaction.commit();
      }
    }

    tutorial();
    LaunchAds(this);

  }

  public void tutorial()
  {
    StudyBuddyApplication StudyBuddy = (StudyBuddyApplication) getApplication();
    if (StudyBuddy.getAllClasses().size() <= 1)
    {
      SettingsData settingsData = new SettingsData(this);

      if (settingsData.firstTime())
      {
        settingsData.updateSetting("First Load", 1);

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
  
  

}