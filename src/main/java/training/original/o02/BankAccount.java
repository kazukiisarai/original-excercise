package training.original.o02;

public class BankAccount {
  private int id;
  private String ownerName ;
  private int balance;

  public BankAccount(int id,String ownerName, int balance){
    this.id = id;
    this.ownerName = validOwnerName(ownerName);
    this.balance = validBalance(balance);
  }
  public BankAccount(BankAccount other){
    this.id = other.id;
    this.ownerName = other.ownerName;
    this.balance = other.balance;
  }
  

  private String validOwnerName(String ownerName){
    if(ownerName == null||ownerName.isBlank()){
      ownerName = "unknown";
    }
    return ownerName;
  }
  private int validBalance(int balance){
    if(balance<0){
      balance = 0;
    }
    return balance;
  }
  public int getId(){
    return this.id;
  }
  public String getOwnerName(){
    return this.ownerName;
  }
  public int getBalance(){
    return this.balance;
  }
  public void setOwnerName(String ownerName){
    this.ownerName = ownerName;
  }
  public void deposit(int amount){
    if(amount>0){
      this.balance += amount;
    }
    return;
  }
  public boolean withdraw(int amount){
  if(amount>balance||amount<0){
      return false;
    }
  else{
      this.balance -= amount;
      return true;
    }
  }
  public void printInfo(){
    System.out.println("口座ID : "+this.id);
    System.out.println("口座名義人 : "+this.ownerName);
    System.out.println("口座残高 : "+this.balance);
  }
  @Override
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }
    if(obj == null||obj.getClass() != this.getClass()){
      return false;
    }
    BankAccount other = (BankAccount) obj;
    return this.id == other.id;
  }
}
