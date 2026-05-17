# Java 練習問題：Student と ScoreBook

## 概要

生徒の情報と成績を管理するプログラムを作成します。

この練習では、次の理解を目的にします。

* カプセル化
* `private` フィールド
* getter / setter の役割
* 配列の防御的コピー
* 浅いコピーと深いコピー
* コピーコンストラクタ
* 拡張for文と通常for文の違い
* 配列のindex管理

---

## 制約事項

以下は使用しないこと。

* Stream API
* `List` / `ArrayList`
* `record`
* Lombok

配列、`for` 文、`if` 文、通常のクラス定義だけで実装してください。

---

# 1. Student クラスの仕様

生徒1人分の情報を管理するクラスです。  
適切なアクセス修飾子を考え、カプセル化を意識して設計してください。

---

## 1.1 フィールド

すべて `private` にしてください。

* `id`（整数）：生徒ID
* `name`（文字列）：名前
* `scores`（整数配列）：テストの点数配列

---

## 1.2 コンストラクタ

### `Student(int id, String name, int[] scores)`

引数の値を各フィールドに設定します。  
ただし、以下の補正ルールを適用してください。

### id

* そのまま代入する。

### name

* `null`、空文字（`""`）、または空白のみ（`" "`など）の場合：`"unknown"`
* 20文字より長い場合：先頭20文字だけを保存する
* それ以外：そのまま保存する

### scores

* `null` の場合：長さ0の配列として扱う
* 配列内の各点数：
  * 負の値は `0` に補正する
  * `100` より大きい値は `100` に補正する
  * それ以外はそのまま保存する

### データ保護要件

インスタンス生成後、外部で引数の `scores` 配列が書き換えられても、`Student` 内部の点数が影響を受けないように実装してください。

つまり、次のような実装は禁止です。

```java
this.scores = scores;
```

外部から渡された配列をそのまま持つのではなく、新しい配列を作って中身をコピーしてください。

---

## 1.3 コピーコンストラクタ

### `Student(Student other)`

`other` と同じ内容を持つ、別インスタンスの `Student` を作成してください。

### other が null の場合

以下の値で初期化してください。

* `id = 0`
* `name = "unknown"`
* `scores = new int[0]`

### other が null でない場合

`other` と同じ `id`、`name`、`scores` を持つ新しい `Student` を作ってください。

ただし、`scores` 配列を共有してはいけません。  
`other.scores` と `this.scores` が別配列になるようにしてください。

例えば、次のコードで `s1` を変更しても `s2` は影響を受けないようにします。

```java
Student s1 = new Student(1, "Taro", new int[]{80, 90});
Student s2 = new Student(s1);

s1.setName("Jiro");
s1.setScore(0, 0);

s2.printInfo(); // Taro, 80, 90 のままであること
```

---

## 1.4 メソッド

### `getId()`

`id` を返す。

### `getName()`

`name` を返す。

### `getScores()`

点数配列を返す。

ただし、内部の `scores` 配列をそのまま返してはいけません。  
呼び出し元で戻り値の配列が書き換えられても、`Student` 内部のデータが壊れないようにしてください。

つまり、次のような実装は禁止です。

```java
return this.scores;
```

### `setName(String name)`

コンストラクタと同じルールで名前を補正して更新する。

### `setScore(int index, int score)`

指定された `index` の点数を書き換える。

* `score` はコンストラクタと同じく `0` 〜 `100` の範囲に補正する。
* `index` が範囲外の場合は、例外を投げずに何も処理しない。

### `average()`

点数の平均値を返す。

* 点数データがない場合は `0.0` を返す。

### `printInfo()`

生徒情報を標準出力に表示する。

出力例：

```text
id=1, name=Taro, average=82.5
```

細かい表示形式は多少違ってもよいですが、`id`、`name`、`average` が確認できるようにしてください。

---

# 2. ScoreBook クラスの仕様

複数の生徒を管理する成績表クラスです。

---

## 2.1 フィールド

すべて `private` にしてください。

* `students`（`Student` の配列）

---

## 2.2 コンストラクタ

### `ScoreBook(Student[] students)`

引数の配列を元に初期化します。

以下のルールを守ってください。

* 引数が `null` の場合は、長さ0の配列として扱う。
* 配列内の `null` 要素は除去する。
* 有効な生徒データだけを隙間なく詰めた新しい配列を作成して保持する。
* `Student` オブジェクト自体もコピーする。

### データ保護要件

`ScoreBook` は、登録された生徒情報を完全に独立して管理する必要があります。

インスタンス生成後、次のものが外部で変更されても、`ScoreBook` 内部の生徒データが影響を受けないようにしてください。

* 引数として渡された大元の `Student[]` 配列
* 大元の `Student` オブジェクト
* 大元の `Student` が持っていた `scores` 配列

つまり、次のような実装は禁止です。

```java
this.students = students;
```

また、次のような実装も不十分です。

```java
newStudents[index] = students[i];
```

これは配列だけを新しくしても、中身の `Student` オブジェクトは共有してしまうためです。  
`Student` のコピーコンストラクタを使って、別インスタンスを作成してください。

---

## 2.3 メソッド

### `size()`

登録されている学生数、つまり有効な要素数を返す。

### `getStudent(int index)`

指定した `index` の生徒オブジェクトを返す。

* `index` が範囲外なら `null` を返す。
* 内部の `Student` をそのまま返してはいけない。
* 戻り値のオブジェクト経由で内部データが破壊されないようにする。

つまり、次のような実装は禁止です。

```java
return this.students[index];
```

### `topStudent()`

平均点が最も高い生徒オブジェクトを返す。

* 学生が0人なら `null` を返す。
* 同点の場合は、配列の先頭に近い生徒を優先する。
* `getStudent` と同様に、内部データが破壊されないようにコピーを返す。

### `classAverage()`

全学生の「平均点」の平均を返す。

* 学生が0人なら `0.0` を返す。

例えば、以下の場合：

* Aさんの平均点：80
* Bさんの平均点：90
* Cさんの平均点：70

クラス平均は `80.0` です。

### `printAll()`

全学生の `printInfo()` を順番に呼び出す。

---

# 3. 動作確認用コード

以下のコードを実行し、意図しないデータの書き換え、つまり参照漏れが起きていないか確認してください。

```java
public class Main {
    public static void main(String[] args) {
        // 1. 初期データの準備
        int[] scores = {80, 90};
        Student s1 = new Student(1, "Taro", scores);
        Student[] students = {s1};
        ScoreBook book = new ScoreBook(students);

        // 2. 外部からの意図しない変更テスト
        s1.setName("Jiro");
        s1.setScore(0, 0);
        scores[1] = 0;
        students[0] = new Student(99, "External", new int[]{0, 0});

        // 3. ScoreBookから取得したオブジェクト経由の変更テスト
        Student got = book.getStudent(0);
        if (got != null) {
            got.setName("Saburo");
            got.setScore(1, 0);

            int[] gotScores = got.getScores();
            gotScores[0] = 999;
        }

        // 4. topStudentから取得したオブジェクト経由の変更テスト
        Student top = book.topStudent();
        if (top != null) {
            top.setName("TopChanged");
            top.setScore(0, 0);
        }

        // 5. 結果確認
        System.out.println("--- ScoreBookの内容 ---");
        book.printAll();

        // 【期待される内容】
        // id=1, name=Taro, average=85.0
        //
        // ※Jiro, Saburo, TopChanged, External になっていないこと
        // ※点数が 80, 90 のままであること
    }
}
```

---

# 4. 実装後の確認問題

実装後、次の問いに自分の言葉で答えてください。

1. `Student` のコンストラクタで `this.scores = scores;` としてはいけない理由は何か。
2. `getScores()` で `return this.scores;` としてはいけない理由は何か。
3. `ScoreBook` のコンストラクタで `newStudents[index] = students[i];` としてはいけない理由は何か。
4. `getStudent()` で `return this.students[index];` としてはいけない理由は何か。
5. コピーコンストラクタでは、なぜ別の `Student` を作るだけでなく、生成中の `this` のフィールドを初期化する必要があるのか。
6. 配列の中身を書き換えたいとき、拡張for文の `for (int score : scores)` で `score = 0;` としても元配列が変わらない理由は何か。
7. `i` と `index` のように、元配列を見るための添字と、新しい配列に詰めるための添字を分ける必要があるのはなぜか。

---

# 5. ヒント

<details>
<summary>1. アクセス修飾子について</summary>

外部から勝手にデータを書き換えられないようにするには、フィールドを隠し、メソッド経由でしかアクセスできないようにするのが基本です。

つまり、フィールドは原則として `private` にし、必要な操作だけをメソッドとして公開します。

これはカプセル化と呼ばれる考え方です。

</details>

<details>
<summary>2. 防御的コピーについて</summary>

配列は参照型です。

そのため、例えば次のようにそのまま代入すると、外部の配列と内部の配列が同じ中身を共有してしまいます。

```java
this.scores = scores;
```

この状態では、コンストラクタに渡した元の配列が外部で書き換えられると、`Student` 内部の点数も変わってしまいます。

これを防ぐには、新しい配列を `new` して、中身を一つずつ移し替える必要があります。

このようなコピーを防御的コピーと呼びます。

</details>

<details>
<summary>3. 浅いコピーと深いコピーについて</summary>

`Student` も参照型です。

そのため、`ScoreBook` の配列の中に元の `Student` オブジェクトをそのまま入れると、外部からその `Student` の名前や点数を変更されたときに、`ScoreBook` 内部のデータも影響を受けてしまいます。

これは、配列だけを新しくしても、中身の `Student` オブジェクトまではコピーできていないためです。

このようなコピーは浅いコピーと呼ばれます。

これを防ぐには、元のオブジェクトと同じ値を持つ「新しい別の `Student` インスタンス」を `new` して作る必要があります。

このようなコピーは深いコピー、またはディープコピーと呼ばれます。

`Student` クラスにコピー専用のコンストラクタを作ると、実装が綺麗になります。

```java
public Student(Student other) {
    // other の id, name, scores を使って
    // this の各フィールドを初期化する
}
```

</details>