import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class NodeImp<T extends Comparator<T>> extends Node {
    public NodeImp(Object k, Object v, T c) {
        super(k, v, c);
    }

    @Override
    public Node rotateLeft() {
        if (this.right == null) return this;

        Node newRoot = this.right;
        this.right = newRoot.left;
        newRoot.left = this;

        return newRoot;
    }

    @Override
    public Node rotateRight() {
        if (this.left == null) return this; 

        Node newRoot = this.left;
        this.left = newRoot.right;
        newRoot.right = this;

        return newRoot;
    }

    @Override
    public int height() {
        int leftHeight = this.left == null ? 0 : this.left.height();
        int rightHeight = this.right == null ? 0 : this.right.height();
        return 1 + Math.max(leftHeight, rightHeight);
    }

    @Override
    public int size() {
        int leftSize = this.left == null ? 0 : this.left.size();
        int rightSize = this.right == null ? 0 : this.right.size();
        return 1 + leftSize + rightSize;
    }

    @Override
    public Object get(Object key) {
        @SuppressWarnings("unchecked")
        int d = this.c.compare(key, this.key);
        if (d == 0) {
            return this.value;
        } else if (d > 0) {
            return this.right == null ? null : this.right.get(key);
        } else {
            return this.left == null ? null : this.left.get(key);
        }
    }

    @Override
    public void put(Object key, Object value) {
        @SuppressWarnings("unchecked")
        int d = this.c.compare(key, this.key);
        if (d == 0) {
            this.value = value;
        } else if (d > 0) {
            if (this.right == null) {
                this.right = new NodeImp<>(key, value, c); 
            } else {
                this.right.put(key, value);
            }
        } else {
            if (this.left == null) {
                this.left = new NodeImp<>(key, value, c); 
            } else {
                this.left.put(key, value);
            }
        }
    }

    @Override
    public Iterator<Node> iterator() {
        Stack<Node> stack = new Stack<>();
        Node smallest = this;  

        
        while (smallest != null) {
            stack.push(smallest);  
            smallest = smallest.left;  
        }

        return new Iterator<Node>() { 
            private Node node = stack.isEmpty() ? null : stack.pop();  // Initialize the first node

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Node next() { 
                if (!hasNext()) throw new NoSuchElementException();
                Node ret = node; 

                node = node.right;
                
                if (node == null && !stack.isEmpty()) {
                    node = stack.pop();
                }

                return ret;
            }
        };
    }
}