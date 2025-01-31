package edu.utdallas.cs4348;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Util {
    public static List<Integer> reverseSortPriorities(Map<Integer, List<CPUBurst>> burstMap) {
        List<Integer> reverseSortedList = new ArrayList<>(burstMap.keySet());
        reverseSortedList.sort(Comparator.reverseOrder());
        return reverseSortedList;
    }
}
