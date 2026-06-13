package training.original.o05;
import java.util.Optional;
import java.util.Arrays;
import java.util.Comparator;
public class StudentScoreStreamAdvanced {
public static StudentScore[] sortedByScoreDescThenIdAsc(StudentScore[] scores){
if(scores == null){
  return new StudentScore[0];
}
  return Arrays.stream(scores)
      .filter(s->s!=null)
      .sorted(
      //  (s1,s2)->{
      //   int compare = s2.getScore()-s1.getScore();
      //   if(compare == 0){
      //     return s1.getStudentId() - s2.getStudentId();
      //   }
      //   return compare;}
      // 最初はこう書いていたけどより良い書き方を知ったので実践
      Comparator.comparingInt(StudentScore::getScore).reversed()
      .thenComparing(StudentScore::getStudentId)
      )
      .toArray(StudentScore[]::new);
}

public static Optional<StudentScore> topStudent(StudentScore[] scores){
  if(scores==null){
    return Optional.empty();
  }
 return Arrays.stream(scores)
      .filter(s->s!=null)
      .max(Comparator.comparing(StudentScore::getScore)
      .thenComparing(Comparator.comparing(StudentScore::getStudentId).reversed()));
}

public static Optional<StudentScore> findById(StudentScore[] scores,int studentId){
  if(scores==null || studentId <= 0){
    return Optional.empty();
  }
return Arrays.stream(scores)
      .filter(s->s!=null)
      .filter(s->s.getStudentId() == studentId)
      .findFirst();
}

public static String topStudentLine(StudentScore[] scores){
return topStudent(scores)
      .map(s->"トップ: "+s.getName()+" "+s.getScore()+"点")
      .orElse("該当なし");
}
}
