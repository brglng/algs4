public class Percolation {
    private WeightedQuickUnionUF uf, uf1;
    private boolean[][] sites;
    private int N;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.N = N;
        this.uf = new WeightedQuickUnionUF(N * N + 1);
        this.uf1 = new WeightedQuickUnionUF(N * N + 2);
        this.sites = new boolean[N][N];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                sites[i - 1][j - 1] = false;
            }
        }
    }

    public void open(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        this.sites[i - 1][j - 1] = true;

        if (i == 1) {
            uf.union(N * N, (i - 1) * N + j - 1);           // virtual top
            uf1.union(N * N, (i - 1) * N + j - 1);           // virtual top
        }

        if (i > 1) {
            if (isOpen(i - 1, j)) {
                uf.union((i - 1) * N + j - 1, (i - 1 - 1) * N + j - 1);
                uf1.union((i - 1) * N + j - 1, (i - 1 - 1) * N + j - 1);
            }
        }

        if (j < N) {
            if (isOpen(i, j + 1)) {
                uf.union((i - 1) * N + j - 1, (i - 1) * N + j - 1 + 1);
                uf1.union((i - 1) * N + j - 1, (i - 1) * N + j - 1 + 1);
            }
        }

        if (i < N) {
            if (isOpen(i + 1, j)) {
                uf.union((i - 1) * N + j - 1, (i - 1 + 1) * N + j - 1);
                uf1.union((i - 1) * N + j - 1, (i - 1 + 1) * N + j - 1);
            }
        }

        if (j > 1) {
            if (isOpen(i, j - 1)) {
                uf.union((i - 1) * N + j - 1, (i - 1) * N + j - 1 - 1);
                uf1.union((i - 1) * N + j - 1, (i - 1) * N + j - 1 - 1);
            }
        }

        if (i == N) {
            uf1.union(N * N + 1, (i - 1) * N + j - 1);  // virtual bottom
        }
    }

    public boolean isOpen(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return sites[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return (isOpen(i, j) && uf.connected(N * N, (i - 1) * N + j - 1));
    }

    public boolean percolates() {
        return uf1.connected(N * N, N * N + 1);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);

        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 1);

        assert !p.isFull(3, 1);
        assert p.percolates();
    }
}