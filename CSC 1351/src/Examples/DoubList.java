package Examples;

/** describe a doubly linked list. */
public interface DoubList {
    /** push an element on the back of a list. */
    public void pushBack(int v);
    /** pop an element off the back of the list. */
    public int popBack();
    /** push an element on the front of the list. */
    public void pushFront(int v);
    /** pop an element off the front of the list. */
    public int popFront();
    /** Iterate from front to back. */
    public IntIterator forward();
    /** Iterate from back to front. */
    public IntIterator backward();
}
