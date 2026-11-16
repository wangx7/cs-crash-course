import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundingLab {
    public static void main(String[] args) {
        // BEGIN modes
        BigDecimal positive = new BigDecimal("1.245");
        BigDecimal negative = new BigDecimal("-1.245");
        RoundingMode[] modes = {RoundingMode.HALF_UP, RoundingMode.HALF_EVEN,
                RoundingMode.DOWN, RoundingMode.FLOOR, RoundingMode.CEILING};
        for (RoundingMode mode : modes) {
            System.out.println(mode + "=" + positive.setScale(2, mode).toPlainString()
                    + "," + negative.setScale(2, mode).toPlainString());
        }
        try {
            positive.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException e) {
            System.out.println("UNNECESSARY=" + e.getClass().getSimpleName());
        }
        // END modes

        // BEGIN boundary
        BigDecimal exactLine = new BigDecimal("0.05").multiply(new BigDecimal("0.10"));
        BigDecimal roundedLine = exactLine.setScale(2, RoundingMode.HALF_UP);
        BigDecimal roundEach = roundedLine.multiply(new BigDecimal("3"));
        BigDecimal roundTotal = exactLine.multiply(new BigDecimal("3"))
                .setScale(2, RoundingMode.HALF_UP);
        System.out.println("exactLine=" + exactLine.toPlainString());
        System.out.println("roundedLine=" + roundedLine.toPlainString());
        System.out.println("roundEach=" + roundEach.toPlainString());
        System.out.println("roundTotal=" + roundTotal.toPlainString());
        // END boundary

        // BEGIN allocation
        BigDecimal each = new BigDecimal("0.05")
                .divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
        System.out.println("each=" + each.toPlainString());
        System.out.println("allocated=" + each.multiply(new BigDecimal("3")).toPlainString());
        // END allocation
    }
}
