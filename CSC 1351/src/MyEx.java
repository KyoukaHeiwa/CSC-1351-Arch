public class MyEx extends Exception {

    public static void test1() {
      // We can still handle this exception
      // with a try/catch block.
      try {
        if(true) throw new MyEx();
        System.out.println("This print doesn't happen.");
      } catch(MyEx me) {
        // This wraps one exception with another
        throw new RuntimeException(me);
      }
    }
  
    public static void main(String[] args) {
      test1();
      System.out.println("This print doesn't happen.");
    }
  }
  