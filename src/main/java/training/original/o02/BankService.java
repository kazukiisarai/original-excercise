package training.original.o02;

public class BankService {
  private BankAccount[] accounts;

  public  BankService(BankAccount[] accounts){
    if(accounts == null){
      this.accounts = new BankAccount[0];
    }
    this.accounts = validAccounts(accounts);
  }

  private BankAccount[] validAccounts(BankAccount[] accounts){
    int nonNullCount = 0;
    for(int i=0;i<accounts.length;i++){
      if(accounts[i]!=null){
        nonNullCount++;
      }
    }
    BankAccount[] validAccount = new BankAccount[nonNullCount];
    int index =0;
    for(int i=0;i<validAccount.length;i++){
      if(accounts[i]!=null){
        validAccount[index]=accounts[i];
        index++;
      }
    }
    return validAccount;
  }
  public BankAccount findById(int id){
    for(int i=0;i<this.accounts.length;i++){
      if(this.accounts[i].getId()==id){
        return new BankAccount(this.accounts[i]);
      }
    }
    return null;
  }
  public boolean transfar(int fromId,int toId,int amount){
    if(amount<=0){
      return false;
    }
    if(findIdCount(fromId)==0){
      return false;
    }
    if(findIdCount(toId)==0){
      return false;
    }
    if(amount>findId(fromId).getBalance()){
      return false;
    }
    findId(fromId).withdraw(amount);
    findId(toId).deposit(amount);
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
  //transferのためのヘルパーメソッド。受け取ったidが見つかればカウントする。
  private int findIdCount(int id){
    int findFromIdCount = 0;
    for(int i=0;i<this.accounts.length;i++){
      if(this.accounts[i].getId()==id){
        findFromIdCount++;
      }
    }
    return findFromIdCount;
  }
  public int totalBalance(){
    int sum = 0;
    for(int i=0;i<this.accounts.length;i++){
      sum += findId(i).getBalance();
    }
    return sum;
  }
  //久々に拡張for文の練習もしときたかったのであえて使いましたが、for(int i=0;i<this.accounts;i++){System.out.println("口座リスト"+i+"番目のデータ");this.accounts[i].printInfo();}のほうが見やすいかなと思いながら書いてます。
  public void printAll(){
    for(BankAccount account:this.accounts){
      account.printInfo();
    }
  }
}
