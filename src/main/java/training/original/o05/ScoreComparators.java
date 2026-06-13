package training.original.o05;
import java.util.Comparator;
public class ScoreComparators {
    public static Comparator<StudentScore> byScoreDesc(){
      return (s1,s2) -> Integer.compare(s2.getScore(), s1.getScore());
    }

    public static Comparator<StudentScore> byNameAsc(){
      return (s1,s2) -> s1.getName().compareTo(s2.getName());
    }

    public static Comparator<StudentScore> byScoreDescThenIdAsc(){
      Comparator<StudentScore> byScoreDesc = byScoreDesc();
      return (s1,s2) -> {
        int result = byScoreDesc.compare(s1, s2);
        if(result != 0){
          return result;
        }
        if(Integer.compare(s1.getStudentId(), s2.getStudentId())>0){
          return 1;
        }
        if(Integer.compare(s1.getStudentId(), s2.getStudentId())==0){
          return 0;
        }
        return -1;
      };
    }
}