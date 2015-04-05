package tools.core;

public class TestAbortException extends TestException {

    public TestAbortException(String message) {
	super(message);
    }

    public TestAbortException(String message, Throwable t) {
	super(message, t);
    }
}
