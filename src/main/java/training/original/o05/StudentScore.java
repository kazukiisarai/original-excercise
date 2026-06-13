package training.original.o05;
import java.util.Objects;
public class StudentScore {
  private int studentId;
  private String name;
  private int score;

  public StudentScore(int studentId,String name,int score){
    if(studentId<=0){
      throw new IllegalArgumentException();
    }
    this.studentId = studentId;
    this.name = validateName(name);
    this.score = validateScore(score);
  }
  private String validateName(String name){
    if(name == null || name.isBlank()){
      return "unknown";
    }
    return name;
  }

  private int validateScore(int score){
    if(score<0){
      return 0;
    }
    if(score > 100){
      return 100;
    }
    return score;
  }

  public int getStudentId(){
    return this.studentId;
  }
  public String getName(){
    return this.name;
  }
  public int getScore(){
    return this.score;
  }

  @Override
  public String toString(){
    return "学生番号: "+this.getStudentId()+" 氏名: "+this.getName()+" スコア: "+this.getScore();
  }
  @Override
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }
    if(!(obj instanceof StudentScore)){
      return false;
    }
    StudentScore studentScore = (StudentScore) obj;
    if(this.getStudentId() == studentScore.getStudentId()){
      return true;
    }
    return false;
  }
  @Override
  public int hashCode(){
    return Objects.hash(this.getStudentId());
  }

}
