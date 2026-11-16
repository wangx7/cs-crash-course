public class ImmutableLab {
    public static void main(String[] args) {
        String s = " Notice ";
        String upper = s.toUpperCase();
        String trimmed = s.trim();
        System.out.println("s=[" + s + "]");
        System.out.println("upper=[" + upper + "]");
        System.out.println("trimmed=[" + trimmed + "]");
        System.out.println("upper==s=" + (upper == s));
        System.out.println("trimmed==s=" + (trimmed == s));
        String clean = "notice";
        System.out.println("clean.trim()==clean=" + (clean.trim() == clean));
        System.out.println("clean.substring(0)==clean=" + (clean.substring(0) == clean));
        String shout = "NOTICE";
        System.out.println("shout.toUpperCase()==shout=" + (shout.toUpperCase() == shout));
        System.out.println("clean.replace('x','y')==clean=" + (clean.replace('x', 'y') == clean));
    }
}