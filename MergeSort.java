public class MergeSort {
  /**
   * Mainly used for init the tempArray.
   *
   * @param array required to be sorted.
   */
  public void sort(int[] array, int cutoff) {
    if (array == null || array.length == 1) {
      return;
    }
    int n = array.length;
    int[] tempArray = new int[n];

    int lo = 0;
    int hi = n - 1;

    sort(array, tempArray, lo, hi, cutoff); // Recursive method
  }

  /**
   * 3 Steps:
   * 1. Split the array in half.
   * 2. Sort left / right sides.
   * 3. Merge.
   */
  public void sort(int[] array, int[] tempArray, int lo, int hi, int cutoff) {
    if (hi <= lo) {
      return;
    }

    if (cutoff > 1 && (hi - lo + 1) <= cutoff) {
      InsertionSort.sort(array, lo, hi);
      return;
    }

    int mid = lo + (hi - lo) / 2;

    sort(array, tempArray, lo, mid, cutoff);
    sort(array, tempArray, mid+1, hi, cutoff);
    merge(array, tempArray, lo, mid, hi);
  }

  public void bottomUpSort(int[] array) {
    int n = array.length;
    int[] tempArray = new int[n];


    // Subarray size
    for (int size = 1; size < n; size *= 2) {

      for (int leftSide = 0; leftSide < n - size; leftSide += size*2) {
        int mid = leftSide + size - 1;
        // Edge case, n % 2 != 0, Extra subarray at the end.
        int hi = Math.min(leftSide + 2*size - 1, n - 1);

        merge(array, tempArray, leftSide, mid, hi);
      }

    }
  }


  public void merge(int[] array, int[] tempArray, int lo, int mid, int hi) {
    // Copy to 2nd Array. (Merging)
    for (int i = lo; i <= hi; ++i) {
      tempArray[i] = array[i];
    }

    // Adding back to the original Array the newly sorted subarray.
    int p1 = lo;
    int p2 = mid + 1;
    for (int i = lo; i <= hi; ++i) {
      if (p1 > mid) {
        array[i] = tempArray[p2];
        p2++;
      }
      else if (p2 > hi) {
        array[i] = tempArray[p1];
        p1++;
      }
      else if (tempArray[p1] <= tempArray[p2]) {

        array[i] = tempArray[p1];
        p1++;
      }
      else {
        array[i] = tempArray[p2];
        p2++;
      }
    }
  }
}
