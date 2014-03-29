package sk.c.urbar.component.validator;

/**
 * Created by coon on 12/30/13.
 */
public interface ITypeValidator<T> {
    IValidationResult validate(T typeToValidate);

}
