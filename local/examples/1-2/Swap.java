import java.util.Arrays;

public class Swap {
	static void swap(int x, int y) {
		int t = x; x = y; y = t;
	}

	static void zeroFirst(int[] arr) {
		arr[0] = 0;
	}

	static void reassign(int[] arr) {
		arr = new int[]{9, 9};
	}

	public static void main(String[] args) {
		int a = 1, b = 2;
		swap(a, b);
		System.out.println(a + ", " + b);

		int[] xs = {1, 2};
		zeroFirst(xs);
		System.out.println(Arrays.toString(xs));
		reassign(xs);
		System.out.println(Arrays.toString(xs));
	}
}
