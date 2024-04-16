package Examples;

public class MyArray {
    int[] data = new int[10];
    int logicalSize = 0;
    long opcount = 0;
    public void add(int n) {
        if(logicalSize >= data.length) {
            int[] new_data = 
                //new int[data.length*3/10+data.length];
                //new int[2*data.length];
                new int[data.length + 10];
            for(int i=0;i<data.length;i++) {
                new_data[i] = data[i];
                opcount++;
            }
            data = new_data;
        }
        data[logicalSize++] = n;
        opcount++;
    }
    public static void main(String[] args) {
        MyArray ma = new MyArray();
        int n = 0;
        while(true) {
            ma.add(n++);
            System.out.printf("ops/add=%.2f%n", ((double)ma.opcount)/n);
        }
    }
}
