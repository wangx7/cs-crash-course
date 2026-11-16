import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalLab {
    public static void main(String[] args) {
        // BEGIN binary
        System.out.println("doubleSum=" + (0.1 + 0.2));
        System.out.println("doubleEquality=" + (0.1 + 0.2 == 0.3));
        System.out.println("printedTenth=" + 0.1);
        System.out.println("exactDoubleTenth=" + new BigDecimal(0.1).toPlainString());
        // END binary

        // BEGIN construction
        BigDecimal fromText = new BigDecimal("0.1");
        BigDecimal fromDouble = new BigDecimal(0.1);
        BigDecimal fromValueOf = BigDecimal.valueOf(0.1);
        BigDecimal fromSum = BigDecimal.valueOf(0.1 + 0.2);
        System.out.println("text=" + fromText.toPlainString());
        System.out.println("double=" + fromDouble.toPlainString());
        System.out.println("valueOf=" + fromValueOf.toPlainString());
        System.out.println("valueOfSum=" + fromSum.toPlainString());
        System.out.println("decimalSum=" + fromText.add(new BigDecimal("0.2")).toPlainString());
        // END construction

        // BEGIN scale
        BigDecimal a = new BigDecimal("2.0");
        BigDecimal b = new BigDecimal("2.00");
        System.out.println("unscaled=" + a.unscaledValue() + "," + b.unscaledValue());
        System.out.println("scale=" + a.scale() + "," + b.scale());
        System.out.println("precision=" + a.precision() + "," + b.precision());
        System.out.println("equals=" + a.equals(b));
        System.out.println("compareTo=" + a.compareTo(b));
        // END scale

        // BEGIN immutable
        BigDecimal amount = new BigDecimal("10.00");
        amount.add(new BigDecimal("0.50"));
        System.out.println("ignoredResult=" + amount.toPlainString());
        amount = amount.add(new BigDecimal("0.50"));
        System.out.println("assignedResult=" + amount.toPlainString());
        // END immutable

        // BEGIN division
        try {
            BigDecimal.ONE.divide(new BigDecimal("3"));
        } catch (ArithmeticException e) {
            System.out.println("exactThird=" + e.getClass().getSimpleName());
        }
        System.out.println("roundedThird=" + BigDecimal.ONE
                .divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP).toPlainString());
        // END division
    }
}
