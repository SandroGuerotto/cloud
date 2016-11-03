package exception;

/**
 * Created by Sandro on 02.11.2016.
 */
public class ErrorException extends CloudException {

	private final String msg = "Ein Fehler ist aufgetreten";
	private char type;

	public ErrorException(char type) {
		this.type = type;
		setMsg(msg);
	}

	public char getType() {
		return type;
	}
}
