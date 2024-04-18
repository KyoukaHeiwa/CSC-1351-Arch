package Examples.Binary_Trees;

import java.io.*;

/**
 * This version of the BTree contains Comparables instead of
 * simple characters. Note, however, that char is autoboxed to
 * Character, which implements Comparable.
 */
class Node3 {
  Comparable c;
  Node3 left, right;

  Node3(Comparable c) {
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

public class BTree3 {
  Node3 head;

  public int height() {
    return height(head);
  }

  int height(Node3 Node3) {
    if(Node3 == null) return 0;
    int hl = height(Node3.left);
    int hr = height(Node3.right);
    if(hr > hl)
        return 1+hr;
    else
        return 1+hl;
  }

  public int nodeCount() {
    return nodeCount(head);
  }
  int nodeCount(Node3 Node3) {
    if(Node3 == null) return 0;
    else return 1 + nodeCount(Node3.left) + nodeCount(Node3.right);
  }

  public void add(Comparable c) {
    if(head == null)
        head = new Node3(c);
    else
        add(head,c);
  }
  void add(Node3 n,Comparable c) {
    if(c.compareTo(n.c) < 0) {
        if(n.left == null) n.left = new Node3(c);
        else add(n.left,c);
    }
    if(n.c.compareTo(c) < 0) {
        if(n.right == null) n.right = new Node3(c);
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
   * is contained within the BTree3.
   */
  public boolean contains(Comparable c) {
    return contains(head,c);
  }
  boolean contains(Node3 node3,Comparable c) {
    if(node3 == null) return false;
    int cmp = node3.c.compareTo(c);
    if(cmp==0) return true;
    if(cmp > 0 && contains(node3.left,c)) return true;
    if(cmp < 0 && contains(node3.right,c)) return true;
    return false;
  }

  public static void main(String[] args) throws Exception {
    BTree3 bt = new BTree3();
    // Note that there's only one 'g' in the tree, despite
    // the fact that we added it twice. Each element is
    // unique.
    for(char c : new char[]{'b','i','g','g','e','r'})
        bt.add(c);
    System.out.printf("Tree height = %d%n",bt.height());
    System.out.printf("Tree Node count = %d%n",bt.nodeCount());
    bt.draw("tree.html");
    for(char c : new char[]{'b','u','g','s'})
        if(bt.contains(c))
            System.out.printf("Tree contains %c%n",c);
        else
            System.out.printf("Tree does not contain %c%n",c);
  }
}
