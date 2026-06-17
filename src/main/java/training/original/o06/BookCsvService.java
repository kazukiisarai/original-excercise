package training.original.o06;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.Objects;
import java.util.Comparator;

public class BookCsvService {

public static List<BookRecord> readBooks(Path path) throws IOException{
if(path == null){
  throw new IllegalArgumentException("pathがnullです");
}
List<String> lines = Files.readAllLines(path);
return lines.stream()
      .filter(Predicate.not(String::isEmpty))
      .map(BookRecord::fromCsvLine)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toList());
}

public static void writeBooks(Path path, List<BookRecord> books) throws IOException{
if(path == null){
  throw new IllegalArgumentException("pathがnullです");
}
List<BookRecord> safeBooks = (books == null) ? new ArrayList<>() : books;
List<String> toWriteCsv = safeBooks.stream()
                          .filter(Objects::nonNull)
                          .map(BookRecord::toCsvLine)
                          .toList();
Files.write(path,toWriteCsv);
}

public static Optional<BookRecord> findById(List<BookRecord> books, int id){
if(books == null || id<=0){
  return Optional.empty();
}
return books.stream()
      .filter(Objects::nonNull)
      .filter(book -> book.getId() == id)
      .findFirst();
}

public static List<BookRecord> topRated(List<BookRecord> books, int minRating){
if(books == null){
  return new ArrayList<>();
}
Comparator<BookRecord> comparator = Comparator.comparingInt(BookRecord::getRating).reversed().thenComparing(BookRecord::getId);
return books.stream()
      .filter(Objects::nonNull)
      .filter(book -> book.getRating()>=minRating)
      .sorted(comparator)
      .toList();

}
}
