package demo.client;

import demo.model.Vault;

public class ProtectedChildError extends Vault {
    int readFromParent(Vault parent) {
        return parent.inheritedValue;
    }
}
