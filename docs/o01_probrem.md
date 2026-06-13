# Java 練習問題：Student と ScoreBook

## 概要

生徒の情報と成績を管理するプログラムを作成してください。

この問題では、1人の生徒を表す `Student` クラスと、複数の生徒を管理する `ScoreBook` クラスを実装します。

外部から渡された配列やオブジェクトがあとから変更されても、クラス内部の状態が意図せず変わらないように設計してください。

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

# 1. Student クラス

## 1.1 役割

`Student` は、生徒1人分の情報を表すクラスです。

保持する情報は以下の通りです。

```text
生徒ID
名前
テストの点数配列
```

外部からフィールドを直接変更できないように設計してください。

---

## 1.2 生成時のルール

`Student` を生成するとき、以下の情報を受け取ります。

```text
id
name
scores
```

それぞれ次のルールで扱ってください。

## id

```text
そのまま保存する。
```

## name

```text
null、空文字、空白のみの場合:
  "unknown"

20文字より長い場合:
  先頭20文字だけを保存する

それ以外:
  そのまま保存する
```

## scores

```text
scores が null の場合:
  長さ0の配列として扱う

各点数が負の場合:
  0 に補正する

各点数が 100 より大きい場合:
  100 に補正する

それ以外:
  そのまま保存する
```

## 外部配列からの独立

`Student` を生成したあと、コンストラクタに渡した元の `scores` 配列が外部で変更されても、`Student` 内部の点数が変わらないようにしてください。

---

## 1.3 コピー用の生成処理

`Student` には、別の `Student` と同じ内容を持つ新しい `Student` を作る処理を用意してください。

```text
コピー元が null の場合:
  id = 0
  name = "unknown"
  scores = 長さ0の配列

コピー元が null でない場合:
  id, name, scores の内容を引き継ぐ
```

ただし、コピー元とコピー先で点数配列を共有してはいけません。

以下のように、一方を変更しても他方に影響しないようにしてください。

```java
Student s1 = new Student(1, "Taro", new int[]{80, 90});
Student s2 = new Student(s1);

s1.setName("Jiro");
s1.setScore(0, 0);

s2.printInfo(); // Taro, 80, 90 のままであること
```

---

## 1.4 提供する操作

`Student` では、以下の操作を提供してください。

```text
生徒IDを取得する
名前を取得する
点数配列を取得する
名前を変更する
指定した位置の点数を変更する
平均点を取得する
生徒情報を表示する
```

## 点数配列の取得

点数配列を取得した呼び出し元が、その戻り値の配列を書き換えても、`Student` 内部の点数が変わらないようにしてください。

## 名前の変更

名前を変更するときも、生成時と同じ名前補正ルールを適用してください。

## 点数の変更

指定した位置の点数を書き換えます。

```text
score < 0 の場合:
  0 に補正する

score > 100 の場合:
  100 に補正する

index が範囲外の場合:
  何もしない
```

## 平均点

点数の平均値を返してください。

```text
点数データがない場合:
  0.0
```

## 表示

生徒情報を標準出力に表示してください。

表示形式は自由ですが、少なくとも以下が確認できるようにしてください。

```text
id
name
average
```

出力例：

```text
id=1, name=Taro, average=82.5
```

---

# 2. ScoreBook クラス

## 2.1 役割

`ScoreBook` は、複数の `Student` を管理する成績表クラスです。

保持する情報は以下の通りです。

```text
Student の配列
```

外部からフィールドを直接変更できないように設計してください。

---

## 2.2 生成時のルール

`ScoreBook` を生成するとき、`Student` 配列を受け取ります。

以下のルールで内部に保存してください。

```text
引数が null の場合:
  長さ0の配列として扱う

配列内に null 要素がある場合:
  除外する

有効な Student だけを、隙間なく詰めた配列として保持する
```

## 外部データからの独立

`ScoreBook` を生成したあと、以下が外部で変更されても、`ScoreBook` 内部の生徒データが影響を受けないようにしてください。

```text
引数として渡された Student[] 配列
配列内に入っていた Student オブジェクト
その Student が持っていた scores 配列
```

---

## 2.3 提供する操作

`ScoreBook` では、以下の操作を提供してください。

```text
登録されている学生数を取得する
指定した位置の学生を取得する
平均点が最も高い学生を取得する
クラス平均を取得する
全学生の情報を表示する
```

## 登録学生数

有効な学生数を返してください。

## 指定位置の学生取得

指定した位置の学生を返してください。

```text
index が範囲外の場合:
  null を返す
```

返した学生オブジェクト経由で、`ScoreBook` 内部のデータが変更されないようにしてください。

## トップ学生取得

平均点が最も高い学生を返してください。

```text
学生が0人の場合:
  null を返す

同点の場合:
  配列の先頭に近い学生を優先する
```

返した学生オブジェクト経由で、`ScoreBook` 内部のデータが変更されないようにしてください。

## クラス平均

全学生の「平均点」の平均を返してください。

```text
学生が0人の場合:
  0.0
```

例：

```text
Aさんの平均点: 80
Bさんの平均点: 90
Cさんの平均点: 70

クラス平均:
  80.0
```

## 全件表示

登録されている全学生の情報を順番に表示してください。

---

# 3. 動作確認

以下のような観点で動作確認してください。

```java
public class Main {
    public static void main(String[] args) {
        int[] scores = {80, 90};
        Student s1 = new Student(1, "Taro", scores);
        Student[] students = {s1};

        ScoreBook book = new ScoreBook(students);

        // 元の配列・元のStudent・元のscoresを変更する
        s1.setName("Jiro");
        s1.setScore(0, 0);
        scores[1] = 0;
        students[0] = new Student(99, "External", new int[]{0, 0});

        // ScoreBookから取得したStudentを変更する
        Student got = book.getStudent(0);
        if (got != null) {
            got.setName("Saburo");
            got.setScore(1, 0);

            int[] gotScores = got.getScores();
            gotScores[0] = 999;
        }

        // topStudentから取得したStudentを変更する
        Student top = book.topStudent();
        if (top != null) {
            top.setName("TopChanged");
            top.setScore(0, 0);
        }

        System.out.println("--- ScoreBookの内容 ---");
        book.printAll();

        // 期待される内容:
        // id=1, name=Taro, average=85.0
        //
        // 次の名前になっていないこと:
        // Jiro
        // Saburo
        // TopChanged
        // External
        //
        // 点数が 80, 90 のままであること。
    }
}
```

---

# 4. 境界条件の確認

以下も確認してください。

```text
Student の scores が null
Student の scores が空配列
Student の scores に負の点数がある
Student の scores に 100 より大きい点数がある
Student の name が null
Student の name が空文字
Student の name が空白のみ
Student の name が20文字を超える
ScoreBook に null 配列を渡す
ScoreBook に null 要素を含む配列を渡す
ScoreBook が空のときに topStudent / classAverage を呼ぶ
```

---

# 5. 実装後の確認事項

実装後、次の観点を説明できるようにしてください。

```text
1. Student が外部から渡された scores 配列の影響を受けない理由
2. getScores で取得した配列を書き換えても、Student 内部の点数が壊れない理由
3. ScoreBook が外部の Student[] や Student オブジェクトの変更に影響されない理由
4. getStudent や topStudent で返した Student を変更しても、ScoreBook 内部が壊れない理由
5. コピー用の生成処理で、生成中のインスタンス自身を初期化する必要がある理由
6. 拡張for文で受け取った変数を書き換えても、元配列の要素が変わらない理由
7. null除去などで、元配列を走査する添字と、新しい配列に詰める添字を分ける必要がある理由
```

---

# 6. ヒント

必要になった場合だけ開いてください。

<details>
<summary>外部配列から独立させる考え方</summary>

配列は参照型です。

そのため、外部から受け取った配列をそのままフィールドに保存すると、外部と内部で同じ配列を共有することになります。

この状態では、外部で配列の中身を書き換えると、クラス内部の状態も変わってしまいます。

これを避けるには、内部用の新しい配列を作り、中身をコピーして保存します。

</details>

<details>
<summary>Student オブジェクト自体を共有しない考え方</summary>

`Student[]` の配列だけを新しく作っても、中に入っている `Student` オブジェクトをそのまま入れると、外部と内部で同じ `Student` を共有します。

その場合、外部からその `Student` の名前や点数を変更されると、内部データも変わります。

`ScoreBook` が独立して生徒データを管理するには、各 `Student` について新しい別インスタンスを作る必要があります。

</details>

<details>
<summary>拡張for文で配列要素を書き換えられない理由</summary>

拡張for文の変数は、配列要素そのものではなく、各要素の値を一時的に受け取るローカル変数です。

例えば次のように書いても、変更されるのは `score` というローカル変数だけです。

```java
for (int score : scores) {
    score = 0;
}
```

配列の要素そのものを書き換えるには、index を使ってアクセスする必要があります。

</details>