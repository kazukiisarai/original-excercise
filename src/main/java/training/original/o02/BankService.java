package training.original.o02;

public class BankService {
  private BankAccount[] accounts;

  public  BankService(BankAccount[] accounts){
    if(accounts == null){
      this.accounts = new BankAccount[0];
      return ;
    }
    this.accounts = validAccounts(accounts);
  }

  private static BankAccount[] validAccounts(BankAccount[] accounts){
    if(accounts == null){
      return new BankAccount[0];
    }
    int nonNullCount = 0;
    for(int i=0;i<accounts.length;i++){
      if(accounts[i]!=null){
        nonNullCount++;
      }
    }
    BankAccount[] validAccount = new BankAccount[nonNullCount];
    int index =0;
    for(int i=0;i<accounts.length;i++){
      if(accounts[i]!=null){
        validAccount[index]=new BankAccount(accounts[i]);
        index++;
      }
    }
    return validAccount;
  }
  public BankAccount findById(int id){
    BankAccount account = findId(id);
    if(account == null){
      return null;
    }
    return new BankAccount(account);
  }
  public boolean transfer(int fromId,int toId,int amount){
    if(amount<=0){
      return false;
    }
    BankAccount from = findId(fromId);
    BankAccount to = findId(toId);
    if(from == null || to == null){
      return false;
    }
    if(!from.withdraw(amount)){
      return false;
    }
    
    to.deposit(amount);
    return true;
    
  }
  //transferのためのヘルパーメソッド。受け取ったidが存在したらそのidの口座インスタンスを返す。
  private BankAccount findId(int id){
    for(int i=0;i<this.accounts.length;i++){
      if(this.accounts[i].getId()==id){
        return this.accounts[i];
      }
    }
    return null;
  }
  public int totalBalance(){ 
    int sum = 0; 
    for(int i=0;i<this.accounts.length;i++){ 
      sum += this.accounts[i].getBalance(); 
    } 
    return sum; 
  }
  public void printAll(){
    for(BankAccount account:this.accounts){
      account.printInfo();
    }
  }
}
