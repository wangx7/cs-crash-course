public class NullDemo {
	public static void main(String[] args) {
		String s = null;
		try {
			System.out.println(s.length());
		} catch (NullPointerException e) {
			System.out.println("s.length() 抛出: " + e);
		}

		Integer n = null;
		try {
			int m = n;
			System.out.println(m);
		} catch (NullPointerException e) {
			System.out.println("int m = n 抛出: " + e);
		}

		System.out.println(s == null);
	}
}
