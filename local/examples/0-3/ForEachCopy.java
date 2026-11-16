import java.util.Arrays;

public class ForEachCopy {
    public static void main(String[] args) {
        int[] stocks = {8, 0, 3};
        for (int stock : stocks) {
            stock = 0;
        }
        System.out.println(Arrays.toString(stocks));
    }
}
