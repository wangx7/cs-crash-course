import java.util.Arrays;

public class CopyVsShare {
	public static void main(String[] args) {
		int a = 5;
		int b = a;
		b = 9;
		int[] xs = {1, 2};
		int[] ys = xs;
		ys[0] = 99;
		System.out.println(a);
		System.out.println(Arrays.toString(xs));
	}
}
