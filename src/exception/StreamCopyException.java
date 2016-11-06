package exception;

/**
 * Exception when copy a stream failed
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 25.10.2016
 * @lastUpdate 04.11.2016 / by Sandro Guerotto
 */
public class StreamCopyException extends CloudException {

	private final String msg = "Es gab ein Problem mit dem kopieren der Streams!";

	public StreamCopyException(char type) {
		setType(type);
		setMsg(msg);
	}

	public StreamCopyException(){
		setMsg(msg);
	}
}
