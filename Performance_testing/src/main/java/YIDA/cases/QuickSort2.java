package YIDA.cases;

/**
 * 三路快排+随机选取基准值
 */
public class QuickSort2 {
    public static void quick(int[] arr){
        if(arr.length<2){
            return;
        }
        quickInit(arr,0,arr.length-1);
    }
    private static void quickInit(int[] arr,int low,int high){
        if(low>=high){
            return;
        }
        //随机选取基准值
        int randomIndex= (int) (Math.random()*(high-low+1)+low);

        swap(arr,low,randomIndex);
        int key=arr[low];
        //分区：[low+1,less]<key
        //[less+1,i-1]==key
        //[more,high]>key
        int less=low;
        int more=high+1;
        int i=low+1;
        while(more>i){
            if(arr[i]<key){
                swap(arr,i,less+1);
                i++;
                less++;
            }else if(arr[i]>key){
                swap(arr,i,more-1);
                more--;
            }else{
                i++;
            }
        }
        swap(arr,low,less);
        quickInit(arr,low,less-1);
        quickInit(arr,more,high);
    }


    private static void swap(int[] arr,int a,int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }
}
