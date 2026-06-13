package training.original.o05;

public class ReportMain {
public static void main(String[] args) {
  StudentScore[] scores = {
    new StudentScore(3, "Taro", 90),
    new StudentScore(1, "Hanako", 90),
    new StudentScore(2, "Jiro", 75),
    new StudentScore(4, "Akira", 100),
    new StudentScore(5, "Yui", 75),
    new StudentScore(1, "HanakoUpdated", 60),
    null
};

System.out.println(StudentScoreReportService.topN(scores, 3));
System.out.println(StudentScoreReportService.countPassedByName(scores, 80));
System.out.println(StudentScoreReportService.passedNameMapById(scores, 80));
System.out.println(StudentScoreReportService.topStudentName(scores));
System.out.println(StudentScoreReportService.reportLines(scores));
}
}
