package com.theStudyBuddy.Ignite.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.theStudyBuddy.Ignite.Entries.EntryObject;

public class ClassObject
{
  private ArrayList<EntryObject> entries;
  private ArrayList<MeetingObject> meetings;
  private String title;
  private int pos; 
  
  private Date lastDate;
  private String professor;
  private String profEmail;
  
  private boolean online;

  ClassObject()
  { // null constructor, probably not going to be used
  }
  
  public ClassObject(String title_, int position, boolean online_){
    title = title_;
    pos = position;
    online = online_;
    entries = new ArrayList<EntryObject>();
    meetings = new ArrayList<MeetingObject>();
  }
  
  public boolean isOnline(){
    return online;
  }

  
  public void setPosition(int pos_){
    pos = pos_;
  }
  
  public int getPosition(){
    return pos;
  }
  
  public void setOnline(boolean online_){
    online = online_;
  }
  
  public ArrayList<EntryObject> getAllEntries(){

    Collections.sort(entries, new Comparator<EntryObject>(){
      public int compare(EntryObject entry1, EntryObject entry2)
      {
        return entry1.getDate().compareTo(entry2.getDate());
      }
    });
    return entries;
  }
  
  public void setMeetingArray(ArrayList<MeetingObject> addThese){
    if(meetings != null) meetings.clear();
    else meetings = new ArrayList<MeetingObject>();
    meetings.addAll(addThese);
  }
  
  public void setEntryArray(ArrayList<EntryObject> addThese){
    if (entries != null) entries.clear();
    else entries = new ArrayList<EntryObject>();
    entries.addAll(addThese); 
  }
  
  public void addEntry(EntryObject newEntry){
    entries.add(newEntry);
  }
  
  public void addMeeting(MeetingObject newMeeting){
    meetings.add(newMeeting);
  }

  public ArrayList<MeetingObject> getMeetings()
  {
    return meetings;
  }

  public String getTitle()
  {
    return title;
  }

  public Date getLastDay()
  {
    return lastDate;
  }

  public String getProf()
  {
    return professor;
  }

  public String getProfEmail()
  {
    return profEmail;
  }

}
