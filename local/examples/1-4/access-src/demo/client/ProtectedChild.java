package demo.client;

import demo.model.Vault;

public class ProtectedChild extends Vault {
    int readFromThis() {
        return this.inheritedValue;
    }

    int readFromChild(ProtectedChild child) {
        return child.inheritedValue;
    }

    public static void main(String[] args) {
        ProtectedChild child = new ProtectedChild();
        System.out.println("this.inheritedValue: " + child.readFromThis());
        System.out.println("child.inheritedValue: " + child.readFromChild(child));
    }
}
