# 課題18：BookRecord と BookCsvService によるCSV入出力

## 目的

CSV形式の文字列とJavaオブジェクトを相互変換し、ファイルへの書き込み・読み込みを行うプログラムを作成してください。

この課題では、以下を扱います。

- immutableなデータクラスの設計
- 文字列からオブジェクトへの変換
- `Optional` による変換失敗の表現
- `Path` / `Files` を使ったファイル入出力
- `IOException` の扱い
- 不正行を含むCSVファイルの読み込み
- `List` / `Stream` / `Comparator` を使った検索・抽出・並び替え

---

## 作成するクラス

以下の3クラスを作成してください。

```text
BookRecord
BookCsvService
BookCsvMain
```

パッケージは以下とします。

```text
training.original.o06
```

---

# 1. BookRecord クラス

本1冊分の情報を表すクラスです。

## フィールド

以下のフィールドを持たせてください。

```text
id
title
author
rating
```

すべて外部から直接変更できないようにしてください。

また、このクラスは作成後に状態が変わらないデータとして扱います。

---

## コンストラクタ

本のID、タイトル、著者、評価を受け取って初期化してください。

### 初期化ルール

| 項目 | ルール |
|---|---|
| id | 0以下なら `IllegalArgumentException` |
| title | `null` または空白のみなら `"unknown"` |
| author | `null` または空白のみなら `"unknown"` |
| rating | 0未満なら0、5より大きければ5 |

---

## 基本メソッド

以下の値を取得できるようにしてください。

```text
id
title
author
rating
```

また、内容が確認しやすいように文字列表現も用意してください。

---

## CSV形式への変換

`BookRecord` をCSV1行の文字列に変換できるようにしてください。

CSV形式は次の通りです。

```text
id,title,author,rating
```

例：

```text
1,Effective Java,Joshua Bloch,5
```

今回は簡単のため、タイトルと著者にカンマは含まれない前提で構いません。

---

## CSV1行からの変換

CSV1行の文字列から `BookRecord` を作成できるようにしてください。

ただし、文字列から必ず `BookRecord` を作れるとは限らないため、変換結果は「存在しない可能性」を表現できる形にしてください。

### 変換ルール

以下の場合は変換失敗として扱います。

| 入力 | 結果 |
|---|---|
| `null` | 変換失敗 |
| 空白のみ | 変換失敗 |
| カンマ区切りで4項目ではない | 変換失敗 |
| idが整数に変換できない | 変換失敗 |
| ratingが整数に変換できない | 変換失敗 |
| `BookRecord` 作成時に例外が発生する | 変換失敗 |

正常に変換できる場合は、対応する `BookRecord` を返してください。

### 例

```text
1,Effective Java,Joshua Bloch,5
```

これは有効です。

```text
abc,Bad Id,Someone,3
```

これはIDが整数ではないため無効です。

```text
2,Missing Rating,Author
```

これは項目数が足りないため無効です。

```text
3,Too High Rating,Author,10
```

これは有効です。  
ただし、評価は `BookRecord` の初期化ルールに従って5に補正されます。

---

# 2. BookCsvService クラス

`BookRecord` のCSVファイル読み書きを担当するユーティリティクラスです。

インスタンスを作らずに使う想定で実装してください。

---

## CSVファイルの読み込み

指定されたファイルを読み込み、正常に変換できた `BookRecord` の一覧を返してください。

### 仕様

| 条件 | 挙動 |
|---|---|
| path が `null` | `IllegalArgumentException` |
| ファイル読み込みに失敗 | `IOException` を呼び出し元へ伝える |
| 空行 | 無視 |
| 不正なCSV行 | 無視 |
| 正常なCSV行 | `BookRecord` に変換して結果に含める |

不正な行が1つあっても、ファイル全体の読み込みを失敗扱いにしないでください。  
読み込める行だけを結果に含めます。

---

## CSVファイルへの書き込み

`BookRecord` の一覧をCSV形式でファイルに書き込んでください。

### 仕様

| 条件 | 挙動 |
|---|---|
| path が `null` | `IllegalArgumentException` |
| books が `null` | 空ファイルを書き込む |
| books 内の `null` 要素 | 無視 |
| ファイル書き込みに失敗 | `IOException` を呼び出し元へ伝える |

---

## IDによる検索

`BookRecord` の一覧から、指定されたIDの本を探してください。

### 仕様

| 条件 | 挙動 |
|---|---|
| books が `null` | 見つからない扱い |
| id が0以下 | 見つからない扱い |
| books 内の `null` 要素 | 無視 |
| 同じIDが複数ある | 最初に見つかったものを返す |

検索結果は、見つからない可能性を表現できる形で返してください。

---

## 評価による抽出・並び替え

指定された評価以上の本だけを抽出し、評価が高い順に並べて返してください。

### 仕様

| 条件 | 挙動 |
|---|---|
| books が `null` | 空の一覧 |
| books 内の `null` 要素 | 無視 |
| rating が minRating 以上 | 結果に含める |
| 並び順 | rating が高い順 |
| rating が同じ場合 | id が小さい順 |

---

# 3. BookCsvMain クラス

実際にファイルを書き込み、読み込み、動作確認を行ってください。

## 確認1：不正行を含むCSVの読み込み

まず、CSV文字列の一覧を直接ファイルに書き込んでください。

この確認では、`BookRecord` に変換してから書き込むのではなく、CSV文字列をそのままファイルに書き込んでください。  
不正行を含んだファイルを読み込めるか確認するためです。

### 使用データ例

```text
1,Effective Java,Joshua Bloch,5
2,Clean Code,Robert C. Martin,5
3,銀河鉄道の夜,宮沢賢治,4
4,リーダブルコード,Dustin Boswell,5
5,吾輩は猫である,夏目漱石,4
6,ドメイン駆動設計,Eric Evans,4
7,こころ,夏目漱石,5
8,ハッカーと画家,Paul Graham,4
9,雪国,川端康成,3
10,達人プログラマー,Andrew Hunt,5
abc,Bad Id,Someone,3
2,Missing Rating,Author
3,Too High Rating,Author,10
4,Good Book,Author,5
```

### 期待する挙動

| 行 | 期待 |
|---|---|
| `abc,Bad Id,Someone,3` | 無視 |
| `2,Missing Rating,Author` | 無視 |
| `3,Too High Rating,Author,10` | 有効。ratingは5に補正 |
| `4,Good Book,Author,5` | 有効 |

読み込み結果に、不正な2行が含まれないことを確認してください。

---

## 確認2：BookRecord一覧の書き込みと読み戻し

次に、`BookRecord` の一覧を作成し、CSVファイルに書き込んでください。

その後、同じファイルを読み戻して、書き込んだ内容が取得できることを確認してください。

確認すること：

```text
1. BookRecord の一覧を作成する
2. CSVファイルに書き込む
3. CSVファイルから読み戻す
4. 読み戻した一覧を表示する
```

---

## 確認3：読み戻したデータに対する検索

読み戻した `BookRecord` の一覧に対して、ID検索を行ってください。

確認例：

```text
id = 4
```

同じIDが複数ある場合は、最初に見つかったものが返ることを確認してください。

---

## 確認4：読み戻したデータに対する抽出・並び替え

読み戻した `BookRecord` の一覧に対して、指定評価以上の本を抽出し、並び替えてください。

確認例：

```text
minRating = 3
```

期待する並び順：

```text
rating が高い順
同じ rating なら id が小さい順
```

---

# 注意事項

## ファイルの配置

学習用であれば、任意の場所にCSVファイルを作成して構いません。

ただし、実行時に生成・更新するCSVファイルは、通常はソースコード本体とは分けて置く方が自然です。

例：

```text
books.csv
data/books.csv
tmp/books.csv
```

---

## CSV仕様の簡略化

この課題では、以下は考慮しなくて構いません。

```text
タイトルや著者にカンマが含まれるケース
ダブルクォートで囲まれたCSV
改行を含む項目
文字コードの細かい指定
```

---

# 確認観点

以下を満たしているか確認してください。

```text
BookRecord は作成後に状態が変わらない設計になっている
不正な id は例外になる
title / author の空値は unknown に補正される
rating は 0〜5 に補正される
CSV文字列から正常に BookRecord を作れる
不正なCSV行は変換失敗として扱える
CSVファイルに書き込める
CSVファイルから読み込める
不正行を含むCSVファイルでも、正常行だけ読み込める
読み込み・書き込みの IOException を呼び出し側で扱える
ID検索で、値がない可能性を安全に扱える
評価順に並び替えられる
```

---

# 提出時に説明すること

コードに加えて、次の2点を説明してください。

```text
1. CSV1行から BookRecord を作る処理では、なぜ値がない可能性を表現する戻り値にしたのか。

2. ファイル読み込み・書き込みで発生する IOException を、なぜサービス側で握りつぶさず呼び出し側へ伝える設計にしたのか。
```