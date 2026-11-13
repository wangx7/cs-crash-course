public class MissingReturn {
    static int fee(boolean paid) {
        if (paid) {
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(fee(true));
    }
}
