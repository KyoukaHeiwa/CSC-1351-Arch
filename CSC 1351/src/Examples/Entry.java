package Examples;

public class Entry {
    final Object k;
    Object v;
    Entry(Object k,Object v) {
        this.k = k;
        this.v = v;
    }
    public Object getKey() { return k; }
    public Object getValue() { return v; }
    public void setValue(Object v) { this.v = v; }
    public String toString() { return "{"+k+" => "+v+"}"; }
}
