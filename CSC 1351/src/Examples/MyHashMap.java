package Examples;

import java.util.*;

public class MyHashMap implements MinMap {
    
    List<List<Entry>> buckets = new ArrayList<>();
    // Keep track of the total number of buckets in
    // the hash map. Alternatively, wee could compute
    // them by running through the buckets list.
    int size = 0;

    public int computeSize() {
        int c = 0;
        for(List<Entry> ll : buckets)
            c += ll.size();
        return c;
    }

    public MyHashMap() { this(100); }
    public MyHashMap(int n) {
        while(buckets.size() < n)
            buckets.add(new ArrayList<>());
    }

    /**
     * We want to use our hashcodes to look up a bucket
     * from a list of buckets. Because we're using the
     * code as an index, we need to make sure it's positive
     * and in range of our table.
     */
    int hash(Object key) {
        return Math.abs(key.hashCode()) % buckets.size();
    }

    public void put(Object key,Object value) {
        List<Entry> hl = buckets.get(hash(key));
        for(Entry he : hl) {
            if(key.equals(he.k)) {
                he.v = value;
                return;
            }
        }
        hl.add(new Entry(key,value));
        size++;
    }

    public Object get(Object key) {
        List<Entry> hl = buckets.get(hash(key));
        for(Entry he : hl) {
            if(key.equals(he.k)) {
                return he.v;
            }
        }
        return null;
    }

    public int size() {
        assert size == computeSize() : "BadSize";
        return size;
    }

    public void clear() {
        // buckets = new ArrayList<>();
        // while(buckets.size() < 100)
        //     buckets.add(new ArrayList<>());
        for(List<Entry> e : buckets)
            e.clear();
        
        size = 0;
    }

    /**
     * This class is used to test
     * our Hashtable class.
     */
    public static class NumVal {
        final int n;
        public NumVal(int n) { this.n = n; }
        public boolean equals(Object o) {
            NumVal nv = (NumVal)o;
            return nv.n == n;
        }

        public int hashCode() {
            return n;//100*n*n*n;
        }
        public String toString() {
            return ""+n;
        }
    }

    @Override
    public Iterable keys() {
        final Iterator entryIter = entrySet().iterator();
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    @Override
                    public boolean hasNext() {
                        return entryIter.hasNext();
                    }
                    @Override
                    public Object next() {
                        Entry e = (Entry)entryIter.next();
                        return e.getKey();
                    }
                };
            }
        };
    }

    public Iterable values() {
        final Iterator entryIter = entrySet().iterator();
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    @Override
                    public boolean hasNext() {
                        return entryIter.hasNext();
                    }
                    @Override
                    public Object next() {
                        Entry e = (Entry)entryIter.next();
                        return e.getValue();
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
                    // index into the buckets array
                    int bucket=0;
                    // index into the buckets.get(bucket) array
                    Iterator bucketIter=buckets.get(0).iterator();
                    void advance() {
                        while(true) {
                            if(bucket >= buckets.size())
                                break; // no more
                            if(bucketIter.hasNext()) {
                                break; // next one is ready
                            } else {
                                bucket += 1;
                                if(bucket < buckets.size())
                                    bucketIter = buckets.get(bucket).iterator();
                            }
                        }
                    }
                    @Override
                    public boolean hasNext() {
                        advance();
                        return bucket < buckets.size();
                    }
                    @Override
                    public Object next() {
                        Entry h = (Entry)bucketIter.next();
                        return h;
                    }
                    @Override
                    public void remove() {
                        bucketIter.remove();
                    }
                };
            }
        };
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<buckets.size();i++) {
            if(buckets.get(i).size() == 0)
                sb.append('-');
            else
                sb.append('*');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MinMap h = new MyHashMap();
        for(int i=0;i<20;i++)
            h.put(new NumVal(i),i*i);
        for(int i=0;i<20;i++)
            System.out.println(i+" "+h.get(new NumVal(i))+" "+i*i);
        for(Object i : h.keys())
            System.out.println(i+" => "+h.get(i));
        h.put("Hello","World");
        for(Object e : h.entrySet())
            System.out.println("Entry: "+e);
        System.out.println(h.size());
        System.out.println(h);
    }
}
