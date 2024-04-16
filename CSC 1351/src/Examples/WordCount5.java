package Examples;

import java.util.*;
import java.io.*;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;

public class WordCount5 {
    static int toInt(String s) {
        if(map.containsKey(s)) {
            return map.get(s);
        } else {
            return 0;
        }
    }
    final static Comparator comp = new Comparator() {
        @Override
        public int compare(Object a,Object b) {
            return toInt((String)a) - toInt((String)b);
        }
    };
    final static Map<String,Integer> map = new HashMap<>();
    final static BiFunction applier = new BiFunction() {
        public Object apply(Object key, Object value) {
            if(value == null)
                return 1;
            else
                return ((Integer)value)+1;
        }
    };
    final static Consumer consumer = new Consumer() {
        public void accept(Object o) {
            MatchResult mr = (MatchResult)o;
            String word = mr.group(0);
            // use the compute method on map
            map.compute(word, applier);
            // Actually, you can just type this...
            //map.compute(word, (k,v)->v==null ? 1 : v+1);
        }
    };
    public static void main(String[] args) throws IOException {
        for(String fn : args) {
            try(Scanner sc = new Scanner(new File(fn))) {
                // Use the Stream's forEach method
                sc.findAll("[\\w']+").forEach(consumer);
            }
        }
        List<String> words = new ArrayList<>();
        words.addAll(map.keySet());
        Collections.sort(words, comp);
        for(var word : words) {
            System.out.println(word+" => "+map.get(word));
        }
    }
}
