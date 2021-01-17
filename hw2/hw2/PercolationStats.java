package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int N, T;
    private PercolationFactory pf;
    private double[] data;
    /**
     * perform T independent experiments on an N-by-N grid
     * @param N     Side length of grid
     * @param T     Experiment times
     * @param pf    Fatory to create Percolation instance
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        this.T = T;
        this.pf = pf;
        data = new double[T];

        test();
    }

    /**
     * Perform experiments T times.
     */
    private void test() {
        for (int i = 0; i < T; i++) {
            Percolation canvas = pf.make(N);
            int totalCount = N * N;
            int openCount = 0;
            while (!canvas.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                if (!canvas.isOpen(row, col)) {
                    openCount += 1;
                    canvas.open(row, col);
                }
            }
            data[i] = (double) openCount / totalCount;
        }
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        return StdStats.mean(data);
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        return StdStats.stddev(data);
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLow() {
        double mean = mean();
        double stdDev = stddev();
        return mean - 1.96 * stdDev / Math.sqrt(T);
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHigh() {
        double mean = mean();
        double stdDev = stddev();
        return mean + 1.96 * stdDev / Math.sqrt(T);
    }

}
