package com.theStudyBuddy.Ignite.Entries;

import com.theStudyBuddy.Ignite.Classes.ClassObject;

public class EntryObject implements Comparable<EntryObject>
{

  private String type;
  private String enteredText;
  private String dueDateString;
  private int DateId;
  private int dbId;
  private ClassObject parentClass;
  private boolean completed;

  public EntryObject(String _enteredText, int _Date, ClassObject _parentClass,
      int _completed, String _type, int _dbId, String dateString)
  {
    enteredText = _enteredText;
    setDate(_Date);
    parentClass = _parentClass;
    type = _type;
    completed = (_completed == 1 ? true : false);
    dbId = _dbId;
    dueDateString = dateString;
  }
  
  public void editEntry(String _enteredText, int _Date, ClassObject _parentClass,
      String _type, int _dbId, String dateString)
  {
    enteredText = _enteredText;
    dueDateString = dateString;
    parentClass = _parentClass;
    DateId = _Date;
    type = _type;
    dbId = _dbId;
    
  }
  
  public void setDBId(int id){
    dbId = id;
  }
  public int getDbId(){
    return dbId;
  }

  // getters
  public String getText()
  {
    return enteredText;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String _type)
  {
    type = _type;
  }

  public String getDate()
  {
    return dueDateString;
  }

  public int getDateId()
  {
    return DateId;
  }

  public ClassObject getParent()
  {
    return parentClass;
  }

  public boolean isCompleted()
  {
    return completed;
  }

  // setters
  public void setText(String Text)
  {
    enteredText = Text;
  }

  public void setParent(ClassObject parent)
  {
    parentClass = parent;
  }

  public void setCompletion(boolean setIt)
  {
    completed = setIt;
  }

  public void setDate(int _dateId)
  {
    DateId = _dateId;
  }

  public int compareTo(EntryObject arg0)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
