package mailManager.exception;

import java.lang.RuntimeException;

public class DuplicateTypeException extends RuntimeException {
    public DuplicateTypeException(Class<?> c) {
        super("Duplicate Class : " + c.getName());
    }
}
