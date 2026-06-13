package training.original.o05;
import java.util.List;
import java.util.Set;
import java.util.Map;
public class CollectorMain {
public static void main(String[] args) {
  StudentScore[] scores = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    new StudentScore(1, "TaroUpdated", 60),
    new StudentScore(5, "Hanako", 80),
    null
};
List<StudentScore> nonNullList = StudentScoreCollectorUtils.toNonNullList(scores);
List<StudentScore> passedList = StudentScoreCollectorUtils.passedList(scores,80);
Set<String> nameSet = StudentScoreCollectorUtils.nameSet(scores);
Map<Integer,StudentScore> mapById = StudentScoreCollectorUtils.toMapById(scores);
Map<Integer,String> nameMapById = StudentScoreCollectorUtils.nameMapById(scores);

System.out.println(nonNullList);
System.out.println(passedList);
System.out.println(nameSet);
System.out.println(mapById);
System.out.println(nameMapById);

}
}
