package training.original.o04;
import java.util.Objects;
public class Student {
  private int id;
  private String name;

  public Student(int id,String name){
    if(id<=0){
      throw new IllegalArgumentException("idは正の整数です。id: "+id);
    }
    this.id = id;
    this.name = validName(name);
  }
  public Student(Student other){
    if(other == null){
      throw new IllegalArgumentException("otherはnullを指定できません。");
    }
    this.id = other.id;
    this.name = other.name;
  }
public int getId(){
  return this.id;
}
public String getName(){
  return this.name;
}
public void setName(String name){
  this.name = validName(name);
}
@Override
public boolean equals(Object obj){
  if(obj == this){
    return true;
  }
  if(!(obj instanceof Student)){
    return false;
  }
  Student student = (Student) obj;
  return this.getId() == student.getId();
}
@Override
public int hashCode(){
 return Objects.hash(this.getId());
}
@Override
public String toString(){
  return "学生番号:"+this.getId()+", 氏名:"+this.getName();
}
private static String validName(String name){
  if(name == null || name.isBlank()){
    return "unknown";
  }
  return name;
}
}
