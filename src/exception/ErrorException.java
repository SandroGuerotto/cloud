package exception;

/**
 * Universal exception
 * @author Sandro Guerotto
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 02.11.2016
 * @lastUpdate 04.11.2016 / by Sandro Guerotto
 */
public class ErrorException extends CloudException {

	private final String msg = "Ein Fehler ist aufgetreten";

	public ErrorException(char type) {
		setType(type);
		setMsg(msg);
	}
	public ErrorException() {
		setMsg(msg);
	}
}
