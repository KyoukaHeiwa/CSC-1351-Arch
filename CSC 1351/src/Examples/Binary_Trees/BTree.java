package Examples.Binary_Trees;

import java.io.*;
import java.util.*;

interface BTreeVisitor {
   void visit(char c);
}

/**
 * Note that for each recursive method in Node, there's
 * a method in BTree.
 */
class Node {
  char c;
  Node left, right;

  Node(char c) {
    this.c = c;
  }

  /**
   * In a tree, everything is done recursively. First we add...
   */
  void add(char c) {
    if(c > this.c) {
      if(right == null) {
        System.out.printf("+++ data = %c is added as a rightChild of %s%n",c,this);
        right = new Node(c);
      } else {
        System.out.printf("    following right child of %s%n",c,this);
        right.add(c);
      }
    } else if(c < this.c) {
      if(left == null) {
        System.out.printf("+++ data = %c is added as a leftChild of %s%n",c,this);
        left = new Node(c);
      } else {
        System.out.printf("    following left child of %s%n",c,this);
        left.add(c);
      }
    }
  }

  /**
   * Print out a textual representation of the tree.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('(');
    if(left == null) sb.append('-');
    else sb.append(left);
    sb.append(' ');
    sb.append(c);
    sb.append(' ');
    if(right == null) sb.append('-');
    else sb.append(right);
    sb.append(')');
    return sb.toString();
  }

  /**
   * Determine the height of the tree.
   */
  int height() {
    int hr = 0, hl = 0;
    if(right != null) hr = right.height();
    if(left != null) hl = left.height();
    if(hr > hl)
        return 1 + hr;
    else
        return 1 + hl;
  }

  /**
   * Determine the number of nodes in the tree.
   * Note how this calculation differs from height.
   */
  int nodeCount() {
    int ncr = 0, ncl = 0;
    if(right != null) ncr = right.nodeCount();
    if(left != null) ncl = left.nodeCount();
    return 1 + ncr + ncl;
  }

  boolean contains(char c) {
    if(this.c == c)
        return true;
    if(c < this.c && left != null && left.contains(c))
        return true;
    if(this.c < c && right != null && right.contains(c))
        return true;
    return false;
  }


  void draw(PrintWriter pw) {
    pw.printf("<table border=1 cellpadding=5 cellspacing=0>%n");
    pw.printf("<tr><td class='top' colspan=2>%c</td></tr>%n",c);
    pw.printf("<tr><td class='left'>");
    if(left != null) left.draw(pw);
    else pw.printf("&empty;");
    pw.printf("</td><td class='right'>");
    if(right != null) right.draw(pw);
    else pw.printf("&empty;");
    pw.printf("</td></tr></table>%n");
  }

  Node findSmallest() {
    if(left != null)
        return left.findSmallest();
    return this;
  }

  Node remove(char c) {
     if(this.c == c) {
        // No children, removal is easy
        if(left == null && right == null) return null;
        // left is null, removal is easy
        if(left == null) return right;
        // right is null, removal is easy
        if(right == null) return left;
        // Removal is harder. Find the smallest. It will
        // be the new head of this subtree.
        Node smallest = right.findSmallest();
        //          2
        //         /  \
        //        1    \
        //              5
        //             / \
        //            3   6
        //             \
        //              4
        //          3
        //         /  \
        //        1    \
        //              5
        //             / \
        //            4   6
        //
        // Remove the smallest from the right tree...
        Node removedRight = right.remove(smallest.c); // Node 3 removed from 5
        // Make the right tree the right child of the smallest...
        smallest.right = removedRight; // 3.right = 5
        smallest.left = left; // 3.left = 1
        return smallest;
     } else if(c < this.c && left != null) {
        left = left.remove(c);
     } else if(c > this.c && right != null) {
        right = right.remove(c);
     }
     return this;
  }


  void visit(BTreeVisitor bv) {
    if(left != null) left.visit(bv);
    bv.visit(c);
    if(right != null) right.visit(bv);
  }

}

public class BTree implements Iterable {
  Node head;

  public int height() {
    if(head == null) return 0;
    else return head.height();
  }

  public int nodeCount() {
    if(head == null) return 0;
    else return head.nodeCount();
  }

  public void add(char c) {
    System.out.printf("Adding data = %c%n",c);
    if(head == null) {
      head = new Node(c);
    } else {
      head.add(c);
    }
    System.out.printf("New tree = %s%n%n",head);
  }

  public void remove(char c) {
    if(head == null)
        return;
    head = head.remove(c);
  }

  public String toString() {
    if(head == null)
      return "()";
    return head.toString();
  }

  /**
   * Draw the tree in HTML. It's easier to understand
   * when viewed. Note: you are not expected to learn how to
   * write HTML for this course.
   */
  public void draw(String fname) throws IOException {
    FileWriter fw = new FileWriter(fname);
    PrintWriter pw = new PrintWriter(fw);
    pw.printf("<html>%n");
    pw.printf("<head>%n");
    pw.printf("<style>%n");
    pw.printf(".top { text-align: center; background-color: #FFDDFF; }");
    pw.printf(".left { text-align: center; background-color: #DDFFFF; width: 50%%; }");
    pw.printf(".right { text-align: center; background-color: #FFFFDD; width: 50%%; }");
    pw.printf("</style>%n");
    pw.printf("</head>%n");
    pw.printf("<body>%n");
    head.draw(pw);
    pw.printf("</body>%n");
    pw.printf("</html>%n");
    pw.close();
  }

  /**
   * Determine if the character c
   * is contained within the BTree.
   */
  public boolean contains(char c) {
    if(head == null) return false;
    else return head.contains(c);
  }

  public void visit(BTreeVisitor v) {
    if(head != null) head.visit(v);
  }

  public static BTree test(String add,String rmv) {
    BTree bt = new BTree();
    Set<Character> sc = new HashSet<>();
    for(int i=0;i<add.length();i++) {
        char c = add.charAt(i);
        bt.add(c);
        sc.add(c);
        assert bt.nodeCount() == sc.size();
    }
    for(int i=0;i<rmv.length();i++) {
        char c = rmv.charAt(i);
        if(bt.contains(c)) bt.remove(c);
        if(sc.contains(c)) sc.remove(c);
        assert bt.nodeCount() == sc.size();
    }
    return bt;
  }

  public static void main(String[] args) throws Exception {
    test("apple","ape");
    test("avacado","down");
    test("window","glow");
    BTree bt = test("the quick brown fox jumped over the lazy dog's back","");
    bt.draw("tree.html");
    System.out.printf("Tree height = %d%n",bt.height());
    System.out.printf("Tree node count = %d%n",bt.nodeCount());
    //bt.visit((c) -> { System.out.println("c: "+c); });
    bt.visit(new BTreeVisitor() {
        public void visit(char c) {
            System.out.println("c: "+c);
        }
    });
    int count = 0;
    for(Object ch : bt) {
        System.out.println("iter: "+ch);
        count++;
    }
    System.out.printf("%d == %d%n", count, bt.nodeCount());
  }

  @SuppressWarnings("rawtypes")
public Iterator iterator() {
    return new Iterator() {
        // instance variables on the anonymous inner class
        Stack<Node> stack = new Stack<>();
        Node node = head;

        public boolean hasNext() {
            return node != null;
        }
        public Object next() {
            // Store the value we want to return
            Object ret = node.c;

            // save the right node on the
            // stack so we can look at it
            // later.
            if(node.right != null)
                stack.push(node.right);

             // Move to the left node
             node = node.left;

             // If we've run out of left nodes
             // check to see if there's anything
             // on our stack.
             if(node == null)
                if(!stack.empty())
                    node = stack.pop();

            return ret;
        }
    };
  }
}
