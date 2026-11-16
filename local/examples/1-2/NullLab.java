public class NullLab {
    static class WorkOrder {
        int status;
        Integer assigneeId;
    }

    public static void main(String[] args) {
        // snippet:defaults
        WorkOrder order = new WorkOrder();
        System.out.println("statusDefault=" + order.status);
        System.out.println("assigneeDefault=" + order.assigneeId);
        // end:defaults

        // snippet:unbox
        try {
            int id = order.assigneeId;
            System.out.println(id);
        } catch (NullPointerException e) {
            System.out.println("unbox=" + e.getClass().getSimpleName());
        }
        // end:unbox

        // snippet:field
        WorkOrder missing = null;
        try {
            System.out.println(missing.status);
        } catch (NullPointerException e) {
            System.out.println("field=" + e.getClass().getSimpleName());
        }
        // end:field

        // snippet:arrays
        int[] counts = new int[1];
        WorkOrder[] orders = new WorkOrder[1];
        System.out.println("countElement=" + counts[0]);
        System.out.println("orderElement=" + orders[0]);
        // end:arrays
    }
}
