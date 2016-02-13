import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node<Item> first;
    private Node<Item> last;

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
    }

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node<Item> oldfirst = first;

        first = new Node<Item>();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;

        if (oldfirst == null) {
            last = first;
        } else {
            oldfirst.prev = first;
        }

        N++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node<Item> oldlast = last;

        last = new Node<Item>();
        last.item = item;
        last.prev = oldlast;
        last.next = null;

        if (first == null) {
            first = last;
        } else {
            oldlast.next = last;
        }

        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }

        Item item = first.item;

        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }

        N--;

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }

        Item item = last.item;

        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }

        N--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> q = new Deque<Integer>();

        try {
            q.removeFirst();
            assert false;
        } catch (NoSuchElementException e) {
            StdOut.println("test 1 passed");
        }

        try {
            q.removeLast();
            assert false;
        } catch (NoSuchElementException e) {
            StdOut.println("test 2 passed");
        }

        q.addFirst(3);
        q.addFirst(2);
        q.addFirst(1);
        assert q.removeLast() == 3;
        assert q.removeLast() == 2;
        assert q.removeLast() == 1;

        StdOut.println("test 3 passed");

        q.addLast(1);
        q.addLast(2);
        q.addLast(3);
        assert q.removeFirst() == 1;
        assert q.removeFirst() == 2;
        assert q.removeFirst() == 3;

        StdOut.println("test 4 passed");

        q.addLast(1);
        q.addLast(2);
        q.addLast(3);
        q.addLast(4);
        q.addLast(5);

        Iterator<Integer> it = q.iterator();
        assert it.next() == 1;
        assert it.next() == 2;
        assert it.next() == 3;
        assert it.next() == 4;
        assert it.next() == 5;
        try {
            it.next();
            assert false;
        } catch (NoSuchElementException e) {
            StdOut.println("test 5 passed");
        }

        try {
            q.addFirst(null);
            assert false;
        } catch (NullPointerException e) {
            StdOut.println("test 6 passed");
        }

        try {
            q.addLast(null);
            assert false;
        } catch (NullPointerException e) {
            StdOut.println("test 7 passed");
        }
    }
}
