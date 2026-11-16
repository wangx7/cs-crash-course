public class ComparePuzzle {
    public static void main(String[] args) {
        String a = "notice";
        String b = "notice";
        String c = new String("notice");
        System.out.println("a==b=" + (a == b));
        System.out.println("a==c=" + (a == c));
        System.out.println("a.equals(c)=" + a.equals(c));
        System.out.println("fold==a=" + ("no" + "tice" == a));
    }
}