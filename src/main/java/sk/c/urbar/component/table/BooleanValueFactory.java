package sk.c.urbar.component.table;

/**
 * boolean {@link sk.c.urbar.component.table.PropertyValueFactory}
 */
public class BooleanValueFactory extends PropertyValueFactory<Boolean> {
    protected String trueValue = ("true");
    protected String falseValue = ("false");

    public String getTrueValue() {
        return trueValue;
    }

    public void setTrueValue(String v) {
        this.trueValue = v;
    }

    public String getFalseValue() {
        return falseValue;
    }

    public void setFalseValue(String v) {
        this.falseValue = v;
    }

    @Override
    protected String getStringValue(Boolean value) {

        return Boolean.TRUE.equals(value) ? getTrueValue() : getFalseValue();
    }

}
