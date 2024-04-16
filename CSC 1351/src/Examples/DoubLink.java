package Examples;

public class DoubLink implements DoubList {
    static class Entry {
        Entry next, prev;
        int data;
    }
    Entry head=null, tail = null;
    public DoubLink() {}
    public void pushBack(int v) { // Queue.add, Stack.push
        Entry e = new Entry();
        e.data = v;
        if(head == null) {
            head = tail = e;
        } else {
            // a -> b -> c
            //   <-   <-
            // tail -> c
            tail.next = e;
            // a -> b -> c -> e
            //   <-   <-
            // tail -> c
            e.prev = tail;
            // a -> b -> c -> e
            //   <-   <-   <-
            // tail -> c
            tail = e;
            // a -> b -> c -> e
            //   <-   <-   <-
            // tail -> e
        }
    }
    public int popBack() { // Stack.pop
        // a -> b -> c
        //   <-   <-
        // tail -> c
        Entry e = tail;
        tail = tail.prev;
        // a -> b -> c
        //   <-   <-
        // tail -> b
        tail.next = null;
        // a -> b    c
        //   <-   <-
        // tail -> b
        return e.data;
    }
    public void pushFront(int v) { // neither
        Entry e = new Entry();
        e.data = v;
        if(head == null) {
            head = tail = e;
        } else {
            head.prev = e;
            e.next = head;
            head = e;
        }
    }
    public int popFront() { // Quoue.remove
        Entry e = head;
        head = head.next;
        head.prev = null;
        return e.data;
    }
    public IntIterator forward() {
        return new IntIterator() {
            Entry e;
            {
                e = new Entry();
                e.next = head;
            }
            public boolean hasNext() {
                return e.next != null;
            }
            public int next() {
                e = e.next;
                return e.data;
            }
        };
    }
    public IntIterator backward() {
        return new IntIterator() {
            Entry e;
            {
                e = new Entry();
                e.prev = tail;
            }
            public boolean hasNext() {
                return e.prev != null;
            }
            public int next() {
                e = e.prev;
                return e.data;
            }
        };
    }
    public static void main(String[] args) {
        DoubLink dl = new DoubLink();
        dl.pushBack(1);
        dl.pushBack(2);
        dl.pushFront(0);
        IntIterator i = dl.forward();
        while(i.hasNext())
            System.out.println(i.next());
        i = dl.backward();
        while(i.hasNext())
            System.out.println(i.next());
    }
}
