public class Floats {
	public static void main(String[] args) {
		System.out.println(0.1 + 0.2);
		System.out.println(0.1 + 0.2 == 0.3);
		System.out.println(0.1f + 0.2f);
		System.out.println(0.1f + 0.2f == 0.3f);
		System.out.println((float) 0.3 == 0.3);
		System.out.println(1.0 / 0.0);
		System.out.println(0.0 / 0.0);
		double nan = 0.0 / 0.0;
		System.out.println(nan == nan);
		System.out.println(Double.isNaN(nan));
	}
}
