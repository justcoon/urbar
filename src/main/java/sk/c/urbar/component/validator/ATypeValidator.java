package sk.c.urbar.component.validator;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Created by coon on 12/30/13.
 */
public abstract class ATypeValidator<T> implements ITypeValidator<T> {

    protected ResourceBundle resourceBundle;
    protected String fieldId;
    protected boolean required;

    public ATypeValidator(String fieldId, boolean required, ResourceBundle resourceBundle) {
        this.fieldId = fieldId;
        this.resourceBundle = resourceBundle;
        this.required = required;
    }

    @Override
    public IValidationResult validate(T typeToValidate) {
        IValidationResult retVal = null;

        ValidatorState state = validateType(typeToValidate);

        if (state != null) {
            switch (state) {
                case ERROR:
                    retVal = createErrorResult(typeToValidate);
                    break;
                case WARNING:
                    retVal = createWarningResult(typeToValidate);
                    break;

                case VALID:
                    retVal = createValidResult(typeToValidate);
                    break;
                default:
                    ;

            }
        }


        return retVal;
    }

    protected IValidationResult createErrorResult(T typeToValidate) {
        return new ValidationResult(ValidatorState.ERROR, getErrorMessage());
    }

    protected IValidationResult createWarningResult(T typeToValidate) {
        return new ValidationResult(ValidatorState.WARNING, getWarningMessage());
    }

    protected IValidationResult createValidResult(T typeToValidate) {
        IValidationResult retVal = null;

        String m = getValidMessage();

        if (m != null) {
            retVal = new ValidationResult(ValidatorState.VALID, m);
        }
        return retVal;
    }

    protected abstract ValidatorState validateType(T typeToValidate);

    protected String getValidMessage() {
        String retVal = null;

        String t = getWarningMessageTemplate();

        if (t != null) {
            retVal = MessageFormat.format(t, getFieldName());
        }


        return retVal;
    }

    protected String getValidMessageTemplate() {
        return null;
    }

    protected String getWarningMessage() {
        String retVal = null;

        String t = getWarningMessageTemplate();

        if (t != null) {
            retVal = MessageFormat.format(t, getFieldName());
        }


        return retVal;
    }

    protected String getWarningMessageTemplate() {
        return resourceBundle.getString("validator.warningMessage");
    }

    protected String getErrorMessage() {
        String retVal = null;

        String t = getErrorMessageTemplate();

        if (t != null) {
            retVal = MessageFormat.format(t, getFieldName());
        }


        return retVal;
    }

    protected String getErrorMessageTemplate() {
        return resourceBundle.getString("validator.errorMessage");
    }

    protected String getFieldName() {
        return resourceBundle.getString(fieldId);
    }
}
