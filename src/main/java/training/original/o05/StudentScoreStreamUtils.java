package training.original.o05;
import java.util.Arrays;
public class StudentScoreStreamUtils {
public static long countPassed(StudentScore[] scores){
if(scores == null){
  return 0;
}
return Arrays.stream(scores)
  .filter(s->s!=null)
  .filter(s->s.getScore()>=80)
  .count();
}

public static StudentScore[] passedStudents(StudentScore[] scores){
  if(scores == null){
    return new StudentScore[0];
  }
  return Arrays.stream(scores)
    .filter(s->s!=null)
    .filter(s->s.getScore()>=80)
    .toArray(StudentScore[]::new);
}

public static String[] toDisplayLines(StudentScore[] scores){
  if(scores == null){
    return new String[0];
  }
  return Arrays.stream(scores)
    .filter(s->s!=null)
    .map(s->s.getName()+":"+s.getScore())
    .toArray(String[]::new);
}

public static void printAll(StudentScore[] scores){
  if(scores == null){
    return ;
  }
  Arrays.stream(scores)
  .filter(s->s!=null)
  .forEach(System.out::println);
}
}
