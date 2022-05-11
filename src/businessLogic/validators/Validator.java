package businessLogic.validators;

/**
 * Validator interface contains the validate method which will throw an exception if given data is invalid.
 *
 * @author ioana
 *
 *
 */

public interface Validator<T> {
    public void validate(T t);
}
