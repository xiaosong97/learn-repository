package com.ekkosong.p1686;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.stoneGameVI(new int[]{1, 3}, new int[]{2, 1}));
        System.out.println(solution.stoneGameVI(new int[]{1, 2}, new int[]{3, 1}));
        System.out.println(solution.stoneGameVI(new int[]{2, 4, 3}, new int[]{1, 6, 7}));
    }

    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int diff = 0;
        Integer[] index = new Integer[aliceValues.length];
        for (int i = 0; i < aliceValues.length; i++) {
            index[i] = i;
        }
        Arrays.sort(index, (o1, o2) -> (aliceValues[o2] + bobValues[o2]) - (aliceValues[o1] + bobValues[o1]));
        for (int i = 0; i < index.length; i++) {
            if (i % 2 == 0) {
                diff += aliceValues[index[i]];
            } else {
                diff -= bobValues[index[i]];
            }
        }
        // System.out.println("diff = " + diff);

        return Integer.compare(diff, 0);
    }

    public int stoneGameVI0(int[] aliceValues, int[] bobValues) {
        int aliceScore = 0;
        int bobScore = 0;
        // 维护两个大根堆，堆内元素为各自value数组的值和索引，根据值排序
        // 每次取堆顶元素，取堆顶元素的value加到自己的分数上，取索引，做已经被取走的石子的记录
        // 如果当前取的堆顶元素已经被取走了，则应该再取新的堆顶元素，直到堆空或者取到没取过的
        // 两个堆任意一个为空，则石子已被全部取完，结束循环
        // 比较两人的分数，得出胜负
        // FIX: 因为要保证不能让对方取得更大的数,所以每次要选择自己的分数和对方分数加起来最大的石头
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o2[0] + o2[1] - o1[0] - o1[1]);
        for (int i = 0; i < aliceValues.length; i++) {
            queue.offer(new int[]{aliceValues[i], bobValues[i], i});
        }

        HashSet<Integer> picked = new HashSet<>();
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            while (picked.contains(poll[2])) {
                poll = queue.poll();
                if (poll == null) break;
            }
            if (poll == null) break;
            aliceScore += poll[0];
            picked.add(poll[2]);

            poll = queue.poll();
            if (poll == null) break;
            while (picked.contains(poll[2])) {
                poll = queue.poll();
                if (poll == null) break;
            }
            if (poll == null) break;
            bobScore += poll[1];
            picked.add(poll[2]);
        }
        return Integer.compare(aliceScore, bobScore);
    }
}