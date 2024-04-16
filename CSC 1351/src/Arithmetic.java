import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Arithmetic
{
   /**
      This class reads a file containing arithmetic expressions and returns an 
      array list of the results. 
      @param filename the file name
      @return a list of results
   */
   
   public static ArrayList<Integer> read(String filename) throws IOException
   {
      ArrayList<Integer> results = new ArrayList<>();
      try(Scanner in = new Scanner(new File(filename))){
         while(in.hasNext()){
            if(!in.hasNextInt()){
               continue;
            }
            int val1 = in.nextInt();
            String op = in.next();
            if(!in.hasNextInt())
               continue;
            int val2 = in.nextInt();
            int result = 0;
            if (op.equals("+")) { result = val1 + val2; }
            else if (op.equals("-")) { result = val1 - val2; }
            else if (op.equals("*")) { result = val1 * val2; }
            results.add(result);
         }
         return results;
      }
   }
   // This method checks your work.
   
   public static String check(String filename)
   {
      try
      {
         return read(filename).toString();
      }
      catch (IOException ex)
      {
         return "I/O exception thrown";
      }
      catch (Exception ex)
      {
         return ex.getClass().getName();
      }
   }
   
   public static void main(String[] args) throws IOException
   {
      Scanner console = new Scanner(System.in);
      String inputFileName = console.nextLine();
      String result = check(inputFileName);
      System.out.println(result);        
   }
}   