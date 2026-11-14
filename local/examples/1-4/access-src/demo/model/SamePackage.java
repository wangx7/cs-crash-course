package demo.model;

public class SamePackage {
    public static void main(String[] args) {
        Vault vault = new Vault();
        System.out.println("packageValue: " + vault.packageValue);
        System.out.println("inheritedValue: " + vault.inheritedValue);
        System.out.println("publicValue: " + vault.publicValue);
    }
}
