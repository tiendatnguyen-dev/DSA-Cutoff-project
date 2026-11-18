import java.util.Scanner;

public class InsertionSort {
  public static void sort(int [] arr, int lo, int hi) {
    int n = arr.length;
    for (int i = lo; i <= hi; i++) {
      for (int j = i; j > lo; j--) {
        if (arr[j] < arr[j-1]) {
          swap(arr, j, j-1);
        } else {
          break;
        }
      }
    }
  }

  public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public void printHelper(int[] arr) {
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }
}