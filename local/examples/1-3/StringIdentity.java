public class StringIdentity {
    public static void main(String[] args) {
        String a = "blade";
        String b = "blade";
        String c = new String("blade");
        String d = "bl" + "ade";
        String prefix = "bl";
        String e = prefix + "ade";
        final String fixed = "bl";
        String f = fixed + "ade";

        System.out.println("a == b: " + (a == b));
        System.out.println("a == c: " + (a == c));
        System.out.println("a == d: " + (a == d));
        System.out.println("a == e: " + (a == e));
        System.out.println("a == f: " + (a == f));
        System.out.println("a.equals(e): " + a.equals(e));
        System.out.println("c.intern() == a: " + (c.intern() == a));
    }
}
