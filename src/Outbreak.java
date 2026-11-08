import java.util.*;

/**
 * Mission 5. Dynamic programming on the tree of infections. Root the tree in person 0.
 * Input.Tree.neighbours() gives the neighbours of every person. The number of people is at most about 100, so a recursive DFS is fine here.
 */
public class Outbreak {
    private static long[][] dp;
    private static boolean[] tested;

    /**
     * Choose people to test so that for every link (somebody infected somebody) at least one of the two is tested,
     * and the total cost of testing is as small as possible. cost[v] is the price of testing person v.
     * Returns tested[v] = true if person v is tested.
     *
     * State for every person v (looking only at v and everything below it): the smallest cost when v IS tested, and
     * the smallest cost when v is NOT tested. What must then be true about the children of v in each case?
     * Do not forget to rebuild the answer (who exactly is tested), not only the cost: the picture needs it.
     * Main calls this twice: with all costs equal to 1 (the line "Tests needed") and with the real costs ("Cheapest testing").
     */
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        dp = new long[n][2];
        tested = new boolean[n];

        if (n == 0) {
            return tested;
        }

        calculate(0, -1, nb, cost);

        boolean takeRoot = dp[0][1] <= dp[0][0];
        rebuild(0, -1, takeRoot, nb);

        return tested;
    }

    private static void calculate(int v, int parent,
                                  int[][] nb, int[] cost) {
        dp[v][0] = 0;
        dp[v][1] = cost[v];

        for (int u : nb[v]) {
            if (u == parent) {
                continue;
            }

            calculate(u, v, nb, cost);

            dp[v][0] += dp[u][1];
            dp[v][1] += Math.min(dp[u][0], dp[u][1]);
        }
    }

    private static void rebuild(int v, int parent,
                                boolean take, int[][] nb) {
        tested[v] = take;

        for (int u : nb[v]) {
            if (u == parent) {
                continue;
            }

            boolean takeChild;

            if (!take) {
                takeChild = true;
            } else {
                takeChild = dp[u][1] <= dp[u][0];
            }

            rebuild(u, v, takeChild, nb);
        }
    }

    private static int diameter;


    /**
     * The number of links on the longest chain of infections, that is the longest path in the tree (counted in links).
     * A path goes up from one person and down to another; for every person you need the two longest ways down.
     * Example: a line 0-1-2-3 has chain 3; a star with centre 0 and leaves 1, 2, 3 has chain 2.
     */
    public static int longestChain(int n, int[][] nb) {
        if (n == 0) {
            return 0;
        }

        diameter = 0;
        depth(0, -1, nb);

        return diameter;
    }

    private static int depth(int v, int parent, int[][] nb) {
        int first = 0;
        int second = 0;

        for (int u : nb[v]) {
            if (u == parent) {
                continue;
            }

            int d = depth(u, v, nb) + 1;

            if (d > first) {
                second = first;
                first = d;
            } else if (d > second) {
                second = d;
            }
        }

        diameter = Math.max(diameter, first + second);

        return first;
    }
}
