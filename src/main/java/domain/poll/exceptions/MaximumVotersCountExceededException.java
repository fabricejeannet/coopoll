package domain.poll.exceptions;

public class MaximumVotersCountExceededException extends Throwable {
    public MaximumVotersCountExceededException(String message) {
        super(message);
    }
}
