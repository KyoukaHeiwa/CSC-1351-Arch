package Examples;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implement a Map using a List
 **/
public class MyArrayMap implements MinMap {
    List<Entry> ma = new ArrayList<>();
    @Override
    public Object get(Object k) {
        for(Entry e : ma) {
            if(k.equals(e.k))
                return e.v;
        }
        return null;
    }
    @Override
    public void put(Object k,Object v) {
        for(Entry e : ma) {
            if(k.equals(e.k)) {
                e.v = v;
                return;
            }
        }
        ma.add(new Entry(k,v));
    }
    @Override
    public void clear() {
        ma.clear();
    }
    @Override
    public int size() {
        return ma.size();
    }

    @Override
    public Iterable keys() {
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    Iterator<Entry> ke = ma.iterator();
                    @Override
                    public boolean hasNext() {
                        return ke.hasNext();
                    }
                    @Override
                    public Object next() {
                        Entry e = ke.next();
                        return e.k;
                    }
                    @Override
                    public void remove() {
                        ke.remove();
                    }
                };
            }
        };
    }

    @Override
    public Iterable values() {
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    Iterator<Entry> ke = ma.iterator();
                    @Override
                    public boolean hasNext() {
                        return ke.hasNext();
                    }
                    @Override
                    public Object next() {
                        Entry e = ke.next();
                        return e.v;
                    }
                    @Override
                    public void remove() {
                        ke.remove();
                    }
                };
            }
        };
    }

    @Override
    public Iterable entrySet() {
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    Iterator<Entry> ke = ma.iterator();
                    @Override
                    public boolean hasNext() {
                        return ke.hasNext();
                    }
                    @Override
                    public Object next() {
                        Entry e = ke.next();
                        return e;
                    }
                    @Override
                    public void remove() {
                        ke.remove();
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        MinMap mm = new MyArrayMap();
        mm.put("Hello","World");
        mm.put("Goodbye","Moon");
        for(Object k : mm.keys()) {
            System.out.println(k+" => "+mm.get(k));
        }
        for(Object o : mm.entrySet()) {
            Entry e = (Entry)o;
            System.out.printf("[%s : %s] ",e.getKey(),e.getValue());
        }
        System.out.println();
    }
}
