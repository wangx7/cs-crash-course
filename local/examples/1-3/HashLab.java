import java.lang.reflect.Field;

public class HashLab {
    public static void main(String[] args) throws Exception {
        String s = new String("notice");
        Field hashField = String.class.getDeclaredField("hash");
        hashField.setAccessible(true);
        System.out.println("hashBefore=" + hashField.getInt(s));
        System.out.println("hashCode=" + s.hashCode());
        System.out.println("hashCodeAgain=" + s.hashCode());
        System.out.println("hashAfter=" + hashField.getInt(s));
    }
}