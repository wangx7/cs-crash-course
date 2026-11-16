public class AssignmentLab {
    static class WorkOrder {
        int status;

        WorkOrder(int status) {
            this.status = status;
        }
    }

    public static void main(String[] args) {
        // snippet:main
        WorkOrder first = new WorkOrder(0);
        WorkOrder second = first;
        System.out.println("sameBefore=" + (first == second));
        second.status = 1;
        System.out.println("firstStatus=" + first.status);
        second = new WorkOrder(9);
        System.out.println("sameAfter=" + (first == second));
        System.out.println("secondStatus=" + second.status);
        // end:main
    }
}
