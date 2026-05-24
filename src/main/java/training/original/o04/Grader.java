package training.original.o04;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
public class Grader {
  private Map<Student,Integer> scores;
  public Grader(){
    this.scores = new HashMap<>();
  }

  public void addOrUpdate(Student student, int score){
    if(student == null){
      throw new IllegalArgumentException("studentはnullを受け付けません");
    }
    scores.put(new Student(student), validateScore(score));
  }
  public boolean remove(Student student){
    if(student == null){
      return false;
    }
    if(!scores.containsKey(student)){
      return false;
    }
    scores.remove(student);
    return true;
  }
  public int getScore(Student student){
    if(student == null){
      return -1;
    }
    if(!scores.containsKey(student)){
      return -1;
    }
    return scores.get(student);
  }
  public double average(){
    if(scores.isEmpty()){
      return 0.0;
    }
    double scoreSum = 0;
    for(Integer score : scores.values()){
      scoreSum += score;
    }
    return scoreSum/scores.size();
  }
  public Student topStudent(){
    if(scores.isEmpty()){
      return null;
    }
    Student topStudent = null;
    int topScore = 0;
    // 値も必要なのでentrySetを使用し、二重検索を排除する
    for(Map.Entry<Student,Integer> entry : scores.entrySet() ){
      // 全員0点のときに最高得点取得者がnullのままになるので、初回topStudent == nullの時だけはifに入らせる
      if(topStudent == null || entry.getValue() > topScore){
        topScore = entry.getValue();
        topStudent = entry.getKey();
      }
    }
    return new Student(topStudent);
  }
  public Set<Student> passedStudents(int passScore){
    Set<Student> passedStudentsSet = new HashSet<>();
    for(Map.Entry<Student,Integer> entry : scores.entrySet()){
      if(entry.getValue()>=passScore){
        passedStudentsSet.add(new Student(entry.getKey()));
      }
    }
    return passedStudentsSet;
  }

  public void printAll(){
    for(Map.Entry<Student,Integer> entry : scores.entrySet()){
      System.out.println("学生番号: "+entry.getKey().getId()+" 氏名: "+entry.getKey().getName()+" 点数: "+entry.getValue());
    }
  }
  private static int validateScore(int score){
    if(score < 0){
      return 0;
    }
    if(score > 100){
      return 100;
    }
    return score;
  }
}
