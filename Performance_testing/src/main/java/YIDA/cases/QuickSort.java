package YIDA.cases;

public class QuickSort {
    /**
     * 快速排序：分治思想
     * 不稳定性排序，空间复杂度0(logn);时间复杂度O(nlogn)-————可优化为
     * 1、确定一个基准值
     * 1）选第一个元素
     * 2）随机取（推荐）
     * 2、对排序区间进行partition，结果让比基准值小的放在基准值的左边，让比基准值大的放在右边
     * 3、按同样的方式处理左右两个小的待排序区间
     *
     * 快排的优化：
     * 1.选基准的方式，最理想的方法是选择的基准恰好能把待排序序列分为两个等长的子序列（随机选取基准；三数取中法）
     * 2.三路快排：分区分为小于等于大于三个区域，适用于给定数组有大量重复相等元素
     * @param arr
     */
    public static void quick(int[] arr){
        int n=arr.length;
        if(n<2){
            return;
        }
        quickSort(arr,0,n-1);
    }

    private static void quickSort(int[] arr,int low,int high){
        //防止给定的数组是有序的
        takeThreeNumber(arr,low,high);
        int part=partition1(arr,low,high);//划分成两部分
        //递归左边
        if(part>low+1){
            //保证左边有两个数据以上
            quickSort(arr,low,part-1);
        }
        //递归右边
        if(part<high-1){
            quickSort(arr,part+1,high);
        }
    }

    /**
     * 递归快排分区
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private static int partition1(int[] arr,int low,int high){
        //选基准
        int key=arr[low];
        while(low<high){
            while(low<high&&arr[high]>=key){
                high--;
            }
            if(low==high){
                //low与high相遇，把基准值放进去
                arr[low]=key;
                //此时已经在区间找到了基准值的最终位置
                break;
            }else{
                //high处的值小于基准值，应该放在low的位置
                arr[low]=arr[high];
            }
            while(low<high&&arr[low]<key){
                low++;
            }
            if(low==high){
                arr[low]=key;
                break;
            }else{
                arr[high]=arr[low];
            }
        }
        arr[low]=key;
        return low;
    }

    /**
     * 三数取中
     * @param arr
     * @param low
     * @param high
     */
    private static void takeThreeNumber(int[] arr,int low,int high){
        //满足arr[high]>arr[mid]>arr[low]
        int mid=(low+high)>>>1;
        if(arr[mid]>arr[low]){
            swap(arr,low,mid);
        }
        if(arr[mid]>arr[high]){
            swap(arr,mid,high);
        }
        if(arr[low]>arr[high]){
            swap(arr,low,high);
        }
    }

    private static void swap(int[] arr,int a,int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }

}
