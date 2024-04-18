package Examples;

import java.util.*; public class InAndOut {
    public static void main(String[] args) throws Exception {
        Stack<Integer> s = new Stack<>();
        Queue<Integer> q = new LinkedList<>();
        s.push(1); s.push(2); s.push(3);
        q.add(1); q.add(2); q.add(3);
        q.remove();
        System.out.printf("%d %d%n", q.remove(), s.pop()); }}