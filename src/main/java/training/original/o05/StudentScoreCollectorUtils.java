package training.original.o05;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
public class StudentScoreCollectorUtils {
public static List<StudentScore> toNonNullList(StudentScore[] scores){
if(scores == null){
  return new ArrayList<StudentScore>();
}
return Arrays.stream(scores)
      .filter(s->s!=null)
      .collect(Collectors.toList());
}

public static List<StudentScore> passedList(StudentScore[] scores, int passScore){
  if(scores == null){
    return new ArrayList<StudentScore>();
  }
  return Arrays.stream(scores)
        .filter(s->s!=null)
        .filter(s->s.getScore()>=passScore)
        .collect(Collectors.toList());
}

public static Set<String> nameSet(StudentScore[] scores){
if(scores == null){
  return new HashSet<>();
}
return Arrays.stream(scores)
      .filter(s->s!=null)
      .map(s->s.getName())
      .collect(Collectors.toSet());
}

public static Map<Integer, StudentScore> toMapById(StudentScore[] scores){
if(scores == null){
  return new HashMap<>();
}
return Arrays.stream(scores)
      .filter(s->s!=null)
      .collect(Collectors.toMap(s->s.getStudentId(),Function.identity(),(existing,replacement)->replacement));
}

public static Map<Integer, String> nameMapById(StudentScore[] scores){
if(scores==null){
  return new HashMap<>();
}
return Arrays.stream(scores)
      .filter(s->s!=null)
      .collect(Collectors.toMap(s->s.getStudentId(), s->s.getName(),(existing,replacement)->replacement));
}
}
