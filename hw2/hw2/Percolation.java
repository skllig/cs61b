package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayDeque;
import java.util.Deque;

public class Percolation {

    private int[] sites;
    private int N;
    private final int OPEN = 1;
    private final int FULL = 2;
    private int openCnt;
    private WeightedQuickUnionUF uf;
    private int ceiling, floor;
    private final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private Deque<Integer> que;

    /**
     * Create N-by-N grid, with all sites initially blocked.
     * @param N
     */
    public Percolation(int N) {
        this.N = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        sites = new int[N * N];
        openCnt = 0;
        ceiling = N * N;
        floor = N * N + 1;
        que = new ArrayDeque<>();
    }

    /**
     * Return index of the given (row, col).
     * @param row
     * @param col
     * @return
     */
    private int getIndex(int row, int col) {
        return row * this.N + col;
    }

    private boolean isCoordinateValid(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }

    private void affectNeighbors(int row, int col) {
        que.addLast(row);
        que.addLast(col);
        while (!que.isEmpty()) {
            int r = que.pollFirst();
            int c = que.pollFirst();
            for (int[] coor : DIRECTIONS) {
                int x = r + coor[0];
                int y = c + coor[1];
                if (isCoordinateValid(x, y) && isOpen(x, y) && !isFull(x, y)) {
                    que.addLast(x);
                    que.addLast(y);
                    sites[getIndex(x, y)] = FULL;
                }
            }
        }
    }

    private void unionNeighbors(int row, int col) {
        for (int[] coor : DIRECTIONS) {
            int x = row + coor[0];
            int y = col + coor[1];
            if (isCoordinateValid(x, y) && isOpen(x, y)) {
                uf.union(getIndex(row, col), getIndex(x, y));

                if (isFull(x, y)) {
                    affectNeighbors(x, y);
                }
                if (isFull(row, col)) {
                    affectNeighbors(row, col);
                }

            }
        }
    }

    /**
     * Open the site (row, col) if it is not open already.
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (isFull(row, col)) {
            return;
        }
        int idx = getIndex(row, col);
        sites[idx] = OPEN;
        openCnt += 1;

        if (row == 0) {
            uf.union(idx, ceiling);
            sites[idx] = FULL;
        } else if (row == N - 1) {
            uf.union(idx, floor);
        }

        unionNeighbors(row, col);
    }

    /**
     * Is the site (row, col) open?
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        return this.sites[getIndex(row, col)] > 0;
    }

    /**
     * Is the site (row, col) full?
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        return this.sites[getIndex(row, col)] == FULL;
    }

    /**
     * Return number of open sites.
     * @return
     */
    public int numberOfOpenSites() {
        return openCnt;
    }

    /**
     * Does the system percolates?
     * @return
     */
    public boolean percolates() {
        return uf.connected(ceiling, floor);
    }

    /**
     * Use for unit testing (not required)
     * @param args
     */
    public static void main(String[] args) {
    }
}












