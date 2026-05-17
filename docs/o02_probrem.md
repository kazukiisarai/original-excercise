# Java 練習問題：BankAccount と BankService

## 概要

銀行の口座情報と、複数の口座を管理・操作するサービスプログラムを作成します。

この問題では、次の点を意識してください。

* カプセル化
* データ保護
* コピーコンストラクタ
* publicメソッドとprivateメソッドの責務分割
* `static` にしてよいメソッドと、インスタンスメソッドにすべきメソッドの区別
* 例外を投げるべき失敗と、戻り値で表すべき失敗の区別

---

## 制約事項

以下は使用しないこと。

* Stream API
* `List` / `ArrayList`
* `record`
* Lombok

配列、`for` 文、`if` 文、通常のクラス定義だけで実装してください。

---

# 1. BankAccount クラスの仕様

銀行口座1つ分の情報を管理するクラスです。  
不正なデータが入らないように設計してください。

---

## 1.1 フィールド

すべて `private` にしてください。

* `id`（整数）：口座ID
* `ownerName`（文字列）：名義人
* `balance`（整数）：残高

---

## 1.2 コンストラクタ

### `BankAccount(int id, String ownerName, int balance)`

引数の値を各フィールドに設定します。

ただし、以下の契約違反が渡された場合は、オブジェクトの生成を拒否し、`IllegalArgumentException` を投げてください。

* `id` が `0` 以下の場合
* `ownerName` が `null`、空文字（`""`）、または空白のみ（`" "`など）の場合
* `balance` が `0` 未満の場合

---

## 1.3 コピーコンストラクタ

### `BankAccount(BankAccount other)`

`other` と同じ内容を持つ、別インスタンスの `BankAccount` を作成してください。

### other が null の場合

今回は、`IllegalArgumentException` を投げてください。

理由は、`BankAccount` の通常コンストラクタでは `id <= 0` や不正な `ownerName` を許さない設計にしているため、`null` からダミー口座を作るより、コピー元が不正であることを明示する方が一貫するからです。

### other が null でない場合

以下の値をコピーしてください。

* `id`
* `ownerName`
* `balance`

`BankAccount` のフィールドは、今回すべて `int` または `String` です。  
`String` は不変オブジェクトとして扱えるため、`Student` の `int[] scores` のような配列コピーは不要です。

---

## 1.4 メソッド

### `getId()`

`id` を返す。

### `getOwnerName()`

`ownerName` を返す。

### `getBalance()`

`balance` を返す。

### `setOwnerName(String ownerName)`

名義人を更新する。  
コンストラクタと同じルールで、不正な文字列は `IllegalArgumentException` で弾く。

### `deposit(int amount)`

口座に入金する。

* `amount` が `0` 以下の場合は、操作として不正であるため `IllegalArgumentException` を投げる。
* 成功した場合は残高を増やす。

### `withdraw(int amount)`

口座から出金する。

* `amount` が `0` 以下の場合は、操作として不正であるため `IllegalArgumentException` を投げる。
* 残高不足の場合は、業務上普通に起こりうる失敗として `false` を返す。
* 成功した場合は残高を減らして `true` を返す。

### `printInfo()`

口座情報を標準出力に表示する。

出力例：

```text
[ID: 101] Name: Taro, Balance: 5000
```

細かい表示形式は多少違ってもよいですが、`id`、`ownerName`、`balance` が確認できるようにしてください。

---

## 1.5 発展：equals / hashCode / toString

余裕があれば、以下も実装してください。

### `equals(Object obj)`

同じ `id` の `BankAccount` を同一口座とみなす。

### `hashCode()`

`equals` をオーバーライドする場合は、`hashCode` もオーバーライドする。

### `toString()`

デバッグしやすい文字列表現を返す。

---

# 2. BankService クラスの仕様

複数の口座をまとめて管理し、送金などの操作を行うサービスクラスです。

---

## 2.1 フィールド

すべて `private` にしてください。

* `accounts`（`BankAccount` の配列）

---

## 2.2 コンストラクタ

### `BankService(BankAccount[] accounts)`

引数の配列を元に初期化します。

以下のルールを守ってください。

* 引数が `null` の場合は、長さ0の配列として扱う。
* 配列内の `null` 要素は除去する。
* 有効な口座データだけを隙間なく詰めた新しい配列を作成して保持する。
* `BankAccount` オブジェクト自体もコピーする。

### データ保護要件

インスタンス生成後、次のものが外部で変更されても、`BankService` 内部の口座データが影響を受けないようにしてください。

* 引数として渡された大元の `BankAccount[]` 配列
* 大元の `BankAccount` オブジェクト

つまり、次のような実装は禁止です。

```java
this.accounts = accounts;
```

また、次のような実装も不十分です。

```java
newAccounts[index] = accounts[i];
```

`BankAccount` のコピーコンストラクタを使って、別インスタンスを作成してください。

---

## 2.3 メソッド

### `findById(int id)`

指定した `id` の口座オブジェクトを返す。  
見つからなければ `null` を返す。

ただし、内部の `BankAccount` をそのまま返してはいけません。  
戻り値のオブジェクト経由で、勝手に内部データが操作されないようにしてください。

つまり、次のような実装は禁止です。

```java
return this.accounts[i];
```

### `transfer(int fromId, int toId, int amount)`

`fromId` の口座から `toId` の口座へ送金する。

以下のいずれかの場合は失敗として `false` を返してください。

* `amount` が `0` 以下の場合
* 送金元が見つからない場合
* 送金先が見つからない場合
* 送金元の残高が不足している場合

成功したら、送金元から引き出し、送金先へ入金して `true` を返してください。

### BankAccount と BankService における `amount <= 0` の扱いの違い

`BankAccount.deposit` や `BankAccount.withdraw` を直接呼ぶ場合、`amount <= 0` はメソッドの前提条件違反として `IllegalArgumentException` を投げます。

一方、`BankService.transfer` は「送金リクエストが成立したかどうか」を返すサービスメソッドとして設計します。  
そのため、この問題では `amount <= 0` の送金リクエストも「送金失敗」として `false` を返す仕様にします。

この設計により、`transfer` は `amount <= 0` のときに内部で `withdraw` や `deposit` を呼ばず、先に `false` を返す必要があります。

### 実装のポイント

`transfer` の操作結果は、`BankService` が管理している内部の本物の口座データに反映されなければなりません。

そのため、`transfer` 内で `findById` を使ってはいけません。  
`findById` はデータ保護のためにコピーを返すメソッドだからです。

内部操作専用に、本物の参照を返す `private` メソッドを作ってください。

例：

```java
private BankAccount findInternalById(int id)
```

このメソッドは外部に公開しないため、内部の本物の `BankAccount` を返してよいです。

### `totalBalance()`

管理している全口座の残高の合計値を返す。

このとき、配列の `index` と口座 `id` を混同しないでください。

悪い例：

```java
for (int i = 0; i < accounts.length; i++) {
    sum += findInternalById(i).getBalance();
}
```

`i` は配列の何番目かを表す値であり、口座IDではありません。  
単純に配列の中身を順番に見て合計してください。

### `printAll()`

全口座の `printInfo()` を順番に呼び出す。

---

# 3. 動作確認用コード

作成したクラスが正しく仕様を満たしているか確認するため、`Main` クラスを作成し、以下の動作を検証してください。

---

## 3.1 正常系のテスト

* 口座を2つ作成し、`BankService` に登録する。
* 片方の口座からもう片方の口座へ、正常に送金処理が行えることを確認する。
* `printAll()` や `totalBalance()` を使って、内部データが正しく更新されたことを確認する。

例：

```java
BankAccount a = new BankAccount(101, "Taro", 1000);
BankAccount b = new BankAccount(102, "Hanako", 500);

BankService service = new BankService(new BankAccount[]{a, b});

boolean result = service.transfer(101, 102, 300);

System.out.println(result); // true
service.printAll();
// 101 の残高が 700、102 の残高が 800 になっていること
```

---

## 3.2 業務上の失敗のテスト

残高を超える金額を送金しようとした際、送金処理が失敗し、両者の残高が変わっていないことを確認してください。

```java
boolean result = service.transfer(101, 102, 100000);

System.out.println(result); // false
service.printAll();
// 直前の残高から変わっていないこと
```

---

## 3.3 契約違反のテスト

`try-catch` 文を使用し、ある口座に対して不正な操作をしたときに、想定通り `IllegalArgumentException` が発生することを確認してください。

例：

```java
try {
    a.deposit(-100);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
```

ほかにも、以下を確認するとよいです。

```java
new BankAccount(0, "Invalid", 1000);
new BankAccount(103, null, 1000);
new BankAccount(104, "   ", 1000);
new BankAccount(105, "Minus", -1);
a.withdraw(0);
```

---

## 3.4 BankService.transfer の `amount <= 0` テスト

`BankAccount.deposit` や `BankAccount.withdraw` では `amount <= 0` は例外ですが、`BankService.transfer` では `false` を返す仕様です。

```java
boolean result = service.transfer(101, 102, -100);

System.out.println(result); // false
```

このとき、例外が発生せず、内部データも変わっていないことを確認してください。

---

## 3.5 データ保護のテスト

### 元の口座オブジェクトを変更しても壊れないこと

```java
BankAccount a = new BankAccount(201, "OriginalA", 1000);
BankAccount b = new BankAccount(202, "OriginalB", 500);

BankAccount[] accounts = {a, b};
BankService service = new BankService(accounts);

a.deposit(9999);
b.withdraw(100);
accounts[0] = new BankAccount(999, "External", 0);

service.printAll();
// service 内部の 201, 202 の口座が、生成時の状態を保っていること
```

### findById で取得した口座を変更しても壊れないこと

```java
BankAccount found = service.findById(201);

if (found != null) {
    found.deposit(9999);
    found.withdraw(100);
    found.setOwnerName("Changed");
}

service.printAll();
// service 内部の口座名義・残高が変わっていないこと
```

---

# 4. 実装後の確認問題

実装後、次の問いに自分の言葉で答えてください。

1. `validOwnerName` や `validBalance` を `static` にしてよい理由は何か。
2. `deposit` や `withdraw` を `static` にしてはいけない理由は何か。
3. `findById` がコピーを返すべき理由は何か。
4. `transfer` が `findById` を使ってはいけない理由は何か。
5. `transfer` のために、内部の本物を返す `private` メソッドを作る理由は何か。
6. `totalBalance` で配列の `index` と口座 `id` を混同すると、なぜ壊れるのか。
7. `withdraw` で、`amount <= 0` は例外にし、残高不足は `false` にする理由は何か。
8. `BankAccount.deposit` では `amount <= 0` を例外にするのに、`BankService.transfer` では `amount <= 0` を `false` にする理由は何か。
9. `equals` をオーバーライドした場合、なぜ `hashCode` もオーバーライドする必要があるのか。

---

# 5. ヒント

<details>
<summary>1. 例外とfalseの使い分けについて</summary>

例外は、主にメソッドの前提条件を破るような契約違反に使います。

例：

```java
account.deposit(-100);
account.withdraw(0);
new BankAccount(0, "Taro", 1000);
```

これらは、操作として不正です。

一方で `false` は、操作の形式自体は正しいが、条件が合わずに実行できなかった場合に使います。

例：

```java
account.withdraw(10000);
```

金額は正しいが、残高が足りない場合は、業務上普通に起こりうる失敗として `false` を返します。

</details>

<details>
<summary>2. staticにしてよいメソッド、してはいけないメソッド</summary>

`static` にしてよいのは、特定のインスタンスの状態に依存しない処理です。

例：

```java
private static int validBalance(int balance)
```

これは、引数 `balance` だけを見て判断できます。  
`this.balance` や `this.accounts` を使いません。

一方、`deposit`、`withdraw`、`transfer` は、特定のインスタンスの状態を読んだり書き換えたりします。

例：

```java
this.balance += amount;
this.balance -= amount;
this.accounts
```

このような処理は `static` にしません。

</details>

<details>
<summary>3. transfer（送金）メソッドの罠</summary>

送金処理では、コピーデータではなく、`BankService` 内部に保存されている本物のデータの残高を更新する必要があります。

もし、送金元の口座を探す際に `findById` を使ってしまうと、`findById` はデータ保護のためにコピーを返す仕様であるため、いくら残高を減らしても本物のデータは更新されません。

外部公開用の `findById` とは別に、内部操作専用の検索メソッドを作ると綺麗に解決できます。

例：

```java
private BankAccount findInternalById(int id)
```

</details>

<details>
<summary>4. 配列のindexとidの違い</summary>

配列の `index` は、配列の何番目に入っているかを表します。

```java
accounts[0]
accounts[1]
accounts[2]
```

一方、口座 `id` は、口座を識別するための番号です。

```java
101
205
999
```

この2つは一致するとは限りません。

したがって、次のように書くと壊れる可能性があります。

```java
for (int i = 0; i < accounts.length; i++) {
    sum += findInternalById(i).getBalance();
}
```

`i` は配列番号であって、口座IDではないからです。

</details>