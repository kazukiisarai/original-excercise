package training.original.o05;

public class StudentScorePredicates {
private StudentScorePredicates(){}
public static boolean isPassed(StudentScore score){
  return score.getScore()>=80;
}
public static boolean isExcellent(StudentScore score){
  return score.getScore()>=90;
}
}
