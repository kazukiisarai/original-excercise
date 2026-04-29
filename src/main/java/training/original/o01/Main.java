package training.original.o01; 

public class Main { 
  public static void main(String[] args){
     int scores[] = {70,180,-90}; 
     Student s = new Student(1, "Taro", scores); 
     scores[0] = 0; 
     s.printInfo(); 
     int[] gotScores = s.getScores(); 
     gotScores[1] = 0; 
     s.printInfo(); 
    } 
  }