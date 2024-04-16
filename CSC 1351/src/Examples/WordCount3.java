package Examples;

import java.util.*;
import java.io.*;

public class WordCount3 {
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
    public static void main(String[] args) throws IOException {
        for(String fn : args) {
            try(Scanner sc = new Scanner(new File(fn))) {
                // Use Regex's
                for(var result : sc.findAll("[\\w']+").toList()) {
                    String word = result.group(0);
                    if(!map.containsKey(word)) {
                        map.put(word, 1);
                    } else {
                        map.put(word, map.get(word) + 1);
                    }
                }
            }
        }
        List<String> words = new ArrayList<>();
        words.addAll(map.keySet());
        // Sort words by frequency
        Collections.sort(words, comp);
        for(var word : words) {
            System.out.println(word+" => "+map.get(word));
        }
    }
}
