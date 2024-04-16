package Examples;

import java.util.*;

public class HashKey {
    final String s;

    public HashKey(String ss) {
        s = ss;
    }

    @Override
    public int hashCode() {
        if(s.length()==0)
            return 0;
        return s.charAt(0);
        //return 1;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof HashKey) {
            HashKey hk = (HashKey)o;
            return s.equals(hk.s);
        }
        return false;
    }

    @Override
    public String toString() {
        return s;
    }

    public static void main(String[] args) {
        /*
        Hashtable<HashKey,String> hs =
            new Hashtable<HashKey,String>();
        hs.put(new HashKey("foo"),"bar");
        hs.put(new HashKey("fruit"),"apple");
        for(HashKey hk : hs.keySet()) {
            System.out.println(hk+" => "+hs.get(hk));
        }
        */
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(1);
        set.add(2);
        System.out.println(set);

        Set<HashKey> set2 = new HashSet<>();
        set2.add(new HashKey("foo"));
        set2.add(new HashKey("bar"));
        System.out.println(set2);
    }
}
