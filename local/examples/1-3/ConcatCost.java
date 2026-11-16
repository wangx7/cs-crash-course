public class ConcatCost {
    public static void main(String[] args) {
        int n = 20000;
        long start = System.nanoTime();
        String s = "";
        for (int i = 0; i < n; i++) {
            s += "x";
        }
        long plusDone = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append("x");
        }
        String t = builder.toString();
        long builderDone = System.nanoTime();
        System.out.println("sameContent=" + s.equals(t));
        System.out.println("length=" + s.length());
        System.out.println("plusMs=" + (plusDone - start) / 1000000);
        System.out.println("builderMs=" + (builderDone - plusDone) / 1000000);
    }
}