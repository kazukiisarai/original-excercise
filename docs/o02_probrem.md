# Java 練習問題：BankAccount と BankService

## 概要

銀行口座と、複数の口座を管理するサービスを作成してください。

この問題では、1つの口座を表す `BankAccount` クラスと、複数の口座を管理して送金処理などを行う `BankService` クラスを実装します。

`BankAccount` 単体での操作と、`BankService` 経由での操作では、失敗の扱いが異なる場面があります。  
それぞれの責務を分けて設計してください。

---

## 制約事項

以下は使用しないこと。

```text
Stream API
List / ArrayList
record
Lombok
```

配列、`for` 文、`if` 文、通常のクラス定義を使って実装してください。

---

# 1. BankAccount クラス

## 1.1 役割

`BankAccount` は、銀行口座1つ分の情報を表すクラスです。

保持する情報は以下の通りです。

```text
口座ID
名義人
残高
```

外部からフィールドを直接変更できないように設計してください。

---

## 1.2 生成時のルール

`BankAccount` を生成するとき、以下の情報を受け取ります。

```text
id
ownerName
balance
```

それぞれ次のルールで扱ってください。

## id

```text
0以下の場合:
  IllegalArgumentException
```

## ownerName

```text
null、空文字、空白のみの場合:
  IllegalArgumentException
```

## balance

```text
0未満の場合:
  IllegalArgumentException
```

不正な値から口座を作成しないでください。

---

## 1.3 コピー用の生成処理

`BankAccount` には、別の `BankAccount` と同じ内容を持つ新しい `BankAccount` を作る処理を用意してください。

```text
コピー元が null の場合:
  IllegalArgumentException

コピー元が null でない場合:
  id, ownerName, balance の内容を引き継ぐ
```

`BankAccount` が保持する値は、この問題では `int` と `String` のみです。

---

## 1.4 提供する操作

`BankAccount` では、以下の操作を提供してください。

```text
口座IDを取得する
名義人を取得する
残高を取得する
名義人を変更する
入金する
出金する
口座情報を表示する
```

## 名義人の変更

生成時と同じ名義人ルールを適用してください。

```text
null、空文字、空白のみの場合:
  IllegalArgumentException
```

## 入金

指定された金額を残高に加算します。

```text
amount <= 0 の場合:
  IllegalArgumentException

成功した場合:
  残高を増やす
```

## 出金

指定された金額を残高から減算します。

```text
amount <= 0 の場合:
  IllegalArgumentException

残高不足の場合:
  false

成功した場合:
  残高を減らして true
```

## 表示

口座情報を標準出力に表示してください。

表示形式は自由ですが、少なくとも以下が確認できるようにしてください。

```text
id
ownerName
balance
```

出力例：

```text
[ID: 101] Name: Taro, Balance: 5000
```

---

## 1.5 同一口座の扱い

同じ `id` を持つ `BankAccount` は、同一の口座として扱ってください。

余裕があれば、デバッグしやすい文字列表現も用意してください。

---

# 2. BankService クラス

## 2.1 役割

`BankService` は、複数の `BankAccount` を管理し、検索・送金・合計残高の計算を行うサービスクラスです。

保持する情報は以下の通りです。

```text
BankAccount の配列
```

外部からフィールドを直接変更できないように設計してください。

---

## 2.2 生成時のルール

`BankService` を生成するとき、`BankAccount` 配列を受け取ります。

以下のルールで内部に保存してください。

```text
引数が null の場合:
  長さ0の配列として扱う

配列内に null 要素がある場合:
  除外する

有効な BankAccount だけを、隙間なく詰めた配列として保持する
```

## 外部データからの独立

`BankService` を生成したあと、以下が外部で変更されても、`BankService` 内部の口座データが影響を受けないようにしてください。

```text
引数として渡された BankAccount[] 配列
配列内に入っていた BankAccount オブジェクト
```

---

## 2.3 提供する操作

`BankService` では、以下の操作を提供してください。

```text
指定したIDの口座を取得する
指定したID間で送金する
管理している全口座の残高合計を取得する
全口座の情報を表示する
```

---

## 指定IDの口座取得

指定した `id` の口座を返してください。

```text
見つからない場合:
  null
```

返した口座オブジェクト経由で、`BankService` 内部の口座データが変更されないようにしてください。

---

## 送金

`fromId` の口座から `toId` の口座へ送金します。

以下の場合は、送金失敗として `false` を返してください。

```text
amount <= 0
送金元が見つからない
送金先が見つからない
送金元の残高が不足している
```

成功した場合は、送金元の残高を減らし、送金先の残高を増やして `true` を返してください。

## BankAccount と BankService における失敗の扱い

`BankAccount` の `deposit` / `withdraw` では、`amount <= 0` は口座操作として不正なので例外にします。

一方、`BankService` の送金処理では、送金リクエスト全体が成立したかどうかを返す設計にします。  
そのため、この問題では `amount <= 0` の送金リクエストは例外ではなく `false` として扱います。

送金処理では、無効な金額で `deposit` や `withdraw` を呼ばないようにしてください。

## 内部データの更新

送金処理では、`BankService` が管理している内部の口座データを実際に変更してください。

外部公開用の口座取得処理がコピーを返す設計の場合、その処理を送金内部で使うと、本物の残高が更新されません。  
外部公開用の取得処理と、内部更新用の検索処理は役割を分けてください。

---

## 残高合計

管理している全口座の残高合計を返してください。

配列の位置番号と口座IDは別物として扱ってください。

---

## 全件表示

管理している全口座の情報を順番に表示してください。

---

# 3. 動作確認

`Main` クラスを作成し、以下の観点で動作確認してください。

---

## 3.1 正常な送金

```java
BankAccount a = new BankAccount(101, "Taro", 1000);
BankAccount b = new BankAccount(102, "Hanako", 500);

BankService service = new BankService(new BankAccount[]{a, b});

boolean result = service.transfer(101, 102, 300);

System.out.println(result); // true
service.printAll();
```

確認すること。

```text
101 の残高が 700
102 の残高が 800
totalBalance も整合している
```

---

## 3.2 残高不足

残高を超える金額を送金しようとした場合、送金に失敗し、残高が変わらないことを確認してください。

```java
boolean result = service.transfer(101, 102, 100000);

System.out.println(result); // false
service.printAll();
```

---

## 3.3 不正な口座生成・口座操作

`try-catch` を使い、不正な値を渡した場合に想定通り例外になることを確認してください。

確認例：

```java
new BankAccount(0, "Invalid", 1000);
new BankAccount(103, null, 1000);
new BankAccount(104, "   ", 1000);
new BankAccount(105, "Minus", -1);

a.deposit(-100);
a.withdraw(0);
```

---

## 3.4 BankService.transfer の不正金額

`BankAccount` 単体の入出金では `amount <= 0` は例外ですが、`BankService.transfer` では `false` を返す仕様です。

```java
boolean result = service.transfer(101, 102, -100);

System.out.println(result); // false
```

確認すること。

```text
例外が発生しない
内部データが変わらない
```

---

## 3.5 外部データからの独立

以下のような操作をしても、`BankService` 内部の口座データが壊れないことを確認してください。

```java
BankAccount a = new BankAccount(201, "OriginalA", 1000);
BankAccount b = new BankAccount(202, "OriginalB", 500);

BankAccount[] accounts = {a, b};
BankService service = new BankService(accounts);

a.deposit(9999);
b.withdraw(100);
accounts[0] = new BankAccount(999, "External", 0);

service.printAll();
```

確認すること。

```text
service 内部の 201, 202 の口座が、生成時の状態を保っている
```

---

## 3.6 取得した口座経由の変更

```java
BankAccount found = service.findById(201);

if (found != null) {
    found.deposit(9999);
    found.withdraw(100);
    found.setOwnerName("Changed");
}

service.printAll();
```

確認すること。

```text
service 内部の口座名義・残高が変わっていない
```

---

# 4. 境界条件の確認

以下も確認してください。

```text
BankService に null 配列を渡す
BankService に null 要素を含む配列を渡す
存在しない fromId で transfer する
存在しない toId で transfer する
amount が 0 の transfer を呼ぶ
amount が負の transfer を呼ぶ
登録口座が0件の状態で totalBalance を呼ぶ
登録口座が0件の状態で printAll を呼ぶ
```

---

# 5. 実装後の確認事項

実装後、次の観点を説明できるようにしてください。

```text
1. 補正ではなく例外にした入力は何か。その理由。
2. false で表した失敗は何か。その理由。
3. 外部に返す口座と、送金で更新する口座を分ける必要がある理由。
4. BankService が、外部から渡された配列や口座オブジェクトに影響されない理由。
5. 残高合計で、配列の位置番号と口座IDを混同してはいけない理由。
6. 同じIDの口座を同一口座として扱うために必要な設計。
7. インスタンスの状態を使う処理と、引数だけで完結する処理の違い。
```

---

# 6. ヒント

必要になった場合だけ開いてください。

<details>
<summary>例外とfalseの使い分け</summary>

例外は、メソッドの前提条件を破るような入力に使います。

例：

```java
account.deposit(-100);
account.withdraw(0);
new BankAccount(0, "Taro", 1000);
```

一方で、`false` は、操作の形式自体は正しいが、条件が合わずに実行できなかった場合に使います。

例：

```java
account.withdraw(10000);
```

金額は正しいが残高が足りない場合は、業務上起こりうる失敗として `false` を返します。

</details>

<details>
<summary>外部公開用の取得処理と内部更新用の検索処理</summary>

外部公開用の取得処理では、呼び出し元が内部データを直接変更できないようにする必要があります。

一方で、送金処理では `BankService` 内部で管理している本物の口座残高を更新する必要があります。

そのため、外部公開用の取得処理と、内部更新用の検索処理は役割を分けると実装しやすくなります。

</details>

<details>
<summary>配列の位置番号と口座ID</summary>

配列の位置番号は、配列の何番目に入っているかを表します。

```java
accounts[0]
accounts[1]
accounts[2]
```

口座IDは、口座を識別する番号です。

```text
101
205
999
```

この2つは一致するとは限りません。

</details>