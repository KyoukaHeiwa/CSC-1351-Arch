//1
//2
public class Ex3 {
    public static void main(String[] args) throws Exception{
      test2();
      //throw new NullPointerException();
      test1();
    }
    public static void test1() throws Exception {
     if(true) throw new Exception("DIE DIE DIE"); 
     int a = 3;
    }
    public static void test2() {
     try {
      //throw new Exception(); 
        int [] a =new int[3];
        a[4] = 5;
     } catch(NullPointerException npe){
     } catch(ArrayIndexOutOfBoundsException ioe){
        System.out.println(ioe);
     } catch(Exception ex){
      ex.printStackTrace();
      System.out.println("Still alive!");
     }
    }
  }
  