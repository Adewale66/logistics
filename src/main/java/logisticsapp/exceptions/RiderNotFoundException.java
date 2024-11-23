package logisticsapp.exceptions;

public class RiderNotFoundException extends RuntimeException{

    int status;

    public RiderNotFoundException(String message, int status) {
        super(message);
        this.status = status;
    }
}
