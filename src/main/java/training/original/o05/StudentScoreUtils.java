package training.original.o05;

public class StudentScoreUtils {
  public static int count(StudentScore[] scores, ScoreCondition condition){
    if(scores == null){
      return 0;
    }
    if(condition == null){
      throw new IllegalArgumentException("条件conditionはnullを認めません");
    }
    StudentScore[] validScores = makeNonNullArray(scores);
    int count = 0;
    for(int i=0;i<validScores.length;i++){
      if(condition.test(validScores[i])){
        count++;
      }
    }
    return count;
  }

  public static StudentScore[] filter(StudentScore[] scores,ScoreCondition condition){
    if(scores == null){
      return new StudentScore[0];
    }
    if(condition == null){
      throw new IllegalArgumentException("条件conditionはnullを認めません");
    }
    StudentScore[] validScores = makeNonNullArray(scores);
    int count = 0;
    for(int i=0;i<validScores.length;i++){
    if(condition.test(validScores[i])){
      count++;
    }
  }
    StudentScore[] filteredScores = new StudentScore[count];
    int index = 0;
    for(int i=0;i<validScores.length;i++){
      if(condition.test(validScores[i])){
      filteredScores[index] = validScores[i]; //もしStudentScoreクラスにnameやid等のsetterがあればここでディープコピー。
      // StudentScoreクラスにコピー用のコンストラクタStudentScore(StudentScore scores)を作ってここではnew StudentScore(validscores[i]);
      index++;
      }
    }
    return filteredScores;
  }

  public static void printFormatted(StudentScore[] scores,ScoreFormatter formatter){
    if(scores == null){
      return;
    }
    if(formatter == null){
      throw new IllegalArgumentException("フォーマット規則formatterはnullを認めません");
    }
    StudentScore[] validScores = makeNonNullArray(scores);
    for(int i=0;i<validScores.length;i++){
      System.out.println(formatter.format(validScores[i]));
    }
  }

  private static StudentScore[] makeNonNullArray(StudentScore[] scores){
    int count = 0;
    for(int i = 0;i<scores.length;i++){
      if(scores[i]!=null){
        count++;
      }
    }
    StudentScore[] nonNullArray = new StudentScore[count];
    int index=0;
    for(int i=0;i<scores.length;i++){
      if(scores[i]!=null){
        nonNullArray[index] = scores[i];
        index++;
      }
    }
    return nonNullArray;
  }
}
