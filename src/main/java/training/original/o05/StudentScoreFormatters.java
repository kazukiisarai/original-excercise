package training.original.o05;

public class StudentScoreFormatters {
private StudentScoreFormatters(){}

public static String simpleLine(StudentScore score){
  return score.getName() + "さん: " + score.getScore() + "点";
}
}
