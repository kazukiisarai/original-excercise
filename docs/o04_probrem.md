# 課題：Student と Grader による成績管理

## 目的

`Student` クラスと `Grader` クラスを実装し、学生ごとの成績を管理するプログラムを作成してください。

この課題では、以下を扱います。

- `HashMap` のキーとして自作クラスを使う
- `equals` / `hashCode` を実装する
- `Map` / `Set` を使ってデータを管理する
- 外部から渡されたオブジェクトによって内部状態が壊れないようにする

---

## 作成するクラス

以下の3クラスを作成してください。

```text
Student
Grader
Main
```

---

## Student クラス

学生を表すクラスです。

### フィールド

以下のフィールドを持たせてください。

```java
private int id;
private String name;
```

---

### コンストラクタ

```java
public Student(int id, String name)
```

以下のルールで初期化してください。

- `id <= 0` の場合は `IllegalArgumentException` を投げる
- `name` が `null` または空白のみの場合は `"unknown"` として扱う

---

### コピーコンストラクタ

```java
public Student(Student other)
```

以下のルールで初期化してください。

- `other == null` の場合は `IllegalArgumentException` を投げる
- `other` と同じ内容を持つ別インスタンスを生成する

---

### メソッド

以下のメソッドを実装してください。

```java
public int getId()
public String getName()
public void setName(String name)
@Override public boolean equals(Object obj)
@Override public int hashCode()
@Override public String toString()
```

---

### 同一判定のルール

`Student` は、`id` が同じなら同じ学生とみなしてください。

つまり、次の2つは同じ学生として扱います。

```java
new Student(1, "Taro")
new Student(1, "Jiro")
```

---

## Grader クラス

学生と点数を管理するクラスです。

### フィールド

以下のフィールドを持たせてください。

```java
private Map<Student, Integer> scores;
```

実装には `HashMap` を使用してください。

---

### コンストラクタ

```java
public Grader()
```

空の成績表を作成してください。

---

### メソッド

以下のメソッドを実装してください。

```java
public void addOrUpdate(Student student, int score)
public boolean remove(Student student)
public int getScore(Student student)
public double average()
public Student topStudent()
public Set<Student> passedStudents(int passScore)
public void printAll()
```

---

## 各メソッドの仕様

### addOrUpdate

```java
public void addOrUpdate(Student student, int score)
```

学生と点数を追加または更新します。

- `student == null` の場合は `IllegalArgumentException` を投げる
- `score < 0` の場合は `0` として扱う
- `score > 100` の場合は `100` として扱う
- すでに同じ学生が登録されている場合は、点数を更新する

---

### remove

```java
public boolean remove(Student student)
```

指定された学生を削除します。

- 削除できた場合は `true`
- 対象が存在しない場合は `false`
- `student == null` の場合は `false`

---

### getScore

```java
public int getScore(Student student)
```

指定された学生の点数を返します。

- 存在しない場合は `-1`
- `student == null` の場合は `-1`

---

### average

```java
public double average()
```

登録されている全学生の平均点を返します。

- 学生が0人の場合は `0.0`

---

### topStudent

```java
public Student topStudent()
```

最も点数が高い学生を返します。

- 学生が0人の場合は `null`
- 同点の場合は、どちらを返してもよい
- 戻り値として返す `Student` は、内部で保持しているインスタンスそのものにしないこと

---

### passedStudents

```java
public Set<Student> passedStudents(int passScore)
```

指定された合格点以上の学生を `Set<Student>` として返します。

- `score >= passScore` の学生を返す
- 実装には `HashSet` を使用する
- 返却する `Set` に入れる `Student` は、内部で保持しているインスタンスそのものにしないこと

---

### printAll

```java
public void printAll()
```

登録されている学生と点数をすべて表示してください。

表示形式は自由ですが、学生番号・氏名・点数が分かるようにしてください。

---

## Main クラスで確認すること

以下のようなデータを使って、各メソッドの動作を確認してください。

```java
Grader grader = new Grader();

Student s1 = new Student(1, "Taro");
Student s2 = new Student(2, "Hanako");
Student s3 = new Student(1, "Jiro");

grader.addOrUpdate(s1, 80);
grader.addOrUpdate(s2, 95);
grader.addOrUpdate(s3, 70);

grader.printAll();

System.out.println("s1 score = " + grader.getScore(s1));
System.out.println("s3 score = " + grader.getScore(s3));
System.out.println("average = " + grader.average());
System.out.println("top = " + grader.topStudent());
System.out.println("passed = " + grader.passedStudents(80));

s1.setName("Changed");
grader.printAll();
```

---

## 制約

- `Student` のフィールドは `private` にしてください
- `Grader` の `scores` フィールドは `private` にしてください
- `Map` の実装には `HashMap` を使用してください
- `Set` の実装には `HashSet` を使用してください
- ラムダ式・Stream API は使用しないでください
- `for` 文または拡張 `for` 文で実装してください

---

## 考察事項

実装後、以下について自分の言葉で説明してください。

1. `Student` を `HashMap` のキーにするために、なぜ `equals` と `hashCode` が重要なのか
2. `new Student(1, "Taro")` と `new Student(1, "Jiro")` が同じキー扱いになる理由
3. `addOrUpdate` で受け取った `Student` をそのまま `Map` に入れると何が問題になりうるか
4. `getScore` で存在しない学生に `-1` を返す設計の良い点と悪い点
5. `HashSet` が重複を除ける仕組み

---

<details>
<summary>ヒント：HashMap のキーについて</summary>

`HashMap` は、キーを探すときに `hashCode` と `equals` を使います。

`equals` で同じとみなすオブジェクトは、`hashCode` も同じ値になるように実装する必要があります。

</details>

<details>
<summary>ヒント：同じキーで put した場合</summary>

`HashMap` に、すでに存在するキーと同じキーを `put` すると、新しい要素が増えるのではなく、対応する値が更新されます。

ただし、キーとして保持されているオブジェクト自体がどうなるかは、実装結果を観察してください。

</details>

<details>
<summary>ヒント：内部状態を守る設計</summary>

外部から渡されたオブジェクトをそのまま内部に保持すると、外部側の変更が内部状態に影響する可能性があります。

必要に応じて、コピーを作って保持する設計を検討してください。

</details>