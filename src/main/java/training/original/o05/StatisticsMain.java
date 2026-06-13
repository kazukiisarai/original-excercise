package training.original.o05;

public class StatisticsMain {
public static void main(String[] args) {
  StudentScore[] scores = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    null
};
System.out.println(StudentScoreStatistics.totalScore(scores));
System.out.println(StudentScoreStatistics.averageScore(scores));
System.out.println(StudentScoreStatistics.maxScore(scores));
System.out.println(StudentScoreStatistics.minScore(scores));
System.out.println(StudentScoreStatistics.averageLine(scores));

StudentScore[] empty = {};
StudentScore[] onlyNull = { null, null };
StudentScore[] nullArray = null;

System.out.println(StudentScoreStatistics.totalScore(empty));
System.out.println(StudentScoreStatistics.averageScore(empty));
System.out.println(StudentScoreStatistics.maxScore(empty));
System.out.println(StudentScoreStatistics.minScore(empty));
System.out.println(StudentScoreStatistics.averageLine(empty));

System.out.println(StudentScoreStatistics.totalScore(onlyNull));
System.out.println(StudentScoreStatistics.averageScore(onlyNull));
System.out.println(StudentScoreStatistics.maxScore(onlyNull));
System.out.println(StudentScoreStatistics.minScore(onlyNull));
System.out.println(StudentScoreStatistics.averageLine(onlyNull));

System.out.println(StudentScoreStatistics.totalScore(nullArray));
System.out.println(StudentScoreStatistics.averageScore(nullArray));
System.out.println(StudentScoreStatistics.maxScore(nullArray));
System.out.println(StudentScoreStatistics.minScore(nullArray));
System.out.println(StudentScoreStatistics.averageLine(nullArray));
}
}
