package demo.model;

public class SamePackagePrivateError {
    public static void main(String[] args) {
        System.out.println(new Vault().secret);
    }
}
