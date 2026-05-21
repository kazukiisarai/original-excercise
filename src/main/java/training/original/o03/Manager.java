package training.original.o03;

public class Manager extends Employee implements BonusEligible {
  private int baseSalary;
  private int managementAllowance;
  public Manager(int id,String name,int baseSalary,int managementAllowance){
    super(id, name);
    if(baseSalary<0){
      this.baseSalary = 0;
    }else{
    this.baseSalary = baseSalary;
  }
    if(managementAllowance<0){
      this.managementAllowance = 0;
    }else{
    this.managementAllowance = managementAllowance;
    }
  }
  @Override
  public Employee copy(){
    return new Manager(this.getId(), this.getName(), this.baseSalary, this.managementAllowance);
  }
  @Override
  public int calculateMonthlyPay(){
    return baseSalary + managementAllowance;
  }
  @Override
  public String getRoleName(){
    return "管理職";
  }
  @Override
  public int calculateBonus(){
    return baseSalary/5;
  }
  public void holdMeeting(){
    System.out.println("会議を開催します。");
  }

}
