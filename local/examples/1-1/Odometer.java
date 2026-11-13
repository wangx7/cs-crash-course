public class Odometer {
	public static void main(String[] args) {
		int max = Integer.MAX_VALUE;
		System.out.println(max);
		System.out.println(max + 1);
		System.out.println(max * 2);

		// 右边全是 int 字面量：先按 int 算，溢出了，再 widening 给 long
		long micros = 24 * 60 * 60 * 1000 * 1000;
		System.out.println(micros);
		long fixed = 24L * 60 * 60 * 1000 * 1000;
		System.out.println(fixed);

		try {
			Math.addExact(max, 1);
		} catch (ArithmeticException e) {
			System.out.println("addExact 抛出: " + e);
		}
	}
}
