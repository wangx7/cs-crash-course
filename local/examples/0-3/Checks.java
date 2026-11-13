import java.util.Arrays;

public class Checks {
    public static void main(String[] args) {
        // 1. 数组元素有默认值
        int[] counts = new int[3];
        String[] names = new String[2];
        System.out.println(Arrays.toString(counts));
        System.out.println(Arrays.toString(names));

        // 2. 增强 for 的循环变量是副本：赋值不写回数组
        int[] quantities = {2, 0, 3};
        for (int quantityCopy : quantities) {
            quantityCopy *= 2;
        }
        System.out.println(Arrays.toString(quantities));
        // 用下标才能写回
        for (int i = 0; i < quantities.length; i++) {
            quantities[i] *= 2;
        }
        System.out.println(Arrays.toString(quantities));

        // 3. && 短路：左边为 false，右边不执行
        int[] missing = null;
        System.out.println(missing != null && missing.length > 0);

        // 4. switch 匹配字符串（冒号写法，要 break）
        String status = "PAID";
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

        // 5. 整数除法：先算完，再赋给 double
        double average = 5 / 2;
        System.out.println(average);
        System.out.println(5 / 2.0);

        // 6. if 接受 Boolean：编译通过，但 null 拆箱会在运行时抛异常
        Boolean flag = null;
        if (flag) {
            System.out.println("不会走到");
        }
    }
}
