
class Solution {
    public int minMoves(int[] nums) {

        // Step 1: Find minimum element
        int min = nums[0];

        for (int i = 1; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
        }

        // Step 2: Calculate total moves
        int moves = 0;

        for (int i = 0; i < nums.length; i++) {
            moves += nums[i] - min;
        }

        // Step 3: Return answer
        return moves;
    }
}
