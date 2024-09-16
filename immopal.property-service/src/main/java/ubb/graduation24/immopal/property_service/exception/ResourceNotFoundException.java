package ubb.graduation24.immopal.property_service.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final String message, Throwable cause) {
        super(message, cause);
    }
}
