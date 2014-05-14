package sk.c.urbar.component.validator;

/**
 * {@link sk.c.urbar.component.validator.IValidationResult} implementation
 *
 * @author coon
 */
public class ValidationResult implements IValidationResult {
    ValidatorState state;
    String message;

    public ValidationResult(ValidatorState state, String message) {
        this.message = message;
        this.state = state;
    }

    @Override
    public ValidatorState getState() {
        return state;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
