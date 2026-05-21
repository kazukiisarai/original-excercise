package training.original.o03;

public abstract class Employee {
  private int id;
  private String name;

  public Employee(int id,String name){
    if(id <= 0){
      throw new IllegalArgumentException("idは正の整数にしてください。id :"+id);
    }
    this.id = id;
    this.name = validateName(name);
  }
  public int getId(){
    return this.id;
  }
  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name = validateName(name);
  }
  public abstract Employee copy();
  public abstract int calculateMonthlyPay();
  public abstract String getRoleName();
  public void printInfo(){
    System.out.println(this.toString());
  }
  @Override
  public String toString(){
    return "id:"+this.getId()+", name:"+this.getName()+", roleName:"+this.getRoleName()+", MonthlyPay:"+this.calculateMonthlyPay();
  }
  @Override
  public boolean equals(Object obj){
    if(obj == this){
      return true;
    }
    if(obj == null || !(obj instanceof Employee)){
      return false;
    }
    Employee employee = (Employee) obj;
    return this.id == employee.getId();
  }
  @Override
  public int hashCode(){
    return Integer.hashCode(id);
  }
  private static String validateName(String name){
    if(name == null || name.isBlank()){
      return "unknown";
    }
    return name;
  }
}
