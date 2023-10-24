package Annotations;

public class AnnotationError extends RuntimeException {
    public AnnotationError(String annotation) {
        super("Wrong or non-existand annotation, expected " + annotation);
    }
}
