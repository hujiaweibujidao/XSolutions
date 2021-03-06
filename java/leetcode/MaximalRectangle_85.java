/**
 * hujiawei - 15/3/22.
 * <p/>
 * 动规
 * <p/>
 * https://leetcode.com/problems/maximal-rectangle/
 */
public class MaximalRectangle_85 {

    public static void main(String[] args) {
        System.out.println(new MaximalRectangle_85().maximalRectangle(new char[][]{
                new char[]{'0', '1', '0', '0'},
                new char[]{'1', '1', '1', '0'},
                new char[]{'0', '1', '1', '0'},
                new char[]{'0', '0', '1', '0'},
                new char[]{'0', '1', '0', '0'},
        }));
    }

    // 参考解法 https://leetcode.com/discuss/20240/share-my-dp-solution
    //The DP solution proceeds row by row, starting from the first row.
    //Let the maximal rectangle area at row i and column j be computed by [right(i,j) - left(i,j)]*height(i,j).
    //All the 3 variables left, right, and height can be determined by the information from previous row,
    //and also information from the current row. So it can be regarded as a DP solution.
    //The transition equations are:
    //left(i,j) = max(left(i-1,j), curleft), curleft can be determined from the current row
    //right(i,j) = min(right(i-1,j), curright), curright can be determined from the current row
    //height(i,j) = height(i-1,j) + 1, if matrix[i][j]=='1';
    //height(i,j) = 0, if matrix[i][j]=='0'

    //left、right、height其实就是给出当前位置的左右边界以及高度，三者都可以通过DP计算得到
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[] left = new int[n], right = new int[n], height = new int[n];
        //fill_n(left,n,0); fill_n(right,n,n); fill_n(height,n,0);
        for (int i = 0; i < n; i++) {
            left[i] = 0;
            right[i] = n;
            height[i] = 0;
        }
        int maxA = 0;
        for (int i = 0; i < m; i++) {
            int cur_left = 0, cur_right = n;
            // compute height (can do this from either side)
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') height[j]++;
                else height[j] = 0;
            }
            // compute left (from left to right)
            for (int j = 0; j < n; j++) {//取较大的
                if (matrix[i][j] == '1') left[j] = Math.max(left[j], cur_left);
                else {
                    left[j] = 0;
                    cur_left = j + 1;
                }
            }
            // compute right (from right to left)
            for (int j = n - 1; j >= 0; j--) {//取较小的
                if (matrix[i][j] == '1') right[j] = Math.min(right[j], cur_right);
                else {
                    right[j] = n;
                    cur_right = j;
                }
            }
            // compute the area of rectangle (can do this from either side)
            for (int j = 0; j < n; j++)
                maxA = Math.max(maxA, (right[j] - left[j]) * height[j]);
        }
        return maxA;
    }

}
