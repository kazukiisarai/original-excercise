package training.original.o02;
import java.util.Objects;
public class BankAccount {
  private int id;
  private String ownerName ;
  private int balance;

  public BankAccount(int id,String ownerName, int balance){
    if(id<=0){
      throw new IllegalArgumentException("IDは正の整数です。");
    }
    this.id = id;
    this.ownerName = validOwnerName(ownerName);
    this.balance = validBalance(balance);
  }
  public BankAccount(BankAccount other){
    if(other == null){
      throw new IllegalArgumentException("コピー元のインスタンスがnullです");
    }
    this.id = other.id;
    this.ownerName = other.ownerName;
    this.balance = other.balance;
  }
  

  private static String validOwnerName(String ownerName){
    if(ownerName == null||ownerName.isBlank()){
      throw new IllegalArgumentException("名前はnullまたは空白を認めません");
    }
    return ownerName;
  }
  private static int validBalance(int balance){
    if(balance<0){
      throw new IllegalArgumentException("残高は0以上の整数です");
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
    this.ownerName = validOwnerName(ownerName);
  }
  public void deposit(int amount){
    if(amount<=0){
      throw new IllegalArgumentException("金額は正の整数です");
    }
    this.balance += amount;
    return;
  }
  public boolean withdraw(int amount){
  if(amount<=0){
      throw new IllegalArgumentException("金額は正の整数です");
    }
  if(amount>balance){
      return false;
    }
      this.balance -= amount;
      return true;
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
  @Override
  public int hashCode(){
    return Objects.hash(id);
  }
  @Override
  public String toString(){
    return "口座{id:"+this.id+"名義人"+this.ownerName+"残高"+this.balance+"}";
  }
}
