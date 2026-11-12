public class Check {
    public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : " ";
        System.out.println(name.isBlank() ? "名字是空的" : "你好，" + name);
    }
}
