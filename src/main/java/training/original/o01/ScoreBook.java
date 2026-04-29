package training.original.o01;
public class ScoreBook {
  private Student[] students;
  
  public ScoreBook(Student[] students){
    if(students == null){
      this.students = new Student[0];
      return;
    }
    // List<Student> studentsList = new ArrayList<>(Arrays.asList(students));
    // studentsList.remove(null);
    // Student[] newStudentsArray = studentsList.toArray(new Student[0]);
    // this.students = newStudentsArray;
    //同じ処理をリストではなく配列のみで実装
    int count = 0;
    for(int i=0;i<students.length;i++){
      if(students[i]==null){
        count++;
      }
    }
    Student[] newStudents = new Student[students.length-count];
    int index = 0;
    for(int i=0;i<students.length;i++){
      if(students[i]!=null){
        newStudents[index] = students[i];
        index++;
      }
    }
    this.students = newStudents;
  }
  public int size(){
    return this.students.length;
  }
  public Student getStudent(int index){
    if(index<0||index>=students.length){
      return null;
    }
    return this.students[index];
  }
  public Student topStudent(){
    if(this.students.length == 0){
      return null;
    }
    //仮のトップ生徒を決める
    Student topStudent = students[0];
    for(Student student:students){
      if(student.average()>topStudent.average()){
        topStudent = student;
      }
    }
    return topStudent;
  }
  public double classAverage(){
    if(students.length==0){
      return 0.0;
    }
    double sumOfAverage = 0;
    for(Student student : students){
      sumOfAverage += student.average();
    }
    sumOfAverage /= students.length;
    return sumOfAverage;
  }
  public void printAll(){
    for(Student student : students){
      student.printInfo();
    }
  }

}
