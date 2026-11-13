public class IntMath {
	public static void main(String[] args) {
		System.out.println(7 / 2);
		System.out.println(-7 / 2);
		System.out.println(-7 % 2);
		System.out.println(7.0 / 2);
		try {
			System.out.println(1 / 0);
		} catch (ArithmeticException e) {
			System.out.println("1 / 0 抛出: " + e);
		}
		System.out.println(1.0 / 0.0);
	}
}
