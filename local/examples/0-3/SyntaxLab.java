import java.util.Arrays;

public class SyntaxLab {
    static int fieldCount;

    public static void main(String[] args) {
        int count = 3;
        long id = 9007199254740993L;
        float ratio = 0.5F;
        double rate = 0.5;
        boolean enabled = count > 0;
        char grade = 'A';
        String title = "A";
        System.out.println("types=" + count + "," + id + "," + ratio
                + "," + rate + "," + enabled + "," + grade + "," + title);

        double a = 5 / 2;
        double b = 5 / 2.0;
        System.out.println("division=" + a + "," + b);

        int[] numbers = new int[3];
        String[] names = new String[2];
        System.out.println("defaults=" + fieldCount + ","
                + Arrays.toString(numbers) + "," + Arrays.toString(names));

        String name = null;
        boolean hasName = name != null && !name.isEmpty();
        System.out.println("shortCircuit=" + hasName);

        int[] stocks = {8, 0, 3};
        for (int stock : stocks) {
            stock = 0;
        }
        System.out.println("afterForEach=" + Arrays.toString(stocks));
        for (int i = 0; i < stocks.length; i++) {
            stocks[i] = 0;
        }
        System.out.println("afterIndex=" + Arrays.toString(stocks));

        final int[] fixed = {8, 0};
        fixed[0] = 9;
        System.out.println("finalArray=" + Arrays.toString(fixed));

        int attempt = 0;
        while (attempt < 2) {
            attempt++;
        }
        System.out.println("while=" + attempt);

        int first = -1;
        int[] quantities = {0, 3, 8};
        for (int i = 0; i < quantities.length; i++) {
            if (quantities[i] > 0) {
                first = i;
                break;
            }
        }
        System.out.println("firstAvailableIndex=" + first);

        String status = "DRAFT";
        switch (status) {
            case "DRAFT":
                System.out.println("switch=草稿");
                break;
            case "LIVE":
                System.out.println("switch=已上架");
                break;
            default:
                System.out.println("switch=未知状态");
        }

        switch (status) {
            case "DRAFT":
                System.out.println("fallThrough=草稿");
            case "LIVE":
                System.out.println("fallThrough=已上架");
                break;
            default:
                System.out.println("fallThrough=未知状态");
        }
    }
}
