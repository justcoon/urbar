package sk.c.urbar.component.table;

/**
 * Created with IntelliJ IDEA.
 * User: coon
 * Date: 11/25/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
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
