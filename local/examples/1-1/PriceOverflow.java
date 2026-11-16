public class PriceOverflow {
    public static void main(String[] args) {
        int unitCents = 50_000;
        int quantity = 50_000;
        long totalCents = unitCents * quantity;
        System.out.println(totalCents);
    }
}
