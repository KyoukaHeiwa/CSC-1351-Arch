// A = B + R
// n*a = n*b + n*r
public class GCD {
    /** Recursion */
    public static int gcd(int x,int y) {
        if(y == 0)
            return x;
        System.out.println("x="+x+",y="+y);
        return gcd(y,x%y);
    }
    /** Iterative */
    public static int gcd2(int x,int y) {
        while(true) {
            if(y == 0)
                return x;
            System.out.println("x="+x+",y="+y);
            int nx = y, ny = x % y;
            x = nx;
            y = ny;
        }
    }
    public static void main(String[] args) {
        int a0 = Integer.parseInt(args[0]);
        int a1 = Integer.parseInt(args[1]);
        System.out.println("gcd("+
            args[0]+","+args[1]+")="+gcd(a0,a1));
        System.out.println("gcd("+
            args[0]+","+args[1]+")="+gcd2(a0,a1));
    }
}