package training.original.o06;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
public class BookCsvMain {
public static void main(String[] args) {
  Path path = Path.of("src","main","java","training","original","o06","books.csv");
  try{
    List<String> rawCsvLines = List.of(
    // --- 事前に用意した正しいフォーマット10件 ---
    "1,Effective Java,Joshua Bloch,5",
    "2,Clean Code,Robert C. Martin,5",
    "3,銀河鉄道の夜,宮沢賢治,4",
    "4,リーダブルコード,Dustin Boswell,5",
    "5,吾輩は猫である,夏目漱石,4",
    "6,ドメイン駆動設計,Eric Evans,4",
    "7,こころ,夏目漱石,5",
    "8,ハッカーと画家,Paul Graham,4",
    "9,雪国,川端康成,3",
    "10,達人プログラマー,Andrew Hunt,5",
    
    // --- 例外テストデータ ---
    "abc,Bad Id,Someone,3",          // 期待：IDが数値に変換できず無視される
    "2,Missing Rating,Author",       // 期待：カンマの数が足りず（項目不足）無視される
    "3,Too High Rating,Author,10",   // 期待：有効な行として読み込まれ、ratingが5に補正される
    "4,Good Book,Author,5"           // 期待：有効な行として読み込まれる
);
System.out.println("============まずこのリストを直接書く============");
Files.write(path,rawCsvLines);
System.out.println("============読む(例外もcsvにある状態で読み飛ばせるか？)============");
System.out.println(BookCsvService.readBooks(path));
    List<BookRecord> books = rawCsvLines.stream()
                            .map(BookRecord::fromCsvLine)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .toList();
    BookCsvService.writeBooks(path, books);
    List<BookRecord> result = BookCsvService.readBooks(path);
    System.out.println("===================書いたものを読み取りした結果===================");
    System.out.println(result);
    System.out.println("===================idで検索===================");
    System.out.println(BookCsvService.findById(result, 4));
    System.out.println("===================並べ替え===================");
    System.out.println(BookCsvService.topRated(result, 3));
  } catch(IOException e){
    System.out.println("例外発生"+e);
  }
}
}
