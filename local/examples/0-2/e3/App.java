public class App {
    public static void main(String[] args) {
        System.out.println("启动了");
        if (args.length > 0) {
            new Report().print();
        }
        System.out.println("结束");
    }
}
