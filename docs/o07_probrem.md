# o07 課題：正規表現によるログ解析

## パッケージ

```text
training.original.o07
```

## 概要

ログ文字列を正規表現で解析し、ログ情報を表すクラスへ変換するプログラムを作成してください。

この課題では、主に以下を扱います。

- `Pattern`
- `Matcher`
- `matches` と `find` の使い分け
- キャプチャグループ
- 正規表現による置換
- `Optional` による解析失敗の表現
- Stream によるリスト処理
- immutable な値オブジェクト

---

## 作成するクラス

以下の3クラスを作成してください。

```text
LogEntry
LogEntryParser
RegexMain
```

---

# 小問1：LogEntry クラス

ログ1件を表す immutable クラスを作成してください。

## フィールド

次の情報を持たせてください。

```text
date
level
userId
message
```

すべて外部から直接変更できないようにしてください。

## コンストラクタ

次の4つの値を受け取って初期化してください。

```text
date
level
userId
message
```

### 初期化ルール

`date` について：

```text
null または空白のみの場合は IllegalArgumentException
```

`level` について：

```text
null または空白のみの場合は IllegalArgumentException
INFO / WARN / ERROR のいずれでもない場合は IllegalArgumentException
```

`userId` について：

```text
null または空白のみの場合は "unknown"
```

`message` について：

```text
null の場合は空文字 ""
```

## メソッド

次の情報を取得できるようにしてください。

```text
date
level
userId
message
```

また、ログ内容を確認しやすい文字列表現を返せるようにしてください。

加えて、4つの値がすべて同じであれば同じログとして扱えるようにしてください。

---

# 小問2：LogEntryParser クラス

ログ文字列を解析するユーティリティクラスを作成してください。

ログの形式は次の通りです。

```text
[YYYY-MM-DD] LEVEL user=USER_ID message=MESSAGE
```

例：

```text
[2026-06-17] INFO user=U001 message=Login succeeded
```

## 形式の詳細

`date`：

```text
YYYY-MM-DD
年は4桁
月は2桁
日は2桁
```

`level`：

```text
INFO
WARN
ERROR
```

`userId`：

```text
user= の後ろに続く値
英数字とアンダースコアのみ
1文字以上
```

`message`：

```text
message= の後ろに続く文字列
空文字でもよい
```

---

## parse

1行のログ文字列を解析し、成功した場合は `LogEntry` を返してください。

解析できない場合は、値が存在しないことを表してください。

### 仕様

```text
line が null の場合は解析失敗
形式に一致しない場合は解析失敗
LogEntry 生成時に例外が発生した場合も解析失敗
```

### 注意

`LogEntry` に保存する値は、ラベルを除いた中身だけにしてください。

例：

```text
入力:
[2026-06-17] INFO user=U001 message=Login succeeded

保存する値:
date    = 2026-06-17
level   = INFO
userId  = U001
message = Login succeeded
```

---

## parseAll

複数行のログ文字列を受け取り、有効なログだけを `LogEntry` のリストとして返してください。

### 仕様

```text
入力リストが null の場合は空リスト
リスト内の null は無視
不正なログ行は無視
有効なログだけ返す
```

---

## filterByLevel

ログリストから、指定された level のログだけを抽出してください。

### 仕様

```text
ログリストが null の場合は空リスト
level が null または空白のみの場合は空リスト
ログリスト内の null は無視
level は完全一致で判定する
```

---

## countByLevel

ログリストを受け取り、level ごとの件数を返してください。

### 仕様

```text
ログリストが null の場合は空Map
ログリスト内の null は無視
```

期待する集計の例：

```text
INFO  -> 2
WARN  -> 1
ERROR -> 1
```

---

## maskUserId

ログ文字列内の userId を伏せ字にしてください。

### 変換例

```text
[2026-06-17] INFO user=U001 message=Login succeeded
```

を、

```text
[2026-06-17] INFO user=**** message=Login succeeded
```

に変換します。

### 仕様

```text
line が null の場合は null を返す
user= が見つからない場合は元の文字列を返す
英数字とアンダースコアだけで構成された userId をマスク対象にする
message= の直前までを userId とみなす
```

次のようなケースも確認してください。

```text
user=U001 message=...
  -> user=**** message=...

user=admin_1 message=...
  -> user=**** message=...

user=U-001 message=...
  -> 変更しない

user= message=...
  -> 変更しない
```

---

# 小問3：RegexMain クラス

動作確認用の `main` メソッドを作成してください。

## 確認用データ

次のログ文字列を使って確認してください。

```java
List<String> lines = List.of(
    "[2026-06-17] INFO user=U001 message=Login succeeded",
    "[2026-06-17] WARN user=U002 message=Password retry",
    "[2026-06-18] ERROR user=admin_1 message=Database down",
    "[2026-6-18] INFO user=U003 message=Bad date",
    "[2026-06-18] DEBUG user=U004 message=Bad level",
    "not a log line",
    "[2026-06-19] INFO user=U005 message="
);
```

## 確認すること

### 1. 全行の解析

有効なログだけが抽出されることを確認してください。

期待される有効ログ数：

```text
4件
```

---

### 2. ERRORログの抽出

`ERROR` のログだけを抽出してください。

期待される内容：

```text
Database down
```

を含む1件。

---

### 3. levelごとの件数

levelごとの件数を集計してください。

期待される内容：

```text
INFO  -> 2
WARN  -> 1
ERROR -> 1
```

---

### 4. userId のマスク

確認用データの各行に対して、`user=...` を `user=****` に置換してください。

期待される例：

```text
[2026-06-17] INFO user=**** message=Login succeeded
```

不正なログ形式であっても、文字列中に正しい形の `user=... message=` があればマスクして構いません。

---

### 5. getter の確認

最初の有効ログを解析し、各getterの戻り値が次のようになることを確認してください。

```text
date    = 2026-06-17
level   = INFO
userId  = U001
message = Login succeeded
```

---

# 提出時に説明すること

コードに加えて、次の3点を説明してください。

## 1. parse では matches と find のどちらを使ったか

ログ1行全体が指定形式に一致している必要があります。  
その観点で、どちらを使ったか説明してください。

## 2. maskUserId は matches と find のどちらに近い考え方か

文字列全体の妥当性を見る処理なのか、文字列中の一部分を探して置換する処理なのかを説明してください。

## 3. キャプチャグループをどこで使ったか

正規表現で抽出した値を、どのように `LogEntry` の生成に利用したか説明してください。

---

# 補足

この課題では、ログのCSV的な厳密なエスケープ処理や、日付として実在するかどうかの検証までは不要です。

たとえば、形式が `YYYY-MM-DD` であれば、正規表現上は有効として構いません。