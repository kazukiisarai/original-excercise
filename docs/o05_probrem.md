# 大問 o05：StudentScore を題材にした関数型インターフェース・Stream・Collectors総合演習

## パッケージ

すべてのクラス・インターフェースは次のパッケージに配置すること。

```java
package training.original.o05;
```

---

## 最終ファイル構成

以下のクラス・インターフェースを作成する。

```text
StudentScore
ScoreComparators
ScoreCondition
ScoreFormatter
StudentScoreUtils
StudentScoreStandardUtils
StudentScorePredicates
StudentScoreFormatters
StudentScoreStreamUtils
StudentScoreStreamAdvanced
StudentScoreStatistics
StudentScoreCollectorUtils
StudentScoreGroupingUtils
StudentScoreReportService

Main
StreamMain
StreamAdvancedMain
StatisticsMain
CollectorMain
GroupingMain
ReportMain
```

---

## この大問の狙い

`StudentScore` を題材にして、以下の内容を段階的に扱う。

```text
Comparator
ラムダ式
自作関数型インターフェース
Predicate / Function / Consumer
メソッド参照
Stream基本操作
sorted / max / Optional
mapToInt / OptionalInt / OptionalDouble
collect / Collectors.toList / toSet / toMap
groupingBy / partitioningBy
Streamとfor文の使い分け
```

各小問では、必要に応じて補助メソッドを追加してよい。  
ただし、小問ごとの主題を飛ばしてしまうようなAPI・書き方は、指定がある場合は避けること。

---

# 小問1：StudentScore クラス

## 作成するクラス

```text
StudentScore
```

## 要件

`StudentScore` は、学生番号・氏名・点数を持つクラスである。

保持する情報は以下の通り。

```text
studentId
name
score
```

## 生成時のルール

```text
studentId <= 0 の場合は IllegalArgumentException
name が null または空白のみの場合は "unknown"
score < 0 の場合は 0
score > 100 の場合は 100
```

## 提供する操作

```text
studentId を取得する
name を取得する
score を取得する
文字列表現を返す
```

また、`studentId` が同じ `StudentScore` は同一の学生のデータとして扱う。

例：

```text
StudentScore(1, "Taro", 90)
StudentScore(1, "Jiro", 70)
```

上記2つは同じ学生のデータとして扱う。

---

# 小問2：Comparator の実装

## 作成するクラス

```text
ScoreComparators
```

## 提供する比較規則

以下の比較規則を返すメソッドを作成する。

```text
byScoreDesc
byNameAsc
byScoreDescThenIdAsc
```

## byScoreDesc

点数が高い順に並べる。

例：

```text
100
90
75
```

## byNameAsc

名前の辞書順で並べる。

例：

```text
Akira
Hanako
Jiro
Taro
Yui
```

## byScoreDescThenIdAsc

まず点数が高い順に並べる。  
点数が同じ場合は、`studentId` が小さい順に並べる。

例：

```text
id=3, score=90
id=1, score=90
```

並び替え後：

```text
id=1, score=90
id=3, score=90
```

## 制限

この小問では、Stream は使わない。

---

# 小問3：Comparator の動作確認

## 使用するクラス

```text
Main
```

## 確認用データ例

```java
List<StudentScore> scores = new ArrayList<>();

scores.add(new StudentScore(3, "Taro", 90));
scores.add(new StudentScore(1, "Hanako", 90));
scores.add(new StudentScore(2, "Jiro", 75));
scores.add(new StudentScore(4, "Akira", 100));
scores.add(new StudentScore(5, "Yui", 75));
```

## 確認観点

```text
点数降順:
  Akira 100
  Taro 90
  Hanako 90
  Jiro 75
  Yui 75

名前昇順:
  Akira
  Hanako
  Jiro
  Taro
  Yui

点数降順、同点ならID昇順:
  Akira 100
  Hanako 90
  Taro 90
  Jiro 75
  Yui 75
```

---

# 小問4：自作関数型インターフェースと配列ユーティリティ

## 作成するインターフェース

```text
ScoreCondition
ScoreFormatter
```

## 作成するクラス

```text
StudentScoreUtils
```

## ScoreCondition

`StudentScore` を受け取り、そのデータが条件を満たすかどうかを返す関数型インターフェースとする。

## ScoreFormatter

`StudentScore` を受け取り、表示用文字列に変換する関数型インターフェースとする。

## StudentScoreUtils で提供する処理

```text
count
filter
printFormatted
```

## count

`StudentScore` 配列と条件を受け取り、条件に合う要素数を返す。

```text
scores == null の場合は 0
scores 内の null 要素は無視
condition == null の場合は IllegalArgumentException
```

## filter

`StudentScore` 配列と条件を受け取り、条件に合う `StudentScore` だけを配列で返す。

```text
scores == null の場合は長さ0の配列
scores 内の null 要素は無視
condition == null の場合は IllegalArgumentException
返す配列には StudentScore をそのまま入れてよい
```

## printFormatted

`StudentScore` 配列と表示形式を受け取り、各要素を指定された形式で出力する。

```text
scores == null の場合は何もしない
scores 内の null 要素は無視
formatter == null の場合は IllegalArgumentException
```

## 制限

この小問では、標準関数型インターフェースとStreamは使わない。  
自作した関数型インターフェースと配列処理で実装すること。

## Mainでの確認用データ例

```java
StudentScore[] scores = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    null
};
```

## 確認観点

```text
80点以上の人数を数える
80点以上の StudentScore 配列を取得する
任意の表示形式で全件出力する
式ラムダとブロックラムダの両方を使う
```

---

# 小問5：標準関数型インターフェース版 Utils

## 作成するクラス

```text
StudentScoreStandardUtils
```

## 提供する処理

```text
count
filter
mapToStrings
forEach
```

## count

`StudentScore` 配列と条件を受け取り、条件に合う要素数を返す。

```text
scores == null の場合は 0
scores 内の null 要素は無視
predicate == null の場合は IllegalArgumentException
```

## filter

`StudentScore` 配列と条件を受け取り、条件に合う `StudentScore` だけを配列で返す。

```text
scores == null の場合は長さ0の配列
scores 内の null 要素は無視
predicate == null の場合は IllegalArgumentException
```

## mapToStrings

`StudentScore` 配列と変換処理を受け取り、`String` 配列に変換する。

```text
scores == null の場合は長さ0の配列
scores 内の null 要素は無視
mapper == null の場合は IllegalArgumentException
```

## forEach

`StudentScore` 配列と処理を受け取り、各要素に対して処理を実行する。

```text
scores == null の場合は何もしない
scores 内の null 要素は無視
consumer == null の場合は IllegalArgumentException
```

## 制限

この小問では、Stream は使わない。  
`Predicate`、`Function`、`Consumer` を使うこと。

---

# 小問6：メソッド参照

## 作成するクラス

```text
StudentScorePredicates
StudentScoreFormatters
```

## StudentScorePredicates

以下の判定処理を提供する。

```text
isPassed
isExcellent
```

仕様：

```text
isPassed:
  score >= 80

isExcellent:
  score >= 90
```

## StudentScoreFormatters

以下の変換処理を提供する。

```text
simpleLine
```

仕様：

```text
name + "さん: " + score + "点"
```

## Mainで確認すること

小問5の `StudentScoreStandardUtils` を使い、以下の処理をメソッド参照で書く。

```text
合格者数を数える
優秀者だけ抽出する
表示用文字列に変換する
全件出力する
名前だけ抽出する
```

---

# 小問7：Stream基本操作

## 作成するクラス

```text
StudentScoreStreamUtils
StreamMain
```

## 提供する処理

```text
countPassed
passedStudents
toDisplayLines
printAll
```

## countPassed

80点以上の人数を数える。

```text
scores == null の場合は 0
scores 内の null 要素は無視
```

## passedStudents

80点以上の `StudentScore` だけを配列で返す。

```text
scores == null の場合は長さ0の配列
scores 内の null 要素は無視
```

## toDisplayLines

各 `StudentScore` を以下の形式に変換して配列で返す。

```text
Taro: 90点
Hanako: 75点
```

```text
scores == null の場合は長さ0の配列
scores 内の null 要素は無視
```

## printAll

nullでない `StudentScore` をすべて出力する。

```text
scores == null の場合は何もしない
```

## 制限

この小問では、Streamの基本操作だけで実装する。  
並び替え、集計用Collector、グルーピングは使わない。

---

# 小問8：sorted / max / Optional

## 作成するクラス

```text
StudentScoreStreamAdvanced
StreamAdvancedMain
```

## 提供する処理

```text
sortedByScoreDescThenIdAsc
topStudent
findById
topStudentLine
```

## sortedByScoreDescThenIdAsc

点数が高い順に並べた配列を返す。  
同点の場合は `studentId` が小さい順。

```text
scores == null の場合は長さ0の配列
scores 内の null は無視
元配列は変更しない
```

## topStudent

最高点の `StudentScore` を返す。

```text
scores == null の場合は値なし
scores 内の null は無視
学生がいない場合は値なし
最高点が複数いる場合は studentId が小さい方
```

## findById

指定された `studentId` の `StudentScore` を探す。

```text
scores == null の場合は値なし
studentId <= 0 の場合は値なし
scores 内の null は無視
同じIDが複数ある場合は、先に見つかったものを返す
```

## topStudentLine

最高点の学生を表示用文字列にして返す。

```text
データあり:
  トップ: Akira 100点

データなし:
  該当なし
```

## 制限

この小問では、`Optional` を使って「値なし」を表現すること。  
`collect` 系の処理は使わない。

---

# 小問9：数値Streamと統計

## 作成するクラス

```text
StudentScoreStatistics
StatisticsMain
```

## 提供する処理

```text
totalScore
averageScore
maxScore
minScore
averageLine
```

## totalScore

nullでない学生の点数合計を返す。

```text
scores == null の場合は 0
```

## averageScore

nullでない学生の平均点を返す。

```text
scores == null の場合は 0.0
学生が0人の場合は 0.0
```

## maxScore

最高点を返す。

```text
scores == null の場合は値なし
学生が0人の場合は値なし
```

## minScore

最低点を返す。

```text
scores == null の場合は値なし
学生が0人の場合は値なし
```

## averageLine

平均点を表示用文字列で返す。

```text
データあり:
  平均: 77.5点

データなし:
  平均: 該当なし
```

## 制限

点数の集計では、数値用Streamを使うこと。  
`summaryStatistics` や `Collectors` による集計は使わない。

## StatisticsMainで確認すること

通常データに加えて、以下も確認する。

```java
StudentScore[] empty = {};
StudentScore[] onlyNull = { null, null };
StudentScore[] nullArray = null;
```

---

# 小問10：collect / toList / toSet / toMap

## 作成するクラス

```text
StudentScoreCollectorUtils
CollectorMain
```

## 提供する処理

```text
toNonNullList
passedList
nameSet
toMapById
nameMapById
```

## toNonNullList

nullでない `StudentScore` を `List<StudentScore>` にして返す。

```text
scores == null の場合は空List
順序は元配列の順序を維持する
```

## passedList

`score >= passScore` の学生だけを `List<StudentScore>` にして返す。

```text
scores == null の場合は空List
scores 内の null 要素は無視
```

## nameSet

nullでない学生の名前を `Set<String>` にして返す。

```text
scores == null の場合は空Set
同じ名前が複数ある場合は1つにまとまる
```

## toMapById

`studentId` をキー、`StudentScore` を値にしたMapを返す。

```text
scores == null の場合は空Map
同じ studentId が複数ある場合は、後に出てきた StudentScore を優先する
```

## nameMapById

`studentId` をキー、`name` を値にしたMapを返す。

```text
scores == null の場合は空Map
同じ studentId が複数ある場合は、後に出てきた name を優先する
```

## 制限

この小問では `toList`、`toSet`、`toMap` を使う。  
`groupingBy`、`partitioningBy` はまだ使わない。

## CollectorMainで使うデータ

```java
StudentScore[] scores = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    new StudentScore(1, "TaroUpdated", 60),
    new StudentScore(5, "Hanako", 80),
    null
};
```

確認観点：

```text
toNonNullList:
  null以外の6件

passedList(scores, 80):
  Taro 90, Akira 100, Hanako 80

nameSet:
  Hanako は重複して1つ

toMapById:
  id=1 は TaroUpdated

nameMapById:
  id=1 は "TaroUpdated"
```

---

# 小問11：groupingBy / partitioningBy

## 作成するクラス

```text
StudentScoreGroupingUtils
GroupingMain
```

## 提供する処理

```text
groupByName
countByName
partitionByPassed
scoresByName
scoreSetByName
```

## groupByName

名前ごとに `StudentScore` をまとめる。

```text
scores == null の場合は空Map
scores 内の null は無視
```

## countByName

名前ごとの人数を数える。

```text
scores == null の場合は空Map
scores 内の null は無視
```

## partitionByPassed

合格者と不合格者に分ける。

```text
true:
  score >= passScore

false:
  score < passScore
```

以下の場合でも、`true` と `false` の両方のキーを持つMapを返す。

```text
scores == null
scores が空配列
scores が null 要素のみ
```

それぞれの値は空Listとする。

```text
true  -> []
false -> []
```

## scoresByName

名前ごとに点数の `List<Integer>` を作る。

```text
scores == null の場合は空Map
```

## scoreSetByName

名前ごとに点数の `Set<Integer>` を作る。

```text
scores == null の場合は空Map
同じ名前・同じ点数が複数ある場合は1つにまとまる
```

## 制限

この小問では `groupingBy`、`partitioningBy`、`counting`、`mapping` を使う。  
返却コレクションの変更不可化は必須ではない。

## GroupingMainで使うデータ

```java
StudentScore[] scores = {
    new StudentScore(1, "Taro", 90),
    new StudentScore(2, "Hanako", 75),
    new StudentScore(3, "Jiro", 45),
    new StudentScore(4, "Akira", 100),
    new StudentScore(5, "Hanako", 80),
    new StudentScore(6, "Taro", 90),
    new StudentScore(7, "Taro", 60),
    null
};
```

確認観点：

```text
groupByName:
  Taro   -> 3件
  Hanako -> 2件
  Jiro   -> 1件
  Akira  -> 1件

countByName:
  Taro   -> 3
  Hanako -> 2
  Jiro   -> 1
  Akira  -> 1

partitionByPassed(scores, 80):
  true  -> Taro90, Akira100, Hanako80, Taro90
  false -> Hanako75, Jiro45, Taro60

scoresByName:
  Taro   -> [90, 90, 60]
  Hanako -> [75, 80]

scoreSetByName:
  Taro   -> [90, 60]
  Hanako -> [75, 80]
```

## 境界条件確認

以下も確認する。

```java
StudentScore[] empty = {};
StudentScore[] onlyNull = { null, null };
StudentScore[] nullArray = null;
```

特に `partitionByPassed` では、どの場合でも以下に相当する構造を返すこと。

```text
true  -> []
false -> []
```

---

# 小問12：Stream総合演習

## 作成するクラス

```text
StudentScoreReportService
ReportMain
```

## 提供する処理

```text
topN
countPassedByName
passedNameMapById
topStudentName
reportLines
```

## topN

点数が高い順、同点なら `studentId` が小さい順に並べ、上位 `n` 件だけ返す。

```text
scores == null の場合は空List
n <= 0 の場合は空List
scores 内の null は無視
```

## countPassedByName

合格点以上の学生だけを対象に、名前ごとの人数を数える。

```text
scores == null の場合は空Map
```

## passedNameMapById

合格点以上の学生だけを対象に、以下のMapを返す。

```text
studentId -> name
```

```text
scores == null の場合は空Map
同じ studentId が複数ある場合は、後に出てきたものを優先する
```

## topStudentName

最高点の学生の名前を返す。

```text
scores == null の場合は値なし
学生がいない場合は値なし
最高点が複数いる場合は studentId が小さい方
```

## reportLines

nullでない学生を、以下の形式の文字列リストにして返す。

```text
1位: Akira 100点
2位: Hanako 90点
3位: Taro 90点
```

並び順は以下とする。

```text
点数が高い順
同点なら studentId が小さい順
```

順位は単純に並び順の `1, 2, 3...` とする。  
同点順位処理は不要。

## ReportMainで使うデータ

```java
StudentScore[] scores = {
    new StudentScore(3, "Taro", 90),
    new StudentScore(1, "Hanako", 90),
    new StudentScore(2, "Jiro", 75),
    new StudentScore(4, "Akira", 100),
    new StudentScore(5, "Yui", 75),
    new StudentScore(1, "HanakoUpdated", 60),
    null
};
```

確認観点：

```text
topN(scores, 3):
  Akira 100
  Hanako 90
  Taro 90

countPassedByName(scores, 80):
  Akira  -> 1
  Hanako -> 1
  Taro   -> 1

passedNameMapById(scores, 80):
  4 -> Akira
  1 -> Hanako
  3 -> Taro

topStudentName:
  Akira

reportLines:
  1位: Akira 100点
  2位: Hanako 90点
  3位: Taro 90点
  4位: Jiro 75点
  5位: Yui 75点
  6位: HanakoUpdated 60点
```

---

# 最終確認項目

この大問を解いた後、以下を自分の言葉で説明できること。

```text
1. Comparatorの比較結果と並び順の関係
2. sort用Comparatorとmax用Comparatorの違い
3. ラムダ式が関数型インターフェースに代入できる理由
4. Predicate / Function / Consumer の使い分け
5. メソッド参照が使える場面
6. filter / map / sorted / collect の役割
7. Optional / OptionalInt / OptionalDouble を使う理由
8. toMap の merge function が必要になる場面
9. groupingBy と partitioningBy の違い
10. Streamだけで書くよりfor文を使った方がよい場面
```

---

# 発展課題

必須ではないが、余裕があれば以下も検討する。

```text
1. 返却するListやMapを変更不可にする
2. StudentScoreのコピーコンストラクタを作り、防御的コピーする
3. nullを許容する設計と例外にする設計を比較する
4. Stream.toList() と Collectors.toList() の違いを調べる
5. 同じ題材をList入力版に変更して実装する
```

ただし、発展課題を行う場合でも、基本課題の仕様と動作確認を先に満たすこと。