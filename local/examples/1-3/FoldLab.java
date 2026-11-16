public class FoldLab {
    public static void main(String[] args) {
        String a = "notice";
        String part = "no";
        final String fixed = "no";
        String r1 = "no" + "tice";
        String r2 = part + "tice";
        String r3 = fixed + "tice";
        System.out.println("r1==a=" + (r1 == a));
        System.out.println("r2==a=" + (r2 == a));
        System.out.println("r3==a=" + (r3 == a));
    }
}