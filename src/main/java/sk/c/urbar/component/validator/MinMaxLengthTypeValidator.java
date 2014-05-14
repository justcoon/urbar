package sk.c.urbar.component.validator;

import java.util.ResourceBundle;

/**
 * min-max length {@link sk.c.urbar.component.validator.ITypeValidator}
 * @author coon
 */
//TODO otestovat
public class MinMaxLengthTypeValidator<T> extends ATypeValidator<T> {

    protected Integer min;
    protected Integer max;

    public MinMaxLengthTypeValidator(String fieldId, Integer min, Integer max, ResourceBundle resourceBundle) {
        super(fieldId, min != null && min > 0, resourceBundle);
        this.min = min;
        this.max = max;
    }

    @Override
    protected ValidatorState validateType(T typeToValidate) {

        ValidatorState retVal = null;

        String v = getStringValue(typeToValidate);

        boolean match = false;

        if (required && v != null && ((min != null && v.length() < min) || (max != null && v.length() > max))) {
            retVal = ValidatorState.ERROR;
        } else {
            retVal = ValidatorState.VALID;
        }


        return retVal;
    }

    protected String getStringValue(T typeToValidate) {
        return typeToValidate != null ? typeToValidate.toString() : null;
    }
}
