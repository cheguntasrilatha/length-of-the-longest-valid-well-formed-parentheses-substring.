# length-of-the-longest-valid-well-formed-parentheses-substring.
Longest Valid Parentheses

Given a string containing just the characters '(' and ')', return the length of the longest valid (well-formed) parentheses substring.


📌 Problem Statement

A parentheses substring is valid if:


Every opening bracket ( has a matching closing bracket ).
Brackets are correctly nested and ordered.


We need the length of the longest such contiguous substring.

Examples

InputOutputExplanation"(()"2The longest valid substring is "()".")()())"4The longest valid substring is "()()".""0Empty string, no valid substring.

Constraints


0 <= s.length <= 3 * 10^4
s[i] is '(' or ')'



💡 Approach: Two-Pointer (Counter) Method

Instead of using a stack, we track two counters and scan the string twice — once in each direction. This achieves O(1) space.

Core Idea


Count ( as left and ) as right.
Whenever left == right, we have a balanced (valid) window → its length is 2 * right.
Whenever the counts become impossible to balance, reset both counters to 0 and start fresh.


Why Two Passes?

PassResets whenCatches substrings with excessLeft → Rightright > leftextra ) closing bracketsRight → Leftleft > rightextra ( opening brackets

A single left-to-right pass fails on inputs like "(()" because left stays ahead of right, so they never become equal again and the trailing "()" is missed. The right-to-left pass fixes this by resetting on the opposite condition. Together, every valid substring is measured from at least one direction.


🧠 Dry Run: "(()"

Pass 1 (Left → Right):

icharleftrightaction0(10left > right1(20—2)21never equal (missed)

maxLen = 0

Pass 2 (Right → Left):

icharleftrightaction2)01—1(11equal → maxLen = 20(21left > right → reset

maxLen = 2 ✅


⏱️ Complexity

MetricValueReasonTimeO(n)Two linear scans of the stringSpaceO(1)Only a few counter variables


💻 Code

javapublic class Solution {

    public int longestValidParentheses(String s) {
        int left = 0, right = 0, maxLen = 0;

        // Pass 1: Left to Right (handles extra ')')
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right) maxLen = Math.max(maxLen, 2 * right);
            else if (right > left) { left = 0; right = 0; }
        }

        // Pass 2: Right to Left (handles extra '(')
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right) maxLen = Math.max(maxLen, 2 * left);
            else if (left > right) { left = 0; right = 0; }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.longestValidParentheses("(()"));     // 2
        System.out.println(sol.longestValidParentheses(")()())"));  // 4
        System.out.println(sol.longestValidParentheses(""));        // 0
    }
}


🔁 Alternative: Stack Approach

Also O(n) time but uses O(n) space. Push -1 as a base index, push indices of (, pop on ), and measure i - stack.peek() for the current valid length.

javapublic int longestValidParentheses(String s) {
    int maxLen = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1);

    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } else {
            stack.pop();
            if (stack.isEmpty()) stack.push(i);
            else maxLen = Math.max(maxLen, i - stack.peek());
        }
    }
    return maxLen;
}
