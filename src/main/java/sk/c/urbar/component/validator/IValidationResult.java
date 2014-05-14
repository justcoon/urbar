package sk.c.urbar.component.validator;

/**
 * validation result
 *
 * @see sk.c.urbar.component.validator.ValidatorState
 *
 */
public interface IValidationResult {

    /**
     * get validation state
     * @return
     */
    ValidatorState getState();

    /**
     * get validation message
     * @return
     */
    String getMessage();
}
