import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int MIN_CAPACITY = 8;

    private Item[] items;
    private int capacity;
    private int N;
    private int head;
    private int tail;

    public RandomizedQueue() {
        capacity = MIN_CAPACITY;
        items = (Item[]) new Object[capacity];
        N = 0;
        head = 0;
        tail = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private boolean isFull() {
        return tail - head == capacity || (head == tail && N > 0);
    }

    private Item at(int i) {
        if (i >= N) {
            throw new IndexOutOfBoundsException();
        }

        if (head + i <= capacity)
            return items[head + i];
        else
            return items[head + i - capacity];
    }

    private void expand() {
        Item[] newitems = (Item[]) new Object[capacity * 2];

        for (int i = 0; i < N; i++) {
            newitems[i] = at(i);
        }
        items = newitems;

        capacity *= 2;
        head = 0;
        tail = N;
    }

    private void shrink() {
        Item[] newitems = (Item[]) new Object[capacity / 2];

        for (int i = 0; i < N; i++) {
            newitems[i] = at(i);
        }
        items = newitems;

        capacity /= 2;
        head = 0;
        tail = N;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        items[tail] = item;

        tail++;
        if (tail >= capacity) {
            tail -= capacity;
        }
        N++;

        if (isFull()) {
            expand();
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized Queue underflow");
        }

        int indexToDequeue = StdRandom.uniform(N);
        Item item = at(indexToDequeue);

        if (indexToDequeue < N - 1) {
            int realIndexToDequeue = indexToDequeue + head;
            if (realIndexToDequeue >= capacity) {
                realIndexToDequeue -= capacity;
            }
            items[realIndexToDequeue] = items[tail - 1];
        }

        tail--;
        items[tail] = null;
        if (tail < 0) {
            tail += capacity;
        }
        N--;

        if (N < capacity / 4 && capacity > MIN_CAPACITY) {
            shrink();
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized Queue underflow");
        }
        return at(StdRandom.uniform(N));
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(this);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private int currenti;
        private Item[] items;
        private int N;

        public ListIterator(RandomizedQueue<Item> q) {
            N = q.size();
            items = (Item[]) new Object[N];

            for (int i = 0; i < N; ++i) {
                items[i] = q.at(i);
            }
            for (int i = 0; i < N; ++i) {
                int r = StdRandom.uniform(i + 1);
                Item temp = items[i];
                items[i] = items[r];
                items[r] = temp;
            }
        }

        public boolean hasNext() {
            return currenti < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return items[currenti++];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();

        for (int i = 0; i < 10; ++i) {
            q.enqueue(i);
        }
        for (int i = 0; i < 5; ++i) {
            for (int item : q) {
                StdOut.print(item);
                StdOut.print(' ');
            }
            StdOut.println();
        }
    }
}
