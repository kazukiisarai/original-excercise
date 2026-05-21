package training.original.o03;

public class PayrollService {
  private Employee[] employees;
  public PayrollService(Employee[] employees){
    if(employees == null){
      this.employees = new Employee[0];
      return;
    }
    int nonNullCount = 0;
    for(int i=0;i<employees.length;i++){
      if(employees[i] != null){
        nonNullCount ++;
      }
    }

    Employee[] copyOfEmployees = new Employee[nonNullCount];
    int index = 0;
    for(int i=0;i<employees.length;i++){
      if(employees[i] != null){
      copyOfEmployees[index] = employees[i].copy();
      index ++;
      }
    }
    this.employees = copyOfEmployees;
  }
  public int totalMonthlyPay(){
    int totalMonthlyPay = 0;
    for(int i=0;i<this.employees.length;i++){
      totalMonthlyPay += employees[i].calculateMonthlyPay();
    }
    return totalMonthlyPay;
  }
  public int totalBonus(){
    int totalBonus = 0;
    for(int i=0;i<this.employees.length;i++){
      if(employees[i] instanceof BonusEligible){
        BonusEligible bonusTarget = (BonusEligible) employees[i];
        totalBonus += bonusTarget.calculateBonus();
      }
    }
    return totalBonus;
  }
  public void printAll(){
    for(int i=0;i<this.employees.length;i++){
      employees[i].printInfo();
    }
  }
  public void printBonusTargets(){
    for(int i=0;i<this.employees.length;i++){
      if(employees[i] instanceof BonusEligible){
        System.out.println(employees[i].getName());
        BonusEligible bonusTarget = (BonusEligible) employees[i];
        System.out.println(bonusTarget.calculateBonus());
      }
    }
  }

}
