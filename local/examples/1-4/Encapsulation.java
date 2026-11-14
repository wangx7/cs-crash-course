class Wallet {
    private int balance;

    Wallet(int balance) {
        this.balance = balance;
    }

    boolean withdraw(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    int getBalance() {
        return balance;
    }

    boolean sameBalance(Wallet other) {
        return this.balance == other.balance;
    }
}

public class Encapsulation {
    public static void main(String[] args) {
        Wallet wallet = new Wallet(100);

        System.out.println("withdraw(30): " + wallet.withdraw(30)
                + ", balance=" + wallet.getBalance());
        System.out.println("withdraw(80): " + wallet.withdraw(80)
                + ", balance=" + wallet.getBalance());
        System.out.println("withdraw(0): " + wallet.withdraw(0)
                + ", balance=" + wallet.getBalance());
        System.out.println("sameBalance(new Wallet(70)): "
                + wallet.sameBalance(new Wallet(70)));
    }
}
