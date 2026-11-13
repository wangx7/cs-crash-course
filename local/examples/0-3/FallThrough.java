public class FallThrough {
    public static void main(String[] args) {
        String status = "PAID";
        switch (status) {
            case "PAID":
                System.out.println("已付款");
            case "CLOSED":
                System.out.println("已关闭");
            default:
                System.out.println("未知状态");
        }
    }
}
