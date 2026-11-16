import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceQuote {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("用法：PriceQuote 单价 数量 折扣系数");
            System.exit(2);
        }
        try {
            BigDecimal unitPrice = new BigDecimal(args[0]);
            int quantity = Integer.parseInt(args[1]);
            BigDecimal rate = new BigDecimal(args[2]);
            if (unitPrice.signum() < 0 || quantity <= 0
                    || rate.compareTo(BigDecimal.ZERO) < 0
                    || rate.compareTo(BigDecimal.ONE) > 0) {
                throw new IllegalArgumentException("单价须非负，数量须为正整数，折扣系数须在 0 到 1 之间");
            }
            unitPrice = unitPrice.setScale(2, RoundingMode.UNNECESSARY);
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal payable = subtotal.multiply(rate).setScale(2, RoundingMode.HALF_UP);
            System.out.println("原价=" + subtotal.toPlainString());
            System.out.println("应付=" + payable.toPlainString());
        } catch (NumberFormatException e) {
            System.err.println("输入错误：单价和折扣系数须为十进制数，数量须为 int 范围内的整数");
            System.exit(2);
        } catch (ArithmeticException e) {
            System.err.println("输入错误：单价必须能精确到分");
            System.exit(2);
        } catch (IllegalArgumentException e) {
            System.err.println("输入错误：" + e.getMessage());
            System.exit(2);
        }
    }
}
