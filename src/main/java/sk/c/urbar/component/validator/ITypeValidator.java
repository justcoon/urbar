package sk.c.urbar.component.validator;

/**
 * type validator
 * @author coon
 *
 * @see sk.c.urbar.component.validator.IValidationResult
 */
public interface ITypeValidator<T> {

    /**
     * validate
     * @param typeToValidate
     * @return
     */
    IValidationResult validate(T typeToValidate);

}
