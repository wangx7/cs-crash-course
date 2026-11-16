public class PoolLab {
    public static void main(String[] args) {
        String a = "notice";
        String b = "notice";
        String c = new String("notice");
        String part = "no";
        String d = part + "tice";
        String e = d.intern();
        System.out.println("a==b=" + (a == b));
        System.out.println("a==c=" + (a == c));
        System.out.println("a==d=" + (a == d));
        System.out.println("a==e=" + (a == e));
        System.out.println("c==d=" + (c == d));
        System.out.println("d==e=" + (d == e));
        System.out.println("a.equals(d)=" + a.equals(d));
        System.out.println("c.intern()==a=" + (c.intern() == a));
        String fresh = new StringBuilder("bla").append("de").toString();
        System.out.println("fresh.intern()==fresh=" + (fresh.intern() == fresh));
    }
}