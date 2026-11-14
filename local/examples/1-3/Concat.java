public class Concat {
    static String runtime(String prefix) {
        return prefix + "ade";
    }

    static String plusLoop() {
        String s = "";
        for (int i = 0; i < 3; i++) {
            s += i;
        }
        return s;
    }

    static String builderLoop() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            b.append(i);
        }
        return b.toString();
    }

    public static void main(String[] args) {
        System.out.println("compile-time: " + ("bl" + "ade"));
        System.out.println("runtime: " + runtime("bl"));
        System.out.println("plusLoop: " + plusLoop());
        System.out.println("builderLoop: " + builderLoop());
    }
}
