package training.original.o05;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class StudentScoreStatistics {
public static int totalScore(StudentScore[] scores){
if(scores==null){
  return 0;
}
return Arrays.stream(scores)
      .filter(s->s!=null)
      .mapToInt(StudentScore::getScore)
      .sum();
}

public static double averageScore(StudentScore[] scores){
  if(scores==null){
    return 0.0;
  }
  return Arrays.stream(scores)
        .filter(s->s!=null)
        .mapToInt(StudentScore::getScore)
        .average()
        .orElse(0.0);
}

public static OptionalInt maxScore(StudentScore[] scores){
  if(scores==null){
    return OptionalInt.empty();
  }
  return Arrays.stream(scores)
        .filter(s->s!=null)
        .mapToInt(StudentScore::getScore)
        .max();
}

public static OptionalInt minScore(StudentScore[] scores){
  if(scores==null){
    return OptionalInt.empty();
  }
  return Arrays.stream(scores)
        .filter(s->s!=null)
        .mapToInt(StudentScore::getScore)
        .min();
}

public static String averageLine(StudentScore[] scores){
  if(scores==null){
    return "平均: 該当なし";
  }
  OptionalDouble scoreAverage =  Arrays.stream(scores)
                                .filter(s->s!=null)
                                .mapToInt(StudentScore::getScore)
                                .average();
  return scoreAverage.isPresent() ? "平均: "+scoreAverage.getAsDouble()+"点" : "平均: 該当なし";
}
}
