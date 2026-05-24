package training.original.o04;

public class Main{
  public static void main(String[] args){
Grader grader = new Grader();

Student s1 = new Student(1, "Taro");
Student s2 = new Student(2, "Hanako");
Student s3 = new Student(1, "Jiro");

grader.addOrUpdate(s1, 80);
grader.addOrUpdate(s2, 95);
grader.addOrUpdate(s3, 70);

grader.printAll();

System.out.println("s1 score = " + grader.getScore(s1));
System.out.println("s3 score = " + grader.getScore(s3));
System.out.println("average = " + grader.average());
System.out.println("top = " + grader.topStudent());
System.out.println("passed = " + grader.passedStudents(80));

s1.setName("Changed");
grader.printAll();
  }
}