package training.original.o05;

public class GroupingMain {
public static void main(String[] args) {
  StudentScore[] scores = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    new StudentScore(5, "Hanako", 80),
    new StudentScore(6, "Taro", 90),
    new StudentScore(7, "Taro", 60),
    null
};
System.out.println("メインの実験");
System.out.println(StudentScoreGroupingUtils.groupByName(scores));
System.out.println(StudentScoreGroupingUtils.countByName(scores));
System.out.println(StudentScoreGroupingUtils.partitionByPassed(scores, 80));
System.out.println(StudentScoreGroupingUtils.scoresByName(scores));
System.out.println(StudentScoreGroupingUtils.scoreSetByName(scores));
StudentScore[] empty = {};
StudentScore[] onlyNull = { null, null };
StudentScore[] nullArray = null;
System.out.println("空の配列の実験");
System.out.println(StudentScoreGroupingUtils.groupByName(empty));
System.out.println(StudentScoreGroupingUtils.countByName(empty));
System.out.println(StudentScoreGroupingUtils.partitionByPassed(empty, 80));
System.out.println(StudentScoreGroupingUtils.scoresByName(empty));
System.out.println(StudentScoreGroupingUtils.scoreSetByName(empty));
System.out.println("nullだけ配列の実験");
System.out.println(StudentScoreGroupingUtils.groupByName(onlyNull));
System.out.println(StudentScoreGroupingUtils.countByName(onlyNull));
System.out.println(StudentScoreGroupingUtils.partitionByPassed(onlyNull, 80));
System.out.println(StudentScoreGroupingUtils.scoresByName(onlyNull));
System.out.println(StudentScoreGroupingUtils.scoreSetByName(onlyNull));
System.out.println("配列自体がnullの実験");
System.out.println(StudentScoreGroupingUtils.groupByName(nullArray));
System.out.println(StudentScoreGroupingUtils.countByName(nullArray));
System.out.println(StudentScoreGroupingUtils.partitionByPassed(nullArray, 80));
System.out.println(StudentScoreGroupingUtils.scoresByName(nullArray));
System.out.println(StudentScoreGroupingUtils.scoreSetByName(nullArray));
}
}
