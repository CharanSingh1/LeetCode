import java.util.*;

class Solution {

    class MedianWindow {

        TreeMap<Integer, Integer> low = new TreeMap<>();
        TreeMap<Integer, Integer> high = new TreeMap<>();

        int lowSize = 0, highSize = 0;
        long lowSum = 0, highSum = 0;

        void add(int val) {

            if (low.isEmpty() || val <= low.lastKey()) {
                low.put(val, low.getOrDefault(val, 0) + 1);
                lowSize++;
                lowSum += val;
            } else {
                high.put(val, high.getOrDefault(val, 0) + 1);
                highSize++;
                highSum += val;
            }

            balance();
        }

        void remove(int val) {

            if (low.containsKey(val)) {
                removeOne(low, val);
                lowSize--;
                lowSum -= val;
            } else {
                removeOne(high, val);
                highSize--;
                highSum -= val;
            }

            balance();
        }

        void removeOne(TreeMap<Integer, Integer> map, int val) {

            int count = map.get(val);

            if (count == 1) {
                map.remove(val);
            } else {
                map.put(val, count - 1);
            }
        }

        void balance() {

            int target = (lowSize + highSize + 1) / 2;

            while (lowSize > target) {

                int val = low.lastKey();

                removeOne(low, val);
                lowSize--;
                lowSum -= val;

                high.put(val, high.getOrDefault(val, 0) + 1);
                highSize++;
                highSum += val;
            }

            while (lowSize < target) {

                int val = high.firstKey();

                removeOne(high, val);
                highSize--;
                highSum -= val;

                low.put(val, low.getOrDefault(val, 0) + 1);
                lowSize++;
                lowSum += val;
            }
        }

        long getCost() {

            int median = low.lastKey();

            return (long) median * lowSize - lowSum
                    + highSum - (long) median * highSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        long[] cost = new long[n - x + 1];

        MedianWindow window = new MedianWindow();

        // First window
        for (int i = 0; i < x; i++) {
            window.add(nums[i]);
        }

        cost[0] = window.getCost();

        // Sliding window
        for (int i = 1; i <= n - x; i++) {

            window.remove(nums[i - 1]);
            window.add(nums[i + x - 1]);

            cost[i] = window.getCost();
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 2;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][0] = 0;
        }

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= k; j++) {

                // Skip current element
                dp[i][j] = dp[i - 1][j];

                // Select a window of size x
                if (i >= x) {

                    long previous = dp[i - x][j - 1];

                    if (previous != INF) {

                        dp[i][j] = Math.min(
                            dp[i][j],
                            previous + cost[i - x]
                        );
                    }
                }
            }
        }

        return dp[n][k];
    }
}