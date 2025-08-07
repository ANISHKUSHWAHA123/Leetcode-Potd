/* 
3363. Find the Maximum Number of Fruits Collected

Target :- Collect max no of fruits

Constrains:-
1. only (n-1) moves is allowed to each children.
2. C1(top, left) can only traverse through main diagonal.
3. C2(top, right) can have only upper traingale to traverse beyond main diagonal.
4. C3(bottom, left) can have only lower traiangle to traverse beyond main diagonal.
*/

class Solution {
    private static final int INF = Integer.MAX_VALUE;
    private int n;
    private int[][] fruits;
    private int[][] memo;
    private List<int[]> dirs;

    // DFS + memoization
    private int dfs(int row, int col, int moves) {
        // Reached bottom-right?
        if (row == n - 1 && col == n - 1) {
            return (moves == 0) ? 0 : INF;
        }
        // No moves left or on main diagonal (forbidden)
        if (moves == 0 || row == col) {
            return INF;
        }
        if (memo[row][col] != -1) {
            return memo[row][col];
        }
        long best = -1;
        for (int[] d : dirs) {
            int nr = row + d[0], nc = col + d[1];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                int val = dfs(nr, nc, moves - 1);
                if (val != INF) {
                    best = Math.max(best, (long) val);
                }
            }
        }
        int result = (best < 0 ? INF : fruits[row][col] + (int) best);
        memo[row][col] = result;
        return result;
    }

    public int maxCollectedFruits(int[][] fruits) {
        this.n = fruits.length;
        this.fruits = fruits;

        // 1) Sum of main diagonal
        long first = 0;
        for (int i = 0; i < n; i++) {
            first += fruits[i][i];
        }

        // 2) Second path: from top-right to bottom-right (down-left/down/down-right)
        dirs = Arrays.asList(
            new int[]{1, -1},
            new int[]{1, 0},
            new int[]{1, 1}
        );
        memo = new int[n][n];
        for (int[] row : memo) Arrays.fill(row, -1);
        int second = dfs(0, n - 1, n - 1);

        // 3) Third path: from bottom-left to bottom-right (up-right/right/down-right)
        dirs = Arrays.asList(
            new int[]{-1, 1},
            new int[]{0, 1},
            new int[]{1, 1}
        );
        for (int[] row : memo) Arrays.fill(row, -1);
        int third = dfs(n - 1, 0, n - 1);

        return (int) (first + second + third);
    }
}
