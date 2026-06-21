package training.original.o07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Objects;

public class LogEntryParser {
public static Optional<LogEntry> parse(String line){
if(line == null){
  return Optional.empty();
}
String regex = "\\[(?<date>\\d{4}-\\d{2}-\\d{2})\\]\\s(?<LEVEL>INFO|WARN|ERROR)\\suser=(?<userId>\\w+)\\smessage=(?<message>.*)";
Pattern pattern = Pattern.compile(regex);
Matcher matcher = pattern.matcher(line);
if(!matcher.matches()){
  return Optional.empty();
}
String date = matcher.group("date");
String level = matcher.group("LEVEL");
String userId = matcher.group("userId");
String message = matcher.group("message");
try{
  return Optional.of(new LogEntry(date,level,userId,message));
}catch(IllegalArgumentException e){
  return Optional.empty();
}
}

public static List<LogEntry> parseAll(List<String> lines){
if(lines == null){
  return new ArrayList<>();
}

return lines.stream()
      .filter(Objects::nonNull)
      .map(LogEntryParser::parse)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .toList();
}

public static List<LogEntry> filterByLevel(List<LogEntry> entries, String level){
  if(entries == null || level == null || level.isBlank()){
    return new ArrayList<>();
  }
  return entries.stream()
        .filter(Objects::nonNull)
        .filter(entry->{
          String thisLevel = entry.getLevel();
          return thisLevel.equals(level);
        })
        .toList();
}

public static Map<String, Long> countByLevel(List<LogEntry> entries){
if(entries == null){
  return new HashMap<>();
}
return entries.stream()
      .filter(Objects::nonNull)
      .collect(Collectors.groupingBy(LogEntry::getLevel,Collectors.counting()));
}

public static String maskUserId(String line){
if(line == null){
  return null;
}
String regexForFindUser = "user=\\w";
Pattern pattern1 = Pattern.compile(regexForFindUser);
Matcher matcher = pattern1.matcher(line);
if(!matcher.find()){
 return line;
}
String regexForReplacement = "(?<=user=)\\w+(?=\\smessage=)";
Pattern pattern2 = Pattern.compile(regexForReplacement);
Matcher matcher2 = pattern2.matcher(line);
return matcher2.replaceAll("****");
}
}
