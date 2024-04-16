import java.io.*;
import java.util.Scanner;

public class Rdme {
  public static void main(String[] args) throws IOException {
    try(FileReader fr = new FileReader(args[0])) {
      Scanner s = new Scanner(fr);
      while(s.hasNextLine()) {
        System.out.println(s.nextLine());
      }
    }
  }
}
