/* #2438. Range Product Queries of Powers

Problem Statement:- Given a positive integer n, there exists a 0-indexed array called powers, composed of the minimum number of powers of 2 that sum to n. The array is sorted in non-decreasing order, 
and there is only one way to form the array.You are also given a 0-indexed 2D integer array queries, where queries[i] = [lefti, righti]. Each queries[i] represents a query where you have to find 
the product of all powers[j] with lefti <= j <= righti.

Return an array answers, equal in length to queries, where answers[i] is the answer to the ith query. Since the answer to the ith query may be too large, each answers[i] should be returned modulo 109 + 7.

Example 1:

Input: n = 15, queries = [[0,1],[2,2],[0,3]]
Output: [2,4,64]
Explanation:
For n = 15, powers = [1,2,4,8]. It can be shown that powers cannot be a smaller size.
Answer to 1st query: powers[0] * powers[1] = 1 * 2 = 2.
Answer to 2nd query: powers[2] = 4.
Answer to 3rd query: powers[0] * powers[1] * powers[2] * powers[3] = 1 * 2 * 4 * 8 = 64.
Each answer modulo 109 + 7 yields the same answer, so [2,4,64] is returned.
Example 2:

Input: n = 2, queries = [[0,0]]
Output: [2]
Explanation:
For n = 2, powers = [2].
The answer to the only query is powers[0] = 2. The answer modulo 109 + 7 is the same, so [2] is returned.
 

Constraints:

1 <= n <= 109
1 <= queries.length <= 105
0 <= starti <= endi < powers.length


*/



class Solution {
    public int[] productQueries(int n, int[][] queries) {
        final int MOD = 1_000_000_007;
        List<Integer> powers = new ArrayList<>();
        
        // Extract powers of two from n (bit decomposition)
        for (int i = 0; i < 31; i++) {
            if (((n >> i) & 1) == 1) {
                powers.add(1 << i);
            }
        }
        
        int qLength = queries.length;
        int[] ans = new int[qLength];
        
        for (int i = 0; i < qLength; i++) {
            int l = queries[i][0], r = queries[i][1];
            long prod = 1;
            for (int j = l; j <= r; j++) {
                prod = (prod * powers.get(j)) % MOD;
            }
            ans[i] = (int) prod;
        }
        
        return ans;
    }
}


/*
Approach :- 
1. Extract powers of 2 from n
----soppose n=38 = 100110(in binary)
    least no of powers of 2 from 38 whose sum is 38 = 2, 4, 32
    How we get it?
    in binary at which place there is one.
    Idea:- left shift the binary of n and find bitwise and with 1(decimal) to get that at which place there is one
    Suppose we get out 1 at ith iteration then find 2**i which is the power of 2 going to add in powers array.

2. for each queries entry we find the product of entries of power according to given indexes and store it in answer array.
   finally return answer array.
*/
