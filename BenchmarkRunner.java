import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {

  private static final int ARRAY_SIZE = 100_000;
  private static final int WARMUP_ITERATIONS = 500;
  private static final int TEST_RUNS = 21;
  private static final int MIN_CUTOFF = 0;
  private static final int MAX_CUTOFF = 30;

  public static void main(String[] args) {
    System.out.println("=== BẮT ĐẦU CHẠY THÍ NGHIỆM MERGESORT CUTOFF ===");

    // 1. Warm-up JVM
    warmupJVM();

    // 2. Chạy thực nghiệm trên 4 loại dữ liệu
    runBenchmarkForDataset("Random", generateRandomArray(ARRAY_SIZE));
    runBenchmarkForDataset("Sorted", generateSortedArray(ARRAY_SIZE));
    runBenchmarkForDataset("ReverseSorted", generateReverseSortedArray(ARRAY_SIZE));
    runBenchmarkForDataset("Duplicates", generateDuplicatesArray(ARRAY_SIZE));

    System.out.println("\n=== HOÀN THÀNH THÍ NGHIỆM ===");
  }

  /**
   * Vòng lặp đo đạc thời gian chạy cho từng mức CUTOFF trên 1 tập dữ liệu
   */
  private static void runBenchmarkForDataset(String datasetName, int[] masterArray) {
    System.out.println("\n--- Đang đo tập dữ liệu: " + datasetName + " ---");
    System.out.println("Dataset,Cutoff,MedianTime_ns,MedianTime_ms");

    MergeSort mergeSort = new MergeSort();

    for (int cutoff = MIN_CUTOFF; cutoff <= MAX_CUTOFF; cutoff++) {
      long[] elapsedTimes = new long[TEST_RUNS];

      for (int run = 0; run < TEST_RUNS; run++) {
        // Nhân bản mảng gốc để đảm bảo tính công bằng cho mọi lần test
        int[] testArray = Arrays.copyOf(masterArray, masterArray.length);

        // Đo thời gian thực thi bằng nanoTime
        long startTime = System.nanoTime();
        mergeSort.sort(testArray, cutoff);
        long endTime = System.nanoTime();

        elapsedTimes[run] = endTime - startTime;
      }

      // Tính giá trị Trung vị (Median) để loại bỏ nhiễu do HĐH
      Arrays.sort(elapsedTimes);
      long medianTimeNs = elapsedTimes[TEST_RUNS / 2];
      double medianTimeMs = medianTimeNs / 1_000_000.0;

      // In kết quả định dạng CSV
      System.out.printf("%s,%d,%d,%.4f\n", datasetName, cutoff, medianTimeNs, medianTimeMs);
    }
  }

  /**
   * Khởi động máy ảo Java (JIT Warm-up) giúp code đạt trạng thái tối ưu trước khi đo
   */
  private static void warmupJVM() {
    System.out.print("Đang khởi động JVM (Warm-up)... ");
    MergeSort mergeSort = new MergeSort();
    Random rand = new Random(42);

    for (int i = 0; i < WARMUP_ITERATIONS; i++) {
      int[] dummy = rand.ints(1000, 0, 10000).toArray();
      mergeSort.sort(dummy, 7);
    }
    System.out.println("Đã xong!");
  }

  // 1. Mảng Ngẫu Nhiên
  private static int[] generateRandomArray(int size) {
    Random rand = new Random(42);
    int[] arr = new int[size];
    for (int i = 0; i < size; i++) {
      arr[i] = rand.nextInt(size * 10);
    }
    return arr;
  }

  // 2. Mảng Đã Sắp Xếp
  private static int[] generateSortedArray(int size) {
    int[] arr = generateRandomArray(size);
    Arrays.sort(arr);
    return arr;
  }

  // 3. Mảng Sắp Xếp Ngược
  private static int[] generateReverseSortedArray(int size) {
    int[] arr = generateSortedArray(size);
    for (int i = 0; i < size / 2; i++) {
      int temp = arr[i];
      arr[i] = arr[arr.length - 1 - i];
      arr[arr.length - 1 - i] = temp;
    }
    return arr;
  }

  // 4. Mảng Trùng Lặp Nhiều
  private static int[] generateDuplicatesArray(int size) {
    Random rand = new Random(42);
    int[] arr = new int[size];
    for (int i = 0; i < size; i++) {
      arr[i] = rand.nextInt(10);
    }
    return arr;
  }
}