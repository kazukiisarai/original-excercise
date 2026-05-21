package training.original.o03;

public class PartTimeEmployee extends Employee {
  private int hourlyWage;
  private int workingHours;
  
  public PartTimeEmployee(int id,String name,int hourlyWage,int workingHours){
    super(id,name);
    if(hourlyWage<0){
      this.hourlyWage = 0;
    }else{
    this.hourlyWage = hourlyWage;
    }
    if(workingHours<0){
      this.workingHours = 0;
    }else{
    this.workingHours = workingHours;
    }
  }
  @Override
  public Employee copy(){
    return new PartTimeEmployee(this.getId(), this.getName(), this.hourlyWage, this.workingHours);
  }
  @Override
  public int calculateMonthlyPay(){
    return hourlyWage*workingHours;
  }
  @Override
  public String getRoleName(){
    return "アルバイト";
  }
}
