public class Utf16 {
    public static void main(String[] args) {
        String s = "A😀B";
        int afterEmoji = s.offsetByCodePoints(0, 2);

        System.out.println("s: " + s);
        System.out.println("length(): " + s.length());
        System.out.println("codePointCount(): " + s.codePointCount(0, s.length()));
        System.out.printf("charAt(1), charAt(2): %04X %04X%n", (int) s.charAt(1), (int) s.charAt(2));
        System.out.println("codePointAt(1): " + s.codePointAt(1));
        System.out.println("offsetByCodePoints(0, 2): " + afterEmoji);
        System.out.println("substring(0, afterEmoji): " + s.substring(0, afterEmoji));
    }
}
