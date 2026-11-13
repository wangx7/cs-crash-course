import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
	public static void main(String[] args) {
		System.out.println(new BigDecimal(0.1));
		System.out.println(new BigDecimal("0.1"));

		BigDecimal a = new BigDecimal("2.0");
		BigDecimal b = new BigDecimal("2.00");
		System.out.println(a.equals(b));
		System.out.println(a.compareTo(b));

		try {
			BigDecimal.ONE.divide(new BigDecimal("3"));
		} catch (ArithmeticException e) {
			System.out.println("1/3 抛出: " + e);
		}
		System.out.println(BigDecimal.ONE.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP));
	}
}
