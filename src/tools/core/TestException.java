package tools.core;

public class TestException extends Exception {

    public TestException(String message) {
	super(message);
    }

    public TestException(String message, Throwable t) {
	super(message, t);
    }
}
