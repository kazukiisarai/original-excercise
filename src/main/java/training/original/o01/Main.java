package training.original.o01; 

public class Main { 
  public static void main(String[] args){
    //  int scores[] = {70,180,-90}; 
    //  Student s = new Student(1, "Taro", scores); 
    //  scores[0] = 0; 
    //  s.printInfo(); 
    //  int[] gotScores = s.getScores(); 
    //  gotScores[1] = 0; 
    //  s.printInfo(); 

     int[] scores = {80, 90};
Student s1 = new Student(1, "Taro", scores);

Student[] students = {s1};
ScoreBook book = new ScoreBook(students);

s1.setName("Jiro");
s1.setScore(0, 0);

book.printAll();
Student got = book.getStudent(0);
got.setName("Saburo");
got.setScore(1, 0);

book.printAll();
    } 
  }