public class NumberLab {
    public static void main(String[] args) {
        // BEGIN promotion
        int unitCents = 50_000;
        int quantity = 50_000;
        long assigned = unitCents * quantity;
        long castAfter = (long) (unitCents * quantity);
        long castBefore = (long) unitCents * quantity;
        long checked = Math.multiplyExact((long) unitCents, (long) quantity);
        System.out.println("assigned=" + assigned);
        System.out.println("castAfter=" + castAfter);
        System.out.println("castBefore=" + castBefore);
        System.out.println("checkedLong=" + checked);
        // END promotion

        // BEGIN division
        System.out.println("intDivision=" + (5 / 2));
        System.out.println("castQuotient=" + (double) (5 / 2));
        System.out.println("castOperand=" + ((double) 5 / 2));
        System.out.println("negativeDivision=" + (-5 / 2));
        System.out.println("negativeRemainder=" + (-5 % 2));
        // END division

        // BEGIN overflow
        System.out.println("maxPlusOne=" + (Integer.MAX_VALUE + 1));
        try {
            Math.multiplyExact(unitCents, quantity);
        } catch (ArithmeticException e) {
            System.out.println("checkedInt=" + e.getClass().getSimpleName());
        }
        try {
            Math.addExact(Long.MAX_VALUE, 1L);
        } catch (ArithmeticException e) {
            System.out.println("checkedAdd=" + e.getClass().getSimpleName());
        }
        // END overflow

        // BEGIN narrow
        long large = 2_500_000_000L;
        System.out.println("narrowed=" + (int) large);
        try {
            Math.toIntExact(large);
        } catch (ArithmeticException e) {
            System.out.println("checkedNarrow=" + e.getClass().getSimpleName());
        }
        byte tiny = 127;
        tiny += 1;
        System.out.println("compoundByte=" + tiny);
        // END narrow

        // BEGIN widening
        long id = 9_007_199_254_740_993L;
        double floatingId = id;
        System.out.println("id=" + id);
        System.out.println("idRoundTrip=" + (long) floatingId);
        System.out.println("mixedEquality=" + (id == floatingId));
        System.out.println("integerEquality=" + (id == (long) floatingId));
        // END widening

        // BEGIN special
        try {
            int zero = 0;
            System.out.println(1 / zero);
        } catch (ArithmeticException e) {
            System.out.println("integerZero=" + e.getClass().getSimpleName());
        }
        System.out.println("floatingZero=" + (1.0 / 0.0));
        System.out.println("zeroOverZero=" + (0.0 / 0.0));
        double nan = Double.NaN;
        System.out.println("nanEqualsSelf=" + (nan == nan));
        System.out.println("isFinite=" + Double.isFinite(1.0 / 0.0));
        // END special

        // BEGIN unboxing
        Integer count = null;
        try {
            int next = count + 1;
            System.out.println(next);
        } catch (NullPointerException e) {
            System.out.println("nullUnboxing=" + e.getClass().getSimpleName());
        }
        // END unboxing
    }
}
