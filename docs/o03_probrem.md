# 課題：給与計算システムを作る

## 目的

継承、抽象クラス、インターフェース、オーバーライド、ポリモーフィズムを使って、複数種類の従業員をまとめて扱う給与計算システムを実装する。

---

## 作成するクラス・インターフェース

以下を作成すること。

- `Employee`
- `RegularEmployee`
- `PartTimeEmployee`
- `Manager`
- `BonusEligible`
- `PayrollService`
- `Main`

---

## 要件

### 1. Employee

従業員の共通部分を表す抽象クラスとする。

#### フィールド

以下のフィールドを持つ。

- `id`
- `name`

フィールドは外部から直接変更できないようにすること。

#### コンストラクタ

`id` と `name` を受け取って初期化する。

- `id` が 0 以下の場合は例外を投げる
- `name` が `null` または空白のみの場合は `"unknown"` とする

#### メソッド

以下のメソッドを持つ。

- `getId`
- `getName`
- `setName`
- `calculateMonthlyPay`
- `getRoleName`
- `printInfo`
- `copy`

従業員の種類によって月給計算と役割名は異なるため、適切に抽象メソッドを使うこと。

`copy` は、その従業員と同じ内容を持つ別インスタンスを返すメソッドとする。

---

### 2. BonusEligible

ボーナス対象者を表すインターフェースとする。

#### メソッド

以下のメソッドを持つ。

- `calculateBonus`

---

### 3. RegularEmployee

正社員を表すクラスとする。

#### 条件

- `Employee` を継承する
- `BonusEligible` を実装する

#### フィールド

以下のフィールドを持つ。

- `baseSalary`
- `overtimeHours`

#### コンストラクタ

`id`, `name`, `baseSalary`, `overtimeHours` を受け取って初期化する。

- `baseSalary` が負の場合は 0 とする
- `overtimeHours` が負の場合は 0 とする

#### 月給

以下の式で計算する。

```text
baseSalary + overtimeHours * 2000
```

#### 役割名

```text
正社員
```

#### ボーナス

以下の式で計算する。

```text
baseSalary / 10
```

---

### 4. PartTimeEmployee

アルバイトを表すクラスとする。

#### 条件

- `Employee` を継承する
- `BonusEligible` は実装しない

#### フィールド

以下のフィールドを持つ。

- `hourlyWage`
- `workingHours`

#### コンストラクタ

`id`, `name`, `hourlyWage`, `workingHours` を受け取って初期化する。

- `hourlyWage` が負の場合は 0 とする
- `workingHours` が負の場合は 0 とする

#### 月給

以下の式で計算する。

```text
hourlyWage * workingHours
```

#### 役割名

```text
アルバイト
```

---

### 5. Manager

管理職を表すクラスとする。

#### 条件

- `Employee` を継承する
- `BonusEligible` を実装する

#### フィールド

以下のフィールドを持つ。

- `baseSalary`
- `managementAllowance`

#### コンストラクタ

`id`, `name`, `baseSalary`, `managementAllowance` を受け取って初期化する。

- `baseSalary` が負の場合は 0 とする
- `managementAllowance` が負の場合は 0 とする

#### 月給

以下の式で計算する。

```text
baseSalary + managementAllowance
```

#### 役割名

```text
管理職
```

#### ボーナス

以下の式で計算する。

```text
baseSalary / 5
```

#### 固有メソッド

以下のメソッドを持つ。

- `holdMeeting`

実行すると、会議を開催する旨のメッセージを出力する。

---

### 6. PayrollService

複数の従業員を扱うクラスとする。

#### フィールド

以下のフィールドを持つ。

- `Employee[] employees`

フィールドは外部から直接変更できないようにすること。

#### コンストラクタ

`Employee[]` を受け取って初期化する。

- 引数が `null` の場合は空配列として扱う
- 配列内の `null` 要素は除外する
- 受け取った従業員をそのまま保持せず、各従業員のコピーを保持する

#### メソッド

以下のメソッドを持つ。

- `totalMonthlyPay`
- `totalBonus`
- `printAll`
- `printBonusTargets`

#### totalMonthlyPay

全従業員の月給合計を返す。

#### totalBonus

ボーナス対象者だけを対象に、ボーナス合計を返す。

#### printAll

全従業員の情報を表示する。

#### printBonusTargets

ボーナス対象者だけについて、名前とボーナス額を表示する。

---

## Mainで確認すること

以下のような従業員配列を作成し、`PayrollService` に渡して動作確認すること。

```java
Employee[] employees = {
    new RegularEmployee(1, "Taro", 300000, 10),
    new PartTimeEmployee(2, "Hanako", 1200, 80),
    new Manager(3, "Sato", 500000, 100000),
    null
};
```

最低限、以下を確認すること。

- 全従業員の情報表示
- 月給合計
- ボーナス合計
- ボーナス対象者の表示
- `Employee` 型の変数に `Manager` インスタンスを代入した場合の挙動
- `Employee` 型の変数から `Manager` 固有メソッドを直接呼べるかどうか

---

## 制約

- `List`、`Stream` は使わないこと
- 配列と `for` 文で実装すること
- 必要に応じて拡張 `for` 文は使ってよい
- フィールドは原則 `private` にすること
- オーバーライドするメソッドには `@Override` を付けること
- `PayrollService` は具象クラスをできるだけ直接判定せず、ポリモーフィズムを活用すること

---

## ヒント

<details>
<summary>ヒント1：Employee はなぜ抽象クラスにするのか</summary>

`Employee` だけでは月給計算の方法が決まらないため、直接インスタンス化する対象ではない。

共通フィールドや共通メソッドは持たせつつ、従業員種別ごとに違う処理は子クラスに任せるとよい。

</details>

<details>
<summary>ヒント2：BonusEligible はなぜ interface にするのか</summary>

ボーナス対象であることは「従業員の種類」ではなく「性質」や「能力」と考えられる。

正社員も管理職もボーナス対象になり得るが、アルバイトは対象外にできる。

</details>

<details>
<summary>ヒント3：PayrollService でコピーを保持する方法</summary>

`Employee` は抽象クラスなので、`new Employee(...)` はできない。

各具象クラスが自分自身のコピー方法を知っているようにすると、`PayrollService` は具体的なクラスを詳しく知らなくてもコピーできる。

</details>

<details>
<summary>ヒント4：ボーナス対象者だけを処理する方法</summary>

`Employee[]` の各要素が `BonusEligible` を実装しているか確認する必要がある。

実装している場合だけ、`BonusEligible` 型として扱えば `calculateBonus` を呼び出せる。

</details>

---

## 考察問題

実装後、以下に答えること。

1. `Employee[]` に `RegularEmployee` や `Manager` を入れられるのはなぜか

2. `Employee e = new Manager(...)` としたとき、`e.calculateMonthlyPay()` はどのクラスの実装が呼ばれるか

3. `Employee e = new Manager(...)` としたとき、`e.holdMeeting()` をそのまま呼べないのはなぜか

4. `BonusEligible` はなぜ抽象クラスではなくインターフェースにした方が自然か

5. `PayrollService` が `Employee[]` を受け取りながら、各具象クラスのコピーを作れるのはなぜか

6. `totalBonus` や `printBonusTargets` で、`instanceof` とキャストが必要になる理由は何か