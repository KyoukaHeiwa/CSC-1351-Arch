package Examples.Binary_Trees;

import java.io.*;
import java.util.Comparator;

class CharComparator implements Comparator {
    @Override
    public int compare(Object a,Object b) {
        Character ca = (Character)a;
        Character cb = (Character)b;
        return ca - cb;
    }
}

/**
 * This version of the BTree uses a Comparator instead of
 * a Comparable Object. It is otherwise the same.
 */
class Node4 {
  Object c;
  Node4 left, right;

  Node4(Object c) {
    this.c = c;
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

  void draw(PrintWriter pw) {
    pw.printf("<table border=1 cellpadding=5 cellspacing=0>%n");
    pw.printf("<tr><td class='top' colspan=2>%s</td></tr>%n",c);
    pw.printf("<tr><td class='left'>");
    if(left != null) left.draw(pw);
    else pw.printf("&empty;");
    pw.printf("</td><td class='right'>");
    if(right != null) right.draw(pw);
    else pw.printf("&empty;");
    pw.printf("</td></tr></table>%n");
  }
}

public class BTree4 {
  Node4 head;
  Comparator comp;

  public BTree4(Comparator c) {
    this.comp = c;
  }

  public int height() {
    return height(head);
  }

  int height(Node4 node) {
    if(node == null) return 0;
    int hl = height(node.left);
    int hr = height(node.right);
    if(hr > hl)
        return 1+hr;
    else
        return 1+hl;
  }

  public int nodeCount() {
    return nodeCount(head);
  }
  int nodeCount(Node4 node) {
    if(node == null) return 0;
    else return 1 + nodeCount(node.left) + nodeCount(node.right);
  }

  public void add(Object c) {
    if(head == null)
        head = new Node4(c);
    else
        add(head,c);
  }
  void add(Node4 n,Object c) {
    if(comp.compare(c,n.c) < 0) {
        if(n.left == null) n.left = new Node4(c);
        else add(n.left,c);
    }
    if(comp.compare(n.c,c) < 0) {
        if(n.right == null) n.right = new Node4(c);
        else add(n.right,c);
    }
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
   * is contained within the BTree4.
   */
  public boolean contains(Object c) {
    return contains(head,c);
  }
  boolean contains(Node4 node,Object c) {
    if(node == null) return false;
    int cmp = comp.compare(node.c,c);
    if(cmp==0) return true;
    if(cmp > 0 && contains(node.left,c)) return true;
    if(cmp < 0 && contains(node.right,c)) return true;
    return false;
  }

  public static void main(String[] args) throws Exception {
    BTree4 bt = new BTree4(new CharComparator());
    // Note that there's only one 'g' in the tree, despite
    // the fact that we added it twice. Each element is
    // unique.
    for(char c : new char[]{'b','i','g','g','e','r'})
        bt.add(c);
    System.out.printf("Tree height = %d%n",bt.height());
    System.out.printf("Tree node count = %d%n",bt.nodeCount());
    bt.draw("tree.html");
    for(char c : new char[]{'b','u','g','s'})
        if(bt.contains(c))
            System.out.printf("Tree contains %c%n",c);
        else
            System.out.printf("Tree does not contain %c%n",c);
  }
}
