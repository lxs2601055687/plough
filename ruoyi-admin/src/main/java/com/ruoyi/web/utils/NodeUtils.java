package com.ruoyi.web.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeUtils {
    public static String findRootId(List<String> nodeIds) {
        //遍历找到nodeIds中出现最多的元素并且返回
        if (nodeIds == null || nodeIds.size() == 0) {
            return null; // Handle the case where the array is empty or null.
        }

        Map<String, Integer> countMap = new HashMap<>();
        String mostFrequentElement = null;
        int maxCount = 0;

        for (String nodeId : nodeIds) {
            if (countMap.containsKey(nodeId)) {
                countMap.put(nodeId, countMap.get(nodeId) + 1);
            } else {
                countMap.put(nodeId, 1);
            }

            if (countMap.get(nodeId) > maxCount) {
                mostFrequentElement = nodeId;
                maxCount = countMap.get(nodeId);
            }
        }
        if (mostFrequentElement == null) {
            return nodeIds.get(0);
        }
        return mostFrequentElement;
    }
}

