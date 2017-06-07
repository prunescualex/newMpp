package Domain.Validators;

import javax.xml.bind.ValidationException;


public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
