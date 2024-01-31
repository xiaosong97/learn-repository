package com.ekkosong.p2670;

import java.util.Arrays;
import java.util.HashSet;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] res1 = solution.distinctDifferenceArray(new int[]{1, 2, 3, 4, 5});
        System.out.println("res1 = " + Arrays.toString(res1));
        int[] res2 = solution.distinctDifferenceArray(new int[]{3, 2, 3, 4, 2});
        System.out.println("res2 = " + Arrays.toString(res2));
    }

    public int[] distinctDifferenceArray(int[] nums) {
        int[] prefixUniqueNumber = new int[nums.length];
        prefixUniqueNumber[0] = 1;
        HashSet<Integer> record = new HashSet<>();
        record.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            prefixUniqueNumber[i] = prefixUniqueNumber[i - 1] + (record.contains(nums[i]) ? 0 : 1);
            record.add(nums[i]);
        }
        System.out.println("prefixUniqueNumber = " + Arrays.toString(prefixUniqueNumber));
        record.clear();

        int[] suffixUniqueNumber = new int[nums.length];
        suffixUniqueNumber[nums.length - 1] = 0;
        // record.add(nums[nums.length - 1]);
        for (int i = nums.length - 2; i >= 0; i--) {
            suffixUniqueNumber[i] = suffixUniqueNumber[i + 1] + (record.contains(nums[i + 1]) ? 0 : 1);
            record.add(nums[i + 1]);
        }
        System.out.println("suffixUniqueNumber = " + Arrays.toString(suffixUniqueNumber));

        int[] diff = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            diff[i] = prefixUniqueNumber[i] - suffixUniqueNumber[i];
        }
        return diff;
    }
}