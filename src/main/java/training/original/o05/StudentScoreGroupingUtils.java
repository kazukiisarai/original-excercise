package training.original.o05; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 
import java.util.Set; 
import java.util.stream.Collectors; 
import java.util.Objects; 
import java.util.ArrayList; 
import java.util.Arrays; 
public class StudentScoreGroupingUtils { 
  public static Map<String, List<StudentScore>> groupByName(StudentScore[] scores){ 
    if(scores==null){ 
      return new HashMap<>(); 
    } 
    return Arrays.stream(scores) 
    .filter(Objects::nonNull) 
    .collect(Collectors.groupingBy(StudentScore::getName)); 
  } 
  public static Map<String, Long> countByName(StudentScore[] scores){ 
    if(scores==null){ 
      return new HashMap<>(); 
    } 
    return Arrays.stream(scores) 
    .filter(Objects::nonNull) 
    .collect(Collectors.groupingBy(StudentScore::getName,Collectors.counting())); 
  } 
  public static Map<Boolean, List<StudentScore>> partitionByPassed(StudentScore[] scores, int passScore){ 
    if(scores==null || scores.length == 0 || Arrays.stream(scores).allMatch(Objects::isNull)){ 
      Map<Boolean,List<StudentScore>> resultMap = new HashMap<>(); 
      resultMap.put(true, new ArrayList<>()); 
      resultMap.put(false, new ArrayList<>()); 
      return resultMap; 
    } 
    return Arrays.stream(scores) 
    .filter(Objects::nonNull) 
    .collect(Collectors.partitioningBy(s->s.getScore()>=passScore)); 
  } 
  public static Map<String, List<Integer>> scoresByName(StudentScore[] scores){ 
    if(scores==null){ return new HashMap<>(); 
    } 
    return Arrays.stream(scores) 
    .filter(Objects::nonNull) 
    .collect(Collectors.groupingBy(StudentScore::getName,Collectors.mapping(StudentScore::getScore,Collectors.toList()))); 
  } 
  public static Map<String, Set<Integer>> scoreSetByName(StudentScore[] scores){ if(scores==null){ 
    return new HashMap<>(); 
  } 
  return Arrays.stream(scores) 
  .filter(Objects::nonNull) 
  .collect(Collectors.groupingBy(StudentScore::getName,Collectors.mapping(StudentScore::getScore, Collectors.toSet()))); 
} 
}