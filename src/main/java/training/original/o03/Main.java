package training.original.o03;

public class Main {
  public static void main(String[] args){
  Employee[] employees = {
    new RegularEmployee(1, "Taro", 300000, 10),
    new PartTimeEmployee(2, "Hanako", 1200, 80),
    new Manager(3, "Sato", 500000, 100000),
    null
};

PayrollService service = new PayrollService(employees);

service.printAll();
System.out.println("月給合計: " + service.totalMonthlyPay());
System.out.println("ボーナス合計: " + service.totalBonus());
service.printBonusTargets();

Employee e = new Manager(4, "Suzuki", 600000, 120000);
e.printInfo();
  }
}
