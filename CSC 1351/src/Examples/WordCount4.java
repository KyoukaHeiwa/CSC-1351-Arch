package Examples;

import java.util.*;
import java.io.*;
import java.util.function.Consumer;
import java.util.regex.MatchResult;

public class WordCount4 {
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
    final static Consumer consumer = new Consumer() {
        public void accept(Object o) {
            MatchResult mr = (MatchResult)o;
            String word = mr.group(0);
            if(!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                map.put(word, map.get(word) + 1);
            }
        }
    };
    final static Map<String,Integer> map = new HashMap<>();
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
