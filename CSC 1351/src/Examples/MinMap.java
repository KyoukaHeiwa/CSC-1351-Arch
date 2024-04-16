package Examples;

public interface MinMap {
    public Object get(Object k);
    public void put(Object k,Object v);
    public int size();
    public void clear();
    public Iterable keys();
    public Iterable values();
    public Iterable entrySet();
}
