package YIDA.cases;

public class MergeSort {
    /**
     * 归并排序：基于分治思想
     * 稳定性排序，但是不是原地排序
     * 时间复杂度为O(nlogn)空间复杂度为O(n)
     * @param arr
     */
    public static void mergeSort(int[] arr){
        int n=arr.length;
        if(n<2){
            return;
        }
        mergeInit(arr,0,n-1);
    }
    private static void mergeInit(int[] arr,int left,int right){
        if(left+1>=right){
            return;
        }
        int mid=(left+right)>>>1;
        mergeInit(arr,left,mid);
        mergeInit(arr,mid,right);

        merge(arr,left,mid,right);
    }

    /**
     * 归并操作
     * @param arr
     * @param left
     * @param mid
     * @param right
     */
    private static void merge(int[] arr,int left,int mid,int right){
        int length=right-left;
        int[] temp=new int[length];

        int i=left;
        int j=mid;
        int k=0;
        while(i<mid&&j<right){
            if(arr[i]<=arr[j]){
                temp[k++]=arr[i++];
            }else{
                temp[k++]=arr[j++];
            }
        }
        while(i<mid){
            temp[k++]=arr[i++];
        }
        while(j<right){
            temp[k++]=arr[j++];
        }
        System.arraycopy(temp,0,arr,left,length);
    }
}
