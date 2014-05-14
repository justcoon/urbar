package sk.c.urbar.component;

import javafx.beans.value.WritableValue;

/**
 * simple {@link javafx.beans.value.WritableValue}
 *
 * @param <T>
 * @author coon
 */
public class SimpleObjectValue<T> implements WritableValue<T> {
    T value;

    public SimpleObjectValue(T v) {
        this.setValue(v);
    }

    public SimpleObjectValue() {
        this(null);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T t) {
        value = t;
    }
}
