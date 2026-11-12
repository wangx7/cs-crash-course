public class Main {
    public static void main(String[] args) {
        System.out.println(Shop.greet());
        System.out.println("每页 " + Shop.PAGE_SIZE + " 条");
        System.out.println(Shop.price(199));
    }
}
