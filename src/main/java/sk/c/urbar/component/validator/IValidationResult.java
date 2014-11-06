package sk.c.urbar.component.validator;

/**
 * @author coon on 12/30/13.
 */
public interface IValidationResult {

    ValidatorState getState();

    String getMessage();
}
