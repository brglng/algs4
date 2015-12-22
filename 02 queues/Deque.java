import java.util.Iterator;
import java.util.NoSuchElementException;

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
        if (q.removeLast() != 3) throw new AssertionError();
        if (q.removeLast() != 2) throw new AssertionError();
        if (q.removeLast() != 1) throw new AssertionError();

        StdOut.println("test 3 passed");

        q.addLast(1);
        q.addLast(2);
        q.addLast(3);
        if (q.removeFirst() != 1) throw new AssertionError();
        if (q.removeFirst() != 2) throw new AssertionError();
        if (q.removeFirst() != 3) throw new AssertionError();

        StdOut.println("test 4 passed");

        q.addLast(1);
        q.addLast(2);
        q.addLast(3);
        q.addLast(4);
        q.addLast(5);

        Iterator<Integer> it = q.iterator();
        if (it.next() != 1) throw new AssertionError();
        if (it.next() != 2) throw new AssertionError();
        if (it.next() != 3) throw new AssertionError();
        if (it.next() != 4) throw new AssertionError();
        if (it.next() != 5) throw new AssertionError();
        try {
            it.next();
            throw new AssertionError();
        } catch (NoSuchElementException e) {
            StdOut.println("test 5 passed");
        }

        try {
            q.addFirst(null);
            throw new AssertionError();
        } catch (NullPointerException e) {
            StdOut.println("test 6 passed");
        }

        try {
            q.addLast(null);
            throw new AssertionError();
        } catch (NullPointerException e) {
            StdOut.println("test 7 passed");
        }
    }
}
