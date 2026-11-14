import java.util.Objects;

public class EqualsNull {
    public static void main(String[] args) {
        String stored = new String("WEB");
        String input = null;

        System.out.println("\"WEB\".equals(stored): " + "WEB".equals(stored));
        System.out.println("\"WEB\".equals(input): " + "WEB".equals(input));
        System.out.println("Objects.equals(stored, input): " + Objects.equals(stored, input));
        System.out.println("Objects.equals(null, null): " + Objects.equals(null, null));
        try {
            input.equals("WEB");
        } catch (NullPointerException e) {
            System.out.println("input.equals(\"WEB\") 抛出: " + e);
        }
    }
}
