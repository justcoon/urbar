package sk.c.urbar.component.validator;

/**
 * Created by coon on 12/30/13.
 */
public interface IValidationResult {

    ValidatorState getState();

    String getMessage();
}
