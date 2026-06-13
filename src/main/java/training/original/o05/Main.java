package training.original.o05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Main {
  public static void main(String[] args){
    StudentScore s1 = new StudentScore(1, "tanaka", 45);
    StudentScore s2 = new StudentScore(2, "suzuki",80);
    StudentScore s3 = new StudentScore(3, "abe", 45);
    StudentScore s4 = new StudentScore(4, "tomita", 91);
    StudentScore s5 = new StudentScore(5, "satou", 32);
    StudentScore s6 = new StudentScore(6, "nakayama", 103);
    StudentScore s7 = new StudentScore(7, "nakayama", -5);

    //コンパレータの準備
    Comparator<StudentScore> scoreDesc = ScoreComparators.byScoreDesc();
    Comparator<StudentScore> nameAsc = ScoreComparators.byNameAsc();
    Comparator<StudentScore> scoreId = ScoreComparators.byScoreDescThenIdAsc();

    // 異なるスコア同士の比較
     System.out.println(scoreDesc.compare(s1,s2)); //1のはず
    //同じスコアの比較
     System.out.println(scoreId.compare(s1,s3)); //-1のはず
     //名前の比較
     System.out.println(nameAsc.compare(s5, s4)); //-1のはず
     //バリデーションの確認
     System.out.println(s6.toString());
     System.out.println(s7.toString());

     //指定された実験
     List<StudentScore> scores = new ArrayList<>();
     scores.add(new StudentScore(3, "Taro", 90));
     scores.add(new StudentScore(1, "Hanako", 90));
     scores.add(new StudentScore(2, "Jiro", 75));
     scores.add(new StudentScore(4, "Akira", 100));
     scores.add(new StudentScore(5, "Yui", 75));

     Collections.sort(scores, ScoreComparators.byScoreDesc());
     System.out.println(scores.toString());
     Collections.sort(scores, ScoreComparators.byNameAsc());
     System.out.println(scores.toString());
     Collections.sort(scores, ScoreComparators.byScoreDescThenIdAsc());
     System.out.println(scores.toString());

     // ユーティリティクラスで定義したメソッドの利用
     System.out.println("===================ユーティリティクラスで定義したメソッドの利用=====================");
     StudentScore[] scores1 = {
      new StudentScore(1, "Taro", 90),
      new StudentScore(2, "Hanako", 75),
      new StudentScore(3, "Jiro", 45),
      new StudentScore(4, "Akira", 100),
      null
  };
  int passedCount = StudentScoreUtils.count(scores1, s -> s.getScore()>=80);
  System.out.println("合格者数 : "+passedCount);

  StudentScore[] passed = StudentScoreUtils.filter(scores1,s->s.getScore()>=80);
  for(StudentScore s : passed){
    System.out.println(s.toString());
  }
  StudentScoreUtils.printFormatted(scores1,s->s.getName()+"さんの得点は"+s.getScore());
  StudentScore[] LowIdAndPassed = StudentScoreUtils.filter(scores1, s->{
    if(s.getStudentId()>3){
      return false;
    }
    return s.getScore()>=75;
  });
  for(int i=0;i<LowIdAndPassed.length;i++){
    System.out.println(LowIdAndPassed[i].getName());
  }
  System.out.println("===================標準APIを利用したユーティリティクラスで定義したメソッドの利用=====================");
  StudentScore[] scores2 = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    null
};
  int passedCount1 = StudentScoreStandardUtils.count(scores2, s->s.getScore()>=80);
  System.out.println("合格者数 : "+passedCount1);

  StudentScore[] passed1 = StudentScoreStandardUtils.filter(scores2,s->s.getScore()>=80);
  for(StudentScore s : passed1){
    System.out.println(s.getName());
  }

  StudentScoreStandardUtils.forEach(scores2, s->System.out.println(s.toString()));

  String[] lines = StudentScoreStandardUtils.mapToStrings(scores2, s->s.getName()+"さん、"+s.getScore()+"点");
  for(String s : lines){
    System.out.println(s);
  }

  StudentScore[] selected = StudentScoreStandardUtils.filter(scores2, s->{
    if(s.getStudentId()<=1){
      return false;
    }
    return s.getScore() >= 75;
  });
  for(StudentScore s : selected){
    System.out.println(s.toString());
  }

  System.out.println("================メソッド参照================");
  StudentScore[] scores3 = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    null
};
int passedCountByLambda = StudentScoreStandardUtils.count(scores3,s->s.getScore()>=80);
int passedCountByMethodRef = StudentScoreStandardUtils.count(scores3,StudentScorePredicates::isPassed);
System.out.println("合格者数"+passedCountByLambda);
System.out.println("合格者数"+passedCountByMethodRef);

StudentScore[] excellentStudents = StudentScoreStandardUtils.filter(scores3,StudentScorePredicates::isExcellent);
for(StudentScore s:excellentStudents){
  System.out.println(s.getName());
}
String[] line = StudentScoreStandardUtils.mapToStrings(scores3, StudentScoreFormatters::simpleLine);
for(String s:line){
  System.out.println(s);
}
StudentScoreStandardUtils.forEach(scores3, s->System.out.println(s));
StudentScoreStandardUtils.forEach(scores3, System.out::println);

String[] names = StudentScoreStandardUtils.mapToStrings(scores3,StudentScore :: getName );
for(String name : names){
  System.out.println(name);
}
}
}