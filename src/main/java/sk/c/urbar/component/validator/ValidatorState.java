package sk.c.urbar.component.validator;

/**
 * validation state
 *
 * @author coon
 */
public enum ValidatorState {


    VALID("validation_valid"),

    WARNING("validation_warning"),

    ERROR("validation_error");

    public final String style;

    private ValidatorState(String style) {
        this.style = style;
    }
}
