import java.util.Arrays;
import java.util.Random;

public class insertionSort {
    public static long count = 0;
    /*Function to sort array using insertion sort*/
    void sort(int arr[])
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            if (j >=0 && arr[j]>key){
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                    count++;
                }
                //count++;
            }
            arr[j + 1] = key;
        }

        System.out.println("insertion-sort "+count);
    }
    /* A utility function to print array of size n*/
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");

        System.out.println();
    }



    // Driver method
    public static void main(String args[])
    {
        Random rd = new Random(); // creating Random object
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt();
        }
        //Arrays.sort(arr);
        //int[] reverse = new int[arr.length];
        /*for(int i = 0; i<arr.length; i++){
            reverse[arr.length-1-i] = arr[i];
        }*/

        AVLTree something = new AVLTree();
        for(int i=0; i<arr.length; i++){
            something.insert(arr[i], "infoSome");
        }

        //int randomKey = rd.ints(0, arr.length).findFirst().getAsInt();
        int key = something.searchForMax(something.getRoot().getLeft()).getKey();
        something.split(key);
        //System.out.println("avl tree "+something.count1);
        System.out.println("avg join "+something.sumJoin/something.countJoin);
        System.out.println("sum join "+something.sumJoin);
        System.out.println("count join "+something.countJoin);
        System.out.println("max join "+something.maxJoin);
        //int[] arr = {4,2,1,5,3};
        /*insertionSort ob = new insertionSort();
        ob.sort(arr);*/

        //printArray(arr);
    }
}
