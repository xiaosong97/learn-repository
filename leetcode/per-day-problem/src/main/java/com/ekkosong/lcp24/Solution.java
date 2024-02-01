package com.ekkosong.lcp24;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] res1 = solution.numsGame(new int[]{3, 4, 5, 1, 6, 7});
        System.out.println("res1 = " + Arrays.toString(res1));
        int[] res2 = solution.numsGame(new int[]{1, 2, 3, 4, 5});
        System.out.println("res2 = " + Arrays.toString(res2));
        int[] res3 = solution.numsGame(new int[]{1, 1, 1, 2, 3, 4});
        System.out.println("res3 = " + Arrays.toString(res3));
    }

    public int[] numsGame(int[] nums) {
        // 定义结果数组
        int n = nums.length;
        // 题目要求计算对于nums的子数组 nums[0,i], 将其变成一个差值为1并且递增的序列需要的最小操作数
        // 对于子数组中的每个数字，每一次操作可以将其加一，或者减一
        // 子数组的初始值为 nums[0]...nums[i]
        // 假设最终的递增序列为 x...x+i
        // 那么将这一子数组变为最终递增序列需要的操作数为:
        // f(i) = |nums[0]-x| + ... + |nums[i]-(x+i)|
        //      = |(nums[0]-0)-x| + ... + |(nums[i]-i)-x)|
        // 我们要求的就是f(i)的最小值
        // 再假设 B[i]=nums[i]-i
        // 则 f(i) = |B[0]-x| + ... + |B[i]-x|
        // 问题转换为求|B[0]-x| + ... + |B[i]-x|的最小值，其中B[i]已知，x未知
        // 也就是要找到一个数x，使得B[0,i]这个子数组的所有数和x的差的总和最小
        // 算法步骤
        // 1）先计算B数组
        int[] B = new int[n];
        for (int i = 0; i < n; i++) {
            B[i] = nums[i] - i;
        }
        // 2) 定义差值的和
        int[] diff = new int[n];

        for (int i = 0; i < n; i++) {
            int[] currNums = Arrays.copyOfRange(B, 0, i + 1);
            // System.out.println("currNums = " + Arrays.toString(currNums));
            diff[i] = getX(currNums);
            // System.out.println("diff[i] = " + diff[i]);
        }

        return diff;
    }

    /**
     * 需要以log(n)的时间复杂度计算出数组的中位数
     * TODO: 后面再仔细思考下
     */
    public int[] numsGame2(int[] nums) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        int[] ans = new int[n];
        // 一个大根堆，存储数组中较小的一部分数
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> b - a);
        // 一个小跟堆，存储数组中较大的一部分数
        PriorityQueue<Integer> right = new PriorityQueue<>();
        long leftSum = 0, rightSum = 0;
        for (int i = 0; i < n; i++) {
            int b = nums[i] - i;
            if (i % 2 == 0) {
                // 前缀长度是奇数
                if (!left.isEmpty() && b < left.peek()) {
                    leftSum -= left.peek() - b;
                    left.offer(b);
                    b = left.poll();
                }
                rightSum += b;
                right.offer(b);
                ans[i] = (int) ((rightSum - right.peek() - leftSum) % MOD);
            } else {
                // 前缀长度是偶数
                if (b > right.peek()) {
                    rightSum += b - right.peek();
                    right.offer(b);
                    b = right.poll();
                }
                leftSum += b;
                left.offer(b);
                ans[i] = (int) ((rightSum - leftSum) % MOD);
            }
        }
        return ans;
    }

    /**
     * 一种更快速的方法：
     * 计算一个目标数字x，满足数组 nums 中的所有数字都通过若干操作后变为该目标数字，这个操作的步骤要尽可能最小
     * 实际上这个数字x就是数组中所有数字的中位数，
     */
    public int getX(int[] nums) {
        if (nums.length == 1) return 0;
        // 排序
        Arrays.sort(nums);
        // 找出中位数
        int mid = nums[nums.length / 2];
        if (nums.length % 2 == 0) {
            mid = (nums[nums.length / 2] + nums[nums.length / 2 - 1]) / 2;
        }
        // 计算步骤和
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += Math.abs(mid - nums[i]);
        }
        return sum;
    }

    /**
     * 暴力解法：遍历最小值到最大值，计算每个对应的x值下的diff，求一个最小diff
     * O（n*m)
     */
    private int getCurrDiff(int[] B, int minB, int maxB, int i) {
        int currDiff = Integer.MAX_VALUE;
        // 定义变量x作为要找的数，这个数一定在B数组的最大值和最小值之间
        // 对于每个i，都需要有O（n*m)的时间复杂度，其中 m 是最大数和最小数的差，n是i的大小
        for (int x = minB; x <= maxB; x++) {
            int tmp = getNumByX(x, B, i);
            currDiff = Math.min(currDiff, tmp);
        }
        return currDiff;
    }

    private int getNumByX(int x, int[] b, int i) {
        // |B[0]-x| + ... + |B[i]-x|
        int num = 0;
        for (int j = 0; j <= i; j++) {
            num += Math.abs(b[j] - x);
        }
        return num;
    }
}