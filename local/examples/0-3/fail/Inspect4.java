public class Inspect4 {
    public static void main(String[] args) {
        int[] a = new int[2];
        if (a.length > 0) {
            System.out.println("有元素");
        }
        int big = a[5];
        int total = 0;
        if (big > 0) {
            total = big;
        }
        System.out.println(total);
        char c = 'x';
        System.out.println(c);
    }
}
