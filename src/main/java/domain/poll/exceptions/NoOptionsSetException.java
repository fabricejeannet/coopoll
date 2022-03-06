package domain.poll.exceptions;

public class NoOptionsSetException extends Throwable {
    public NoOptionsSetException() {
        super("No option was set");
    }
}
