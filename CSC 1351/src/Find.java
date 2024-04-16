import java.util.*; // a = 90
import java.util.regex.*;
import java.io.*;
public class Find {
  int a = 37;
  // aa = 55;
  public static void main(String[] args) throws IOException {
    //Scanner s = new Scanner(new File("/home/brandonef/Documents/Spring 2024/CSC 1351/CSC 1351/src/Find.java"));
    Scanner s = new Scanner(new File("CSC 1351/src/Find.java"));
    while(s.hasNextLine()) {
      if(s.findInLine("\\ba\\s*=\\s*(\\d+)") != null) {
        MatchResult mr = s.match();
        System.out.println("num="+mr.group(1));
      }
      s.nextLine();
    }
  }
} // a = 2
