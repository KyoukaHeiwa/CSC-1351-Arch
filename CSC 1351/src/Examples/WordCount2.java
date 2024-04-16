package Examples;

import java.util.*;
import java.io.*;

public class WordCount2 {
    public static void main(String[] args) throws IOException {
        Map<String,Integer> map = new HashMap<>();
        for(String fn : args) {
            try(Scanner sc = new Scanner(new File(fn))) {
                // Use Regex's
                for(var result : sc.findAll("\\w+").toList()) { //.toList as it comes back as a stream
                    String word = result.group(0);
                    if(!map.containsKey(word)) {
                        map.put(word, 1);
                    } else {
                        map.put(word, map.get(word) + 1);
                    }
                }
            }
        }
        for(var entry : map.entrySet()) {
            System.out.println(entry.getKey()+" => "+entry.getValue());
        }
    }
}
