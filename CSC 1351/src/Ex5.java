public class Ex5 {
    public static void main(String[] args) {
      try {
        String x = null;
        char c = x.charAt(0);
      } finally {
        System.out.println("Finally!");
      }
      System.out.println("After");
    }
  }
  