public class ReassignOrder {
    static class WorkOrder {
        int status;
        Integer assigneeId;

        WorkOrder(int status, Integer assigneeId) {
            this.status = status;
            this.assigneeId = assigneeId;
        }
    }

    // snippet:assignCopy
    static WorkOrder assignCopy(WorkOrder source, int assigneeId) {
        if (source == null) {
            throw new IllegalArgumentException("工单不存在");
        }
        WorkOrder copy = new WorkOrder(source.status, source.assigneeId);
        copy.assigneeId = assigneeId;
        return copy;
    }
    // end:assignCopy

    public static void main(String[] args) {
        // snippet:main
        WorkOrder source = new WorkOrder(0, null);
        WorkOrder assigned = assignCopy(source, 7);
        System.out.println("sameObject=" + (source == assigned));
        System.out.println("sourceAssignee=" + source.assigneeId);
        System.out.println("assignedAssignee=" + assigned.assigneeId);
        try {
            assignCopy(null, 7);
        } catch (IllegalArgumentException e) {
            System.out.println("rejected=" + e.getMessage());
        }
        // end:main
    }
}
