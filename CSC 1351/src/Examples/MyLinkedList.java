package Examples;

import java.util.Iterator;

public class MyLinkedList implements MinList {
    /**
     * Use this inner class to make the links
     * of our LinkedList.
     **/
    static class Link {
        Object data;
        Link next;
        Link() {}
        Link(Object data) { this.data = data; }
        Link(Link next) { this.next = next; }
        Link(Object data,Link next) { this.data = data; this.next = next; }
    }

    /** The head or first element of the list. */
    Link head;
    /** The tail of the list. Keeping it is an optimization. */
    Link tail;
    /** The size of the list. */
    int size;

    public MyLinkedList() {
        size = 0;
    }

    /**
     * Get the nth element. Requires walking through
     * the n times.
     **/
    @Override
    public Object get(int n) {
        assert(n < size);
        Link x = head;
        for(int counter=0;counter<n;counter++) {
            x = x.next;
        }
        return x.data;
    }

    /**
     * Set the nth element. Requires walking through
     * the n times.
     **/
    @Override
    public void set(int n,Object o) {
        assert(n < size);
        Link x = head;
        for(int counter=0;counter<n;counter++) {
            x = x.next;
        }
        x.data = o;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public void add(Object o) {
        size++;
        Link link = new Link(o);
        // This is how we implemented
        // things before we added the
        // tail field to the class.
        /*
        if(head == null) {
            head = link;
        } else {
            Link x = head;
            for(; x.next != null;x = x.next)
                ;
            x.next = link;
        }
        */
        if(head == null) {
            head = tail = link;
        } else {
            tail.next = link;
            tail = link;
        }
    }

    @Override
    public int size() {
        return size;
    }

    /** Much easier than what we had to do in MyArrayList! */
    @Override
    public void prepend(Object o) {
        Link temp = new Link(o,head);
        if(head == null)
            tail = temp;
        head = temp;
        size++;
    }

    /**
     * Test our implementation.
     **/
    public static void main(String[] args) {
        MinList ml = new MyLinkedList();
        System.out.println("prepend ten");
        for(int i=0;i<10;i++)
            ml.prepend(i);
        System.out.println(ml);
        System.out.println("iterate #1");
        for(int i=0;i<ml.size();i++)
            System.out.println(ml.get(i));
        System.out.println("iterate #2");
        for(Object o : ml)
            System.out.println(o);
        System.out.println("iterate #3, with 0, 6, and 9 removed");
        Iterator i = ml.iterator();
        while(i.hasNext()) {
            int n = (Integer)i.next();
            if(n == 6||n == 9||n == 0)
                i.remove();
        }
        for(Object o : ml)
            System.out.println(o);
        System.out.println("iterate over nothing");
        for(Object o : new MyLinkedList())
            System.out.println("o="+o);
        System.out.println("iterate over 1 thing");
        ml = new MyLinkedList();
        ml.add(3);
        i = ml.iterator();
        while(i.hasNext()) {
            System.out.println(i.next());
            i.remove();
        }
        System.out.println("iterate over nothing");
        for(Object o : ml) {
            System.out.println("o="+o);
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            // Start before head
            Link cur = new Link(head);
            Link prev = null;
            public boolean hasNext() {
                return cur.next != null;
            }
            public Object next() {
                prev = cur;
                cur = cur.next;
                return cur.data;
            }
            public void remove() {
                if(cur == head)
                    head = cur.next;
                else
                    prev.next = cur.next;
            }
        };
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        //char sep = '[';
        sb.append('[');
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            Object o = iter.next();
            sb.append(o); /** implicity calls toString */
            if (iter.hasNext()) {
                sb.append(',');
            }
        }
        // for(Object o : this) {
        //     sb.append(sep);
        //     sb.append(o);
        //     sep = ',';
        // }
        sb.append(']');
        return sb.toString();
    }
}
