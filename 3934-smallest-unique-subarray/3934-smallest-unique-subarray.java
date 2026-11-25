import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int[] sa = buildSuffixArray(nums);
        int[] rank = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        // Kasai algorithm
        int h = 0;
        for (int i = 0; i < n; i++) {
            int r = rank[i];

            if (r == 0) {
                h = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n
                    && nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int r = 0; r < n; r++) {
            int maxLcp = lcp[r];

            if (r + 1 < n) {
                maxLcp = Math.max(maxLcp, lcp[r + 1]);
            }

            int uniqueLength = maxLcp + 1;
            int suffixLength = n - sa[r];

            if (uniqueLength <= suffixLength) {
                ans = Math.min(ans, uniqueLength);
            }
        }

        return ans;
    }

    private int[] buildSuffixArray(int[] nums) {
        int n = nums.length;
        Integer[] sa = new Integer[n];
        int[] rank = nums.clone();

        for (int i = 0; i < n; i++) {
            sa[i] = i;
        }

        for (int k = 1; k < n; k *= 2) {
            final int step = k;
            final int[] currentRank = rank;

            Arrays.sort(sa, (a, b) -> {
                if (currentRank[a] != currentRank[b]) {
                    return Integer.compare(currentRank[a], currentRank[b]);
                }

                int ra = a + step < n ? currentRank[a + step] : -1;
                int rb = b + step < n ? currentRank[b + step] : -1;

                return Integer.compare(ra, rb);
            });

            int[] newRank = new int[n];
            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                int a1 = rank[a];
                int b1 = rank[b];

                int a2 = a + k < n ? rank[a + k] : -1;
                int b2 = b + k < n ? rank[b + k] : -1;

                newRank[b] = newRank[a] +
                        ((a1 != b1 || a2 != b2) ? 1 : 0);
            }

            rank = newRank;

            if (rank[sa[n - 1]] == n - 1) {
                break;
            }
        }

        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = sa[i];
        }

        return result;
    }
}