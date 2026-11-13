public class StackDepth {
	static long depth = 0;

	public static void main(String[] args) {
		try {
			go();
		} catch (StackOverflowError e) {
			System.out.println("StackOverflowError，深度停在 " + depth);
		}
	}

	static void go() {
		depth++;
		go();
	}
}
