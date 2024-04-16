package Examples;

/**
 * This is similar to, but smaller than,
 * the java.util.List interface. We implement
 * this list using arrays and a singly linked list.
 * If you want to make this class work with the
 * for(Object o : mylist) {...} construct you
 * need to have MinList extend java.lang.Iterable.
 **/
public interface MinList extends Iterable
{
    /** Add an element to the end of the list. */
    public void add(Object o);

    /** Get the ith element of the list. */
    public Object get(int i);

    /** Set the ith element of the list. */
    public void set(int i,Object o);

    /** Return the length of the list. */
    public int size();

    /** Truncate the list to size zero. */
    public void clear();

    /** Put an element on the front of the list. */
    public void prepend(Object o);
}
