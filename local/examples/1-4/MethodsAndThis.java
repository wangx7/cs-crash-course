class DisplayName {
    private String name = "unset";

    void rename(String name) {
        System.out.println("before: field=" + this.name + ", parameter=" + name);
        this.name = name;
    }

    String describe() {
        return name;
    }
}

public class MethodsAndThis {
    public static void main(String[] args) {
        DisplayName left = new DisplayName();
        DisplayName right = new DisplayName();

        left.rename("left");
        right.rename("right");
        System.out.println("left.describe(): " + left.describe());
        System.out.println("right.describe(): " + right.describe());
    }
}
