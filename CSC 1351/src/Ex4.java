public class Ex4 {
    public static void main(String[] args) {
      try {
        assert false;
        throw new Error("Please enable assertions");
      } catch(AssertionError er) {
      }
      int a = 3, b = 5;
      try {
        if(a > b) throw new Exception();
        System.out.println("Got here 1");
      } catch(Exception ex) { //this line does not execute because the exception isn't thrown
        System.out.println("Got here 2");
      }
      System.out.println("Got here 3");
    }
  }
  