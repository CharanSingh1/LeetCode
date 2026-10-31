class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1_000_000_007;

        int[][] nextRight = new int[m][n];
        int[][] nextDown = new int[m][n];

        // Step 1: Precompute mirror movements
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                int pos = i * n + j;

                if (grid[i][j] == 0) {
                    nextRight[i][j] = pos;
                    nextDown[i][j] = pos;
                } else {

                    // Entering mirror while moving RIGHT
                    if (i + 1 < m) {
                        nextRight[i][j] = nextDown[i + 1][j];
                    } else {
                        nextRight[i][j] = -1;
                    }

                    // Entering mirror while moving DOWN
                    if (j + 1 < n) {
                        nextDown[i][j] = nextRight[i][j + 1];
                    } else {
                        nextDown[i][j] = -1;
                    }
                }
            }
        }

        // Step 2: Dynamic Programming
        long[][] dp = new long[m][n];

        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dp[i][j] == 0) {
                    continue;
                }

                // Move RIGHT
                if (j + 1 < n) {

                    int target = nextRight[i][j + 1];

                    if (target != -1) {

                        int r = target / n;
                        int c = target % n;

                        dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                    }
                }

                // Move DOWN
                if (i + 1 < m) {

                    int target = nextDown[i + 1][j];

                    if (target != -1) {

                        int r = target / n;
                        int c = target % n;

                        dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}