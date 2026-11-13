public class OrderStatsContinue {
    public static void main(String[] args) {
        int[] quantities = {2, 0, 3, -1, 6};
        int total = sumBeforeStop(quantities);
        String label = total >= 5 ? "可发货" : "待凑单";
        System.out.println("件数=" + total);
        System.out.println(label);
    }

    static int sumBeforeStop(int[] quantities) {
        int total = 0;
        for (int i = 0; i < quantities.length; i++) {
            int quantity = quantities[i];
            if (quantity < 0) {
                continue;
            }
            if (quantity == 0) {
                continue;
            }
            total += quantity;
        }
        return total;
    }
}
