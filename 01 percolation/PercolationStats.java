public class PercolationStats {
    private double[] fractions;

    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }

        fractions = new double[T];

        for (int i = 0; i < T; ++i) {
            Percolation perc = new Percolation(N);

            int numOpen = 0;
            while (!perc.percolates()) {
                int m = StdRandom.uniform(1, N + 1);
                int n = StdRandom.uniform(1, N + 1);

                if (!perc.isOpen(m, n)) {
                    perc.open(m, n);
                    ++numOpen;
                }
            }

            fractions[i] = (double) numOpen / ((double) N * N);
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(fractions.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(fractions.length);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        StdRandom.setSeed(System.currentTimeMillis());

        PercolationStats ps = new PercolationStats(N, T);

        StdOut.print("mean                    = ");
        StdOut.println(ps.mean());
        StdOut.print("stddev                  = ");
        StdOut.println(ps.stddev());
        StdOut.print("95% confidence interval = ");
        StdOut.print(ps.confidenceLo());
        StdOut.print(", ");
        StdOut.println(ps.confidenceHi());
    }
}
