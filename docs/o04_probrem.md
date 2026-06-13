# Java 練習問題：Student と Grader による成績管理

## 概要

学生ごとの成績を管理するプログラムを作成してください。

この問題では、学生を表す `Student` クラスと、学生ごとの点数を管理する `Grader` クラスを実装します。

`Student` は成績表のキーとして扱います。  
そのため、「同じ学生である」とはどういうことかをクラス側で定義し、その前提で `Map` や `Set` を正しく扱えるようにしてください。

---

## パッケージ

すべてのクラスは次のパッケージに配置すること。

```java
package training.original.o04;
```

---

## 作成するクラス

以下のクラスを作成してください。

```text
Student
Grader
Main
```

---

## 制約事項

以下は使用しないこと。

```text
Stream API
ラムダ式
record
Lombok
```

`Map`、`Set`、`HashMap`、`HashSet` は使用してください。  
繰り返し処理は `for` 文または拡張for文で実装してください。

---

# 1. Student クラス

## 1.1 役割

`Student` は、学生1人分の情報を表すクラスです。

保持する情報は以下の通りです。

```text
学生番号
名前
```

外部からフィールドを直接変更できないようにしてください。

---

## 1.2 生成時のルール

`Student` を生成するとき、以下の情報を受け取ります。

```text
id
name
```

それぞれ次のルールで扱ってください。

## id

```text
0以下の場合:
  IllegalArgumentException
```

## name

```text
null または空白のみの場合:
  "unknown"
```

---

## 1.3 コピー用の生成処理

`Student` には、別の `Student` と同じ内容を持つ新しい `Student` を作る処理を用意してください。

```text
コピー元が null の場合:
  IllegalArgumentException

コピー元が null でない場合:
  id と name の内容を引き継ぐ
```

---

## 1.4 同一学生の扱い

`id` が同じ `Student` は、名前が異なっていても同一学生として扱ってください。

例：

```java
new Student(1, "Taro")
new Student(1, "Jiro")
```

上記2つは、同じ学生として扱います。

この同一性は、`Map` のキーや `Set` の要素として扱う場合にも反映されるようにしてください。

---

## 1.5 提供する操作

`Student` では、以下の操作を提供してください。

```text
学生番号を取得する
名前を取得する
名前を変更する
文字列表現を返す
```

## 名前の変更

名前を変更するときも、生成時と同じ名前補正ルールを適用してください。

---

# 2. Grader クラス

## 2.1 役割

`Grader` は、学生と点数の対応を管理する成績表クラスです。

内部では、学生をキー、点数を値として管理してください。

```text
Student -> score
```

実装には `HashMap` を使用してください。

---

## 2.2 生成時のルール

`Grader` を生成するとき、空の成績表を作成してください。

---

## 2.3 提供する操作

`Grader` では、以下の操作を提供してください。

```text
学生と点数を追加または更新する
学生を削除する
学生の点数を取得する
平均点を取得する
最高点の学生を取得する
合格点以上の学生集合を取得する
登録内容をすべて表示する
```

---

## 学生と点数の追加・更新

学生と点数を登録します。  
すでに同じ学生が登録されている場合は、点数を更新してください。

```text
student == null の場合:
  IllegalArgumentException

score < 0 の場合:
  0 として扱う

score > 100 の場合:
  100 として扱う
```

登録時、外部から渡された `Student` オブジェクトをあとから変更しても、`Grader` 内部のキー情報が影響を受けないようにしてください。

---

## 学生の削除

指定された学生を削除してください。

```text
削除できた場合:
  true

対象が存在しない場合:
  false

student == null の場合:
  false
```

---

## 点数の取得

指定された学生の点数を返してください。

```text
存在する場合:
  登録されている点数

存在しない場合:
  -1

student == null の場合:
  -1
```

---

## 平均点

登録されている全学生の平均点を返してください。

```text
学生が0人の場合:
  0.0
```

---

## 最高点の学生

最も点数が高い学生を返してください。

```text
学生が0人の場合:
  null

同点の場合:
  どちらを返してもよい
```

返した `Student` オブジェクト経由で、`Grader` 内部の状態が変更されないようにしてください。

---

## 合格点以上の学生集合

指定された合格点以上の学生を `Set<Student>` として返してください。

```text
score >= passScore
```

実装には `HashSet` を使用してください。

返却する `Set` に入れる `Student` は、`Grader` 内部で保持しているインスタンスそのものにしないでください。

---

## 全件表示

登録されている学生と点数をすべて表示してください。

表示形式は自由ですが、少なくとも以下が確認できるようにしてください。

```text
学生番号
名前
点数
```

---

# 3. 動作確認

`Main` クラスを作成し、以下のようなデータで動作確認してください。

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

確認すること。

```text
s1 と s3 は同じ学生として扱われる
id=1 の点数は後から登録した 70 に更新される
getScore(s1) と getScore(s3) は同じ点数を返す
s1.setName("Changed") の後も、Grader内部の表示が意図せず変わらない
```

なお、同じ学生番号で点数を更新した場合、キーとして保持されている学生名が最初に登録した名前のままになるか、後から登録した名前に置き換わるかは設計によって異なります。  
どちらの設計にしたかを説明できるようにしてください。

---

# 4. 境界条件の確認

以下も確認してください。

```text
Student の id が 0 以下
Student の name が null
Student の name が空白のみ
Student のコピー元が null
addOrUpdate に null の Student を渡す
addOrUpdate に負の点数を渡す
addOrUpdate に 100 より大きい点数を渡す
remove に null を渡す
remove に未登録の Student を渡す
getScore に null を渡す
getScore に未登録の Student を渡す
学生が0人の状態で average を呼ぶ
学生が0人の状態で topStudent を呼ぶ
合格者が0人の状態で passedStudents を呼ぶ
```

---

# 5. 実装後の確認事項

実装後、次の観点を説明できるようにしてください。

```text
1. Student の同一性をどの値で判断する設計にしたか。
2. 同じ学生番号で名前が異なる Student を登録したとき、なぜ同じ学生として扱われるのか。
3. addOrUpdate で受け取った Student をそのまま内部に保持すると、どのような問題が起こりうるか。
4. 同じ学生番号の Student を再登録したとき、点数と名前がそれぞれどう扱われる設計にしたか。
5. getScore で未登録の場合に -1 を返す設計の利点と欠点。
6. passedStudents で返した Set や、その中の Student を外部で操作した場合、Grader 内部に影響するか。
7. Map と Set が、Student の同一性をどのように利用しているか。
```

---

# 6. ヒント

必要になった場合だけ開いてください。

<details>
<summary>自作クラスをMapのキーとして使うとき</summary>

`Map` は、キーが同じかどうかを判断して値を登録・取得します。

自作クラスをキーにする場合、そのクラスにおける「同じ」とは何かを正しく定義する必要があります。

今回であれば、学生番号が同じなら同じ学生として扱う設計です。

</details>

<details>
<summary>同じキーでputした場合</summary>

`Map` に、すでに存在するキーと同じキーを登録すると、新しい要素が増えるのではなく、対応する値が更新されます。

ただし、キーとして保持されているオブジェクト自体を差し替えるかどうかは、実装の仕方によって変わります。

例えば、いったん削除してから登録し直す設計にすると、キー側の情報も新しくできます。

</details>

<details>
<summary>内部状態を守る考え方</summary>

外部から渡されたオブジェクトをそのまま内部に保持すると、外部側でそのオブジェクトが変更されたときに、内部状態も影響を受ける可能性があります。

内部で独立して管理したい場合は、受け取ったオブジェクトと同じ内容を持つ別インスタンスを作って保持します。

</details>