public class Foo {
    static int id_sequence = 1;
    int id;
    public Foo() { int id = id_sequence++; }
    public String toString() {
        return "-"+id+" ("+id_sequence+")-";
    }
    public static void main(String[] args) {
        Foo f1 = new Foo();
        Foo f2 = new Foo();
        System.out.printf("%s / %s%n",f2,f1); } }