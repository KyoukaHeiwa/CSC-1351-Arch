import java.util.Scanner;
import java.util.regex.MatchResult;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.io.StringWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Closeable;

// Patterns:
// + means 1 or more of the preceeding thing
// * means 0 or more of the preceeding thing
// . means anything
// \\d means a digit
// \\w means a letter, digit, or underscore
// \\s means a whitespace character (space, tab, newline)
// \\n means line feed
// \\t means tab
// \\b means word boundary (either the start of a string,
//       the end of a string, or a transition between a
//       \\w character and a non \\w character)
// () means a group
// use \\ to make a character literal, \\\\ matches \
// letters and numbers are literal, things that aren't
// letters and numbers are literal with \\ in front
// (but might be anyway, e.g. =, @, etc.)
//
// String.format:
//  %s = string
//  %3s = string, width 3 chars
//  %d = integer
//  %4d = integer, width 4 chars
//  %04d = integer, width 4 chars, zeros in front
//  %f = floating point
//  %.2f = floating point with 2 decimal places
//  %g = scientific notatoin
//  %.2g = scientific notation with 2 decimal places
//  %n = new line, os independent
//
// Output:
// Writer -- superclass of FileWriter and StringWriter
// FileWriter -- write to a file
// StringWriter -- write to memory
// BufferedWriter -- just an optimization
// PrintWriter -- formatting
//
// Input:
// FileReader -- read from a file
// Scanner -- parse input

public class ReadersAndWriters {
  public static void main(String[] args) throws Exception {
    System.out.println("Formatting:");
    System.out.print(String.format("%s%n","x"));
    System.out.print(String.format("%3s%n","x"));
    System.out.print(String.format("%d%n",3));
    System.out.print(String.format("%3d%n",3));
    System.out.print(String.format("%03d%n",3));
    System.out.print(String.format("%f%n",Math.PI*1e3));
    System.out.print(String.format("%.20f%n",Math.PI*1e3));
    System.out.print(String.format("%g%n",Math.PI*1e3));
    System.out.print(String.format("%.3g%n",Math.PI*1e3));

    System.out.println("File I/O");
    File file = new File("output.txt");

    // delete the file
    file.delete();

    // See if a file exists
    if(!file.exists()) {
      // Create it if it doesn't
      try (FileWriter fw = new FileWriter(file)) {
        // Write a line
        fw.write("Jane Doe 28\n");
        // Use format
        fw.write(String.format("%s %s %d%n","John","Doe",30));
      } // automatically closes the file here

      // Append the file, if it exists
      Writer fw = new FileWriter(file,true);
      // Buffer the output
      try(BufferedWriter bw = new BufferedWriter(fw)) {
        bw.write(" George Lucas 19\n");
        bw.write("Jennifer Garner 31\n");
      }
    }

    // Read the file line by line
    try(FileReader fr = new FileReader(file)) {
      Scanner sr = new Scanner(fr);
      while(sr.hasNextLine()) {
        System.out.println(sr.nextLine());
      }
      sr.close();
    }

    try(FileReader fr = new FileReader(file)) {
      Scanner sr = new Scanner(fr);
      while(sr.hasNext()) {
        String firstName = sr.next();
        String lastName = sr.next();
        int age = sr.nextInt();
        System.out.print(String.format(
              "1: First: %s, Last: %s, Age: %d%n",
              firstName,
              lastName,
              age));
      }
      sr.close();
    }
    try(Closeable c = new Closeable() { public void close() { System.out.println("Close"); } }) {
        System.out.println("Test of closeable");
    }

    // Parse by pattern. Note the subtle problem
    // that when you read text with a scanner you'd
    // better put a newline at the end. Either that,
    // or don't call next line.
    String input = "3 + 4 = 7\n";
    Scanner s = new Scanner(input);
    String pat = "(\\d+)\\s*\\+\\s*(\\d+)\\s*=\\s*(\\d+)";
    while(s.hasNextLine()) {
      if(s.findInLine(pat) != null) {
        System.out.println("Match found!");
        MatchResult m = s.match();
        for(int i=1; i<=m.groupCount();i++)
          System.out.println(i+"]="+m.group(i));
      }
      s.nextLine();
    }
    s.close();

    // Read the file line by line again
    try(FileReader fr = new FileReader(file)) {
      Scanner sr = new Scanner(fr);
      String pattern = "\\s*(\\w+)\\s+(\\w+)\\s+(\\d+).*";
      while(sr.hasNextLine()) { // are there more lines?
        if(sr.findInLine(pattern) != null) { // does the pattern exist?
          MatchResult m = sr.match(); // get the result
          System.out.print(String.format(
                "2: First: %s, Last: %s, Age: %d%n",
                m.group(1),
                m.group(2),
                Integer.parseInt(m.group(3))));
        }
        sr.nextLine();
      }
      sr.close();
    }

    // Using a StringWriter
    Writer sw = new StringWriter();
    sw.write(String.format("add: %d %d\n",3,2));
    sw.write(String.format("mul: %d %d\n",2,9));
    sw.write(String.format("mul:\n"));
    sw.close();

    Scanner sr = new Scanner(sw.toString());
    while(sr.hasNext()) {
      String op = sr.next();
      if(!sr.hasNextInt())
        continue;
      int val1 = sr.nextInt();
      if(!sr.hasNextInt())
        continue;
      int val2 = sr.nextInt();
      if(op.equals("add:"))
        System.out.print(String.format("%d+%d=%d%n",val1,val2,val1+val2));
      else if(op.equals("mul:"))
        System.out.print(String.format("%d*%d=%d%n",val1,val2,val1*val2));
    }

    // Using patterns
    sr = new Scanner(sw.toString());
    String pattern = "(\\w+):\\s+(\\d+)\\s+(\\d+)";
    while(sr.hasNextLine()) {
      if(sr.findInLine(pattern) != null) {
        MatchResult m = sr.match();
        String op = m.group(1);
        int val1 = Integer.parseInt(m.group(2));
        int val2 = Integer.parseInt(m.group(3));
        if(op.equals("add")) // Note: no colon, that was outside the group
          System.out.print(String.format("%d+%d=%d%n",val1,val2,val1+val2));
        else if(op.equals("mul"))
          System.out.print(String.format("%d*%d=%d%n",val1,val2,val1*val2));
      }
      sr.nextLine();
    }
  }
}
