package demo.client;

import demo.model.Vault;

public class OtherPackage {
    public static void main(String[] args) {
        Vault vault = new Vault();
        System.out.println("publicValue: " + vault.publicValue);
    }
}
