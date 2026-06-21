package training.original.o07;
import java.util.Objects;
public class LogEntry {
  private final String date;
  private final String level;
  private final String userId;
  private final String message;

  public LogEntry(String date,String level,String userId,String message){
    if(date == null || date.isBlank() || level == null || level.isBlank()){
      throw new IllegalArgumentException("dateまたはlevelがnullです。");
    }
    if(!(level.equals("INFO")||level.equals("WARN")||level.equals("ERROR"))){
      throw new IllegalArgumentException("levelは指定された3種以外を受け付けません。");
    }
    this.date = date;
    this.level = level;
    this.userId = (userId == null || userId.isBlank()) ? "unknown" : userId;
    this.message = (message == null) ? "" : message;
  }
public String getDate(){
return this.date;
}
public String getLevel(){
return this.level;
}
public String getUserId(){
return this.userId;
}
public String getMessage(){
return this.message;
}
@Override
public String toString(){
return "date: "+this.date+"level: "+this.level+"userId: "+this.userId+"message: "+this.message;
}
@Override
public boolean equals(Object obj){
  if(this == obj){
    return true;
  }
  if(obj == null || !(obj instanceof LogEntry)){
    return false;
  }
  LogEntry other = (LogEntry) obj;
  return this.date.equals(other.getDate())
  && this.level.equals(other.getLevel())
  && this.userId.equals(other.getUserId())
  && this.message.equals(other.getMessage());
}
@Override
public int hashCode(){
  return Objects.hash(this.date,this.level,this.userId,this.message);
}
}
