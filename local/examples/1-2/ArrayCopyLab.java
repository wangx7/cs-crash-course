public class ArrayCopyLab {
    static class WorkOrder {
        int status;

        WorkOrder(int status) {
            this.status = status;
        }
    }

    public static void main(String[] args) {
        // snippet:main
        WorkOrder[] original = { new WorkOrder(0) };
        WorkOrder[] copy = original.clone();
        System.out.println("sameArray=" + (original == copy));
        System.out.println("sameElement=" + (original[0] == copy[0]));
        copy[0].status = 1;
        System.out.println("originalStatus=" + original[0].status);
        copy[0] = new WorkOrder(9);
        System.out.println("originalAfterReplace=" + original[0].status);
        System.out.println("copyStatus=" + copy[0].status);
        // end:main
    }
}
