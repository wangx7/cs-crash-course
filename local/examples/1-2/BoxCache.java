public class BoxCache {
	public static void main(String[] args) {
		Integer x = 127, y = 127;
		System.out.println(x == y);
		Integer p = 128, q = 128;
		System.out.println(p == q);
		System.out.println(p.equals(q));
		System.out.println(p.intValue() == q.intValue());
	}
}
