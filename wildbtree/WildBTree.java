/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package wildbtree;

/**
 *
 * @author steve
 */
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class WildBTree {
    Comparator c;
    Node head;

    void balance() {
        head = balance(head);
    }

    public WildBTree(Comparator c) {
      this.c = c;
    }

    Node newNode(Object k,Object v) { return new NodeImp(k,v,c); }

    public Node getRoot() {
      return head;
    }

    /**
     * Get an element from the tree,
     * identified by key.
     */
    public Object get(Object k) {
      if(head == null) return null;
      return head.get(k);
    }

    /**
     * Put an element into the tree
     * using key and value.
     */
    public void put(Object k,Object v) {
      if(head == null) {
        head = newNode(k,v);
      } else {
        head.put(k,v);
        head = balance(head);
      }
    }

    /**
     * Compute the size (number of nodes)
     * in the tree.
     */
    public int size() {
        if(head == null) return 0;
        else return head.size();
    }

    public int height() {
        if(head == null) return 0;
        else return head.height();
    }

    public static int getHeight(Node n) {
        if(n == null) return 0;
        else return n.height();
    }

    /**
     * This is essentially an AVL Tree algorithm.
     * 
     * @param n
     * @return 
     */
    public static Node balance(Node n) {
        for(int i=0;i<2;i++) {
            int sl = n.left  == null ? 0 : n.left.height();
            int sr = n.right == null ? 0 : n.right.height();
            int diff = sr - sl;
            if(diff >= 2) {
                assert getHeight(n.left) < getHeight(n.right);
                if(n.right != null && n.right.left != null && getHeight(n.right.right) <= getHeight(n.right.left)) {
                    n.right = (Node)n.right.rotateRight();
                }
                String pre = stringify(n);
                n = (Node)n.rotateLeft();
                String post = stringify(n);
                checkRol(pre, post);
                assert n != null : "rotateLeft() should not return null";
            } else if(diff <= -2) {
                assert getHeight(n.left) > getHeight(n.right);
                if(n.left != null && n.left.right != null && getHeight(n.left.left) < getHeight(n.left.right)) {
                    n.left = (Node)n.left.rotateLeft();
                }
                String pre = stringify(n);
                n = (Node)n.rotateRight();
                String post = stringify(n);
                checkRor(pre, post);
                assert n != null : "rotateRight() should not return null";
            } else {
                if(n.left != null) n.left = balance(n.left);
                if(n.right != null) n.right = balance(n.right);
                return n;
            }
        }
        throw new Error("balance is not converging");
    }

    public static boolean isBalanced(Node n) {
        int sl = getHeight(n.left);
        int sr = getHeight(n.right);
        if(sl + 2 < sr) {
            return false;
        } else if(sr + 2 < sl) {
            return false;
        } else {
            boolean b = true;
            if(n.left != null) b = b && isBalanced(n.left);
            if(n.right != null) b = b && isBalanced(n.right);
            return b;
        }
    }

    public static void main(String[] args) {
        try {
            assert false;
            System.out.println("Please enable assertions");
            System.exit(2);
        } catch(AssertionError ae) {
        }

        int fcount = 0;
        Field[] fs = NodeImp.class.getDeclaredFields();
        if(fs != null) {
            for(Field f : fs) {
                if(!f.toString().contains("$")) {
                    fcount++;
                }
            }
        }
        assert fcount == 0 : "You should not add instance/static fields to this class";

        WildBTree bi = new WildBTree((a,b)->{
            Integer ia = (Integer)a;
            Integer ib = (Integer)b;
            return ia - ib;
        });
        Map<Integer,Integer> heights = new HashMap<>();
        heights.put(0,1);
        heights.put(1,2);
        heights.put(2,2);
        heights.put(3,3);
        heights.put(4,3);
        heights.put(5,3);
        heights.put(6,4);
        heights.put(7,4);
        
        Map<Integer,Growth> map = new HashMap<>();
        for(int i=0;i<Growth.GROWTHS.length;i++) {
            int branchId = i;
            Growth g = new Growth(i);
            bi.put(branchId, g);
            map.put(branchId ,g);
            if(map.size() == 1) continue;
            if(heights.size() > 1)
                assert bi.height() == heights.get(i) : String.format("height() returned %d instead of %d",bi.height(), heights.get(i));
            assert bi.size() == map.size() : String.format("BTree size is %d, Map size is %d",
                bi.size(), map.size());
            assert bi.height() != 0 : "The height() function returned 0 on a non-empty tree";
            assert g.equals(bi.get(branchId)) : "Your get() method does not work (or maybe it's put()?)";
            boolean b = isBalanced(bi.head);
            System.out.printf("tree: %d %d %s%n",bi.size(),bi.height(),b);
            assert b : "The tree is not balanced";
        }
        // ---
        map = new HashMap<>();
        bi = new WildBTree((a,b)->{
            Integer ia = (Integer)a;
            Integer ib = (Integer)b;
            return ia - ib;
        });
        Random random = new Random();
        for(int i=0;i<Growth.GROWTHS.length;i++) {
            int branchId = random.nextInt();
            Growth g = new Growth(i % Growth.GROWTHS.length);
            bi.put(branchId, g);
            map.put(branchId ,g);
            assert bi.size() == map.size() : String.format("BTree size is %d, Map size is %d",
                bi.size(), map.size());
            assert bi.height() != 0 : "The height() function returned 0 on a non-empty tree";
            assert g.equals(bi.get(branchId)) : "Your get() method does not work";
            boolean b = isBalanced(bi.head);
            System.out.printf("tree: %d %d %s%n",bi.size(),bi.height(),b);
            assert b : "The tree is not balanced";
        }
        // ---
        map = new HashMap<>();
        bi = new WildBTree((a,b)->{
            Integer ia = (Integer)a;
            Integer ib = (Integer)b;
            return ia - ib;
        });
        Random rand = new Random();
        for(int i=0;i<100;i++) {
            int branchId = rand.nextInt(1000);
            Growth g = new Growth(rand);
            bi.put(branchId,g);
            map.put(branchId,g);
            assert bi.size() == map.size() : String.format("BTree size is %d, Map size is %d",
                bi.size(), map.size());
            assert bi.height() != 0 : "The height() function returned 0 on a non-empty tree";
            assert g.equals(bi.get(branchId)) : "Your get() method does not work";
            boolean b = isBalanced(bi.head);
            System.out.printf("tree: %d %d %s%n",bi.size(),bi.height(),b);
            assert b : "The tree is not balanced";
        }
        for(Integer key : map.keySet()) {
            Object o1 = map.get(key);
            Object o2 = bi.get(key);
            assert o2 != null : "Your get() returned null when it should not.";
            System.out.printf("%s == %s%n",o1,o2);
            assert o1.equals(o2) : String.format("%s != %s. Is your get() method working?",o1,o2);
        }
        int misses = 0;
        int gsize = 1000;
        for(int i=0;i<gsize;i++) {
            Integer k = rand.nextInt(3*gsize);
            if(!map.containsKey(k))
                assert bi.get(k) == null : String.format("BTree has key %s, but Map does not",k);
            else misses ++;
        }
        assert misses > 0 && misses < gsize : "Try running the test again.";
        System.out.println("SIZE: "+bi.size());
        Iterator iter = bi.iterator();
        Node prev = null;
        while(iter.hasNext()) {
            Node node = (Node)iter.next();
            if(prev != null) {
                // assert keys are in order
                assert bi.c.compare(prev.key, node.key) <= 0;
            }
            prev = node;
        }
        System.out.println("All tests passed");
    }

    static Map<String,String> rolChecks = new HashMap<>();
    static Map<String,String> rorChecks = new HashMap<>();
    static {
        rolChecks.put("(-0(-1(-2-)))","((-0-)1(-2-))");
        rolChecks.put("((-0-)1(-2(-3(-4-))))","(((-0-)1-)2(-3(-4-)))");
        rorChecks.put("(((-5-)6-)7-)","((-5-)6(-7-))");
        rorChecks.put("((((-3-)4-)5-)6(-7-))","(((-3-)4-)5(-6(-7-)))");
    }
    public static void checkRor(String pre,String post) {
        if(rorChecks.containsKey(pre)) {
            String res = rorChecks.get(pre);
            assert rorChecks.get(pre).equals(post) : String.format("rotateRight failed: %s became %s, not %s",
                pre, post, res);
        } else
            ;//assert false : "ROR: "+pre + " / " + post;
    }
    public static void checkRol(String pre,String post) {
        if(rolChecks.containsKey(pre)) {
            String res = rolChecks.get(pre);
            assert rolChecks.get(pre).equals(post) : String.format("rotateLeft failed: %s became %s, not %s",
                pre, post, res);
        } else
            ;//assert false : "ROL: "+pre + " / " + post;
    }
    public static String stringify(Node n) {
        return stringify(n,0);
    }
    public static String stringify(Node n,int count) {
        if(count > 5) return "<too deep to show>";
        if(n == null) return "-";
        else return "(" + stringify(n.left,count+1) + n.key + stringify(n.right,count+1) + ")";
    }
    
    public Iterator iterator() {
        return head.iterator();
    }
}
