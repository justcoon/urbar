package sk.c.urbar.component.validator;

/**
 * Created by coon on 12/30/13.
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
