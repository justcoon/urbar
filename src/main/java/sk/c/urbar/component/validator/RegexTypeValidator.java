package sk.c.urbar.component.validator;

import java.util.ResourceBundle;

/**
 * Created by coon on 12/30/13.
 */
public class RegexTypeValidator<T> extends ATypeValidator<T> {

    protected String regex;


    public RegexTypeValidator(String fieldId, boolean required, String regex, ResourceBundle resourceBundle) {
        super(fieldId, required, resourceBundle);
        this.regex = regex;

    }

    @Override
    protected ValidatorState validateType(T typeToValidate) {

        ValidatorState retVal = null;

        String v = getStringValue(typeToValidate);

        boolean match = false;

        if (required && (v == null || v.length() <= 0 || !v.matches(regex))) {
            retVal = ValidatorState.ERROR;
        } else if (!required && (v != null && v.length() > 0 && !v.matches(regex))) {
            retVal = ValidatorState.WARNING;
        } else {
            retVal = ValidatorState.VALID;
        }


        return retVal;
    }

    protected String getStringValue(T typeToValidate) {
        return typeToValidate != null ? typeToValidate.toString() : null;
    }
}
