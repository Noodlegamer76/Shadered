package com.noodlegamer76.shadered.core.component;


import com.noodlegamer76.shadered.entity.GameObject;

public class ComponentType<T extends Component> {
    private final InitComponents.ComponentSupplier<T> supplier;

    public ComponentType(InitComponents.ComponentSupplier<T> supplier) {
        this.supplier = supplier;
    }

    public Component create(GameObject gameObject) {
        return supplier.create(gameObject);
    }
}

