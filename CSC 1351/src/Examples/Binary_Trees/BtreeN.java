package Examples.Binary_Trees;

public class BtreeN {
    static class Node{
        Comparable data;
        Node left, right;
        Node(Comparable d){data = d;}
    }
    Node head;

    void add(Comparable c){
        if (head==null) {head = new Node(c);}
        else add(c, head);
    }

    void add(Comparable c, Node n){
        int diff = n.data.compareTo(c);
        if (diff == 0) {}
        else if (diff < 0){
            if (n.left == null) {n.left = new Node(c);}
            else add(c, n.left);
        }
        else { // diff > 0
            if (n.right == null) {n.right = new Node(c);}
            else add(c, n.right);
        }
    }
    /*
     *          n3
     *         /  \   
     *       n2    t4
     *      /  \
     *     n1  t3
     *    /      \
     *   t1      t2
     * 
     *          n2
     *         /  \
     *       n1    n3
     *      /  \   / \
     *     t1  t2 t3 t4
     */
    //It's going to take a tree that has the structure at the top and return a tree that has the structure at the bottom
    Node balance(){
        Node n3 = head;
        if (n3 == null) return n3;
        Node n2 = n3.left;
        if (n2 == null) return n3;
        Node n1 = n2.left;
        if (n1 == null) return n3;
        //Guards for NullPointerException
        Node t1 = n1.left;
        Node t2 = n1.right;
        Node t3 = n2.right;
        Node t4 = n3.right;
        n2.left = n1;
        n2.right = n3;
        n1.left = t1;
        n1.right = t2;
        n3.left = t3;
        n3.right = t4;
        return n2;
    }
}
