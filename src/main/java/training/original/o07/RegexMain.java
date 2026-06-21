package training.original.o07;
import java.util.List;
import java.util.Optional;
public class RegexMain {
public static void main(String[] args) {
  List<String> lines = List.of(
    "[2026-06-17] INFO user=U001 message=Login succeeded",
    "[2026-06-17] WARN user=U002 message=Password retry",
    "[2026-06-18] ERROR user=admin_1 message=Database down",
    "[2026-6-18] INFO user=U003 message=Bad date",
    "[2026-06-18] DEBUG user=U004 message=Bad level",
    "not a log line",
    "[2026-06-19] INFO user=U005 message="
);
System.out.println("====全要素パース====");
System.out.println(LogEntryParser.parseAll(lines));
System.out.println("====レベルでフィルター====");
List<LogEntry> entries = lines.stream().map(LogEntryParser::parse).filter(Optional::isPresent).map(Optional::get).toList();
System.out.println(LogEntryParser.filterByLevel(entries,"ERROR"));
System.out.println("====レベルごとにカウント====");
System.out.println(LogEntryParser.countByLevel(entries));
System.out.println("====userIdを伏せ字に====");
List<String> hiddenUserList = lines.stream().map(LogEntryParser::maskUserId).toList();
System.out.println(hiddenUserList);

System.out.println("========最初のやつパースして各種getメソッド実行======");
LogEntry entry = LogEntryParser.parse(lines.get(0)).get();
System.out.println(entry.getDate());
System.out.println(entry.getLevel());
System.out.println(entry.getMessage());
System.out.println(entry.getUserId());
}
}
