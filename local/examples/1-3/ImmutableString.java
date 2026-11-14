import java.util.Locale;

public class ImmutableString {
    public static void main(String[] args) {
        String raw = "  Blade  ";
        String trimmed = raw.trim();
        String upper = trimmed.toUpperCase(Locale.ROOT);

        System.out.println("raw: [" + raw + "]");
        System.out.println("trimmed: [" + trimmed + "]");
        System.out.println("upper: [" + upper + "]");
        System.out.println("raw == trimmed: " + (raw == trimmed));
        System.out.println("trimmed == upper: " + (trimmed == upper));
        System.out.println("trimmed == trimmed.trim(): " + (trimmed == trimmed.trim()));
    }
}
