package com.google.evochko;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Created by Admin on 3/26/2020.
 */
public class Test1 {
    public static void main(String[] arg)   {
        int[] arr = {3,4,5,7,81,-12,-290,94};
        // -290 -12 3 4 5 7 81 94
        System.out.println(new Test1().get4maxValue(arr));
    }

    public int get4maxValue(int[] arr)  {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> {
            return Integer.compare((Integer) o2, (Integer) o1);
        });
        //Arrays.sort(arr);

        queue.addAll(Arrays.stream(arr).mapToObj(Integer::new).collect(Collectors.toList()));
        queue.poll();
        queue.poll();
        queue.poll();
        return queue.poll();
    }
}
