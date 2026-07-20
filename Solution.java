public class Solution {

    /**
     * Returns the length of the longest valid (well-formed) parentheses substring.
     *
     * Approach: Two-pointer / counter method with two passes.
     * Time Complexity:  O(n) — two linear scans over the string.
     * Space Complexity: O(1) — only a few counter variables used.
     */
    public int longestValidParentheses(String s) {
        int left = 0;    // count of '(' seen so far in current window
        int right = 0;   // count of ')' seen so far in current window
        int maxLen = 0;  // stores the answer (longest valid length found)

        // -------- Pass 1: Left to Right --------
        // This pass correctly handles substrings that have extra ')' at some point.
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;           // opening bracket → increment left count
            } else {
                right++;          // closing bracket → increment right count
            }

            if (left == right) {
                // Balanced window: valid substring of length 2 * right
                maxLen = Math.max(maxLen, 2 * right);
            } else if (right > left) {
                // Too many closing brackets → this window can't be valid anymore.
                // Reset both counters and start fresh from next index.
                left = 0;
                right = 0;
            }
        }

        // -------- Pass 2: Right to Left --------
        // Needed to handle substrings that have extra '(' left over
        // (e.g. "(()"), which Pass 1 would miss.
        left = 0;
        right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;           // opening bracket
            } else {
                right++;          // closing bracket
            }

            if (left == right) {
                // Balanced window: valid substring of length 2 * left
                maxLen = Math.max(maxLen, 2 * left);
            } else if (left > right) {
                // Too many opening brackets → reset and start fresh.
                left = 0;
                right = 0;
            }
        }

        return maxLen;
    }

    // -------- Driver code to test the solution --------
    public static void main(String[] args) {
        Solution sol = new Solution();

        System.out.println(sol.longestValidParentheses("(()"));     // Expected: 2
        System.out.println(sol.longestValidParentheses(")()())"));  // Expected: 4
        System.out.println(sol.longestValidParentheses(""));        // Expected: 0
        System.out.println(sol.longestValidParentheses("()(()"));   // Expected: 2
        System.out.println(sol.longestValidParentheses("()(())"));  // Expected: 6
    }
}