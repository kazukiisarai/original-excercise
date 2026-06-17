package training.original.o06;

import java.util.Optional;

public class BookRecord {
private final int id;
private final String title;
private final String author;
private final int rating;

public BookRecord(int id,String title,String author,int rating){
  if(id<=0){
    throw new IllegalArgumentException("idは正の整数です"+id);
  }
  this.id = id;
  this.title = (title == null || title.isBlank()) ? "unknown" : title;
  this.author = (author == null || author.isBlank()) ? "unknown" : author;
  this.rating = (rating < 0) ? 0 :(rating > 5 ? 5 : rating);

}

public int getId(){
  return this.id;
}
public String getTitle(){
  return this.title;
}
public String getAuthor(){
  return this.author;
}
public int getRating(){
  return this.rating;
}
public String toCsvLine(){
  return this.id+","+this.title+","+this.author+","+this.rating;
}
public static Optional<BookRecord> fromCsvLine(String line){
if(line == null || line.isBlank()){
  return Optional.empty();
}
String[] splittedLine = line.split(",",-1);
if(splittedLine.length != 4){
  return Optional.empty();
}
try{
  int id = Integer.parseInt(splittedLine[0]);
  String title = splittedLine[1];
  String author = splittedLine[2];
  int rating = Integer.parseInt(splittedLine[3]);
  return Optional.of(new BookRecord(id, title, author, rating));
}catch(IllegalArgumentException e){
  return Optional.empty();
}
}
public String toString(){
return "id: "+this.id+", title: "+this.title+", author: "+this.author+", rating: "+this.rating;
}
}
