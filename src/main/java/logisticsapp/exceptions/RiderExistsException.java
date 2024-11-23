package logisticsapp.exceptions;

public class RiderExistsException extends RuntimeException {

    int status;

    public RiderExistsException(String message, int status) {
        super(message);
        this.status = status;
    }
}
