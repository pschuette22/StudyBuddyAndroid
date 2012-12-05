package com.theStudyBuddy.Ignite.Classes;

public class MeetingObject
{
  private ClassObject parentClass;
  private int scheduleId;
  private String dayOfWeek;
  private String start;
  private String end;
  private boolean PAOn;
  private int alarmId;
  private int startId;

  MeetingObject()
  {
  }

  public MeetingObject(ClassObject _parent, int _scheduleId, String _day,
      String _start, String _end, int paStat, int _alarmId, int _startId)
  {
    parentClass = _parent;
    scheduleId = _scheduleId;
    dayOfWeek = _day;
    start = _start;
    end = _end;
    PAOn = (paStat == 1 ? true : false);
    alarmId = _alarmId;
    startId = _startId;
  }

  public int getScheduleId()
  {
    return scheduleId;
  }
  
  public void setScheduleId(int id){
    scheduleId = id;
  }

  public ClassObject getParent()
  {
    return parentClass;
  }

  public String getDayOfWeek()
  {
    return dayOfWeek;
  }

  public String getStart()
  {
    return start;
  }

  public void setStart(String _start)
  {
    start = _start;
  }

  public void setStartId(int _startId)
  {
    startId = _startId;
  }

  public int getStartId()
  {
    return startId;
  }

  public void setAlarmId(int _alarmId)
  {
    alarmId = _alarmId;
  }

  public int getAlarmId()
  {
    return alarmId;
  }

  public void setEnd(String _end)
  {
    end = _end;
  }

  public void setPA(boolean titties)
  {
    PAOn = titties;
  }

  public boolean paIsOn()
  {
    return PAOn;
  }

  public String getEnd()
  {
    return end;
  }
}
