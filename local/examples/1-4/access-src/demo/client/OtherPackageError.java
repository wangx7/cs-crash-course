package demo.client;

import demo.model.Vault;

public class OtherPackageError {
    public static void main(String[] args) {
        System.out.println(new Vault().packageValue);
    }
}
