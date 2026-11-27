class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // Calculate free copies earned by activating each item type
        int[] bonus = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    bonus[i]++;
                }
            }
        }

        // DP for buying the first copy of selected item types
        int[] activation = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            int reward = 1 + bonus[i];

            // Each item type can be activated only once
            for (int b = budget; b >= price; b--) {
                activation[b] = Math.max(
                    activation[b],
                    activation[b - price] + reward
                );
            }
        }

        // Unbounded knapsack for additional purchased copies
        int[] regular = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int price = items[i][1];

            // Unlimited copies are allowed
            for (int b = price; b <= budget; b++) {
                regular[b] = Math.max(
                    regular[b],
                    regular[b - price] + 1
                );
            }
        }

        // Combine activation rewards and additional purchases
        int ans = 0;

        for (int b = 0; b <= budget; b++) {
            ans = Math.max(ans, activation[b] + regular[budget - b]);
        }

        return ans;
    }
}