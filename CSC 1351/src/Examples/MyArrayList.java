package Examples;

import java.util.Iterator;

/**
 * Implement the MinList using arrays.
 **/
public class MyArrayList implements MinList {
    /** Internal storage for data. May be bigger than what size() reports.*/
    Object[] array;
    /** The current end of the array.*/
    int end_array;

    /** Initialize with storage space for 10 elements. This is
      * is arbitrary.
      **/
    public MyArrayList() {
        array = new Object[10];
        end_array = 0;
    }

    /** We only need to change our notion of the current end
      * of the array. Because we don't null out the elements of
      * the array, the garbage collector won't be able to reclaim
      * elements.
      */
    @Override
    public void clear() {
        end_array = 0;
    }

    /** Report the current size of the array. */
    @Override
    public int size() {
        return end_array;
    }

    @Override
    public void set(int n,Object o) {
        // Run with -ea to enable assertions
        // Without assertions, we might succeed in
        // setting elements past the logical
        // end of the array.
        assert(n < end_array);
        array[n] = o;
    }
    
    @Override
    public Object get(int n) {
        // Run with -ea to enable assertions
        // Without assertions, we might succeed in
        // getting elements past the logical
        // end of the array.
        assert(n < end_array);
        return array[n];
    }

    @Override
    public void add(Object o) {
        // Make sure we have room to add an element
        if(end_array == array.length) {
            // Need to grow by some factor.
            int n = (int)(1.3*array.length);
            Object[] temp = new Object[n];
            for(int i=0;i<end_array;i++)
                temp[i] = array[i];
            array = temp;
        }
        // Add the element
        array[end_array] = o;
        end_array++;
    }

    /** Copy elements from array to temp, right shifting
      * as we do. Both arguments could be the same array,
      * in which case this is an in-place shift.
      **/
    private void rshift(Object[] temp,Object[] array) {
        for(int i=end_array;i>0;i--) {
            temp[i] = array[i-1];
        }
    }

    @Override
    public void prepend(Object o) {
        if(end_array == array.length) {
            // Need to grow by some factor.
            int n = (int)(1.3*array.length);
            Object[] temp = new Object[n];
            rshift(temp,array);
            array = temp;
        } else {
            rshift(array,array);
        }
        array[0] = o;
        end_array++;
    }

    /**
     * Test our implementation.
     **/
    public static void main(String[] args) {
        MinList ml = new MyArrayList();
        for(int i=0;i<10;i++)
            ml.prepend(i);
        for(int i=0;i<ml.size();i++)
            System.out.println(ml.get(i));
        System.out.println(ml);
        Iterator i = ml.iterator();
        while(i.hasNext()) {
            int n = (Integer)i.next();
            if(n == 0 || n == 6 || n == 9)
                i.remove();
        }
        for(Object o : ml) {
            System.out.println("o="+o);
        }
        System.out.println(ml);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        for(int i=0;i<size();i++) {
            if(i > 0) sb.append(',');
            sb.append(array[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int counter = 0;
            public boolean hasNext() {
                return counter < end_array;
            }
            public Object next() {
                return array[counter++];
            }
            public void remove() {
                for(int i=counter;i<end_array;i++) {
                    array[i-1] = array[i];
                }
                end_array--;
            }
        };
    }

    public void remove(int index) {
        for(int i=index;i+1<end_array;i++) {
            array[i] = array[i+1];
        }
        end_array--;
    }
}
