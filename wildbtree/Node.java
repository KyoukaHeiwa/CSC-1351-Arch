/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package wildbtree;

/**
 *
 * @author steve
 */
import java.util.Comparator;
import java.util.Iterator;

public abstract class Node {
  final Comparator c;
  final Object key;
  Object value;
  Node left, right;

  /**
   * Note that your subclass will need to call
   * this coustructor using super().
   */
  public Node(Object k,Object v,Comparator c) {
    key = k;
    value = v;
    this.c = c;
  }

  /**
  Transform the current node according
  to the diagram. Initially, the "this"
  variable should point to "t2". The
  return value should be a reconstructed
  tree with "t4" at the top.

  //    t2               t4
  //   /  \             /  \
  // t1    \    -->    /    t5
  //        t4       t2
  //       /  \     /  \
  //     t3    t5  t1   t3
  */
  public abstract Node rotateLeft();

  /**
  Transform the current node according
  to the diagram. Initially, the "this"
  variable should point to "t4". The
  return value should be a reconstructed
  tree with "t2" at the top.

  //       t4           t2
  //      /  \         /  \
  //     /    t5 --> t1    \
  //   t2                  t4
  //  /  \                /  \
  // t1   t3             t3   t5
  */
  public abstract Node rotateRight();

  /**
   * Compute the maximum number of
   * nodes that can be traversed
   * starting from this one (and
   * including this one).
   */
  public abstract int height();

  /**
   * Compute the total number of nodes
   * beneath (and including) this one.
   */
  public abstract int size();

  /**
   * (1) call the comparator and store the result in d.
   * (2) If d is zero, return the value on this node.
   * (3) If d is greater than zero, and if the right
   *     child is not null, then return the value from the
   *     right child.
   * (4) If d is less than zeero, and if the left
   *     child is not null, then return the value from the
   *     left child.
   * (5) If the above fails, return null.
   */
  public abstract Object get(Object key);

  /**
   * (1) call the comparator and store the result in d.
   * (2) If d is zero, update the value on this node
   *     with the value supplied in the argument.
   * (3a) If d is greater than zero, and if the right
   *     child is not null, then put the value into the
   *     right child.
   * (3b) If d is greater than zero, and if the right
   *     child is null, then create a new node and
   *     assign it to the right child.
   * (4a) If d is less than zeero, and if the left
   *     child is not null, then put the value into the
   *     left child.
   * (4b) If d is greater than zero, and if the left
   *     child is null, then create a new node and
   *     assign it to the left child.
   */
  public abstract void put(Object key,Object value);
  
  /**
   * Step 1: Instantiate a stack object
   * Step 2: Declare variable named "smallest" of type "Node". Initialize it
   *         with the value of "this".
   * Step 3: begin loop
   * Step 3a: if smallest is null, break out of the loop
   * Step 3b: push the value of smallest onto the stack
   * Step 3c: go to step 3
   * Step 4: Instantiate an anonymous inner class of type Iterator.
   *         The class should have a field named "node". Initialize
   *         the value of node by popping a value off the stack.
   * Step 5: hasNext() returns true if node is not null and returns
   *         false if it is null.
   * Step 6: next() first saves the value of node in a local variable
   *         called ret.
   * Step 6a: node is updated with the value in node.right.
   * Step 6b: if node is null and the stack is not empty, update 
   *          node with the value in stack.pop()
   * Step 6c: return ret.
   * @return 
   */
  public abstract Iterator iterator();
}
