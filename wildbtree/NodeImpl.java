import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class NodeImpl extends Node {
    public NodeImpl(Object k, Object v, Comparator c) {
        super(k, v, c);
    }

    @Override
    public Node rotateLeft() {
        if (this.right == null) {
            return this;
        } else {
            Node newRoot = this.right;
            this.right = newRoot.left;
            newRoot.left = this;
            return newRoot;
        }
    }

    @Override
    public Node rotateRight() {
        if (this.left == null) {
            return this;
        } else {
            Node newRoot = this.left;
            this.left = newRoot.right;
            newRoot.right = this;
            return newRoot;
        }
    }

    @Override
    public int height() {
        int leftHeight;
        if (this.left == null) {
            leftHeight = 0;
        } else {
            leftHeight = this.left.height();
        }

        int rightHeight;
        if (this.right == null) {
            rightHeight = 0;
        } else {
            rightHeight = this.right.height();
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }

    @Override
    public int size() {
        int leftSize;
        if (this.left == null) {
            leftSize = 0;
        } else {
            leftSize = this.left.size();
        }

        int rightSize;
        if (this.right == null) {
            rightSize = 0;
        } else {
            rightSize = this.right.size();
        }

        return 1 + leftSize + rightSize;
    }

    @Override
    public Object get(Object key) {
        int diff = this.c.compare(key, this.key);
        if (diff == 0) {
            return this.value;
        } else if (diff > 0) {
            if (this.right == null) {
                return null;
            } else {
                return this.right.get(key);
            }
        } else {
            if (this.left == null) {
                return null;
            } else {
                return this.left.get(key);
            }
        }
    }

    @Override
    public void put(Object key, Object value) {
        int diff = this.c.compare(key, this.key);
        if (diff == 0) {
            this.value = value;
        } else if (diff > 0) {
            if (this.right == null) {
                this.right = new NodeImp(key, value, c);
            } else {
                this.right.put(key, value);
            }
        } else {
            if (this.left == null) {
                this.left = new NodeImp(key, value, c);
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
            private Node node;
            {
                if (stack.isEmpty()) {
                    node = null;
                } else {
                    node = stack.pop();
                }
            }

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Node next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node ret = node;
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                if (node == null && !stack.isEmpty()) {
                    node = stack.pop();
                }
                return ret;
            }
        };
    }
}
