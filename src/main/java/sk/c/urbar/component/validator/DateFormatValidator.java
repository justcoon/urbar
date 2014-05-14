package sk.c.urbar.component.validator;

import sk.c.urbar.Utils;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * date format {@link sk.c.urbar.component.validator.ITypeValidator}
 * @author coon
 */
public class DateFormatValidator<T> extends ATypeValidator<T> {

    protected SimpleDateFormat format;


    public DateFormatValidator(String fieldId, boolean required, SimpleDateFormat format, ResourceBundle resourceBundle) {
        super(fieldId, required, resourceBundle);
        this.format = format;

    }

    @Override
    protected ValidatorState validateType(T typeToValidate) {

        ValidatorState retVal = null;

        String v = getStringValue(typeToValidate);

        boolean match = false;

        if (required && (v == null || v.length() <= 0 || !match(v))) {
            retVal = ValidatorState.ERROR;
        } else if (!required && (v != null && v.length() > 0 && !match(v))) {
            retVal = ValidatorState.WARNING;
        } else {
            retVal = ValidatorState.VALID;
        }


        return retVal;
    }

    protected boolean match(String value) {
       return Utils.getDate(value,format) != null;
    }


    protected String getStringValue(T typeToValidate) {
        return typeToValidate != null ? typeToValidate.toString() : null;
    }
}
