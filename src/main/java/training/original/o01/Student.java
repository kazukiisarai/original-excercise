package training.original.o01;

public class Student {
  private int id;
  private String name;
  private int[] scores;

  //コンストラクタ
  public Student(int id,String name,final int[] scores){
    this.id = id;
    this.name = validateName(name);
    this.scores = validateScores(scores);
  }
  //ゲッター
  public int getId(){
    return this.id;
  }
  public String getName(){
    return this.name;
  }
  public int[] getScores(){
    int[] gotScores = new int[scores.length];
    for(int i=0;i<scores.length;i++){
      gotScores[i] = scores[i];
    }
    return gotScores;
  }
  //セッター
  public void setName(String name){
    this.name = validateName(name);
  }
  public void setScore(int index,int score){
    if(index<0||index>=scores.length){
      return;
    }
    this.scores[index] = validateScore(score);
  }
  //Validation
  private static String validateName(String name){
    if(name == null || name.isBlank()){
      name = "unknown";
    }
    if(name.length()>20){
      name = name.substring(0, 20);
    }
    return name;
  }
  private static int[] validateScores(int[] scores){
    if(scores == null || scores.length==0){
      return new int[0];
    }
    int[] validScores = new int[scores.length];
    for(int i = 0;i<scores.length;i++){
      validScores[i] = validateScore(scores[i]);    
  }
  return validScores;
}
  private static int validateScore(int score){
    if(score<0){
      return 0;
    }
    if(score>100){
      return 100;
    }
    return score;
  }
  //Average
  public double average(){
    if(scores.length==0){
      return 0.0;
    }
    double sum = 0;
    for(int score : this.scores){
      sum += score;
    }
    return sum/(double)this.scores.length;
  }
  public void printInfo(){
    System.out.println("====生徒情報====");
    System.out.println("名前 : "+this.name);
    System.out.println("番号 : "+this.id);
    System.out.println("平均点 : "+this.average());
    for(int score : scores){
      System.out.println("得点"+score);
    }
  }
}
