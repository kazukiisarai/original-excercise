package training.original.o05;
import static training.original.o05.StudentScoreStreamAdvanced.sortedByScoreDescThenIdAsc;
import static training.original.o05.StudentScoreStreamAdvanced.topStudent;
import static training.original.o05.StudentScoreStreamAdvanced.findById;
import static training.original.o05.StudentScoreStreamAdvanced.topStudentLine;
public class StreamAdvancedMain {
public static void main(String[] args){
  StudentScore[] scores = {
    new StudentScore(3, "Taro", 90),
    new StudentScore(1, "Hanako", 90),
    new StudentScore(2, "Jiro", 75),
    new StudentScore(4, "Akira", 100),
    new StudentScore(5, "Yui", 75),
    null
};
System.out.println("====sort====");
StudentScore[] sortedScores = sortedByScoreDescThenIdAsc(scores);
System.out.print("[");
for(int i=0;i<sortedScores.length;i++){
  System.out.print(sortedScores[i].getName()+sortedScores[i].getScore());
  if(i<sortedScores.length-1){
    System.out.print(",");
  }
}
System.out.println("]");
System.out.println("====top====");
System.out.println(topStudent(scores));
System.out.println("====find====");
System.out.println(findById(scores, 1));
System.out.println(findById(scores, 999));
System.out.println("====topline====");
System.out.println(topStudentLine(scores));
}
}
