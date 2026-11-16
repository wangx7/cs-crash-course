public class InventoryReport {
    public static void main(String[] args) {
        String[] names = {"钢笔", "笔记本", "尺子", "文件夹", "橡皮"};
        int[] stocks = {8, 0, -2, 3, 12};
        final int threshold = 5;

        for (int i = 0; i < stocks.length; i++) {
            String status = statusOf(stocks[i], threshold);
            System.out.println(names[i] + "：" + status);
        }
        for (int stock : stocks) {
            stock = 0;
        }
        System.out.println("清零后=" + java.util.Arrays.toString(stocks));
        System.out.println("可用库存合计：" + totalAvailable(stocks));
    }

    static String statusOf(int stock, int threshold) {
        if (stock < 0) {
            return "数据异常";
        }
        if (stock == 0) {
            return "缺货";
        }
        return stock < threshold ? "需补货" : "正常";
    }

    static int totalAvailable(int[] stocks) {
        int total = 0;
        for (int stock : stocks) {
            if (stock <= 0) {
                continue;
            }
            total += stock;
        }
        return total;
    }
}