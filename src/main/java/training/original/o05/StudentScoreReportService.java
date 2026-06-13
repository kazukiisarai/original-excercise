package training.original.o05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Objects;

public class StudentScoreReportService {
public static List<StudentScore> topN(StudentScore[] scores, int n){
if(scores == null || n<=0){
  return new ArrayList<>();
}
Comparator<StudentScore> comparator = Comparator.comparing(StudentScore::getScore)
                                      .reversed()
                                      .thenComparing(StudentScore::getStudentId);
return Arrays.stream(scores)
      .filter(Objects::nonNull)
      .sorted(comparator)
      .limit(n)
      .collect(Collectors.toList());
}

public static Map<String, Long> countPassedByName(StudentScore[] scores, int passScore){
if(scores == null){
  return new HashMap<>();
}
return Arrays.stream(scores)
      .filter(Objects::nonNull)
      .filter(s->s.getScore()>=passScore)
      .collect(Collectors.groupingBy(StudentScore::getName,Collectors.counting()));
}

public static Map<Integer, String> passedNameMapById(StudentScore[] scores, int passScore){
  if(scores == null){
    return new HashMap<>();
  }
  return Arrays.stream(scores)
        .filter(Objects::nonNull)
        .filter(s->s.getScore()>=passScore)
        .collect(Collectors.toMap(StudentScore::getStudentId, StudentScore::getName,(existing,replacement)->replacement));
}

public static Optional<String> topStudentName(StudentScore[] scores){
if(scores == null || scores.length == 0){
  return Optional.empty();
}
Comparator<StudentScore> comparator = Comparator.comparing(StudentScore::getScore).thenComparing(Comparator.comparing(StudentScore::getStudentId).reversed());
return Arrays.stream(scores)
      .filter(Objects::nonNull)
      .max(comparator)
      .map(StudentScore::getName);
}

public static List<String> reportLines(StudentScore[] scores){
  if(scores == null){
    return new ArrayList<>();
  }
  Comparator<StudentScore> comparator = Comparator.comparing(StudentScore::getScore).reversed().thenComparing(StudentScore::getStudentId);
  List<StudentScore> sortedList = Arrays.stream(scores)
                                .filter(Objects::nonNull)
                                .sorted(comparator)
                                .toList();
  List<String> resultList = new ArrayList<>();
  for(int i=0;i<sortedList.size();i++){
    resultList.add(i+1+"位: "+sortedList.get(i).getName()+" "+sortedList.get(i).getScore()+"点");
  }
  return resultList;
}
}
