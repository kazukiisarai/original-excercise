package training.original.o05;

public class StreamMain {
public static void main(String[] args){
  StudentScore[] scores = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    null
};
System.out.println(StudentScoreStreamUtils.countPassed(scores));
StudentScore[] passedStudent = StudentScoreStreamUtils.passedStudents(scores);
for(int i=0;i<passedStudent.length;i++){
  System.out.println(passedStudent[i].getName());
}
String[] students = StudentScoreStreamUtils.toDisplayLines(scores);
for(int i=0;i<students.length;i++){
  System.out.println(students[i]);
}
StudentScoreStreamUtils.printAll(scores);
}
}
