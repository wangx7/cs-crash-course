public class ReferencePuzzle {
    static class WorkOrder {
        int status;

        WorkOrder(int status) {
            this.status = status;
        }
    }

    // snippet:process
    static void process(WorkOrder order, int attempts) {
        order.status = 1;
        order = new WorkOrder(9);
        attempts++;
    }
    // end:process

    public static void main(String[] args) {
        // snippet:main
        WorkOrder order = new WorkOrder(0);
        int attempts = 0;
        process(order, attempts);
        System.out.println("status=" + order.status);
        System.out.println("attempts=" + attempts);
        // end:main
    }
}
