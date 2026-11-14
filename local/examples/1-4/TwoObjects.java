class Counter {
    private int value;

    void increment() {
        value++;
    }

    int getValue() {
        return value;
    }
}

public class TwoObjects {
    public static void main(String[] args) {
        Counter first = new Counter();
        Counter second = new Counter();

        first.increment();
        System.out.println("two objects: first=" + first.getValue()
                + ", second=" + second.getValue());

        second = first;
        second.increment();
        System.out.println("after alias: first=" + first.getValue()
                + ", second=" + second.getValue());
        System.out.println("first == second: " + (first == second));
    }
}
