package sk.c.urbar.component.validator;

/**
 * Created by coon on 12/30/13.
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
