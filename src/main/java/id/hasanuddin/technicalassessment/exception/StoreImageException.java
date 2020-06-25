package id.hasanuddin.technicalassessment.exception;

public class StoreImageException extends RuntimeException {

    public StoreImageException(Throwable e) {
        super("There was a problem saving the image", e);
    }

}