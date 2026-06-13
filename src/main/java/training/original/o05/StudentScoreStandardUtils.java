package training.original.o05;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
public class StudentScoreStandardUtils {
public static int count(StudentScore[] scores,Predicate<StudentScore> predicate){
  if(scores == null){
    return 0;
  }
  if(predicate == null){
    throw new IllegalArgumentException("条件はnullを認めません");
  }
  int count = 0;
  for(int i=0;i<scores.length;i++){
    if(scores[i]!=null && predicate.test(scores[i])){
      count ++;
    }
  }
  return count;
}

public static StudentScore[] filter(StudentScore[] scores,Predicate<StudentScore> predicate){
  if(scores == null){
    return new StudentScore[0];
  }
  if(predicate == null){
    throw new IllegalArgumentException("条件はnullを認めません");
  }
  int count = 0;
  for(int i=0;i<scores.length;i++){
    if(scores[i]!=null && predicate.test(scores[i])){
      count ++;
    }
  }
  StudentScore[] filteredScores = new StudentScore[count];
  int index = 0;
  for(int i=0;i<scores.length;i++){
    if(scores[i]!=null && predicate.test(scores[i])){
      filteredScores[index] = scores[i];
      index ++;
    }
  }
  return filteredScores;
}

public static String[] mapToStrings(StudentScore[] scores,Function<StudentScore,String> mapper){
  if(scores == null){
    return new String[0];
  }
  if(mapper == null){
    throw new IllegalArgumentException("mapperはnullを認めません");
  }
  int count = 0;
  for(int i=0;i<scores.length;i++){
    if(scores[i]!=null){
      count ++;
    }
  }
  String[] stringificationOfScores = new String[count];
  int index = 0;
  for(int i=0;i<scores.length;i++){
    if(scores[i]!=null){
      stringificationOfScores[index] = mapper.apply(scores[i]);
      index ++;
    }
  }
  return stringificationOfScores;
}

public static void forEach(StudentScore[] scores,Consumer<StudentScore> consumer){
  if(scores == null){
    return;
  }
  if(consumer == null){
    throw new IllegalArgumentException("consumerはnullを認めません");
  }
  for(int i=0;i<scores.length;i++){
    if(scores[i]!=null){
      consumer.accept(scores[i]);
    }
  }
}
}
