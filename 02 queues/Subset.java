public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        for (int i = 0; !q.isEmpty() && i < n; ++i) {
            StdOut.println(q.dequeue());
        }
    }
}
