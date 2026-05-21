package training.original.o03;

public class RegularEmployee extends Employee implements BonusEligible{
  private int baseSalary;
  private int overTimeHours;
  public RegularEmployee(int id,String name,int baseSalary,int overTimeHours){
    super(id,name);
    if(baseSalary<0){
      this.baseSalary = 0;
    }else{
    this.baseSalary = baseSalary;
  }
    if(overTimeHours<0){
      this.overTimeHours = 0;
    }else{
    this.overTimeHours = overTimeHours;
    }
  }
  @Override
  public Employee copy(){
    return new RegularEmployee(this.getId(), this.getName(), this.baseSalary, this.overTimeHours);
  }
  @Override
  public int calculateMonthlyPay(){
    return baseSalary + overTimeHours * 2000;
  }
  @Override
  public String getRoleName(){
    return "正社員";
  }
  @Override
  public int calculateBonus(){
    return baseSalary / 10;
  }
}
