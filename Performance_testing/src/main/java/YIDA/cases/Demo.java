package YIDA.cases;

import YIDA.TestCase;
import YIDA.annotations.Benchmark;
import YIDA.annotations.Measurement;
import YIDA.annotations.Preheat;
import org.openjdk.jmh.annotations.Warmup;

import java.util.*;
@Preheat(interactions = 3)
@Measurement(iterations = 10,group = 3)

public class Demo implements TestCase {

    /**
     * 1.快排和归并的差别
     * 2.Arrays.sort()中的参数如果是int型的是快排，Object类型的是归并排序
     *   Arrays.parallelSort并发排序
     */
    @Benchmark
    public void testQuickSort(){
        int[] arr=new int[100000];
        Random random=new Random(20190726);
        for(int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(100000);
        }
        QuickSort quickSort=new QuickSort();
        quickSort.quick(arr);

    }
    @Benchmark
    public void testMergeSort(){
        int[] arr=new int[10000];
        Random random=new Random(20190726);
        for(int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(10000);
        }
        MergeSort mergeSort=new MergeSort();
        mergeSort.mergeSort(arr);
    }
    @Benchmark
    public void testQuickThree(){
        int[] arr=new int[10000];
        Random random=new Random(20190726);
        for(int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(10000);
        }
        QuickSort2 quickSort2=new QuickSort2();
        quickSort2.quick(arr);
    }
    @Benchmark
    public void ArraysSort(){
        int[] arr=new int[10000];
        Random random=new Random(20190726);
        for(int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(10000);
        }
        Arrays.sort(arr);
    }
}
