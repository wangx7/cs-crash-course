public class ReturnLab {
    static class WorkOrder {
        int status;

        WorkOrder(int status) {
            this.status = status;
        }
    }

    // snippet:methods
    static WorkOrder create() {
        WorkOrder order = new WorkOrder(0);
        return order;
    }

    static WorkOrder replace(WorkOrder order) {
        order = new WorkOrder(9);
        return order;
    }
    // end:methods

    public static void main(String[] args) {
        // snippet:return
        WorkOrder order = create();
        replace(order);
        System.out.println("ignored=" + order.status);
        order = replace(order);
        System.out.println("received=" + order.status);
        // end:return

        // snippet:null
        WorkOrder alias = order;
        order = null;
        System.out.println("aliasStatus=" + alias.status);
        System.out.println("orderIsNull=" + (order == null));
        // end:null
    }
}
