class UserAccount {
    private String name = "anonymous";
    private int balance = 10;

    UserAccount() {
        this("guest", 0);
        System.out.println("no-arg body");
    }

    UserAccount(String name, int balance) {
        System.out.println("before body: name=" + this.name + ", balance=" + this.balance);
        this.name = name;
        this.balance = balance;
        System.out.println("two-arg body");
    }

    String describe() {
        return name + ":" + balance;
    }
}

public class Construction {
    public static void main(String[] args) {
        UserAccount custom = new UserAccount("blade", 100);
        System.out.println("custom: " + custom.describe());

        UserAccount noArg = new UserAccount();
        System.out.println("no-arg: " + noArg.describe());
    }
}
