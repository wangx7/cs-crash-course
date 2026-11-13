import java.util.Arrays;

public class SyntaxChecks {
    public static void main(String[] args) {
        // types
        int quantity = 3;
        long orderId = 3000000000L;
        double ratio = 0.5;
        float progress = 0.5F;
        boolean paid = false;
        char grade = 'A';
        String status = "PAID";
        System.out.println(quantity + "," + orderId + "," + ratio
                + "," + progress + "," + paid + "," + grade + "," + status);
        // defaults
        int[] counts = new int[3];
        String[] names = new String[2];
        System.out.println(Arrays.toString(counts));
        System.out.println(Arrays.toString(names));
        // branches
        if (quantity > 0 && "PAID".equals(status)) {
            System.out.println("可处理");
        } else {
            System.out.println("暂不处理");
        }
        int[] missing = null;
        System.out.println(missing != null && missing.length > 0);
        switch (status) {
            case "PAID":
                System.out.println("已付款");
                break;
            case "CLOSED":
                System.out.println("已关闭");
                break;
            default:
                System.out.println("未知状态");
        }
        // loops
        int[] quantities = {2, 0, 3};
        for (int quantityCopy : quantities) {
            quantityCopy *= 2;
        }
        System.out.println(Arrays.toString(quantities));
        for (int i = 0; i < quantities.length; i++) {
            quantities[i] *= 2;
        }
        System.out.println(Arrays.toString(quantities));
        int i = 0;
        while (i < quantities.length) {
            System.out.println(quantities[i]);
            i++;
        }
        int attempts = 0;
        do {
            attempts++;
        } while (attempts < 1);
        System.out.println(attempts);
        // numeric teaser
        double average = 5 / 2;
        System.out.println(average);
        System.out.println(5 / 2.0);
    }
}
